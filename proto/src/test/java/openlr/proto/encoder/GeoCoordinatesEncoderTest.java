package openlr.proto.encoder;

import openlr.LocationReference;
import openlr.LocationType;
import openlr.proto.OpenLRProtoException;
import openlr.proto.impl.GeoCoordinatesProtoImpl;
import openlr.proto.schema.Coordinates;
import openlr.proto.schema.GeoCoordinatesLocationReference;
import openlr.proto.schema.LocationReferenceData;
import openlr.rawLocRef.RawGeoCoordLocRef;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class GeoCoordinatesEncoderTest {
    GeoCoordinatesEncoder geoCoordinatesEncoder = new GeoCoordinatesEncoder();

    @Test
    public void testEncodeGeoCoordinates() throws OpenLRProtoException {
        GeoCoordinatesProtoImpl geoCoordinates = new GeoCoordinatesProtoImpl(7.3, 52.5);
        RawGeoCoordLocRef rawLocationReference = new RawGeoCoordLocRef("1", geoCoordinates);

        LocationReference locationReference = geoCoordinatesEncoder.encode(rawLocationReference);

        assertNotNull(locationReference);
        assertEquals(locationReference.getID(), "1");
        assertEquals(locationReference.getLocationType(), LocationType.GEO_COORDINATES);
        assertEquals(locationReference.getDataIdentifier(), "proto");
        assertEquals(locationReference.getDataClass(), LocationReferenceData.class);

        assertNotNull(locationReference);
        assertEquals(locationReference.getID(), "1");
        assertEquals(locationReference.getLocationType(), LocationType.GEO_COORDINATES);
        assertTrue(locationReference.isValid());
        assertEquals(locationReference.getDataClass(), LocationReferenceData.class);

        Object locationReferenceData = locationReference.getLocationReferenceData();

        assertTrue(locationReferenceData instanceof LocationReferenceData);

        LocationReferenceData data = (LocationReferenceData) locationReferenceData;

        assertTrue(data.hasGeoCoordinatesLocationReference());

        GeoCoordinatesLocationReference geoCoordinatesLocationReference = data.getGeoCoordinatesLocationReference();

        assertTrue(geoCoordinatesLocationReference.hasCoordinates());

        Coordinates coordinates = geoCoordinatesLocationReference.getCoordinates();

        assertEquals(coordinates.getLongitude(), 7.3);
        assertEquals(coordinates.getLatitude(), 52.5);
    }
}
