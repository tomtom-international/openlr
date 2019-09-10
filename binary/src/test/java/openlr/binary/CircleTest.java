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

import openlr.LocationReference;
import openlr.LocationType;
import openlr.PhysicalFormatException;
import openlr.binary.OpenLRBinaryException.PhysicalFormatError;
import openlr.binary.impl.LocationReferenceBinaryImpl;
import openlr.map.GeoCoordinates;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.utils.GeometryUtils;
import openlr.rawLocRef.RawCircleLocRef;
import openlr.rawLocRef.RawLocationReference;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * The Class CircleTest.
 */
public class CircleTest {

    /** The latitude of the tested geo coordinate. */
    private static final double COORDINATE_LATITUDE = 49.60728;
    /** The longitude of the tested geo coordinate. */
    private static final double COORDINATE_LONGITUDE = 6.12699;

    /**
     * The latitude of the geo coordinate of the result of decoding. It differs
     * from the input {@link #COORDINATE_LATITUDE} because of the conversion
     * algorithm into 24 bit during encoding.
     */
    private static final double DECODED_COORDINATE_LATITUDE = 49.60727;
    /** The longitude of the geo coordinate of the result of decoding. */
    private static final double DECODED_COORDINATE_LONGITUDE = COORDINATE_LONGITUDE;

    /** The expected result of the valid binary encoding. */
    private static final byte[] EXPECTED_ENCODING_RESULT_SMALL = new byte[]{
            0x03, 0x04, 0x5b, 0x63, 0x23, 0x46, -0x45, 0x64};

    /** The Constant EXPECTED_ENCODING_RESULT_MEDIUM. */
    private static final byte[] EXPECTED_ENCODING_RESULT_MEDIUM = new byte[]{
            0x03, 0x04, 0x5b, 0x63, 0x23, 0x46, -0x45, -0x16, 0x60};

    /** The Constant EXPECTED_ENCODING_RESULT_LARGE. */
    private static final byte[] EXPECTED_ENCODING_RESULT_LARGE = new byte[]{
            0x03, 0x04, 0x5b, 0x63, 0x23, 0x46, -0x45, -0x0C, 0x24, 0x00};

    /** The Constant EXPECTED_ENCODING_RESULT_EXTRA_LARGE. */
    private static final byte[] EXPECTED_ENCODING_RESULT_EXTRA_LARGE = new byte[]{
            0x03, 0x04, 0x5b, 0x63, 0x23, 0x46, -0x45, -0x12, 0x6B, 0x28, 0x00};

    /** The Constant RADIUS. */
    private static final long RADIUS_SMALL = 100;

    /** The Constant RADIUS_MEDIUM. */
    private static final long RADIUS_MEDIUM = 60000;

    /** The Constant RADIUS_LARGE. */
    private static final long RADIUS_LARGE = 16000000;

    /** The Constant RADIUS_EXTRA_LARGE. */
    private static final long RADIUS_EXTRA_LARGE = 4000000000L;

    /** The Constant RADIUS_EXTRA_LARGE. */
    private static final long RADIUS_INVALID = 5000000000L;

    /** The Constant BINARY_ENCODER. */
    private static final OpenLRBinaryEncoder BINARY_ENCODER = new OpenLRBinaryEncoder();

    /** The mocked geo coordinate object. */
    private GeoCoordinates coord;

    /** The mocked geo coordinate location. */
    private RawLocationReference rawLocRefSmall;

    /** The mocked geo coordinate location. */
    private RawLocationReference rawLocRefMedium;

    /** The mocked geo coordinate location. */
    private RawLocationReference rawLocRefLarge;

    /** The mocked geo coordinate location. */
    private RawLocationReference rawLocRefExtraLarge;

    /** The mocked geo coordinate location. */
    private RawLocationReference rawLocRefInvalid;

    /** The mocked geo coordinate location with coordinates == null. */
    private RawLocationReference rawLocRefCoordsNull;

