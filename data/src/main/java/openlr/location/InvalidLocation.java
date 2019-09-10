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
import openlr.StatusCode;
import openlr.location.data.Orientation;
import openlr.location.data.SideOfRoad;
import openlr.map.GeoCoordinates;
import openlr.map.Line;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.Collections;
import java.util.List;

/**
 * Implementation of the location interface for invalid locations.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class InvalidLocation extends openlr.location.AbstractLocation {

    /** The location as an ordered list of lines (from start to end). */
    private final List<Line> lines;

    /** The positive offset. */
    private final int positiveOffset;

    /** The negative offset. */
    private final int negativeOffset;

    /** The geo coord. */
    private final GeoCoordinates geoCoord;

    /** The access point. */
    private final GeoCoordinates accessPoint;

    /** The side of road. */
    private final SideOfRoad sideOfRoad;

    /** The orientation. */
    private final Orientation orientation;

    /** The list of sub route being found by the decoder. */
    private final List<List<Line>> subRouteList;

    /**
     * Instantiates a new decoded location.
     *
     * @param idValue
     *            the id value
     * @param locTypeValue
     *            the loc type value
     * @param err
     *            the err
     * @param loc
     *            the loc
     * @param subRoutes
     *            the sub routes
     * @param pOff
     *            the off
     * @param nOff
     *            the n off
     * @param gC
     *            the g c
     * @param aP
     *            the a p
     * @param sor
     *            the sor
     * @param ori
     *            the ori
     */
    public InvalidLocation(final String idValue,
                           final LocationType locTypeValue, final StatusCode err,
                           final List<Line> loc, final List<List<Line>> subRoutes,
                           final int pOff, final int nOff, final GeoCoordinates gC,
                           final GeoCoordinates aP, final SideOfRoad sor, final Orientation ori) {
        super(idValue, locTypeValue, err);
        if (loc != null) {
            lines = Collections.unmodifiableList(loc);
        } else {
            lines = Collections.emptyList();
        }
        subRouteList = subRoutes;
        positiveOffset = pOff;
        negativeOffset = nOff;
        geoCoord = gC;
        accessPoint = aP;
        sideOfRoad = sor;
        orientation = ori;
    }

    /**
     * Instantiates a new invalid location.
     *
     * @param idValue
     *            the id value
     * @param err
     *            the err
     * @param locTypeValue
     *            the loc type value
     */
    public InvalidLocation(final String idValue, final StatusCode err,
                           final LocationType locTypeValue) {
        this(idValue, locTypeValue, err, null, null, -1, -1, null, null, null,
                null);
    }

    /**
     * Instantiates a new invalid location.
     *
     * @param idValue
     *            the id value
     * @param err
     *            the err
     * @param locTypeValue
     *            the loc type value
     * @param subRoute
     *            the sub route
     */
    public InvalidLocation(final String idValue, final StatusCode err,
                           final LocationType locTypeValue, final List<List<Line>> subRoute) {
        this(idValue, locTypeValue, err, null, subRoute, -1, -1, null, null,
                null, null);
    }

    /**
     * Instantiates a new invalid location.
     *
     * @param id
     *            the id
     * @param locTypeValue
     *            the loc type value
     * @param retCode
     *            the ret code
     * @param geoCoordinates
     *            the geo coordinates
     * @param sor
     *            the sor
     * @param or
     *            the or
     */
    public InvalidLocation(final String id, final LocationType locTypeValue,
                           final StatusCode retCode,
                           final GeoCoordinates geoCoordinates, final SideOfRoad sor,
                           final Orientation or) {
        this(id, locTypeValue, retCode, null, null, -1, -1, geoCoordinates,
                null, sor, or);
    }

    /**
     * Instantiates a new invalid location.
     *
     * @param id
     *            the id
     * @param locTypeValue
     *            the loc type value
     * @param retCode
     *            the ret code
     * @param subRoutes
     *            the sub routes
     * @param gC
     *            the geo coord
     * @param sor
     *            the sor
     * @param or
     *            the or
     */
    public InvalidLocation(final String id, final LocationType locTypeValue,
                           final StatusCode retCode, final List<List<Line>> subRoutes,
                           final GeoCoordinates gC, final SideOfRoad sor, final Orientation or) {
        this(id, locTypeValue, retCode, null, subRoutes, -1, -1, gC, null, sor,
                or);
    }

    /**
     * Instantiates a new invalid location.
     *
     * @param id
     *            the id
     * @param locTypeValue
     *            the loc type value
     * @param retCode
     *            the ret code
     * @param subRoutes
     *            the sub routes
     * @param sor
     *            the sor
     * @param or
     *            the or
     */
    public InvalidLocation(final String id, final LocationType locTypeValue,
                           final StatusCode retCode, final List<List<Line>> subRoutes,
                           final SideOfRoad sor, final Orientation or) {
        this(id, locTypeValue, retCode, null, subRoutes, -1, -1, null, null,
                sor, or);
    }

    /**
     * Instantiates a new invalid location.
     *
     * @param id
     *            the id
     * @param retCode
     *            the ret code
     * @param locTypeValue
     *            the loc type value
     * @param sor
     *            the sor
     * @param or
     *            the or
     */
    public InvalidLocation(final String id, final StatusCode retCode,
                           final LocationType locTypeValue, final SideOfRoad sor,
                           final Orientation or) {
        this(id, locTypeValue, retCode, null, null, -1, -1, null, null, sor, or);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<Line> getLocationLines() {
        return lines;
    }

    /**
     * Gets the list of sub routes being found by the decoder.
     *
     * @return the sub route list
     */
    public final List<List<Line>> getSubRouteList() {
        return subRouteList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getPositiveOffset() {
        return positiveOffset;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getNegativeOffset() {
        return negativeOffset;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final GeoCoordinates getPointLocation() {
        return geoCoord;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final GeoCoordinates getAccessPoint() {
        return accessPoint;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean hasNegativeOffset() {
        return negativeOffset > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean hasPositiveOffset() {
        return positiveOffset > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Line getPoiLine() {
        Line l = null;
        if (lines != null && !lines.isEmpty()) {
            l = lines.get(0);
        }
        return l;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Orientation getOrientation() {
        return orientation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final SideOfRoad getSideOfRoad() {
        return sideOfRoad;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id: ").append(id);
        sb.append(" locType: ").append(locType);
        if (lines != null && !lines.isEmpty()) {
            sb.append(" lines: [");
            for (int i = 0; i < lines.size(); i++) {
                Line l = lines.get(i);
                sb.append(l.getID());
                if (i == lines.size() - 1) {
                    sb.append("]");
                } else {
                    sb.append(", ");
                }
            }
        }
        sb.append(" posOff: ").append(positiveOffset);
        sb.append(" negOff: ").append(negativeOffset);
        if (geoCoord != null) {
            sb.append(" geoCoord: ").append(geoCoord);
        }
        if (accessPoint != null) {
            sb.append(" accessPoint: ").append(accessPoint);
        }
        if (sideOfRoad != null) {
            sb.append(" sideOfRoad: ").append(sideOfRoad);
        }
        if (orientation != null) {
            sb.append(" orientation: ").append(orientation);
        }
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int calculateHashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(negativeOffset)
                .append(positiveOffset)
                .append(geoCoord)
                .append(accessPoint)
                .append(sideOfRoad)
                .append(orientation)
                .append(lines)
                .append(subRouteList)
                .append(locType);
        return builder.toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object obj) {
        if (!(obj instanceof InvalidLocation)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        InvalidLocation other = (InvalidLocation) obj;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(negativeOffset, other.negativeOffset)
                .append(positiveOffset, other.positiveOffset)
                .append(geoCoord, other.geoCoord)
                .append(accessPoint, other.accessPoint)
                .append(sideOfRoad, other.sideOfRoad)
                .append(orientation, other.orientation)
                .append(lines, other.lines)
                .append(subRouteList, other.subRouteList)
                .append(locType, other.locType);
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
