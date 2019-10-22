package openlr.proto.decoder;

import openlr.proto.OpenLRProtoException;
import openlr.proto.OpenLRProtoStatusCode;
import openlr.proto.impl.GeoCoordinatesProtoImpl;
import openlr.proto.schema.Coordinates;
import openlr.proto.schema.GeoCoordinatesLocationReference;
import openlr.proto.schema.LocationReferenceData;
import openlr.rawLocRef.RawGeoCoordLocRef;
import openlr.rawLocRef.RawLocationReference;

public class GeoCoordinatesDecoder implements LocationReferenceDecoder {
    @Override
    public RawLocationReference decode(String id, LocationReferenceData data) throws OpenLRProtoException {
        if (!data.hasGeoCoordinatesLocationReference()) {
            throw new OpenLRProtoException(OpenLRProtoStatusCode.MISSING_LOCATION_REFERENCE);
        }

        GeoCoordinatesLocationReference geoCoordinatesLocationReference = data.getGeoCoordinatesLocationReference();

        if (!geoCoordinatesLocationReference.hasCoordinates()) {
            throw new OpenLRProtoException(OpenLRProtoStatusCode.INVALID_LOCATION_REFERENCE);
        }

        Coordinates coordinates = geoCoordinatesLocationReference.getCoordinates();
        GeoCoordinatesProtoImpl geoCoordinates = new GeoCoordinatesProtoImpl(coordinates.getLongitude(), coordinates.getLatitude());

        return new RawGeoCoordLocRef(id, geoCoordinates);
    }
}
