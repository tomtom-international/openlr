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
package openlr.binary.data;

import openlr.binary.OpenLRBinaryConstants;
import openlr.binary.bitstream.BitstreamException;
import openlr.binary.bitstream.BitstreamInput;
import openlr.binary.bitstream.BitstreamOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Class EncodedRadius holds the encoded radius information as defined in
 * the physical data format for OpenLR. See OpenLR white paper for additional
 * information.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author DLR e.V. (RE)
 */
public class Radius extends OpenLRBinaryInformation {

    /** The Constant MAX_RADIUS_SMALL. */
    private static final long MAX_RADIUS_SMALL = (long) Math.pow(2,
            OpenLRBinaryConstants.SMALL_RADIUS_BITS);

    /** The Constant MAX_RADIUS_MEDIUM. */
    private static final long MAX_RADIUS_MEDIUM = (long) Math.pow(2,
            OpenLRBinaryConstants.MEDIUM_RADIUS_BITS);

    /** The Constant MAX_RADIUS_LARGE. */
    private static final long MAX_RADIUS_LARGE = (long) Math.pow(2,
            OpenLRBinaryConstants.LARGE_RADIUS_BITS);

    /** The Constant MAX_RADIUS_EXTRA_LARGE. */
    private static final long MAX_RADIUS_EXTRA_LARGE = (long) Math.pow(2,
            OpenLRBinaryConstants.EXTRA_LARGE_RADIUS_BITS);
    /** logger */
    private static final Logger LOG = LogManager.getLogger(Radius.class);
    /** The radius (up to 4 bytes) according to OpenLR white paper */
    private long radius;

    /**
     * Fills the internal values with the values received from the input stream.
     *
     * @param r
     *            the radius value
     */
    public Radius(final long r) {
        radius = r;
        if (LOG.isDebugEnabled()) {
            LOG.debug("Radius filled with data: radius - " + radius);
        }
    }

    /**
     * Fills the internal values with the values received from the input stream.
     *
     * @param ibs
     *            the input stream to read from
     * @param type
     *            the type of radius
     *
     * @throws BitstreamException
     *             if a bitstream handling error occurred
     */
    public Radius(final BitstreamInput ibs, final RadiusType type)
            throws BitstreamException {
        switch (type) {
            case SMALL: // r represents radius in meters
                radius = intToLong(ibs
                        .getBits(OpenLRBinaryConstants.SMALL_RADIUS_BITS));
                break;
            case MEDIUM:
                radius = intToLong(ibs
                        .getBits(OpenLRBinaryConstants.MEDIUM_RADIUS_BITS));
                break;
            case LARGE:
                radius = intToLong(ibs
                        .getBits(OpenLRBinaryConstants.LARGE_RADIUS_BITS));
                break;
            case EXTRA_LARGE:
                radius = intToLong(ibs
                        .getBits(OpenLRBinaryConstants.EXTRA_LARGE_RADIUS_BITS));
                break;
            default:
                throw new BitstreamException(
                        BitstreamException.BitstreamErrorType.INVALIDVALUERANGE);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Radius filled with data: radius - " + radius);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void put(final BitstreamOutput rbs) throws BitstreamException {
        if (radius <= MAX_RADIUS_SMALL) {
            rbs.putBits(longToInt(radius),
                    OpenLRBinaryConstants.SMALL_RADIUS_BITS);
        } else if (radius <= MAX_RADIUS_MEDIUM) {
            rbs.putBits(longToInt(radius),
                    OpenLRBinaryConstants.MEDIUM_RADIUS_BITS);
        } else if (radius <= MAX_RADIUS_LARGE) {
            rbs.putBits(longToInt(radius),
                    OpenLRBinaryConstants.LARGE_RADIUS_BITS);
        } else if (radius <= MAX_RADIUS_EXTRA_LARGE) {
            rbs.putBits(longToInt(radius),
                    OpenLRBinaryConstants.EXTRA_LARGE_RADIUS_BITS);
        } else {
            throw new BitstreamException(
                    BitstreamException.BitstreamErrorType.INVALIDVALUERANGE);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Radius data written: radius - " + radius);
        }
    }

    /**
     * Gets the radius information.
     *
     * @return the radius
     */
    public final long getRadius() {
        return radius;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("radius: ").append(radius);
        return sb.toString();
    }

    /**
     * Int to long.
     *
     * @param i
     *            the i
     * @return the long
     */
    private long intToLong(final int i) {
        if (i < 0) {
            long l = 0;
            l = i & (MAX_RADIUS_EXTRA_LARGE - 1);
            return l;
        } else {
            return (long) i;
        }
    }

    /**
     * Long to int.
     *
     * @param l
     *            the l
     * @return the int
     * @throws BitstreamException
     *             the bitstream exception
     */
    private int longToInt(final long l) throws BitstreamException {
        return (int) l;
    }

    /**
     * The Enum RadiusType.
     */
    public enum RadiusType {

        /** Small radius (BINARY_VERSION_3: up to 255m) */
        SMALL(1),
        /** Medium radius (BINARY_VERSION_3: up to 65536m) */
        MEDIUM(2),
        /** Large radius (BINARY_VERSION_3: up to 16777216m) */
        LARGE(3),
        /** Extra large radius (BINARY_VERSION_3: up to 4294967296m) */
        EXTRA_LARGE(4),
        /** Unknown radius type */
        UNKNOWN(0);

        /** The nr bytes. */
        private final int nrBytes;

        /**
         * Instantiates a new radius type.
         *
         * @param bytes the bytes
         */
        private RadiusType(final int bytes) {
            nrBytes = bytes;
        }

        /**
         * Resolve radius.
         *
         * @param bytes the bytes
         * @return the radius type
         */
        public static final RadiusType resolveRadius(final int bytes) {
            RadiusType rt = UNKNOWN;
            for (RadiusType r : values()) {
                if (r.nrBytes == bytes) {
                    rt = r;
                    break;
                }
            }
            return rt;
        }
    }
}
