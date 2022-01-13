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
package openlr.decoder.data;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;


/**
 * The Class CandidatePair represents a pair of candidates lines in combination with a score
 * value indicating the relevance of this pair. Each LRP might have resolved several candidate
 * lines and for each consecutive LRP pair combinations of candidate lines might be ordered in
 * order to determine the most promising pair to start the route calculation in between.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class CandidateLinePair {

    /** The start index. */
    private int startIndex;

    /** The destination index. */
    private int destIndex;

    /** The score. */
    private long score;

    /**
     * Instantiates a new candidate pair. The candidate lines are referenced by their index in the
     * candidate lines ranking.
     *
     * @param i1 the index of the start line
     *
     * @param i2 the index of the destination line
     * @param s the score for this pair of candidate lines
     */
    public CandidateLinePair(final int i1, final int i2, final long s) {
        startIndex = i1;
        destIndex = i2;
        score = s;
    }

    /**
     * Gets the start index.
     *
     * @return the start index
     */
    public final int getStartIndex() {
        return startIndex;
    }

    /**
     * Gets the destination index.
     *
     * @return the destination index
     */
    public final int getDestIndex() {
        return destIndex;
    }

    /**
     * Gets the score.
     *
     * @return the score
     */
    public final long getScore() {
        return score;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        return String.format("startIdx[%d] - destIdx[%d], score: [%d]", startIndex, destIndex, score);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CandidateLinePair that = (CandidateLinePair) o;
        return startIndex == that.startIndex && destIndex == that.destIndex && score == that.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startIndex, destIndex, score);
    }
}
