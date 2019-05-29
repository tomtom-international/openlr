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
package openlr.decoder.routesearch;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import openlr.decoder.TestData;
import openlr.decoder.routesearch.RouteSearch.RouteSearchResult;
import openlr.map.FunctionalRoadClass;
import openlr.map.Line;
import openlr.map.MapDatabase;

import org.testng.annotations.Test;

/**
 * The Class TestRouteSearch.
 */
public class RouteSearchTest {
	
	/** The value of the max distance parameter for the valid route search. */
	private static final int VALID_ROUTE_MAX_DISTANCE = Integer.MAX_VALUE;
	
	/** The value of the max distance parameter for the test case "no route found". */
	private static final int NO_ROUTE_FOUND_MAX_DISTANCE = 100;

	/**
	 * An utility class holding prepared/mocked test data. 
	 */
	protected static TestData td = TestData.getInstance();
	
	/** The start line of the valid route. */
	private static final long VALID_ROUTE_START_LINE = 4;
	/** The end line of the valid route. */
	private static final long VALID_ROUTE_END_LINE = 18;

	/** The expected sequence of lines of the valid route using minimum FRC 7. */
	private static final long[] VALID_ROUTE_FRC7_EXPECTED_LINES = new long[] {4, 
		6, 8, 9, 13, 18};

	/** The expected sequence of lines of the valid route using minimum FRC 4. */
	private static final long[] VALID_ROUTE_FRC4_EXPECTED_LINES = new long[] {4, 
		6, 10, 14, 16, 18};

	/**
	 * Tests a valid routing with minimum FRC 7.
	 */
	@Test
	public final void testValidRoute() {
		
		MapDatabase mdb = td.getMapDatabase();
		RouteSearch rs = new RouteSearch();

		assertTrue(rs.getCalculatedRoute().size() == 0);

		RouteSearchResult rsr = rs.calculateRoute(mdb
				.getLine(VALID_ROUTE_START_LINE), mdb
				.getLine(VALID_ROUTE_END_LINE), VALID_ROUTE_MAX_DISTANCE,
				FunctionalRoadClass.FRC_7.getID(), true);
		assertEquals(rsr, RouteSearchResult.ROUTE_FOUND);

		int i = 0;
		for (Line l : rs.getCalculatedRoute()) {
			assertSame(l.getID(), VALID_ROUTE_FRC7_EXPECTED_LINES[i++]);
		}

		assertNotNull(rs.toString());
	}
	
	/**
	 * Tests the routing with the same start and end as in 
	 * {@link #testValidRoute} but with more restrictive FRC which leads to a
	 * different route. 
	 */
	@Test
	public final void testValidRoute2() {
		
		MapDatabase mdb = td.getMapDatabase();
		RouteSearch rs = new RouteSearch();

		assertTrue(rs.getCalculatedRoute().size() == 0);

		RouteSearchResult rsr = rs.calculateRoute(mdb
				.getLine(VALID_ROUTE_START_LINE), mdb
				.getLine(VALID_ROUTE_END_LINE), VALID_ROUTE_MAX_DISTANCE,
				FunctionalRoadClass.FRC_5.getID(), true);
		assertEquals(rsr, RouteSearchResult.ROUTE_FOUND);

		int i = 0;
		for (Line l : rs.getCalculatedRoute()) {
			assertSame(l.getID(), VALID_ROUTE_FRC4_EXPECTED_LINES[i++]);
		}		

	}	
	
	/**
	 * Tests a failing route search.
	 */
	@Test
	public final void testNoRouteFound() {
		
		MapDatabase mdb = td.getMapDatabase();
		RouteSearch rs = new RouteSearch();

		// this route search should fail because of the too short max distance.
		RouteSearchResult rsr = rs.calculateRoute(mdb
				.getLine(VALID_ROUTE_START_LINE), mdb
				.getLine(VALID_ROUTE_END_LINE), NO_ROUTE_FOUND_MAX_DISTANCE,
				FunctionalRoadClass.FRC_7.getID(), true);
		
		assertEquals(rsr, RouteSearchResult.NO_ROUTE_FOUND);
		
		assertTrue(rs.getCalculatedRoute().size() == 0);		

		assertNotNull(rs.toString());
	}	
}
