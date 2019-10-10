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
package openlr.decoder.data;

import cern.colt.map.OpenIntObjectHashMap;
import openlr.LocationReferencePoint;
import openlr.decoder.data.NodeWithDistance.NodeWithDistanceComparator;

import java.util.Collections;
import java.util.List;


/**
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class CandidateNodesResultSet {

    /** The Constant NODE_WITH_DISTANCE_COMPARATOR. */
    private static final NodeWithDistanceComparator NODE_WITH_DISTANCE_COMPARATOR = new NodeWithDistance.NodeWithDistanceComparator();

    /** The candidate nodes. */
    private final OpenIntObjectHashMap candidateNodes = new OpenIntObjectHashMap();

    /**
     * Put candidate nodes.
     *
     * @param lrp the lrp
     * @param candidates the candidates
     */
    public final void putCandidateNodes(final LocationReferencePoint lrp, final List<NodeWithDistance> candidates) {
        Collections.sort(candidates, NODE_WITH_DISTANCE_COMPARATOR);
        candidateNodes.put(lrp.getSequenceNumber(), candidates);
    }

    /**
     * Gets the candidate nodes.
     *
     * @param lrp the lrp
     * @return the candidate nodes
     */
    @SuppressWarnings("unchecked")
    public final List<NodeWithDistance> getCandidateNodes(final LocationReferencePoint lrp) {
        if (candidateNodes.containsKey(lrp.getSequenceNumber())) {
            return (List<NodeWithDistance>) candidateNodes.get(lrp.getSequenceNumber());
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    /**
     * Gets the closest node.
     *
     * @param lrp the lrp
     * @return the closest node
     */
    @SuppressWarnings("unchecked")
    public final NodeWithDistance getClosestNode(final LocationReferencePoint lrp) {
        NodeWithDistance nwd = null;
        List<NodeWithDistance> list = (List<NodeWithDistance>) candidateNodes.get(lrp.getSequenceNumber());
        if (list != null && !list.isEmpty()) {
            nwd = list.get(0);
        }
        return nwd;
    }

}
