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
package openlr.decoder.areaLocation;

import openlr.LocationType;
import openlr.decoder.OpenLRDecoder;
import openlr.decoder.OpenLRDecoderParameter;
import openlr.location.Location;
import openlr.map.GeoCoordinates;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.InvalidMapDataException;
import openlr.rawLocRef.RawCircleLocRef;
import openlr.rawLocRef.RawLocationReference;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CircleTest {

    /** The latitude of the tested geo coordinate. */
    private static final double COORDINATE_LATITUDE = 49.60728;
    /** The longitude of the tested geo coordinate. */
    private static final double COORDINATE_LONGITUDE = 6.12699;

    private static final GeoCoordinates COORDINATE;
    /** The Constant RADIUS. */
    private static final long RADIUS_SMALL = 100;
    /** The Constant RADIUS_MEDIUM. */
    private static final long RADIUS_MEDIUM = 60000;
    /** The Constant RADIUS_LARGE. */
    private static final long RADIUS_LARGE = 16000000;
    /** The Constant RADIUS_EXTRA_LARGE. */
    private static final long RADIUS_EXTRA_LARGE = 4000000000L;

    static {
        GeoCoordinates gc = null;
        try {
            gc = new GeoCoordinatesImpl(COORDINATE_LONGITUDE, COORDINATE_LATITUDE);
        } catch (InvalidMapDataException e) {
            Assert.fail("Unexpected exception", e);
        }
        COORDINATE = gc;
    }

    @Test
    public final void testDecodeCircleSmall() {
        try {
            RawLocationReference locRef = new RawCircleLocRef("circle",
                    COORDINATE, RADIUS_SMALL);
            OpenLRDecoderParameter params = new OpenLRDecoderParameter.Builder().buildParameter();
            OpenLRDecoder decoder = new OpenLRDecoder();
            Location loc = decoder.decodeRaw(params, locRef);
            Assert.assertNotNull(loc);
            Assert.assertTrue(loc.isValid());
            Assert.assertEquals(loc.getID(), "circle");
            Assert.assertEquals(loc.getLocationType(), LocationType.CIRCLE);
            Assert.assertEquals(loc.getRadius(), RADIUS_SMALL);
            Assert.assertNull(loc.getReturnCode());
            Assert.assertNotNull(loc.getCenterPoint());
            Assert.assertEquals(loc.getCenterPoint(), COORDINATE);
            Assert.assertNotNull(loc.getAffectedLines());
            Assert.assertTrue(loc.getAffectedLines().isEmpty());
        } catch (Exception e) {
            Assert.fail("Unexpected exception", e);
        }
    }

    @Test
    public final void testDecodeCircleMedium() {
        try {
            RawLocationReference locRef = new RawCircleLocRef("circle",
                    COORDINATE, RADIUS_MEDIUM);
            OpenLRDecoderParameter params = new OpenLRDecoderParameter.Builder().buildParameter();
            OpenLRDecoder decoder = new OpenLRDecoder();
            Location loc = decoder.decodeRaw(params, locRef);
            Assert.assertNotNull(loc);
            Assert.assertTrue(loc.isValid());
            Assert.assertEquals(loc.getID(), "circle");
            Assert.assertEquals(loc.getLocationType(), LocationType.CIRCLE);
            Assert.assertEquals(loc.getRadius(), RADIUS_MEDIUM);
            Assert.assertNull(loc.getReturnCode());
            Assert.assertNotNull(loc.getCenterPoint());
            Assert.assertEquals(loc.getCenterPoint(), COORDINATE);
            Assert.assertNotNull(loc.getAffectedLines());
            Assert.assertTrue(loc.getAffectedLines().isEmpty());
        } catch (Exception e) {
            Assert.fail("Unexpected exception", e);
        }
    }

    @Test
    public final void testDecodeCircleLarge() {
        try {
            RawLocationReference locRef = new RawCircleLocRef("circle",
                    COORDINATE, RADIUS_LARGE);
            OpenLRDecoderParameter params = new OpenLRDecoderParameter.Builder().buildParameter();
            OpenLRDecoder decoder = new OpenLRDecoder();
            Location loc = decoder.decodeRaw(params, locRef);
            Assert.assertNotNull(loc);
            Assert.assertTrue(loc.isValid());
            Assert.assertEquals(loc.getID(), "circle");
            Assert.assertEquals(loc.getLocationType(), LocationType.CIRCLE);
            Assert.assertEquals(loc.getRadius(), RADIUS_LARGE);
            Assert.assertNull(loc.getReturnCode());
            Assert.assertNotNull(loc.getCenterPoint());
            Assert.assertEquals(loc.getCenterPoint(), COORDINATE);
            Assert.assertNotNull(loc.getAffectedLines());
            Assert.assertTrue(loc.getAffectedLines().isEmpty());
        } catch (Exception e) {
            Assert.fail("Unexpected exception", e);
        }
    }

    @Test
    public final void testDecodeCircleExtraLarge() {
        try {
            RawLocationReference locRef = new RawCircleLocRef("circle",
                    COORDINATE, RADIUS_EXTRA_LARGE);
            OpenLRDecoderParameter params = new OpenLRDecoderParameter.Builder().buildParameter();
            OpenLRDecoder decoder = new OpenLRDecoder();
            Location loc = decoder.decodeRaw(params, locRef);
            Assert.assertNotNull(loc);
            Assert.assertTrue(loc.isValid());
            Assert.assertEquals(loc.getID(), "circle");
            Assert.assertEquals(loc.getLocationType(), LocationType.CIRCLE);
            Assert.assertEquals(loc.getRadius(), RADIUS_EXTRA_LARGE);
            Assert.assertNull(loc.getReturnCode());
            Assert.assertNotNull(loc.getCenterPoint());
            Assert.assertEquals(loc.getCenterPoint(), COORDINATE);
            Assert.assertNotNull(loc.getAffectedLines());
            Assert.assertTrue(loc.getAffectedLines().isEmpty());
        } catch (Exception e) {
            Assert.fail("Unexpected exception", e);
        }
    }


}
