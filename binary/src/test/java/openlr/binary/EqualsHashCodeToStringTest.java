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

import openlr.LocationReferencePoint;
import openlr.LocationType;
import openlr.binary.data.*;
import openlr.binary.impl.LocationReferenceBinaryImpl;
import openlr.binary.impl.LocationReferencePointBinaryImpl;
import openlr.binary.impl.OffsetsBinaryImpl;
import openlr.location.data.Orientation;
import openlr.location.data.SideOfRoad;
import openlr.map.*;
import openlr.map.utils.GeometryUtils;
import openlr.rawLocRef.RawInvalidLocRef;
import openlr.rawLocRef.RawLineLocRef;
import openlr.rawLocRef.RawPoiAccessLocRef;
import openlr.rawLocRef.RawPointAlongLocRef;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static openlr.testutils.CommonObjectTestUtils.testCompare;
import static openlr.testutils.CommonObjectTestUtils.testToString;

/**
 * This class performs tests of the toString, hashCode and equals methods of 
 * several classes.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class EqualsHashCodeToStringTest {

    /**
     * An instance of {@link LocationReferencePointBinaryImpl}.
     */
    private static final LocationReferencePointBinaryImpl LRP_IMPL = new LocationReferencePointBinaryImpl(
            1, FunctionalRoadClass.FRC_2, FormOfWay.MOTORWAY, Double.MAX_VALUE,
            Double.MIN_VALUE, Double.MIN_NORMAL, 0, FunctionalRoadClass.FRC_4,
            false);

    /**
     * A {@link LocationReferencePointBinaryImpl} instance that should not be
     * equal to {@link #LRP_IMPL}.
     */
    private static final LocationReferencePointBinaryImpl LRP_IMPL_UNEQUAL = new LocationReferencePointBinaryImpl(
            1, FunctionalRoadClass.FRC_1, FormOfWay.MOTORWAY, Double.MAX_VALUE,
            Double.MIN_VALUE, Double.MIN_NORMAL, 2, FunctionalRoadClass.FRC_4,
            false);
    /**
     * An instance of {@link OffsetsBinaryImpl}.
     */
    private static final OffsetsBinaryImpl OFFSETS_IMPL = new OffsetsBinaryImpl(
            2, 1);
    /**
     * An instance of {@link OffsetsBinaryImpl} that should be equal to
     * {@link #OFFSETS_IMPL}.
     */
    private static final OffsetsBinaryImpl OFFSETS_IMPL_EQUAL = new OffsetsBinaryImpl(
            2, 1);
    /**
     * An instance of {@link GeoCoordinates}.
     */
    private final GeoCoordinates geoCoordinate;
    /**
     * An instance of {@link GeoCoordinates} that should be
     * equal to {@link #geoCoordinate}.
     */
    private final GeoCoordinates geoCoordinateEqual;
    /**
     * An instance of {@link GeoCoordinates} that should not be
     * equal to {@link #geoCoordinate}.
     */
    private final GeoCoordinates geoCoordinateUnEqual;

    /**
     * Sets up some fields
     * @throws InvalidMapDataException If an error occurs setting up some map objects
     */
    public EqualsHashCodeToStringTest() throws InvalidMapDataException {

        geoCoordinate = new GeoCoordinatesImpl(GeometryUtils.MAX_LON,
                GeometryUtils.MIN_LAT);

        geoCoordinateEqual = new GeoCoordinatesImpl(GeometryUtils.MAX_LON,
                GeometryUtils.MIN_LAT);

        geoCoordinateUnEqual = new GeoCoordinatesImpl(GeometryUtils.MAX_LON, 0);
    }

    /**
     * Tests the raw location reference classes.
     */
    @Test
    public final void testRawLocationReferenceObjects() {


        List<LocationReferencePoint> lrps = new ArrayList<LocationReferencePoint>(
                2);
        lrps.add(LRP_IMPL);
        lrps.add(LRP_IMPL_UNEQUAL);

        RawLineLocRef lineLoc = new RawLineLocRef("line", lrps, OFFSETS_IMPL);
        RawLineLocRef lineLocEqual = new RawLineLocRef("lineEq", lrps, OFFSETS_IMPL_EQUAL);
        RawLineLocRef lineLocUnequal = new RawLineLocRef("lineUnEq", lrps,
                new OffsetsBinaryImpl(2, 0));
        testCompare(lineLoc, lineLocEqual, lineLocUnequal);

        RawPoiAccessLocRef poiLoc = new RawPoiAccessLocRef("poi", LRP_IMPL,
                LRP_IMPL_UNEQUAL, OFFSETS_IMPL, geoCoordinate, SideOfRoad.BOTH,
                Orientation.AGAINST_LINE_DIRECTION);
        RawPoiAccessLocRef poiLocEqual = new RawPoiAccessLocRef("poiEq",
                LRP_IMPL, LRP_IMPL_UNEQUAL, OFFSETS_IMPL_EQUAL,
                geoCoordinateEqual, SideOfRoad.BOTH,
                Orientation.AGAINST_LINE_DIRECTION);
        RawPoiAccessLocRef poiLocUnEqual = new RawPoiAccessLocRef("poiUnEq",
                LRP_IMPL, LRP_IMPL_UNEQUAL, OFFSETS_IMPL_EQUAL,
                geoCoordinateEqual, SideOfRoad.LEFT,
                Orientation.AGAINST_LINE_DIRECTION);
        testCompare(poiLoc, poiLocEqual, poiLocUnEqual);
        testToString(poiLoc);

        RawPointAlongLocRef palLoc = new RawPointAlongLocRef("pal", LRP_IMPL, LRP_IMPL_UNEQUAL, OFFSETS_IMPL,
                SideOfRoad.LEFT, Orientation.BOTH);
        RawPointAlongLocRef palLocEqual = new RawPointAlongLocRef("palEq",
                LRP_IMPL, LRP_IMPL_UNEQUAL, OFFSETS_IMPL_EQUAL,
                SideOfRoad.LEFT, Orientation.BOTH);
        RawPointAlongLocRef palLocUnequal = new RawPointAlongLocRef("palUnEq", LRP_IMPL, LRP_IMPL_UNEQUAL,
                OFFSETS_IMPL, SideOfRoad.RIGHT, Orientation.BOTH);
        testCompare(palLoc, palLocEqual, palLocUnequal);
        testToString(palLoc);

        RawInvalidLocRef invLoc = new RawInvalidLocRef("inv",
                BinaryReturnCode.INVALID_HEADER);
        RawInvalidLocRef invLocEqual = new RawInvalidLocRef("invEq",
                BinaryReturnCode.INVALID_HEADER);
        RawInvalidLocRef invLocUnEqual = new RawInvalidLocRef("invUneq",
                BinaryReturnCode.INVALID_VERSION);
        testCompare(invLoc, invLocEqual, invLocUnEqual);
        testToString(invLoc);
    }

    /**
     * Tests the classes implementing the data interfaces.
     */
    @Test
    public final void testInterfaceImplementationObject() {

        testCompare(
                new LocationReferenceBinaryImpl("id1",
                        BinaryReturnCode.INVALID_OFFSET,
                        LocationType.LINE_LOCATION, 2),
                new LocationReferenceBinaryImpl("id1",
                        BinaryReturnCode.INVALID_OFFSET,
                        LocationType.LINE_LOCATION, 2),
                new LocationReferenceBinaryImpl("id",
                        BinaryReturnCode.INVALID_OFFSET,
                        LocationType.GEO_COORDINATES, 2));

        LocationReferencePointBinaryImpl lrpEqual = new LocationReferencePointBinaryImpl(
                1, FunctionalRoadClass.FRC_2, FormOfWay.MOTORWAY,
                Double.MAX_VALUE, Double.MIN_VALUE, Double.MIN_NORMAL, 0,
                FunctionalRoadClass.FRC_4, false);
        testCompare(LRP_IMPL, lrpEqual, LRP_IMPL_UNEQUAL);

        testCompare(new OffsetsBinaryImpl(0, 1), new OffsetsBinaryImpl(0, 1),
                new OffsetsBinaryImpl(1, 1));
    }

    /**
     * Tests the binary data classes.
     */
    @Test
    public final void testDataObjects() {

        ByteArray ba = new ByteArray(new byte[]{1, 2});
        testCompare(ba, new ByteArray(new byte[]{1, 2}), new ByteArray(
                new byte[]{1}));
        testToString(ba);

        AbsoluteCoordinates ac = new AbsoluteCoordinates(Integer.MIN_VALUE,
                Integer.MAX_VALUE);
        testCompare(ac, new AbsoluteCoordinates(Integer.MIN_VALUE,
                Integer.MAX_VALUE), new AbsoluteCoordinates(Integer.MAX_VALUE,
                Integer.MIN_VALUE));
        testToString(ac);

        RelativeCoordinates rc = new RelativeCoordinates(Integer.MIN_VALUE,
                Integer.MAX_VALUE);
        testCompare(rc, new RelativeCoordinates(Integer.MIN_VALUE,
                Integer.MAX_VALUE), new RelativeCoordinates(Integer.MAX_VALUE,
                Integer.MIN_VALUE));
        testToString(rc);

        Attr1 a1 = new Attr1(1, 2, 1);
        Attr2 a2 = new Attr2(1, 2);
        Attr3 a3 = new Attr3(1);
        Attr4 a4 = new Attr4(1, 2, 0);

        testCompare(a1, new Attr1(1, 2, 1), new Attr1(1, 0, 1));
        testCompare(a2, new Attr2(1, 2), new Attr2(1, 0));
        testCompare(a3, new Attr3(1), new Attr3(0));
        testCompare(a4, new Attr4(1, 2, 0), new Attr4(1, 2, 2));

        testCompare(new FirstLRP(Integer.MIN_VALUE, Integer.MAX_VALUE, a1, a2,
                a3), new FirstLRP(Integer.MIN_VALUE, Integer.MAX_VALUE, a1, a2,
                a3), new FirstLRP(Integer.MIN_VALUE, Integer.MAX_VALUE, a1, a2,
                new Attr3(0)));

        testCompare(new IntermediateLRP(Integer.MIN_VALUE, Integer.MAX_VALUE,
                a1, a2, a3), new IntermediateLRP(Integer.MIN_VALUE,
                Integer.MAX_VALUE, a1, a2, a3), new IntermediateLRP(
                Integer.MIN_VALUE, Integer.MAX_VALUE, a1, a2, new Attr3(0)));

        testCompare(new LastLRP(Integer.MIN_VALUE, Integer.MAX_VALUE, a1, a4),
                new LastLRP(Integer.MIN_VALUE, Integer.MAX_VALUE, a1, a4),
                new LastLRP(Integer.MIN_VALUE, 0, a1, a4));

        testCompare(new Offset(2), new Offset(2), new Offset(1));

        testCompare(new Header(Integer.MIN_VALUE, Integer.MAX_VALUE, 1, 0),
                new Header(Integer.MIN_VALUE, Integer.MAX_VALUE, 1, 0),
                new Header(Integer.MIN_VALUE, Integer.MAX_VALUE, 1, 1));
    }
}
