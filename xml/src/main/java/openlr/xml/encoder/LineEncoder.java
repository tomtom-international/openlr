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
import openlr.LocationReferencePoint;
import openlr.LocationType;
import openlr.Offsets;
import openlr.rawLocRef.RawLocationReference;
import openlr.xml.XmlReturnCode;
import openlr.xml.generated.LastLocationReferencePoint;
import openlr.xml.generated.LineLocationReference;
import openlr.xml.generated.OpenLR;
import openlr.xml.generated.XMLLocationReference;
import openlr.xml.impl.LocationReferenceXmlImpl;

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

    /**
     * {@inheritDoc}
     */
    @Override
    public final LocationReference encodeData(final RawLocationReference rawLocRef, final int version) {
        List<? extends LocationReferencePoint> locRef = rawLocRef.getLocationReferencePoints();
        Offsets od = rawLocRef.getOffsets();
        if (locRef == null || od == null) {
            return new LocationReferenceXmlImpl(rawLocRef.getID(), XmlReturnCode.INVALID_DATA, LocationType.LINE_LOCATION, version);
        }
        OpenLR xmlData = OBJECT_FACTORY.createOpenLR();
        String id = "";
        if (rawLocRef.hasID()) {
            id = rawLocRef.getID();
        }
        xmlData.setLocationID(id);
        XMLLocationReference xmlLoc = createXMLLineLocRef(locRef, od);
        xmlData.setXMLLocationReference(xmlLoc);
        LocationReference locRefData = new LocationReferenceXmlImpl(id, xmlData, version);
        return locRefData;
    }

    /**
     * Creates the xml location reference.
     *
     * @param locRef the OpenLR location reference
     * @param od the OpenLR offsets
     *
     * @return the XML location reference
     */
    private XMLLocationReference createXMLLineLocRef(
            final List<? extends LocationReferencePoint> locRef, final Offsets od) {
        XMLLocationReference xmlLoc = OBJECT_FACTORY.createXMLLocationReference();
        LineLocationReference lineLocRef = createLineLocRef(locRef, od);
        xmlLoc.setLineLocationReference(lineLocRef);
        return xmlLoc;
    }

    /**
     * Creates the line location reference.
     *
     * @param locRef the OpenLR location reference
     * @param od the OpenLR offsets
     *
     * @return the line location reference
     */
    private LineLocationReference createLineLocRef(
            final List<? extends LocationReferencePoint> locRef, final Offsets od) {
        LineLocationReference lineLocRef = OBJECT_FACTORY.createLineLocationReference();
        int posDist = locRef.get(0).getDistanceToNext();
        int negDist = locRef.get(locRef.size() - 2).getDistanceToNext();
        openlr.xml.generated.Offsets o = createOffsets(od, true, posDist, negDist);
        lineLocRef.setOffsets(o);
        List<openlr.xml.generated.LocationReferencePoint> lrps = createLRPs(locRef);
        lineLocRef.getLocationReferencePoint().addAll(lrps);
        LastLocationReferencePoint llrp = createLastLRP(locRef);
        lineLocRef.setLastLocationReferencePoint(llrp);
        return lineLocRef;
    }

}
