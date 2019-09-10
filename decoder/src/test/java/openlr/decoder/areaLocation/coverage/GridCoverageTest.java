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
 * Copyright (C) 2009,2010 TomTom International B.V.
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
 *  Copyright (C) 2009,2010 TomTom International B.V.
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
package openlr.decoder.areaLocation.coverage;

import openlr.decoder.TestMap;
import openlr.decoder.worker.coverage.GridCoverage;
import openlr.location.data.AffectedLines;
import openlr.map.GeoCoordinates;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.MapDatabase;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GridCoverageTest {

    /** The Constant EXPECTED_COVERAGE. */
    private static final Set<Long> EXPECTED_COVERAGE = new HashSet<Long>(
            Arrays.asList(new Long[]{15280001229338L, 15280001229335L,
                    -15280001229332L, 15280001229336L, -15280001229336L,
                    -15280001229337L, -15280001229333L, 15280001235663L,
                    15280001235664L, 15280001235665L, 15280001235666L,
                    15280001235667L, 15280001235668L}));

    /** The Constant EXPECTED_INTERSECTION. */
    private static final Set<Long> EXPECTED_INTERSECTION = new HashSet<Long>(
            Arrays.asList(new Long[]{15280001229940L, 15280001229339L, -15280001229339L,
                    15280001229331L, -15280001229331L,
                    15280001229330L, -15280001229330L, 15280001229334L, 15280001229175L,
                    -15280001229175L}));

    @Test
    public final void testGridCoverage() {
        try {
            GeoCoordinates ll = new GeoCoordinatesImpl(5.09934, 52.10580);
            GeoCoordinates ur = new GeoCoordinatesImpl(5.10033, 52.10650);
            MapDatabase mdb = TestMap.getTestMapDatabase();
            GridCoverage coverage = new GridCoverage(ll, ur, 2, 2);
            AffectedLines affected = coverage.getAffectedLines(mdb);
            Assert.assertNotNull(affected);
            Utils.checkAffectedLines(affected, EXPECTED_COVERAGE,
                    EXPECTED_INTERSECTION);
        } catch (Exception e) {
            Assert.fail("Unexpected exception", e);
        }
    }

    @Test
    public final void testGridCoverageEmpty1() {
        try {
            GeoCoordinates ll = new GeoCoordinatesImpl(5.09934, 52.10580);
            GeoCoordinates ur = new GeoCoordinatesImpl(5.09934, 52.10580);
            MapDatabase mdb = TestMap.getTestMapDatabase();
            GridCoverage coverage = new GridCoverage(ll, ur, 2, 2);
            AffectedLines affected = coverage.getAffectedLines(mdb);
            Assert.assertNotNull(affected);
            Assert.assertTrue(affected.isEmpty());
        } catch (Exception e) {
            Assert.fail("Unexpected exception", e);
        }
    }

    @Test
    public final void testGridCoverageEmpty2() {
        try {
            GeoCoordinates ll = new GeoCoordinatesImpl(5.09934, 52.10580);
            GeoCoordinates ur = new GeoCoordinatesImpl(5.10033, 52.10650);
            MapDatabase mdb = null;
            GridCoverage coverage = new GridCoverage(ll, ur, 2, 2);
            AffectedLines affected = coverage.getAffectedLines(mdb);
            Assert.assertNotNull(affected);
            Assert.assertTrue(affected.isEmpty());
        } catch (Exception e) {
            Assert.fail("Unexpected exception", e);
        }
    }

}
