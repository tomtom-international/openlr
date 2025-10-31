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
package openlr.binary.encoder;

import openlr.LocationReference;
import openlr.LocationType;
import openlr.PhysicalFormatException;
import openlr.binary.BinaryReturnCode;
import openlr.binary.ByteArray;
import openlr.binary.OpenLRBinaryConstants;
import openlr.binary.OpenLRBinaryException;
import openlr.binary.OpenLRBinaryException.PhysicalFormatError;
import openlr.binary.bitstream.impl.ByteArrayBitstreamOutput;
import openlr.binary.data.AbsoluteCoordinates;
import openlr.binary.data.Header;
import openlr.binary.data.RelativeCoordinates;
import openlr.binary.impl.LocationReferenceBinaryImpl;
import openlr.map.GeoCoordinates;
import openlr.rawLocRef.RawLocationReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * The encoder for the polygon coordinate location type.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author DLR e.V. (RE)
 */
public class PolygonEncoder extends AbstractEncoder {

    /** Logging */
    private static final Logger LOG = LoggerFactory.getLogger(PolygonEncoder.class);

    /**
     * Encode data.
     *
     * @param rawLocRef
     *            the raw loc ref
     * @param version
     *            the version
     * @return the location reference
     */
    @Override
    public final LocationReference encodeData(
            final RawLocationReference rawLocRef, final int version) {
        if (rawLocRef == null) {
            return new LocationReferenceBinaryImpl("",
                    BinaryReturnCode.MISSING_DATA, LocationType.POLYGON,
                    version);
        }
        String id = rawLocRef.getID();
        List<? extends GeoCoordinates> cornerPoints = rawLocRef
                .getCornerPoints();
        if (cornerPoints == null) {
            return new LocationReferenceBinaryImpl(id,
                    BinaryReturnCode.MISSING_DATA, LocationType.POLYGON,
                    version);
        }
        if (version < OpenLRBinaryConstants.BINARY_VERSION_3) {
            return new LocationReferenceBinaryImpl(id,
                    BinaryReturnCode.INVALID_VERSION, LocationType.POLYGON,
                    version);
        }

        LocationReference lr = null;
        try {
            byte[] bd = generateBinaryPolygonLocation(cornerPoints, version);
            ByteArray ba = new ByteArray(bd.clone());
            lr = new LocationReferenceBinaryImpl(id, ba);
        } catch (PhysicalFormatException e) {
            lr = new LocationReferenceBinaryImpl(rawLocRef.getID(),
                    BinaryReturnCode.INVALID_BINARY_DATA,
                    LocationType.POLYGON, version);
        }
        return lr;
    }

    /**
     * Generate binary polygon location.
     *
     * @param cornerPoints the list of geo coordinates of the corners of the polygon
     * @param version the version
     * @return the byte[]
     * @throws PhysicalFormatException the physical format exception
     */
    private byte[] generateBinaryPolygonLocation(
            final List<? extends GeoCoordinates> cornerPoints, final int version)
            throws PhysicalFormatException {
        GeoCoordinates prevCoord = cornerPoints.get(0);
        AbsoluteCoordinates firstCornerPoint = generateAbsCoord(prevCoord);
        List<RelativeCoordinates> relCornerCoords = new ArrayList<RelativeCoordinates>();

        for (int i = 1; i < cornerPoints.size(); ++i) {
            GeoCoordinates geoCoord = cornerPoints.get(i);
            int relRepValLon = getRelativeRepresentation(
                    prevCoord.getLongitudeDeg(), geoCoord.getLongitudeDeg());
            int relRepValLat = getRelativeRepresentation(
                    prevCoord.getLatitudeDeg(), geoCoord.getLatitudeDeg());

            if (fitsInto2Bytes(relRepValLon) && fitsInto2Bytes(relRepValLat)) {
                RelativeCoordinates relCornerCoord = new RelativeCoordinates(
                        relRepValLon, relRepValLat);
                relCornerCoords.add(relCornerCoord);
                prevCoord = geoCoord;
            } else {
                throw new OpenLRBinaryException(
                        PhysicalFormatError.INVALID_RELATIVE_COORD);
            }
        }
        Header header = generateHeader(version, LocationType.POLYGON, false);
        byte[] data = null;
        ByteArrayBitstreamOutput out = new ByteArrayBitstreamOutput();
        header.put(out);
        firstCornerPoint.put(out);
        for (RelativeCoordinates relCoord : relCornerCoords) {
            relCoord.put(out);
        }
        data = out.getData();

        if (LOG.isDebugEnabled()) {
            LOG.debug("binary data size (bytes): " + data.length);
        }
        return data;
    }
}
