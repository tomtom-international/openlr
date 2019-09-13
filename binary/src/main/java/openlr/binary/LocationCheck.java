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
package openlr.binary;

import openlr.map.GeoCoordinates;
import openlr.map.Line;

import java.util.List;

/**
 * The class LocationCheck checks the maximum allowed distance value for a location
 * path. The binary package is currently not able to deal with locations having a node
 * with a latitude value larger than 65.69° (lower than -65.69°). In such cases a reduced
 * maximum distance between two subsequent location reference points must be used.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public final class LocationCheck {

    /** The Constant METER_PER_KM. */
    private static final int METER_PER_KM = 1000;

    /** The Constant LAT_LON_BORDER. */
    private static final double LAT_LON_BORDER = 65.69;

    /** The Constant EARTH. */
    private static final int EARTH = 6371;

    /** The Constant MAX_LON_DIFF. */
    private static final double MAX_LON_DIFF = 0.999983647;

    /** The Constant DEFAULT_DISTANCE. */
    private static final int DEFAULT_DISTANCE = 15000;

    /** The Constant RADIANS_FACTOR. */
    private static final double RADIANS_FACTOR = 0.0174532925;

    /**
     * Utility class shall not be instantiated.
     */
    private LocationCheck() {
        throw new UnsupportedOperationException();
    }

    /**
     * Calculates the maximum distance depending on the max latitude value along the path.
     *
     * @param lines the lines
     * @return the maximum distance
     */
    public static int calculateMaxDistanceFromPath(final List<? extends Line> lines) {
        int maxD = DEFAULT_DISTANCE;
        double maxLat = resolveMaximumLatitudeFromPath(lines);
        if (maxLat > LAT_LON_BORDER) {
            maxD = calculateDist(maxLat);
        }
        return maxD;
    }

    /**
     * Calculates the maximum distance depending on the max latitude value of the coordinates.
     *
     * @param coords the coordinates
     * @return the maximum distance
     */
    public static int calculateMaxDistanceFromCoordinates(final List<? extends GeoCoordinates> coords) {
        int maxD = DEFAULT_DISTANCE;
        double maxLat = resolveMaximumLatitudeFromCoordinates(coords);
        if (maxLat > LAT_LON_BORDER) {
            maxD = calculateDist(maxLat);
        }
        return maxD;
    }

    /**
     * Calculate dist.
     *
     * @param maxLat the max lat
     * @return the int
     */
    private static int calculateDist(final double maxLat) {
        double mLat = maxLat * RADIANS_FACTOR;
        double a = Math.sin(mLat);
        double b = Math.cos(mLat);
        double c = a * a + b * b * MAX_LON_DIFF;
        double d = Math.acos(c);
        return (int) Math.round(d * EARTH * METER_PER_KM);
    }

    /**
     * Resolve maximum latitude.
     *
     * @param lines the lines
     * @return the double
     */
    private static double resolveMaximumLatitudeFromPath(final List<? extends Line> lines) {
        double maxLat = 0;
        for (Line l : lines) {
            double currLat = Math.abs(l.getStartNode().getLatitudeDeg());
            if (currLat > maxLat) {
                maxLat = currLat;
            }
        }
        double lastLat = Math.abs(lines.get(lines.size() - 1).getEndNode().getLatitudeDeg());
        if (lastLat > maxLat) {
            maxLat = lastLat;
        }
        return maxLat;
    }

    /**
     * Resolve maximum latitude.
     *
     * @param coords the coords
     * @return the double
     */
    private static double resolveMaximumLatitudeFromCoordinates(final List<? extends GeoCoordinates> coords) {
        double maxLat = 0;
        for (GeoCoordinates c : coords) {
            double currLat = c.getLatitudeDeg();
            if (currLat > maxLat) {
                maxLat = currLat;
            }
        }
        return maxLat;
    }

}
