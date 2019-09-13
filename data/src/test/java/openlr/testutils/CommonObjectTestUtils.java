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
package openlr.testutils;

import static org.testng.Assert.*;

/**
 * Provides some utility methods for common tests of java objects like toString,
 * equals or hashCode.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public final class CommonObjectTestUtils {

    /**
     * Disabled constructor.
     */
    private CommonObjectTestUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Performs some tests of the methods equals() and hashCode() on the given
     * objects. In case of error
     *
     * @param a
     *            Object a.
     * @param equalToA
     *            An object that should be equal to a.
     * @param unEqualToA
     *            An object that should be unequal to a.
     */
    public static void testCompare(final Object a, final Object equalToA,
                                   final Object unEqualToA) {

        assertTrue(a.equals(a));
        assertTrue(a.equals(equalToA));
        assertTrue(equalToA.equals(a));

        assertTrue(a.hashCode() == a.hashCode());
        assertTrue(a.hashCode() == equalToA.hashCode());

        assertFalse(a.equals(unEqualToA));
        assertFalse(unEqualToA.equals(a));
        assertFalse(a.equals(null));

        assertTrue(a.hashCode() != unEqualToA.hashCode());
    }

    /**
     * Tests the toString method of the given object for not returning null.
     * @param o The object to check.
     */
    public static void testToString(final Object o) {
        assertNotNull(o.toString());
    }
}
