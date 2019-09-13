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
import openlr.StatusCode;
import openlr.location.data.AffectedLines;
import openlr.location.data.Orientation;
import openlr.location.data.SideOfRoad;
import openlr.map.GeoCoordinates;
import openlr.map.Line;

import java.util.List;

/**
 * The location class stores data about the location which needs to be encoded.
 * The location as a list of lines might be longer than the precise location and
 * the precise location can be determined by using the positive and negative
 * offsets. The offset values are measured in meters.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
abstract class AbstractLocation implements Location {

    /** The unique ID. */
    protected final String id;

    /** The loc type. */
    protected final LocationType locType;

    /** The ret code. */
    protected final StatusCode retCode;

    /** the hash code (to avoid multiple calculations) */
    private int hashCode;

    /**
     * Instantiates a new abstract location.
     *
     * @param l
     *            the l
     */
    AbstractLocation(final Location l) {
        id = l.getID();
        locType = l.getLocationType();
        retCode = l.getReturnCode();
    }

    /**
     * Instantiates a new abstract location.
     *
     * @param idValue the id value
     * @param lt the location type
     */
    AbstractLocation(final String idValue, final LocationType lt) {
        id = idValue;
        locType = lt;
        retCode = null;
    }

    /**
     * Instantiates a new abstract location.
     *
     * @param idValue
     *            the id value
     * @param lt
     *            the location type
     * @param rt
     *            the rt
     */
    AbstractLocation(final String idValue, final LocationType lt, final StatusCode rt) {
        id = idValue;
        locType = lt;
        retCode = rt;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getID() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final LocationType getLocationType() {
        return locType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final StatusCode getReturnCode() {
        return retCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isValid() {
        return retCode == null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract int hashCode();

    /**
     * Gets the hashCode. This methods calculates the hashCode and stores it.
     *
     * @return the hashCode
     */
    protected final int getHashCode() {
        if (hashCode == 0) {
            hashCode = calculateHashCode();
        }
        return hashCode;
    }

    /**
     * Calculate the hash code which can be used to be stored if several hashCode calls
     * are required.
     *
     * @return the int
     */
    public abstract int calculateHashCode();

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract boolean equals(Object obj);

    /******************************** default implementations ****************************/

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Line> getLocationLines() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPositiveOffset() {
        return -1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNegativeOffset() {
        return -1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Line getPoiLine() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPositiveOffset() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasNegativeOffset() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeoCoordinates getAccessPoint() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Orientation getOrientation() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeoCoordinates getPointLocation() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SideOfRoad getSideOfRoad() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AffectedLines getAffectedLines() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GeoCoordinates> getCornerPoints() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeoCoordinates getLowerLeftPoint() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeoCoordinates getUpperRightPoint() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeoCoordinates getCenterPoint() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getRadius() {
        return -1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfColumns() {
        return -1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfRows() {
        return -1;
    }

}
