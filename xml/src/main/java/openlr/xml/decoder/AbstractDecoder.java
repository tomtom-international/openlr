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
package openlr.xml.decoder;

import openlr.LocationReferencePoint;
import openlr.Offsets;
import openlr.PhysicalFormatException;
import openlr.location.data.Orientation;
import openlr.location.data.SideOfRoad;
import openlr.map.*;
import openlr.rawLocRef.RawLocationReference;
import openlr.xml.OpenLRXMLException;
import openlr.xml.OpenLRXMLException.XMLErrorType;
import openlr.xml.generated.*;
import openlr.xml.impl.LocationReferencePointXmlImpl;
import openlr.xml.impl.OffsetsXmlImpl;

import java.math.BigInteger;

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
     * @param data the data
     * @return the raw location reference
     * @throws PhysicalFormatException the physical format exception
     */
    public abstract RawLocationReference decodeData(final String id, final Object data)
            throws PhysicalFormatException;


    /**
     * Resolve orientation.
     *
     * @param orientation the orientation
     *
     * @return the orientation
     */
    protected final Orientation resolveOrientation(final OrientationType orientation) {
        Orientation o = null;
        switch (orientation) {
            case NO_ORIENTATION_OR_UNKNOWN:
                o = Orientation.NO_ORIENTATION_OR_UNKNOWN;
                break;
            case WITH_LINE_DIRECTION:
                o = Orientation.WITH_LINE_DIRECTION;
                break;
            case AGAINST_LINE_DIRECTION:
                o = Orientation.AGAINST_LINE_DIRECTION;
                break;
            case BOTH:
                o = Orientation.BOTH;
                break;
            default:
                o = Orientation.getDefault();
        }
        return o;
    }

    /**
     * Resolve side of road.
     *
     * @param sideOfRoad the side of road2
     * @return the side of road
     */
    protected final SideOfRoad resolveSideOfRoad(final SideOfRoadType sideOfRoad) {
        SideOfRoad s = null;
        switch (sideOfRoad) {
            case ON_ROAD_OR_UNKNOWN:
                s = SideOfRoad.ON_ROAD_OR_UNKNOWN;
                break;
            case RIGHT:
                s = SideOfRoad.RIGHT;
                break;
            case LEFT:
                s = SideOfRoad.LEFT;
                break;
            case BOTH:
                s = SideOfRoad.BOTH;
                break;
            default:
                s = SideOfRoad.getDefault();
        }
        return s;
    }

    /**
     * Creates the geo coord.
     *
     * @param c the c
     * @return the geo coordinates
     * @throws OpenLRXMLException the open lrxml exception
     */
    protected final GeoCoordinates createGeoCoord(final Coordinates c)
            throws OpenLRXMLException {
        if (c == null) {
            throw new OpenLRXMLException(XMLErrorType.DATA_ERROR,
                    "no coordinates found");
        }
        GeoCoordinates gc = null;
        try {
            gc = new GeoCoordinatesImpl(c.getLongitude(), c.getLatitude());
        } catch (InvalidMapDataException e) {
            throw new OpenLRXMLException(XMLErrorType.DATA_ERROR,
                    "coordinates out of bounds");
        }
        return gc;
    }

    /**
     * Creates the last location reference point from xml data.
     *
     * @param seqNr the seq nr
     * @param lastLRP the xml data for the last LRP
     * @return the last location reference point
     * @throws OpenLRXMLException if converting data failed
     */
    protected final LocationReferencePoint createLastLRP(final int seqNr,
                                                         final LastLocationReferencePoint lastLRP) throws OpenLRXMLException {
        Coordinates coord = lastLRP.getCoordinates();
        if (coord == null) {
            throw new OpenLRXMLException(XMLErrorType.DATA_ERROR,
                    "invalid coordinates");
        }
        double lon = coord.getLongitude();
        double lat = coord.getLatitude();
        LineAttributes lineAttr = lastLRP.getLineAttributes();
        if (lineAttr == null) {
            throw new OpenLRXMLException(XMLErrorType.DATA_ERROR,
                    "invalid line attributes");
        }
        double bearing = lineAttr.getBEAR();
        FunctionalRoadClass frc = mapFRC(lineAttr.getFRC());
        FormOfWay fow = mapFOW(lineAttr.getFOW());
        return new LocationReferencePointXmlImpl(seqNr, frc, fow, lon, lat, bearing, 0,
                null, true);
    }

    /**
     * Maps form of way type from XML to OpenLR.
     *
     * @param fow
     *            the xml fow
     *
     * @return the form of way
     */
    protected final FormOfWay mapFOW(final FOWType fow) {
        FormOfWay f = null;
        switch (fow) {
            case MOTORWAY:
                f = FormOfWay.MOTORWAY;
                break;
            case MULTIPLE_CARRIAGEWAY:
                f = FormOfWay.MULTIPLE_CARRIAGEWAY;
                break;
            case OTHER:
                f = FormOfWay.OTHER;
                break;
            case ROUNDABOUT:
                f = FormOfWay.ROUNDABOUT;
                break;
            case SINGLE_CARRIAGEWAY:
                f = FormOfWay.SINGLE_CARRIAGEWAY;
                break;
            case SLIPROAD:
                f = FormOfWay.SLIPROAD;
                break;
            case TRAFFICSQUARE:
                f = FormOfWay.TRAFFIC_SQUARE;
                break;
            case UNDEFINED:
                f = FormOfWay.UNDEFINED;
                break;
            default:
                f = null;
        }
        return f;
    }

    /**
     * Maps functional road class from XML to OpenLR.
     *
     * @param frc
     *            the xml functional road class
     * @return the functional road class
     */
    protected final FunctionalRoadClass mapFRC(final FRCType frc) {
        FunctionalRoadClass f = null;
        switch (frc) {
            case FRC_0:
                f = FunctionalRoadClass.FRC_0;
                break;
            case FRC_1:
                f = FunctionalRoadClass.FRC_1;
                break;
            case FRC_2:
                f = FunctionalRoadClass.FRC_2;
                break;
            case FRC_3:
                f = FunctionalRoadClass.FRC_3;
                break;
            case FRC_4:
                f = FunctionalRoadClass.FRC_4;
                break;
            case FRC_5:
                f = FunctionalRoadClass.FRC_5;
                break;
            case FRC_6:
                f = FunctionalRoadClass.FRC_6;
                break;
            case FRC_7:
                f = FunctionalRoadClass.FRC_7;
                break;
            default:
                f = null;
        }
        return f;
    }

    /**
     * Creates the location reference point from xml data.
     *
     * @param seqNr the seq nr
     * @param xmlLRP the xml data for the LRP
     * @return the location reference point
     * @throws OpenLRXMLException if converting data failed
     */
    protected final LocationReferencePoint createLRP(final int seqNr,
                                                     final openlr.xml.generated.LocationReferencePoint xmlLRP)
            throws OpenLRXMLException {
        Coordinates coord = xmlLRP.getCoordinates();
        if (coord == null) {
            throw new OpenLRXMLException(XMLErrorType.DATA_ERROR,
                    "invalid coordinates");
        }
        double lon = coord.getLongitude();
        double lat = coord.getLatitude();
        LineAttributes lineAttr = xmlLRP.getLineAttributes();
        if (lineAttr == null) {
            throw new OpenLRXMLException(XMLErrorType.DATA_ERROR,
                    "invalid line attributes");
        }
        double bearing = lineAttr.getBEAR();
        FunctionalRoadClass frc = mapFRC(lineAttr.getFRC());
        FormOfWay fow = mapFOW(lineAttr.getFOW());
        PathAttributes pathAttr = xmlLRP.getPathAttributes();
        if (pathAttr == null) {
            throw new OpenLRXMLException(XMLErrorType.DATA_ERROR,
                    "invalid path attributes");
        }
        BigInteger xmlDNP = pathAttr.getDNP();
        if (xmlDNP == null) {
            throw new OpenLRXMLException(XMLErrorType.DATA_ERROR,
                    "invalid dnp attribute");
        }
        int dnp = xmlDNP.intValue();
        FunctionalRoadClass lfrc = mapFRC(pathAttr.getLFRCNP());
        return new LocationReferencePointXmlImpl(seqNr, frc, fow, lon, lat, bearing, dnp,
                lfrc, false);
    }

    /**
     * Read xml offsets and creates OpenLR offsets.
     *
     * @param xmlOffsets
     *            the xml offsets
     *
     * @return the OpenLR offsets
     *
     * @throws OpenLRXMLException
     *             if converting data failed
     */
    protected final Offsets readOffsets(final openlr.xml.generated.Offsets xmlOffsets)
            throws OpenLRXMLException {
        if (xmlOffsets == null) {
            return new OffsetsXmlImpl(0, 0);
        }
        BigInteger poff = xmlOffsets.getPosOff();
        BigInteger noff = xmlOffsets.getNegOff();
        int pValue = 0;
        int nValue = 0;
        if (poff != null) {
            pValue = poff.intValue();
        }
        if (noff != null) {
            nValue = noff.intValue();
        }
        return new OffsetsXmlImpl(pValue, nValue);
    }

}
