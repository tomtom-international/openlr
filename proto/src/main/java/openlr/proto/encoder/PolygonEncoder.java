package openlr.proto.encoder;

import openlr.LocationReference;
import openlr.LocationType;
import openlr.map.GeoCoordinates;
import openlr.proto.OpenLRProtoException;
import openlr.proto.OpenLRProtoStatusCode;
import openlr.proto.impl.LocationReferenceProtoImpl;
import openlr.proto.schema.Coordinates;
import openlr.proto.schema.LocationReferenceData;
import openlr.proto.schema.PolygonLocationReference;
import openlr.rawLocRef.RawLocationReference;

import java.util.ArrayList;
import java.util.List;

public class PolygonEncoder implements LocationReferenceEncoder {
    @Override
    public LocationReference encode(RawLocationReference rawLocationReference) throws OpenLRProtoException {
        if (rawLocationReference.getLocationType() != LocationType.POLYGON) {
            throw new OpenLRProtoException(OpenLRProtoStatusCode.INVALID_LOCATION_REFERENCE);
        }

        List<Coordinates> coordinates = new ArrayList<>();

        for (GeoCoordinates cornerPoint : rawLocationReference.getCornerPoints()) {
            Coordinates c = Coordinates.newBuilder()
                    .setLongitude(cornerPoint.getLongitudeDeg())
                    .setLatitude(cornerPoint.getLatitudeDeg())
                    .build();
            coordinates.add(c);
        }

        PolygonLocationReference polygonLocationReference = PolygonLocationReference.newBuilder()
                .addAllCoordinates(coordinates)
                .build();

        LocationReferenceData locationReferenceData = LocationReferenceData.newBuilder()
                .setPolygonLocationReference(polygonLocationReference)
                .build();

        return new LocationReferenceProtoImpl(rawLocationReference.getID(), LocationType.POLYGON, locationReferenceData);
    }
}
