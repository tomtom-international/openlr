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
package openlr.decoder;

import openlr.OpenLRProcessingException;

/**
 * The Class OpenLRDecoderProcessingException will be used to indicate a major decoding
 * error. The decoding process cannot be continued. 
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class OpenLRDecoderProcessingException extends OpenLRProcessingException {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -4621121884132829111L;

    /**
     * Instantiates a new openlr decoder processing exception.
     *
     * @param e the error
     */
    public OpenLRDecoderProcessingException(final DecoderProcessingError e) {
        super(e);
    }

    /**
     * Instantiates a new openlr decoder processing exception.
     *
     * @param e the error
     * @param s the error message
     */
    public OpenLRDecoderProcessingException(final DecoderProcessingError e, final String s) {
        super(e, s);
    }

    /**
     * Instantiates a new openlr decoder processing exception.
     *
     * @param e the error
     * @param t previously thrown exception
     */
    public OpenLRDecoderProcessingException(final DecoderProcessingError e, final Throwable t) {
        super(e, t);
    }

    /**
     * Instantiates a new openlr decoder processing exception.
     *
     * @param e the error
     * @param s the error message
     * @param t previously thrown exception
     */
    public OpenLRDecoderProcessingException(final DecoderProcessingError e, final String s, final Throwable t) {
        super(e, s, t);
    }

    /**
     * The Enum DecoderRuntimeError.
     */
    public enum DecoderProcessingError implements ErrorCode {

        /** No physical decoder detected. */
        NO_PHYSICAL_DECODER_DETECTED("no physical decoder detected"),

        /** route disconnected */
        ROUTE_DISCONNECTED("route is not connected"),

        /** the location type is not supported */
        INVALID_LOCATION_TYPE("location type is invalid"),

        /** The INVALI d_ lo c_ re f_ data. */
        INVALID_LOC_REF_DATA("invalid location reference data"),

        /** The ROUT e_ resolv e_ error. */
        ROUTE_RESOLVE_ERROR("route cannot be resolved"),

        /** The INVALI d_ ma p_ data. */
        INVALID_MAP_DATA("geo coordinates are out of bounds");

        /** The description. */
        private final String description;

        /**
         * Instantiates a new decoder processing error.
         *
         * @param s the s
         */
        private DecoderProcessingError(final String s) {
            description = s;
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
}
