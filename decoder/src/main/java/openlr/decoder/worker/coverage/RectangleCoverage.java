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
package openlr.decoder.worker.coverage;

import openlr.decoder.OpenLRDecoderProcessingException;
import openlr.decoder.OpenLRDecoderProcessingException.DecoderProcessingError;
import openlr.map.*;
import openlr.map.utils.GeometryUtils;

import java.util.Iterator;

/**
 * The Class RectangleCoverage.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author DLR e.V. (RE)
 */
public class RectangleCoverage extends AbstractCoverage {

    /** The corners. */
    private final RectangleCorners corners;

    /**
     * Instantiates a new rectangle coverage.
     *
     * @param ll the ll
     * @param ur the ur
     * @throws InvalidMapDataException the invalid map data exception
     */
    public RectangleCoverage(final GeoCoordinates ll, final GeoCoordinates ur)
            throws InvalidMapDataException {
        corners = new RectangleCorners(ll, ur);
    }

    /**
     * {@inheritDoc}
     *
     * @throws OpenLRDecoderProcessingException
     */
    @Override
    public final Iterator<? extends Line> determineCoveredLines(final MapDatabase mdb)
            throws OpenLRDecoderProcessingException {
        Iterator<? extends Line> directLinesIterator = null;
        GeoCoordinates lowerLeft = corners.getLowerLeft();
        GeoCoordinates upperRight = corners.getUpperRight();
        try {
            GeoCoordinates center = new GeoCoordinatesImpl(
                    (lowerLeft.getLongitudeDeg() + upperRight.getLongitudeDeg()) / 2.,
                    (lowerLeft.getLatitudeDeg() + upperRight.getLatitudeDeg()) / 2.);
            int radius = (int) GeometryUtils.distance(center.getLongitudeDeg(),
                    center.getLatitudeDeg(), lowerLeft.getLongitudeDeg(),
                    lowerLeft.getLatitudeDeg());

            directLinesIterator = mdb.findLinesCloseByCoordinate(
                    center.getLongitudeDeg(), center.getLatitudeDeg(),
                    ((int) radius + MINIMUM_ADDITIONAL_DISTANCE));
        } catch (InvalidMapDataException e) {
            throw new OpenLRDecoderProcessingException(
                    DecoderProcessingError.INVALID_MAP_DATA, e);
        }
        return directLinesIterator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    final boolean isContainedIn(final double longitude, final double latitude) {
        GeoCoordinates lowerLeft = corners.getLowerLeft();
        GeoCoordinates upperRight = corners.getUpperRight();
        return (longitude >= lowerLeft.getLongitudeDeg()
                && longitude <= upperRight.getLongitudeDeg()
                && latitude >= lowerLeft.getLatitudeDeg() && latitude <= upperRight
                .getLatitudeDeg());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    final boolean intersectsBoundary(final GeoCoordinates gcStart,
                                     final GeoCoordinates gcEnd) {
        GeoCoordinates lowerLeft = corners.getLowerLeft();
        GeoCoordinates upperRight = corners.getUpperRight();
        GeoCoordinates upperLeft = corners.getUpperLeft();
        GeoCoordinates lowerRight = corners.getLowerRight();
        if (GeometryUtils.lineIntersection(gcStart, gcEnd, lowerLeft,
                lowerRight)
                || GeometryUtils.lineIntersection(gcStart, gcEnd, lowerRight,
                upperRight)
                || GeometryUtils.lineIntersection(gcStart, gcEnd, upperRight,
                upperLeft)
                || GeometryUtils.lineIntersection(gcStart, gcEnd, upperLeft,
                lowerLeft)) {
            return true;
        }
        return false;
    }

}
