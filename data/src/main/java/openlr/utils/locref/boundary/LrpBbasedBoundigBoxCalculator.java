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
 * Copyright (C) 2009-2012 TomTom International B.V.
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

import openlr.LocationReferencePoint;
import openlr.map.GeoCoordinates;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.InvalidMapDataException;
import openlr.map.RectangleCorners;
import openlr.map.utils.GeometryUtils;
import openlr.rawLocRef.RawLocationReference;

/**
 * Calculates the bounding box from the location reference points.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
class LrpBbasedBoundigBoxCalculator implements BoundingBoxCalculator {

    /** The Constant ADDITIONAL_VARIANCE_KM. */
    private static final float ADDITIONAL_VARIANCE_KM = 0.060f;

    /** The Constant FACTOR_KM. */
    private static final float FACTOR_KM = 2 * 1000.0f;


    /**
     * Calculates the bounding box of the location reference points. This box
     * max include the location but it may happen that the location path is also
     * outside the box. Returns {@code null} if the given location reference does not
     * contain location reference points. 
     *
     * @param rawLocRef
     *            the raw loc ref
     * @return the rectangle corners
     * @throws InvalidMapDataException
     *             the invalid map data exception
     */
    public RectangleCorners calculateLocRefPointBoundary(
            final RawLocationReference rawLocRef)
            throws InvalidMapDataException {
        Envelope envelope = new Envelope();
        if (rawLocRef.getLocationReferencePoints() == null) {
            return null;
        }
        for (LocationReferencePoint lrp : rawLocRef
                .getLocationReferencePoints()) {
            envelope.include(lrp.getLongitudeDeg(), lrp.getLatitudeDeg());
        }
        GeoCoordinates ll = new GeoCoordinatesImpl(envelope.getMinX(),
                envelope.getMinY());
        GeoCoordinates ur = new GeoCoordinatesImpl(envelope.getMaxX(),
                envelope.getMaxY());
        return new RectangleCorners(ll, ur);
    }

    /**
     * Calculates the bounding box of a location reference. This box includes
     * the location reference points and the location path. The box may not be
     * the smallest box enclosing the location path.
     *
     * @param rawLocRef
     *            the raw loc ref
     * @return the rectangle corners
     * @throws InvalidMapDataException
     *             the invalid map data exception
     */
    private RectangleCorners calculateLocRefBoundary(
            final RawLocationReference rawLocRef)
            throws InvalidMapDataException {
        RectangleCorners rcMin = calculateLocRefPointBoundary(rawLocRef);
        if (rcMin != null) {
            double maxDist = calculateMaxDist(rawLocRef);
            return createMaximumRectangle(rcMin, maxDist);
        } else {
            return null;
        }
    }

    /**
     * Calculate max dist.
     *
     * @param lr
     *            the lr
     * @return the double
     */
    private double calculateMaxDist(final RawLocationReference lr) {
        double maxDist = 0;
        if (lr.getLocationReferencePoints() != null) {
            for (int i = 0; i < lr.getLocationReferencePoints().size() - 1; i++) {
                LocationReferencePoint lrp = lr.getLocationReferencePoints()
                        .get(i);
                int dnp = lrp.getDistanceToNext();
                LocationReferencePoint next = lr.getLocationReferencePoints()
                        .get(i + 1);
                double dist = GeometryUtils.distance(lrp.getLongitudeDeg(),
                        lrp.getLatitudeDeg(), next.getLongitudeDeg(),
                        next.getLatitudeDeg());
                double boundingBoxDist = ((dnp - dist) / FACTOR_KM)
                        + ADDITIONAL_VARIANCE_KM;
                if (boundingBoxDist > maxDist) {
                    maxDist = boundingBoxDist;
                }
            }
        }
        return maxDist;
    }

    /**
     * Creates the maximum rectangle.
     *
     * @param min
     *            the min
     * @param distance
     *            the distance in kilometers 
     * @return the rectangle corners
     * @throws InvalidMapDataException
     *             the invalid map data exception
     */
    private RectangleCorners createMaximumRectangle(
            final RectangleCorners min, final double distance)
            throws InvalidMapDataException {
        GeoCoordinates temp1 = GeometryUtils.determineCoordinateInDistance(min
                .getLowerLeft().getLongitudeDeg(), min.getLowerLeft()
                .getLatitudeDeg(), (int) GeometryUtils.HALF_CIRCLE, distance);
        GeoCoordinates temp2 = GeometryUtils.determineCoordinateInDistance(min
                        .getLowerLeft().getLongitudeDeg(), min.getLowerLeft()
                        .getLatitudeDeg(), (int) GeometryUtils.THREE_QUARTER_CIRCLE,
                distance);
        GeoCoordinates newLL = new GeoCoordinatesImpl(temp2.getLongitudeDeg(),
                temp1.getLatitudeDeg());
        GeoCoordinates temp3 = GeometryUtils.determineCoordinateInDistance(min
                .getUpperRight().getLongitudeDeg(), min.getUpperRight()
                .getLatitudeDeg(), (int) GeometryUtils.ZERO_CIRCLE, distance);
        GeoCoordinates temp4 = GeometryUtils
                .determineCoordinateInDistance(min.getUpperRight()
                                .getLongitudeDeg(), min.getUpperRight()
                                .getLatitudeDeg(), (int) GeometryUtils.QUARTER_CIRCLE,
                        distance);
        GeoCoordinates newUR = new GeoCoordinatesImpl(temp4.getLongitudeDeg(),
                temp3.getLatitudeDeg());
        return new RectangleCorners(newLL, newUR);
    }


    /* (non-Javadoc)
     * @see openlr.otk.locRefBoundary.BoundingBoxCalculator#calculateBoundary(openlr.rawLocRef.RawLocationReference)
     */
    @Override
    public final RectangleCorners calculateBoundary(final RawLocationReference locRef) throws InvalidMapDataException {
        return calculateLocRefBoundary(locRef);
    }

}
