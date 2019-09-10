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
package openlr.rawLocRef;

import openlr.LocationReferencePoint;
import openlr.LocationType;
import openlr.Offsets;
import openlr.StatusCode;
import openlr.location.data.Orientation;
import openlr.location.data.SideOfRoad;
import openlr.map.GeoCoordinates;

import java.util.List;

/**
 * A raw location reference represents an intermediate format of a location
 * reference used by the encoder and decoder. This format is the exchange format
 * between a specific physical format (e.g. binary) and the data format used for
 * encoding and decoding.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public abstract class RawLocationReference {

    /** The loc type. */
    protected final LocationType locType;

    /** The id. */
    protected final String id;

    /** The return code. */
    protected final StatusCode returnCode;

    /** the hash code (to avoid multiple calculations) */
    private int hashCode = 0;

    /**
     * Instantiates a new raw location reference.
     *
     * @param idValue
     *            the id value
     * @param lt
     *            the location type
     * @param rc
     *            the return code
     */
    public RawLocationReference(final String idValue, final LocationType lt,
                                final StatusCode rc) {
        locType = lt;
        returnCode = rc;
        id = idValue;
    }

    /**
     * Instantiates a new raw location reference.
     *
     * @param idValue the id value
     * @param lt the location type
     */
    public RawLocationReference(final String idValue, final LocationType lt) {
        locType = lt;
        returnCode = null;
        id = idValue;
    }

    /**
     * Gets the unique id stored in the location reference data. If no id is
     * stored the method will return null.
     *
     * @return the id or null if no id is available
     */
    public final String getID() {
        return id;
    }

    /**
     * Checks if the location reference data contains an unique id.
     *
     * @return true, if the location reference data contains an id
     */
    public final boolean hasID() {
        return id != null;
    }

    /**
     * Gets the location type.
     *
     * @return the location type
     */
    public final LocationType getLocationType() {
        return locType;
    }

    /**
     * Gets the return code (only valid as a result of a decoding process).
     *
     * @return the return code
     */
    public final StatusCode getReturnCode() {
        return returnCode;
    }

    /**
     * Checks if the location reference is valid. If this location reference was
     * encoded successfully and it contains a location reference object, then
     * the location reference is valid. It is invalid if an error occurred
     * during encoding and the exception stored within the location reference
     * indicates what went wrong.
     *
     * @return true, if the location reference is valid, otherwise false
     */
    public final boolean isValid() {
        return returnCode == null;
    }

    /**
     * Calculate the hash code which can be used to be stored if several hashCode calls
     * are required.
     *
     * @return the int
     */
    public abstract int calculateHashCode();

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract boolean equals(Object obj);

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract int hashCode();


    /**
     * Gets the hash code.
     *
     * @return the hash code
     */
    protected final int getHashCode() {
        if (hashCode == 0) {
            hashCode = calculateHashCode();
        }
        return hashCode;
    }


    /*************************** default implementations **************************/

    //do not check default implementations
    //would case a DesignForExtension error

    //CHECKSTYLE:OFF

    /**
     * Gets the location reference points.
     *
     * @return the location reference points or null if data was not decoded
     *         before
     */
    public List<LocationReferencePoint> getLocationReferencePoints() {
        return null;
    }

    /**
     * Gets the offset information.
     *
     * @return the offset information or null if data was not decoded before
     */
    public Offsets getOffsets() {
        return null;
    }

    /**
     * Gets the geo coordinates.
     *
     * @return the geo coordinates
     */
    public GeoCoordinates getGeoCoordinates() {
        return null;
    }

    /**
     * Gets the side of road.
     *
     * @return the side of road
     */
    public SideOfRoad getSideOfRoad() {
        return null;
    }

    /**
     * Gets the orientation.
     *
     * @return the orientation
     */
    public Orientation getOrientation() {
        return null;
    }

    /**
     * Gets the points defining the corners of an area location.
     *
     * @return a list of corner points, if the area location is of type
     *         <code>POLYGON</code>, <code>RECTANGLE</code>, or
     *         <code>GRID</code>, otherwise <code>null</code>
     */
    public List<GeoCoordinates> getCornerPoints() {
        return null;
    }

    /**
     * Gets the leftmost corner point of a rectangle or grid location. If there
     * are two possibilities, gets the upper one of them.
     *
     * @return the leftmost point, if the area location is of type
     *         <code>RECTANGLE</code>, or <code>GRID</code>, otherwise
     *         <code>null</code>
     */
    public GeoCoordinates getLowerLeftPoint() {
        return null;
    }

    /**
     * Gets the rightmost corner point of a rectangle or grid location. If there
     * are two possibilities, gets the lower one of them.
     *
     * @return the rightmost point, if the area location is of type
     *         <code>RECTANGLE</code>, or <code>GRID</code>, otherwise
     *         <code>null</code>
     */
    public GeoCoordinates getUpperRightPoint() {
        return null;
    }

    /**
     * Gets the center point of a circle location.
     *
     * @return the center point of a circle, otherwise <code>null</code>
     */
    public GeoCoordinates getCenterPoint() {
        return null;
    }

    /**
     * Gets the radius (in meters) of a circle location.
     *
     * @return the radius of a circle, otherwise <code>-1</code>
     */
    public long getRadius() {
        return -1;
    }

    /**
     * Gets the number of columns of a grid location.
     *
     * @return the number of columns of a grid location , otherwise
     *         <code>-1</code>
     */
    public int getNumberOfColumns() {
        return -1;
    }

    /**
     * Gets the number of rows of a grid location.
     *
     * @return the number of rows of a grid location, otherwise <code>-1</code>
     */
    public int getNumberOfRows() {
        return -1;
    }

    //CHECKSTYLE:ON

}
