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
package openlr.encoder;

import openlr.map.mockdb.InvalidConfigurationException;
import openlr.map.mockdb.MockedMapDatabase;
import org.testng.annotations.Test;

/**
 * Tests several cases of encoding of line locations containing loops.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class LineLoopTest extends SimpleLineEncodingTest {

    /**
     * The mocked map configuration for the loop tests. Its structure looks as
     * follows.
     *     5____4
     *     |    |     ____
     * 1___2____3___6_____|
     *
     * The numbers specify network nodes. Each line is possible to drive in both
     * directions.
     */
    private static final String MOCKED_MAP_LOOPS = "LoopMap.xml";
    /**
     * Data holder for the different test cases.
     */
    private static final TestCase[] TEST_CASES = new TestCase[]{

            /**
             * Tests encoding a line location with a loop at the start of the location.
             *
             *     5____4
             *     |    |
             *     2____3___6
            */
            new TestCase("LoopStart", new long[]{2, 3, 4, 5, 2, 6},
                    new long[]{2, 3, 4, 6}),

            /**
             * Tests encoding a line location with a loop in the middle.
             *
             *     5____4
             *     |    |
             * 1___2____3___6
            */
            new TestCase("LoopMiddle", new long[]{1, 2, 3, 4, 5, 2, 6},
                    new long[]{1, 3, 4, 6}),

            /**
             * Tests encoding a line location with a loop at the end of the location.
             *
             *     5____4
             *     |    |
             * 1___2____3   (Location node sequence: 1,2,3,4,5,2,3)
            */
            new TestCase("LoopEnd", new long[]{1, 2, 3, 4, 5, 2},
                    new long[]{1, 2, 3, 3}),

            /**
             * Tests a location that looks this way:
             *     ____
             *    6____|    (Location node sequence: 6,6)
            */
            new TestCase("LoopLineOnly", new long[]{7}, new long[]{6, 6}),

            /**
             * Tests a location that looks this way:
             *     ____
             * 3__6____|    (Location node sequence: 3,6,6,3)
            */
            new TestCase("LoopLineMiddle", new long[]{6, 7, -6}, new long[]{
                    3, 6, 3}),

            /**
             * Tests a location that looks this way:
             *     ____
             * 3__6____|    (Location node sequence: 6,6,3)
            */
            new TestCase("LoopLineStart", new long[]{7, -6}, new long[]{6, 3}),

            /**
             * Tests a location that looks this way:
             *     ____
             * 3__6____|    (Location node sequence: 3,6,6)
            */
            new TestCase("LoopLineEnd", new long[]{6, 7}, new long[]{3, 6})
    };
    /**
     * A reference to the mocked map database instance containing a loop.
     */
    private final MockedMapDatabase mockedLoopDb;


    /**
     * Performs setup tasks.
     * @throws InvalidConfigurationException If an error occurs setting up
     * the internal {@link MockedMapDatabase}.
     */
    public LineLoopTest() throws InvalidConfigurationException {
        mockedLoopDb = new MockedMapDatabase(MOCKED_MAP_LOOPS, true);
    }

    /**
     * Executes the tests of all test cases defined in {@link #TEST_CASES}.
     */
    @Test
    public final void testLoops() {

        for (TestCase test : TEST_CASES) {
            testLocation(mockedLoopDb, test);
        }
    }
}
