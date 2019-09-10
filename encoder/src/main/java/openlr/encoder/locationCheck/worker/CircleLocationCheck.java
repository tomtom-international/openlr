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
package openlr.encoder.locationCheck.worker;

import openlr.OpenLRProcessingException;
import openlr.encoder.EncoderReturnCode;
import openlr.encoder.locationCheck.CheckResult;
import openlr.encoder.locationCheck.LocationCheck;
import openlr.encoder.properties.OpenLREncoderProperties;
import openlr.location.Location;
import openlr.map.MapDatabase;
import org.apache.log4j.Logger;

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
public class CircleLocationCheck extends LocationCheck {

    /** The Constant MAX_RADIUS. */
    private static final long MAX_RADIUS = (long) Math.pow(2, 32);

    /** The Constant LOG. */
    private static final Logger LOG = Logger
            .getLogger(CircleLocationCheck.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public final CheckResult check(final OpenLREncoderProperties properties,
                                   final MapDatabase mapDB, final Location location) {
        if (location.getRadius() <= 0) {
            LOG.error("invalid radius");
            return new CheckResult(EncoderReturnCode.INVALID_RADIUS);
        }
        if (location.getRadius() > MAX_RADIUS) {
            LOG.error("invalid radius");
            return new CheckResult(EncoderReturnCode.INVALID_RADIUS);
        }
        if (!checkCoordinateBounds(location.getCenterPoint().getLongitudeDeg(),
                location.getCenterPoint().getLatitudeDeg())) {
            LOG.error("invalid center coordinates");
            return new CheckResult(EncoderReturnCode.COORDINATES_OUT_OF_BOUNDS);
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
