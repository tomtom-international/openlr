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
package openlr.location.utils;

import openlr.LocationType;
import openlr.location.Location;
import openlr.location.utils.worker.*;

/**
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public final class LocationDataWriter {

    /**
     * Utility class shall not be instantiated.
     */
    private LocationDataWriter() {
        throw new UnsupportedOperationException();
    }


    /**
     * Creates the location string.
     *
     * @param location the location
     * @return the string
     */
    public static String createLocationString(
            final Location location) {
        LocationType lt = location.getLocationType();
        AbstractRW rw = null;
        switch (lt) {
            case LINE_LOCATION:
                rw = new LineRW();
                break;
            case CIRCLE:
                rw = new CircleRW();
                break;
            case CLOSED_LINE:
                rw = new ClosedLineRW();
                break;
            case GEO_COORDINATES:
                rw = new GeoCoordRW();
                break;
            case GRID:
                rw = new GridRW();
                break;
            case POI_WITH_ACCESS_POINT:
                rw = new PoiAccessRW();
                break;
            case POINT_ALONG_LINE:
                rw = new PointAlongRW();
                break;
            case POLYGON:
                rw = new PolygonRW();
                break;
            case RECTANGLE:
                rw = new RectangleRW();
                break;
            default:
            case UNKNOWN:
                return null;

        }
        return rw.createLocationString(location);
    }

}
