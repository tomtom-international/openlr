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
 * Copyright (C) 2009,2010 TomTom International B.V.
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
 *  Copyright (C) 2009,2010 TomTom International B.V.
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
package openlr.location;

import openlr.LocationType;
import openlr.StatusCode;
import openlr.location.data.Orientation;
import openlr.location.data.SideOfRoad;
import openlr.map.*;
import openlr.map.mockdb.InvalidConfigurationException;
import openlr.map.mockdb.MockedMapDatabase;
import openlr.map.utils.GeometryUtils;
import openlr.testutils.CommonObjectTestUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Tests the creation of the different location type objects.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class LocationTypesTest {

    /**
     * The default map configuration. <b> This configuration represents the
     * ported configuration from the Java code to the XML file after
     * introduction of the configurable database mocking. For future tests
     * please create {@link MockedMapDatabase} instances with smaller map
     * configurations. </b>
     */
    public static final String DEFAULT_MAP_DB_CONFIG = "DefaultMapDatabase.xml";
    /**
     * A negative offset.
     */
    private static final int NEG_OFFSET = 30;
    /**
     * A positive offset.
     */
    private static final int POS_OFFSET = 20;
    /**
     * The GEO-coordinate of the White Paper example, x = longitude, y =
     * latitude.
     */
    private static final Point2D.Double GEO_COORDINATE = new Point2D.Double(
            6.12699, 49.60728);
    /** A reference to the map database. */
    private static final MapDatabase MDB;
    /** The latitude of the tested geo coordinate. */
    private static final double COORDINATE_LATITUDE = 49.60728;
    /** The longitude of the tested geo coordinate. */
    private static final double COORDINATE_LONGITUDE = 6.12699;
    /** The Constant RADIUS. */
    private static final long RADIUS_SMALL = 100;
    /** The Constant RADIUS_MEDIUM. */
    private static final long RADIUS_MEDIUM = 60000;
    /** The Constant RADIUS_LARGE. */
    private static final long RADIUS_LARGE = 16000000;
    /** The Constant RADIUS_EXTRA_LARGE. */
    private static final long RADIUS_EXTRA_LARGE = 4000000000L;
    private static final int ROWS = 5;
    private static final int COLUMNS = 4;
    private static final GeoCoordinates RECTANGLE_LL;
    private static final GeoCoordinates RECTANGLE_UR;
    private static final List<GeoCoordinates> RECTANGLE_CORNERS = new ArrayList<GeoCoordinates>();
    private static final List<GeoCoordinates> CORNERS = new ArrayList<GeoCoordinates>();
    private static final GeoCoordinates SCALED_UR;
    private static final List<GeoCoordinates> SCALED_RECTANGLE_CORNERS = new ArrayList<GeoCoordinates>();

    /**
     * Gets the default mocked map database. <br>
     * <b>Note! This global instance of map database shouldn't be used any more.
     * Our new approach is to generate smaller instances of mocked map databases
     * via configuration for each single test. For future tests please create
     * {@link MockedMapDatabase} instances with smaller map configurations.</b>
     *
     * @return the map database
     *
     * @see MockedMapDatabase
     */
    static {
        try {
            MDB = new MockedMapDatabase(DEFAULT_MAP_DB_CONFIG, false);
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(
                    "Unexpected exception during mock build-up from config "
                            + "file " + DEFAULT_MAP_DB_CONFIG, e);
        }
    }



    /**
     * The list of lines used for creating the line locations.
     */
    private static final List<Line> LINES_LINE_LOCATION = Arrays.asList(
            MDB.getLine(4), MDB.getLine(6));
    /**
     * The line used for the POI with access point location.
     */
    private static final Line POI_LOC_LINE = MDB.getLine(6);
    /**
     * The POI in the point with access point example, x = longitude, y =
     * latitude.
     */
    private static final Point2D.Double POI = new Point2D.Double(6.12699,
            49.60728);
    /**
     * Another POI, x = longitude, y = latitude.
     */
    private static final Point2D.Double POI2 = new Point2D.Double(6.1256,
            49.607);
    /** The Constant geoLoc. */
    private static final GeoCoordLocation geoLoc;
    /** The Constant poiLoc. */
    private static final PoiAccessLocation poiLoc;
    /** The Constant poiLocAtNode. */
    private static final PoiAccessLocation poiLocAtNode;
    /** The Constant palLoc. */
    private static final PointAlongLocation palLoc;
    /** The Constant palLocAtNode. */
    private static final PointAlongLocation palLocAtNode;
    /** The Constant circleSmall. */
    private static final CircleLocation circleSmall;
    /** The Constant circleMedium. */
    private static final CircleLocation circleMedium;
    /** The Constant circleLarge. */
    private static final CircleLocation circleLarge;
    /** The Constant circleExtraLarge. */
    private static final CircleLocation circleExtraLarge;
    /** The Constant rectangle. */
    private static final RectangleLocation rectangle;
    /** The Constant gridBasis. */
    private static final GridLocation gridBasis;
    /** The Constant gridArea. */
    private static final GridLocation gridArea;
    /** The Constant polygon. */
    private static final PolygonLocation polygon;
    /** The Constant closedLine. */
    private static final ClosedLineLocation closedLine;

    static {
        GeoCoordinates ll = null;
        GeoCoordinates ur = null;
        GeoCoordinates scaled = null;
        try {
            ll = new GeoCoordinatesImpl(5.09030, 52.08591);
            ur = new GeoCoordinatesImpl(5.12141, 52.10437);
            scaled = new GeoCoordinatesImpl(5.09808, 52.08960);
            CORNERS.add(new GeoCoordinatesImpl(5.06853, 52.05942));
            CORNERS.add(new GeoCoordinatesImpl(5.06019, 52.07334));
            CORNERS.add(new GeoCoordinatesImpl(5.08782, 52.07235));
            RECTANGLE_CORNERS.add(ll);
            RECTANGLE_CORNERS.add(new GeoCoordinatesImpl(5.12141, 52.08591));
            RECTANGLE_CORNERS.add(ur);
            RECTANGLE_CORNERS.add(new GeoCoordinatesImpl(5.0903, 52.10437));
            SCALED_RECTANGLE_CORNERS.add(ll);
            SCALED_RECTANGLE_CORNERS.add(new GeoCoordinatesImpl(5.09808, 52.08591));
            SCALED_RECTANGLE_CORNERS.add(scaled);
            SCALED_RECTANGLE_CORNERS.add(new GeoCoordinatesImpl(5.09030, 52.08960));
        } catch (InvalidMapDataException e) {
            Assert.fail("unexpected exception", e);
        }
        RECTANGLE_LL = ll;
        RECTANGLE_UR = ur;
        SCALED_UR = scaled;
    }

    static {
        Location l = null;
        try {
            l = LocationFactory.createGeoCoordinateLocation("wpGeo",
                    GEO_COORDINATE.x, GEO_COORDINATE.y);
        } catch (InvalidMapDataException e) {
            Assert.fail("unexpected exception", e);
        }
        geoLoc = (GeoCoordLocation) l;

        try {
            l = LocationFactory.createPoiAccessLocationWithSideAndOrientation(
                    "poi", POI_LOC_LINE, POS_OFFSET, POI.x, POI.y,
                    SideOfRoad.LEFT, Orientation.BOTH);
        } catch (InvalidMapDataException e) {
            Assert.fail("unexpected exception", e);
        }
        poiLoc = (PoiAccessLocation) l;

        try {
            l = LocationFactory
                    .createPoiAccessAtNodeLocationWithSideAndOrientation(
                            "poiAtNode", POI_LOC_LINE, POI.x, POI.y,
                            SideOfRoad.RIGHT, Orientation.WITH_LINE_DIRECTION);
        } catch (InvalidMapDataException e) {
            Assert.fail("unexpected exception", e);
        }
        poiLocAtNode = (PoiAccessLocation) l;

        try {
            l = LocationFactory
                    .createPointAlongLineLocationWithSideAndOrientation("pal",
                            POI_LOC_LINE, POS_OFFSET, SideOfRoad.LEFT,
                            Orientation.BOTH);
        } catch (InvalidMapDataException e) {
            Assert.fail("unexpected exception", e);
        }
        palLoc = (PointAlongLocation) l;
        try {
            l = LocationFactory.createNodeLocation("palAtNode", POI_LOC_LINE);
        } catch (InvalidMapDataException e) {
            Assert.fail("unexpected exception", e);
        }
        palLocAtNode = (PointAlongLocation) l;

        try {
            l = LocationFactory.createCircleLocation("circleSmall",
                    COORDINATE_LONGITUDE, COORDINATE_LATITUDE, RADIUS_SMALL);
        } catch (InvalidMapDataException e) {
            Assert.fail("unexpected exception", e);
        }
        circleSmall = (CircleLocation) l;

        try {
            l = LocationFactory.createCircleLocation("circleMedium",
                    COORDINATE_LONGITUDE, COORDINATE_LATITUDE, RADIUS_MEDIUM);
        } catch (InvalidMapDataException e) {
            Assert.fail("unexpected exception", e);
        }
        circleMedium = (CircleLocation) l;

        try {
            l = LocationFactory.createCircleLocation("circleSmall",
                    COORDINATE_LONGITUDE, COORDINATE_LATITUDE, RADIUS_LARGE);
        } catch (InvalidMapDataException e) {
            Assert.fail("unexpected exception", e);
        }
        circleLarge = (CircleLocation) l;

        try {
            l = LocationFactory.createCircleLocation("circleSmall",
                    COORDINATE_LONGITUDE, COORDINATE_LATITUDE,
                    RADIUS_EXTRA_LARGE);
        } catch (InvalidMapDataException e) {
            Assert.fail("unexpected exception", e);
        }
        circleExtraLarge = (CircleLocation) l;

        try {
            l = LocationFactory.createRectangleLocation("rectangle",
                    RECTANGLE_LL, RECTANGLE_UR);
        } catch (InvalidMapDataException e) {
            Assert.fail("unexpected exception", e);
        }
        rectangle = (RectangleLocation) l;

        try {
            l = LocationFactory.createGridLocationFromBasisCell("gridBasis",
                    RECTANGLE_LL, RECTANGLE_UR, COLUMNS, ROWS);
        } catch (InvalidMapDataException e) {
            Assert.fail("unexpected exception", e);
        }
        gridBasis = (GridLocation) l;

        try {
            l = LocationFactory.createGridLocationFromGridArea("gridArea",
                    RECTANGLE_LL, RECTANGLE_UR, COLUMNS, ROWS);
        } catch (InvalidMapDataException e) {
            Assert.fail("unexpected exception", e);
        }
        gridArea = (GridLocation) l;

        polygon = (PolygonLocation) LocationFactory.createPolygonLocation(
                "polygon", CORNERS);

        closedLine = (ClosedLineLocation) LocationFactory
                .createClosedLineLocation("closedLine", LINES_LINE_LOCATION);

    }

    /**
     * A line location with offsets.
     */
    private LineLocation lineLocOff = (LineLocation) LocationFactory
            .createLineLocationWithOffsets("liLoOff", LINES_LINE_LOCATION,
                    POS_OFFSET, NEG_OFFSET);
    /**
     * A line location without offsets.
     */
    private LineLocation lineLocNoOff = (LineLocation) LocationFactory
            .createLineLocation("liLoNoOff", LINES_LINE_LOCATION);

    /**
     * Tests the {@link GeoCoordLocation}.
     */
    @Test
    public final void testGeoLocation() {

        assertNull(geoLoc.getLocationLines());
        assertFalse(geoLoc.hasNegativeOffset());
        assertFalse(geoLoc.hasPositiveOffset());
        assertTrue(geoLoc.getNegativeOffset() <= 0);
        assertTrue(geoLoc.getPositiveOffset() <= 0);
        assertNull(geoLoc.getPoiLine());
        assertNull(geoLoc.getSideOfRoad());
        assertNull(geoLoc.getOrientation());
        assertNull(geoLoc.getAccessPoint());

        assertEquals(geoLoc.getPointLocation().getLongitudeDeg(),
                GEO_COORDINATE.x);
        assertEquals(geoLoc.getPointLocation().getLatitudeDeg(),
                GEO_COORDINATE.y);

        assertTrue(geoLoc.isValid());
        assertNull(geoLoc.getReturnCode());
        assertNotNull(geoLoc.toString());
    }

    /**
     * Tests the {@link PoiAccessLocation}.
     */
    @Test
    public final void testPoiWithAccessLocation() {

        PoiAccessLocation[] toCheck = new PoiAccessLocation[]{poiLoc,
                poiLocAtNode};
        // common checks for both types
        for (PoiAccessLocation loc : toCheck) {

            String message = "Failed assertion while checking location: \""
                    + loc.getID() + "\"";

            assertNull(loc.getLocationLines(), message);
            assertFalse(loc.hasNegativeOffset(), message);
            assertTrue(loc.getNegativeOffset() <= 0, message);
            assertEquals(loc.getPoiLine().getID(), POI_LOC_LINE.getID(),
                    message);

            // The actual value of the access point shall not be subject of this
            // test. It is mocked anyway.
            assertNotNull(poiLoc.getAccessPoint(), message);

            assertEquals(loc.getPointLocation().getLongitudeDeg(), POI.x,
                    message);
            assertEquals(loc.getPointLocation().getLatitudeDeg(), POI.y,
                    message);

            assertTrue(loc.isValid());
            assertNull(loc.getReturnCode());
            assertNotNull(loc.toString());
        }

        assertTrue(poiLoc.hasPositiveOffset());
        assertEquals(poiLoc.getPositiveOffset(), POS_OFFSET);
        assertEquals(poiLoc.getSideOfRoad(), SideOfRoad.LEFT);
        assertEquals(poiLoc.getOrientation(), Orientation.BOTH);

        assertFalse(poiLocAtNode.hasPositiveOffset());
        assertTrue(poiLocAtNode.getPositiveOffset() <= 0);
        assertEquals(poiLocAtNode.getSideOfRoad(), SideOfRoad.RIGHT);
        assertEquals(poiLocAtNode.getOrientation(),
                Orientation.WITH_LINE_DIRECTION);
    }

    /**
     * Tests the {@link PointAlongLocation}.
     */
    @Test
    public final void testPointAlongLocation() {

        PointAlongLocation[] toCheck = new PointAlongLocation[]{palLoc,
                palLocAtNode};
        // common checks for both types
        for (PointAlongLocation loc : toCheck) {

            String message = "Failed assertion while checking location: \""
                    + loc.getID() + "\"";

            assertNull(loc.getLocationLines(), message);
            assertFalse(loc.hasNegativeOffset(), message);
            assertTrue(loc.getNegativeOffset() <= 0, message);
            assertEquals(loc.getPoiLine().getID(), POI_LOC_LINE.getID(),
                    message);

            // The actual value of the access point shall not be subject of this
            // test. It is mocked anyway.
            assertNotNull(poiLoc.getAccessPoint(), message);

            assertNull(loc.getPointLocation(), message);

            assertTrue(loc.isValid());
            assertNull(loc.getReturnCode());
            assertNotNull(loc.toString());
        }

        assertTrue(palLoc.hasPositiveOffset());
        assertEquals(palLoc.getPositiveOffset(), POS_OFFSET);
        assertEquals(palLoc.getSideOfRoad(), SideOfRoad.LEFT);
        assertEquals(palLoc.getOrientation(), Orientation.BOTH);

        assertFalse(palLocAtNode.hasPositiveOffset());
        assertTrue(palLocAtNode.getPositiveOffset() <= 0);
        assertEquals(palLocAtNode.getSideOfRoad(), SideOfRoad.getDefault());
        assertEquals(palLocAtNode.getOrientation(), Orientation.getDefault());
    }

    /**
     * Tests the {@link LineLocation}.
     */
    @Test
    public final void testLineLocation() {

        LineLocation[] toCheck = new LineLocation[]{lineLocNoOff, lineLocOff};
        // common checks for both types
        for (LineLocation loc : toCheck) {

            String message = "Failed assertion while checking location: \""
                    + loc.getID() + "\"";

            int i = 0;
            for (Line line : loc.getLocationLines()) {
                assertEquals(line.getID(), LINES_LINE_LOCATION.get(i++).getID());
            }

            assertNull(loc.getPoiLine(), message);
            assertNull(loc.getSideOfRoad(), message);
            assertNull(loc.getOrientation(), message);
            assertNull(loc.getAccessPoint(), message);
            assertNull(loc.getPointLocation(), message);

            assertTrue(loc.isValid());
            assertNull(loc.getReturnCode());
            assertNotNull(loc.toString(), message);
        }

        assertTrue(lineLocOff.hasNegativeOffset());
        assertTrue(lineLocOff.hasPositiveOffset());
        assertEquals(lineLocOff.getNegativeOffset(), NEG_OFFSET);
        assertEquals(lineLocOff.getPositiveOffset(), POS_OFFSET);

        assertFalse(lineLocNoOff.hasNegativeOffset());
        assertFalse(lineLocNoOff.hasPositiveOffset());
        assertTrue(lineLocNoOff.getNegativeOffset() <= 0);
        assertTrue(lineLocNoOff.getPositiveOffset() <= 0);
    }

    /**
     * Test circle location.
     */
    @Test
    public final void testCircleLocation() {
        List<CircleLocation> circles = new ArrayList<CircleLocation>();
        circles.add(circleSmall);
        circles.add(circleMedium);
        circles.add(circleLarge);
        circles.add(circleExtraLarge);

        for (CircleLocation c : circles) {
            assertNotNull(c.getID());
            assertNull(c.getAccessPoint());
            assertNull(c.getAffectedLines());
            assertNotNull(c.getCenterPoint());
            assertEquals(c.getCenterPoint().getLongitudeDeg(),
                    COORDINATE_LONGITUDE);
            assertEquals(c.getCenterPoint().getLatitudeDeg(),
                    COORDINATE_LATITUDE);
            assertNull(c.getCornerPoints());
            assertNull(c.getLocationLines());
            assertEquals(c.getLocationType(), LocationType.CIRCLE);
            assertNull(c.getLowerLeftPoint());
            assertTrue(c.getNegativeOffset() <= 0);
            assertEquals(c.getNumberOfColumns(), -1);
            assertEquals(c.getNumberOfRows(), -1);
            assertNull(c.getOrientation());
            assertNull(c.getPoiLine());
            assertNull(c.getPointLocation());
            assertNull(c.getReturnCode());
            assertTrue(c.getPositiveOffset() <= 0);
            assertNull(c.getSideOfRoad());
            assertNull(c.getUpperRightPoint());
            assertTrue(c.isValid());
            assertFalse(c.hasNegativeOffset());
            assertFalse(c.hasPositiveOffset());
        }
        assertEquals(circleSmall.getRadius(), RADIUS_SMALL);
        assertEquals(circleMedium.getRadius(), RADIUS_MEDIUM);
        assertEquals(circleLarge.getRadius(), RADIUS_LARGE);
        assertEquals(circleExtraLarge.getRadius(), RADIUS_EXTRA_LARGE);

    }

    /**
     * Test rectangle.
     */
    @Test
    public final void testRectangle() {
        assertNotNull(rectangle.getID());
        assertNull(rectangle.getAccessPoint());
        assertNull(rectangle.getAffectedLines());
        assertNull(rectangle.getCenterPoint());

        assertNotNull(rectangle.getCornerPoints());
        assertEquals(rectangle.getCornerPoints().size(),
                RECTANGLE_CORNERS.size());
        for (int i = 0; i < RECTANGLE_CORNERS.size(); i++) {
            assertEquals(rectangle.getCornerPoints().get(i),
                    RECTANGLE_CORNERS.get(i));
        }

        assertNull(rectangle.getLocationLines());
        assertEquals(rectangle.getLocationType(), LocationType.RECTANGLE);
        assertNotNull(rectangle.getLowerLeftPoint());
        assertEquals(rectangle.getLowerLeftPoint(), RECTANGLE_LL);
        assertTrue(rectangle.getNegativeOffset() <= 0);
        assertEquals(rectangle.getNumberOfColumns(), -1);
        assertEquals(rectangle.getNumberOfRows(), -1);
        assertNull(rectangle.getOrientation());
        assertNull(rectangle.getPoiLine());
        assertNull(rectangle.getPointLocation());
        assertNull(rectangle.getReturnCode());
        assertTrue(rectangle.getPositiveOffset() <= 0);
        assertNull(rectangle.getSideOfRoad());
        assertNotNull(rectangle.getUpperRightPoint());
        assertEquals(rectangle.getUpperRightPoint(), RECTANGLE_UR);
        assertTrue(rectangle.isValid());
        assertFalse(rectangle.hasNegativeOffset());
        assertFalse(rectangle.hasPositiveOffset());
        assertEquals(rectangle.getRadius(), -1);
    }

    @Test
    public final void testGrid() {
        List<GridLocation> grids = new ArrayList<GridLocation>();
        grids.add(gridArea);
        grids.add(gridBasis);

        for (GridLocation loc : grids) {
            assertNotNull(loc.getID());
            assertNull(loc.getAccessPoint());
            assertNull(loc.getAffectedLines());
            assertNull(loc.getCenterPoint());
            assertNotNull(loc.getCornerPoints());
            assertNull(loc.getLocationLines());
            assertEquals(loc.getLocationType(), LocationType.GRID);
            assertNotNull(loc.getLowerLeftPoint());
            assertEquals(loc.getLowerLeftPoint(), RECTANGLE_LL);
            assertTrue(loc.getNegativeOffset() <= 0);
            assertEquals(loc.getNumberOfColumns(), COLUMNS);
            assertEquals(loc.getNumberOfRows(), ROWS);
            assertNull(loc.getOrientation());
            assertNull(loc.getPoiLine());
            assertNull(loc.getPointLocation());
            assertNull(loc.getReturnCode());
            assertTrue(loc.getPositiveOffset() <= 0);
            assertNull(loc.getSideOfRoad());
            assertNotNull(loc.getUpperRightPoint());
            assertTrue(loc.isValid());
            assertFalse(loc.hasNegativeOffset());
            assertFalse(loc.hasPositiveOffset());
        }

        assertEquals(gridBasis.getUpperRightPoint(), RECTANGLE_UR);
        assertEquals(gridBasis.getCornerPoints().size(),
                RECTANGLE_CORNERS.size());
        for (int i = 0; i < RECTANGLE_CORNERS.size(); i++) {
            assertEquals(gridBasis.getCornerPoints().get(i),
                    RECTANGLE_CORNERS.get(i));
        }

        assertEquals(GeometryUtils.round(gridArea.getUpperRightPoint()
                .getLongitudeDeg()), SCALED_UR.getLongitudeDeg());
        assertEquals(GeometryUtils.round(gridArea.getUpperRightPoint()
                .getLatitudeDeg()), SCALED_UR.getLatitudeDeg());
        assertEquals(gridArea.getCornerPoints().size(),
                SCALED_RECTANGLE_CORNERS.size());
        for (int i = 0; i < SCALED_RECTANGLE_CORNERS.size(); i++) {
            assertEquals(GeometryUtils.round(gridArea.getCornerPoints().get(i)
                    .getLongitudeDeg()), SCALED_RECTANGLE_CORNERS.get(i)
                    .getLongitudeDeg());
            assertEquals(GeometryUtils.round(gridArea.getCornerPoints().get(i)
                    .getLatitudeDeg()), SCALED_RECTANGLE_CORNERS.get(i)
                    .getLatitudeDeg());
        }


    }

    @Test
    public final void testPolygon() {
        assertNotNull(polygon.getID());
        assertNull(polygon.getAccessPoint());
        assertNull(polygon.getAffectedLines());
        assertNull(polygon.getCenterPoint());

        assertNotNull(polygon.getCornerPoints());
        assertEquals(polygon.getCornerPoints().size(), CORNERS.size());
        for (int i = 0; i < CORNERS.size(); i++) {
            assertEquals(polygon.getCornerPoints().get(i), CORNERS.get(i));
        }

        assertNull(polygon.getLocationLines());
        assertEquals(polygon.getLocationType(), LocationType.POLYGON);
        assertNull(polygon.getLowerLeftPoint());
        assertTrue(polygon.getNegativeOffset() <= 0);
        assertEquals(polygon.getNumberOfColumns(), -1);
        assertEquals(polygon.getNumberOfRows(), -1);
        assertNull(polygon.getOrientation());
        assertNull(polygon.getPoiLine());
        assertNull(polygon.getPointLocation());
        assertNull(polygon.getReturnCode());
        assertTrue(polygon.getPositiveOffset() <= 0);
        assertNull(polygon.getSideOfRoad());
        assertNull(polygon.getUpperRightPoint());
        assertTrue(polygon.isValid());
        assertFalse(polygon.hasNegativeOffset());
        assertFalse(polygon.hasPositiveOffset());
        assertEquals(polygon.getRadius(), -1);
    }

    @Test
    public final void testClosedLine() {
        assertNotNull(closedLine.getID());
        assertNull(closedLine.getAccessPoint());
        assertNull(closedLine.getAffectedLines());
        assertNull(closedLine.getCenterPoint());

        assertNull(closedLine.getCornerPoints());
        assertNotNull(closedLine.getLocationLines());
        assertEquals(closedLine.getLocationLines().size(),
                LINES_LINE_LOCATION.size());
        for (int i = 0; i < LINES_LINE_LOCATION.size(); i++) {
            assertEquals(closedLine.getLocationLines().get(i),
                    LINES_LINE_LOCATION.get(i));
        }
        assertEquals(closedLine.getLocationType(), LocationType.CLOSED_LINE);
        assertNull(closedLine.getLowerLeftPoint());
        assertTrue(closedLine.getNegativeOffset() <= 0);
        assertEquals(closedLine.getNumberOfColumns(), -1);
        assertEquals(closedLine.getNumberOfRows(), -1);
        assertNull(closedLine.getOrientation());
        assertNull(closedLine.getPoiLine());
        assertNull(closedLine.getPointLocation());
        assertNull(closedLine.getReturnCode());
        assertTrue(closedLine.getPositiveOffset() <= 0);
        assertNull(closedLine.getSideOfRoad());
        assertNull(closedLine.getUpperRightPoint());
        assertTrue(closedLine.isValid());
        assertFalse(closedLine.hasNegativeOffset());
        assertFalse(closedLine.hasPositiveOffset());
        assertEquals(closedLine.getRadius(), -1);
    }

    /**
     * Tests the equals and hashCode methods.
     */
    @Test
    public final void testComparison() {
        try {
            GeoCoordLocation geoLoc2 = new GeoCoordLocation(geoLoc);
            CommonObjectTestUtils.testCompare(geoLoc, geoLoc2, lineLocNoOff);

            LineLocation lineLoc2 = new LineLocation(lineLocOff);
            CommonObjectTestUtils.testCompare(lineLocOff, lineLoc2, lineLocNoOff);
            assertFalse(lineLocOff.equals(geoLoc));

            Location poiLoc2 = new PoiAccessLocation(poiLoc);
            Location poiLoc3 = LocationFactory.createPoiAccessLocation("poi2",
                    POI_LOC_LINE, POS_OFFSET, POI2.x, POI2.y);
            CommonObjectTestUtils.testCompare(poiLoc, poiLoc2, poiLoc3);
            assertFalse(poiLoc.equals(geoLoc));

            Location palLoc2 = new PointAlongLocation(palLoc);
            CommonObjectTestUtils.testCompare(palLoc, palLoc2, palLocAtNode);
            assertFalse(palLoc.equals(poiLoc));
        } catch (InvalidMapDataException e) {
            Assert.fail("unexpected exception", e);
        }

        InvalidLocation invalidLoc = new InvalidLocation("inv",
                new TestStatusCode(), LocationType.CIRCLE);
        InvalidLocation invalidLocEqual = new InvalidLocation("inv",
                new TestStatusCode(), LocationType.CIRCLE);
        InvalidLocation invalidLocUnEqual = new InvalidLocation("invUn",
                new TestStatusCode(), LocationType.POLYGON);

        CommonObjectTestUtils.testCompare(invalidLoc, invalidLocEqual,
                invalidLocUnEqual);
        CommonObjectTestUtils.testToString(invalidLoc);

        try {
            RectangleLocation rectangleEqual = (RectangleLocation) LocationFactory.createRectangleLocation("rectangle2",
                    RECTANGLE_LL, RECTANGLE_UR);
            RectangleLocation rectangleUnEqual = (RectangleLocation) LocationFactory.createRectangleLocation("rectangle2",
                    new GeoCoordinatesImpl(0, 0), RECTANGLE_UR);


            CommonObjectTestUtils.testCompare(rectangle, rectangleEqual,
                    rectangleUnEqual);
            CommonObjectTestUtils.testToString(rectangle);
        } catch (InvalidMapDataException e) {
            Assert.fail("unexpected exception", e);
        }

        try {
            GridLocation gridBasisEqual = (GridLocation) LocationFactory.createGridLocationFromBasisCell("gridBasis2",
                    RECTANGLE_LL, RECTANGLE_UR, COLUMNS, ROWS);


            CommonObjectTestUtils.testCompare(gridBasis, gridBasisEqual,
                    gridArea);
            CommonObjectTestUtils.testToString(gridBasis);
        } catch (InvalidMapDataException e) {
            Assert.fail("unexpected exception", e);
        }

        PolygonLocation polygonEqual = (PolygonLocation) LocationFactory
                .createPolygonLocation("polygon2", polygon.getCornerPoints());

        try {
            PolygonLocation polygonUnEqual = (PolygonLocation) LocationFactory
                    .createPolygonLocation("polygonUnequal", Arrays.asList(
                            new GeoCoordinatesImpl(0, 0), new GeoCoordinatesImpl(1, 1)));

            CommonObjectTestUtils.testCompare(polygon, polygonEqual,
                    polygonUnEqual);
            CommonObjectTestUtils.testToString(polygon);
        } catch (InvalidMapDataException e) {
            Assert.fail("unexpected exception", e);
        }

        try {
            CircleLocation circleEqual = (CircleLocation) LocationFactory
                    .createCircleLocation("circle2", circleSmall
                            .getCenterPoint().getLongitudeDeg(), circleSmall
                            .getCenterPoint().getLatitudeDeg(), circleSmall
                            .getRadius());

            CommonObjectTestUtils.testCompare(circleSmall, circleEqual,
                    circleLarge);
            CommonObjectTestUtils.testToString(circleSmall);
        } catch (InvalidMapDataException e) {
            Assert.fail("unexpected exception", e);
        }

        ClosedLineLocation closedLineEqual = (ClosedLineLocation) LocationFactory
                .createClosedLineLocation("closedLine2", LINES_LINE_LOCATION);
        ClosedLineLocation closedLineUnEqual = (ClosedLineLocation) LocationFactory
                .createClosedLineLocation("closedLineUn",
                        Arrays.asList(MDB.getLine(1), MDB.getLine(2)));

        CommonObjectTestUtils.testCompare(closedLine, closedLineEqual,
                closedLineUnEqual);

        CommonObjectTestUtils.testToString(closedLine);
    }

    /**
     * A status code implementation for the test cases.
     */
    private static final class TestStatusCode implements StatusCode {

        @Override
        public String name() {
            return "DummyStatus";
        }

        @Override
        public int getID() {
            return 0;
        }
    }
}
