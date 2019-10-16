package openlr.proto.decoder;

import openlr.LocationReferencePoint;
import openlr.Offsets;
import openlr.proto.OpenLRProtoException;
import openlr.proto.OpenLRProtoStatusCode;
import openlr.proto.impl.OffsetsProtoImpl;
import openlr.proto.schema.LineLocationReference;
import openlr.proto.schema.LocationReferenceData;
import openlr.rawLocRef.RawLineLocRef;
import openlr.rawLocRef.RawLocationReference;

import java.util.ArrayList;
import java.util.List;

public class LineDecoder implements LocationReferenceDecoder {
    private final LocationReferencePointDecoder locationReferencePointDecoder;

    LineDecoder(LocationReferencePointDecoder locationReferencePointDecoder) {
        this.locationReferencePointDecoder = locationReferencePointDecoder;
    }

    @Override
    public RawLocationReference decode(String id, LocationReferenceData data) throws OpenLRProtoException {
        if (!data.hasLineLocationReference()) {
            throw new OpenLRProtoException(OpenLRProtoStatusCode.MISSING_LOCATION_REFERENCE);
        }

        LineLocationReference lineLocationReference = data.getLineLocationReference();

        int lrpCount = lineLocationReference.getLocationReferencePointsCount();

        if (lrpCount < 2) {
            throw new OpenLRProtoException(OpenLRProtoStatusCode.INVALID_LOCATION_REFERENCE);
        }

        List<LocationReferencePoint> locationReferencePoints = new ArrayList<>();

        for (int sequenceNumber = 1; sequenceNumber <= lrpCount; sequenceNumber++) {
            LocationReferencePoint locationReferencePoint = locationReferencePointDecoder.decode(
                    lineLocationReference.getLocationReferencePoints(sequenceNumber - 1),
                    sequenceNumber,
                    sequenceNumber == lrpCount);
            locationReferencePoints.add(locationReferencePoint);
        }

        Offsets offsets = new OffsetsProtoImpl(
                lineLocationReference.getPositiveOffset(),
                lineLocationReference.getNegativeOffset());

        return new RawLineLocRef(id, locationReferencePoints, offsets);
    }
}
