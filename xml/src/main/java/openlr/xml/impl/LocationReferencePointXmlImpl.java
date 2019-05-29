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
package openlr.xml.impl;

import openlr.LocationReferencePoint;
import openlr.map.FormOfWay;
import openlr.map.FunctionalRoadClass;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Implementation of the {@link LocationReferencePoint} interface.
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public class LocationReferencePointXmlImpl implements LocationReferencePoint {

	/** The bearing of the line referenced by the LRP. */
	private final double bearing;

	/** The distance to the next LRP along the shortest-path. */
	private final int distanceToNext;

	/** The functional road class of the line referenced by the LRP. */
	private final FunctionalRoadClass frc;

	/** The form of way of the line referenced by the LRP. */
	private final FormOfWay fow;

	/** The lowest functional road class to the next LRP. */
	private final FunctionalRoadClass lfrcnp;

	/** indicate that this is the last LRP */
	private final boolean isLast;
	
	/** The longitude coordinate. */
	private final double longitude;
	
	/** The latitude coordinate. */
	private final double latitude;
	
	/** The sequence number. */
	private final int sequenceNumber;

	/**
	 * Instantiates a new location reference point. A LRP consists of an unique
	 * ID which is the index in the sequence of location reference points. The
	 * LRP holds a position as longitude/latitude coordinates referencing to a
	 * position in the road network. Attributes like functional road class, form
	 * of way and bearing refer to a line in the road network whereby one of the
	 * nodes of that line is close to the position of the LRP. The properties
	 * distance to next point and lowest functional road class are used as
	 * checksums and also give some hints to reduce the size of the network
	 * being investigated during decoding.
	 *
	 * @param seqNr the seq nr
	 * @param frcValue the functional road class
	 * @param fowValue the form of way
	 * @param longitudeValue the longitude coordinate [in deca-micro degree]
	 * @param latitudeValue the latitude coordinate [in deca-micro degree]
	 * @param bearingValue the bearing
	 * @param dnp the distance to next point
	 * @param lfrcnpValue the lowest functional road class to next point
	 * @param last indicator being the last LRP in the sequence
	 */
	public LocationReferencePointXmlImpl(final int seqNr, final FunctionalRoadClass frcValue, final FormOfWay fowValue,
			final double longitudeValue, final double latitudeValue,
			final double bearingValue, final int dnp,
			final FunctionalRoadClass lfrcnpValue, final boolean last) {
		longitude = longitudeValue;
		latitude = latitudeValue;
		frc = frcValue;
		fow = fowValue;
		bearing = bearingValue;
		lfrcnp = lfrcnpValue;
		isLast = last;
		distanceToNext = dnp;
		sequenceNumber = seqNr;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final double getLongitudeDeg() {
		return longitude;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final double getLatitudeDeg() {
		return latitude;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final double getBearing() {
		return bearing;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int getDistanceToNext() {
		return distanceToNext;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final FunctionalRoadClass getFRC() {
		return frc;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final FormOfWay getFOW() {
		return fow;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final FunctionalRoadClass getLfrc() {
		return lfrcnp;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean isLastLRP() {
		return isLast;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("lon: ").append(longitude);		
		sb.append(" lat: ").append(latitude);
		sb.append(" frc: ").append(frc);
		sb.append(" fow: ").append(fow);
		sb.append(" bearing: ").append(bearing);
		sb.append(" dnp: ").append(distanceToNext);
		sb.append(" lfrcnp: ").append(lfrcnp);
		sb.append(" isLast: ").append(isLast);		
		return sb.toString();
	}
	
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		builder.append(bearing).append(distanceToNext).append(frc).append(fow)
				.append(lfrcnp).append(isLast).append(longitude).append(
						latitude);
		return builder.toHashCode();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean equals(final Object obj) {
		if (!(obj instanceof LocationReferencePointXmlImpl)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		LocationReferencePointXmlImpl other = (LocationReferencePointXmlImpl) obj;
		EqualsBuilder builder = new EqualsBuilder();
		builder.append(bearing, other.bearing).append(distanceToNext,
				other.distanceToNext).append(frc, other.frc).append(fow,
				other.fow).append(lfrcnp, other.lfrcnp).append(isLast,
				other.isLast).append(longitude, other.longitude).append(
				latitude, other.latitude);
		return builder.isEquals();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int getSequenceNumber() {
		return sequenceNumber;
	}

}
