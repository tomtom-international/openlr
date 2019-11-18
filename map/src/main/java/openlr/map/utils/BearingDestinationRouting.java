package openlr.map.utils;

import openlr.map.GeoCoordinates;
import openlr.map.Line;


import java.util.Iterator;

/**
 * @since 1.4.4
 * Find the point(Bearing destination) along the road network which is used to estimate the bearing of the LRP
 */
public class BearingDestinationRouting {
    private Line lrpLine;
    private int bearingDistance;
    private int projectionAlongLine;

    /**
     * @param lrpLine             line on the location on which LRP is placed
     * @param bearingDistance     maximum distance to the bearing destination from LRP
     * @param projectionAlongLine distance from the start node of the point on line to which the LRP is projected on
     */
    private BearingDestinationRouting(Line lrpLine, int bearingDistance, int projectionAlongLine) {
        this.lrpLine = lrpLine;
        this.bearingDistance = bearingDistance;
        this.projectionAlongLine = projectionAlongLine;
    }

    /**
     * @param lrpLine             line on the location on which LRP is placed
     * @param bearingDistance     maximum distance to the bearing destination from LRP
     * @param projectionAlongLine distance from the start node of the point on line to which the LRP is projected on
     * @return BearingDestinationRouting
     */
    public static BearingDestinationRouting withConfig(Line lrpLine, int bearingDistance, int projectionAlongLine) {
        return new BearingDestinationRouting(lrpLine, bearingDistance, projectionAlongLine);
    }

    /**
     * Verify the relevant end (Start/End node) of the line is not an intersection
     *
     * @param connectedLine lines connected to relevant end
     * @return true: if there is only one line connected to the node
     * false: if there are more line connected to the node
     */
    private boolean isNotIntersection(Iterator<Line> connectedLine) {
        int counter = 0;
        while (connectedLine.hasNext()) {
            connectedLine.next();
            ++counter;
        }
        return (counter == 1);
    }

    /**
     * Lines connected to the relevant end of the line
     *
     * @param line                parent line
     * @param inLocationDirection true: if bearing direction is same as the overall location direction
     * @return if 'inLocationDirection == true' outgoing lines of the end node
     * else if 'inLocationDirection == false' incoming lines of the start node
     */
    private Iterator<Line> calculateNextLineInRoute(Line line, boolean inLocationDirection) {
        if (inLocationDirection) {
            return line.getNextLines();
        } else {
            return line.getPrevLines();
        }
    }

    /**
     * @param line                line on which bearing destination exist
     * @param offset              distance from the relevant end, based on the location direction, of the line to the bearing destination
     * @param inLocationDirection true: if bearing direction is same as the overall location direction
     * @return bearing destination coordinates
     */
    private GeoCoordinates calculateBearingDestinationOnLine(Line line, int offset, boolean inLocationDirection) {
        if (inLocationDirection) {
            return line.getGeoCoordinateAlongLine(offset);
        } else {
            return line.getGeoCoordinateAlongLine(line.getLineLength() - offset);
        }

    }

    /**
     * @param lrpLine                line on which lrp is placed
     * @param lengthCoveredOnLrpLine distance along the line from the point on the line to which the LRP is projected to the relevant end based on bearing direction
     * @param inLocationDirection    true: if bearing direction is same as the overall location direction
     * @return Coordinates of the bearing destination
     */
    private GeoCoordinates getBearingDestination(Line lrpLine, int lengthCoveredOnLrpLine, boolean inLocationDirection) {
        Line currentLine = lrpLine;
        int lengthCovered = lengthCoveredOnLrpLine;
        while (bearingDistance >= lengthCovered && isNotIntersection(calculateNextLineInRoute(currentLine, inLocationDirection))) {
            currentLine = calculateNextLineInRoute(currentLine, inLocationDirection).next();
            lengthCovered += currentLine.getLineLength();
        }
        if (lengthCovered > bearingDistance) {
            int offset = Math.abs((lengthCovered - bearingDistance) - currentLine.getLineLength());
            return calculateBearingDestinationOnLine(currentLine, offset, inLocationDirection);
        } else if (inLocationDirection) {
            return currentLine.getEndNode().getGeoCoordinates();
        } else {
            return currentLine.getStartNode().getGeoCoordinates();
        }
    }

    /**
     * @return bearing destination for all but last location reference points
     */
    public GeoCoordinates calculateBearingDestinationInDirection() {
        GeoCoordinates bearingDestination = getBearingDestination(lrpLine, lrpLine.getLineLength() - projectionAlongLine, true);
        if (bearingDestination == null) {
            bearingDestination = lrpLine.getEndNode().getGeoCoordinates();
        }
        return bearingDestination;
    }

    /**
     * @return bearing destination for last location reference point
     */
    public GeoCoordinates calculateBearingDestinationAgainstDirection() {
        GeoCoordinates bearingDestination = getBearingDestination(lrpLine, projectionAlongLine, false);
        if (bearingDestination == null) {
            bearingDestination = lrpLine.getStartNode().getGeoCoordinates();
        }
        return bearingDestination;
    }
}
