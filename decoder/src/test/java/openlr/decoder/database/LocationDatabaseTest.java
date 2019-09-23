/**
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 2 of the License and the extra
 * conditions for OpenLR. (see openlr-license.txt)
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
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
package openlr.decoder.database;

import openlr.OpenLRProcessingException;
import openlr.decoder.*;
import openlr.location.Location;
import openlr.map.InvalidMapDataException;
import openlr.rawLocRef.RawLocationReference;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

/**
 * Tests the LRU cache.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class LocationDatabaseTest {

    /** The max size of the cache. */
    private static final int CACHE_SIZE = 20;

    /** The central cache object for all the subclasses of this one. */
    private static final LocationDatabase CACHE = LocationDatabaseFactory
            .createLocationDatabase(CACHE_SIZE);
    /** The name of the tests group that fills the cache. */
    private static final String TEST_GROUP_FILL_CACHE = "fillCache";
    /**
     * An utility class holding prepared/mocked test data.
     */
    protected static TestData td = TestData.getInstance();

    /**
     * Tests using Geo coordinate locations.
     */
    @Test(groups = {TEST_GROUP_FILL_CACHE})
    public final void testGeoCoordinate() {
        try {
            testCommon(td.getWhitepaperGeoCoordinateLocation());
        } catch (InvalidMapDataException e) {
            Assert.fail("Unexpected exception", e);
        }
    }

    /**
     * Tests using POI with access point locations.
     */
    @Test(groups = {TEST_GROUP_FILL_CACHE})
    public final void testPOIWithAccess() {
        try {
            RawLocationReference loc = td
                    .getWhitepaperPOIWithAccessPointLocation();
            RawLocationReference loc2 = td.getPWANoOffsets();
            RawLocationReference locInvalid = td.getNotConnectedPWA();

            Location deLoc = decodeLocation(loc);
            Location deLoc2 = decodeLocation(loc2);
            Location deLocInvalid = decodeLocation(locInvalid);
            Location deLocInvalid2 = decodeLocation(locInvalid);

            int sizeBefore = CACHE.getCurrentNrEntries();

            CACHE.storeResult(loc, deLoc);
            CACHE.storeResult(loc2, deLoc2);

            assertSame(CACHE.getCurrentNrEntries(), sizeBefore + 2);

            assertTrue(deLoc.equals(CACHE.getResult(loc)));
            assertTrue(deLoc2.equals(CACHE.getResult(loc2)));

            assertTrue(deLocInvalid.equals(deLocInvalid2));

            assertFalse(deLoc2.equals(CACHE.getResult(loc)));

            assertTrue(deLoc.hashCode() == deLoc.hashCode());
            assertTrue(deLocInvalid.hashCode() == deLocInvalid.hashCode());
            assertFalse(deLoc.hashCode() == deLoc2.hashCode());
        } catch (InvalidMapDataException e) {
            Assert.fail("Unexpected exception", e);
        }
    }

    /**
     * Tests using point along line locations.
     */
    @Test(groups = {TEST_GROUP_FILL_CACHE})
    public final void testPointAlongLine() {

        testCommon(td.getWhitepaperPointAlongLineLocationReference());
    }

    /**
     * Tests using Line locations.
     */
    @Test(groups = {TEST_GROUP_FILL_CACHE})
    public final void testLineLocation() {

        testCommon(td.getWhitepaperLineLocationReference());
    }

    /**
     * Performs some common tests for different location types.
     *
     * @param loc
     *            The input location data.
     */
    private void testCommon(final RawLocationReference loc) {

        Location deLoc = decodeLocation(loc);
        Location deLo2 = decodeLocation(loc);

        int sizeBefore = CACHE.getCurrentNrEntries();

        CACHE.storeResult(loc, deLoc);
        CACHE.storeResult(loc, deLo2);

        assertSame(CACHE.getCurrentNrEntries(), sizeBefore + 1);

        assertTrue(deLoc.equals(CACHE.getResult(loc)));
        assertTrue(deLo2.equals(CACHE.getResult(loc)));

        assertTrue(deLoc.hashCode() == deLoc.hashCode());
    }

    /**
     * Tests the filled cache object.
     */
    @Test(dependsOnGroups = {TEST_GROUP_FILL_CACHE})
    public final void testDatabaseObject() {
        try {
            assertTrue(CACHE.containsLR(td.getWhitepaperGeoCoordinateLocation()));
            assertTrue(CACHE.containsLR(td.getWhitepaperLineLocationReference()));
            assertTrue(CACHE.containsLR(td
                    .getWhitepaperPointAlongLineLocationReference()));
            assertTrue(CACHE.containsLR(td
                    .getWhitepaperPOIWithAccessPointLocation()));
            assertFalse(CACHE.containsLR(td.getNotConnectedPWA()));

            CACHE.storeResult(null, null);
            assertFalse(CACHE.containsLR(null));

            assertNotNull(CACHE.toString());
            assertEquals(CACHE.getCacheSize(), CACHE_SIZE);

            CACHE.clear();
            assertEquals(0, CACHE.getCurrentNrEntries());
            assertNull(CACHE.getResult(td.getWhitepaperGeoCoordinateLocation()));
            assertFalse(CACHE.containsLR(td.getWhitepaperGeoCoordinateLocation()));
        } catch (InvalidMapDataException e) {
            Assert.fail("Unexpected exception", e);
        }
    }

    @Test(dependsOnGroups = {TEST_GROUP_FILL_CACHE}, expectedExceptions = IllegalArgumentException.class)
    public final void testDatabaseObjectFailure() {
        assertNull(CACHE.getResult(null));
    }

    /**
     * Decodes the given raw location data.
     *
     * @param inputLoc
     *            The input data.
     * @return the decoded location reference.
     */
    private Location decodeLocation(final RawLocationReference inputLoc) {
        Location decLocRef = null;

        try {
            OpenLRDecoder decoder = new OpenLRDecoder();
            OpenLRDecoderParameter parameter = new OpenLRDecoderParameter.Builder().with(
                    td.getMapDatabase()).with(td.getProperties()).buildParameter();
            decLocRef = decoder.decodeRaw(parameter, inputLoc);

        } catch (OpenLRProcessingException e) {
            fail("Decoding location failed with exception: " + e.getErrorCode(),
                    e);
        }

        return decLocRef;
    }

}
