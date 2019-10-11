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
package openlr.map.utils;

import openlr.map.Line;
import openlr.map.utils.PQElem.PQElemComparator;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

import static org.testng.Assert.*;

/**
 * The Class PathUtilsTest.
 */
public class PathUtilsTest {

    /** Just an integer of value 5. */
    private static final int VALUE_5 = 5;
    /** Just an integer of value 100. */
    private static final int VALUE_100 = 100;
    /** Just an integer of value 400. */
    private static final int VALUE_400 = 400;
    /** Just an integer of value 500. */
    private static final int VALUE_500 = 500;

    /** Return code "-1". */
    private static final int RETURN_CODE_MINUS_ONE = -1;
    /** The expected length of the test {@link #path}. */
    private static final int PATH_LENGTH = 2553;
    /** The path. */
    private final ArrayList<Line> path = new ArrayList<Line>();
    /** The empty path. */
    private final ArrayList<Line> emptyPath = new ArrayList<Line>();

    /** The not connected path. */
    private final ArrayList<Line> notConnectedPath = new ArrayList<Line>();

    /** The pq. */
    private final PriorityQueue<PQElem> pq = new PriorityQueue<PQElem>(11,
            new PQElem.PQElemComparator());

    /** The l1. */
    private Line l1;

    /** The elem1. */
    private PQElem elem1;

    /** The l4. */
    private Line l4;

    /** The elem4. */
    private PQElem elem4;

    /** The l3. */
    private Line l3;

    /** The elem5. */
    private PQElem elem5;

    /** The elem6. */
    private PQElem elem6;

    /**
     * Setup test.
     */
    @BeforeTest
    public final void setupTest() {
        Mockery context = new Mockery();
        l1 = context.mock(Line.class, "l1");
        final Line l2 = context.mock(Line.class, "l2");
        l3 = context.mock(Line.class, "l3");
        l4 = context.mock(Line.class, "l4");
        final Line l5 = context.mock(Line.class, "l5");
        final HashSet<Line> s1 = new HashSet<Line>();
        s1.add(l2);
        final HashSet<Line> s2 = new HashSet<Line>();
        s2.add(l3);
        final HashSet<Line> s3 = new HashSet<Line>();
        s3.add(l4);
        context.checking(new Expectations() {
            {
                allowing(l1).getLineLength();
                will(returnValue(Lines.L1.length));
            }

            {
                allowing(l1).getNextLines();
                will(returnValue(s1.iterator()));
            }

            {
                allowing(l1).getID();
                will(returnValue(Lines.L1.id));
            }
        });
        context.checking(new Expectations() {
            {
                allowing(l2).getLineLength();
                will(returnValue(Lines.L2.length));
            }

            {
                allowing(l2).getNextLines();
                will(returnValue(s2.iterator()));
            }

            {
                allowing(l2).getID();
                will(returnValue(Lines.L2.id));
            }
        });
        context.checking(new Expectations() {
            {
                allowing(l3).getLineLength();
                will(returnValue(Lines.L3.length));
            }

            {
                allowing(l3).getNextLines();
                will(returnValue(s3.iterator()));
            }

            {
                allowing(l3).getID();
                will(returnValue(Lines.L3.id));
            }
        });
        context.checking(new Expectations() {
            {
                allowing(l4).getLineLength();
                will(returnValue(Lines.L4.length));
            }

            {
                allowing(l4).getID();
                will(returnValue(Lines.L4.id));
            }
        });
        path.add(l1);
        path.add(l2);
        path.add(l3);
        path.add(l4);
        notConnectedPath.add(l1);
        notConnectedPath.add(l3);
        notConnectedPath.add(l4);

        elem1 = new PQElem(l1, VALUE_100, VALUE_100, null);
        final PQElem elem2 = new PQElem(l2, 200, 100, elem1);
        final PQElem elem3 = new PQElem(l3, 300, 100, elem2);
        elem4 = new PQElem(l4, VALUE_400, VALUE_100, elem3);
        elem5 = new PQElem(l3, VALUE_500, VALUE_100, elem4);
        elem6 = new PQElem(l5, VALUE_500, VALUE_100, elem4);
        pq.add(elem1);
        pq.add(elem2);
        pq.add(elem3);
        pq.add(elem4);
    }

    /**
     * Test get length1.
     */
    @Test
    public final void testGetLength1() {
        assertEquals(PathUtils.getLength(path), PATH_LENGTH);
        assertEquals(PathUtils.getLength(emptyPath), 0);
        assertEquals(PathUtils.getLength(null), 0);
    }

    /**
     * Test get length2.
     */
    @Test
    public final void testGetLength2() {
        assertEquals(PathUtils.getLength(path, path.get(0), path.get(path
                .size() - 1)), PATH_LENGTH);
        assertEquals(PathUtils.getLength(path, null, null), RETURN_CODE_MINUS_ONE);
        assertEquals(PathUtils.getLength(null, null, null), 0);
        assertEquals(PathUtils.getLength(emptyPath, null, null), 0);
        assertEquals(PathUtils.getLength(path, path.get(path.size() - 1), path
                .get(0)), RETURN_CODE_MINUS_ONE);
        assertEquals(PathUtils.getLength(path, path.get(0), null),
                RETURN_CODE_MINUS_ONE);
        assertEquals(PathUtils.getLength(path, null, path.get(0)),
                RETURN_CODE_MINUS_ONE);
    }

