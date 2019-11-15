package openlr.proto.decoder;

import openlr.proto.OpenLRProtoException;
import openlr.proto.schema.LocationReferenceData;
import openlr.rawLocRef.RawLocationReference;

public interface LocationReferenceDecoder {
    RawLocationReference decode(String id, LocationReferenceData data) throws OpenLRProtoException;
}
