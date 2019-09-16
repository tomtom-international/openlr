/**
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

import openlr.location.*;
import openlr.location.data.Orientation;
import openlr.location.data.SideOfRoad;
import openlr.location.utils.LocationData;
import openlr.map.*;
import openlr.map.utils.GeometryUtils;
import openlr.rawLocRef.RawGeoCoordLocRef;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static openlr.testutils.CommonObjectTestUtils.testCompare;
import static openlr.testutils.CommonObjectTestUtils.testToString;
import static org.testng.Assert.fail;

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

    /** The mocking object. */
    private static Mockery mockery = new Mockery();
    /**
     * A mocked line instance with ID = 1;
     */
    private static final Line LINE_1 = mockLine(1);
    /**
     * A mocked line instance with ID = -1;
     */
    private static final Line LINE_MINUS_1 = mockLine(-1);
    /**
     * An instance of {@link GeoCoordinates}.
     */
    private final GeoCoordinates geoCoordinate;
    /**
     * An instance of {@link GeoCoordinates} that should be equal to
     * {@link #geoCoordinate}.
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
     * Delivers a {@link Line} object with the given ID and with some default
     * values that should not be relevant for this tests here.
     *
     * @param id
     *            The ID of the line object.
     * @return The created line object.
     */
    private static Line mockLine(final long id) {

        final Line result = mockery.mock(Line.class, "Line " + id);

        mockery.checking(new Expectations() {
            {
                allowing(result).getID();
                will(returnValue(id));
            }
        });

        mockery.checking(new Expectations() {
            {
                allowing(result).getStartNode();
                will(returnValue(mockNode(id << 1)));
            }
        });
        mockery.checking(new Expectations() {
            {
                allowing(result).getEndNode();
                will(returnValue(mockNode(id << 2)));
            }
        });

        mockery.checking(new Expectations() {
            {
                allowing(result).getGeoCoordinateAlongLine(with(any(Integer.class)));
                will(returnValue(GeoCoordinatesImpl.newGeoCoordinatesUnchecked(1, 1)));
            }
        });

        return result;
    }

    /**
     * Delivers a {@link Node} object with the given ID and with some default
     * values that should not be relevant for this tests here.
     *
     * @param id
     *            The ID of the line object.
     * @return The created line object.
     */
    private static Node mockNode(final long id) {

        final Node result = mockery.mock(Node.class, "Node " + id);

        mockery.checking(new Expectations() {
            {
                allowing(result).getID();
                will(returnValue(id));
            }
        });
        mockery.checking(new Expectations() {
            {
                allowing(result).getLongitudeDeg();
                will(returnValue(0d));
            }
        });
        mockery.checking(new Expectations() {
            {
                allowing(result).getLatitudeDeg();
                will(returnValue(1d));
            }
        });
        mockery.checking(new Expectations() {
            {
                allowing(result).getGeoCoordinates();
                will(returnValue(GeoCoordinatesImpl.newGeoCoordinatesUnchecked(0, 1)));
            }
        });

        return result;
    }

    /**
     * Tests the relevant methods of class {@link GeoCoordLocation}.
     */
    @Test
    public final void testGeoCoordLocation() {

        GeoCoordLocation geoL;
        try {
            geoL = (GeoCoordLocation) LocationFactory
                    .createGeoCoordinateLocation("id",
                            geoCoordinate.getLongitudeDeg(),
                            geoCoordinate.getLatitudeDeg());

            GeoCoordLocation geoLEqual = (GeoCoordLocation) LocationFactory
                    .createGeoCoordinateLocation("idEqual",
                            geoCoordinateEqual.getLongitudeDeg(),
                            geoCoordinateEqual.getLatitudeDeg());
            GeoCoordLocation geoLUnEqual = (GeoCoordLocation) LocationFactory
                    .createGeoCoordinateLocation("od", GeometryUtils.MAX_LON, 0);

            testCompare(geoL, geoLEqual, geoLUnEqual);
            testToString(geoL);

        } catch (InvalidMapDataException e) {
            fail("Unexpected exception", e);
        }
    }

    /**
     * Tests the classes implementing the data interfaces.
     */
    @Test
    public final void testInterfaceImplementationObject() {

        testCompare(geoCoordinate, geoCoordinateEqual, geoCoordinateUnEqual);
    }

    /**
     * Tests the raw location reference classes.
     */
    @Test
    public final void testRawLocationReferenceObjects() {

        RawGeoCoordLocRef geoL = new RawGeoCoordLocRef("geo", geoCoordinate);
        RawGeoCoordLocRef geoLEqual = new RawGeoCoordLocRef("geoEq",
                geoCoordinateEqual);
        RawGeoCoordLocRef geoLUnEqual = new RawGeoCoordLocRef("geoUnequ", geoCoordinateUnEqual);

        testCompare(geoL, geoLEqual, geoLUnEqual);

        testToString(geoL);
    }

    /**
     * Tests the relevant methods of class {@link GeoCoordLocation}.
     */
    @Test
    public final void testLineLocation() {
        List<Line> lines = new ArrayList<Line>(2);
        lines.add(LINE_1);
        lines.add(null); // special test besides: null entry
        lines.add(LINE_MINUS_1);

        LineLocation lineLoc = (LineLocation) LocationFactory
                .createLineLocationWithOffsets("id", lines, 1, 2);
        LineLocation lineLocEqual = (LineLocation) LocationFactory
                .createLineLocationWithOffsets("id", lines, 1, 2);
        LineLocation lineLocUnequal = (LineLocation) LocationFactory
                .createLineLocationWithOffsets("od", lines, 0, 2);
        testCompare(lineLoc, lineLocEqual, lineLocUnequal);
        testToString(lineLoc);

    }

    /**
     * Tests the relevant methods of class {@link GeoCoordLocation}.
     */
    @Test
    public final void testPoiAccessLocation() {

        PoiAccessLocation poiLoc;
        try {
            poiLoc = (PoiAccessLocation) LocationFactory
                    .createPoiAccessAtNodeLocationWithSideAndOrientation("id",
                            LINE_1, GeometryUtils.MIN_LON, GeometryUtils.MAX_LAT,
                            SideOfRoad.LEFT, Orientation.BOTH);

            PoiAccessLocation poiLocEqual = (PoiAccessLocation) LocationFactory
                    .createPoiAccessAtNodeLocationWithSideAndOrientation("id",
                            LINE_1, GeometryUtils.MIN_LON, GeometryUtils.MAX_LAT,
                            SideOfRoad.LEFT, Orientation.BOTH);
            PoiAccessLocation poiLocUnEqual = (PoiAccessLocation) LocationFactory
                    .createPoiAccessAtNodeLocationWithSideAndOrientation("id",
                            LINE_1, GeometryUtils.MAX_LON, GeometryUtils.MAX_LAT,
                            SideOfRoad.RIGHT, Orientation.BOTH);
            testCompare(poiLoc, poiLocEqual, poiLocUnEqual);
            testToString(poiLoc);

        } catch (InvalidMapDataException e) {
            fail("Unexpected exception", e);
        }
    }

    /**
     * Tests the relevant methods of class {@link GeoCoordLocation}.
     */
    @Test
    public final void testPointAlongLocation() {

        PointAlongLocation palLoc;
        try {
            palLoc = (PointAlongLocation) LocationFactory
                    .createPointAlongLineLocationWithSideAndOrientation("id",
                            LINE_1, 1, SideOfRoad.LEFT, Orientation.BOTH);

            PointAlongLocation palLocEqual = (PointAlongLocation) LocationFactory
                    .createPointAlongLineLocationWithSideAndOrientation("id",
                            LINE_1, 1, SideOfRoad.LEFT, Orientation.BOTH);
            // the line ID differs!
            PointAlongLocation palLocUnEqualLine = (PointAlongLocation) LocationFactory
                    .createPointAlongLineLocationWithSideAndOrientation("id",
                            LINE_MINUS_1, 2, SideOfRoad.LEFT, Orientation.BOTH);
            testCompare(palLoc, palLocEqual, palLocUnEqualLine);

            // the offset differs
            PointAlongLocation palLocUnEqualMember = (PointAlongLocation) LocationFactory
                    .createPointAlongLineLocationWithSideAndOrientation("id",
                            LINE_1, 0, SideOfRoad.LEFT, Orientation.BOTH);
            testCompare(palLoc, palLocEqual, palLocUnEqualMember);

            testToString(palLoc);
        } catch (InvalidMapDataException e) {
            fail("Unexpected exception", e);
        }
    }

    /**
     * Tests toString of class {@link LocationData}.
     */
    @Test
    public final void testLocationData() {

        LocationData ld = new LocationData();
        testToString(ld);
    }
}
