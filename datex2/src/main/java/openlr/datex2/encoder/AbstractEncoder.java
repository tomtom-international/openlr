/**
 * Licensed to the TomTom International B.V. under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  TomTom International B.V.
 * licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * <p>
 * Copyright (C) 2009-2019 TomTom International B.V.
 * <p>
 * TomTom (Legal Department)
 * Email: legal@tomtom.com
 * <p>
 * TomTom (Technical contact)
 * Email: openlr@tomtom.com
 * <p>
 * Address: TomTom International B.V., Oosterdoksstraat 114, 1011DK Amsterdam,
 * the Netherlands
 * <p>
 * Copyright (C) 2009-2019 TomTom International B.V.
 * <p>
 * TomTom (Legal Department)
 * Email: legal@tomtom.com
 * <p>
 * TomTom (Technical contact)
 * Email: openlr@tomtom.com
 * <p>
 * Address: TomTom International B.V., Oosterdoksstraat 114, 1011DK Amsterdam,
 * the Netherlands
 */
/**
 *  Copyright (C) 2009-2019 TomTom International B.V.
 *
 *   TomTom (Legal Department)
 *   Email: legal@tomtom.com
 *
 *   TomTom (Technical contact)
 *   Email: openlr@tomtom.com
 *
 *   Address: TomTom International B.V., Oosterdoksstraat 114, 1011DK Amsterdam,
 *   the Netherlands
 */
package openlr.datex2.encoder;

