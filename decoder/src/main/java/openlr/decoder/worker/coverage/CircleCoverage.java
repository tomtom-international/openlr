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

import java.util.Iterator;

import openlr.map.GeoCoordinates;
import openlr.map.Line;
import openlr.map.MapDatabase;
import openlr.map.utils.GeometryUtils;

/**
 * The Class CircleCoverage.
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author DLR e.V. (RE)
 */
public class CircleCoverage extends AbstractCoverage {
	
	/** The center. */
	private final GeoCoordinates center;
	
	/** The radius. */
	private final long radius;
	
	
	/**
	 * Instantiates a new circle coverage.
	 *
	 * @param c the c
	 * @param r the r
	 */
	public CircleCoverage(final GeoCoordinates c,
			final long r) {
		center = c;
		radius = r;	
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	final Iterator<? extends Line> determineCoveredLines(
			final MapDatabase mdb) {
		Iterator<? extends Line> directLinesIterator = mdb
				.findLinesCloseByCoordinate(center.getLongitudeDeg(),
						center.getLatitudeDeg(),
						((int) radius + MINIMUM_ADDITIONAL_DISTANCE));
		return directLinesIterator;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	final boolean isContainedIn(final double longitude, final double latitude) {
		double distance = GeometryUtils.distance(center.getLongitudeDeg(),
				center.getLatitudeDeg(), longitude, latitude);
		return distance <= radius;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	final boolean intersectsBoundary(final GeoCoordinates gcStart, final GeoCoordinates gcEnd) {
		return false;
	}

}
