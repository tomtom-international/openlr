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
package openlr.location.utils;

import openlr.location.Location;

import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Enables to iterate forward and backwards over a list of locations. The
 * indexes start at 0 and thus end with size - 1. Initially the iterator points
 * to no location.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class LocationDataIterator implements ListIterator<Location> {

    /** The data. */
    private final LocationData data;

    /** The current location. */
    private int currentLocation = -1;

    /**
     * Instantiates a new location data iterator.
     *
     * @param locD the loc d
     */
    LocationDataIterator(final LocationData locD) {
        data = locD;
    }

    /**
     * Number of locations.
     *
     * @return the int
     */
    public final int size() {
        return data.numberOfLocations();
    }

    /**
     * Gets the current location index. This returns "-1" if the iterator is
     * still in start position or was {@link #reset()}.
     *
     * @return the current location index
     */
    public final int currentIndex() {
        return currentLocation;
    }

    /**
     * This method delivers the location the iterator currently points to. If
     * the iterator is still in start position or was {@link #reset()} before
     * this returns {@code null}.
     *
     * @return the current location
     */
    public final Location current() {

        if (currentLocation >= 0 && currentLocation < data.numberOfLocations()) {
            return data.getLocation(currentLocation);
        }
        return null;
    }

    /**
     * Sets the pointer to the next location and delivers it back. Throws a
     * {@link NoSuchElementException} if there is no next location available.
     * Please see {@link #hasNext()}.
     *
     * @return the next location
     * @see #hasNext()
     */
    @Override
    public final Location next() {
        if (currentLocation < (data.numberOfLocations() - 1)) {
            currentLocation++;
        } else {
            throw new NoSuchElementException("No element available for index "
                    + currentLocation);
        }
        return current();
    }

    /**
     * Sets the pointer to the previous location and delivers it back. Throws a
     * {@link NoSuchElementException} if there is no previous location available.
     * Please see {@link #hasPrevious()}.
     *
     * @return the previous location
     */
    @Override
    public final Location previous() {
        if (currentLocation > 0) {
            currentLocation--;
        } else {
            throw new NoSuchElementException("No element available for index "
                    + (currentLocation - 1));
        }
        return current();
    }

    /**
     * Checks if is previous location available.
     *
     * @return true, if is previous location available
     */
    @Override
    public final boolean hasPrevious() {
        return currentLocation > 0;
    }

    /**
     * Checks if is next location available.
     *
     * @return true, if is next location available
     */
    @Override
    public final boolean hasNext() {
        return currentLocation < (data.numberOfLocations() - 1);
    }

    /**
     * Resets the iterator to the start position. In this state no location is
     * selected.
     */
    public final void reset() {
        currentLocation = -1;
    }

    /**
     * Sets the pointer to a specific location index. Valid values are 0 to
     * {@link #size()} - 1. 
     *
     * @param newIndex
     *            the new index
     * @return the current location
     * @see #current()
     */
    public final Location setCurrent(final int newIndex) {
        if (newIndex >= 0 && newIndex < data.numberOfLocations()) {
            currentLocation = newIndex;
            return current();
        }
        throw new IndexOutOfBoundsException("index out of bounds: " + newIndex);
    }

    /**
     * Not supported by this iterator. Throws
     * {@link UnsupportedOperationException}.
     */
    @Override
    public final void remove() {
        throw new UnsupportedOperationException("Remove not supported by "
                + getClass().getName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int nextIndex() {
        return currentLocation + 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int previousIndex() {
        return currentLocation;
    }

    /**
     * Not supported by this iterator. Throws
     * {@link UnsupportedOperationException}.
     *
     * @param e
     *            ignored
     */
    @Override
    public final void set(final Location e) {
        throw new UnsupportedOperationException("Set not supported by "
                + getClass().getName());

    }

    /**
     * Not supported by this iterator. Throws
     * {@link UnsupportedOperationException}.
     *
     * @param e
     *            ignored
     */
    @Override
    public final void add(final Location e) {
        throw new UnsupportedOperationException("Add not supported by "
                + getClass().getName());
    }

}

