package openlr.proto;

import openlr.*;
import openlr.proto.impl.LocationReferenceProtoImpl;
import openlr.proto.schema.*;
import openlr.proto.schema.LocationReferencePoint;
import openlr.rawLocRef.RawLocationReference;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class OpenLRProtoDecoderTest {
    OpenLRProtoDecoder decoder = new OpenLRProtoDecoder();

    @Test
    public void testLineLocationReference() throws PhysicalFormatException {
        Coordinates firstCoordinates = Coordinates.newBuilder()
                .setLongitude(1)
                .setLatitude(2)
                .build();

        LineAttributes firstLineAttributes = LineAttributes.newBuilder()
                .setBearing(90)
                .setFrc(FRC.FRC_0)
                .setFow(FOW.FOW_MOTORWAY)
                .build();

        PathAttributes firstPathAttributes = PathAttributes.newBuilder()
                .setDistanceToNext(100)
                .setLowestFrcAlongPath(FRC.FRC_1)
                .build();

        LocationReferencePoint first = LocationReferencePoint.newBuilder()
                .setCoordinates(firstCoordinates)
                .setLineAttributes(firstLineAttributes)
                .setPathAttributes(firstPathAttributes)
                .build();

        Coordinates lastCoordinates = Coordinates.newBuilder()
                .setLongitude(3)
                .setLatitude(4)
                .build();

        LineAttributes lastLineAttributes = LineAttributes.newBuilder()
                .setBearing(270)
                .setFrc(FRC.FRC_1)
                .setFow(FOW.FOW_MULTIPLE_CARRIAGEWAY)
                .build();

        LocationReferencePoint last = LocationReferencePoint.newBuilder()
                .setCoordinates(lastCoordinates)
                .setLineAttributes(lastLineAttributes)
                .build();

        LineLocationReference lineLocationReference = LineLocationReference.newBuilder()
                .addLocationReferencePoints(first)
                .addLocationReferencePoints(last)
                .setPositiveOffset(1)
                .setNegativeOffset(2)
                .build();

        LocationReferenceData locationReferenceData = LocationReferenceData.newBuilder()
                .setLineLocationReference(lineLocationReference)
                .build();

        LocationReference locationReference = new LocationReferenceProtoImpl("1", LocationType.LINE_LOCATION, locationReferenceData);

        RawLocationReference rawLocationReference = decoder.decodeData(locationReference);

        assertNotNull(rawLocationReference);
        assertEquals(rawLocationReference.getID(), "1");
        assertEquals(rawLocationReference.getLocationType(), LocationType.LINE_LOCATION);
        assertTrue(rawLocationReference.isValid());
    }

    @Test
    public void testGeoCoordinatesLocationReference() throws PhysicalFormatException {
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

        LocationReference locationReference = new LocationReferenceProtoImpl("2", LocationType.GEO_COORDINATES, locationReferenceData);

        RawLocationReference rawLocationReference = decoder.decodeData(locationReference);

        assertNotNull(rawLocationReference);
        assertEquals(rawLocationReference.getID(), "2");
        assertEquals(rawLocationReference.getLocationType(), LocationType.GEO_COORDINATES);
        assertTrue(rawLocationReference.isValid());
    }

    @Test
    public void testPointAlongLineLocationReference() throws PhysicalFormatException {
        Coordinates firstCoordinates = Coordinates.newBuilder()
                .setLongitude(4)
                .setLatitude(5)
                .build();

        LineAttributes firstLineAttributes = LineAttributes.newBuilder()
                .setBearing(120)
                .setFrc(FRC.FRC_3)
                .setFow(FOW.FOW_SLIPROAD)
                .build();

        PathAttributes firstPathAttributes = PathAttributes.newBuilder()
                .setDistanceToNext(200)
                .setLowestFrcAlongPath(FRC.FRC_5)
                .build();

        LocationReferencePoint first = LocationReferencePoint.newBuilder()
                .setCoordinates(firstCoordinates)
                .setLineAttributes(firstLineAttributes)
                .setPathAttributes(firstPathAttributes)
                .build();

        Coordinates lastCoordinates = Coordinates.newBuilder()
                .setLongitude(4.2)
                .setLatitude(5.2)
                .build();

        LineAttributes lastLineAttributes = LineAttributes.newBuilder()
                .setBearing(270)
                .setFrc(FRC.FRC_1)
                .setFow(FOW.FOW_MULTIPLE_CARRIAGEWAY)
                .build();

        LocationReferencePoint last = LocationReferencePoint.newBuilder()
                .setCoordinates(lastCoordinates)
                .setLineAttributes(lastLineAttributes)
                .build();

        PointAlongLineLocationReference pointAlongLineLocationReference = PointAlongLineLocationReference.newBuilder()
                .setFirst(first)
                .setLast(last)
                .setPositiveOffset(20)
                .setSideOfRoad(openlr.proto.schema.SideOfRoad.SIDE_OF_ROAD_RIGHT)
                .setOrientation(openlr.proto.schema.Orientation.ORIENTATION_BOTH)
                .build();

        LocationReferenceData locationReferenceData = LocationReferenceData.newBuilder()
                .setPointAlongLineLocationReference(pointAlongLineLocationReference)
                .build();

        LocationReference locationReference = new LocationReferenceProtoImpl("3", LocationType.POINT_ALONG_LINE, locationReferenceData);

        RawLocationReference rawLocationReference = decoder.decodeData(locationReference);

        assertNotNull(rawLocationReference);
        assertEquals(rawLocationReference.getID(), "3");
        assertEquals(rawLocationReference.getLocationType(), LocationType.POINT_ALONG_LINE);
        assertTrue(rawLocationReference.isValid());
    }

    @Test
    public void testUnsupportedLocationType() {
        LocationReferenceData locationReferenceData = LocationReferenceData.newBuilder()
                .build();

        LocationReference locationReference = new LocationReferenceProtoImpl("1", LocationType.UNKNOWN, locationReferenceData);

        try {
            decoder.decodeData(locationReference);
            fail();
        } catch (PhysicalFormatException e) {
            assertEquals(e.getErrorCode(), OpenLRProtoStatusCode.UNSUPPORTED_LOCATION_TYPE);
        }
    }

    @Test
    public void testInvalidLocationReference() {
        Coordinates firstCoordinates = Coordinates.newBuilder()
                .setLongitude(1)
                .setLatitude(2)
                .build();

        LineAttributes firstLineAttributes = LineAttributes.newBuilder()
                .setBearing(90)
                .setFrc(FRC.FRC_0)
                .setFow(FOW.FOW_MOTORWAY)
                .build();

        PathAttributes firstPathAttributes = PathAttributes.newBuilder()
                .setDistanceToNext(100)
                .setLowestFrcAlongPath(FRC.FRC_1)
                .build();

        LocationReferencePoint first = LocationReferencePoint.newBuilder()
                .setCoordinates(firstCoordinates)
                .setLineAttributes(firstLineAttributes)
                .setPathAttributes(firstPathAttributes)
                .build();

        LineLocationReference lineLocationReference = LineLocationReference.newBuilder()
                .addLocationReferencePoints(first)
                .setPositiveOffset(1)
                .setNegativeOffset(2)
                .build();

        LocationReferenceData locationReferenceData = LocationReferenceData.newBuilder()
                .setLineLocationReference(lineLocationReference)
                .build();

        LocationReference locationReference = new LocationReferenceProtoImpl("1", LocationType.LINE_LOCATION, locationReferenceData);

        try {
            decoder.decodeData(locationReference);
            fail();
        } catch (PhysicalFormatException e) {
            assertEquals(e.getErrorCode(), OpenLRProtoStatusCode.INVALID_LOCATION_REFERENCE);
        }
    }
}
