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
package openlr.encoder;

import openlr.OpenLRProcessingException;
import openlr.location.Location;
import openlr.location.LocationFactory;
import openlr.map.Line;
import openlr.map.mockdb.InvalidConfigurationException;
import openlr.map.mockdb.MockedMapDatabase;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.testng.Assert.*;

/**
 * This class contains test of the general encoding process.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class LongLineNewLastLRPTest {

    /** The Constant EXPECTED_BEARING_ONE_LINE. */
    private static final double EXPECTED_BEARING_ONE_LINE = 85.0804881381537;

    /** The Constant EXPECTED_BEARING_TWO_LINE. */
    private static final double EXPECTED_BEARING_TWO_LINE = 98.910088617926;

    /** The Constant TWO_LINES. */
    private static final long[] TWO_LINES = new long[]{27, 28};

    /** The Constant ONE_LINE. */
    private static final long[] ONE_LINE = new long[]{26};

    /** The Constant LARGE_OFFSET_TWO_LINES. */
    private static final int LARGE_OFFSET_TWO_LINES = 8000;

    /** The Constant LARGE_OFFSET_ONE_LINE. */
    private static final int LARGE_OFFSET_ONE_LINE = 7000;

    /** The Constant ALLOWED_VARIANCE. */
    private static final double ALLOWED_VARIANCE = 0.01;

    /**
     * An utility class holding prepared/mocked test data.
     */
    private TestData td = TestData.getInstance();


    /**
     * Test long line new last lrp. LRP is on line and the bearing must have changed when
     * this LRP becomes the last LRP.
     */
    @Test
    public final void testLongLineNewLastLRPOneLine() {
        LocationReferenceHolder locationRef = testLongLineImpl(0, LARGE_OFFSET_ONE_LINE, ONE_LINE);
        assertTrue(locationRef.isValid(),
                "Encoding delivered invalid location.");
        assertEquals(locationRef.getNrOfLRPs(), 2);
        double bearing = locationRef.getLRPs().get(1).getBearing();
        assertTrue(Math.abs(bearing - EXPECTED_BEARING_ONE_LINE) < ALLOWED_VARIANCE);

    }

    /**
     * Test long line new last lrp. LRP is not on line and the bearing must have changed when
     * this LRP becomes the last LRP. Here the reference line of the LRP also changed.
     */
    @Test
    public final void testLongLineNewLastLRPTwoLines() {
        LocationReferenceHolder locationRef = testLongLineImpl(0, LARGE_OFFSET_TWO_LINES, TWO_LINES);
        assertTrue(locationRef.isValid(),
                "Encoding delivered invalid location.");
        assertEquals(locationRef.getNrOfLRPs(), 2);
        double bearing = locationRef.getLRPs().get(1).getBearing();
        assertTrue(Math.abs(bearing - EXPECTED_BEARING_TWO_LINE) < ALLOWED_VARIANCE);

    }


    /**
     * Test the encoding of a location containing an extra long line. This is at
     * the time of writing the first example of using a dedicated mocked
     * database via configuration for only this single test.
     *
     * @param positiveOffset The positive offset to set
     * @param negativeOffset The negative offset to set
     * @param ids the line ids
     * @return the location reference holder after encoding
     */
    public final LocationReferenceHolder testLongLineImpl(
            final int positiveOffset, final int negativeOffset, final long[] ids) {

        MockedMapDatabase mdb = null;
        try {
            mdb = new MockedMapDatabase("ExtraLongLineMapNewLRP.xml", false);
        } catch (InvalidConfigurationException e) {
            fail("Unexpected exception!", e);
        }

        /** The location containing a extra long line. */
        ArrayList<Line> longLineLocation = new ArrayList<Line>();
        for (int i = 0; i < ids.length; i++) {
            longLineLocation.add(mdb.getLine(ids[i]));
        }
        Location loc = LocationFactory.createLineLocationWithOffsets("longLine",
                longLineLocation, positiveOffset, negativeOffset);

        LocationReferenceHolder locationRef = null;
        try {
            OpenLREncoder encoder = new OpenLREncoder();
            OpenLREncoderParameter params = new OpenLREncoderParameter.Builder().with(mdb).with(td.getConfiguration()).buildParameter();
            locationRef = encoder.encodeLocation(params, loc);
        } catch (OpenLRProcessingException e) {
            fail("Encoding location failed with exception: " + e.getErrorCode(),
                    e);
        }
        return locationRef;
    }


}
