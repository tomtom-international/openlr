/**
 * Licensed to the TomTom International B.V. under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  TomTom International B.V.
 * licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
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
package openlr.map.sqlite.impl;

import openlr.map.*;
import openlr.map.sqlite.helpers.SpatialUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.*;

/**
 * Implementation of the OpenLR {@link openlr.map.Node} interface for use with a
 * TT digital map in SQLite format.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class NodeImpl implements Node {

    /**
     * The incoming lines.
     */
    private final Set<Long> incoming = new HashSet<Long>();

    /**
     * The outgoing lines.
     */
    private final Set<Long> outgoing = new HashSet<Long>();

    /**
     * The unique ID of this {@link openlr.map.Node}.
     */
    private final long id;

    /**
     * A set of {@link openlr.map.Line} objects having a incoming topological
     * connection to this {@link openlr.map.Node}. This set should be populated
     * on first access to the getter method.
     */
    private List<Line> incomingLines;

    /**
     * Latitude value of the spatial position of this {@link openlr.map.Node}.
     */
    private final double latitudeDeg;

    /**
     * Longitude value of the spatial position of this {@link openlr.map.Node}.
     */
    private final double longitudeDeg;

    /**
     * The {@link openlr.map.MapDatabase} instance which manages this feature.
     */
    private final MapDatabase mdb;

    /**
     * A set of {@link openlr.map.Line} objects having a outgoing topological
     * connection to this {@link openlr.map.Node}. This set should be populated
     * on first access to the getter method.
     */
    private List<Line> outgoingLines;

    /**
     * Creates an instance of this class representing a {@link openlr.map.Node}
     * feature with a given ID in the network managed by the given
     *
     * @param map     the {@link openlr.map.MapDatabase} this feature is managed by
     * @param idValue the unique ID of this {@link openlr.map.Node}
     * @param lonDeg  the longitude value of the coordinate in WGS84 coordinate
     *                system
     * @param latDeg  the longitude value of the coordinate in WGS84 coordinate
     *                system
     * @param in      the incoming line ids
     * @param out     the outgoing line ids {@link openlr.map.MapDatabase} instance.
     */
    NodeImpl(final MapDatabase map, final long idValue, final double lonDeg,
             final double latDeg, final Set<Long> in, final Set<Long> out) {
        if (!SpatialUtils.isCoordinateValid(lonDeg, latDeg)) {
            throw new IllegalArgumentException("Coordinate is out of bounds.");
        }
        this.mdb = map;
        this.id = idValue;
        this.longitudeDeg = lonDeg;
        this.latitudeDeg = latDeg;
        incoming.addAll(in);
        outgoing.addAll(out);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object other) {
        boolean ret = true;
        if (this == other) {
            ret = true;
        } else if (other == null) {
            ret = false;
        } else if (getClass() != other.getClass()) {
            ret = false;
        } else {
            final Node otherNode = (Node) other;
            if (getID() != otherNode.getID()) {
                ret = false;
            }
        }
        return ret;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Iterator<Line> getConnectedLines() {
        return resolveConnectedLines().iterator();
    }

    /**
     * Resolve connected lines.
     *
     * @return the list
     */
    private List<Line> resolveConnectedLines() {
        List<Line> connectedLines = new ArrayList<Line>();
        resolveIncomingLines();
        connectedLines.addAll(incomingLines);
        resolveOutgoingLines();
        connectedLines.addAll(outgoingLines);
        return Collections.unmodifiableList(connectedLines);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final long getID() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Iterator<Line> getIncomingLines() {
        resolveIncomingLines();
        return incomingLines.iterator();
    }

    /**
     * Resolve incoming lines.
     */
    private void resolveIncomingLines() {
        if (incomingLines == null) {
            incomingLines = new ArrayList<Line>();
            for (long idValue : incoming) {
                incomingLines.add(mdb.getLine(idValue));
            }
            incomingLines = Collections.unmodifiableList(incomingLines);
            incoming.clear();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final double getLatitudeDeg() {
        return latitudeDeg;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final double getLongitudeDeg() {
        return longitudeDeg;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getNumberConnectedLines() {
        resolveIncomingLines();
        resolveOutgoingLines();
        return incomingLines.size() + outgoingLines.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Iterator<Line> getOutgoingLines() {
        resolveOutgoingLines();
        return outgoingLines.iterator();
    }

    /**
     * Resolve outgoing lines.
     */
    private void resolveOutgoingLines() {
        if (outgoingLines == null) {
            outgoingLines = new ArrayList<Line>();
            for (long idValue : outgoing) {
                outgoingLines.add(mdb.getLine(idValue));
            }
            outgoingLines = Collections.unmodifiableList(outgoingLines);
            outgoing.clear();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(id);
        return builder.toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        return String.valueOf(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final GeoCoordinates getGeoCoordinates() {
        return GeoCoordinatesImpl.newGeoCoordinatesUnchecked(longitudeDeg, latitudeDeg);
    }
}
