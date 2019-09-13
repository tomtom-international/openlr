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
package openlr.decoder.data;

import cern.colt.function.IntObjectProcedure;
import cern.colt.map.OpenIntObjectHashMap;
import openlr.LocationReferencePoint;
import openlr.decoder.data.CandidateLine.CandidateLineComparator;

import java.util.Collections;
import java.util.List;

/**
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class CandidateLinesResultSet {

    /** The Constant CANDIDATE_LINE_COMPARATOR. */
    private static final CandidateLineComparator CANDIDATE_LINE_COMPARATOR = new CandidateLine.CandidateLineComparator();

    /** The list of candidate lines for this LRP. */
    private final OpenIntObjectHashMap candidateLines = new OpenIntObjectHashMap();


    /**
     * Put candidate lines.
     *
     * @param lrp the lrp
     * @param candidates the candidates
     */
    public final void putCandidateLines(final LocationReferencePoint lrp, final List<CandidateLine> candidates) {
        Collections.sort(candidates, CANDIDATE_LINE_COMPARATOR);
        candidateLines.put(lrp.getSequenceNumber(), candidates);
    }

    /**
     * Gets the ordered list of candidate lines (unmodifiable). The first
     * elements has the highest rating and if there are elements with the same
     * rating the order of these elements is undefined.
     *
     * @param lrp the lrp
     * @return the ordered list of candidate lines
     */
    @SuppressWarnings("unchecked")
    public final List<CandidateLine> getCandidateLines(final LocationReferencePoint lrp) {
        if (candidateLines.containsKey(lrp.getSequenceNumber())) {
            return (List<CandidateLine>) candidateLines.get(lrp.getSequenceNumber());
        } else {
            return Collections.EMPTY_LIST;
        }
    }


    /**
     * Gets the number of candidate lines.
     *
     * @param lrp the lrp
     * @return the number of candidate lines
     */
    @SuppressWarnings("unchecked")
    public final int getNumberOfCandidateLines(final LocationReferencePoint lrp) {
        int number = 0;
        List<CandidateLine> list = (List<CandidateLine>) candidateLines.get(lrp.getSequenceNumber());
        if (list != null) {
            number = list.size();
        }
        return number;
    }

    /**
     * Gets the ordered list of candidate lines (unmodifiable). The first
     * elements has the highest rating and if there are elements with the same
     * rating the order of these elements is undefined.
     *
     * @param lrp the lrp
     * @return the ordered list of candidate lines
     */
    @SuppressWarnings("unchecked")
    public final CandidateLine getBestCandidateLine(final LocationReferencePoint lrp) {
        CandidateLine line = null;
        List<CandidateLine> list = (List<CandidateLine>) candidateLines.get(lrp.getSequenceNumber());
        if (list != null && !list.isEmpty()) {
            line = list.get(0);
        }
        return line;
    }

    /**
     * Gets the candidate line at index.
     *
     * @param lrp the lrp
     * @param idx the idx
     * @return the candidate line at index
     */
    @SuppressWarnings("unchecked")
    public final CandidateLine getCandidateLineAtIndex(final LocationReferencePoint lrp, final int idx) {
        CandidateLine line = null;
        List<CandidateLine> list = (List<CandidateLine>) candidateLines.get(lrp.getSequenceNumber());
        if (list != null && !list.isEmpty() && idx < list.size()) {
            line = list.get(idx);
        }
        return line;
    }


    /**
     * All candidate lines found.
     *
     * @return true, if successful
     */
    @SuppressWarnings("unchecked")
    public final boolean allCandidateLinesFound() {
        return candidateLines.forEachPair(new IntObjectProcedure() {
            @Override
            public boolean apply(final int first, final Object second) {
                List<CandidateLine> list = (List<CandidateLine>) second;
                if (list == null || list.isEmpty()) {
                    return false;
                }
                return true;
            }
        });
    }

    /**
     * To debug.
     *
     * @param lrp the lrp
     * @return the string
     */
    @SuppressWarnings("unchecked")
    public final String toDebug(final LocationReferencePoint lrp) {
        StringBuilder sb = new StringBuilder();
        List<CandidateLine> list = (List<CandidateLine>) candidateLines.get(lrp.getSequenceNumber());
        for (int i = 0; i < list.size(); i++) {
            CandidateLine cl = list.get(i);
            sb.append(cl.getRating()).append(": ").append(cl.getLine().getID()).append("\n");
        }
        return sb.toString();
    }


}
