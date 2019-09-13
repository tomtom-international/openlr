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
package openlr.decoder.areaLocation;

import openlr.LocationType;
import openlr.decoder.OpenLRDecoder;
import openlr.decoder.OpenLRDecoderParameter;
import openlr.location.Location;
import openlr.map.GeoCoordinates;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.InvalidMapDataException;
import openlr.rawLocRef.RawGridLocRef;
import openlr.rawLocRef.RawLocationReference;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GridTest {

    private static final GeoCoordinates LL_SMALL;
    private static final GeoCoordinates UR_SMALL;

    private static final int NR_COLUMNS_1 = 4;

    private static final int NR_ROWS_1 = 5;

    static {
        GeoCoordinates lls = null;
        GeoCoordinates urs = null;
        try {
            lls = new GeoCoordinatesImpl(5.09030, 52.08591);
            urs = new GeoCoordinatesImpl(5.12141, 52.10437);
        } catch (InvalidMapDataException e) {
            Assert.fail("Unexpected exception", e);
        }
        LL_SMALL = lls;
        UR_SMALL = urs;
    }

    /**
     * Test rectangle1 encode.
     */
    @Test
    public final void testGrid1Decode() {
        try {
            RawLocationReference rawloc = new RawGridLocRef("grid", LL_SMALL, UR_SMALL, NR_COLUMNS_1, NR_ROWS_1);

            OpenLRDecoder decoder = new OpenLRDecoder();
            OpenLRDecoderParameter parameter = new OpenLRDecoderParameter.Builder().buildParameter();
            Location loc = decoder.decodeRaw(parameter,
                    rawloc);
            Assert.assertNotNull(loc);
            Assert.assertTrue(loc.isValid());
            Assert.assertEquals(loc.getID(), "grid");
            Assert.assertEquals(loc.getLocationType(), LocationType.GRID);
            Assert.assertEquals(loc.getLowerLeftPoint(), LL_SMALL);
            Assert.assertEquals(loc.getUpperRightPoint(), UR_SMALL);
            Assert.assertEquals(loc.getNumberOfColumns(), NR_COLUMNS_1);
            Assert.assertEquals(loc.getNumberOfRows(), NR_ROWS_1);
        } catch (Exception e) {
            Assert.fail("Unexpected exception", e);
        }
    }

}
