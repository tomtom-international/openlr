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
import openlr.binary.OpenLRBinaryConstants;
import openlr.binary.OpenLRBinaryException;
import openlr.binary.bitstream.impl.ByteArrayBitstreamOutput;
import openlr.binary.data.*;
import openlr.binary.impl.LocationReferenceBinaryImpl;
import openlr.location.data.Orientation;
import openlr.location.data.SideOfRoad;
import openlr.map.GeoCoordinates;
import openlr.rawLocRef.RawLocationReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * The encoder for the poi with access point location type.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class PoiAccessEncoder extends AbstractEncoder {

    /** Logging */
    private static final Logger LOG = LoggerFactory.getLogger(PoiAccessEncoder.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public final LocationReference encodeData(final RawLocationReference rawLocRef, final int version) {
        if (rawLocRef == null || rawLocRef.getLocationReferencePoints() == null || rawLocRef.getLocationReferencePoints().isEmpty()) {
            return new LocationReferenceBinaryImpl("",
                    BinaryReturnCode.MISSING_DATA,
                    LocationType.POI_WITH_ACCESS_POINT, version);
        }
        LocationReferencePoint startLRP = rawLocRef
                .getLocationReferencePoints().get(0);
        LocationReferencePoint endLRP = rawLocRef.getLocationReferencePoints()
                .get(1);
        Offsets od = rawLocRef.getOffsets();
        GeoCoordinates coord = rawLocRef.getGeoCoordinates();
        SideOfRoad s = rawLocRef.getSideOfRoad();
        Orientation o = rawLocRef.getOrientation();

        if (od == null || coord == null) {
            return new LocationReferenceBinaryImpl(rawLocRef.getID(),
                    BinaryReturnCode.MISSING_DATA,
                    LocationType.POI_WITH_ACCESS_POINT, version);
        }
        if (version < OpenLRBinaryConstants.BINARY_VERSION_3) {
            return new LocationReferenceBinaryImpl(rawLocRef.getID(),
                    BinaryReturnCode.INVALID_VERSION,
                    LocationType.POI_WITH_ACCESS_POINT, version);
        }
        boolean retCode = checkOffsets(od, true, rawLocRef.getLocationReferencePoints());
        if (!retCode) {
            return new LocationReferenceBinaryImpl(rawLocRef.getID(), BinaryReturnCode.INVALID_OFFSET, LocationType.POI_WITH_ACCESS_POINT, version);
        }

        LocationReference lr = null;
        try {
            byte[] bd = generateBinaryPointWithAccessLocation(startLRP, endLRP, od,
                    version, coord, s, o);
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
     * Generate binary point with access location.
     *
     * @param startLRP the start lrp
     * @param endLRP the end lrp
     * @param od the od
     * @param version the version
     * @param coord the coord
     * @param s the s
     * @param o the o
     * @return the byte[]
     * @throws OpenLRBinaryException the open lr binary processing exception
     */
    private byte[] generateBinaryPointWithAccessLocation(
            final LocationReferencePoint startLRP,
            final LocationReferencePoint endLRP, final Offsets od,
            final int version, final GeoCoordinates coord, final SideOfRoad s,
            final Orientation o) throws OpenLRBinaryException {
        Header header = generateHeader(version, LocationType.POI_WITH_ACCESS_POINT, true);
        FirstLRP first = generateFirstLRP(startLRP, o);
        List<LocationReferencePoint> lrps = new ArrayList<LocationReferencePoint>();
        lrps.add(startLRP);
        lrps.add(endLRP);
        Offset pOff = generateOffset(od, true, version, lrps);
        LastLRP last = generateLastLrp(lrps, pOff, s);
        RelativeCoordinates relCoord = generateRelativeCoordinates(startLRP,
                coord);
        byte[] data = null;
        ByteArrayBitstreamOutput out = new ByteArrayBitstreamOutput();
        header.put(out);
        first.put(out);
        last.put(out);
        if (pOff != null) {
            pOff.put(out);
        }
        relCoord.put(out);
        data = out.getData();

        if (LOG.isDebugEnabled()) {
            LOG.debug("binary data size (bytes): " + data.length);
        }
        return data;
    }

}
