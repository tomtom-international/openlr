package openlr.proto.decoder;

import openlr.LocationType;
import openlr.PhysicalFormatException;
import openlr.map.GeoCoordinates;
import openlr.proto.OpenLRProtoStatusCode;
import openlr.proto.schema.Coordinates;
import openlr.proto.schema.LocationReferenceData;
import openlr.proto.schema.PolygonLocationReference;
import openlr.rawLocRef.RawLocationReference;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.fail;

public class PolygonDecoderTest {
    private PolygonDecoder polygonDecoder = new PolygonDecoder();

    @Test
    public void testValidLocationReference() throws PhysicalFormatException {
        Coordinates first = Coordinates.newBuilder()
                .setLongitude(-122.915)
                .setLatitude(39.045)
                .build();

        Coordinates second = Coordinates.newBuilder()
                .setLongitude(-122.914)
                .setLatitude(39.046)
                .build();

        Coordinates third = Coordinates.newBuilder()
                .setLongitude(-122.913)
                .setLatitude(39.047)
                .build();

        PolygonLocationReference polygonLocationReference = PolygonLocationReference.newBuilder()
                .addCoordinates(first)
                .addCoordinates(second)
                .addCoordinates(third)
                .build();

        LocationReferenceData locationReferenceData = LocationReferenceData.newBuilder()
                .setPolygonLocationReference(polygonLocationReference)
                .build();

        RawLocationReference rawLocationReference = polygonDecoder.decode("1", locationReferenceData);

        assertNotNull(rawLocationReference);
        assertEquals("1", rawLocationReference.getID());
        assertEquals(LocationType.POLYGON, rawLocationReference.getLocationType());

        List<GeoCoordinates> cornerPoints = rawLocationReference.getCornerPoints();
        assertNotNull(cornerPoints);
        assertEquals(cornerPoints.size(), 3);

        GeoCoordinates firstCornerPoint = cornerPoints.get(0);
        assertNotNull(firstCornerPoint);
        assertEquals(firstCornerPoint.getLongitudeDeg(), -122.915);
        assertEquals(firstCornerPoint.getLatitudeDeg(), 39.045);

        GeoCoordinates secondCornerPoint = cornerPoints.get(1);
        assertNotNull(secondCornerPoint);
        assertEquals(secondCornerPoint.getLongitudeDeg(), -122.914);
        assertEquals(secondCornerPoint.getLatitudeDeg(), 39.046);

        GeoCoordinates thirdCornerPoint = cornerPoints.get(2);
        assertNotNull(thirdCornerPoint);
        assertEquals(thirdCornerPoint.getLongitudeDeg(), -122.913);
        assertEquals(thirdCornerPoint.getLatitudeDeg(), 39.047);
    }

    @Test
    public void testMissingLocationReference() {
        LocationReferenceData locationReferenceData = LocationReferenceData.newBuilder()
                .build();

        try {
            polygonDecoder.decode("1", locationReferenceData);
            fail();
        } catch (PhysicalFormatException e) {
            assertEquals(e.getErrorCode(), OpenLRProtoStatusCode.MISSING_LOCATION_REFERENCE);
        }
    }

    @Test
    public void testInvalidLocationReference() {
        PolygonLocationReference polygonLocationReference = PolygonLocationReference.newBuilder()
                .build();

        LocationReferenceData locationReferenceData = LocationReferenceData.newBuilder()
                .setPolygonLocationReference(polygonLocationReference)
                .build();

        try {
            polygonDecoder.decode("1", locationReferenceData);
            fail();
        } catch (PhysicalFormatException e) {
            assertEquals(e.getErrorCode(), OpenLRProtoStatusCode.INVALID_LOCATION_REFERENCE);
        }
    }
}
