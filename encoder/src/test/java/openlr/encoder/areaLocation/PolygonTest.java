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

import java.util.ArrayList;
import java.util.List;

import openlr.LocationType;
import openlr.encoder.EncoderReturnCode;
import openlr.encoder.LocationReferenceHolder;
import openlr.encoder.OpenLREncoder;
import openlr.encoder.OpenLREncoderParameter;
import openlr.location.Location;
import openlr.location.LocationFactory;
import openlr.map.GeoCoordinates;
import openlr.map.GeoCoordinatesImpl;
import openlr.rawLocRef.RawLocationReference;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PolygonTest {

	/**
	 * Test triangle encode.
	 */
	@Test
	public final void testTriangleEncode() {
		List<GeoCoordinates> coords = new ArrayList<GeoCoordinates>();
		try {
			GeoCoordinates gc1 = new GeoCoordinatesImpl(5.06853, 52.05942);
			GeoCoordinates gc2 = new GeoCoordinatesImpl(5.06019, 52.07334);
			GeoCoordinates gc3 = new GeoCoordinatesImpl(5.08782, 52.07235);
			coords.add(gc1);
			coords.add(gc2);
			coords.add(gc3);

			Location loc = LocationFactory.createPolygonLocation("triangle",
					coords);

			OpenLREncoder encoder = new OpenLREncoder();
			OpenLREncoderParameter parameter = new OpenLREncoderParameter.Builder().buildParameter();
			LocationReferenceHolder data = encoder.encodeLocation(parameter,
					loc);

			Assert.assertNotNull(data);
			Assert.assertTrue(data.isValid());
			Assert.assertEquals(data.getID(), "triangle");
			Assert.assertNotNull(data.getRawLocationReferenceData());
			Assert.assertEquals(data.getLocationType(), LocationType.POLYGON);
			RawLocationReference rawLocRef = data.getRawLocationReferenceData();
			List<? extends GeoCoordinates> points = rawLocRef.getCornerPoints();
			Assert.assertNotNull(rawLocRef.getCornerPoints());
			Assert.assertEquals(points.size(), 3);
			Assert.assertEquals(points.get(0), gc1);
			Assert.assertEquals(points.get(1), gc2);
			Assert.assertEquals(points.get(2), gc3);
		} catch (Exception e) {
			Assert.fail("Unexpected exception", e);
		}
	}

	/**
	 * Test triangle encode reverse.
	 */
	@Test
	public final void testTriangleEncodeReverse() {
		List<GeoCoordinates> coords = new ArrayList<GeoCoordinates>();
		try {
			GeoCoordinates gc1 = new GeoCoordinatesImpl(5.06853, 52.05942);
			GeoCoordinates gc2 = new GeoCoordinatesImpl(5.08782, 52.07235);
			GeoCoordinates gc3 = new GeoCoordinatesImpl(5.06019, 52.07334);
			coords.add(gc1);
			coords.add(gc2);
			coords.add(gc3);

			Location loc = LocationFactory.createPolygonLocation("triangle",
					coords);
			OpenLREncoder encoder = new OpenLREncoder();
			OpenLREncoderParameter parameter = new OpenLREncoderParameter.Builder().buildParameter();
			LocationReferenceHolder data = encoder.encodeLocation(parameter,
					loc);

			Assert.assertNotNull(data);
			Assert.assertTrue(data.isValid());
			Assert.assertEquals(data.getID(), "triangle");
			Assert.assertNotNull(data.getRawLocationReferenceData());
			Assert.assertEquals(data.getLocationType(), LocationType.POLYGON);
			RawLocationReference rawLocRef = data.getRawLocationReferenceData();
			List<? extends GeoCoordinates> points = rawLocRef.getCornerPoints();
			Assert.assertNotNull(rawLocRef.getCornerPoints());
			Assert.assertEquals(points.size(), 3);
			Assert.assertEquals(points.get(0), gc1);
			Assert.assertEquals(points.get(1), gc2);
			Assert.assertEquals(points.get(2), gc3);
		} catch (Exception e) {
			Assert.fail("Unexpected exception", e);
		}
	}

	/**
	 * Test pentagon encode.
	 */
	@Test
	public final void testPentagonEncode() {
		List<GeoCoordinates> coords = new ArrayList<GeoCoordinates>();
		try {
			GeoCoordinates gc1 = new GeoCoordinatesImpl(5.08715, 52.13274);
			GeoCoordinates gc2 = new GeoCoordinatesImpl(5.07557, 52.12698);
			GeoCoordinates gc3 = new GeoCoordinatesImpl(5.07905, 52.12079);
			GeoCoordinates gc4 = new GeoCoordinatesImpl(5.09396, 52.12002);
			GeoCoordinates gc5 = new GeoCoordinatesImpl(5.09941, 52.12698);
			coords.add(gc1);
			coords.add(gc2);
			coords.add(gc3);
			coords.add(gc4);
			coords.add(gc5);

			Location loc = LocationFactory.createPolygonLocation("pentagon",
					coords);
			OpenLREncoder encoder = new OpenLREncoder();
			OpenLREncoderParameter parameter = new OpenLREncoderParameter.Builder().buildParameter();
			LocationReferenceHolder data = encoder.encodeLocation(parameter,
					loc);

			Assert.assertNotNull(data);
			Assert.assertTrue(data.isValid());
			Assert.assertEquals(data.getID(), "pentagon");
			Assert.assertNotNull(data.getRawLocationReferenceData());
			Assert.assertEquals(data.getLocationType(), LocationType.POLYGON);
			RawLocationReference rawLocRef = data.getRawLocationReferenceData();
			List<? extends GeoCoordinates> points = rawLocRef.getCornerPoints();
			Assert.assertNotNull(rawLocRef.getCornerPoints());
			Assert.assertEquals(points.size(), 5);
			Assert.assertEquals(points.get(0), gc1);
			Assert.assertEquals(points.get(1), gc2);
			Assert.assertEquals(points.get(2), gc3);
			Assert.assertEquals(points.get(3), gc4);
			Assert.assertEquals(points.get(4), gc5);
		} catch (Exception e) {
			Assert.fail("Unexpected exception", e);
		}
	}
	
	
	/**
	 * Test pentagon encode.
	 */
	@Test 
	public final void testIntersectingPentagon() {
		List<GeoCoordinates> coords = new ArrayList<GeoCoordinates>();
		try {
			GeoCoordinates gc1 = new GeoCoordinatesImpl(5.08715, 52.13274);
			GeoCoordinates gc2 = new GeoCoordinatesImpl(5.07557, 52.12698);
			GeoCoordinates gc3 = new GeoCoordinatesImpl(5.07905, 52.12079);
			//intersecting line
			GeoCoordinates gc4 = new GeoCoordinatesImpl(5.07645, 52.13295);
			GeoCoordinates gc5 = new GeoCoordinatesImpl(5.09941, 52.12698);
			coords.add(gc1);
			coords.add(gc2);
			coords.add(gc3);
			coords.add(gc4);
			coords.add(gc5);

			Location loc = LocationFactory.createPolygonLocation("pentagon",
					coords);
			OpenLREncoder encoder = new OpenLREncoder();
			OpenLREncoderParameter parameter = new OpenLREncoderParameter.Builder().buildParameter();
			LocationReferenceHolder data = encoder.encodeLocation(parameter,
					loc);

			Assert.assertNotNull(data);
			Assert.assertFalse(data.isValid());
			Assert.assertEquals(data.getID(), "pentagon");
			Assert.assertEquals(data.getLocationType(), LocationType.POLYGON);
			Assert.assertEquals(data.getReturnCode(), EncoderReturnCode.POLYGON_NOT_SIMPLE);
		} catch (Exception e) {
			Assert.fail("Unexpected exception", e);
		}
	}
	
	/**
	 * Test pentagon encode.
	 */
	@Test
	public final void testMissingCoordinatesPolygon() {
		List<GeoCoordinates> coords = new ArrayList<GeoCoordinates>();
		try {
			GeoCoordinates gc1 = new GeoCoordinatesImpl(5.08715, 52.13274);
			GeoCoordinates gc2 = new GeoCoordinatesImpl(5.07557, 52.12698);
			coords.add(gc1);
			coords.add(gc2);

			Location loc = LocationFactory.createPolygonLocation("pentagon",
					coords);
			OpenLREncoder encoder = new OpenLREncoder();
			OpenLREncoderParameter parameter = new OpenLREncoderParameter.Builder().buildParameter();
			LocationReferenceHolder data = encoder.encodeLocation(parameter,
					loc);

			Assert.assertNotNull(data);
			Assert.assertFalse(data.isValid());
			Assert.assertEquals(data.getID(), "pentagon");
			Assert.assertEquals(data.getLocationType(), LocationType.POLYGON);
			Assert.assertEquals(data.getReturnCode(), EncoderReturnCode.MISSING_CORNERS);
		} catch (Exception e) {
			Assert.fail("Unexpected exception", e);
		}
	}
	
	

}
