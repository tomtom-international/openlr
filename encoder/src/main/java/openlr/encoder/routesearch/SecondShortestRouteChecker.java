package openlr.encoder.routesearch;

import openlr.encoder.OpenLREncoderProcessingException;
import openlr.map.GeoCoordinates;
import openlr.map.Line;
import openlr.map.utils.GeometryUtils;
import openlr.map.utils.PQElem;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class SecondShortestRouteChecker {
    private GeoCoordinates destination;
    private List<? extends Line> location;
    public BiFunction<Integer, Integer, Boolean> lengthFilter;


    private SecondShortestRouteChecker(GeoCoordinates destination, List<? extends Line> location, BiFunction<Integer, Integer, Boolean> lengthFilter) {
        this.destination = destination;
        this.location = location;
        this.lengthFilter = lengthFilter;
    }


    public static SecondShortestRouteChecker on(List<? extends Line> location, double relativeTolerance) throws OpenLREncoderProcessingException {
        if (location.isEmpty()) {
            throw new OpenLREncoderProcessingException(OpenLREncoderProcessingException.EncoderProcessingError.NO_ROUTE_FOUND_ERROR);
        }
        GeoCoordinates destinationStart = location.get(location.size() - 1).getStartNode().getGeoCoordinates();
        int locationLength = location.stream().mapToInt(Line::getLineLength).sum();

        int maxLengthAllowed = locationLength + (int) (locationLength * relativeTolerance);
        BiFunction<Integer, Integer, Boolean> lengthFilter = (Integer lengthAlongSecondShortestRoute, Integer lengthAlongLocation) -> {
            if (lengthAlongLocation != null) {
                return (lengthAlongSecondShortestRoute < (lengthAlongLocation + (int) (lengthAlongLocation * relativeTolerance)));
            } else {
                return (lengthAlongSecondShortestRoute < maxLengthAllowed);
            }
        };

        return new SecondShortestRouteChecker(destinationStart, location, lengthFilter);
    }


    private int lengthAlongLocation(int lastLineIndex) {
        int routeLength = 0;
        for (int index = 0; index <= lastLineIndex; ++index) {
            routeLength += location.get(index).getLineLength();
        }
        return routeLength;
    }

    public boolean hasValidDeviationBefore(int index) {
        if (index > 0 && index < location.size() - 1) {
            Set<Long> closedSet = new HashSet<>();
            closedSet.add(location.get(index).getID());
            RouteSearchData data = new RouteSearchData();
            Line parentLine = location.get(index - 1);
            int lengthCoveredByParentLine = lengthAlongLocation(index - 1);
            int heuristics = lengthCoveredByParentLine + (int) GeometryUtils.distance(parentLine.getEndNode().getGeoCoordinates(),
                    destination);
            PQElem parent = new PQElem(parentLine, heuristics, lengthCoveredByParentLine, null);
            data.addToOpen(parent);
            return exploreNetwork(closedSet, data, index);
        } else {
            return false;
        }
    }

    private List<Line> getAcceptableSuccessors(PQElem current, Set<Long> closedSet) {
        List<Line> lines = new ArrayList<>();
        current.getLine().getNextLines().forEachRemaining(lines::add);
        return lines.stream()
                .filter(line -> !closedSet.contains(line.getID()))
                .filter(line -> {
                    int newDist = current.getSecondVal() + line.getLineLength() + (int) GeometryUtils.distance(line.getEndNode().getGeoCoordinates(), destination);
                    return lengthFilter.apply(newDist, null);
                })
                .collect(Collectors.toList());
    }


    private void updateRouteSearchData(RouteSearchData data, List<Line> children, PQElem parent) {
        for (Line child : children) {

            int distanceCoveredByChild = parent.getSecondVal() + child.getLineLength();
            int heuristics = distanceCoveredByChild + (int) GeometryUtils.distance(child.getEndNode().getGeoCoordinates(),
                    destination);

            if (data.hasLengthValue(child)) {

                if (data.getLengthValue(child) < distanceCoveredByChild) {
                    PQElem newElem = new PQElem(child, heuristics, distanceCoveredByChild, parent);
                    data.updateInOpen(newElem);
                }
            } else {
                data.addToOpen(new PQElem(child, heuristics, distanceCoveredByChild, parent));

            }
        }
    }

    private int lengthOfSecondShortestRoute(final PQElem destination, final int index) {
        Long deviationStart = location.get(index - 1).getID();
        int secondShortestRouteLength = 0;
        PQElem to = destination;
        while (to.getPrevious() != null && to.getPrevious().getLine().getID() != deviationStart) {
            secondShortestRouteLength += to.getPrevious().getLine().getLineLength();
            to = to.getPrevious();
        }
        return secondShortestRouteLength;
    }

    private boolean exploreNetwork(Set<Long> closedSet, RouteSearchData routeSearchData, int index) {
        List<Long> possibleDestinations = location.stream().map(Line::getID).collect(Collectors.toList()).subList(index + 1, location.size());


        while (!routeSearchData.isOpenEmpty()) {
            PQElem parent = routeSearchData.pollElement();
            if (possibleDestinations.contains(parent.getLine().getID())) {
                int destinationIndexOnLocation = location.subList(index, location.size()).indexOf(parent.getLine());
                int subLocationLength = location.subList(index, destinationIndexOnLocation + index).stream().mapToInt(Line::getLineLength).sum();
                int secondShortestRouteLength = lengthOfSecondShortestRoute(parent, index);
                return lengthFilter.apply(secondShortestRouteLength, subLocationLength);
            } else {
                List<Line> children = getAcceptableSuccessors(parent, closedSet);
                updateRouteSearchData(routeSearchData, children, parent);
                closedSet.add(parent.getLine().getID());
            }
        }
        return false;
    }
}
