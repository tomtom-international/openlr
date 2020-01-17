package openlr.proto.encoder;

import openlr.LocationReference;
import openlr.LocationType;
import openlr.map.GeoCoordinates;
import openlr.proto.OpenLRProtoException;
import openlr.proto.impl.GeoCoordinatesProtoImpl;
import openlr.proto.schema.Coordinates;
import openlr.proto.schema.LocationReferenceData;
import openlr.proto.schema.PolygonLocationReference;
import openlr.rawLocRef.RawPolygonLocRef;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class PolygonEncoderTest {
    private PolygonEncoder polygonEncoder = new PolygonEncoder();

    @Test
    public void testEncodePolygon() throws OpenLRProtoException {
        List<GeoCoordinates> geoCoordinates = new ArrayList<>();
        geoCoordinates.add(new GeoCoordinatesProtoImpl(-122.915, 39.045));
        geoCoordinates.add(new GeoCoordinatesProtoImpl(-122.914, 39.046));
        geoCoordinates.add(new GeoCoordinatesProtoImpl(-122.913, 39.047));

        RawPolygonLocRef rawLocationReference = new RawPolygonLocRef("1", geoCoordinates);

        LocationReference locationReference = polygonEncoder.encode(rawLocationReference);

        assertNotNull(locationReference);
        assertEquals(locationReference.getID(), "1");
        assertEquals(locationReference.getLocationType(), LocationType.POLYGON);
        assertEquals(locationReference.getDataIdentifier(), "proto");
        assertEquals(locationReference.getDataClass(), LocationReferenceData.class);

        Object locationReferenceData = locationReference.getLocationReferenceData();

        assertTrue(locationReferenceData instanceof LocationReferenceData);

        LocationReferenceData data = (LocationReferenceData) locationReferenceData;

        assertTrue(data.hasPolygonLocationReference());

        PolygonLocationReference polygonLocationReference = data.getPolygonLocationReference();

        List<Coordinates> coordinatesList = polygonLocationReference.getCoordinatesList();

        assertEquals(coordinatesList.size(), 3);

        Coordinates first = coordinatesList.get(0);
        assertNotNull(first);
        assertEquals(first.getLongitude(), -122.915);
        assertEquals(first.getLatitude(), 39.045);

        Coordinates second = coordinatesList.get(1);
        assertNotNull(second);
        assertEquals(second.getLongitude(), -122.914);
        assertEquals(second.getLatitude(), 39.046);

        Coordinates third = coordinatesList.get(2);
        assertNotNull(third);
        assertEquals(third.getLongitude(), -122.913);
        assertEquals(third.getLatitude(), 39.047);
    }
}
