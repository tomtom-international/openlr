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
package openlr.decoder.areaLocation;

import java.util.ArrayList;
import java.util.List;

import openlr.LocationType;
import openlr.decoder.OpenLRDecoder;
import openlr.decoder.OpenLRDecoderParameter;
import openlr.location.Location;
import openlr.map.GeoCoordinates;
import openlr.map.GeoCoordinatesImpl;
import openlr.rawLocRef.RawLocationReference;
import openlr.rawLocRef.RawPolygonLocRef;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PolygonTest {
	
	/**
	 * Test triangle encode.
	 */
	@Test
	public final void testTriangleDecode() {
		List<GeoCoordinates> coords = new ArrayList<GeoCoordinates>();
		try {
			GeoCoordinates gc1 = new GeoCoordinatesImpl(5.06853, 52.05942);
			GeoCoordinates gc2 = new GeoCoordinatesImpl(5.06019, 52.07334);
			GeoCoordinates gc3 = new GeoCoordinatesImpl(5.08782, 52.07235);
			coords.add(gc1);
			coords.add(gc2);
			coords.add(gc3);

			RawLocationReference rawloc = new RawPolygonLocRef("triangle",
					coords);

			OpenLRDecoder decoder = new OpenLRDecoder();
			OpenLRDecoderParameter parameter = new OpenLRDecoderParameter.Builder().buildParameter();
			Location data = decoder.decodeRaw(parameter,
					rawloc);
			Assert.assertNotNull(data);
			Assert.assertTrue(data.isValid());
			Assert.assertEquals(data.getID(), "triangle");
			Assert.assertEquals(data.getLocationType(), LocationType.POLYGON);
			Assert.assertNotNull(data.getCornerPoints());
			List<? extends GeoCoordinates> points = data.getCornerPoints();
			Assert.assertEquals(points.size(), coords.size());
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

			RawLocationReference loc = new RawPolygonLocRef("triangle",
					coords);
			OpenLRDecoder decoder = new OpenLRDecoder();
			OpenLRDecoderParameter parameter = new OpenLRDecoderParameter.Builder().buildParameter();
			Location data = decoder.decodeRaw(parameter,
					loc);
			Assert.assertNotNull(data);
			Assert.assertTrue(data.isValid());
			Assert.assertEquals(data.getID(), "triangle");
			Assert.assertEquals(data.getLocationType(), LocationType.POLYGON);
			Assert.assertNotNull(data.getCornerPoints());
			List<? extends GeoCoordinates> points = data.getCornerPoints();
			Assert.assertEquals(points.size(), coords.size());
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

			RawLocationReference loc = new RawPolygonLocRef("pentagon",
					coords);
			OpenLRDecoder decoder = new OpenLRDecoder();
			OpenLRDecoderParameter parameter = new OpenLRDecoderParameter.Builder().buildParameter();
			Location data = decoder.decodeRaw(parameter,
					loc);

			Assert.assertNotNull(data);
			Assert.assertTrue(data.isValid());
			Assert.assertEquals(data.getID(), "pentagon");
			Assert.assertEquals(data.getLocationType(), LocationType.POLYGON);
			Assert.assertNotNull(data.getCornerPoints());
			List<? extends GeoCoordinates> points = data.getCornerPoints();
			Assert.assertEquals(points.size(), coords.size());
			Assert.assertEquals(points.get(0), gc1);
			Assert.assertEquals(points.get(1), gc2);
			Assert.assertEquals(points.get(2), gc3);
			Assert.assertEquals(points.get(3), gc4);
			Assert.assertEquals(points.get(4), gc5);
		} catch (Exception e) {
			Assert.fail("Unexpected exception", e);
		}
	}

}
