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
package openlr.encoder.data;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.util.Arrays;
import java.util.List;

import openlr.encoder.TestData;
import openlr.location.Location;
import openlr.location.LocationFactory;
import openlr.map.Line;

import org.testng.annotations.Test;

/**
 * Tests the expansion of locations.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public class ExpandedLocationTest {
	
	/**
	 * The single line used for the input location that shall be expanded from 
	 * the map configuration DefaultMapDatabase.xml.
	 */
	private static final int LOCATION_LINE = 9;
	
	/** The test data. */
	private TestData td = TestData.getInstance();
	
	/**
	 * Test expansion at start and end. The tested location contains of line 
	 * from Node 6 to 9 in the map described in the OpenLR white paper. Start 
	 * and end node are not valid according to our rules. The location therefore
	 * should get extended be another line each.
	 */
	@Test
	public final void testExpansionStart() {
		
		Location loc = getExpadibleLocation();
		ExpansionData exploc = null;
		LocRefData lrd = new LocRefData(loc);
		try {
			exploc = ExpansionHelper.createExpandedLocation(
					td.getProperties(), td.getMapDatabase(), lrd);
		} catch (Exception e) {
			fail("Expansion failure", e);
		}
		assertTrue(exploc.hasExpansionStart(),
				"Location is not expanded at start");
		List<Line> expStart = exploc.getExpansionStart();
		assertTrue(expStart.size() == 1,
				"Wrong number of dsegs expanded at start");

		assertTrue(exploc.hasExpansionEnd(), "Location is not expanded at end");
		List<Line> expEnd = exploc.getExpansionEnd();
		assertTrue(expEnd.size() == 1, "Wrong number of dsegs expanded at end");

		assertNotNull(exploc.toString());
	}

	/**
	 * Delivers a line location that should get extended at the beginning and 
	 * end.
	 * @return The extendible location.
	 */
	private Location getExpadibleLocation() {
		return LocationFactory.createLineLocation("lineLocExpand", 
				Arrays.asList(td.getMapDatabase().getLine(LOCATION_LINE)));
	}
}
