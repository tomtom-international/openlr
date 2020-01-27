package openlr.encoder.postprocessor;

import openlr.OpenLRProcessingException;
import openlr.encoder.properties.OpenLREncoderProperties;
import openlr.map.GeoCoordinates;
import openlr.map.Line;
import openlr.map.Node;
import openlr.map.utils.GeometryUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RoadForkProcessor extends AbstractLrpProcessor {

    private RoadForkProcessor(OpenLREncoderProperties properties) {
        this.properties = properties;
    }

    public static RoadForkProcessor with(OpenLREncoderProperties properties) {
        return new RoadForkProcessor(properties);
    }

    private double getAirlineDistanceViaStartNodeOfLine(GeoCoordinates source, Line line, GeoCoordinates destination) {
        GeoCoordinates startNodePosition = line.getStartNode().getGeoCoordinates();
        double sourceToNode = GeometryUtils.distance(source, startNodePosition);
        double nodeToDestination = GeometryUtils.distance(startNodePosition, destination);
        return sourceToNode + nodeToDestination;
    }

    private boolean isOffRampPossible(double bearingOfLrpLine, List<Line> siblingsOfLrpLine) {

        for (Line line : siblingsOfLrpLine) {
            double bearingOfSiblingLine = GeometryUtils.calculateLineBearing(line, GeometryUtils.BearingDirection.IN_DIRECTION, this.properties.getBearingDistance(), 0);
            double difference = GeometryUtils.bearingDifference(bearingOfLrpLine, bearingOfSiblingLine);

            if (difference <= this.properties.getSiblingBearingLimit()) {
                return true;
            }
        }

        return false;
    }

    /**
     * @return true if postprocessor is not configured in properties file;
     */
    protected final boolean isActive() {
        return properties.insertLrpToAvoidOffRamp();
    }

    public List<Integer> determineNewIntermediatePoints(List<Line> route, Line firstLineOfNextLrp, boolean lastLRP) throws OpenLRProcessingException {
        List<Integer> intermediates = new ArrayList<>();

        if (route.isEmpty()) {
            return intermediates;
        }

        Line lrpLine = route.get(0);
        double bearingOfLrpLine = GeometryUtils.calculateLineBearing(lrpLine, GeometryUtils.BearingDirection.IN_DIRECTION, this.properties.getBearingDistance(), 0);
        Node startNode = route.get(0).getStartNode();

        List<Line> siblingsOfLrpLine = StreamSupport.stream(Spliterators.spliteratorUnknownSize(startNode.getOutgoingLines(), 0), false)
                .filter(line -> line != lrpLine)
                .collect(Collectors.toList());

        if (!isOffRampPossible(bearingOfLrpLine, siblingsOfLrpLine)) {
            return intermediates;
        }

        GeoCoordinates source = startNode.getGeoCoordinates();
        GeoCoordinates destination = lastLRP ?
                route.get(route.size() - 1).getEndNode().getGeoCoordinates() : firstLineOfNextLrp.getStartNode().getGeoCoordinates();
        Map<Integer, Double> distanceViaNodes = new HashMap<>();

        for (int index = 1; index < route.size(); ++index) {
            Line line = route.get(index);
            double airlineDistanceViaStartNodeOfLine = getAirlineDistanceViaStartNodeOfLine(source, line, destination);
            distanceViaNodes.put(index, airlineDistanceViaStartNodeOfLine);
        }

        Integer intermediatePoint = distanceViaNodes.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .filter(cp -> cp.getValue() != 0)
                .map(cp -> cp.getKey())
                .findFirst()
                .orElse(null);

        if (intermediatePoint != null) {
            intermediates.add(intermediatePoint);
        }
        return intermediates;
    }
}
