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
 * Copyright (C) 2009-2012 TomTom International B.V.
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
 *  Copyright (C) 2009-2012 TomTom International B.V.
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
import openlr.binary.data.AbsoluteCoordinates;
import openlr.binary.data.Header;
import openlr.binary.data.Radius;
import openlr.binary.impl.LocationReferenceBinaryImpl;
import openlr.map.GeoCoordinates;
import openlr.rawLocRef.RawLocationReference;
import org.apache.log4j.Logger;

/**
 * The encoder for the circle coordinate location type.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author DLR e.V. (RE)
 */
public class CircleEncoder extends AbstractEncoder {

    /** Logging */
    private static final Logger LOG = Logger.getLogger(CircleEncoder.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public final LocationReference encodeData(
            final RawLocationReference rawLocRef, final int version) {
        if (rawLocRef == null) {
            return new LocationReferenceBinaryImpl("",
                    BinaryReturnCode.MISSING_DATA, LocationType.CIRCLE, version);
        }
        String id = rawLocRef.getID();
        GeoCoordinates center = rawLocRef.getCenterPoint();
        long radius = rawLocRef.getRadius();
        if (center == null) {
            return new LocationReferenceBinaryImpl(id,
                    BinaryReturnCode.MISSING_DATA, LocationType.CIRCLE, version);
        }
        if (radius < 0) {
            return new LocationReferenceBinaryImpl(id,
                    BinaryReturnCode.INVALID_RADIUS, LocationType.CIRCLE,
                    version);
        }
        if (version < OpenLRBinaryConstants.BINARY_VERSION_3) {
            return new LocationReferenceBinaryImpl(id,
                    BinaryReturnCode.INVALID_VERSION, LocationType.CIRCLE,
                    version);
        }

        LocationReference lr = null;
        try {
            byte[] bd = generateBinaryCircleLocation(center, radius, version);
            ByteArray ba = new ByteArray(bd.clone());
            lr = new LocationReferenceBinaryImpl(id, ba);
        } catch (PhysicalFormatException e) {
            lr = new LocationReferenceBinaryImpl(rawLocRef.getID(),
                    BinaryReturnCode.INVALID_BINARY_DATA, LocationType.CIRCLE,
                    version);
        }
        return lr;
    }

    /**
     * Generate binary circle location.
     *
     * @param center
     *            the coord
     * @param r
     *            the radius
     * @param version
     *            the version
     * @return the byte[]
     * @throws PhysicalFormatException
     *             the physical format exception
     */
    private byte[] generateBinaryCircleLocation(final GeoCoordinates center,
                                                final long r, final int version) throws PhysicalFormatException {
        Header header;
        Radius radius;
        // r represents radius in meters
        header = generateHeader(version,
                LocationType.CIRCLE, false);
        radius = generateRadius(r);
        AbsoluteCoordinates absCoord = generateAbsCoord(center);
        byte[] data = null;
        ByteArrayBitstreamOutput out = new ByteArrayBitstreamOutput();
        header.put(out);
        absCoord.put(out);
        if (radius != null) {
            radius.put(out);
        }
        data = out.getData();

        if (LOG.isDebugEnabled()) {
            LOG.debug("binary data size (bytes): " + data.length);
        }
        return data;
    }
}
