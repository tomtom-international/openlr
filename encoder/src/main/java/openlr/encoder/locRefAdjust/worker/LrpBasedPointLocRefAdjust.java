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
package openlr.encoder.locRefAdjust.worker;

import openlr.OpenLRProcessingException;
import openlr.encoder.OpenLREncoderProcessingException;
import openlr.encoder.OpenLREncoderProcessingException.EncoderProcessingError;
import openlr.encoder.data.LocRefData;
import openlr.encoder.data.LocRefPoint;
import openlr.encoder.properties.OpenLREncoderProperties;
import openlr.location.Location;
import openlr.map.Line;

import java.util.ArrayList;
import java.util.List;

/**
 * Performs adjustment for LRP based point location references.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class LrpBasedPointLocRefAdjust extends BasicLrpBasedLocRefAdjust {

    /**
     * {@inheritDoc}
     */
    @Override
    protected final void additionalAdjustments(final OpenLREncoderProperties properties,
                                               final LocRefData locRefData) throws OpenLRProcessingException {

        if (locRefData.getLocRefPoints().size() != 2) {
            fit4PointLocation(locRefData, properties);
            if (locRefData.getLocRefPoints().size() != 2) {
                throw new OpenLREncoderProcessingException(
                        EncoderProcessingError.INVALID_POINT_LOCATION_LRP);
            }
        }
    }


    /**
     * Removes location reference points as point locations only supports two
     * LRPs. This changes the input {@code locRefData} instance!
     * Besides removing LRPs that are not directly around the POI line it eliminates 
     * their expansion data.
     *
     * @param locRefData
     *            the loc ref data
     * @param properties
     *            the properties
     * @throws OpenLRProcessingException
     *             the open lr processing exception
     */
    private void fit4PointLocation(
            final LocRefData locRefData,
            final OpenLREncoderProperties properties)
            throws OpenLRProcessingException {
        Location location = locRefData.getLocation();
        List<LocRefPoint> reducedLRPs = new ArrayList<LocRefPoint>();
        Line pLine = location.getPoiLine();
        List<LocRefPoint> points = locRefData.getLocRefPoints();
        for (int i = 0; i < points.size() - 1; i++) {
            LocRefPoint l = points.get(i);

            if (l.getRoute().contains(pLine)) {
                reducedLRPs.add(l);

                int nextIndex = i + 1;
                LocRefPoint oldSuccessor = points.get(nextIndex);

                LocRefPoint newLast;
                // create an artificial last LRP
                if (oldSuccessor.isLRPOnLine()) {
                    newLast = new LocRefPoint(oldSuccessor.getLine(),
                            oldSuccessor.getLongitudeDeg(),
                            oldSuccessor.getLatitudeDeg(), properties, true);
                } else {
                    // the new last LRP is at the end of the CURRENT sub path!
                    newLast = new LocRefPoint(l.getLastLineOfSubRoute(), properties);
                }

                reducedLRPs.add(newLast);

                // cleanup the expansion at the end of the loc ref from all the following LRP routes
                for (int x = nextIndex; x < points.size() - 1; x++) {
                    LocRefPoint lrp = points.get(x);
                    locRefData.getExpansionData().modifyExpansionAtEnd(lrp.getRoute());
                }

                break;
            } else {
                // We remove an LRP whose subroute doesn't contain the POI line, must have been an expansion.
                // correct the expansion here to keep the positive offset calculation in the loc ref valid
                locRefData.getExpansionData().modifyExpansionAtStart(l.getRoute());
            }
        }


        locRefData.setLocRefPoints(reducedLRPs);
    }

}
