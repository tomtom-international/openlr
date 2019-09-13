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
package openlr.xml.encoder;

import openlr.LocationReference;
import openlr.LocationType;
import openlr.map.GeoCoordinates;
import openlr.rawLocRef.RawLocationReference;
import openlr.xml.XmlReturnCode;
import openlr.xml.generated.*;
import openlr.xml.impl.LocationReferenceXmlImpl;

/**
 * The encoder for the grid area location type.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author DLR e.V. (RE,LTouk)
 */
public class GridEncoder extends AbstractEncoder {

    /**
     * {@inheritDoc}
     */
    @Override
    public final LocationReference encodeData(
            final RawLocationReference rawLocRef, final int version) {
        GeoCoordinates lowerLeftPoint = rawLocRef.getLowerLeftPoint();
        GeoCoordinates upperRightPoint = rawLocRef.getUpperRightPoint();
        int ncols = rawLocRef.getNumberOfColumns();
        int nrows = rawLocRef.getNumberOfRows();
        if (lowerLeftPoint == null || upperRightPoint == null) {
            return new LocationReferenceXmlImpl(rawLocRef.getID(),
                    XmlReturnCode.INVALID_DATA, LocationType.GRID, version);
        }

        OpenLR xmlData = OBJECT_FACTORY.createOpenLR();
        String id = "";
        if (rawLocRef.hasID()) {
            id = rawLocRef.getID();
        }
        xmlData.setLocationID(id);
        XMLLocationReference xmlLoc = createXMLGridLocRef(lowerLeftPoint,
                upperRightPoint, ncols, nrows);
        xmlData.setXMLLocationReference(xmlLoc);
        LocationReference locRefData = new LocationReferenceXmlImpl(id,
                xmlData, version);
        return locRefData;
    }

    /**
     * Creates the xml location reference.
     *
     * @param lowerLeftPoint the leftmost point
     * @param upperRightPoint the rightmost point
     * @param ncols the number of columns
     * @param nrows the number of rows
     * @return the XML location reference
     */
    private XMLLocationReference createXMLGridLocRef(
            final GeoCoordinates lowerLeftPoint,
            final GeoCoordinates upperRightPoint, final int ncols,
            final int nrows) {
        XMLLocationReference xmlLoc = OBJECT_FACTORY
                .createXMLLocationReference();
        GridLocationReference gridLocRef = createGridLocRef(lowerLeftPoint,
                upperRightPoint, ncols, nrows);
        AreaLocationReference areaLoc = OBJECT_FACTORY
                .createAreaLocationReference();
        areaLoc.setGridLocationReference(gridLocRef);
        xmlLoc.setAreaLocationReference(areaLoc);

        return xmlLoc;
    }

    /**
     * Creates the grid location reference.
     *
     * @param lowerLeftPoint
     *            the leftmost point
     * @param upperRightPoint
     *            the rightmost point
     * @param ncols
     *            the number of columns
     * @param nrows
     *            the number of rows
     * @param encodeLLC
     *
     * @return the grid location reference
     */
    private GridLocationReference createGridLocRef(
            final GeoCoordinates lowerLeftPoint,
            final GeoCoordinates upperRightPoint, final int ncols,
            final int nrows) {
        GridLocationReference gridLocRef = OBJECT_FACTORY
                .createGridLocationReference();

        Rectangle rect = OBJECT_FACTORY.createRectangle();
        rect.setLowerLeft(createXMLCoordinates(lowerLeftPoint));
        rect.setUpperRight(createXMLCoordinates(upperRightPoint));
        gridLocRef.setRectangle(rect);

        gridLocRef.setNumColumns((short) ncols);
        gridLocRef.setNumRows((short) nrows);
        return gridLocRef;
    }

}
