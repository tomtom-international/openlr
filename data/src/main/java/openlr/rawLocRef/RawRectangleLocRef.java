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
 * Implementation of the RawLocationReference interface for rectangle locations.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author DLR e.V. (RE)
 */
public class RawRectangleLocRef extends RawLocationReference {

    /** The leftmost point. */
    private final GeoCoordinates lowerLeft;

    /** The rightmost point. */
    private final GeoCoordinates upperRight;

    /**
     * Instantiates a new raw encoder location reference for a rectangle
     * location.
     *
     * @param idValue
     *            the id
     * @param ll
     *            the ll
     * @param ur
     *            the ur
     */
    public RawRectangleLocRef(final String idValue, final GeoCoordinates ll,
                              final GeoCoordinates ur) {
        super(idValue, LocationType.RECTANGLE);
        lowerLeft = ll;
        upperRight = ur;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final GeoCoordinates getLowerLeftPoint() {
        return lowerLeft;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final GeoCoordinates getUpperRightPoint() {
        return upperRight;
    }

    /**
     * {@inheritDoc}
     */
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id: ").append(id);
        sb.append(" locType: ").append(locType);
        if (lowerLeft != null) {
            sb.append(" leftmost: ");
            sb.append(lowerLeft);
        }
        if (upperRight != null) {
            sb.append(" rightmost: ");
            sb.append(upperRight);
        }
        return sb.toString();
    }

    /**
     * { {@inheritDoc}
     */
    @Override
    public final int calculateHashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(lowerLeft).append(locType).append(upperRight);
        return builder.toHashCode();
    }

    /**
     * { {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object obj) {
        if (!(obj instanceof RawRectangleLocRef)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        RawRectangleLocRef other = (RawRectangleLocRef) obj;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(lowerLeft, other.lowerLeft)
                .append(locType, other.locType)
                .append(upperRight, other.upperRight);
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
