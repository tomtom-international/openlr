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
import openlr.map.Line;
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
public class PointAlongLocationCheck extends LocationCheck {

    /** The Constant LOG. */
    private static final Logger LOG = LogManager.getLogger(PointAlongLocationCheck.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public final CheckResult check(final OpenLREncoderProperties properties,
                                   final MapDatabase mapDB, final Location location) {
        if (mapDB == null) {
            LOG.error("map database is empty");
            return new CheckResult(EncoderReturnCode.MAP_DATABASE_IS_EMPTY);
        }
        Line l = location.getPoiLine();
        if (l == null) {
            LOG.error("no line found");
            return new CheckResult(
                    EncoderReturnCode.NO_LINE_FOR_POINT_LOCATION);
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
        Line l = location.getPoiLine();
        if (l == null) {
            LOG.error("no line found");
            return new CheckResult(EncoderReturnCode.NO_LINE_FOR_POINT_LOCATION);
        }
        int offsetValue = location.getPositiveOffset();
        if (offsetValue > l.getLineLength()) {
            LOG.error("offsets are longer than line");
            return new CheckResult(EncoderReturnCode.OFFSET_LINE_MISMATCH);
        }
        return CheckResult.PASSED;
    }

}
