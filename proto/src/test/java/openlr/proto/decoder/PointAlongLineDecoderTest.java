package openlr.proto.decoder;

import openlr.LocationType;
import openlr.Offsets;
import openlr.PhysicalFormatException;
import openlr.location.data.Orientation;
import openlr.location.data.SideOfRoad;
import openlr.map.FormOfWay;
import openlr.map.FunctionalRoadClass;
import openlr.proto.OpenLRProtoStatusCode;
import openlr.proto.schema.*;
import openlr.rawLocRef.RawLocationReference;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

public class PointAlongLineDecoderTest {
    PointAlongLineDecoder pointAlongLineDecoder = new PointAlongLineDecoder(new LocationReferencePointDecoder());

    @Test
    public void testValidLocationReference() throws PhysicalFormatException {
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

        RawLocationReference rawLocationReference = pointAlongLineDecoder.decode("1", locationReferenceData);

        assertNotNull(rawLocationReference);
        assertEquals(rawLocationReference.getID(), "1");
        assertEquals(rawLocationReference.getLocationType(), LocationType.POINT_ALONG_LINE);

        List<openlr.LocationReferencePoint> locationReferencePoints = rawLocationReference.getLocationReferencePoints();
        assertNotNull(locationReferencePoints);
        assertEquals(locationReferencePoints.size(), 2);

        openlr.LocationReferencePoint firstLrp = locationReferencePoints.get(0);
        assertEquals(firstLrp.getLongitudeDeg(), 4);
        assertEquals(firstLrp.getLatitudeDeg(), 5);
        assertEquals(firstLrp.getBearing(), 120);
        assertEquals(firstLrp.getFRC(), FunctionalRoadClass.FRC_3);
        assertEquals(firstLrp.getFOW(), FormOfWay.SLIPROAD);
        assertEquals(firstLrp.getDistanceToNext(), 200);
        assertEquals(firstLrp.getLfrc(), FunctionalRoadClass.FRC_5);

        openlr.LocationReferencePoint lastLrp = locationReferencePoints.get(1);
        assertEquals(lastLrp.getLongitudeDeg(), 4.2);
        assertEquals(lastLrp.getLatitudeDeg(), 5.2);
        assertEquals(lastLrp.getBearing(), 270);
        assertEquals(lastLrp.getFRC(), FunctionalRoadClass.FRC_1);
        assertEquals(lastLrp.getFOW(), FormOfWay.MULTIPLE_CARRIAGEWAY);

        Offsets offsets = rawLocationReference.getOffsets();
        assertNotNull(offsets);
        assertEquals(offsets.getPositiveOffset(200),20);

        assertEquals(rawLocationReference.getSideOfRoad(), SideOfRoad.RIGHT);
        assertEquals(rawLocationReference.getOrientation(), Orientation.BOTH);
    }

    @Test
    public void testMissingLocationReference() {
        LocationReferenceData locationReferenceData = LocationReferenceData.newBuilder()
                .build();

        try {
            pointAlongLineDecoder.decode("1", locationReferenceData);
            fail();
        } catch (PhysicalFormatException e) {
            assertEquals(e.getErrorCode(), OpenLRProtoStatusCode.MISSING_LOCATION_REFERENCE);
        }
    }

    @Test
    public void testInvalidLocationReference() {
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
                .setLast(last)
                .setPositiveOffset(20)
                .build();

        LocationReferenceData locationReferenceData = LocationReferenceData.newBuilder()
                .setPointAlongLineLocationReference(pointAlongLineLocationReference)
                .build();

        try {
            pointAlongLineDecoder.decode("1", locationReferenceData);
            fail();
        } catch (PhysicalFormatException e) {
            assertEquals(e.getErrorCode(), OpenLRProtoStatusCode.INVALID_LOCATION_REFERENCE);
        }
    }
}
