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
package openlr.decoder.worker;

import openlr.LocationReferencePoint;
import openlr.LocationType;
import openlr.OpenLRProcessingException;
import openlr.decoder.DecoderReturnCode;
import openlr.decoder.data.*;
import openlr.decoder.properties.OpenLRDecoderProperties;
import openlr.decoder.rating.OpenLRRating;
import openlr.decoder.rating.OpenLRRatingImpl;
import openlr.decoder.routesearch.RouteSearch;
import openlr.location.Location;
import openlr.map.FunctionalRoadClass;
import openlr.map.Line;
import openlr.map.MapDatabase;
import openlr.map.Node;
import openlr.map.utils.GeometryUtils;
import openlr.rawLocRef.RawLocationReference;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The class OpenLRDecoderWorker provides the worker method for an OpenLR
 * decoder. The physical location reference data will be transformed into an
 * internal format and then the method tries to find candidate nodes and
 * candidate lines for each LRP. Afterwards it calculates the sub routes between
 * two subsequent LRPs and concatenates such sub routes. After pruning the path
 * the decoded location will be returned.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public abstract class AbstractDecoder {

    /** logger. */
    private static final Logger LOG = Logger.getLogger(AbstractDecoder.class);

    /** The rating function being used. */
    private static final OpenLRRating RATING_FUNCTION = new OpenLRRatingImpl();

    /**
     * This method decodes a location reference which has already been
     * transformed from a physical format into a structured (raw) data set. The
     * map being used for decoding is mdb and the process is configured by the
     * OpenLR properties.
     *
     * The method consists of the following steps (from top to bottom):
     * <ul>
     * <li>get location reference points
     * <li>find candidate nodes
     * <li>find candidate lines
     * <li>resolve shortest-paths(s)
     * <li>combine shortest-path(s)
     * <li>check shortest-path(s)
     * <li>prune path if offsets available
     * </ul>
     *
     * @param prop
     *            the properties
     * @param mdb
     *            the map database
     * @param rawLocRef
     *            the raw location reference
     * @return the decoded location
     * @throws OpenLRProcessingException
     *             if the decoding fails
     */
    public abstract Location doDecoding(final OpenLRDecoderProperties prop,
                                        final MapDatabase mdb, final RawLocationReference rawLocRef)
            throws OpenLRProcessingException;

    /**
     * Find candidate nodes for all location reference points. The OpenLR
     * properties configure the search for nodes being a possibility.
     *
     * @param properties
     *            the OpenLR properties
     * @param rawLocRef
     *            the raw loc ref
     * @param mdb
     *            the map database
     * @return the candidate nodes result set
     * @throws OpenLRProcessingException
     *             the open lr processing exception
     */
    protected final CandidateNodesResultSet findCandidateNodes(
            final OpenLRDecoderProperties properties,
            final RawLocationReference rawLocRef, final MapDatabase mdb)
            throws OpenLRProcessingException {
        // read maximum distance from the properties
        int maxDistance = properties.getMaxNodeDistance();
        List<? extends LocationReferencePoint> points = rawLocRef
                .getLocationReferencePoints();
        CandidateNodesResultSet resultSet = new CandidateNodesResultSet();
        int lrpCount = 0;
        // iterate over all LRP
        for (LocationReferencePoint p : points) {
            lrpCount++;
            List<NodeWithDistance> closeByNodes = new ArrayList<NodeWithDistance>();
            int minDistance = Integer.MAX_VALUE;
            // find the nodes close by by calling a map database function
            Iterator<? extends Node> nodes = mdb.findNodesCloseByCoordinate(
                    p.getLongitudeDeg(), p.getLatitudeDeg(), maxDistance);
            // check the node distances again
            while (nodes.hasNext()) {
                Node n = nodes.next();
                int distance = (int) Math.round(GeometryUtils.distance(
                        p.getLongitudeDeg(), p.getLatitudeDeg(),
                        n.getLongitudeDeg(), n.getLatitudeDeg()));
                if (distance < minDistance) {
                    minDistance = distance;
                }
                // if distance is still too big then continue
                if (distance <= maxDistance) {
                    // otherwise add this node to the CloseByNodes list
                    NodeWithDistance nwd = new NodeWithDistance(n, distance);
                    closeByNodes.add(nwd);
                }
            }
            resultSet.putCandidateNodes(p, closeByNodes);

            if (LOG.isDebugEnabled()) {
                if (closeByNodes.isEmpty()) {
                    LOG.warn("no candidate nodes found for node " + lrpCount
                            + "[investigated: " + p.getSequenceNumber()
                            + ", minDist: " + minDistance + "]");
                } else {
                    LOG.debug("candidate nodes for node " + lrpCount + " (lon:"
                            + p.getLongitudeDeg() + ", lat:"
                            + p.getLatitudeDeg() + "): " + closeByNodes.size()
                            + " - closest node distance: "
                            + resultSet.getClosestNode(p).getDistance());
                }
            }
        }
        return resultSet;
    }

    /**
     * Find candidate lines directly if no node or line has been detected so
     * far. This method tries to find all lines which are around the LRP
     * coordinate. The coordinate will be projected onto the line and the
     * distance between that projection point and the coordinate shall be small
     * (according to the encoder properties). All lines will be rated and
     * proposed as candidate lines for the LRP.
     *
     * @param properties
     *            the OpenLR encoder properties
     * @param lrp
     *            the location reference point (having no candidate lines so
     *            far)
     * @param mdb
     *            the map database
     * @param alreadyFound
     *            the already found
     * @return the list
     * @throws OpenLRProcessingException
     *             the open lr processing exception
     */
    private List<CandidateLine> findCandidateLinesDirectly(
            final OpenLRDecoderProperties properties,
            final LocationReferencePoint lrp, final MapDatabase mdb,
            final List<CandidateLine> alreadyFound)
            throws OpenLRProcessingException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("find candidate lines directly");
        }
        List<CandidateLine> candidates = new ArrayList<CandidateLine>();
        int maxDistance = properties.getMaxNodeDistance();
        Iterator<? extends Line> directLines = mdb.findLinesCloseByCoordinate(
                lrp.getLongitudeDeg(), lrp.getLatitudeDeg(), maxDistance);
        while (directLines.hasNext()) {
            Line line = directLines.next();
            int dist = line.distanceToPoint(lrp.getLongitudeDeg(),
                    lrp.getLatitudeDeg());
            // check distances
            if (dist > maxDistance) {
                continue;
            }
            FunctionalRoadClass frc = line.getFRC();

            if (lrp.getLfrc() != null
                    && frc.getID() > lrp.getLfrc().getID()
                    + properties.getFrcVariance()) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("line " + line.getID() + " ignored [low frc ("
                            + frc.getID() + ")]");
                }
                continue;
            }
            int lengthAlongDseg = line.measureAlongLine(lrp.getLongitudeDeg(),
                    lrp.getLatitudeDeg());
            int rating = RATING_FUNCTION.getRating(properties, dist, lrp, line,
                    lengthAlongDseg);
            if (!alreadyFound.isEmpty()) {
                float factor = properties.getLinesDirectlyFactor();
                rating = Math.round(factor * rating);
            }

            if (rating < properties.getMinimumAcceptedRating()) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("line " + line.getID() + " ignored [low rating ("
                            + rating + ")]");
                }
                continue;
            }
            CandidateLine rds = new CandidateLine(line, rating, lengthAlongDseg);
            boolean isNew = true;
            for (CandidateLine cl : alreadyFound) {
                if (rds.hasSameLine(cl)) {
                    isNew = false;
                    if (cl.getRating() < rating) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("new line rating has been found for "
                                    + cl.getLine().getID() + " [" + rating
                                    + "]");
                        }
                        cl.setNewRating(rating, lengthAlongDseg);
                    }
                    break;
                }
            }
            if (isNew) {
                candidates.add(rds);
            }
        }
        return candidates;
    }

    /**
     * Find candidate lines for each location reference point. The candidate
     * lines will be rated indicating how good they match the LRP attributes.
     * The method will be configured by OpenLR properties.
     *
     * The method investigates lines starting/ending at the nodes being
     * determined in the {@link #findCandidateNodes} method. This method needs
     * to be executed in advance!
     *
     * @param properties
     *            the OpenLR properties
     * @param rawLocRef
     *            the raw loc ref
     * @param candidateNodes
     *            the candidate nodes
     * @param mdb
     *            the map database
     * @return the candidate lines result set
     * @throws OpenLRProcessingException
     *             the open lr processing exception
     */
    public final CandidateLinesResultSet findCandidateLines(
            final OpenLRDecoderProperties properties,
            final RawLocationReference rawLocRef,
            final CandidateNodesResultSet candidateNodes, final MapDatabase mdb)
            throws OpenLRProcessingException {
        CandidateLinesResultSet resultSet = new CandidateLinesResultSet();
        List<? extends LocationReferencePoint> points = rawLocRef
                .getLocationReferencePoints();
        // iterate over all LRP
        for (LocationReferencePoint p : points) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("investigate lrp " + p.getSequenceNumber()
                        + " with: frc=" + p.getFRC() + " fow=" + p.getFOW()
                        + " bearing=" + p.getBearing() + "°");
            }
            List<CandidateLine> candidatesAtNodes = new ArrayList<CandidateLine>();
            // iterate over all nodes close by the LRP position
            for (NodeWithDistance nwd : candidateNodes.getCandidateNodes(p)) {
                // get all possible lines
                Iterator<? extends Line> linesIterator = nwd.getNode()
                        .getConnectedLines();

                while (linesIterator.hasNext()) {
                    Line line = linesIterator.next();
                    // check the current line
                    CandidateLine candidateLine = investigateline(properties,
                            line, p, nwd);
                    if (candidateLine.isValid()) {
                        // if the line is valid and rated add it as candidate
                        // line
                        candidatesAtNodes.add(candidateLine);
                    }
                }
            }

            // also look for candidate lines directly (not starting from /
            // ending at a node)
            List<CandidateLine> candidatesDirectly = findCandidateLinesDirectly(
                    properties, p, mdb, candidatesAtNodes);

            // merge the candidates
            List<CandidateLine> candidates = new ArrayList<CandidateLine>();
            candidates.addAll(candidatesDirectly);
            candidates.addAll(candidatesAtNodes);
            resultSet.putCandidateLines(p, candidates);
            // check if still no lines found
            if (candidates.isEmpty()) {
                LOG.error("no candidate lines found for lrp "
                        + p.getSequenceNumber());
                return resultSet;
            } else if (LOG.isDebugEnabled()) {
                LOG.debug(candidates.size() + " lines found for lrp "
                        + p.getSequenceNumber() + "  [best rate: "
                        + resultSet.getBestCandidateLine(p).getRating() + "]");
            }
        }
        if (LOG.isDebugEnabled()) {
            for (LocationReferencePoint p : points) {
                LOG.debug("Candidate lines for LRP " + p.getSequenceNumber());
                LOG.debug(resultSet.toDebug(p));
            }
        }
        return resultSet;
    }

    /**
     * Investigates and rates a line. The rating value indicates how good the
     * lines matches the LRP attributes. If the line does not match at all (due
     * to invalid direction or a worse rating value) the method will return
     * null.
     *
     * @param properties
     *            the OpenLR properties
     * @param line
     *            the line being investigated
     * @param p
     *            the location reference point
     * @param nwd
     *            the candidate node
     * @return the rated candidate line or null, if the line is not valid
     * @throws OpenLRProcessingException
     *             the open lr processing exception
     */
    private CandidateLine investigateline(
            final OpenLRDecoderProperties properties, final Line line,
            final LocationReferencePoint p, final NodeWithDistance nwd)
            throws OpenLRProcessingException {
        if (line == null || p == null || nwd == null) {
            throw new java.lang.IllegalArgumentException();
        }

        // check the line direction
        // only outgoing lines are accepted for the LRPs, except the last LRP
        // where only incoming lines are accepted
        Node refNode = null;
        if (p.isLastLRP()) {
            refNode = line.getEndNode();
        } else {
            refNode = line.getStartNode();
        }
        if (!refNode.equals(nwd.getNode())) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("line " + line.getID() + " ignored [wrong direction]");
            }
            return CandidateLine.INVALID;
        }

        // check the functional road class value
        FunctionalRoadClass frc = line.getFRC();
        if (!p.isLastLRP()
                && frc.getID() > p.getLfrc().getID()
                + properties.getFrcVariance()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("line " + line.getID() + " ignored [low frc (" + frc.getID() + ")]");
            }
            return CandidateLine.INVALID;
        }

        // rate the line
        int rating = RATING_FUNCTION.getRating(properties, nwd.getDistance(),
                p, line, 0);

        // check if the rating value fulfills the minimum criteria
        if (rating < properties.getMinimumAcceptedRating()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("line " + line.getID() + " ignored [low rating ("
                        + rating + ")]");
            }
            return CandidateLine.INVALID;
        }

        // ok, the line passed the test!
        CandidateLine cLine = new CandidateLine(line, rating);
        return cLine;
    }

    /**
     * Resolves the shortest-paths between each subsequent pair of location
     * reference points. The method orders the candidate line pairs for two
     * subsequent LRPs and starts with the best rated pair to calculate a
     * shortest-path in between. The method further checks the minimum and
     * maximum distance criteria for the calculated shortest-path. If one of the
     * criteria is not fulfilled the methods tries the next best candidate line
     * pair. If no further pair is available the method fails. For each
     * subsequent pair of LRPs the start LRP will hold the calculated route
     * after finishing this method.
     *
     * @param properties
     *            the OpenLR properties
     * @param rawLocRef
     *            the raw loc ref
     * @param candidateLines
     *            the candidate lines
     * @param locType
     *            the loc type
     * @return the decoder return code
     * @throws OpenLRProcessingException
     *             the open lr processing exception
     */
    public final ResolvedRoutes resolveRoute(
            final OpenLRDecoderProperties properties,
            final RawLocationReference rawLocRef,
            final CandidateLinesResultSet candidateLines,
            final LocationType locType) throws OpenLRProcessingException {
        ResolvedRoutes resolvedRoutes = new ResolvedRoutes();

        List<? extends LocationReferencePoint> points = rawLocRef
                .getLocationReferencePoints();
        Line singleLine = checkForSingleLineOnly(candidateLines, points);
        int nrLRP = points.size();
        if (singleLine != null) {
            boolean first = true;
            for (int i = 0; i < nrLRP - 1; ++i) {
                LocationReferencePoint lrp = points.get(i);
                LocationReferencePoint lrpNext = points.get(i + 1);
                List<Line> resolvedPath = new ArrayList<Line>();
                if (first) {
                    resolvedPath.add(singleLine);
                    first = false;
                }
                resolvedRoutes.putRoute(lrp, resolvedPath,
                        candidateLines.getBestCandidateLine(lrp),
                        candidateLines.getBestCandidateLine(lrpNext));
            }
        } else {
            // iterate over all LRP pairs
            for (int i = 0; i < nrLRP - 1; ++i) {
                LocationReferencePoint lrpPrev = null;
                if (i > 0) {
                    lrpPrev = points.get(i - 1);
                }
                LocationReferencePoint lrp = points.get(i);
                LocationReferencePoint lrpNext = points.get(i + 1);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("investigate point " + lrp.getSequenceNumber()
                            + " with expected distance: "
                            + lrp.getDistanceToNext() + "m");
                }
                // determine the minimum frc for the path to be calculated
                int lfrc = lrp.getLfrc().getID() + properties.getFrcVariance();
                CandidateLine previousEndCandidate = null;
                if (lrpPrev != null) {
                    previousEndCandidate = resolvedRoutes
                            .getCandidateEnd(lrpPrev);
                }
                List<CandidateLinePair> lrpPairs = DecoderUtils
                        .resolveCandidatesOrder(lrp, lrpNext, candidateLines,
                                previousEndCandidate, properties, locType);
                boolean routeSearchFinished = false;
                for (CandidateLinePair currentPair : lrpPairs) {
                    CandidateLine startCandidate = candidateLines
                            .getCandidateLineAtIndex(lrp,
                                    currentPair.getStartIndex());
                    CandidateLine destCandidate = candidateLines
                            .getCandidateLineAtIndex(lrpNext,
                                    currentPair.getDestIndex());
                    routeSearchFinished = checkCandidatePair(startCandidate,
                            destCandidate, properties, resolvedRoutes, lfrc,
                            lrpPrev, lrp, lrpNext, previousEndCandidate);
                    if (routeSearchFinished) {
                        break;
                    }
                }
                if (!routeSearchFinished) {
                    LOG.error("cannot determine a route between lrp "
                            + lrp.getSequenceNumber() + " and "
                            + lrpNext.getSequenceNumber());
                    resolvedRoutes.setError(DecoderReturnCode.NO_ROUTE_FOUND);
                    return resolvedRoutes;
                }
                if (resolvedRoutes.hasErrorCode()) {
                    return resolvedRoutes;
                }
            }
        }
        resolvedRoutes.setAllResolved();
        return resolvedRoutes;
    }

    /**
     * Check for single line coverage only. If no single line is covered the
     * method will return null;
     *
     * @param candidateLines
     *            the candidate lines
     * @param points
     *            the points
     * @return the single line or null
     */
    private Line checkForSingleLineOnly(
            final CandidateLinesResultSet candidateLines,
            final List<? extends LocationReferencePoint> points) {
        Line singleLine = null;
        for (LocationReferencePoint lrp : points) {
            CandidateLine bestLine = candidateLines.getBestCandidateLine(lrp);
            if (singleLine == null) {
                singleLine = bestLine.getLine();
            } else if (singleLine.getID() != bestLine.getLine().getID()) {
                return null;
            }
        }
        return singleLine;
    }

    /**
     * Check candidate pair.
     *
     * @param startCandidate
     *            the start candidate
     * @param destCandidate
     *            the dest candidate
     * @param properties
     *            the properties
     * @param resolvedRoutes
     *            the resolved routes
     * @param lfrc
     *            the lfrc
     * @param lrpPrev
     *            the lrp prev
     * @param lrp
     *            the lrp
     * @param lrpNext
     *            the lrp next
     * @param previousEndCandidate
     *            the previous end candidate
     * @return true, if successful
     * @throws OpenLRProcessingException
     *             the open lr processing exception
     */
    private boolean checkCandidatePair(final CandidateLine startCandidate,
                                       final CandidateLine destCandidate,
                                       final OpenLRDecoderProperties properties,
                                       final ResolvedRoutes resolvedRoutes, final int lfrc,
                                       final LocationReferencePoint lrpPrev,
                                       final LocationReferencePoint lrp,
                                       final LocationReferencePoint lrpNext,
                                       final CandidateLine previousEndCandidate)
            throws OpenLRProcessingException {
        RouteSearch rsearch = new RouteSearch();
        Line startLine = startCandidate.getLine();
        Line destLine = destCandidate.getLine();
        if (LOG.isDebugEnabled()) {
            LOG.debug("test candidate pair: start-" + startLine.getID()
                    + " - dest-" + destLine.getID());
        }
        if (startLine.getID() == destLine.getID()) {
            handleSameStartEnd(resolvedRoutes, lrp, lrpNext, startCandidate,
                    destCandidate);
            return true;
        }
        int maxDistance = DecoderUtils.calculateMaxLength(lrp, startCandidate,
                destCandidate, properties);
        // calculate route between start and end and a maximum distance
        RouteSearch.RouteSearchResult result = rsearch.calculateRoute(
                startLine, destLine, maxDistance, lfrc, lrpNext.isLastLRP());
        return handleRouteSearchResult(properties, resolvedRoutes, rsearch,
                lrpPrev, lrp, previousEndCandidate, startCandidate,
                destCandidate, result);
    }

    /**
     * Handle route search result.
     *
     * @param properties
     *            the properties
     * @param resolvedRoutes
     *            the resolved routes
     * @param rsearch
     *            the rsearch
     * @param lrpPrev
     *            the lrp prev
     * @param lrp
     *            the lrp
     * @param previousEndCandidate
     *            the previous end candidate
     * @param startCandidate
     *            the start candidate
     * @param destCandidate
     *            the dest candidate
     * @param result
     *            the result
     * @return true, if successful
     * @throws OpenLRProcessingException
     *             the open lr processing exception
     */
    private boolean handleRouteSearchResult(
            final OpenLRDecoderProperties properties,
            final ResolvedRoutes resolvedRoutes, final RouteSearch rsearch,
            final LocationReferencePoint lrpPrev,
            final LocationReferencePoint lrp,
            final CandidateLine previousEndCandidate,
            final CandidateLine startCandidate,
            final CandidateLine destCandidate,
            final RouteSearch.RouteSearchResult result)
            throws OpenLRProcessingException {
        boolean finished = false;
        // check the route search result
        if (result == RouteSearch.RouteSearchResult.NO_ROUTE_FOUND) {
            // no route was found, so fail or have a second try
            if (LOG.isDebugEnabled()) {
                LOG.debug("no route found");
            }
        } else if (result == RouteSearch.RouteSearchResult.ROUTE_CONSTRUCTION_FAILED) {
            // the route cannot be constructed correctly, so fail or
            // have a second try
            if (LOG.isDebugEnabled()) {
                LOG.debug("route construction error");
            }
        } else if (result == RouteSearch.RouteSearchResult.ROUTE_FOUND) {
            // route was found!
            int rLength = DecoderUtils.determineRouteLength(rsearch,
                    destCandidate);
            if (LOG.isDebugEnabled()) {
                LOG.debug("  route found with length: " + rLength + "m");
            }
            // check the minimum distance criteria
            if (DecoderUtils.getMinDistanceNP(lrp, properties) <= rLength) {
                boolean retCode = handleValidRoute(properties, resolvedRoutes,
                        rsearch, lrpPrev, lrp, previousEndCandidate,
                        startCandidate, destCandidate);
                if (!retCode) {
                    resolvedRoutes
                            .setError(DecoderReturnCode.NO_ALTERNATIVE_FOUND);
                }
                finished = true;
            } else {
                // minimum length criteria failed, so have a second try
                if (LOG.isDebugEnabled()) {
                    LOG.debug("minimum route length error");
                    LOG.debug("  route length should be at least "
                            + DecoderUtils.getMinDistanceNP(lrp, properties));
                }
                // store current start/dest pair (which failed!!)
                resolvedRoutes.putRoute(lrp, rsearch.getCalculatedRoute(),
                        startCandidate, destCandidate);
            }
        }
        return finished;
    }

    /**
     * Handle same start end.
     *
     * @param resolvedRoutes
     *            the resolved routes
     * @param lrp
     *            the lrp
     * @param lrpNext
     *            the lrp next
     * @param startCandidate
     *            the start candidate
     * @param destCandidate
     *            the dest candidate
     */
    private void handleSameStartEnd(final ResolvedRoutes resolvedRoutes,
                                    final LocationReferencePoint lrp,
                                    final LocationReferencePoint lrpNext,
                                    final CandidateLine startCandidate,
                                    final CandidateLine destCandidate) {
        if (lrpNext.isLastLRP()) {
            ArrayList<Line> path = new ArrayList<Line>(1);
            path.add(startCandidate.getLine());
            resolvedRoutes.putRoute(lrp, path, startCandidate, destCandidate);
        } else {
            resolvedRoutes.putRoute(lrp, new ArrayList<Line>(), startCandidate,
                    destCandidate);
        }
    }

    /**
     * Handle valid route.
     *
     * @param properties
     *            the properties
     * @param resolvedRoutes
     *            the resolved routes
     * @param rsearch
     *            the rsearch
     * @param lrpPrev
     *            the lrp prev
     * @param lrp
     *            the lrp
     * @param previousEndCandidate
     *            the previous end candidate
     * @param startCandidate
     *            the start candidate
     * @param destCandidate
     *            the dest candidate
     * @return the decoder return code
     * @throws OpenLRProcessingException
     *             the open lr processing exception
     */
    private boolean handleValidRoute(final OpenLRDecoderProperties properties,
                                     final ResolvedRoutes resolvedRoutes, final RouteSearch rsearch,
                                     final LocationReferencePoint lrpPrev,
                                     final LocationReferencePoint lrp,
                                     final CandidateLine previousEndCandidate,
                                     final CandidateLine startCandidate,
                                     final CandidateLine destCandidate) throws OpenLRProcessingException {
        if (previousEndCandidate != null
                && !startCandidate.hasSameLine(previousEndCandidate)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("start index has changed, need to redo previous route!!");
            }
            boolean retCode = handleStartLineChange(startCandidate, lrpPrev,
                    lrp, resolvedRoutes, properties);
            if (!retCode) {
                return false;
            }
        }
        // passed, set route found and store the line index
        // being used
        resolvedRoutes.putRoute(lrp, rsearch.getCalculatedRoute(),
                startCandidate, destCandidate);
        if (LOG.isDebugEnabled()) {
            LOG.debug("route found");
        }
        return true;
    }

    /**
     * Handle start line change.
     *
     * @param newCandidate
     *            the new candidate
     * @param lrpPrev
     *            the lrp prev
     * @param lrp
     *            the lrp
     * @param resolvedRoutes
     *            the resolved routes
     * @param properties
     *            the properties
     * @return the decoder return code
     * @throws OpenLRProcessingException
     *             the open lr processing exception
     */
    private boolean handleStartLineChange(final CandidateLine newCandidate,
                                          final LocationReferencePoint lrpPrev,
                                          final LocationReferencePoint lrp,
                                          final ResolvedRoutes resolvedRoutes,
                                          final OpenLRDecoderProperties properties)
            throws OpenLRProcessingException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("start index has changed, need to redo previous route!!");
        }
        CandidateLine ppreviousCandidate = resolvedRoutes
                .getCandidateStart(lrpPrev);
        Line newStart = ppreviousCandidate.getLine();
        RouteSearch rsearchInner = new RouteSearch();
        int maxdistanceInner = DecoderUtils.calculateMaxLength(lrpPrev,
                ppreviousCandidate, newCandidate, properties);
        RouteSearch.RouteSearchResult resultRedo = rsearchInner.calculateRoute(
                newStart, newCandidate.getLine(), maxdistanceInner, lrpPrev
                        .getLfrc().getID() + properties.getFrcVariance(),
                lrp.isLastLRP());
        if (resultRedo == RouteSearch.RouteSearchResult.ROUTE_FOUND
                && DecoderUtils.getMinDistanceNP(lrpPrev, properties) <= rsearchInner
                .getRouteLength()) {
            resolvedRoutes.putRoute(lrpPrev, rsearchInner.getCalculatedRoute(),
                    ppreviousCandidate, newCandidate);
            if (LOG.isDebugEnabled()) {
                LOG.debug("new route found for previous lrp");
            }
            return true;
        } else {
            LOG.error("cannot find a proper route after start index change!");
            return false;
        }
    }

}
