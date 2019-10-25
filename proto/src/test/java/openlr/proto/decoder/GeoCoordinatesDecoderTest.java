package openlr.proto.decoder;

import openlr.LocationType;
import openlr.PhysicalFormatException;
import openlr.map.GeoCoordinates;
import openlr.proto.OpenLRProtoStatusCode;
import openlr.proto.schema.Coordinates;
import openlr.proto.schema.GeoCoordinatesLocationReference;
import openlr.proto.schema.LocationReferenceData;
import openlr.rawLocRef.RawLocationReference;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class GeoCoordinatesDecoderTest {
    GeoCoordinatesDecoder geoCoordinatesDecoder = new GeoCoordinatesDecoder();

    @Test
    public void testValidLocationReference() throws PhysicalFormatException {
        Coordinates coordinates = Coordinates.newBuilder()
                .setLongitude(1.5)
                .setLatitude(2.5)
                .build();

        GeoCoordinatesLocationReference geoCoordinatesLocationReference = GeoCoordinatesLocationReference.newBuilder()
                .setCoordinates(coordinates)
                .build();

        LocationReferenceData locationReferenceData = LocationReferenceData.newBuilder()
                .setGeoCoordinatesLocationReference(geoCoordinatesLocationReference)
                .build();

        RawLocationReference rawLocationReference = geoCoordinatesDecoder.decode("1", locationReferenceData);

        assertNotNull(rawLocationReference);
        assertEquals("1", rawLocationReference.getID());
        assertEquals(LocationType.GEO_COORDINATES, rawLocationReference.getLocationType());

        GeoCoordinates geoCoordinates = rawLocationReference.getGeoCoordinates();
        assertNotNull(geoCoordinates);
        assertEquals(geoCoordinates.getLongitudeDeg(), 1.5);
        assertEquals(geoCoordinates.getLatitudeDeg(), 2.5);
    }

    @Test
    public void testMissingLocationReference() {
        LocationReferenceData locationReferenceData = LocationReferenceData.newBuilder()
                .build();

        try {
            geoCoordinatesDecoder.decode("1", locationReferenceData);
            fail();
        } catch (PhysicalFormatException e) {
            assertEquals(e.getErrorCode(), OpenLRProtoStatusCode.MISSING_LOCATION_REFERENCE);
        }
    }

    @Test
    public void testInvalidLocationReference() {
        GeoCoordinatesLocationReference geoCoordinatesLocationReference = GeoCoordinatesLocationReference.newBuilder()
                .build();

        LocationReferenceData locationReferenceData = LocationReferenceData.newBuilder()
                .setGeoCoordinatesLocationReference(geoCoordinatesLocationReference)
                .build();

        try {
            geoCoordinatesDecoder.decode("1", locationReferenceData);
            fail();
        } catch (PhysicalFormatException e) {
            assertEquals(e.getErrorCode(), OpenLRProtoStatusCode.INVALID_LOCATION_REFERENCE);
        }
    }
}
