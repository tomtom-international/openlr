package openlr.proto.encoder;

import openlr.LocationType;

public class LocationTypeEncoderRegistry {
    public static LocationTypeEncoderRegistry create() {
        LocationReferencePointEncoder locationReferencePointEncoder = new LocationReferencePointEncoder();
        LineEncoder lineEncoder = new LineEncoder(locationReferencePointEncoder);
        GeoCoordinatesEncoder geoCoordinatesEncoder = new GeoCoordinatesEncoder();
        PointAlongLineEncoder pointAlongLineEncoder = new PointAlongLineEncoder(locationReferencePointEncoder);

        return new LocationTypeEncoderRegistry(lineEncoder, geoCoordinatesEncoder, pointAlongLineEncoder);
    }

    private final LineEncoder lineEncoder;
    private final GeoCoordinatesEncoder geoCoordinatesEncoder;
    private final PointAlongLineEncoder pointAlongLineEncoder;

    LocationTypeEncoderRegistry(LineEncoder lineEncoder, GeoCoordinatesEncoder geoCoordinatesEncoder, PointAlongLineEncoder pointAlongLineEncoder) {
        this.lineEncoder = lineEncoder;
        this.geoCoordinatesEncoder = geoCoordinatesEncoder;
        this.pointAlongLineEncoder = pointAlongLineEncoder;
    }

    public LocationReferenceEncoder getEncoder(LocationType locationType) {
        switch (locationType) {
            case LINE_LOCATION:
                return lineEncoder;
            case GEO_COORDINATES:
                return geoCoordinatesEncoder;
            case POINT_ALONG_LINE:
                return pointAlongLineEncoder;
            case POI_WITH_ACCESS_POINT:
                break;
            case CIRCLE:
                break;
            case POLYGON:
                break;
            case CLOSED_LINE:
                break;
            case RECTANGLE:
                break;
            case GRID:
                break;
            case UNKNOWN:
                return null;
        }
        return null;
    }
}
