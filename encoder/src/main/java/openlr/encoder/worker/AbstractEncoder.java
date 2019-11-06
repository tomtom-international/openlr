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
package openlr.encoder.worker;

import openlr.OpenLRProcessingException;
import openlr.encoder.LrpHandler.AlternatePathLrpHandler;
import openlr.encoder.OpenLREncoderProcessingException;
import openlr.encoder.OpenLREncoderProcessingException.EncoderProcessingError;
import openlr.encoder.data.ExpansionHelper;
import openlr.encoder.data.LocRefData;
import openlr.encoder.data.LocRefPoint;
import openlr.encoder.properties.OpenLREncoderProperties;
import openlr.encoder.routesearch.RouteSearch;
import openlr.encoder.routesearch.RouteSearchResult;
import openlr.location.Location;
import openlr.map.FunctionalRoadClass;
import openlr.map.FunctionalRoadClass.FrcComparator;
import openlr.map.GeoCoordinates;
import openlr.map.Line;
import openlr.map.MapDatabase;
import openlr.map.utils.NodeCheck;
import openlr.rawLocRef.RawLocationReference;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class holds the main OpenLR encoder method and all relevant methods
 * which needs to be run through for the generation of an OpenLR location
 * reference. At least one physical encoder is needed to run the process. All
 * physical encoders are used to produce a physical location reference format.
 * If encoding failed an OpenLRRuntimeException will be thrown. If a location
 * cannot be encoded these exceptions will be caught and wrapped in the returned
 * location reference.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public abstract class AbstractEncoder {

    /** The Constant logger. */
    private static final Logger LOG = Logger.getLogger(AbstractEncoder.class);

    /** The Constant FRC_COMPARATOR. */
    private static final FrcComparator FRC_COMPARATOR = new FunctionalRoadClass.FrcComparator();

    /**
     * Do OpenLR encoding. This method checks the validity of the location and
     * encodes it. It also handles the the check whether a location has already
     * been encoded. The method returns a location reference which includes
     * either all physical representations of the location reference or an error
     * code if an error occurred during encoding.
     *
     * @param location the location
     * @param properties the properties
     * @param map the map
     * @return the location reference holder
     * @throws OpenLRProcessingException the open lr processing exception
     */
    public abstract RawLocationReference doEncoding(final Location location,
                                                    final OpenLREncoderProperties properties, final MapDatabase map) throws OpenLRProcessingException;

    /**
     * Generates a list of location reference points. This list of LRP is the
     * location reference of the argument location. The method tries to cover
     * the location by shortest-path(s). It starts a shortest-path calculation
     * and handles the adding of additional intermediate location reference
     * points.
     *
     * @param locRefData the loc ref data
     * @param properties the properties
     * @return the location reference as a list of LRP
     * @throws OpenLRProcessingException the open lr processing exception
     */
    protected final List<LocRefPoint> generateLocRef(
            final LocRefData locRefData, final OpenLREncoderProperties properties)
            throws OpenLRProcessingException {
        // initialize data structures
        List<LocRefPoint> locRefPoints = new ArrayList<LocRefPoint>();
        // start the coverage calculation with the whole location
        List<Line> remainingLocation = new ArrayList<Line>(
                ExpansionHelper.getExpandedLocation(locRefData));
        Line finalDest = remainingLocation.get(remainingLocation.size() - 1);

        int maxLength = properties.getMaximumDistanceLRP();

        AlternatePathLrpHandler alternatePathHandler = AlternatePathLrpHandler.with(properties);

        // find shortest-path(s) until the whole location is covered by a
        // concatenation of these shortest-path(s)
        while (!remainingLocation.isEmpty()) {

            RouteSearch rs = new RouteSearch(remainingLocation);
            // do route search between current start and end of location
            RouteSearchResult rsResult = rs.calculateRoute();
            RouteSearchResult.RouteSearchReturnCode searchResult = rsResult
                    .getResult();

            // handle the route search result
            switch (searchResult) {
                case INTERMEDIATE_FOUND:
                    // intermediate found, location will be split and new route
                    // search started for the remaining part
                    List<LocRefPoint> newPoints = handleIntermediateFound(
                            remainingLocation, rsResult, properties);
                    locRefPoints.addAll(newPoints);
                    // trim remaining location
                    int pos = rsResult.getIntermediatePos();
                    remainingLocation = remainingLocation.subList(pos,
                            remainingLocation.size());
                    break;
                case NO_ROUTE_FOUND:
                    // error
                    throw createRuntimeException(locRefData.getID(),
                            EncoderProcessingError.NO_ROUTE_FOUND_ERROR);
                case ROUTE_FOUND:
                    // route search ended at the end of the location, complete
                    // coverage calculated
                    // handle expansion and last LRP and then return
                    LocRefPoint newLRP = new LocRefPoint(remainingLocation,
                            properties);
                    locRefPoints.add(newLRP);
                    remainingLocation = remainingLocation.subList(
                            remainingLocation.size(), remainingLocation.size());
                    break;
                default:
                    break;
            }
        }

        // add last LRP
        LocRefPoint lastLRP = new LocRefPoint(finalDest, properties);
        locRefPoints.add(lastLRP);

        List<LocRefPoint> checkedList = new ArrayList<LocRefPoint>();
        // create a linked list of LRP
        for (int i = 0; i < locRefPoints.size() - 1; i++) {
            LocRefPoint lrp = locRefPoints.get(i);
            if (lrp.getDistanceToNext() > maxLength) {
                // limit is exceeded and we need to add additional points
                if (LOG.isDebugEnabled()) {
                    LOG.debug("maximum distance between two LRP is exceeded (distance is: "
                            + lrp.getDistanceToNext() + ")");
                }
                List<Line> oldRoute = lrp.getRoute();
                // determine new intermediates
                List<LocRefPoint> newLRPs = determineNewIntermediates(
                        properties, oldRoute);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("added " + (newLRPs.size() - 1));
                }
                checkedList.addAll(newLRPs);
            } else {
                checkedList.add(lrp);
            }
        }

        checkedList.add(locRefPoints.get(locRefPoints.size() - 1));
        checkedList = alternatePathHandler.process(checkedList);
        int lrpCount = 1;
        for (int i = 0; i < checkedList.size() - 1; i++) {
            LocRefPoint lrp = checkedList.get(i);
            lrp.setNextLRP(checkedList.get(i + 1));
            lrp.setSequenceNumber(lrpCount);
            lrpCount++;
        }
        checkedList.get(checkedList.size() - 1).setSequenceNumber(lrpCount);
        return checkedList;
    }

    /**
     * Determines new intermediate LRP if the maximum distance between two LRPs
     * was exceeded. The method divides the original path into several subpaths
     * each having a length less than the maximum distance. It returns an
     * ordered list of new location reference points. If a single line exceeds
     * the maximum length the method will add a LRP directly on the line.
     *
     * @param properties
     *            the OpenLR properties
     * @param oldRoute
     *            the old route (exceeding the maximum length)
     * @return the number of intermediates added to the list
     * @throws OpenLRProcessingException
     *             the open lr processing exception
     */
    private List<LocRefPoint> determineNewIntermediates(
            final OpenLREncoderProperties properties, final List<Line> oldRoute)
            throws OpenLRProcessingException {
        if (oldRoute.isEmpty()) {
            throw new IllegalArgumentException("old route is empty");
        }
        boolean finished = false;
        List<Line> subRoute = new ArrayList<Line>();
        int subDist = 0;
        int oldRouteSize = oldRoute.size();
        int oldRoutePos = 0;
        List<LocRefPoint> newLRPs = new ArrayList<LocRefPoint>();
        int maxDist = properties.getMaximumDistanceLRP();
        // iterate over the route between these two LR-points and
        // collect all lines until the
        // maximum distance value is reached. mark this as an additional lrp
        // and check if this new
        // LR-point refers to an avoidable node, if yes, try to shift
        // the LR-point again.
        // then add the new intermediate lrp, adjust the original one,
        // and go on
        while (!finished) {
            // collect data about the new partial route
            Line next = oldRoute.get(oldRoutePos);
            oldRoutePos++;
            int lineLength = next.getLineLength();
            // check if we have reached the maximum length or the end of the old
            // route
            if (subDist + lineLength > maxDist || oldRoutePos >= oldRouteSize) {
                if (lineLength > maxDist) {
                    // extremely long line needs to be split up
                    // special case: these lines are referenced by one LRP at
                    // start node
                    // several internal LRPs referencing the line directly
                    // and one LRP after this long line
                    if (subDist > 0 && !subRoute.isEmpty()) {
                        // create LRP for the path already being covered
                        // except if there is no sub path
                        LocRefPoint firstLRP = new LocRefPoint(subRoute,
                                properties);
                        newLRPs.add(firstLRP);
                        // start new sub-route
                        subRoute = new ArrayList<Line>();
                        subDist = 0;
                    }
                    // eat up line length as long the remaining length exceeds
                    // the maximum distance value
                    int consumedLength = 0;
                    int counter = 0;
                    List<Line> path = new ArrayList<Line>(1);
                    path.add(next);
                    LocRefPoint startLRP = new LocRefPoint(path, properties);
                    newLRPs.add(startLRP);
                    while (lineLength - consumedLength > maxDist) {
                        counter++;
                        GeoCoordinates point = next.getGeoCoordinateAlongLine(counter
                                * maxDist);
                        LocRefPoint newIntermediate = new LocRefPoint(next,
                                point.getLongitudeDeg(), point.getLatitudeDeg(), properties, false);
                        newLRPs.add(newIntermediate);
                        consumedLength += maxDist;
                    }
                    // check if we are already finished with this subroute
                    if (oldRoutePos >= oldRouteSize) {
                        finished = true;
                    } else {
                        // store remaining length
                        subDist = lineLength - consumedLength;
                    }
                } else {
                    // line length is ok, but there must be an additional LRP
                    if (subDist + lineLength <= maxDist
                            && oldRoutePos == oldRouteSize) {
                        subRoute.add(next);
                        finished = true;
                    } else {
                        // check valid/invalid status of the new intermediate
                        if (NodeCheck.isValidNode(subRoute.get(
                                subRoute.size() - 1).getEndNode())) {
                            oldRoutePos--;
                        } else {
                            // oldRoutePos--;
                            oldRoutePos = handleInvalidIntermediateNode(
                                    oldRoutePos, subRoute);
                        }
                    }
                    // add a new location reference data (LR-point) to
                    // the list
                    LocRefPoint newIntermediate = new LocRefPoint(subRoute,
                            properties);
                    // put it in the right place
                    newLRPs.add(newIntermediate);

                    if (!finished) {
                        // start new sub-route
                        subRoute = new ArrayList<Line>();
                        subDist = 0;
                    }
                }
            } else {
                // add line to current subroute and continue
                subDist += lineLength;
                subRoute.add(next);
            }
        }
        // return the new intermediates substituting the "old" LRP
        return newLRPs;
    }

    /**
     * Handle invalid intermediate node and try to find a valid node along the
     * subroute, starting from the end of the subroute. It returns the new
     * position in the old Route and adjusts the subroute respectively.
     *
     * @param routePos
     *            the old route position
     * @param subRoute
     *            the subroute
     *
     * @return the new position pointer in the old route
     */
    private int handleInvalidIntermediateNode(final int routePos,
                                              final List<Line> subRoute) {
        // subroute does not end at a valid node
        // try to find a valid node in the subroute, starting from
        // its end
        int oldRoutePos = routePos;
        int validNodePos = checkBackwardForValidNode(subRoute) + 1;
        if (validNodePos > 0) {
            // valid node found
            int resetCounterBy = subRoute.size() - validNodePos + 1;
            List<Line> unusedLines = new ArrayList<Line>(resetCounterBy);
            unusedLines.addAll(subRoute.subList(validNodePos, subRoute.size()));
            subRoute.removeAll(unusedLines);
            oldRoutePos -= resetCounterBy;

        } else {
            oldRoutePos--;
        }
        // else, no valid node found
        // take at least exceptionally the invalid node
        return oldRoutePos;
    }

    /**
     * Checks if a route contains a valid node, starting at the end of the route
     * and stepping backwards. It returns the position of the line having a
     * valid end node. If no valid node can be found, the method returns -1.
     *
     * @param route
     *            a route
     *
     * @return the position of the line having a valid end node or -1 if no
     *         valid node can be found
     */
    private int checkBackwardForValidNode(final List<Line> route) {
        int pos = route.size() - 1;
        while (!NodeCheck.isValidNode(route.get(pos).getEndNode())) {
            pos--;
            if (pos < 0) {
                break;
            }
        }
        return pos;
    }

    /**
     * Calc lfrc.
     *
     * @param remainingLocation
     *            the remaining location
     * @return the functional road class
     */
    private FunctionalRoadClass calcLFRC(final List<Line> remainingLocation) {
        FunctionalRoadClass lfrc = FunctionalRoadClass.getHighestFrc();
        Iterator<Line> iter = remainingLocation.iterator();
        while (iter.hasNext()) {
            Line next = iter.next();
            if (FRC_COMPARATOR.compare(next.getFRC(), lfrc) < 0) {
                lfrc = next.getFRC();
            }
        }
        return lfrc.lower().lower();
    }

    /**
     * Creates an encoding exception for ID id and error type t.
     *
     * @param id
     *            the location id
     * @param t
     *            the error type
     *
     * @return the encoding exception
     */
    private OpenLREncoderProcessingException createRuntimeException(
            final String id, final EncoderProcessingError t) {
        LOG.error("Encoding error (id:" + id + ") -- " + t.toString());
        return new OpenLREncoderProcessingException(t);
    }

    /**
     * Handle route search result: INTERMEDIATE_FOUND. <br>
     * <br>
     * This method adds a new intermediate LRP to the list of LRP and if a
     * second intermediate was found by the route search algorithm, then the
     * previously found intermediate will be adjusted and two new intermediate
     * LRPs are added. It returns the remaining part of the location which still
     * needs to be covered.
     *
     * @param currentLocation
     *            the current part of location
     * @param rd
     *            the result of the route search.
     * @param properties
     *            the properties
     * @return the remaining part of the location
     * @throws OpenLRProcessingException
     *             the open lr processing exception
     */
    private List<LocRefPoint> handleIntermediateFound(
            final List<Line> currentLocation, final RouteSearchResult rd,
            final OpenLREncoderProperties properties) throws OpenLRProcessingException {
        List<LocRefPoint> locRefPoints = new ArrayList<LocRefPoint>();
        // the calculated route does not cover the location and a new
        // intermediate was reported
        List<Line> theRoute = rd.getRoute();
        if (rd.hasSecondIntermediate()) {
            Line secondLRPline = rd.getSecondIntermediate(); // the new
            // starting line
            int pos = currentLocation.indexOf(secondLRPline);
            if (pos < 0) {
                throw new OpenLREncoderProcessingException(
                        EncoderProcessingError.INTERMEDIATE_CALCULATION_FAILED);
                // this should never happen!!
            }
            // split the lastly calculated shortest-path into two shortest-paths
            List<Line> newRoute1 = new ArrayList<Line>(theRoute.subList(0,
                    pos));
            List<Line> newRoute2 = new ArrayList<Line>(theRoute.subList(pos,
                    theRoute.size()));

            // create path data for new route
            LocRefPoint newLRP1 = new LocRefPoint(newRoute1, properties);
            LocRefPoint newLRP2 = new LocRefPoint(newRoute2, properties);
            locRefPoints.add(newLRP1);
            locRefPoints.add(newLRP2);
        } else {
            // only one intermediate, so add this LRP
            LocRefPoint newLRP = new LocRefPoint(theRoute, properties);
            locRefPoints.add(newLRP);
        }
        return locRefPoints;
    }
}
