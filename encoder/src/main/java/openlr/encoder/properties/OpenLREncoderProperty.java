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

import openlr.properties.OpenLRProperty;
import openlr.properties.PropertyType;


/**
 *
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public enum OpenLREncoderProperty implements OpenLRProperty {

    /** The bearing distance */
    BEAR_DIST("BearingDistance", PropertyType.INTEGER, 20),

    /** the maximum distance between two LRP */
    MAX_DIST_LRP("MaximumDistanceLRP", PropertyType.INTEGER, 15000),

    /** check for turn restrictions */
    TURN_RESTRICTION_CHECK("CheckTurnRestrictions", PropertyType.BOOLEAN, Boolean.FALSE),

    /** The PHYSICA l_ forma t_ version. */
    PHYSICAL_FORMAT_VERSION("PhysicalFormatVersion", PropertyType.INTEGER_BY_ID, -1),

    /** The COM p_ tim e_4_ cache. */
    COMP_TIME_4_CACHE("CompTime4Cache", PropertyType.INTEGER, 0),

    /** Insert points if there is an alternate path*/
    LRP_ALTERNATIVE_PATH("LrpAlternativePath", PropertyType.BOOLEAN, false),

    /** Relative threshold for alternate routes **/
    ALTERNATIVE_PATH_RELATIVE_THRESHOLD("AlternatePathRelativeThreshold", PropertyType.FLOAT, 0.1f),

    /** Insert lrp at off ramp **/
    INSERT_EXTRA_LRP_TO_AVOID_OFF_RAMP("OfframpExtraLrp", PropertyType.BOOLEAN, false),

    SIBLING_BEARING_LIMIT("SiblingBearingLimit", PropertyType.FLOAT, 45.0f);

    /** The key. */
    private final String key;
    /** The class type. */
    private final PropertyType propertyType;
    /** The default value. */
    private final Object defaultValue;

    /**
     * Instantiates a new open lr encoder properties.
     *
     * @param s the s
     * @param c the c
     * @param d the d
     */
    private OpenLREncoderProperty(final String s, final PropertyType c, final Object d) {
        key = s;
        propertyType = c;
        defaultValue = d;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getKey() {
        return key;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PropertyType getPropertyType() {
        return propertyType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Object getDefault() {
        return defaultValue;
    }
}
