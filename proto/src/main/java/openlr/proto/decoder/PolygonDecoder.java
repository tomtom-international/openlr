package openlr.proto.decoder;

import openlr.map.GeoCoordinates;
import openlr.proto.OpenLRProtoException;
import openlr.proto.OpenLRProtoStatusCode;
import openlr.proto.impl.GeoCoordinatesProtoImpl;
import openlr.proto.schema.Coordinates;
import openlr.proto.schema.LocationReferenceData;
import openlr.proto.schema.PolygonLocationReference;
import openlr.rawLocRef.RawLocationReference;
import openlr.rawLocRef.RawPolygonLocRef;

import java.util.ArrayList;
import java.util.List;

public class PolygonDecoder implements LocationReferenceDecoder {
    @Override
    public RawLocationReference decode(String id, LocationReferenceData data) throws OpenLRProtoException {
        if (!data.hasPolygonLocationReference()) {
            throw new OpenLRProtoException(OpenLRProtoStatusCode.MISSING_LOCATION_REFERENCE);
        }

        PolygonLocationReference polygonLocationReference = data.getPolygonLocationReference();

        if (polygonLocationReference.getCoordinatesCount() == 0) {
            throw new OpenLRProtoException(OpenLRProtoStatusCode.INVALID_LOCATION_REFERENCE);
        }

        List<GeoCoordinates> cornerPoints = new ArrayList<>();

        for (Coordinates coordinates : polygonLocationReference.getCoordinatesList()) {
            GeoCoordinatesProtoImpl cornerPoint = new GeoCoordinatesProtoImpl(coordinates.getLongitude(), coordinates.getLatitude());
            cornerPoints.add(cornerPoint);
        }

        return new RawPolygonLocRef(id, cornerPoints);
    }
}
