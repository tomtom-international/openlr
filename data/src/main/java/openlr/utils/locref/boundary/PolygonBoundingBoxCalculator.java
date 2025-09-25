/**
 * Licensed to the TomTom International B.V. under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  TomTom International B.V.
 * licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * <p>
 * Copyright (C) 2009-2019 TomTom International B.V.
 * <p>
 * TomTom (Legal Department)
 * Email: legal@tomtom.com
 * <p>
 * TomTom (Technical contact)
 * Email: openlr@tomtom.com
 * <p>
 * Address: TomTom International B.V., Oosterdoksstraat 114, 1011DK Amsterdam,
 * the Netherlands
 * <p>
 * Copyright (C) 2009-2019 TomTom International B.V.
 * <p>
 * TomTom (Legal Department)
 * Email: legal@tomtom.com
 * <p>
 * TomTom (Technical contact)
 * Email: openlr@tomtom.com
 * <p>
 * Address: TomTom International B.V., Oosterdoksstraat 114, 1011DK Amsterdam,
 * the Netherlands
 */
/**
 *  Copyright (C) 2009-2019 TomTom International B.V.
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
import openlr.rawLocRef.RawLocationReference;

/**
 * Calculates bounding boxes on polygon location references.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
final class PolygonBoundingBoxCalculator implements BoundingBoxCalculator {

    /**
     * {@inheritDoc}
     */
    @Override
    public RectangleCorners calculateBoundary(final RawLocationReference locRef)
            throws InvalidMapDataException {

        double minLong = Double.MAX_VALUE;
        double minLat = Double.MAX_VALUE;
        double maxLong = -Double.MAX_VALUE;
        double maxLat = -Double.MAX_VALUE;

        for (GeoCoordinates coord : locRef.getCornerPoints()) {
            if (coord.getLongitudeDeg() < minLong) {
                minLong = coord.getLongitudeDeg();
            }
            if (coord.getLongitudeDeg() > maxLong) {
                maxLong = coord.getLongitudeDeg();
            }
            if (coord.getLatitudeDeg() < minLat) {
                minLat = coord.getLatitudeDeg();
            }
            if (coord.getLatitudeDeg() > maxLat) {
                maxLat = coord.getLatitudeDeg();
            }
        }

        GeoCoordinates lowerLeft = new GeoCoordinatesImpl(minLong, minLat);
        GeoCoordinates upperRight = new GeoCoordinatesImpl(maxLong, maxLat);
        return new RectangleCorners(lowerLeft, upperRight);
    }
}
