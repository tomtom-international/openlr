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
package openlr.decoder.data;

import static org.testng.Assert.assertTrue;
import openlr.decoder.data.NodeWithDistance.NodeWithDistanceComparator;
import openlr.map.Node;
import openlr.testutils.CommonObjectTestUtils;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.annotations.Test;

/**
 * Tests class {@link NodeWithDistanceComparator}.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public class NodeWithDistanceTest {

	/** A distance value of 45. */
	private static final int DIST_45 = 45;
	/** A distance value of 99. */
	private static final int DIST_99 = 99;
	/** A distance value of 98. */
	private static final int DIST_98 = 98;

	/**
	 * Tests the comparison via {@link NodeWithDistanceComparator}.
	 */
	@Test
	public final void testComparison() {
		
		Mockery context = new Mockery();
		final Node n1 = context.mock(Node.class, "n1");
		context.checking(new Expectations() {
            {
                allowing(n1).getID();
                will(returnValue(Long.MAX_VALUE));
            }
        });
		Node n2 = context.mock(Node.class, "n2");
		NodeWithDistance nwd01 = new NodeWithDistance(n1, DIST_98);
		NodeWithDistance nwd02 = new NodeWithDistance(n1, DIST_99);
		NodeWithDistance nwd03 = new NodeWithDistance(n1, DIST_45);
		NodeWithDistance nwd04 = new NodeWithDistance(n1, DIST_98);
		NodeWithDistance nwdMax = new NodeWithDistance(n2, Integer.MAX_VALUE);
		NodeWithDistance nwdMin = new NodeWithDistance(n2, Integer.MIN_VALUE);
		
		CommonObjectTestUtils.testToString(nwd01);  

		NodeWithDistanceComparator comp = new NodeWithDistanceComparator();
		assertTrue(comp.compare(nwd01, nwd02) < 0);
		assertTrue(comp.compare(nwd03, nwd02) < 0);
		assertTrue(comp.compare(nwd01, nwd04) == 0);
		assertTrue(comp.compare(nwd02, nwd03) > 0);
		assertTrue(comp.compare(nwdMax, nwdMax) == 0);
		assertTrue(comp.compare(nwdMax, nwdMin) > 0);
		assertTrue(comp.compare(nwdMin, nwdMax) < 0);	
	}
}
