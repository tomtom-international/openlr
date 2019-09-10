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
 * Copyright (C) 2009-2012 TomTom International B.V.
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
package openlr.binary.data;

import openlr.binary.bitstream.BitstreamException;
import openlr.binary.bitstream.BitstreamInput;
import openlr.binary.bitstream.BitstreamOutput;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.log4j.Logger;

/**
 * The Class Header holds administrative data for a binary location reference in
 * the physical data format of OpenLR.
 * <p>
 * The header is used for the ARF (area flag), AF (attribute flag) and VER
 * (version) information. See OpenLR white paper for additional information.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class Header extends OpenLRBinaryInformation {

    /** logger */
    private static final Logger LOG = Logger.getLogger(Header.class);

    /** number of bits unused */
    // DLR e.V. (RE) changed into:
    private static final int RFU_BITS = 1;

    /** */
    // DLR e.V. (RE) added:
    private static final int AREA_FLAG_BIT0 = 1;

    /** */
    private static final int AREA_FLAG_BIT1 = 1;
    //

    /** number bits used for area flag */
    // DLR e.V. (RE) changed into:
    //private static final int AREA_FLAG_BITS = AREA_FLAG_BIT0 + AREA_FLAG_BIT1;

    /** number of bits used for attributes flag */
    private static final int ATTR_FLAG_BITS = 1;

    /** number of bits used for point flag */
    private static final int POINT_FLAG_BITS = 1;

    /** number of bits used for version */
    private static final int VERSION_BITS = 3;

    /** The area flag information. */
    private final int arf;

    /** The attribute flag information. */
    private final int af;

    /** The point flag information */
    private final int pf;

    /** The version information. */
    private final int ver;

    /**
     * Instantiates a new header.
     *
     * @param arfValue
     *            the arf value
     * @param afValue
     *            the af value
     * @param pfValue
     *            the pf value
     * @param verValue
     *            the ver value
     */
    public Header(final int arfValue, final int afValue, final int pfValue,
                  final int verValue) {
        arf = arfValue;
        af = afValue;
        pf = pfValue;
        ver = verValue;
    }

    /**
     * Fills the internal values with the values received from the input stream.
     *
     * @param ibs
     *            the input stream to read from
     *
     * @throws BitstreamException
     *             if a bitstream handling error occurred
     */
    public Header(final BitstreamInput ibs) throws BitstreamException {
        // DLR e.V. (RE) changed this routine to match the new two-bit area location
        // codes
        int rfu = ibs.getBits(RFU_BITS);
        if (rfu != RFU_VALUE) {
            throw new BitstreamException(
                    BitstreamException.BitstreamErrorType.CONST_VALUE_MISMATCH);
        }
        // Added by DLR e.V. (RE):
        int arf1 = ibs.getBits(AREA_FLAG_BIT0);
        //
        pf = ibs.getBits(POINT_FLAG_BITS);
        // Added by DLR e.V. (RE):
        int arf0 = ibs.getBits(AREA_FLAG_BIT1);
        arf = 2 * arf1 + arf0; // binary composition of the two bits
        // end of DLR´s addition
        af = ibs.getBits(ATTR_FLAG_BITS);
        ver = ibs.getBits(VERSION_BITS);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Header filled with data: arf - " + arf + "   af - " + af
                    + "   ver - " + ver);
        }
    }

    /** {@inheritDoc} */
    @Override
    public final void put(final BitstreamOutput obs) throws BitstreamException {
        // DLR e.V. (RE) changed this routine to match the new two-bit area location
        // codes
        obs.putBits(RFU_VALUE, RFU_BITS);
        // Added by DLR e.V. (RE):
        int arf1 = arf / 2;
        int arf0 = arf % 2;
        obs.putBits(arf1, AREA_FLAG_BIT1);
        //
        obs.putBits(pf, POINT_FLAG_BITS);
        // DLR e.V. (RE) changed to:
        obs.putBits(arf0, AREA_FLAG_BIT0);
        //
        obs.putBits(af, ATTR_FLAG_BITS);
        obs.putBits(ver, VERSION_BITS);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Header data written: arf - " + arf + "   af - " + af
                    + "   ver - " + ver);
        }
    }

    /**
     * Gets the area flag information.
     *
     * @return the arf
     */
    public final int getArf() {
        return arf;
    }

    /**
     * Gets the attribute flag information.
     *
     * @return the af
     */
    public final int getAf() {
        return af;
    }

    /**
     * Gets the point flag information.
     *
     * @return the pf
     */
    public final int getPf() {
        return pf;
    }

    /**
     * Gets the version information.
     *
     * @return the ver
     */
    public final int getVer() {
        return ver;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("version: ").append(ver).append(" point flag: ").append(pf)
                .append(" area flag: ").append(arf).append(" attribute flag: ")
                .append(af).append(" ellc: ");
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(arf).append(af).append(pf).append(ver);
        return builder.toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object obj) {
        if (!(obj instanceof Header)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Header other = (Header) obj;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(arf, other.arf).append(af, other.af)
                .append(pf, other.pf).append(ver, other.ver);
        return builder.isEquals();
    }
}
