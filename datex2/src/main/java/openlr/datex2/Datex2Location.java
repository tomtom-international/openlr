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
package openlr.datex2;

import javax.xml.bind.JAXBElement;

import openlr.LocationType;
import openlr.datex2.OpenLRDatex2Exception.XMLErrorType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import eu.datex2.schema._2_0rc2._2_0.Accident;
import eu.datex2.schema._2_0rc2._2_0.D2LogicalModel;
import eu.datex2.schema._2_0rc2._2_0.Linear;
import eu.datex2.schema._2_0rc2._2_0.LinearExtensionType;
import eu.datex2.schema._2_0rc2._2_0.ObjectFactory;
import eu.datex2.schema._2_0rc2._2_0.OpenlrExtendedLinear;
import eu.datex2.schema._2_0rc2._2_0.OpenlrExtendedPoint;
import eu.datex2.schema._2_0rc2._2_0.OpenlrLineLocationReference;
import eu.datex2.schema._2_0rc2._2_0.OpenlrPointLocationReference;
import eu.datex2.schema._2_0rc2._2_0.Point;
import eu.datex2.schema._2_0rc2._2_0.PointExtensionType;
import eu.datex2.schema._2_0rc2._2_0.Situation;
import eu.datex2.schema._2_0rc2._2_0.SituationPublication;

/**
 * The class Datex2Location is the data class for Datex II location
 * references. It consists of a line location reference or a point
 * location reference but not both.
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public class Datex2Location {

	/** The Constant FACTORY. */
	private static final ObjectFactory FACTORY = new ObjectFactory();

	/** The location reference data. */
	private final OpenlrPointLocationReference pointData;

	/** The line data. */
	private final OpenlrLineLocationReference lineData;

	/** The loc type. */
	private final LocationType locType;

	/**
	 * Instantiates a new datex2 location.
	 *
	 * @param p the point location reference
	 */
	public Datex2Location(final OpenlrPointLocationReference p) {
		pointData = p;
		lineData = null;
		locType = resolveLocationType(p);		
	}
	
	/**
	 * Resolves the location type.
	 *
	 * @param p the point location reference
	 * @return the location type
	 */
	private LocationType resolveLocationType(final OpenlrPointLocationReference p) {
		LocationType lType = LocationType.UNKNOWN;
		if (p.getOpenlrGeoCoordinate() != null) {
			lType = LocationType.GEO_COORDINATES;
		} else if (p.getOpenlrPointAlongLine() != null) {
			lType = LocationType.POINT_ALONG_LINE;
		} else if (p.getOpenlrPoiWithAccessPoint() != null) {
			lType = LocationType.POI_WITH_ACCESS_POINT;
		}
		return lType;
	}

	/**
	 * Instantiates a new datex2 location.
	 * 
	 * @param l the line location reference
	 */
	public Datex2Location(final OpenlrLineLocationReference l) {
		pointData = null;
		lineData = l;
		locType = LocationType.LINE_LOCATION;
	}

	/**
	 * Instantiates a new datex2 location from an object.
	 * 
	 * @param o the o
	 * @throws OpenLRDatex2Exception if no valid location reference can be identified
	 */
	Datex2Location(final Object o) throws OpenLRDatex2Exception {
		LocationType l = null;
		if (o instanceof OpenlrLineLocationReference) {
			pointData = null;
			lineData = (OpenlrLineLocationReference) o;
			l = LocationType.LINE_LOCATION;
		} else if (o instanceof OpenlrPointLocationReference) {
			pointData = (OpenlrPointLocationReference) o;
			lineData = null;
			l = resolveLocationType(pointData);
		} else {
			throw new OpenLRDatex2Exception(XMLErrorType.DATA_ERROR);
		}
		if (l == LocationType.UNKNOWN) {
			throw new OpenLRDatex2Exception(XMLErrorType.DATA_ERROR);
		}
		locType = l;
	}

	/**
	 * Gets the location type.
	 * 
	 * @return the location type
	 */
	public final LocationType getLocationType() {
		return locType;
	}

	/**
	 * Checks if is point location.
	 * 
	 * @return true, if is point location
	 */
	public final boolean isPointLocation() {
		return pointData != null;
	}

	/**
	 * Checks if is line location.
	 * 
	 * @return true, if is line location
	 */
	public final boolean isLineLocation() {
		return lineData != null;
	}

	/**
	 * Gets the line location.
	 *
	 * @return the line location
	 */
	public final OpenlrLineLocationReference getLineLocation() {
		return lineData;
	}

	/**
	 * Gets the point location.
	 *
	 * @return the point location
	 */
	public final OpenlrPointLocationReference getPointLocation() {
		return pointData;
	}

	/**
	 * Gets (fake) XML data. The generated XML is not valid. It only
	 * shows an example where the OpenLR location references may be
	 * embedded within DatexII.
	 *
	 * @return the (fake) XML data
	 */
	public final JAXBElement<D2LogicalModel> getXMLData() {
		D2LogicalModel d2 = FACTORY.createD2LogicalModel();
		SituationPublication payload = FACTORY.createSituationPublication();
		d2.setPayloadPublication(payload);
		Situation s = FACTORY.createSituation();
		payload.getSituation().add(s);
		Accident record = FACTORY.createAccident();
		s.getSituationRecord().add(record);
		if (isLineLocation()) {
			Linear locations = FACTORY.createLinear();
			LinearExtensionType ext = FACTORY.createLinearExtensionType();
			OpenlrExtendedLinear openlrLinear = FACTORY.createOpenlrExtendedLinear();
			openlrLinear.setOpenlrLineLocationReference(lineData);
			ext.setOpenlrExtendedLinear(openlrLinear);
			locations.setLinearExtension(ext);
			record.setGroupOfLocations(locations);
		} else {
			Point locations = FACTORY.createPoint();
			PointExtensionType ext = FACTORY.createPointExtensionType();
			OpenlrExtendedPoint openlrPoint = FACTORY.createOpenlrExtendedPoint();
			openlrPoint.setOpenlrPointLocationReference(pointData);
			ext.setOpenlrExtendedPoint(openlrPoint);
			locations.setPointExtension(ext);
			record.setGroupOfLocations(locations);
		}
		return FACTORY.createD2LogicalModel(d2);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		builder.append(locType).append(lineData).append(pointData);
		return builder.toHashCode();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean equals(final Object obj) {
		if (!(obj instanceof Datex2Location)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Datex2Location other = (Datex2Location) obj;
		EqualsBuilder builder = new EqualsBuilder();
		builder.append(locType, other.locType).append(lineData,
				other.lineData).append(pointData, other.pointData);
		return builder.isEquals();
	}

}
