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
import openlr.location.data.Orientation;
import openlr.location.data.SideOfRoad;
import openlr.map.GeoCoordinates;
import openlr.map.InvalidMapDataException;
import openlr.map.Line;

/**
 * Abstract class for point locations.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public abstract class PointLocation extends AbstractLocation {

    /**
     * The positive offset indicates the distance between start point of the
     * first line and the real start point of the location.
     */
    protected final int posOff;

    /** The line for point. */
    protected final Line lineForPoint;

    /** The access. */
    protected final GeoCoordinates access;

    /** The side of road. */
    protected final SideOfRoad sideOfRoad;

    /** The orientation. */
    protected final Orientation orientation;

    /**
     * Instantiates a new point location.
     *
     * @param idString the id string
     * @param lt the lt
     * @param l the l
     * @param poff the poff
     * @param s the s
     * @param o the o
     * @throws InvalidMapDataException the invalid map data exception
     */
    protected PointLocation(final String idString, final LocationType lt, final Line l, final int poff,
                            final SideOfRoad s, final Orientation o) throws InvalidMapDataException {
        super(idString, lt);
        posOff = poff;
        lineForPoint = l;
        if (poff == 0) {
            access = l.getStartNode().getGeoCoordinates();
        } else {
            access = l.getGeoCoordinateAlongLine(poff);
        }
        sideOfRoad = s;
        orientation = o;
    }

    /**
     * Copy constructor.
     *
     * @param l the l
     */
    public PointLocation(final PointLocation l) {
        super(l);
        posOff = l.getPositiveOffset();
        lineForPoint = l.getPoiLine();
        access = l.getAccessPoint();
        sideOfRoad = l.getSideOfRoad();
        orientation = l.getOrientation();
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
    public final Line getPoiLine() {
        return lineForPoint;
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
    public final GeoCoordinates getAccessPoint() {
        return access;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Orientation getOrientation() {
        return orientation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final SideOfRoad getSideOfRoad() {
        return sideOfRoad;
    }


}
