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
package openlr.binary;

import openlr.LocationReference;
import openlr.LocationType;
import openlr.PhysicalEncoder;
import openlr.binary.encoder.*;
import openlr.binary.impl.LocationReferenceBinaryImpl;
import openlr.rawLocRef.RawLocationReference;

/**
 * Implements the {@link PhysicalEncoder} interface and generate a binary format
 * of an OpenLR location reference.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class OpenLRBinaryEncoder implements PhysicalEncoder {

    /** The Constant VERSIONS. */
    private static final int[] VERSIONS = {2, 3};

    /**
     * Checks if is supported version.
     *
     * @param version
     *            the version
     * @param lt
     *            the lt
     * @return true, if is supported version
     */
    private boolean checkVersion(final int version, final LocationType lt) {
        boolean valid = false;
        for (int ver : VERSIONS) {
            if (version == ver) {
                valid = true;
            }
        }
        if (OpenLRBinaryConstants.POINT_LOCATION_TYPES.contains(lt)
                && version < OpenLRBinaryConstants.POINT_LOCATION_VERSION) {
            valid = false;
        }
        if (OpenLRBinaryConstants.AREA_LOCATION_TYPES.contains(lt)
                && version < OpenLRBinaryConstants.AREA_LOCATION_VERSION) {
            valid = false;
        }
        return valid;
    }

    /**
     * {@inheritDoc}
     */
    public final Class<?> getDataClass() {
        return ByteArray.class;
    }

    /**
     * {@inheritDoc}
     */
    public final String getDataFormatIdentifier() {
        return OpenLRBinaryConstants.IDENTIFIER;
    }

    /**
     * {@inheritDoc}
     */
    public final int[] getSupportedVersions() {
        return VERSIONS.clone();
    }

    /**
     * {@inheritDoc}
     */
    public final LocationReference encodeData(
            final RawLocationReference rawLocRef) {
        return encodeData(rawLocRef, VERSIONS[VERSIONS.length - 1]);
    }

    /**
     * {@inheritDoc}
     */
    public final LocationReference encodeData(
            final RawLocationReference rawLocRef, final int version) {
        LocationType lt = rawLocRef.getLocationType();
        if (!checkVersion(version, lt)) {
            return new LocationReferenceBinaryImpl(rawLocRef.getID(),
                    BinaryReturnCode.INVALID_VERSION, lt, version);
        }
        AbstractEncoder encoder = null;
        switch (lt) {
            case GEO_COORDINATES:
                encoder = new GeoCoordEncoder();
                break;
            case LINE_LOCATION:
                encoder = new LineEncoder();
                break;
            case POI_WITH_ACCESS_POINT:
                encoder = new PoiAccessEncoder();
                break;
            case POINT_ALONG_LINE:
                encoder = new PointAlongEncoder();
                break;
            case CIRCLE: // added by DLR e.V. (RE)
                encoder = new CircleEncoder();
                break;
            case RECTANGLE: // added by DLR e.V. (RE)
                encoder = new RectangleEncoder();
                break;
            case GRID: // added by DLR e.V. (RE)
                encoder = new GridEncoder();
                break;
            case POLYGON: // added by DLR e.V. (RE)
                encoder = new PolygonEncoder();
                break;
            case CLOSED_LINE: // added by DLR e.V. (RE)
                encoder = new ClosedLineEncoder();
                break;
            case UNKNOWN:
            default:
                return new LocationReferenceBinaryImpl(rawLocRef.getID(),
                        BinaryReturnCode.UNKNOWN_LOCATION_TYPE, lt, version);
        }
        LocationReference locRef = encoder.encodeData(rawLocRef, version);
        return locRef;
    }

}
