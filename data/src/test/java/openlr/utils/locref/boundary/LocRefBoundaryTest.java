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
package openlr.utils.locref.boundary;

import openlr.LocationReferencePoint;
import openlr.location.data.Orientation;
import openlr.location.data.SideOfRoad;
import openlr.map.GeoCoordinates;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.InvalidMapDataException;
import openlr.map.RectangleCorners;
import openlr.rawLocRef.*;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * Tests the bounding box calculation on location references.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class LocRefBoundaryTest {

    /**
     * The delta in coordinate values that is valid to pass an assumption
     */
    private static final double VALID_COORDINATE_DELTA = 0.000005;

    /**
     * The mocking context
     */
    private final Mockery mockery = new Mockery();

    /**
     * Tests calculation of a bounding box for a line location
     */
    @Test
    public final void testLineLocationReference() {

        LocationReferencePoint lrp1 = mockLRP(5.110692, 52.100590, 147);
        LocationReferencePoint lrp2 = mockLRP(5.108922, 52.100300, 0);

        RawLineLocRef lineLocRef = new RawLineLocRef("id", Arrays.asList(lrp1,
                lrp2), null);

        calculateBox(lineLocRef, 5.10861, 52.09999, 5.11100, 52.1009);
    }

    /**
     * Tests calculation of a bounding box for a point along the line location
     */
    @Test
    public final void testPointAlongLineLocationReference() {

        LocationReferencePoint lrp1 = mockLRP(5.10009, 52.10825, 147);
        LocationReferencePoint lrp2 = mockLRP(5.10115, 52.10926, 0);

        RawPointAlongLocRef lineLocRef = new RawPointAlongLocRef("id", lrp1,
                lrp2, null, SideOfRoad.ON_ROAD_OR_UNKNOWN,
                Orientation.NO_ORIENTATION_OR_UNKNOWN);

        calculateBox(lineLocRef, 5.099618, 52.107777, 5.10162, 52.10973);
    }

    /**
     * Tests calculation of a bounding box for a rectangle location reference
     *
     * @throws InvalidMapDataException
     *             never
     */
    @Test
    public final void testRectangleLocationReference()
            throws InvalidMapDataException {

        GeoCoordinates ll = new GeoCoordinatesImpl(5.10007, 52.10321);
        GeoCoordinates ur = new GeoCoordinatesImpl(5.10398, 52.10704);

        RawRectangleLocRef lineLocRef = new RawRectangleLocRef("id", ll, ur);

        calculateBox(lineLocRef, ll.getLatitudeDeg(), ll.getLongitudeDeg(),
                ur.getLatitudeDeg(), ur.getLongitudeDeg());
    }

    /**
     * Tests calculation of a bounding box for a grid location reference
     *
     * @throws InvalidMapDataException
     *             never
     */
    @Test
    public final void testGridLocationReference()
            throws InvalidMapDataException {

        GeoCoordinates ll = new GeoCoordinatesImpl(5.10007, 52.10321);
        GeoCoordinates ur = new GeoCoordinatesImpl(5.10398, 52.10704);

        RawGridLocRef lineLocRef = new RawGridLocRef("id", ll, ur, 3, 2);

        calculateBox(lineLocRef, ll.getLatitudeDeg(), ll.getLongitudeDeg(),
                52.11087, 5.1118);
    }

    /**
     * Tests calculation of a bounding box for a circle location reference
     *
     * @throws InvalidMapDataException
     *             never
     */
    @Test
    public final void testCircleLocationReference()
            throws InvalidMapDataException {

        GeoCoordinates center = new GeoCoordinatesImpl(5.10185, 52.10598);

        RawCircleLocRef lineLocRef = new RawCircleLocRef("id", center, 300);

        calculateBox(lineLocRef, 52.10329, 5.09746, 52.10867, 5.10624);
    }

    /**
     * Tests calculation of a bounding box for a geo-coordinate location
     * reference
     *
     * @throws InvalidMapDataException
     *             never
     */
    @Test
    public final void testGeoCoordinateLocationReference()
            throws InvalidMapDataException {

        GeoCoordinates coordinate = new GeoCoordinatesImpl(5.0979, 52.10887);

        RawGeoCoordLocRef lineLocRef = new RawGeoCoordLocRef("id", coordinate);

        calculateBox(lineLocRef, 52.10887, 5.0979, 52.10888, 5.09791);
    }

    /**
     * Tests calculation of a bounding box for a circle location reference
     *
     * @throws InvalidMapDataException
     *             never
     */
    @Test
    public final void testPolygonLocationReference()
            throws InvalidMapDataException {

        List<GeoCoordinates> corners = new ArrayList<GeoCoordinates>();
        corners.add(new GeoCoordinatesImpl(5.09936, 52.10306));
        corners.add(new GeoCoordinatesImpl(5.10516, 52.10432));
        corners.add(new GeoCoordinatesImpl(5.1046, 52.107354));
        corners.add(new GeoCoordinatesImpl(5.1019, 52.10933));
        corners.add(new GeoCoordinatesImpl(5.0988, 52.107374));

        RawPolygonLocRef lineLocRef = new RawPolygonLocRef("id", corners);

        calculateBox(lineLocRef, 52.10306, 5.0988, 52.10933, 5.10516);
    }

    /**
     * Executes the calculation of the bounding box and checks it against the
     * given expected coordinates
     *
     * @param locRef
     * @param expectedLatLL
     * @param expectedLonLL
     * @param expectedLatUR
     * @param expectedLongUR
     */
    private void calculateBox(RawLocationReference locRef,
                              double expectedLatLL, double expectedLonLL, double expectedLatUR,
                              double expectedLongUR) {
        try {
            RectangleCorners bbox = LocRefBoundary
                    .calculateLocRefBoundary(locRef);

            GeoCoordinates lowerLeft = bbox.getLowerLeft();
            GeoCoordinates upperRight = bbox.getUpperRight();
            assertEquals(expectedLatLL, lowerLeft.getLatitudeDeg(),
                    VALID_COORDINATE_DELTA);
            assertEquals(expectedLonLL, lowerLeft.getLongitudeDeg(),
                    VALID_COORDINATE_DELTA);
            assertEquals(expectedLatUR, upperRight.getLatitudeDeg(),
                    VALID_COORDINATE_DELTA);
            assertEquals(expectedLongUR, upperRight.getLongitudeDeg(),
                    VALID_COORDINATE_DELTA);
        } catch (InvalidMapDataException e) {
            Assert.fail("Unexpected exception", e);
        }
    }

    /**
     * Mocks an LRP
     *
     * @param latitude
     *            The latitude
     * @param longitude
     *            The longitude
     * @param dnp
     *            The DNP
     * @return The mocked LRP
     */
    private LocationReferencePoint mockLRP(final double latitude,
                                           final double longitude, final int dnp) {

        final LocationReferencePoint lrp = mockery.mock(
                LocationReferencePoint.class, "lrp" + latitude + "-"
                        + longitude + "-" + dnp);

        mockery.checking(new Expectations() {
            {
                allowing(lrp).getLatitudeDeg();
                will(returnValue(latitude));
            }
        });
        mockery.checking(new Expectations() {
            {
                allowing(lrp).getLongitudeDeg();
                will(returnValue(longitude));
            }
        });
        mockery.checking(new Expectations() {
            {
                allowing(lrp).getDistanceToNext();
                will(returnValue(dnp));
            }
        });
        return lrp;
    }

}
