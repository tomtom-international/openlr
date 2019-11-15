package openlr.encoder.postprocessor;

import openlr.OpenLRProcessingException;
import openlr.encoder.data.LocRefPoint;

import java.util.List;

public interface LrpProcessor {
    /**
     * This method adds intermediate points between the adjacent location reference points
     * @param lrps linked list of estimated location reference points
     * @return revised linked list of location reference point with the new intermediate points
     * @throws OpenLRProcessingException
     */
    List<LocRefPoint> process(List<LocRefPoint> lrps) throws OpenLRProcessingException;
}

