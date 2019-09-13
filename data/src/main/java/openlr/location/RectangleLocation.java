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
import openlr.map.RectangleCorners;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.List;

/**
 * Implementation of the location interface for rectangle locations.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author DLR e.V. (RE)
 */
public class RectangleLocation extends AbstractLocation {

    /** The list of corner positions. */
    private final RectangleCorners corners;


    /**
     * Instantiates a new rectangle area location.
     *
     * @param idString the id string
     * @param lowerLeftLonDeg the leftmost lon deg
     * @param lowerLeftLatDeg the leftmost lat deg
     * @param upperRightLonDeg the rightmost lon deg
     * @param upperRightLatDeg the rightmost lat deg
     * @throws InvalidMapDataException the invalid map data exception
     */
    public RectangleLocation(final String idString,
                             final double lowerLeftLonDeg, final double lowerLeftLatDeg,
                             final double upperRightLonDeg, final double upperRightLatDeg) throws InvalidMapDataException {
        super(idString, LocationType.RECTANGLE);
        GeoCoordinates lowerLeft = new GeoCoordinatesImpl(lowerLeftLonDeg,
                lowerLeftLatDeg);
        GeoCoordinates upperRight = new GeoCoordinatesImpl(upperRightLonDeg,
                upperRightLatDeg);
        corners = new RectangleCorners(lowerLeft, upperRight);
    }

    /********************** COPY CONSTRUCTOR *************************/

    /**
     * Instantiates a copy of location l.
     *
     * @param l
     *            the location be copied
     */
    public RectangleLocation(final RectangleLocation l) {
        super(l);
        corners = l.corners;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<GeoCoordinates> getCornerPoints() {
        return corners.getCornerPoints();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final GeoCoordinates getLowerLeftPoint() {
        return corners.getLowerLeft();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final GeoCoordinates getUpperRightPoint() {
        return corners.getUpperRight();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id: ").append(id);
        sb.append(" loc type: ").append(locType);
        sb.append(" corners: ").append(corners);
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int calculateHashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(locType).append(corners);
        return builder.toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object obj) {
        if (!(obj instanceof RectangleLocation)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        RectangleLocation other = (RectangleLocation) obj;
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
