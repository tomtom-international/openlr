package openlr.map.utils;

import openlr.map.GeoCoordinates;
import openlr.map.Line;
import openlr.map.MapDatabase;
import openlr.map.teststubs.OpenLRMapDatabaseAdaptor;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class BearingPointCalculatorTest {
    private static MapDatabase mapDatabase = OpenLRMapDatabaseAdaptor.from(OpenLRMapDatabaseAdaptor.class.getClassLoader().getResourceAsStream("teststubs/TestMapStub.xml"));
    private static int bearingDistance = 25;

    private static MapDatabase multiWalkMapDatabase = OpenLRMapDatabaseAdaptor.from(OpenLRMapDatabaseAdaptor.class.getClassLoader().getResourceAsStream("teststubs/BearingRouteMapStub.xml"));

    private static final BearingPointCalculator bearingPointCalculator = new BearingPointCalculator();


    @Test
    public void testBearingDestinationAlongLocationDirection() {
        Line lrpLine = mapDatabase.getLine(3L); //84
        int projectionAlongLine = 70;
        GeoCoordinates bearingDestinationInDirection = bearingPointCalculator.calculateBearingDestinationInDirection(lrpLine, bearingDistance, projectionAlongLine);
        int distanceAlongSuccessorLine = bearingDistance - (lrpLine.getLineLength() - projectionAlongLine); // 25 - (84-70) = 11
        assertEquals(bearingDestinationInDirection, mapDatabase.getLine(4L).getGeoCoordinateAlongLine(distanceAlongSuccessorLine));
    }

    @Test
    public void testBearingDestinationAgainstLocationDirection() {
        Line lrpLine = mapDatabase.getLine(4L); //101
        int projectionAlongLine = 14;
        GeoCoordinates bearingDestinationAgainstDirection = bearingPointCalculator.calculateBearingDestinationAgainstDirection(lrpLine, bearingDistance, projectionAlongLine);
        Line parentLine = mapDatabase.getLine(3L); //84
        int distanceAlongPredecessorLine = parentLine.getLineLength() - (bearingDistance - projectionAlongLine); // 84 - (25 - 14) = 73
        assertEquals(bearingDestinationAgainstDirection, parentLine.getGeoCoordinateAlongLine(distanceAlongPredecessorLine));
    }

    @Test
    public void testBearingDestinationAtIntersectionInDirection() {
        Line lrpLine = mapDatabase.getLine(1L); //80
        int projectionAlongLine = 70;
        assertEquals(bearingPointCalculator.calculateBearingDestinationInDirection(lrpLine, bearingDistance, projectionAlongLine), lrpLine.getEndNode().getGeoCoordinates()); // (80 - 70) < 25
    }

    @Test
    public void testBearingDestinationAtIntersectionAgainstDirection() {
        Line lrpLine = mapDatabase.getLine(6L); //34
        int projectionAlongLine = 5;
        assertEquals(bearingPointCalculator.calculateBearingDestinationAgainstDirection(lrpLine, bearingDistance, projectionAlongLine), lrpLine.getStartNode().getGeoCoordinates()); //(5 < 25)
    }

    @Test
    public void testBearingDestinationAtLrpLineInDirection() {
        Line lrpLine = mapDatabase.getLine(6L); //34
        assertEquals(bearingPointCalculator.calculateBearingDestinationInDirection(lrpLine, bearingDistance, 0), lrpLine.getGeoCoordinateAlongLine(bearingDistance));
    }


    @Test
    public void testBearingDestinationAtLrpLineAgainstDirection() {
        Line lrpLine = mapDatabase.getLine(6L); //34
        assertEquals(bearingPointCalculator.calculateBearingDestinationAgainstDirection(lrpLine, bearingDistance, lrpLine.getLineLength()), lrpLine.getGeoCoordinateAlongLine(lrpLine.getLineLength() - bearingDistance));
    }

    @Test
    public void testBearingRouteAlongMultipleRoadSegments() {
        Line lrpLine = multiWalkMapDatabase.getLine(1L);
        Line bearingDestination = multiWalkMapDatabase.getLine(6L);
        assertEquals(bearingPointCalculator.calculateBearingDestinationInDirection(lrpLine, 500, 0), bearingDestination.getEndNode().getGeoCoordinates());
    }

    @Test
    public void testBearingRouteAlongMultipleRoadSegmentsInReverseDirection() {
        Line lrpLine = multiWalkMapDatabase.getLine(6L);
        Line bearingDestination = multiWalkMapDatabase.getLine(1L);
        assertEquals(bearingPointCalculator.calculateBearingDestinationAgainstDirection(lrpLine, 500, lrpLine.getLineLength()), bearingDestination.getStartNode().getGeoCoordinates());
    }


}
