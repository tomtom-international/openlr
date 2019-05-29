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
package openlr.decoder;

import openlr.StatusCode;


/**
 * The decoder return codes.
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public enum DecoderReturnCode implements StatusCode {

	/** no candidate line found */
	NO_CANDIDATE_LINE_FOUND("no candidate line found"),

	/** no route found */
	NO_ROUTE_FOUND("no route found"),

	/** no alternative found */
	NO_ALTERNATIVE_FOUND("no alternative sub route found"), 
	
	/** The INVALI d_ locatio n_ type. */
	INVALID_LOCATION_TYPE("invalid location type"),

	/** The INVALI d_ offsets. */
	INVALID_OFFSETS("invalid offsets"),
	
	/** The N o_ ma p_ databas e_ found. */
	NO_MAP_DATABASE_FOUND("map database is required"),
	
	/** The INVALI d_ locatio n_ referenc e_ data. */
	INVALID_LOCATION_REFERENCE_DATA("the input data is not valid");

	/** The error type description. */
	private String description;

	/**
	 * Instantiates a new error type.
	 *
	 * @param desc the desc
	 */
	private DecoderReturnCode(final String desc) {
		description = desc;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return description;
	}

	/**
	 * Gets the ID.
	 * 
	 * @return the ID
	 */
	public int getID() {
		return ordinal();
	}
	
}
