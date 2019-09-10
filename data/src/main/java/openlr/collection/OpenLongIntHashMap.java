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
/*
 Copyright � 1999 CERN - European Organization for Nuclear Research.
 Permission to use, copy, modify, distribute and sell this software and its documentation for any purpose 
 is hereby granted without fee, provided that the above copyright notice appear in all copies and 
 that both that copyright notice and this permission notice appear in supporting documentation. 
 CERN makes no representations about the suitability of this software for any purpose. 
 It is provided "as is" without expressed or implied warranty.
 */
package openlr.collection;

import cern.colt.function.LongProcedure;
import cern.colt.list.ByteArrayList;
import cern.colt.list.IntArrayList;
import cern.colt.list.LongArrayList;
import cern.colt.map.PrimeFinder;

/**
 * Hash map holding (key,value) associations of type <tt>(long-->Object)</tt>;
 * Automatically grows and shrinks as needed; Implemented using open addressing
 * with double hashing. First see the <a href="package-summary.html">package
 * summary</a> and javadoc <a href="package-tree.html">tree view</a> to get the
 * broad picture.
 *
 * Overrides many methods for performance reasons only.
 *
 * @author wolfgang.hoschek@cern.ch
 * @version 1.0, 09/24/99
 * @see java.util.HashMap
 */
public class OpenLongIntHashMap extends OpenAbstractCollection {

    /**
     * serial ID
     */
    private static final long serialVersionUID = -2371346990798084253L;


    /**
     * The hash table values.
     *
     * @serial
     */
    private int[] values;


    /**
     * Constructs an empty map with default capacity and default load factors.
     */
    public OpenLongIntHashMap() {
        this(defaultCapacity);
    }

    /**
     * Constructs an empty map with the specified initial capacity and default
     * load factors.
     *
     * @param initialCapacity
     *            the initial capacity
     */
    public OpenLongIntHashMap(final int initialCapacity) {
        this(initialCapacity, defaultMinLoadFactor, defaultMaxLoadFactor);
    }

    /**
     * Constructs an empty map with the specified initial capacity and the
     * specified minimum and maximum load factor.
     *
     * @param initialCapacity
     *            the initial capacity
     * @param minLoadFactor
     *            the min load factor
     * @param maxLoadFactor
     *            the max load factor
     */
    public OpenLongIntHashMap(final int initialCapacity,
                              final double minLoadFactor, final double maxLoadFactor) {
        setUp(initialCapacity, minLoadFactor, maxLoadFactor);
    }

    /**
     * Removes all (key,value) associations from the receiver. Implicitly calls
     * <tt>trimToSize()</tt>.
     */
    @Override
    public final void clear() {
        new ByteArrayList(this.state).fillFromToWith(0, this.state.length - 1,
                FREE);
        new IntArrayList(values).fillFromToWith(0, state.length - 1, FREE); // delta

        this.distinct = 0;
        this.freeEntries = table.length; // delta
        trimToSize();
    }

    /**
     * Returns a deep copy of the receiver.
     *
     * @return a deep copy of the receiver.
     */
    @Override
    public final Object clone() {
        OpenLongIntHashMap copy = (OpenLongIntHashMap) super.clone();
        copy.table = copy.table.clone();
        copy.values = copy.values.clone();
        copy.state = copy.state.clone();
        return copy;
    }

    /**
     * Returns <tt>true</tt> if the receiver contains the specified value.
     *
     * @param value the value
     * @return <tt>true</tt> if the receiver contains the specified value.
     */
    public final boolean containsValue(final int value) {
        return indexOfValue(value) >= 0;
    }


