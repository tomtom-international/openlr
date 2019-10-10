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
package openlr.binary.decoder;

import openlr.binary.BinaryReturnCode;
import openlr.binary.OpenLRBinaryException;
import openlr.binary.bitstream.impl.ByteArrayBitstreamInput;
import openlr.binary.data.AbsoluteCoordinates;
import openlr.binary.data.RawBinaryData;
import openlr.map.GeoCoordinates;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.InvalidMapDataException;
import openlr.rawLocRef.RawGeoCoordLocRef;
import openlr.rawLocRef.RawLocationReference;

/**
 * The decoder for the geo coordinate location type.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class GeoCoordDecoder extends AbstractDecoder {

    /**
     * Decode data.
     *
     * @param ibs
     *            the ibs
     * @param totalBytes
     *            the total bytes
     * @param version
     *            the version
     * @return the raw binary location reference
     * @throws OpenLRBinaryException
     *             the open lr binary processing exception {@inheritDoc}
     */
    @Override
    public final RawLocationReference decodeData(final String id,
                                                 final ByteArrayBitstreamInput ibs, final int totalBytes,
                                                 final int version, final RawBinaryData binData)
            throws OpenLRBinaryException {
        AbsoluteCoordinates absCoord = null;
        absCoord = new AbsoluteCoordinates(ibs);

        GeoCoordinates geoCoord = null;
        try {
            geoCoord = new GeoCoordinatesImpl(
                    calculate32BitRepresentation(absCoord.getLon()),
                    calculate32BitRepresentation(absCoord.getLat()));
        } catch (InvalidMapDataException e) {
            throw new OpenLRBinaryException(BinaryReturnCode.INVALID_BINARY_DATA, e);
        }
        RawGeoCoordLocRef rawLocRef = new RawGeoCoordLocRef(id, geoCoord);
        if (binData != null) {
            binData.setBinaryAbsoluteCoordinates(absCoord);
        }
        return rawLocRef;
    }

}
