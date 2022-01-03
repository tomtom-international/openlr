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
import openlr.binary.bitstream.BitstreamException.BitstreamErrorType;
import openlr.binary.bitstream.BitstreamInput;
import openlr.binary.bitstream.BitstreamOutput;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Class Attr2 holds the data for the second attribute defined in the
 * physical data format of OpenLR.
 * <p>
 * This attribute is used for the LFRCNP (lowest functional road class to next
 * point) and BEAR (bearing) information. See OpenLR white paper for additional
 * information.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class Attr6 extends OpenLRBinaryInformation {

    /** logger */
    private static final Logger LOG = LogManager.getLogger(Attr2.class);

    /** number of bits used for lfrcnp */
    private static final int NR_RFU = 3;

    /** number of bits used for bear */
    private static final int BEAR_BITS = 5;

    /** The bearing information. */
    private final int bear;


    /**
     * Instantiates a new attr6.
     *
     * @param bearValue the bear value
     */
    public Attr6(final int bearValue) {
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
    public Attr6(final BitstreamInput ibs) throws BitstreamException {
        int rfu = ibs.getBits(NR_RFU);
        if (rfu != RFU_VALUE) {
            throw new BitstreamException(BitstreamErrorType.CONST_VALUE_MISMATCH, "RFU in use");
        }
        bear = ibs.getBits(BEAR_BITS);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Attribute 6 filled with data: bear - " + bear);
        }
    }


    /** {@inheritDoc} */
    public final void put(final BitstreamOutput obs) throws BitstreamException {
        obs.putBits(RFU_VALUE, NR_RFU);
        obs.putBits(bear, BEAR_BITS);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Attribute 6 data written: bear - " + bear);
        }
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
        sb.append(" bearing: ").append(bear);
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(bear);
        return builder.toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object obj) {
        if (!(obj instanceof Attr6)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Attr6 other = (Attr6) obj;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(bear, other.bear);
        return builder.isEquals();
    }

}
