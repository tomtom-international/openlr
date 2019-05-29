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
package openlr.decoder.worker.coverage;

import java.awt.Polygon;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import openlr.map.GeoCoordinates;
import openlr.map.InvalidMapDataException;
import openlr.map.Line;
import openlr.map.MapDatabase;
import openlr.map.utils.GeometryUtils;

/**
 * The Class ClosedLineCoverage.
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author DLR e.V. (RE)
 */
public class ClosedLineCoverage extends AbstractCoverage {

	/** The corner points. */
	private final List<GeoCoordinates> cornerPoints;

	/**
	 * Instantiates a new closed line coverage.
	 *
	 * @param l the l
	 * @throws InvalidMapDataException the invalid map data exception
	 */
	public ClosedLineCoverage(final List<Line> l)
			throws InvalidMapDataException {
		cornerPoints = determinePolygon(l);
	}

	/**
	 * Determine polygon.
	 *
	 * @param lines the lines
	 * @return the list
	 * @throws InvalidMapDataException the invalid map data exception
	 */
	private List<GeoCoordinates> determinePolygon(final List<Line> lines)
			throws InvalidMapDataException {
		List<GeoCoordinates> cPoints = new ArrayList<GeoCoordinates>();
		for (Line line : lines) {
			List<GeoCoordinates> shape = line.getShapeCoordinates();
			if (shape != null) {				
				GeoCoordinates prev = null;
				for (GeoCoordinates gc : shape) {					
					if (prev == null || !gc.equals(prev)) {
						cPoints.add(gc);
					} 
					prev = gc;
				}
			} else {
				cPoints.add(line.getStartNode().getGeoCoordinates());
			}
		}
		return cPoints;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	final Iterator<? extends Line> determineCoveredLines(final MapDatabase mdb) {
		Polygon polygon = createPolygon(cornerPoints);
		Rectangle2D bounds = polygon.getBounds2D();
		double centerLongitude = bounds.getCenterX() / COORD_TO_INT_FACTOR;
		double centerLatitude = bounds.getCenterY() / COORD_TO_INT_FACTOR;
		double topleftLongitude = bounds.getX() / COORD_TO_INT_FACTOR;
		double topleftLatitude = bounds.getY() / COORD_TO_INT_FACTOR;
		int radius = (int) GeometryUtils.distance(centerLongitude,
				centerLatitude, topleftLongitude, topleftLatitude);
		return mdb.findLinesCloseByCoordinate(centerLongitude, centerLatitude,
				((int) radius + MINIMUM_ADDITIONAL_DISTANCE));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	final boolean isContainedIn(final double longitude, final double latitude)
			throws InvalidMapDataException {
		PolygonCoverage coverage = new PolygonCoverage(cornerPoints);
		return coverage.isContainedIn(longitude, latitude);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	final boolean intersectsBoundary(final GeoCoordinates gcStart, final GeoCoordinates gcEnd) {
		PolygonCoverage coverage = new PolygonCoverage(cornerPoints);
		return coverage.intersectsBoundary(gcStart, gcEnd);
	}

}
