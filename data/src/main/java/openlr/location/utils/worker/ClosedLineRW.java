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
package openlr.location.utils.worker;

import openlr.location.Location;
import openlr.location.LocationFactory;
import openlr.location.utils.LocationDataConstants;
import openlr.location.utils.LocationDataException;
import openlr.map.Line;
import openlr.map.MapDatabase;

import java.util.ArrayList;
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
public class ClosedLineRW extends AbstractRW {

    /** The Constant LINE_LINES_START_INDEX. */
    private static final int LINE_LINES_START_INDEX = 0;

    /**
     * {@inheritDoc}
     */
    @Override
    public final String createLocationString(
            final Location location) {
        StringBuilder sb = new StringBuilder();
        sb.append(LocationDataConstants.CLOSED_LINE_MARKER).append(
                LocationDataConstants.PART_DELIMITER);
        sb.append(location.getID());
        sb.append(LocationDataConstants.PART_DELIMITER);
        List<? extends Line> lines = location.getLocationLines();
        for (int i = 0; i < lines.size(); i++) {
            sb.append(lines.get(i).getID());
            if (i != lines.size() - 1) {
                sb.append(LocationDataConstants.FEATURE_DELIMITER);
            }
        }
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
        List<Line> lines = new ArrayList<Line>();
        Line prevLine = null;
        for (int i = LINE_LINES_START_INDEX; i < features.length; i++) {
            String s = features[i];
            long lineID = Long.parseLong(s);
            Line line = mdb.getLine(lineID);
            if (line == null) {
                throw new LocationDataException(id + ": line " + lineID
                        + " not found");
            }
            if (isConnected(prevLine, line)) {
                lines.add(line);
                prevLine = line;
            } else {
                throw new LocationDataException(id + ": line " + line
                        + " not connected to previous line");
            }
        }
        return LocationFactory.createClosedLineLocation(id, lines);
    }

}
