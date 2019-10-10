/**
 * Licensed to the TomTom International B.V. under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  TomTom International B.V.
 * licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * <p>
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
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License and the extra
 *  conditions for OpenLR. (see openlr-license.txt)
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
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
package openlr.binary;

import openlr.LocationReference;
import openlr.LocationReferencePoint;
import openlr.Offsets;
import openlr.PhysicalFormatException;
import openlr.binary.impl.LocationReferenceBinaryImpl;
import openlr.rawLocRef.RawLocationReference;
import org.apache.commons.codec.binary.Base64;
import org.testng.annotations.Test;

import java.io.*;
import java.util.List;

import static openlr.binary.BinaryReturnCode.READING_HEADER_FAILURE;
import static org.testng.Assert.*;

/**
 * Tests the binary decoder, primarily with line locations.
 */
public class OpenLRBinaryDecoderTest {

    /** The Constant RFU_POSITION_IN_HEADER. */
    private static final int RFU_POSITION_IN_HEADER = 128;

    /** Valid binary location data in version 2. */
    private static final byte[] VALID_BIN_DATA_POS_OFFSET_V2 = {10, 4, 91, 91,
            35, 70, -11, 26, 108, 9, 0, -102, -2, 59, 27, -76, 4, -1, -21, -1, -92,
            43, 121, 3, 2};

    /** Valid binary location data. */
    private static final byte[] VALID_BIN_DATA_OFFSETS_V3 = {11, 4, 91, 91,
            35, 70, -11, 26, 108, 9, 0, -102, -2, 59, 27, -76, 4, -1, -21, -1, -92,
            43, 121, 93, 97};

    /** Valid binary location data. */
    private static final byte[] VALID_BIN_DATA_NO_OFFSET_V3 = {11, 4, 91, 91,
            35, 70, -11, 26, 108, 9, 0, -102, -2, 59, 27, -76, 4, -1, -21, -1,
            -92, 43, 25};

    /** Invalid binary location data. */
    private static final byte[] INVALID_BIN_LOC_DATA = {
            Byte.parseByte("00001010", 2), Byte.parseByte("00000100", 2),
            Byte.parseByte("01011011", 2), Byte.parseByte("01011011", 2),
            Byte.parseByte("00100011", 2), Byte.parseByte("01000110", 2),
            Byte.parseByte("-1110100", 2), Byte.parseByte("00011010", 2),
            Byte.parseByte("01101100", 2), Byte.parseByte("00001001", 2),
            Byte.parseByte("-1111111", 2), Byte.parseByte("01100101", 2),
            Byte.parseByte("00000001", 2), Byte.parseByte("-1000101", 2),
            Byte.parseByte("00011011", 2), Byte.parseByte("-0110100", 2),
            Byte.parseByte("00000100", 2), Byte.parseByte("00000010", 2),
            Byte.parseByte("-0001011", 2), Byte.parseByte("00000000", 2),
            Byte.parseByte("01011101", 2), Byte.parseByte("00101011", 2),
            Byte.parseByte("01011001", 2)};
    /** The binary decoder object.  */
    private static final OpenLRBinaryDecoder DECODER = new OpenLRBinaryDecoder();
    /** Expected value for the positive offset after decoding with version 2. */
    private static final int EXPECTED_POS_OFFSET_V2 = 205;
    /** Expected value for the negative offset after decoding with version 2. */
    private static final int EXPECTED_NEG_OFFSET_V2 = 147;
    /** Expected value for the positive offset after decoding with version 3. */
    private static final int EXPECTED_POS_OFFSET_V3 = 203;
    /**
     * Expected value for the positive offset from decoding the White Paper
     * example.
     */
    private static final int EXPECTED_POS_OFFSET_WP_EXAMPLE = 149;
    /** Expected value for the negative offset after decoding with version 3. */
    private static final int EXPECTED_NEG_OFFSET_V3 = 101;
    /** A reference to the valid decoding result of test {@link #testValidData()} */
    private RawLocationReference validDecodeLocation;

    /**
     * Tests some methods of the decoder.
     */
    @Test
    public final void testDecoderObject() {
        assertEquals(DECODER.getDataClass(), ByteArray.class);
        assertEquals(DECODER.getDataFormatIdentifier(),
                OpenLRBinaryConstants.IDENTIFIER);
    }

