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
import openlr.location.utils.LocationDataException;
import openlr.map.Line;
import openlr.map.MapDatabase;
import openlr.map.utils.IteratorHelper;

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
public abstract class AbstractRW {

    /**
     * Creates the location string.
     *
     * @param location the location
     * @return the string
     */
    public abstract String createLocationString(final Location location);

    /**
     * Read location string.
     *
     * @param id the id
     * @param features the features
     * @param mdb the mdb
     * @return the location
     * @throws LocationDataException the location data exception
     */
    public abstract Location readLocationString(final String id,
                                                final String[] features, final MapDatabase mdb) throws LocationDataException;

    /**
     * Checks if two lines are connected connected.
     *
     * @param l1
     *            the l1
     * @param l2
     *            the l2
     * @return true, if is connected
     */
    protected final boolean isConnected(final Line l1, final Line l2) {
        if (l1 == null || l2 == null) {
            return true;
        }
        if (IteratorHelper.contains(l1.getNextLines(), l2)) {
            return true;
        }
        return false;
    }

}
