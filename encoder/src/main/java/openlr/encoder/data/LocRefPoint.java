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
package openlr.encoder.data;

import openlr.LocationReferencePoint;
import openlr.OpenLRProcessingException;
import openlr.encoder.properties.OpenLREncoderProperties;
import openlr.map.FormOfWay;
import openlr.map.FunctionalRoadClass;
import openlr.map.Line;
import openlr.map.Node;
import openlr.map.utils.GeometryUtils;
import openlr.map.utils.PathUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.Arrays;
import java.util.List;

/**
 * The Class LocRefPoint encapsulates the data for a location reference point.
 * This includes the line being represented by this LRP and the shortest-path to
 * the next location reference point. All relevant data for an LRP can be
 * calculated.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class LocRefPoint implements LocationReferencePoint {

    /** The LRP refers to this line. */
    private final Line line;

    /** The shortest-path to the next LRP. */
    private final List<Line> subroute;

    /**
     * indicates whether the LRP is located directly on a line or refers to a
     * node
     */
    private final boolean isPointOnLine;

    /** indicates whether the LRP is the last one in the sequence or not */
    private final boolean isLast;

    /** the longitude coordinate of the LRP */
    private final double longitude;

    /** the latitude coordinate of the LRP */
    private final double latitude;
    /** the calculated bearing value */
    private final double bearing;
    /** the next location reference point */
    private LocRefPoint next;
    /** The sequence number. */
    private int sequenceNumber;

    /**
     * Instantiates a new location reference point based on a node. This LRP is
     * not the last LRP in the sequence of LRPs.
     *
     * @param route
     *            the shortest-path to the next LRP
     * @param p
     *            the encoder properties
     * @throws OpenLRProcessingException
     *             the open lr processing exception
     */
    public LocRefPoint(final List<Line> route, final OpenLREncoderProperties p)
            throws OpenLRProcessingException {
        this(route.get(0), route, false, route.get(0).getStartNode()
                .getLongitudeDeg(), route.get(0).getStartNode()
                .getLatitudeDeg(), p, false);
    }

    /**
     * Instantiates a new location reference point based on the end node of
     * the given line and this LRP is the last LRP in the sequence of the LRPs.
     *
     * @param l
     *            the referenced line
     * @param p
     *            the encoder properties
     * @throws OpenLRProcessingException
     *             the open lr processing exception
     */
    public LocRefPoint(final Line l, final OpenLREncoderProperties p)
            throws OpenLRProcessingException {
        this(l, null, false, l.getEndNode().getLongitudeDeg(), l.getEndNode()
                .getLatitudeDeg(), p, true);
    }

    /**
     * Instantiates a new location reference point based on a point directly on
     * a line. This LRP is not based on a node. It is assumed that the single
     * given line is the entire path to the next point.
     *
     * @param l the line containing the referenced point
     * @param lon the longitude coordinate of the point
     * @param lat the latitude coordinate of the point
     * @param p the encoder properties
     * @param lastLRP whether this LRP is the last lrp in the location reference
     * @throws OpenLRProcessingException the open lr processing exception
     */
    public LocRefPoint(final Line l, final double lon, final double lat,
                       final OpenLREncoderProperties p, final boolean lastLRP)
            throws OpenLRProcessingException {
        this(l, Arrays.asList(l), true, lon, lat, p, lastLRP);
    }

    /**
     * The internal worker constructor.
     *
     * @param l the line containing the referenced point
     * @param route
     * @param isOnLine Whether this LRP is located directly on a line instead of a node
     * @param lon the longitude coordinate of the point
     * @param lat the latitude coordinate of the point
     * @param p the encoder properties
     * @param lastLRP whether this LRP is the last lrp in the location reference
     * @throws OpenLRProcessingException the open lr processing exception
     */
    private LocRefPoint(final Line l, final List<Line> route, final boolean isOnLine, final double lon,
                        final double lat, final OpenLREncoderProperties p,
                        final boolean lastLRP) throws OpenLRProcessingException {

        line = l;
        if (lastLRP) {
            subroute = null;
        } else {
            subroute = route;
        }
        isPointOnLine = isOnLine;
        isLast = lastLRP;
        longitude = lon;
        latitude = lat;
        bearing = calculateBearing(p);
    }

    /**
     * Sets the next location reference point.
     *
     * @param n
     *            the next location reference point
     */
    public final void setNextLRP(final LocRefPoint n) {
        next = n;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final double getLongitudeDeg() {
        return longitude;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final double getLatitudeDeg() {
        return latitude;
    }

    /**
     * Gets the LRP node.
     *
     * @return the LRP node
     */
    public final Node getLRPNode() {
        if (line == null) {
            throw new UnsupportedOperationException();
        }
        Node n = null;
        if (!isPointOnLine) {
            if (!isLast) {
                n = line.getStartNode();
            } else {
                n = line.getEndNode();
            }
        }
        return n;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getDistanceToNext() {
        int dist = 0;
        // last LRP has distance 0 for all other LRP the distance will be
        // calculated
        if (!isLast) {
            if (isPointOnLine) {
                // if LRP is a point on a line there is no route available
                if (next != null && next.isPointOnLine) {
                    // next LRP is on the same line, so dist is the difference
                    dist = next.line.measureAlongLine(next.longitude,
                            next.latitude)
                            - line.measureAlongLine(longitude, latitude);
                } else {
                    // next LRP is node based, so distance is rest of the line
                    dist = line.getLineLength()
                            - line.measureAlongLine(longitude, latitude);
                }
            } else {
                // LRP is node based
                if (next != null && next.isPointOnLine) {
                    // next LRP is on that line, so distance from start is
                    // required
                    dist += next.line.measureAlongLine(next.longitude,
                            next.latitude);
                } else {
                    // next LRP is node based, so take the route distance
                    dist = PathUtils.getLength(subroute);
                }
            }
        }
        return dist;
    }

    /**
     * Gets the shortest-path to the next LRP.
     *
     * @return the shortest-path to the next LRP or null if it is the last LRP
     */
    public final List<Line> getRoute() {
        return subroute;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final FunctionalRoadClass getLfrc() {
        if (line == null) {
            throw new UnsupportedOperationException();
        }
        FunctionalRoadClass lfrc = null;
        if (isPointOnLine) {
            lfrc = line.getFRC();
        } else if (subroute != null && subroute.size() > 0) {
            // setup data
            int frcID = 0;
            // iterate over the whole route
            for (Line ds : subroute) {
                frcID = Math.max(frcID, ds.getFRC().getID());
            }
            return FunctionalRoadClass.getFRCs().get(frcID);
        }
        return lfrc;
    }

    /**
     * Gets the bearing of the referenced line.
     *
     * @param properties
     *            the properties
     * @return the bearing of the referenced line
     * @throws OpenLRProcessingException
     *             the open lr processing exception
     */
    private double calculateBearing(final OpenLREncoderProperties properties) {
        GeometryUtils.BearingDirection dir;
        if (isLast) {
            dir = GeometryUtils.BearingDirection.AGAINST_DIRECTION;
        } else {
            dir = GeometryUtils.BearingDirection.IN_DIRECTION;
        }
        int projection;
        if (isPointOnLine) {
            projection = line.measureAlongLine(longitude, latitude);
        } else if (isLast) {
            projection = line.getLineLength();
        } else {
            projection = 0;
        }
        return GeometryUtils.calculateLineBearing(line, dir, properties.getBearingDistance(), projection);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public final FormOfWay getFOW() {
        if (line == null) {
            throw new UnsupportedOperationException();
        }
        return line.getFOW();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final FunctionalRoadClass getFRC() {
        if (line == null) {
            throw new UnsupportedOperationException();
        }
        return line.getFRC();
    }

    /**
     * Checks if this LRP is based directly on a line. It returns false if the
     * LRP is based on a node.
     *
     * @return true, if the LRP is based on a line
     */
    public final boolean isLRPOnLine() {
        return isPointOnLine;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isLastLRP() {
        return isLast;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final double getBearing() {
        return bearing;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("line: ").append(line.getID());
        sb.append(" lon: ").append(longitude);
        sb.append(" lat: ").append(latitude);
        sb.append(" bear: ").append(bearing);
        sb.append(" isPointOnLine: ").append(isPointOnLine);
        sb.append(" isLast: ").append(isLast);
        if ((next != null) && (next.getRoute() != null)
                && !next.getRoute().isEmpty()) {
            sb.append(" next lrp line: ")
                    .append(next.getRoute().get(0).getID());
        }
        if (subroute != null && !subroute.isEmpty()) {
            sb.append(" [");
            for (int i = 0; i < subroute.size(); i++) {
                sb.append(subroute.get(i).getID());
                if (i != subroute.size() - 1) {
                    sb.append(",");
                }
            }
            sb.append("]");
        }
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(bearing).append(subroute).append(line).append(next)
                .append(isPointOnLine).append(isLast).append(longitude)
                .append(latitude);
        return builder.toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object obj) {
        if (!(obj instanceof LocRefPoint)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        LocRefPoint other = (LocRefPoint) obj;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(bearing, other.bearing).append(subroute, other.subroute)
                .append(line, other.line).append(next, other.next)
                .append(isPointOnLine, other.isPointOnLine)
                .append(isLast, other.isLast)
                .append(longitude, other.longitude)
                .append(latitude, other.latitude);
        return builder.isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * Sets the sequence number.
     *
     * @param seqNr
     *            the new sequence number
     */
    public final void setSequenceNumber(final int seqNr) {
        sequenceNumber = seqNr;
    }

    /**
     * Gets the referenced line.
     *
     * @return the referenced line
     */
    public final Line getLine() {
        return line;
    }

    /**
     * Gets the last line of sub route or null if no sub route exists.
     *
     * @return the last line of sub route
     */
    public final Line getLastLineOfSubRoute() {
        if (subroute != null) {
            return subroute.get(subroute.size() - 1);
        }
        return null;
    }

}
