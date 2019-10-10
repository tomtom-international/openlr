/**
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 2 of the License and the extra
 * conditions for OpenLR. (see openlr-license.txt)
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 * <p>
 * Copyright (C) 2009-2019 TomTom International B.V.
 * <p>
 * TomTom (Legal Department)
 * Email: legal@tomtom.com
 * <p>
 * TomTom (Technical contact)
 * Email: openlr@tomtom.com
 * <p>
 * Address: TomTom International B.V., Oosterdoksstraat 114, 1011DK Amsterdam,
 * the Netherlands
 * <p>
 * Copyright (C) 2009-2019 TomTom International B.V.
 * <p>
 * TomTom (Legal Department)
 * Email: legal@tomtom.com
 * <p>
 * TomTom (Technical contact)
 * Email: openlr@tomtom.com
 * <p>
 * Address: TomTom International B.V., Oosterdoksstraat 114, 1011DK Amsterdam,
 * the Netherlands
 */

/**
 *  Copyright (C) 2009-2019 TomTom International B.V.
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
package openlr.decoder;

import openlr.LocationReference;
import openlr.LocationType;
import openlr.OpenLRProcessingException;
import openlr.decoder.OpenLRDecoderProcessingException.DecoderProcessingError;
import openlr.decoder.impl.DummyPhysicalDecoderImpl;
import openlr.location.Location;
import openlr.map.GeoCoordinates;
import openlr.map.InvalidMapDataException;
import openlr.map.MapDatabase;
import openlr.map.Node;
import openlr.map.mockdb.InvalidConfigurationException;
import openlr.map.mockdb.MockedMapDatabase;
import openlr.properties.OpenLRPropertiesReader;
import openlr.rawLocRef.RawInvalidLocRef;
import openlr.rawLocRef.RawLocationReference;
import openlr.testutils.CommonObjectTestUtils;
import org.apache.commons.configuration.Configuration;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static openlr.decoder.TestData.DECODER_PROPERTIES;
import static org.testng.Assert.*;

/**
 * Test several decodings.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class OpenLRDecoderTest {

    /**
     * The expected node in the result of test
     * {@link #testPOIWithAccesNoOffsets}
     */
    private static final int EXPECTED_NODE_NO_OFFSETS = 5;
    /** The mocking object. */
    private final Mockery mockery = new Mockery();
    /**
     * An utility class holding prepared/mocked test data.
     */
    private TestData td = TestData.getInstance();
    /**
     * A reference to a valid location reference.
     */
    private RawLocationReference mockedLocRefPoiWithAccess;
    /** The mocked database for some of the tests. */
    private MapDatabase mockedMapDB;

    /**
     * Creates a mocked map database.
     */
    @SuppressWarnings("deprecation")
    @BeforeTest
    public final void setup() {
        try {
            mockedMapDB = new MockedMapDatabase(TestData.DEFAULT_MAP_DB_CONFIG,
                    false);
        } catch (InvalidConfigurationException e) {
            fail("Unexpected exception during setup of mocked map DB", e);
        }
    }

    /**
     * Tests a point with access point location with not properly connected
     * lines.
     */
    @Test
    public final void testPOIWithAccesNotConnected() {
        Location decLocRef;
        String id = "notConnectedPAL";
        InputStream properties = null;

        try {
            properties = new FileInputStream(DECODER_PROPERTIES);

        } catch (IOException e) {
            fail("Error creating properties stream.", e);
        }

        try {
            OpenLRDecoder decoder = new OpenLRDecoder();
            OpenLRDecoderParameter parameter = new OpenLRDecoderParameter.Builder()
                    .with(mockedMapDB)
                    .with(OpenLRPropertiesReader.loadPropertiesFromStream(
                            properties, true)).buildParameter();
            decLocRef = decoder.decodeRaw(parameter, td.getNotConnectedPWA());
            assertFalse(decLocRef.isValid());
            assertEquals(decLocRef.getReturnCode().getID(),
                    DecoderReturnCode.NO_ROUTE_FOUND.getID());
            assertNotNull(decLocRef.toString());
            assertEquals(decLocRef.getID(), id);

        } catch (OpenLRProcessingException e) {
            fail("Decoding location failed with exception: " + e.getErrorCode(),
                    e);
        }
    }

    /**
     * Tests decoding of a line location with too large offset definitions (but
     * inside the valid length of line length * 2). <br>
     * The decoder will deliver a valid location pruned to a single remaining
     * line with maximum offsets on it.
     */
    @Test
    public final void testLineLocationExceedingOffsets() {
        Location decLocRef;

        InputStream properties = null;

        try {
            properties = new FileInputStream(DECODER_PROPERTIES);

        } catch (IOException e) {
            fail("Error creating properties stream.", e);
        }

        try {
            OpenLRDecoder decoder = new OpenLRDecoder();
            OpenLRDecoderParameter parameter = new OpenLRDecoderParameter.Builder()
                    .with(mockedMapDB)
                    .with(OpenLRPropertiesReader.loadPropertiesFromStream(
                            properties, true)).buildParameter();
            decLocRef = decoder.decodeRaw(parameter,
                    td.getLineLocationExceedingOffsets());
            assertTrue(decLocRef.isValid());
            assertNotNull(decLocRef.toString());

        } catch (OpenLRProcessingException e) {
            fail("Decoding location failed with exception: " + e.getErrorCode(),
                    e);
        }
    }

    /**
     * Tests a point with access point location with no offset.
     */
    @Test
    public final void testPOIWithAccesNoOffsets() {
        Location decLocRef = null;
        RawLocationReference inputLocation = null;
        try {
            inputLocation = td.getPWANoOffsets();
            mockedLocRefPoiWithAccess = inputLocation;

            OpenLRDecoder decoder = new OpenLRDecoder();
            OpenLRDecoderParameter parameter = new OpenLRDecoderParameter.Builder()
                    .with(mockedMapDB)
                    .with(td.getProperties()).buildParameter();
            decLocRef = decoder.decodeRaw(parameter, inputLocation);

        } catch (OpenLRProcessingException e) {
            fail("Decoding location failed with exception: " + e.getErrorCode(),
                    e);
        } catch (InvalidMapDataException e) {
            fail("Decoding location failed with exception",
                    e);
        }
        assertTrue(decLocRef.isValid());

        GeoCoordinates inputPoi = inputLocation.getGeoCoordinates();

        GeoCoordinates coords = decLocRef.getPointLocation();

        assertEquals(coords.getLatitudeDeg(), inputPoi.getLatitudeDeg());
        assertEquals(coords.getLongitudeDeg(), inputPoi.getLongitudeDeg());

        // because of the absent offset the access point should be the start
        // Node
        GeoCoordinates accessP = decLocRef.getAccessPoint();
        Node node5 = mockedMapDB.getNode(EXPECTED_NODE_NO_OFFSETS);
        assertEquals(accessP.getLongitudeDeg(), node5.getLongitudeDeg());
        assertEquals(accessP.getLatitudeDeg(), node5.getLatitudeDeg());

        assertNotNull(accessP.toString());
    }

    /**
     * Tests the resolving of the physical decoder service.
     */
    @Test(dependsOnMethods = {"testPOIWithAccesNoOffsets"})
    public final void testResolvePhysicalDecoder() {

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
                will(returnValue(DummyPhysicalDecoderImpl.DATA_IDENTIFIER));
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
                will(returnValue(mockedLocRefPoiWithAccess.getID()));
            }
        });

        assertNotNull(mockedLocRefPoiWithAccess,
                "Required input object is null!");
        DummyPhysicalDecoderImpl.setRawLocToReturn(mockedLocRefPoiWithAccess);

        try {
            InputStream properties = new FileInputStream(DECODER_PROPERTIES);
            Configuration conf = OpenLRPropertiesReader
                    .loadPropertiesFromStream(properties, true);
            OpenLRDecoder decoder = new OpenLRDecoder();
            // Test two possible methods, the one with the single object ...
            OpenLRDecoderParameter parameter = new OpenLRDecoderParameter.Builder()
                    .with(mockedMapDB)
                    .with(conf).buildParameter();
            Location decLocRef = decoder.decode(parameter, mockedLocRef);
            assertEquals(decLocRef.getID(), mockedLocRefPoiWithAccess.getID());

            // ... and the one with the list.
            properties = new FileInputStream(DECODER_PROPERTIES);
            List<Location> decLocRefs = decoder.decode(parameter,
                    Arrays.asList(mockedLocRef));
            assertEquals(decLocRefs.get(0).getID(),
                    mockedLocRefPoiWithAccess.getID());

        } catch (OpenLRProcessingException e) {
            fail("Decoding location failed with exception: " + e.getErrorCode(),
                    e);
        } catch (IOException e) {
            fail("Error creating properties stream.", e);
        } finally {
            DummyPhysicalDecoderImpl.setRawLocToReturn(null);
        }
    }

    /**
     * Tests the case of an invalid location reference type.
     */
    @Test
    public final void testInvalidLocationType() {

        final RawLocationReference mockedLocRef = new RawInvalidLocRef("id",
                null, LocationType.UNKNOWN);

        try {
            OpenLRDecoder decoder = new OpenLRDecoder();
            OpenLRDecoderParameter parameter = new OpenLRDecoderParameter.Builder()
                    .with(mockedMapDB)
                    .with(td.getProperties()).buildParameter();
            Location result = decoder.decodeRaw(parameter, mockedLocRef);
            assertFalse(result.isValid());
            // while we're at it we check the toString method of DecoderReturnCode
            assertEquals(result.getReturnCode(),
                    DecoderReturnCode.INVALID_LOCATION_TYPE);

            assertNull(result.getAccessPoint());
            assertNull(result.getPointLocation());
            assertNull(result.getPoiLine());
            assertNull(result.getOrientation());
            assertNull(result.getSideOfRoad());
            assertTrue(result.getLocationLines().isEmpty());
            assertFalse(result.hasNegativeOffset());
            assertFalse(result.hasPositiveOffset());

            CommonObjectTestUtils.testToString(result);

        } catch (OpenLRProcessingException e) {
            fail("Unexpected exception!", e);
        }
    }

    /**
     * Test if a location reference is decoded even if it cannot be found in the
     * location database.
     */
    @Test
    public final void testDecodingWithLRDB() {

        OpenLRDecoder decoder = new OpenLRDecoder();
        // empty location database
        LocationDatabase db = LocationDatabaseFactory
                .createLocationDatabase(2);
        OpenLRDecoderParameter parameter = new OpenLRDecoderParameter.Builder()
                .with(mockedMapDB)
                .with(td.getProperties()).with(db).buildParameter();
        try {
            Location result = decoder.decodeRaw(parameter,
                    td.getWhitepaperGeoCoordinateLocation());
            assertTrue(result.isValid());
        } catch (Exception e) {
            fail("Unexpected exception!", e);
        }
    }

    /**
     * Tests the access methods for the version information of the decoder.
     */
    @Test
    public final void testVersion() {

        char versionDelimiter = '.';

        OpenLRDecoder decoder = new OpenLRDecoder();
        String[] v = decoder.getVersion().split("\\" + versionDelimiter);
        assertEquals(decoder.getMajorVersion(), v[0]);
        assertEquals(decoder.getMinorVersion(), v[1]);
        assertEquals(decoder.getPatchVersion(), v[2]);
    }

    /**
     * Tests the construction of {@link OpenLRDecoderProcessingException}.
     */
    @Test
    public final void testDecoderRuntimeException() {

        OpenLRDecoderProcessingException e = new OpenLRDecoderProcessingException(
                DecoderProcessingError.ROUTE_DISCONNECTED);
        assertEquals(e.getErrorCode(),
                DecoderProcessingError.ROUTE_DISCONNECTED);

        String message = "Message";
        e = new OpenLRDecoderProcessingException(
                DecoderProcessingError.ROUTE_DISCONNECTED, message);
        assertEquals(e.getMessage(), message);

        Exception cause = new Exception();
        e = new OpenLRDecoderProcessingException(
                DecoderProcessingError.ROUTE_DISCONNECTED, cause);
        assertEquals(e.getCause(), cause);

        e = new OpenLRDecoderProcessingException(
                DecoderProcessingError.ROUTE_DISCONNECTED, message, cause);
        assertEquals(e.getMessage(), message);
        assertEquals(e.getCause(), cause);
    }
}
