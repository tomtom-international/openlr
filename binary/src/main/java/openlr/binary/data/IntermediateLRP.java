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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class IntermediateLRP holds data for intermediate location reference
 * points as defined for a binary location reference in the physical data format
 * of OpenLR.
 * <p>
 * The intermediate LRP consists of a coordinate pair (lat/lon) and three
 * attributes (attrib1, attrib2 and attrib3). See OpenLR white paper for
 * additional information.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class IntermediateLRP extends AbstractLRP {

    /** logger */
    private static final Logger LOG = LoggerFactory.getLogger(IntermediateLRP.class);

    /** number of bits used for coordinates (relative) */
    private static final int COORD_BITS = 16;

    /** The attrib2. */
    private final Attr2 attrib2;

    /** The attrib3. */
    private final Attr3 attrib3;

    /**
     * Instantiates a new intermediate lrp.
     *
     * @param lonValue
     *            the lon value
     * @param latValue
     *            the lat value
     * @param a1
     *            the a1
     * @param a2
     *            the a2
     * @param a3
     *            the a3
     */
    public IntermediateLRP(final int lonValue, final int latValue,
                           final Attr1 a1, final Attr2 a2, final Attr3 a3) {
        super(COORD_BITS);
        lon = lonValue;
        lat = latValue;
        attrib1 = a1;
        attrib2 = a2;
        attrib3 = a3;
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
    public IntermediateLRP(final BitstreamInput ibs) throws BitstreamException {
        super(COORD_BITS);
        read(ibs);
        attrib1 = new Attr1(ibs);
        attrib2 = new Attr2(ibs);
        attrib3 = new Attr3(ibs);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Intermediate LRP filled with data: lon - " + lon
                    + "   lat - " + lat + "   and attributes 1, 2 and 3");
        }
    }

    /** {@inheritDoc} */
    public final void put(final BitstreamOutput obs) throws BitstreamException {
        putCoordinates(obs);
        attrib1.put(obs);
        attrib2.put(obs);
        attrib3.put(obs);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Intermediate LRP data written: lon - " + lon
                    + "   lat - " + lat + "   and attributes 1, 2 and 3");
        }
    }

    /**
     * Gets the attribute 2 information.
     *
     * @return the attrib2
     */
    public final Attr2 getAttrib2() {
        return attrib2;
    }

    /**
     * Gets the attribute 3 information.
     *
     * @return the attrib3
     */
    public final Attr3 getAttrib3() {
        return attrib3;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("lon: ").append(lon).append(" lat: ").append(lat)
                .append(", ");
        sb.append(attrib1).append(", ");
        sb.append(attrib2).append(", ");
        sb.append(attrib3);
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(attrib1).append(attrib2).append(attrib3).append(lon)
                .append(lat);
        return builder.toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object obj) {
        if (!(obj instanceof IntermediateLRP)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        IntermediateLRP other = (IntermediateLRP) obj;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(attrib1, other.attrib1).append(attrib2, other.attrib2)
                .append(attrib3, other.attrib3).append(lon, other.lon).append(
                lat, other.lat);
        return builder.isEquals();
    }
}
