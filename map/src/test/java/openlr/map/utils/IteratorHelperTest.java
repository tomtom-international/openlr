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

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Tests class {@link IteratorHelper}.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class IteratorHelperTest {

    /** The size of the list used for the tests. */
    private static final int LIST_SIZE = 3;

    /**
     * Tests class {@link IteratorHelper}.
     */
    @Test
    public final void testIteratorHelper() {

        List<String> fruits = new ArrayList<String>(LIST_SIZE);
        fruits.add("Apple");
        fruits.add("Orange");
        fruits.add("Pear");

        assertTrue(IteratorHelper.contains(fruits.iterator(), "Apple"));
        assertFalse(IteratorHelper.contains(fruits.iterator(), "Potato"));

        try {
            IteratorHelper.contains(fruits.iterator(), null);
            Assert.fail("Expected NullPointerException missing");
        } catch (NullPointerException e) {
            // that's expected
            assertNotNull(e); // to satisfy checkstyle
        }

        try {
            IteratorHelper.contains(null, "Apple");
            fail("Expected NullPointerException missing");
        } catch (NullPointerException e) {
            // that's expected
            assertNotNull(e); // to satisfy checkstyle
        }

        assertEquals(IteratorHelper.size(fruits.iterator()), LIST_SIZE,
                "Unexpected size delivered.");

        try {
            IteratorHelper.size(null);
            Assert.fail("Expected NullPointerException missing");
        } catch (NullPointerException e) {
            // that's expected
            assertNotNull(e); // to satisfy checkstyle
        }

    }

}
