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
package openlr.encoder.data;

import openlr.OpenLRProcessingException;
import openlr.encoder.TestData;
import openlr.map.FunctionalRoadClass;
import openlr.map.Line;
import openlr.testutils.CommonObjectTestUtils;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * Tests of class {@link LocRefPoint}.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class LocRefPointTest {

    /**
     * The line of the mocked map used for the comparison test.
     */
    private static final int LINE_FOR_COMPARISON_TEST = 19;

    /**
     * The expected DNP value of the created {@link LocRefPoint}.
     */
    private static final int EXPECTED_DNP = 835;

    /**
     * The expected DNP value of the created {@link LocRefPoint}.
     */
    private static final double EXPECTED_BEARING = 135.0879067689071;

    /** The test data. */
    private TestData td = TestData.getInstance();

    /**
     * Test the creation of {@link LocRefPoint}s objects.
     */
    @Test
    public final void testLocRefPoint() {
        LocRefData lrd = new LocRefData(td.getRoute());
        lrd.setExpansion(td.getLRLocation());
        List<Line> path = ExpansionHelper.getExpandedLocation(lrd);
        LocRefPoint lrp1 = null;
        LocRefPoint lrp2 = null;
        try {
            lrp1 = new LocRefPoint(path, td.getProperties());
            lrp2 = new LocRefPoint(path, td.getProperties());
        } catch (OpenLRProcessingException e) {
            fail("", e);
        }
        lrp1.setNextLRP(lrp2);
        assertEquals(lrp1.getLfrc(), FunctionalRoadClass.FRC_5);
        assertEquals(lrp1.getBearing(),
                EXPECTED_BEARING);
        assertEquals(lrp1.getDistanceToNext(), EXPECTED_DNP);
    }

    /**
     * Tests methods hashCode() and equals().
     */
    @Test
    public final void testComparison() {
        LocRefData lrd = new LocRefData(td.getRoute());
        lrd.setExpansion(td.getLRLocation());
        List<Line> path = ExpansionHelper.getExpandedLocation(lrd);
        Line aLine = td.getMapDatabase().getLine(LINE_FOR_COMPARISON_TEST);

        LocRefPoint a = null;
        LocRefPoint equalToA = null;
        LocRefPoint unEqualToA = null;
        try {
            a = new LocRefPoint(path, td.getProperties());
            equalToA = new LocRefPoint(path, td.getProperties());
            unEqualToA = new LocRefPoint(aLine, td.getProperties());
            a.setNextLRP(unEqualToA);
            equalToA.setNextLRP(unEqualToA);
        } catch (OpenLRProcessingException e) {
            fail("Unexpected location.", e);
        }

        CommonObjectTestUtils.testCompare(a, equalToA, unEqualToA);
        CommonObjectTestUtils.testToString(equalToA);
    }
}
