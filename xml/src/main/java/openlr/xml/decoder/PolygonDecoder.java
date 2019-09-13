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
package openlr.xml.decoder;

import openlr.LocationType;
import openlr.PhysicalFormatException;
import openlr.map.GeoCoordinates;
import openlr.rawLocRef.RawInvalidLocRef;
import openlr.rawLocRef.RawLocationReference;
import openlr.rawLocRef.RawPolygonLocRef;
import openlr.xml.OpenLRXMLException;
import openlr.xml.OpenLRXMLException.XMLErrorType;
import openlr.xml.XmlReturnCode;
import openlr.xml.generated.Coordinates;
import openlr.xml.generated.PolygonLocationReference;

import java.util.ArrayList;
import java.util.List;

/**
 * The decoder for the polygon area location type.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author DLR e.V. (RE)
 */
public class PolygonDecoder extends AbstractDecoder {

    /** The Constant MIN_NUMBER_CORNERS. */
    private static final int MIN_NUMBER_CORNERS = 3;

    /**
     * {@inheritDoc}
     */
    @Override
    public final RawLocationReference decodeData(final String id, final Object data)
            throws PhysicalFormatException {
        if (!(data instanceof PolygonLocationReference)) {
            throw new OpenLRXMLException(XMLErrorType.DATA_ERROR,
                    "incorrect data class");
        }
        PolygonLocationReference polygonLoc = (PolygonLocationReference) data;
        List<Coordinates> corners = polygonLoc.getPolygonCorners().getCoordinates();
        if (corners == null || corners.size() < MIN_NUMBER_CORNERS) {
            return new RawInvalidLocRef(id, XmlReturnCode.MISSING_POLYGON_DATA,
                    LocationType.POLYGON);
        }
        List<GeoCoordinates> cornerPoints = new ArrayList<GeoCoordinates>();
        for (Coordinates geoCoord : corners) {
            cornerPoints.add(createGeoCoord(geoCoord));
        }
        RawLocationReference rawLocRef = new RawPolygonLocRef(id,
                cornerPoints);

        return rawLocRef;
    }

}
