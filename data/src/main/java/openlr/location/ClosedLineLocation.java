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
 * Implementation of the location interface for closed line locations.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author DLR e.V. (RE)
 */
public class ClosedLineLocation extends AbstractLocation {
    /**
     * The location as an ordered list of lines (from start to end, where the
     * end node of the last line must coincide with the start node of the first
     * line )
     */
    private final List<Line> lines;


    /**
     * Instantiates a new location with a unique key and the closed line
     * location as a list of lines.
     *
     * @param idString the unique ID
     * @param loc the location as a list of lines
     */
    protected ClosedLineLocation(final String idString,
                                 final List<? extends Line> loc) {
        super(idString, LocationType.CLOSED_LINE);
        lines = Collections.unmodifiableList(loc);
    }

    /********************** COPY CONSTRUCTOR *************************/

    /**
     * Instantiates a copy of location l.
     *
     * @param l
     *            the location be copied
     */
    ClosedLineLocation(final ClosedLineLocation l) {
        super(l);
        lines = Collections.unmodifiableList(l.getLocationLines());
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
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id: ").append(id);
        sb.append(" loc type: ").append(locType);
        sb.append(" #lines: ").append(lines.size());
        sb.append(" [");
        for (int i = 0; i < lines.size(); i++) {
            sb.append(lines.get(i).getID());
            if (i != lines.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int calculateHashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(locType).append(lines);
        return builder.toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object obj) {
        if (!(obj instanceof ClosedLineLocation)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        ClosedLineLocation other = (ClosedLineLocation) obj;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(lines, other.lines).append(locType,
                other.locType);
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
