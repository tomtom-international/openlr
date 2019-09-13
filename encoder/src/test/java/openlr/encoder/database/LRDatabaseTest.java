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
package openlr.encoder.database;

import openlr.OpenLRProcessingException;
import openlr.encoder.*;
import openlr.location.Location;
import openlr.map.InvalidMapDataException;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Tests the functionality of the location reference database.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class LRDatabaseTest {

    /** The max size of the cache. */
    private static final int CACHE_SIZE = 20;

    /** The central cache object for all the subclasses of this one. */
    private static final LRDatabase CACHE = LRDatabaseFactory
            .createLRDatabase(CACHE_SIZE);
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
            testCommon(td.getWhitepaperPOIWithAccessPointLocation());
        } catch (InvalidMapDataException e) {
            Assert.fail("Unexpected exception", e);
        }
    }

    /**
     * Tests using point along line locations.
     */
    @Test(groups = {TEST_GROUP_FILL_CACHE})
    public final void testPointAlongLine() {
        try {
            testCommon(td.getWhitepaperPointAlongLineLocation());
        } catch (InvalidMapDataException e) {
            Assert.fail("Unexpected exception", e);
        }
    }

    /**
     * Tests using Line locations.
     */
    @Test(groups = {TEST_GROUP_FILL_CACHE})
    public final void testLineLocation() {

        testCommon(td.getWhitepaperLineLocation());
    }

    /**
     * Performs some common tests for different location types.
     *
     * @param loc
     *            The input location data.
     */
    private void testCommon(final Location loc) {

        LocationReferenceHolder deLoc = encodeLocation(loc);
        LocationReferenceHolder deLo2 = encodeLocation(loc);

        int sizeBefore = CACHE.getCurrentNrEntries();

        CACHE.storeResult(loc, deLoc);
        CACHE.storeResult(loc, deLo2);

        assertSame(CACHE.getCurrentNrEntries(), sizeBefore + 1);

        assertEquals(deLoc.getID(), CACHE.getResult(loc).getID());
        assertEquals(deLo2.getID(), CACHE.getResult(loc).getID());

        assertTrue(deLoc.hashCode() == deLoc.hashCode());
    }

    /**
     * Tests the filled cache object.
     */
    @Test(dependsOnGroups = {TEST_GROUP_FILL_CACHE})
    public final void testDatabaseObject() {
        try {
            assertTrue(CACHE
                    .containsLR(td.getWhitepaperGeoCoordinateLocation()));
            assertTrue(CACHE.containsLR(td.getWhitepaperLineLocation()));
            assertTrue(CACHE.containsLR(td
                    .getWhitepaperPointAlongLineLocation()));
            assertTrue(CACHE.containsLR(td
                    .getWhitepaperPOIWithAccessPointLocation()));

            CACHE.storeResult(null, null);
            assertFalse(CACHE.containsLR(null));

            assertNotNull(CACHE.toString());
            assertEquals(CACHE.getCacheSize(), CACHE_SIZE);

            CACHE.clear();
            assertEquals(0, CACHE.getCurrentNrEntries());
            assertNull(CACHE.getResult(td.getWhitepaperGeoCoordinateLocation()));
            assertFalse(CACHE.containsLR(td
                    .getWhitepaperGeoCoordinateLocation()));
        } catch (InvalidMapDataException e) {
            Assert.fail("Unexpected exception", e);
        }
    }

    @Test(dependsOnGroups = {TEST_GROUP_FILL_CACHE}, expectedExceptions = IllegalArgumentException.class)
    public final void testDatabaseObjectFailure() {
        assertNull(CACHE.getResult(null));
    }

    /**
     * Encodes the given location.
     *
     * @param inputLoc
     *            The input data.
     * @return the decoded location reference.
     */
    private LocationReferenceHolder encodeLocation(final Location inputLoc) {
        LocationReferenceHolder encLocRef = null;

        try {
            OpenLREncoder encoder = new OpenLREncoder();
            OpenLREncoderParameter params = new OpenLREncoderParameter.Builder()
                    .with(td.getMapDatabase()).with(td.getConfiguration())
                    .buildParameter();
            encLocRef = encoder.encodeLocation(params, inputLoc);

        } catch (OpenLRProcessingException e) {
            fail("Decoding location failed with exception: " + e.getErrorCode(),
                    e);
        }

        return encLocRef;
    }

}
