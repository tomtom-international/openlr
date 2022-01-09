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
 * <p>
 */
package openlr.map.utils;

import openlr.map.GeoCoordinatesImpl;
import openlr.map.Line;
import openlr.map.Node;
import openlr.map.utils.GeometryUtils.BearingDirection;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.annotations.Test;

import java.awt.geom.Point2D;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

import static org.testng.Assert.*;

/**
 * The Class GeometryUtilsTest.
 */
public class GeometryUtilsTest {

    /** The expected calculated bearing of line 4 in the PAL test against the direction. */
    private static final double BEARING_L4_PAL_AGAINST_DIRECTION = 347.81124542247596;
    /** The expected calculated bearing of line 3 in the PAL test in forward direction. */
    private static final double BEARING_L3_PAL_IN_DIRECTION = 229.04320047566506;
    /** The expected calculated bearing of line 4 in the PAL test against the direction. */
    private static final double BEARING_L2_PAL_AGAINST_DIRECTION = 306.42629695892174;
    /** The expected calculated bearing of line 1 in the PAL test in forward direction. */
    private static final double BEARING_L1_PAL_IN_DIRECTION = 143.0818227792747;
    /** The expected calculated bearing of line 4 against the direction. */
    private static final double BEARING_L4_AGAINST_DIRECTION = 351.8056536951603;
    /** The expected calculated bearing of line 3 in forward direction. */
    private static final double BEARING_L3_IN_DIRECTION = 226.68116808410966;
    /** The expected calculated bearing of line 2 against the direction. */
    private static final double BEARING_L2_AGAINST_DIRECTION = 289.5999085596942;
    /** The expected calculated bearing of line 1 in forward direction. */
    private static final double BEARING_L1_IN_DIRECTION = 135.0879067689071;
    /** A bearing value of 0.0. */
    private static final double BEARING_0 = 0.0;
    /** A bearing value of 180.0. */
    private static final double BEARING_180 = 180.0;
    /** A double value of 180.0. */
    private static final double HUNDRED_EIGHTY_DEGREE = 180.0;
    /** A double value of 90.0. */
    private static final double NINETY_DEGREE = 90.0;
    /** A very small double. */
    private static final double VERY_SMALL = 0.0000000000001;


    /** The expected result of the bearing calculation in case of error. */
    private static final double EXPECTED_BEARING_ERROR = -1.0;

    /** Point a of the distance test. */
    private static final Point2D.Double DISTANCE_POINT_A = new Point2D.Double(
            13.40833, 52.51833);

    /** Point b of the distance test. */
    private static final Point2D.Double DISTANCE_POINT_B = new Point2D.Double(
            4.89044, 52.37019);

    /**
     * The expected result of the distance calculation from
     * {@link #DISTANCE_POINT_A} to {@link #DISTANCE_POINT_B}.
     */
    private static final double DISTANCE_RESULT = 579079.6452902432;

    /** The value for the parameter bearing distance. */
    private static final int BEARING_DISTANCE = 20;

    /**
     * The distance of the point along the line used in tests with lines
     * longer than the {@link #BEARING_DISTANCE}.
     */
    private static final int PAL_DISTANCE_LONG = 30;

    /**
     * The distance of the point along the line used in tests with lines
     * shorter than the {@link #BEARING_DISTANCE}.
     */
    private static final int PAL_DISTANCE_SHORT = 3;

    /**
     * Test distance.
     */
    @Test
    public final void testDistance() {

        assertEquals(GeometryUtils.distance(DISTANCE_POINT_A.x,
                DISTANCE_POINT_A.y, DISTANCE_POINT_A.x, DISTANCE_POINT_A.y),
                0.0);
        assertEquals(GeometryUtils.distance(DISTANCE_POINT_A.x,
                DISTANCE_POINT_A.y, DISTANCE_POINT_B.x, DISTANCE_POINT_B.y),
                DISTANCE_RESULT);
    }

