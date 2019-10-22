package openlr.proto.decoder;

import openlr.LocationReferencePoint;
import openlr.map.FormOfWay;
import openlr.map.FunctionalRoadClass;
import openlr.proto.OpenLRProtoException;
import openlr.proto.OpenLRProtoStatusCode;
import openlr.proto.impl.LocationReferencePointProtoImpl;
import openlr.proto.schema.*;

public class LocationReferencePointDecoder {
    LocationReferencePoint decode(openlr.proto.schema.LocationReferencePoint data, int sequenceNumber, boolean isLast) throws OpenLRProtoException {
        if (!data.hasCoordinates()) {
            throw new OpenLRProtoException(OpenLRProtoStatusCode.INVALID_LOCATION_REFERENCE);
        }

        Coordinates coordinates = data.getCoordinates();
        double longitudeDeg = coordinates.getLongitude();
        double latitudeDeg = coordinates.getLatitude();

        if (!data.hasLineAttributes()) {
            throw new OpenLRProtoException(OpenLRProtoStatusCode.INVALID_LOCATION_REFERENCE);
        }

        LineAttributes lineAttributes = data.getLineAttributes();
        int bearing = lineAttributes.getBearing();
        FunctionalRoadClass frc = decode(lineAttributes.getFrc());
        FormOfWay fow = decode(lineAttributes.getFow());

        if (isLast) {
            return new LocationReferencePointProtoImpl(
                    sequenceNumber,
                    longitudeDeg,
                    latitudeDeg,
                    bearing,
                    frc,
                    fow);
        } else {
            if (!data.hasPathAttributes()) {
                throw new OpenLRProtoException(OpenLRProtoStatusCode.INVALID_LOCATION_REFERENCE);
            }

            PathAttributes pathAttributes = data.getPathAttributes();
            int distanceToNext = pathAttributes.getDistanceToNext();
            FunctionalRoadClass lfrc = decode(pathAttributes.getLowestFrcAlongPath());

            return new LocationReferencePointProtoImpl(
                    sequenceNumber,
                    longitudeDeg,
                    latitudeDeg,
                    bearing,
                    frc,
                    fow,
                    distanceToNext,
                    lfrc);
        }
    }

    private FunctionalRoadClass decode(FRC frc) throws OpenLRProtoException {
        switch (frc) {
            case FRC_0:
                return FunctionalRoadClass.FRC_0;
            case FRC_1:
                return FunctionalRoadClass.FRC_1;
            case FRC_2:
                return FunctionalRoadClass.FRC_2;
            case FRC_3:
                return FunctionalRoadClass.FRC_3;
            case FRC_4:
                return FunctionalRoadClass.FRC_4;
            case FRC_5:
                return FunctionalRoadClass.FRC_5;
            case FRC_6:
                return FunctionalRoadClass.FRC_6;
            case FRC_7:
                return FunctionalRoadClass.FRC_7;
            default:
                throw new OpenLRProtoException(OpenLRProtoStatusCode.INVALID_LOCATION_REFERENCE);
        }
    }

    private FormOfWay decode(FOW fow) throws OpenLRProtoException {
        switch (fow) {
            case FOW_UNDEFINED:
                return FormOfWay.UNDEFINED;
            case FOW_MOTORWAY:
                return FormOfWay.MOTORWAY;
            case FOW_MULTIPLE_CARRIAGEWAY:
                return FormOfWay.MULTIPLE_CARRIAGEWAY;
            case FOW_SINGLE_CARRIAGEWAY:
                return FormOfWay.SINGLE_CARRIAGEWAY;
            case FOW_ROUNDABOUT:
                return FormOfWay.ROUNDABOUT;
            case FOW_TRAFFICSQUARE:
                return FormOfWay.TRAFFIC_SQUARE;
            case FOW_SLIPROAD:
                return FormOfWay.SLIPROAD;
            case FOW_OTHER:
                return FormOfWay.OTHER;
            default:
                throw new OpenLRProtoException(OpenLRProtoStatusCode.INVALID_LOCATION_REFERENCE);
        }
    }
}
