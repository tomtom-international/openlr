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
import openlr.map.utils.GeometryUtils;
import openlr.rawLocRef.*;
import openlr.utils.locref.LocationReferenceProcessor;

/**
 * A content provider factory that creates content providers for location
 * reference objects.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
final class BoundingBoxCalculatorFactory extends
        LocationReferenceProcessor<BoundingBoxCalculator> {

    /**
     * {@inheritDoc}
     */
    @Override
    public BoundingBoxCalculator process(
            final RawLineLocRef locRef) {
        return new LrpBbasedBoundigBoxCalculator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BoundingBoxCalculator process(
            final RawGeoCoordLocRef locRef) {

        return new BoundingBoxCalculator() {

            private static final double OFFSET_UPPER_RGHT = 0.00001;

            @Override
            public RectangleCorners calculateBoundary(
                    final RawLocationReference locRef)
                    throws InvalidMapDataException {
                GeoCoordinates coordinate = locRef.getGeoCoordinates();
                GeoCoordinates upperRight = new GeoCoordinatesImpl(
                        coordinate.getLongitudeDeg() + OFFSET_UPPER_RGHT,
                        coordinate.getLatitudeDeg() + OFFSET_UPPER_RGHT);
                return new RectangleCorners(coordinate, upperRight);
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BoundingBoxCalculator process(
            final RawPointAlongLocRef locRef) {
        return new LrpBbasedBoundigBoxCalculator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BoundingBoxCalculator process(
            final RawPoiAccessLocRef locRef) {
        return new LrpBbasedBoundigBoxCalculator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BoundingBoxCalculator process(
            final RawRectangleLocRef locRef) {

        return new BoundingBoxCalculator() {

            @Override
            public RectangleCorners calculateBoundary(
                    final RawLocationReference locRef)
                    throws InvalidMapDataException {

                return new RectangleCorners(locRef.getLowerLeftPoint(),
                        locRef.getUpperRightPoint());
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BoundingBoxCalculator process(
            final RawPolygonLocRef locRef) {

        return new PolygonBoundingBoxCalculator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BoundingBoxCalculator process(
            final RawCircleLocRef locRef) {

        return new CircleBoundingBoxCalculator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BoundingBoxCalculator process(
            final RawGridLocRef locRef) {
        return new BoundingBoxCalculator() {

            @Override
            public RectangleCorners calculateBoundary(
                    final RawLocationReference locRef)
                    throws InvalidMapDataException {

                GeoCoordinates lowerLeft = locRef.getLowerLeftPoint();
                GeoCoordinates upperRight = locRef.getUpperRightPoint();
                GeoCoordinates scaledUpperRight = GeometryUtils
                        .scaleUpperRightCoordinate(lowerLeft.getLongitudeDeg(),
                                lowerLeft.getLatitudeDeg(),
                                upperRight.getLongitudeDeg(),
                                upperRight.getLatitudeDeg(),
                                locRef.getNumberOfColumns(),
                                locRef.getNumberOfRows());

                return new RectangleCorners(lowerLeft, scaledUpperRight);
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BoundingBoxCalculator process(
            final RawClosedLineLocRef locRef) {
        return new LrpBbasedBoundigBoxCalculator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BoundingBoxCalculator processUnknown(
            final RawLocationReference locationReference) {
        throw new IllegalArgumentException("Bounding box calculator called with location reference of type "
                + locationReference.getLocationType());
    }

}
