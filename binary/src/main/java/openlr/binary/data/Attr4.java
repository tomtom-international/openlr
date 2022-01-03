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

import openlr.binary.bitstream.BitstreamException;
import openlr.binary.bitstream.BitstreamInput;
import openlr.binary.bitstream.BitstreamOutput;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Class Attr4 holds the data for the fourth attribute defined in the
 * physical data format of OpenLR.
 * <p>
 * This attribute is used for the POffF (positive offset flag), NOffF (negative
 * offset flag) and BEAR (bearing) information. See OpenLR white paper for
 * additional information.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class Attr4 extends OpenLRBinaryInformation {

    /** logger */
    private static final Logger LOG = LogManager.getLogger(Attr4.class);

    /** number of unused bits */
    private static final int RFU_BITS = 1;

    /** number of bits used for positive offset flag */
    private static final int POFFF_BITS = 1;

    /** number of bits used for negative offset flag */
    private static final int NOFFF_BITS = 1;

    /** number of bits used for bearing */
    private static final int BEAR_BITS = 5;

    /** The positive offset flag information. */
    private final int pOffsetf;

    /** The negative offset flag information. */
    private final int nOffsetf;

    /** The bearing information. */
    private final int bear;

    /**
     * Instantiates a new attr4.
     *
     * @param pofffValue
     *            the pofff value
     * @param nofffValue
     *            the nofff value
     * @param bearValue
     *            the bear value
     */
    public Attr4(final int pofffValue, final int nofffValue, final int bearValue) {
        pOffsetf = pofffValue;
        nOffsetf = nofffValue;
        bear = bearValue;
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
    public Attr4(final BitstreamInput ibs) throws BitstreamException {
        int rfu = ibs.getBits(RFU_BITS);
        if (rfu != RFU_VALUE) {
            throw new BitstreamException(
                    BitstreamException.BitstreamErrorType.CONST_VALUE_MISMATCH);
        }
        pOffsetf = ibs.getBits(POFFF_BITS);
        nOffsetf = ibs.getBits(NOFFF_BITS);
        bear = ibs.getBits(BEAR_BITS);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Attribute 4 filled with data: pOffsetf - " + pOffsetf
                    + "   nOffsetf - " + nOffsetf + "   bear - " + bear);
        }
    }

    /** {@inheritDoc} */
    public final void put(final BitstreamOutput obs) throws BitstreamException {
        obs.putBits(RFU_VALUE, RFU_BITS);
        obs.putBits(pOffsetf, POFFF_BITS);
        obs.putBits(nOffsetf, NOFFF_BITS);
        obs.putBits(bear, BEAR_BITS);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Attribute 4 data written: pOffsetf - " + pOffsetf
                    + "   nOffsetf - " + nOffsetf + "   bear - " + bear);
        }
    }

    /**
     * Gets the positive offset flag information.
     *
     * @return the pOffsetf
     */
    public final int getPOffsetf() {
        return pOffsetf;
    }

    /**
     * Gets the negative offset flag information.
     *
     * @return the nOffsetf
     */
    public final int getNOffsetf() {
        return nOffsetf;
    }

    /**
     * Gets the bearing information.
     *
     * @return the bear
     */
    public final int getBear() {
        return bear;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("bearing: ").append(bear).append(" posOff flag: ").append(
                pOffsetf).append(" negOff flag: ").append(nOffsetf);
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(pOffsetf).append(nOffsetf).append(bear);
        return builder.toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object obj) {
        if (!(obj instanceof Attr4)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Attr4 other = (Attr4) obj;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(pOffsetf, other.pOffsetf).append(nOffsetf,
                other.nOffsetf).append(bear, other.bear);
        return builder.isEquals();
    }
}
