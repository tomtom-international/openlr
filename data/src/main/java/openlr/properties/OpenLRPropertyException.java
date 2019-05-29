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
package openlr.properties;

import openlr.OpenLRProcessingException;

/**
 * This exception will be thrown if a problem with the OpenLR
 * properties ecist.
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public class OpenLRPropertyException extends OpenLRProcessingException {
	
	/**
	 * The Enum PropertyError.
	 */
	public enum PropertyError implements ErrorCode {
		
		/** the properties file was not found. */
		PROPERTIES_FILE_NOT_FOUND("the properties file was not found"),
		
		/** reading the properties file failed. */
		READING_PROPERTIES_ERROR("reading the properties file failed"),
		
		/** The INVALI d_ propert y_ type. */
		INVALID_PROPERTY_TYPE("invalid property type"),

		/** accessing the property failed (property is missing). */
		MISSING_PROPERTY("accessing the property failed (property is missing)");
		
		/** The explanation. */
		private final String explanation;
		
		/**
		 * Instantiates a new property error.
		 *
		 * @param desc the desc
		 */
		private PropertyError(final String desc) {
			explanation = desc;
		}

		@Override
		public String getName() {
			return name();
		}

		@Override
		public String getExplanation() {
			return explanation;
		}
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3621526423656350558L;
	
	/**
	 * Instantiates a new open lr property exception.
	 *
	 * @param err the err
	 */
	public OpenLRPropertyException(final ErrorCode err) {
		super(err);
	}

	/**
	 * Instantiates a new open lr property exception.
	 *
	 * @param err the err
	 * @param msg the msg
	 */
	public OpenLRPropertyException(final ErrorCode err, final String msg) {
		super(err, msg);
	}
	
	/**
	 * Instantiates a new open lr property exception.
	 *
	 * @param err the err
	 * @param t the t
	 */
	public OpenLRPropertyException(final ErrorCode err, final Throwable t) {
		super(err, t);
	}

}