    /** A reference to the valid encoded location of the corresponding test. */
    private LocationReference validEncodedLocationSmall;

    /** The valid encoded location medium. */
    private LocationReference validEncodedLocationMedium;

    /** The valid encoded location large. */
    private LocationReference validEncodedLocationLarge;

    /** The valid encoded location extra large. */
    private LocationReference validEncodedLocationExtraLarge;

    /**
     * Setup.
     */
    @BeforeTest
    public final void setup() {
        try {
            coord = new GeoCoordinatesImpl(COORDINATE_LONGITUDE,
                    COORDINATE_LATITUDE);
            rawLocRefSmall = new RawCircleLocRef("", coord, RADIUS_SMALL);
            rawLocRefCoordsNull = new RawCircleLocRef("", null, RADIUS_SMALL);
            rawLocRefMedium = new RawCircleLocRef("", coord, RADIUS_MEDIUM);
            rawLocRefLarge = new RawCircleLocRef("", coord, RADIUS_LARGE);
            rawLocRefExtraLarge = new RawCircleLocRef("", coord,
                    RADIUS_EXTRA_LARGE);
            rawLocRefInvalid = new RawCircleLocRef("", coord, RADIUS_INVALID);
        } catch (Exception e) {
            Assert.fail("Unexpected exception", e);
        }
    }

    /**
     * Test circle example encoding invalid radius.
     */
    @Test
    public final void testCircleExampleEncodingInvalidRadius() {

        LocationReference lr = BINARY_ENCODER.encodeData(rawLocRefInvalid);

        Assert.assertNotNull(lr);
        Assert.assertFalse(lr.isValid());
        assertSame(lr.getReturnCode(), BinaryReturnCode.INVALID_BINARY_DATA);
    }

    /**
     * Tests encoding of the valid geo location used in the OpenLr White Paper.
     */
    @Test
    public final void testCircleExampleEncodingSmall() {

        validEncodedLocationSmall = BINARY_ENCODER.encodeData(rawLocRefSmall);

        Assert.assertNotNull(validEncodedLocationSmall);
        Assert.assertTrue(validEncodedLocationSmall.isValid());

        ByteArray ba = (ByteArray) validEncodedLocationSmall
                .getLocationReferenceData();

        Assert.assertSame(validEncodedLocationSmall.getVersion(),
                OpenLRBinaryConstants.LATEST_BINARY_VERSION);

        Utils.checkBinData(ba, EXPECTED_ENCODING_RESULT_SMALL,
                validEncodedLocationSmall.getVersion());
    }

    /**
     * Tests encoding of the valid geo location used in the OpenLr White Paper.
     */
    @Test
    public final void testCircleExampleEncodingMedium() {

        validEncodedLocationMedium = BINARY_ENCODER.encodeData(rawLocRefMedium);

        Assert.assertNotNull(validEncodedLocationMedium);
        Assert.assertTrue(validEncodedLocationMedium.isValid());

        ByteArray ba = (ByteArray) validEncodedLocationMedium
                .getLocationReferenceData();

        Assert.assertSame(validEncodedLocationMedium.getVersion(),
                OpenLRBinaryConstants.LATEST_BINARY_VERSION);

        Utils.checkBinData(ba, EXPECTED_ENCODING_RESULT_MEDIUM,
                validEncodedLocationMedium.getVersion());
    }

    /**
     * Tests encoding of the valid geo location used in the OpenLr White Paper.
     */
    @Test
    public final void testCircleExampleEncodingLarge() {

        validEncodedLocationLarge = BINARY_ENCODER.encodeData(rawLocRefLarge);

        Assert.assertNotNull(validEncodedLocationLarge);
        Assert.assertTrue(validEncodedLocationLarge.isValid());

        ByteArray ba = (ByteArray) validEncodedLocationLarge
                .getLocationReferenceData();

        Assert.assertSame(validEncodedLocationLarge.getVersion(),
                OpenLRBinaryConstants.LATEST_BINARY_VERSION);

        Utils.checkBinData(ba, EXPECTED_ENCODING_RESULT_LARGE,
                validEncodedLocationLarge.getVersion());
    }

