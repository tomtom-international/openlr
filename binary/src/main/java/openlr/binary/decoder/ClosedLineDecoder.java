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

import openlr.LocationReferencePoint;
import openlr.binary.OpenLRBinaryConstants;
import openlr.binary.OpenLRBinaryException;
import openlr.binary.bitstream.impl.ByteArrayBitstreamInput;
import openlr.binary.data.FirstLRP;
import openlr.binary.data.IntermediateLRP;
import openlr.binary.data.LastClosedLineLRP;
import openlr.binary.data.RawBinaryData;
import openlr.rawLocRef.RawClosedLineLocRef;
import openlr.rawLocRef.RawLocationReference;

import java.util.ArrayList;
import java.util.List;

/**
 * The decoder for the closed line location type.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author DLR e.V. (RE)
 */
public class ClosedLineDecoder extends AbstractDecoder {

    /**
     * {@inheritDoc}
     */
    @Override
    public final RawLocationReference decodeData(final String id,
                                                 final ByteArrayBitstreamInput ibs, final int totalBytes,
                                                 final int version, final RawBinaryData binData)
            throws OpenLRBinaryException {
        // calculate number of intermediates
        // integer division!! (get rid of possible offset information)
        int nrIntermediates = (totalBytes - OpenLRBinaryConstants.MIN_BYTES_CLOSED_LINE_LOCATION)
                / OpenLRBinaryConstants.LRP_SIZE;

        FirstLRP firstLRP = null;
        // read first location reference point
        firstLRP = new FirstLRP(ibs);

        List<IntermediateLRP> intermediates = new ArrayList<IntermediateLRP>();
        // read intermediate location reference points
        for (int i = 0; i < nrIntermediates; ++i) {
            IntermediateLRP lrp = null;
            lrp = new IntermediateLRP(ibs);
            intermediates.add(lrp);
        }
        LastClosedLineLRP lastLRP = new LastClosedLineLRP(ibs);

        int lrpCount = 1;
        List<LocationReferencePoint> points = new ArrayList<LocationReferencePoint>();
        LocationReferencePoint p = createLRP(lrpCount, firstLRP);
        lrpCount++;
        points.add(p);
        double prevLon = p.getLongitudeDeg();
        double prevLat = p.getLatitudeDeg();
        for (IntermediateLRP i : intermediates) {
            LocationReferencePoint ip = createLRP(lrpCount, i, prevLon, prevLat);
            lrpCount++;
            points.add(ip);
            prevLon = ip.getLongitudeDeg();
            prevLat = ip.getLatitudeDeg();
        }
        LocationReferencePoint lp = createLRP(lrpCount, lastLRP, firstLRP);
        points.add(lp);
        RawClosedLineLocRef rawLocRef = new RawClosedLineLocRef(id, points);
        if (binData != null) {
            binData.setBinaryLastClosedLineLRP(lastLRP);
            binData.setBinaryIntermediates(intermediates);
            binData.setBinaryFirstLRP(firstLRP);
        }
        return rawLocRef;
    }

}
