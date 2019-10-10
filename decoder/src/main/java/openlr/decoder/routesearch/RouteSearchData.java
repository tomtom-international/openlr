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
package openlr.decoder.routesearch;

import openlr.collection.OpenLongIntHashMap;
import openlr.map.Line;
import openlr.map.utils.PQElem;
import openlr.map.utils.PQElem.PQElemComparator;

import java.util.PriorityQueue;

/**
 * The class RouteSearchData holds the best values for each line and the open list
 * for the route search process.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class RouteSearchData {

    /** The Constant PQ_INITIAL_SIZE. */
    private static final int PQ_INITIAL_SIZE = 11;

    /**
     * open contains an ordered listlocElem of elements which need to be
     * investigated during route search
     */
    private final PriorityQueue<PQElem> open;

    /** contains the shortest distance to each line being investigated so far */
    private final OpenLongIntHashMap bestValues;

    /**
     * Instantiates a new route search data.
     */
    public RouteSearchData() {
        // setup local data structures
        open = new PriorityQueue<PQElem>(PQ_INITIAL_SIZE, new PQElemComparator());
        bestValues = new OpenLongIntHashMap();
    }

    /**
     * Adds the to open.
     *
     * @param elem the elem
     */
    public final void addToOpen(final PQElem elem) {
        open.add(elem);
        bestValues.put(elem.getLine().getID(), elem.getSecondVal());
    }

    /**
     * Checks if is open empty.
     *
     * @return true, if is open empty
     */
    public final boolean isOpenEmpty() {
        return open.isEmpty();
    }

    /**
     * Poll element.
     *
     * @return the pQ elem
     */
    public final PQElem pollElement() {
        return open.poll();
    }

    /**
     * Checks for length value.
     *
     * @param l the line
     * @return true, if line has already a path length
     */
    public final boolean hasLengthValue(final Line l) {
        return bestValues.containsKey(l.getID());
    }

    /**
     * Gets the length value.
     *
     * @param l the l
     * @return the length value
     */
    public final int getLengthValue(final Line l) {
        return bestValues.get(l.getID());
    }

    /**
     * Update in open.
     *
     * @param elem the elem
     */
    public final void updateInOpen(final PQElem elem) {
        open.remove(elem);
        addToOpen(elem);
    }

    /**
     * Gets the open list.
     *
     * @return the open list
     */
    public final PriorityQueue<PQElem> getOpenList() {
        return open;
    }


}
