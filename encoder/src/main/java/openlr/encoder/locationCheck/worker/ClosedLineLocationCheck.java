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
package openlr.encoder.locationCheck.worker;

import openlr.OpenLRProcessingException;
import openlr.encoder.EncoderReturnCode;
import openlr.encoder.locationCheck.CheckResult;
import openlr.encoder.locationCheck.LocationCheck;
import openlr.encoder.properties.OpenLREncoderProperties;
import openlr.location.Location;
import openlr.map.MapDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
public class ClosedLineLocationCheck extends LocationCheck {
    /** The Constant LOG. */
    private static final Logger LOG = LogManager.getLogger(LineLocationCheck.class);

    /**
     * Check.
     *
     * @param mapDB the map db
     * @param location the location
     * @param properties the properties
     * @return the check result
     * @throws OpenLRProcessingException the open lr processing exception
     */
    public final CheckResult check(final OpenLREncoderProperties properties, final MapDatabase mapDB,
                                   final Location location) throws OpenLRProcessingException {
        if (mapDB == null) {
            LOG.error("map database is empty");
            return new CheckResult(EncoderReturnCode.MAP_DATABASE_IS_EMPTY);
        }

        if (location.getLocationLines() == null
                || location.getLocationLines().isEmpty()) {
            LOG.error("location is empty");
            return new CheckResult(EncoderReturnCode.LOCATION_IS_EMPTY);
        }

        /*
         * check connectivity (DLR e.V. (RE) added check for first line
         * following last line for closed line locations)
         */
        if (!checkLocationConnection(location.getLocationLines())) {
            LOG.error("location " + location.getID() + " is not connected");
            return new CheckResult(EncoderReturnCode.LOCATION_NOT_CONNECTED);
        }
        if (!isPathClosed(location.getLocationLines())) {
            LOG.error("location " + location.getID() + " is not connected");
            return new CheckResult(EncoderReturnCode.LOCATION_NOT_CONNECTED);
        }

        /* check for turn restrictions */
        if (properties.isCheckTurnRestrictions()
                && checkTurnRestrictionClosedLine(mapDB, location.getLocationLines())) {
            LOG.error("turn restriction conflict (" + location.getID() + ")");
            return new CheckResult(
                    EncoderReturnCode.LOCATION_CONTAINS_TURN_RESTRICTION);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("location check passed");
        }
        return CheckResult.PASSED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final CheckResult checkOffsets(final OpenLREncoderProperties properties,
                                          final Location location) throws OpenLRProcessingException {
        return CheckResult.PASSED;
    }


}
