/**
 * Licensed to the TomTom International B.V. under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  TomTom International B.V.
 * licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
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
package openlr.utils.location;

import openlr.location.CircleLocation;
import openlr.location.ClosedLineLocation;
import openlr.location.GeoCoordLocation;
import openlr.location.GridLocation;
import openlr.location.LineLocation;
import openlr.location.Location;
import openlr.location.PoiAccessLocation;
import openlr.location.PointAlongLocation;
import openlr.location.PolygonLocation;
import openlr.location.RectangleLocation;

/**
 * This class defines a base functionality for classes that perform tasks
 * depending on the the type of locations. It encapsulates the cumbersome
 * evaluation of the location type and calls concrete type-safe worker methods
 * then.<br>
 * This interface does not define any checked exception for the worker methods.
 * If implementors have a need for throwing one it is recommended to implement
 * this processor as a factory producing worker objects that provide the
 * concrete processor method of their needs.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @param <T>
 *            The type of result the processor delivers.
 * @author TomTom International B.V.
 */
public abstract class LocationProcessor<T> {

    /**
     * Processes the given location.
     * 
     * @param location
     *            The location to process
     * @return The processing result
     */
    public final T process(final Location location) {

        T result;
        switch (location.getLocationType()) {
        case LINE_LOCATION:
            result = process(castTo(LineLocation.class, location));
            break;
        case GEO_COORDINATES:
            result = process(castTo(GeoCoordLocation.class, location));
            break;
        case POINT_ALONG_LINE:
            result = process(castTo(PointAlongLocation.class, location));
            break;
        case POI_WITH_ACCESS_POINT:
            result = process(castTo(PoiAccessLocation.class, location));
            break;
        case RECTANGLE:
            result = process(castTo(RectangleLocation.class, location));
            break;
        case POLYGON:
            result = process(castTo(PolygonLocation.class, location));
            break;
        case CIRCLE:
            result = process(castTo(CircleLocation.class, location));
            break;
        case GRID:
            result = process(castTo(GridLocation.class, location));
            break;
        case CLOSED_LINE:
            result = process(castTo(ClosedLineLocation.class, location));
            break;
        case UNKNOWN:
            result = processUnknown(location);
            break;
        default:
            throw new IllegalArgumentException("Unexpected location type "
                    + location.getLocationType());
        }

        return result;
    }

    /**
     * Processes the given line location.
     * 
     * @param location
     *            The location to process
     * @return The processing result
     */
    public abstract T process(LineLocation location);

    /**
     * Processes the given geo coordinate location.
     * 
     * @param location
     *            The location to process
     * @return The processing result
     */
    public abstract T process(GeoCoordLocation location);

    /**
     * Processes the given point along line location.
     * 
     * @param location
     *            The location to process
     * @return The processing result
     */
    public abstract T process(PointAlongLocation location);

    /**
     * Processes the given POI with access point location.
     * 
     * @param location
     *            The location to process
     * @return The processing result
     */
    public abstract T process(PoiAccessLocation location);

    /**
     * Processes the given rectangle location.
     * 
     * @param location
     *            The location to process
     * @return The processing result
     */
    public abstract T process(RectangleLocation location);

    /**
     * Processes the given polygon location.
     * 
     * @param location
     *            The location to process
     * @return The processing result
     */
    public abstract T process(PolygonLocation location);

    /**
     * Processes the given circle location.
     * 
     * @param location
     *            The location to process
     * @return The processing result
     */
    public abstract T process(CircleLocation location);

    /**
     * Processes the given grid location.
     * 
     * @param location
     *            The location to process
     * @return The processing result
     */
    public abstract T process(GridLocation location);

    /**
     * Processes the given closed line location.
     * 
     * @param location
     *            The location to process
     * @return The processing result
     */
    public abstract T process(ClosedLineLocation location);
    
    /**
     * Processes the case of location of type
     * {@link openlr.LocationType#UNKNOWN UNKNOWN}.
     * 
     * @param location
     *            The location to process
     * @return The processing result
     */
    public abstract T processUnknown(Location location);

    /**
     * Performs a checked cast of the given {@code location} instance to the
     * specified more concrete location interface. Throws
     * IllegalArgumentException if the cast is not possible.
     * 
     * @param <T>
     *            The concrete location type to cast to
     * @param locationClass
     *            The location type to cast to
     * @param location
     *            The location object
     * @return The location cast to the specific type
     */
    private static <T extends Location> T castTo(final Class<T> locationClass,
            final Location location) {

        if (locationClass.isInstance(location)) {
            return locationClass.cast(location);
        } else {
            // this is very unexpected, therefore just a runtime exception
            throw new IllegalArgumentException("Could not cast "
                    + location.getClass() + " to " + locationClass);
        }

    }

}
