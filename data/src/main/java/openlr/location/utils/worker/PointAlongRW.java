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
package openlr.location.utils.worker;

import openlr.location.Location;
import openlr.location.LocationFactory;
import openlr.location.data.Orientation;
import openlr.location.data.SideOfRoad;
import openlr.location.utils.LocationDataConstants;
import openlr.location.utils.LocationDataException;
import openlr.map.InvalidMapDataException;
import openlr.map.Line;
import openlr.map.MapDatabase;

import java.util.List;

/**
 * Location reader and writer.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class PointAlongRW extends AbstractRW {

    /** The Constant POINT_ALONG_LINE_INDEX. */
    private static final int POINT_ALONG_LINE_INDEX = 0;

    /** The Constant POINT_ALONG_OFFSET_INDEX. */
    private static final int POINT_ALONG_OFFSET_INDEX = 1;

    /** The Constant POINT_ALONG_SOR_INDEX. */
    private static final int POINT_ALONG_SOR_INDEX = 2;

    /** The Constant POINT_ALONG_ORI_INDEX. */
    private static final int POINT_ALONG_ORI_INDEX = 3;

    /**
     * {@inheritDoc}
     */
    @Override
    public final String createLocationString(
            final Location location) {
        Line l = location.getPoiLine();
        int poff = location.getPositiveOffset();
        SideOfRoad sor = location.getSideOfRoad();
        Orientation o = location.getOrientation();
        StringBuilder sb = new StringBuilder();
        sb.append(LocationDataConstants.POINT_ALONG_MARKER).append(
                LocationDataConstants.PART_DELIMITER);
        sb.append(location.getID());
        sb.append(LocationDataConstants.PART_DELIMITER);
        sb.append(l.getID());
        sb.append(LocationDataConstants.FEATURE_DELIMITER);
        sb.append(poff);
        sb.append(LocationDataConstants.FEATURE_DELIMITER);
        sb.append(sor.ordinal());
        sb.append(LocationDataConstants.FEATURE_DELIMITER);
        sb.append(o.ordinal());
        sb.append("\n");
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Location readLocationString(final String id,
                                             final String[] features, final MapDatabase mdb)
            throws LocationDataException {

        if (features.length < POINT_ALONG_ORI_INDEX + 1) {
            throw new LocationDataException(id + ": Invalid input string");
        }

        long lineID = Long.parseLong(features[POINT_ALONG_LINE_INDEX]);
        Line line = mdb.getLine(lineID);
        if (line == null) {
            throw new LocationDataException("line id not found " + lineID);
        }
        int offset = Integer.parseInt(features[POINT_ALONG_OFFSET_INDEX]);
        int so = Integer.parseInt(features[POINT_ALONG_SOR_INDEX]);
        List<SideOfRoad> sValues = SideOfRoad.getSideOfRoadValues();
        if (so < 0 || so > sValues.size()) {
            throw new LocationDataException(id + ": invalid side of road");
        }
        SideOfRoad s = sValues.get(so);
        int oo = Integer.parseInt(features[POINT_ALONG_ORI_INDEX]);
        List<Orientation> oValues = Orientation.getOrientationValues();
        if (oo < 0 || oo > oValues.size()) {
            throw new LocationDataException(id + ": invalid orientation");
        }
        Orientation o = oValues.get(oo);
        try {
            return LocationFactory
                    .createPointAlongLineLocationWithSideAndOrientation(id,
                            line, offset, s, o);
        } catch (InvalidMapDataException e) {
            throw new LocationDataException(id + ": " + e.getMessage());
        }
    }

}
