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
package openlr.xml.encoder;

import openlr.LocationReference;
import openlr.LocationType;
import openlr.map.GeoCoordinates;
import openlr.rawLocRef.RawLocationReference;
import openlr.xml.XmlReturnCode;
import openlr.xml.generated.*;
import openlr.xml.impl.LocationReferenceXmlImpl;

/**
 * The encoder for the rectangle area location type.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author DLR e.V. (RE,LTouk)
 */
public class RectangleEncoder extends AbstractEncoder {

    /**
     * {@inheritDoc}
     */
    @Override
    public final LocationReference encodeData(
            final RawLocationReference rawLocRef, final int version) {
        GeoCoordinates lowerLeftPoint = rawLocRef.getLowerLeftPoint();
        GeoCoordinates upperRightPoint = rawLocRef.getUpperRightPoint();

        if (lowerLeftPoint == null || upperRightPoint == null) {
            return new LocationReferenceXmlImpl(rawLocRef.getID(),
                    XmlReturnCode.INVALID_DATA, LocationType.RECTANGLE, version);
        }

        OpenLR xmlData = OBJECT_FACTORY.createOpenLR();
        String id = "";
        if (rawLocRef.hasID()) {
            id = rawLocRef.getID();
        }
        xmlData.setLocationID(id);
        XMLLocationReference xmlLoc = createXMLRectangleLocRef(lowerLeftPoint,
                upperRightPoint);
        xmlData.setXMLLocationReference(xmlLoc);
        LocationReference locRefData = new LocationReferenceXmlImpl(id,
                xmlData, version);
        return locRefData;
    }

    /**
     * Creates the xml location reference.
     *
     * @param leftmostPoint
     *            the leftmost point
     * @param rightmostPoint
     *            the rightmost point
     *
     * @return the XML location reference
     */
    private XMLLocationReference createXMLRectangleLocRef(
            final GeoCoordinates leftmostPoint,
            final GeoCoordinates rightmostPoint) {
        XMLLocationReference xmlLoc = OBJECT_FACTORY
                .createXMLLocationReference();
        RectangleLocationReference rectangleLocRef = createRectangleLocRef(
                leftmostPoint, rightmostPoint);

        AreaLocationReference areaLoc = OBJECT_FACTORY
                .createAreaLocationReference();
        areaLoc.setRectangleLocationReference(rectangleLocRef);
        xmlLoc.setAreaLocationReference(areaLoc);
        return xmlLoc;
    }

    /**
     * Creates the rectangle location reference.
     *
     * @param leftmostPoint
     *            the leftmost point
     * @param rightmostPoint
     *            the rightmost point
     *
     * @return the rectangle location reference
     */
    private RectangleLocationReference createRectangleLocRef(
            final GeoCoordinates leftmostPoint,
            final GeoCoordinates rightmostPoint) {
        RectangleLocationReference rectangleLocRef = OBJECT_FACTORY
                .createRectangleLocationReference();

        Rectangle rect = OBJECT_FACTORY.createRectangle();
        rect.setLowerLeft(createXMLCoordinates(leftmostPoint));
        rect.setUpperRight(createXMLCoordinates(rightmostPoint));
        rectangleLocRef.setRectangle(rect);

        return rectangleLocRef;
    }

}
