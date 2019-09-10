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
import openlr.map.GeoCoordinates;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.InvalidMapDataException;
import openlr.map.utils.GeometryUtils;
import openlr.rawLocRef.RawLocationReference;
import openlr.rawLocRef.RawPoiAccessLocRef;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.awt.geom.Point2D;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static openlr.binary.BinaryReturnCode.MISSING_DATA;
import static org.testng.Assert.*;

/**
 * The Class OpenLRBinaryEncoderTest.
 */
public class PoiWithAccessTest {

    /**
     * The geo coordinates of the point of interest of the White Paper example
     * as input for encoding (x = longitude, y latitude).
     */
    private static final Point2D.Double WP_GEO_COORDINATE_ENC = new Point2D.Double(
            6.12699, 49.60728);
    /**
     * The geo coordinates of the point of interest of the White Paper example
     * as expected as output of the decoding (x = longitude, y latitude).
     */
    private static final Point2D.Double WP_GEO_COORDINATE_DEC = new Point2D.Double(
            6.127, 49.60727);

    /**
     * The expected result of the binary decoding of the White Paper example.
     */
    private static final byte[] EXPECTED_RESULT_WP_ENCODING = new byte[]{43,
            4, 91, -96, 35, 70, 126, 18, 81, 1, -1, -50, -1, -76, -110, 67, 77,
            -1, 126, 0, -125};

    /**
     * Valid binary data containing no offsets.
     */
    private static final byte[] VALID_BIN_DATA_NO_OFFSETS = new byte[]{43, 4,
            91, -96, 35, 70, 126, 18, 81, 1, -1, -50, -1, -76, -110, 3, -1,
            126, 0, -125};

    /** The value of the positive offset used in the WP example. */
    private static final int POSITIVE_OFFSET = 28;

    /** The orientation of the location reference example from the White Paper. */
    private static final Orientation WP_EXAMPLE_ORIENTATION = Orientation.NO_ORIENTATION_OR_UNKNOWN;
    /** The side-of-road of the location reference example from the White Paper. */
    private static final SideOfRoad WP_EXAMPLE_SIDE_OF_ROAD = SideOfRoad.LEFT;

    /** The Constant BINARY_ENCODER. */
    private static final OpenLRBinaryEncoder BINARY_ENCODER = new OpenLRBinaryEncoder();

    /** The mocked RawLocationReference of the White Paper example. */
    private RawLocationReference rawLocRefWPExample;

    /** A location reference with value <code>null<code> for the geo coordinate. */
    private RawLocationReference rawLocRefCoordinateNull;

