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
import openlr.binary.bitstream.impl.ByteArrayBitstreamOutput;
import openlr.binary.data.*;
import openlr.binary.impl.LocationReferenceBinaryImpl;
import openlr.map.GeoCoordinates;
import openlr.rawLocRef.RawLocationReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The encoder for the rectangle coordinate location type.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author DLR e.V. (RE)
 */
public class GridEncoder extends AbstractEncoder {

    /** Logging */
    private static final Logger LOG = LoggerFactory.getLogger(GridEncoder.class);

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
                    BinaryReturnCode.MISSING_DATA, LocationType.GRID, version);
        }
        String id = rawLocRef.getID();
        GeoCoordinates leftmost = rawLocRef.getLowerLeftPoint();
        GeoCoordinates rightmost = rawLocRef.getUpperRightPoint();
        int ncols = rawLocRef.getNumberOfColumns();
        int nrows = rawLocRef.getNumberOfRows();
        if (leftmost == null || rightmost == null || ncols <= 0 || nrows <= 0) {
            return new LocationReferenceBinaryImpl(id,
                    BinaryReturnCode.MISSING_DATA, LocationType.GRID, version);
        }
        if (version < OpenLRBinaryConstants.BINARY_VERSION_3) {
            return new LocationReferenceBinaryImpl(id,
                    BinaryReturnCode.INVALID_VERSION, LocationType.GRID,
                    version);
        }

        LocationReference lr = null;
        try {
            byte[] bd = generateBinaryGridLocation(leftmost, rightmost, ncols,
                    nrows, version);
            ByteArray ba = new ByteArray(bd.clone());
            lr = new LocationReferenceBinaryImpl(id, ba);
        } catch (PhysicalFormatException e) {
            lr = new LocationReferenceBinaryImpl(rawLocRef.getID(),
                    BinaryReturnCode.INVALID_BINARY_DATA,
                    LocationType.GRID, version);
        }
        return lr;
    }

    /**
     * Generate binary rectangle location.
     *
     * @param leftmost the leftmost
     * @param rightmost the rightmost
     * @param ncols the number of columns
     * @param nrows the number of rows
     * @param version the version
     * @return the byte[]
     * @throws PhysicalFormatException the physical format exception
     */
    private byte[] generateBinaryGridLocation(final GeoCoordinates leftmost,
                                              final GeoCoordinates rightmost, final int ncols, final int nrows,
                                              final int version) throws PhysicalFormatException {
        AbsoluteCoordinates coordLeftmost = generateAbsCoord(leftmost);
        AbstractCoordinate coordRightmost = null;
        int relRepValLon = getRelativeRepresentation(
                leftmost.getLongitudeDeg(), rightmost.getLongitudeDeg());
        int relRepValLat = getRelativeRepresentation(leftmost.getLatitudeDeg(),
                rightmost.getLatitudeDeg());

        if (fitsInto2Bytes(relRepValLon) && fitsInto2Bytes(relRepValLat)) {
            coordRightmost = new RelativeCoordinates(relRepValLon, relRepValLat);
        } else {
            coordRightmost = generateAbsCoord(rightmost);
        }
        Header header = generateHeader(version, LocationType.GRID, false);
        byte[] data = null;
        ByteArrayBitstreamOutput out = new ByteArrayBitstreamOutput();
        GridDimension numCols = new GridDimension(ncols);
        GridDimension numRows = new GridDimension(nrows);
        header.put(out);
        coordLeftmost.put(out);
        coordRightmost.put(out);
        numCols.put(out);
        numRows.put(out);
        data = out.getData();

        if (LOG.isDebugEnabled()) {
            LOG.debug("binary data size (bytes): " + data.length);
        }
        return data;
    }
}
