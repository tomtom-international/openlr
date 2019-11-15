package openlr.proto.encoder;

import openlr.LocationReference;
import openlr.proto.OpenLRProtoException;
import openlr.rawLocRef.RawLocationReference;

public interface LocationReferenceEncoder {
    LocationReference encode(RawLocationReference rawLocationReference) throws OpenLRProtoException;
}
