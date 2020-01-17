package openlr.encoder.postprocessor;

import openlr.OpenLRProcessingException;
import openlr.encoder.properties.OpenLREncoderProperties;
import openlr.encoder.routesearch.SecondShortestRouteChecker;
import openlr.map.Line;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 1.4.4
 * <h1>Processor to insert intermediate points along the shortest route between adjacent location reference points
 * where there are alternate paths with length under the threshold exist</h1>
 */
public class AlternatePathLrpProcessor extends AbstractLrpProcessor {

    private AlternatePathLrpProcessor(OpenLREncoderProperties properties) {
        this.properties = properties;
    }

    public static AlternatePathLrpProcessor with(OpenLREncoderProperties properties){
        return new AlternatePathLrpProcessor(properties);
    }


    /**
     * @return true if postprocessor is not configured in properties file;
     */
    protected final boolean isNotActive() {
        return !properties.insertLrpAtAlternatePath();
    }

    /**
     *
     * @param route linked list of road segment
     * @return list of indices in the linked list where the alternate path with length under threshold starts.
     * @throws OpenLRProcessingException
     */
    protected List<Integer> determineNewIntermediatePoints(List<Line> route) throws OpenLRProcessingException {
        List<Integer> intermediates = new ArrayList<>();
        SecondShortestRouteChecker checker = SecondShortestRouteChecker.on(route, properties.getAlternatePathRelativeThreshold());
        for (int index = 1; index < route.size(); ++index) {
            if (checker.hasValidDeviationBefore(index)) {
                intermediates.add(index);
            }
        }
        return intermediates;
    }
}
