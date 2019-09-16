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
import openlr.location.data.Orientation;
import openlr.location.data.SideOfRoad;
import openlr.map.InvalidMapDataException;
import openlr.map.Line;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Implementation of the location interface for point along line locations.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class PointAlongLocation extends PointLocation {


    /**
     * Instantiates a new point along line location.
     *
     * @param idString
     *            the id string
     * @param l
     *            the l
     * @param poff
     *            the poff
     * @param s
     *            the s
     * @param o
     *            the o
     * @throws InvalidMapDataException if coordinates are out of range
     */
    protected PointAlongLocation(final String idString, final Line l, final int poff,
                                 final SideOfRoad s, final Orientation o) throws InvalidMapDataException {
        super(idString, LocationType.POINT_ALONG_LINE, l, poff, s, o);
    }

    /**
     * Instantiates a new point along line location.
     *
     * @param idString the id string
     * @param l the l
     * @param poff the poff
     * @param s the s
     * @throws InvalidMapDataException if coordinates are out of range
     */
    PointAlongLocation(final String idString, final Line l, final int poff,
                       final SideOfRoad s) throws InvalidMapDataException {
        this(idString, l, poff, s, Orientation.getDefault());
    }

    /**
     * Instantiates a new point along line location.
     *
     * @param idString the id string
     * @param l the l
     * @param poff the poff
     * @param o the o
     * @throws InvalidMapDataException if coordinates are out of range
     */
    PointAlongLocation(final String idString, final Line l, final int poff,
                       final Orientation o) throws InvalidMapDataException {
        this(idString, l, poff, SideOfRoad.getDefault(), o);
    }

    /**
     * Instantiates a new point along line location.
     *
     * @param idString the id string
     * @param l the l
     * @param poff the poff
     * @throws InvalidMapDataException if coordinates are out of range
     */
    PointAlongLocation(final String idString, final Line l, final int poff) throws InvalidMapDataException {
        this(idString, l, poff, SideOfRoad.getDefault(), Orientation
                .getDefault());
    }


    /**
     * Instantiates a new point along line location at a network node.
     *
     * @param idString
     *            the id string
     * @param l
     *            the l
     * @param s
     *            the s
     * @throws InvalidMapDataException if coordinates are out of range
     */
    PointAlongLocation(final String idString, final Line l, final SideOfRoad s) throws InvalidMapDataException {
        this(idString, l, 0, s, Orientation.getDefault());
    }

    /**
     * Instantiates a new point along line location at a network node.
     *
     * @param idString the id string
     * @param l the l
     * @param o the o
     * @throws InvalidMapDataException if coordinates are out of range
     */
    PointAlongLocation(final String idString, final Line l, final Orientation o) throws InvalidMapDataException {
        this(idString, l, 0, SideOfRoad.getDefault(), o);
    }

    /**
     * Instantiates a new point along line location at a network node.
     *
     * @param idString the id string
     * @param l the l
     * @param s the s
     * @param o the o
     * @throws InvalidMapDataException if coordinates are out of range
     */
    PointAlongLocation(final String idString, final Line l, final SideOfRoad s,
                       final Orientation o) throws InvalidMapDataException {
        this(idString, l, 0, s, o);
    }

    /**
     * Instantiates a new point along line location at a network node.
     *
     * @param idString the id string
     * @param l the l
     * @throws InvalidMapDataException if coordinates are out of range
     */
    PointAlongLocation(final String idString, final Line l) throws InvalidMapDataException {
        this(idString, l, 0);
    }

    /********************** COPY CONSTRUCTOR *************************/

    /**
     * Instantiates a copy of location l.
     *
     * @param l
     *            the location be copied
     */
    public PointAlongLocation(final PointAlongLocation l) {
        super(l);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id: ").append(id);
        sb.append(" loc type: ").append(locType);
        sb.append(" posOff: ").append(posOff);
        if (lineForPoint != null) {
            sb.append(" lineForPoint: ").append(lineForPoint.getID());
        }
        sb.append(" access: ").append(access);
        sb.append(" sideOfRoad: ").append(sideOfRoad);
        sb.append(" orientation: ").append(orientation);
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int calculateHashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(posOff).append(lineForPoint).append(access)
                .append(sideOfRoad).append(orientation).append(locType);
        return builder.toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object obj) {
        if (!(obj instanceof PointAlongLocation)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        PointAlongLocation other = (PointAlongLocation) obj;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(posOff, other.posOff).append(lineForPoint,
                other.lineForPoint).append(access,
                other.access).append(sideOfRoad, other.sideOfRoad).append(
                orientation, other.orientation).append(locType, other.locType);
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
