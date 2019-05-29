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
import openlr.LocationReference;
import openlr.LocationType;
import openlr.PhysicalFormatException;
import openlr.map.GeoCoordinates;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.InvalidMapDataException;
import openlr.rawLocRef.RawGeoCoordLocRef;
import openlr.rawLocRef.RawLocationReference;
import openlr.xml.OpenLRXMLException.XMLErrorType;
import openlr.xml.decoder.GeoCoordDecoder;
import openlr.xml.generated.Coordinates;
import openlr.xml.generated.OpenLR;
import openlr.xml.generated.PointLocationReference;
import openlr.xml.generated.XMLLocationReference;
import openlr.xml.impl.LocationReferenceXmlImpl;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Tests XML decoding and encoding.
 */
public class GeoCoordTest {

	/**
	 * The latitude value of the geo coordinate from the white paper example
	 */
	private static final double LAT_WP_EXAMPLE = 49.60728;

	/**
	 * The longitude value of the geo coordinate from the white paper example
	 */
	private static final double LONG_WP_EXAMPLE = 6.12699;

	/** The used XML encoder object. */
	private static final OpenLRXMLEncoder XML_ENCODER = new OpenLRXMLEncoder();
	/** The used XML encoder object. */
	private static final OpenLRXMLDecoder XML_DECODER = new OpenLRXMLDecoder();

	/** The geo coordinates of the example from the OpenLR white paper (v.1.3) */
	private GeoCoordinates coordWhitePaperExample;

	/**
	 * Setup.
	 */
	@BeforeTest
	public final void setup() {
		try {
			coordWhitePaperExample = new GeoCoordinatesImpl(LONG_WP_EXAMPLE,
					LAT_WP_EXAMPLE);
		} catch (InvalidMapDataException e) {
			Assert.fail("Unexpected exception", e);
		}
	}

	/**
	 * Tests encoding of the white paper example.
	 */
	@Test
	public final void testWhitePaperExampleEncoding() {

		RawLocationReference rawLocRef = new RawGeoCoordLocRef("",
				coordWhitePaperExample);
		LocationReference locRef = null;
		try {
			locRef = XML_ENCODER.encodeData(rawLocRef);

			checkEncodedLocation(locRef, coordWhitePaperExample);

		} catch (Exception e) {
			fail("Unexpected exception!", e);
		}

		if (!XML_ENCODER.getDataFormatIdentifier().equals(
				locRef.getDataIdentifier())) {
			fail("Invalid data identifier");
		}

		assertEquals(locRef.getDataClass(), XML_ENCODER.getDataClass(),
				"invalid data class");

		if (locRef.getLocationReferenceData() == null) {
			fail("loc ref data is null but valid");
		}
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
			final GeoCoordinates expectedGeoCoordinates) {

		assertSame(encodeGeoCoordLoc.getLocationType(),
				LocationType.GEO_COORDINATES);
		assertTrue(encodeGeoCoordLoc.isValid());
		assertNull(encodeGeoCoordLoc.getReturnCode());

		XMLLocationReference xmLoc = ((OpenLR) encodeGeoCoordLoc
				.getLocationReferenceData()).getXMLLocationReference();

		assertNull(xmLoc.getLineLocationReference());
		PointLocationReference pLoc = xmLoc.getPointLocationReference();
		assertNull(pLoc.getPointAlongLine());
		assertNull(pLoc.getPoiWithAccessPoint());

		Coordinates coords = xmLoc.getPointLocationReference()
				.getGeoCoordinate().getCoordinates();

		assertEquals(coords.getLatitude(),
				expectedGeoCoordinates.getLatitudeDeg());
		assertEquals(coords.getLongitude(),
				expectedGeoCoordinates.getLongitudeDeg());
	}

	/**
	 * Tests decoding of the white paper example.
	 */
	@Test
	public final void testWhitePaperExampleDecoding() {

		OpenLR wpGeoLocation = Utils.readLocationFromFile(
				"whitePaperGeoCoordLocation.xml", true);

		try {
			LocationReference lr = new LocationReferenceXmlImpl("",
					wpGeoLocation, 1);
			RawLocationReference rawLocRef = XML_DECODER.decodeData(lr);

			assertSame(rawLocRef.getLocationType(),
					LocationType.GEO_COORDINATES);
			assertTrue(rawLocRef.isValid());
			assertNull(rawLocRef.getReturnCode());

			assertNull(rawLocRef.getOrientation());
			assertNull(rawLocRef.getSideOfRoad());
			assertNull(rawLocRef.getOffsets());
			assertNull(rawLocRef.getLocationReferencePoints());

			GeoCoordinates coords = rawLocRef.getGeoCoordinates();
			assertEquals(coords.getLongitudeDeg(), LONG_WP_EXAMPLE);
			assertEquals(coords.getLatitudeDeg(), LAT_WP_EXAMPLE);

		} catch (PhysicalFormatException e) {
			fail("Unexpected exception!", e);
		}
	}

	/**
	 * Tests the case of providing an invalid location class to the
	 * {@link GeoCoordDecoder}.
	 */
	@Test
	public final void testWrongDataClassDecoding() {

		OpenLR wpPaperLineLocation = Utils.readLocationFromFile(
				"whitePaperLineLocation.xml", true);

		try {
			new GeoCoordDecoder().decodeData("", wpPaperLineLocation);
			fail("Exception expected!");

		} catch (PhysicalFormatException e) {
			assertSame(e.getErrorCode(), XMLErrorType.DATA_ERROR);
		}
	}
}
