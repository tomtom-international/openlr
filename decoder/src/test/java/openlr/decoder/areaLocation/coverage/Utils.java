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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import openlr.location.data.AffectedLines;
import openlr.map.Line;

import org.testng.Assert;

public class Utils {
	
	/**
	 * Utility class shall not be instantiated.
	 */
	private Utils() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Check affected lines.
	 *
	 * @param affected the affected
	 * @param expectedCovered the expected covered
	 * @param expectedIntersected the expected intersected
	 */
	public static final void checkAffectedLines(final AffectedLines affected,
			final Set<Long> expectedCovered, final Set<Long> expectedIntersected) {
		List<? extends Line> covered = affected.getCoveredLines();
		List<? extends Line> intersected = affected.getIntersectedLines();
		Assert.assertEquals(covered.size(), expectedCovered.size());
		for (Line l : covered) {
			Assert.assertTrue(expectedCovered.contains(l.getID()));
		}
		Assert.assertEquals(intersected.size(), expectedIntersected.size());
		for (Line l : intersected) {
			Assert.assertTrue(expectedIntersected.contains(l.getID()));
		}
	}

}
