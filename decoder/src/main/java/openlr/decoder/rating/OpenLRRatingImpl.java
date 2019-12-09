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
package openlr.decoder.rating;

import openlr.LocationReferencePoint;
import openlr.OpenLRProcessingException;
import openlr.decoder.properties.OpenLRDecoderProperties;
import openlr.decoder.rating.critics.BearingCritic;
import openlr.map.FormOfWay;
import openlr.map.FunctionalRoadClass;
import openlr.map.Line;
import openlr.map.Node;
import org.apache.log4j.Logger;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * The Class OpenLRRatingImpl implements the rating function for OpenLR.
 * <p>
 * This implementation makes use of the following attributes:
 * <ul>
 * <li>distance between the LRP position and the corresponding node of the line
 * <li>differences in the functional road class
 * <li>differences in the form of way
 * <li>differences in the bearing
 * </ul>
 * The formula used for the rating value looks like:<br>
 * <br>
 * [node_factor] * node_value + [line_factor] * (bearing_value + frc_value +
 * fow_value) <br>
 * <br>
 * The factors are defined in the OpenLR properties. The values are based on a
 * classification how good the actual line value matches the LRP attributes. The
 * possible categories are defined in {@link OpenLRRating.RatingCategory} and
 * the rating values are also defined in the OpenLR properties. <br>
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class OpenLRRatingImpl implements OpenLRRating {

    /** logger */
    private static final Logger LOG = Logger.getLogger(OpenLRRatingImpl.class);

    /** The Constant fowRatingTable. */
    private static final FormOfWayRatingTable FOW_RATING_TABLE = new FormOfWayRatingTable();

    private final OpenLRDecoderProperties properties;
    private final BearingCritic bearingCritic;

    private OpenLRRatingImpl(OpenLRDecoderProperties properties, BearingCritic bearingCritic) {
        this.properties = properties;
        this.bearingCritic = bearingCritic;
    }

    public static OpenLRRatingImpl with(OpenLRDecoderProperties properties) {
        BearingCritic bearingCritic = BearingCritic.with(properties);
        return new OpenLRRatingImpl(properties, bearingCritic);
    }

    /** {@inheritDoc} */
    @Override
    public final int getRating(final int distance, final LocationReferencePoint p,
                               final Line line, final int projectionAlongLine)
            throws OpenLRProcessingException {
        int nodeRating = calculateDistanceRating(properties, distance);

        if (shouldApplyNonJunctionNodeFactor(line, p.isLastLRP(), projectionAlongLine)) {
            nodeRating = (int) (properties.getNonJunctionNodeFactor() * nodeRating);
        }

        int bearingRating = bearingCritic.rate(line, p.isLastLRP(), p.getBearing(), projectionAlongLine);
        if (bearingRating < 0) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("bearing of a candidate line is out of range ["
                        + line.getID() + "]");
            }
            return -1;
        }
        int frcRating = calculateFRCRating(properties, p.getFRC(), line);
        int fowRating = calculateFOWRating(properties, p.getFOW(), line);
        int lineRating = bearingRating + frcRating + fowRating;
        int rating = properties.getNodeFactor() * nodeRating
                + properties.getLineFactor() * lineRating;
        if (LOG.isDebugEnabled()) {
            LOG.debug("total rating [" + line.getID() + "]: " + rating
                    + "  (node: " + nodeRating + ", line: " + lineRating
                    + ", bearing: " + bearingRating + ", frc: " + frcRating
                    + ", fow: " + fowRating + ")");
        }
        return rating;
    }

    /**
     * Determine whether to apply the non-junction node factor to the node score
     *
     * @param line the line under consideration
     * @param isLastLrp true if the search is for lrp
     * @param projectionAlongLine the projected distance along the line
     * @return true if the non-junction node factor is to be applied
     */
    private boolean shouldApplyNonJunctionNodeFactor(Line line, boolean isLastLrp, int projectionAlongLine) {
        // Only apply the non-junction node factor when the LRP matches a node and not a line directly
        if (projectionAlongLine > 0 && projectionAlongLine < line.getLineLength()) {
            return false;
        }

        // Find the node to check
        Node node = isLastLrp ? line.getEndNode() : line.getStartNode();

        // Check if the node is not a junction
        return !isJunction(node);
    }

    /**
     * Check if a node is a junction. Junction nodes are nodes which are directly connected to more than 2 nodes.
     *
     * @param node the node to check
     * @return true if the node is a junction
     */
    private boolean isJunction(Node node) {
        return getConnectedNodeCount(node) > 2;
    }

    /**
     * Get the number of nodes that are directly connected to a node via its connected lines
     *
     * @param node the node
     * @return the number of connected nodes
     */
    private int getConnectedNodeCount(Node node) {
        return (int) StreamSupport.stream(Spliterators.spliteratorUnknownSize(node.getConnectedLines(), Spliterator.ORDERED), false)
                .flatMap(l -> Stream.of(l.getStartNode(), l.getEndNode()))
                .filter(n -> !node.equals(n))
                .distinct()
                .count();
    }

    /**
     * Calculates the node value based on the distance between the LRP position
     * and the corresponding node. The formula looks like: <br>
     * <br>
     * node_value = [max_node_distance] - distance <br>
     * <br>
     * The max_node_distance is configurable in the OpenLR properties.
     *
     * @param properties
     *            the OpenLR properties
     * @param distance
     *            the distance of the node to the LRP position
     * @return the node value
     * @throws OpenLRProcessingException
     *             the open lr processing exception
     */
    private int calculateDistanceRating(
            final OpenLRDecoderProperties properties, final double distance)
            throws OpenLRProcessingException {
        int diff = properties.getMaxNodeDistance() - (int) Math.round(distance);
        return Math.max(0, diff);
    }

    /**
     * Calculates the frc value based on the frc values of the line and the lrp
     * attribute. <br>
     * <br>
     * The method calculates the difference of the frc IDs and looks up the
     * rating category for this difference value. The defined value based on
     * this category is used for the rating value. The rating categories and its
     * rating values are defined in the OpenLR properties.
     *
     * @param properties
     *            the OpenLR properties
     * @param frc
     *            the functional road class of the LRP
     * @param line
     *            the line to be rated
     * @return the frc value
     * @throws OpenLRProcessingException
     *             the open lr processing exception
     */
    private int calculateFRCRating(final OpenLRDecoderProperties properties,
                                   final FunctionalRoadClass frc, final Line line)
            throws OpenLRProcessingException {
        int lineFRC = line.getFRC().getID();
        int diff = Math.abs(frc.getID() - lineFRC);

        RatingCategory bestCat;
        if (diff <= properties.getFrcIntervals(RatingCategory.EXCELLENT)) {
            bestCat = RatingCategory.EXCELLENT;
        } else if (diff <= properties.getFrcIntervals(RatingCategory.GOOD)) {
            bestCat = RatingCategory.GOOD;
        } else if (diff <= properties.getFrcIntervals(RatingCategory.AVERAGE)) {
            bestCat = RatingCategory.AVERAGE;
        } else {
            bestCat = RatingCategory.POOR;
        }
        return properties.getFrcRating(bestCat);
    }

    /**
     * Calculates the fow value based on the line and lrp attributes. The
     * formula uses the rating values defined in the OpenLR properties. The
     * matching between the line and lrp fow attribute is EXCELLENT if both IDs
     * are equal. Otherwise if one of the attributes is undefined then the match
     * is AVERAGE and if no former rule matches then we have a POOR match of the
     * attributes.
     *
     * @param properties
     *            the OpenLR properties
     * @param fow
     *            the fow attribute of the LRP
     * @param line
     *            the line to be rated
     * @return the fow value
     * @throws OpenLRProcessingException
     *             the open lr processing exception
     */
    private int calculateFOWRating(final OpenLRDecoderProperties properties,
                                   final FormOfWay fow, final Line line)
            throws OpenLRProcessingException {
        RatingCategory category = FOW_RATING_TABLE.getRating(fow, line.getFOW());
        return properties.getFowRating(category);
    }
}
