package openlr.proto.decoder;

import openlr.map.FormOfWay;
import openlr.map.FunctionalRoadClass;
import openlr.proto.OpenLRProtoException;
import openlr.proto.OpenLRProtoStatusCode;
import openlr.proto.schema.*;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class LocationReferencePointDecoderTest {
    LocationReferencePointDecoder locationReferencePointDecoder = new LocationReferencePointDecoder();

    @Test
    public void testLocationReferencePoint() throws OpenLRProtoException {
        Coordinates coordinates = Coordinates.newBuilder()
                .setLongitude(5)
                .setLatitude(52)
                .build();

        LineAttributes lineAttributes = LineAttributes.newBuilder()
                .setBearing(90)
                .setFrc(FRC.FRC_0)
                .setFow(FOW.FOW_MOTORWAY)
                .build();

        PathAttributes pathAttributes = PathAttributes.newBuilder()
                .setDistanceToNext(100)
                .setLowestFrcAlongPath(FRC.FRC_1)
                .build();

        LocationReferencePoint locationReferencePoint = LocationReferencePoint.newBuilder()
                .setCoordinates(coordinates)
                .setLineAttributes(lineAttributes)
                .setPathAttributes(pathAttributes)
                .build();

        openlr.LocationReferencePoint lrp = locationReferencePointDecoder.decode(locationReferencePoint, 1, false);

        assertNotNull(lrp);
        assertEquals(lrp.getSequenceNumber(), 1);
        assertEquals(lrp.getLongitudeDeg(),5);
        assertEquals(lrp.getLatitudeDeg(), 52);
        assertEquals(lrp.getBearing(), 90);
        assertEquals(lrp.getFRC(), FunctionalRoadClass.FRC_0);
        assertEquals(lrp.getFOW(), FormOfWay.MOTORWAY);
        assertEquals(lrp.getDistanceToNext(), 100);
        assertEquals(lrp.getLfrc(), FunctionalRoadClass.FRC_1);
        assertEquals(lrp.isLastLRP(), false);
    }

    @Test
    public void testLastLocationReferencePoint() throws OpenLRProtoException {
        Coordinates coordinates = Coordinates.newBuilder()
                .setLongitude(2)
                .setLatitude(51)
                .build();

        LineAttributes lineAttributes = LineAttributes.newBuilder()
                .setBearing(180)
                .setFrc(FRC.FRC_4)
                .setFow(FOW.FOW_SINGLE_CARRIAGEWAY)
                .build();

        LocationReferencePoint locationReferencePoint = LocationReferencePoint.newBuilder()
                .setCoordinates(coordinates)
                .setLineAttributes(lineAttributes)
                .build();

        openlr.LocationReferencePoint lrp = locationReferencePointDecoder.decode(locationReferencePoint, 3, true);

        assertNotNull(lrp);
        assertEquals(lrp.getSequenceNumber(), 3);
        assertEquals(lrp.getLongitudeDeg(), 2);
        assertEquals(lrp.getLatitudeDeg(), 51);
        assertEquals(lrp.getBearing(), 180);
        assertEquals(lrp.getFRC(), FunctionalRoadClass.FRC_4);
        assertEquals(lrp.getFOW(), FormOfWay.SINGLE_CARRIAGEWAY);
        assertEquals(lrp.isLastLRP(),true);
    }

    @Test
    public void testMissingCoordinates() {
        LineAttributes lineAttributes = LineAttributes.newBuilder()
                .setBearing(180)
                .setFrc(FRC.FRC_4)
                .setFow(FOW.FOW_SINGLE_CARRIAGEWAY)
                .build();

        PathAttributes pathAttributes = PathAttributes.newBuilder()
                .setDistanceToNext(100)
                .setLowestFrcAlongPath(FRC.FRC_1)
                .build();

        LocationReferencePoint locationReferencePoint = LocationReferencePoint.newBuilder()
                .setLineAttributes(lineAttributes)
                .setPathAttributes(pathAttributes)
                .build();

        try {
            locationReferencePointDecoder.decode(locationReferencePoint, 2, false);
        }
        catch (OpenLRProtoException e) {
            assertEquals(e.getErrorCode(), OpenLRProtoStatusCode.INVALID_LOCATION_REFERENCE);
        }
    }

    @Test
    public void testMissingLineAttributes() {
        Coordinates coordinates = Coordinates.newBuilder()
                .setLongitude(2)
                .setLatitude(51)
                .build();

        PathAttributes pathAttributes = PathAttributes.newBuilder()
                .setDistanceToNext(100)
                .setLowestFrcAlongPath(FRC.FRC_1)
                .build();

        LocationReferencePoint locationReferencePoint = LocationReferencePoint.newBuilder()
                .setCoordinates(coordinates)
                .setPathAttributes(pathAttributes)
                .build();

        try {
            locationReferencePointDecoder.decode(locationReferencePoint, 2, false);
        }
        catch (OpenLRProtoException e) {
            assertEquals(e.getErrorCode(), OpenLRProtoStatusCode.INVALID_LOCATION_REFERENCE);
        }
    }

    @Test
    public void testMissingPathAttributes() {
        Coordinates coordinates = Coordinates.newBuilder()
                .setLongitude(2)
                .setLatitude(51)
                .build();

        LineAttributes lineAttributes = LineAttributes.newBuilder()
                .setBearing(180)
                .setFrc(FRC.FRC_4)
                .setFow(FOW.FOW_SINGLE_CARRIAGEWAY)
                .build();

        LocationReferencePoint locationReferencePoint = LocationReferencePoint.newBuilder()
                .setCoordinates(coordinates)
                .setLineAttributes(lineAttributes)
                .build();

        try {
            locationReferencePointDecoder.decode(locationReferencePoint, 1, false);
        }
        catch (OpenLRProtoException e) {
            assertEquals(e.getErrorCode(), OpenLRProtoStatusCode.INVALID_LOCATION_REFERENCE);
        }
    }
}
