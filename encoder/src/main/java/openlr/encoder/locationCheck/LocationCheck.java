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
package openlr.encoder.locationCheck;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import openlr.OpenLRProcessingException;
import openlr.encoder.properties.OpenLREncoderProperties;
import openlr.location.Location;
import openlr.map.Line;
import openlr.map.MapDatabase;
import openlr.map.utils.IteratorHelper;

import org.apache.log4j.Logger;

/**
 * A location needs to be checked if it is valid for OpenLR(tm) encoding.
 * 
 * A valid location must fulfill the following recommendations:
 * <ul>
 * <li>(a) there must be at least one line in the list
 * <li>(b) the list must be ordered in a way that the sequence of lines is
 * connected and traversable from start to end
 * <li>(c) the offsets must be less than the maximum distance value
 * </ul>
 * <br>
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public abstract class LocationCheck {

	/** Logging. */
	private static final Logger LOG = Logger.getLogger(LocationCheck.class);

	/** The Constant MAX_LAT. */
	private static final float MAX_LAT = 90;

	/** The Constant MIN_LAT. */
	private static final float MIN_LAT = -90;

	/** The Constant MAX_LON. */
	private static final float MAX_LON = 180;

	/** The Constant MIN_LON. */
	private static final float MIN_LON = -180;

	/**
	 * Checks if the location is valid. A valid location does contain at least
	 * one line, is connected and traversable and (optionally) it does not
	 * violate any turn restriction.
	 * 
	 * @param properties
	 *            the OpenLR properties
	 * @param mapDB
	 *            the MapDatabase
	 * @param location
	 *            the location to be encoded
	 * @return the encoder return code
	 * @throws OpenLRProcessingException
	 *             the open lr processing exception
	 */
	public abstract CheckResult check(final OpenLREncoderProperties properties,
			final MapDatabase mapDB, final Location location)
			throws OpenLRProcessingException;
	
	/**
	 * Check offset validity and if necessary adjust the location.
	 * 
	 * The location will be changed if the offset length exceed the length
	 * of the affected lines. These superfluous lines will be removed from the
	 * location and the offset values will be adjusted.
	 *
	 * @param properties the properties
	 * @param location the location
	 * @return the location
	 * @throws OpenLRProcessingException the open lr processing exception
	 */
	public abstract CheckResult checkOffsets(final OpenLREncoderProperties properties,
			final Location location) throws OpenLRProcessingException;

	/**
	 * Check coordinate bounds.
	 * 
	 * @param x
	 *            the longitude
	 * @param y
	 *            the latitude
	 * 
	 * @return true, if coordinate is valid
	 */
	protected final boolean checkCoordinateBounds(final double x, final double y) {
		return (x >= MIN_LON && x <= MAX_LON && y >= MIN_LAT && y <= MAX_LAT);
	}

	/**
	 * Checks if the location violates any turn restrictions. If the restricted
	 * path is fully covered and in the same order part of the location, then
	 * the location violates the turn restriction. The check returns true if no
	 * turn restriction is violated, otherwise false. The set of turn
	 * restrictions is hold by the MapDatabase and if there are no turn
	 * restrictions available then the check will return true.
	 * 
	 * @param mapDB
	 *            the MapDatabase, including turn restriction information
	 * @param location
	 *            the location to be tested
	 * 
	 * @return true, if no turn restriction is violated, otherwise false
	 */
	protected final boolean checkTurnRestrictions(final MapDatabase mapDB,
			final Location location) {
		if (!mapDB.hasTurnRestrictions()) {
			return true;
		}
		return mapDB.hasTurnRestrictionOnPath(location.getLocationLines());
	}

	/**
	 * Check turn restriction closed line.
	 *
	 * @param mapDB the map db
	 * @param lines the lines
	 * @return true, if successful
	 */
	protected final boolean checkTurnRestrictionClosedLine(
			final MapDatabase mapDB, final List<? extends Line> lines) {
		if (!mapDB.hasTurnRestrictions()) {
			return true;
		}
		// check whether there is a turn restriction from last to first
		// line, too.
		List<Line> theLines = new ArrayList<Line>(lines);
		theLines.add(theLines.get(0));
		return mapDB.hasTurnRestrictionOnPath(theLines);
	}

	/**
	 * Checks a location whether it is connected and traversable from its start
	 * to its end. The method returns true if it is connected and traversable,
	 * otherwise false.
	 * 
	 * @param lines
	 *            the lines
	 * @return true, if the location is connected and traversable from its start
	 *         to its end
	 */
	protected final boolean checkLocationConnection(
			final List<? extends Line> lines) {
		/*
		 * check whether the subsequent line in the location is part of the set
		 * of successors of that line
		 */
		int size = lines.size();
        String error = null;
        if ((size == 1) && (lines.get(0) == null)) {
            error = "location contains only a null entry in lines list";
        }

        /* iterate over all location lines from start to end */
        /* skip last line, because this line does not have a successor */
        for (int i = 0; i < size - 1; ++i) {
            Line currentLine = lines.get(i);
            Line successorLine = lines.get(i + 1);
            
            if (currentLine == null || successorLine == null) {
                error = "location contains null entries in lines list";
                break;
            } else {
                Iterator<? extends Line> succLines = currentLine.getNextLines();
                if (!IteratorHelper.contains(succLines, successorLine)) {
                    error = "location not connected from "
                            + currentLine.getID() + " to "
                            + successorLine.getID();
                    break;
                }
            }
        }

        if (error != null) {
            LOG.error(error);
            return false;
        } else {
            return true;
        }
	}

	/**
	 * Checks if is path closed.
	 * 
	 * @param lines
	 *            the lines
	 * @return true, if is path closed
	 */
	protected final boolean isPathClosed(final List<? extends Line> lines) {
		int size = lines.size();
		/* Check whether first line is within the successors of last line */
		Line firstLine = lines.get(0);
		Line lastLine = lines.get(size - 1);
        if (firstLine == null || lastLine == null) {
            LOG.error("location contains null entries in lines list");
            return false;
        }
		Iterator<? extends Line> succLines = lastLine.getNextLines();
		if (!IteratorHelper.contains(succLines, firstLine)) {
			LOG.error("unclosed closed location, not connected from "
					+ lastLine.getID() + " to " + firstLine.getID());
			return false;
		}
		return true;
	}
}
