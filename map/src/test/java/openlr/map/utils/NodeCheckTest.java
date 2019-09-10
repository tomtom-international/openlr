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
package openlr.map.utils;

import openlr.map.Line;
import openlr.map.Node;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.annotations.Test;

import java.util.HashSet;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * The Class NodeCheckTest.
 */
public class NodeCheckTest {

    /** Return value 3 for mocked {@link Node#getConnectedLines()} */
    private static final int THREE_CONNECTED_LINES = 3;
    /** Return value 4 for mocked {@link Node#getConnectedLines()} */
    private static final int FOUR_CONNECTED_LINES = 4;
    /** Return value 5 for mocked {@link Node#getConnectedLines()} */
    private static final int FIVE_CONNECTED_LINES = 5;

    /**
     * Test is node valid.
     */
    @Test
    public void testIsNodeValid() {
        Mockery context = new Mockery();
        final Node valid1 = context.mock(Node.class, "valid1");
        final Node valid2 = context.mock(Node.class, "valid2");
        final Node valid3 = context.mock(Node.class, "valid3");
        final Node valid4 = context.mock(Node.class, "valid4");
        final Node valid5 = context.mock(Node.class, "valid5");

        final Node invalid2 = context.mock(Node.class, "invalid2");
        final Node invalid4 = context.mock(Node.class, "invalid4");

        final Node n1 = context.mock(Node.class, "n1");
        final Node n2 = context.mock(Node.class, "n2");
        final Node n3 = context.mock(Node.class, "n3");

        final Line l1 = context.mock(Line.class, "l1");
        final Line l2 = context.mock(Line.class, "l2");
        final Line l3 = context.mock(Line.class, "l3");
        final Line l4 = context.mock(Line.class, "l4");
        final Line l5 = context.mock(Line.class, "l5");

        context.checking(new Expectations() {
            {
                allowing(valid1).getNumberConnectedLines();
                will(returnValue(1));
            }
        });

        final HashSet<Line> set2 = new HashSet<Line>();
        set2.add(l1);
        set2.add(l2);
        context.checking(new Expectations() {
            {
                allowing(valid2).getNumberConnectedLines();
                will(returnValue(2));
            }

            {
                allowing(valid2).getConnectedLines();
                will(returnValue(set2.iterator()));
            }
        });
        context.checking(new Expectations() {
            {
                allowing(l1).getStartNode();
                will(returnValue(n1));
            }

            {
                allowing(l1).getEndNode();
                will(returnValue(valid2));
            }
        });
        context.checking(new Expectations() {
            {
                allowing(l2).getStartNode();
                will(returnValue(valid2));
            }

            {
                allowing(l2).getEndNode();
                will(returnValue(n1));
            }
        });

        context.checking(new Expectations() {
            {
                allowing(valid3).getNumberConnectedLines();
                will(returnValue(THREE_CONNECTED_LINES));
            }
        });

        context.checking(new Expectations() {
            {
                allowing(valid5).getNumberConnectedLines();
                will(returnValue(FIVE_CONNECTED_LINES));
            }
        });

        final HashSet<Line> set4 = new HashSet<Line>();
        set4.addAll(set2);
        set4.add(l3);
        set4.add(l4);
        context.checking(new Expectations() {
            {
                allowing(valid4).getNumberConnectedLines();
                will(returnValue(FOUR_CONNECTED_LINES));
            }

            {
                allowing(valid4).getConnectedLines();
                will(returnValue(set4.iterator()));
            }
        });
        context.checking(new Expectations() {
            {
                allowing(l3).getStartNode();
                will(returnValue(n2));
            }

            {
                allowing(l3).getEndNode();
                will(returnValue(valid2));
            }
        });
        context.checking(new Expectations() {
            {
                allowing(l4).getStartNode();
                will(returnValue(valid2));
            }

            {
                allowing(l4).getEndNode();
                will(returnValue(n3));
            }
        });

        context.checking(new Expectations() {
            {
                allowing(l5).getStartNode();
                will(returnValue(valid2));
            }

            {
                allowing(l5).getEndNode();
                will(returnValue(n2));
            }
        });

        final HashSet<Line> set4invalid = new HashSet<Line>();
        set4invalid.addAll(set2);
        set4invalid.add(l3);
        set4invalid.add(l5);
        context.checking(new Expectations() {
            {
                allowing(invalid4).getNumberConnectedLines();
                will(returnValue(FOUR_CONNECTED_LINES));
            }

            {
                allowing(invalid4).getConnectedLines();
                will(returnValue(set4invalid.iterator()));
            }
        });

        final HashSet<Line> set2invalid = new HashSet<Line>();
        set2invalid.add(l3);
        set2invalid.add(l4);
        context.checking(new Expectations() {
            {
                allowing(invalid2).getNumberConnectedLines();
                will(returnValue(2));
            }

            {
                allowing(invalid2).getConnectedLines();
                will(returnValue(set2invalid.iterator()));
            }
        });

        assertTrue(NodeCheck.isValidNode(valid1));
        assertTrue(NodeCheck.isValidNode(valid2));
        assertTrue(NodeCheck.isValidNode(valid3));
        assertTrue(NodeCheck.isValidNode(valid4));
        assertTrue(NodeCheck.isValidNode(valid5));

        assertFalse(NodeCheck.isValidNode(invalid2));
        assertFalse(NodeCheck.isValidNode(invalid4));
    }

}
