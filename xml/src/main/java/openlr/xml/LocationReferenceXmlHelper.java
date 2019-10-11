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
package openlr.xml;

import openlr.LocationType;
import openlr.xml.generated.*;

/**
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public final class LocationReferenceXmlHelper {


    /**
     * Utility class shall not be instantiated.
     */
    private LocationReferenceXmlHelper() {
        throw new UnsupportedOperationException();
    }

    /**
     * Resolve location type.
     *
     * @param xmlData the xml data
     * @return the location type
     */
    public static LocationType resolveLocationType(final OpenLR xmlData) {
        LocationType lt = LocationType.UNKNOWN;
        if (xmlData != null) {
            XMLLocationReference locref = xmlData.getXMLLocationReference();
            if (locref != null) {
                LineLocationReference linelocref = locref.getLineLocationReference();
                PointLocationReference pointlocref = locref.getPointLocationReference();
                AreaLocationReference areaLocRef = locref.getAreaLocationReference();
                if (linelocref != null) {
                    lt = LocationType.LINE_LOCATION;
                } else if (pointlocref != null) {
                    if (pointlocref.getGeoCoordinate() != null) {
                        lt = LocationType.GEO_COORDINATES;
                    } else if (pointlocref.getPointAlongLine() != null) {
                        lt = LocationType.POINT_ALONG_LINE;
                    } else if (pointlocref.getPoiWithAccessPoint() != null) {
                        lt = LocationType.POI_WITH_ACCESS_POINT;
                    }
                } else if (areaLocRef != null) {
                    if (areaLocRef.getCircleLocationReference() != null) {
                        lt = LocationType.CIRCLE;
                    } else if (areaLocRef.getClosedLineLocationReference() != null) {
                        lt = LocationType.CLOSED_LINE;
                    } else if (areaLocRef.getGridLocationReference() != null) {
                        lt = LocationType.GRID;
                    } else if (areaLocRef.getRectangleLocationReference() != null) {
                        lt = LocationType.RECTANGLE;
                    } else if (areaLocRef.getPolygonLocationReference() != null) {
                        lt = LocationType.POLYGON;
                    }
                }
            }
        }
        return lt;
    }

}
