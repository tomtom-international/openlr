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
package openlr.xml.impl;

import openlr.Offsets;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Implementation of the {@link Offsets} interface.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class OffsetsXmlImpl implements Offsets {

    /**
     * The positive offset of the binary data (0 if no positive offset
     * available).
     */
    private final int pOffset;

    /**
     * The negative offset of the binary data (0 if not negative offset
     * available).
     */
    private final int nOffset;


    /**
     * Instantiates new offsets.
     *
     * @param poff the positive offset
     * @param noff the negative offset
     */
    public OffsetsXmlImpl(final int poff,
                          final int noff) {
        if (poff < 0) {
            throw new IllegalArgumentException("invalid positive offset");
        }
        if (noff < 0) {
            throw new IllegalArgumentException("invalid negative offset");
        }
        pOffset = poff;
        nOffset = noff;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean hasPositiveOffset() {
        return pOffset != 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean hasNegativeOffset() {
        return nOffset != 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getPositiveOffset(final int length) {
        if (hasPositiveOffset()) {
            return pOffset;
        }
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getNegativeOffset(final int length) {
        if (hasNegativeOffset()) {
            return nOffset;
        }
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" posOff: ");
        sb.append(pOffset);
        sb.append(" negOff: ");
        sb.append(nOffset);
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(pOffset).append(nOffset);
        return builder.toHashCode();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object obj) {
        if (!(obj instanceof OffsetsXmlImpl)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        OffsetsXmlImpl other = (OffsetsXmlImpl) obj;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(pOffset, other.pOffset).append(nOffset, other.nOffset);
        return builder.isEquals();
    }

}
