package openlr.proto.impl;

import openlr.map.GeoCoordinates;

public class GeoCoordinatesProtoImpl implements GeoCoordinates {
    private final double longitude;
    private final double latitude;

    public GeoCoordinatesProtoImpl(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }


    @Override
    public double getLongitudeDeg() {
        return longitude;
    }

    @Override
    public double getLatitudeDeg() {
        return latitude;
    }
}
