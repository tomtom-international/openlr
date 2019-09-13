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
package openlr.rawLocRef;

import openlr.LocationReferencePoint;
import openlr.LocationType;
import openlr.Offsets;
import openlr.location.data.Orientation;
import openlr.location.data.SideOfRoad;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
public abstract class RawPointLocRef extends RawLocationReference {

    /** The points. */
    protected final List<LocationReferencePoint> points;

    /** The offsets. */
    protected final Offsets offsets;

    /** The orientation. */
    protected final Orientation orientation;

    /** The side of road. */
    protected final SideOfRoad sideOfRoad;

    /**
     * Instantiates a new raw point loc ref.
     *
     * @param idValue the id value
     * @param lt the lt
     * @param lrp the lrp
     * @param lrp2 the lrp2
     * @param od the od
     * @param sor the sor
     * @param o the o
     */
    protected RawPointLocRef(final String idValue, final LocationType lt, final LocationReferencePoint lrp,
                             final LocationReferencePoint lrp2, final Offsets od,
                             final SideOfRoad sor,
                             final Orientation o) {
        super(idValue, lt);
        List<LocationReferencePoint> temp = new ArrayList<LocationReferencePoint>();
        temp.add(lrp);
        temp.add(lrp2);
        points = Collections.unmodifiableList(temp);
        offsets = od;
        orientation = o;
        sideOfRoad = sor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<LocationReferencePoint> getLocationReferencePoints() {
        return points;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Offsets getOffsets() {
        return offsets;
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
