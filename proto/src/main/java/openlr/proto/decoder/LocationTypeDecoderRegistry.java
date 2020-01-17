package openlr.proto.decoder;

import openlr.LocationType;

public class LocationTypeDecoderRegistry {
    private final LineDecoder lineDecoder;
    private final GeoCoordinatesDecoder geoCoordinatesDecoder;
    private final PointAlongLineDecoder pointAlongLineDecoder;
    private final PolygonDecoder polygonDecoder;

    public static LocationTypeDecoderRegistry create() {
        LocationReferencePointDecoder locationReferencePointDecoder = new LocationReferencePointDecoder();
        LineDecoder lineDecoder = new LineDecoder(locationReferencePointDecoder);
        GeoCoordinatesDecoder geoCoordinatesDecoder = new GeoCoordinatesDecoder();
        PointAlongLineDecoder pointAlongLineDecoder = new PointAlongLineDecoder(locationReferencePointDecoder);
        PolygonDecoder polygonDecoder = new PolygonDecoder();

        return new LocationTypeDecoderRegistry(
                lineDecoder,
                geoCoordinatesDecoder,
                pointAlongLineDecoder,
                polygonDecoder);
    }

    LocationTypeDecoderRegistry(LineDecoder lineDecoder, GeoCoordinatesDecoder geoCoordinatesDecoder, PointAlongLineDecoder pointAlongLineDecoder, PolygonDecoder polygonDecoder) {
        this.lineDecoder = lineDecoder;
        this.geoCoordinatesDecoder = geoCoordinatesDecoder;
        this.pointAlongLineDecoder = pointAlongLineDecoder;
        this.polygonDecoder = polygonDecoder;
    }

    public LocationReferenceDecoder getDecoder(LocationType locationType) {
        switch (locationType) {
            case LINE_LOCATION:
                return lineDecoder;
            case GEO_COORDINATES:
                return geoCoordinatesDecoder;
            case POINT_ALONG_LINE:
                return pointAlongLineDecoder;
            case POLYGON:
                return polygonDecoder;
            case POI_WITH_ACCESS_POINT:
                break;
            case CIRCLE:
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
