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
package openlr.xml;

import openlr.xml.generated.OpenLR;
import org.testng.annotations.Test;

import javax.xml.bind.UnmarshalException;
import java.io.InputStream;

import static org.testng.Assert.fail;

/**
 * Tests XML decoding and encoding.
 */
public class InvalidGeoCoordTest {

    /**
     * Tests invalid longitude value in xml.
     */
    @Test
    public final void testInvalidLongitude() {
        OpenLR result = null;
        try {
            OpenLRXmlReader reader = new OpenLRXmlReader();
            InputStream stream = Utils.class.getClassLoader()
                    .getResourceAsStream("invalidLongitude.xml");

            result = reader.readOpenLRXML(stream, true);

        } catch (Exception e) {
            if (!(e instanceof UnmarshalException)) {
                fail("Unexpected exception!", e);
            }
        }
        if (result != null) {
            fail("Expected exception!");
        }

    }

    /**
     * Tests invalid latitude value in xml.
     */
    @Test
    public final void testInvalidLatitude() {
        OpenLR result = null;
        try {
            OpenLRXmlReader reader = new OpenLRXmlReader();
            InputStream stream = Utils.class.getClassLoader()
                    .getResourceAsStream("invalidLatitude.xml");

            result = reader.readOpenLRXML(stream, true);

        } catch (Exception e) {
            if (!(e instanceof UnmarshalException)) {
                fail("Unexpected exception!", e);
            }
        }
        if (result != null) {
            fail("Expected exception!");
        }

    }
}
