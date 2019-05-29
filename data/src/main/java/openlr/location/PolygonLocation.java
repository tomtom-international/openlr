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
package openlr.location;

import java.util.Collections;
import java.util.List;

import openlr.LocationType;
import openlr.map.GeoCoordinates;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Implementation of the location interface for polygon locations.
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author DLR e.V. (RE)
 */
public class PolygonLocation extends AbstractLocation {

	/** The ordered list of corner positions. */
	private final List<GeoCoordinates> corners;

	

	/**
	 * Instantiates a new polygon location.
	 *
	 * @param idString the id string
	 * @param loc the loc
	 */
	public PolygonLocation(final String idString,
			final List<? extends GeoCoordinates> loc) {
		super(idString, LocationType.POLYGON);
		corners = Collections.unmodifiableList(loc);
	}

	/********************** COPY CONSTRUCTOR *************************/

	/**
	 * Instantiates a copy of location l.
	 * 
	 * @param l
	 *            the location be copied
	 */
	protected PolygonLocation(final PolygonLocation l) {
		super(l);
		corners = Collections.unmodifiableList(l.getCornerPoints());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final List<GeoCoordinates> getCornerPoints() {
		return corners;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id: ").append(id);
		sb.append(" loc type: ").append(locType);
		sb.append(" corners: ");
		sb.append(" [");
		for (int i = 0; i < corners.size(); i++) {
			sb.append(corners.get(i));
			if (i != corners.size() - 1) {
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int calculateHashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		builder.append(locType).append(corners);
		return builder.toHashCode();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean equals(final Object obj) {
		if (!(obj instanceof PolygonLocation)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		PolygonLocation other = (PolygonLocation) obj;
		EqualsBuilder builder = new EqualsBuilder();
		builder.append(corners, other.corners).append(locType, other.locType);
		return builder.isEquals();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int hashCode() {
		return getHashCode();
	}
}
