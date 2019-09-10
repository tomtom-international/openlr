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
package openlr.decoder.wpaper;

import openlr.OpenLRProcessingException;
import openlr.decoder.OpenLRDecoder;
import openlr.decoder.OpenLRDecoderParameter;
import openlr.decoder.TestData;
import openlr.location.Location;
import openlr.map.InvalidMapDataException;
import openlr.rawLocRef.RawLocationReference;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Tests the encoding examples from the OpenLr White paper.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
@Test
public abstract class AbstractDecoderTest {

    /**
     * An utility class holding prepared/mocked test data.
     */
    protected static TestData td = TestData.getInstance();
    /**
     * A reference to the result of the decoding.
     */
    protected Location decodedLoc;

    /**
     * Tests the line location decoding of the example from the White paper
     */
    @Test
    public final void testWhitepaperLocation() {

        decodedLoc = decodeLocation();

        checkLocation(decodedLoc);
        checkOffsets(decodedLoc, getExpectedOffsets());
    }

    /**
     * Delivers the input location for the encoder.
     *
     * @return The input location.
     * @throws InvalidMapDataException
     */
    abstract RawLocationReference getInputLocation()
            throws InvalidMapDataException;

    /**
     * Performs validity tests of the decoded location.
     *
     * @param loc
     *            The decoded location.
     */
    abstract void checkLocation(Location loc);

    /**
     * Delivers the offsets expected after encoding in the following sequence:
     * {positive offset, negative offset}
     *
     * @return The offsets expected after encoding.
     */
    abstract int[] getExpectedOffsets();

    /**
     * Decodes the location of the example. Fails the test if if couldn't be
     * decoded.
     *
     * @return The valid location reference.
     */
    private Location decodeLocation() {
        Location decLocRef = null;

        try {
            OpenLRDecoder decoder = new OpenLRDecoder();
            OpenLRDecoderParameter parameter = new OpenLRDecoderParameter.Builder()
                    .with(td.getMapDatabase()).with(td.getProperties())
                    .buildParameter();
            decLocRef = decoder.decodeRaw(parameter, getInputLocation());

        } catch (OpenLRProcessingException e) {
            fail("Decoding location failed with exception: " + e.getErrorCode(),
                    e);
        } catch (InvalidMapDataException e) {
            fail("Decoding location failed with exception!", e);
        }
        return decLocRef;
    }

    /**
     * Tests the offsets for the expected values.
     *
     * @param locRef
     *            The decoded location reference.
     * @param expectedOffsets
     *            An array of expected offset values in the order {positive
     *            offset, negative offset}.
     */
    private void checkOffsets(final Location locRef, final int[] expectedOffsets) {

        if (expectedOffsets[0] > 0) {
            assertTrue(locRef.hasPositiveOffset());
        } else {
            assertFalse(locRef.hasPositiveOffset());
        }
        if (expectedOffsets[1] > 0) {
            assertTrue(locRef.hasNegativeOffset());
        } else {
            assertFalse(locRef.hasNegativeOffset());
        }
        assertEquals(locRef.getPositiveOffset(), expectedOffsets[0]);
        assertEquals(locRef.getNegativeOffset(), expectedOffsets[1]);
    }
}
