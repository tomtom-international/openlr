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
package openlr.xml.rawlocref;

import static openlr.testutils.CommonObjectTestUtils.testCompare;
import static openlr.testutils.CommonObjectTestUtils.testToString;
import static org.testng.Assert.fail;
import openlr.LocationReference;
import openlr.PhysicalFormatException;
import openlr.rawLocRef.RawLocationReference;
import openlr.xml.OpenLRXMLDecoder;
import openlr.xml.Utils;
import openlr.xml.generated.OpenLR;
import openlr.xml.impl.LocationReferenceXmlImpl;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/** 
 * Tests classes of package openlr.xml.rawlocaref.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public class RawXmlLocationReferenceTest {
	
	/** The file name of the POI with access location reference. */
	private static final String POI_ACCESS_LOCATION_FILE = "whitePaperPoiAccessLocation.xml";

	/** The file name of the point along line location reference. */
	private static final String POINT_ALONG_LOCATION_FILE = "whitePaperPointAlongLocation.xml";

	/** The file name of the line location reference. */
	private static final String LINE_LOCATION_FILE = "anotherLineLocation.xml";

	/** The file name of the GEO-coordinate location reference. */
	private static final String GEO_COORD_LOCATION_FILE = "whitePaperGeoCoordLocation.xml";
	
	/** The file name of the invalid location reference. */
	private static final String INVALID_LOCATION_FILE = "missingLastLrpLineLocation.xml";

	/** The used XML encoder object. */
	private static final OpenLRXMLDecoder XML_DECODER = new OpenLRXMLDecoder();

	/** A valid GEO-coordinate location reference. */
	private RawLocationReference geoLocation;
	/** A valid line location reference. */
	private RawLocationReference lineLocation;
	/** A valid point along line location reference. */
	private RawLocationReference pointAlongLocation;
	/** A valid POI with access point location reference. */
	private RawLocationReference poiAccessLocation;
	/** An invalid location reference. */
	private RawLocationReference invalidLocation;
	
	/**
	 * Setup test environment.
	 */
	@BeforeTest
	public final void setUp() {

		geoLocation = createLocationFromFile(GEO_COORD_LOCATION_FILE, true);
		lineLocation = createLocationFromFile(LINE_LOCATION_FILE, true);
		pointAlongLocation = createLocationFromFile(POINT_ALONG_LOCATION_FILE,
				true);
		poiAccessLocation = createLocationFromFile(POI_ACCESS_LOCATION_FILE,
				true);
		invalidLocation = createLocationFromFile(INVALID_LOCATION_FILE, false);
	}

    /**
     * Creates a {@link RawLocationReference} from the location specified in the
     * given file.
     * 
     * @param fileName
     *            The name of the file containing the location.
     * @param validate
     *            Flag indication if this read location reference XML shall be
     *            validated.
     * @param lt
     *            The location type
     * @return The result of decoding the location.
     */
	private RawLocationReference createLocationFromFile(final String fileName,
			final boolean validate) {

		OpenLR loc = Utils.readLocationFromFile(fileName, validate);

		try {		
			LocationReference lr = new LocationReferenceXmlImpl("", loc, 1);
			return XML_DECODER.decodeData(lr);		
			
		} catch (PhysicalFormatException e) {
			fail("Unexpected exception!", e);
		}
		return null;
	}

	/**
	 * Tests comparison of {@link RawLocationReference} objects.
	 */
	@Test
	public final void testComparison() {

		RawLocationReference equalGeoLoc = createLocationFromFile(
				GEO_COORD_LOCATION_FILE, true);
		testCompare(geoLocation, equalGeoLoc, poiAccessLocation);

		RawLocationReference equalLineLoc = createLocationFromFile(
				LINE_LOCATION_FILE, true);
		testCompare(lineLocation, equalLineLoc, pointAlongLocation);

		RawLocationReference equalPoiLocation = createLocationFromFile(
				POI_ACCESS_LOCATION_FILE, true);
		testCompare(poiAccessLocation, equalPoiLocation, geoLocation);

		RawLocationReference equalPointAlongLocation = createLocationFromFile(
				POINT_ALONG_LOCATION_FILE, true);
		testCompare(pointAlongLocation, equalPointAlongLocation, lineLocation);

		RawLocationReference equalInvalidLocation = createLocationFromFile(
				INVALID_LOCATION_FILE, false);

		testCompare(invalidLocation, equalInvalidLocation, lineLocation);
	}

	/**
	 * Tests the toString() methods.
	 */
	@Test
	public final void testToStrings() {
		
		testToString(geoLocation);
		testToString(lineLocation);
		testToString(pointAlongLocation);
		testToString(poiAccessLocation);
		testToString(invalidLocation);
	}
}
