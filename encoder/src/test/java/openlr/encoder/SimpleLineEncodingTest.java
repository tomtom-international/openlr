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
import openlr.map.MapDatabase;
import openlr.map.Node;
import openlr.rawLocRef.RawLocationReference;
import org.apache.commons.configuration.Configuration;
import org.testng.Reporter;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

/**
 * A base class for simple tests that perform an encoding of line locations. The 
 * result is verified by comparing the coordinates of the LRP of the result with 
 * the coordinates of the corresponding map nodes that are expected to be the 
 * base of the coordinates of the LRP. 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
abstract class SimpleLineEncodingTest {

    /**
     * A reference to the test data holder.
     */
    private static final Configuration ENCODER_PROPERTIES =
            TestData.getInstance().getConfiguration();

    /**
     * Performs the actions of testing a line location encoding. <br>
     * Creates a line location out of the given sequence of line IDs and encodes
     * it. Compares the coordinate of each LRP of the result with the
     * coordinates of the corresponding map node in
     * <code>expectedRelevantNodes</code> that is expected to be the base of the
     * coordinates of the LRP.
     * @param mockedDb
     *            The used database.
     * @param testCase
     *            The data of the test case.
     */
    final void testLocation(final MapDatabase mockedDb, final TestCase testCase) {

        Reporter.log("Testing " + testCase.getId());

        List<Line> theRoute = new ArrayList<Line>(testCase.getLines().length);
        for (long id : testCase.getLines()) {
            theRoute.add(mockedDb.getLine(id));
        }

        Location loc = LocationFactory.createLineLocation(testCase.getId(),
                theRoute);
        try {
            OpenLREncoder encoder = new OpenLREncoder();
            OpenLREncoderParameter params = new OpenLREncoderParameter.Builder().with(mockedDb).with(ENCODER_PROPERTIES).buildParameter();
            LocationReferenceHolder lh = encoder.encodeLocation(
                    params, loc);
            assertTrue(lh.isValid());

            RawLocationReference data = lh.getRawLocationReferenceData();
            List<? extends LocationReferencePoint> lrps = data
                    .getLocationReferencePoints();

            long[] expectedNodes = testCase.getExpectedNodes();
            assertEquals(lrps.size(), expectedNodes.length,
                    "Error while checking loaction \"" + testCase.getId()
                            + "\": Number of expected LRPs doesn't match.");

            Node expectedNode;
            LocationReferencePoint lrp;

            String errMessage = "Error while checking loaction \""
                    + testCase.getId()
                    + "\": Unexpected coordinate found for LRP ";

            for (int i = 0; i < expectedNodes.length; i++) {

                expectedNode = mockedDb.getNode(expectedNodes[i]);
                lrp = lrps.get(i);
                assertEquals(lrp.getLongitudeDeg(), expectedNode
                        .getLongitudeDeg(), errMessage + (i + 1));
                assertEquals(lrp.getLatitudeDeg(), expectedNode
                        .getLatitudeDeg(), errMessage + (i + 1));
            }

        } catch (OpenLRProcessingException e) {
            fail("Unexpected exception during encoding of loop location \""
                    + testCase.getId() + "\"", e);
        }
    }

    /**
     * A data class holding the parameters for one test case.
     */
    static class TestCase {

        /** The line sequence of the location to be encoded. */
        private final long[] lines;
        /**
         * The map nodes that are expected to be the corresponding nodes for the
         * LRPs in the result of encoding the line location.
         */
        private final long[] expectedNodes;

        /**
         * The identifier of this instance.
         */
        private final String id;

        /**
         * Creates a new instance of TestCase.
         *
         * @param identifier
         *            The identifier of this test case.
         * @param locationLines
         *            The line sequence of the location to be encoded.
         * @param expectedNodeIDs
         *            The map nodes that are expected to be the corresponding
         *            nodes for the LRPs in the result of encoding the line
         *            location.
         */
        TestCase(final String identifier, final long[] locationLines,
                 final long[] expectedNodeIDs) {
            id = identifier;
            lines = locationLines;
            expectedNodes = expectedNodeIDs;
        }

        /**
         * @return The identifier.
         */
        String getId() {
            return id;
        }

        /**
         * @return The line IDs.
         */
        long[] getLines() {
            return lines;
        }

        /**
         * @return The node IDs.
         */
        long[] getExpectedNodes() {
            return expectedNodes;
        }
    }
}
