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
package openlr.binary.data;

import openlr.binary.bitstream.BitstreamException;
import openlr.binary.bitstream.BitstreamInput;
import openlr.binary.bitstream.BitstreamOutput;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Holds values representing a relative coordinate. The base coordinate
 * is not stored here.
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public class RelativeCoordinates extends AbstractCoordinate {
	
	/** number of bits used for coordinates (relative) */
    private static final int COORD_BITS = 16;

    /**
     * Instantiates a new relative coordinates.
     *
     * @param lonValue the lon value
     * @param latValue the lat value
     */
    public RelativeCoordinates(final int lonValue, final int latValue) {
    	super(COORD_BITS);
    	lon = lonValue;
    	lat = latValue;
    }

    /**
	 * Fills the internal values with the values received from the input stream.
	 * 
	 * @param ibs
	 *            the input stream to read from
	 * 
	 * @throws BitstreamException
	 *             if a bitstream handling error occurred
	 */
	public RelativeCoordinates(final BitstreamInput ibs) throws BitstreamException {
		super(COORD_BITS);
		read(ibs);
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean equals(final Object obj) {
		if (!(obj instanceof RelativeCoordinates)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		RelativeCoordinates other = (RelativeCoordinates) obj;
		EqualsBuilder builder = new EqualsBuilder();
		builder.append(lon, other.lon).append(lat, other.lat);
		return builder.isEquals();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		builder.append(lon).append(lat);
		return builder.toHashCode();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("lon: ").append(lon).append(" lat: ").append(lat);
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void put(final BitstreamOutput obs) throws BitstreamException {
		putCoordinates(obs);		
	}

}
