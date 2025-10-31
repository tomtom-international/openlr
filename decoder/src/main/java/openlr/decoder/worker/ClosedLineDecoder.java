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
import openlr.decoder.OpenLRDecoderProcessingException;
import openlr.decoder.OpenLRDecoderProcessingException.DecoderProcessingError;
import openlr.decoder.data.CandidateLinesResultSet;
import openlr.decoder.data.CandidateNodesResultSet;
import openlr.decoder.data.ResolvedRoutes;
import openlr.decoder.location.AffectedLinesImpl;
import openlr.decoder.location.DecodedClosedLineLocation;
import openlr.decoder.properties.OpenLRDecoderProperties;
import openlr.decoder.worker.coverage.ClosedLineCoverage;
import openlr.location.InvalidLocation;
import openlr.location.Location;
import openlr.location.data.AffectedLines;
import openlr.map.InvalidMapDataException;
import openlr.map.Line;
import openlr.map.MapDatabase;
import openlr.map.utils.IteratorHelper;
import openlr.map.utils.PathUtils;
import openlr.rawLocRef.RawLocationReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The line decoder.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author DLR e.V. (RE)
 */
public class ClosedLineDecoder extends AbstractDecoder {

    /** logger. */
    private static final Logger LOG = LoggerFactory.getLogger(ClosedLineDecoder.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public final Location doDecoding(final OpenLRDecoderProperties prop, final MapDatabase mdb,
                                     final RawLocationReference rawLocRef) throws OpenLRProcessingException {

        if (mdb == null) {
            return new InvalidLocation(rawLocRef.getID(),
                    DecoderReturnCode.NO_MAP_DATABASE_FOUND,
                    LocationType.CLOSED_LINE);
        }

        List<? extends LocationReferencePoint> lrps = rawLocRef
                .getLocationReferencePoints();

        // find candidate nodes
        if (LOG.isDebugEnabled()) {
            LOG.debug("find candidate nodes");
        }
        CandidateNodesResultSet candidateNodes = findCandidateNodes(prop,
                rawLocRef, mdb);

        // find candidate lines
        if (LOG.isDebugEnabled()) {
            LOG.debug("find candidate lines");
        }
        CandidateLinesResultSet candidateLines = findCandidateLines(prop,
                rawLocRef, candidateNodes, mdb);
        if (!candidateLines.allCandidateLinesFound()) {
            return new InvalidLocation(rawLocRef.getID(),
                    DecoderReturnCode.NO_CANDIDATE_LINE_FOUND,
                    LocationType.CLOSED_LINE);
        }
        // resolve routes
        if (LOG.isDebugEnabled()) {
            LOG.debug("resolve routes");
        }
        ResolvedRoutes resolvedRoutes = resolveRoute(prop, rawLocRef,
                candidateLines, LocationType.CLOSED_LINE);

        if (!resolvedRoutes.allRoutesResolved()) {
            ArrayList<List<Line>> subRouteList = new ArrayList<List<Line>>();
            for (LocationReferencePoint p : lrps) {
                List<Line> path = resolvedRoutes.getRoute(p);
                subRouteList.add(path);
            }
            return new InvalidLocation(rawLocRef.getID(),
                    resolvedRoutes.getErrorCode(), LocationType.CLOSED_LINE,
                    subRouteList);
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

        /* Check whether first line is within the successors of last line */
        Line firstLine = lines.get(0);
        Line lastLine = lines.get(lines.size() - 1);
        Iterator<? extends Line> succLines = lastLine.getNextLines();
        if (!IteratorHelper.contains(succLines, firstLine)) {
            LOG.error("unclosed closed location, not connected from "
                    + lastLine.getID() + " to " + firstLine.getID());
            throw new OpenLRDecoderProcessingException(
                    DecoderProcessingError.ROUTE_DISCONNECTED);
        }

        AffectedLines result = null;
        if (prop.isCalcAffectedLines()) {
            ClosedLineCoverage coverage = null;
            try {
                coverage = new ClosedLineCoverage(lines);
            } catch (InvalidMapDataException e) {
                throw new OpenLRDecoderProcessingException(
                        DecoderProcessingError.INVALID_MAP_DATA, e);
            }
            result = coverage.getAffectedLines(mdb);
        } else {
            result = AffectedLinesImpl.EMPTY;
        }

        Location decoded = new DecodedClosedLineLocation(rawLocRef.getID(), result, lines);

        // return the decoded location
        return decoded;
    }

}