    /**
     * Tests decoding of valid binary data.
     */
    @Test
    public final void testValidData() {

        ByteArray validBinLoc = new ByteArray(VALID_BIN_DATA_POS_OFFSET_V2);

        try {
            validDecodeLocation = decodeData(validBinLoc);
            Lrp[] expectedLrps = new Lrp[]{Lrp.LINE_DEC_LRP1, Lrp.LINE_DEC_LRP2,
                    Lrp.LINE_DEC_LRP3};
            Utils.checkDecodedLrps(validDecodeLocation, expectedLrps);
            checkOffsetsV2(validDecodeLocation.getOffsets(),
                    EXPECTED_POS_OFFSET_V2, EXPECTED_NEG_OFFSET_V2);

        } catch (PhysicalFormatException e) {
            fail("failed on valid input", e);
        }
    }

    /**
     * Checks the given offsets against the specified values basing upon binary
     * format version 2.
     *
     * @param expectedPosOffset
     *            The expected positive offset or <code>null</code> if none is
     *            expected.
     * @param expectedNegativeOffset
     *            The expected negative offset or <code>null</code> if none is
     *            expected.
     * @param offset
     *            The offsets object to check.
     */
    private void checkOffsetsV2(final Offsets offset,
                                final Integer expectedPosOffset,
                                final Integer expectedNegativeOffset) {

        assertTrue(offset.hasPositiveOffset() == (expectedPosOffset != null));
        assertTrue(offset.hasNegativeOffset() == (expectedNegativeOffset != null));
        if (expectedPosOffset != null) {
            assertEquals(offset.getPositiveOffset(0), expectedPosOffset
                    .intValue());
        }
        if (expectedNegativeOffset != null) {
            assertEquals(offset.getNegativeOffset(0), expectedNegativeOffset
                    .intValue());
        }
    }

    /**
     * Checks the given offsets against the specified values basing upon binary
     * format version 3. Assumes that the location contains of the {@link Lrp}
     * {@link Lrp#LINE_DEC_LRP1 LINE_ENC_LRP1}, {@link Lrp#LINE_DEC_LRP2 LINE_ENC_LRP2} and
     * {@link Lrp#LINE_DEC_LRP3 LINE_ENC_LRP3}.
     *
     * @param offset
     *            The offsets object to check.
     * @param expectedPosOffset
     *            The expected positive offset or <code>null</code> if none is
     *            expected.
     * @param expectedNegativeOffset
     *            The expected negative offset or <code>null</code> if none is
     *            expected.
     */
    private void checkOffsetsV3(final Offsets offset,
                                final Integer expectedPosOffset,
                                final Integer expectedNegativeOffset) {

        assertTrue(offset.hasPositiveOffset() == (expectedPosOffset != null));
        assertTrue(offset.hasNegativeOffset() == (expectedNegativeOffset != null));
        if (expectedPosOffset != null) {
            assertEquals(offset
                            .getPositiveOffset(Lrp.LINE_DEC_LRP1.getDistanceToNext()),
                    expectedPosOffset.intValue());
        }
        if (expectedNegativeOffset != null) {
            assertEquals(offset
                            .getNegativeOffset(Lrp.LINE_DEC_LRP2.getDistanceToNext()),
                    expectedNegativeOffset.intValue());
        }
    }

    /**
     * Tests a valid location with no offsets set.
     */
    @Test
    public final void testValidDataNoOffsets() {

        ByteArray validBinLoc = new ByteArray(VALID_BIN_DATA_NO_OFFSET_V3);

        try {
            RawLocationReference decodeData = decodeData(validBinLoc);
            Lrp[] expectedLrps = new Lrp[]{Lrp.LINE_DEC_LRP1, Lrp.LINE_DEC_LRP2,
                    Lrp.LINE_DEC_LRP3};
            Utils.checkDecodedLrps(decodeData, expectedLrps);
            checkOffsetsV3(decodeData.getOffsets(), null, null);
        } catch (PhysicalFormatException e) {
            fail("failed on valid input", e);
        }
    }

    /**
     * Tests a valid location with no offsets set.
     */
    @Test
    public final void testValidDataBothOffsets() {

        ByteArray validBinLoc = new ByteArray(VALID_BIN_DATA_OFFSETS_V3);

        try {
            RawLocationReference decodeData = decodeData(validBinLoc);
            Lrp[] expectedLrps = new Lrp[]{Lrp.LINE_DEC_LRP1, Lrp.LINE_DEC_LRP2,
                    Lrp.LINE_DEC_LRP3};
            Utils.checkDecodedLrps(decodeData, expectedLrps);
            checkOffsetsV3(decodeData.getOffsets(), EXPECTED_POS_OFFSET_V3,
                    EXPECTED_NEG_OFFSET_V3);
            assertNotNull(decodeData.getOffsets().toString());
        } catch (PhysicalFormatException e) {
            fail("failed on valid input", e);
        }
    }

