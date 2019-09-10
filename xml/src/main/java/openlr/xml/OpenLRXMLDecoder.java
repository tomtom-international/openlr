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
import openlr.PhysicalDecoder;
import openlr.PhysicalFormatException;
import openlr.rawLocRef.RawLocationReference;
import openlr.xml.OpenLRXMLException.XMLErrorType;
import openlr.xml.decoder.*;
import openlr.xml.generated.*;

/**
 * Implementation of the {@link PhysicalDecoder} interface.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class OpenLRXMLDecoder implements PhysicalDecoder {

    /**
     * {@inheritDoc}
     */
    @Override
    public final RawLocationReference decodeData(final LocationReference lr)
            throws PhysicalFormatException {
        Object data = lr.getLocationReferenceData();
        if (!(data instanceof OpenLR)) {
            throw new OpenLRXMLException(XMLErrorType.DATA_ERROR,
                    "incorrect data class");
        }
        OpenLR xmlData = (OpenLR) data;
        String id = xmlData.getLocationID();
        XMLLocationReference xmlLoc = xmlData.getXMLLocationReference();
        if (xmlLoc == null) {
            throw new OpenLRXMLException(XMLErrorType.BINARY_DATA_ONLY,
                    "cannot decode binary string using xml package");
        }

        LineLocationReference lineLoc = xmlLoc.getLineLocationReference();
        PointLocationReference pointLoc = xmlLoc.getPointLocationReference();
        /** DLR e.V. (LTouk) XML area location reference */
        AreaLocationReference areaLoc = xmlLoc.getAreaLocationReference();

        if (lineLoc == null && pointLoc == null && areaLoc == null) {
            throw new OpenLRXMLException(XMLErrorType.DATA_ERROR,
                    "no location reference");
        }

        RawLocationReference rawLocRef = null;
        if (lineLoc != null) {
            LineDecoder decoder = new LineDecoder();
            rawLocRef = decoder.decodeData(id, lineLoc);
        } else if (pointLoc != null) {
            GeoCoordinate gCoord = pointLoc.getGeoCoordinate();
            PointAlongLine pal = pointLoc.getPointAlongLine();
            PoiWithAccessPoint palwap = pointLoc.getPoiWithAccessPoint();
            if (gCoord == null && pal == null && palwap == null) {
                throw new OpenLRXMLException(XMLErrorType.DATA_ERROR,
                        "no point location found");
            }
            if (gCoord != null) {
                GeoCoordDecoder decoder = new GeoCoordDecoder();
                rawLocRef = decoder.decodeData(id, gCoord);
            } else if (pal != null) {
                PointAlongDecoder decoder = new PointAlongDecoder();
                rawLocRef = decoder.decodeData(id, pal);
            } else if (palwap != null) {
                PoiAccessDecoder decoder = new PoiAccessDecoder();
                rawLocRef = decoder.decodeData(id, palwap);
            }
        } else if (areaLoc != null) {
            /** DLR e.V. (LTouk) XML circle location reference */
            CircleLocationReference circleLoc = areaLoc
                    .getCircleLocationReference();
            RectangleLocationReference rectangleLoc = areaLoc
                    .getRectangleLocationReference();
            GridLocationReference gridLoc = areaLoc.getGridLocationReference();
            PolygonLocationReference polygonLoc = areaLoc
                    .getPolygonLocationReference();
            ClosedLineLocationReference closedLineLoc = areaLoc
                    .getClosedLineLocationReference();

            if (circleLoc == null && polygonLoc == null && rectangleLoc == null
                    && gridLoc == null && closedLineLoc == null) {
                throw new OpenLRXMLException(XMLErrorType.DATA_ERROR,
                        "no area location found");
            }
            if (circleLoc != null) {
                CircleDecoder circleDecoder = new CircleDecoder();
                rawLocRef = circleDecoder.decodeData(id, circleLoc);
            } else if (rectangleLoc != null) {
                RectangleDecoder rectangleDecoder = new RectangleDecoder();
                rawLocRef = rectangleDecoder.decodeData(id, rectangleLoc);
            } else if (gridLoc != null) {
                GridDecoder gridDecoder = new GridDecoder();
                rawLocRef = gridDecoder.decodeData(id, gridLoc);
            } else if (polygonLoc != null) {
                PolygonDecoder polygonDecoder = new PolygonDecoder();
                rawLocRef = polygonDecoder.decodeData(id, polygonLoc);
            } else if (closedLineLoc != null) {
                ClosedLineDecoder closedLineDecoder = new ClosedLineDecoder();
                rawLocRef = closedLineDecoder.decodeData(id, closedLineLoc);
            }

        }

        return rawLocRef;
    }

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

}