    /**
     * Tests encoding of the valid geo location used in the OpenLr White Paper.
     */
    @Test
    public final void testCircleExampleEncodingExtraLarge() {

        validEncodedLocationExtraLarge = BINARY_ENCODER
                .encodeData(rawLocRefExtraLarge);

        Assert.assertNotNull(validEncodedLocationExtraLarge);
        Assert.assertTrue(validEncodedLocationExtraLarge.isValid());

        ByteArray ba = (ByteArray) validEncodedLocationExtraLarge
                .getLocationReferenceData();

        Assert.assertSame(validEncodedLocationExtraLarge.getVersion(),
                OpenLRBinaryConstants.LATEST_BINARY_VERSION);

        Utils.checkBinData(ba, EXPECTED_ENCODING_RESULT_EXTRA_LARGE,
                validEncodedLocationExtraLarge.getVersion());
    }

    /**
     * Test encoding of a location reference containing <code>null</code> for
     * the geo coordinate field.
     */
    @Test
    public final void testGeoCoordinatesNullEncoding() {

        LocationReference result = BINARY_ENCODER
                .encodeData(rawLocRefCoordsNull);
        assertFalse(result.isValid());
        assertSame(result.getReturnCode(), BinaryReturnCode.MISSING_DATA);
    }

    /**
     * Tests an encoder call with an invalid version parameter.
     */
    @Test
    public final void testInvalidVersionEncoding() {

        LocationReference result = BINARY_ENCODER.encodeData(rawLocRefSmall,
                OpenLRBinaryConstants.BINARY_VERSION_2);
        assertFalse(result.isValid());
        assertSame(result.getReturnCode(), BinaryReturnCode.INVALID_VERSION);

        result = BINARY_ENCODER.encodeData(rawLocRefSmall, Integer.MAX_VALUE);
        assertFalse(result.isValid());
        assertSame(result.getReturnCode(), BinaryReturnCode.INVALID_VERSION);
    }

    /**
     * Tests decoding of the valid geo location used in the OpenLr White Paper.
     */
    @Test(dependsOnMethods = {"testCircleExampleEncodingSmall"})
    public final void testCircleExampleDecodingSmall() {
        OpenLRBinaryDecoder decoder = new OpenLRBinaryDecoder();

        try {
            RawLocationReference decodedData = decoder
                    .decodeData(validEncodedLocationSmall);

            assertEquals(decodedData.getLocationType(), LocationType.CIRCLE);

            GeoCoordinates gc = decodedData.getCenterPoint();
            long radius = decodedData.getRadius();

            double logRounded = GeometryUtils.round(gc.getLongitudeDeg());

            double latRounded = GeometryUtils.round(gc.getLatitudeDeg());

            assertEquals(logRounded, DECODED_COORDINATE_LONGITUDE);
            assertEquals(latRounded, DECODED_COORDINATE_LATITUDE);
            assertEquals(radius, RADIUS_SMALL);
        } catch (PhysicalFormatException e) {
            fail("exception", e);
        }
    }

    /**
     * Tests decoding of the valid geo location used in the OpenLr White Paper.
     */
    @Test(dependsOnMethods = {"testCircleExampleEncodingMedium"})
    public final void testCircleExampleDecodingMedium() {
        OpenLRBinaryDecoder decoder = new OpenLRBinaryDecoder();

        try {
            RawLocationReference decodedData = decoder
                    .decodeData(validEncodedLocationMedium);

            assertEquals(decodedData.getLocationType(), LocationType.CIRCLE);

            GeoCoordinates gc = decodedData.getCenterPoint();
            long radius = decodedData.getRadius();

            double logRounded = GeometryUtils.round(gc.getLongitudeDeg());

            double latRounded = GeometryUtils.round(gc.getLatitudeDeg());

            assertEquals(logRounded, DECODED_COORDINATE_LONGITUDE);
            assertEquals(latRounded, DECODED_COORDINATE_LATITUDE);
            assertEquals(radius, RADIUS_MEDIUM);
        } catch (PhysicalFormatException e) {
            fail("exception", e);
        }
    }

