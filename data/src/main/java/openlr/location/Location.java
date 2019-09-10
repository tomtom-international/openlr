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
package openlr.location;

import openlr.LocationType;
import openlr.StatusCode;
import openlr.location.data.AffectedLines;
import openlr.location.data.Orientation;
import openlr.location.data.SideOfRoad;
import openlr.map.GeoCoordinates;
import openlr.map.Line;

import java.util.List;

/**
 * The interface Location defines a location (in a map) which can be encoded
 * using the OpenLR encoder and is also the result of the OpenLR decoding
 * process.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public interface Location {

    /**
     * Checks if the decoded location is valid. If valid, then the decoding
     * process was successful and a location could be matched. Otherwise the
     * exception is reported.
     *
     * @return true, if the location is valid
     */
    boolean isValid();

    /**
     * Gets the return code (only valid as a result of a decoding process).
     *
     * @return the return code
     */
    StatusCode getReturnCode();

    /**
     * Gets the location as a list of lines, if the decoded location is valid.
     *
     * @return list of lines if the decoded location is valid, otherwise null
     */
    List<Line> getLocationLines();

    /**
     * Gets the affected lines. Affected lines are fully or partly covered by the area location, if the
     * decoded area location is valid.
     *
     * @return affected lines if the decoded area location is
     *         valid, otherwise null
     */
    AffectedLines getAffectedLines();


    /**
     * Gets the unique ID.
     *
     * @return the unique ID
     */
    String getID();

    /**
     * Gets the line used to define a point location on.
     *
     * @return the line used for a point location
     */
    Line getPoiLine();

    /**
     * Gets the remaining positive offset.
     * <p/>
     * Offset values shorten the location reference path down to the desired
     * location and are measured in meters. The positive offset is used to
     * locate the precise start of a location. It defines the distance along the
     * shape of the line between the start of the location reference path, i.e.
     * the start point of the first location line, and the real start of the
     * location.
     *
     * @return the remaining positive offset
     */
    int getPositiveOffset();

    /**
     * Gets the remaining negative offset.
     * <p/>
     * Offset values shorten the location reference path down to the desired
     * location and are measured in meters. The negative offset is used to
     * locate the precise end of the location. It defines the distance along the
     * shape of the line between the end of the location and the end of the
     * location reference path, i.e. the end point of the last line.
     *
     * @return the remaining negative offset
     */
    int getNegativeOffset();

    /**
     * Gets the location type.
     *
     * @return the location type
     */
    LocationType getLocationType();

    /**
     * Gets the point location.
     *
     * @return the point location
     */
    GeoCoordinates getPointLocation();

    /**
     * Gets the access point.
     *
     * @return the access point
     */
    GeoCoordinates getAccessPoint();

    /**
     * Gets the points defining the corners of an area location.
     *
     * @return a list of corner points, if the area location is of type
     *         <code>POLYGON</code>, <code>RECTANGLE</code>, or
     *         <code>GRID</code>, otherwise <code>null</code>
     */
    List<GeoCoordinates> getCornerPoints();

    /**
     * Gets the lower left corner point of a rectangle or grid location.
     *
     * @return the lower left point, if the area location is of type
     *         <code>RECTANGLE</code>, or <code>GRID</code>, otherwise
     *         <code>null</code>
     */
    GeoCoordinates getLowerLeftPoint();

    /**
     * Gets the upper right corner point of a rectangle or grid location.
     *
     * @return the upper right point, if the area location is of type
     *         <code>RECTANGLE</code>, or <code>GRID</code>, otherwise
     *         <code>null</code>
     */
    GeoCoordinates getUpperRightPoint();

    /**
     * Gets the center point of a circle location.
     *
     * @return the center point of a circle, otherwise
     *         <code>null</code>
     */
    GeoCoordinates getCenterPoint();

    /**
     * Gets the radius (in meters) of a circle location.
     *
     * @return the radius of a circle, otherwise <code>-1</code>
     */
    long getRadius();

    /**
     * Gets the number of columns of a grid location.
     *
     * @return the number of columns of a grid location , otherwise
     *         <code>-1</code>
     */
    int getNumberOfColumns();

    /**
     * Gets the number of rows of a grid location.
     *
     * @return the number of rows of a grid location, otherwise <code>-1</code>
     */
    int getNumberOfRows();

    /**
     * Checks for positive offset.
     *
     * @return true, if successful
     */
    boolean hasPositiveOffset();

    /**
     * Checks for negative offset.
     *
     * @return true, if successful
     */
    boolean hasNegativeOffset();

    /**
     * Gets the orientation.
     *
     * @return the orientation
     */
    Orientation getOrientation();

    /**
     * Gets the side of road.
     *
     * @return the side of road
     */
    SideOfRoad getSideOfRoad();

}
