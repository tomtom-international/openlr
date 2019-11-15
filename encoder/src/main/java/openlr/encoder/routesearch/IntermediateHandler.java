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

import openlr.encoder.OpenLREncoderProcessingException;
import openlr.encoder.OpenLREncoderProcessingException.EncoderProcessingError;
import openlr.encoder.routesearch.RouteSearchResult.RouteSearchReturnCode;
import openlr.map.Line;
import openlr.map.utils.NodeCheck;
import openlr.map.utils.PQElem;
import openlr.map.utils.PathUtils;
import org.apache.log4j.Logger;
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
public class IntermediateHandler {

    /** The logger */
    private static final Logger LOG = Logger
            .getLogger(IntermediateHandler.class);
    /** The location. */
    private final List<? extends Line> location;
    /**
     * The last element in the location found so far (for tracking the location
     * in the shortest path tree).
     */
    private PQElem lastElemOnLocation;
    /** The position of the last element on location in the location */
    private int lastElemPos;

    /**
     * Instantiates a new intermediate handler.
     *
     * @param loc
     *            the loc
     */
    IntermediateHandler(final List<? extends Line> loc) {
        location = loc;
        lastElemOnLocation = null;
        lastElemPos = -1;
    }

    /**
     * Check for intermediates. The first location element should always be in
     * the tree, so nothing needs to be done. There is a pointer to the last
     * element found in the location so far so that the method checks if the
     * next line in the location is a direct successor of the last one found. If
     * not there is a deviation and a new intermediate needs to be reported. The
     * method will return true if a new intermediate was found, otherwise false.
     *
     * @param actualElement the actual element
     * @param data          the data
     * @return true, if a new intermediate was found, otherwise false
     * @throws OpenLREncoderProcessingException the open lr encoder runtime exception
     */
    public final RouteSearchResult checkIntermediate(
            final PQElem actualElement, final RouteSearchData data)
            throws OpenLREncoderProcessingException {
        RouteSearchResult result = null;
        //
        // The following cases are documented in <bla> version //TODO
        //
        if (lastElemOnLocation == null) { //CASE 0
            // the first line is found, nothing strange happens here
            lastElemOnLocation = actualElement;
            lastElemPos = 0;
            if (LOG.isDebugEnabled()) {
                LOG.debug("first line in sub route found: "
                        + actualElement.getLine().getID());
            }
        } else if (isNextElementInLocation(actualElement)) {//CASE 1
            // the new line is a direct successor of the last element found
            // so the new line is now the last element found, we can
            // continue calculating the route
            lastElemPos++;
            lastElemOnLocation = actualElement;
            if (LOG.isDebugEnabled()) {
                LOG.debug("next line in sub route found: "
                        + actualElement.getLine().getID());
            }
        } else {
            // a new intermediate needs to be added
            // find start of deviation along the current path
            // at least the start line should be found
            Line deviationStart = PathUtils.findCommonLineInPaths(location,
                    actualElement.getPrevious());
            if (deviationStart == null) {
                LOG.error("no start of deviation found");
                // should not happen, but you never know
                throw new OpenLREncoderProcessingException(
                        EncoderProcessingError.INTERMEDIATE_CALCULATION_FAILED);
            }

            // check if deviation starts at the last element found so far or
            // earlier in the path
            if (deviationStart.getID() == lastElemOnLocation.getLine().getID()) { // CASE 2
                // deviation starts at last element on location
                // take next element on location as intermediate
                Line intermediate = location.get(lastElemPos + 1);
                // find the priority queue element holding this intermediate
                // line
                // this should exist because the predecessor line (lastElem) has
                // already
                // been investigated and deviation cannot be the actual line as
                // the
                // deviation starts earlier in the location
                PQElem intermediateElement = (PQElem) PathUtils
                        .findElementInQueue(data.getOpenList(), intermediate);
                if (intermediateElement == null) {
                    // if not found, this is an error
                    LOG.error("intermediate not found in open list");
                    throw new OpenLREncoderProcessingException(
                            EncoderProcessingError.INTERMEDIATE_CALCULATION_FAILED);
                }

                // investigate the predecessor of the new intermediate in the
                // calculated path
                PQElem pIntermediate = intermediateElement.getPrevious();
                if (pIntermediate == null) {
                    // if not found, this is an error
                    LOG.error("no predecessor of intermediate element found");
                    throw new OpenLREncoderProcessingException(
                            EncoderProcessingError.INTERMEDIATE_CALCULATION_FAILED);
                }

                // construct path which has been covered so far
                List<Line> route = PathUtils.constructPath(lastElemOnLocation);

                // check if the shortest-path predecessor of the intermediate
                // line
                // is also the
                // predecessor in the location
                if (pIntermediate.getLine().getID() == lastElemOnLocation
                        .getLine().getID()) { // CASE 2a
                    // ok, only one intermediate to add
                    if (LOG.isDebugEnabled()) {
                        LOG
                                .debug("one intermediate found: one deviation at current element detected");
                    }
                    result = new RouteSearchResult(
                            RouteSearchReturnCode.INTERMEDIATE_FOUND, route,
                            intermediate, location.indexOf(intermediate));
                } else { // CASE 2b
                    // parent pointer is not the direct predecessor in the
                    // location
                    // in this case there are 2 deviations and a second
                    // intermediate is needed
                    // find a second intermediate with a valid start node (there
                    // should be one, as we have a deviation)
                    PQElem secondIntermediate = findValidIntermediateAlongCurrentPath();
                    if (secondIntermediate == null) {
                        // if not found, this is an error
                        LOG
                                .error("cannot determine second intermediate position");
                        throw new OpenLREncoderProcessingException(
                                EncoderProcessingError.INTERMEDIATE_CALCULATION_FAILED);
                    }
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("second intermediate found");
                    }
                    // add second intermediate
                    result = new RouteSearchResult(
                            RouteSearchReturnCode.INTERMEDIATE_FOUND, route,
                            intermediate, location.indexOf(intermediate),
                            secondIntermediate.getLine());
                }
            } else { // CASE 3
                // deviation starts earlier in the location
                // the intermediate shall have a valid start node, start
                // searching
                // for an intermediate at the
                // last element on location we found so far
                PQElem intermediateElem = findValidIntermediateAlongCurrentPath();
                if (intermediateElem == null) {
                    // if not found, this is an error
                    LOG.error("cannot determine intermediate for deviation");
                    throw new OpenLREncoderProcessingException(
                            EncoderProcessingError.INTERMEDIATE_CALCULATION_FAILED);
                } else {
                    if (LOG.isDebugEnabled()) {
                        LOG
                                .debug("one intermediate found: one deviation at previous element detected");
                    }
                    List<Line> route = PathUtils.constructPath(intermediateElem
                            .getPrevious());
                    result = new RouteSearchResult(
                            RouteSearchReturnCode.INTERMEDIATE_FOUND, route,
                            intermediateElem.getLine(), location
                            .indexOf(intermediateElem.getLine()));
                }
            }
        }
        return result;
    }

    /**
     * Checks if is next element in location.
     *
     * @param actualElement
     *            the actual element
     * @return true, if is next element in location
     */
    private boolean isNextElementInLocation(final PQElem actualElement) {
        long lastLocLineID = lastElemOnLocation.getLine().getID();
        long actPrevLineID = actualElement.getPrevious().getLine().getID();
        long nextLocLineID = location.get(lastElemPos + 1).getID();
        long actLineID = actualElement.getLine().getID();
        // check if actual previous equals the last line found in location
        boolean equalPrev = (lastLocLineID == actPrevLineID);
        // check if actual line comes next in location
        boolean nextInLocation = (nextLocLineID == actLineID);
        return (equalPrev && nextInLocation);
    }

    /**
     * Find an intermediate which has a valid start node. The method traverses
     * the path from the last element found in the location back to the start
     * and searches for a line having a valid start node.
     *
     * The first line matching this criterion will be reported. There should be
     * at least the start itself having a valid start node, but this will lead
     * to an infinite loop in the location reference. Ending at the start (no
     * other valid line in between) will end up in returning the last element
     * found so far. This is done to ensure that encoding will not fail due to a
     * missing valid node.
     *
     * @return the line
     */
    private PQElem findValidIntermediateAlongCurrentPath() {
        if (location.isEmpty() || lastElemOnLocation == null) {
            return null;
        }
        long startLineID = location.get(0).getID();
        boolean ready = false;
        PQElem currentElem = lastElemOnLocation;
        PQElem retValue = null;
        // we need to find a crossing for the LRP
        while (!ready) {
            if (currentElem.getLine().getID() == startLineID) {
                // we came back to the previous lrp
                // this means that we had a cycle in our path and
                // cannot
                // find a proper intersection
                // for placing the new intermediate
                // we choose the last element found in location
                // which is
                // unfortunately not placed at an intersection
                retValue = lastElemOnLocation;
                ready = true;
            } else if (NodeCheck.isValidNode(currentElem.getLine().getStartNode())) {
                ready = true;
                retValue = currentElem;
            } else {
                currentElem = currentElem.getPrevious();
                if (currentElem == null) {
                    // we reached the end of the path and nothing was found
                    // this is an error as we should have found at least the
                    // start line
                    ready = true;
                }
            }
        }
        return retValue;
    }

    /**
     * Gets the last elem on route pos.
     *
     * @return the last elem on route pos
     */
    public final int getLastElemOnRoutePos() {
        return lastElemPos;
    }

}
