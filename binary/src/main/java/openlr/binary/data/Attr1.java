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
 * The Class Attr1 holds the data for the first attribute defined in the physical data format of OpenLR.
 * <p>
 * This attribute is used for the FRC (functional road class) and FOW (form of way) information. See OpenLR
 * white paper for additional information.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class Attr1 extends OpenLRBinaryInformation {

    /** logger. */
    private static final Logger LOG = Logger.getLogger(Attr1.class);

    /** The Constant SIDE_OR_ORIENTATION_BITS. */
    private static final int SIDE_OR_ORIENTATION_BITS = 2;

    /** number of bits used for frc */
    private static final int FRC_BITS = 3;

    /** number of bits used for fow */
    private static final int FOW_BITS = 3;

    /** The functional road class information. */
    private final int frc;

    /** The form of way information. */
    private final int fow;

    /** The side or orientation. */
    private final int sideOrOrientation;


    /**
     * Instantiates a new attr1.
     *
     * @param frcValue the frc value
     * @param fowValue the fow value
     * @param sorValue the sor value
     */
    public Attr1(final int frcValue, final int fowValue, final int sorValue) {
        frc = frcValue;
        fow = fowValue;
        sideOrOrientation = sorValue;
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
    public Attr1(final BitstreamInput ibs) throws BitstreamException {
        sideOrOrientation = ibs.getBits(SIDE_OR_ORIENTATION_BITS);
        frc = ibs.getBits(FRC_BITS);
        fow = ibs.getBits(FOW_BITS);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Attribute 1 filled with data: frc - " + frc + "   fow - " + fow);
        }
    }


    /** {@inheritDoc} */
    public final void put(final BitstreamOutput obs) throws BitstreamException {
        obs.putBits(sideOrOrientation, SIDE_OR_ORIENTATION_BITS
        );
        obs.putBits(frc, FRC_BITS);
        obs.putBits(fow, FOW_BITS);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Attribute 1 data written: frc - " + frc + "   fow - " + fow);
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
     * Gets the side or orientation.
     *
     * @return the side or orientation
     */
    public final int getSideOrOrientation() {
        return sideOrOrientation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("frc: ").append(frc).append(" fow: ").append(fow);
        sb.append(" side/orientation: ").append(sideOrOrientation);
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(frc).append(fow).append(sideOrOrientation);
        return builder.toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object obj) {
        if (!(obj instanceof Attr1)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Attr1 other = (Attr1) obj;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(frc, other.frc).append(fow, other.fow).append(sideOrOrientation, other.sideOrOrientation);
        return builder.isEquals();
    }

}

