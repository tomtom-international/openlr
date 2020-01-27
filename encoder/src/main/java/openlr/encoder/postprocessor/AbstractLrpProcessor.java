package openlr.encoder.postprocessor;

import openlr.OpenLRProcessingException;
import openlr.encoder.data.LocRefPoint;
import openlr.encoder.properties.OpenLREncoderProperties;
import openlr.map.Line;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLrpProcessor implements LrpProcessor {

    protected OpenLREncoderProperties properties;

    /**
     *
     * @param route linked list of road segment
     * @param firstLineOfNextLrp
     * @param lastLRP
     * @return list of indices in the linked list where the alternate path with length under threshold starts.
     * @throws OpenLRProcessingException
     */
    protected abstract List<Integer> determineNewIntermediatePoints(List<Line> route, Line firstLineOfNextLrp, boolean lastLRP) throws OpenLRProcessingException;

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
     * @return true if postprocessor is not configured in properties file;
     */
    protected abstract boolean isActive();



    /**
     * Generate a linked list of location reference points where No Partial or fully joined  alternate path of length under the given threshold
     * exist between the road segments of adjacent location reference points
     * @param lrps linked list of estimated location reference points
     * @return linked list of revised lrp point linked list with intermediate points
     * @throws OpenLRProcessingException
     */
    public List<LocRefPoint> process(List<LocRefPoint> lrps) throws OpenLRProcessingException {

        if (!isActive()) {
            return lrps;
        }

        List<LocRefPoint> revisedLrpList = new ArrayList<>();

        for (int index = 0; index < lrps.size() - 1; ++index) {
            LocRefPoint lrp = lrps.get(index);
            List<Line> lrpRoute = lrp.getRoute();
            LocRefPoint nextLrp = lrps.get(index + 1);
            Line firstLineOfNextLrp = nextLrp.getLine();
            List<Integer> intermediateLrpPositions = determineNewIntermediatePoints(lrpRoute,firstLineOfNextLrp,nextLrp.isLastLRP());
            if (intermediateLrpPositions.isEmpty()) {
                revisedLrpList.add(lrp);
            } else {
                // The source lrp needs to be replaced by a new lrp because the route which it represents
                // is now only till first intermediate point.
                revisedLrpList.addAll(createNewLRPs(lrp.getRoute(), intermediateLrpPositions));
            }
        }

        revisedLrpList.add(lrps.get(lrps.size() - 1));
        return revisedLrpList;
    }
}
