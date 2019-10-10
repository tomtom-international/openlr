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
package openlr.binary;

import openlr.LocationReference;
import openlr.PhysicalDecoder;
import openlr.PhysicalFormatException;
import openlr.binary.OpenLRBinaryException.PhysicalFormatError;
import openlr.binary.bitstream.BitstreamException;
import openlr.binary.bitstream.impl.ByteArrayBitstreamInput;
import openlr.binary.data.Header;
import openlr.binary.data.RawBinaryData;
import openlr.binary.decoder.*;
import openlr.rawLocRef.RawInvalidLocRef;
import openlr.rawLocRef.RawLocationReference;
import org.apache.log4j.Logger;

/**
 * The Class OpenLRBinaryDecoder implements the {@link PhysicalDecoder}
 * interface and parses binary location reference data.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public final class OpenLRBinaryDecoder implements PhysicalDecoder {

    /** The Constant VERSIONS. */
    private static final int[] VERSIONS = {2, 3};

    /** logger. */
    private static final Logger LOG = Logger
            .getLogger(OpenLRBinaryDecoder.class);

    /**
     * Gets the versions.
     *
     * @return the versions
     */
    public static int[] getVersions() {
        return VERSIONS.clone();
    }

    /**
     * Check version.
     *
     * @param h
     *            the header
     *
     * @return true, if successful
     */
    private boolean checkVersion(final Header h) {
        int ver = h.getVer();
        for (int v : VERSIONS) {
            if (v == ver) {
                return true;
            }
        }
        return false;
    }

    /**
     * The method parses binary data in the physical OpenLR format and decodes
     * it. The method parses OpenLR data of version 2. See OpenLR white paper
     * for additional information.
     *
     * @param id
     *            the id
     * @param data
     *            the binary data
     * @param binData
     *            the bin data
     * @return the raw location reference
     * @throws PhysicalFormatException
     *             the physical format exception
     */
    private RawLocationReference parseBinaryData(final String id,
                                                 final byte[] data, final RawBinaryData binData)
            throws PhysicalFormatException {
        int totalBytes = data.length;

        // check if enough bytes available
        // OpenLRBinaryConstants.MIN_BYTES_POLYGON added by DLR e.V. (RE)
        // OpenLRBinaryConstants.MIN_BYTES_CLOSED_LINE added by DLR e.V. (RE)
        if (totalBytes < Math.min(
                OpenLRBinaryConstants.MIN_BYTES_LINE_LOCATION, Math.min(Math
                                .min(OpenLRBinaryConstants.MIN_BYTES_POINT_LOCATION,
                                        OpenLRBinaryConstants.MIN_BYTES_POLYGON),
                        OpenLRBinaryConstants.MIN_BYTES_CLOSED_LINE_LOCATION))) {
            LOG.error("not enough bytes in the stream");
            return new RawInvalidLocRef(id, BinaryReturnCode.NOT_ENOUGH_BYTES);
        }

        // read in data
        ByteArrayBitstreamInput ibs = new ByteArrayBitstreamInput(data,
                totalBytes);

        Header header = null;
        // read header information
        try {
            header = new Header(ibs);
            if (binData != null) {
                binData.setHeader(header);
            }
        } catch (BitstreamException be) {
            LOG.error("reading header data failed", be);
            ibs.close();
            return new RawInvalidLocRef(id,
                    BinaryReturnCode.READING_HEADER_FAILURE);
        }

        // check version
        if (!checkVersion(header)) {
            LOG.error("invalid version");
            ibs.close();
            return new RawInvalidLocRef(id, BinaryReturnCode.INVALID_VERSION);
        }

        boolean isPointLocation = header.getPf() == OpenLRBinaryConstants.IS_POINT;
        boolean hasAttributes = header.getAf() == OpenLRBinaryConstants.HAS_ATTRIBUTES;
        // DLR e.V. (RE) changed to:
        int areaLocationCode = header.getArf();
        boolean isAreaLocation = ((areaLocationCode == 0 && !isPointLocation && !hasAttributes) || areaLocationCode > 0);
        //
        RawLocationReference rawLocRef = null;
        AbstractDecoder decoder = null;
        if (!isPointLocation && !isAreaLocation && hasAttributes) {
            decoder = new LineDecoder();
        } else if (isPointLocation && !isAreaLocation) {
            if (!hasAttributes) {
                if (totalBytes == OpenLRBinaryConstants.GEOCOORD_SIZE) {
                    decoder = new GeoCoordDecoder();
                } else {
                    rawLocRef = new RawInvalidLocRef(id,
                            BinaryReturnCode.INVALID_BYTE_SIZE);
                }
            } else {
                if (totalBytes == OpenLRBinaryConstants.POINT_ALONG_LINE_SIZE
                        || totalBytes == OpenLRBinaryConstants.POINT_ALONG_LINE_SIZE
                        + OpenLRBinaryConstants.POINT_OFFSET_SIZE) {
                    decoder = new PointAlongDecoder();
                } else if (totalBytes == OpenLRBinaryConstants.POINT_WITH_ACCESS_SIZE
                        || totalBytes == OpenLRBinaryConstants.POINT_WITH_ACCESS_SIZE
                        + OpenLRBinaryConstants.POINT_OFFSET_SIZE) {
                    decoder = new PoiAccessDecoder();
                } else {
                    rawLocRef = new RawInvalidLocRef(id,
                            BinaryReturnCode.INVALID_BYTE_SIZE);
                }
            }
        } else if (isAreaLocation && !isPointLocation && hasAttributes) {
            // Added by DLR e.V. (RE)
            // CLOSED_LINE
            if (totalBytes >= OpenLRBinaryConstants.MIN_BYTES_CLOSED_LINE_LOCATION) {
                decoder = new ClosedLineDecoder();
            } else {
                rawLocRef = new RawInvalidLocRef(id,
                        BinaryReturnCode.INVALID_BYTE_SIZE);
            }
        } else { // DLR e.V. (RE)
            switch (areaLocationCode) {
                case OpenLRBinaryConstants.AREA_CODE_CIRCLE:
                    decoder = new CircleDecoder();
                    break;
                case OpenLRBinaryConstants.AREA_CODE_RECTANGLE:
                    /* includes case OpenLRBinaryConstants.AREA_CODE_GRID */
                    if (totalBytes == OpenLRBinaryConstants.RECTANGLE_SIZE
                            || totalBytes == OpenLRBinaryConstants.LARGE_RECTANGLE_SIZE) {
                        decoder = new RectangleDecoder();
                    } else if (totalBytes == OpenLRBinaryConstants.GRID_SIZE
                            || totalBytes == OpenLRBinaryConstants.LARGE_GRID_SIZE) {
                        decoder = new GridDecoder();
                    } else {
                        rawLocRef = new RawInvalidLocRef(id,
                                BinaryReturnCode.INVALID_BYTE_SIZE);
                    }
                    break;
                case OpenLRBinaryConstants.AREA_CODE_POLYGON:
                    if (!hasAttributes
                            && totalBytes >= OpenLRBinaryConstants.MIN_BYTES_POLYGON) {
                        decoder = new PolygonDecoder();
                    } else {
                        rawLocRef = new RawInvalidLocRef(id,
                                BinaryReturnCode.INVALID_BYTE_SIZE);
                    }
                    break;
                default:
                    rawLocRef = new RawInvalidLocRef(id,
                            BinaryReturnCode.INVALID_HEADER);
            }
        }
        try {
            if (decoder != null) {
                rawLocRef = decoder.decodeData(id, ibs, totalBytes,
                        header.getVer(), binData);
            }
        } catch (OpenLRBinaryException e) {
            throw e;
        } finally {
            ibs.close();
        }
        return rawLocRef;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RawLocationReference decodeData(final LocationReference lr)
            throws PhysicalFormatException {
        Object data = lr.getLocationReferenceData();
        if (data == null || !(data instanceof ByteArray)) {
            throw new OpenLRBinaryException(
                    PhysicalFormatError.INVALID_BINARY_DATA);
        }
        ByteArray dataList = (ByteArray) data;
        return parseBinaryData(lr.getID(), dataList.getData(), null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<?> getDataClass() {
        return ByteArray.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDataFormatIdentifier() {
        return OpenLRBinaryConstants.IDENTIFIER;
    }

    /**
     * Resolve binary data.
     *
     * @param id
     *            the id
     * @param ba
     *            the byte array
     * @return the raw binary data
     * @throws PhysicalFormatException
     *             the physical format exception
     */
    public RawBinaryData resolveBinaryData(final String id, final ByteArray ba)
            throws PhysicalFormatException {
        RawBinaryData binData = new RawBinaryData();
        parseBinaryData(id, ba.getData(), binData);
        return binData;
    }
}
