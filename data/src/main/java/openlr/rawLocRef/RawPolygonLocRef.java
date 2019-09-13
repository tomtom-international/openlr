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
package openlr.rawLocRef;

import openlr.LocationType;
import openlr.map.GeoCoordinates;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.Collections;
import java.util.List;

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
public class RawPolygonLocRef extends RawLocationReference {

    /** The corner list. */
    private final List<GeoCoordinates> corners;

    /**
     * Instantiates a new raw encoder location reference for a polygon
     * location.
     *
     * @param idValue
     *            the id
     * @param c
     *            the corner list
     */
    public RawPolygonLocRef(final String idValue, final List<? extends GeoCoordinates> c) {
        super(idValue, LocationType.POLYGON);
        corners = Collections.unmodifiableList(c);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<GeoCoordinates> getCornerPoints() {
        return corners;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id: ").append(id);
        sb.append(" locType: ").append(locType);
        sb.append(" corners: ");
        sb.append(" [");
        for (int i = 0; i < corners.size(); i++) {
            sb.append(corners.get(i));
            if (i != corners.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**{
     * {@inheritDoc}
     */
    @Override
    public final int calculateHashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(corners).append(locType);
        return builder.toHashCode();
    }


    /**{
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object obj) {
        if (!(obj instanceof RawPolygonLocRef)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        RawPolygonLocRef other = (RawPolygonLocRef) obj;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(corners, other.corners).append(locType, other.locType);
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
