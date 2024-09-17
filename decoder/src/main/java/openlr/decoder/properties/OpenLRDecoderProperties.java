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
package openlr.decoder.properties;

import openlr.OpenLRProcessingException;
import openlr.decoder.rating.OpenLRRating.RatingCategory;
import openlr.map.FunctionalRoadClass;
import openlr.properties.OpenLRPropertyAccess;
import org.apache.commons.configuration.Configuration;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * The Class OpenLRDecoderProperties.
 */
public class OpenLRDecoderProperties {

    /** The bearing distance. */
    private final int bearingDistance;

    /** The max node distance. */
    private final int maxNodeDistance;

    /** The node factor. */
    private final int nodeFactor;

    /** The line factor. */
    private final int lineFactor;

    /** The frc variance. */
    private final Map<FunctionalRoadClass, Integer> frcVariance;

    /** The minimum accepted rating. */
    private final int minimumAcceptedRating;

    /** The max number retries. */
    private final int maxNumberRetries;

    /** The connected route increase. */
    private final float connectedRouteIncrease;

    /** The dnp variance. */
    private final int dnpVariance;

    /** The max bearing diff. */
    private final int maxBearingDiff;

    /** The minimum bearing score.  Candidates with bearing scores less than this value will be disregarded. */
    private final int minBearingScore;

    /** The maximum score bearing rating can contribute to the overall rating. */
    private final int maxBearingScore;

    /** The frc rating. */
    private final EnumMap<RatingCategory, Integer> frcRating;

    /** The frc intervals. */
    private final EnumMap<RatingCategory, Integer> frcIntervals;

    /** The fow rating. */
    private final EnumMap<RatingCategory, Integer> fowRating;

    /** The calc affected lines. */
    private final boolean calcAffectedLines;

    /** The non junction node factor. */
    private final float nonJunctionNodeFactor;

    /** The lines directly factor. */
    private final float linesDirectlyFactor;

    /** The comp time4 cache. */
    private final int compTime4Cache;

    /**
     * Instantiates a new open lr decoder properties.
     *
     * @param config
     *            the config
     * @throws OpenLRProcessingException
     *             the open lr processing exception
     */
    public OpenLRDecoderProperties(final Configuration config)
            throws OpenLRProcessingException {
        bearingDistance = OpenLRPropertyAccess.getIntegerPropertyValue(config,
                OpenLRDecoderProperty.BEAR_DIST);
        maxNodeDistance = OpenLRPropertyAccess.getIntegerPropertyValue(config,
                OpenLRDecoderProperty.MAX_NODE_DIST);
        nodeFactor = OpenLRPropertyAccess.getIntegerPropertyValue(config,
                OpenLRDecoderProperty.NODE_FACTOR);
        lineFactor = OpenLRPropertyAccess.getIntegerPropertyValue(config,
                OpenLRDecoderProperty.LINE_FACTOR);
        nonJunctionNodeFactor = OpenLRPropertyAccess.getFloatPropertyValue(config,
                OpenLRDecoderProperty.NON_JUNCTION_NODE_FACTOR);

        frcVariance = new HashMap<>();

        for (FunctionalRoadClass frc : FunctionalRoadClass.values()) {
            int variance = OpenLRPropertyAccess.getIntegerPropertyValueFromMap(
                    config,
                    OpenLRDecoderProperty.FRC_VARIANCE,
                    frc.name());
            frcVariance.put(frc, variance);
        }

        minimumAcceptedRating = OpenLRPropertyAccess.getIntegerPropertyValue(
                config, OpenLRDecoderProperty.MIN_ACC_RATING);
        maxNumberRetries = OpenLRPropertyAccess.getIntegerPropertyValue(config,
                OpenLRDecoderProperty.MAX_NR_RETRIES);
        connectedRouteIncrease = OpenLRPropertyAccess.getFloatPropertyValue(
                config, OpenLRDecoderProperty.CONNECT_ROUTE_INC);
        dnpVariance = OpenLRPropertyAccess.getIntegerPropertyValue(config,
                OpenLRDecoderProperty.DNP_VARIANCE);
        maxBearingDiff = OpenLRPropertyAccess.getIntegerPropertyValueFromMap(config, OpenLRDecoderProperty.BEAR_RATING, "MaxBearingDiff");
        minBearingScore = OpenLRPropertyAccess.getIntegerPropertyValueFromMap(config, OpenLRDecoderProperty.BEAR_RATING, "MinScore");
        maxBearingScore = OpenLRPropertyAccess.getIntegerPropertyValueFromMap(config, OpenLRDecoderProperty.BEAR_RATING, "MaxScore");

        frcRating = new EnumMap<RatingCategory, Integer>(RatingCategory.class);
        fowRating = new EnumMap<RatingCategory, Integer>(RatingCategory.class);
        frcIntervals = new EnumMap<RatingCategory, Integer>(
                RatingCategory.class);
        for (RatingCategory cat : RatingCategory.values()) {
            fowRating.put(cat, OpenLRPropertyAccess
                    .getIntegerPropertyValueFromMap(config,
                            OpenLRDecoderProperty.FOW_RATING,
                            cat.getIdentifier()));
            frcRating.put(cat, OpenLRPropertyAccess
                    .getIntegerPropertyValueFromMap(config,
                            OpenLRDecoderProperty.FRC_RATING,
                            cat.getIdentifier()));
            if (cat != RatingCategory.POOR) {
                frcIntervals.put(cat, OpenLRPropertyAccess
                        .getIntegerPropertyValueFromMap(config,
                                OpenLRDecoderProperty.FRC_INTERVALS,
                                cat.getIdentifier()));
            }
        }

        calcAffectedLines = OpenLRPropertyAccess.getBooleanPropertyValue(
                config, OpenLRDecoderProperty.CALC_AFFECTED_LINES);
        linesDirectlyFactor = OpenLRPropertyAccess.getFloatPropertyValue(
                config, OpenLRDecoderProperty.LINES_DIRECTLY_FACTOR);
        compTime4Cache = OpenLRPropertyAccess.getIntegerPropertyValue(config,
                OpenLRDecoderProperty.COMP_TIME_4_CACHE);
    }

