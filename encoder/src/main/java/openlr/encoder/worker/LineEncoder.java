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
package openlr.encoder.worker;

import openlr.OpenLRProcessingException;
import openlr.encoder.data.AdjustOffsets;
import openlr.encoder.data.ExpansionHelper;
import openlr.encoder.data.LocRefData;
import openlr.encoder.locRefAdjust.LocationReferenceAdjust;
import openlr.encoder.locRefAdjust.worker.BasicLrpBasedLocRefAdjust;
import openlr.encoder.locationCheck.CheckResult;
import openlr.encoder.locationCheck.LocationCheck;
import openlr.encoder.locationCheck.worker.LineLocationCheck;
import openlr.encoder.properties.OpenLREncoderProperties;
import openlr.location.Location;
import openlr.map.MapDatabase;
import openlr.rawLocRef.RawInvalidLocRef;
import openlr.rawLocRef.RawLineLocRef;
import openlr.rawLocRef.RawLocationReference;

/**
 * The line encoder.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class LineEncoder extends AbstractEncoder {

    /**
     * {@inheritDoc}
     */
    @Override
    public final RawLocationReference doEncoding(final Location location,
                                                 final OpenLREncoderProperties prop, final MapDatabase mdb)
            throws OpenLRProcessingException {

        // check if the location is valid
        LocationCheck locCheck = new LineLocationCheck();
        CheckResult retCode = locCheck.check(prop, mdb,
                location);
        if (!retCode.checkPassed()) {
            RawLocationReference invalid = new RawInvalidLocRef(location.getID(), retCode.getError(), location.getLocationType());
            return invalid;
        }

        retCode = locCheck.checkOffsets(prop, location);
        if (!retCode.checkPassed()) {
            RawLocationReference invalid = new RawInvalidLocRef(location.getID(), retCode.getError(), location.getLocationType());
            return invalid;
        }

        LocRefData locRefData = new LocRefData(location);
        locRefData.setAdjustedLocation(AdjustOffsets.adjustOffsets(location, prop));
        locRefData.setExpansion(ExpansionHelper.createExpandedLocation(prop, mdb, locRefData));

        // initialize location reference data array and start encoding
        locRefData.setLocRefPoints(generateLocRef(locRefData, prop));

        // check if the location reference meets all restrictions and adjust
        // length values if necessary
        LocationReferenceAdjust locRefAdjust = new BasicLrpBasedLocRefAdjust();
        locRefAdjust.adjustLocationReference(prop, locRefData);

        RawLocationReference rawLocRef = new RawLineLocRef(
                locRefData.getID(), locRefData.getLocRefPoints(), locRefData.getOffsets());
        return rawLocRef;
    }

}
