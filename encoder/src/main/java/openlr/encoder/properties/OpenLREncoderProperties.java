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
package openlr.encoder.properties;

import openlr.OpenLRProcessingException;
import openlr.PhysicalEncoder;
import openlr.encoder.OpenLREncoderProcessingException;
import openlr.properties.OpenLRPropertyAccess;
import org.apache.commons.configuration.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Class OpenLREncoderProperties.
 */
public class OpenLREncoderProperties {

    /** The bearing distance. */
    private final int bearingDistance;

    /** The maximum distance lrp. */
    private final int maximumDistanceLRP;

    /** The check turn restrictions. */
    private final boolean checkTurnRestrictions;

    /** The physical format version. */
    private final Map<String, Integer> physicalFormatVersion;

    /** The comp time4 cache. */
    private final int compTime4Cache;

    private Boolean insertLrpAtAlternatePath;

    private float alternatePathRelativeThreshold;

    /**
     * Instantiates a new open lr encoder properties.
     *
     * @param config
     *            the config
     * @param physEncoders
     *            the phys encoders
     * @throws OpenLRProcessingException
     *             the open lr processing exception
     */
    public OpenLREncoderProperties(final Configuration config,
                                   final List<PhysicalEncoder> physEncoders)
            throws OpenLRProcessingException {
        bearingDistance = OpenLRPropertyAccess.getIntegerPropertyValue(config,
                OpenLREncoderProperty.BEAR_DIST);

        maximumDistanceLRP = OpenLRPropertyAccess.getIntegerPropertyValue(
                config, OpenLREncoderProperty.MAX_DIST_LRP);

        checkTurnRestrictions = OpenLRPropertyAccess.getBooleanPropertyValue(
                config, OpenLREncoderProperty.TURN_RESTRICTION_CHECK);

        physicalFormatVersion = new HashMap<String, Integer>();
        for (PhysicalEncoder pEnc : physEncoders) {
            String id = pEnc.getDataFormatIdentifier();
            int version = OpenLRPropertyAccess.getIntegerPropertyValueById(
                    config, OpenLREncoderProperty.PHYSICAL_FORMAT_VERSION, id);
            physicalFormatVersion.put(id, version);
        }

        compTime4Cache = OpenLRPropertyAccess.getIntegerPropertyValue(config,
                OpenLREncoderProperty.COMP_TIME_4_CACHE);

        insertLrpAtAlternatePath = OpenLRPropertyAccess.getBooleanPropertyValue(config,OpenLREncoderProperty.LRP_ALTERNATIVE_PATH);

        alternatePathRelativeThreshold = OpenLRPropertyAccess.getFloatPropertyValue(config,OpenLREncoderProperty.ALTERNATIVE_PATH_RELATIVE_THRESHOLD);
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
     * Gets the maximum distance lrp.
     *
     * @return the maximumDistanceLRP
     */
    public final int getMaximumDistanceLRP() {
        return maximumDistanceLRP;
    }

    /**
     * Checks if is check turn restrictions.
     *
     * @return the checkTurnRestrictions
     */
    public final boolean isCheckTurnRestrictions() {
        return checkTurnRestrictions;
    }

    /**
     * Gets the physical format version for a given identifier.
     *
     * @param id the identifier
     * @return the physicalFormatVersion or -1 if no specific version has been defined
     */
    public final int getPhysicalFormatVersion(final String id) {
        if (physicalFormatVersion.containsKey(id)) {
            return physicalFormatVersion.get(id);
        }
        return -1;
    }

    /**
     * Gets the comp time4 cache.
     *
     * @return the compTime4Cache
     */
    public final int getCompTime4Cache() {
        return compTime4Cache;
    }


    public final boolean insertLrpForAlternatePath(){
        return insertLrpAtAlternatePath;
    }

    public final float getAlternatePathRelativeThreshold(){
            return alternatePathRelativeThreshold;
    }

}
