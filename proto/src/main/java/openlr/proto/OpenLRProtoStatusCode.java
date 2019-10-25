package openlr.proto;

import openlr.StatusCode;

public enum OpenLRProtoStatusCode implements StatusCode {
    INVALID_DATA_TYPE,
    UNSUPPORTED_LOCATION_TYPE,
    MISSING_LOCATION_REFERENCE,
    INVALID_LOCATION_REFERENCE;

    @Override
    public int getID() {
        return ordinal();
    }
}
