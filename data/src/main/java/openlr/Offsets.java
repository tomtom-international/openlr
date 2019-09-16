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
package openlr;

/**
 * The interface Offsets defines the basic structure to deal with offsets. The
 * OpenLR method defines two offsets: positive offset and negative offset.
 * The positive offset indicates the distance between start point of the
 * first line and the real start point of the location.
 * The negative offset indicates the distance between end point of the last
 * line and the real end point of the location.
 * Offsets are not mandatory in OpenLR and using no offsets means that the location
 * starts at the start node of the first line and ends at the end node of the
 * last line of the location. It is also possible to use only one offset value.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 *
 */
public interface Offsets {

    /**
     * Gets the positive offset [in meter].
     *
     * @param length the length of the path to calculate the offset from
     *
     * @return the positive offset
     */
    int getPositiveOffset(int length);

    /**
     * Gets the negative offset [in meter].
     *
     * @param length the length of the path to calculate the offset from
     *
     * @return the negative offset
     */
    int getNegativeOffset(int length);

    /**
     * Checks is a positive offset is used.
     *
     * @return true, if a positive offset is used
     */
    boolean hasPositiveOffset();

    /**
     * Checks if a negative offset is used.
     *
     * @return true, if a negative offset is used
     */
    boolean hasNegativeOffset();

}
