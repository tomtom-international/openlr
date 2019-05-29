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
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.io.InputStream;

import openlr.LocationReference;
import openlr.LocationType;
import openlr.Offsets;
import openlr.PhysicalFormatException;
import openlr.rawLocRef.RawLocationReference;
import openlr.xml.OpenLRXMLException.XMLErrorType;
import openlr.xml.decoder.LineDecoder;
import openlr.xml.generated.OpenLR;
import openlr.xml.impl.LocationReferenceXmlImpl;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Tests the XML decoder.
 */
public class OpenLRXMLDecoderTest {

	/**
	 * The positive offset of the line location example of the OpenLR white 
	 * paper (v. 1.3).
	 */
	private static final int POSITIVE_OFFSET_WP_LINE_LOCATION = 150;


	/** The input data of the white paper line location example. */
	private OpenLR whitePaperLineLocation;

	/** The input data of an invalid data example. */
	private OpenLR invalidXML;
	/** The input data of an invalid data example because of missing last LRP. */
	private OpenLR missingLastLrp;

	/**
	 * Setup the tests.
	 */
	@BeforeTest
	public final void setup() {
		OpenLRXmlReader reader;
		InputStream whitePaperLineLocationStream = getClass().getClassLoader()
				.getResourceAsStream("whitePaperLineLocation.xml");
		InputStream invalidURL = getClass().getClassLoader()
				.getResourceAsStream("invalid_test02.xml");
		InputStream missingLastLrpStream = getClass().getClassLoader()
				.getResourceAsStream("missingLastLrpLineLocation.xml");

		try {
			reader = new OpenLRXmlReader();
			whitePaperLineLocation = reader.readOpenLRXML(whitePaperLineLocationStream, true);
			invalidXML = reader.readOpenLRXML(invalidURL, false /* don't validate*/);
			missingLastLrp = reader.readOpenLRXML(missingLastLrpStream, false /* don't validate*/);
		} catch (Exception e) {
			fail("loading xml test file failed", e);
		} 
	}

	/**
	 * Tests the data of the white paper line location example.
	 */
	@Test
	public final void testWhitePaperLineLocation() {
		OpenLRXMLDecoder dec = new OpenLRXMLDecoder();
		try {
			LocationReference lr = new LocationReferenceXmlImpl("", whitePaperLineLocation, 1);
			RawLocationReference decodedData = dec
					.decodeData(lr);
			
			assertSame(decodedData.getLocationType(), LocationType.LINE_LOCATION);
			assertTrue(decodedData.isValid());
			assertNull(decodedData.getReturnCode());

			Lrp[] expectedLrps = new Lrp[] {Lrp.LINE_ENC_LRP1, Lrp.LINE_ENC_LRP2,
					Lrp.LINE_ENC_LRP3 };
			Utils.checkDecodedLrps(decodedData, expectedLrps, true);
			
			checkOffsets(decodedData.getOffsets(),
					POSITIVE_OFFSET_WP_LINE_LOCATION, null);
			
			assertNull(decodedData.getGeoCoordinates());
			assertNull(decodedData.getOrientation());
			assertNull(decodedData.getSideOfRoad());

		} catch (PhysicalFormatException e) {
			fail("failed on valid input", e);
		}
	}

	/**
	 * Tests decoding of invalid XML data.
	 */
	@Test
	public final void testInvalidLocation() {
		OpenLRXMLDecoder dec = new OpenLRXMLDecoder();
		try {
			LocationReference lr = new LocationReferenceXmlImpl("", invalidXML, 1);
			dec.decodeData(lr);
			fail("exception expected");
		} catch (PhysicalFormatException e) {
			assertNotNull(e);
		}
	}
	
	/**
	 * Tests decoding of invalid XML data because of a missing last LRP.
	 */
	@Test
	public final void testMissingLastLrpLocation() {
		OpenLRXMLDecoder dec = new OpenLRXMLDecoder();
		try {
			LocationReference lr = new LocationReferenceXmlImpl("", missingLastLrp, 1);
			RawLocationReference ref = dec.decodeData(lr);
			assertFalse(ref.isValid());
			assertEquals(ref.getLocationType(), LocationType.LINE_LOCATION);
			assertSame(ref.getReturnCode(), XmlReturnCode.NO_LAST_LRP_FOUND);
			
			assertNull(ref.getGeoCoordinates());
			assertNull(ref.getLocationReferencePoints());
			assertNull(ref.getOffsets());
			assertNull(ref.getOrientation());
			assertNull(ref.getSideOfRoad());
						
		} catch (PhysicalFormatException e) {
			fail("Unexpected exception!", e);
		}
	}

	/**
	 * Checks the given offsets against the specified values. Assumes that the
	 * location contains of the {@link Lrp} {@link Lrp#LINE_DEC_LRP1
	 * LINE_ENC_LRP1}, {@link Lrp#LINE_DEC_LRP2 LINE_ENC_LRP2} and
	 * {@link Lrp#LINE_DEC_LRP3 LINE_ENC_LRP3}.
	 * 
	 * @param offset
	 *            The offsets object to check.
	 * @param expectedPosOffset
	 *            The expected positive offset or <code>null</code> if none is
	 *            expected.
	 * @param expectedNegativeOffset
	 *            The expected negative offset or <code>null</code> if none is
	 *            expected.
	 */
	private void checkOffsets(final Offsets offset,
			final Integer expectedPosOffset,
			final Integer expectedNegativeOffset) {


		assertTrue(offset.hasPositiveOffset() == (expectedPosOffset != null));
		assertTrue(offset.hasNegativeOffset() == (expectedNegativeOffset != null));
		if (expectedPosOffset != null) {
			assertEquals(offset.getPositiveOffset(0), expectedPosOffset
					.intValue());
		}
		if (expectedNegativeOffset != null) {
			assertEquals(offset.getNegativeOffset(0), expectedNegativeOffset
					.intValue());
		}
	}
	
	/**
	 * Tests the case of providing an invalid location class to the 
	 * {@link LineDecoder}.
	 */
	@Test
	public final void testWrongDataClassDecoding() {
		
		OpenLR wpPaperLineLocation = Utils.readLocationFromFile(
				"whitePointAlongLocation.xml", true);

		try {
			new LineDecoder().decodeData("", wpPaperLineLocation);
			fail("Exception expected!");

		} catch (PhysicalFormatException e) {
			assertSame(e.getErrorCode(), XMLErrorType.DATA_ERROR);
		}
	}	

	/**
	 * Tests the processing of an invalid XML file.
	 */
	@Test
	public final void testInvalidXmlInput() {
		
		InputStream invalidXml = getClass().getClassLoader()
				.getResourceAsStream("invalidXml.xml");

		try {
			OpenLRXmlReader reader = new OpenLRXmlReader();
			reader.readOpenLRXML(invalidXml, false);
			fail("Exception expected!");
		} catch (Exception e) {
			assertNotNull(e);
		}
	}
}
