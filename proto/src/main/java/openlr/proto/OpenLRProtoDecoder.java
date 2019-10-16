package openlr.proto;

import openlr.LocationReference;
import openlr.LocationType;
import openlr.PhysicalDecoder;
import openlr.PhysicalFormatException;
import openlr.proto.decoder.LocationReferenceDecoder;
import openlr.proto.decoder.LocationTypeDecoderRegistry;
import openlr.proto.schema.LocationReferenceData;
import openlr.rawLocRef.RawLocationReference;

public class OpenLRProtoDecoder implements PhysicalDecoder {
    private final LocationTypeDecoderRegistry locationTypeDecoderRegistry = LocationTypeDecoderRegistry.create();

    @Override
    public Class<?> getDataClass() {
        return LocationReferenceData.class;
    }

    @Override
    public RawLocationReference decodeData(LocationReference data) throws PhysicalFormatException {
        LocationType locationType = data.getLocationType();
        LocationReferenceDecoder locationReferenceDecoder = locationTypeDecoderRegistry.getDecoder(locationType);

        if (locationReferenceDecoder == null) {
            throw new OpenLRProtoException(OpenLRProtoStatusCode.UNSUPPORTED_LOCATION_TYPE);
        }

        Object locationReferenceData = data.getLocationReferenceData();

        if (!(locationReferenceData instanceof LocationReferenceData)) {
            throw new OpenLRProtoException(OpenLRProtoStatusCode.INVALID_DATA_TYPE);
        }

        return locationReferenceDecoder.decode(data.getID(), (LocationReferenceData) locationReferenceData);
    }

    @Override
    public String getDataFormatIdentifier() {
        return OpenLRProtoConstants.DATA_FORMAT_IDENTIFIER;
    }
}
