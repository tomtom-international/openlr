package openlr.encoder.postprocessor;

import openlr.OpenLRProcessingException;
import openlr.encoder.data.LocRefPoint;

import java.util.List;

public interface LrpProcessor {
    List<LocRefPoint> process(List<LocRefPoint> lrps) throws OpenLRProcessingException;
}

