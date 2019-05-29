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
package openlr.xml;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import openlr.LocationReference;
import openlr.LocationType;
import openlr.PhysicalFormatException;
import openlr.map.GeoCoordinates;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.InvalidMapDataException;
import openlr.rawLocRef.RawLocationReference;
import openlr.rawLocRef.RawPolygonLocRef;
import openlr.xml.generated.AreaLocationReference;
import openlr.xml.generated.Coordinates;
import openlr.xml.generated.OpenLR;
import openlr.xml.generated.XMLLocationReference;
import openlr.xml.impl.LocationReferenceXmlImpl;

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
			coords.add(new GeoCoordinatesImpl(5.06853, 52.05942));
			coords.add(new GeoCoordinatesImpl(5.06019, 52.07334));
			coords.add(new GeoCoordinatesImpl(5.08782, 52.07235));
		} catch (InvalidMapDataException e) {
			Assert.fail("Unexpected exception", e);
		}
		RawLocationReference rawLocRef = new RawPolygonLocRef("triangle",
				coords);
		OpenLRXMLEncoder encoder = new OpenLRXMLEncoder();
		LocationReference data = encoder.encodeData(rawLocRef);
		Assert.assertNotNull(data);
		Assert.assertTrue(data.isValid());
		Assert.assertEquals(data.getID(), "triangle");
		Assert.assertNotNull(data.getLocationReferenceData());
		Assert.assertEquals(data.getLocationType(), LocationType.POLYGON);
		checkEncodedLocation(data, coords);
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
		OpenLRXMLEncoder encoder = new OpenLRXMLEncoder();
		LocationReference data = encoder.encodeData(rawLocRef);
		Assert.assertNotNull(data);
		Assert.assertTrue(data.isValid());
		Assert.assertEquals(data.getID(), "triangle");
		Assert.assertNotNull(data.getLocationReferenceData());
		Assert.assertEquals(data.getLocationType(), LocationType.POLYGON);
		checkEncodedLocation(data, coords);		
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
		OpenLRXMLEncoder encoder = new OpenLRXMLEncoder();
		LocationReference data = encoder.encodeData(rawLocRef);
		Assert.assertNotNull(data);
		Assert.assertTrue(data.isValid());
		Assert.assertEquals(data.getID(), "pentagon");
		Assert.assertNotNull(data.getLocationReferenceData());
		Assert.assertEquals(data.getLocationType(), LocationType.POLYGON);
		checkEncodedLocation(data, coords);
	}

	/**
	 * Test triangle decode.
	 */
	@Test
	public final void testTriangleDecode() {
		
		OpenLR polygonLocation = Utils.readLocationFromFile("Triangle.xml",
				true);
		try {
			LocationReference lr = new LocationReferenceXmlImpl("triangle",
					polygonLocation, 1);
			OpenLRXMLDecoder decoder = new OpenLRXMLDecoder();
			RawLocationReference rawLocRef = decoder.decodeData(lr);
			Assert.assertTrue(rawLocRef.isValid());
			Assert.assertEquals(rawLocRef.getID(), "triangle");
			Assert.assertEquals(rawLocRef.getLocationType(),
					LocationType.POLYGON);
			List<GeoCoordinates> coords = new ArrayList<GeoCoordinates>();
			try {
				coords.add(new GeoCoordinatesImpl(5.06853, 52.05942));
				coords.add(new GeoCoordinatesImpl(5.06019, 52.07334));
				coords.add(new GeoCoordinatesImpl(5.08782, 52.07235));
			} catch (InvalidMapDataException e) {
				Assert.fail("Unexpected exception", e);
			}
			checkDecodedData(rawLocRef, coords);
			
		} catch (PhysicalFormatException e) {
			Assert.fail("Unexpected exception", e);
		}
	}

	/**
	 * Test triangle reverse decode.
	 */
	@Test
	public final void testTriangleReverseDecode() {
		OpenLR polygonLocation = Utils.readLocationFromFile("TriangleReverse.xml",
				true);
		try {
			LocationReference lr = new LocationReferenceXmlImpl("triangle",
					polygonLocation, 1);
			OpenLRXMLDecoder decoder = new OpenLRXMLDecoder();
			RawLocationReference rawLocRef = decoder.decodeData(lr);
			Assert.assertTrue(rawLocRef.isValid());
			Assert.assertEquals(rawLocRef.getID(), "triangle");
			Assert.assertEquals(rawLocRef.getLocationType(),
					LocationType.POLYGON);
			List<GeoCoordinates> coords = new ArrayList<GeoCoordinates>();
			try {
				coords.add(new GeoCoordinatesImpl(5.06853, 52.05942));
				coords.add(new GeoCoordinatesImpl(5.08782, 52.07235));
				coords.add(new GeoCoordinatesImpl(5.06019, 52.07334));
			} catch (InvalidMapDataException e) {
				Assert.fail("Unexpected exception", e);
			}
			checkDecodedData(rawLocRef, coords);
			
		} catch (PhysicalFormatException e) {
			Assert.fail("Unexpected exception", e);
		}
	}

	/**
	 * Test pentagon decode.
	 */
	@Test
	public final void testPentagonDecode() {
		OpenLR polygonLocation = Utils.readLocationFromFile("Pentagon.xml",
				true);
		try {
			LocationReference lr = new LocationReferenceXmlImpl("pentagon",
					polygonLocation, 1);
			OpenLRXMLDecoder decoder = new OpenLRXMLDecoder();
			RawLocationReference rawLocRef = decoder.decodeData(lr);
			Assert.assertTrue(rawLocRef.isValid());
			Assert.assertEquals(rawLocRef.getID(), "pentagon");
			Assert.assertEquals(rawLocRef.getLocationType(),
					LocationType.POLYGON);
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
			checkDecodedData(rawLocRef, coords);
			
		} catch (PhysicalFormatException e) {
			Assert.fail("Unexpected exception", e);
		}
	}
	
	
	public final void checkDecodedData(final RawLocationReference rawLocRef,
			final List<GeoCoordinates> coords) {
		assertSame(rawLocRef.getLocationType(), LocationType.POLYGON);
		assertTrue(rawLocRef.isValid());
		assertNull(rawLocRef.getReturnCode());

		assertNull(rawLocRef.getOrientation());
		assertNull(rawLocRef.getSideOfRoad());
		assertNull(rawLocRef.getOffsets());
		assertNull(rawLocRef.getLocationReferencePoints());

		List<? extends GeoCoordinates> cornerPoints = rawLocRef.getCornerPoints();
		assertEquals(cornerPoints.size(), coords.size());
		for (int i = 0; i < coords.size(); i++) {
			GeoCoordinates gc1 = cornerPoints.get(i);
			GeoCoordinates gc2 = coords.get(i);
			assertEquals(gc1.getLongitudeDeg(), gc2.getLongitudeDeg());
			assertEquals(gc1.getLatitudeDeg(), gc2.getLatitudeDeg());
		}

	}

	/**
	 * Checks the given encode geo coordinates against the expected geo
	 * coordinates.
	 * 
	 * @param encodedRectangleLoc
	 *            The encoded geo coordinate location.
	 * @param expectedLL
	 *            the expected ll
	 * @param expectedUR
	 *            the expected ur
	 */
	private void checkEncodedLocation(
			final LocationReference encodedRectangleLoc,
			final List<GeoCoordinates> coords) {
		assertSame(encodedRectangleLoc.getLocationType(),
				LocationType.POLYGON);
		assertTrue(encodedRectangleLoc.isValid());
		assertNull(encodedRectangleLoc.getReturnCode());

		XMLLocationReference xmLoc = ((OpenLR) encodedRectangleLoc
				.getLocationReferenceData()).getXMLLocationReference();

		assertNull(xmLoc.getLineLocationReference());
		assertNull(xmLoc.getPointLocationReference());
		AreaLocationReference aLoc = xmLoc.getAreaLocationReference();
		assertNull(aLoc.getClosedLineLocationReference());
		assertNull(aLoc.getGridLocationReference());
		Assert.assertNull(aLoc.getRectangleLocationReference());
		Assert.assertNotNull(aLoc.getPolygonLocationReference());
		assertNull(aLoc.getCircleLocationReference());
		
		Assert.assertNotNull(aLoc.getPolygonLocationReference().getPolygonCorners());
		List<Coordinates> corners = aLoc.getPolygonLocationReference().getPolygonCorners().getCoordinates();
		assertEquals(corners.size(), coords.size());
		for (int i = 0; i < coords.size(); i++) {
			Coordinates gc1 = corners.get(i);
			GeoCoordinates gc2 = coords.get(i);
			assertEquals(gc1.getLongitude(), gc2.getLongitudeDeg());
			assertEquals(gc1.getLatitude(), gc2.getLatitudeDeg());
		}
	}

}
