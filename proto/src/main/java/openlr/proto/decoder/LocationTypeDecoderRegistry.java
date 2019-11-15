package openlr.proto.decoder;

import openlr.LocationType;

public class LocationTypeDecoderRegistry {
    public static LocationTypeDecoderRegistry create() {
        LocationReferencePointDecoder locationReferencePointDecoder = new LocationReferencePointDecoder();
        LineDecoder lineDecoder = new LineDecoder(locationReferencePointDecoder);
        GeoCoordinatesDecoder geoCoordinatesDecoder = new GeoCoordinatesDecoder();
        PointAlongLineDecoder pointAlongLineDecoder = new PointAlongLineDecoder(locationReferencePointDecoder);

        return new LocationTypeDecoderRegistry(
                lineDecoder,
                geoCoordinatesDecoder,
                pointAlongLineDecoder);
    }

    private final LineDecoder lineDecoder;
    private final GeoCoordinatesDecoder geoCoordinatesDecoder;
    private final PointAlongLineDecoder pointAlongLineDecoder;

    LocationTypeDecoderRegistry(LineDecoder lineDecoder, GeoCoordinatesDecoder geoCoordinatesDecoder, PointAlongLineDecoder pointAlongLineDecoder) {
        this.lineDecoder = lineDecoder;
        this.geoCoordinatesDecoder = geoCoordinatesDecoder;
        this.pointAlongLineDecoder = pointAlongLineDecoder;
    }

    public LocationReferenceDecoder getDecoder(LocationType locationType) {
        switch (locationType) {
            case LINE_LOCATION:
                return lineDecoder;
            case GEO_COORDINATES:
                return geoCoordinatesDecoder;
            case POINT_ALONG_LINE:
                return pointAlongLineDecoder;
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
