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
 * Tests several cases of u-turns in line locations.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class UTurnTest extends SimpleLineEncodingTest {

    /**
     * The mocked map configuration for the u-turn tests. Its structure looks as
     * follows.
     *
     * 1--->2--->
     *            3
     * 1<---2<---
     *
     * The numbers specify network nodes. Each line is possible to drive in both
     * directions.
     */
    private static final String MOCKED_MAP = "UTurnMap.xml";
    /**
     * Data holder for the different test cases.
     */
    private static final TestCase[] TEST_CASES = new TestCase[]{

            /**
             * Tests encoding a line location with a u-turn at the start of the location.
             * 1--->2--->
             *           3
             * 1<---2<---   (Location node sequence: 2, 3, 2, 1)
             *
             * This location should get expanded at the start till node 1 because
             * node 2 is invalid (no junction).
            */
            new TestCase("UTurnStart", new long[]{2, -2, -1}, new long[]{1, 2, 1}),

            /**
             * Tests encoding a line location with a u-turn in the middle of the location.
             * 1--->2--->
             *           3
             * 1<---2<---   (Location node sequence: 1, 2, 3, 2, 1)
            */
            new TestCase("UTurnMiddle", new long[]{1, 2, -2, -1},
                    new long[]{1, 2, 1}),

            /**
             * Tests encoding a line location with a u-turn at the end of the location.
             * 1--->2--->
             *           3
             *      2<---   (Location node sequence: 1, 2, 3, 2)
             *
             * This location should get expanded at the end till node 1 because
             * node 2 is invalid (no junction).
            */
            new TestCase("UTurnEnd", new long[]{1, 2, -2}, new long[]{1, 2, 1}),

            /**
             * Tests encoding a line location containing of just a u-turn.
             * 2--->
             *      3
             * 2<---   (Location node sequence: 2, 3, 2)
             *
             * This location should get expanded at both ends till node 1 because
             * node 2 is invalid (no junction).
            */
            new TestCase("UTurnOnly", new long[]{2, -2}, new long[]{1, 2, 1})
    };
    /**
     * A reference to the mocked map database instance containing a loop.
     */
    private final MockedMapDatabase mockedDb;

    /**
     * Performs setup tasks.
     *
     * @throws InvalidConfigurationException
     *             If an error occurs setting up the internal
     *             {@link MockedMapDatabase}.
     */
    public UTurnTest() throws InvalidConfigurationException {
        mockedDb = new MockedMapDatabase(MOCKED_MAP, true);
    }

    /**
     * Executes the tests of all test cases defined in {@link #TEST_CASES}.
     */
    @Test
    public final void testUTurns() {

        for (TestCase test : TEST_CASES) {
            testLocation(mockedDb, test);
        }
    }
}
