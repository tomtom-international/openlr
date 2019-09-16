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
package openlr.map;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * The Enum FunctionalRoadClass is a classification based on the importance of
 * the role that a line performs in the connectivity of the total road network.
 * Each line in a road network should have a FRC value attached.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public enum FunctionalRoadClass {

    /** Main road. */
    FRC_0(0),

    /** First class road. */
    FRC_1(1),

    /** Second class road. */
    FRC_2(2),

    /** Third class road. */
    FRC_3(3),

    /** Forth class road. */
    FRC_4(4),

    /** Fifth class road. */
    FRC_5(5),

    /** Sixth class road. */
    FRC_6(6),

    /** Other class road. */
    FRC_7(7);

    /** The Constant VALUES. */
    private static final List<FunctionalRoadClass> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    /**
     * The unique ID. This id corresponds to the value in the physical data
     * format.
     */
    private int uid;

    /**
     * Instantiates a new functional road class.
     *
     * @param id
     *            the unique id
     */
    FunctionalRoadClass(final int id) {
        this.uid = id;
    }

    /**
     * Gets the FRC values.
     *
     * @return the frc values
     */
    public static List<FunctionalRoadClass> getFRCs() {
        return VALUES;
    }

    /**
     * Gets the highest frc.
     *
     * @return the highest frc
     */
    public static FunctionalRoadClass getHighestFrc() {
        return FunctionalRoadClass.FRC_0;
    }

    /**
     * Gets the lowest frc.
     *
     * @return the lowest frc
     */
    public static FunctionalRoadClass getLowestFrc() {
        return FunctionalRoadClass.FRC_7;
    }

    /**
     * Gets an unique ID identifying that specific FunctionalRoadClass. This
     * integer value also defines the place value of this FRC in the sequence
     * of possible FRCs where 0 specifies the highest importance.
     *
     * @return the unique ID
     */
    public int getID() {
        return uid;
    }

    /**
     * Lower.
     *
     * @return the functional road class
     */
    public FunctionalRoadClass lower() {
        int temp = uid + 1;
        if (temp >= VALUES.size()) {
            temp = VALUES.size() - 1;
        }
        return VALUES.get(temp);
    }

    /**
     * Higher.
     *
     * @return the functional road class
     */
    public FunctionalRoadClass higher() {
        int temp = uid - 1;
        if (temp < 0) {
            temp = 0;
        }
        return VALUES.get(temp);
    }

    /**
     * The class FrcComparator can be used to order functional road classes
     * according to its importance in the road network. The highest functional
     * road class should be the most important class (usually expressed as
     * FRC_0).
     */
    public static final class FrcComparator implements Comparator<FunctionalRoadClass>, Serializable {

        /**
         * Serial ID.
         */
        private static final long serialVersionUID = -1253845002995317959L;

        /**
         * {@inheritDoc}
         */
        @Override
        public int compare(final FunctionalRoadClass o1, final FunctionalRoadClass o2) {
            if (o1.getID() < o2.getID()) {
                return 1;
            } else if (o1.getID() > o2.getID()) {
                return -1;
            }
            return 0;
        }
    }
}
