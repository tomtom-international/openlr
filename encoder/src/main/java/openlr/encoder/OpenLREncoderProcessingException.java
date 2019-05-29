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

import openlr.OpenLRProcessingException;

/**
 * The Class OpenLREncoderProcessingException will be used to indicate a major encoding
 * error. The encoding process cannot be continued. 
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 * 
 */
public class OpenLREncoderProcessingException extends OpenLRProcessingException {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = 4806159045982809706L;

	/**
	 * The Enum EncoderRuntimeError.
	 */
	public enum EncoderProcessingError implements
			ErrorCode {

		/** No physical encoder found. */
		NO_PHYSICAL_ENCODER_FOUND("no physical encoder found"),

		/** No route found. */
		NO_ROUTE_FOUND_ERROR("no route found"),

		/** Route construction error. */
		ROUTE_CONSTRUCTION_ERROR("route construction failed"),

		/**
		 * The location reference must cover the location completely, but it
		 * does not.
		 */
		LOCATION_REFERENCE_DOES_NOT_COVER_LOCATION("Location reference does not cover the location"),

		/** The intermediate calculation failed. */
		INTERMEDIATE_CALCULATION_FAILED("intermediate calculation failed"),
		
		/** calling the encoder failed due to invalid parameter */
		INVALID_PARAMETER("invalid parameter"), 
	
		
		/** cannot adjust offset values */
		OFFSET_TRIMMING_FAILED("trimming the offsets failed"), 
		
		/** The INVALI d_ poin t_ locatio n_ lrp. */
		INVALID_POINT_LOCATION_LRP("invalid number of LRPs for a point location"), 
		
		/** The INVALI d_ ma p_ data. */
		INVALID_MAP_DATA("geo coordinates out of bounds!"),
		
		/** The maximum allowed distance between LRPs in exceeded. */
		MAX_LRP_DISTANCE_EXCEEDED("The maximum allowed distance between LRPs in exceeded!");

		/** The description. */
		private final String description;

		/**
		 * Instantiates a new encoder runtime error.
		 * 
		 * @param desc the description
		 */
		EncoderProcessingError(final String desc) {
			description = desc;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getExplanation() {
			return description;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getName() {
			return name();
		}
	}

	/**
	 * Instantiates a new openlr encoder runtime exception.
	 * 
	 * @param e
	 *            the error
	 */
	public OpenLREncoderProcessingException(final EncoderProcessingError e) {
		super(e);
	}

	/**
	 * Instantiates a new openlr encoder runtime exception.
	 * 
	 * @param e
	 *            the error
	 * @param s
	 *            the error message
	 */
	public OpenLREncoderProcessingException(final EncoderProcessingError e,
			final String s) {
		super(e, s);
	}

	/**
	 * Instantiates a new openlr encoder runtime exception.
	 * 
	 * @param e
	 *            the error
	 * @param t
	 *            previously thrown exception
	 */
	public OpenLREncoderProcessingException(final EncoderProcessingError e,
			final Throwable t) {
		super(e, t);
	}

	/**
	 * Instantiates a new open lr encoder runtime exception.
	 * 
	 * @param e
	 *            the error
	 * @param s
	 *            the error message
	 * @param t
	 *            previously thrown exception
	 */
	public OpenLREncoderProcessingException(final EncoderProcessingError e,
			final String s, final Throwable t) {
		super(e, s, t);
	}

}
