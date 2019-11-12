package openlr.encoder.postprocessor;

import openlr.OpenLRProcessingException;
import openlr.encoder.data.LocRefPoint;
import openlr.encoder.properties.OpenLREncoderProperties;
import openlr.encoder.routesearch.SecondShortestRouteChecker;
import openlr.map.Line;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Processor to insert intermediate points along the shortest route between adjacent location reference points
 * where there are alternate paths with length under the threshold exist</h1>
 */
public class AlternatePathLrpProcessor implements LrpProcessor {
    private OpenLREncoderProperties properties;

    private AlternatePathLrpProcessor(float alternatePathRelativeThreshold, OpenLREncoderProperties properties) {
        this.properties = properties;
    }

    public static AlternatePathLrpProcessor with(OpenLREncoderProperties properties){
        float alternatePathRelativeThreshold = properties.getAlternatePathRelativeThreshold();
        return new AlternatePathLrpProcessor(alternatePathRelativeThreshold, properties);
    }

    /**
     *
     * @param route linked list of road segment
     * @return list of indices in the linked list where the alternate path with length under threshold starts.
     * @throws OpenLRProcessingException
     */
    private List<Integer> determineNewIntermediatePoints(List<Line> route) throws OpenLRProcessingException {
        List<Integer> intermediates = new ArrayList<>();
        SecondShortestRouteChecker checker = SecondShortestRouteChecker.on(route, properties.getAlternatePathRelativeThreshold());
        for (int index = 1; index < route.size(); ++index) {
            if (checker.hasValidDeviationBefore(index)) {
                intermediates.add(index);
            }
        }
        return intermediates;
    }

    /**
     * @param route route between the adjacent location reference points(including the road segment of the destination lrp)
     * @param lrpPositions indices of the road segment where to insert the intermediate points.
     * @return list of location reference points with intermediate points
     * @throws OpenLRProcessingException
     */
    private List<LocRefPoint> createNewLRPs(List<Line> route, List<Integer> lrpPositions) throws OpenLRProcessingException {
        List<LocRefPoint> revisedLrpList = new ArrayList<>();
        LocRefPoint firstPoint = new LocRefPoint(route.subList(0, lrpPositions.get(0)), properties);
        revisedLrpList.add(firstPoint);
        for (int index = 0; index < lrpPositions.size() - 1; ++index) {
            int from = lrpPositions.get(index);
            int to = lrpPositions.get(index + 1);
            LocRefPoint intermediatePoint = new LocRefPoint(route.subList(from, to), properties);
            revisedLrpList.add(intermediatePoint);
        }
        int from = lrpPositions.get(lrpPositions.size() - 1);
        LocRefPoint lastPoint = new LocRefPoint(route.subList(from, route.size()), properties);
        revisedLrpList.add(lastPoint);
        return revisedLrpList;
    }


    /**
     * Generate a linked list of location reference points where No Partial or fully joined  alternate path of length under the given threshold
     * exist between the road segments of adjacent location reference points
     * @param lrps linked list of estimated location reference points
     * @return linked list of revised lrp point linked list with intermediate points
     * @throws OpenLRProcessingException
     */
    public List<LocRefPoint> process(List<LocRefPoint> lrps) throws OpenLRProcessingException {

        if (properties.insertLrpAtAlternatePath()) {
            List<LocRefPoint> revisedLrpList = new ArrayList<>();
            for (int index = 0; index < lrps.size() - 1; ++index) {
                LocRefPoint lrp = lrps.get(index);
                Line firstLineOfNextLrp = lrps.get(index + 1).getLine();
                List<Line> oldRoute = lrp.getRoute();
                List<Line> connectedRoute = new ArrayList<>();
                for (Line line : oldRoute) {
                    connectedRoute.add(line);
                }
                connectedRoute.add(firstLineOfNextLrp);
                List<Integer> intermediateLrpPositions = determineNewIntermediatePoints(connectedRoute);
                if (intermediateLrpPositions.isEmpty()) {
                    revisedLrpList.add(lrp);
                } else {
                    revisedLrpList.addAll(createNewLRPs(lrp.getRoute(), intermediateLrpPositions));
                }
            }
            revisedLrpList.add(lrps.get(lrps.size() - 1));
            return revisedLrpList;
        } else {
            return lrps;
        }
    }
}
