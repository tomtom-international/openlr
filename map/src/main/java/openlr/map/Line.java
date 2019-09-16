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
package openlr.map;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * The Interface Line is a one-dimensional representation of a road or part of road in a road network. 
 * A line starts and ends at a node. It is directed, this means two-way traffic flow is represented by 
 * two (directed) lines, one per direction. A line is not abstracted by the airline between start and 
 * end node, it rather "knows" its geometry. Additional information on that line includes its functional
 * road class and its form of way. Since the line is part of a network, its predecessors and successors are
 * also accessible if available. 
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public interface Line {

    /**
     * Gets the start node of the line.
     *
     * @return the start node
     */
    Node getStartNode();

    /**
     * Gets the end node of the line.
     *
     * @return the end node
     */
    Node getEndNode();

    /**
     * Gets the {@link FormOfWay} of the line.
     *
     * @return the form of way
     */
    FormOfWay getFOW();

    /**
     * Gets the {@link FunctionalRoadClass} of the line.
     *
     * @return the functional road class
     */
    FunctionalRoadClass getFRC();

    /**
     * Gets a point along the line geometry which is {@code distanceAlong} meter
     * away from the start node of the line. If the given distance exceeds the
     * length of the line the end node is returned! The x-coordinate of the
     * point refers to the longitude value and the y-coordinate refers to the
     * latitude value.
     *
     * @param distanceAlong
     *            the distance along the geometry of the line to find the
     *            position
     *
     * @return the point on the line being {@code distanceAlong} meters away
     *         from the start node
     * @deprecated This method still exists to keep backwards compatibility
     *             and will be removed in future releases, use
     *             {@link #getGeoCoordinateAlongLine(int)} instead.
     */
    @Deprecated
    Point2D.Double getPointAlongLine(int distanceAlong);

    /**
     * Gets a point along the line geometry which is {@code distanceAlong} meter
     * away from the start node of the line. If the given distance exceeds the
     * length of the line the end node is returned! The x-coordinate of the
     * point refers to the longitude value and the y-coordinate refers to the
     * latitude value.
     *
     * @param distanceAlong
     *            the distance along the geometry of the line to find the
     *            position
     *
     * @return the point on the line being {@code distanceAlong} meters away
     *         from the start node
     */
    GeoCoordinates getGeoCoordinateAlongLine(int distanceAlong);

    /**
     * Gets the length of the line indicating its real dimension along the geometry of the line. The resolution used
     * for the length value should be meter [m].
     *
     * @return the length of the line in meter
     */
    int getLineLength();

    /**
     * Gets the unique ID.
     *
     * @return the unique ID
     */
    long getID();

    /**
     * Returns a set of lines which precedes this line in the same direction. The set of lines
     * is equal to the set of incoming lines of the start node of this line.
     *
     * @return a set of lines preceding this line in the same direction
     */
    Iterator<Line> getPrevLines();

    /**
     * Returns a set of lines which follows this line in the same direction. The set of lines
     * is equal to the set of outgoing lines of the end node of this line.
     *
     * @return a set of lines following this line in the same direction
     */
    Iterator<Line> getNextLines();

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param obj the reference object with which to compare
     *
     * @return true, if this object is the same as the obj argument, otherwise false
     */
    @Override
    boolean equals(Object obj);

    /**
     * Returns a hash code value for the line.
     *
     * @return a hash code value for the line 
     */
    @Override
    int hashCode();

    /**
     * Calculates the (smallest) distance in meters between the line and the
     * point given by its longitude and latitude coordinates.
     *
     * @param longitude
     *            the longitude coordinate of the point
     * @param latitude
     *            the latitude coordinate of the point
     *
     * @return the (smallest) distance between the line and the point
     */
    int distanceToPoint(double longitude, double latitude);

    /**
     * Calculates a projection point on the line for the given coordinates and
     * returns the distance between the start node of the line and the
     * projection point along the line shape. The projection point shall be that
     * point on the line with the smallest distance between the line and the
     * point given by the longitude and latitude coordinates.
     *
     * @param longitude
     *            the longitude coordinate of the point
     * @param latitude
     *            the latitude coordinate of the point
     *
     * @return the distance between the start node and the projection of the
     *         point given by the coordinates
     */
    int measureAlongLine(double longitude, double latitude);


    /**
     * Gets the shape of the line. <br>
     * <br>
     * This method is <b>optional</b> and shall return null if not supported.
     *
     * @return the shape of the line
     * @deprecated This method still exists to keep backwards compatibility
     *             and will be removed in future releases,
     *             use {@link #getShapeCoordinates()} instead.
     */
    @Deprecated
    Path2D.Double getShape();

    /**
     * Gets the shape of the line.
     * <br><br>
     * This method is <b>optional</b> and shall return null if not supported.
     *
     * @return the shape of the line
     */
    List<GeoCoordinates> getShapeCoordinates();

    /**
     * Gets the names of the line. The map addresses all available locale settings and the most
     * common name for this line should come first in the list of name strings.
     * <br><br>
     * This method is <b>optional</b> and shall return null if not supported.
     *
     * @return the names of the line
     */
    Map<Locale, List<String>> getNames();


}
