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
package openlr.encoder.locationCheck.worker;

import openlr.OpenLRProcessingException;
import openlr.encoder.EncoderReturnCode;
import openlr.encoder.locationCheck.CheckResult;
import openlr.encoder.locationCheck.LocationCheck;
import openlr.encoder.properties.OpenLREncoderProperties;
import openlr.location.Location;
import openlr.map.GeoCoordinates;
import openlr.map.MapDatabase;
import openlr.map.utils.GeometryUtils;
import org.apache.log4j.Logger;

import java.awt.geom.Line2D;
import java.util.List;

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
public class PolygonLocationCheck extends LocationCheck {

    /** The Constant LOG. */
    private static final Logger LOG = Logger.getLogger(PolygonLocationCheck.class);

    /** The Constant MIN_NR_COORDINATES. */
    private static final int MIN_NR_COORDINATES = 3;

    /**
     * Checks if is intersection free.
     *
     * @param points the points
     * @return true, if is intersection free
     */
    private static boolean isIntersectionFree(final List<? extends GeoCoordinates> points) {
        int nrCoord = points.size();
        for (int i = 0; i < nrCoord - 2; i++) {
            GeoCoordinates lineStart = points.get(i);
            GeoCoordinates lineEnd = points.get(i + 1);
            for (int j = i + 1; j < nrCoord - 1; j++) {
                GeoCoordinates nextLineStart = points.get(j);
                GeoCoordinates nextLineEnd = points.get(j + 1);
                if (j == i + 1) {
                    Line2D.Double line1 = new Line2D.Double(lineStart.getLongitudeDeg(),
                            lineStart.getLatitudeDeg(), lineEnd.getLongitudeDeg(),
                            lineEnd.getLatitudeDeg());
                    if (line1.contains(nextLineEnd.getLongitudeDeg(), nextLineEnd.getLatitudeDeg())) {
                        return false;
                    }
                } else if (GeometryUtils.lineIntersection(lineStart, lineEnd, nextLineStart, nextLineEnd)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final CheckResult check(final OpenLREncoderProperties properties,
                                   final MapDatabase mapDB, final Location location) {
        List<? extends GeoCoordinates> points = location.getCornerPoints();
        if (points == null) {
            LOG.error("corner points are missing");
            return new CheckResult(EncoderReturnCode.MISSING_CORNERS);
        }
        if (points.size() < MIN_NR_COORDINATES) {
            LOG.error("not enough corner points");
            return new CheckResult(EncoderReturnCode.MISSING_CORNERS);
        }
        for (GeoCoordinates corner : points) {
            if (!checkCoordinateBounds(corner.getLongitudeDeg(),
                    corner.getLatitudeDeg())) {
                LOG.error("invalid coordinates");
                return new CheckResult(
                        EncoderReturnCode.COORDINATES_OUT_OF_BOUNDS);
            }
        }
        if (!isIntersectionFree(points)) {
            LOG.error("the polygon is not simple");
            return new CheckResult(
                    EncoderReturnCode.POLYGON_NOT_SIMPLE);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("location check passed");
        }
        return CheckResult.PASSED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final CheckResult checkOffsets(final OpenLREncoderProperties properties,
                                          final Location location) throws OpenLRProcessingException {
        return CheckResult.PASSED;
    }

}
