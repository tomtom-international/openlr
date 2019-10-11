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
package openlr.encoder.routesearch;

import openlr.collection.OpenLongHashSet;
import openlr.encoder.OpenLREncoderProcessingException;
import openlr.encoder.OpenLREncoderProcessingException.EncoderProcessingError;
import openlr.encoder.routesearch.RouteSearchResult.RouteSearchReturnCode;
import openlr.map.FunctionalRoadClass;
import openlr.map.FunctionalRoadClass.FrcComparator;
import openlr.map.Line;
import openlr.map.Node;
import openlr.map.utils.GeometryUtils;
import openlr.map.utils.PQElem;
import openlr.map.utils.PathUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * The Class RouteSearch defines a modified A* route search which checks
 * automatically if a provided location (= ordered and connected list of lines)
 * is also a shortest-path. The route search calculates a shortest-path between
 * the start and the destination of the location. It will be checked in every
 * iteration if the desired location is still part of the current shortest-path
 * tree. The route calculation will stop if this is not the case and it will
 * return one or two lines which can be used as intermediate information to
 * split the location into several shortest-paths.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class RouteSearch {

    /** The Constant FRC_COMPARATOR. */
    private static final FrcComparator FRC_COMPARATOR = new FunctionalRoadClass.FrcComparator();

    /** The logger */
    private static final Logger LOG = Logger.getLogger(RouteSearch.class);
    /** The location. */
    private final List<? extends Line> location;
    /** The start line. */
    private final Line startLine;
    /** The dest line. */
    private final Line destLine;
    /** The start loop index. */
    private final int startLoopIndex;
    /** The end loop index. */
    private final int endLoopIndex;
    /** the x coordinate for the calculation of the heuristic value */
    private double destX = 0;
    /** the y coordinate for the calculation of the heuristic value */
    private double destY = 0;
    private int lengthBetweenStartAndEndLineAlongLocation = 0;

    /**
     * Instantiates a new route search.
     *
     * @param loc
     *            the loc
     * @throws OpenLREncoderProcessingException
     *             the open lr encoder runtime exception
     */
    public RouteSearch(final List<? extends Line> loc)
            throws OpenLREncoderProcessingException {
        location = loc;
        if (location == null || location.isEmpty()) {
            throw new OpenLREncoderProcessingException(
                    EncoderProcessingError.ROUTE_CONSTRUCTION_ERROR);
        }
        startLine = location.get(0);
        destLine = location.get(location.size() - 1);
        for (int routeIndex = 0; routeIndex < location.size(); ++routeIndex) {
            lengthBetweenStartAndEndLineAlongLocation += location.get(routeIndex).getLineLength();
        }
        startLoopIndex = checkLoopAtStart();
        endLoopIndex = checkLoopAtEnd();
    }

    /**
     * Check loop at start.
     *
     * @return the int
     */
    private int checkLoopAtStart() {
        int index = location.subList(1, location.size()).indexOf(
                startLine);
        if (index > 0) {
            // loop detected
            int totalLoops = Collections.frequency(location, startLine);
            if (LOG.isDebugEnabled()) {
                LOG.debug("loop detected");
                LOG.debug("total occurences of start line: " + totalLoops);
            }
        }
        return index;
    }

    /**
     * Check loop at end.
     *
     * @return the int
     */
    private int checkLoopAtEnd() {
        int firstEndLineIndex = location.indexOf(destLine);
        int index = -1;
        if (firstEndLineIndex < location.size() - 1) {
            // loop detected
            if (LOG.isDebugEnabled()) {
                LOG.debug("end loop detected");
            }
            index = firstEndLineIndex;
        }
        return index;
    }

    /**
     * Calculates a shortest-path between start and destination of the location
     * and stops if the provided location is not part of the shortest path tree
     * anymore. The returned route search result contains information about
     * lines which can be used as intermediates which are good lines to split
     * the location into several shortest-paths. It also contains information
     * about the shortest-path found (so far).
     *
     * @return the status of the route search after stopping the search
     * @throws OpenLREncoderProcessingException if route search fails
     */
    public final RouteSearchResult calculateRoute()
            throws OpenLREncoderProcessingException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("route search between start (" + startLine.getID()
                    + ") and dest (" + destLine.getID() + ")");
        }
        RouteSearchResult result = null;

        // start and end are equals but there is a path in between
        // so skip the start and proceed with the next line in the location
        if (startLine.getID() == destLine.getID() && location.size() > 1) {
            List<Line> route = new ArrayList<Line>(1);
            route.add(startLine);
            result = new RouteSearchResult(
                    RouteSearchReturnCode.INTERMEDIATE_FOUND, route, location
                    .get(1), 1);
        } else if (startLoopIndex == 0) {
            // there is a loop of a single line in the location
            List<Line> route = new ArrayList<Line>();
            route.add(startLine);
            result = new RouteSearchResult(RouteSearchReturnCode.INTERMEDIATE_FOUND, route, location.get(1), 1);
        } else {
            //we have to start a search
            // global data structures
            IntermediateHandler iHandler = new IntermediateHandler(location);
            RouteSearchData data = new RouteSearchData();

            // for the use of the heuristic
            Node e = destLine.getEndNode();
            destX = e.getLongitudeDeg();
            destY = e.getLatitudeDeg();

            // initialize the open list with the start element
            int startLength = startLine.getLineLength();
            PQElem startElem = new PQElem(startLine, 0/* HACK */, startLength,
                    null);
            data.addToOpen(startElem);
            // open.add(startElem);
            // bestValues.put(startLine.getID(), startLength);
            OpenLongHashSet locationIDs = new OpenLongHashSet(location.size());
            for (Line l : location) {
                locationIDs.put(l.getID());
            }

            // main loop
            while (!data.isOpenEmpty()) {
                // stop if route or intermediate is found or if no further line
                // is
                // available

                // get the top element of the open list
                PQElem actualElement = data.pollElement();

                // check the location being part of the shortest path tree and
                // if not change the state to INTERMEDIATE_FOUND
                if (locationIDs.containsKey(actualElement.getLine().getID())) {
                    result = iHandler.checkIntermediate(actualElement, data);
                    if (result != null) {
                        break;
                    }
                }

                // check if we have found the destination and if so then
                // construct the route and stop calculation
                if (actualElement.getLine().getID() == destLine.getID()) {
                    if (endLoopIndex >= 0) {
                        //route found until the end loop starts
                        //break here and add intermediate
                        List<Line> route = PathUtils
                                .constructPath(actualElement.getPrevious());
                        result = new RouteSearchResult(
                                RouteSearchReturnCode.INTERMEDIATE_FOUND,
                                route, destLine, endLoopIndex);
                        break;
                    } else {
                        List<Line> route = PathUtils
                                .constructPath(actualElement);
                        if (route.isEmpty()) {
                            // strange error
                            throw new OpenLREncoderProcessingException(
                                    EncoderProcessingError.ROUTE_CONSTRUCTION_ERROR);
                        } else {
                            // route found and constructed
                            result = new RouteSearchResult(
                                    RouteSearchResult.RouteSearchReturnCode.ROUTE_FOUND,
                                    route);
                            break;
                        }
                    }
                }

                //check for loops at start
                if (startLoopIndex > 0
                        && iHandler.getLastElemOnRoutePos() == startLoopIndex) {
                    // the start loop is completed and route search needs to be
                    // interrupted, add intermediate
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("loop is traversed, intermediate found");
                    }
                    List<Line> route = PathUtils.constructPath(actualElement.getPrevious());
                    result = new RouteSearchResult(
                            RouteSearchReturnCode.INTERMEDIATE_FOUND, route,
                            startLine, startLoopIndex);
                    break;
                }
                //route search can continue so
                // iterate over the successors, because we have not reached the
                // end
                extractNextLines(actualElement, data);
            }
            if (result == null) {
                //no route found
                result = new RouteSearchResult(RouteSearchReturnCode.NO_ROUTE_FOUND);
            }
        }
        return result;
    }

    /**
     * Extract the successor lines and add the lines to the open list if a
     * shorter distance to that line is found.
     *
     * @param actualElement the current element
     * @param data the data
     * @param lfrc the lfrc
     */
    private void extractNextLines(final PQElem actualElement,
                                  final RouteSearchData data) {
        Iterator<? extends Line> iterNext = actualElement.getLine()
                .getNextLines();
        // check all successors
        while (iterNext.hasNext()) {
            Line succ = iterNext.next();

            // calculate heuristic value, length, weighted length
            Node succEnd = succ.getEndNode();
            int heurist = (int) Math.round(GeometryUtils.distance(destX, destY,
                    succEnd.getLongitudeDeg(), succEnd.getLatitudeDeg()));

            int newDist = actualElement.getSecondVal() + succ.getLineLength();
            int newHeurVal = newDist + heurist;

            //Length filter
            if (newDist > lengthBetweenStartAndEndLineAlongLocation) {
                continue;
            }

            // check if we already have a value for this line
            if (data.hasLengthValue(succ)) {
                // check if we can improve the value
                if (newDist < data.getLengthValue(succ)) {
                    // improving the value means deleting the existing
                    // entry and creating a new one with the better
                    // values
                    PQElem newElem = new PQElem(succ, newHeurVal, newDist,
                            actualElement);
                    data.updateInOpen(newElem);
                }
            } else {
                // no value stored yet, so create a new one
                PQElem newElem = new PQElem(succ, newHeurVal, newDist,
                        actualElement);
                data.addToOpen(newElem);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" destination lon: ").append(destX);
        sb.append(" destination lat: ").append(destY);
        if (startLoopIndex > 0) {
            sb.append(" start loop found at: ").append(startLoopIndex);
        }
        if (endLoopIndex > 0) {
            sb.append(" end loop found at: ").append(endLoopIndex);
        }
        return sb.toString();
    }

}
