package openlr.proto.encoder;

import openlr.LocationReference;
import openlr.LocationType;
import openlr.Offsets;
import openlr.location.data.Orientation;
import openlr.location.data.SideOfRoad;
import openlr.map.FormOfWay;
import openlr.map.FunctionalRoadClass;
import openlr.proto.OpenLRProtoException;
import openlr.proto.impl.LocationReferencePointProtoImpl;
import openlr.proto.impl.OffsetsProtoImpl;
import openlr.proto.schema.*;
import openlr.rawLocRef.RawLocationReference;
import openlr.rawLocRef.RawPointAlongLocRef;
import org.testng.annotations.Test;

import static org.testng.Assert.*;
import static org.testng.Assert.assertEquals;

public class PointAlongLineEncoderTest {
    PointAlongLineEncoder pointAlongLineEncoder = new PointAlongLineEncoder(new LocationReferencePointEncoder());

    @Test
    public void testEncodeLine() throws OpenLRProtoException {
        LocationReferencePointProtoImpl first = new LocationReferencePointProtoImpl(
                1,
                5.7,
                53.5,
                15.5,
                FunctionalRoadClass.FRC_3,
                FormOfWay.MULTIPLE_CARRIAGEWAY,
                200,
                FunctionalRoadClass.FRC_4);

        LocationReferencePointProtoImpl last = new LocationReferencePointProtoImpl(
                2,
                5.6,
                53.6,
                165,
                FunctionalRoadClass.FRC_4,
                FormOfWay.MULTIPLE_CARRIAGEWAY);

        Offsets offsets = new OffsetsProtoImpl(20);

        RawLocationReference rawLocationReference = new RawPointAlongLocRef("1", first, last, offsets, SideOfRoad.LEFT, Orientation.AGAINST_LINE_DIRECTION);

        LocationReference locationReference = pointAlongLineEncoder.encode(rawLocationReference);

        assertNotNull(locationReference);
        assertEquals(locationReference.getID(), "1");
        assertEquals(locationReference.getLocationType(), LocationType.POINT_ALONG_LINE);
        assertTrue(locationReference.isValid());
        assertEquals(locationReference.getDataClass(), LocationReferenceData.class);

        Object locationReferenceData = locationReference.getLocationReferenceData();

        assertNotNull(locationReferenceData);
        assertTrue(locationReferenceData instanceof LocationReferenceData);

        LocationReferenceData data = (LocationReferenceData) locationReferenceData;

        assertTrue(data.hasPointAlongLineLocationReference());

        PointAlongLineLocationReference pointAlongLineLocationReference = data.getPointAlongLineLocationReference();

        assertTrue(pointAlongLineLocationReference.hasFirst());

        openlr.proto.schema.LocationReferencePoint firstLrp = pointAlongLineLocationReference.getFirst();

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

        assertTrue(pointAlongLineLocationReference.hasLast());

        openlr.proto.schema.LocationReferencePoint lastLrp = pointAlongLineLocationReference.getLast();

        assertTrue(lastLrp.hasCoordinates());

        Coordinates lastCoordinates = lastLrp.getCoordinates();
        assertEquals(lastCoordinates.getLongitude(),5.6);
        assertEquals(lastCoordinates.getLatitude(), 53.6);

        assertTrue(lastLrp.hasLineAttributes());

        LineAttributes lastLineAttributes = lastLrp.getLineAttributes();
        assertEquals(lastLineAttributes.getBearing(), 165);
        assertEquals(lastLineAttributes.getFrc(), FRC.FRC_4);
        assertEquals(lastLineAttributes.getFow(), FOW.FOW_MULTIPLE_CARRIAGEWAY);

        assertEquals(pointAlongLineLocationReference.getPositiveOffset(), 20);
        assertEquals(pointAlongLineLocationReference.getSideOfRoad(), openlr.proto.schema.SideOfRoad.SIDE_OF_ROAD_LEFT);
        assertEquals(pointAlongLineLocationReference.getOrientation(), openlr.proto.schema.Orientation.ORIENTATION_AGAINST_LINE_DIRECTION);
    }
}
