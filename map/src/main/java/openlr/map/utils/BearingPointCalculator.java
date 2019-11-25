package openlr.map.utils;

import openlr.map.GeoCoordinates;
import openlr.map.Line;


import java.util.Iterator;
import java.util.List;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @since 1.4.4
 * Find the point(Bearing destination) along the road network which is used to estimate the bearing of the LRP
 */
public class BearingPointCalculator {
    /**
     * Lines connected to the relevant end of the line
     *
     * @param line                parent line
     * @param inLocationDirection true: if bearing direction is same as the overall location direction
     * @return if 'inLocationDirection == true' outgoing lines of the end node
     * else if 'inLocationDirection == false' incoming lines of the start node
     */
    private List<Line> calculateNextLinesInRoute(Line line, boolean inLocationDirection) {
        Iterator<Line> successors;
        if (inLocationDirection) {
            successors = line.getNextLines();
        } else {
            successors = line.getPrevLines();
        }
        return StreamSupport
                .stream(Spliterators.spliteratorUnknownSize(successors, 0), false)
                .collect(Collectors.toList());
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
    private GeoCoordinates getBearingDestination(Line lrpLine, int lengthCoveredOnLrpLine, boolean inLocationDirection, int bearingDistance) {
        Line currentLine = lrpLine;
        int lengthCovered = lengthCoveredOnLrpLine;

        if (bearingDistance >= lengthCovered) {
            List<Line> successors = calculateNextLinesInRoute(currentLine, inLocationDirection);
            while (bearingDistance >= lengthCovered && successors.size() == 1) {
                currentLine = successors.get(0);
                lengthCovered += currentLine.getLineLength();
                successors = calculateNextLinesInRoute(currentLine, inLocationDirection);
            }
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
    public GeoCoordinates calculateBearingDestinationInDirection(Line lrpLine, int bearingDistance, int projectionAlongLine) {
        return getBearingDestination(lrpLine, lrpLine.getLineLength() - projectionAlongLine, true, bearingDistance);
    }

    /**
     * @return bearing destination for last location reference point
     */
    public GeoCoordinates calculateBearingDestinationAgainstDirection(Line lrpLine, int bearingDistance, int projectionAlongLine) {
        return getBearingDestination(lrpLine, projectionAlongLine, false, bearingDistance);
    }
}
