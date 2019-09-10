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
 * Copyright (C) 2009-2012 TomTom International B.V.
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
 *  Copyright (C) 2009-2012 TomTom International B.V.
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
import openlr.Offsets;
import openlr.OpenLRProcessingException;
import openlr.decoder.DecoderReturnCode;
import openlr.decoder.OpenLRDecoderProcessingException;
import openlr.decoder.OpenLRDecoderProcessingException.DecoderProcessingError;
import openlr.decoder.data.CandidateLine;
import openlr.decoder.data.CandidateLinesResultSet;
import openlr.decoder.data.CandidateNodesResultSet;
import openlr.decoder.data.ResolvedRoutes;
import openlr.decoder.location.DecodedLineLocation;
import openlr.decoder.properties.OpenLRDecoderProperties;
import openlr.location.InvalidLocation;
import openlr.location.Location;
import openlr.map.Line;
import openlr.map.MapDatabase;
import openlr.map.utils.PathUtils;
import openlr.rawLocRef.RawLocationReference;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * The line decoder.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class LineDecoder extends AbstractDecoder {

    /** logger. */
    private static final Logger LOG = Logger.getLogger(LineDecoder.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public final Location doDecoding(
            final OpenLRDecoderProperties prop, final MapDatabase mdb,
            final RawLocationReference rawLocRef)
            throws OpenLRProcessingException {

        if (mdb == null) {
            return new InvalidLocation(rawLocRef.getID(), DecoderReturnCode.NO_MAP_DATABASE_FOUND, LocationType.LINE_LOCATION);
        }

        List<? extends LocationReferencePoint> lrps = rawLocRef
                .getLocationReferencePoints();
        Offsets od = rawLocRef.getOffsets();

        // find candidate nodes
        if (LOG.isDebugEnabled()) {
            LOG.debug("find candidate nodes");
        }
        CandidateNodesResultSet candidateNodes = findCandidateNodes(prop, rawLocRef, mdb);

        // find candidate lines
        if (LOG.isDebugEnabled()) {
            LOG.debug("find candidate lines");
        }
        CandidateLinesResultSet candidateLines = findCandidateLines(prop, rawLocRef, candidateNodes, mdb);
        if (!candidateLines.allCandidateLinesFound()) {
            return new InvalidLocation(rawLocRef.getID(), DecoderReturnCode.NO_CANDIDATE_LINE_FOUND, LocationType.LINE_LOCATION);
        }
        // resolve routes
        if (LOG.isDebugEnabled()) {
            LOG.debug("resolve routes");
        }
        ResolvedRoutes resolvedRoutes = resolveRoute(prop, rawLocRef, candidateLines, LocationType.LINE_LOCATION);

        if (!resolvedRoutes.allRoutesResolved()) {
            ArrayList<List<Line>> subRouteList = new ArrayList<List<Line>>();
            for (LocationReferencePoint p : lrps) {
                List<Line> path = resolvedRoutes.getRoute(p);
                subRouteList.add(path);
            }
            return new InvalidLocation(rawLocRef.getID(), resolvedRoutes.getErrorCode(), LocationType.LINE_LOCATION, subRouteList);
        }
        // combine the shortest-path(s)
        ArrayList<Line> lines = new ArrayList<Line>();
        ArrayList<List<Line>> subRoutes = new ArrayList<List<Line>>();
        for (LocationReferencePoint p : lrps) {
            List<Line> path = resolvedRoutes.getRoute(p);
            lines.addAll(path);
            subRoutes.add(path);
        }
        // check, if the route is connected
        // normally not necessary but this check detects errors after
        // changing
        // the code
        if (!PathUtils.checkPathConnection(lines)) {
            LOG.error("resolved path is not connected");
            throw new OpenLRDecoderProcessingException(
                    DecoderProcessingError.ROUTE_DISCONNECTED);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("prune location path");
        }

        int[] result = calculateOffsets(lrps, od, resolvedRoutes);

        // prune path if offsets are available
        Location decoded = pruneAndCreateLocation(rawLocRef.getID(), lines, subRoutes,
                result[0], result[1]);

        // return the decoded location
        return decoded;
    }

    /**
     * Calculates the positive and negative offset values that trim the location
     * to the desired length.
     *
     * @param lrps
     *            The location reference points
     * @param od
     *            The input offset data
     * @param resolvedRoutes
     *            The resolved routes between the LRPs
     * @return An array of integers that contains the calculates offsets, index
     *         0 holds the positive, 1 the negative offset
     * @throws OpenLRDecoderProcessingException
     *             If the resolved routes are corrupt
     */
    private int[] calculateOffsets(
            final List<? extends LocationReferencePoint> lrps,
            final Offsets od, final ResolvedRoutes resolvedRoutes)
            throws OpenLRDecoderProcessingException {

        LocationReferencePoint lrp1 = lrps.get(0);
        CandidateLine headStartLine = resolvedRoutes.getCandidateStart(lrp1);
        CandidateLine headEndLine = resolvedRoutes.getCandidateEnd(lrp1);

        LocationReferencePoint lrpTail = lrps.get(lrps.size() - 2);
        CandidateLine tailStartLine = resolvedRoutes.getCandidateStart(lrpTail);
        CandidateLine tailEndLine = resolvedRoutes.getCandidateEnd(lrpTail);

        List<Line> routeHead = resolvedRoutes.getRoute(lrp1);
        List<Line> routeTail = resolvedRoutes.getRoute(lrpTail);
        // location head is the first sub-path of the location (LRP1 - LRP2)
        int locationHeadLength = PathUtils.getLength(routeHead);
        // location tail is the last sub-path of the location ((LastLRP-1) - LastLRP)
        int locationTailLength = PathUtils.getLength(routeTail);

        int cutStart = 0;
        int cutEnd = 0;

        if (headStartLine.hasProjectionAlongLine()) {
            cutStart = headStartLine.getProjectionAlongLine();
            locationHeadLength -= cutStart;
        }

        if (tailEndLine.hasProjectionAlongLine()) {
            cutEnd = tailEndLine.getLine().getLineLength()
                    - tailEndLine.getProjectionAlongLine();
            locationTailLength -= cutEnd;
        }

        if (lrp1.getSequenceNumber() == lrpTail.getSequenceNumber()) {
            // if start and end are in the same sub-path the net location length is
            // also reduced be the counter offset
            locationHeadLength -= cutEnd;
            locationTailLength -= cutStart;
        } else {

            if (headEndLine.hasProjectionAlongLine()) {
                // there is another part on the first line of the next
                // sub-path that relates to the head part
                locationHeadLength += headEndLine.getProjectionAlongLine();
            }

            if (tailStartLine.hasProjectionAlongLine()) {
                // not everything of the tail belongs to it, there is a snippet
                // at the start that refers to the former sub-path
                locationTailLength -= tailStartLine.getProjectionAlongLine();
            }
        }

        int startOffset = od.getPositiveOffset(locationHeadLength) + cutStart;
        int endOffset = od.getNegativeOffset(locationTailLength) + cutEnd;

        return new int[]{startOffset, endOffset};
    }

    /**
     * Prunes a location according to positive and negative offsets. Both offset
     * values are measured in meter and negative values indicate that this
     * offset is not existent. The positive offset will be used to shorten the
     * location from the beginning and the negative offset will be used to
     * shorten the location from the end. <br>
     * The pruning will always stop at nodes and there will be no pruning of
     * parts of lines. The remaining offsets can be accessed from the returned
     * decoded location object. Remaining offsets which are below the length
     * variance parameter will be refused and set to 0.
     *
     * @param location
     *            the location to be pruned
     * @param posOff
     *            the positive offset (< 0 if not available)
     * @param negOff
     *            the negative offset (< 0 if not available)
     * @param id
     *            the location id
     * @param subRoutes
     *            the sub routes created during decoding
     *
     * @return the decoded location including remaining offsets
     *
     * @throws OpenLRDecoderProcessingException
     *             if configuration failed
     */
    private Location pruneAndCreateLocation(final String id,
                                            final List<Line> location, final List<List<Line>> subRoutes,
                                            final int posOff, final int negOff)
            throws OpenLRDecoderProcessingException {
        List<Line> pruned = new ArrayList<Line>(location.size());
        pruned.addAll(location);
        int locLength = PathUtils.getLength(pruned);

        // check for too long offset values
        if (posOff + negOff >= 2 * locLength) {
            return new InvalidLocation(id, DecoderReturnCode.INVALID_OFFSETS, LocationType.LINE_LOCATION);
        }

        int pOff = posOff;
        int nOff = negOff;
        int offSum = pOff + nOff;
        if (locLength < offSum) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("offsets exceed location length");
            }
            //decrease offset values
            float ratio = locLength / (float) offSum;
            pOff = Math.round(posOff * ratio);
            nOff = Math.round(negOff * ratio);
            //retainable length shall be 1 meter
            if (pOff > nOff) {
                pOff--;
            } else {
                nOff--;
            }
        }

        int prunedLength = 0;
        int remainingPosOff = 0;
        int remainingNegOff = 0;

        // prune positive offset
        if (pOff > 0) {
            // prune posOff
            while (true) {
                // check if there is no more to prune and stop if necessary
                if (pruned.size() == 1) {
                    remainingPosOff = pOff - prunedLength;
                    break;
                }
                // get first line
                Line next = pruned.get(0);
                int length = next.getLineLength();
                // check if the total length of the line pruned so far will
                // reach the maximum pruning distance
                if (prunedLength + length > pOff) {
                    remainingPosOff = pOff - prunedLength;
                    break;
                } else {
                    // get rid of this line as well
                    prunedLength += length;
                    pruned.remove(0);
                }
            }
        }

        // now do the same for the negative offset
        prunedLength = 0;
        if (nOff > 0) {
            // prune negOff
            while (true) {
                if (pruned.size() == 1) {
                    remainingNegOff = nOff - prunedLength;
                    break;
                }
                Line next = pruned.get(pruned.size() - 1);
                int length = next.getLineLength();
                if (prunedLength + length > nOff) {
                    remainingNegOff = nOff - prunedLength;
                    break;
                } else {
                    prunedLength += length;
                    pruned.remove(pruned.size() - 1);
                }
            }
        }
        Location decoded;
        if (remainingNegOff == 0 && remainingPosOff == 0) {
            decoded = new DecodedLineLocation(id, pruned,
                    subRoutes);
        } else {
            decoded = new DecodedLineLocation(id,
                    pruned, subRoutes, remainingPosOff, remainingNegOff);
        }
        // return pruned path including remaining offsets
        return decoded;
    }

}
