package openlr.encoder.routesearch;

import openlr.encoder.OpenLREncoderProcessingException;
import openlr.map.GeoCoordinates;
import openlr.map.Line;
import openlr.map.utils.GeometryUtils;
import openlr.map.utils.PQElem;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @since 1.4.4
 * <h1>Verifying the existence of Fully or partially joined second shortest route with length less than relative threshold</h1>
 */
public class SecondShortestRouteChecker {
    private GeoCoordinates destination;
    private List<? extends Line> location;
    private int maxLengthAllowed;
    private double relativeThreshold;

    private SecondShortestRouteChecker(GeoCoordinates destination, List<? extends Line> location, int maxLengthAllowed, double relativeThreshold) {
        this.destination = destination;
        this.location = location;
        this.maxLengthAllowed = maxLengthAllowed;
        this.relativeThreshold = relativeThreshold;
    }

    /**
     * @param location          route
     * @param relativeThreshold threshold relative to the route length
     * @return Instance of SecondShortestRouteChecker for the location with threshold
     * @throws OpenLREncoderProcessingException
     */
    public static SecondShortestRouteChecker on(List<? extends Line> location, double relativeThreshold) throws OpenLREncoderProcessingException {
        if (location.isEmpty()) {
            throw new OpenLREncoderProcessingException(OpenLREncoderProcessingException.EncoderProcessingError.NO_ROUTE_FOUND_ERROR);
        }
        GeoCoordinates destinationStart = location.get(location.size() - 1).getStartNode().getGeoCoordinates();
        int locationLength = location.stream().mapToInt(Line::getLineLength).sum();

        int maxLengthAllowed = locationLength + (int) (locationLength * relativeThreshold);

        return new SecondShortestRouteChecker(destinationStart, location, maxLengthAllowed, relativeThreshold);
    }

    /**
     * @param lengthAlongSecondShortestRoute length of the second shortest route
     * @param lengthAlongLocation            length of the route along location to which the second shortest route gives alternative to
     * @return true: if the length of the second shortest route is not greater than route along location by relative threshold percentage
     * false: if the length of the second shortest route is greater than route along location by relative threshold percentage
     */
    private boolean isUnderThreshold(int lengthAlongSecondShortestRoute, int lengthAlongLocation) {
        return (lengthAlongSecondShortestRoute < (lengthAlongLocation + (int) (lengthAlongLocation * relativeThreshold)));
    }

    /**
     * @param length heuristic length
     * @return true  if the heuristic is less than the maximum  length
     * false if the heuristic is greater than the maximum  length
     */
    private boolean networkLengthFilter(Integer length) {
        return (length < maxLengthAllowed);
    }

    /**
     * @param lastLineIndex index of the road segment in the route
     * @return length along the location from start till the road segment at the lastLineIndex
     */
    private int lengthAlongLocation(int lastLineIndex) {
        int routeLength = 0;
        for (int index = 0; index <= lastLineIndex; ++index) {
            routeLength += location.get(index).getLineLength();
        }
        return routeLength;
    }

    /**
     * @param index index of the road segment in the location between the two location reference points
     * @return true if an alternate path of length under the the relative threshold exist
     * false if no alternate path of length under the the relative threshold exist
     */
    public boolean hasValidDeviationBefore(int index) {
        if (index <= 0 || index >= location.size() - 1) {
            return false;
        }
        Set<Long> closedSet = new HashSet<>();
        closedSet.add(location.get(index).getID());
        RouteSearchData data = new RouteSearchData();
        Line parentLine = location.get(index - 1);
        int lengthCoveredByParentLine = lengthAlongLocation(index - 1);
        int heuristics = lengthCoveredByParentLine + (int) GeometryUtils.distance(parentLine.getEndNode().getGeoCoordinates(),
                destination);
        PQElem parent = new PQElem(parentLine, heuristics, lengthCoveredByParentLine, null);
        data.addToOpen(parent);
        return isSecondShortestRouteUnderThreshold(closedSet, data, index);
    }

    /**
     * @param parentRoadElement    parent road segment
     * @param closedRoadSegmentSet Set of road segments to skip
     * @return list of successor road segments
     */
    private List<Line> calculateAcceptableSuccessors(PQElem parentRoadElement, Set<Long> closedRoadSegmentSet) {
        List<Line> lines = new ArrayList<>();
        parentRoadElement.getLine().getNextLines().forEachRemaining(lines::add);
        return lines.stream()
                .filter(line -> !closedRoadSegmentSet.contains(line.getID()))
                .filter(line -> {
                    int newDist = parentRoadElement.getSecondVal() + line.getLineLength() + (int) GeometryUtils.distance(line.getEndNode().getGeoCoordinates(), destination);
                    return networkLengthFilter(newDist);
                })
                .collect(Collectors.toList());
    }

    /**
     * @param data     priority queue list
     * @param children list of successor road segment
     * @param parent   parent road segment
     */
    private void updateRouteSearchData(RouteSearchData data, List<Line> children, PQElem parent) {
        for (Line child : children) {

            int distanceCoveredByChild = parent.getSecondVal() + child.getLineLength();
            int heuristics = distanceCoveredByChild + (int) GeometryUtils.distance(child.getEndNode().getGeoCoordinates(),
                    destination);
            if (!data.hasLengthValue(child)) {
                data.addToOpen(new PQElem(child, heuristics, distanceCoveredByChild, parent));
            } else if (data.getLengthValue(child) < distanceCoveredByChild) {
                PQElem newElem = new PQElem(child, heuristics, distanceCoveredByChild, parent);
                data.updateInOpen(newElem);
            }
        }
    }

    /**
     * length of the alternate path
     *
     * @param destination road segment where the alternate path join back to the actual location
     * @param index       index of the road segment where alternate path starts
     * @return length of the alternate bathe between start and route segments of alternate path.
     */
    private int calculateLengthOfSecondShortestRoute(final PQElem destination, final int index) {
        long deviationStart = location.get(index - 1).getID();
        int secondShortestRouteLength = 0;
        PQElem to = destination;
        while (to.getPrevious() != null && to.getPrevious().getLine().getID() != deviationStart) {
            secondShortestRouteLength += to.getPrevious().getLine().getLineLength();
            to = to.getPrevious();
        }
        return secondShortestRouteLength;
    }

    /**
     * @param closedSet       set of road segments which need not be explored
     * @param routeSearchData priority queue
     * @param index           index of the road segment along the location where the second shortest route deviates from shortest route.
     * @return true: if an alternate path with length less than the threshold exist
     * false: if no alternate path with length less than the threshold exist
     */
    private boolean isSecondShortestRouteUnderThreshold(Set<Long> closedSet, RouteSearchData routeSearchData, int index) {
        List<Long> possibleDestinations = location.stream().map(Line::getID).collect(Collectors.toList()).subList(index + 1, location.size());
        while (!routeSearchData.isOpenEmpty()) {
            PQElem parent = routeSearchData.pollElement();
            if (possibleDestinations.contains(parent.getLine().getID())) {
                int destinationIndexOnLocation = location.subList(index, location.size()).indexOf(parent.getLine());
                int subLocationLength = location.subList(index, destinationIndexOnLocation + index).stream().mapToInt(Line::getLineLength).sum();
                int secondShortestRouteLength = calculateLengthOfSecondShortestRoute(parent, index);
                return isUnderThreshold(secondShortestRouteLength, subLocationLength);
            } else {
                List<Line> children = calculateAcceptableSuccessors(parent, closedSet);
                updateRouteSearchData(routeSearchData, children, parent);
                closedSet.add(parent.getLine().getID());
            }
        }
        return false;
    }
}