import eu.datex2.schema._2_0rc2._2_0.*;
import openlr.LocationReference;
import openlr.LocationReferencePoint;
import openlr.Offsets;
import openlr.datex2.OpenLRDatex2Constants;
import openlr.location.data.Orientation;
import openlr.location.data.SideOfRoad;
import openlr.map.FormOfWay;
import openlr.map.FunctionalRoadClass;
import openlr.rawLocRef.RawLocationReference;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * The class AbstractEncoder is the base class for all location type encoders.
 * It provides common methods for the generation of a location reference out of
 * the raw data.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public abstract class AbstractEncoder {

    /** The Object Factory. */
    protected static final ObjectFactory OBJECT_FACTORY = new ObjectFactory();
    /** degrees of a full circle */
    private static final int FULL_CIRCLE = 360;

    /**
     * Encodes the data.
     *
     * @param locRef the raw location reference
     * @param version the version
     * @return the location reference

     */
    public abstract LocationReference encodeData(
            final RawLocationReference locRef, final int version);

    /**
     * Creates the last location reference point.
     *
     * @param locRef
     *            the OpenLR location reference
     *
     * @return the last location reference point
     */
    protected final OpenlrLastLocationReferencePoint createLastLRP(
            final List<? extends LocationReferencePoint> locRef) {
        OpenlrLastLocationReferencePoint llrp = OBJECT_FACTORY
                .createOpenlrLastLocationReferencePoint();
        LocationReferencePoint l = locRef.get(locRef.size() - 1);
        PointCoordinates coord = createCoordinates(l);
        llrp.setOpenlrCoordinate(coord);
        OpenlrLineAttributes lineAttr = createLineAttr(l);
        llrp.setOpenlrLineAttributes(lineAttr);
        return llrp;
    }

    /**
     * Creates the location reference points (except the last LRP).
     *
     * @param locRef
     *            the OpenLR location reference
     *
     * @return the location reference points
     */
    protected final List<OpenlrLocationReferencePoint> createLRPs(
            final List<? extends LocationReferencePoint> locRef) {
        List<OpenlrLocationReferencePoint> points = new ArrayList<OpenlrLocationReferencePoint>();

        for (int i = 0; i < locRef.size() - 1; i++) {
            LocationReferencePoint lrp = locRef.get(i);
            points.add(createLRP(lrp));
        }
        return points;
    }

    /**
     * Creates the location reference point.
     *
     * @param lrp
     *            the OpenLR location reference point
     *
     * @return the location reference point
     */
    protected final OpenlrLocationReferencePoint createLRP(
            final LocationReferencePoint lrp) {
        OpenlrLocationReferencePoint l = OBJECT_FACTORY
                .createOpenlrLocationReferencePoint();
        PointCoordinates coord = createCoordinates(lrp);
        l.setOpenlrCoordinate(coord);
        OpenlrLineAttributes lineAttr = createLineAttr(lrp);
        l.setOpenlrLineAttributes(lineAttr);
        OpenlrPathAttributes pathAttr = createPathAttr(lrp);
        l.setOpenlrPathAttributes(pathAttr);
        return l;
    }

    /**
     * Creates the path attributes.
     *
     * @param lrp
     *            the location reference point
     *
     * @return the path attributes
     */
    private OpenlrPathAttributes createPathAttr(final LocationReferencePoint lrp) {
        OpenlrPathAttributes pathAttr = OBJECT_FACTORY
                .createOpenlrPathAttributes();
        pathAttr.setOpenlrDistanceToNextLRPoint(BigInteger.valueOf(lrp
                .getDistanceToNext()));
        pathAttr.setOpenlrLowestFRCToNextLRPoint(mapFRC(lrp.getLfrc()));
        return pathAttr;
    }

    /**
     * Creates the line attributes.
     *
     * @param lrp
     *            the location reference point
     *
     * @return the line attributes
     */
    private OpenlrLineAttributes createLineAttr(final LocationReferencePoint lrp) {
        OpenlrLineAttributes lineAttr = OBJECT_FACTORY
                .createOpenlrLineAttributes();
        int bearing = (int) Math.round(lrp.getBearing());
        if (bearing == FULL_CIRCLE) {
            bearing = 0;
        }
        lineAttr.setOpenlrBearing(bearing);
        lineAttr.setOpenlrFormOfWay(mapFOW(lrp.getFOW()));
        lineAttr.setOpenlrFunctionalRoadClass(mapFRC(lrp.getFRC()));
        return lineAttr;
    }

    /**
     * Map from OpenLR funational road class to XML FRCType.
     *
     * @param frc
     *            the functional road class
     *
     * @return the FRC type
     */
    private OpenlrFunctionalRoadClassEnum mapFRC(final FunctionalRoadClass frc) {
        OpenlrFunctionalRoadClassEnum frcType = null;
        switch (frc) {
            case FRC_0:
                frcType = OpenlrFunctionalRoadClassEnum.FRC_0;
                break;
            case FRC_1:
                frcType = OpenlrFunctionalRoadClassEnum.FRC_1;
                break;
            case FRC_2:
                frcType = OpenlrFunctionalRoadClassEnum.FRC_2;
                break;
            case FRC_3:
                frcType = OpenlrFunctionalRoadClassEnum.FRC_3;
                break;
            case FRC_4:
                frcType = OpenlrFunctionalRoadClassEnum.FRC_4;
                break;
            case FRC_5:
                frcType = OpenlrFunctionalRoadClassEnum.FRC_5;
                break;
            case FRC_6:
                frcType = OpenlrFunctionalRoadClassEnum.FRC_6;
                break;
            case FRC_7:
                frcType = OpenlrFunctionalRoadClassEnum.FRC_7;
                break;
            default:
                frcType = null;
        }
        return frcType;
    }

    /**
     * Maps from OpenLR form of way to XML FOWType.
     *
     * @param fow
     *            the form of way
     *
     * @return the FOW type
     */
    private OpenlrFormOfWayEnum mapFOW(final FormOfWay fow) {
        OpenlrFormOfWayEnum fowType = null;
        switch (fow) {
            case MOTORWAY:
                fowType = OpenlrFormOfWayEnum.MOTORWAY;
                break;
            case MULTIPLE_CARRIAGEWAY:
                fowType = OpenlrFormOfWayEnum.MULTIPLE_CARRIAGEWAY;
                break;
            case OTHER:
                fowType = OpenlrFormOfWayEnum.OTHER;
                break;
            case ROUNDABOUT:
                fowType = OpenlrFormOfWayEnum.ROUNDABOUT;
                break;
            case SINGLE_CARRIAGEWAY:
                fowType = OpenlrFormOfWayEnum.SINGLE_CARRIAGEWAY;
                break;
            case SLIPROAD:
                fowType = OpenlrFormOfWayEnum.SLIP_ROAD;
                break;
            case TRAFFIC_SQUARE:
                fowType = OpenlrFormOfWayEnum.TRAFFIC_SQUARE;
                break;
            case UNDEFINED:
                fowType = OpenlrFormOfWayEnum.UNDEFINED;
                break;
            default:
                fowType = null;
        }
        return fowType;
    }

    /**
     * Creates the coordinates.
     *
     * @param lrp
     *            the location reference point
     *
     * @return the coordinates
     */
    private PointCoordinates createCoordinates(final LocationReferencePoint lrp) {
        PointCoordinates coord = OBJECT_FACTORY.createPointCoordinates();
        coord.setLatitude((float) lrp.getLatitudeDeg());
        coord.setLongitude((float) lrp.getLongitudeDeg());
        return coord;
    }

    /**
     * Creates the offsets.
     *
     * @param od
     *            the OpenLR offsets
     * @param negIncluded
     *            the neg included
     * @return the offsets
     */
    protected final OpenlrOffsets createOffsets(final Offsets od,
                                                final boolean negIncluded) {
        OpenlrOffsets off = OBJECT_FACTORY.createOpenlrOffsets();
        BigInteger pdist = OpenLRDatex2Constants.NO_OFFSET;
        BigInteger ndist = OpenLRDatex2Constants.NO_OFFSET;
        if (od.hasPositiveOffset()) {
            pdist = BigInteger.valueOf(od.getPositiveOffset(0));
        }
        if (negIncluded && od.hasNegativeOffset()) {
            ndist = BigInteger.valueOf(od.getNegativeOffset(0));
        }
        off.setOpenlrNegativeOffset(ndist);
        off.setOpenlrPositiveOffset(pdist);
        return off;
    }

    /**
     * Resolve orientation.
     *
     * @param o
     *            the o
     *
     * @return the orientation type
     */
    protected final OpenlrOrientationEnum resolveOrientation(final Orientation o) {
        OpenlrOrientationEnum ot = null;
        switch (o) {
            case NO_ORIENTATION_OR_UNKNOWN:
                ot = OpenlrOrientationEnum.NO_ORIENTATION_OR_UNKNOWN;
                break;
            case WITH_LINE_DIRECTION:
                ot = OpenlrOrientationEnum.WITH_LINE_DIRECTION;
                break;
            case AGAINST_LINE_DIRECTION:
                ot = OpenlrOrientationEnum.AGAINST_LINE_DIRECTION;
                break;
            case BOTH:
                ot = OpenlrOrientationEnum.BOTH;
                break;
            default:
                ot = OpenlrOrientationEnum.NO_ORIENTATION_OR_UNKNOWN;
        }
        return ot;
    }

    /**
     * Resolve side of road.
     *
     * @param s
     *            the s
     *
     * @return the side of road type
     */
    protected final OpenlrSideOfRoadEnum resolveSideOfRoad(final SideOfRoad s) {
        OpenlrSideOfRoadEnum st = null;
        switch (s) {
            case ON_ROAD_OR_UNKNOWN:
                st = OpenlrSideOfRoadEnum.ON_ROAD_OR_UNKNOWN;
                break;
            case RIGHT:
                st = OpenlrSideOfRoadEnum.RIGHT;
                break;
            case LEFT:
                st = OpenlrSideOfRoadEnum.LEFT;
                break;
            case BOTH:
                st = OpenlrSideOfRoadEnum.BOTH;
                break;
            default:
                st = OpenlrSideOfRoadEnum.ON_ROAD_OR_UNKNOWN;
        }
        return st;
    }

}
