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
package openlr.collection;

import cern.colt.function.LongProcedure;
import cern.colt.map.AbstractMap;
import cern.colt.map.HashFunctions;

/**
 *
 * Overrides many methods for performance reasons only.
 *
 * @author TomTom International B.V.
 */
public abstract class OpenAbstractCollection extends AbstractMap {

    /** The Constant FREE. */
    protected static final byte FREE = 0;
    /** The Constant FULL. */
    protected static final byte FULL = 1;
    /** The Constant REMOVED. */
    protected static final byte REMOVED = 2;
    /**
     * serial ID.
     */
    private static final long serialVersionUID = 5163029925978869924L;
    /** The Constant HASH_BIT. */
    private static final int HASH_BIT = 0x7FFFFFFF;
    /** The Constant CAPACITY_FACTOR. */
    private static final float CAPACITY_FACTOR = 1.2f;
    /**
     * The hash table keys.
     *
     * @serial
     */
    protected long[] table;

    /**
     * The state of each hash table entry (FREE, FULL, REMOVED).
     *
     * @serial
     */
    protected byte[] state;

    /**
     * The number of table entries in state==FREE.
     *
     * @serial
     */
    protected int freeEntries;

    /**
     * Returns <tt>true</tt> if the receiver contains the specified key.
     *
     * @param key
     *            the key
     * @return <tt>true</tt> if the receiver contains the specified key.
     */
    public final boolean containsKey(final long key) {
        return indexOfKey(key) >= 0;
    }

    /**
     * Ensures that the receiver can hold at least the specified number of
     * associations without needing to allocate new internal memory. If
     * necessary, allocates new internal memory and increases the capacity of
     * the receiver.
     * <p>
     * This method never need be called; it is for performance tuning only.
     * Calling this method before <tt>put()</tt>ing a large number of
     * associations boosts performance, because the receiver will grow only once
     * instead of potentially many times and hash collisions get less probable.
     *
     * @param minCapacity
     *            the desired minimum capacity.
     */
    @Override
    public final void ensureCapacity(final int minCapacity) {
        if (table.length < minCapacity) {
            int newCapacity = nextPrime(minCapacity);
            rehash(newCapacity);
        }
    }

    /**
     * Applies a procedure to each key of the receiver, if any. Note: Iterates
     * over the keys in no particular order. Subclasses can define a particular
     * order, for example, "sorted by key". All methods which <i>can</i> be
     * expressed in terms of this method (most methods can) <i>must
     * guarantee</i> to use the <i>same</i> order defined by this method, even
     * if it is no particular order. This is necessary so that, for example,
     * methods <tt>keys</tt> and <tt>values</tt> will yield association pairs,
     * not two uncorrelated lists.
     *
     * @param procedure
     *            the procedure to be applied. Stops iteration if the procedure
     *            returns <tt>false</tt>, otherwise continues.
     * @return <tt>false</tt> if the procedure stopped before all keys where
     *         iterated over, <tt>true</tt> otherwise.
     */
    public final boolean forEachKey(final LongProcedure procedure) {
        for (int i = table.length; i-- > 0; ) {
            if (state[i] == FULL && !procedure.apply(table[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param key
     *            the key to be added to the receiver.
     * @return the index where the key would need to be inserted, if it is not
     *         already contained. Returns -index-1 if the key is already
     *         contained at slot index. Therefore, if the returned index < 0,
     *         then it is already contained at slot -index-1. If the returned
     *         index >= 0, then it is NOT already contained and should be
     *         inserted at slot index.
     */
    protected final int indexOfInsertion(final long key) {
        final long[] tab = table;
        final byte[] stat = state;
        final int length = tab.length;

        final int hash = HashFunctions.hash(key) & HASH_BIT;
        int i = hash % length;
        int decrement = hash % (length - 2); // double hashing, see
        // http://www.eece.unm.edu/faculty/heileman/hash/node4.html
        // int decrement = (hash / length) % length;
        if (decrement == 0) {
            decrement = 1;
        }

        // stop if we find a removed or free slot, or if we find the key itself
        // do NOT skip over removed slots (yes, open addressing is like that...)
        while (stat[i] == FULL && tab[i] != key) {
            i -= decrement;
            // hashCollisions++;
            if (i < 0) {
                i += length;
            }
        }

        if (stat[i] == REMOVED) {
            // stop if we find a free slot, or if we find the key itself.
            // do skip over removed slots (yes, open addressing is like that...)
            // assertion: there is at least one FREE slot.
            int j = i;
            while (stat[i] != FREE && (stat[i] == REMOVED || tab[i] != key)) {
                i -= decrement;
                // hashCollisions++;
                if (i < 0) {
                    i += length;
                }
            }
            if (stat[i] == FREE) {
                i = j;
            }
        }

        if (stat[i] == FULL) {
            // key already contained at slot i.
            // return a negative number identifying the slot.
            return -i - 1;
        }
        // not already contained, should be inserted at slot i.
        // return a number >= 0 identifying the slot.
        return i;
    }

    /**
     * @param key
     *            the key to be searched in the receiver.
     * @return the index where the key is contained in the receiver, returns -1
     *         if the key was not found.
     */
    protected final int indexOfKey(final long key) {
        final long[] tab = table;
        final byte[] stat = state;
        final int length = tab.length;

        final int hash = HashFunctions.hash(key) & HASH_BIT;
        int i = hash % length;
        int decrement = hash % (length - 2); // double hashing, see
        // http://www.eece.unm.edu/faculty/heileman/hash/node4.html
        // int decrement = (hash / length) % length;
        if (decrement == 0) {
            decrement = 1;
        }

        // stop if we find a free slot, or if we find the key itself.
        // do skip over removed slots (yes, open addressing is like that...)
        while (stat[i] != FREE && (stat[i] == REMOVED || tab[i] != key)) {
            i -= decrement;
            // hashCollisions++;
            if (i < 0) {
                i += length;
            }
        }

        if (stat[i] == FREE) {
            return -1; // not found
        }
        return i; // found, return index where key is contained
    }

    /**
     * Trims the capacity of the receiver to be the receiver's current size.
     * Releases any superfluous internal memory. An application can use this
     * operation to minimize the storage of the receiver.
     */
    @Override
    public final void trimToSize() {
        // * 1.2 because open addressing's performance exponentially degrades
        // beyond that point
        // so that even rehashing the table can take very long
        int newCapacity = nextPrime((int) (1 + CAPACITY_FACTOR * size()));
        if (table.length > newCapacity) {
            rehash(newCapacity);
        }
    }

    /**
     * Rehash.
     *
     * @param newCapacity the new capacity
     */
    abstract void rehash(final int newCapacity);

}
