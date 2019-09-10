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
package openlr.encoder.database;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A LRU (least recently used) cache, based on a LinkedHashMap. This cache has
 * a fixed maximum number of elements and if the cache is full and another entry
 * shall be added, the LRU (least recently used) entry is dropped.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @param <K>
 *            the key of the entry
 * @param <V>
 *            the value of the entry
 *            <p>
 *
 * @author TomTom International B.V.
 */
public class LRUCache<K, V> {

    /** The Constant hashTableLoadFactor. */
    private static final float HASH_TABLE_LOAD_FACTOR = 0.75f;

    /** The internal map. */
    private LinkedHashMap<K, V> map;

    /** The maximum cache size. */
    private int cacheSize;

    /**
     * Creates a new LRU cache with maximum cacheSize.
     *
     * @param cacheSizeValue
     *            the maximum number of entries that will be kept in this cache.
     */
    public LRUCache(final int cacheSizeValue) {
        this.cacheSize = cacheSizeValue;
        int hashTableCapacity = (int) Math.ceil(cacheSizeValue
                / HASH_TABLE_LOAD_FACTOR) + 1;
        map = new LinkedHashMap<K, V>(hashTableCapacity,
                HASH_TABLE_LOAD_FACTOR, true) {
            // (an anonymous inner class)
            private static final long serialVersionUID = 1;

            @Override
            protected boolean removeEldestEntry(final Map.Entry<K, V> eldest) {
                // check if maximum size is exceeded and eldest entry needs to
                // be deleted
                return super.size() > LRUCache.this.cacheSize;
            }
        };
    }

    /**
     * Retrieves an entry from the cache.<br>
     * The retrieved entry becomes the MRU (most recently used) entry.
     *
     * @param key
     *            the key whose associated value is to be returned.
     *
     * @return the value associated to this key, or null if no value with this
     *         key exists in the cache.
     */
    public final synchronized V get(final K key) {
        return map.get(key);
    }

    /**
     * Adds an entry to this cache. If the cache is full, the LRU (least
     * recently used) entry is dropped.
     *
     * @param key
     *            the key with which the specified value is to be associated.
     * @param value
     *            a value to be associated with the specified key.
     */
    public final synchronized void put(final K key, final V value) {
        map.put(key, value);
    }

    /**
     * Clears the cache.
     */
    public final synchronized void clear() {
        map.clear();
    }

    /**
     * Checks if the cache is empty.
     *
     * @return true, if cache empty
     */
    public final synchronized boolean isEmpty() {
        return map.isEmpty();
    }

    /**
     * Checks if the key is used in the cache.
     *
     * @param key
     *            the key
     *
     * @return true, if key is used in the cache
     */
    public final synchronized boolean containsKey(final K key) {
        return map.containsKey(key);
    }

    /**
     * Returns the size of the cache.
     *
     * @return the size of the cache
     */
    public final synchronized int size() {
        return map.size();
    }

    /**
     * Gets the maximum cache size.
     *
     * @return the maximum cache size
     */
    public final synchronized int getMaximumSize() {
        return cacheSize;
    }

}
