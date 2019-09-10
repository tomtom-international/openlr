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
package openlr.location.utils;

import openlr.LocationType;
import openlr.location.Location;
import openlr.map.GeoCoordinates;
import openlr.map.Line;

import java.util.*;

/**
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class LocationData {

    /** The location data. */
    private List<Location> locations = new ArrayList<Location>();


    /** The errors. */
    private List<String> errors = new ArrayList<String>();


    /**
     * Adds the line location.
     *
     * @param l the l
     */
    public final void addLocation(final Location l) {
        if (l == null) {
            throw new IllegalArgumentException(
                    "Null location instance added to LocationData!");
        }
        locations.add(l);
    }

    /**
     * Adds the error.
     * @param message A short error message
     */
    public final void addError(final String message) {
        errors.add(message);
    }

    /**
     * Number of locations.
     *
     * @return the int
     */
    public final int numberOfLocations() {
        return locations.size();
    }

    /**
     * Gets the all location lines.
     *
     * @return the all location lines
     */
    public final Set<Line> getAllLocationLines() {
        Set<Line> lines = new HashSet<Line>();
        for (Location ll : locations) {
            LocationType locType = ll.getLocationType();
            if (locType == LocationType.LINE_LOCATION
                    || locType == LocationType.POI_WITH_ACCESS_POINT
                    || locType == LocationType.POINT_ALONG_LINE) {
                List<? extends Line> temp = ll.getLocationLines();
                Line poiLine = ll.getPoiLine();
                if (temp != null) {
                    lines.addAll(temp);
                }
                if (poiLine != null) {
                    lines.add(poiLine);
                }
            }
        }
        return lines;
    }

    /**
     * Gets the all point geo coordinates.
     *
     * @return the all point geo coordinates
     */
    public final Set<GeoCoordinates> getAllPointGeoCoordinates() {
        Set<GeoCoordinates> points = new HashSet<GeoCoordinates>();
        for (Location ll : locations) {
            LocationType locType = ll.getLocationType();
            if (locType == LocationType.GEO_COORDINATES
                    || locType == LocationType.POI_WITH_ACCESS_POINT) {
                points.add(ll.getPointLocation());
            }
        }
        return points;
    }

    /**
     * Gets the locations.
     *
     * @return the locations
     */
    public final List<Location> getLocations() {
        return locations;
    }

    /**
     * Checks for errors.
     *
     * @return true, if successful
     */
    public final boolean hasErrors() {
        return errors.size() > 0;
    }

    /**
     * Gets the nr of errors.
     *
     * @return the nr of errors
     */
    public final int getNrOfErrors() {
        return errors.size();
    }

    /**
     * Delivers the error in the sequence of there detections.
     * @return The list of errors.
     */
    public final List<String> getErrors() {
        return Collections.unmodifiableList(errors);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("numberLocations: ").append(locations.size());
        return sb.toString();
    }

    /**
     * Gets the location.
     *
     * @param index the index
     * @return the location
     */
    public final Location getLocation(final int index) {
        if (index < 0 || index >= locations.size()) {
            throw new IndexOutOfBoundsException();
        }
        return locations.get(index);
    }

    /**
     * Gets the iterator.
     *
     * @return the iterator
     */
    public final LocationDataIterator getIterator() {
        return new LocationDataIterator(this);
    }

    /**
     * Checks for locations.
     *
     * @return true, if successful
     */
    public final boolean hasLocations() {
        return locations.size() > 0;
    }

}
