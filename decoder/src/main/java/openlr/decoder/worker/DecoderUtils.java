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
package openlr.decoder.worker;

import openlr.LocationReferencePoint;
import openlr.LocationType;
import openlr.OpenLRProcessingException;
import openlr.decoder.data.CandidateLine;
import openlr.decoder.data.CandidateLinePair;
import openlr.decoder.data.CandidateLinesResultSet;
import openlr.decoder.properties.OpenLRDecoderProperties;
import openlr.decoder.routesearch.RouteSearch;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * DecoderUtils provides utility methods for the decoding process.
 *
 * @author svba
 *
 */
public final class DecoderUtils {

    /** logger. */
    private static final Logger LOG = Logger.getLogger(DecoderUtils.class);

    /**
     * Utility class shall not be instantiated.
     */
    private DecoderUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Each LRP might have several candidate lines. In order to find the best
     * pair to start with each pair needs to be investigated and rated. The
     * rating process includes:
     * <ul>
     * <li>score of the first candidate line</li>
     * <li>score of the second candidate line</li>
     * <li>connection to the previously calculated path</li>
     * <li>candidate lines shall not be equal</li>
     * </ul>
     *
     * The sequence of candidate pairs will be trimmed down to a maximum size
     * according to the encoder properties.
     *
     * @param p1
     *            the first LRP
     * @param p2
     *            the successor LRP
     * @param candidateLines
     *            the candidate lines
     * @param lastUsed
     *            the last used
     * @param properties
     *            the OpenLR encoder properties
     * @param locType
     *            the loc type
     * @return an ordered list of candidate pairs, best comes first
     * @throws OpenLRProcessingException
     *             the open lr processing exception
     */
    public static List<CandidateLinePair> resolveCandidatesOrder(
            final LocationReferencePoint p1, final LocationReferencePoint p2,
            final CandidateLinesResultSet candidateLines,
            final CandidateLine lastUsed,
            final OpenLRDecoderProperties properties, final LocationType locType)
            throws OpenLRProcessingException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("resolve candidates order with SameLineDegradation: "
                    + properties.getSameLineDegradation()
                    + " and ConnectedRouteIncrease: "
                    + properties.getConnectedRouteIncrease()
                    + " and MaxNrRetries: " + properties.getMaxNumberRetries());
        }
        List<CandidateLinePair> pairs = new ArrayList<CandidateLinePair>();
        List<CandidateLine> p1List = candidateLines.getCandidateLines(p1);
        List<CandidateLine> p2List = candidateLines.getCandidateLines(p2);
        for (int i = 0; i < p1List.size(); ++i) {
            int p1Score = p1List.get(i).getRating();
            CandidateLine cl = p1List.get(i);
            // check connection with previously calculated path
            if (lastUsed != null && cl.hasSameLine(lastUsed)) {
                p1Score += (properties.getConnectedRouteIncrease() * p1Score);
            }
            for (int j = 0; j < p2List.size(); ++j) {
                int p2Score = p2List.get(j).getRating();
                // check same line
                if (!p2.isLastLRP() && locType == LocationType.LINE_LOCATION
                        && p2List.get(j).hasSameLine(p1List.get(i))) {
                    p2Score -= (properties.getSameLineDegradation() * p2Score);
                }
                CandidateLinePair candidate = new CandidateLinePair(i, j,
                        p1Score * p2Score);
                pairs.add(candidate);
            }
        }
        Collections.sort(pairs,
                new CandidateLinePair.CandidateLinePairComparator());
        int returnSize = Math.min(properties.getMaxNumberRetries() + 1,
                pairs.size());
        List<CandidateLinePair> subList = pairs.subList(0, returnSize);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Candidate pair list for " + p1.getSequenceNumber()
                    + " and " + p2.getSequenceNumber());
            for (CandidateLinePair clp : subList) {
                LOG.debug("startIdx[" + clp.getStartIndex() + "] - destIdx["
                        + clp.getDestIndex() + "], score: " + clp.getScore());
            }
        }
        return subList;
    }

    /**
     * Determine route length.
     *
     * @param rs
     *            the rs
     * @param destCandidate
     *            the dest candidate
     * @return the int
     */
    public static int determineRouteLength(final RouteSearch rs,
                                           final CandidateLine destCandidate) {
        int length = rs.getRouteLength();
        if (destCandidate.hasProjectionAlongLine()) {
            length += destCandidate.getProjectionAlongLine();
        }
        return length;
    }

    /**
     * Calculates the maximum allowed distance between two location reference
     * points taking into account that at least one LRP might be projected onto
     * a line and the maximum distance must be adjusted as the route calculation
     * can only stop at distances between real nodes.
     *
     * @param p
     *            the start lrp
     * @param candP
     *            the cand p
     * @param candNext
     *            the cand next
     * @param properties
     *            the properties
     * @return the maximum allowed distance for the route calculation
     * @throws OpenLRProcessingException
     *             the open lr processing exception
     */
    public static int calculateMaxLength(final LocationReferencePoint p,
                                         final CandidateLine candP, final CandidateLine candNext,
                                         final OpenLRDecoderProperties properties)
            throws OpenLRProcessingException {
        int maxDistance = getMaxDistanceNP(p, properties);
        // check if LRPs were projected on line
        // if yes, add line length to maxDistance (complete length as route
        // search stops at nodes)
        if (candP.hasProjectionAlongLine()) {
            maxDistance += candP.getLine().getLineLength();
        }
        if (candNext.hasProjectionAlongLine()) {
            maxDistance += candNext.getLine().getLineLength();
        }
        return maxDistance;
    }

    /**
     * Gets the maximum distance to next point.
     *
     * @param lrp
     *            the lrp
     * @param properties
     *            the properties
     * @return the maximum distance to next point
     * @throws OpenLRProcessingException
     *             the open lr processing exception
     */
    public static int getMaxDistanceNP(final LocationReferencePoint lrp,
                                       final OpenLRDecoderProperties properties)
            throws OpenLRProcessingException {
        return lrp.getDistanceToNext() + properties.getDnpVariance();
    }

    /**
     * Gets the minimum distance to next point.
     *
     * @param lrp
     *            the lrp
     * @param properties
     *            the properties
     * @return the minimum distance to next point
     * @throws OpenLRProcessingException
     *             the open lr processing exception
     */
    public static int getMinDistanceNP(final LocationReferencePoint lrp,
                                       final OpenLRDecoderProperties properties)
            throws OpenLRProcessingException {
        return Math.max(0,
                lrp.getDistanceToNext() - properties.getDnpVariance());

    }

}
