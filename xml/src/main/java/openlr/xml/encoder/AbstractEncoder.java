/**
 * Licensed to the TomTom International B.V. under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  TomTom International B.V.
 * licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
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
package openlr.xml.encoder;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import openlr.LocationReference;
import openlr.LocationReferencePoint;
import openlr.Offsets;
import openlr.location.data.Orientation;
import openlr.location.data.SideOfRoad;
import openlr.map.FormOfWay;
import openlr.map.FunctionalRoadClass;
import openlr.map.GeoCoordinates;
import openlr.map.utils.GeometryUtils;
import openlr.rawLocRef.RawLocationReference;
import openlr.xml.OpenLRXMLConstants;
import openlr.xml.generated.Coordinates;
import openlr.xml.generated.FOWType;
import openlr.xml.generated.FRCType;
import openlr.xml.generated.LastLocationReferencePoint;
import openlr.xml.generated.LineAttributes;
import openlr.xml.generated.ObjectFactory;
import openlr.xml.generated.OrientationType;
import openlr.xml.generated.PathAttributes;
import openlr.xml.generated.SideOfRoadType;

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
	public static final ObjectFactory OBJECT_FACTORY = new ObjectFactory();

	/**
	 * Encode date.
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
	 * Creates the last location reference point.
	 * 
	 * @param locRef
	 *            the OpenLR location reference
	 * 
	 * @return the last location reference point
	 */
	protected final LastLocationReferencePoint createLastLRP(
			final List<? extends LocationReferencePoint> locRef) {
		LastLocationReferencePoint llrp = OBJECT_FACTORY
				.createLastLocationReferencePoint();
		LocationReferencePoint l = locRef.get(locRef.size() - 1);
		Coordinates coord = createCoordinates(l);
		llrp.setCoordinates(coord);
		LineAttributes lineAttr = createLineAttr(l);
		llrp.setLineAttributes(lineAttr);
		return llrp;
	}

	/**
	 * Creates all the location reference points.
	 * 
	 * @param locRef
	 *            the OpenLR location reference
	 * 
	 * @return the location reference points
	 */
	protected final List<openlr.xml.generated.LocationReferencePoint> createClosedLineLRPs(
			final List<? extends LocationReferencePoint> locRef) {
		List<openlr.xml.generated.LocationReferencePoint> points = new ArrayList<openlr.xml.generated.LocationReferencePoint>();
		for (int i = 0; i < locRef.size() - 1; i++) {
			LocationReferencePoint lrp = locRef.get(i);
			points.add(createLRP(lrp));
		}
		return points;
	}

	/**
	 * Creates the location reference points (except the last LRP).
	 * 
	 * @param locRef
	 *            the OpenLR location reference
	 * 
	 * @return the location reference points
	 */
	protected final List<openlr.xml.generated.LocationReferencePoint> createLRPs(
			final List<? extends LocationReferencePoint> locRef) {
		List<openlr.xml.generated.LocationReferencePoint> points = new ArrayList<openlr.xml.generated.LocationReferencePoint>();

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
	protected final openlr.xml.generated.LocationReferencePoint createLRP(
			final LocationReferencePoint lrp) {
		openlr.xml.generated.LocationReferencePoint l = OBJECT_FACTORY
				.createLocationReferencePoint();
		Coordinates coord = createCoordinates(lrp);
		l.setCoordinates(coord);
		LineAttributes lineAttr = createLineAttr(lrp);
		l.setLineAttributes(lineAttr);
		PathAttributes pathAttr = createPathAttr(lrp);
		l.setPathAttributes(pathAttr);
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
	private PathAttributes createPathAttr(final LocationReferencePoint lrp) {
		PathAttributes pathAttr = OBJECT_FACTORY.createPathAttributes();
		pathAttr.setDNP(BigInteger.valueOf(lrp.getDistanceToNext()));
		pathAttr.setLFRCNP(mapFRC(lrp.getLfrc()));
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
	protected final LineAttributes createLineAttr(final LocationReferencePoint lrp) {
		LineAttributes lineAttr = OBJECT_FACTORY.createLineAttributes();
		int bearing = (int) Math.round(lrp.getBearing());
		if (bearing == GeometryUtils.FULL_CIRCLE_DEGREE) {
			bearing = 0;
		}
		lineAttr.setBEAR(bearing);
		lineAttr.setFOW(mapFOW(lrp.getFOW()));
		lineAttr.setFRC(mapFRC(lrp.getFRC()));
		return lineAttr;
	}

	/**
	 * Map from OpenLR functional road class to XML FRCType.
	 * 
	 * @param frc
	 *            the functional road class
	 * 
	 * @return the FRC type
	 */
	private FRCType mapFRC(final FunctionalRoadClass frc) {
		FRCType frcType = null;
		switch (frc) {
		case FRC_0:
			frcType = FRCType.FRC_0;
			break;
		case FRC_1:
			frcType = FRCType.FRC_1;
			break;
		case FRC_2:
			frcType = FRCType.FRC_2;
			break;
		case FRC_3:
			frcType = FRCType.FRC_3;
			break;
		case FRC_4:
			frcType = FRCType.FRC_4;
			break;
		case FRC_5:
			frcType = FRCType.FRC_5;
			break;
		case FRC_6:
			frcType = FRCType.FRC_6;
			break;
		case FRC_7:
			frcType = FRCType.FRC_7;
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
	private FOWType mapFOW(final FormOfWay fow) {
		FOWType fowType = null;
		switch (fow) {
		case MOTORWAY:
			fowType = FOWType.MOTORWAY;
			break;
		case MULTIPLE_CARRIAGEWAY:
			fowType = FOWType.MULTIPLE_CARRIAGEWAY;
			break;
		case OTHER:
			fowType = FOWType.OTHER;
			break;
		case ROUNDABOUT:
			fowType = FOWType.ROUNDABOUT;
			break;
		case SINGLE_CARRIAGEWAY:
			fowType = FOWType.SINGLE_CARRIAGEWAY;
			break;
		case SLIPROAD:
			fowType = FOWType.SLIPROAD;
			break;
		case TRAFFIC_SQUARE:
			fowType = FOWType.TRAFFICSQUARE;
			break;
		case UNDEFINED:
			fowType = FOWType.UNDEFINED;
			break;
		default:
			fowType = null;
		}
		return fowType;
	}

	/**
	 * Creates the coordinates.
	 * 
	 * @param centerPoint
	 *            the center point of circle location reference
	 * 
	 * @return the coordinates
	 */
	protected final Coordinates createCoordinates(
			final GeoCoordinates centerPoint) {
		Coordinates coord = OBJECT_FACTORY.createCoordinates();
		coord.setLongitude(centerPoint.getLongitudeDeg());
		coord.setLatitude(centerPoint.getLatitudeDeg());
		return coord;

	}

	/**
	 * Creates the xml coordinates.
	 * 
	 * @param centerPoint
	 *            the center point
	 * @return the coordinates
	 */
	protected final Coordinates createXMLCoordinates(
			final GeoCoordinates centerPoint) {
		Coordinates coord = OBJECT_FACTORY.createCoordinates();
		coord.setLongitude(centerPoint.getLongitudeDeg());
		coord.setLatitude(centerPoint.getLatitudeDeg());
		return coord;
	}

	/**
	 * Creates the coordinates.
	 * 
	 * @param lrp
	 *            the location reference point
	 * 
	 * @return the coordinates
	 */
	private Coordinates createCoordinates(final LocationReferencePoint lrp) {
		Coordinates coord = OBJECT_FACTORY.createCoordinates();
		coord.setLatitude(lrp.getLatitudeDeg());
		coord.setLongitude(lrp.getLongitudeDeg());
		return coord;
	}

	/**
	 * Creates the offsets.
	 *
	 * @param od the OpenLR offsets
	 * @param negIncluded the neg included
	 * @param posDist the pos dist
	 * @param negDist the neg dist
	 * @return the offsets
	 */
	protected final openlr.xml.generated.Offsets createOffsets(final Offsets od,
			final boolean negIncluded, final int posDist, final int negDist) {
		openlr.xml.generated.Offsets off = OBJECT_FACTORY.createOffsets();
		if (od.hasPositiveOffset()) {
			off.setPosOff(BigInteger.valueOf(od.getPositiveOffset(posDist)));
		} else {
			off.setPosOff(OpenLRXMLConstants.NO_OFFSET);
		}
		if (negIncluded && od.hasNegativeOffset()) {
			off.setNegOff(BigInteger.valueOf(od.getNegativeOffset(negDist)));
		} else {
			off.setNegOff(OpenLRXMLConstants.NO_OFFSET);
		}
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
	protected final OrientationType resolveOrientation(final Orientation o) {
		OrientationType ot = null;
		switch (o) {
		case NO_ORIENTATION_OR_UNKNOWN:
			ot = OrientationType.NO_ORIENTATION_OR_UNKNOWN;
			break;
		case WITH_LINE_DIRECTION:
			ot = OrientationType.WITH_LINE_DIRECTION;
			break;
		case AGAINST_LINE_DIRECTION:
			ot = OrientationType.AGAINST_LINE_DIRECTION;
			break;
		case BOTH:
			ot = OrientationType.BOTH;
			break;
		default:
			ot = OrientationType.NO_ORIENTATION_OR_UNKNOWN;
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
	protected final SideOfRoadType resolveSideOfRoad(final SideOfRoad s) {
		SideOfRoadType st = null;
		switch (s) {
		case ON_ROAD_OR_UNKNOWN:
			st = SideOfRoadType.ON_ROAD_OR_UNKNOWN;
			break;
		case RIGHT:
			st = SideOfRoadType.RIGHT;
			break;
		case LEFT:
			st = SideOfRoadType.LEFT;
			break;
		case BOTH:
			st = SideOfRoadType.BOTH;
			break;
		default:
			st = SideOfRoadType.ON_ROAD_OR_UNKNOWN;
		}
		return st;
	}

}
