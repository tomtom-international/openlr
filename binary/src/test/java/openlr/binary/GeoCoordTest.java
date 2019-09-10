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
import openlr.map.GeoCoordinates;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.InvalidMapDataException;
import openlr.map.utils.GeometryUtils;
import openlr.rawLocRef.RawGeoCoordLocRef;
import openlr.rawLocRef.RawLocationReference;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Tests binary encoding and decoding of geo coordinate locations.
 */
public class GeoCoordTest {

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
    private static final byte[] EXPECTED_ENCODING_RESULT = new byte[]{35, 4,
            91, 99, 35, 70, -69};

    /** The Constant BINARY_ENCODER. */
    private static final OpenLRBinaryEncoder BINARY_ENCODER = new OpenLRBinaryEncoder();

    /** The mocked geo coordinate object. */
    private GeoCoordinates coord;

    /** The mocked geo coordinate location. */
    private RawLocationReference rawLocRef;
    /** The mocked geo coordinate location with coordinates == null. */
    private RawLocationReference rawLocRefCoordsNull;

    /** A reference to the valid encoded location of the corresponding test. */
    private LocationReference validEncodedLocation;

    /**
     * Setup.
     */
    @BeforeTest
    public final void setup() {
        try {
            coord = new GeoCoordinatesImpl(COORDINATE_LONGITUDE,
                    COORDINATE_LATITUDE);
            rawLocRef = new RawGeoCoordLocRef("", coord);
            rawLocRefCoordsNull = new RawGeoCoordLocRef("", null);
        } catch (InvalidMapDataException e) {
            Assert.fail("Unexpected exception", e);
        }
    }

    /**
     * Tests encoding of the valid geo location used in the OpenLr White Paper.
     */
    @Test
    public final void testWhitePaperExampleEncoding() {

        validEncodedLocation = BINARY_ENCODER.encodeData(rawLocRef);

        ByteArray ba = (ByteArray) validEncodedLocation
                .getLocationReferenceData();

        Assert.assertSame(validEncodedLocation.getVersion(),
                OpenLRBinaryConstants.LATEST_BINARY_VERSION);

        Utils.checkBinData(ba, EXPECTED_ENCODING_RESULT,
                validEncodedLocation.getVersion());
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

        LocationReference result = BINARY_ENCODER.encodeData(rawLocRef,
                OpenLRBinaryConstants.BINARY_VERSION_2);
        assertFalse(result.isValid());
        assertSame(result.getReturnCode(), BinaryReturnCode.INVALID_VERSION);

        result = BINARY_ENCODER.encodeData(rawLocRef, Integer.MAX_VALUE);
        assertFalse(result.isValid());
        assertSame(result.getReturnCode(), BinaryReturnCode.INVALID_VERSION);
    }

    /**
     * Tests decoding of the valid geo location used in the OpenLr White Paper.
     */
    @Test(dependsOnMethods = {"testWhitePaperExampleEncoding"})
    public final void testWhitePaperExampleDecoding() {
        OpenLRBinaryDecoder decoder = new OpenLRBinaryDecoder();

        try {
            RawLocationReference decodedData = decoder
                    .decodeData(validEncodedLocation);

            assertEquals(decodedData.getLocationType(),
                    LocationType.GEO_COORDINATES);

            GeoCoordinates gc = decodedData.getGeoCoordinates();

            double logRounded = GeometryUtils.round(gc.getLongitudeDeg());

            double latRounded = GeometryUtils.round(gc.getLatitudeDeg());

            assertEquals(logRounded, DECODED_COORDINATE_LONGITUDE);
            assertEquals(latRounded, DECODED_COORDINATE_LATITUDE);
        } catch (PhysicalFormatException e) {
            fail("exception", e);
        }
    }

}
