package openlr.proto;

import openlr.LocationReference;
import openlr.LocationReferencePoint;
import openlr.LocationType;
import openlr.Offsets;
import openlr.location.data.Orientation;
import openlr.location.data.SideOfRoad;
import openlr.map.FormOfWay;
import openlr.map.FunctionalRoadClass;
import openlr.map.GeoCoordinates;
import openlr.proto.impl.GeoCoordinatesProtoImpl;
import openlr.proto.impl.LocationReferencePointProtoImpl;
import openlr.proto.impl.OffsetsProtoImpl;
import openlr.proto.schema.LocationReferenceData;
import openlr.rawLocRef.RawCircleLocRef;
import openlr.rawLocRef.RawGeoCoordLocRef;
import openlr.rawLocRef.RawLineLocRef;
import openlr.rawLocRef.RawPointAlongLocRef;
import openlr.rawLocRef.RawPolygonLocRef;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class OpenLRProtoEncoderTest {
    OpenLRProtoEncoder openLREncoder = new OpenLRProtoEncoder();

    @Test
    public void testLineLocationReference() {
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

        LocationReference locationReference = openLREncoder.encodeData(rawLocationReference);

        assertEquals(locationReference.getID(), "1");
        assertEquals(locationReference.getLocationType(), LocationType.LINE_LOCATION);
        assertTrue(locationReference.isValid());
        assertEquals(locationReference.getDataIdentifier(), "proto");
        assertEquals(locationReference.getDataClass(), LocationReferenceData.class);
        assertNotNull(locationReference.getLocationReferenceData());
    }

    @Test
    public void testGeoCoordinatesLocationReference() {
        GeoCoordinatesProtoImpl geoCoordinates = new GeoCoordinatesProtoImpl(7.3, 52.5);
        RawGeoCoordLocRef rawLocationReference = new RawGeoCoordLocRef("1", geoCoordinates);

        LocationReference locationReference = openLREncoder.encodeData(rawLocationReference);

        assertNotNull(locationReference);
        assertEquals(locationReference.getID(), "1");
        assertEquals(locationReference.getLocationType(), LocationType.GEO_COORDINATES);
        assertTrue(locationReference.isValid());
        assertEquals(locationReference.getDataIdentifier(), "proto");
        assertEquals(locationReference.getDataClass(), LocationReferenceData.class);
    }

    @Test
    public void testPointAlongLineLocationReference() {
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

        LocationReferencePointProtoImpl last = new LocationReferencePointProtoImpl(
                2,
                5.6,
                53.6,
                165,
                FunctionalRoadClass.FRC_4,
                FormOfWay.MULTIPLE_CARRIAGEWAY);

        locationReferencePoints.add(first);
        locationReferencePoints.add(last);

        Offsets offsets = new OffsetsProtoImpl(20);

        RawPointAlongLocRef rawLocationReference = new RawPointAlongLocRef("1", first, last, offsets, SideOfRoad.LEFT, Orientation.BOTH);

        LocationReference locationReference = openLREncoder.encodeData(rawLocationReference);

        assertEquals(locationReference.getID(), "1");
        assertEquals(locationReference.getLocationType(), LocationType.POINT_ALONG_LINE);
        assertTrue(locationReference.isValid());
        assertEquals(locationReference.getDataIdentifier(), "proto");
        assertEquals(locationReference.getDataClass(), LocationReferenceData.class);
        assertNotNull(locationReference.getLocationReferenceData());
    }

    @Test
    public void testPolygonLocationReference() {
        List<GeoCoordinates> geoCoordinates = new ArrayList<>();
        geoCoordinates.add(new GeoCoordinatesProtoImpl(-122.915, 39.045));
        geoCoordinates.add(new GeoCoordinatesProtoImpl(-122.914, 39.046));
        geoCoordinates.add(new GeoCoordinatesProtoImpl(-122.913, 39.047));

        RawPolygonLocRef rawLocationReference = new RawPolygonLocRef("1", geoCoordinates);

        LocationReference locationReference = openLREncoder.encodeData(rawLocationReference);

        assertNotNull(locationReference);
        assertEquals(locationReference.getID(), "1");
        assertEquals(locationReference.getLocationType(), LocationType.POLYGON);
        assertEquals(locationReference.getDataIdentifier(), "proto");
        assertEquals(locationReference.getDataClass(), LocationReferenceData.class);
    }

    @Test
    public void testUnsupportedLocationType() {
        GeoCoordinates geoCoordinates = new GeoCoordinatesProtoImpl(1, 2);
        RawCircleLocRef rawLocationReference = new RawCircleLocRef("1", geoCoordinates, 100);

        LocationReference locationReference = openLREncoder.encodeData(rawLocationReference);

        assertFalse(locationReference.isValid());
        assertEquals(locationReference.getReturnCode(), OpenLRProtoStatusCode.UNSUPPORTED_LOCATION_TYPE);
    }
}