    /**
     * Test check path connection.
     */
    @Test
    public final void testCheckPathConnection() {
        assertTrue(PathUtils.checkPathConnection(path));
        assertFalse(PathUtils.checkPathConnection(notConnectedPath));
        assertTrue(PathUtils.checkPathConnection(null));
        assertTrue(PathUtils.checkPathConnection(emptyPath));
    }

    /**
     * Test construct path.
     */
    @Test
    public final void testConstructPath() {
        assertEquals(PathUtils.constructPath(elem4), path);
        assertEquals(PathUtils.constructPath(null), emptyPath);
        assertNull(PathUtils.constructPath(elem5));
    }

    /**
     * Test find element in queue.
     */
    @Test
    public final void testFindElementInQueue() {
        assertEquals(PathUtils.findElementInQueue(pq, l1), elem1);
        assertEquals(PathUtils.findElementInQueue(pq, l4), elem4);
        assertEquals(PathUtils.findElementInQueue(new PriorityQueue<PQElem>(),
                l4), null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public final void testFindElementInQueueException01() {
        PathUtils.findElementInQueue(pq, null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public final void testFindElementInQueueException02() {
        PathUtils.findElementInQueue(null, l1);
    }

    /**
     * Test find common line in paths.
     */
    @Test
    public final void testFindCommonLineInPaths() {
        assertEquals(PathUtils.findCommonLineInPaths(path, elem4), l4);
        assertEquals(PathUtils.findCommonLineInPaths(path, elem6), l4);
        assertNull(PathUtils.findCommonLineInPaths(path, null));
        assertNull(PathUtils.findCommonLineInPaths(null, elem6));
        assertNull(PathUtils.findCommonLineInPaths(path,
                new LineLinkedListElement() {
                    @Override
                    public LineLinkedListElement getPrevious() {
                        return null;
                    }

                    @Override
                    public Line getLine() {
                        return null;
                    }
                }));
        assertNull(PathUtils.findCommonLineInPaths(emptyPath, elem4));
    }

    /**
     * Tests class {@link PQElem}.
     */
    @Test
    public final void testPQElement() {

        PQElem e = new PQElem(l1, Integer.MIN_VALUE, -1 * VALUE_500, null);
        PQElem e2 = new PQElem(l1, Integer.MAX_VALUE, VALUE_5, e);
        PQElem e3 = new PQElem(l1, Integer.MAX_VALUE, VALUE_5, e2);
        PQElem e5 = new PQElem(l3, Integer.MIN_VALUE, VALUE_500, e2);

        assertEquals(e.getFirstVal(), Integer.MIN_VALUE);
        assertEquals(e.getSecondVal(), -1 * VALUE_500);

        try {
            new PQElem(null, 0, 1, e);
            fail("NullPointerException because of missing parameter 'l' "
                    + "was expected.");
        } catch (NullPointerException npe) {
            // that was expected
            Assert.assertNotNull(npe); // to satisfy checkstyle
        }

        PQElem.PQElemComparator comparator = new PQElemComparator();

        // unequal
        assertFalse(e.hashCode() == e5.hashCode());
        assertFalse(e3.equals(null));
        assertFalse(e3.equals(e5));
        assertTrue(comparator.compare(e, e2) < 0);
        assertTrue(comparator.compare(e2, e) > 0);
        assertTrue(comparator.compare(e, e5) < 0);
        assertTrue(comparator.compare(e5, e) > 0);

        // equal
        assertTrue(e.equals(e));
        assertTrue(e.hashCode() == e.hashCode());
        assertTrue(comparator.compare(e, e) == 0);
        assertTrue(comparator.compare(e2, e3) == 0);
        assertTrue(comparator.compare(e3, e2) == 0);

        assertTrue(e2.equals(e3));
        assertTrue(e3.equals(e2));
        assertTrue(e2.hashCode() == e3.hashCode());

        assertTrue(e3.equals(e2));
        assertTrue(e3.hashCode() == e2.hashCode());

        try {
            e.toString();
            e2.toString();
        } catch (Exception ex) {
            fail("Unexpected exception in toString()", ex);
        }

    }

    /**
     * Enumeration containing lines data for the tests of this class.
     */
    private enum Lines {

        /** Line 1 */
        L1(1, 117),
        /** Line 2 */
        L2(2, 2305),
        /** Line 3 */
        L3(3, 15),
        /** Line 3 */
        L4(4, 116);

        /** The line ID. */
        private long id;
        /** The line length. */
        private int length;

        /**
         * Create a line instance.
         *
         * @param idValue
         *            The line ID.
         * @param lengthValue
         *            The line length.
         */
        Lines(final long idValue, final int lengthValue) {
            this.id = idValue;
            this.length = lengthValue;
        }
    }

}
