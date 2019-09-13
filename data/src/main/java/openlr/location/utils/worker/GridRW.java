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
import openlr.map.InvalidMapDataException;
import openlr.map.MapDatabase;

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
public class GridRW extends AbstractRW {

    /** The Constant GRID_INDEX. */
    private static final int GRID_LEFT_LON_INDEX = 0;

    /** The Constant GRID_LEFT_LAT_INDEX. */
    private static final int GRID_LEFT_LAT_INDEX = 1;

    /** The Constant GRID_RIGHT_LON_INDEX. */
    private static final int GRID_RIGHT_LON_INDEX = 2;

    /** The Constant GRID_RIGHT_LAT_INDEX. */
    private static final int GRID_RIGHT_LAT_INDEX = 3;

    /** The Constant GRID_ROWS_INDEX. */
    private static final int GRID_ROWS_INDEX = 4;

    /** The Constant GRID_COLUMNS_INDEX. */
    private static final int GRID_COLUMNS_INDEX = 5;

    /**
     * {@inheritDoc}
     */
    @Override
    public final String createLocationString(
            final Location location) {
        GeoCoordinates ll = location.getLowerLeftPoint();
        GeoCoordinates ur = location.getUpperRightPoint();
        StringBuilder sb = new StringBuilder();
        sb.append(LocationDataConstants.GRID_MARKER).append(
                LocationDataConstants.PART_DELIMITER);
        sb.append(location.getID());
        sb.append(LocationDataConstants.PART_DELIMITER);
        sb.append(ll.getLongitudeDeg());
        sb.append(LocationDataConstants.FEATURE_DELIMITER);
        sb.append(ll.getLatitudeDeg());
        sb.append(LocationDataConstants.FEATURE_DELIMITER);
        sb.append(ur.getLongitudeDeg());
        sb.append(LocationDataConstants.FEATURE_DELIMITER);
        sb.append(ur.getLatitudeDeg());
        sb.append(LocationDataConstants.FEATURE_DELIMITER);
        sb.append(location.getNumberOfRows());
        sb.append(LocationDataConstants.FEATURE_DELIMITER);
        sb.append(location.getNumberOfColumns());
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

        if (features.length < GRID_COLUMNS_INDEX + 1) {
            throw new LocationDataException(id
                    + ": Not enough attributes for grid");
        }
        try {
            double leftmostLonDeg = Double
                    .parseDouble(features[GRID_LEFT_LON_INDEX]);
            double leftmostLatDeg = Double
                    .parseDouble(features[GRID_LEFT_LAT_INDEX]);
            double rightmostLonDeg = Double
                    .parseDouble(features[GRID_RIGHT_LON_INDEX]);
            double rightmostLatDeg = Double
                    .parseDouble(features[GRID_RIGHT_LAT_INDEX]);

            int nrows = Integer.parseInt(features[GRID_ROWS_INDEX]);
            int ncolumns = Integer.parseInt(features[GRID_COLUMNS_INDEX]);

            return LocationFactory.createGridLocationFromBasisCell(id,
                    leftmostLonDeg, leftmostLatDeg, rightmostLonDeg,
                    rightmostLatDeg, ncolumns, nrows);
        } catch (InvalidMapDataException e) {
            throw new LocationDataException(e);
        } catch (NumberFormatException e) {
            throw new LocationDataException(id + ": Wrong coordinate format", e);
        }

    }

}
