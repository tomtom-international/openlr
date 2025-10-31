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
import openlr.Offsets;
import openlr.OpenLRProcessingException;
import openlr.decoder.DecoderReturnCode;
import openlr.decoder.OpenLRDecoderProcessingException;
import openlr.decoder.OpenLRDecoderProcessingException.DecoderProcessingError;
import openlr.decoder.data.CandidateLine;
import openlr.decoder.data.CandidateLinesResultSet;
import openlr.decoder.data.CandidateNodesResultSet;
import openlr.decoder.data.ResolvedRoutes;
import openlr.decoder.location.DecodedPoiAccessLocation;
import openlr.decoder.properties.OpenLRDecoderProperties;
import openlr.location.InvalidLocation;
import openlr.location.Location;
import openlr.map.InvalidMapDataException;
import openlr.map.Line;
import openlr.map.MapDatabase;
import openlr.map.utils.PathUtils;
import openlr.rawLocRef.RawLocationReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * The poi with access point decoder.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class PoiAccessDecoder extends AbstractDecoder {

    /** logger. */
    private static final Logger LOG = LoggerFactory.getLogger(PoiAccessDecoder.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public final Location doDecoding(
            final OpenLRDecoderProperties prop, final MapDatabase mdb,
            final RawLocationReference rawLocRef)
            throws OpenLRProcessingException {

        if (mdb == null) {
            return new InvalidLocation(rawLocRef.getID(), DecoderReturnCode.NO_MAP_DATABASE_FOUND, LocationType.POI_WITH_ACCESS_POINT);
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
            // no line found (no access point available) but we can
            // calculate the poi position
            return new InvalidLocation(rawLocRef.getID(), LocationType.POI_WITH_ACCESS_POINT, DecoderReturnCode.NO_CANDIDATE_LINE_FOUND, rawLocRef
                    .getGeoCoordinates(), rawLocRef.getSideOfRoad(), rawLocRef
                    .getOrientation());
        }
        // resolve routes
        if (LOG.isDebugEnabled()) {
            LOG.debug("resolve routes");
        }
        ResolvedRoutes resolvedRoutes = resolveRoute(prop, rawLocRef, candidateLines, LocationType.POI_WITH_ACCESS_POINT);
        if (!resolvedRoutes.allRoutesResolved()) {
            ArrayList<List<Line>> subRouteList = new ArrayList<List<Line>>();
            for (LocationReferencePoint p : lrps) {
                List<Line> path = resolvedRoutes.getRoute(p);
                subRouteList.add(path);
            }
            return new InvalidLocation(rawLocRef.getID(), LocationType.POI_WITH_ACCESS_POINT, resolvedRoutes.getErrorCode(), subRouteList, rawLocRef.getGeoCoordinates(), rawLocRef
                    .getSideOfRoad(), rawLocRef.getOrientation());
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
        int lStart = 0;
        for (Line l : subRoutes.get(0)) {
            lStart += l.getLineLength();
        }

        LocationReferencePoint lrp1 = lrps.get(0);
        CandidateLine cl1 = resolvedRoutes.getCandidateStart(lrp1);
        CandidateLine cl2 = resolvedRoutes.getCandidateEnd(lrp1);

        int additionalStartOffset = 0;
        int pruneAtStart = 0;


        if ((cl1 != null) && cl1.hasProjectionAlongLine()) {
            additionalStartOffset = cl1.getProjectionAlongLine();
            pruneAtStart += additionalStartOffset;
        }
        if ((cl2 != null) && cl2.hasProjectionAlongLine()) {
            pruneAtStart += cl2.getProjectionAlongLine();
        }

        int startOffset = od.getPositiveOffset(lStart - pruneAtStart) + additionalStartOffset;

        // prune path if offsets are available
        Location decoded = pruneAndCreateLocation(rawLocRef.getID(), lines, subRoutes,
                startOffset, rawLocRef);

        // return the decoded location
        return decoded;

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
     * @param id
     *            the location id
     * @param subRoutes
     *            the sub routes created during decoding
     * @param rawLocRef
     *            the raw location reference
     *
     * @return the decoded location including remaining offsets
     *
     * @throws OpenLRDecoderProcessingException
     *             if configuration failed
     */
    private Location pruneAndCreateLocation(final String id,
                                            final List<Line> location, final List<List<Line>> subRoutes,
                                            final int posOff, final RawLocationReference rawLocRef)
            throws OpenLRDecoderProcessingException {
        List<Line> pruned = new ArrayList<Line>(location.size());
        pruned.addAll(location);
        int locLength = PathUtils.getLength(pruned);
        if (posOff >= 2 * locLength) {
            return new InvalidLocation(id, DecoderReturnCode.INVALID_OFFSETS, LocationType.POI_WITH_ACCESS_POINT);
        }

        int pOff = posOff;
        if (posOff > locLength) {
            pOff = locLength;
        }
        int prunedLength = 0;
        int remainingPosOff = 0;
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

        Location decoded;
        try {
            if (remainingPosOff == 0) {
                decoded = new DecodedPoiAccessLocation(id,
                        pruned.get(0), subRoutes, rawLocRef.getGeoCoordinates(),
                        rawLocRef.getSideOfRoad(), rawLocRef.getOrientation());
            } else {
                decoded = new DecodedPoiAccessLocation(id, pruned.get(0),
                        subRoutes, remainingPosOff, rawLocRef
                        .getGeoCoordinates(), rawLocRef.getSideOfRoad(),
                        rawLocRef.getOrientation());
            }
        } catch (InvalidMapDataException e) {
            throw new OpenLRDecoderProcessingException(DecoderProcessingError.INVALID_MAP_DATA, e);
        }
        // return pruned path including remaining offsets
        return decoded;
    }

}
