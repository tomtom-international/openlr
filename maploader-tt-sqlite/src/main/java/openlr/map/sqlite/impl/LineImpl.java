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
package openlr.map.sqlite.impl;

import openlr.map.*;
import openlr.map.utils.GeometryUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.*;

/**
 * Implementation of the OpenLR {@link openlr.map.Line} interface for use with a
 * TomTom digital map in SQLite format.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public final class LineImpl implements Line {

    /**
     * The ID of the {@link openlr.map.Node} located at the end of this
     * {@link openlr.map.Line}.
     */
    private final long endNodeId;

    /**
     * The {@link openlr.map.FormOfWay} attribute value.
     */
    private final FormOfWay formOfWay;

    /**
     * The {@link openlr.map.FunctionalRoadClass} attribute value.
     */
    private final FunctionalRoadClass functionalRoadClass;

    /**
     * The unique ID of this {@link openlr.map.Line}.
     */
    private final long id;

    /**
     * The length of this line in meters.
     */
    private final int length;

    /**
     * The internal representation of the geometry.
     */
    private final List<GeoCoordinates> shape;

    /**
     * The {@link openlr.map.MapDatabase} instance which manages this feature.
     */
    private final MapDatabase mdb;

    /**
     * A map containing naming information in different languages.
     */
    private final Map<Locale, List<String>> names;

    /**
     * The ID of the {@link openlr.map.Node} located at the start of this
     * {@link openlr.map.Line}.
     */
    private final long startNodeId;

    /**
     * Creates an instance of this class representing a {@link openlr.map.Line}
     * feature with a given ID in the network managed by the given
     *
     * @param mapDB the {@link openlr.map.MapDatabase} this feature is managed by.
     * @param idValue the unique ID of this {@link openlr.map.Line}.
     * @param startNode the start node id
     * @param endNode the end node id
     * @param fow the {@link openlr.map.FormOfWay} attribute value.
     * @param frc the {@link openlr.map.FunctionalRoadClass} attribute value.
     * @param lineS the list of shape points defining the geometry.
     * @param lengthValue the length of this line in meters.
     * @param roadNames the naming information for the road which is represented by
     * this {@link openlr.map.Line}.
     * {@link openlr.map.MapDatabase} instance.
     */
    LineImpl(final MapDatabase mapDB, final long idValue,
             final long startNode, final long endNode,
             final FormOfWay fow,
             final FunctionalRoadClass frc,
             final List<GeoCoordinates> lineS, final int lengthValue,
             final Map<Locale, List<String>> roadNames) {
        this.mdb = mapDB;
        this.id = idValue;
        this.startNodeId = startNode;
        this.endNodeId = endNode;
        this.formOfWay = fow;
        this.functionalRoadClass = frc;
        this.shape = lineS;
        this.length = lengthValue;
        this.names = Collections.unmodifiableMap(roadNames);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int distanceToPoint(final double arg0, final double arg1) {
        return determineShapeIndex(arg0, arg1).getDist();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object other) {
        boolean ret = true;
        if (this == other) {
            ret = true;
        } else if (other == null) {
            ret = false;
        } else if (getClass() != other.getClass()) {
            ret = false;
        } else {
            final Line otherLine = (Line) other;
            if (getID() != otherLine.getID()) {
                ret = false;
            }
        }
        return ret;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Node getEndNode() {
        return mdb.getNode(endNodeId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormOfWay getFOW() {
        return formOfWay;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FunctionalRoadClass getFRC() {
        return functionalRoadClass;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getID() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLineLength() {
        return length;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Locale, List<String>> getNames() {
        return names;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<Line> getNextLines() {
        return getEndNode().getOutgoingLines();
    }

    /**
     * {@inheritDoc}
     * @deprecated This method still exists to keep backwards compatibility
     *             and will be removed in future releases, use
     *             {@link #getGeoCoordinateAlongLine(int)} instead.
     */
    @Override
    @Deprecated
    public Point2D.Double getPointAlongLine(final int distanceAlong) {

        GeoCoordinates point = getGeoCoordinateAlongLine(distanceAlong);
        return new Point2D.Double(point.getLongitudeDeg(),
                point.getLatitudeDeg());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeoCoordinates getGeoCoordinateAlongLine(int distanceAlong) {

        if (distanceAlong == 0) {
            return GeoCoordinatesImpl.newGeoCoordinatesUnchecked(getStartNode()
                    .getLongitudeDeg(), getStartNode().getLatitudeDeg());
        } else if (distanceAlong >= getLineLength()) {
            return GeoCoordinatesImpl.newGeoCoordinatesUnchecked(getEndNode()
                    .getLongitudeDeg(), getEndNode().getLatitudeDeg());
        }

        GeoCoordinates previous = null;
        double remaining = distanceAlong;

        for (GeoCoordinates shapePoint : shape) {

            if (previous != null) {
                double dist = GeometryUtils.distance(
                        previous.getLongitudeDeg(), previous.getLatitudeDeg(),
                        shapePoint.getLongitudeDeg(),
                        shapePoint.getLatitudeDeg());
                if (remaining > dist) {
                    remaining -= dist;
                } else {
                    double frac = remaining / dist;

                    return getPointOnLine(previous, shapePoint, frac);
                }
            }

            previous = shapePoint;
        }

        throw new IllegalStateException("No shape points available");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<Line> getPrevLines() {
        return getStartNode().getIncomingLines();
    }

    /**
     * Delivers the shape in form of a {@link Path2D}. This method is marked
     * deprecated in the OpenLR interface. It is implemented in an inefficient
     * way here to keep it working but clients should switch to the successor
     * method {@link #getShapeCoordinates()}!
     *
     * @deprecated This method is kept but inefficient, please use  {@link #getShapeCoordinates()} instead! 
     */
    @Override
    @Deprecated
    public Path2D.Double getShape() {

        Path2D.Double path = new Path2D.Double();

        boolean first = true;

        for (GeoCoordinates coord : shape) {

            if (first) {
                path.moveTo(coord.getLongitudeDeg(), coord.getLatitudeDeg());
                first = false;
            } else {
                path.lineTo(coord.getLongitudeDeg(), coord.getLatitudeDeg());
            }

        }
        return path;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GeoCoordinates> getShapeCoordinates() {
        return shape;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Node getStartNode() {
        return mdb.getNode(startNodeId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(id);
        return builder.toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int measureAlongLine(final double arg0, final double arg1) {
        int currLength = 0;
        LinePointRelation linePoint = determineShapeIndex(arg0, arg1);
        Iterator<GeoCoordinates> coordsIter = shape.iterator();
        GeoCoordinates previous = null;
        int counter = 1;
        int idx = linePoint.getIndex();
        while (coordsIter.hasNext() && counter <= idx) {

            GeoCoordinates current = coordsIter.next();
            if (previous != null) {
                if (counter < idx) {
                    int dist = (int) GeometryUtils
                            .distance(previous.getLongitudeDeg(),
                                    previous.getLatitudeDeg(),
                                    current.getLongitudeDeg(),
                                    current.getLatitudeDeg());
                    currLength += dist;
                } else if (counter == idx) {
                    int dist = (int) GeometryUtils
                            .distance(previous.getLongitudeDeg(),
                                    previous.getLatitudeDeg(),
                                    current.getLongitudeDeg(),
                                    current.getLatitudeDeg());
                    currLength += linePoint.getProjectionFactor() * dist;
                }
            }

            previous = current;

            counter++;
        }
        return currLength;
    }

    /**
     * Determine shape index.
     *
     * @param lon the lon
     * @param lat the lat
     * @return the line point relation
     */
    private LinePointRelation determineShapeIndex(final double lon,
                                                  final double lat) {
        int idx = -1;
        int minDist = Integer.MAX_VALUE;
        GeoCoordinates previous = null;
        int counter = 1;
        double minProjFac = 0.0;
        for (GeoCoordinates current : shape) {

            if (previous != null) {
                double projFac = projectionFactor(previous.getLongitudeDeg(),
                        previous.getLatitudeDeg(), current.getLongitudeDeg(),
                        current.getLatitudeDeg(), lon, lat);
                GeoCoordinates d = getPointOnLine(previous, current, projFac);
                int dist = (int) GeometryUtils.distance(lon, lat,
                        d.getLongitudeDeg(), d.getLatitudeDeg());
                if (dist < minDist) {
                    idx = counter;
                    minDist = dist;
                    minProjFac = projFac;
                }
            }
            previous = current;

            counter++;
        }
        return new LinePointRelation(idx, minDist, minProjFac);
    }

    /**
     * Projection factor.
     *
     * @param p1Lon the p1 lon
     * @param p1Lat the p1 lat
     * @param p2Lon the p2 lon
     * @param p2Lat the p2 lat
     * @param lon the lon
     * @param lat the lat
     * @return the double
     */
    private double projectionFactor(final double p1Lon, final double p1Lat,
                                    final double p2Lon, final double p2Lat, final double lon,
                                    final double lat) {
        double dx = p2Lon - p1Lon;
        double dy = p2Lat - p1Lat;
        double len = dx * dx + dy * dy;
        double r = ((lon - p1Lon) * dx + (lat - p1Lat) * dy) / len;
        if (r < 0.0) {
            r = 0.0;
        } else if (r > 1.0) {
            r = 1.0;
        }
        return r;
    }

    /**
     * Gets the point on line.
     *
     * @param p1 the p1
     * @param p2 the p2
     * @param frac the frac
     * @return the point on line
     */
    private GeoCoordinates getPointOnLine(final GeoCoordinates p1,
                                          final GeoCoordinates p2, final double frac) {
        if (frac <= 0.0) {
            return p1;
        }
        if (frac >= 1.0) {
            return p2;
        }
        double x = (p2.getLongitudeDeg() - p1.getLongitudeDeg()) * frac
                + p1.getLongitudeDeg();
        double y = (p2.getLatitudeDeg() - p1.getLatitudeDeg()) * frac
                + p1.getLatitudeDeg();
        return GeoCoordinatesImpl.newGeoCoordinatesUnchecked(x, y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.valueOf(id);
    }

    /**
     * The Class LinePointRelation.
     */
    private static class LinePointRelation {

        /** The idx. */
        private final int idx;

        /** The dist. */
        private final int dist;

        /** The projection factor. */
        private final double projectionFactor;

        /**
         * Instantiates a new line point relation.
         *
         * @param index the index
         * @param d the d
         * @param projFac the proj fac
         */
        LinePointRelation(final int index, final int d, final double projFac) {
            idx = index;
            projectionFactor = projFac;
            dist = d;
        }

        /**
         * Gets the index.
         *
         * @return the index
         */
        int getIndex() {
            return idx;
        }

        /**
         * Gets the dist.
         *
         * @return the dist
         */
        int getDist() {
            return dist;
        }

        /**
         * Gets the projection factor.
         *
         * @return the projection factor
         */
        double getProjectionFactor() {
            return projectionFactor;
        }
    }
}
