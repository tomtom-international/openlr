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
package openlr.xml.encoder;

import openlr.LocationReference;
import openlr.LocationReferencePoint;
import openlr.Offsets;
import openlr.location.data.Orientation;
import openlr.location.data.SideOfRoad;
import openlr.rawLocRef.RawLocationReference;
import openlr.xml.generated.OpenLR;
import openlr.xml.generated.PointAlongLine;
import openlr.xml.generated.PointLocationReference;
import openlr.xml.generated.XMLLocationReference;
import openlr.xml.impl.LocationReferenceXmlImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * The encoder for the point along line location type.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class PointAlongEncoder extends AbstractEncoder {


    /**
     * {@inheritDoc}
     */
    @Override
    public final LocationReference encodeData(final RawLocationReference rawLocRef, final int version) {
        LocationReferencePoint startLRP = rawLocRef.getLocationReferencePoints().get(0);
        LocationReferencePoint endLRP = rawLocRef.getLocationReferencePoints().get(1);
        Offsets od = rawLocRef.getOffsets();
        SideOfRoad s = rawLocRef.getSideOfRoad();
        Orientation o = rawLocRef.getOrientation();
        OpenLR xmlData = OBJECT_FACTORY.createOpenLR();
        String id = "";
        if (rawLocRef.hasID()) {
            id = rawLocRef.getID();
        }
        xmlData.setLocationID(id);
        XMLLocationReference xmlLoc = createXMLPointAlongLineLocRef(startLRP, endLRP, od, s, o);
        xmlData.setXMLLocationReference(xmlLoc);
        LocationReference locRefData = new LocationReferenceXmlImpl(id, xmlData, version);
        return locRefData;
    }

    /**
     * Creates the xml point along line loc ref.
     *
     * @param startLRP the start lrp
     * @param endLRP the end lrp
     * @param od the od
     * @param s the s
     * @param o the o
     * @return the xML location reference
     */
    private XMLLocationReference createXMLPointAlongLineLocRef(
            final LocationReferencePoint startLRP, final LocationReferencePoint endLRP,
            final Offsets od, final SideOfRoad s, final Orientation o) {
        XMLLocationReference xmlLoc = OBJECT_FACTORY.createXMLLocationReference();
        PointLocationReference pointLoc = OBJECT_FACTORY.createPointLocationReference();
        PointAlongLine pal = OBJECT_FACTORY.createPointAlongLine();
        openlr.xml.generated.LocationReferencePoint lrp1 = createLRP(startLRP);
        List<LocationReferencePoint> lrps = new ArrayList<LocationReferencePoint>();
        lrps.add(startLRP);
        lrps.add(endLRP);
        openlr.xml.generated.LastLocationReferencePoint lrp2 = createLastLRP(lrps);
        openlr.xml.generated.Offsets off = createOffsets(od, false, startLRP.getDistanceToNext(), 0);
        pal.setLocationReferencePoint(lrp1);
        pal.setLastLocationReferencePoint(lrp2);
        pal.setOffsets(off);
        pal.setSideOfRoad(resolveSideOfRoad(s));
        pal.setOrientation(resolveOrientation(o));
        pointLoc.setPointAlongLine(pal);
        xmlLoc.setPointLocationReference(pointLoc);
        return xmlLoc;
    }

}
