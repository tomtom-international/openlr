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
package openlr.decoder.data;

import openlr.map.Node;

import java.io.Serializable;
import java.util.Comparator;

/**
 * The Class NodeWithDistance assigns a node in the road network with a distance
 * value measuring the distance to a defined position in the map.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class NodeWithDistance {

    /** The node in the road network. */
    private final Node node;

    /** The distance in meter [m] to a predefined position in the road network . */
    private final int distance;

    /**
     * Instantiates a new node with distance.
     *
     * @param n
     *            the node
     * @param dist
     *            the distance to a predefined position in the map
     */
    public NodeWithDistance(final Node n, final int dist) {
        this.node = n;
        this.distance = dist;
    }


    /**
     * Gets the node.
     *
     * @return the node
     */
    public final Node getNode() {
        return node;
    }

    /**
     * Gets the distance to a predefined position in the network.
     *
     * @return the distance
     */
    public final int getDistance() {
        return distance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("node: ").append(node.getID());
        sb.append(" - distance: ").append(distance).append("m");
        return sb.toString();
    }


    /**
     * The Class NodeWithDistanceComparator.
     */
    public static class NodeWithDistanceComparator implements Comparator<NodeWithDistance>, Serializable {

        /**
         * Serialization ID.
         */
        private static final long serialVersionUID = -3067041787960067220L;

        /** {@inheritDoc} */
        @Override
        public final int compare(final NodeWithDistance o1, final NodeWithDistance o2) {
            if (o1.distance < o2.distance) {
                return -1;
            }
            if (o1.distance > o2.distance) {
                return 1;
            }
            return 0;
        }
    }

}
