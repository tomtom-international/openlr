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
package openlr.rawLocRef;

import java.util.Collections;
import java.util.List;

import openlr.LocationReferencePoint;
import openlr.LocationType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Implementation of the RawLocationReference interface for closed line locations.
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author DLR e.V. (RE)
 */
public class RawClosedLineLocRef extends RawLocationReference {
	
	/** The points. */
	private final List<LocationReferencePoint> points;
	
	/**
	 * Instantiates a new raw encoder location reference for a closed line location.
	 *
	 * @param idValue the id
	 * @param locRefPoints the loc ref points
	 */
	public RawClosedLineLocRef(final String idValue,
			final List<? extends LocationReferencePoint> locRefPoints) {
		super(idValue, LocationType.CLOSED_LINE);
		points = Collections.unmodifiableList(locRefPoints);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
    public final List<LocationReferencePoint> getLocationReferencePoints() {
		return points;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
    public final String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id: ").append(id);
		sb.append(" locType: ").append(locType);
		if (points != null && !points.isEmpty()) {
			sb.append(" points: [");
			for (int i = 0; i < points.size(); i++) {
				sb.append(points.get(i));
				if (i == points.size() - 1) {
					sb.append("]");
				} else {
					sb.append(", ");
				}
			}
		}
		return sb.toString();		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int calculateHashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		builder.append(locType).append(points);
		return builder.toHashCode();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean equals(final Object obj) {
		if (!(obj instanceof RawClosedLineLocRef)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		RawClosedLineLocRef other = (RawClosedLineLocRef) obj;
		EqualsBuilder builder = new EqualsBuilder();
		builder.append(locType, other.locType).append(
				points, other.points);
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
