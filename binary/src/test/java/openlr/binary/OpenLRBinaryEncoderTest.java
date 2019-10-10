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
import openlr.rawLocRef.RawLineLocRef;
import openlr.rawLocRef.RawLocationReference;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static openlr.binary.BinaryReturnCode.*;
import static org.testng.Assert.*;

/**
 * Tests the binary decoder, primarily with line locations.
 */
public class OpenLRBinaryEncoderTest {

    /** The expected result data for the White Paper example of the encoder. */
    static final byte[] DATA_WP_EXAMPLE = {11, 4, 91, 91, 35, 70, -11, 26, 108,
            9, 0, -101, -2, 59, 27, -76, 4, -1, -21, -1, -93, 43, 89, 68};
    /**
     * The expected result data for the example valid location with no
     * bothOffsets.
     */
    private static final byte[] DATA_VALID_NO_OFFSETS_V3 = {11, 4, 91, 91, 35,
            70, -11, 26, 108, 9, 0, -101, -2, 59, 27, -76, 4, -1, -21, -1, -93,
            43, 25};
    /**
     * The expected result data for the example valid location with bothOffsets.
     */
    private static final byte[] DATA_VALID_WITH_OFFSETS_V3 = {11, 4, 91, 91,
            35, 70, -11, 26, 108, 9, 0, -101, -2, 59, 27, -76, 4, -1, -21, -1,
            -93, 43, 121, 68, 93};
    /** The value of the first byte in the expected data of version "2". */
    private static final int DATA_VALID_WITH_OFFSETS_V2_BYTE1 = 10;
    /** The value the positive offset in the expected data of version "2". */
    private static final int POSITIVE_OFFSET_BINARY_V2 = 2;
    /**
     * The positive offset value of the White Paper example.
     */
    private static final int POSITVE_OFFSET_WP_EXAMPLE = 150;
    /**
     * The negative offset value used to create a the mocked offset
     */
    private static final int NEGATIVE_OFFSET = 100;
    /** The positive offset value for the test of a too long offset. */
    private static final int POSITVE_OFFSET_TOO_LONG = 2000;
    /** The Constant BINARY_ENCODER. */
    private static final OpenLRBinaryEncoder BINARY_ENCODER = new OpenLRBinaryEncoder();
    /** The expected result data for the example in binary version 2. */
    private static byte[] dataValidWithOffsetsV2 = new byte[DATA_VALID_WITH_OFFSETS_V3.length];
    /**
     * A map holding the expected data for encoding the
     * {@link #lineLocRefValidWithOffset} assigned to the binary format version.
     */
    private static Map<Integer, byte[]> expectedData;

    static {
        System.arraycopy(DATA_VALID_WITH_OFFSETS_V3, 0, dataValidWithOffsetsV2,
                0, DATA_VALID_WITH_OFFSETS_V3.length);
        dataValidWithOffsetsV2[0] = DATA_VALID_WITH_OFFSETS_V2_BYTE1;
        // The positive offset value in version 2, expected as last but one byte
        dataValidWithOffsetsV2[DATA_VALID_WITH_OFFSETS_V3.length - 2] = POSITIVE_OFFSET_BINARY_V2;
        // The positive offset value in version 2, expected as last byte
        dataValidWithOffsetsV2[DATA_VALID_WITH_OFFSETS_V3.length - 1] = 1;

        expectedData = new HashMap<Integer, byte[]>();
        expectedData.put(OpenLRBinaryConstants.BINARY_VERSION_2,
                dataValidWithOffsetsV2);
        expectedData.put(OpenLRBinaryConstants.BINARY_VERSION_3,
                DATA_VALID_WITH_OFFSETS_V3);
    }

    /** A location reference that contains an empty list for the LRPs. */
    private RawLocationReference lineLocRefEmpty;
    /** A location reference with bothOffsets = null. */
    private RawLocationReference lineLocRefOffsetsNull;
    /** A valid location reference with bothOffsets of zero meters. */
    private RawLocationReference lineLocRefValidOffset0;
    /** A valid location reference with bothOffsets. */
    private RawLocationReference lineLocRefValidWithOffset;
    /** The location reference of the White Paper (v. 1.3) example. */
    private RawLocationReference lineLocRefWpExample;
    /** A valid location reference with a too long offset value. */
    private RawLocationReference lineLocRefValidOffsetTooLong;

    /**
     * Setup.
     */
    @BeforeTest
    public final void setup() {

        final Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);

        final List<LocationReferencePoint> points123 = Utils.mockLrps123(context, new Lrp[]{Lrp.LINE_ENC_LRP1, Lrp.LINE_ENC_LRP2,
                Lrp.LINE_ENC_LRP3});

        final Offsets zeroOffsets = Utils.mockOffsets(context, 0, 0);

