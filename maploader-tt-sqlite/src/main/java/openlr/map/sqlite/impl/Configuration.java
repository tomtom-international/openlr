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
package openlr.map.sqlite.impl;

/**
 * Contains configuration parameters and a routine to read the database schema
 * properties file.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public final class Configuration {

    /**
     * The Constant BBOX_MINX.
     */
    public static final int BBOX_MINX = 1;

    /**
     * The Constant BBOX_MINY.
     */
    public static final int BBOX_MINY = 2;

    /**
     * The Constant BBOX_MAXX.
     */
    public static final int BBOX_MAXX = 3;

    /**
     * The Constant BBOX_MAXY.
     */
    public static final int BBOX_MAXY = 4;

    /**
     * Load factor of internal feature caches.
     */
    public static final float CACHE_LOAD_FACTOR = 0.75f;

    /**
     * The maximum number of elements allowed in internal feature caches.
     */
    public static final int CACHE_MAX_SIZE = 1000;

    /**
     * Initial size of internal feature caches.
     */
    public static final int CACHE_INITIAL_SIZE = (int) (CACHE_MAX_SIZE * CACHE_LOAD_FACTOR);

    /**
     * Default delay for logging properties file change monitoring.
     */
    public static final long LOGGING_WATCH_DELAY = 5000;

    /**
     * Defines whether turn restrictions are supported.
     */
    public static final boolean TURN_RESTRICTIONS_SUPPORTED = false;

    /**
     * Hides the constructor of this utility class.
     */
    private Configuration() {
        throw new UnsupportedOperationException();
    }

    /**
     * The Constant SQL_NODE_COUNT.
     */
    public static final String SQL_NODE_COUNT = "SELECT	Count(*) FROM Node";

    /**
     * The Constant SQL_SELECT_NODE.
     */
    public static final String SQL_SELECT_NODE = "SELECT Longitude, Latitude FROM Node WHERE Id = ?";

    /**
     * The Constant SQL_FIND_CLOSE_BY_NODE.
     */
    public static final String SQL_FIND_CLOSE_BY_NODE = "SELECT	Id FROM Node WHERE Longitude >= ? AND Latitude >= ? AND Longitude <= ? AND Latitude <= ?";

    /**
     * The Constant SQL_LINE_COUNT.
     */
    public static final String SQL_LINE_COUNT = "SELECT Count(*) FROM Line";

    /**
     * The Constant SQL_SELECT_LINE.
     */
    public static final String SQL_SELECT_LINE = "SELECT Start_Node_Id, End_Node_Id, Display_Name, Display_Name_LangCode, Length_Meters, FOW, FRC, Geom FROM Line WHERE Id = ?";

    /**
     * The Constant SQL_LINE_INCOMING.
     */
    public static final String SQL_LINE_INCOMING = "SELECT Id FROM Line WHERE End_Node_Id = ?";

    /**
     * The Constant SQL_LINE_OUTGOING.
     */
    public static final String SQL_LINE_OUTGOING = "SELECT Id FROM Line WHERE Start_Node_Id = ?";

    /**
     * The Constant SQL_FIND_CLOSE_BY_LINE.
     */
    public static final String SQL_FIND_CLOSE_BY_LINE = "SELECT Id FROM Line WHERE Max_Longitude >= ? AND Max_Latitude >= ? AND Min_Longitude <= ? AND Min_Latitude <= ?";

    /**
     * The Constant SQL_METADATA.
     */
    public static final String SQL_METADATA = "SELECT Map_Name, Map_Release, Map_Compile_Date, Map_Copyright_Owner, Min_Longitude, Max_Longitude, Min_Latitude, Max_Latitude FROM Metadata LIMIT 1";

}
