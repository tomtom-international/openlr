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
package openlr.encoder.database;

import openlr.encoder.LRDatabase;
import openlr.encoder.LocationReferenceHolder;
import openlr.location.Location;

/**
 * The Class LRDatabaseLRU implements the LRDatabase interface for storing
 * encoded location references. This implementation bases on a
 * "least recently used" cache and it will overwrite elements which are not used
 * for a long time first. The maximum number of database entries will be
 * configured during instantiation.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class LRDatabaseLRU implements LRDatabase {

    /** The "least recently used" cache. */
    private final LRUCache<Integer, LocationReferenceHolder> db;

    /**
     * Instantiates a new location reference database based on LRU (least
     * recently used) technique and with a maximum size of cacheSize entries.
     *
     * @param cacheSize
     *            the maximum cache size
     */
    public LRDatabaseLRU(final int cacheSize) {
        db = new LRUCache<Integer, LocationReferenceHolder>(cacheSize);
    }

    /** {@inheritDoc} */
    public final boolean containsLR(final Location loc) {
        if (loc == null || db.isEmpty()) {
            return false;
        }
        return db.containsKey(loc.hashCode());
    }

    /** {@inheritDoc} */
    public final void storeResult(final Location loc,
                                  final LocationReferenceHolder locref) {
        if (loc == null || locref == null) {
            return;
        }
        int key = loc.hashCode();
        db.put(key, locref);
    }

    /** {@inheritDoc} */
    public final LocationReferenceHolder getResult(final Location loc) {
        if (loc == null) {
            throw new IllegalArgumentException();
        }
        int key1 = loc.hashCode();
        return db.get(key1);
    }

    /** {@inheritDoc} */
    public final int getCacheSize() {
        return db.getMaximumSize();
    }

    /** {@inheritDoc} */
    public final int getCurrentNrEntries() {
        return db.size();
    }

    /** {@inheritDoc} */
    public final void clear() {
        db.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("max size: ").append(getCacheSize());
        sb.append(" #entries: ").append(getCurrentNrEntries());
        return sb.toString();
    }
}
