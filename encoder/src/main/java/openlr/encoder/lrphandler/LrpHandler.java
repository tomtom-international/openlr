package openlr.encoder.lrphandler;

import openlr.OpenLRProcessingException;
import openlr.encoder.data.LocRefPoint;

import java.util.List;

public interface LrpHandler {
    List<LocRefPoint> process(List<LocRefPoint> lrps) throws OpenLRProcessingException;
}

