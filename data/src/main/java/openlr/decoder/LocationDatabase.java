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
package openlr.decoder;

import openlr.location.Location;
import openlr.rawLocRef.RawLocationReference;

/**
 * The Interface LocationDatabase defines methods for a database storing location references and
 * their locations. This database shall avoid decoding the same location reference twice. It is 
 * assumed that a fast database implementation will speed up the response time of decoding a 
 * location reference if the location is already known.
 *
 * The database shall be able to store a pre-defined number of location reference / location pairs
 * in its cache. 
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public interface LocationDatabase {


    /**
     * Gets the maximum cache size.
     *
     * @return the maximum cache size
     */
    int getCacheSize();

    /**
     * Gets the current number of entries in the database.
     *
     * @return the current number of entries in the database
     */
    int getCurrentNrEntries();

    /**
     * Clear the database cache.
     */
    void clear();

    /**
     * Checks if the location reference is already stored in the database.
     *
     * @param locRef the location reference
     *
     * @return true, if the location reference is stored in the database
     */
    boolean containsLR(RawLocationReference locRef);

    /**
     * Gets the location for the location reference. The method returns null if
     * no Location is stored for the RawLocationReference
     *
     * @param locRef the location reference
     *
     * @return the location 
     */
    Location getResult(RawLocationReference locRef);

    /**
     * Store the location and its location reference
     *
     * @param loc the location
     * @param locRef the location reference
     */
    void storeResult(RawLocationReference locRef, Location loc);
}
