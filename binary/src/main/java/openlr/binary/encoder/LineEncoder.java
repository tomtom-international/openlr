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

import openlr.*;
import openlr.binary.BinaryReturnCode;
import openlr.binary.ByteArray;
import openlr.binary.OpenLRBinaryException;
import openlr.binary.bitstream.impl.ByteArrayBitstreamOutput;
import openlr.binary.data.*;
import openlr.binary.impl.LocationReferenceBinaryImpl;
import openlr.rawLocRef.RawLocationReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * The encoder for the line location type.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class LineEncoder extends AbstractEncoder {

    /** Logging */
    private static final Logger LOG = LoggerFactory.getLogger(LineEncoder.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public final LocationReference encodeData(final RawLocationReference rawLocRef, final int version) {
        if (rawLocRef == null) {
            return new LocationReferenceBinaryImpl("", BinaryReturnCode.MISSING_DATA, LocationType.LINE_LOCATION, version);
        }
        List<? extends LocationReferencePoint> locRef = rawLocRef.getLocationReferencePoints();
        Offsets od = rawLocRef.getOffsets();
        if (locRef == null || od == null || locRef.isEmpty()) {
            return new LocationReferenceBinaryImpl(rawLocRef.getID(), BinaryReturnCode.MISSING_DATA, LocationType.LINE_LOCATION, version);
        }
        boolean retCode = checkOffsets(od, true, locRef);
        if (!retCode) {
            return new LocationReferenceBinaryImpl(rawLocRef.getID(), BinaryReturnCode.INVALID_OFFSET, LocationType.LINE_LOCATION, version);
        }
        retCode = checkOffsets(od, false, locRef);
        if (!retCode) {
            return new LocationReferenceBinaryImpl(rawLocRef.getID(), BinaryReturnCode.INVALID_OFFSET, LocationType.LINE_LOCATION, version);
        }

        LocationReference lr = null;
        try {
            byte[] bd = generateBinaryLineLocation(locRef, od, version);
            ByteArray ba = new ByteArray(bd.clone());
            lr = new LocationReferenceBinaryImpl(rawLocRef.getID(), ba);
        } catch (PhysicalFormatException e) {
            lr = new LocationReferenceBinaryImpl(rawLocRef.getID(),
                    BinaryReturnCode.INVALID_BINARY_DATA,
                    LocationType.GEO_COORDINATES, version);
        }
        return lr;
    }


    /**
     * Generates binary data of a line location reference according to the OpenLR
     * white paper.
     *
     * @param locref list of location reference points
     * @param od the offset data
     * @param version the version
     * @return the binary location reference
     * @throws OpenLRBinaryException the open lr binary processing exception
     */
    private byte[] generateBinaryLineLocation(
            final List<? extends LocationReferencePoint> locref,
            final Offsets od, final int version) throws OpenLRBinaryException {
        Header header = generateHeader(version, LocationType.LINE_LOCATION, true);
        FirstLRP firstLRP = generateFirstLRP(locref.get(0));
        IntermediateLRP[] lrps = generateLRPs(locref);
        Offset pOff = generateOffset(od, true, version, locref);
        Offset nOff = generateOffset(od, false, version, locref);
        LastLRP lastLRP = generateLastLrp(locref, pOff, nOff);
        byte[] data = null;
        ByteArrayBitstreamOutput out = new ByteArrayBitstreamOutput();
        header.put(out);
        firstLRP.put(out);
        for (int i = 0; i < lrps.length; ++i) {
            lrps[i].put(out);
        }
        lastLRP.put(out);
        if (pOff != null) {
            pOff.put(out);
        }
        if (nOff != null) {
            nOff.put(out);
        }
        data = out.getData();

        if (LOG.isDebugEnabled()) {
            LOG.debug("binary data size (bytes): " + data.length);
        }
        return data;
    }

}
