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
import static org.testng.Assert.fail;
import junit.framework.Assert;
import openlr.LocationReference;
import openlr.LocationType;
import openlr.PhysicalFormatException;
import openlr.map.GeoCoordinates;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.InvalidMapDataException;
import openlr.rawLocRef.RawCircleLocRef;
import openlr.rawLocRef.RawLocationReference;
import openlr.xml.generated.AreaLocationReference;
import openlr.xml.generated.Coordinates;
import openlr.xml.generated.OpenLR;
import openlr.xml.generated.XMLLocationReference;
import openlr.xml.impl.LocationReferenceXmlImpl;

import org.testng.annotations.Test;

public class CircleTest {

	/** The used XML encoder object. */
	private static final OpenLRXMLEncoder XML_ENCODER = new OpenLRXMLEncoder();
	/** The used XML encoder object. */
	private static final OpenLRXMLDecoder XML_DECODER = new OpenLRXMLDecoder();

	/** The latitude of the tested geo coordinate. */
	private static final double COORDINATE_LATITUDE = 49.60728;
	/** The longitude of the tested geo coordinate. */
	private static final double COORDINATE_LONGITUDE = 6.12699;

	/** The Constant RADIUS. */
	private static final long RADIUS_SMALL = 100;

	/** The Constant RADIUS_MEDIUM. */
	private static final long RADIUS_MEDIUM = 60000;

	/** The Constant RADIUS_LARGE. */
	private static final long RADIUS_LARGE = 16000000;

	/** The Constant RADIUS_EXTRA_LARGE. */
	private static final long RADIUS_EXTRA_LARGE = 4000000000L;

	private static final GeoCoordinates G_COORD;

	static {
		GeoCoordinates gc = null;
		try {
			gc = new GeoCoordinatesImpl(COORDINATE_LONGITUDE,
					COORDINATE_LATITUDE);
		} catch (InvalidMapDataException e) {
			Assert.fail("Unexpected exception");
		}
		G_COORD = gc;

	}

	@Test
	public final void testCircleSmall() {
		RawLocationReference rawLocRef = new RawCircleLocRef("circle", G_COORD,
				RADIUS_SMALL);
		LocationReference lr = XML_ENCODER.encodeData(rawLocRef);
		Assert.assertNotNull(lr);
		checkEncodedLocation(lr, G_COORD, RADIUS_SMALL);
	}

	@Test
	public final void testCircleMedium() {
		RawLocationReference rawLocRef = new RawCircleLocRef("circle", G_COORD,
				RADIUS_MEDIUM);
		LocationReference lr = XML_ENCODER.encodeData(rawLocRef);
		Assert.assertNotNull(lr);
		checkEncodedLocation(lr, G_COORD, RADIUS_MEDIUM);
	}

	@Test
	public final void testCircleLarge() {
		RawLocationReference rawLocRef = new RawCircleLocRef("circle", G_COORD,
				RADIUS_LARGE);
		LocationReference lr = XML_ENCODER.encodeData(rawLocRef);
		Assert.assertNotNull(lr);
		checkEncodedLocation(lr, G_COORD, RADIUS_LARGE);
	}

	@Test
	public final void testCircleExtraLarge() {
		RawLocationReference rawLocRef = new RawCircleLocRef("circle", G_COORD,
				RADIUS_EXTRA_LARGE);
		LocationReference lr = XML_ENCODER.encodeData(rawLocRef);
		Assert.assertNotNull(lr);
		checkEncodedLocation(lr, G_COORD, RADIUS_EXTRA_LARGE);
	}