    /**
     * Tests decoding of the valid geo location used in the OpenLr White Paper.
     */
    @Test(dependsOnMethods = {"testCircleExampleEncodingLarge"})
    public final void testCircleExampleDecodingLarge() {
        OpenLRBinaryDecoder decoder = new OpenLRBinaryDecoder();

        try {
            RawLocationReference decodedData = decoder
                    .decodeData(validEncodedLocationLarge);

            assertEquals(decodedData.getLocationType(), LocationType.CIRCLE);

            GeoCoordinates gc = decodedData.getCenterPoint();
            long radius = decodedData.getRadius();

            double logRounded = GeometryUtils.round(gc.getLongitudeDeg());

            double latRounded = GeometryUtils.round(gc.getLatitudeDeg());

            assertEquals(logRounded, DECODED_COORDINATE_LONGITUDE);
            assertEquals(latRounded, DECODED_COORDINATE_LATITUDE);
            assertEquals(radius, RADIUS_LARGE);
        } catch (PhysicalFormatException e) {
            fail("exception", e);
        }
    }

    /**
     * Tests decoding of the valid geo location used in the OpenLr White Paper.
     */
    @Test(dependsOnMethods = {"testCircleExampleEncodingExtraLarge"})
    public final void testCircleExampleDecodingExtraLarge() {
        OpenLRBinaryDecoder decoder = new OpenLRBinaryDecoder();

        try {
            RawLocationReference decodedData = decoder
                    .decodeData(validEncodedLocationExtraLarge);

            assertEquals(decodedData.getLocationType(), LocationType.CIRCLE);

            GeoCoordinates gc = decodedData.getCenterPoint();
            long radius = decodedData.getRadius();

            double logRounded = GeometryUtils.round(gc.getLongitudeDeg());

            double latRounded = GeometryUtils.round(gc.getLatitudeDeg());

            assertEquals(logRounded, DECODED_COORDINATE_LONGITUDE);
            assertEquals(latRounded, DECODED_COORDINATE_LATITUDE);
            assertEquals(radius, RADIUS_EXTRA_LARGE);
        } catch (PhysicalFormatException e) {
            fail("exception", e);
        }
    }

    /**
     * Test some cases of invalid input for the decoder.
     */
    @Test
    public final void testInvalidLocationDecoding() {

        byte[] invalidVersion = new byte[EXPECTED_ENCODING_RESULT_SMALL.length];
        System.arraycopy(EXPECTED_ENCODING_RESULT_SMALL, 0, invalidVersion, 0,
                EXPECTED_ENCODING_RESULT_SMALL.length);
        // invalidate the first byte of the decoder input by setting "version 1"
        invalidVersion[0] = Byte.parseByte("100001", 2);
        try {
            LocationReference lr1 = new LocationReferenceBinaryImpl("",
                    new ByteArray(invalidVersion));
        } catch (PhysicalFormatException e1) {
            assertEquals(e1.getErrorCode(),
                    PhysicalFormatError.INVALID_BINARY_DATA);
        }

        // too short data
        byte[] invalidData = new byte[EXPECTED_ENCODING_RESULT_SMALL.length - 2];
        System.arraycopy(EXPECTED_ENCODING_RESULT_SMALL, 0, invalidData, 0,
                EXPECTED_ENCODING_RESULT_SMALL.length - 2);
        try {
            LocationReference lr2 = new LocationReferenceBinaryImpl("",
                    new ByteArray(invalidData));
        } catch (PhysicalFormatException e1) {
            assertEquals(e1.getErrorCode(),
                    PhysicalFormatError.INVALID_BINARY_DATA);
        }

    }

}
