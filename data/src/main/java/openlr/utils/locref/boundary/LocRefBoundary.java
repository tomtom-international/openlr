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
package openlr.utils.locref.boundary;

import openlr.map.InvalidMapDataException;
import openlr.map.RectangleCorners;
import openlr.rawLocRef.RawLocationReference;

/**
 * The Class LocRefUtils.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public final class LocRefBoundary {

    /**
     * Utility class shall not be instantiated.
     */
    private LocRefBoundary() {
        throw new UnsupportedOperationException();
    }

    /**
     * Calculates the bounding box of the location reference points. This box
     * max include the location but it may happen that the location path is also
     * outside the box. Returns {@code null} if the given location reference does not
     * contain location reference points.
     *
     * @param rawLocRef
     *            the raw loc ref
     * @return the rectangle corners
     * @throws InvalidMapDataException
     *             the invalid map data exception
     */
    public static RectangleCorners calculateLocRefPointBoundary(
            final RawLocationReference rawLocRef)
            throws InvalidMapDataException {
        return new LrpBbasedBoundigBoxCalculator().calculateLocRefPointBoundary(rawLocRef);
    }

    /**
     * Calculates the bounding box of a location reference. This box includes
     * the location reference points and the location path. The box may not be
     * the smallest box enclosing the location path.
     *
     * @param rawLocRef
     *            the raw loc ref
     * @return the rectangle corners
     * @throws InvalidMapDataException
     *             the invalid map data exception
     */
    public static RectangleCorners calculateLocRefBoundary(
            final RawLocationReference rawLocRef)
            throws InvalidMapDataException {
        BoundingBoxCalculatorFactory factory = new BoundingBoxCalculatorFactory();
        BoundingBoxCalculator calculator = factory
                .process(rawLocRef);
        RectangleCorners rcMax = calculator.calculateBoundary(rawLocRef);
        return rcMax;
    }
}
