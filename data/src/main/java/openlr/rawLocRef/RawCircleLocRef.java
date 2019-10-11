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
package openlr.rawLocRef;

import openlr.LocationType;
import openlr.map.GeoCoordinates;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Implementation of the RawLocationReference interface for circle locations.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author DLR e.V. (RE).
 */
public class RawCircleLocRef extends RawLocationReference {

    /** The center point. */
    private final GeoCoordinates center;

    /** The radius. */
    private final long radius;

    /**
     * Instantiates a new raw encoder location reference for a circle location.
     *
     * @param idValue the id
     * @param c the c
     * @param r the r
     */
    public RawCircleLocRef(final String idValue, final GeoCoordinates c,
                           final long r) {
        super(idValue, LocationType.CIRCLE);
        center = c;
        radius = r;
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
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id: ").append(id);
        sb.append(" locType: ").append(locType);
        if (center != null) {
            sb.append(" center: ");
            sb.append(center);
        }
        sb.append(" radius: ").append(radius);
        return sb.toString();
    }

    /**{
     * {@inheritDoc}
     */
    @Override
    public final int calculateHashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(center).append(locType).append(radius);
        return builder.toHashCode();
    }


    /**{
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object obj) {
        if (!(obj instanceof RawCircleLocRef)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        RawCircleLocRef other = (RawCircleLocRef) obj;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(center, other.center).append(locType, other.locType).append(radius, other.radius);
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