    /**
     * Applies a procedure to each (key,value) pair of the receiver, if any.
     * Iteration order is guaranteed to be <i>identical</i> to the order used by
     * method {@link #forEachKey(LongProcedure)}.
     *
     * @param procedure
     *            the procedure to be applied. Stops iteration if the procedure
     *            returns <tt>false</tt>, otherwise continues.
     * @return <tt>false</tt> if the procedure stopped before all keys where
     *         iterated over, <tt>true</tt> otherwise.
     */
    public final boolean forEachPair(final LongIntProcedure procedure) {
        for (int i = table.length; i-- > 0; ) {
            if (state[i] == FULL && !procedure.apply(table[i], values[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the value associated with the specified key. It is often a good
     * idea to first check with {@link #containsKey(long)} whether the given key
     * has a value associated or not, i.e. whether there exists an association
     * for the given key or not.
     *
     * @param key
     *            the key to be searched for.
     * @return the value associated with the specified key; <tt>null</tt> if no
     *         such key is present.
     */
    public final int get(final long key) {
        int i = indexOfKey(key);
        if (i < 0) {
            return Integer.MIN_VALUE; // not contained
        }
        return values[i];
    }


    /**
     * @param value
     *            the value to be searched in the receiver.
     * @return the index where the value is contained in the receiver, returns
     *         -1 if the value was not found.
     */
    protected final int indexOfValue(final int value) {
        final int[] val = values;
        final byte[] stat = state;

        for (int i = stat.length; --i >= 0; ) {
            if (stat[i] == FULL && val[i] == value) {
                return i;
            }
        }

        return -1; // not found
    }

    /**
     * Returns the first key the given value is associated with. It is often a
     * good idea to first check with {@link #containsValue(int)} whether
     * there exists an association from a key to this value. Search order is
     * guaranteed to be <i>identical</i> to the order used by method
     * {@link #forEachKey(LongProcedure)}.
     *
     * @param value
     *            the value to search for.
     * @return the first key for which holds <tt>get(key) == value</tt>; returns
     *         <tt>Long.MIN_VALUE</tt> if no such key exists.
     */
    public final long keyOf(final int value) {
        // returns the first key found; there may be more matching keys,
        // however.
        int i = indexOfValue(value);
        if (i < 0) {
            return Long.MIN_VALUE;
        }
        return table[i];
    }

    /**
     * Fills all keys contained in the receiver into the specified list. Fills
     * the list, starting at index 0. After this call returns the specified list
     * has a new size that equals <tt>this.size()</tt>. Iteration order is
     * guaranteed to be <i>identical</i> to the order used by method
     * {@link #forEachKey(LongProcedure)}.
     * <p>
     * This method can be used to iterate over the keys of the receiver.
     *
     * @param list
     *            the list to be filled, can have any size.
     */
    public final void keys(final LongArrayList list) {
        list.setSize(distinct);
        long[] elements = list.elements();

        long[] tab = table;
        byte[] stat = state;

        int j = 0;
        for (int i = tab.length; i-- > 0; ) {
            if (stat[i] == FULL) {
                elements[j++] = tab[i];
            }
        }
    }

    /**
     * Fills all pairs satisfying a given condition into the specified lists.
     * Fills into the lists, starting at index 0. After this call returns the
     * specified lists both have a new size, the number of pairs satisfying the
     * condition. Iteration order is guaranteed to be <i>identical</i> to the
     * order used by method {@link #forEachKey(LongProcedure)}.
     * <p>
     * <b>Example:</b> <br>
     *
     * <pre>
     * LongObjectProcedure condition = new LongObjectProcedure() { // match even keys only
     * 	public boolean apply(long key, Object value) { return key%2==0; }
     * }
     * keys = (8,7,6), values = (1,2,2) --> keyList = (6,8), valueList = (2,1)</tt>
     * </pre>
     *
     * @param condition
     *            the condition to be matched. Takes the current key as first
     *            and the current value as second argument.
     * @param keyList
     *            the list to be filled with keys, can have any size.
     * @param valueList
     *            the list to be filled with values, can have any size.
     */
    public final void pairsMatching(final LongIntProcedure condition,
                                    final LongArrayList keyList, final IntArrayList valueList) {
        keyList.clear();
        valueList.clear();

        for (int i = table.length; i-- > 0; ) {
            if (state[i] == FULL && condition.apply(table[i], values[i])) {
                keyList.add(table[i]);
                valueList.add(values[i]);
            }
        }
    }

    /**
     * Associates the given key with the given value. Replaces any old
     * <tt>(key,someOtherValue)</tt> association, if existing.
     *
     * @param key
     *            the key the value shall be associated with.
     * @param value
     *            the value to be associated.
     * @return <tt>true</tt> if the receiver did not already contain such a key;
     *         <tt>false</tt> if the receiver did already contain such a key -
     *         the new value has now replaced the formerly associated value.
     */
    public final boolean put(final long key, final int value) {
        int i = indexOfInsertion(key);
        if (i < 0) { // already contained
            i = -i - 1;
            this.values[i] = value;
            return false;
        }

        if (this.distinct > this.highWaterMark) {
            int newCapacity = chooseGrowCapacity(this.distinct + 1,
                    this.minLoadFactor, this.maxLoadFactor);
            rehash(newCapacity);
            return put(key, value);
        }

        this.table[i] = key;
        this.values[i] = value;
        if (this.state[i] == FREE) {
            this.freeEntries--;
        }
        this.state[i] = FULL;
        this.distinct++;

        if (this.freeEntries < 1) { // delta
            int newCapacity = chooseGrowCapacity(this.distinct + 1,
                    this.minLoadFactor, this.maxLoadFactor);
            rehash(newCapacity);
        }

        return true;
    }

    /**
     * Rehashes the contents of the receiver into a new table with a smaller or
     * larger capacity. This method is called automatically when the number of
     * keys in the receiver exceeds the high water mark or falls below the low
     * water mark.
     *
     * @param newCapacity the new capacity
     */
    @Override
    protected final void rehash(final int newCapacity) {
        int oldCapacity = table.length;
        // if (oldCapacity == newCapacity) return;

        long[] oldTable = table;
        int[] oldValues = values;
        byte[] oldState = state;

        long[] newTable = new long[newCapacity];
        int[] newValues = new int[newCapacity];
        byte[] newState = new byte[newCapacity];

        this.lowWaterMark = chooseLowWaterMark(newCapacity, this.minLoadFactor);
        this.highWaterMark = chooseHighWaterMark(newCapacity,
                this.maxLoadFactor);

        this.table = newTable;
        this.values = newValues;
        this.state = newState;
        this.freeEntries = newCapacity - this.distinct; // delta

        for (int i = oldCapacity; i-- > 0; ) {
            if (oldState[i] == FULL) {
                long element = oldTable[i];
                int index = indexOfInsertion(element);
                newTable[index] = element;
                newValues[index] = oldValues[i];
                newState[index] = FULL;
            }
        }
    }

    /**
     * Removes the given key with its associated element from the receiver, if
     * present.
     *
     * @param key
     *            the key to be removed from the receiver.
     * @return <tt>true</tt> if the receiver contained the specified key,
     *         <tt>false</tt> otherwise.
     */
    public final boolean removeKey(final long key) {
        int i = indexOfKey(key);
        if (i < 0) {
            return false; // key not contained
        }

        this.state[i] = REMOVED;
        this.values[i] = FREE; // delta
        this.distinct--;

        if (this.distinct < this.lowWaterMark) {
            int newCapacity = chooseShrinkCapacity(this.distinct,
                    this.minLoadFactor, this.maxLoadFactor);
            rehash(newCapacity);
        }

        return true;
    }

    /**
     * Initializes the receiver.
     *
     * @param initialCapacity the initial capacity of the receiver.
     * @param minLoadFactor the minLoadFactor of the receiver.
     * @param maxLoadFactor the maxLoadFactor of the receiver.
     */
    @Override
    protected final void setUp(final int initialCapacity, final double minLoadFactor,
                               final double maxLoadFactor) {
        int capacity = initialCapacity;
        super.setUp(capacity, minLoadFactor, maxLoadFactor);
        capacity = nextPrime(capacity);
        if (capacity == 0) {
            capacity = 1; // open addressing needs at least one FREE slot at any
            // time.
        }

        this.table = new long[capacity];
        this.values = new int[capacity];
        this.state = new byte[capacity];

        // memory will be exhausted long before this pathological case happens,
        // anyway.
        this.minLoadFactor = minLoadFactor;
        if (capacity == PrimeFinder.largestPrime) {
            this.maxLoadFactor = 1.0;
        } else {
            this.maxLoadFactor = maxLoadFactor;
        }

        this.distinct = 0;
        this.freeEntries = capacity; // delta

        // lowWaterMark will be established upon first expansion.
        // establishing it now (upon instance construction) would immediately
        // make the table shrink upon first put(...).
        // After all the idea of an "initialCapacity" implies violating
        // lowWaterMarks when an object is young.
        // See ensureCapacity(...)
        this.lowWaterMark = 0;
        this.highWaterMark = chooseHighWaterMark(capacity, this.maxLoadFactor);
    }

    /**
     * Fills all values contained in the receiver into the specified list. Fills
     * the list, starting at index 0. After this call returns the specified list
     * has a new size that equals <tt>this.size()</tt>. Iteration order is
     * guaranteed to be <i>identical</i> to the order used by method
     * {@link #forEachKey(LongProcedure)}.
     * <p>
     * This method can be used to iterate over the values of the receiver.
     *
     * @param list
     *            the list to be filled, can have any size.
     */
    public final void values(final IntArrayList list) {
        list.setSize(distinct);
        int[] elements = list.elements();

        int[] val = values;
        byte[] stat = state;

        int j = 0;
        for (int i = stat.length; i-- > 0; ) {
            if (stat[i] == FULL) {
                elements[j++] = val[i];
            }
        }
    }

}