    /**
     * A reference to the valid encoded location of the White Paper example.
     */
    private LocationReference validEncodedWPExample;

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
                will(returnValue(POSITIVE_OFFSET));
            }

            {
                allowing(off1).getNegativeOffset(with(any(int.class)));
                will(returnValue(0));
            }
        });
        try {
            final GeoCoordinates coord = new GeoCoordinatesImpl(WP_GEO_COORDINATE_ENC.x, WP_GEO_COORDINATE_ENC.y);

            final List<LocationReferencePoint> lrps = mockLrps12(context);
            rawLocRefWPExample = new RawPoiAccessLocRef("", lrps.get(0), lrps.get(1), off1, coord, SideOfRoad.LEFT, Orientation.NO_ORIENTATION_OR_UNKNOWN);
            rawLocRefCoordinateNull = new RawPoiAccessLocRef("", lrps.get(0), lrps.get(1), off1, null, SideOfRoad.RIGHT, Orientation.AGAINST_LINE_DIRECTION);
        } catch (InvalidMapDataException e) {
            Assert.fail("Unexpected exception", e);
        }

    }

    /**
     * Mocks the LRPs used in the tests of this class.
     *
     * @param context
     *            The mocking object.
     * @return A list of the mocked LRPs 1 and 2 in exactly this order.
     */
    private List<LocationReferencePoint> mockLrps12(final Mockery context) {
        final LocationReferencePoint lrp1;

        final LocationReferencePoint lrp2;
        lrp1 = context.mock(LocationReferencePoint.class, "lrp1");
        lrp2 = context.mock(LocationReferencePoint.class, "lrp2");
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
                allowing(lrp2).getLfrc();
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
     * Tests the encoding of the point-with-access-point example from the White
     * Paper (v 1.3).
     */
    @Test
    public final void testEncodingWhitePaperExample() {
        validEncodedWPExample = BINARY_ENCODER.encodeData(rawLocRefWPExample);

        assertNotNull(validEncodedWPExample.toString());

        assertTrue(validEncodedWPExample.isValid());

        assertSame(BINARY_ENCODER.getDataClass(),
                validEncodedWPExample.getDataClass());
        assertSame(BINARY_ENCODER.getDataFormatIdentifier(),
                validEncodedWPExample.getDataIdentifier());

        assertSame(validEncodedWPExample.getLocationType(),
                LocationType.POI_WITH_ACCESS_POINT);

        Utils.checkBinData(
                (ByteArray) validEncodedWPExample.getLocationReferenceData(),
                EXPECTED_RESULT_WP_ENCODING,
                OpenLRBinaryConstants.LATEST_BINARY_VERSION);
    }

    /**
     * Tests decoding of the White Paper example.
     */
    @Test(dependsOnMethods = {"testEncodingWhitePaperExample"})
    public final void testDecodingWhitePaperExample() {
        OpenLRBinaryDecoder decoder = new OpenLRBinaryDecoder();

        try {
            RawLocationReference decodedData = decoder.decodeData(validEncodedWPExample);

            assertEquals(decodedData.getLocationType(),
                    LocationType.POI_WITH_ACCESS_POINT);

            GeoCoordinates gc = decodedData.getGeoCoordinates();

            assertNotNull(gc.toString());

            double logRounded = GeometryUtils.round(gc.getLongitudeDeg());

            double latRounded = GeometryUtils.round(gc.getLatitudeDeg());

            assertEquals(logRounded, WP_GEO_COORDINATE_DEC.x);
            assertEquals(latRounded, WP_GEO_COORDINATE_DEC.y);

            Offsets offsets = decodedData.getOffsets();

            assertFalse(offsets.hasNegativeOffset());
            assertTrue(offsets.hasPositiveOffset());
            assertEquals(offsets.getNegativeOffset(Lrp.PL_ENC_LRP1
                    .getDistanceToNext()), 0);
            assertEquals(offsets.getPositiveOffset(Lrp.PL_ENC_LRP1
                    .getDistanceToNext()), POSITIVE_OFFSET);

            assertSame(decodedData.getSideOfRoad(), WP_EXAMPLE_SIDE_OF_ROAD);
            assertSame(decodedData.getOrientation(), WP_EXAMPLE_ORIENTATION);

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

        ByteArray ba = new ByteArray(VALID_BIN_DATA_NO_OFFSETS);

        try {
            LocationReference lr = new LocationReferenceBinaryImpl("", ba);
            RawLocationReference decodedData = decoder.decodeData(lr);

            assertEquals(decodedData.getLocationType(),
                    LocationType.POI_WITH_ACCESS_POINT);

            Utils.checkLrps(decodedData.getLocationReferencePoints(),
                    Lrp.PWA_DEC_LRP1, Lrp.PWA_DEC_LRP2);

            assertEquals(
                    decodedData.getOffsets().getPositiveOffset(
                            Lrp.PWA_DEC_LRP1.getDistanceToNext()), 0);
            assertEquals(decodedData.getOrientation(), WP_EXAMPLE_ORIENTATION);
            assertEquals(decodedData.getSideOfRoad(), WP_EXAMPLE_SIDE_OF_ROAD);

        } catch (PhysicalFormatException e) {
            fail("Unexpected exception", e);
        }
    }

    /**
     * Tests the case that geo coordinate is <code>null</code> in the location
     * ref.
     */
    @Test
    public final void testEncodingCoordinateNull() {
        LocationReference ref = BINARY_ENCODER
                .encodeData(rawLocRefCoordinateNull);
        assertFalse(ref.isValid());
        assertSame(ref.getReturnCode(), MISSING_DATA);
    }

    /**
     * Tests the toStream() method of {@link LocationReference}.
     */
    @Test
    public final void testLocRefToStream() {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            validEncodedWPExample.toStream(os);
            Utils.checkBinData((ByteArray) validEncodedWPExample
                    .getLocationReferenceData(), os.toByteArray(), null);
        } catch (OpenLRProcessingException e) {
            fail("Unexpected exception", e);
        } catch (IOException e) {
            fail("Unexpected exception", e);
        }
    }

    /**
     * Tests method {@link LocationReferenceBinaryImpl#resolveLocationType} with
     * an byte array of invalid length.
     */
    @Test
    public final void testBinaryLocationType() {
        byte[] invalidLength = new byte[VALID_BIN_DATA_NO_OFFSETS.length - 2];
        System.arraycopy(VALID_BIN_DATA_NO_OFFSETS, 0, invalidLength, 0,
                VALID_BIN_DATA_NO_OFFSETS.length - 2);
        try {
            LocationReferenceBinaryImpl.resolveLocationType(invalidLength);
            fail("Exception expected!");
        } catch (PhysicalFormatException e) {
            assertNotNull(e);
        }
    }
}
