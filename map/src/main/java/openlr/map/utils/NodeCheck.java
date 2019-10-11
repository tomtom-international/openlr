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
package openlr.map.utils;

import openlr.map.Line;
import openlr.map.Node;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The Class NodeCheck checks the validity of a node. A node is invalid if it is
 * not possible to leave a path at this node (no start of a deviation possible).
 *
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public final class NodeCheck {

    /** number of roads at nodes with degree 2 */
    private static final int DEGREE_2 = 2;

    /** number of roads at nodes with degree 4 */
    private static final int DEGREE_4 = 4;

    /**
     * Utility class shall not be instantiated.
     */
    private NodeCheck() {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if node n is valid. The method distinguishes two cases for a node
     * being invalid.
     * <ul>
     * <li>the node has a degree of 2 and it is not a dead-end street
     * <ul>
     * <li>if a node has only one incoming and one outgoing line then it can be
     * skipped during route search because no deviation at this point is
     * possible
     * <li>if the node is part of a dead-end street, then the node is still
     * valid because otherwise at this point it is only possible to go back the
     * line
     * <li>if such a dead-end node is start or end of a location it needs to be
     * valid, otherwise every extension might be useless
     * </ul>
     * <li>the node has a degree of 4 and the incoming/outgoing lines are
     * pairwise
     * <ul>
     * <li>if the incoming/outgoing lines form two pairs, then the node connects
     * only two other nodes and no deviation is possible (u-turns are also not
     * allowed)
     * <li>if there are more connected lines, then the node is valid
     * </ul>
     * </ul>
     *
     * @param n
     *            the node
     *
     * @return true, if node n is valid, otherwise false
     */
    public static boolean isValidNode(final Node n) {
        if (n == null) {
            return false;
        }
        // node degree is 2
        int degree = n.getNumberConnectedLines();
        if (degree == DEGREE_2) {
            Iterator<? extends Line> iter = n.getConnectedLines();
            Line line1 = iter.next();
            Line line2 = iter.next();
            // if isPair then we have a dead-end street
            // otherwise we can travel along the streets without coming back to
            // the start point
            return isPair(line1, line2);
        }
        // node degree is 4
        if (degree == DEGREE_4) {
            Iterator<? extends Line> connectedLines = n.getConnectedLines();
            List<Line> lines = new ArrayList<Line>();
            while (connectedLines.hasNext()) {
                lines.add(connectedLines.next());
            }

            // find two pairs within this array
            // if there are two pairs then the node is avoidable otherwise we
            // are connecting more than
            // two nodes and this node needs to be considered
            Line line = lines.get(0);
            boolean found = false;
            int pos = 1;
            // find a pair containing the first line and delete both lines
            while (!found && pos < lines.size()) {
                if (isPair(line, lines.get(pos))) {
                    found = true;
                    lines.remove(pos);
                    lines.remove(0);
                }
                pos++;
            }
            // if no pair containing the first line can be found then we have a
            // considerable node
            if (!found) {
                return true;
            }
            // check the remaining edges if they form a pair and if so, then
            // avoid the node
            if (isPair(lines.get(0), lines.get(1))) {
                return false;
            }
            return true;
        }
        return true;
    }

    /**
     * Checks if line1 and line2 connect only two nodes and point into two
     * different directions. So one line is the opposite direction of the other
     * one.
     *
     * @param line1
     *            the first line
     * @param line2
     *            the second line
     *
     * @return true, if the lines have start/end nodes in common and define two
     *         different directions
     */
    public static boolean isPair(final Line line1, final Line line2) {
        return (line1.getStartNode().getID() == line2.getEndNode().getID()) && (line1.getEndNode().getID() ==
                line2.getStartNode().getID());
    }

}
