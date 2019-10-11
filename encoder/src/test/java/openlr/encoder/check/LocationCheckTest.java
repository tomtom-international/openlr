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
package openlr.encoder.check;

import openlr.OpenLRProcessingException;
import openlr.encoder.EncoderReturnCode;
import openlr.encoder.TestData;
import openlr.encoder.locationCheck.CheckResult;
import openlr.encoder.locationCheck.LocationCheck;
import openlr.encoder.locationCheck.worker.LineLocationCheck;
import org.testng.annotations.Test;

import static org.testng.Assert.*;


/**
 * The Class TestLocationCheck test the LocationCheck class.
 *
 */
public class LocationCheckTest {

    /** The td. */
    private TestData td = TestData.getInstance();

    /**
     * Test check valid location.
     */
    @Test
    public final void testCheckValidLocation() {
        try {
            LocationCheck locCheck = new LineLocationCheck();
            CheckResult retCode = locCheck.check(td
                    .getProperties(), td.getMapDatabase(), td
                    .getWhitepaperLineLocation());
            assertTrue(retCode.checkPassed());
        } catch (OpenLRProcessingException e) {
            fail("LocationCheck failed: " + e.getMessage());
        }
    }


    /**
     * Test check not connected location.
     */
    @Test
    public final void testCheckNotConnectedLocation() {
        try {
            LocationCheck locCheck = new LineLocationCheck();
            CheckResult retCode = locCheck.check(td
                    .getProperties(), td.getMapDatabase(), td
                    .getNotConnectedLocation());
            assertFalse(retCode.checkPassed());
            assertEquals(retCode.getError(), EncoderReturnCode.LOCATION_NOT_CONNECTED);
        } catch (Exception e) {
            fail("Wrong error detected: " + e.getMessage());
        }
    }

    /**
     * Test empty location.
     */
    @Test
    public final void testEmptyLocation() {
        try {
            LocationCheck locCheck = new LineLocationCheck();
            CheckResult retCode = locCheck.check(td
                    .getProperties(), td.getMapDatabase(), td
                    .getEmptyLocation());
            assertFalse(retCode.checkPassed());
            assertEquals(retCode.getError(), EncoderReturnCode.LOCATION_IS_EMPTY);
        } catch (Exception e) {
            fail("Wrong error detected: " + e.getMessage());
        }
    }

}
