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
package openlr.binary;

import openlr.PhysicalFormatException;
import openlr.StatusCode;

/**
 * The processing exception of the OpenLR binary format.
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public class OpenLRBinaryException extends PhysicalFormatException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2806497620771051725L;

	/**
	 * The Enum PhysicalFormatError.
	 */
	public enum PhysicalFormatError implements StatusCode {
		
		/** The INVALI d_ version. */
		INVALID_VERSION("invalid version"),
		
		/** The INVALI d_ binar y_ data. */
		INVALID_BINARY_DATA("invalid binary data"),
		
		//Added by DLR. e.V. (RE)
		/** The INVALI d_ radius. */
		INVALID_RADIUS("invalid radius"),
		
		/** The INVALI d_ internal calculation. */
		INVALID_RELATIVE_COORD("invalid relative coordinates");
		
		/** The explanation. */
		private final String explanation;
		
		/**
		 * Instantiates a new binary processing error.
		 *
		 * @param s the explanation
		 */
		PhysicalFormatError(final String s) {
			explanation = s;
		}
		
	
		/**
		 * {@inheritDoc}
		 */
		@Override
		public final String toString() {
			return explanation;
		}


		@Override
		public int getID() {
			return ordinal();
		}
		
	}
	
	/**
	 * Instantiates a new open lr binary processing exception.
	 *
	 * @param err the err
	 */
	public OpenLRBinaryException(final StatusCode err) {
		super(err);
	}
	
	/**
	 * Instantiates a new open lr binary processing exception.
	 *
	 * @param err the err
	 * @param t the t
	 */
	public OpenLRBinaryException(final StatusCode err, final Throwable t) {
		super(err, t);
	}
	
	/**
	 * Instantiates a new open lr binary processing exception.
	 *
	 * @param err the err
	 * @param s the s
	 */
	public OpenLRBinaryException(final StatusCode err, final String s) {
		super(err, s);
	}

	/**
	 * Instantiates a new open lr binary processing exception.
	 *
	 * @param errorCode the error code
	 * @param s the s
	 * @param t the t
	 */
	public OpenLRBinaryException(final StatusCode errorCode, final String s,
			final Throwable t) {
		super(errorCode, s, t);
	}
}
