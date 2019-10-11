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
package openlr.encoder.data;

import openlr.Offsets;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * The Class OffsetData collects data about positive and negative offset. The
 * positive offset is used to shorten the location path from its start and the
 * negative offset is used to shorten the location path from its end. The
 * remaining part of the path is the precise location.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class OffsetData implements Offsets {

    /** The positive offset (precise start of the location). */
    private final int positiveOff;

    /** The negative offset (precise end of the location). */
    private final int negativeOff;

    /**
     * Instantiates a new offset data.
     *
     * @param poff
     *            the positive offset
     * @param noff
     *            the negative offset
     */
    public OffsetData(final int poff, final int noff) {
        positiveOff = poff;
        negativeOff = noff;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getPositiveOffset(final int length) {
        return positiveOff;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getNegativeOffset(final int length) {
        return negativeOff;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean hasNegativeOffset() {
        return negativeOff > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean hasPositiveOffset() {
        return positiveOff > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("posOff: ").append(positiveOff);
        sb.append(" negOff: ").append(negativeOff);
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(positiveOff).append(negativeOff);
        return builder.toHashCode();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object obj) {
        if (!(obj instanceof OffsetData)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        OffsetData other = (OffsetData) obj;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(positiveOff, other.positiveOff).append(negativeOff, other.negativeOff);
        return builder.isEquals();
    }

}
