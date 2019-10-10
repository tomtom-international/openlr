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
package openlr.binary;

import openlr.LocationType;

import java.util.EnumSet;


/**
 * Defines constants for the OpenLR binary package.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public final class OpenLRBinaryConstants {

    /** The data format IDENTIFIER. */
    public static final String IDENTIFIER = "binary";

    /** used for rounding */
    public static final float ROUND_FACTOR = 0.5f;

    /** The Constant BITS_PER_BYTE. */
    public static final int BITS_PER_BYTE = 8;

    /**
     * The Constant DECA_MICRO_DEG_FACTOR is used to transform degree values
     * into deca-micro degrees.
     */
    public static final float DECA_MICRO_DEG_FACTOR = 100000.0f;

    /**
     * The Constant BIT24FACTOR is used for the conversion of lat/lon
     * coordinates into a 24bit accuracy.
     */
    public static final double BIT24FACTOR = 46603.377778;

    /**
     * The Constant BIT24FACTOR_REVERSED is used for the conversion of 24bit
     * lat/lon values back into prior accuracy.
     */
    public static final double BIT24FACTOR_REVERSED = 1 / BIT24FACTOR;

    /** The BEARING_SECTOR defines the length of a bearing interval. */
    public static final float BEARING_SECTOR = 11.25f;

    /** The LENGTH_INTERVAL defines the length of a dnp and offset interval. */
    public static final float LENGTH_INTERVAL = 58.6f;

    /** The IS_POINT defines a point location reference. */
    public static final int IS_POINT = 1;

    /** The IS_NOT_POINT indicates that the location reference is not a point location. */
    public static final int IS_NOT_POINT = 0;

    ///** The IS_AREA defines an area location reference. */
    //public static final int IS_AREA = 1;

    /**The AREA_CODE_CIRCLE defines the code for a cirle location reference. */
    public static final short AREA_CODE_CIRCLE = 0;

    /**The AREA_CODE_RECTANGLE defines the code for a rectangle location reference. */
    public static final short AREA_CODE_RECTANGLE = 2;

    /**The AREA_CODE_GRID defines the code for a grid location reference. */
    public static final short AREA_CODE_GRID = 2; //for BINARY_VERSION_3 the same as for AREA_CODE_RECTANGLE

    /**The AREA_CODE_POLYGON defines the code for a polygon location reference. */
    public static final short AREA_CODE_POLYGON = 1;

    /**The AREA_CODE_CLOSEDLINE defines the code for a closed line location reference. */
    public static final short AREA_CODE_CLOSEDLINE = 3;

    /**The AREA_CODE_NOAREA defines the code for a non-area location reference. */
    public static final short IS_NOT_AREA = 0;

    /** The HAS_ATTRIBUTES the existence of attribute information in the stream. */
    public static final int HAS_ATTRIBUTES = 1;

    /** The Constant HAS_NO_ATTRIBUTES. */
    public static final int HAS_NO_ATTRIBUTES = 0;

    /** The HEADER_SIZE defines the size [in bytes] of the header. */
    public static final int HEADER_SIZE = 1;

    /**
     * The FIRST_LRP_SIZE defines the size [in bytes] of the first location
     * reference point.
     */
    public static final int FIRST_LRP_SIZE = 9;

    /**
     * The LRP_SIZE defines the size [in bytes] of an intermediate location
     * reference point.
     */
    public static final int LRP_SIZE = 7;

    /**
     * The LAST_LRP_SIZE defines the size [in bytes] of the last location
     * reference point.
     */
    public static final int LAST_LRP_SIZE = 6;

    /** The Constant ABS_COORD_SIZE. */
    public static final int ABS_COORD_SIZE = 6;

    /** The Constant RELATIVE_OFFSET_LENGTH. */
    public static final float RELATIVE_OFFSET_LENGTH = 0.390625f;

    /**
     * The MIN_BYTES defines the minimum size [in bytes] of a binary location
     * reference.
     */
    public static final int MIN_BYTES_LINE_LOCATION = HEADER_SIZE + FIRST_LRP_SIZE
            + LAST_LRP_SIZE;

    //Added by DLR e.V. (RE)
    /**
     * The MIN_BYTES defines the minimum size [in bytes] of a binary closed line location
     * reference.
     */
    public static final int MIN_BYTES_CLOSED_LINE_LOCATION = HEADER_SIZE + FIRST_LRP_SIZE
            + 2;

    /** The Constant GEOCOORD_SIZE. */
    public static final int GEOCOORD_SIZE = HEADER_SIZE + ABS_COORD_SIZE;

    /** The Constant MIN_BYTES_POINT_LOCATION. */
    public static final int MIN_BYTES_POINT_LOCATION = GEOCOORD_SIZE;

    /** The Constant BINARY_VERSION_2. */
    public static final int BINARY_VERSION_2 = 2;

    /** The Constant BINARY_VERSION_3. */
    public static final int BINARY_VERSION_3 = 3;

    /** The LATEST_BINARY_VERSION defines the current version of the binary format. */
    public static final int LATEST_BINARY_VERSION = BINARY_VERSION_3;

    /** The HAS_OFFSET defines the existence of offset information. */
    public static final int HAS_OFFSET = 1;

    /** The Constant OFFSET_BUCKETS. */
    public static final int OFFSET_BUCKETS = 256;

    /** The Constant POINT_ALONG_LINE_SIZE. */
    public static final int POINT_ALONG_LINE_SIZE = HEADER_SIZE + FIRST_LRP_SIZE + LAST_LRP_SIZE;

    /** The Constant RELATIVE_COORD_SIZE. */
    public static final int RELATIVE_COORD_SIZE = 4;

    /** number of bits used for a small radius */
    public static final int SMALL_RADIUS_BITS = 8;

    /** number of bits used for a medium radius */
    public static final int MEDIUM_RADIUS_BITS = 16;

    /** number of bits used for a large radius */
    public static final int LARGE_RADIUS_BITS = 24;

    /** number of bits used for a small radius */
    public static final int EXTRA_LARGE_RADIUS_BITS = 32;

    /** The Constant DIMENSION_SIZE. */
    public static final int DIMENSION_SIZE = 2;

    /** The Constant RECTANGLE_SIZE. */
    public static final int RECTANGLE_SIZE = HEADER_SIZE + ABS_COORD_SIZE + RELATIVE_COORD_SIZE;

    /** The Constant LARGE_RECTANGLE_SIZE. */
    public static final int LARGE_RECTANGLE_SIZE = HEADER_SIZE + ABS_COORD_SIZE + ABS_COORD_SIZE;

    /** The Constant GRID_SIZE. */
    public static final int GRID_SIZE = RECTANGLE_SIZE + 2 * DIMENSION_SIZE;

    /** The Constant LARGE_GRID_SIZE. */
    public static final int LARGE_GRID_SIZE = LARGE_RECTANGLE_SIZE + 2 * DIMENSION_SIZE;

    /** The Constant MIN_BYTES_POLYGON. */
    public static final int MIN_BYTES_POLYGON = HEADER_SIZE + ABS_COORD_SIZE + 2 * RELATIVE_COORD_SIZE;

    /** The Constant POINT_OFFSET_SIZE. */
    public static final int POINT_OFFSET_SIZE = 1;

    /** The Constant POINT_WITH_ACCESS_SIZE. */
    public static final int POINT_WITH_ACCESS_SIZE = HEADER_SIZE + FIRST_LRP_SIZE + LAST_LRP_SIZE + RELATIVE_COORD_SIZE;

    /** The Constant POINT_LOCATION_VERSION. */
    public static final int POINT_LOCATION_VERSION = 3;

    /** The Constant POINT_LOCATION_TYPES. */
    public static final EnumSet<LocationType> POINT_LOCATION_TYPES = EnumSet.of(LocationType.GEO_COORDINATES, LocationType.POI_WITH_ACCESS_POINT, LocationType.POINT_ALONG_LINE);

    /** The Constant AREA_LOCATION_VERSION. */
    public static final int AREA_LOCATION_VERSION = 3;

    /** The Constant AREA_LOCATION_TYPES. */
    public static final EnumSet<LocationType> AREA_LOCATION_TYPES = EnumSet.of(LocationType.CIRCLE, LocationType.GRID, LocationType.CLOSED_LINE, LocationType.RECTANGLE, LocationType.POLYGON);


    /**
     * Utility class shall not be instantiated.
     */
    private OpenLRBinaryConstants() {
        throw new UnsupportedOperationException();
    }

}
