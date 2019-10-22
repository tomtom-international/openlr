package openlr.proto.decoder;

import openlr.LocationType;
import openlr.Offsets;
import openlr.PhysicalFormatException;
import openlr.map.FormOfWay;
import openlr.map.FunctionalRoadClass;
import openlr.proto.OpenLRProtoStatusCode;
import openlr.proto.schema.*;
import openlr.rawLocRef.RawLocationReference;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;
import static org.testng.Assert.assertEquals;

public class LineDecoderTest {
    LineDecoder lineDecoder = new LineDecoder(new LocationReferencePointDecoder());

    @Test
    public void testValidLocationReference() throws PhysicalFormatException {
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

        RawLocationReference rawLocationReference = lineDecoder.decode("1", locationReferenceData);

        assertNotNull(rawLocationReference);
        assertEquals(rawLocationReference.getID(), "1");
        assertEquals(rawLocationReference.getLocationType(), LocationType.LINE_LOCATION);

        List<openlr.LocationReferencePoint> locationReferencePoints = rawLocationReference.getLocationReferencePoints();
        assertNotNull(locationReferencePoints);
        assertEquals(locationReferencePoints.size(), 2);

        openlr.LocationReferencePoint firstLrp = locationReferencePoints.get(0);
        assertEquals(firstLrp.getLongitudeDeg(), 1);
        assertEquals(firstLrp.getLatitudeDeg(), 2);
        assertEquals(firstLrp.getBearing(), 90);
        assertEquals(firstLrp.getFRC(), FunctionalRoadClass.FRC_0);
        assertEquals(firstLrp.getFOW(), FormOfWay.MOTORWAY);
        assertEquals(firstLrp.getDistanceToNext(),100);
        assertEquals(firstLrp.getLfrc(), FunctionalRoadClass.FRC_1);

        openlr.LocationReferencePoint lastLrp = locationReferencePoints.get(1);
        assertEquals(lastLrp.getLongitudeDeg(), 3);
        assertEquals(lastLrp.getLatitudeDeg(),4);
        assertEquals(lastLrp.getBearing(), 270);
        assertEquals(lastLrp.getFRC(), FunctionalRoadClass.FRC_1);
        assertEquals(lastLrp.getFOW(), FormOfWay.MULTIPLE_CARRIAGEWAY);

        Offsets offsets = rawLocationReference.getOffsets();
        assertNotNull(offsets);
        assertEquals(offsets.getPositiveOffset(100), 1);
        assertEquals(offsets.getNegativeOffset(100), 2);
    }

    @Test
    public void testMissingLocationReference() {
        LocationReferenceData locationReferenceData = LocationReferenceData.newBuilder()
                .build();

        try {
            lineDecoder.decode("1", locationReferenceData);
            fail();
        } catch (PhysicalFormatException e) {
            assertEquals(e.getErrorCode(), OpenLRProtoStatusCode.MISSING_LOCATION_REFERENCE);
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

        try {
            lineDecoder.decode("1", locationReferenceData);
            fail();
        } catch (PhysicalFormatException e) {
            assertEquals(e.getErrorCode(), OpenLRProtoStatusCode.INVALID_LOCATION_REFERENCE);
        }
    }
}
