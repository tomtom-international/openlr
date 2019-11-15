package openlr.proto.impl;

import openlr.LocationReferencePoint;
import openlr.map.FormOfWay;
import openlr.map.FunctionalRoadClass;

public class LocationReferencePointProtoImpl implements LocationReferencePoint {
    private final int sequenceNumber;
    private final double longitudeDeg;
    private final double latitudeDeg;
    private final double bearing;
    private final FunctionalRoadClass frc;
    private final FormOfWay fow;
    private final int distanceToNext;
    private final FunctionalRoadClass lfrc;
    private final boolean lastLrp;

    public LocationReferencePointProtoImpl(int sequenceNumber, double longitudeDeg, double latitudeDeg, double bearing, FunctionalRoadClass frc, FormOfWay fow, int distanceToNext, FunctionalRoadClass lfrc, boolean lastLrp) {
        this.sequenceNumber = sequenceNumber;
        this.longitudeDeg = longitudeDeg;
        this.latitudeDeg = latitudeDeg;
        this.bearing = bearing;
        this.frc = frc;
        this.fow = fow;
        this.distanceToNext = distanceToNext;
        this.lfrc = lfrc;
        this.lastLrp = lastLrp;
    }

    public LocationReferencePointProtoImpl(int sequenceNumber, double longitudeDeg, double latitudeDeg, double bearing, FunctionalRoadClass frc, FormOfWay fow, int distanceToNext, FunctionalRoadClass lfrc) {
        this(sequenceNumber, longitudeDeg, latitudeDeg, bearing, frc, fow, distanceToNext, lfrc, false);
    }

    public LocationReferencePointProtoImpl(int sequenceNumber, double longitudeDeg, double latitudeDeg, double bearing, FunctionalRoadClass frc, FormOfWay fow) {
        this(sequenceNumber, longitudeDeg, latitudeDeg, bearing, frc, fow, 0, null, true);
    }

    @Override
    public int getSequenceNumber() {
        return sequenceNumber;
    }

    @Override
    public double getLongitudeDeg() {
        return longitudeDeg;
    }

    @Override
    public double getLatitudeDeg() {
        return latitudeDeg;
    }

    @Override
    public double getBearing() {
        return bearing;
    }

    @Override
    public FunctionalRoadClass getFRC() {
        return frc;
    }

    @Override
    public FormOfWay getFOW() {
        return fow;
    }

    @Override
    public boolean isLastLRP() {
        return lastLrp;
    }


    @Override
    public int getDistanceToNext() {
        return distanceToNext;
    }

    @Override
    public FunctionalRoadClass getLfrc() {
        return lfrc;
    }
}
