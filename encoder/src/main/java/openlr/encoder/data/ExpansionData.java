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

import openlr.map.Line;
import openlr.map.utils.PathUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class ExpansionData {

    /** The Constant EMPTY_LIST. */
    private static final List<Line> EMPTY_LIST = Collections.emptyList();

    /** The Constant NO_EXPANSION. */
    public static final ExpansionData NO_EXPANSION = new ExpansionData(
            EMPTY_LIST, EMPTY_LIST);

    /**
     * The list of lines being an expansion of the start. This expansion is a
     * uniquely connected stretch which ends at the start of the location
     */
    private List<Line> expansionStart = new ArrayList<Line>();

    /**
     * The list of lines being an expansion of the dest. This expansion is a
     * uniquely connected stretch which starts at the end of the location
     */
    private List<Line> expansionEnd = new ArrayList<Line>();

    /** The length in meter expanded at the start of the location. */
    private int lengthAtStart = 0;

    /** The length in meter expanded at the end of the location. */
    private int lengthAtEnd = 0;

    /**
     * Instantiates a new expanded location with the original location l and the
     * expanded paths at start and end.
     *
     * @param expStart
     *            the expansion at start
     * @param expEnd
     *            the expansion at end
     */
    public ExpansionData(final List<Line> expStart, final List<Line> expEnd) {
        expansionEnd = new ArrayList<Line>(expEnd);
        expansionStart = new ArrayList<Line>(expStart);
        lengthAtStart = PathUtils.getLength(expansionStart);
        lengthAtEnd = PathUtils.getLength(expansionEnd);
    }

    /**
     * Modify expansion at start. This needs to be done if the offset placement
     * is incorrect.
     *
     * @param toRemove
     *            the to remove
     * @return true, if successful
     */
    public final boolean modifyExpansionAtStart(final List<Line> toRemove) {
        for (Line l : toRemove) {
            if (expansionStart.contains(l)) {
                expansionStart.remove(l);
                lengthAtStart -= l.getLineLength();
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * Modify expansion at end. This needs to be done if the offset placement is
     * incorrect.
     *
     * @param toRemove
     *            the to remove
     * @return true, if successful
     */
    public final boolean modifyExpansionAtEnd(final List<Line> toRemove) {
        for (Line l : toRemove) {
            if (expansionEnd.contains(l)) {
                expansionEnd.remove(l);
                lengthAtEnd -= l.getLineLength();
            } else {
                return false;
            }
        }

        return true;
    }

    /**
     * Gets the expansion at the start. The List will be empty if no expansion
     * at the start is available.
     *
     * @return the expansion at start
     */
    public final List<Line> getExpansionStart() {
        return expansionStart;
    }

    /**
     * Gets the expansion at the end. The List will be empty if no expansion at
     * the end is available.
     *
     * @return the expansion at end
     */
    public final List<Line> getExpansionEnd() {
        return expansionEnd;
    }

    /**
     * Checks if an expansion at the start is available.
     *
     * @return true, if available
     */
    public final boolean hasExpansionStart() {
        return !expansionStart.isEmpty();
    }

    /**
     * Checks if an expansion at the end is available.
     *
     * @return true, if available
     */
    public final boolean hasExpansionEnd() {
        return !expansionEnd.isEmpty();
    }

    /**
     * Gets the expansion length at the start of the location.
     *
     * @return the expansion length at the start
     */
    public final int getExpansionLengthStart() {
        return lengthAtStart;
    }

    /**
     * Gets the expansion length at the end of the location.
     *
     * @return the expansion length at the end
     */
    public final int getExpansionLengthEnd() {
        return lengthAtEnd;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" expansion start: ").append(lengthAtStart).append("m [");
        for (int i = 0; i < expansionStart.size(); i++) {
            sb.append(expansionStart.get(i).getID());
            if (i != expansionStart.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        sb.append(" expansion end: ").append(lengthAtEnd).append("m [");
        for (int i = 0; i < expansionEnd.size(); i++) {
            sb.append(expansionEnd.get(i).getID());
            if (i != expansionEnd.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

}
