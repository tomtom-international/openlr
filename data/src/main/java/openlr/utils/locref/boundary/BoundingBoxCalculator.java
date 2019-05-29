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
package openlr.utils.locref.boundary;

import openlr.map.InvalidMapDataException;
import openlr.map.RectangleCorners;
import openlr.rawLocRef.RawLocationReference;

/**
 * Defines the interface of location reference type specific bounding box
 * calculators.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
interface BoundingBoxCalculator {

    /**
     * Calculates the sufficient bounding box for the given location reference
     * 
     * @param locRef
     *            The location reference
     * @return The bounding box
     * @throws InvalidMapDataException
     *             If an error occurred calculating valid geo-coordinates for
     *             the bounding box from the given location reference
     */
    RectangleCorners calculateBoundary(RawLocationReference locRef)
            throws InvalidMapDataException;
}
