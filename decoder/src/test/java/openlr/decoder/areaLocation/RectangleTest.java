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
package openlr.decoder.areaLocation;

import openlr.LocationType;
import openlr.decoder.OpenLRDecoder;
import openlr.decoder.OpenLRDecoderParameter;
import openlr.location.Location;
import openlr.map.GeoCoordinates;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.InvalidMapDataException;
import openlr.rawLocRef.RawLocationReference;
import openlr.rawLocRef.RawRectangleLocRef;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RectangleTest {

    private static final GeoCoordinates LL_SMALL;
    private static final GeoCoordinates UR_SMALL;

    private static final GeoCoordinates LL_LARGE;
    private static final GeoCoordinates UR_LARGE;

    static {
        GeoCoordinates lls = null;
        GeoCoordinates urs = null;
        GeoCoordinates lll = null;
        GeoCoordinates url = null;
        try {
            lls = new GeoCoordinatesImpl(5.09030, 52.08591);
            urs = new GeoCoordinatesImpl(5.12141, 52.10437);
            lll = new GeoCoordinatesImpl(4.95941, 52.01232);
            url = new GeoCoordinatesImpl(5.61497, 52.29168);
        } catch (InvalidMapDataException e) {
            Assert.fail("Unexpected exception", e);
        }
        LL_SMALL = lls;
        UR_SMALL = urs;
        LL_LARGE = lll;
        UR_LARGE = url;
    }


    @Test
    public final void testRectangleSmall() {
        try {
            RawLocationReference locref = new RawRectangleLocRef("rectangle", LL_SMALL, UR_SMALL);
            OpenLRDecoderParameter params = new OpenLRDecoderParameter.Builder().buildParameter();
            OpenLRDecoder decoder = new OpenLRDecoder();
            Location loc = decoder.decodeRaw(params, locref);
            Assert.assertNotNull(loc);
            Assert.assertTrue(loc.isValid());
            Assert.assertEquals(loc.getID(), "rectangle");
            Assert.assertEquals(loc.getLocationType(), LocationType.RECTANGLE);
            Assert.assertNull(loc.getReturnCode());
            Assert.assertNotNull(loc.getLowerLeftPoint());
            Assert.assertEquals(loc.getLowerLeftPoint(), LL_SMALL);
            Assert.assertNotNull(loc.getUpperRightPoint());
            Assert.assertEquals(loc.getUpperRightPoint(), UR_SMALL);
            Assert.assertNotNull(loc.getAffectedLines());
            Assert.assertTrue(loc.getAffectedLines().isEmpty());
        } catch (Exception e) {
            Assert.fail("Unexpected exception", e);
        }
    }

    @Test
    public final void testRectangleLarge() {
        try {
            RawLocationReference locref = new RawRectangleLocRef("rectangle", LL_LARGE, UR_LARGE);
            OpenLRDecoderParameter params = new OpenLRDecoderParameter.Builder().buildParameter();
            OpenLRDecoder decoder = new OpenLRDecoder();
            Location loc = decoder.decodeRaw(params, locref);
            Assert.assertNotNull(loc);
            Assert.assertTrue(loc.isValid());
            Assert.assertEquals(loc.getID(), "rectangle");
            Assert.assertEquals(loc.getLocationType(), LocationType.RECTANGLE);
            Assert.assertNull(loc.getReturnCode());
            Assert.assertNotNull(loc.getLowerLeftPoint());
            Assert.assertEquals(loc.getLowerLeftPoint(), LL_LARGE);
            Assert.assertNotNull(loc.getUpperRightPoint());
            Assert.assertEquals(loc.getUpperRightPoint(), UR_LARGE);
            Assert.assertNotNull(loc.getAffectedLines());
            Assert.assertTrue(loc.getAffectedLines().isEmpty());
        } catch (Exception e) {
            Assert.fail("Unexpected exception", e);
        }
    }

}
