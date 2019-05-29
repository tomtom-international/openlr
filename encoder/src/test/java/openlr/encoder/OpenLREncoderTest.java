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
package openlr.encoder;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import openlr.LocationReference;
import openlr.LocationReferencePoint;
import openlr.LocationType;
import openlr.Offsets;
import openlr.OpenLRProcessingException;
import openlr.encoder.OpenLREncoderProcessingException.EncoderProcessingError;
import openlr.encoder.impl.PhysicalEncoderImpl;
import openlr.encoder.properties.OpenLREncoderProperty;
import openlr.location.LineLocation;
import openlr.location.Location;
import openlr.location.LocationFactory;
import openlr.location.PoiAccessLocation;
import openlr.map.GeoCoordinates;
import openlr.map.InvalidMapDataException;
import openlr.map.Line;
import openlr.map.mockdb.InvalidConfigurationException;
import openlr.map.mockdb.MockedMapDatabase;
import openlr.properties.OpenLRPropertiesReader;
import openlr.properties.OpenLRPropertyAccess;
import openlr.properties.OpenLRPropertyException;
import openlr.rawLocRef.RawLocationReference;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * This class contains test of the general encoding process.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public class OpenLREncoderTest {

	/**
     * An offset value above 15.000
     */
    private static final int LARGE_OFFSET = 17000;

    /**
	 * The number of expected LRP after encoding the long line example.
	 */
	private static final int EXPECTED_NR_LRPS_LONG_LINE = 5;

	/** The mocking object. */
	private Mockery mockery = new Mockery();

	/**
	 * An utility class holding prepared/mocked test data.
	 */
	private TestData td = TestData.getInstance();

	/** The lines contained in the path of the long line test. */
	private static final long[] LONG_LINE_IDS = {19, 24, 25};

    /**
     * Tests encoding of a location containing an extra long line. The location
     * is defined without offsets.
     */
    @Test
    public final void testLongLine() {
        LocationReferenceHolder locationRef = testLongLineImpl(0, 0);
        assertTrue(locationRef.isValid(),
                "Encoding delivered invalid location.");
        assertEquals(locationRef.getNrOfLRPs(), EXPECTED_NR_LRPS_LONG_LINE);
        
    }

    /**
     * Tests encoding of a location containing an extra long line. The location
     * is defined with a positive offsets above 15000.
     * 
     * @throws OpenLRPropertyException
     *             In case of an error reading the max distance set
     */
    @Test
    public final void testLongLineWithLargePostiveOffset()
            throws OpenLRPropertyException {
        LocationReferenceHolder locationRef = testLongLineImpl(LARGE_OFFSET, 0);
        assertTrue(locationRef.isValid(),
                "Encoding delivered invalid location.");

        // we expect the encoder to crop the location to a valid offset below
        // the max distance (15.000 meters) between the first two LRPs
        RawLocationReference rawData = locationRef
                .getRawLocationReferenceData();
        int dnpFirst = rawData.getLocationReferencePoints().get(0)
                .getDistanceToNext();
        int posOff = rawData.getOffsets().getPositiveOffset(dnpFirst);

        int maxDistance = OpenLRPropertyAccess.getIntegerPropertyValue(
                td.getConfiguration(), OpenLREncoderProperty.MAX_DIST_LRP);
        assertTrue(posOff < maxDistance,
                "Offset in raw location reference is not below " + maxDistance);
    }

    /**
     * Tests encoding of a location containing an extra long line. The location
     * is defined with a negative offsets above 15000.
     * 
     * @throws OpenLRPropertyException
     *             In case of an error reading the max distance set
     */  
    @Test
    public final void testLongLineWithLargeNegativeOffset() throws OpenLRPropertyException {
        LocationReferenceHolder locationRef = testLongLineImpl(0, LARGE_OFFSET);
        assertTrue(locationRef.isValid(),
                "Encoding delivered invalid location.");
        
        // we expect the encoder to crop the location to a valid offset below
        // the max distance (15.000 meters) between the last two LRPs        
        RawLocationReference rawData = locationRef
                .getRawLocationReferenceData();
        List<LocationReferencePoint> lrps = rawData.getLocationReferencePoints();
        int dnpLast = lrps.get(lrps.size() - 2)
                .getDistanceToNext();
        int negOffset = rawData.getOffsets().getPositiveOffset(dnpLast);

        int maxDistance = OpenLRPropertyAccess.getIntegerPropertyValue(
                td.getConfiguration(), OpenLREncoderProperty.MAX_DIST_LRP);
        assertTrue(negOffset < maxDistance,
                "Offset in raw location reference is not below " + maxDistance);        
    }
	    
	/**
     * Test the encoding of a location containing an extra long line. This is at
     * the time of writing the first example of using a dedicated mocked
     * database via configuration for only this single test.
     * 
     * @param positiveOffset
     *            The positive offset to set
     * @param negativeOffset
     *            The negative offset to set
     * @return the location reference holder after encoding
     */
    public final LocationReferenceHolder testLongLineImpl(
            final int positiveOffset, final int negativeOffset) {

		MockedMapDatabase mdb = null;
		try {
			mdb = new MockedMapDatabase("ExtraLongLineMap.xml", false);
		} catch (InvalidConfigurationException e) {
			fail("Unexpected exception!", e);
		}

		/** The location containing a extra long line. */
		ArrayList<Line> longLineLocation = new ArrayList<Line>();
		for (int i = 0; i < LONG_LINE_IDS.length; i++) {
			longLineLocation.add(mdb.getLine(LONG_LINE_IDS[i]));
		}
		Location loc = LocationFactory.createLineLocationWithOffsets("longLine",
				longLineLocation, positiveOffset, negativeOffset);

		LocationReferenceHolder locationRef = null;
		try {
			OpenLREncoder encoder = new OpenLREncoder();
			OpenLREncoderParameter params = new OpenLREncoderParameter.Builder().with(mdb).with(td.getConfiguration()).buildParameter();
			locationRef = encoder.encodeLocation(params, loc);
		} catch (OpenLRProcessingException e) {
			fail("Encoding location failed with exception: " + e.getErrorCode(),
					e);
		}
        return locationRef;
	}

	/**
	 * Tests encoding of a not connected location and additionally calling the
	 * encoder with a list parameter for the input locations.
	 */
	@Test
	public final void testnotConnectedLocation() {

		List<LocationReferenceHolder> locationRef = null;
		try {
			OpenLREncoder encoder = new OpenLREncoder();
			OpenLREncoderParameter params = new OpenLREncoderParameter.Builder().with(td.getMapDatabase()).with(td.getConfiguration()).buildParameter();
			locationRef = encoder.encodeLocations(params,
					Arrays.asList(td.getNotConnectedLocation()));
			assertFalse(locationRef.get(0).isValid());

		} catch (OpenLRProcessingException e) {
			fail("Encoding location failed with exception: " + e.getErrorCode(),
					e);
		}
	}

	/**
	 * Tests a point with access point location with no offset.
	 */
	@Test
	public final void testPOIWithAccesNoOffsets() {
		LocationReferenceHolder encLocRef = null;
		try {
			PoiAccessLocation inputLocation = td.getPOIWithAccessNoOffset();

			encLocRef = encodeLocation(inputLocation);

			assertTrue(encLocRef.isValid());

			RawLocationReference rawData = encLocRef
					.getRawLocationReferenceData();

			GeoCoordinates inputPoi = inputLocation.getPointLocation();
			GeoCoordinates coords = rawData.getGeoCoordinates();

			assertEquals(coords.getLatitudeDeg(), inputPoi.getLatitudeDeg());
			assertEquals(coords.getLongitudeDeg(), inputPoi.getLongitudeDeg());

			Offsets offsets = rawData.getOffsets();
			assertTrue(offsets.getPositiveOffset(0) <= 0);
			assertTrue(offsets.getNegativeOffset(0) <= 0);
		} catch (InvalidMapDataException e) {
			Assert.fail("Unexpected exception", e);
		}

	}

	/**
	 * Tests encoding a line location with positive and negative offset that can
	 * be reduced because the offsets exceed the first and last line.
	 */
	@Test
	public final void testReducedLineLocation() {

		LocationReferenceHolder encLocRef = null;
		LineLocation inputLocation = td.getLineLocationReducibleOffsets();
		int lengtIn = 0;
		for (Line l : inputLocation.getLocationLines()) {
			lengtIn += l.getLineLength();
		}

		int pOffIn = inputLocation.getPositiveOffset();
		int nOffIn = inputLocation.getNegativeOffset();

		encLocRef = encodeLocation(inputLocation);
		assertTrue(encLocRef.isValid());

		int lengthOut = 0;
		for (LocationReferencePoint lrp : encLocRef.getLRPs()) {
			lengthOut += lrp.getDistanceToNext();
		}
		Offsets offOut = encLocRef.getRawLocationReferenceData().getOffsets();

		assertTrue(lengtIn > lengthOut);
		assertTrue(pOffIn != offOut.getPositiveOffset(0));
		assertTrue(nOffIn != offOut.getNegativeOffset(0));

		// The central location length is the stretch between the offset points
		int locationLengthIn = lengtIn - pOffIn - nOffIn;
		int locationLengthOut = lengthOut - offOut.getPositiveOffset(0)
				- offOut.getNegativeOffset(0);
		// ... it should remain the same after pruning
		assertTrue(locationLengthIn == locationLengthOut);
	}

	/**
	 * Tests the case of an invalid location reference type.
	 */
	@Test
	public final void testInvalidLocationType() {

		final Location mockedLocRef = mockery.mock(Location.class,
				"invalidType");
		final String id = "locID";

		mockery.checking(new Expectations() {
			{
				allowing(mockedLocRef).getLocationType();
				will(returnValue(LocationType.UNKNOWN));
			}
		});
		mockery.checking(new Expectations() {
			{
				allowing(mockedLocRef).getID();
				will(returnValue(id));
			}
		});

		LocationReferenceHolder result = encodeLocation(mockedLocRef);
		assertFalse(result.isValid());
		// while we're at it we check the toString method of
		// DecoderReturnCode
		assertSame(result.getReturnCode().toString(),
				EncoderReturnCode.INVALID_LOCATION_TYPE.toString());
		assertEquals(result.getID(), id);
		assertNotNull(result.toString());
	}

	/**
	 * Tests the access methods for the version information of the encoder.
	 */
	@Test
	public final void testVersion() {

		char versionDelimiter = '.';
		OpenLREncoder encoder = new OpenLREncoder();
		String[] v = encoder.getVersion().split("\\" + versionDelimiter);

		assertEquals(encoder.getMajorVersion(), v[0]);
		assertEquals(encoder.getMinorVersion(), v[1]);
		assertEquals(encoder.getPatchVersion(), v[2]);
	}

	/**
	 * Tests the construction of {@link OpenLREncoderProcessingException}.
	 */
	@Test
	public final void testEncoderRuntimeException() {

		OpenLREncoderProcessingException e = new OpenLREncoderProcessingException(
				EncoderProcessingError.INTERMEDIATE_CALCULATION_FAILED);
		assertSame(e.getErrorCode().toString(),
				EncoderProcessingError.INTERMEDIATE_CALCULATION_FAILED.toString());

		String message = "Message";
		e = new OpenLREncoderProcessingException(
				EncoderProcessingError.NO_PHYSICAL_ENCODER_FOUND, message);
		assertSame(e.getMessage(), message);

		Exception cause = new Exception();
		e = new OpenLREncoderProcessingException(
				EncoderProcessingError.NO_PHYSICAL_ENCODER_FOUND, cause);
		assertSame(e.getCause(), cause);

		e = new OpenLREncoderProcessingException(
				EncoderProcessingError.NO_PHYSICAL_ENCODER_FOUND, message, cause);
		assertSame(e.getMessage(), message);
		assertSame(e.getCause(), cause);
	}

	/**
	 * Tests the encoder provided with a location containing <code>null</code> 
	 * entries in the contained list of lines.
	 */
	@Test
    public final void testLocationWithNullLines() {

        Location locNullLines = LocationFactory.createLineLocation(
                "locWithNullLines", Arrays.asList((Line) null, null));
        Location locOneNullLine = LocationFactory.createLineLocation(
                "locWithOnlyOneNullLine", Arrays.asList((Line) null));

        List<LocationReferenceHolder> encLocRef;
        try {
            OpenLREncoder encoder = new OpenLREncoder();
            OpenLREncoderParameter params = new OpenLREncoderParameter.Builder().with(td.getMapDatabase()).with(td.getConfiguration()).buildParameter();
            encLocRef = encoder.encodeLocations(params,
                    Arrays.asList(locNullLines, locOneNullLine));

            for (LocationReferenceHolder lrh : encLocRef) {
                assertFalse(lrh.isValid());
                assertEquals(lrh.getReturnCode(),
                        EncoderReturnCode.LOCATION_NOT_CONNECTED);
            }
        } catch (OpenLRProcessingException e) {
            fail("Encoding location failed with exception: " + e.getErrorCode(),
                    e);
        }
    }
	
	/**
	 * Tests the resolving of the physical encoder service.
	 */
	@Test
	public final void testResolvePhysicalDecoder() {
		Location iLocation = null;
		try {
			iLocation = td.getPOIWithAccessNoOffset();
		} catch (InvalidMapDataException e) {
			Assert.fail("Unexpected exception", e);
		}
		final Location inputLocation = iLocation;

		// our self-made return object
		final LocationReference mockedLocRef = mockery.mock(
				LocationReference.class, "locRefDummy");
		mockery.checking(new Expectations() {
			{
				allowing(mockedLocRef).getDataClass();
				will(returnValue(Object.class));
			}
		});
		mockery.checking(new Expectations() {
			{
				allowing(mockedLocRef).getDataIdentifier();
				will(returnValue(PhysicalEncoderImpl.DATA_IDENTIFIER));
			}
		});
		mockery.checking(new Expectations() {
			{
				allowing(mockedLocRef).getLocationReferenceData();
				will(returnValue(null));
			}
		});
		mockery.checking(new Expectations() {
			{
				allowing(mockedLocRef).getID();
				will(returnValue(inputLocation.getID()));
			}
		});
		mockery.checking(new Expectations() {
			{
				allowing(mockedLocRef).isValid();
				will(returnValue(true));
			}
		});

		PhysicalEncoderImpl.setLocToReturn(mockedLocRef);

		try {
			OpenLREncoder encoder = new OpenLREncoder();
			OpenLREncoderParameter params = new OpenLREncoderParameter.Builder().with(td.getMapDatabase()).with(td.getConfiguration()).buildParameter();
			// Test two possible methods, the one with the single object ...
			LocationReferenceHolder encLocRef = encoder.encodeLocation(params,
					inputLocation);

			LocationReference returnLoc = encLocRef
					.getLocationReference(PhysicalEncoderImpl.DATA_IDENTIFIER);

			assertEquals(returnLoc.getID(), mockedLocRef.getID());

			assertTrue(encLocRef.getNumberOfDataFormats() > 0);
			assertTrue(encLocRef.getDataFormatIdentifiers().contains(
					PhysicalEncoderImpl.DATA_IDENTIFIER));
			assertTrue(encLocRef.getNumberOfValidLR() > 0);

			// ... and the one with the list.
			InputStream props = getClass().getResourceAsStream(
					TestData.ENCODER_PROPERTIES);
			OpenLREncoderParameter params2 = new OpenLREncoderParameter.Builder().with(td.getMapDatabase()).with(OpenLRPropertiesReader
					.loadPropertiesFromStream(props, true)).buildParameter();
			List<LocationReferenceHolder> decLocRefs = encoder.encodeLocations(
					params2, Arrays.asList(inputLocation));

			returnLoc = decLocRefs.get(0).getLocationReference(
					PhysicalEncoderImpl.DATA_IDENTIFIER);
			assertEquals(returnLoc.getID(), mockedLocRef.getID());

		} catch (OpenLRProcessingException e) {
			fail("Decoding location failed with exception: " + e.getErrorCode(),
					e);
		} finally {
			PhysicalEncoderImpl.setLocToReturn(null);
		}
	}

	/**
	 * Test empty location.
	 */
	@Test
	public final void testEmptyLocation() {
		try {
			LocationReferenceHolder encLocRef = encodeLocation(td
					.getEmptyLocation());
			assertFalse(encLocRef.isValid());
			assertEquals(encLocRef.getReturnCode(),
					EncoderReturnCode.LOCATION_IS_EMPTY);
		} catch (Exception e) {
			fail("Wrong error detected: " + e.getMessage());
		}
	}

	/**
	 * Performs the encoding of the location and fails the test if an
	 * {@link OpenLREncoderProcessingException} occurs.
	 * 
	 * @param location
	 *            The location to encode.
	 * @return The successful encoded location.
	 */
	private LocationReferenceHolder encodeLocation(final Location location) {
		try {
			OpenLREncoder encoder = new OpenLREncoder();
			OpenLREncoderParameter params = new OpenLREncoderParameter.Builder().with(td.getMapDatabase()).with(td.getConfiguration()).buildParameter();
			return encoder.encodeLocation(params, location);
		} catch (OpenLRProcessingException e) {
			fail("Encoding location failed with exception: " + e.getErrorCode(),
					e);
			return null;
		}
	}
}
