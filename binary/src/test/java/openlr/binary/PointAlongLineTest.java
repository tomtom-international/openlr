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
package openlr.binary;


import openlr.*;
import openlr.binary.impl.LocationReferenceBinaryImpl;
import openlr.location.data.Orientation;
import openlr.location.data.SideOfRoad;
import openlr.rawLocRef.RawLocationReference;
import openlr.rawLocRef.RawPointAlongLocRef;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static openlr.binary.BinaryReturnCode.*;
import static org.testng.Assert.*;

/**
 * The Class OpenLRBinaryEncoderTest.
 */
public class PointAlongLineTest {

    /**
     * The positive offset value of the mocked offset for the white paper
     * encoder example.
     */
    private static final int POS_OFFSET_WP_EXAMPLE_IN = 28;
    /**
     * The positive expected after decoding the encoded location of the wp example.
     */
    private static final int POS_OFFSET_WP_EXAMPLE_OUT = 27;

    /** The expected result of the white paper example test. */
    private static final byte[] EXPECTED_RESULT_WP_ENCODING = new byte[]{43,
            4, 91, -96, 35, 70, 126, 18, 81, 1, -1, -50, -1, -76, -110, 67, 77};

    /** Valid bin data without offset. */
    private static final byte[] VALID_BIN_DATA_NO_OFFSET = new byte[]{43, 4,
            91, -96, 35, 70, 126, 18, 81, 1, -1, -50, -1, -76, -110, 3};

    /** The Constant BINARY_ENCODER. */
    private static final OpenLRBinaryEncoder BINARY_ENCODER = new OpenLRBinaryEncoder();
    /** The orientation of the location reference example from the white paper. */
    private static final Orientation WP_EXAMPLE_ORIENTATION =
            Orientation.NO_ORIENTATION_OR_UNKNOWN;
    /** The side-of-road of the location reference example from the white paper. */
    private static final SideOfRoad WP_EXAMPLE_SIDE_OF_ROAD = SideOfRoad.LEFT;
    /** A reference to the valid encoded location of the white paper example. */
    private LocationReference validEncodedLocation;
    /**
     * The location reference for the white paper example.
     */
    private RawLocationReference rawLocRefWPExample;

    /** The location reference for an example with invalid offset.*/
    private RawLocationReference rawLocRefInvalidOffset;
    /** A location reference with value <code>null<code> for the offsets. */
    private RawLocationReference rawLocRefOffsetsNull;


    /**
     * Setup.
     */
    @BeforeTest
    public final void setup() {
        Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);

        final Offsets off1 = context.mock(Offsets.class, "off1");

        context.checking(new Expectations() {
            {
                allowing(off1).hasNegativeOffset();
                will(returnValue(false));
            }

            {
                allowing(off1).hasPositiveOffset();
                will(returnValue(true));
            }

            {
                allowing(off1).getPositiveOffset(with(any(int.class)));
                will(returnValue(POS_OFFSET_WP_EXAMPLE_IN));
            }

            {
                allowing(off1).getNegativeOffset(with(any(int.class)));
                will(returnValue(0));
            }
        });
        final Offsets tooLongOffset = context.mock(Offsets.class,
                "tooLongOffset");
        context.checking(new Expectations() {
            {
                allowing(tooLongOffset).hasNegativeOffset();
                will(returnValue(false));
            }

            {
                allowing(tooLongOffset).hasPositiveOffset();
                will(returnValue(true));
            }

            {
                allowing(tooLongOffset).getPositiveOffset(with(any(int.class)));
                will(returnValue(Integer.MAX_VALUE));
            }

            {
                allowing(tooLongOffset).getNegativeOffset(with(any(int.class)));
                will(returnValue(0));
            }
        });

