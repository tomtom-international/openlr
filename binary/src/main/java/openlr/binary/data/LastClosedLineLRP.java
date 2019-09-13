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
import openlr.binary.bitstream.BitstreamInput;
import openlr.binary.bitstream.BitstreamOutput;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.log4j.Logger;

/**
 * The last closed line lrp finishes a closed line location reference.
 * This lrp just directs to a line which ends at the start node of the
 * location.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author DLR e.V. (RE)
 */
public class LastClosedLineLRP extends OpenLRBinaryInformation {

    /** logger */
    private static final Logger LOG = Logger.getLogger(LastClosedLineLRP.class);

    /** The attrib5 information. */
    private final Attr5 attrib5;

    /** The attrib2. */
    private final Attr6 attrib6;

    /**
     * Instantiates a new last closed line lrp.
     *
     * @param a5 the attribute 5
     * @param a6 the attribute 6
     */
    public LastClosedLineLRP(final Attr5 a5, final Attr6 a6) {
        attrib5 = a5;
        attrib6 = a6;

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
    public LastClosedLineLRP(final BitstreamInput ibs) throws BitstreamException {
        attrib5 = new Attr5(ibs);
        attrib6 = new Attr6(ibs);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Intermediate LRP filled with data: attributes 5 and 6");
        }
    }


    /**
     * {@inheritDoc}
     */
    public final void put(final BitstreamOutput obs) throws BitstreamException {
        attrib5.put(obs);
        attrib6.put(obs);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Intermediate LRP data written: attributes 5 and 6");
        }
    }

    /**
     * Gets the attribute 5 information.
     *
     * @return the attrib5
     */
    public final Attr5 getAttrib5() {
        return attrib5;
    }

    /**
     * Gets the attribute 6 information.
     *
     * @return the attrib2
     */
    public final Attr6 getAttrib6() {
        return attrib6;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(attrib5).append(", ").append(attrib6);
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(attrib5).append(attrib6);
        return builder.toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object obj) {
        if (!(obj instanceof LastClosedLineLRP)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        LastClosedLineLRP other = (LastClosedLineLRP) obj;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(attrib5, other.attrib5).append(attrib6, other.attrib6);
        return builder.isEquals();
    }
}
