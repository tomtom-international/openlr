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
package openlr.binary.data;

import openlr.binary.bitstream.BitstreamException;
import openlr.binary.bitstream.BitstreamException.BitstreamErrorType;
import openlr.binary.bitstream.BitstreamInput;
import openlr.binary.bitstream.BitstreamOutput;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.log4j.Logger;

/**
 * The Class Attr5 holds the data for the fifth attribute defined in the
 * physical data format of OpenLR.
 * <p>
 * This attribute is used for the FRC (functional road class) and FOW (form of
 * way) information. See OpenLR white paper for additional information.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author DLR e.V. (RE)
 */
public class Attr5 extends OpenLRBinaryInformation {

    /** logger. */
    private static final Logger LOG = Logger.getLogger(Attr5.class);

    /** The Constant RFU. */
    private static final int NR_RFU = 2;

    /** number of bits used for frc */
    private static final int FRC_BITS = 3;

    /** number of bits used for fow */
    private static final int FOW_BITS = 3;

    /** The functional road class information. */
    private final int frc;

    /** The form of way information. */
    private final int fow;

    /**
     * Instantiates a new attr5.
     *
     * @param frcValue the frc value
     * @param fowValue the fow value
     */
    public Attr5(final int frcValue, final int fowValue) {
        frc = frcValue;
        fow = fowValue;
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
    public Attr5(final BitstreamInput ibs) throws BitstreamException {
        int rfu = ibs.getBits(NR_RFU);
        if (rfu != RFU_VALUE) {
            throw new BitstreamException(BitstreamErrorType.CONST_VALUE_MISMATCH, "RFU in use");
        }
        frc = ibs.getBits(FRC_BITS);
        fow = ibs.getBits(FOW_BITS);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Attribute 5 filled with data: frc - " + frc + "   fow - "
                    + fow);
        }
    }

    /** {@inheritDoc} */
    public final void put(final BitstreamOutput obs) throws BitstreamException {
        obs.putBits(RFU_VALUE, NR_RFU);
        obs.putBits(frc, FRC_BITS);
        obs.putBits(fow, FOW_BITS);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Attribute 5 data written: frc - " + frc + " fow - "
                    + fow);
        }
    }

    /**
     * Gets the functional road class information.
     *
     * @return the frc
     */
    public final int getFrc() {
        return frc;
    }

    /**
     * Gets the form of way information.
     *
     * @return the fow
     */
    public final int getFow() {
        return fow;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("frc: ").append(frc).append(" fow: ")
                .append(fow);
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(frc).append(fow);
        return builder.toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object obj) {
        if (!(obj instanceof Attr5)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Attr5 other = (Attr5) obj;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(frc, other.frc)
                .append(fow, other.fow);
        return builder.isEquals();
    }

}
