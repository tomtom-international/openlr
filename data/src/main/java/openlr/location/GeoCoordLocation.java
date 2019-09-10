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
package openlr.location;

import openlr.LocationType;
import openlr.map.GeoCoordinates;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.InvalidMapDataException;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Implementation of the location interface for geo coordinate locations.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class GeoCoordLocation extends AbstractLocation {

    /** The poi. */
    private final GeoCoordinates poi;


    /**
     * Instantiates a new geo coordinate location.
     *
     * @param idString the id string
     * @param lonDeg the lon deg
     * @param latDeg the lat deg
     * @throws InvalidMapDataException the invalid map data exception
     */
    GeoCoordLocation(final String idString, final double lonDeg,
                     final double latDeg) throws InvalidMapDataException {
        super(idString, LocationType.GEO_COORDINATES);
        poi = new GeoCoordinatesImpl(lonDeg, latDeg);
    }

    /**
     * Instantiates a new geo coord location.
     *
     * @param idString the id string
     * @param coord the coord
     */
    GeoCoordLocation(final String idString, final GeoCoordinates coord) {
        super(idString, LocationType.GEO_COORDINATES);
        poi = coord;
    }

    /********************** COPY CONSTRUCTOR *************************/

    /**
     * Instantiates a copy of location l.
     *
     * @param l
     *            the location be copied
     */
    public GeoCoordLocation(final GeoCoordLocation l) {
        super(l);
        poi = l.getPointLocation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final GeoCoordinates getPointLocation() {
        return poi;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id: ").append(id);
        sb.append(" loc type: ").append(locType);
        sb.append(" poi: ").append(poi);
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int calculateHashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(locType).append(poi);
        return builder.toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object obj) {
        if (!(obj instanceof GeoCoordLocation)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        GeoCoordLocation other = (GeoCoordLocation) obj;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(poi, other.poi).append(locType,
                other.locType);
        return builder.isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        return getHashCode();
    }

}