    /**
     * Test calculate line bearing.
     */
    @Test
    public final void testCalculateLineBearing() {


        DecimalFormat df = new DecimalFormat("#.#############");
        df.setRoundingMode(RoundingMode.FLOOR);

        Map<Lines, Line> lines = mockBearingTestLines();

        assertEquals(GeometryUtils.calculateLineBearing(lines.get(Lines.L1),
                BearingDirection.IN_DIRECTION, BEARING_DISTANCE, 0),
                BEARING_L1_IN_DIRECTION);
        assertEquals(df.format(GeometryUtils.calculateLineBearing(lines.get(Lines.L2),
                        BearingDirection.AGAINST_DIRECTION, BEARING_DISTANCE, Lines.L2.lenght)),
                String.valueOf(BEARING_L2_AGAINST_DIRECTION));
        assertEquals(GeometryUtils.calculateLineBearing(lines.get(Lines.L3),
                BearingDirection.IN_DIRECTION, BEARING_DISTANCE, 0),
                BEARING_L3_IN_DIRECTION);
        assertEquals(GeometryUtils.calculateLineBearing(lines.get(Lines.L4),
                BearingDirection.AGAINST_DIRECTION, BEARING_DISTANCE, Lines.L4.lenght),
                BEARING_L4_AGAINST_DIRECTION);
        assertEquals(GeometryUtils.calculateLineBearing(null, null,
                BEARING_DISTANCE, 0), EXPECTED_BEARING_ERROR);

        assertEquals(df.format(GeometryUtils.calculateLineBearing(lines.get(Lines.L1),
                BearingDirection.IN_DIRECTION, BEARING_DISTANCE,
                PAL_DISTANCE_LONG)), String.valueOf(BEARING_L1_PAL_IN_DIRECTION));
        assertEquals(GeometryUtils.calculateLineBearing(lines.get(Lines.L2),
                BearingDirection.AGAINST_DIRECTION, BEARING_DISTANCE,
                PAL_DISTANCE_LONG), BEARING_L2_PAL_AGAINST_DIRECTION);
        assertEquals(GeometryUtils.calculateLineBearing(lines.get(Lines.L3),
                BearingDirection.IN_DIRECTION, BEARING_DISTANCE,
                PAL_DISTANCE_SHORT), BEARING_L3_PAL_IN_DIRECTION);
        assertEquals(GeometryUtils.calculateLineBearing(lines.get(Lines.L4),
                BearingDirection.AGAINST_DIRECTION, BEARING_DISTANCE,
                PAL_DISTANCE_SHORT), BEARING_L4_PAL_AGAINST_DIRECTION);

        // test the bearing calculation in the case all points are in the same
        // latitude line.
        assertEquals(GeometryUtils.calculateLineBearing(lines.get(Lines.VERTICAL_LINE),
                BearingDirection.IN_DIRECTION, BEARING_DISTANCE, 0),
                BEARING_180);
        assertEquals(GeometryUtils.calculateLineBearing(lines.get(Lines.VERTICAL_LINE),
                BearingDirection.AGAINST_DIRECTION, BEARING_DISTANCE, 0),
                BEARING_0);
        assertEquals(GeometryUtils.calculateLineBearing(lines.get(Lines.VERTICAL_LINE),
                BearingDirection.IN_DIRECTION, BEARING_DISTANCE,
                PAL_DISTANCE_SHORT), BEARING_180);
        assertEquals(GeometryUtils.calculateLineBearing(lines.get(Lines.VERTICAL_LINE),
                BearingDirection.AGAINST_DIRECTION, BEARING_DISTANCE,
                PAL_DISTANCE_SHORT), BEARING_0);
    }

