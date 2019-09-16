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
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 2 of the License and the extra
 * conditions for OpenLR. (see openlr-license.txt)
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
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
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License and the extra
 *  conditions for OpenLR. (see openlr-license.txt)
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
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
package openlr.binary;

import openlr.map.FormOfWay;
import openlr.map.FunctionalRoadClass;

/**
 * Contains LRP data used in several tests of this package.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
enum Lrp {

    /** LRP #1 used for a line location encoder test. */
    LINE_ENC_LRP1(FunctionalRoadClass.FRC_3, FormOfWay.MULTIPLE_CARRIAGEWAY,
            49.60851, 6.12683, 135, 561, FunctionalRoadClass.FRC_3),

    /** LRP #2 used for a line location encoder test. */
    LINE_ENC_LRP2(FunctionalRoadClass.FRC_3, FormOfWay.SINGLE_CARRIAGEWAY,
            49.60398, 6.12838, 227.0, 274, FunctionalRoadClass.FRC_5),

    /** LRP #3 used for a line location encoder test. */
    LINE_ENC_LRP3(FunctionalRoadClass.FRC_5, FormOfWay.SINGLE_CARRIAGEWAY,
            49.60305, 6.12817, 290, 0, null),

    /** LRP #3 used for a line location encoder test. */
    LINE_ENC_LRP3_CLOSEDLINE(FunctionalRoadClass.FRC_5,
            FormOfWay.SINGLE_CARRIAGEWAY, 49.60305, 6.12817, 290, 0,
            FunctionalRoadClass.FRC_4),

    /** LRP #1 used for a line location decoder test. */
    LINE_DEC_LRP1(FunctionalRoadClass.FRC_3, FormOfWay.MULTIPLE_CARRIAGEWAY,
            49.60852, 6.12682, 140.625, 557, FunctionalRoadClass.FRC_3),

    /** LRP #2 used for a line location decoder test */
    LINE_DEC_LRP2(FunctionalRoadClass.FRC_3, FormOfWay.SINGLE_CARRIAGEWAY,
            49.60399, 6.12836, 230.625, 264, FunctionalRoadClass.FRC_5),

    /** LRP #3 used for a line location decoder test */
    LINE_DEC_LRP3(FunctionalRoadClass.FRC_5, FormOfWay.SINGLE_CARRIAGEWAY,
            49.60307, 6.12815, 286.875, 0, FunctionalRoadClass.FRC_7),

    /** LRP #2 used for the line location decoder test of the white paper location */
    LINE_DEC_LRP2_WP(FunctionalRoadClass.FRC_3, FormOfWay.SINGLE_CARRIAGEWAY,
            49.60399, 6.12837, 230.625, 264, FunctionalRoadClass.FRC_5),

    /** LRP #3 used for a line location decoder test of the white paper location */
    LINE_DEC_LRP3_WP(FunctionalRoadClass.FRC_5, FormOfWay.SINGLE_CARRIAGEWAY,
            49.60306, 6.12816, 286.875, 0, FunctionalRoadClass.FRC_7),

    /** LRP #3 used for a line location decoder test */
    LINE_DEC_LRP3_CLOSEDLINE(FunctionalRoadClass.FRC_5,
            FormOfWay.SINGLE_CARRIAGEWAY, 49.60852, 6.12682, 286.875, 0,
            null),

    /**
     * LRP #1 of the white paper examples for point along line and point with
     * access point encoding.
     */
    PL_ENC_LRP1(FunctionalRoadClass.FRC_2, FormOfWay.MULTIPLE_CARRIAGEWAY,
            49.60597, 6.12829, 202.0, 92, FunctionalRoadClass.FRC_2),

    /**
     * LRP #2 of the white paper example for point along line and point with
     * access point encoding.
     */
    PL_ENC_LRP2(FunctionalRoadClass.FRC_2, FormOfWay.MULTIPLE_CARRIAGEWAY,
            49.60521, 6.12779, 42.0, 0, null),

    /**
     * LRP #1 of the white paper example for point along line output of the decoder.
     */
    PAL_DEC_LRP1(FunctionalRoadClass.FRC_2, FormOfWay.MULTIPLE_CARRIAGEWAY,
            49.60596, 6.1283, 196.875, 88, FunctionalRoadClass.FRC_2),

    /**
     * LRP #2 of the white paper example for point along line output of the decoder.
     */
    PAL_DEC_LRP2(FunctionalRoadClass.FRC_2, FormOfWay.MULTIPLE_CARRIAGEWAY,
            49.6052, 6.1278, 39.375, 0, FunctionalRoadClass.FRC_7 /* default */),

    /**
     * LRP #1 of the white paper example for poi with access point of the
     * decoder.
     */
    PWA_DEC_LRP1(FunctionalRoadClass.FRC_2, FormOfWay.MULTIPLE_CARRIAGEWAY,
            49.60596, 6.1283, 196.875, 88, FunctionalRoadClass.FRC_2),

    /**
     * LRP #2 of the white paper example for poi with access point of the decoder.
     */
    PWA_DEC_LRP2(FunctionalRoadClass.FRC_2, FormOfWay.MULTIPLE_CARRIAGEWAY,
            49.6052, 6.1278, 39.375, 0, FunctionalRoadClass.FRC_7 /* default */);

    /** The bearing of the line referenced by the LRP. */
    private final double bearing;

    /** The distance to the next LRP along the shortest-path. */
    private final int distanceToNext;

    /** The functional road class of the line referenced by the LRP. */
    private final FunctionalRoadClass frc;

    /** The form of way of the line referenced by the LRP. */
    private final FormOfWay fow;

    /** The lowest functional road class to the next LRP. */
    private final FunctionalRoadClass lfrcnp;

    /** The longitude coordinate. */
    private final double longitude;

    /** The latitude coordinate. */
    private final double latitude;

    /**
     * @param frcValue
     *            The FRC
     * @param fowValue
     *            The FOW
     * @param longitudeValue
     *            The longitude
     * @param latitudeValue
     *            The latitude
     * @param bearingValue
     *            TThe bearing
     * @param dnpValue
     *            The DNP
     * @param lfrcnpValue
     *            The lowest FRC to the next point.
     */
    private Lrp(final FunctionalRoadClass frcValue, final FormOfWay fowValue,
                final double latitudeValue, final double longitudeValue,
                final double bearingValue, final int dnpValue,
                final FunctionalRoadClass lfrcnpValue) {
        this.longitude = longitudeValue;
        this.latitude = latitudeValue;
        this.frc = frcValue;
        this.fow = fowValue;
        this.bearing = bearingValue;
        this.lfrcnp = lfrcnpValue;
        this.distanceToNext = dnpValue;
    }

    /** @return The bearing of the line referenced by the LRP. */
    public double getBearing() {
        return bearing;
    }

    /** @return The distance to the next LRP along the shortest-path. */
    public int getDistanceToNext() {
        return distanceToNext;
    }

    /** @return The functional road class of the line referenced by the LRP. */
    public FunctionalRoadClass getFrc() {
        return frc;
    }

    /** @return The form of way of the line referenced by the LRP. */
    public FormOfWay getFow() {
        return fow;
    }

    /** @return The lowest functional road class to the next LRP. */
    public FunctionalRoadClass getLfrcnp() {
        return lfrcnp;
    }

    /** @return The longitude coordinate. */
    public double getLongitude() {
        return longitude;
    }

    /** @return The latitude coordinate. */
    public double getLatitude() {
        return latitude;
    }
}
