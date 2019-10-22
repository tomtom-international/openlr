package openlr.proto.encoder;

import openlr.map.FormOfWay;
import openlr.map.FunctionalRoadClass;
import openlr.proto.impl.LocationReferencePointProtoImpl;
import openlr.proto.schema.*;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class LocationReferencePointEncoderTest {
    LocationReferencePointEncoder locationReferencePointEncoder = new LocationReferencePointEncoder();

    @Test
    public void testEncodeLocationReferencePoint() {
        LocationReferencePointProtoImpl locationReferencePoint = new LocationReferencePointProtoImpl(
                1,
                5.7,
                53.5,
                15.5,
                FunctionalRoadClass.FRC_3,
                FormOfWay.MULTIPLE_CARRIAGEWAY,
                200,
                FunctionalRoadClass.FRC_4);

        LocationReferencePoint lrp = locationReferencePointEncoder.encode(locationReferencePoint);

        assertNotNull(lrp);
        assertTrue(lrp.hasCoordinates());
        assertTrue(lrp.hasLineAttributes());
        assertTrue(lrp.hasPathAttributes());

        Coordinates coordinates = lrp.getCoordinates();
        assertEquals(coordinates.getLongitude(), 5.7);
        assertEquals(coordinates.getLatitude(), 53.5);

        LineAttributes lineAttributes = lrp.getLineAttributes();
        assertEquals(lineAttributes.getBearing(), 15);
        assertEquals(lineAttributes.getFrc(), FRC.FRC_3);
        assertEquals(lineAttributes.getFow(), FOW.FOW_MULTIPLE_CARRIAGEWAY);

        PathAttributes pathAttributes = lrp.getPathAttributes();
        assertEquals(pathAttributes.getDistanceToNext(), 200);
        assertEquals(pathAttributes.getLowestFrcAlongPath(), FRC.FRC_4);
    }

    @Test
    public void testEncodeLastLocationReferencePoint() {
        LocationReferencePointProtoImpl locationReferencePoint = new LocationReferencePointProtoImpl(
                2,
                5.6,
                53.6,
                165,
                FunctionalRoadClass.FRC_4,
                FormOfWay.MULTIPLE_CARRIAGEWAY);

        LocationReferencePoint lrp = locationReferencePointEncoder.encode(locationReferencePoint);

        assertNotNull(lrp);
        assertTrue(lrp.hasCoordinates());
        assertTrue(lrp.hasLineAttributes());
        assertFalse(lrp.hasPathAttributes());

        Coordinates coordinates = lrp.getCoordinates();
        assertEquals(coordinates.getLongitude(), 5.6);
        assertEquals(coordinates.getLatitude(), 53.6);

        LineAttributes lineAttributes = lrp.getLineAttributes();
        assertEquals(lineAttributes.getBearing(), 165);
        assertEquals(lineAttributes.getFrc(), FRC.FRC_4);
        assertEquals(lineAttributes.getFow(), FOW.FOW_MULTIPLE_CARRIAGEWAY);
    }
}
