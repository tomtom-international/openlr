/**
 * Licensed to the TomTom International B.V. under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  TomTom International B.V.
 * licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
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
package openlr.encoder;

import openlr.location.Location;

/**
 * The Interface LRDatabase defines methods for a database storing locations and their location 
 * references. This database shall avoid encoding the same location twice. It is assumed that a
 * fast database implementation will speed up the response time of encoding a location if the 
 * location reference is already known.
 * 
 * The database shall be able to store a pre-defined number of location / location reference pairs
 * in its cache. 
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public interface LRDatabase {


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
     * Checks if the location is already stored in the database.
     * 
     * @param loc the location
     * 
     * @return true, if the location is stored in the database
     */
    boolean containsLR(Location loc);
    
    /**
     * Gets the location reference for the location
     * 
     * @param loc the location
     * 
     * @return the location reference
     */
    LocationReferenceHolder getResult(Location loc);
    
    /**
     * Store the location and its location reference
     * 
     * @param loc the location
     * @param locref the location reference
     */
    void storeResult(Location loc, LocationReferenceHolder locref);
}
