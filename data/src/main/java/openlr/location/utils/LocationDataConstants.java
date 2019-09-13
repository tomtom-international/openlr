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
package openlr.location.utils;

/**
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public final class LocationDataConstants {

    /** The Constant COMMENT. */
    public static final String COMMENT = "#";

    /** The Constant GEOCOORD_MARKER. */
    public static final String GEOCOORD_MARKER = "GEO";

    /** The Constant POINT_ALONG_MARKER. */
    public static final String POINT_ALONG_MARKER = "PAL";

    /** The Constant POI_ACCESS_MARKER. */
    public static final String POI_ACCESS_MARKER = "POI";

    /** The Constant LINE_MARKER. */
    public static final String LINE_MARKER = "LIN";

    /** The Constant CIRCLE_MARKER. */
    public static final String CIRCLE_MARKER = "CIR";

    /** The Constant RECTANGLE_MARKER. */
    public static final String RECTANGLE_MARKER = "REC";

    /** The Constant GRID_MARKER. */
    public static final String GRID_MARKER = "GRI";

    /** The Constant POLYGON_MARKER. */
    public static final String POLYGON_MARKER = "POL";

    /** The Constant CLOSED_LINE_MARKER. */
    public static final String CLOSED_LINE_MARKER = "CLL";

    /** The Constant PART_DELIMITER. */
    public static final String PART_DELIMITER = ";";

    /**
     * The regular expression used to split parts of a read location
     */
    public static final String REGEX_PART_DELIMITER = ";";

    /** The Constant FEATURE_DELIMITER. */
    public static final String FEATURE_DELIMITER = ",";

    /**
     * The regular expression used to split features of a read location
     */
    public static final String REGEX_FEATURE_DELIMITER = "\\s*,\\s*";

    /** The Constant REQUIRED_PARTS. */
    public static final int REQUIRED_PARTS = 3;

    /** The Constant MARKER_PART. */
    public static final int MARKER_PART = 0;

    /** The Constant ID_PART. */
    public static final int ID_PART = 1;

    /** The Constant FEATURES_PART. */
    public static final int FEATURES_PART = 2;


    /**
     * Utility class shall not be instantiated.
     */
    private LocationDataConstants() {
        throw new UnsupportedOperationException();
    }

}
