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
package openlr.xml;

import openlr.LocationReference;
import openlr.LocationType;
import openlr.PhysicalEncoder;
import openlr.rawLocRef.RawLocationReference;
import openlr.xml.encoder.*;
import openlr.xml.generated.OpenLR;
import openlr.xml.impl.LocationReferenceXmlImpl;

/**
 * Implementation of the {@link PhysicalEncoder} interface.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class OpenLRXMLEncoder implements PhysicalEncoder {

    /** The Constant VERSIONS. */
    private static final int[] VERSIONS = {1};

    /**
     * {@inheritDoc}
     */
    public final Class<?> getDataClass() {
        return OpenLR.class;
    }

    /**
     * {@inheritDoc}
     */
    public final String getDataFormatIdentifier() {
        return OpenLRXMLConstants.IDENTIFIER;
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
     * Checks if the version is valid.
     *
     * @param ver
     *            the version
     * @return true, if the version is valid
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
    public final LocationReference encodeData(
            final RawLocationReference rawLocRef, final int version) {
        if (!checkVersion(version)) {
            return new LocationReferenceXmlImpl(rawLocRef.getID(),
                    XmlReturnCode.INVALID_VERSION, rawLocRef.getLocationType(),
                    version);
        }
        LocationType lt = rawLocRef.getLocationType();
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
            /** DLR e.V. (LTouk) XML encode circle area location */
            case CIRCLE:
                encoder = new CircleEncoder();
                break;
            case RECTANGLE:
                encoder = new RectangleEncoder();
                break;
            case GRID:
                encoder = new GridEncoder();
                break;
            case POLYGON:
                encoder = new PolygonEncoder();
                break;
            case CLOSED_LINE:
                encoder = new ClosedLineEncoder();
                break;
            case UNKNOWN:
            default:
                return new LocationReferenceXmlImpl(rawLocRef.getID(),
                        XmlReturnCode.UNKNOWN_LOCATION_TYPE,
                        rawLocRef.getLocationType(), version);
        }
        LocationReference lr = encoder.encodeData(rawLocRef, version);
        return lr;
    }

}
