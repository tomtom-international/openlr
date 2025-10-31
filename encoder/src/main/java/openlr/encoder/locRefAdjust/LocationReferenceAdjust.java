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
package openlr.encoder.locRefAdjust;

import openlr.LocationType;
import openlr.OpenLRProcessingException;
import openlr.encoder.OpenLREncoderProcessingException;
import openlr.encoder.OpenLREncoderProcessingException.EncoderProcessingError;
import openlr.encoder.data.ExpansionData;
import openlr.encoder.data.ExpansionHelper;
import openlr.encoder.data.LocRefData;
import openlr.encoder.data.LocRefPoint;
import openlr.encoder.properties.OpenLREncoderProperties;
import openlr.location.Location;
import openlr.map.Line;
import openlr.map.Node;
import openlr.map.utils.NodeCheck;
import openlr.map.utils.PathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * The generated OpenLR location reference needs to be checked whether all
 * criteria are met. This class provides the relevant methods for this
 * particular check.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public abstract class LocationReferenceAdjust {

    /** Logging */
    private static final Logger LOG = LoggerFactory.getLogger(LocationReferenceAdjust.class);

    /**
     * The generated OpenLR(tm) location references need to be checked whether
     * all criteria are met. Currently this includes the check for the maximum
     * distance between two successive location reference points. If the
     * distance exceeds such a maximum then additional intermediate location
     * reference points will be inserted. Additionally it will be checked
     * whether the location reference path covers the location completely. This
     * is the last check of the data and should never fail if the generation
     * process was successful.
     *
     * @param properties
     *            the OpenLR properties
     * @param locRefData
     *            the loc ref data
     * @throws OpenLRProcessingException
     *             the open lr processing exception
     */
    public abstract void adjustLocationReference(
            final OpenLREncoderProperties properties,
            final LocRefData locRefData) throws OpenLRProcessingException;

    /**
     * Check and adjust offsets. This method checks of there are offsets defined
     * in the current state of the location reference data that are invalid. Invalid
     * offsets are such that are greater than the route(s) between the LRPs, for the
     * negative offset this means backwards into the location reference. In such case
     * the "overleaped" LRPs are removed and the offsets are trimmed accordingly.
     *
     * @param lrd
     *            the loc ref data.
     *
     * @param properties
     *            the properties
     * @return the list
     * @throws OpenLRProcessingException
     *             the open lr processing exception
     */
    protected final List<LocRefPoint> checkAndAdjustOffsets(
            final LocRefData lrd, final OpenLREncoderProperties properties)
            throws OpenLRProcessingException {
        List<LocRefPoint> checkedAndAdjusted = lrd.getLocRefPoints();
        Location location = lrd.getLocation();
        ExpansionData expansion = lrd.getExpansionData();
        int startLength = checkedAndAdjusted.get(0).getDistanceToNext();
        int totalPosOff = location.getPositiveOffset()
                + expansion.getExpansionLengthStart();
        while (totalPosOff > startLength) {
            if (expansion.hasExpansionStart()) {
                if (!expansion.modifyExpansionAtStart(checkedAndAdjusted.get(0)
                        .getRoute())) {
                    throw new OpenLREncoderProcessingException(
                            EncoderProcessingError.OFFSET_TRIMMING_FAILED);
                }
            } else {
                lrd.addToReducedPosOff(startLength);
            }
            checkedAndAdjusted.remove(0);
            if (checkedAndAdjusted.size() < 2) {
                throw new OpenLREncoderProcessingException(
                        EncoderProcessingError.OFFSET_TRIMMING_FAILED);
            }
            startLength = checkedAndAdjusted.get(0).getDistanceToNext();
            totalPosOff = location.getPositiveOffset()
                    + expansion.getExpansionLengthStart()
                    - lrd.getReducedPosOff();
        }

        int endLength = checkedAndAdjusted.get(checkedAndAdjusted.size() - 2)
                .getDistanceToNext();
        int totalNegOff = location.getNegativeOffset()
                + expansion.getExpansionLengthEnd();
        while (totalNegOff > endLength) {
            if (expansion.hasExpansionEnd()) {
                if (!expansion.modifyExpansionAtEnd(checkedAndAdjusted.get(
                        checkedAndAdjusted.size() - 2).getRoute())) {
                    throw new OpenLREncoderProcessingException(
                            EncoderProcessingError.OFFSET_TRIMMING_FAILED);
                }
            } else {
                lrd.addToReducedNegOff(endLength);
            }
            // remove last LRP
            checkedAndAdjusted.remove(checkedAndAdjusted.size() - 1);
            if (checkedAndAdjusted.size() < 2) {
                throw new OpenLREncoderProcessingException(
                        EncoderProcessingError.OFFSET_TRIMMING_FAILED);
            }

            LocRefPoint oldLastLRP = checkedAndAdjusted.get(checkedAndAdjusted
                    .size() - 1);
            LocRefPoint prevLRP = checkedAndAdjusted.get(checkedAndAdjusted
                    .size() - 2);
            LocRefPoint newLastLRP;
            if (oldLastLRP.isLRPOnLine()) {
                newLastLRP = new LocRefPoint(oldLastLRP.getLine(), oldLastLRP.getLongitudeDeg(), oldLastLRP.getLatitudeDeg(), properties, true);
            } else {
                newLastLRP = new LocRefPoint(prevLRP.getLastLineOfSubRoute(), properties);
            }
            checkedAndAdjusted.remove(checkedAndAdjusted.size() - 1);
            prevLRP.setNextLRP(newLastLRP);
            checkedAndAdjusted.add(newLastLRP);

            endLength = checkedAndAdjusted.get(checkedAndAdjusted.size() - 2)
                    .getDistanceToNext();
            totalNegOff = location.getNegativeOffset()
                    + expansion.getExpansionLengthEnd()
                    - lrd.getReducedNegOff();
        }
        return checkedAndAdjusted;
    }

    /**
     * Checks if the LRPs are on a valid node. It logs a warning if the
     * corresponding node is invalid.
     *
     * @param locRefPoints
     *            the location reference points
     */
    protected final void checkNodeValidity(final List<LocRefPoint> locRefPoints) {
        int total = locRefPoints.size();
        for (int i = 0; i < total; i++) {
            LocRefPoint lrp = locRefPoints.get(i);
            Node lrpNode = lrp.getLRPNode();
            if (!NodeCheck.isValidNode(lrpNode)) {
                LOG.warn("location reference point (" + (i + 1)
                        + " is located on an invalid node)");
            }
        }
    }

    /**
     * Checks if the distance between two consecutive location reference points
     * exceeds the maximum allowed distance.
     *
     * @param properties
     *            the OpenLR properties
     * @param locRefData
     *            the loc ref data
     * @return Whether the DNP values are correct.
     * @throws OpenLRProcessingException
     *             the open lr processing exception
     */
    protected final boolean checkMaxDistances(
            final OpenLREncoderProperties properties,
            final LocRefData locRefData) throws OpenLRProcessingException {
        /*
         * check for the maximum distance between two successive LRP
         */
        if (LOG.isDebugEnabled()) {
            LOG.debug("check location reference (maximum distance check)");
        }
        final List<LocRefPoint> locRefPoints = locRefData.getLocRefPoints();
        int maxDistance = properties.getMaximumDistanceLRP();

        boolean dnpsAreVAlid = true;
        for (LocRefPoint lrPoint : locRefPoints) {
            // check the distance limit
            if (!lrPoint.isLastLRP()
                    && lrPoint.getDistanceToNext() > maxDistance) {
                // limit is exceeded
                LOG.error(String
                        .format("maximum distance between two LRP is exceeded (LRP #: %s, distance: %s)",
                                lrPoint.getSequenceNumber(),
                                lrPoint.getDistanceToNext()));
                dnpsAreVAlid = false;
                break;
            }
        }

        return dnpsAreVAlid;
    }

    /**
     * Checks if the path of the location reference covers the expanded
     * location. The check includes checking the identity and order of the lines
     * in both paths and the comparison of the location path length and the
     * accumulated length of the location reference paths.
     *
     * @param locRefData
     *            the loc ref data
     * @return true, if the location reference path covers the location,
     *         otherwise false
     */
    protected final boolean checkLRPCoverage(final LocRefData locRefData) {
        List<LocRefPoint> locRefPoints = locRefData.getLocRefPoints();
        List<Line> locRoute = ExpansionHelper.getExpandedLocation(locRefData);
        int lrpLength = 0;
        List<Line> lrpRoute = new ArrayList<Line>();
        for (LocRefPoint lrd : locRefPoints) {
            if (lrd.getRoute() != null) {
                if (!lrd.isLRPOnLine()) {
                    // Added by DLR e.V. (RE)
                    if (locRefData.getLocationType() == LocationType.CLOSED_LINE
                            && lrd == locRefPoints.get(locRefPoints.size() - 1)) {
                        List<Line> route = lrd.getRoute();
                        route.add(locRefPoints.get(0).getRoute().get(0));
                        lrpRoute.addAll(route);
                        route.remove(route.size() - 1);
                    } else { // original code
                        lrpRoute.addAll(lrd.getRoute());
                    }
                    //
                }
                // Added by DLR e.V. (RE)
                if (locRefData.getLocationType() == LocationType.CLOSED_LINE
                        && lrd == locRefPoints.get(locRefPoints.size() - 1)) {
                    List<Line> route = lrd.getRoute();
                    route.add(locRefPoints.get(0).getRoute().get(0));
                    lrpLength += PathUtils.getLength(route);
                    route.remove(route.size() - 1);
                } else { // original code
                    lrpLength += lrd.getDistanceToNext();
                }
                //
            }
        }
        // check for the same amount of lines
        if (locRoute.size() != lrpRoute.size()) {
            LOG.error("number of lines are different");
            LOG.error("location: " + locRoute.size() + " - locRef: "
                    + lrpRoute.size());
            return false;
        }
        int locationLength = 0;
        int count = locRoute.size();
        // check if the lines are equal and in the same order
        for (int i = 0; i < count; ++i) {
            Line next = locRoute.get(i);
            if (next.getID() != lrpRoute.get(i).getID()) {
                // if a line is missing or in an incorrect order, then fail
                LOG.error("paths are different");
                LOG.error("location line: " + next.getID() + " - locRef line: "
                        + lrpRoute.get(i).getID());
                return false;
            }
            locationLength += next.getLineLength();
        }
        // check that the location length and location reference path length are
        // equal
        if (locationLength != lrpLength) {
            LOG.error("lengths are different");
            LOG.error("location: " + locationLength + " - locRef: " + lrpLength);
            return false;
        }
        return true;
    }
}
