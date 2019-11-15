package openlr.map.utils;

import openlr.map.GeoCoordinates;
import openlr.map.Line;


import java.util.Iterator;
import java.util.function.Function;

public class BearingDestinationRouting {
    private Line lrpLine;
    private int bearingDistance;
    private int projectionAlongLine;

    private BearingDestinationRouting(Line lrpLine, int bearingDistance, int projectionAlongLine) {
        this.lrpLine = lrpLine;
        this.bearingDistance = bearingDistance;
        this.projectionAlongLine = projectionAlongLine;
    }

    public static BearingDestinationRouting withConfig(Line lrpLine, int bearingDistance, int projectionAlongLine) {
        return new BearingDestinationRouting(lrpLine, bearingDistance, projectionAlongLine);
    }

    boolean hasSingleNextLine(Iterator<Line> connectedLine) {
        int counter = 0;
        while (connectedLine.hasNext()) {
            connectedLine.next();
            ++counter;
        }
        return (counter == 1);
    }

    private Iterator<Line> calculateNextLineInRoute(Line line,boolean inLocationDirection){
        if (inLocationDirection) {
            return line.getNextLines();
        } else {
            return line.getPrevLines();
        }
    }

    private GeoCoordinates  calculateBearingDestinationOnLine(Line line,int offset,boolean inLocationDirection){
        if (inLocationDirection) {
            return line.getGeoCoordinateAlongLine(offset);
        } else {
            return line.getGeoCoordinateAlongLine(line.getLineLength() - offset);
        }

    }

    private GeoCoordinates getBearingDestination(Line lrpLine, int lengthCoveredOnLrpLine, boolean inLocationDirection) {
        Line currentLine = lrpLine;
        int lengthCovered = lengthCoveredOnLrpLine;
        while (bearingDistance >= lengthCovered && hasSingleNextLine(calculateNextLineInRoute(currentLine,inLocationDirection))) {
            currentLine = calculateNextLineInRoute(currentLine,inLocationDirection).next();
            lengthCovered += currentLine.getLineLength();
        }
        if (lengthCovered > bearingDistance) {
            int offset = Math.abs((lengthCovered - bearingDistance) - currentLine.getLineLength());
            return calculateBearingDestinationOnLine(currentLine,offset,inLocationDirection);
        } else if (inLocationDirection) {
            return currentLine.getEndNode().getGeoCoordinates();
        } else {
            return currentLine.getStartNode().getGeoCoordinates();
        }
    }

    public GeoCoordinates calculateBearingDestinationInDirection() {
        return getBearingDestination(lrpLine, lrpLine.getLineLength() - projectionAlongLine, true);
    }

    public GeoCoordinates calculateBearingDestinationAgainstDirection() {
        return getBearingDestination(lrpLine, projectionAlongLine, false);
    }
}