    /**
     * Tests the example for line location decoding from the OpenLR White Paper.
     */
    @Test
    public final void testWhitepaperExample() {

        ByteArray validBinLoc = new ByteArray(
                OpenLRBinaryEncoderTest.DATA_WP_EXAMPLE);

        try {
            RawLocationReference decodeData = decodeData(validBinLoc);
            Lrp[] expectedLrps = new Lrp[]{Lrp.LINE_DEC_LRP1, Lrp.LINE_DEC_LRP2_WP,
                    Lrp.LINE_DEC_LRP3_WP};
            Utils.checkDecodedLrps(decodeData, expectedLrps);
            checkOffsetsV3(decodeData.getOffsets(),
                    EXPECTED_POS_OFFSET_WP_EXAMPLE, null);
        } catch (PhysicalFormatException e) {
            fail("failed on valid input", e);
        }
    }

    /**
     * Tests decoding of valid binary data provided as a Base 64 string.
     */
    @Test(dependsOnMethods = {"testValidData"})
    public final void testValidDataBase64() {

        ByteArray validBinLoc = new ByteArray(Base64
                .encodeBase64String(VALID_BIN_DATA_POS_OFFSET_V2));
        RawLocationReference decodeData = null;
        try {
            decodeData = decodeData(validBinLoc);
        } catch (PhysicalFormatException e) {
            fail("failed on valid input", e);
        }

        // Verify the data against them from the byte[] input
        List<? extends LocationReferencePoint> b64Points = decodeData
                .getLocationReferencePoints();
        List<? extends LocationReferencePoint> templateLRPs = validDecodeLocation
                .getLocationReferencePoints();
        LocationReferencePoint currentPoint;
        LocationReferencePoint currentB64Point;
        for (int i = 0; i < templateLRPs.size(); i++) {
            currentPoint = templateLRPs.get(i);
            currentB64Point = b64Points.get(i);

            assertEquals(currentPoint.getBearing(), currentB64Point
                    .getBearing());
            assertEquals(currentPoint.getDistanceToNext(), currentB64Point
                    .getDistanceToNext());
            assertEquals(currentPoint.getFOW(), currentB64Point.getFOW());
            assertEquals(currentPoint.getFRC(), currentB64Point.getFRC());
            assertEquals(currentPoint.getLatitudeDeg(), currentB64Point
                    .getLatitudeDeg());
            assertEquals(currentPoint.getLongitudeDeg(), currentB64Point
                    .getLongitudeDeg());
            assertEquals(currentPoint.getLfrc(), currentB64Point.getLfrc());
        }

        Offsets b64Offset = decodeData.getOffsets();
        Offsets templateOffsets = validDecodeLocation.getOffsets();
        assertEquals(templateOffsets.hasPositiveOffset(), b64Offset
                .hasPositiveOffset());
        assertEquals(templateOffsets.hasNegativeOffset(), b64Offset
                .hasNegativeOffset());
        assertEquals(templateOffsets.getPositiveOffset(0), b64Offset
                .getPositiveOffset(0));
        assertEquals(templateOffsets.getNegativeOffset(0), b64Offset
                .getNegativeOffset(0));
    }

    /**
     * Tests decoding of valid binary data provided as input stream.
     */
    @Test
    public final void testValidDataStream() {
        ByteArray validBinLoc = null;
        try {
            validBinLoc = new ByteArray(new ByteArrayInputStream(
                    VALID_BIN_DATA_POS_OFFSET_V2));
        } catch (IOException obe) {
            fail("Unexpected Exception during creation of ByteArray from "
                    + "stream", obe);
        }
        try {
            RawLocationReference locRef = decodeData(validBinLoc);
            Lrp[] expectedLrps = new Lrp[]{Lrp.LINE_DEC_LRP1, Lrp.LINE_DEC_LRP2,
                    Lrp.LINE_DEC_LRP3};
            Utils.checkDecodedLrps(locRef, expectedLrps);
            checkOffsetsV3(locRef.getOffsets(), EXPECTED_POS_OFFSET_V2,
                    EXPECTED_NEG_OFFSET_V2);
        } catch (PhysicalFormatException e) {
            fail("failed on valid input", e);
        }
    }

