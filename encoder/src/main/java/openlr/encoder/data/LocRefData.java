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

import openlr.LocationType;
import openlr.Offsets;
import openlr.location.Location;

import java.util.ArrayList;
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
public class LocRefData {

    /** The original location. */
    private final Location originalLocation;

    /** The adjusted location. */
    private Location adjustedLocation;

    /** The expansion. */
    private ExpansionData expansion = ExpansionData.NO_EXPANSION;

    /** The loc ref points. */
    private List<LocRefPoint> locRefPoints = new ArrayList<LocRefPoint>();

    /** The reduced pos off. */
    private int reducedPosOff = 0;

    /** The reduced neg off. */
    private int reducedNegOff = 0;

    /**
     * Instantiates a new loc ref data.
     *
     * @param origin
     *            the origin
     */
    public LocRefData(final Location origin) {
        originalLocation = origin;
    }

    /**
     * Sets the adjusted location.
     *
     * @param loc
     *            the new adjusted location
     */
    public final void setAdjustedLocation(final Location loc) {
        adjustedLocation = loc;
    }

    /**
     * Sets the expansion.
     *
     * @param eData
     *            the new expansion
     */
    public final void setExpansion(final ExpansionData eData) {
        expansion = eData;
    }

    /**
     * Gets the location.
     *
     * @return the location
     */
    public final Location getLocation() {
        if (adjustedLocation != null) {
            return adjustedLocation;
        }
        return originalLocation;
    }

    /**
     * Gets the original location.
     *
     * @return the original location
     */
    public final Location getOriginalLocation() {
        return originalLocation;
    }

    /**
     * Gets the expansion data.
     *
     * @return the expansion data
     */
    public final ExpansionData getExpansionData() {
        return expansion;
    }

    /**
     * Gets the loc ref points.
     *
     * @return the loc ref points
     */
    public final List<LocRefPoint> getLocRefPoints() {
        return new ArrayList<LocRefPoint>(locRefPoints);
    }

    /**
     * Sets the loc ref points.
     *
     * @param points
     *            the new loc ref points
     */
    public final void setLocRefPoints(final List<LocRefPoint> points) {
        locRefPoints.clear();
        locRefPoints.addAll(points);
    }

    /**
     * Gets the iD.
     *
     * @return the iD
     */
    public final String getID() {
        return originalLocation.getID();
    }

    /**
     * Gets the location type.
     *
     * @return the location type
     */
    public final LocationType getLocationType() {
        return originalLocation.getLocationType();
    }

    /**
     * Adds the to reduced pos off.
     *
     * @param value the value
     */
    public final void addToReducedPosOff(final int value) {
        reducedPosOff += value;
    }

    /**
     * Adds the to reduced neg off.
     *
     * @param value the value
     */
    public final void addToReducedNegOff(final int value) {
        reducedNegOff += value;
    }

    /**
     * Gets the offsets.
     *
     * @return the offsets
     */
    public final Offsets getOffsets() {
        // get original offsets and add expansion
        int pOff = getLocation().getPositiveOffset()
                + expansion.getExpansionLengthStart() - reducedPosOff;
        int nOff = getLocation().getNegativeOffset()
                + expansion.getExpansionLengthEnd() - reducedNegOff;
        return new OffsetData(pOff, nOff);
    }

    /**
     * Gets the reduced pos off.
     *
     * @return the reduced pos off
     */
    public final int getReducedPosOff() {
        return reducedPosOff;
    }

    /**
     * Gets the reduced neg off.
     *
     * @return the reduced neg off
     */
    public final int getReducedNegOff() {
        return reducedNegOff;
    }

}
