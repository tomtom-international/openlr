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
package openlr.decoder.data;

import openlr.decoder.TestData;
import openlr.decoder.data.CandidateLine.CandidateLineComparator;
import openlr.decoder.data.CandidateLinePair.CandidateLinePairComparator;
import openlr.map.Line;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.*;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Tests class {@link CandidateLine}.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class CandidateLineTest {

    /** The ratio of value 400. */
    private static final int RATIO_400 = 400;
    /** The ratio of value 400. */
    private static final int RATIO_999 = 999;
    /** The ratio of value 400. */
    private static final int RATIO_1000 = 1000;

    /**
     * Test the comparison of line objects.
     */
    @Test
    public final void testComparison() {

        TestData td = TestData.getInstance();
        Line line1 = td.getMapDatabase().getLine(1);
        Line line2 = td.getMapDatabase().getLine(2);

        CandidateLine ratio1000 = new CandidateLine(null, RATIO_1000);
        CandidateLine ratio999a = new CandidateLine(line1, RATIO_999, 0);
        CandidateLine rds03 = new CandidateLine(line2, RATIO_400, Integer.MAX_VALUE);
        CandidateLine ratio999b = new CandidateLine(null, RATIO_999);
        CandidateLine maxRatio = new CandidateLine(null, Integer.MAX_VALUE);
        CandidateLine minRatio = new CandidateLine(line2, Integer.MIN_VALUE);
        CandidateLine rdsRatioZero = new CandidateLine(null, 0);

        CandidateLineComparator comp = new CandidateLineComparator();
        assertTrue(comp.compare(ratio1000, ratio999a) < 0);
        assertTrue(comp.compare(ratio999a, ratio1000) > 0);
        assertTrue(comp.compare(ratio1000, rds03) < 0);
        assertTrue(comp.compare(rds03, ratio999a) > 0);
        assertTrue(comp.compare(ratio999a, ratio999b) == 0);
        assertTrue(comp.compare(maxRatio, minRatio) < 0);
        assertTrue(comp.compare(minRatio, maxRatio) > 0);
        assertTrue(comp.compare(minRatio, rdsRatioZero) > 0);
        assertTrue(comp.compare(rdsRatioZero, rdsRatioZero) == 0);
        assertTrue(comp.compare(rdsRatioZero, rdsRatioZero) == 0);

        CandidateLine[] expectedSorting = new CandidateLine[]{
                maxRatio, ratio1000, ratio999a, rdsRatioZero, minRatio};

        List<CandidateLine> cLines = Arrays.asList(rdsRatioZero, minRatio,
                maxRatio, ratio999a, ratio1000);
        Collections.sort(cLines, new CandidateLineComparator());

        for (int i = 0; i < expectedSorting.length; i++) {
            assertSame(cLines.get(i), expectedSorting[i],
                    "Unexpected entry at index " + i);
        }
    }

    /**
     * Tests the toString method.
     */
    @Test
    public final void testToString() {
        TestData td = TestData.getInstance();
        CandidateLine line = new CandidateLine(td.getMapDatabase().getLine(1),
                RATIO_1000, -1);
        assertNotNull(line.toString());
    }


    /**
     * Test the comparison of line objects.
     */
    @Test
    public final void testComparisonPair() {

        CandidateLinePair scoreOneA = new CandidateLinePair(0, 1, 1);
        CandidateLinePair scoreOneB = new CandidateLinePair(0, 1, 1);
        CandidateLinePair scoreMax = new CandidateLinePair(0, 2,
                Integer.MAX_VALUE);
        CandidateLinePair scoreMin = new CandidateLinePair(1, 2,
                Integer.MIN_VALUE);
        CandidateLinePair scoreZero = new CandidateLinePair(2, 1, 0);

        assertEquals(scoreOneA.getStartIndex(), scoreMax.getStartIndex());
        assertEquals(scoreMin.getDestIndex(), scoreMax.getDestIndex());
        assertEquals(scoreOneA.getScore(), scoreOneB.getScore());

        CandidateLinePairComparator comp = new CandidateLinePairComparator();
        assertTrue(comp.compare(scoreOneA, scoreOneA) == 0);
        assertTrue(comp.compare(scoreOneA, scoreOneB) == 0);
        assertTrue(comp.compare(scoreMax, scoreOneA) < 0);
        assertTrue(comp.compare(scoreOneA, scoreMax) > 0);
        assertTrue(comp.compare(scoreMin, scoreMax) > 0);
        assertTrue(comp.compare(scoreMax, scoreMin) < 0);
        assertTrue(comp.compare(scoreMax, scoreMax) == 0);
        assertTrue(comp.compare(scoreMax, scoreZero) < 0);
        assertTrue(comp.compare(scoreZero, scoreMax) > 0);

        CandidateLinePair[] expectedSorting = new CandidateLinePair[]{
                scoreMax, scoreOneA, scoreZero, scoreMin};

        List<CandidateLinePair> pairs = Arrays.asList(scoreMax, scoreOneA,
                scoreMin, scoreZero);
        Collections.sort(pairs, new CandidateLinePairComparator());

        for (int i = 0; i < expectedSorting.length; i++) {
            assertSame(pairs.get(i), expectedSorting[i],
                    "Unexpected entry at index " + i);
        }
    }

    /**
     * Tests the toString method.
     */
    @Test
    public final void testToStringPair() {
        CandidateLinePair pair = new CandidateLinePair(0, 1, Integer.MAX_VALUE);
        assertNotNull(pair.toString());
    }

}
