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
package openlr.xml.decoder;

import openlr.LocationReferencePoint;
import openlr.LocationType;
import openlr.Offsets;
import openlr.PhysicalFormatException;
import openlr.location.data.Orientation;
import openlr.location.data.SideOfRoad;
import openlr.rawLocRef.RawInvalidLocRef;
import openlr.rawLocRef.RawLocationReference;
import openlr.rawLocRef.RawPointAlongLocRef;
import openlr.xml.OpenLRXMLException;
import openlr.xml.OpenLRXMLException.XMLErrorType;
import openlr.xml.XmlReturnCode;
import openlr.xml.generated.PointAlongLine;

/**
 * The decoder for the point along line location type.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class PointAlongDecoder extends AbstractDecoder {

    /**
     * {@inheritDoc}
     */
    @Override
    public final RawLocationReference decodeData(final String id,
                                                 final Object data) throws PhysicalFormatException {
        if (!(data instanceof PointAlongLine)) {
            throw new OpenLRXMLException(XMLErrorType.DATA_ERROR,
                    "incorrect data class");
        }
        PointAlongLine pal = (PointAlongLine) data;
        openlr.xml.generated.LocationReferencePoint startLRP = pal
                .getLocationReferencePoint();
        openlr.xml.generated.LastLocationReferencePoint endLRP = pal
                .getLastLocationReferencePoint();
        if (startLRP == null || endLRP == null) {
            return new RawInvalidLocRef(id,
                    XmlReturnCode.INVALID_NUMBER_OF_LRP,
                    LocationType.POINT_ALONG_LINE);
        }
        Offsets offsets = readOffsets(pal.getOffsets());
        LocationReferencePoint lrp01 = createLRP(1, startLRP);
        LocationReferencePoint lrp02 = createLastLRP(2, endLRP);

        SideOfRoad sideOfRoad = SideOfRoad.getDefault();
        if (pal.getSideOfRoad() != null) {
            sideOfRoad = resolveSideOfRoad(pal.getSideOfRoad());
        }
        Orientation orientation = Orientation.getDefault();
        if (pal.getOrientation() != null) {
            orientation = resolveOrientation(pal.getOrientation());
        }
        RawLocationReference rawLocRef = new RawPointAlongLocRef(id, lrp01, lrp02,
                offsets, sideOfRoad, orientation);
        return rawLocRef;
    }

}
