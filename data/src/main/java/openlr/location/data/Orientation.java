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
package openlr.location.data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The enumeration Orientation defines how a point location is oriented with
 * respect to the direction of the line from first LRP to second LRP.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public enum Orientation {

    /** no orientation or unknown */
    NO_ORIENTATION_OR_UNKNOWN,

    /** with line direction from first LRP to second LRP */
    WITH_LINE_DIRECTION,

    /** against line direction means from second LRP to first LRP */
    AGAINST_LINE_DIRECTION,

    /** in both directions */
    BOTH;

    /** The Constant VALUES. */
    private static final List<Orientation> VALUES = Collections.unmodifiableList(Arrays.asList(values()));

    /**
     * Gets the orientation values.
     *
     * @return the orientation values
     */
    public static List<Orientation> getOrientationValues() {
        return VALUES;
    }

    /**
     * Gets the default.
     *
     * @return the default
     */
    public static Orientation getDefault() {
        return NO_ORIENTATION_OR_UNKNOWN;
    }
}
