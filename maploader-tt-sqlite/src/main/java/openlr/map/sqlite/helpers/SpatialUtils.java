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
package openlr.map.sqlite.helpers;

import openlr.map.GeoCoordinates;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.InvalidMapDataException;
import openlr.map.RectangleCorners;
import openlr.map.sqlite.helpers.wkb.WKBException;
import openlr.map.sqlite.helpers.wkb.WKBReader;
import openlr.map.utils.GeometryUtils;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static openlr.map.sqlite.impl.Configuration.*;

/**
 * Offers various thread safe utility functions for use with spatial data.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public final class SpatialUtils {

    /** The Constant QUARTER_CIRCLE. */
    private static final int QUARTER_CIRCLE = 90;

    /** The Constant HALF_CIRCLE. */
    private static final int HALF_CIRCLE = 180;

    /** The Constant THREE_QUARTER_CIRCLE. */
    private static final int THREE_QUARTER_CIRCLE = 270;

    /** The Constant METER_PER_KM. */
    private static final float METER_PER_KM = 1000.0f;

    /**
     * Hides the constructor of this utility class.
     */
    private SpatialUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Creates a simple thread safe LRU cache with default settings defined in
     * {@link openlr.map.sqlite.impl.Configuration}.
     *
     * @param <E>
     *            the data type of the cached features.
     * @return a {@link java.util.LinkedHashMap} based LRU cache.
     */
    public static <E> Map<Long, E> createLRUCache() {
        return Collections.synchronizedMap(new LinkedHashMap<Long, E>(
                CACHE_INITIAL_SIZE, CACHE_LOAD_FACTOR, true) {
            private static final long serialVersionUID = 1L;

            @Override
            protected boolean removeEldestEntry(final Map.Entry<Long, E> eldest) {
                return size() > CACHE_MAX_SIZE;
            }
        });
    }

    /**
     * Checks whether a given WGS84 coordinate is within the allowed bounds.
     *
     * @param longitude
     *            the longitude value of the coordinate.
     * @param latitude
     *            the latitude value of the coordinate.
     * @return true if the coordinate is within the allowed bounds.
     */
    public static boolean isCoordinateValid(final double longitude,
                                            final double latitude) {
        return longitude >= -HALF_CIRCLE && longitude <= HALF_CIRCLE
                && latitude >= -QUARTER_CIRCLE && latitude <= QUARTER_CIRCLE;
    }

    /**
     * Calc bounding box.
     *
     * @param longitude
     *            the longitude
     * @param latitude
     *            the latitude
     * @param distance
     *            the distance
     * @return the rectangle corners
     * @throws InvalidMapDataException
     *             the invalid map data exception
     */
    public static RectangleCorners calcBoundingBox(final double longitude,
                                                   final double latitude, final int distance)
            throws InvalidMapDataException {
        double distKM = (double) distance / METER_PER_KM;
        GeoCoordinates top = GeometryUtils.determineCoordinateInDistance(
                longitude, latitude, 0, distKM);
        GeoCoordinates right = GeometryUtils.determineCoordinateInDistance(
                longitude, latitude, QUARTER_CIRCLE, distKM);
        GeoCoordinates bottom = GeometryUtils.determineCoordinateInDistance(
                longitude, latitude, HALF_CIRCLE, distKM);
        GeoCoordinates left = GeometryUtils.determineCoordinateInDistance(
                longitude, latitude, THREE_QUARTER_CIRCLE, distKM);
        GeoCoordinates ll = new GeoCoordinatesImpl(left.getLongitudeDeg(),
                bottom.getLatitudeDeg());
        GeoCoordinates ur = new GeoCoordinatesImpl(right.getLongitudeDeg(),
                top.getLatitudeDeg());
        return new RectangleCorners(ll, ur);
    }

    /**
     * Shape from wkb.
     *
     * @param bytes
     *            the bytes
     * @return the path2 d. double
     */
    public static List<GeoCoordinates> shapeFromWKB(final byte[] bytes) {
        try {
            return WKBReader.readShape(bytes);
        } catch (WKBException e) {
            throw new IllegalStateException(e);
        }
    }
}
