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
package openlr;

/**
 * The class PhysicalFormatException defines an exception where the translation of
 * a raw location reference into a phydical format failed due to an error. 
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 * 
 */
public abstract class PhysicalFormatException extends Exception {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = -8878972335210494506L;

	/**
	 * The error code
	 */
	private final StatusCode errorCode;

	/**
	 * Instantiates a new PhysicalFormatException.
	 * 
	 * @param err
	 *            the error code
	 */
	public PhysicalFormatException(final StatusCode err) {
		super();
		errorCode = err;
	}

	/**
	 * Instantiates a new PhysicalFormatException with an error message.
	 * 
	 * @param err
	 *            the error code
	 * @param s
	 *            the s
	 */
	public PhysicalFormatException(final StatusCode err, final String s) {
		super(s);
		errorCode = err;
	}

	/**
	 * Instantiates a new PhysicalFormatException due to a previously thrown
	 * exception.
	 * 
	 * @param err
	 *            the error code
	 * @param t
	 *            the t
	 */
	public PhysicalFormatException(final StatusCode err, final Throwable t) {
		super(t);
		errorCode = err;
	}

	/**
	 * Instantiates a new PhysicalFormatException due to a previously thrown
	 * exception and an error message.
	 * 
	 * @param err
	 *            the error code
	 * @param t
	 *            the t
	 * @param s
	 *            the s
	 */
	public PhysicalFormatException(final StatusCode err, final String s,
			final Throwable t) {
		super(s, t);
		errorCode = err;
	}

	/**
	 * Delivers the error code behind this exception.
	 * 
	 * @return The error code.
	 */
	public final StatusCode getErrorCode() {
		return errorCode;
	}

}
