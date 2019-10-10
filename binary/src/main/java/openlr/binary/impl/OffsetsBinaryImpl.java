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
package openlr.binary.impl;

import openlr.Offsets;
import openlr.binary.OpenLRBinaryConstants;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Implementation of the interface {@link Offsets}.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class OffsetsBinaryImpl implements Offsets {

    /** The Constant PERCENTAGE. */
    private static final int PERCENTAGE = 100;

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

    /** The p off relative. */
    private final float pOffRelative;

    /** The n off relative. */
    private final float nOffRelative;

    /** The version. */
    private final int version;

    /**
     * Instantiates new offsets .
     *
     * @param poff
     *            the positive offset
     * @param noff
     *            the negative offset
     */
    public OffsetsBinaryImpl(final int poff, final int noff) {
        pOffset = poff;
        nOffset = noff;
        version = OpenLRBinaryConstants.BINARY_VERSION_2;
        pOffRelative = 0.0f;
        nOffRelative = 0.0f;
    }

    /**
     * Instantiates a new offsets impl.
     *
     * @param poff
     *            the poff
     * @param noff
     *            the noff
     */
    public OffsetsBinaryImpl(final float poff, final float noff) {
        pOffset = 0;
        nOffset = 0;
        version = OpenLRBinaryConstants.BINARY_VERSION_3;
        pOffRelative = poff;
        nOffRelative = noff;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean hasPositiveOffset() {
        return (pOffset != 0 || pOffRelative != 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean hasNegativeOffset() {
        return (nOffset != 0 || nOffRelative != 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getPositiveOffset(final int length) {
        if (hasPositiveOffset()) {
            if (version == OpenLRBinaryConstants.BINARY_VERSION_2) {
                return pOffset;
            } else if (version == OpenLRBinaryConstants.BINARY_VERSION_3) {
                return Math.round(pOffRelative * length / PERCENTAGE);
            }
        }
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getNegativeOffset(final int length) {
        if (hasNegativeOffset()) {
            if (version == OpenLRBinaryConstants.BINARY_VERSION_2) {
                return nOffset;
            } else if (version == OpenLRBinaryConstants.BINARY_VERSION_3) {
                return Math.round(nOffRelative * length / PERCENTAGE);
            }
        }
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("version: ").append(version);
        sb.append(" posOff: ");
        if (version == OpenLRBinaryConstants.BINARY_VERSION_2) {
            sb.append(pOffset);
        } else if (version == OpenLRBinaryConstants.BINARY_VERSION_3) {
            sb.append(pOffRelative);
        }
        sb.append(" negOff: ");
        if (version == OpenLRBinaryConstants.BINARY_VERSION_2) {
            sb.append(nOffset);
        } else if (version == OpenLRBinaryConstants.BINARY_VERSION_3) {
            sb.append(nOffRelative);
        }
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(version).append(pOffRelative).append(nOffRelative)
                .append(pOffset).append(nOffset);
        return builder.toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object obj) {
        if (!(obj instanceof OffsetsBinaryImpl)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        OffsetsBinaryImpl other = (OffsetsBinaryImpl) obj;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(version, other.version).append(pOffRelative,
                other.pOffRelative).append(nOffRelative, other.nOffRelative)
                .append(pOffset, other.pOffset).append(nOffset, other.nOffset);
        return builder.isEquals();
    }

}
