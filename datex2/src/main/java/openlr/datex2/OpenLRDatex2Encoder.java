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
package openlr.datex2;

import openlr.LocationReference;
import openlr.LocationType;
import openlr.PhysicalEncoder;
import openlr.datex2.encoder.GeoCoordEncoder;
import openlr.datex2.encoder.LineEncoder;
import openlr.datex2.encoder.PoiAccessEncoder;
import openlr.datex2.encoder.PointAlongEncoder;
import openlr.datex2.impl.LocationReferenceImpl;
import openlr.rawLocRef.RawLocationReference;

/**
 * The class OpenLRDatex2Encoder is the implementation of the PhysicalEncoder
 * interface. This encoder gets a RawLocationReference and transforms it into a
 * DatexII representation.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class OpenLRDatex2Encoder implements PhysicalEncoder {

    /** The Constant VERSIONS. */
    private static final int[] VERSIONS = {11};

    /**
     * Checks the version number to be valid.
     *
     * @param ver
     *            the version number
     * @return true, if the version number is valid
     */
    private boolean checkVersion(final int ver) {
        for (int v : VERSIONS) {
            if (v == ver) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Class<?> getDataClass() {
        return Datex2Location.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getDataFormatIdentifier() {
        return OpenLRDatex2Constants.IDENTIFIER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int[] getSupportedVersions() {
        return VERSIONS.clone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final LocationReference encodeData(
            final RawLocationReference rawLocRef) {
        return encodeData(rawLocRef, VERSIONS[VERSIONS.length - 1]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final LocationReference encodeData(
            final RawLocationReference rawLocRef, final int version) {
        if (!checkVersion(version)) {
            return new LocationReferenceImpl(rawLocRef.getID(),
                    Datex2ReturnCode.INVALID_VERSION,
                    rawLocRef.getLocationType(), version);
        }
        LocationType lt = rawLocRef.getLocationType();
        LocationReference lr = null;
        switch (lt) {
            case GEO_COORDINATES:
                GeoCoordEncoder geoEncoder = new GeoCoordEncoder();
                lr = geoEncoder.encodeData(rawLocRef, version);
                break;
            case LINE_LOCATION:
                LineEncoder lineEncoder = new LineEncoder();
                lr = lineEncoder.encodeData(rawLocRef, version);
                break;
            case POI_WITH_ACCESS_POINT:
                PoiAccessEncoder poiEncoder = new PoiAccessEncoder();
                lr = poiEncoder.encodeData(rawLocRef, version);
                break;
            case POINT_ALONG_LINE:
                PointAlongEncoder pointAlongEncoder = new PointAlongEncoder();
                lr = pointAlongEncoder.encodeData(rawLocRef, version);
                break;
            case UNKNOWN:
            default:
                return new LocationReferenceImpl(rawLocRef.getID(),
                        Datex2ReturnCode.UNKNOWN_LOCATION_TYPE,
                        rawLocRef.getLocationType(), version);
        }

        return lr;
    }

}
