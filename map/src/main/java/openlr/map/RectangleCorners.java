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
 * Copyright (C) 2009-2012 TomTom International B.V.
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
 *  Copyright (C) 2009-2012 TomTom International B.V.
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
package openlr.map;

import openlr.map.utils.GeometryUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class RectangleCorners.
 */
public class RectangleCorners {

    /** The Constant ZERO_CIRCLE. */
    private static final double ZERO_CIRCLE = 0;

    /** The Constant HALF_CIRCLE. */
    private static final double HALF_CIRCLE = 180;

    /** The Constant QUARTER_CIRCLE. */
    private static final double QUARTER_CIRCLE = 90;

    /** The Constant QUARTER_CIRCLE. */
    private static final double THREE_QUARTER_CIRCLE = 270;

    /** The lower left. */
    private final GeoCoordinates lowerLeft;

    /** The lower right. */
    private final GeoCoordinates lowerRight;

    /** The upper left. */
    private final GeoCoordinates upperLeft;

    /** The upper right. */
    private final GeoCoordinates upperRight;

    /**
     * Instantiates a new rectangle corners.
     *
     * @param ll
     *            the ll
     * @param ur
     *            the ur
     * @throws InvalidMapDataException
     */
    public RectangleCorners(final GeoCoordinates ll, final GeoCoordinates ur) throws InvalidMapDataException {
        if (!GeometryUtils.checkCoordinateBounds(ll.getLongitudeDeg(), ll.getLatitudeDeg())) {
            throw new InvalidMapDataException("rectangle lower left coordinates are invalid");
        }
        if (!GeometryUtils.checkCoordinateBounds(ur.getLongitudeDeg(), ur.getLatitudeDeg())) {
            throw new InvalidMapDataException("rectangle upper right coordinates are invalid");
        }
        lowerLeft = ll;
        upperRight = ur;
        // check for valid input
        if (ll.getLongitudeDeg() > ur.getLongitudeDeg()
                || ll.getLatitudeDeg() > ur.getLatitudeDeg()) {
            throw new InvalidMapDataException("rectangle coordinates are invalid");
        }
        upperLeft = GeometryUtils.intersectStraights(ll.getLongitudeDeg(),
                ll.getLatitudeDeg(), ZERO_CIRCLE, ur.getLongitudeDeg(),
                ur.getLatitudeDeg(), THREE_QUARTER_CIRCLE);
        lowerRight = GeometryUtils.intersectStraights(ll.getLongitudeDeg(),
                ll.getLatitudeDeg(), QUARTER_CIRCLE, ur.getLongitudeDeg(),
                ur.getLatitudeDeg(), HALF_CIRCLE);
    }

    /**
     * Instantiates a new rectangle corners.
     *
     * @param ll
     *            the ll
     * @param ur
     *            the ur
     * @param lr
     *            the lr
     * @param ul
     *            the ul
     * @throws InvalidMapDataException
     */
    public RectangleCorners(final GeoCoordinates ll, final GeoCoordinates ur,
                            final GeoCoordinates lr, final GeoCoordinates ul) throws InvalidMapDataException {
        if (ll.getLongitudeDeg() > ur.getLongitudeDeg()
                || ll.getLatitudeDeg() > ur.getLatitudeDeg()) {
            throw new InvalidMapDataException("rectangle coordinates are invalid");
        }
        lowerLeft = ll;
        upperRight = ur;
        upperLeft = ul;
        lowerRight = lr;
    }

    /**
     * Gets the lower left.
     *
     * @return the lower left
     */
    public final GeoCoordinates getLowerLeft() {
        return lowerLeft;
    }

    /**
     * Gets the lower right.
     *
     * @return the lower right
     */
    public final GeoCoordinates getLowerRight() {
        return lowerRight;
    }

    /**
     * Gets the upper left.
     *
     * @return the upper left
     */
    public final GeoCoordinates getUpperLeft() {
        return upperLeft;
    }

    /**
     * Gets the upper right.
     *
     * @return the upper right
     */
    public final GeoCoordinates getUpperRight() {
        return upperRight;
    }

    /**
     * Gets the corner points (anti-clockwise starting from lower left
     * coordinate).
     *
     * @return the corner points
     */
    public final List<GeoCoordinates> getCornerPoints() {
        List<GeoCoordinates> coords = new ArrayList<GeoCoordinates>();
        coords.add(lowerLeft);
        coords.add(lowerRight);
        coords.add(upperRight);
        coords.add(upperLeft);
        return coords;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(lowerLeft).append(lowerRight).append(upperLeft)
                .append(upperRight);
        return builder.toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof RectangleCorners)) {
            return false;
        }
        if (o == this) {
            return true;
        }
        RectangleCorners other = (RectangleCorners) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(lowerLeft, other.lowerLeft)
                .append(lowerRight, other.lowerRight)
                .append(upperLeft, other.upperLeft)
                .append(upperLeft, other.upperLeft);
        return builder.isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("lower left: ").append(lowerLeft).append("\n");
        sb.append("lower right: ").append(lowerRight).append("\n");
        sb.append("upper right: ").append(upperRight).append("\n");
        sb.append("upper left: ").append(upperLeft).append("\n");
        return sb.toString();
    }

}
