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
package openlr.encoder;

import openlr.LocationReferencePoint;
import openlr.OpenLRProcessingException;
import openlr.location.Location;
import openlr.location.LocationFactory;
import openlr.map.Line;
import openlr.map.mockdb.InvalidConfigurationException;
import openlr.map.mockdb.MockedMapDatabase;
import org.testng.annotations.Test;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import static org.testng.Assert.*;

/**
 * This class tests cases with encoding locations in parts of the map with
 * parallel roads.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class ParallelRoadsTest {

    /**
     * The lines contained in the path test containing parallel roads in the
     * middle.
     */
    private static final long[] LINE_IDS_PARALLEL_MIDDLE = {1, 2, 3};

    /**
     * The lines contained in the path test containing parallel roads in the
     * middle.
     */
    private static final long[] LINE_IDS_PARALLEL_END = {1, 2};

    /**
     * The lines contained in the path test containing only two parallel roads.
     */
    private static final long[] LINE_IDS_PARALLEL_ONLY = {2};

    /** The network node 1. */
    private static final Point2D N1 = new Point2D.Double(-73.96526, 40.75629);
    /** The network node 2. */
    private static final Point2D N2 = new Point2D.Double(-73.96696, 40.75702);
    /** The network node 3. */
    private static final Point2D N3 = new Point2D.Double(-73.96922, 40.75797);
    /** The network node 4. */
    private static final Point2D N4 = new Point2D.Double(-73.97080, 40.75862);

    /**
     * The mocked map database. Its structure looks as follows: 
     *        ____
     * N1---N2____N3---N4
     *
     * Each connection is drivable in both directions.
     */
    private final MockedMapDatabase mdb;

    /**
     * Initializes the map database.
     *
     * @throws InvalidConfigurationException
     *             If an error occurs initializing the map database from the
     *             configuration.
     */
    public ParallelRoadsTest() throws InvalidConfigurationException {
        mdb = new MockedMapDatabase("ParallelRoadsMap.xml");
    }

    /**
     * Tests the encoding of a location with two parallel roads in the middle.
     *        ____ 
     * N1---N2____N3---N4 (Path: N1, N2, N3, N4)
     */
    @Test
    public final void testParallelRoads() {

        /** The location containing a extra long line. */
        ArrayList<Line> longLineLocation = new ArrayList<Line>();
        for (int i = 0; i < LINE_IDS_PARALLEL_MIDDLE.length; i++) {
            longLineLocation.add(mdb.getLine(LINE_IDS_PARALLEL_MIDDLE[i]));
        }
        Location loc = LocationFactory.createLineLocation("parallelMiddle",
                longLineLocation);

        runTest(loc, N1, N4);
    }

    /**
     * Tests the encoding of a location with only two parallel roads. 
     *   ____
     * N2____N3 (Path: N2, N3)
     */
    @Test
    public final void testParallelRoadsOnly() {

        /** The location containing a extra long line. */
        ArrayList<Line> longLineLocation = new ArrayList<Line>();
        for (int i = 0; i < LINE_IDS_PARALLEL_ONLY.length; i++) {
            longLineLocation.add(mdb.getLine(LINE_IDS_PARALLEL_ONLY[i]));
        }
        Location loc = LocationFactory.createLineLocation("parallelOnly",
                longLineLocation);

        runTest(loc, N2, N3);
    }

    /**
     * Tests the encoding of a location with two parallel roads at the end. 
     *        ____
     * N1---N2____N3 (Path: N1, N2, N3)
     */
    @Test
    public final void testParallelRoadsAtEnd() {

        /** The location containing a extra long line. */
        ArrayList<Line> longLineLocation = new ArrayList<Line>();
        for (int i = 0; i < LINE_IDS_PARALLEL_END.length; i++) {
            longLineLocation.add(mdb.getLine(LINE_IDS_PARALLEL_END[i]));
        }
        Location loc = LocationFactory.createLineLocation("parallelEnd",
                longLineLocation);

        runTest(loc, N1, N3);
    }

    /**
     * Executes the encoding of the given location. Checks validity and number
     * of expected LRP afterwards.
     *
     * @param loc
     *            The location to encode.
     * @param expectedLrpCoordinates
     *            The coordinates of the expected LRPs, used to determine if the
     *            result was the expected.
     */
    private void runTest(final Location loc,
                         final Point2D... expectedLrpCoordinates) {

        LocationReferenceHolder locationRef = null;
        try {
            OpenLREncoder encoder = new OpenLREncoder();
            OpenLREncoderParameter params =
                    new OpenLREncoderParameter.Builder().with(mdb).with(TestData.getInstance()
                            .getConfiguration()).buildParameter();
            locationRef = encoder.encodeLocation(params, loc);
        } catch (OpenLRProcessingException e) {
            fail("Encoding location failed with exception: " + e.getErrorCode(),
                    e);
        }
        assertTrue(locationRef.isValid(),
                "Encoding delivered invalid location.");

        assertEquals(locationRef.getNrOfLRPs(), expectedLrpCoordinates.length);

        int i = 0;
        Point2D expected;
        for (LocationReferencePoint lrp : locationRef.getLRPs()) {
            expected = expectedLrpCoordinates[i++];
            assertEquals(lrp.getLongitudeDeg(), expected.getX());
            assertEquals(lrp.getLatitudeDeg(), expected.getY());
        }
    }
}
