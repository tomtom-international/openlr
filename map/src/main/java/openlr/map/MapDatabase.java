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
package openlr.map;

import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.List;

/**
 * The Interface MapDatabase is the geospatial representation of an area on the earth surface. A map database 
 * of a road network holds lines and nodes whereby the lines represent the roads and the nodes represent start
 * and end of lines as well as intersections of lines.
 * <p>
 * A map database representing a road network might also hold information about turn restrictions. Paths in a 
 * network might not be drivable in a certain sequence.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public interface MapDatabase {

    /**
     * Checks if the map database also holds information on turn restrictions.
     *
     * @return true, if the map database holds information on turn restrictions, otherwise false
     */
    boolean hasTurnRestrictions();

    /**
     * Returns the line of the road network with the ID id. If the map database holds no line with that ID, 
     * it will return null.
     *
     * @param id the id of the requested line
     *
     * @return the line with ID id or null if no line with that ID exists
     */
    Line getLine(long id);

    /**
     * Returns the node of the road network with the ID id. If the map database holds no node with that ID, 
     * it will return null.
     *
     * @param id the id of the requested node
     *
     * @return the node with ID id or null if no node with that ID exists
     */
    Node getNode(long id);

    /**
     * Returns a set of nodes in the road network which are within a distance of distance meter away from the 
     * position arguments (latitude, longitude). The coordinates shall be in the WGS84 format and the distance
     * argument shall be measured in meter. The implementation shall ensure that nodes with a greater distance
     * are not included in the return set!
     *
     * @param latitude the latitude of the position
     * @param longitude the longitude of the position
     * @param distance the radius around the position where the nodes should be located
     *
     * @return a set of nodes being at most distance meters away from the position
     */
    Iterator<Node> findNodesCloseByCoordinate(double longitude, double latitude, int distance);

    /**
     * Returns a set of lines in the road network which are within a distance of distance meter away from the 
     * position arguments (latitude, longitude). The coordinates shall be in the WGS84 format and the distance
     * argument shall be measured in meter. The implementation shall ensure that lines with a greater distance
     * are not included in the return set!
     *
     * @param latitude the latitude of the position
     * @param longitude the longitude of the position
     * @param distance the radius around the position where the lines should be located
     *
     * @return a set of lines being at most distance meters away from the position
     */
    Iterator<Line> findLinesCloseByCoordinate(double longitude, double latitude, int distance);

    /**
     * Checks for turn restrictions on a path. Turn restrictions always limit the number of drivable paths in a
     * road network. A path consists of an ordered and connected sequence of lines. If a turning along a path is
     * not allowed, then the turn restriction will contain at least the line before the not-allowed turning point 
     * and the line directly behind that point.
     * <p>
     * This methods checks whether at least one turn restriction exists which is completely part of the path in the
     * argument of this method. It returns true if there is a turn restriction on that path. 
     *
     * @param path the path to be checked for turn restrictions
     *
     * @return true, if there exists a turn restriction on that path, otherwise false
     */
    boolean hasTurnRestrictionOnPath(List<? extends Line> path);

    /**
     * Returns all nodes.
     *
     * @return all nodes
     */
    Iterator<Node> getAllNodes();

    /**
     * Returns all lines.
     *
     * @return all lines
     */
    Iterator<Line> getAllLines();

    /**
     * Gets the bounding box of the map.
     * <br><br>
     * This method is <b>optional</b> and shall return null if not supported.
     *
     * @return the bounding box
     */
    Rectangle2D.Double getMapBoundingBox();

    /**
     * Returns the number of nodes.
     * <br><br>
     * This method is <b>optional</b> and shall return -1 if not supported.
     *
     * @return the number of nodes
     */
    int getNumberOfNodes();

    /**
     * Returns the number of lines.     * 
     * <br><br>
     * This method is <b>optional</b> and shall return -1 if not supported.
     *
     * @return the number of lines
     */
    int getNumberOfLines();
}
