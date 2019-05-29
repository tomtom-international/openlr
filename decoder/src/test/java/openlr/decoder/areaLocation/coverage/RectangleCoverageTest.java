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
package openlr.decoder.areaLocation.coverage;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import openlr.decoder.TestMap;
import openlr.decoder.worker.coverage.RectangleCoverage;
import openlr.location.data.AffectedLines;
import openlr.map.GeoCoordinates;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.MapDatabase;

import org.testng.Assert;
import org.testng.annotations.Test;

public class RectangleCoverageTest {

	/** The Constant EXPECTED_COVERAGE. */
	private static final Set<Long> EXPECTED_COVERAGE = new HashSet<Long>(
			Arrays.asList(new Long[] {15280001229161L, -15280001229161L,
					15280001229160L, -15280001229160L, 15280001229159L,
					-15280001229159L, 15280001229158L, -15280001229158L,
					15280001229157L, -15280001229157L, 15280001229165L,
					-15280001229165L, 15280001229214L, 15280001229215L,
					-15280001229215L, 15280001229216L, -15280001229216L,
					15280001229217L, -15280001229217L, 15280001229164L,
					-15280001229164L, 15280001234940L, -15280001234940L,
					15280001234939L, -15280001234939L}));

	/** The Constant EXPECTED_INTERSECTION. */
	private static final Set<Long> EXPECTED_INTERSECTION = new HashSet<Long>(
			Arrays.asList(new Long[] {15280001229162L, -15280001229162L,
					15280001229154L, -15280001229154L, 15280001229173L,
					-15280001229173L, 15280001229174L, -15280001229174L,
					15280001229218L, -15280001229213L, 15280001229211L,
					15280001229156L, -15280001229156L, 15280001235650L,
					-15280001235650L, 15280001234938L, -15280001234938L,
					15280001235647L, 15280049411686L, -15280049411686L, 15280001305857L}));

	/**
	 * Test rectangle coverage.
	 */
	@Test
	public final void testRectangleCoverage() {
		try {
			GeoCoordinates ll = new GeoCoordinatesImpl(5.09952, 52.10771);
			GeoCoordinates ur = new GeoCoordinatesImpl(5.10272, 52.10987);
			RectangleCoverage coverage = new RectangleCoverage(ll, ur);
			MapDatabase mdb = TestMap.getTestMapDatabase();
			AffectedLines affected = coverage.getAffectedLines(mdb);
			Assert.assertNotNull(affected);
			Utils.checkAffectedLines(affected, EXPECTED_COVERAGE,
					EXPECTED_INTERSECTION);
		} catch (Exception e) {
			Assert.fail("Unexpected exception", e);
		}
	}

	/**
	 * Test rectangle coverage empty1.
	 */
	@Test
	public final void testRectangleCoverageEmpty1() {
		try {
			GeoCoordinates ll = new GeoCoordinatesImpl(5.09952, 52.10771);
			GeoCoordinates ur = new GeoCoordinatesImpl(5.09952, 52.10771);
			RectangleCoverage coverage = new RectangleCoverage(ll, ur);
			MapDatabase mdb = TestMap.getTestMapDatabase();
			AffectedLines affected = coverage.getAffectedLines(mdb);
			Assert.assertNotNull(affected);
			Assert.assertTrue(affected.isEmpty());
		} catch (Exception e) {
			Assert.fail("Unexpected exception", e);
		}
	}

	/**
	 * Test rectangle coverage empty2.
	 */
	@Test
	public final void testRectangleCoverageEmpty2() {
		try {
			GeoCoordinates ll = new GeoCoordinatesImpl(5.09952, 52.10771);
			GeoCoordinates ur = new GeoCoordinatesImpl(5.10272, 52.10987);
			RectangleCoverage coverage = new RectangleCoverage(ll, ur);
			MapDatabase mdb = null;
			AffectedLines affected = coverage.getAffectedLines(mdb);
			Assert.assertNotNull(affected);
			Assert.assertTrue(affected.isEmpty());
		} catch (Exception e) {
			Assert.fail("Unexpected exception", e);
		}
	}

}
