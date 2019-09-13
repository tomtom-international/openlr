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
package openlr.decoder.worker.coverage;

import openlr.map.GeoCoordinates;
import openlr.map.InvalidMapDataException;
import openlr.map.Line;
import openlr.map.MapDatabase;
import openlr.map.utils.GeometryUtils;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.List;

/**
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author DLR e.V. (RE)
 */
public class PolygonCoverage extends AbstractCoverage {

    /** The Constant MAX_POINT_LINE_DIST. */
    private static final double MAX_POINT_LINE_DIST = 0.0000001;


    /** The polygon. */
    private final Polygon polygon;

    /** The corners. */
    private final List<? extends GeoCoordinates> corners;

    /**
     * Instantiates a new polygon coverage.
     *
     * @param c the c
     */
    public PolygonCoverage(final List<? extends GeoCoordinates> c) {
        polygon = createPolygon(c);
        corners = c;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    final Iterator<? extends Line> determineCoveredLines(final MapDatabase mdb) {
        Rectangle2D bounds = polygon.getBounds2D();
        double centerLongitude = bounds.getCenterX() / COORD_TO_INT_FACTOR;
        double centerLatitude = bounds.getCenterY() / COORD_TO_INT_FACTOR;
        double topleftLongitude = bounds.getX() / COORD_TO_INT_FACTOR;
        double topleftLatitude = bounds.getY() / COORD_TO_INT_FACTOR;
        int radius = (int) GeometryUtils.distance(centerLongitude,
                centerLatitude, topleftLongitude, topleftLatitude);
        return mdb.findLinesCloseByCoordinate(
                centerLongitude, centerLatitude, (radius
                        + MINIMUM_ADDITIONAL_DISTANCE));
    }

    /**
     * {@inheritDoc}
     * @throws InvalidMapDataException
     */
    @Override
    final boolean isContainedIn(final double longitude, final double latitude) throws InvalidMapDataException {
        if (isOnBoundary(longitude, latitude)) {
            return true;
        }
        double x = longitude * COORD_TO_INT_FACTOR;
        int intx = (int) x;
        double y = latitude * COORD_TO_INT_FACTOR;
        int inty = (int) y;
        return polygon.contains(new Point(intx, inty));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    final boolean intersectsBoundary(final GeoCoordinates gcStart, final GeoCoordinates gcEnd) {
        for (int i = 0; i < corners.size() - 1; i++) {
            GeoCoordinates start = corners.get(i);
            GeoCoordinates end = corners.get(i + 1);
            if (GeometryUtils.lineIntersection(gcStart, gcEnd, start, end)) {
                return true;
            }
        }
        if (GeometryUtils.lineIntersection(gcStart, gcEnd, corners.get(corners.size() - 1), corners.get(0))) {
            return true;
        }
        return false;
    }

    /**
     * Checks if is on boundary.
     *
     * @param longitude the longitude
     * @param latitude the latitude
     * @return true, if is on boundary
     */
    private boolean isOnBoundary(final double longitude, final double latitude) {
        for (int i = 0; i < corners.size() - 1; i++) {
            GeoCoordinates start = corners.get(i);
            GeoCoordinates end = corners.get(i + 1);
            Line2D.Double line1 = new Line2D.Double(start.getLongitudeDeg(),
                    start.getLatitudeDeg(), end.getLongitudeDeg(),
                    end.getLatitudeDeg());
            if (line1.ptSegDist(longitude, latitude) < MAX_POINT_LINE_DIST) {
                return true;
            }
        }
        GeoCoordinates start = corners.get(corners.size() - 1);
        GeoCoordinates end = corners.get(0);
        Line2D.Double line1 = new Line2D.Double(start.getLongitudeDeg(),
                start.getLatitudeDeg(), end.getLongitudeDeg(),
                end.getLatitudeDeg());
        if (line1.ptSegDist(longitude, latitude) < MAX_POINT_LINE_DIST) {
            return true;
        }
        return false;
    }

}