    /**
     * Gets the bearing distance.
     *
     * @return the bearingDistance
     */
    public final int getBearingDistance() {
        return bearingDistance;
    }

    /**
     * Gets the max node distance.
     *
     * @return the maxNodeDistance
     */
    public final int getMaxNodeDistance() {
        return maxNodeDistance;
    }

    /**
     * Gets the node factor.
     *
     * @return the nodeFactor
     */
    public final int getNodeFactor() {
        return nodeFactor;
    }

    /**
     * Gets the line factor.
     *
     * @return the lineFactor
     */
    public final int getLineFactor() {
        return lineFactor;
    }

    /**
     * Get the non-junction node factor. This is a value between 0 and 1 and is the scaling value to apply
     * during decoding to the score of nodes that are not junctions.
     *
     * By default the non-junction node factor is 1, meaning that non-junction nodes are not penalised.
     * To apply a penalty to non-junction nodes, reduce the non-junction node factor to a value under 1.
     *
     * @return the non-junction node factor
     */
    public final float getNonJunctionNodeFactor() {
        return nonJunctionNodeFactor;
    }

    /**
     * Gets the frc variance.
     *
     * @return the frcVariance
     */
    public final int getFrcVariance(FunctionalRoadClass frc) {
        return frcVariance.get(frc);
    }

    /**
     * Gets the minimum accepted rating.
     *
     * @return the minimumAcceptedRating
     */
    public final int getMinimumAcceptedRating() {
        return minimumAcceptedRating;
    }

    /**
     * Gets the max number retries.
     *
     * @return the maxNumberRetries
     */
    public final int getMaxNumberRetries() {
        return maxNumberRetries;
    }

    /**
     * Gets the connected route increase.
     *
     * @return the connectedRouteIncrease
     */
    public final float getConnectedRouteIncrease() {
        return connectedRouteIncrease;
    }

    /**
     * Gets the dnp variance.
     *
     * @return the dnpVariance
     */
    public final int getDnpVariance() {
        return dnpVariance;
    }

    /**
     * Gets the max bearing diff.
     *
     * @return the maxBearingDiff
     */
    public final int getMaxBearingDiff() {
        return maxBearingDiff;
    }

    /**
     * Gets the min bearing score.
     *
     * @return the maxBearingDiff
     */
    public final int getMinBearingScore() {
        return minBearingScore;
    }

    /**
     * Gets the max bearing score.
     *
     * @return the maxBearingDiff
     */
    public final int getMaxBearingScore() {
        return maxBearingScore;
    }

    /**
     * Gets the frc rating.
     *
     * @param cat
     *            the cat
     * @return the frcRating
     */
    public final int getFrcRating(final RatingCategory cat) {
        return frcRating.get(cat);
    }

    /**
     * Gets the frc intervals.
     *
     * @param interval
     *            the interval
     * @return the frcIntervals
     */
    public final int getFrcIntervals(final RatingCategory interval) {
        return frcIntervals.get(interval);
    }

    /**
     * Gets the fow rating.
     *
     * @param cat
     *            the cat
     * @return the fowRating
     */
    public final int getFowRating(final RatingCategory cat) {
        return fowRating.get(cat);
    }


    /**
     * Checks if is calc affected lines.
     *
     * @return the calcAffectedLines
     */
    public final boolean isCalcAffectedLines() {
        return calcAffectedLines;
    }

    /**
     * Gets the lines directly factor.
     *
     * @return the linesDirectlyFactor
     */
    public final float getLinesDirectlyFactor() {
        return linesDirectlyFactor;
    }

    /**
     * Gets the comp time4 cache.
     *
     * @return the compTime4Cache
     */
    public final int getCompTime4Cache() {
        return compTime4Cache;
    }

}
