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
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import openlr.decoder.TestMap;
import openlr.decoder.worker.coverage.CircleCoverage;
import openlr.location.data.AffectedLines;
import openlr.map.GeoCoordinates;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.MapDatabase;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CircleCoverageTest {

	/** The Constant EXPECTED_COVERAGE. */
	private final Set<Long> EXPECTED_COVERAGE = new HashSet<Long>(
			Arrays.asList(new Long[] {15280001229335L, -15280001229333L,
					15280001229330L, -15280001229330L, 15280001229308L,
					-15280001229308L, 15280001229187L, -15280001229187L,
					15280001229188L, -15280001229188L}));

	/** The Constant EXPECTED_INTERSECTION. */
	private static final Set<Long> EXPECTED_INTERSECTION = new HashSet<Long>(
			Arrays.asList(new Long[] {15280001229334L, 15280001235667L,
					15280001229207L, -15280001229207L, 15280001229177L,
					-15280001229177L, 15280001229178L, -15280001229178L,
					15280001229304L, 15280001229189L, -15280001229189L,
					15280001229305L, 15280001229331L, -15280001229331L,
					15280001235663L, -15280001229332L}));

	/**
	 * Test circle coverage.
	 */
	@Test
	public final void testCircleCoverage() {
		try {
			MapDatabase mdb = TestMap.getTestMapDatabase();
			GeoCoordinates gc = new GeoCoordinatesImpl(5.10187, 52.10592);
			CircleCoverage coverage = new CircleCoverage(gc, 100);
			AffectedLines affected = coverage.getAffectedLines(mdb);
			Assert.assertNotNull(affected);
			Utils.checkAffectedLines(affected, EXPECTED_COVERAGE,
					EXPECTED_INTERSECTION);
		} catch (Exception e) {
			Assert.fail("Unexpected exception", e);
		}
	}
	
	/**
	 * Test circle coverage.
	 */
	@Test
	public final void testCircleCoverageEmpty1() {
		try {
			MapDatabase mdb = TestMap.getTestMapDatabase();
			GeoCoordinates gc = new GeoCoordinatesImpl(5.10187, 52.10592);
			CircleCoverage coverage = new CircleCoverage(gc, 0);
			AffectedLines affected = coverage.getAffectedLines(mdb);
			Set<Long> empty = Collections.emptySet();
			Utils.checkAffectedLines(affected, empty,
					empty);
		} catch (Exception e) {
			Assert.fail("Unexpected exception", e);
		}
	}
	
	/**
	 * Test circle coverage.
	 */
	@Test
	public final void testCircleCoverageEmpty2() {
		try {
			MapDatabase mdb = null;
			GeoCoordinates gc = new GeoCoordinatesImpl(5.10187, 52.10592);
			CircleCoverage coverage = new CircleCoverage(gc, 100);
			AffectedLines affected = coverage.getAffectedLines(mdb);
			Set<Long> empty = Collections.emptySet();
			Utils.checkAffectedLines(affected, empty,
					empty);
		} catch (Exception e) {
			Assert.fail("Unexpected exception", e);
		}
	}

}
