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
package openlr.xml.impl;

import static openlr.testutils.CommonObjectTestUtils.testCompare;
import static openlr.testutils.CommonObjectTestUtils.testToString;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.util.List;

import openlr.LocationType;
import openlr.map.FormOfWay;
import openlr.map.FunctionalRoadClass;
import openlr.rawLocRef.RawLocationReference;
import openlr.xml.BinaryLocRefHandler;
import openlr.xml.BinaryLocRefHandler.BinaryLocationReferenceData;
import openlr.xml.OpenLRXMLException;
import openlr.xml.OpenLRXMLException.XMLErrorType;
import openlr.xml.Utils;
import openlr.xml.XmlReturnCode;
import openlr.xml.generated.OpenLR;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/** 
 * Tests classes of package openlr.xml.impl.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public class XmlImplTest {
	
	/** The file name of the POI with access location reference. */
	private static final String POI_ACCESS_LOCATION_FILE = "whitePaperPoiAccessLocation.xml";

	/** The file name of the point along line location reference. */
	private static final String POINT_ALONG_LOCATION_FILE = "whitePaperPointAlongLocation.xml";

	/** The file name of the line location reference. */
	private static final String LINE_LOCATION_FILE = "anotherLineLocation.xml";

	/** The file name of the GEO-coordinate location reference. */
	private static final String GEO_COORD_LOCATION_FILE = "whitePaperGeoCoordLocation.xml";
	
	/** A valid GEO-coordinate location reference. */
	private LocationReferenceXmlImpl geoLocation;
	/** A valid line location reference. */
	private LocationReferenceXmlImpl lineLocation;
	/** A valid point along line location reference. */
	private LocationReferenceXmlImpl pointAlongLocation;
	/** A valid POI with access point location reference. */
	private LocationReferenceXmlImpl poiAccessLocation;
	/** An invalid location reference. */
	private LocationReferenceXmlImpl invalidLocation;
	
	/** A positive offset value used in tests of offset objects. */
	private static final int POS_OFFSET_A = 10;
	/** Another positive offset value used in tests of offset objects. */
	private static final int POS_OFFSET_B = 200;
	/** A negative offset value used in tests of offset objects. */
	private static final int NEG_OFFSET_A = Integer.MAX_VALUE;
	
	/** An instance of LocationReferencePointXmlImpl used for testing this type of objects. */
	private static final LocationReferencePointXmlImpl LOC_REF_POINT_XML = 
		new LocationReferencePointXmlImpl(3, 
			FunctionalRoadClass.FRC_1, FormOfWay.ROUNDABOUT, 1, 1, 222, 22,
			FunctionalRoadClass.FRC_1, false);	
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
		invalidLocation = new LocationReferenceXmlImpl(
				"1", XmlReturnCode.INVALID_NUMBER_OF_LRP, LocationType.UNKNOWN,
				1);

	}

    /**
     * Creates a {@link RawLocationReference} from the location specified in the
     * given file.
     * 
     * @param fileName
     *            The name of the file containing the location.
     * @param l
     *            The type of location.
     * @param validate
     *            Flag indication if this read location reference XML shall be
     *            validated.
     * @return The result of decoding the location.
     */
	private LocationReferenceXmlImpl createLocationFromFile(
			final String fileName, final boolean validate) {

		OpenLR loc = Utils.readLocationFromFile(fileName, validate);
		return new LocationReferenceXmlImpl("", loc, 0);
	}
	
	/**
	 * Tests class {@link LocationReferencePointXmlImpl}.
	 */
	@Test
	public final void testLocationReferencePointXmlImpl() {
		
		LocationReferencePointXmlImpl equal = new LocationReferencePointXmlImpl(1, 
				LOC_REF_POINT_XML.getFRC(), LOC_REF_POINT_XML.getFOW(),
				LOC_REF_POINT_XML.getLongitudeDeg(), LOC_REF_POINT_XML
						.getLatitudeDeg(), LOC_REF_POINT_XML.getBearing(),
				LOC_REF_POINT_XML.getDistanceToNext(), LOC_REF_POINT_XML
						.getLfrc(), LOC_REF_POINT_XML.isLastLRP());
		
		LocationReferencePointXmlImpl unEqual = new LocationReferencePointXmlImpl(2, 
				LOC_REF_POINT_XML.getFRC(), LOC_REF_POINT_XML.getFOW(),
				LOC_REF_POINT_XML.getLongitudeDeg() - 2, LOC_REF_POINT_XML
				.getLatitudeDeg(), LOC_REF_POINT_XML.getBearing(),
				LOC_REF_POINT_XML.getDistanceToNext(), LOC_REF_POINT_XML
				.getLfrc(), !LOC_REF_POINT_XML.isLastLRP());
		
		testCompare(LOC_REF_POINT_XML, equal, unEqual);	
	}
	
	/**
	 * Tests class {@link OffsetsXmlImpl}.
	 */
	@Test
	public final void testOffsetsXmlImpl() {
		
		OffsetsXmlImpl a = new OffsetsXmlImpl(POS_OFFSET_A, NEG_OFFSET_A);		
		OffsetsXmlImpl equalToA = new OffsetsXmlImpl(POS_OFFSET_A, NEG_OFFSET_A);		
		OffsetsXmlImpl unEqualToA = new OffsetsXmlImpl(POS_OFFSET_B, 0);
		
		testCompare(a, equalToA, unEqualToA);	
		
		OffsetsXmlImpl noOffsets = new OffsetsXmlImpl(0, 0);
		
		assertTrue(a.hasNegativeOffset());
		assertTrue(a.hasPositiveOffset());
		assertFalse(noOffsets.hasNegativeOffset());
		assertFalse(noOffsets.hasPositiveOffset());
		assertEquals(a.getPositiveOffset(0), POS_OFFSET_A);
		assertEquals(a.getNegativeOffset(0), NEG_OFFSET_A);
		assertEquals(noOffsets.getPositiveOffset(0), 0);
		assertEquals(noOffsets.getNegativeOffset(0), 0);
		
		try {
			new OffsetsXmlImpl(-1, 0);
			fail("Exception expected when creating offset with negative value.");
		} catch (Exception e) {
			assertNotNull(e);
		}
		try {
			new OffsetsXmlImpl(0, -1);
			fail("Exception expected when creating offset with negative value.");
		} catch (Exception e) {
			assertNotNull(e);
		}
	}
	
	/**
     * Test resolving of binary location references via BinaryLocRefHandler.
	 * This test checks an XML location reference containing an empty tag 
	 * BinaryLocationReference.
	 */
	@Test
    public final void testBinaryLocRefHandlerEmptyBinLoc() {
	    OpenLR loc = Utils.readLocationFromFile("emptyBinaryLineLocation.xml", true);
	    
        assertTrue(BinaryLocRefHandler.containsBinaryLocationReference(loc));
        
	    try {	        
	        List<BinaryLocationReferenceData> result = 
	            BinaryLocRefHandler.resolveBinaryDatafromXML(loc);
	        assertTrue(result.size() == 0);
	        

        } catch (OpenLRXMLException e) {
            assertEquals(e.getErrorCode(), XMLErrorType.DATA_ERROR);
        }
	}			
	
	/**
	 * Test resolving of binary location references via BinaryLocRefHandler.
	 * This test processes the "good case".
	 */
	@Test
	public final void testBinaryLocRefHandlerValidBinLoc() {
	    OpenLR loc = Utils.readLocationFromFile(LINE_LOCATION_FILE, true);
	    
	    assertTrue(BinaryLocRefHandler.containsBinaryLocationReference(loc));
	    
	    try {	        
	        List<BinaryLocationReferenceData> result = 
	            BinaryLocRefHandler.resolveBinaryDatafromXML(loc);
	        assertTrue(result.size() == 1);
	        
	        
	    } catch (OpenLRXMLException e) {
	        fail("Unexpected exception", e);
	    }
	}			

	/**
	 * Tests comparison of {@link RawLocationReference} objects.
	 */
	@Test
	public final void testComparison() {

		LocationReferenceXmlImpl equalGeoLoc = createLocationFromFile(

				GEO_COORD_LOCATION_FILE, true);
		testCompare(geoLocation, equalGeoLoc, poiAccessLocation);

		LocationReferenceXmlImpl equalLineLoc = createLocationFromFile(
				LINE_LOCATION_FILE, true);
		testCompare(lineLocation, equalLineLoc, pointAlongLocation);

		LocationReferenceXmlImpl equalPoiLocation = createLocationFromFile(
				POI_ACCESS_LOCATION_FILE, true);
		testCompare(poiAccessLocation, equalPoiLocation, geoLocation);

		LocationReferenceXmlImpl equalPointAlongLocation = createLocationFromFile(
				POINT_ALONG_LOCATION_FILE, true);
		testCompare(pointAlongLocation, equalPointAlongLocation, lineLocation);

		LocationReferenceXmlImpl equalInvalidLocation = new LocationReferenceXmlImpl(
				"1", XmlReturnCode.INVALID_NUMBER_OF_LRP, LocationType.UNKNOWN,
				1);
		LocationReferenceXmlImpl unEqualInvalidLoc = new LocationReferenceXmlImpl(
				"1", XmlReturnCode.NO_LAST_LRP_FOUND,
				LocationType.GEO_COORDINATES, 1);
		assertFalse(unEqualInvalidLoc.isValid());

		testCompare(invalidLocation, equalInvalidLocation, unEqualInvalidLoc);
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
