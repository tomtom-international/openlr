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
package openlr;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

/**
 * The enumeration LocationType specifies the different types of location
 * supported by the OpenLR method.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public enum LocationType {

    /** Location is UNKNOWN. */
    UNKNOWN,

    /** line location. */
    LINE_LOCATION,

    /** simple geo coordinates */
    GEO_COORDINATES,

    /** point along a line */
    POINT_ALONG_LINE,

    /** point of interest with an access point along a line */
    POI_WITH_ACCESS_POINT,

    /** circle area location */
    CIRCLE,

    /** polygon area location */
    POLYGON,

    /** closed line area location */
    CLOSED_LINE,

    /** rectangular area location */
    RECTANGLE,

    /** grid area location */
    GRID;

    /** The Constant AREA_LOCATIONS. */
    public static final EnumSet<LocationType> AREA_LOCATIONS = EnumSet.range(CIRCLE, GRID);
    /** The Constant POINTS_LOCATIONS. */
    public static final EnumSet<LocationType> POINTS_LOCATIONS = EnumSet.range(GEO_COORDINATES, POI_WITH_ACCESS_POINT);
    /** The Constant VALUES. */
    private static final List<LocationType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));

    /**
     * Gets the location types.
     *
     * @return the location types
     */
    public static List<LocationType> getLocationTypes() {
        return VALUES;
    }

}
