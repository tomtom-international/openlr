package openlr.proto.encoder;

import openlr.LocationReference;
import openlr.LocationType;
import openlr.map.GeoCoordinates;
import openlr.proto.OpenLRProtoException;
import openlr.proto.OpenLRProtoStatusCode;
import openlr.proto.impl.LocationReferenceProtoImpl;
import openlr.proto.schema.Coordinates;
import openlr.proto.schema.GeoCoordinatesLocationReference;
import openlr.proto.schema.LocationReferenceData;
import openlr.rawLocRef.RawLocationReference;

public class GeoCoordinatesEncoder implements LocationReferenceEncoder {
    @Override
    public LocationReference encode(RawLocationReference rawLocationReference) throws OpenLRProtoException {
        if (rawLocationReference.getLocationType() != LocationType.GEO_COORDINATES) {
            throw new OpenLRProtoException(OpenLRProtoStatusCode.INVALID_LOCATION_REFERENCE);
        }

        GeoCoordinates geoCoordinates = rawLocationReference.getGeoCoordinates();

        Coordinates coordinates = Coordinates.newBuilder()
                .setLongitude(geoCoordinates.getLongitudeDeg())
                .setLatitude(geoCoordinates.getLatitudeDeg())
                .build();

        GeoCoordinatesLocationReference geoCoordinatesLocationReference = GeoCoordinatesLocationReference.newBuilder()
                .setCoordinates(coordinates)
                .build();

        LocationReferenceData locationReferenceData = LocationReferenceData.newBuilder()
                .setGeoCoordinatesLocationReference(geoCoordinatesLocationReference)
                .build();

        return new LocationReferenceProtoImpl(rawLocationReference.getID(), LocationType.GEO_COORDINATES, locationReferenceData);
    }
}
