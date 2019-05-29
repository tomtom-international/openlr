/**
 * Licensed to the TomTom International B.V. under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  TomTom International B.V.
 * licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
/**
 *  Copyright (C) 2009-2012 TomTom International B.V.
 *
 *   TomTom (Legal Department)
 *   Email: legal@tomtom.com
 *
 *   TomTom (Technical contact)
 *   Email: openlr@tomtom.com
 *
 *   Address: TomTom International B.V., Oosterdoksstraat 114, 1011DK Amsterdam,
 *   the Netherlands
 */
package openlr.encoder.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import openlr.LocationType;
import openlr.OpenLRProcessingException;
import openlr.encoder.properties.OpenLREncoderProperties;
import openlr.location.Location;
import openlr.map.Line;
import openlr.map.MapDatabase;
import openlr.map.utils.NodeCheck;

import org.apache.log4j.Logger;

/**
 * 
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public final class ExpansionHelper {

	/** Logging. */
	private static final Logger LOG = Logger.getLogger(ExpansionHelper.class);
	
	/**
	 * Utility class shall not be instantiated.
	 */
	private ExpansionHelper() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Gets the expanded location. The resulting path contains the expansion at
	 * the start, the original location and the expansion at the end.
	 *
	 * @param locRefData the loc ref data
	 * @return the expanded location
	 */
	public static List<Line> getExpandedLocation(final LocRefData locRefData) {
		ExpansionData expansion = locRefData.getExpansionData();
		Location loc = locRefData.getLocation();
		List<Line> expLocation = new ArrayList<Line>();
		expLocation.addAll(expansion.getExpansionStart());
		LocationType locType = loc.getLocationType();
		if (locType == LocationType.LINE_LOCATION
				|| locType == LocationType.CLOSED_LINE) {
			expLocation.addAll(loc.getLocationLines());
		} else if (locType == LocationType.POI_WITH_ACCESS_POINT
				|| locType == LocationType.POINT_ALONG_LINE) {
			expLocation.add(loc.getPoiLine());
		}
		expLocation.addAll(expansion.getExpansionEnd());
		return expLocation;
	}

	/**
	 * Creates an expanded location. The location loc will be investigated if an
	 * expansion at the start and at the end are possible. The expansion needs
	 * to be unique so that no deviation from that path is possible. The start
	 * and end of the expanded path shall correspond to valid nodes. A turn
	 * restriction check can be enabled so that the expansions are checked to be
	 * drivable.
	 *
	 * @param properties the OpenLR properties
	 * @param mdb the MapDatabase
	 * @param locRefData the loc ref data
	 * @return the expanded location
	 * @throws OpenLRProcessingException if accessing the properties fails
	 */
	public static ExpansionData createExpandedLocation(
			final OpenLREncoderProperties properties, final MapDatabase mdb,
			final LocRefData locRefData) throws OpenLRProcessingException {
		ExpansionData expData = ExpansionData.NO_EXPANSION;
		Location loc = locRefData.getLocation();
		List<? extends Line> linelist = null;
		LocationType locType = loc.getLocationType();
		int pOff = 0;
		int nOff = 0;
		switch (locType) {
		case LINE_LOCATION:
			linelist = loc.getLocationLines();
			if (loc.hasPositiveOffset()) {
				pOff = loc.getPositiveOffset();
			}
			if (loc.hasNegativeOffset()) {
				nOff = loc.getNegativeOffset();
			}
			expData = doExpansion(mdb, properties, linelist, pOff, nOff);
			break;
		case POI_WITH_ACCESS_POINT:
		case POINT_ALONG_LINE:
			List<Line> temp = new ArrayList<Line>();
			temp.add(loc.getPoiLine());
			linelist = temp;
			if (loc.hasPositiveOffset()) {
				pOff = loc.getPositiveOffset();
			}
			expData = doExpansion(mdb, properties, linelist, pOff, 0);
			break;
		case CIRCLE:
		case GEO_COORDINATES:
		case GRID:
		case POLYGON:
		case RECTANGLE:
		case CLOSED_LINE:
			break;
		default:
		case UNKNOWN:
			LOG.error("Unknown location type");
			break;
		}
		return expData;
	}

	/**
	 * Do expansion.
	 *
	 * @param mdb the mdb
	 * @param properties the properties
	 * @param lineList the line list
	 * @param posOff the pos off
	 * @param negOff the neg off
	 * @return the expansion data
	 * @throws OpenLRProcessingException the open lr processing exception
	 */
	private static ExpansionData doExpansion(final MapDatabase mdb,
			final OpenLREncoderProperties properties, final List<? extends Line> lineList,
			final int posOff, final int negOff) throws OpenLRProcessingException {
		// expand location at start if start node is invalid
		List<Line> expansionStart = new ArrayList<Line>();
		if (!NodeCheck.isValidNode(lineList.get(0).getStartNode())) {
			expansionStart = checkExpansion(lineList, true, posOff,	properties);
		}
		// expand location at end if end node is invalid
		List<Line> expansionDest = new ArrayList<Line>();
		if (!NodeCheck.isValidNode(lineList.get(lineList.size() - 1)
				.getEndNode())) {
			expansionDest = checkExpansion(lineList, false, negOff, properties);
		}

		// check turn restrictions, if enabled
		if (properties.isCheckTurnRestrictions()
				&& mdb.hasTurnRestrictions()) {
			ArrayList<Line> path = new ArrayList<Line>();
			// check at start
			path.addAll(expansionStart);
			path.addAll(lineList);
			if (mdb.hasTurnRestrictionOnPath(path)) {
				// if turn restrictions are on path, then reject this
				// expansion
				expansionStart.clear();
			}
			path.clear();
			// check at end
			path.addAll(lineList);
			path.addAll(expansionDest);
			if (mdb.hasTurnRestrictionOnPath(path)) {
				// if turn restrictions are on path, then reject this
				// expansion
				expansionDest.clear();
			}
		}
		// return expanded location
		if (LOG.isDebugEnabled()) {
			LOG.debug("expanded location created");
			LOG.debug("lines added at start: " + expansionStart.size());
			LOG.debug("lines added at end: " + expansionDest.size());
		}
		return new ExpansionData(expansionStart, expansionDest);
	}

	/**
	 * Check if the location can be expanded uniquely. The flag indicates
	 * whether the start or the end of the location shall be investigated. The
	 * expansion stops if a node is reached where it is possible to leave the
	 * path (deviation possible). It also stops if a loop is detected during
	 * expansion and if the maximum distance is expected to be exceeded.
	 * 
	 * @param location
	 *            the original location
	 * @param start
	 *            if true, expansion at start, if false, expansion at end
	 * @param offset
	 *            the existing offset
	 * @param properties
	 *            the encoder properties
	 * @return the expansion lines
	 * @throws OpenLRProcessingException
	 *             the open lr processing exception
	 */
	private static List<Line> checkExpansion(
			final List<? extends Line> location, final boolean start,
			final int offset, final OpenLREncoderProperties properties)
			throws OpenLRProcessingException {
		// determine where to expand
		Line line = null;
		if (start) {
			line = location.get(0);
		} else {
			line = location.get(location.size() - 1);
		}
		int totalOffset = offset;
		int maxOffset = properties.getMaximumDistanceLRP();
		// expansion path
		List<Line> expansion = new ArrayList<Line>();
		boolean finished = false;
		Iterator<? extends Line> candidates = null;
		while (!finished) {
			boolean nodeIsInvalid = true;
			// check if the desired node is valid
			// if node is not simple then we have to stop the expansion
			if (start) {
				// go backward
				candidates = line.getPrevLines();
				nodeIsInvalid = !NodeCheck.isValidNode(line.getStartNode());
			} else {
				// go forward
				candidates = line.getNextLines();
				nodeIsInvalid = !NodeCheck.isValidNode(line.getEndNode());
			}

			if (candidates.hasNext() && nodeIsInvalid) {
				// node is invalid and there is a possibility to expand the path
				Line expline = getExpansion(line, candidates);
				if (expline == null) {
					finished = true;
				} else if (location.contains(expline)
						|| expansion.contains(expline)) {
					// loop detected
					if (LOG.isDebugEnabled()) {
						LOG.debug("expansion would create a loop, stop here!");
					}
					finished = true;
				} else if (totalOffset + expline.getLineLength() > maxOffset) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("expansion would exceed the maxDistance value, stop here!");
					}
					finished = true;
				} else {
					if (LOG.isDebugEnabled()) {
						LOG.debug("expansion with line: " + expline.getID());
					}
					// expansion is possible
					if (start) {
						expansion.add(0, expline);
					} else {
						expansion.add(expline);
					}
					// prepare next iteration
					line = expline;
					totalOffset += line.getLineLength();
				}
			} else {
				finished = true;
			}
		}
		// return the list of lines being used as expansion
		return expansion;
	}

	/**
	 * Resolves the next line for expansion out of a set of candidate lines. If
	 * no expansion is possible the method returns null.
	 * 
	 * @param l
	 *            the previously expanded line
	 * @param candidates
	 *            set of candidate lines for expansion
	 * 
	 * @return next line for expansion or null if no further expansion possible
	 */
	private static Line getExpansion(final Line l,
			final Iterator<? extends Line> candidates) {
		List<Line> lines = new ArrayList<Line>();
		while (candidates.hasNext()) {
			lines.add(candidates.next());
		}
		if (lines.size() == 1) {
			return lines.get(0);
		} else if (lines.size() == 2) {
			Line l1 = lines.get(0);
			Line l2 = lines.get(1);
			boolean pair1 = NodeCheck.isPair(l, l1);
			boolean pair2 = NodeCheck.isPair(l, l2);
			if (pair1 && !pair2) {
				return l2;
			}
			if (pair2 && !pair1) {
				return l1;
			}
			if (pair1 && pair2) {
				boolean geom1 = checkLength(l, l1);
				boolean geom2 = checkLength(l, l2);
				if (geom1 && !geom2) {
					return l2;
				}
				if (geom2 && !geom1) {
					return l1;
				}
			}
		}
		return null;
	}

	/**
	 * Check length.
	 * 
	 * @param l1
	 *            the l1
	 * @param l2
	 *            the l2
	 * @return true, if successful
	 */
	private static boolean checkLength(final Line l1, final Line l2) {
		int diff = Math.abs(l1.getLineLength() - l2.getLineLength());
		return diff <= 1;
	}

}
