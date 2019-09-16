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
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

/**
 * Tests the {@link LocationDataIterator}.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class LocationDataIteratorTest {

    /** To mock stuff. */
    private static Mockery mockery = new Mockery();

    /**
     * A mocked location data instance
     */
    private final LocationData someLocationData = mockSomeLocationData(5);

    /**
     * Performs some tests of all the possible states of the iterator.
     */
    @Test
    public final void testIteration() {

        LocationDataIterator iterator = new LocationDataIterator(
                someLocationData);

        assertEquals(someLocationData.numberOfLocations(), iterator.size());

        // init state
        assertTrue(iterator.hasNext());
        assertFalse(iterator.hasPrevious());
        assertEquals(-1, iterator.currentIndex());

        assertEquals(null, iterator.current());

        try {
            iterator.previous();
        } catch (NoSuchElementException e) {
            // Expected exception
            assertTrue(e != null);
        }

        // point to first
        iterator.next();
        assertTrue(iterator.hasNext());
        assertFalse(iterator.hasPrevious());
        assertEquals(0, iterator.currentIndex());
        assertEquals("0", iterator.current().getID());
        assertEquals(1, iterator.nextIndex());

        // point to second
        Location loc = iterator.next();

        assertEquals("1", loc.getID());

        assertTrue(iterator.hasNext());
        assertTrue(iterator.hasPrevious());
        assertEquals(1, iterator.currentIndex());

        assertEquals("1", iterator.current().getID());
        assertEquals("0", iterator.previous().getID());

        // exceed the bounds
        try {
            iterator.setCurrent(someLocationData.numberOfLocations());
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
            assertTrue(e != null);
        }
        try {
            iterator.setCurrent(-1);
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
            assertTrue(e != null);
        }

        // point to last
        int lastIndex = someLocationData.numberOfLocations() - 1;
        Location locLast = iterator.setCurrent(lastIndex);
        assertEquals(String.valueOf(lastIndex), locLast.getID());

        assertFalse(iterator.hasNext());
        assertTrue(iterator.hasPrevious());
        assertEquals(lastIndex, iterator.currentIndex());

        try {
            iterator.next();
        } catch (NoSuchElementException e) {
            // Expected exception
            assertTrue(e != null);
        }

        iterator.setCurrent(0);
        assertEquals("0", iterator.current().getID());
    }

    /**
     * Test the iterators general {@link Iterator} interface.
     */
    @Test
    public final void testGeneralIteratorInterface() {

        final int numberLocations = someLocationData.numberOfLocations();
        ListIterator<Location> iterator = new LocationDataIterator(someLocationData);

        assertEquals(-1, iterator.previousIndex());
        assertEquals(0, iterator.nextIndex());

        int count = 0;
        while (iterator.hasNext()) {
            assertEquals(String.valueOf(count), iterator.next().getID());
            count++;
        }

        assertEquals(numberLocations, iterator.nextIndex());

        assertTrue(count == numberLocations);
    }

    /**
     * Mocks a collection of locations with a count as specified in
     * {@code count}. Each will have the ID of its sequence number starting with
     * 0.
     *
     * @param count
     *            The number of locations to add
     * @return The mocked location data object
     */
    private LocationData mockSomeLocationData(final int count) {
        LocationData data = new LocationData();

        for (int i = 0; i < count; i++) {

            data.addLocation(mockALocation(String.valueOf(i)));
        }

        data.addError("An error in loading locations");

        return data;

    }

    /**
     * Mocks a single location.
     *
     * @param id
     *            The id the location should deliver in {@link Location#getID()}
     * @return The mocked location
     */
    private Location mockALocation(final String id) {

        final Location loc = mockery.mock(Location.class, id);
        mockery.checking(new Expectations() {
            {
                allowing(loc).getID();
                will(returnValue(id));
            }
        });

        return loc;
    }

}
