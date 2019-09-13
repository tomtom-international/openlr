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
package openlr.location;

import openlr.LocationType;
import openlr.map.GeoCoordinates;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.InvalidMapDataException;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Implementation of the location interface for circle locations.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author DLR e.V. (RE)
 */
public class CircleLocation extends AbstractLocation {

    /** The center point. */
    private final GeoCoordinates center;

    /** The radius in meters. */
    private final long radius;

    /**
     * Instantiates a new circle area location.
     *
     * @param idString the id string
     * @param lonDeg the lon deg
     * @param latDeg the lat deg
     * @param r the r
     * @throws InvalidMapDataException the invalid map data exception
     */
    protected CircleLocation(final String idString, final double lonDeg,
                             final double latDeg, final long r) throws InvalidMapDataException {
        super(idString, LocationType.CIRCLE);
        center = new GeoCoordinatesImpl(lonDeg, latDeg);
        radius = r;
    }

    /********************** COPY CONSTRUCTOR *************************/

    /**
     * Instantiates a copy of location l.
     *
     * @param l
     *            the location be copied
     */
    CircleLocation(final CircleLocation l) {
        super(l);
        center = l.getCenterPoint();
        radius = l.getRadius();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final GeoCoordinates getCenterPoint() {
        return center;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final long getRadius() {
        return radius;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id: ").append(id);
        sb.append(" loc type: ").append(locType);
        if (center != null) {
            sb.append(" center: ").append(center);
        }
        sb.append(" radius: ").append(radius);
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int calculateHashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(locType).append(center).append(radius);
        return builder.toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object obj) {
        if (!(obj instanceof CircleLocation)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        CircleLocation other = (CircleLocation) obj;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(center, other.center).append(locType, other.locType)
                .append(radius, other.radius);
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