    /**
     * Tests decoding with invalid binary data.
     */
    @Test
    public final void testInvalidData() {
        ByteArray invalidBinLoc = new ByteArray(INVALID_BIN_LOC_DATA);
        try {
            decodeData(invalidBinLoc);
        } catch (PhysicalFormatException e) {
            fail("failed on invalid input");
        }
    }

    /**
     * Performs the actual decoding of the given {@link ByteArray}.
     *
     * @param locationData The binary location data.
     * @return The decoded location.
     * @throws PhysicalFormatException the physical format exception
     */
    private RawLocationReference decodeData(final ByteArray locationData)
            throws PhysicalFormatException {
        LocationReference lr = new LocationReferenceBinaryImpl("", locationData);
        return DECODER.decodeData(lr);
    }

    /**
     * Tests class {@link ByteArray} with an invalid stream.
     */
    @Test
    public final void testByteArrayInvalidStream() {

        // test handling of invalid stream
        File file = new File("src/test/resources/test01.stream");
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            is.close();

            try {
                new ByteArray(is);
                fail("Exception was expected.");
            } catch (IOException e) {
                // Thats expected
                assertNotNull(e); // to satisfy checkstyle
            }
        } catch (Exception ex) {
            fail("Unexpected Exception during creation of input stream.", ex);
        }

    }

    /**
     * Test the data of initialized {@link ByteArray} objects.
     */
    @Test
    public final void testByteArrayData() {
        ByteArray validBinLoc = new ByteArray(VALID_BIN_DATA_POS_OFFSET_V2);

        for (int i = 0; i < VALID_BIN_DATA_POS_OFFSET_V2.length; i++) {
            assertEquals(VALID_BIN_DATA_POS_OFFSET_V2[i], validBinLoc.get(i),
                    "ByteArray data doesn't fit input data at index " + i);
        }

        try {
            validBinLoc.get(VALID_BIN_DATA_POS_OFFSET_V2.length);
            fail("Exception expected");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Thats expected
            assertNotNull(e); // to satisfy checkstyle
        }
        try {
            validBinLoc.get(Integer.MIN_VALUE);
            fail("Exception expected");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Thats expected
            assertNotNull(e); // to satisfy checkstyle
        }
    }

    /**
     * Test ByteArray's toString() method.
     */
    @Test
    public final void testByteArrayToString() {
        ByteArray ba = new ByteArray(new byte[]{});
        ByteArray ba2 = new ByteArray(VALID_BIN_DATA_NO_OFFSET_V3);

        try {
            assertNotNull(ba.toString());
            assertNotNull(ba2.toString());
        } catch (Exception e) {
            fail("Unexpected exception during ByteArray.toString()");
        }
    }

    /**
     * Test some general methods of the valid {@link RawLocationReference}.
     */
    @Test(dependsOnMethods = {"testValidData"})
    public final void testRawLocationReferenceObject() {
        try {
            assertNotNull(validDecodeLocation.toString());
        } catch (Exception e) {
            fail("Unexpected exception during RawLocationReference.toString()");
        }

        if (validDecodeLocation.hasID()) {
            assertNotNull(validDecodeLocation.getID());
        } else {
            assertNull(validDecodeLocation.getID());
        }
    }

    /**
     * Tests encoding of an invalid stream because of non-zero RFU (reserved for
     * future use) bits.
     */
    @Test(groups = {"broken"})
    public final void testInvalidRFU() {
        byte[] invalidRFU = new byte[VALID_BIN_DATA_OFFSETS_V3.length];
        System.arraycopy(VALID_BIN_DATA_OFFSETS_V3, 0, invalidRFU, 0,
                VALID_BIN_DATA_OFFSETS_V3.length);
        // set bit # 8 (2^7) which is an RFU
        invalidRFU[0] = (byte) (invalidRFU[0] | RFU_POSITION_IN_HEADER);

        ByteArray invalidBinLoc = new ByteArray(invalidRFU);
        try {
            RawLocationReference result = decodeData(invalidBinLoc);
            assertFalse(result.isValid());
            assertSame(result.getReturnCode(), READING_HEADER_FAILURE);
        } catch (PhysicalFormatException e) {
            fail("Unexpected exception!", e);
        }
    }
}
