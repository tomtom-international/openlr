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
package openlr.utils.locref.boundary;

import openlr.map.GeoCoordinates;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.InvalidMapDataException;
import openlr.map.RectangleCorners;
import openlr.map.utils.GeometryUtils;
import openlr.rawLocRef.RawLocationReference;

/** 
 * Calculates a bounding box for circle location references.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
final class CircleBoundingBoxCalculator implements
        BoundingBoxCalculator {

    /**
     * Meters per kilometer as double value
     */
    private static final double METERS_PER_KILOMETER = 1000.;

    @Override
    public RectangleCorners calculateBoundary(
            final RawLocationReference locRef)
            throws InvalidMapDataException {

        GeoCoordinates center = locRef.getCenterPoint();
        double lon = center.getLongitudeDeg();
        double lat = center.getLatitudeDeg();
        double radiusInKilometers = locRef.getRadius() / METERS_PER_KILOMETER;

        // we determine lower left and upper right by the expansion of the circle
        double longLeft = GeometryUtils.determineCoordinateInDistance(
                lon, lat, (int) GeometryUtils.THREE_QUARTER_CIRCLE, radiusInKilometers).getLongitudeDeg();
        double longRight = GeometryUtils.determineCoordinateInDistance(
                lon, lat, (int) GeometryUtils.QUARTER_CIRCLE, radiusInKilometers).getLongitudeDeg();

        double latLeft = GeometryUtils.determineCoordinateInDistance(
                lon, lat, (int) GeometryUtils.HALF_CIRCLE, radiusInKilometers).getLatitudeDeg();
        double latRight = GeometryUtils.determineCoordinateInDistance(
                lon, lat, 0, radiusInKilometers).getLatitudeDeg();

        return new RectangleCorners(new GeoCoordinatesImpl(longLeft,
                latLeft), new GeoCoordinatesImpl(longRight, latRight));
    }
}
