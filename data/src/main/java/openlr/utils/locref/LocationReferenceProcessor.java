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
package openlr.utils.locref;

import openlr.rawLocRef.RawCircleLocRef;
import openlr.rawLocRef.RawClosedLineLocRef;
import openlr.rawLocRef.RawGeoCoordLocRef;
import openlr.rawLocRef.RawGridLocRef;
import openlr.rawLocRef.RawLineLocRef;
import openlr.rawLocRef.RawLocationReference;
import openlr.rawLocRef.RawPoiAccessLocRef;
import openlr.rawLocRef.RawPointAlongLocRef;
import openlr.rawLocRef.RawPolygonLocRef;
import openlr.rawLocRef.RawRectangleLocRef;

/**
 * This class defines a base functionality for classes that perform tasks
 * depending on the the type of location references. It encapsulates the
 * cumbersome evaluation of the location type and calls concrete type-safe
 * worker methods then. <br>
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
public abstract class LocationReferenceProcessor<T> {

    /**
     * Processes the given location reference.
     * 
     * @param locationReference
     *            The location reference to process
     * @return The processing result
     */
    public final T process(final RawLocationReference locationReference) {

        T result;
        switch (locationReference.getLocationType()) {
        case LINE_LOCATION:
            result = process(castTo(RawLineLocRef.class, locationReference));
            break;
        case GEO_COORDINATES:
            result = process(castTo(RawGeoCoordLocRef.class, locationReference));
            break;
        case POINT_ALONG_LINE:
            result = process(castTo(RawPointAlongLocRef.class,
                    locationReference));
            break;
        case POI_WITH_ACCESS_POINT:
            result = process(castTo(RawPoiAccessLocRef.class, locationReference));
            break;
        case RECTANGLE:
            result = process(castTo(RawRectangleLocRef.class, locationReference));
            break;
        case POLYGON:
            result = process(castTo(RawPolygonLocRef.class, locationReference));
            break;
        case CIRCLE:
            result = process(castTo(RawCircleLocRef.class, locationReference));
            break;
        case GRID:
            result = process(castTo(RawGridLocRef.class, locationReference));
            break;
        case CLOSED_LINE:
            result = process(castTo(RawClosedLineLocRef.class,
                    locationReference));
            break;
        case UNKNOWN:
            result = processUnknown(locationReference);
            break;
        default:
            throw new IllegalArgumentException(
                    "Unexpected location reference type "
                            + locationReference.getLocationType());
        }

        return result;
    }

    /**
     * Processes the given line location reference.
     * 
     * @param locationReference
     *            The location reference to process
     * @return The processing result
     */
    public abstract T process(RawLineLocRef locationReference);

    /**
     * Processes the given geo coordinate location reference.
     * 
     * @param locationReference
     *            The location reference to process
     * @return The processing result
     */
    public abstract T process(RawGeoCoordLocRef locationReference);

    /**
     * Processes the given point along line location reference.
     * 
     * @param locationReference
     *            The location reference to process
     * @return The processing result
     */
    public abstract T process(RawPointAlongLocRef locationReference);

    /**
     * Processes the given POI with access point location reference.
     * 
     * @param locationReference
     *            The location reference to process
     * @return The processing result
     */
    public abstract T process(RawPoiAccessLocRef locationReference);

    /**
     * Processes the given rectangle location reference.
     * 
     * @param locationReference
     *            The location reference to process
     * @return The processing result
     */
    public abstract T process(RawRectangleLocRef locationReference);

    /**
     * Processes the given polygon location reference.
     * 
     * @param locationReference
     *            The location reference to process
     * @return The processing result
     */
    public abstract T process(RawPolygonLocRef locationReference);

    /**
     * Processes the given circle location reference.
     * 
     * @param locationReference
     *            The location reference to process
     * @return The processing result
     */
    public abstract T process(RawCircleLocRef locationReference);

    /**
     * Processes the given grid location reference.
     * 
     * @param locationReference
     *            The location reference to process
     * @return The processing result
     */
    public abstract T process(RawGridLocRef locationReference);

    /**
     * Processes the given closed line location reference.
     * 
     * @param locationReference
     *            The location reference to process
     * @return The processing result
     */
    public abstract T process(RawClosedLineLocRef locationReference);
    
    /**
     * Processes the case of location location of type
     * {@link openlr.LocationType#UNKNOWN UNKNOWN}.
     * 
     * @param locationReference
     *            The location reference to process
     * @return The processing result
     */
    public abstract T processUnknown(RawLocationReference locationReference);

    /**
     * Performs a checked cast of the given {@code locRef} instance to the
     * specified more concrete location reference interface. Throws
     * IllegalArgumentException if the cast is not possible.
     * 
     * @param <T>
     *            The concrete location reference type to cast to
     * @param locRefClass
     *            The location reference type to cast to
     * @param locRef
     *            The location reference object
     * @return The location cast to the specific type
     */
    private static <T extends RawLocationReference> T castTo(
            final Class<T> locRefClass, final RawLocationReference locRef) {

        if (locRefClass.isInstance(locRef)) {
            return locRefClass.cast(locRef);
        } else {
            // this would be an implementation error, therefore just a runtime
            // exception
            throw new IllegalArgumentException("Could not cast "
                    + locRef.getClass() + " to " + locRefClass);
        }

    }

}
