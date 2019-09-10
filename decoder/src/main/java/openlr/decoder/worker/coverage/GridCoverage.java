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
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author DLR e.V. (RE)
 */
public class GridCoverage extends AbstractCoverage {

    /** The lower left. */
    private final GeoCoordinates lowerLeft;

    /** The upper right. */
    private final GeoCoordinates upperRight;

    /** The scaled upper right. */
    private final GeoCoordinates scaledUpperRight;

    /** The n cols. */
    private final int nCols;

    /** The n rows. */
    private final int nRows;

    /**
     * Instantiates a new grid coverage.
     *
     * @param ll the ll
     * @param ur the ur
     * @param cols the cols
     * @param rows the rows
     * @throws InvalidMapDataException the invalid map data exception
     */
    public GridCoverage(final GeoCoordinates ll, final GeoCoordinates ur,
                        final int cols, final int rows) throws InvalidMapDataException {
        lowerLeft = ll;
        upperRight = ur;
        nCols = cols;
        nRows = rows;
        scaledUpperRight = GeometryUtils.scaleUpperRightCoordinate(
                lowerLeft.getLongitudeDeg(), lowerLeft.getLatitudeDeg(),
                upperRight.getLongitudeDeg(), upperRight.getLatitudeDeg(),
                nCols, nRows);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    final Iterator<? extends Line> determineCoveredLines(final MapDatabase mdb)
            throws OpenLRDecoderProcessingException {
        Iterator<? extends Line> directLinesIterator = null;
        try {
            GeoCoordinates center = new GeoCoordinatesImpl(
                    (lowerLeft.getLongitudeDeg() + scaledUpperRight
                            .getLongitudeDeg()) / 2.,
                    (lowerLeft.getLatitudeDeg() + scaledUpperRight.getLatitudeDeg()) / 2.);
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
    final boolean isContainedIn(final double longitude, final double latitude)
            throws InvalidMapDataException {
        return (longitude >= lowerLeft.getLongitudeDeg()
                && longitude <= scaledUpperRight.getLongitudeDeg()
                && latitude >= lowerLeft.getLatitudeDeg() && latitude <= scaledUpperRight
                .getLatitudeDeg());
    }

    /**
     * {@inheritDoc}
     *
     * @throws InvalidMapDataException
     */
    @Override
    final boolean intersectsBoundary(final GeoCoordinates gcStart,
                                     final GeoCoordinates gcEnd) throws InvalidMapDataException {
        RectangleCorners corners = new RectangleCorners(lowerLeft, scaledUpperRight);
        GeoCoordinates ll = corners.getLowerLeft();
        GeoCoordinates ur = corners.getUpperRight();
        GeoCoordinates ul = corners.getUpperLeft();
        GeoCoordinates lr = corners.getLowerRight();
        if (GeometryUtils.lineIntersection(gcStart, gcEnd, ll, lr)
                || GeometryUtils.lineIntersection(gcStart, gcEnd, lr, ur)
                || GeometryUtils.lineIntersection(gcStart, gcEnd, ur, ul)
                || GeometryUtils.lineIntersection(gcStart, gcEnd, ul, ll)) {
            return true;
        }
        return false;
    }

}
