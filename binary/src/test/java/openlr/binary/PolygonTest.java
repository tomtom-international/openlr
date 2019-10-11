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
import openlr.LocationType;
import openlr.PhysicalFormatException;
import openlr.binary.impl.LocationReferenceBinaryImpl;
import openlr.map.GeoCoordinates;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.InvalidMapDataException;
import openlr.map.utils.GeometryUtils;
import openlr.rawLocRef.RawLocationReference;
import openlr.rawLocRef.RawPolygonLocRef;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class PolygonTest.
 */
public class PolygonTest {

    /** The Constant EXPECTED_TRIANGLE_RESULT. */
    private static final byte[] EXPECTED_TRIANGLE_RESULT = {0x13, 0x03, -0x66,
            -0x4d, 0x25, 0x05, 0x21, -0x04, -0x42, 0x05, 0x70, 0x0a, -0x35,
            -0x01, -0x63};

    /** The Constant EXPECTED_TRIANGLE_REVERSE_RESULT. */
    private static final byte[] EXPECTED_TRIANGLE_REVERSE_RESULT = {0x13, 0x03,
            -0x66, -0x4d, 0x25, 0x05, 0x21, 0x07, -0x77, 0x05, 0x0d, -0x0b,
            0x35, 0x00, 0x63};

    /** The Constant EXPECTED_PENTAGON_RESULT. */
    private static final byte[] EXPECTED_PENTAGON_RESULT = {0x13, 0x03, -0x62,
            0x17, 0x25, 0x12, 0x7a, -0x05, 0x7a, -0x03, -0x40, 0x01, 0x5c,
            -0x03, -0x6b, 0x05, -0x2d, -0x01, -0x4d, 0x02, 0x21, 0x02, -0x48};

    /**
     * Test triangle encode.
     */
    @Test
    public final void testTriangleEncode() {
        List<GeoCoordinates> coords = new ArrayList<GeoCoordinates>();
        try {
            coords.add(new GeoCoordinatesImpl(5.06853, 52.05942));
            coords.add(new GeoCoordinatesImpl(5.06019, 52.07334));
            coords.add(new GeoCoordinatesImpl(5.08782, 52.07235));
        } catch (InvalidMapDataException e) {
            Assert.fail("Unexpected exception", e);
        }
        RawLocationReference rawLocRef = new RawPolygonLocRef("triangle",
                coords);
        OpenLRBinaryEncoder encoder = new OpenLRBinaryEncoder();
        LocationReference data = encoder.encodeData(rawLocRef);

        Assert.assertNotNull(data);
        Assert.assertTrue(data.isValid());
        Assert.assertEquals(data.getID(), "triangle");
        Assert.assertNotNull(data.getLocationReferenceData());
        Assert.assertEquals(data.getLocationType(), LocationType.POLYGON);

        Utils.checkBinData((ByteArray) data.getLocationReferenceData(),
                EXPECTED_TRIANGLE_RESULT, 3);
    }

    /**
     * Test triangle encode reverse.
     */
    @Test
    public final void testTriangleEncodeReverse() {
        List<GeoCoordinates> coords = new ArrayList<GeoCoordinates>();
        try {
            coords.add(new GeoCoordinatesImpl(5.06853, 52.05942));
            coords.add(new GeoCoordinatesImpl(5.08782, 52.07235));
            coords.add(new GeoCoordinatesImpl(5.06019, 52.07334));
        } catch (InvalidMapDataException e) {
            Assert.fail("Unexpected exception", e);
        }
        RawLocationReference rawLocRef = new RawPolygonLocRef("triangle",
                coords);
        OpenLRBinaryEncoder encoder = new OpenLRBinaryEncoder();
        LocationReference data = encoder.encodeData(rawLocRef);

        Assert.assertNotNull(data);
        Assert.assertTrue(data.isValid());
        Assert.assertEquals(data.getID(), "triangle");
        Assert.assertNotNull(data.getLocationReferenceData());
        Assert.assertEquals(data.getLocationType(), LocationType.POLYGON);

        Utils.checkBinData((ByteArray) data.getLocationReferenceData(),
                EXPECTED_TRIANGLE_REVERSE_RESULT, 3);
    }

    /**
     * Test pentagon encode.
     */
    @Test
    public final void testPentagonEncode() {
        List<GeoCoordinates> coords = new ArrayList<GeoCoordinates>();
        try {
            coords.add(new GeoCoordinatesImpl(5.08715, 52.13274));
            coords.add(new GeoCoordinatesImpl(5.07557, 52.12698));
            coords.add(new GeoCoordinatesImpl(5.07905, 52.12079));
            coords.add(new GeoCoordinatesImpl(5.09396, 52.12002));
            coords.add(new GeoCoordinatesImpl(5.09941, 52.12698));
        } catch (InvalidMapDataException e) {
            Assert.fail("Unexpected exception", e);
        }
        RawLocationReference rawLocRef = new RawPolygonLocRef("pentagon",
                coords);
        OpenLRBinaryEncoder encoder = new OpenLRBinaryEncoder();
        LocationReference data = encoder.encodeData(rawLocRef);

        Assert.assertNotNull(data);
        Assert.assertTrue(data.isValid());
        Assert.assertEquals(data.getID(), "pentagon");
        Assert.assertNotNull(data.getLocationReferenceData());
        Assert.assertEquals(data.getLocationType(), LocationType.POLYGON);

        Utils.checkBinData((ByteArray) data.getLocationReferenceData(),
                EXPECTED_PENTAGON_RESULT, 3);
    }