        final List<LocationReferencePoint> lrps12 = mockLrps12(context);
        rawLocRefWPExample = new RawPointAlongLocRef("", lrps12.get(0), lrps12.get(1), off1, WP_EXAMPLE_SIDE_OF_ROAD, WP_EXAMPLE_ORIENTATION);
        rawLocRefInvalidOffset = new RawPointAlongLocRef("", lrps12.get(0), lrps12.get(1), tooLongOffset, SideOfRoad.RIGHT, WP_EXAMPLE_ORIENTATION);
        rawLocRefOffsetsNull = new RawPointAlongLocRef("", lrps12.get(0), lrps12.get(1), null, SideOfRoad.RIGHT, Orientation.AGAINST_LINE_DIRECTION);
    }


    /**
     * Mocks LRPs 1 and 2 that are used in tests of this class.
     *
     * @param context The mocking context.
     * @return A list of both LRPs ordered in sequence 1, 2.
     */
    private List<LocationReferencePoint> mockLrps12(final Mockery context) {
        final LocationReferencePoint lrp1;
        final LocationReferencePoint lrp2;
        lrp1 = context.mock(LocationReferencePoint.class, "lrp1");
        lrp2 = context.mock(
                LocationReferencePoint.class, "lrp2");
        context.checking(new Expectations() {
            {
                allowing(lrp1).getLongitudeDeg();
                will(returnValue(Lrp.PL_ENC_LRP1.getLongitude()));
            }

            {
                allowing(lrp1).getLatitudeDeg();
                will(returnValue(Lrp.PL_ENC_LRP1.getLatitude()));
            }

            {
                allowing(lrp1).getBearing();
                will(returnValue(Lrp.PL_ENC_LRP1.getBearing()));
            }

            {
                allowing(lrp1).getDistanceToNext();
                will(returnValue(Lrp.PL_ENC_LRP1.getDistanceToNext()));
            }

            {
                allowing(lrp1).getFOW();
                will(returnValue(Lrp.PL_ENC_LRP1.getFow()));
            }

            {
                allowing(lrp1).getFRC();
                will(returnValue(Lrp.PL_ENC_LRP1.getFrc()));
            }

            {
                allowing(lrp1).getLfrc();
                will(returnValue(Lrp.PL_ENC_LRP1.getLfrcnp()));
            }

            {
                allowing(lrp1).isLastLRP();
                will(returnValue(false));
            }
        });
        context.checking(new Expectations() {
            {
                allowing(lrp2).getLongitudeDeg();
                will(returnValue(Lrp.PL_ENC_LRP2.getLongitude()));
            }

            {
                allowing(lrp2).getLatitudeDeg();
                will(returnValue(Lrp.PL_ENC_LRP2.getLatitude()));
            }

            {
                allowing(lrp2).getBearing();
                will(returnValue(Lrp.PL_ENC_LRP2.getBearing()));
            }

            {
                allowing(lrp2).getDistanceToNext();
                will(returnValue(Lrp.PL_ENC_LRP2.getDistanceToNext()));
            }

            {
                allowing(lrp2).getFOW();
                will(returnValue(Lrp.PL_ENC_LRP2.getFow()));
            }

            {
                allowing(lrp2).getFRC();
                will(returnValue(Lrp.PL_ENC_LRP2.getFrc()));
            }

            {
                allowing(lrp1).getLfrc();
                will(returnValue(Lrp.PL_ENC_LRP2.getLfrcnp()));
            }

            {
                allowing(lrp2).isLastLRP();
                will(returnValue(true));
            }
        });

        final List<LocationReferencePoint> lrps = new ArrayList<LocationReferencePoint>();
        lrps.add(lrp1);
        lrps.add(lrp2);
        return lrps;
    }


    /**
     * Tests the binary encoding of the example of binary encoding in the
     * OpenLR WhitePaper (v1.3).
     */
    @Test
    public final void testEncoderWhitePaperExample() {
        validEncodedLocation = BINARY_ENCODER.encodeData(rawLocRefWPExample);

        assertSame(validEncodedLocation.getLocationType(),
                LocationType.POINT_ALONG_LINE);
        assertSame(validEncodedLocation.getVersion(),
                OpenLRBinaryConstants.LATEST_BINARY_VERSION);
        assertEquals(validEncodedLocation.getDataIdentifier(), BINARY_ENCODER
                .getDataFormatIdentifier());

        assertEquals(validEncodedLocation.getDataClass(), BINARY_ENCODER
                .getDataClass());

        Utils.checkBinData((ByteArray) validEncodedLocation
                        .getLocationReferenceData(), EXPECTED_RESULT_WP_ENCODING,
                OpenLRBinaryConstants.LATEST_BINARY_VERSION);
    }

    /**
     * Tests the case that offsets are <code>null</code> in the location ref.
     */
    @Test
    public final void testEncodingOffsetNull() {
        LocationReference result = BINARY_ENCODER.encodeData(rawLocRefOffsetsNull);
        assertFalse(result.isValid());
        assertSame(result.getReturnCode(), MISSING_DATA);
    }

    /**
     * Tests the binary encoding with an invalid version specified.
     */
    @Test
    public final void testWrongVersion() {
        LocationReference result = BINARY_ENCODER.encodeData(rawLocRefWPExample,
                OpenLRBinaryConstants.BINARY_VERSION_2);
        assertFalse(result.isValid());
        assertSame(result.getReturnCode(), INVALID_VERSION);
    }

    /**
     * Tests in encoding with a too long offset.
     */
    @Test
    public final void testEncodingToolongOffset() {
        LocationReference ref = BINARY_ENCODER.encodeData(rawLocRefInvalidOffset);
        assertFalse(ref.isValid());
        assertSame(ref.getReturnCode(), INVALID_OFFSET);
    }

    /**
     * Tests decoding of the validly encoded point along line location.
     */
    @Test(dependsOnMethods = {"testEncoderWhitePaperExample"})
    public final void testValidLocationDecoding() {
        OpenLRBinaryDecoder decoder = new OpenLRBinaryDecoder();

        try {
            RawLocationReference decodedData = decoder.decodeData(validEncodedLocation);

            assertEquals(decodedData.getLocationType(),
                    LocationType.POINT_ALONG_LINE);

            Utils.checkLrps(decodedData.getLocationReferencePoints(),
                    Lrp.PAL_DEC_LRP1, Lrp.PAL_DEC_LRP2);

            assertEquals(decodedData.getOffsets().getPositiveOffset(
                    Lrp.PAL_DEC_LRP1.getDistanceToNext()), POS_OFFSET_WP_EXAMPLE_OUT);
            assertEquals(decodedData.getOrientation(), WP_EXAMPLE_ORIENTATION);
            assertEquals(decodedData.getSideOfRoad(), WP_EXAMPLE_SIDE_OF_ROAD);

        } catch (PhysicalFormatException e) {
            fail("Unexpected exception", e);
        }
    }

    /**
     * Tests decoding of a valid location with no offset.
     */
    @Test
    public final void testDecoderNoOffset() {
        OpenLRBinaryDecoder decoder = new OpenLRBinaryDecoder();

        ByteArray ba = new ByteArray(VALID_BIN_DATA_NO_OFFSET);

        try {
            LocationReference lr = new LocationReferenceBinaryImpl("", ba);
            RawLocationReference decodedData = decoder.decodeData(lr);

            assertEquals(decodedData.getLocationType(),
                    LocationType.POINT_ALONG_LINE);

            Utils.checkLrps(decodedData.getLocationReferencePoints(),
                    Lrp.PAL_DEC_LRP1, Lrp.PAL_DEC_LRP2);

            assertEquals(decodedData.getOffsets().getPositiveOffset(
                    Lrp.PAL_DEC_LRP1.getDistanceToNext()), 0);
            assertEquals(decodedData.getOrientation(), WP_EXAMPLE_ORIENTATION);
            assertEquals(decodedData.getSideOfRoad(), WP_EXAMPLE_SIDE_OF_ROAD);

        } catch (PhysicalFormatException e) {
            fail("Unexpected exception", e);
        }
    }
}
