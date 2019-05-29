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
package openlr.xml;

import openlr.PhysicalFormatException;
import openlr.StatusCode;

/**
 * An OpenLRXMLException will be thrown if reading/writing/encoding/decoding of
 * XML data failed.
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public class OpenLRXMLException extends PhysicalFormatException {
	
	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = -1174502068736890324L;

	/**
	 * The Enum XMLErrorType.
	 */
	public enum XMLErrorType implements StatusCode {
		
		/** The XM l_ error. */
		XML_ERROR("xml error"),
		
		/** The I o_ error. */
		IO_ERROR("i/o error"), 
		
		/** The XS d_ error. */
		XSD_ERROR("xsd not found"),
		
		/** The DAT a_ error. */
		DATA_ERROR("data error"),
		
		/** The BINAR y_ dat a_ only. */
		BINARY_DATA_ONLY("only binary data"),
		
		/** The UNKNOW n_ locatio n_ type. */
		UNKNOWN_LOCATION_TYPE("location type is unknnown"), 
		
		/** The INVALI d_ version. */
		INVALID_VERSION("invalid version");	
		
		/** The explanation. */
		private final String explanation;		
		
		/**
		 * Instantiates a new xML error type.
		 *
		 * @param s the s
		 */
		XMLErrorType(final String s) {
			explanation = s;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			return explanation;
		}

		@Override
		public int getID() {
			return ordinal();
		}
		
	}

	/**
	 * Instantiates a new openlr xml exception due to a previously thrown exception.
	 * 
	 * @param e the error
	 * @param t the previously thrown exception
	 */
	public OpenLRXMLException(final StatusCode e, final Throwable t) {
		super(e, t);
	}
	
	/**
	 * Instantiates a new openlr xml exception with an error message.
	 * 
	 * @param e the error
	 * @param s the error message
	 */
	public OpenLRXMLException(final StatusCode e, final String s) {
		super(e, s);
	}
	
	/**
	 * Instantiates a new openlr xml exception.
	 * 
	 * @param e the error
	 */
	public OpenLRXMLException(final StatusCode e) {
		super(e);
	}

}
