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
package openlr.location;

import openlr.LocationType;
import openlr.map.Line;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.Collections;
import java.util.List;

/**
 * Implementation of the location interface for line locations.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class LineLocation extends AbstractLocation {

    /** The location as an ordered list of lines (from start to end) */
    private final List<Line> lines;

    /**
     * The positive offset indicates the distance in meters between start point
     * of the first line and the real start point of the location.
     */
    private final int posOff;

    /**
     * The negative offset indicates the distance in meters between end point of
     * the last line and the real end point of the location.
     */
    private final int negOff;

    /**
     * ************* LINE LOCATION *****************************.
     *
     * @param idString the id string
     * @param loc the loc
     * @param pOff the off
     * @param nOff the n off
     */

    /**
     * Instantiates a new location with a unique key and the location as a list
     * of lines including offset information used to find the precise location
     * on the location path.
     *
     * @param loc
     *            the location as a list of lines
     * @param pOff
     *            the distance between the start of the location and the start
     *            of the precise location
     * @param nOff
     *            the distance between the end of the location and the end of
     *            the precise location
     * @param idString
     *            the unique ID
     */
    protected LineLocation(final String idString, final List<? extends Line> loc,
                           final int pOff, final int nOff) {
        super(idString, LocationType.LINE_LOCATION);
        lines = Collections.unmodifiableList(loc);
        negOff = nOff;
        posOff = pOff;
    }

    /**
     * Instantiates a new location with a unique key and the location as a list
     * of lines. The positive and negative offset are treated as 0.
     *
     * @param idString the unique ID
     * @param loc the location as a list of lines
     */
    LineLocation(final String idString, final List<Line> loc) {
        this(idString, loc, 0, 0);
    }

    /********************** COPY CONSTRUCTOR *************************/

    /**
     * Instantiates a copy of location l.
     *
     * @param l
     *            the location be copied
     */
    public LineLocation(final LineLocation l) {
        super(l);
        lines = Collections.unmodifiableList(l.getLocationLines());
        negOff = l.getNegativeOffset();
        posOff = l.getPositiveOffset();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<Line> getLocationLines() {
        return lines;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getPositiveOffset() {
        return posOff;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getNegativeOffset() {
        return negOff;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean hasPositiveOffset() {
        return posOff > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean hasNegativeOffset() {
        return negOff > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id: ").append(id);
        sb.append(" loc type: ").append(locType);
        sb.append(" #lines: ").append(lines.size());
        sb.append(" [");
        Line currentLine;
        for (int i = 0; i < lines.size(); i++) {
            currentLine = lines.get(i);
            if (currentLine != null) {
                sb.append(lines.get(i).getID());
            } else {
                sb.append("null");
            }
            if (i != lines.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        sb.append(" posOff: ").append(posOff);
        sb.append(" negOff: ").append(negOff);
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int calculateHashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(posOff).append(lines).append(negOff)
                .append(locType);
        return builder.toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object obj) {
        if (!(obj instanceof LineLocation)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        LineLocation other = (LineLocation) obj;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(posOff, other.posOff).append(lines,
                other.lines).append(negOff,
                other.negOff).append(locType, other.locType);
        return builder.isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        return getHashCode();
    }

}
