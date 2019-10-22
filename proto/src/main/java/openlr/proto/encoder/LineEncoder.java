package openlr.proto.encoder;

import openlr.LocationReference;
import openlr.LocationReferencePoint;
import openlr.LocationType;
import openlr.Offsets;
import openlr.proto.OpenLRProtoException;
import openlr.proto.OpenLRProtoStatusCode;
import openlr.proto.impl.LocationReferenceProtoImpl;
import openlr.proto.schema.LineLocationReference;
import openlr.proto.schema.LocationReferenceData;
import openlr.rawLocRef.RawLocationReference;

import java.util.ArrayList;
import java.util.List;

public class LineEncoder implements LocationReferenceEncoder {
    private final LocationReferencePointEncoder locationReferencePointEncoder;

    LineEncoder(LocationReferencePointEncoder locationReferencePointEncoder) {
        this.locationReferencePointEncoder = locationReferencePointEncoder;
    }

    @Override
    public LocationReference encode(RawLocationReference rawLocationReference) throws OpenLRProtoException {
        if (rawLocationReference.getLocationType() != LocationType.LINE_LOCATION) {
            throw new OpenLRProtoException(OpenLRProtoStatusCode.INVALID_LOCATION_REFERENCE);
        }

        List<LocationReferencePoint> locationReferencePoints = rawLocationReference.getLocationReferencePoints();

        if (locationReferencePoints.size() < 2) {
            throw new OpenLRProtoException(OpenLRProtoStatusCode.INVALID_LOCATION_REFERENCE);
        }

        List<openlr.proto.schema.LocationReferencePoint> lrps = new ArrayList<>();

        for (LocationReferencePoint locationReferencePoint : locationReferencePoints) {
            openlr.proto.schema.LocationReferencePoint lrp = locationReferencePointEncoder.encode(locationReferencePoint);
            lrps.add(lrp);
        }

        Offsets offsets = rawLocationReference.getOffsets();

        int positiveLength = locationReferencePoints.get(0).getDistanceToNext();
        int positiveOffset = offsets.getPositiveOffset(positiveLength);

        int negativeLength = locationReferencePoints.get(locationReferencePoints.size() - 2).getDistanceToNext();
        int negativeOffset = offsets.getNegativeOffset(negativeLength);

        LineLocationReference lineLocationReference = LineLocationReference.newBuilder()
                .addAllLocationReferencePoints(lrps)
                .setPositiveOffset(positiveOffset)
                .setNegativeOffset(negativeOffset)
                .build();

        LocationReferenceData locationReferenceData = LocationReferenceData.newBuilder()
                .setLineLocationReference(lineLocationReference)
                .build();

        return new LocationReferenceProtoImpl(rawLocationReference.getID(), LocationType.LINE_LOCATION, locationReferenceData);
    }
}
