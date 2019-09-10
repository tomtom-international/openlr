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
package openlr;

import openlr.map.FormOfWay;
import openlr.map.FunctionalRoadClass;

/**
 * The interface LocationReferencePoint defines the access to a location reference
 * point (LRP). Such a LRP is a basic structure for an OpenLR location reference.
 * It represents a line in the network and two successive LRPs represent a shortest-
 * path being part of the location.
 *
 * The location reference point consists of coordinates and attributes like form
 * of way, functional road class and a bearing. It also holds information on the 
 * path to the next LRP like the lowest functional road class on that path and the
 * distance to the next LRP.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 *
 */
public interface LocationReferencePoint {


    /**
     * Gets the longitude coordinate in degree.
     *
     * @return the longitude coordinate
     */
    double getLongitudeDeg();

    /**
     * Gets the latitude coordinate in degree.
     *
     * @return the latitude coordinate
     */
    double getLatitudeDeg();


    /**
     * Gets the real distance between this LRP and the next one along the
     * shortest-path in between.
     *
     * @return the distance to the next LRP
     */
    int getDistanceToNext();

    /**
     * Gets the lowest functional road class value along the shortest-path
     * to the next LRP. The lowest frc value means in this context the highest
     * frc value ID since a higher ID indicates a less important road.
     *
     * @return the lowest functional road class along the shortest-path to the
     * next LRP, returns null if no route is available
     */
    FunctionalRoadClass getLfrc();

    /**
     * Gets the bearing of the referenced line. The bearing is represented by the
     * angle between the line and the true North.
     *
     * @return the bearing of the referenced line
     */
    double getBearing();

    /**
     * Gets the form of way for this LRP.
     *
     * @return the FOW
     */
    FormOfWay getFOW();

    /**
     * Gets the functional road class of this LRP.
     *
     * @return the FRC
     */
    FunctionalRoadClass getFRC();

    /**
     * Checks if this LRP is the last one in the sequence.
     *
     * @return true, if this is the last LRP
     */
    boolean isLastLRP();

    /**
     * Gets the position of the location reference point in the list of location reference
     * points starting with 1.
     *
     * @return the sequence number
     */
    int getSequenceNumber();

}
