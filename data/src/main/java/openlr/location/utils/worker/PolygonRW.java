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
package openlr.location.utils.worker;

import openlr.location.Location;
import openlr.location.LocationFactory;
import openlr.location.utils.LocationDataConstants;
import openlr.location.utils.LocationDataException;
import openlr.map.GeoCoordinates;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.InvalidMapDataException;
import openlr.map.MapDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Location reader and writer.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class PolygonRW extends AbstractRW {

    /**
     * {@inheritDoc}
     */
    @Override
    public final String createLocationString(
            final Location location) {
        StringBuilder sb = new StringBuilder();
        sb.append(LocationDataConstants.POLYGON_MARKER).append(
                LocationDataConstants.PART_DELIMITER);
        sb.append(location.getID());
        sb.append(LocationDataConstants.PART_DELIMITER);
        List<? extends GeoCoordinates> cornerPoints = location.getCornerPoints();
        for (int i = 0; i < cornerPoints.size(); i++) {
            sb.append(cornerPoints.get(i).getLongitudeDeg());
            sb.append(LocationDataConstants.FEATURE_DELIMITER);
            sb.append(cornerPoints.get(i).getLatitudeDeg());
            if (i != cornerPoints.size() - 1) {
                sb.append(LocationDataConstants.FEATURE_DELIMITER);
            }
        }
        sb.append("\n");
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Location readLocationString(final String id,
                                             final String[] features, final MapDatabase mdb)
            throws LocationDataException {
        List<GeoCoordinates> geoCornerPoints = new ArrayList<GeoCoordinates>();
        try {
            for (int i = 0; i < features.length; i++) {
                try {
                    double lon = Double.parseDouble(features[i]);
                    i = i + 1;
                    double lat = Double.parseDouble(features[i]);
                    GeoCoordinates g = new GeoCoordinatesImpl(lon, lat);
                    geoCornerPoints.add(g);
                } catch (NumberFormatException e) {
                    throw new LocationDataException(id + ": Wrong coordinate format", e);
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new LocationDataException(id + ": Not enough coordinates for rectangle");
                }
            }
        } catch (InvalidMapDataException e) {
            throw new LocationDataException(id + ": " + e.getMessage());
        }
        if (geoCornerPoints.isEmpty()) {
            throw new LocationDataException(id
                    + ": no valid corner points found");
        }
        return LocationFactory.createPolygonLocation(id, geoCornerPoints);
    }

}
