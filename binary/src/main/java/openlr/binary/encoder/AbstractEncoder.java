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
package openlr.binary.encoder;

import openlr.LocationReference;
import openlr.LocationReferencePoint;
import openlr.LocationType;
import openlr.Offsets;
import openlr.binary.OpenLRBinaryConstants;
import openlr.binary.OpenLRBinaryException;
import openlr.binary.OpenLRBinaryException.PhysicalFormatError;
import openlr.binary.data.*;
import openlr.location.data.Orientation;
import openlr.location.data.SideOfRoad;
import openlr.map.GeoCoordinates;
import openlr.rawLocRef.RawLocationReference;

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

    /**
     * Encode data.
     *
     * @param rawLocRef
     *            the raw location reference
     * @param version
     *            the version
     * @return the location reference
     */
    public abstract LocationReference encodeData(
            final RawLocationReference rawLocRef, final int version);

    /**
     * Check offsets.
     *
     * @param o
     *            the o
     * @param positiveDirection
     *            the positive direction
     * @param locref
     *            the locref
     * @return the binary return code
     */
    protected final boolean checkOffsets(final Offsets o,
                                         final boolean positiveDirection,
                                         final List<? extends LocationReferencePoint> locref) {
        int length = 0;
        int value = -1;
        if (positiveDirection) {
            length = locref.get(0).getDistanceToNext();
            value = o.getPositiveOffset(length);
        } else {
            length = locref.get(locref.size() - 2).getDistanceToNext();
            value = o.getNegativeOffset(length);
        }
        if (value > length) {
            return false;
        }
        return true;
    }

    /**
     * Generates offset information according to the OpenLR white paper. The
     * parameter positiveDirection is used to differentiate between positive
     * Offset (true) and negative offset (false).
     *
     * @param o
     *            the offset data
     * @param positiveDirection
     *            direction indicator
     * @param version
     *            the version
     * @param locref
     *            the locref
     * @return the binary offset
     * @throws OpenLRBinaryException
     *             the open lr binary processing exception
     */
    protected final Offset generateOffset(final Offsets o,
                                          final boolean positiveDirection, final int version,
                                          final List<? extends LocationReferencePoint> locref)
            throws OpenLRBinaryException {
        int length = 0;
        int value = -1;
        if (positiveDirection) {
            length = locref.get(0).getDistanceToNext();
            value = o.getPositiveOffset(length);
        } else {
            length = locref.get(locref.size() - 2).getDistanceToNext();
            value = o.getNegativeOffset(length);
        }
        Offset offset = null;
        if (value > 0) {
            int offValue = -1;
            if (version == OpenLRBinaryConstants.BINARY_VERSION_2) {
                offValue = calculateLengthInterval(value);
            } else if (version == OpenLRBinaryConstants.BINARY_VERSION_3) {
                offValue = calculateRelativeInterval(value, length);
            } else {
                throw new OpenLRBinaryException(
                        PhysicalFormatError.INVALID_VERSION, "invalid version");
            }
            offset = new Offset(offValue);
        }
        return offset;
    }

    /**
     * Generates radius information according to the OpenLR white
     * paper.
     *
     * @param r
     *            the radius
     * @return the binary radius
     */
    public final Radius generateRadius(final long r) {
        Radius radius = new Radius(r);
        return radius;
    }

    /**
     * Calculate relative interval.
     *
     * @param value
     *            the value
     * @param length
     *            the length
     *
     * @return the int
     */
    private int calculateRelativeInterval(final int value, final int length) {
        if (value == length) {
            return OpenLRBinaryConstants.OFFSET_BUCKETS - 1;
        }
        return (int) Math.floor((OpenLRBinaryConstants.OFFSET_BUCKETS * value)
                / (float) length);
    }

    /**
     * Generates the binary header according to the OpenLR white paper..
     *
     * @param version the version
     * @param locType the loc type
     * @param hasAttributes the has attributes
     * @return the binary header
     */
    protected final Header generateHeader(final int version,
                                          final LocationType locType,
                                          final boolean hasAttributes) {
        int pF = OpenLRBinaryConstants.IS_NOT_POINT;
        int arF = OpenLRBinaryConstants.IS_NOT_AREA;

        if (LocationType.POINTS_LOCATIONS.contains(locType)) {
            pF = OpenLRBinaryConstants.IS_POINT;
        } else if (LocationType.AREA_LOCATIONS.contains(locType)) {
            if (locType == LocationType.CIRCLE) {
                arF = OpenLRBinaryConstants.AREA_CODE_CIRCLE;
            } else if (locType == LocationType.RECTANGLE) {
                arF = OpenLRBinaryConstants.AREA_CODE_RECTANGLE;
            } else if (locType == LocationType.GRID) {
                arF = OpenLRBinaryConstants.AREA_CODE_GRID;
            } else if (locType == LocationType.POLYGON) {
                arF = OpenLRBinaryConstants.AREA_CODE_POLYGON;
            } else if (locType == LocationType.CLOSED_LINE) {
                arF = OpenLRBinaryConstants.AREA_CODE_CLOSEDLINE;
            }
        }
        int aF = OpenLRBinaryConstants.HAS_NO_ATTRIBUTES;
        if (hasAttributes) {
            aF = OpenLRBinaryConstants.HAS_ATTRIBUTES;
        }
        Header header = new Header(arF, aF, pF, version);
        return header;
    }

    /**
     * Generates the first location reference point according to the OpenLR
     * white paper.
     *
     * @param p
     *            the location reference point information
     *
     * @return the first location reference point
     *
     */
    protected final FirstLRP generateFirstLRP(final LocationReferencePoint p) {
        FirstLRP firstLRP = new FirstLRP(
                get24BitRepresentation(p.getLongitudeDeg()),
                get24BitRepresentation(p.getLatitudeDeg()),
                generateAttribute1(p), generateAttribute2(p),
                generateAttribute3(p));
        return firstLRP;
    }

    /**
     * Generates the first location reference point according to the OpenLR
     * white paper.
     *
     * @param p
     *            the location reference point information
     * @param o
     *            the o
     *
     * @return the first location reference point
     *
     */
    protected final FirstLRP generateFirstLRP(final LocationReferencePoint p,
                                              final Orientation o) {
        FirstLRP firstLRP = new FirstLRP(
                get24BitRepresentation(p.getLongitudeDeg()),
                get24BitRepresentation(p.getLatitudeDeg()), generateAttribute1(
                p, o), generateAttribute2(p), generateAttribute3(p));
        return firstLRP;
    }

    /**
     * Generates the last location reference point according to the OpenLR white
     * paper.
     *
     * @param points
     *            the location reference points list
     * @param pOff
     *            the positive offset
     * @param nOff
     *            the negative offset
     *
     * @return the last location reference point
     */
    protected final LastLRP generateLastLrp(
            final List<? extends LocationReferencePoint> points,
            final Offset pOff, final Offset nOff) {
        int psize = points.size();
        LocationReferencePoint p = points.get(psize - 1);
        LocationReferencePoint prev = points.get(psize - 2);
        LastLRP lastLRP = new LastLRP(getRelativeRepresentation(
                prev.getLongitudeDeg(), p.getLongitudeDeg()),
                getRelativeRepresentation(prev.getLatitudeDeg(),
                        p.getLatitudeDeg()), generateAttribute1(p),
                generateAttribute4(p, pOff, nOff));
        return lastLRP;
    }

    /**
     * Generates the last location reference point according to the OpenLR white
     * paper.
     *
     * @param points
     *            the location reference points list
     * @param pOff
     *            the positive offset
     * @param s
     *            the s
     *
     * @return the last location reference point
     */
    protected final LastLRP generateLastLrp(
            final List<? extends LocationReferencePoint> points,
            final Offset pOff, final SideOfRoad s) {
        int psize = points.size();
        LocationReferencePoint p = points.get(psize - 1);
        LocationReferencePoint prev = points.get(psize - 2);
        LastLRP lastLRP = new LastLRP(getRelativeRepresentation(
                prev.getLongitudeDeg(), p.getLongitudeDeg()),
                getRelativeRepresentation(prev.getLatitudeDeg(),
                        p.getLatitudeDeg()), generateAttribute1(p, s),
                generateAttribute4(p, pOff, null));
        return lastLRP;
    }

    /**
     * Generates the list of binary intermediate location reference points.
     *
     * @param pointList
     *            the location reference point list
     *
     * @return the intermediate location reference points
     *
     */
    protected final IntermediateLRP[] generateLRPs(
            final List<? extends LocationReferencePoint> pointList) {
        ArrayList<IntermediateLRP> data = new ArrayList<IntermediateLRP>();
        int nrPoints = pointList.size();
        for (int i = 1; i < nrPoints - 1; ++i) {
            LocationReferencePoint lrp = pointList.get(i);
            LocationReferencePoint prev = pointList.get(i - 1);
            IntermediateLRP newLRP = new IntermediateLRP(
                    getRelativeRepresentation(prev.getLongitudeDeg(),
                            lrp.getLongitudeDeg()), getRelativeRepresentation(
                    prev.getLatitudeDeg(), lrp.getLatitudeDeg()),
                    generateAttribute1(lrp), generateAttribute2(lrp),
                    generateAttribute3(lrp));
            data.add(newLRP);
        }
        return data.toArray(new IntermediateLRP[data.size()]);
    }

    /**
     * Generates the last binary intermediate location reference point for
     * closed lines.
     *
     * @param pointList
     *            the location reference point list
     *
     * @return the last intermediate location reference point
     *
     */
    protected final LastClosedLineLRP generateLastLineLRP(
            final List<? extends LocationReferencePoint> pointList) {
        // last "intermediate" LRP
        int psize = pointList.size();
        LocationReferencePoint lrp = pointList.get(psize - 1);
        LastClosedLineLRP newLRP = new LastClosedLineLRP(
                generateAttribute5(lrp), generateAttribute6(lrp));
        return newLRP;
    }

    /**
     * Generates attribute 2 according to the OpenLR white paper.
     *
     * @param p
     *            the location reference point information
     *
     * @return the attribute 2
     *
     */
    private Attr2 generateAttribute2(final LocationReferencePoint p) {
        Attr2 attr2 = new Attr2(p.getLfrc().getID(),
                calculateBearingInterval(p.getBearing()));
        return attr2;
    }

    /**
     * Generates attribute 6 according to the OpenLR white paper.
     *
     * @param p
     *            the location reference point information
     *
     * @return the attribute 6
     *
     */
    private Attr6 generateAttribute6(final LocationReferencePoint p) {
        Attr6 attr6 = new Attr6(calculateBearingInterval(p.getBearing()));
        return attr6;
    }

    /**
     * Generates attribute 1 according to the OpenLR white paper.
     *
     * @param p
     *            the location reference point information
     *
     * @return the attribute 1
     */
    private Attr1 generateAttribute1(final LocationReferencePoint p) {
        Attr1 attr1 = new Attr1(p.getFRC().getID(), p.getFOW().getID(), 0);
        return attr1;
    }

    /**
     * Generates attribute 1 according to the OpenLR white paper.
     *
     * @param p
     *            the location reference point information
     * @param s
     *            the s
     *
     * @return the attribute 1
     */
    private Attr1 generateAttribute1(final LocationReferencePoint p,
                                     final SideOfRoad s) {
        Attr1 attr1 = new Attr1(p.getFRC().getID(), p.getFOW().getID(),
                s.ordinal());
        return attr1;
    }

    /**
     * Generates attribute 1 according to the OpenLR white paper.
     *
     * @param p
     *            the location reference point information
     * @param o
     *            the o
     *
     * @return the attribute 1
     */
    private Attr1 generateAttribute1(final LocationReferencePoint p,
                                     final Orientation o) {
        Attr1 attr1 = new Attr1(p.getFRC().getID(), p.getFOW().getID(),
                o.ordinal());
        return attr1;
    }

    /**
     * Generates attribute 3 according to the OpenLR white paper.
     *
     * @param p
     *            the location reference point information
     *
     * @return the attribute 3
     */
    private Attr3 generateAttribute3(final LocationReferencePoint p) {
        Attr3 attr3 = new Attr3(calculateLengthInterval(p.getDistanceToNext()));
        return attr3;
    }

    /**
     * Generates attribute 4 according to the OpenLR white paper.
     *
     * @param p
     *            the location reference point information
     * @param pOff
     *            the positive offset
     * @param nOff
     *            the negative offset
     *
     * @return the attribute 4
     *
     */
    private Attr4 generateAttribute4(final LocationReferencePoint p,
                                     final Offset pOff, final Offset nOff) {
        int pF = 0;
        if (pOff != null) {
            pF = 1;
        }
        int nF = 0;
        if (nOff != null) {
            nF = 1;
        }
        Attr4 attr4 = new Attr4(pF, nF,
                calculateBearingInterval(p.getBearing()));
        return attr4;
    }

    /**
     * Generates attribute 1 according to the OpenLR white paper.
     *
     * @param p
     *            the location reference point information
     *
     * @return the attribute 1
     */
    private Attr5 generateAttribute5(final LocationReferencePoint p) {
        Attr5 attr5 = new Attr5(p.getFRC().getID(), p.getFOW().getID());
        return attr5;
    }

    /**
     * Calculates the bearing interval index for the angle angle.
     *
     * @param angle
     *            the angle
     *
     * @return the bearing interval index
     */
    private int calculateBearingInterval(final double angle) {
        return (int) Math.floor(angle / OpenLRBinaryConstants.BEARING_SECTOR);
    }

    /**
     * Calculates the length interval index for the length val.
     *
     * @param val
     *            the length
     *
     * @return the length interval index
     */
    private int calculateLengthInterval(final int val) {
        return (int) Math.floor(val / OpenLRBinaryConstants.LENGTH_INTERVAL);
    }

    /**
     * Calculates the 24 bit representation of a coordinate value.
     *
     * @param val
     *            the coordinate value
     *
     * @return the 24 bit representation of the coordinate value
     */
    private int get24BitRepresentation(final double val) {
        int sgn = (int) Math.signum(val);
        int retVal = Math
                .round((float) ((sgn * OpenLRBinaryConstants.ROUND_FACTOR) + (val * OpenLRBinaryConstants.BIT24FACTOR)));
        return retVal;
    }

    /**
     * Calculates the relative representation of a coordinate value. The method
     * returns the relative difference of the coordinate values in deca micro
     * degree.
     *
     * @param prevVal
     *            the old val
     * @param nextVal
     *            the new val
     *
     * @return the relative representation of a coordinate
     */
    protected final int getRelativeRepresentation(final double prevVal,
                                                  final double nextVal) {
        int retVal = (int) Math.round(OpenLRBinaryConstants.DECA_MICRO_DEG_FACTOR * (nextVal - prevVal));
        return retVal;
    }

    /**
     * Generate abs coord.
     *
     * @param coord
     *            the coord
     *
     * @return the absolute coordinates
     */
    protected final AbsoluteCoordinates generateAbsCoord(
            final GeoCoordinates coord) {
        AbsoluteCoordinates absCoord = new AbsoluteCoordinates(
                get24BitRepresentation(coord.getLongitudeDeg()),
                get24BitRepresentation(coord.getLatitudeDeg()));
        return absCoord;
    }

    /**
     * Generate relative coordinates.
     *
     * @param startLRP
     *            the start lrp
     * @param coord
     *            the coord
     *
     * @return the relative coordinates
     */
    protected final RelativeCoordinates generateRelativeCoordinates(
            final LocationReferencePoint startLRP, final GeoCoordinates coord) {
        RelativeCoordinates relCoord = new RelativeCoordinates(
                getRelativeRepresentation(startLRP.getLongitudeDeg(),
                        coord.getLongitudeDeg()), getRelativeRepresentation(
                startLRP.getLatitudeDeg(), coord.getLatitudeDeg()));
        return relCoord;
    }

    /**
     * Fits into 2 bytes.
     *
     * @param value the value
     * @return true, if successful
     */
    protected final boolean fitsInto2Bytes(final int value) {
        return (value >= Short.MIN_VALUE && value <= Short.MAX_VALUE);
    }
}
