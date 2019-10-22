package openlr.proto.encoder;

import openlr.LocationReferencePoint;
import openlr.map.FormOfWay;
import openlr.map.FunctionalRoadClass;
import openlr.proto.schema.*;

public class LocationReferencePointEncoder {
    public openlr.proto.schema.LocationReferencePoint encode(LocationReferencePoint locationReferencePoint) {
        openlr.proto.schema.LocationReferencePoint.Builder builder = openlr.proto.schema.LocationReferencePoint.newBuilder();

        Coordinates coordinates = Coordinates.newBuilder()
                .setLongitude(locationReferencePoint.getLongitudeDeg())
                .setLatitude(locationReferencePoint.getLatitudeDeg())
                .build();
        builder.setCoordinates(coordinates);

        LineAttributes lineAttributes = LineAttributes.newBuilder()
                .setBearing((int) locationReferencePoint.getBearing())
                .setFrc(encode(locationReferencePoint.getFRC()))
                .setFow(encode(locationReferencePoint.getFOW()))
                .build();
        builder.setLineAttributes(lineAttributes);

        if (!locationReferencePoint.isLastLRP()) {
            PathAttributes pathAttributes = PathAttributes.newBuilder()
                    .setDistanceToNext(locationReferencePoint.getDistanceToNext())
                    .setLowestFrcAlongPath(encode(locationReferencePoint.getLfrc()))
                    .build();
            builder.setPathAttributes(pathAttributes);
        }

        return builder.build();
    }

    private FRC encode(FunctionalRoadClass functionalRoadClass) {
        switch (functionalRoadClass) {
            case FRC_0:
                return FRC.FRC_0;
            case FRC_1:
                return FRC.FRC_1;
            case FRC_2:
                return FRC.FRC_2;
            case FRC_3:
                return FRC.FRC_3;
            case FRC_4:
                return FRC.FRC_4;
            case FRC_5:
                return FRC.FRC_5;
            case FRC_6:
                return FRC.FRC_6;
            case FRC_7:
                return FRC.FRC_7;
            default:
                throw new IllegalStateException();
        }
    }

    private FOW encode(FormOfWay formOfWay) {
        switch (formOfWay) {
            case UNDEFINED:
                return FOW.FOW_UNDEFINED;
            case MOTORWAY:
                return FOW.FOW_MOTORWAY;
            case MULTIPLE_CARRIAGEWAY:
                return FOW.FOW_MULTIPLE_CARRIAGEWAY;
            case SINGLE_CARRIAGEWAY:
                return FOW.FOW_SINGLE_CARRIAGEWAY;
            case ROUNDABOUT:
                return FOW.FOW_ROUNDABOUT;
            case TRAFFIC_SQUARE:
                return FOW.FOW_TRAFFICSQUARE;
            case SLIPROAD:
                return FOW.FOW_SLIPROAD;
            case OTHER:
                return FOW.FOW_OTHER;
            default:
                throw new IllegalStateException();
        }
    }
}