    /**
     * Test triangle decode.
     */
    @Test
    public final void testTriangleDecode() {
        try {
            LocationReference lr = new LocationReferenceBinaryImpl("triangle",
                    new ByteArray(EXPECTED_TRIANGLE_RESULT));
            OpenLRBinaryDecoder decoder = new OpenLRBinaryDecoder();
            RawLocationReference rawLocRef = decoder.decodeData(lr);
            Assert.assertTrue(rawLocRef.isValid());
            Assert.assertEquals(rawLocRef.getID(), "triangle");
            Assert.assertEquals(rawLocRef.getLocationType(),
                    LocationType.POLYGON);
            List<? extends GeoCoordinates> points = rawLocRef.getCornerPoints();
            Assert.assertNotNull(points);
            Assert.assertEquals(points.size(), 3);
            GeoCoordinates gc1 = points.get(0);
            Assert.assertEquals(GeometryUtils.round(gc1.getLongitudeDeg()), 5.06853);
            Assert.assertEquals(GeometryUtils.round(gc1.getLatitudeDeg()), 52.05941);
            GeoCoordinates gc2 = points.get(1);
            Assert.assertEquals(GeometryUtils.round(gc2.getLongitudeDeg()), 5.06019);
            Assert.assertEquals(GeometryUtils.round(gc2.getLatitudeDeg()), 52.07333);
            GeoCoordinates gc3 = points.get(2);
            Assert.assertEquals(GeometryUtils.round(gc3.getLongitudeDeg()), 5.08781);
            Assert.assertEquals(GeometryUtils.round(gc3.getLatitudeDeg()), 52.07235);
        } catch (PhysicalFormatException e) {
            Assert.fail("Unexpected exception", e);
        }
    }

    /**
     * Test triangle reverse decode.
     */
    @Test
    public final void testTriangleReverseDecode() {
        try {
            LocationReference lr = new LocationReferenceBinaryImpl("triangle",
                    new ByteArray(EXPECTED_TRIANGLE_REVERSE_RESULT));
            OpenLRBinaryDecoder decoder = new OpenLRBinaryDecoder();
            RawLocationReference rawLocRef = decoder.decodeData(lr);
            Assert.assertTrue(rawLocRef.isValid());
            Assert.assertEquals(rawLocRef.getID(), "triangle");
            Assert.assertEquals(rawLocRef.getLocationType(),
                    LocationType.POLYGON);
            List<? extends GeoCoordinates> points = rawLocRef.getCornerPoints();
            Assert.assertNotNull(points);
            Assert.assertEquals(points.size(), 3);
            GeoCoordinates gc1 = points.get(0);
            Assert.assertEquals(GeometryUtils.round(gc1.getLongitudeDeg()), 5.06853);
            Assert.assertEquals(GeometryUtils.round(gc1.getLatitudeDeg()), 52.05941);

            GeoCoordinates gc2 = points.get(1);
            Assert.assertEquals(GeometryUtils.round(gc2.getLongitudeDeg()), 5.08782);
            Assert.assertEquals(GeometryUtils.round(gc2.getLatitudeDeg()), 52.07234);

            GeoCoordinates gc3 = points.get(2);
            Assert.assertEquals(GeometryUtils.round(gc3.getLongitudeDeg()), 5.06019);
            Assert.assertEquals(GeometryUtils.round(gc3.getLatitudeDeg()), 52.07334);
        } catch (PhysicalFormatException e) {
            Assert.fail("Unexpected exception", e);
        }
    }

    /**
     * Test pentagon decode.
     */
    @Test
    public final void testPentagonDecode() {
        try {
            LocationReference lr = new LocationReferenceBinaryImpl("pentagon",
                    new ByteArray(EXPECTED_PENTAGON_RESULT));
            OpenLRBinaryDecoder decoder = new OpenLRBinaryDecoder();
            RawLocationReference rawLocRef = decoder.decodeData(lr);
            Assert.assertTrue(rawLocRef.isValid());
            Assert.assertEquals(rawLocRef.getID(), "pentagon");
            List<? extends GeoCoordinates> points = rawLocRef.getCornerPoints();
            Assert.assertNotNull(points);
            Assert.assertEquals(points.size(), 5);

            GeoCoordinates gc1 = points.get(0);
            Assert.assertEquals(GeometryUtils.round(gc1.getLongitudeDeg()), 5.08715);
            Assert.assertEquals(GeometryUtils.round(gc1.getLatitudeDeg()), 52.13273);

            GeoCoordinates gc2 = points.get(1);
            Assert.assertEquals(GeometryUtils.round(gc2.getLongitudeDeg()), 5.07557);
            Assert.assertEquals(GeometryUtils.round(gc2.getLatitudeDeg()), 52.12697);

            GeoCoordinates gc3 = points.get(2);
            Assert.assertEquals(GeometryUtils.round(gc3.getLongitudeDeg()), 5.07905);
            Assert.assertEquals(GeometryUtils.round(gc3.getLatitudeDeg()), 52.12079);

            GeoCoordinates gc4 = points.get(3);
            Assert.assertEquals(GeometryUtils.round(gc4.getLongitudeDeg()), 5.09395);
            Assert.assertEquals(GeometryUtils.round(gc4.getLatitudeDeg()), 52.12003);

            GeoCoordinates gc5 = points.get(4);
            Assert.assertEquals(GeometryUtils.round(gc5.getLongitudeDeg()), 5.0994);
            Assert.assertEquals(GeometryUtils.round(gc5.getLatitudeDeg()), 52.12699);
        } catch (PhysicalFormatException e) {
            Assert.fail("Unexpected exception", e);
        }
    }

}
