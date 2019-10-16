package openlr.proto.impl;

import openlr.LocationReference;
import openlr.LocationType;
import openlr.StatusCode;
import openlr.proto.OpenLRProtoConstants;
import openlr.proto.schema.LocationReferenceData;

import java.io.IOException;
import java.io.OutputStream;

public class LocationReferenceProtoImpl implements LocationReference {
    private final String id;
    private final LocationType locationType;
    private final LocationReferenceData locationReferenceData;
    private final boolean valid;
    private final StatusCode returnCode;

    public LocationReferenceProtoImpl(String id, LocationType locationType, LocationReferenceData locationReferenceData, boolean valid, StatusCode returnCode) {
        this.id = id;
        this.locationType = locationType;
        this.locationReferenceData = locationReferenceData;
        this.valid = valid;
        this.returnCode = returnCode;
    }

    public LocationReferenceProtoImpl(String id, LocationType locationType, LocationReferenceData locationReferenceData) {
        this(id, locationType, locationReferenceData, true, null);
    }

    public LocationReferenceProtoImpl(String id, LocationType locationType, StatusCode returnCode) {
        this(id, locationType, null, false, returnCode);
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public LocationType getLocationType() {
        return locationType;
    }

    @Override
    public Object getLocationReferenceData() {
        return locationReferenceData;
    }

    @Override
    public boolean isValid() {
        return valid;
    }

    @Override
    public StatusCode getReturnCode() {
        return returnCode;
    }

    @Override
    public Class<?> getDataClass() {
        return LocationReferenceData.class;
    }

    @Override
    public String getDataIdentifier() {
        return OpenLRProtoConstants.DATA_FORMAT_IDENTIFIER;
    }

    @Override
    public void toStream(OutputStream os) throws IOException {
        locationReferenceData.writeTo(os);
    }

    @Override
    public int getVersion() {
        return OpenLRProtoConstants.VERSION;
    }
}
