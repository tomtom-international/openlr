package openlr.proto.encoder;

import openlr.LocationReference;
import openlr.LocationReferencePoint;
import openlr.LocationType;
import openlr.Offsets;
import openlr.location.data.Orientation;
import openlr.location.data.SideOfRoad;
import openlr.proto.OpenLRProtoException;
import openlr.proto.OpenLRProtoStatusCode;
import openlr.proto.impl.LocationReferenceProtoImpl;
import openlr.proto.schema.LocationReferenceData;
import openlr.proto.schema.PointAlongLineLocationReference;
import openlr.rawLocRef.RawLocationReference;

import java.util.List;

public class PointAlongLineEncoder implements LocationReferenceEncoder {
    private final LocationReferencePointEncoder locationReferencePointEncoder;

    PointAlongLineEncoder(LocationReferencePointEncoder locationReferencePointEncoder) {
        this.locationReferencePointEncoder = locationReferencePointEncoder;
    }

    @Override
    public LocationReference encode(RawLocationReference rawLocationReference) throws OpenLRProtoException {
        if (rawLocationReference.getLocationType() != LocationType.POINT_ALONG_LINE) {
            throw new OpenLRProtoException(OpenLRProtoStatusCode.INVALID_LOCATION_REFERENCE);
        }

        List<LocationReferencePoint> locationReferencePoints = rawLocationReference.getLocationReferencePoints();

        if (locationReferencePoints.size() < 2) {
            throw new OpenLRProtoException(OpenLRProtoStatusCode.INVALID_LOCATION_REFERENCE);
        }

        openlr.proto.schema.LocationReferencePoint first = locationReferencePointEncoder.encode(locationReferencePoints.get(0));
        openlr.proto.schema.LocationReferencePoint last = locationReferencePointEncoder.encode(locationReferencePoints.get(locationReferencePoints.size() - 1));

        Offsets offsets = rawLocationReference.getOffsets();
        int positiveLength = locationReferencePoints.get(0).getDistanceToNext();
        int positiveOffset = offsets.getPositiveOffset(positiveLength);

        openlr.proto.schema.SideOfRoad sideOfRoad = map(rawLocationReference.getSideOfRoad());
        openlr.proto.schema.Orientation orientation = map(rawLocationReference.getOrientation());

        PointAlongLineLocationReference pointAlongLineLocationReference = PointAlongLineLocationReference.newBuilder()
                .setFirst(first)
                .setLast(last)
                .setPositiveOffset(positiveOffset)
                .setSideOfRoad(sideOfRoad)
                .setOrientation(orientation)
                .build();

        LocationReferenceData locationReferenceData = LocationReferenceData.newBuilder()
                .setPointAlongLineLocationReference(pointAlongLineLocationReference)
                .build();

        return new LocationReferenceProtoImpl(rawLocationReference.getID(), LocationType.POINT_ALONG_LINE, locationReferenceData);
    }

    private openlr.proto.schema.SideOfRoad map(SideOfRoad sideOfRoad) {
        switch (sideOfRoad) {
            case ON_ROAD_OR_UNKNOWN:
                return openlr.proto.schema.SideOfRoad.SIDE_OF_ROAD_ON_ROAD_OR_UNKNOWN;
            case RIGHT:
                return openlr.proto.schema.SideOfRoad.SIDE_OF_ROAD_RIGHT;
            case LEFT:
                return openlr.proto.schema.SideOfRoad.SIDE_OF_ROAD_LEFT;
            case BOTH:
                return openlr.proto.schema.SideOfRoad.SIDE_OF_ROAD_BOTH;
            default:
                throw new IllegalStateException();
        }
    }

    private openlr.proto.schema.Orientation map(Orientation orientation) {
        switch (orientation) {
            case NO_ORIENTATION_OR_UNKNOWN:
                return openlr.proto.schema.Orientation.ORIENTATION_NO_ORIENTATION_OR_UNKNOWN;
            case WITH_LINE_DIRECTION:
                return openlr.proto.schema.Orientation.ORIENTATION_WITH_LINE_DIRECTION;
            case AGAINST_LINE_DIRECTION:
                return openlr.proto.schema.Orientation.ORIENTATION_AGAINST_LINE_DIRECTION;
            case BOTH:
                return openlr.proto.schema.Orientation.ORIENTATION_BOTH;
            default:
                throw new IllegalStateException();
        }
    }
}