    /**
     * Mocks all the lines necessary for the bearing calculation tests.
     * @return A map of all mocked lines.
     */
    private Map<Lines, Line> mockBearingTestLines() {

        Map<Lines, Line> lines = new HashMap<Lines, Line>(Lines.values().length);

        Mockery context = new Mockery();
        final Line l1 = context.mock(Line.class, Lines.L1.name());
        final Point2D.Double bearingPointL1 = new Point2D.Double(6.12703,
                49.60838);
        final Point2D.Double palL1 = new Point2D.Double(6.12711, 49.608308);
        final Point2D.Double bearingPoint1WithPal = new Point2D.Double(6.12727,
                49.60817);
        final Line l2 = context.mock(Line.class, Lines.L2.name());
        final Point2D.Double bearingPointL2 = new Point2D.Double(6.12791,
                49.60311);
        final Point2D.Double palL2 = new Point2D.Double(6.12706, 49.60332);
        final Point2D.Double palL3 = new Point2D.Double(6.12836, 49.60396);
        final Point2D.Double palL4 = new Point2D.Double(6.12673, 49.60849);
        final Point2D.Double palL5 = new Point2D.Double(6.12672, 49.60849);
        final Point2D.Double bearingPoint2WithPal = new Point2D.Double(6.12683,
                49.60343);
        final Line l3 = context.mock(Line.class, Lines.L3.name());
        final Line l4 = context.mock(Line.class, Lines.L4.name());
        final Line verticalLine = context.mock(Line.class, Lines.VERTICAL_LINE
                .name());

        lines.put(Lines.L1, l1);
        lines.put(Lines.L2, l2);
        lines.put(Lines.L3, l3);
        lines.put(Lines.L4, l4);
        lines.put(Lines.VERTICAL_LINE, verticalLine);

        final Map<Nodes, Node> nodes = mockBearingTestNodes(context);

        context.checking(new Expectations() {
            {
                allowing(l1).getLineLength();
                will(returnValue(Lines.L1.lenght));
            }

            {
                allowing(l1).getGeoCoordinateAlongLine(0);
                will(returnValue(nodes.get(Nodes.N1START).getGeoCoordinates()));
            }

            {
                allowing(l1).getStartNode();
                will(returnValue(nodes.get(Nodes.N1START)));
            }

            {
                allowing(l1).getGeoCoordinateAlongLine(BEARING_DISTANCE);
                will(returnValue(GeoCoordinatesImpl.newGeoCoordinatesUnchecked(bearingPointL1.x, bearingPointL1.y)));
            }

            {
                allowing(l1).getGeoCoordinateAlongLine(PAL_DISTANCE_LONG);
                will(returnValue(GeoCoordinatesImpl.newGeoCoordinatesUnchecked(palL1.x, palL1.y)));
            }

            {
                allowing(l1).getGeoCoordinateAlongLine(
                        PAL_DISTANCE_LONG + BEARING_DISTANCE);
                will(returnValue(GeoCoordinatesImpl.newGeoCoordinatesUnchecked(bearingPoint1WithPal.x, bearingPoint1WithPal.y)));
            }
        });
        context.checking(new Expectations() {
            {
                allowing(l2).getLineLength();
                will(returnValue(Lines.L2.lenght));
            }

            {
                allowing(l2).getGeoCoordinateAlongLine(Lines.L2.lenght);
                will(returnValue(nodes.get(Nodes.N2END).getGeoCoordinates()));
            }

            {
                allowing(l2).getGeoCoordinateAlongLine(0);
                will(returnValue(nodes.get(Nodes.N2START).getGeoCoordinates()));
            }

            {
                allowing(l2).getStartNode();
                will(returnValue(nodes.get(Nodes.N2START)));
            }

            {
                allowing(l2).getPrevLines();
                will(returnValue((new ArrayList<Line>()).iterator()));
            }

            {
                allowing(l2).getEndNode();
                will(returnValue(nodes.get(Nodes.N2END)));
            }

            {
                allowing(l2).getGeoCoordinateAlongLine(
                        Lines.L2.lenght - BEARING_DISTANCE);
                will(returnValue(GeoCoordinatesImpl.newGeoCoordinatesUnchecked(bearingPointL2.x, bearingPointL2.y)));
            }

            {
                allowing(l2).getGeoCoordinateAlongLine(PAL_DISTANCE_LONG);
                will(returnValue(GeoCoordinatesImpl.newGeoCoordinatesUnchecked(palL2.x, palL2.y)));
            }

            {
                allowing(l2).getGeoCoordinateAlongLine(
                        PAL_DISTANCE_LONG - BEARING_DISTANCE);
                will(returnValue(GeoCoordinatesImpl.newGeoCoordinatesUnchecked(bearingPoint2WithPal.x, bearingPoint2WithPal.y)));
            }
        });
        context.checking(new Expectations() {
            {
                allowing(l3).getLineLength();
                will(returnValue(Lines.L3.lenght));
            }

            {
                allowing(l3).getStartNode();
                will(returnValue(nodes.get(Nodes.N3START)));
            }

            {
                allowing(l3).getGeoCoordinateAlongLine(0);
                will(returnValue(nodes.get(Nodes.N3START).getGeoCoordinates()));
            }

            {
                allowing(l3).getNextLines();
                will(returnValue((new ArrayList<Line>()).iterator()));
            }

            {
                allowing(l3).getEndNode();
                will(returnValue(nodes.get(Nodes.N3END)));
            }

            {
                allowing(l3).getGeoCoordinateAlongLine(PAL_DISTANCE_SHORT);
                will(returnValue(GeoCoordinatesImpl.newGeoCoordinatesUnchecked(palL3.x, palL3.y)));
            }
        });
        context.checking(new Expectations() {
            {
                allowing(l4).getLineLength();
                will(returnValue(Lines.L4.lenght));
            }

            {
                allowing(l4).getStartNode();
                will(returnValue(nodes.get(Nodes.N4START)));
            }

            {
                allowing(l4).getGeoCoordinateAlongLine(0);
                will(returnValue(nodes.get(Nodes.N4START).getGeoCoordinates()));
            }

            {
                allowing(l4).getGeoCoordinateAlongLine(Lines.L4.lenght);
                will(returnValue(nodes.get(Nodes.N4END).getGeoCoordinates()));
            }

            {
                allowing(l4).getPrevLines();
                will(returnValue((new ArrayList<Line>()).iterator()));
            }

            {
                allowing(l4).getEndNode();
                will(returnValue(nodes.get(Nodes.N4END)));
            }

            {
                allowing(l4).getGeoCoordinateAlongLine(PAL_DISTANCE_SHORT);
                will(returnValue(GeoCoordinatesImpl.newGeoCoordinatesUnchecked(palL4.x, palL4.y)));
            }
        });
        context.checking(new Expectations() {
            {
                allowing(verticalLine).getLineLength();
                will(returnValue(Lines.VERTICAL_LINE.lenght));
            }

            {
                allowing(verticalLine).getGeoCoordinateAlongLine(0);
                will(returnValue(nodes.get(Nodes.VERTICAL_LINE_START).getGeoCoordinates()));
            }

            {
                allowing(verticalLine).getNextLines();
                will(returnValue((new ArrayList<Line>()).iterator()));
            }

            {
                allowing(verticalLine).getPrevLines();
                will(returnValue((new ArrayList<Line>()).iterator()));
            }

            {
                allowing(verticalLine).getStartNode();
                will(returnValue(nodes.get(Nodes.VERTICAL_LINE_START)));
            }

            {
                allowing(verticalLine).getEndNode();
                will(returnValue(nodes.get(Nodes.VERTICAL_LINE_END)));
            }

            {
                allowing(verticalLine).getGeoCoordinateAlongLine(PAL_DISTANCE_SHORT);
                will(returnValue(GeoCoordinatesImpl.newGeoCoordinatesUnchecked(palL5.x, palL5.y)));
            }
        });

        return lines;
    }

