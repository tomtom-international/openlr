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
package openlr.binary.bitstream;

import openlr.StatusCode;
import openlr.binary.OpenLRBinaryException;

/**
 * A BitstreamException will be thrown if an error occurs during reading or
 * writing bits from/into a bitstream. The type of the error is stored in the
 * exception object and can be accessed.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
@SuppressWarnings("serial")
public class BitstreamException extends OpenLRBinaryException {

    /**
     * Create BitstreamException with an error type error_code.
     *
     * @param errorCode
     *            the error code representing the exception condition
     */
    public BitstreamException(final StatusCode errorCode) {
        super(errorCode);
    }

    /**
     * Create BitstreamException with an error type error_code and linked with a
     * previously thrown exception.
     *
     * @param errorCode
     *            the error code representing the exception condition
     * @param t
     *            a previously thrown exception
     */
    public BitstreamException(final StatusCode errorCode, final Throwable t) {
        super(errorCode, t);

    }

    /**
     * Create BitstreamException with an error type error_code and an error message.
     *
     * @param errorCode the error code representing the exception condition
     * @param s the s
     */
    public BitstreamException(final StatusCode errorCode, final String s) {
        super(errorCode, s);
    }

    /**
     * Create BitstreamException with an error type error_code, a linked with a
     * previously thrown exception and an error message.
     *
     * @param errorCode the error code representing the exception condition
     * @param t a previously thrown exception
     * @param s the s
     */
    public BitstreamException(final StatusCode errorCode, final Throwable t, final String s) {
        super(errorCode, s, t);
    }

    /**
     * The of the error which occured.
     */
    public enum BitstreamErrorType implements StatusCode {

        /** no more data to read */
        ENDOFDATA("End of Data"),

        /** invalid alignment of the data */
        INVALIDALIGNMENT("Invalid Alignment"),

        /** read i/o error */
        READFAILED("Read I/O Failed"),

        /** write i/o error */
        WRITEFAILED("Write I/O Failed"),

        /** file cannot be opened */
        FILEOPENFAILED("File Open Failed"),

        /** system i/o error */
        SYSTEMIOFAILED("System I/O Error"),

        /** invalid input type */
        INVALIDIOTYPE("Invalid Input Type"),

        /** invalid bit size */
        INVALIDBITSIZE("Invalid bit size"),

        /** not enough data */
        NOTENOUGHDATA("Not enough data"),

        //Added by DLR e.V. (RE)
        /** invalid value range */
        INVALIDVALUERANGE("Invalid Value Range"),

        /** a const value does not match the expected value */
        CONST_VALUE_MISMATCH("Const value mismatch");

        /** The description of the error type. */
        private final String message;

        /**
         * Instantiates a new error type including a descriptive message.
         *
         * @param s
         *            the description of the error
         */
        BitstreamErrorType(final String s) {
            message = s;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return message;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getID() {
            return ordinal();
        }
    }
}
