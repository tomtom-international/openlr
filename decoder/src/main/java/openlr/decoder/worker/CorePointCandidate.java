package openlr.decoder.worker;

import openlr.LocationReferencePoint;
import openlr.decoder.data.CandidateLine;

public class CorePointCandidate {
    private final LocationReferencePoint lrp;
    private final CandidateLine candidateLine;

    public LocationReferencePoint getLrp() {
        return lrp;
    }

    public CandidateLine getCandidateLine() {
        return candidateLine;
    }

    public CorePointCandidate(LocationReferencePoint lrp, CandidateLine candidateLine) {
        this.lrp = lrp;
        this.candidateLine = candidateLine;
    }
}