        List<LocationReferencePoint> empty = Collections.emptyList();
        lineLocRefEmpty = new RawLineLocRef("", empty, zeroOffsets);
        lineLocRefOffsetsNull = new RawLineLocRef("", points123, null);
        lineLocRefValidOffset0 = new RawLineLocRef("", points123, zeroOffsets);
        lineLocRefValidWithOffset = new RawLineLocRef("", points123, Utils.mockOffsets(context,
                POSITVE_OFFSET_WP_EXAMPLE, NEGATIVE_OFFSET));
        lineLocRefWpExample = new RawLineLocRef("", points123, Utils.mockOffsets(context,
                POSITVE_OFFSET_WP_EXAMPLE, 0));
        lineLocRefValidOffsetTooLong = new RawLineLocRef("", points123, Utils.mockOffsets(context, POSITVE_OFFSET_TOO_LONG,
                0));
    }


    /**
     * Tests the example for line location encoding from the OpenLR White Paper.
     */
    @Test
    public final void testWhitepaperExample() {
        LocationReference ref = null;
        ref = BINARY_ENCODER.encodeData(lineLocRefWpExample);
        assertTrue(ref.isValid());

        assertSame(ref.getVersion(), OpenLRBinaryConstants.BINARY_VERSION_3);
        Utils.checkBinData((ByteArray) ref.getLocationReferenceData(),
                DATA_WP_EXAMPLE, OpenLRBinaryConstants.BINARY_VERSION_3);
    }

    /**
     * Tests encoding with an location with empty list of LRPs.
     */
    @Test
    public final void testInvalidLocationEncoding() {
        LocationReference result = BINARY_ENCODER.encodeData(lineLocRefEmpty);
        assertFalse(result.isValid());
        assertSame(result.getReturnCode(), MISSING_DATA);
        assertNotNull(result.toString());
        assertNull(result.getLocationReferenceData());
    }

    /**
     * Tests encoding with an location containing <code>null</code> for the
     * bothOffsets.
     */
    @Test
    public final void testOffsetsNull() {
        LocationReference result = BINARY_ENCODER
                .encodeData(lineLocRefOffsetsNull);
        assertFalse(result.isValid());
        assertSame(result.getReturnCode(), MISSING_DATA);
    }

    /**
     * Tests encoding of a valid location containing bothOffsets with value
     * zero.
     */
    @Test
    public final void testValidWithNoOffsets() {
        LocationReference ref = null;
        ref = BINARY_ENCODER.encodeData(lineLocRefValidOffset0);
        assertTrue(ref.isValid());
        if (!"binary".equals(ref.getDataIdentifier())) {
            fail("Invalid data identifier");
        }
        if (!checkDataClass(ref)) {
            fail("invalid data class");
        }

        assertSame(ref.getVersion(),
                OpenLRBinaryConstants.LATEST_BINARY_VERSION);

        Utils.checkBinData((ByteArray) ref.getLocationReferenceData(),
                DATA_VALID_NO_OFFSETS_V3,
                OpenLRBinaryConstants.LATEST_BINARY_VERSION);
    }

    /**
     * Tests encoding of a valid location containing bothOffsets with value <>
     * zero.
     */
    @Test
    public final void testValidWithOffsets() {
        LocationReference ref = null;
        ref = BINARY_ENCODER.encodeData(lineLocRefValidWithOffset);
        assertTrue(ref.isValid());
        if (!"binary".equals(ref.getDataIdentifier())) {
            fail("Invalid data identifier");
        }
        if (!checkDataClass(ref)) {
            fail("invalid data class");
        }

        assertSame(ref.getVersion(),
                OpenLRBinaryConstants.LATEST_BINARY_VERSION);
        Utils.checkBinData((ByteArray) ref.getLocationReferenceData(),
                DATA_VALID_WITH_OFFSETS_V3,
                OpenLRBinaryConstants.LATEST_BINARY_VERSION);
    }

    /**
     * Test encoding with a too long offset value.
     */
    @Test
    public final void testOffsetsTooLong() {
        LocationReference ref = null;
        ref = BINARY_ENCODER.encodeData(lineLocRefValidOffsetTooLong);
        assertFalse(ref.isValid());
        assertSame(ref.getReturnCode(), INVALID_OFFSET);

    }

    /**
     * Test encoding with all supported versions.
     */
    @Test
    public final void testVersions() {
        LocationReference ref;

        int[] versions = BINARY_ENCODER.getSupportedVersions();
        for (int version : versions) {
            ref = BINARY_ENCODER.encodeData(lineLocRefValidWithOffset, version);
            assertTrue(ref.isValid(), "Invalid location after encoding with"
                    + " binary version " + version);

            assertSame(ref.getVersion(), version);

            byte[] expected = expectedData.get(version);
            assertNotNull(expected,
                    "There is no expected encoder data for version " + version
                            + " configured!");
            Utils.checkBinData((ByteArray) ref.getLocationReferenceData(),
                    expected, version);

        }

        // test unknown version
        ref = BINARY_ENCODER.encodeData(lineLocRefValidWithOffset,
                Integer.MAX_VALUE);
        assertFalse(ref.isValid());
        assertSame(ref.getReturnCode(), INVALID_VERSION);

    }

    /**
     * Check data class.
     *
     * @param ref
     *            the location reference
     *
     * @return true, if successful
     */
    private boolean checkDataClass(final LocationReference ref) {
        return (ref.getLocationReferenceData() instanceof ByteArray);
    }

}
