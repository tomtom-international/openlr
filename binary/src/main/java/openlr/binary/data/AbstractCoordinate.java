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
package openlr.binary.data;

import openlr.binary.bitstream.BitstreamException;
import openlr.binary.bitstream.BitstreamInput;
import openlr.binary.bitstream.BitstreamOutput;

/**
 * Base class for different coordinates types.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 *
 */
public abstract class AbstractCoordinate extends OpenLRBinaryInformation {

    /** The coord bits. */
    private final int coordBits;
    /** The longitude information. */
    protected int lon;
    /** The latitude information. */
    protected int lat;


    /**
     * Instantiates a new abstract coordinate.
     *
     * @param nrBits the nr bits
     */
    public AbstractCoordinate(final int nrBits) {
        coordBits = nrBits;
    }

    /**
     * Gets the longitude coordinate information.
     *
     * @return the lon
     */
    public final int getLon() {
        return lon;
    }

    /**
     * Read in coordinate data.
     *
     * @param ibs the ibs
     * @throws BitstreamException the bitstream exception
     */
    protected final void read(final BitstreamInput ibs) throws BitstreamException {
        lon = ibs.getSignedBits(coordBits);
        lat = ibs.getSignedBits(coordBits);
    }


    /**
     * Gets the latitude coordinate information.
     *
     * @return the lat
     */
    public final int getLat() {
        return lat;
    }


    /**
     * Put coordinates.
     *
     * @param obs the obs
     * @throws BitstreamException the bitstream exception
     */
    public final void putCoordinates(final BitstreamOutput obs) throws BitstreamException {
        obs.putBits(lon, coordBits);
        obs.putBits(lat, coordBits);
    }

}
