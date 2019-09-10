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
import openlr.LocationReferencePoint;
import openlr.LocationType;
import openlr.PhysicalFormatException;
import openlr.binary.BinaryReturnCode;
import openlr.binary.ByteArray;
import openlr.binary.bitstream.impl.ByteArrayBitstreamOutput;
import openlr.binary.data.FirstLRP;
import openlr.binary.data.Header;
import openlr.binary.data.IntermediateLRP;
import openlr.binary.data.LastClosedLineLRP;
import openlr.binary.impl.LocationReferenceBinaryImpl;
import openlr.rawLocRef.RawLocationReference;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * The encoder for the line location type.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author DLR e.V. (RE)
 */
public class ClosedLineEncoder extends AbstractEncoder {

    /** Logging */
    private static final Logger LOG = Logger.getLogger(ClosedLineEncoder.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public final LocationReference encodeData(
            final RawLocationReference rawLocRef, final int version) {
        if (rawLocRef == null) {
            return new LocationReferenceBinaryImpl("",
                    BinaryReturnCode.MISSING_DATA, LocationType.CLOSED_LINE,
                    version);
        }
        List<? extends LocationReferencePoint> locRef = rawLocRef
                .getLocationReferencePoints();
        if (locRef == null || locRef.isEmpty()) {
            return new LocationReferenceBinaryImpl(rawLocRef.getID(),
                    BinaryReturnCode.MISSING_DATA, LocationType.CLOSED_LINE,
                    version);
        }
        LocationReference lr = null;
        try {
            byte[] bd = generateBinaryClosedLineLocation(locRef, version);
            ByteArray ba = new ByteArray(bd.clone());
            lr = new LocationReferenceBinaryImpl(rawLocRef.getID(), ba);
        } catch (PhysicalFormatException e) {
            lr = new LocationReferenceBinaryImpl(rawLocRef.getID(),
                    BinaryReturnCode.INVALID_BINARY_DATA,
                    LocationType.CLOSED_LINE, version);
        }
        return lr;
    }

    /**
     * Generates binary data of a closed line location reference according to
     * the OpenLR white paper.
     *
     * @param locref list of location reference points
     * @param version the version
     * @return the binary location reference
     * @throws PhysicalFormatException the physical format exception
     */
    private byte[] generateBinaryClosedLineLocation(
            final List<? extends LocationReferencePoint> locref,
            final int version) throws PhysicalFormatException {
        Header header = generateHeader(version,
                LocationType.CLOSED_LINE, true);
        FirstLRP firstLRP = generateFirstLRP(locref.get(0));
        IntermediateLRP[] lrps = generateLRPs(locref);
        LastClosedLineLRP lastLineLRP = generateLastLineLRP(
                locref);
        byte[] data = null;
        ByteArrayBitstreamOutput out = new ByteArrayBitstreamOutput();
        header.put(out);
        firstLRP.put(out);
        for (int i = 0; i < lrps.length; ++i) {
            lrps[i].put(out);
        }
        lastLineLRP.put(out);
        data = out.getData();

        if (LOG.isDebugEnabled()) {
            LOG.debug("binary data size (bytes): " + data.length);
        }
        return data;
    }

}