	/**
	 * Checks the given encode geo coordinates against the expected geo
	 * coordinates.
	 * 
	 * @param encodeGeoCoordLoc
	 *            The encoded geo coordinate location.
	 * @param expectedGeoCoordinates
	 *            The expected coordinates.
	 */
	private void checkEncodedLocation(
			final LocationReference encodeGeoCoordLoc,
			final GeoCoordinates expectedGeoCoordinates,
			final long expectedRadius) {

		assertSame(encodeGeoCoordLoc.getLocationType(), LocationType.CIRCLE);
		assertTrue(encodeGeoCoordLoc.isValid());
		assertNull(encodeGeoCoordLoc.getReturnCode());

		XMLLocationReference xmLoc = ((OpenLR) encodeGeoCoordLoc
				.getLocationReferenceData()).getXMLLocationReference();

		assertNull(xmLoc.getLineLocationReference());
		assertNull(xmLoc.getPointLocationReference());
		AreaLocationReference aLoc = xmLoc.getAreaLocationReference();
		assertNull(aLoc.getClosedLineLocationReference());
		assertNull(aLoc.getGridLocationReference());
		assertNull(aLoc.getRectangleLocationReference());
		assertNull(aLoc.getPolygonLocationReference());
		Assert.assertNotNull(aLoc.getCircleLocationReference());

		Coordinates coords = aLoc.getCircleLocationReference()
				.getGeoCoordinate().getCoordinates();

		assertEquals(coords.getLatitude(),
				expectedGeoCoordinates.getLatitudeDeg());
		assertEquals(coords.getLongitude(),
				expectedGeoCoordinates.getLongitudeDeg());

		Assert.assertNotNull(aLoc.getCircleLocationReference().getRadius());
		long radius = aLoc.getCircleLocationReference().getRadius().longValue();
		assertEquals(radius, expectedRadius);
	}

	@Test
	public final void testCircleDecodeSmall() {
		OpenLR wpGeoLocation = Utils.readLocationFromFile(
				"CircleLocationSmall.xml", true);

		try {
			LocationReference lr = new LocationReferenceXmlImpl("",
					wpGeoLocation, 1);
			RawLocationReference rawLocRef = XML_DECODER.decodeData(lr);
			checkDecodedData(rawLocRef, G_COORD, RADIUS_SMALL);
		} catch (PhysicalFormatException e) {
			fail("Unexpected exception!", e);
		}
	}

	@Test
	public final void testCircleDecodeMedium() {
		OpenLR wpGeoLocation = Utils.readLocationFromFile(
				"CircleLocationMedium.xml", true);

		try {
			LocationReference lr = new LocationReferenceXmlImpl("",
					wpGeoLocation, 1);
			RawLocationReference rawLocRef = XML_DECODER.decodeData(lr);
			checkDecodedData(rawLocRef, G_COORD, RADIUS_MEDIUM);
		} catch (PhysicalFormatException e) {
			fail("Unexpected exception!", e);
		}
	}

	@Test
	public final void testCircleDecodeLarge() {
		OpenLR wpGeoLocation = Utils.readLocationFromFile(
				"CircleLocationLarge.xml", true);

		try {
			LocationReference lr = new LocationReferenceXmlImpl("",
					wpGeoLocation, 1);
			RawLocationReference rawLocRef = XML_DECODER.decodeData(lr);
			checkDecodedData(rawLocRef, G_COORD, RADIUS_LARGE);
		} catch (PhysicalFormatException e) {
			fail("Unexpected exception!", e);
		}
	}

	@Test
	public final void testCircleDecodeExtraLarge() {
		OpenLR wpGeoLocation = Utils.readLocationFromFile(
				"CircleLocationExtraLarge.xml", true);

		try {
			LocationReference lr = new LocationReferenceXmlImpl("",
					wpGeoLocation, 1);
			RawLocationReference rawLocRef = XML_DECODER.decodeData(lr);
			checkDecodedData(rawLocRef, G_COORD, RADIUS_EXTRA_LARGE);
		} catch (PhysicalFormatException e) {
			fail("Unexpected exception!", e);
		}
	}

	public final void checkDecodedData(final RawLocationReference rawLocRef,
			final GeoCoordinates gcoord, final long expectedRadius) {
		assertSame(rawLocRef.getLocationType(), LocationType.CIRCLE);
		assertTrue(rawLocRef.isValid());
		assertNull(rawLocRef.getReturnCode());

		assertNull(rawLocRef.getOrientation());
		assertNull(rawLocRef.getSideOfRoad());
		assertNull(rawLocRef.getOffsets());
		assertNull(rawLocRef.getLocationReferencePoints());

		GeoCoordinates coords = rawLocRef.getCenterPoint();
		assertEquals(coords.getLongitudeDeg(), gcoord.getLongitudeDeg());
		assertEquals(coords.getLatitudeDeg(), gcoord.getLatitudeDeg());
		assertEquals(rawLocRef.getRadius(), expectedRadius);
	}

}
