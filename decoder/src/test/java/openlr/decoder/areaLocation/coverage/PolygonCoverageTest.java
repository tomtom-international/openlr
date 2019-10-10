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
package openlr.decoder.areaLocation.coverage;

import openlr.decoder.TestMap;
import openlr.decoder.worker.coverage.PolygonCoverage;
import openlr.location.data.AffectedLines;
import openlr.map.GeoCoordinates;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.MapDatabase;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

public class PolygonCoverageTest {

    /** The Constant EXPECTED_COVERAGE. */
    private static final Set<Long> EXPECTED_COVERAGE = new HashSet<Long>(
            Arrays.asList(new Long[]{15280001229215L,
                    -15280001229215L, 15280001229218L,
                    15280001229217L, -15280001229217L}));

    /** The Constant EXPECTED_INTERSECTION. */
    private static final Set<Long> EXPECTED_INTERSECTION = new HashSet<Long>(
            Arrays.asList(new Long[]{15280001229174L, -15280001229174L,
                    -15280001229213L, 15280001229216L, -15280001229216L, 15280001229219L,
                    15280001229159L, -15280001229159L, 15280001229160L, -15280001229160L,
                    15280001229214L, 15280001234940L, -15280001234940L}));

    @Test
    public final void testPolygonCoverage() {
        try {
            List<GeoCoordinates> corners = new ArrayList<GeoCoordinates>();
            corners.add(new GeoCoordinatesImpl(5.09990, 52.10760));
            corners.add(new GeoCoordinatesImpl(5.10201, 52.10763));
            corners.add(new GeoCoordinatesImpl(5.10165, 52.10980));
            corners.add(new GeoCoordinatesImpl(5.10034, 52.10908));
            corners.add(new GeoCoordinatesImpl(5.09982, 52.10848));
            PolygonCoverage coverage = new PolygonCoverage(corners);
            MapDatabase mdb = TestMap.getTestMapDatabase();
            AffectedLines affected = coverage.getAffectedLines(mdb);
            Assert.assertNotNull(affected);
            Utils.checkAffectedLines(affected, EXPECTED_COVERAGE,
                    EXPECTED_INTERSECTION);
        } catch (Exception e) {
            Assert.fail("Unexpected exception", e);
        }
    }


    @Test
    public final void testPolygonCoverageEmpty1() {
        try {
            List<GeoCoordinates> corners = new ArrayList<GeoCoordinates>();
            corners.add(new GeoCoordinatesImpl(0, 0));
            corners.add(new GeoCoordinatesImpl(0, 0));
            corners.add(new GeoCoordinatesImpl(0, 0));
            PolygonCoverage coverage = new PolygonCoverage(corners);
            MapDatabase mdb = TestMap.getTestMapDatabase();
            AffectedLines affected = coverage.getAffectedLines(mdb);
            Assert.assertNotNull(affected);
            Assert.assertTrue(affected.isEmpty());
        } catch (Exception e) {
            Assert.fail("Unexpected exception", e);
        }
    }


    @Test
    public final void testPolygonCoverageEmpty2() {
        try {
            List<GeoCoordinates> corners = new ArrayList<GeoCoordinates>();

            PolygonCoverage coverage = new PolygonCoverage(corners);
            MapDatabase mdb = null;
            AffectedLines affected = coverage.getAffectedLines(mdb);
            Assert.assertNotNull(affected);
            Assert.assertTrue(affected.isEmpty());
        } catch (Exception e) {
            Assert.fail("Unexpected exception", e);
        }
    }

}
