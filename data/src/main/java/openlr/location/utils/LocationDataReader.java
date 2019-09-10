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
package openlr.location.utils;

import openlr.location.Location;
import openlr.location.utils.worker.*;
import openlr.map.MapDatabase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * d
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public final class LocationDataReader {

    /**
     * Utility class shall not be instantiated.
     */
    private LocationDataReader() {
        throw new UnsupportedOperationException();
    }

    /**
     * Skip line.
     *
     * @param line
     *            the line
     *
     * @return true, if skip line
     */
    private static boolean skipDataLine(final String line) {
        if (line.isEmpty() || line.startsWith(LocationDataConstants.COMMENT)) {
            return true;
        }
        return false;
    }

    /**
     * Load location data. This method catches all the
     * {@link LocationDataException}s that are possible during processing each
     * location entry (= each line) of the given file and increases the error
     * counter of the result object instead.
     *
     * @param dataFile
     *            the data file, must not be null
     * @param mdb
     *            the mdb, must not be null
     * @return always an instance of {@link LocationData} which can be empty if
     *         no locations could be found in the file
     * @throws IOException
     *             if an error occurred reading the file
     */
    public static LocationData loadLocationData(final File dataFile,
                                                final MapDatabase mdb) throws IOException {

        assert dataFile != null;
        assert mdb != null;

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(dataFile));
            return loadLocationData(br, mdb);
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    /**
     * Load location data from a reader. This method catches all the
     * {@link LocationDataException}s that are possible during processing each
     * location entry (= each line) of the given file and increases the error
     * counter of the result object instead.
     *
     * @param reader
     *            the reader that provides the location data
     * @param mdb
     *            the mdb, must not be null
     * @return always an instance of {@link LocationData} which can be empty if
     *         no locations could be found in the file
     * @throws IOException
     *             if an error occurred reading the reader
     */
    public static LocationData loadLocationData(final BufferedReader reader,
                                                final MapDatabase mdb) throws IOException {

        LocationData locData = new LocationData();
        String line = reader.readLine();
        while (line != null) {
            line = line.trim();
            if (!skipDataLine(line)) {
                try {
                    resolveDataLine(line, mdb, locData);
                } catch (LocationDataException e) {
                    locData.addError(e.getMessage());
                }
            }
            line = reader.readLine();
        }
        return locData;
    }

    /**
     * Resolve data line.
     *
     * @param line
     *            the line
     * @param mdb
     *            the mdb
     * @param locData
     *            the loc data
     * @throws LocationDataException
     *             the location data exception
     */
    public static void resolveDataLine(final String line,
                                       final MapDatabase mdb, final LocationData locData)
            throws LocationDataException {
        String[] parts = line.split(LocationDataConstants.REGEX_PART_DELIMITER);
        if (parts.length == LocationDataConstants.REQUIRED_PARTS) {
            String marker = parts[LocationDataConstants.MARKER_PART];
            String id = parts[LocationDataConstants.ID_PART];
            String features = parts[LocationDataConstants.FEATURES_PART];
            String[] featureList = features
                    .split(LocationDataConstants.REGEX_FEATURE_DELIMITER);
            AbstractRW rw;
            if (LocationDataConstants.LINE_MARKER.equals(marker)) {
                rw = new LineRW();
            } else if (LocationDataConstants.GEOCOORD_MARKER.equals(marker)) {
                rw = new GeoCoordRW();
            } else if (LocationDataConstants.POI_ACCESS_MARKER.equals(marker)) {
                rw = new PoiAccessRW();
            } else if (LocationDataConstants.POINT_ALONG_MARKER.equals(marker)) {
                rw = new PointAlongRW();
            } else if (LocationDataConstants.CIRCLE_MARKER.equals(marker)) {
                rw = new CircleRW();
            } else if (LocationDataConstants.RECTANGLE_MARKER.equals(marker)) {
                rw = new RectangleRW();
            } else if (LocationDataConstants.GRID_MARKER.equals(marker)) {
                rw = new GridRW();
            } else if (LocationDataConstants.POLYGON_MARKER.equals(marker)) {
                rw = new PolygonRW();
            } else if (LocationDataConstants.CLOSED_LINE_MARKER.equals(marker)) {
                rw = new ClosedLineRW();
            } else {
                throw new LocationDataException("Unknown location marker");
            }
            Location location = rw.readLocationString(id, featureList, mdb);
            locData.addLocation(location);
        } else {
            throw new LocationDataException("Invalid location string: " + line);
        }
    }
}
