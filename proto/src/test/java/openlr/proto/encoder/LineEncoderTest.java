package openlr.proto.encoder;

import openlr.LocationReference;
import openlr.LocationReferencePoint;
import openlr.LocationType;
import openlr.Offsets;
import openlr.map.FormOfWay;
import openlr.map.FunctionalRoadClass;
import openlr.proto.OpenLRProtoException;
import openlr.proto.impl.LocationReferencePointProtoImpl;
import openlr.proto.impl.OffsetsProtoImpl;
import openlr.proto.schema.*;
import openlr.rawLocRef.RawLineLocRef;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class LineEncoderTest {
    LineEncoder lineEncoder = new LineEncoder(new LocationReferencePointEncoder());

    @Test
    public void testEncodeLine() throws OpenLRProtoException {
        List<LocationReferencePoint> locationReferencePoints = new ArrayList<>();

        LocationReferencePointProtoImpl first = new LocationReferencePointProtoImpl(
                1,
                5.7,
                53.5,
                15.5,
                FunctionalRoadClass.FRC_3,
                FormOfWay.MULTIPLE_CARRIAGEWAY,
                200,
                FunctionalRoadClass.FRC_4);

        LocationReferencePointProtoImpl second = new LocationReferencePointProtoImpl(
                1,
                5.8,
                53.6,
                20,
                FunctionalRoadClass.FRC_3,
                FormOfWay.MULTIPLE_CARRIAGEWAY,
                100,
                FunctionalRoadClass.FRC_4);

        LocationReferencePointProtoImpl last = new LocationReferencePointProtoImpl(
                2,
                5.6,
                53.6,
                165,
                FunctionalRoadClass.FRC_4,
                FormOfWay.MULTIPLE_CARRIAGEWAY);

        locationReferencePoints.add(first);
        locationReferencePoints.add(second);
        locationReferencePoints.add(last);

        Offsets offsets = new OffsetsProtoImpl(20, 10);

        RawLineLocRef rawLocationReference = new RawLineLocRef("1", locationReferencePoints, offsets);

        LocationReference locationReference = lineEncoder.encode(rawLocationReference);

        assertNotNull(locationReference);
        assertEquals(locationReference.getID(), "1");
        assertEquals(locationReference.getLocationType(), LocationType.LINE_LOCATION);
        assertTrue(locationReference.isValid());
        assertEquals(locationReference.getDataClass(), LocationReferenceData.class);

        Object locationReferenceData = locationReference.getLocationReferenceData();

        assertNotNull(locationReferenceData);
        assertTrue(locationReferenceData instanceof LocationReferenceData);

        LocationReferenceData data = (LocationReferenceData) locationReferenceData;

        assertTrue(data.hasLineLocationReference());

        LineLocationReference lineLocationReference = data.getLineLocationReference();

        assertEquals(lineLocationReference.getLocationReferencePointsCount(), 3);

        openlr.proto.schema.LocationReferencePoint firstLrp = lineLocationReference.getLocationReferencePoints(0);

        assertTrue(firstLrp.hasCoordinates());

        Coordinates firstCoordinates = firstLrp.getCoordinates();
        assertEquals(firstCoordinates.getLongitude(),5.7);
        assertEquals(firstCoordinates.getLatitude(), 53.5);

        assertTrue(firstLrp.hasLineAttributes());

        LineAttributes firstLineAttributes = firstLrp.getLineAttributes();
        assertEquals(firstLineAttributes.getBearing(), 15);
        assertEquals(firstLineAttributes.getFrc(), FRC.FRC_3);
        assertEquals(firstLineAttributes.getFow(), FOW.FOW_MULTIPLE_CARRIAGEWAY);

        assertTrue(firstLrp.hasPathAttributes());

        PathAttributes firstPathAttributes = firstLrp.getPathAttributes();
        assertEquals(firstPathAttributes.getDistanceToNext(), 200);
        assertEquals(firstPathAttributes.getLowestFrcAlongPath(), FRC.FRC_4);

        openlr.proto.schema.LocationReferencePoint secondLrp = lineLocationReference.getLocationReferencePoints(1);

        assertTrue(secondLrp.hasCoordinates());

        Coordinates secondCoordinates = secondLrp.getCoordinates();
        assertEquals(secondCoordinates.getLongitude(),5.8);
        assertEquals(secondCoordinates.getLatitude(), 53.6);

        assertTrue(secondLrp.hasLineAttributes());

        LineAttributes secondLineAttributes = secondLrp.getLineAttributes();
        assertEquals(secondLineAttributes.getBearing(), 20);
        assertEquals(secondLineAttributes.getFrc(), FRC.FRC_3);
        assertEquals(secondLineAttributes.getFow(), FOW.FOW_MULTIPLE_CARRIAGEWAY);

        assertTrue(secondLrp.hasPathAttributes());

        PathAttributes secondPathAttributes = secondLrp.getPathAttributes();
        assertEquals(secondPathAttributes.getDistanceToNext(), 100);
        assertEquals(secondPathAttributes.getLowestFrcAlongPath(), FRC.FRC_4);

        openlr.proto.schema.LocationReferencePoint lastLrp = lineLocationReference.getLocationReferencePoints(2);

        assertTrue(lastLrp.hasCoordinates());

        Coordinates lastCoordinates = lastLrp.getCoordinates();
        assertEquals(lastCoordinates.getLongitude(),5.6);
        assertEquals(lastCoordinates.getLatitude(), 53.6);

        assertTrue(lastLrp.hasLineAttributes());

        LineAttributes lastLineAttributes = lastLrp.getLineAttributes();
        assertEquals(lastLineAttributes.getBearing(), 165);
        assertEquals(lastLineAttributes.getFrc(), FRC.FRC_4);
        assertEquals(lastLineAttributes.getFow(), FOW.FOW_MULTIPLE_CARRIAGEWAY);

        assertFalse(lastLrp.hasPathAttributes());

        assertEquals(lineLocationReference.getPositiveOffset(), 20);
        assertEquals(lineLocationReference.getNegativeOffset(), 10);
    }
}
