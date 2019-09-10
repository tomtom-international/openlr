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
 * The Class LastLRP holds data for the last location reference point as defined
 * for a binary location reference in the physical data format of OpenLR.
 * <p>
 * The last LRP consists of a coordinate pair (lat/lon) and two attributes
 * (attrib1 and attrib4). See OpenLR white paper for additional information.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class LastLRP extends AbstractLRP {

    /** logger */
    private static final Logger LOG = Logger.getLogger(LastLRP.class);

    /** number of bits used for coordinates (relative) */
    private static final int COORD_BITS = 16;

    /** The attrib4 information. */
    private final Attr4 attrib4;

    /**
     * Instantiates a new last lrp.
     *
     * @param lonValue
     *            the lon value
     * @param latValue
     *            the lat value
     * @param a1
     *            the a1
     * @param a4
     *            the a4
     */
    public LastLRP(final int lonValue, final int latValue, final Attr1 a1,
                   final Attr4 a4) {
        super(COORD_BITS);
        lon = lonValue;
        lat = latValue;
        attrib1 = a1;
        attrib4 = a4;
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
    public LastLRP(final BitstreamInput ibs) throws BitstreamException {
        super(COORD_BITS);
        read(ibs);
        attrib1 = new Attr1(ibs);
        attrib4 = new Attr4(ibs);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Last LRP filled with data: lon - " + lon + "   lat - "
                    + lat + "   and attributes 1 and 4");
        }
    }

    /** {@inheritDoc} */
    public final void put(final BitstreamOutput obs) throws BitstreamException {
        putCoordinates(obs);
        attrib1.put(obs);
        attrib4.put(obs);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Last LRP data written: lon - " + lon + "   lat - " + lat
                    + "   and attributes 1 and 4");
        }
    }

    /**
     * Gets the attribute4 information.
     *
     * @return the attrib4
     */
    public final Attr4 getAttrib4() {
        return attrib4;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("lon: ").append(lon).append(" lat: ").append(lat)
                .append(", ");
        sb.append(attrib4);
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(lon).append(lat).append(attrib1).append(attrib4);
        return builder.toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object obj) {
        if (!(obj instanceof LastLRP)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        LastLRP other = (LastLRP) obj;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(lon, other.lon).append(lat, other.lat).append(attrib1,
                other.attrib1).append(attrib4, other.attrib4);
        return builder.isEquals();
    }
}
