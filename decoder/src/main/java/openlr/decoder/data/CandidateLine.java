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

import openlr.map.Line;

import java.io.Serializable;
import java.util.Comparator;

/**
 * The Class CandidateLine assigns a line in the road network with a rating
 * value. The rating value needs to be calculated outside of this class.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class CandidateLine {

    /** The Constant INVALID. */
    public static final CandidateLine INVALID = new CandidateLine();

    /** The line. */
    private final Line line;

    /** The rating value. */
    private int rating;

    /** The projection along the line in meter (from start). */
    private int projectionAlongLine = Integer.MIN_VALUE;

    /**
     * Instantiates a new candidate line with rating and a projection point.
     * This candidate line refers to a projection point on the line being
     * projection meter away from the start of the line.
     *
     * @param l the line
     * @param r the rating value
     * @param projection distance in meter between the projection point and the start of the line
     */
    public CandidateLine(final Line l, final int r, final int projection) {
        this.line = l;
        this.rating = r;
        this.projectionAlongLine = projection;
    }

    /**
     * Instantiates a new candidate line with rating.
     * This candidate line refers to the start or end of the line depending
     * on the position of the corresponding LRP.
     *
     * @param l the line
     * @param r the rating value
     */
    public CandidateLine(final Line l, final int r) {
        this.line = l;
        this.rating = r;
    }

    /**
     * Instantiates an invalid candidate line.
     */
    private CandidateLine() {
        this.line = null;
        this.rating = 0;
    }

    /**
     * Checks if is valid.
     *
     * @return true, if is valid
     */
    public final boolean isValid() {
        return line != null;
    }


    /**
     * Returns the candidate line.
     *
     * @return the line
     */
    public final Line getLine() {
        return line;
    }

    /**
     * Returns the rating.
     *
     * @return the rating
     */
    public final int getRating() {
        return rating;
    }

    /**
     * Gets the distance between the projection point and the start of the line.
     * Returns a value < 0 if line does not have a projection point.
     * @see #hasProjectionAlongLine()
     *
     * @return the distance between the projection point and the start of the line
     */
    public final int getProjectionAlongLine() {
        return projectionAlongLine;
    }

    /**
     * Delivers <code>true</code> if this candidate line has a projection point
     * set.
     *
     * @return <code>true</code> if a projection point is set, otherwise
     *         <code>false</code>
     */
    public final boolean hasProjectionAlongLine() {
        return projectionAlongLine >= 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("line: ").append(line.getID());
        sb.append(" rating: ").append(rating);
        sb.append(" projectionAlongLine: ").append(projectionAlongLine).append("m");
        return sb.toString();
    }

    /**
     * Checks for same line.
     *
     * @param cl the cl
     * @return true, if successful
     */
    public final boolean hasSameLine(final CandidateLine cl) {
        return line.getID() == cl.getLine().getID();
    }

    /**
     * Sets the new rating.
     *
     * @param rate the rate
     * @param lengthAlong the length along
     */
    public final void setNewRating(final int rate, final int lengthAlong) {
        rating = rate;
        projectionAlongLine = lengthAlong;
    }

    /**
     * The Class CandidateLineComparator compares two CandidateLines according to their rating.
     * This comparator prefers greater rating values.
     */
    public static class CandidateLineComparator implements Comparator<CandidateLine>, Serializable {

        /**
         * Serialization ID
         */
        private static final long serialVersionUID = 1561170372253675757L;

        /** {@inheritDoc} */
        @Override
        public final int compare(final CandidateLine o1, final CandidateLine o2) {
            if (o1.rating > o2.rating) {
                return -1;
            }
            if (o1.rating < o2.rating) {
                return 1;
            }
            return 0;
        }
    }

}
