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
/*
 Copyright � 1999 CERN - European Organization for Nuclear Research.
 Permission to use, copy, modify, distribute and sell this software and its documentation for any purpose 
 is hereby granted without fee, provided that the above copyright notice appear in all copies and 
 that both that copyright notice and this permission notice appear in supporting documentation. 
 CERN makes no representations about the suitability of this software for any purpose. 
 It is provided "as is" without expressed or implied warranty.
 */
package openlr.collection;

import cern.colt.list.ByteArrayList;
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
 * @author TomTom International B.V.
 */
public class OpenLongHashSet extends OpenAbstractCollection {

	/**
	 * serial ID
	 */
	private static final long serialVersionUID = 4523482049259662861L;


	/**
	 * Constructs an empty map with default capacity and default load factors.
	 */
	public OpenLongHashSet() {
		this(defaultCapacity);
	}

	/**
	 * Constructs an empty map with the specified initial capacity and default
	 * load factors.
	 * 
	 * @param initialCapacity
	 *            the initial capacity of the map.
	 */
	public OpenLongHashSet(final int initialCapacity) {
		this(initialCapacity, defaultMinLoadFactor, defaultMaxLoadFactor);
	}

	/**
	 * Constructs an empty map with the specified initial capacity and the
	 * specified minimum and maximum load factor.
	 * 
	 * @param initialCapacity
	 *            the initial capacity.
	 * @param minLoadFactor
	 *            the minimum load factor.
	 * @param maxLoadFactor
	 *            the maximum load factor.
	 */
	public OpenLongHashSet(final int initialCapacity,
			final double minLoadFactor, final double maxLoadFactor) {
		setUp(initialCapacity, minLoadFactor, maxLoadFactor);
	}

	/**
	 * Removes all (key,value) associations from the receiver. Implicitly calls
	 * <tt>trimToSize()</tt>.
	 */
	public final void clear() {
		new ByteArrayList(this.state).fillFromToWith(0, this.state.length - 1,
				FREE);
		distinct = 0;
		freeEntries = table.length; // delta
		trimToSize();
	}

	/**
	 * Returns a deep copy of the receiver.
	 * 
	 * @return a deep copy of the receiver.
	 */
	public final Object clone() {
		OpenLongHashSet copy = (OpenLongHashSet) super.clone();
		copy.table = (long[]) copy.table.clone();
		copy.state = (byte[]) copy.state.clone();
		return copy;
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
		for (int i = tab.length; i-- > 0;) {
			if (stat[i] == FULL) {
				elements[j++] = tab[i];
			}
		}
	}

	/**
	 * Associates the given key with the given value. Replaces any old
	 * <tt>(key,someOtherValue)</tt> association, if existing.
	 * 
	 * @param key
	 *            the key the value shall be associated with.
	 * @return <tt>true</tt> if the receiver did not already contain such a key;
	 *         <tt>false</tt> if the receiver did already contain such a key -
	 *         the new value has now replaced the formerly associated value.
	 */
	public final boolean put(final long key) {
		int i = indexOfInsertion(key);
		if (i < 0) { // already contained
			return false;
		}

		if (this.distinct > this.highWaterMark) {
			int newCapacity = chooseGrowCapacity(this.distinct + 1,
					this.minLoadFactor, this.maxLoadFactor);
			rehash(newCapacity);
			return put(key);
		}

		this.table[i] = key;
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
	 * @param newCapacity
	 *            the new capacity
	 */
	protected final void rehash(final int newCapacity) {
		int oldCapacity = table.length;
		// if (oldCapacity == newCapacity) return;

		long[] oldTable = table;
		byte[] oldState = state;

		long[] newTable = new long[newCapacity];
		byte[] newState = new byte[newCapacity];

		this.lowWaterMark = chooseLowWaterMark(newCapacity, this.minLoadFactor);
		this.highWaterMark = chooseHighWaterMark(newCapacity,
				this.maxLoadFactor);

		this.table = newTable;
		this.state = newState;
		this.freeEntries = newCapacity - this.distinct; // delta

		for (int i = oldCapacity; i-- > 0;) {
			if (oldState[i] == FULL) {
				long element = oldTable[i];
				int index = indexOfInsertion(element);
				newTable[index] = element;
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
	 * @param initialCapacity
	 *            the initial capacity of the receiver.
	 * @param minLoadFactor
	 *            the minLoadFactor of the receiver.
	 * @param maxLoadFactor
	 *            the maxLoadFactor of the receiver.
	 */
	protected final void setUp(final int initialCapacity,
			final double minLoadFactor, final double maxLoadFactor) {
		int capacity = initialCapacity;
		super.setUp(capacity, minLoadFactor, maxLoadFactor);
		capacity = nextPrime(capacity);
		if (capacity == 0) {
			capacity = 1; // open addressing needs at least one FREE slot at any
							// time.
		}

		this.table = new long[capacity];
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

}
