package openlr.map.utils;

import openlr.map.GeoCoordinates;
import openlr.map.Line;
import openlr.map.MapDatabase;
import openlr.map.teststubs.OpenLRMapDatabaseAdaptor;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

public class BearingDestinationRoutingTest {
    private static MapDatabase mapDatabase = OpenLRMapDatabaseAdaptor.from(OpenLRMapDatabaseAdaptor.class.getClassLoader().getResourceAsStream("teststubs/TestMapStub.xml"));
    private static int bearingDistance = 25;

    @Test
    public void TestBearingDestinationAlongLocationDirection() {
        Line lrpLine = mapDatabase.getLine(3L);
        int projectionAlongLine = 70;
        BearingDestinationRouting bearingDestinationRouting = BearingDestinationRouting.withConfig(lrpLine, bearingDistance, projectionAlongLine);
        GeoCoordinates bearingDestinationInDirection = bearingDestinationRouting.calculateBearingDestinationInDirection();
        int distanceAlongSuccessorLine = bearingDistance - (lrpLine.getLineLength() - projectionAlongLine);
        assertEquals(bearingDestinationInDirection,mapDatabase.getLine(4L).getGeoCoordinateAlongLine(distanceAlongSuccessorLine));
    }

    @Test
    public void TestBearingDestinationAgainstLocationDirection(){
        Line lrpLine = mapDatabase.getLine(4L);
        int projectionAlongLine = 14;
        BearingDestinationRouting bearingDestinationRouting = BearingDestinationRouting.withConfig(lrpLine, bearingDistance, projectionAlongLine);
        GeoCoordinates bearingDestinationAgainstDirection = bearingDestinationRouting.calculateBearingDestinationAgainstDirection();
        Line parentLine = mapDatabase.getLine(3L);
        int distanceAlongPredecessorLine =  parentLine.getLineLength() - (bearingDistance - projectionAlongLine);
        assertEquals(bearingDestinationAgainstDirection,parentLine.getGeoCoordinateAlongLine(distanceAlongPredecessorLine));
    }

    @Test
    public void TestBearingDestinationAtIntersectionInDirection(){
        Line lrpLine = mapDatabase.getLine(1L);
        int projectionAlongLine = 70;
        BearingDestinationRouting bearingDestinationRouting = BearingDestinationRouting.withConfig(lrpLine, bearingDistance, projectionAlongLine);
        assertEquals(bearingDestinationRouting.calculateBearingDestinationInDirection(),lrpLine.getEndNode().getGeoCoordinates());
    }

    @Test
    public void TestBearingDestinationAtIntersectionAgainstDirection(){
        Line lrpLine = mapDatabase.getLine(6L);
        int projectionAlongLine = 5;
        BearingDestinationRouting bearingDestinationRouting = BearingDestinationRouting.withConfig(lrpLine, bearingDistance, projectionAlongLine);
        assertEquals(bearingDestinationRouting.calculateBearingDestinationAgainstDirection(),lrpLine.getStartNode().getGeoCoordinates());
    }

    @Test
    public void TestBearingDestinationAtLrpLineInDirection(){
        Line lrpLine = mapDatabase.getLine(6L);
        BearingDestinationRouting bearingDestinationRouting = BearingDestinationRouting.withConfig(lrpLine, bearingDistance, 0);
        assertEquals(bearingDestinationRouting.calculateBearingDestinationInDirection(),lrpLine.getGeoCoordinateAlongLine(bearingDistance));
    }


    @Test
    public void  TestBearingDestinationAtLrpLineAgainstDirection(){
        Line lrpLine = mapDatabase.getLine(6L);
        BearingDestinationRouting bearingDestinationRouting = BearingDestinationRouting.withConfig(lrpLine, bearingDistance, lrpLine.getLineLength());
        assertEquals(bearingDestinationRouting.calculateBearingDestinationAgainstDirection(),lrpLine.getGeoCoordinateAlongLine(lrpLine.getLineLength()-bearingDistance));
    }


}
