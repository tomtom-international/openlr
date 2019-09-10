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
package openlr.map;

import openlr.map.utils.GeometryUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * This class is an implementation of interface {@link GeoCoordinates}. 
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class GeoCoordinatesImpl implements GeoCoordinates {

    /** The longitude. */
    private final double longitude;

    /** The latitude. */
    private final double latitude;

    /**
     * Instantiates a new geo-coordinate.
     *
     * @param lon the lon
     * @param lat the lat
     * @throws InvalidMapDataException the invalid map data exception
     */
    public GeoCoordinatesImpl(final double lon, final double lat) throws InvalidMapDataException {
        if (!GeometryUtils.checkCoordinateBounds(lon, lat)) {
            throw new InvalidMapDataException("Coordinates out of bounds!");
        }
        longitude = lon;
        latitude = lat;
    }

    /**
     * Instantiates a new geo-coordinate without declaration of a checked
     * exception. If the given coordinates data are invalid an
     * {@link IllegalArgumentException} is thrown instead.
     *
     * @param lon
     *            the longitude
     * @param lat
     *            the latitude
     * @return The created instance of {@link GeoCoordinatesImpl}
     */
    public static final GeoCoordinatesImpl newGeoCoordinatesUnchecked(
            final double lon, final double lat) {

        try {
            return new GeoCoordinatesImpl(lon, lat);
        } catch (InvalidMapDataException e) {
            throw new IllegalArgumentException(
                    "Illegal coordinates provided for instantiation of GeoCoordinateImpl: "
                            + lon + ", " + lat + " (long, lat)");
        }
    }

    /**
     * Gets the latitude deg.
     *
     * @return the latitude deg
     */
    @Override
    public final double getLatitudeDeg() {
        return latitude;
    }

    /**
     * Gets the longitude deg.
     *
     * @return the longitude deg
     */
    @Override
    public final double getLongitudeDeg() {
        return longitude;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("lon: ").append(longitude);
        sb.append(" lat: ").append(latitude);
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(latitude).append(longitude);
        return builder.toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object obj) {
        if (!(obj instanceof GeoCoordinatesImpl)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        GeoCoordinatesImpl other = (GeoCoordinatesImpl) obj;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(latitude, other.latitude).append(longitude, other.longitude);
        return builder.isEquals();
    }
}