    /**
     * Mocks the nodes for the bearing calculation test.
     * @param context The mocking context.
     * @return A Map of all mocked nodes.
     */
    private Map<Nodes, Node> mockBearingTestNodes(final Mockery context) {

        final Map<Nodes, Node> nodes = new HashMap<Nodes, Node>(
                Nodes.values().length);
        for (final Nodes node : Nodes.values()) {

            final Node mockedNode = context.mock(Node.class, node.name());
            context.checking(new Expectations() {
                {
                    allowing(mockedNode).getLatitudeDeg();
                    will(returnValue(node.lat));
                }

                {
                    allowing(mockedNode).getLongitudeDeg();
                    will(returnValue(node.lon));
                }

                {
                    allowing(mockedNode).getGeoCoordinates();
                    will(returnValue(GeoCoordinatesImpl.newGeoCoordinatesUnchecked(node.lon, node.lat)));
                }
            });

            nodes.put(node, mockedNode);
        }
        return nodes;
    }

    /**
     * Tests {@link GeometryUtils#checkCoordinateBounds}.
     */
    @Test
    public final void testCoordinateBounds() {

        assertTrue(GeometryUtils.checkCoordinateBounds(HUNDRED_EIGHTY_DEGREE,
                NINETY_DEGREE));
        assertTrue(GeometryUtils.checkCoordinateBounds(-1
                * HUNDRED_EIGHTY_DEGREE, -1 * NINETY_DEGREE));

        assertFalse(GeometryUtils.checkCoordinateBounds(
                HUNDRED_EIGHTY_DEGREE + 2, 0));
        assertFalse(GeometryUtils.checkCoordinateBounds(0, NINETY_DEGREE + 2));
        assertFalse(GeometryUtils.checkCoordinateBounds(
                -HUNDRED_EIGHTY_DEGREE - 2, 0));
        assertFalse(GeometryUtils.checkCoordinateBounds(0, -NINETY_DEGREE - 2));


        assertFalse(GeometryUtils.checkCoordinateBounds(HUNDRED_EIGHTY_DEGREE
                + VERY_SMALL, 0));
        assertFalse(GeometryUtils.checkCoordinateBounds(0, -NINETY_DEGREE
                - VERY_SMALL));
        assertFalse(GeometryUtils.checkCoordinateBounds(Double.MAX_VALUE,
                Double.MIN_VALUE));
    }

