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
package openlr.encoder.areaLocation;

import openlr.LocationType;
import openlr.encoder.LocationReferenceHolder;
import openlr.encoder.OpenLREncoder;
import openlr.encoder.OpenLREncoderParameter;
import openlr.location.Location;
import openlr.location.LocationFactory;
import openlr.map.GeoCoordinates;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.InvalidMapDataException;
import openlr.rawLocRef.RawLocationReference;

import org.testng.Assert;
import org.testng.annotations.Test;

public class RectangleTest {

	private static final GeoCoordinates LL_SMALL;
	private static final GeoCoordinates UR_SMALL;

	private static final GeoCoordinates LL_LARGE;
	private static final GeoCoordinates UR_LARGE;

	static {
		GeoCoordinates lls = null;
		GeoCoordinates urs = null;
		GeoCoordinates lll = null;
		GeoCoordinates url = null;
		try {
			lls = new GeoCoordinatesImpl(5.09030, 52.08591);
			urs = new GeoCoordinatesImpl(5.12141, 52.10437);
			lll = new GeoCoordinatesImpl(4.95941, 52.01232);
			url = new GeoCoordinatesImpl(5.61497, 52.29168);
		} catch (InvalidMapDataException e) {
			Assert.fail("Unexpected exception", e);
		}
		LL_SMALL = lls;
		UR_SMALL = urs;
		LL_LARGE = lll;
		UR_LARGE = url;
	}

	/**
	 * Test rectangle1 encode.
	 */
	@Test
	public final void testRectangle1Encode() {
		try {
		Location loc = LocationFactory.createRectangleLocation("rectangle",
				LL_SMALL.getLongitudeDeg(), LL_SMALL.getLatitudeDeg(),
				UR_SMALL.getLongitudeDeg(), UR_SMALL.getLatitudeDeg());

		OpenLREncoder encoder = new OpenLREncoder();
		OpenLREncoderParameter parameter = new OpenLREncoderParameter.Builder().buildParameter();
			LocationReferenceHolder locrefs = encoder.encodeLocation(parameter,
					loc);
			Assert.assertNotNull(locrefs);
			Assert.assertTrue(locrefs.isValid());
			Assert.assertEquals(locrefs.getID(), "rectangle");
			Assert.assertEquals(locrefs.getLocationType(),
					LocationType.RECTANGLE);
			RawLocationReference raw = locrefs.getRawLocationReferenceData();
			Assert.assertNotNull(raw);
			Assert.assertEquals(raw.getLowerLeftPoint(), LL_SMALL);
			Assert.assertEquals(raw.getUpperRightPoint(), UR_SMALL);
		} catch (Exception e) {
			Assert.fail("Unexpected exception", e);
		}
	}

	/**
	 * Test rectangle2 encode.
	 */
	@Test
	public final void testRectangle2Encode() {
		try {
		Location loc = LocationFactory.createRectangleLocation("rectangle",
				LL_LARGE.getLongitudeDeg(), LL_LARGE.getLatitudeDeg(),
				UR_LARGE.getLongitudeDeg(), UR_LARGE.getLatitudeDeg());

		OpenLREncoder encoder = new OpenLREncoder();
		OpenLREncoderParameter parameter = new OpenLREncoderParameter.Builder().buildParameter();
			LocationReferenceHolder locrefs = encoder.encodeLocation(parameter,
					loc);
			Assert.assertNotNull(locrefs);
			Assert.assertTrue(locrefs.isValid());
			Assert.assertEquals(locrefs.getID(), "rectangle");
			Assert.assertEquals(locrefs.getLocationType(),
					LocationType.RECTANGLE);
			RawLocationReference raw = locrefs.getRawLocationReferenceData();
			Assert.assertNotNull(raw);
			Assert.assertEquals(raw.getLowerLeftPoint(), LL_LARGE);
			Assert.assertEquals(raw.getUpperRightPoint(), UR_LARGE);
		} catch (Exception e) {
			Assert.fail("Unexpected exception", e);
		}
	}

}
