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
package openlr.binary.decoder;

import openlr.binary.BinaryReturnCode;
import openlr.binary.OpenLRBinaryConstants;
import openlr.binary.OpenLRBinaryException;
import openlr.binary.OpenLRBinaryException.PhysicalFormatError;
import openlr.binary.bitstream.impl.ByteArrayBitstreamInput;
import openlr.binary.data.AbsoluteCoordinates;
import openlr.binary.data.AbstractCoordinate;
import openlr.binary.data.RawBinaryData;
import openlr.binary.data.RelativeCoordinates;
import openlr.map.GeoCoordinates;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.InvalidMapDataException;
import openlr.rawLocRef.RawLocationReference;
import openlr.rawLocRef.RawRectangleLocRef;

/**
 * The decoder for the rectangle location type.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author DLR e.V. (RE)
 */
public class RectangleDecoder extends AbstractDecoder {
    /**
     * {@inheritDoc}
     */
    @Override
    public final RawLocationReference decodeData(final String id,
                                                 final ByteArrayBitstreamInput ibs, final int totalBytes,
                                                 final int version, final RawBinaryData binData)
            throws OpenLRBinaryException {
        AbsoluteCoordinates lowerLeftCoord = new AbsoluteCoordinates(ibs);
        AbstractCoordinate upperRightCoord = null;
        GeoCoordinates upperRight = null;
        GeoCoordinates lowerLeft = null;
        try {
            lowerLeft = new GeoCoordinatesImpl(
                    calculate32BitRepresentation(lowerLeftCoord.getLon()),
                    calculate32BitRepresentation(lowerLeftCoord.getLat()));
            if (version < OpenLRBinaryConstants.BINARY_VERSION_3) {
                throw new OpenLRBinaryException(
                        PhysicalFormatError.INVALID_VERSION);
            }
            if (totalBytes == OpenLRBinaryConstants.RECTANGLE_SIZE) {
                upperRightCoord = new RelativeCoordinates(ibs);
                double lon = calculate32BitRepresentation(lowerLeftCoord
                        .getLon())
                        + (upperRightCoord.getLon() / OpenLRBinaryConstants.DECA_MICRO_DEG_FACTOR);
                double lat = calculate32BitRepresentation(lowerLeftCoord
                        .getLat())
                        + (upperRightCoord.getLat() / OpenLRBinaryConstants.DECA_MICRO_DEG_FACTOR);
                upperRight = new GeoCoordinatesImpl(lon, lat);
                if (binData != null) {
                    binData.setBinaryAbsoluteCoordinatesUR(upperRightCoord);
                }
            } else if (totalBytes == OpenLRBinaryConstants.LARGE_RECTANGLE_SIZE) {
                upperRightCoord = new AbsoluteCoordinates(ibs);
                upperRight = new GeoCoordinatesImpl(
                        calculate32BitRepresentation(upperRightCoord.getLon()),
                        calculate32BitRepresentation(upperRightCoord.getLat()));
                if (binData != null) {
                    binData.setBinaryAbsoluteCoordinatesUR(upperRightCoord);
                }
            } else {
                throw new OpenLRBinaryException(
                        PhysicalFormatError.INVALID_BINARY_DATA);
            }
        } catch (InvalidMapDataException e) {
            throw new OpenLRBinaryException(
                    BinaryReturnCode.INVALID_BINARY_DATA, e);
        }

        if (binData != null) {
            binData.setBinaryAbsoluteCoordinatesLL(lowerLeftCoord);
        }
        RawLocationReference rawLocRef = new RawRectangleLocRef(id, lowerLeft,
                upperRight);
        return rawLocRef;
    }
}
