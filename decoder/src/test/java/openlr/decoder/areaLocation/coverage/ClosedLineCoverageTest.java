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
import openlr.decoder.worker.coverage.ClosedLineCoverage;
import openlr.location.data.AffectedLines;
import openlr.map.Line;
import openlr.map.MapDatabase;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

public class ClosedLineCoverageTest {

    /** The Constant EXPECTED_INTERSECTION. */
    private static final Set<Long> EXPECTED_INTERSECTION = new HashSet<Long>(
            Arrays.asList(new Long[]{15280002184966L, 15280001229341L,
                    -15280001229341L, 15280001441173L, -15280001441173L, 15280001229174L,
                    -15280001229174L, 15280001229210L, -15280001229210L, 15280001229313L, -15280001229313L,
                    15280001229187L, -15280001229187L, 15280001229178L, -15280001229178L, 15280001229207L,
                    -15280001229207L}));
    /** The Constant EXPECTED_COVERAGE. */
    private final Set<Long> EXPECTED_COVERAGE = new HashSet<Long>(
            Arrays.asList(new Long[]{15280001229940L, 15280001229939L,
                    -15280001229312L, 15280001229312L, 15280001441181L,
                    15280001441182L, -15280001441182L, -15280001229331L,
                    15280001229331L, -15280001229330L, 15280001229308L,
                    15280001229330L, -15280001229308L, -15280001229177L,
                    -15280001229176L, -15280001229175L, 15280001229175L,
                    15280001229177L, 15280001229176L, 15280001229334L,
                    -15280001229333L, 15280001229317L, 15280001229339L,
                    -15280001229339L, 15280001229336L, -15280001229336L,
                    15280001229335L, -15280001229337L, -15280001229332L,
                    15280001229338L, 15280001441169L, -15280001441169L,
                    15280001235663L, 15280001235664L, 15280001235665L,
                    15280001235666L, 15280001235667L, 15280001235668L}));

    @Test
    public final void testClosedLineCoverage() {
        try {
            List<Line> lines = new ArrayList<Line>();
            MapDatabase mdb = TestMap.getTestMapDatabase();
            lines.add(mdb.getLine(15280001229940L));
            lines.add(mdb.getLine(15280001229939L));
            lines.add(mdb.getLine(-15280001229312L));
            lines.add(mdb.getLine(15280001441181L));
            lines.add(mdb.getLine(15280001441182L));
            lines.add(mdb.getLine(-15280001229331L));
            lines.add(mdb.getLine(-15280001229330L));
            lines.add(mdb.getLine(15280001229308L));
            lines.add(mdb.getLine(-15280001229177L));
            lines.add(mdb.getLine(-15280001229176L));
            lines.add(mdb.getLine(-15280001229175L));
            ClosedLineCoverage coverage = new ClosedLineCoverage(lines);
            AffectedLines affected = coverage.getAffectedLines(mdb);
            Assert.assertNotNull(affected);
            Utils.checkAffectedLines(affected, EXPECTED_COVERAGE,
                    EXPECTED_INTERSECTION);
        } catch (Exception e) {
            Assert.fail("Unexpected exception", e);
        }
    }

    @Test
    public final void testClosedLineCoverageEmpty1() {
        try {
            List<Line> lines = new ArrayList<Line>();
            MapDatabase mdb = TestMap.getTestMapDatabase();
            ClosedLineCoverage coverage = new ClosedLineCoverage(lines);
            AffectedLines affected = coverage.getAffectedLines(mdb);
            Assert.assertNotNull(affected);
            Assert.assertTrue(affected.isEmpty());
        } catch (Exception e) {
            Assert.fail("Unexpected exception", e);
        }
    }

    @Test
    public final void testClosedLineCoverageEmpty2() {
        try {
            List<Line> lines = new ArrayList<Line>();
            MapDatabase mdb = null;
            ClosedLineCoverage coverage = new ClosedLineCoverage(lines);
            AffectedLines affected = coverage.getAffectedLines(mdb);
            Assert.assertNotNull(affected);
            Assert.assertTrue(affected.isEmpty());
        } catch (Exception e) {
            Assert.fail("Unexpected exception", e);
        }
    }

}
