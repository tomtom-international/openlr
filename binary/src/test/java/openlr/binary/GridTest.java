/**
 * Licensed to the TomTom International B.V. under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  TomTom International B.V.
 * licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
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
import openlr.binary.impl.LocationReferenceBinaryImpl;
import openlr.map.GeoCoordinates;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.utils.GeometryUtils;
import openlr.rawLocRef.RawGridLocRef;
import openlr.rawLocRef.RawLocationReference;

import org.testng.Assert;
import org.testng.annotations.Test;

public class GridTest {

	/** The Constant EXPECTED_RECTANGLE_RELATIVE_RESULT. */
	private static final byte[] EXPECTED_GRID_RELATIVE_RESULT = {0x43, 0x03,
			-0x62, -0x56, 0x25, 0x09, -0x0c, 0x0c, 0x27, 0x07, 0x36, 0x00,
			0x04, 0x00, 0x06};

	private static final int NR_COLUMNS_1 = 4;

	private static final int NR_ROWS_1 = 6;

	private static final int NR_COLUMNS_2 = 10;

	private static final int NR_ROWS_2 = 26;

	/** The Constant EXPECTED_RECTANGLE_ABSOLUTE_RESULT. */
	private static final byte[] EXPECTED_GRID_ABSOLUTE_RESULT = {0x43, 0x03,
			-0x7a, -0x2a, 0x24, -0x04, -0x72, 0x03, -0x02, 0x2d, 0x25, 0x2f,
			0x6a, 0x00, 0x0a, 0x00, 0x1a};

	/**
	 * Test rectangle1 encode.
	 */
	@Test
	public final void testGrid1Encode() {
		try {
			GeoCoordinates ll = new GeoCoordinatesImpl(5.09030, 52.08591);
			GeoCoordinates ur = new GeoCoordinatesImpl(5.12141, 52.10437);
			RawLocationReference rawLocRef = new RawGridLocRef("grid", ll, ur,
					NR_COLUMNS_1, NR_ROWS_1);
			OpenLRBinaryEncoder encoder = new OpenLRBinaryEncoder();
			LocationReference lr = encoder.encodeData(rawLocRef);
			Assert.assertTrue(lr.isValid());
			Assert.assertNotNull(lr.getLocationReferenceData());
			Assert.assertEquals(lr.getLocationType(), LocationType.GRID);
			Utils.checkBinData((ByteArray) lr.getLocationReferenceData(),
					EXPECTED_GRID_RELATIVE_RESULT, 3);
		} catch (Exception e) {
			Assert.fail("Unexpected exception", e);
		}
	}

	/**
	 * Test rectangle2 encode.
	 */
	@Test
	public final void testGrid2Encode() {
		try {
			GeoCoordinates ll = new GeoCoordinatesImpl(4.95941, 52.01232);
			GeoCoordinates ur = new GeoCoordinatesImpl(5.61497, 52.29168);
			RawLocationReference rawLocRef = new RawGridLocRef("grid", ll, ur,
					NR_COLUMNS_2, NR_ROWS_2);
			OpenLRBinaryEncoder encoder = new OpenLRBinaryEncoder();
			LocationReference lr = encoder.encodeData(rawLocRef);
			Assert.assertTrue(lr.isValid());
			Assert.assertNotNull(lr.getLocationReferenceData());
			Assert.assertEquals(lr.getLocationType(), LocationType.GRID);
			Utils.checkBinData((ByteArray) lr.getLocationReferenceData(),
					EXPECTED_GRID_ABSOLUTE_RESULT, 3);
		} catch (Exception e) {
			Assert.fail("Unexpected exception", e);
		}
	}

	/**
	 * Test rectangle1 decode.
	 */
	@Test
	public final void testGrid1Decode() {

		try {
			LocationReference lr = new LocationReferenceBinaryImpl("grid",
					new ByteArray(EXPECTED_GRID_RELATIVE_RESULT));
			OpenLRBinaryDecoder decoder = new OpenLRBinaryDecoder();
			RawLocationReference rawLocRef = decoder.decodeData(lr);
			Assert.assertTrue(rawLocRef.isValid());
			Assert.assertEquals(rawLocRef.getID(), "grid");
			Assert.assertEquals(rawLocRef.getLocationType(), LocationType.GRID);
			Assert.assertEquals(rawLocRef.getNumberOfColumns(), NR_COLUMNS_1);
			Assert.assertEquals(rawLocRef.getNumberOfRows(), NR_ROWS_1);
			Assert.assertNotNull(rawLocRef.getLowerLeftPoint());
			Assert.assertNotNull(rawLocRef.getUpperRightPoint());
			GeoCoordinates ll = rawLocRef.getLowerLeftPoint();
			GeoCoordinates ur = rawLocRef.getUpperRightPoint();

			Assert.assertEquals(GeometryUtils.round(ll.getLongitudeDeg()), 5.09031);
			Assert.assertEquals(GeometryUtils.round(ll.getLatitudeDeg()), 52.08591);

			Assert.assertEquals(GeometryUtils.round(ur.getLongitudeDeg()), 5.12142);
			Assert.assertEquals(GeometryUtils.round(ur.getLatitudeDeg()), 52.10437);

		} catch (PhysicalFormatException e) {
			Assert.fail("Unexpected exception", e);
		}
	}

	/**
	 * Test rectangle2 decode.
	 */
	@Test
	public final void testGrid2Decode() {

		try {
			LocationReference lr = new LocationReferenceBinaryImpl("grid",
					new ByteArray(EXPECTED_GRID_ABSOLUTE_RESULT));
			OpenLRBinaryDecoder decoder = new OpenLRBinaryDecoder();
			RawLocationReference rawLocRef = decoder.decodeData(lr);
			Assert.assertTrue(rawLocRef.isValid());
			Assert.assertEquals(rawLocRef.getID(), "grid");
			Assert.assertEquals(rawLocRef.getLocationType(), LocationType.GRID);
			Assert.assertNotNull(rawLocRef.getLowerLeftPoint());
			Assert.assertNotNull(rawLocRef.getUpperRightPoint());
			Assert.assertEquals(rawLocRef.getNumberOfColumns(), NR_COLUMNS_2);
			Assert.assertEquals(rawLocRef.getNumberOfRows(), NR_ROWS_2);
			GeoCoordinates ll = rawLocRef.getLowerLeftPoint();
			GeoCoordinates ur = rawLocRef.getUpperRightPoint();

			Assert.assertEquals(GeometryUtils.round(ll.getLongitudeDeg()), 4.95942);
			Assert.assertEquals(GeometryUtils.round(ll.getLatitudeDeg()), 52.01231);

			Assert.assertEquals(GeometryUtils.round(ur.getLongitudeDeg()), 5.61497);
			Assert.assertEquals(GeometryUtils.round(ur.getLatitudeDeg()), 52.29169);

		} catch (PhysicalFormatException e) {
			Assert.fail("Unexpected exception", e);
		}
	}

}
