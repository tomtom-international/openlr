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
package openlr.encoder;

import openlr.StatusCode;

/**
 * The encoder return codes
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public enum EncoderReturnCode implements StatusCode {

	/** no location found */
	NO_LOCATION_FOUND("location not found"),

	/** location type is not supported */
	INVALID_LOCATION_TYPE("invalid location type"),

	/** coordinate values are not valid */
	COORDINATES_OUT_OF_BOUNDS("invalid coordinates"),

	/** no line for a point location found */
	NO_LINE_FOR_POINT_LOCATION("no line found for point location"),

	/** offset value is invalid */
	OFFSET_LINE_MISMATCH("offsets does not match to line"),

	/** The location is empty. */
	LOCATION_IS_EMPTY("location is empty"),

	/** The location is not connected. */
	LOCATION_NOT_CONNECTED("location is not connected"),

	/**
	 * The location needs to be drivable and not being affected by a turn
	 * restriction.
	 */
	LOCATION_CONTAINS_TURN_RESTRICTION(
			"Location is affected by a turn restriction!"),

	/** one offset value exceeds the maximum distance value */
	OFFSET_FAILURE("offset failure: offset exceeds maximum distance"),

	/**
	* added the following Return Codes
	 */

	/**
	 * A rectangle or grid location is given by two explicit corner points,
	 * while the remaining two must be calculated. This calculation either has
	 * not been done or has failed, and thus the list of corners is missing.
	 */
	MISSING_CORNERS("Location lacks of the explicit list of corners"),

	/**
	 * The number of columns of a rectangle or grid location is negative or
	 * zero.
	 */
	INVALID_NUMBER_OF_COLUMNS("invalid number of columns"),

	/** The number of rows of a rectangle or grid location is negative or zero. */
	INVALID_NUMBER_OF_ROWS("invalid number of rows"),

	/** The radius of a circle location is zero or negative. */
	INVALID_RADIUS("invalid radius"),
	
	/** The MA p_ databas e_ i s_ empty. */
	MAP_DATABASE_IS_EMPTY("map database is empty but required"), 
	
	/** The POLYGO n_ no t_ simple. */
	POLYGON_NOT_SIMPLE("the polygon is not simple");

	/** The error description. */
	private String description;

	/**
	 * Instantiates a new encoder error type.
	 * 
	 * @param desc
	 *            the description
	 */
	EncoderReturnCode(final String desc) {
		description = desc;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return description;
	}

	/** {@inheritDoc} */
	@Override
	public int getID() {
		return ordinal();
	}
}
