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

import java.util.Iterator;

/**
 * The Interface Node defines a zero-dimensional object in the road network. 
 * A node acts as start and end for lines and it is located at a specific position
 * on the earth surface. The coordinates of a node position shall be represented in 
 * the WGS84 system.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public interface Node {

    /**
     * Gets the latitude of the node position in degree.
     *
     * @return the latitude  of the node position in degree
     */
    double getLatitudeDeg();

    /**
     * Gets the longitude of the node position in degree.
     *
     * @return the longitude of the node position in degree
     */
    double getLongitudeDeg();

    /**
     * Gets the geo coordinates of the node position.
     *
     * @return the geo coordinates of the node position
     */
    GeoCoordinates getGeoCoordinates();

    /**
     * Returns a set of connected lines. Connected lines are lines whose end node or start node are equal to
     * this node.
     *
     * @return a set if incident lines
     */
    Iterator<Line> getConnectedLines();

    /**
     * Gets the number of connected lines.
     *
     * @return the number of connected lines
     */
    int getNumberConnectedLines();

    /**
     * Returns a set of outgoing lines. Outgoing lines are lines whose start nodes are equal to this node.
     *
     * @return a set of outgoing lines
     */
    Iterator<Line> getOutgoingLines();

    /**
     * Returns a set of incoming lines. Incoming lines are lines whose end nodes are equal to this node.
     *
     * @return a set of incoming lines
     */
    Iterator<Line> getIncomingLines();


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
     * Return the unique ID of the node
     *
     * @return the unique ID
     */
    long getID();


}
