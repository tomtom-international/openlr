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
package openlr.decoder.database;

import openlr.decoder.LocationDatabase;
import openlr.location.Location;
import openlr.rawLocRef.RawLocationReference;

/**
 * The Class LocationDatabaseLRU implements the LocationDatabase interface for storing
 * decoded locations. This implementation bases on a
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
public class LocationDatabaseLRU implements LocationDatabase {

	/** The "least recently used" cache. */
	private final LRUCache<Integer, Location> db;

	/**
	 * Instantiates a new location reference database based on LRU (least
	 * recently used) technique and with a maximum size of cacheSize entries.
	 * 
	 * @param cacheSize
	 *            the maximum cache size
	 */
	public LocationDatabaseLRU(final int cacheSize) {
		db = new LRUCache<Integer, Location>(cacheSize);
	}

	/**
	 * Contains lr.
	 * 
	 * @param locRef
	 *            the loc ref
	 * @return true, if successful {@inheritDoc}
	 */
	public final boolean containsLR(final RawLocationReference locRef) {
		if (locRef == null || db.isEmpty()) {
			return false;
		}
		return db.containsKey(locRef.hashCode());
	}

	/**
	 * Store result.
	 * 
	 * @param locRef
	 *            the loc ref
	 * @param loc
	 *            the loc {@inheritDoc}
	 */
	public final void storeResult(final RawLocationReference locRef,
			final Location loc) {
		if (loc == null || locRef == null) {
			return;
		}
		int key = locRef.hashCode();
		db.put(key, loc);
	}

	/**
	 * Gets the result.
	 * 
	 * @param locRef
	 *            the loc ref
	 * @return the result {@inheritDoc}
	 */
	public final Location getResult(final RawLocationReference locRef) {
		if (locRef == null) {
			throw new IllegalArgumentException();
		}
		int key1 = locRef.hashCode();
		return db.get(key1);
	}

	/**
	 * Gets the cache size.
	 * 
	 * @return the cache size {@inheritDoc}
	 */
	public final int getCacheSize() {
		return db.getMaximumSize();
	}

	/**
	 * Gets the current nr entries.
	 * 
	 * @return the current nr entries {@inheritDoc}
	 */
	public final int getCurrentNrEntries() {
		return db.size();
	}

	/**
	 * Clear.
	 * 
	 * {@inheritDoc}
	 */
	public final void clear() {
		db.clear();
	}

	/**
	 * To string.
	 * 
	 * @return the string {@inheritDoc}
	 */
	@Override
	public final String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("max size: ").append(getCacheSize());
		sb.append(" #entries: ").append(getCurrentNrEntries());
		return sb.toString();
	}

}
