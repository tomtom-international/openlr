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
 * Copyright (C) 2009-2012 TomTom International B.V.
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
 *  Copyright (C) 2009-2012 TomTom International B.V.
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
package openlr.binary.decoder;

import openlr.LocationReferencePoint;
import openlr.binary.OpenLRBinaryConstants;
import openlr.binary.OpenLRBinaryException;
import openlr.binary.bitstream.impl.ByteArrayBitstreamInput;
import openlr.binary.data.*;
import openlr.binary.impl.LocationReferencePointBinaryImpl;
import openlr.location.data.Orientation;
import openlr.location.data.SideOfRoad;
import openlr.map.FormOfWay;
import openlr.map.FunctionalRoadClass;
import openlr.rawLocRef.RawLocationReference;

/**
 * The class AbstractDecoder is the base class for all location type decoders. It
 * provides common methods for the generation of a raw location reference out of the
 * data.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public abstract class AbstractDecoder {


    /**
     * Decode data.
     *
     * @param id the id
     * @param ibs the input binary stream
     * @param totalBytes the total bytes
     * @param version the version
     * @param binData the bin data
     * @return the raw location reference
     * @throws OpenLRBinaryException the openlr binary processing exception
     */
    public abstract RawLocationReference decodeData(
            final String id, final ByteArrayBitstreamInput ibs, final int totalBytes,
            final int version, final RawBinaryData binData) throws OpenLRBinaryException;

    /**
     * Resolve side of road.
     *
     * @param attrib1 the attrib1
     *
     * @return the side of road
     */
    protected final SideOfRoad resolveSideOfRoad(final Attr1 attrib1) {
        int value = attrib1.getSideOrOrientation();
        return SideOfRoad.getSideOfRoadValues().get(value);
    }

    /**
     * Resolve orientation.
     *
     * @param attrib1 the attrib1
     *
     * @return the orientation
     */
    protected final Orientation resolveOrientation(final Attr1 attrib1) {
        int value = attrib1.getSideOrOrientation();
        return Orientation.getOrientationValues().get(value);
    }

    /**
     * Calculate relative distance.
     *
     * @param offset the offset
     *
     * @return the float
     */
    protected final float calculateRelativeDistance(final int offset) {
        float lower = offset * OpenLRBinaryConstants.RELATIVE_OFFSET_LENGTH;
        float upper = (offset + 1) * OpenLRBinaryConstants.RELATIVE_OFFSET_LENGTH;
        return (lower + upper) / 2;
    }

    /**
     * Creates the first location reference point.
     *
     * @param seqNr the seq nr
     * @param f the first LRP data
     * @return the first location reference point
     */
    protected final LocationReferencePoint createLRP(final int seqNr, final FirstLRP f) {
        FunctionalRoadClass frc = FunctionalRoadClass.getFRCs().get(f.getAttrib1()
                .getFrc());
        FormOfWay fow = FormOfWay.getFOWs().get(f.getAttrib1().getFow());
        double lon = calculate32BitRepresentation(f.getLon());
        double lat = calculate32BitRepresentation(f.getLat());
        double bearing = calculateBearingEstimate(f.getAttrib2().getBear());
        int dnp = calculateDistanceEstimate(f.getAttrib3().getDnp());
        FunctionalRoadClass lfrc = FunctionalRoadClass.getFRCs().get(f.getAttrib2()
                .getLfrcnp());
        LocationReferencePoint lrp = new LocationReferencePointBinaryImpl(seqNr, frc, fow,
                lon, lat, bearing, dnp, lfrc, false);
        return lrp;
    }

    /**
     * Creates an intermediate location reference point.
     *
     * @param seqNr the seq nr
     * @param i the intermediate LRP data
     * @param prevLon the previous longitude
     * @param prevLat the previous latitude
     * @return an intermediate location reference point
     */
    protected final LocationReferencePoint createLRP(final int seqNr, final IntermediateLRP i,
                                                     final double prevLon, final double prevLat) {
        FunctionalRoadClass frc = FunctionalRoadClass.getFRCs().get(i.getAttrib1()
                .getFrc());
        FormOfWay fow = FormOfWay.getFOWs().get(i.getAttrib1().getFow());
        double lon = prevLon
                + (i.getLon() / OpenLRBinaryConstants.DECA_MICRO_DEG_FACTOR);
        double lat = prevLat
                + (i.getLat() / OpenLRBinaryConstants.DECA_MICRO_DEG_FACTOR);
        double bearing = calculateBearingEstimate(i.getAttrib2().getBear());
        int dnp = calculateDistanceEstimate(i.getAttrib3().getDnp());
        FunctionalRoadClass lfrc = FunctionalRoadClass.getFRCs().get(i.getAttrib2()
                .getLfrcnp());
        LocationReferencePoint lrp = new LocationReferencePointBinaryImpl(seqNr, frc, fow,
                lon, lat, bearing, dnp, lfrc, false);
        return lrp;
    }

    /**
     * Creates an intermediate location reference point.
     *
     * @param seqNr the seq nr
     * @param i the intermediate LRP data
     * @param firstLRP the first lrp
     * @return an intermediate location reference point
     */
    protected final LocationReferencePoint createLRP(final int seqNr, final LastClosedLineLRP i,
                                                     final FirstLRP firstLRP) {
        FunctionalRoadClass frc = FunctionalRoadClass.getFRCs().get(i.getAttrib5()
                .getFrc());
        FormOfWay fow = FormOfWay.getFOWs().get(i.getAttrib5().getFow());
        double bearing = calculateBearingEstimate(i.getAttrib6().getBear());
        double lon = calculate32BitRepresentation(firstLRP.getLon());
        double lat = calculate32BitRepresentation(firstLRP.getLat());
        LocationReferencePoint lrp = new LocationReferencePointBinaryImpl(seqNr, frc, fow,
                lon, lat, bearing, 0, null, true);
        return lrp;
    }

    /**
     * Creates the last location reference point.
     *
     * @param seqNr the seq nr
     * @param l the last LRP data
     * @param prevLon the previous longitude
     * @param prevLat the previous latitude
     * @return the last location reference point
     */
    protected final LocationReferencePoint createLRP(final int seqNr, final LastLRP l,
                                                     final double prevLon, final double prevLat) {
        FunctionalRoadClass frc = FunctionalRoadClass.getFRCs().get(l.getAttrib1()
                .getFrc());
        FormOfWay fow = FormOfWay.getFOWs().get(l.getAttrib1().getFow());
        double lon = prevLon
                + (l.getLon() / OpenLRBinaryConstants.DECA_MICRO_DEG_FACTOR);
        double lat = prevLat
                + (l.getLat() / OpenLRBinaryConstants.DECA_MICRO_DEG_FACTOR);
        double bearing = calculateBearingEstimate(l.getAttrib4().getBear());
        int dnp = 0;
        FunctionalRoadClass lfrc = FunctionalRoadClass.FRC_7;
        LocationReferencePoint lrp = new LocationReferencePointBinaryImpl(seqNr, frc, fow,
                lon, lat, bearing, dnp, lfrc, true);
        return lrp;
    }

    /**
     * Calculates the 32 bit double value representation of a coordinate out of
     * a 24 bit integer value representation.
     *
     * @param val the 24 bit integer value
     *
     * @return the 32 bit double value representation
     */
    protected final double calculate32BitRepresentation(final int val) {
        int sgn = (int) Math.signum(val);
        double retVal = (val - (sgn * OpenLRBinaryConstants.ROUND_FACTOR))
                * OpenLRBinaryConstants.BIT24FACTOR_REVERSED;
        return retVal;
    }

    /**
     * Calculates an estimate for the bearing value. The bearing information
     * provided by the location reference point indicates an interval in which
     * the concrete value is. The approximation is the middle of that interval.
     *
     * @param interval the interval
     *
     * @return the bearing estimate
     */
    private double calculateBearingEstimate(final int interval) {
        double lower = interval * OpenLRBinaryConstants.BEARING_SECTOR;
        double upper = (interval + 1) * OpenLRBinaryConstants.BEARING_SECTOR;
        return ((upper + lower) / 2);
    }

    /**
     * Calculates an estimate for a distance value. The distance information
     * provided by the location reference point indicates an interval in which
     * the concrete value is. The approximation is the middle of that interval.
     *
     * @param interval the interval
     *
     * @return the distance estimate
     */
    protected final int calculateDistanceEstimate(final int interval) {
        float lower = interval * OpenLRBinaryConstants.LENGTH_INTERVAL;
        float upper = (interval + 1) * OpenLRBinaryConstants.LENGTH_INTERVAL;
        return Math.round(((upper + lower) / 2));
    }

    /**
     * Calculates the 24 bit representation of a coordinate value.
     *
     * @param val
     *            the coordinate value
     *
     * @return the 24 bit representation of the coordinate value
     */
    protected final int get24BitRepresentation(final double val) {
        int sgn = (int) Math.signum(val);
        int retVal = Math
                .round((float) ((sgn * OpenLRBinaryConstants.ROUND_FACTOR) + (val * OpenLRBinaryConstants.BIT24FACTOR)));
        return retVal;
    }

}
