/**
 * Licensed to the TomTom International B.V. under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  TomTom International B.V.
 * licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * <p>
 * Copyright (C) 2009-2019 TomTom International B.V.
 * <p>
 * TomTom (Legal Department)
 * Email: legal@tomtom.com
 * <p>
 * TomTom (Technical contact)
 * Email: openlr@tomtom.com
 * <p>
 * Address: TomTom International B.V., Oosterdoksstraat 114, 1011DK Amsterdam,
 * the Netherlands
 * <p>
 * Copyright (C) 2009-2019 TomTom International B.V.
 * <p>
 * TomTom (Legal Department)
 * Email: legal@tomtom.com
 * <p>
 * TomTom (Technical contact)
 * Email: openlr@tomtom.com
 * <p>
 * Address: TomTom International B.V., Oosterdoksstraat 114, 1011DK Amsterdam,
 * the Netherlands
 */
/**
 *  Copyright (C) 2009-2019 TomTom International B.V.
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
 * The class OpenLRProcessingException defines an exception where the total 
 * process of encoding or decoding failed due to an error. If this exception
 * is thrown the whole process shall terminate.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 28
 * @author TomTom International B.V.
 *
 */
public abstract class OpenLRProcessingException extends Exception {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -8878972335210494506L;
    /**
     * The error code
     */
    private final ErrorCode errorCode;

    /**
     * Instantiates a new OpenLR processing exception.
     * @param err the error code
     */
    public OpenLRProcessingException(final ErrorCode err) {
        super();
        errorCode = err;
    }

    /**
     * Instantiates a new OpenLR processing exception with an error message.
     *
     * @param err the error code
     * @param s the s
     */
    public OpenLRProcessingException(final ErrorCode err, final String s) {
        super(s);
        errorCode = err;
    }

    /**
     * Instantiates a new OpenLR processing exception due to a previously thrown exception.
     *
     * @param err the error code
     * @param t the t
     */
    public OpenLRProcessingException(final ErrorCode err, final Throwable t) {
        super(t);
        errorCode = err;
    }

    /**
     * Instantiates a new OpenLR processing exception due to a previously thrown
     * exception and an error message.
     *
     * @param err the error code
     * @param t the t
     * @param s the s
     */
    public OpenLRProcessingException(final ErrorCode err, final String s, final Throwable t) {
        super(s, t);
        errorCode = err;
    }

    /**
     * Delivers the error code behind this exception.
     * @return The error code.
     */
    public final ErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * {@inheritDoc} <br>
     * This implementation additional delivers the return coder data of the OpenLRProcessingException.
     */
    @Override
    public final String toString() {
        return super.toString() + " [" + errorCode.getName() + ": \""
                + errorCode.getExplanation() + "\"]";
    }

    /**
     * Defines the base interface of error codes. Classes that implement
     * {@link OpenLRProcessingException} should define concrete classes/ enums
     * that implement {@link ErrorCode}.
     */
    public interface ErrorCode {

        /**
         * Gets the name.
         *
         * @return the name
         */
        String getName();

        /**
         * Gets the explanation.
         *
         * @return the explanation
         */
        String getExplanation();
    }
}
