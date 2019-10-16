package openlr.proto;

import openlr.PhysicalFormatException;
import openlr.StatusCode;

public class OpenLRProtoException extends PhysicalFormatException {
    public OpenLRProtoException(StatusCode err) {
        super(err);
    }
}