    @Test
    public void bearingDifferenceTest() {
        assertEquals(GeometryUtils.bearingDifference(0, 355), 5);
        assertEquals(GeometryUtils.bearingDifference(0, 5), 5);
        assertEquals(GeometryUtils.bearingDifference(15, 20), 5);
        assertEquals(GeometryUtils.bearingDifference(20, 15), 5);
        assertEquals(GeometryUtils.bearingDifference(90, 180), 90);
        assertEquals(GeometryUtils.bearingDifference(345, 10), 25);
        assertEquals(GeometryUtils.bearingDifference(150, 220), 70);
    }

    /**
     * An enum of nodes data used in the tests of this class.
     */
    enum Nodes {

        /** The start node of line 1. */
        N1START(49.60851, 6.12683),
        /** The start node of line 2. */
        N2START(49.60361, 6.12775),
        /** The end node of line 2. */
        N2END(49.60305, 6.12817),
        /** The start node of line 3. */
        N3START(49.60398, 6.12838),
        /** The end node of line 3. */
        N3END(49.60387, 6.12820),
        /** The end node of line 4. */
        N4END(49.60843, 6.12674),
        /** The start node of line 4. */
        N4START(49.60852, 6.12672),
        /** The end node of the vertical line. */
        VERTICAL_LINE_END(49.60843, 6.12672),
        /** The start node of the vertical line. */
        VERTICAL_LINE_START(49.60852, 6.12672);

        /** The longitude. */
        private double lon;
        /** The latitude. */
        private double lat;

        /**
         * Creates an enum instance.
         * @param longitude The longitude.
         * @param latitude The latitude.
         */
        Nodes(final double latitude, final double longitude) {
            this.lon = longitude;
            this.lat = latitude;
        }
    }


    /**
     * An enum of line data used in the tests of this class.
     */
    enum Lines {

        /** Line 1 */
        L1(117),
        /** Line 2. */
        L2(1117),
        /** Line 3. */
        L3(17),
        /** Line 4. */
        L4(10),
        /** A line forming a vertical line geographically. */
        VERTICAL_LINE(10);

        /** The line length. */
        private int lenght;

        /**
         * Creates an enum instance.
         * @param lenghtValue The line length.
         */
        Lines(final int lenghtValue) {
            this.lenght = lenghtValue;
        }
    }

}
