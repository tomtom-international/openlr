package openlr.proto.decoder;

import openlr.LocationReferencePoint;
import openlr.Offsets;
import openlr.location.data.Orientation;
import openlr.location.data.SideOfRoad;
import openlr.proto.OpenLRProtoException;
import openlr.proto.OpenLRProtoStatusCode;
import openlr.proto.impl.OffsetsProtoImpl;
import openlr.proto.schema.LocationReferenceData;
import openlr.proto.schema.PointAlongLineLocationReference;
import openlr.rawLocRef.RawLocationReference;
import openlr.rawLocRef.RawPointAlongLocRef;

public class PointAlongLineDecoder implements LocationReferenceDecoder {
    private final LocationReferencePointDecoder locationReferencePointDecoder;

    PointAlongLineDecoder(LocationReferencePointDecoder locationReferencePointDecoder) {
        this.locationReferencePointDecoder = locationReferencePointDecoder;
    }

    @Override
    public RawLocationReference decode(String id, LocationReferenceData data) throws OpenLRProtoException {
        if (!data.hasPointAlongLineLocationReference()) {
            throw new OpenLRProtoException(OpenLRProtoStatusCode.MISSING_LOCATION_REFERENCE);
        }

        PointAlongLineLocationReference pointAlongLineLocationReference = data.getPointAlongLineLocationReference();

        if (!pointAlongLineLocationReference.hasFirst()) {
            throw new OpenLRProtoException(OpenLRProtoStatusCode.INVALID_LOCATION_REFERENCE);
        }

        LocationReferencePoint first = locationReferencePointDecoder.decode(
                pointAlongLineLocationReference.getFirst(), 1, false);

        if (!pointAlongLineLocationReference.hasLast()) {
            throw new OpenLRProtoException(OpenLRProtoStatusCode.INVALID_LOCATION_REFERENCE);
        }

        LocationReferencePoint last = locationReferencePointDecoder.decode(
                pointAlongLineLocationReference.getLast(), 2, true);

        Offsets offsets = new OffsetsProtoImpl(pointAlongLineLocationReference.getPositiveOffset());

        SideOfRoad sideOfRoad = map(pointAlongLineLocationReference.getSideOfRoad());
        Orientation orientation = map(pointAlongLineLocationReference.getOrientation());

        return new RawPointAlongLocRef(id, first, last, offsets, sideOfRoad, orientation);
    }

    private SideOfRoad map(openlr.proto.schema.SideOfRoad sideOfRoad) throws OpenLRProtoException {
        switch (sideOfRoad) {
            case SIDE_OF_ROAD_ON_ROAD_OR_UNKNOWN:
                return SideOfRoad.ON_ROAD_OR_UNKNOWN;
            case SIDE_OF_ROAD_RIGHT:
                return SideOfRoad.RIGHT;
            case SIDE_OF_ROAD_LEFT:
                return SideOfRoad.LEFT;
            case SIDE_OF_ROAD_BOTH:
                return SideOfRoad.BOTH;
            default:
                throw new OpenLRProtoException(OpenLRProtoStatusCode.INVALID_LOCATION_REFERENCE);
        }
    }

    private Orientation map(openlr.proto.schema.Orientation orientation) throws OpenLRProtoException {
        switch (orientation) {
            case ORIENTATION_NO_ORIENTATION_OR_UNKNOWN:
                return Orientation.NO_ORIENTATION_OR_UNKNOWN;
            case ORIENTATION_WITH_LINE_DIRECTION:
                return Orientation.WITH_LINE_DIRECTION;
            case ORIENTATION_AGAINST_LINE_DIRECTION:
                return Orientation.AGAINST_LINE_DIRECTION;
            case ORIENTATION_BOTH:
                return Orientation.BOTH;
            default:
                throw new OpenLRProtoException(OpenLRProtoStatusCode.INVALID_LOCATION_REFERENCE);
        }
    }
}
