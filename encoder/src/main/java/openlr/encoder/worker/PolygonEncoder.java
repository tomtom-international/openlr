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
package openlr.encoder.worker;

/**
 * The encoder for the polygon location type.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author DLR e.V. (RE)
 */

import openlr.OpenLRProcessingException;
import openlr.encoder.locationCheck.CheckResult;
import openlr.encoder.locationCheck.LocationCheck;
import openlr.encoder.locationCheck.worker.PolygonLocationCheck;
import openlr.encoder.properties.OpenLREncoderProperties;
import openlr.location.Location;
import openlr.map.GeoCoordinates;
import openlr.map.MapDatabase;
import openlr.rawLocRef.RawInvalidLocRef;
import openlr.rawLocRef.RawLocationReference;
import openlr.rawLocRef.RawPolygonLocRef;

import java.util.List;

/**
 * The encoder for the polygon location type.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author DLR e.V. (RE)
 */
public class PolygonEncoder extends AbstractEncoder {

    /**
     * {@inheritDoc}
     */
    @Override
    public final RawLocationReference doEncoding(final Location location,
                                                 final OpenLREncoderProperties prop, final MapDatabase mdb) throws OpenLRProcessingException {

        // check if the location is valid
        LocationCheck locCheck = new PolygonLocationCheck();
        CheckResult retCode = locCheck.check(prop, mdb,
                location);
        if (!retCode.checkPassed()) {
            RawLocationReference invalid = new RawInvalidLocRef(location.getID(), retCode.getError(), location.getLocationType());
            return invalid;
        }

        List<? extends GeoCoordinates> cornerCoords = location.getCornerPoints();
        RawLocationReference rawLocRef = new RawPolygonLocRef(location.getID(), cornerCoords);
        return rawLocRef;
    }
}
