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
package openlr.encoder.location;

import openlr.encoder.TestData;
import openlr.location.*;
import openlr.location.data.Orientation;
import openlr.location.data.SideOfRoad;
import openlr.map.InvalidMapDataException;
import openlr.map.Line;
import openlr.map.MapDatabase;
import openlr.testutils.CommonObjectTestUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.geom.Point2D;
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
    private static final MapDatabase MDB = TestData.getInstance()
            .getMapDatabase();

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
    private static final GeoCoordLocation geoLoc;
    private static final PoiAccessLocation poiLoc;
    private static final PoiAccessLocation poiLocAtNode;
    private static final PointAlongLocation palLoc;
    private static final PointAlongLocation palLocAtNode;

    static {
        Location l = null;
        try {
            l = LocationFactory.createGeoCoordinateLocation("wpGeo",
                    GEO_COORDINATE.x, GEO_COORDINATE.y);
        } catch (InvalidMapDataException e) {
            Assert.fail("Unexpected exception", e);
        }
        geoLoc = (GeoCoordLocation) l;

        try {
            l = LocationFactory.createPoiAccessLocationWithSideAndOrientation(
                    "poi", POI_LOC_LINE, POS_OFFSET, POI.x, POI.y,
                    SideOfRoad.LEFT, Orientation.BOTH);
        } catch (InvalidMapDataException e) {
            Assert.fail("Unexpected exception", e);
        }
        poiLoc = (PoiAccessLocation) l;

        try {
            l = LocationFactory
                    .createPoiAccessAtNodeLocationWithSideAndOrientation(
                            "poiAtNode", POI_LOC_LINE, POI.x, POI.y,
                            SideOfRoad.RIGHT, Orientation.WITH_LINE_DIRECTION);
        } catch (InvalidMapDataException e) {
            Assert.fail("Unexpected exception", e);
        }
        poiLocAtNode = (PoiAccessLocation) l;
        try {
            l = LocationFactory
                    .createPointAlongLineLocationWithSideAndOrientation("pal",
                            POI_LOC_LINE, POS_OFFSET, SideOfRoad.LEFT,
                            Orientation.BOTH);
        } catch (InvalidMapDataException e) {
            Assert.fail("Unexpected exception", e);
        }
        palLoc = (PointAlongLocation) l;
        try {
            l = LocationFactory.createNodeLocation("palAtNode", POI_LOC_LINE);
        } catch (InvalidMapDataException e) {
            Assert.fail("Unexpected exception", e);
        }
        palLocAtNode = (PointAlongLocation) l;
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
            Assert.fail("Unepected exception", e);
        }
    }
}
