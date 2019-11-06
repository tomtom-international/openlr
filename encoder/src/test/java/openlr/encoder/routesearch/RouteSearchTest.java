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
package openlr.encoder.routesearch;

import openlr.encoder.OpenLREncoderProcessingException;
import openlr.encoder.TestData;
import openlr.location.Location;
import openlr.map.Line;
import openlr.map.MapDatabase;
import openlr.testutils.CommonObjectTestUtils;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.fail;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Tests the route search.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class RouteSearchTest {


    /**
     * Test route search.
     */
    @Test
    public final void testRouteSearch() {
        TestData td = TestData.getInstance();
        Location loc = td.getRoute();
        List<? extends Line> theRoute = loc.getLocationLines();
        RouteSearchResult result = null;
        try {
            RouteSearch rs = new RouteSearch(theRoute);
            result = rs.calculateRoute();
        } catch (OpenLREncoderProcessingException e1) {
            fail("Unexpected exception", e1);
        }
        assertTrue("Route search failed for first calculation!", result
                .getResult() == RouteSearchResult.RouteSearchReturnCode.INTERMEDIATE_FOUND);
        assertEquals("Wrong intermediate found!", result.getIntermediate()
                .getID(), td.getIntermediate().getID());
        assertEquals("Wrong route length 1!", result.getRouteLength(), td
                .getRoutLength1());
        List<? extends Line> rest = theRoute.subList(td.getIntermediatePos(),
                theRoute.size());
        try {
            RouteSearch rs = new RouteSearch(rest);
            result = rs.calculateRoute();

            CommonObjectTestUtils.testToString(rs);
        } catch (OpenLREncoderProcessingException e) {
            fail("Unexpected exception", e);
        }
        assertTrue("Route search failed for first calculation!", result
                .getResult() == RouteSearchResult.RouteSearchReturnCode.ROUTE_FOUND);
        assertEquals("Wrong route length 2!", result.getRouteLength(), td
                .getRoutLength2());

        //TODO calculate invalid route
    }

    @Test
    public final void testLowestFrcRouting() {
        try {
            TestData td = TestData.getInstance();
            MapDatabase mdb = td.getMapDatabase();
            RouteSearch rs = new RouteSearch(Arrays.asList(mdb.getLine(1), mdb.getLine(3), mdb.getLine(5), mdb.getLine(8)));
            RouteSearchResult result = rs.calculateRoute();
            assertEquals(1, result.getIntermediatePos());
        } catch (Exception e) {
            fail("Wrong error detected: " + e.getMessage());
        }
    }

}
