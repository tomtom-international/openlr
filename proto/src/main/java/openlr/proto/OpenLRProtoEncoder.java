package openlr.proto;

import openlr.LocationReference;
import openlr.PhysicalEncoder;
import openlr.proto.encoder.LocationReferenceEncoder;
import openlr.proto.encoder.LocationTypeEncoderRegistry;
import openlr.proto.impl.LocationReferenceProtoImpl;
import openlr.proto.schema.LocationReferenceData;
import openlr.rawLocRef.RawLocationReference;

public class OpenLRProtoEncoder implements PhysicalEncoder {
    private LocationTypeEncoderRegistry locationTypeEncoderRegistry = LocationTypeEncoderRegistry.create();

    @Override
    public LocationReference encodeData(RawLocationReference rawLocRef) {
        LocationReferenceEncoder locationReferenceEncoder = locationTypeEncoderRegistry.getEncoder(rawLocRef.getLocationType());

        if (locationReferenceEncoder == null) {
            return new LocationReferenceProtoImpl(
                    rawLocRef.getID(),
                    rawLocRef.getLocationType(),
                    OpenLRProtoStatusCode.UNSUPPORTED_LOCATION_TYPE);
        }

        try {
            return locationReferenceEncoder.encode(rawLocRef);
        }
        catch (OpenLRProtoException e) {
            return new LocationReferenceProtoImpl(
                    rawLocRef.getID(),
                    rawLocRef.getLocationType(),
                    e.getErrorCode());
        }
    }

    @Override
    public LocationReference encodeData(RawLocationReference rawLocRef, int version) {
        return encodeData(rawLocRef);
    }

    @Override
    public int[] getSupportedVersions() {
        return new int[] { OpenLRProtoConstants.VERSION };
    }

    @Override
    public String getDataFormatIdentifier() {
        return OpenLRProtoConstants.DATA_FORMAT_IDENTIFIER;
    }

    @Override
    public Class<?> getDataClass() {
        return LocationReferenceData.class;
    }
}
