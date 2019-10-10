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
package openlr.encoder.areaLocation;

import openlr.LocationType;
import openlr.encoder.EncoderReturnCode;
import openlr.encoder.LocationReferenceHolder;
import openlr.encoder.OpenLREncoder;
import openlr.encoder.OpenLREncoderParameter;
import openlr.location.Location;
import openlr.location.LocationFactory;
import openlr.rawLocRef.RawLocationReference;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CircleTest {

    /** The latitude of the tested geo coordinate. */
    private static final double COORDINATE_LATITUDE = 49.60728;
    /** The longitude of the tested geo coordinate. */
    private static final double COORDINATE_LONGITUDE = 6.12699;

    /** The Constant RADIUS. */
    private static final long RADIUS_SMALL = 100;

    /** The Constant RADIUS_MEDIUM. */
    private static final long RADIUS_MEDIUM = 60000;

    /** The Constant RADIUS_LARGE. */
    private static final long RADIUS_LARGE = 16000000;

    /** The Constant RADIUS_EXTRA_LARGE. */
    private static final long RADIUS_EXTRA_LARGE = 4000000000L;

    /** The Constant RADIUS_EXTRA_LARGE. */
    private static final long RADIUS_INVALID = 5000000000L;

    /** The Constant RADIUS_EXTRA_LARGE. */
    private static final long RADIUS_INVALID2 = -10L;

    @Test
    public final void testCircleSmall() {
        try {
            Location loc = LocationFactory.createCircleLocation("circle",
                    COORDINATE_LONGITUDE, COORDINATE_LATITUDE, RADIUS_SMALL);
            OpenLREncoderParameter params = new OpenLREncoderParameter.Builder().buildParameter();
            OpenLREncoder encoder = new OpenLREncoder();
            LocationReferenceHolder locrefs = encoder.encodeLocation(params, loc);
            Assert.assertNotNull(locrefs);
            Assert.assertTrue(locrefs.isValid());
            Assert.assertEquals(locrefs.getID(), "circle");
            Assert.assertEquals(locrefs.getLocationType(), LocationType.CIRCLE);
            RawLocationReference raw = locrefs.getRawLocationReferenceData();
            Assert.assertNotNull(raw);
            Assert.assertEquals(raw.getCenterPoint().getLongitudeDeg(), COORDINATE_LONGITUDE);
            Assert.assertEquals(raw.getCenterPoint().getLatitudeDeg(), COORDINATE_LATITUDE);
            Assert.assertEquals(raw.getRadius(), RADIUS_SMALL);
        } catch (Exception e) {
            Assert.fail("unexpected exception", e);
        }
    }

    @Test
    public final void testCircleMedium() {
        try {
            Location loc = LocationFactory.createCircleLocation("circle",
                    COORDINATE_LONGITUDE, COORDINATE_LATITUDE, RADIUS_MEDIUM);
            OpenLREncoderParameter params = new OpenLREncoderParameter.Builder().buildParameter();
            OpenLREncoder encoder = new OpenLREncoder();
            LocationReferenceHolder locrefs = encoder.encodeLocation(params, loc);
            Assert.assertNotNull(locrefs);
            Assert.assertTrue(locrefs.isValid());
            Assert.assertEquals(locrefs.getLocationType(), LocationType.CIRCLE);
            RawLocationReference raw = locrefs.getRawLocationReferenceData();
            Assert.assertNotNull(raw);
            Assert.assertEquals(raw.getCenterPoint().getLongitudeDeg(), COORDINATE_LONGITUDE);
            Assert.assertEquals(raw.getCenterPoint().getLatitudeDeg(), COORDINATE_LATITUDE);
            Assert.assertEquals(raw.getRadius(), RADIUS_MEDIUM);
        } catch (Exception e) {
            Assert.fail("unexpected exception", e);
        }
    }

    @Test
    public final void testCircleLarge() {
        try {
            Location loc = LocationFactory.createCircleLocation("circle",
                    COORDINATE_LONGITUDE, COORDINATE_LATITUDE, RADIUS_LARGE);
            OpenLREncoderParameter params = new OpenLREncoderParameter.Builder().buildParameter();
            OpenLREncoder encoder = new OpenLREncoder();
            LocationReferenceHolder locrefs = encoder.encodeLocation(params, loc);
            Assert.assertNotNull(locrefs);
            Assert.assertTrue(locrefs.isValid());
            Assert.assertEquals(locrefs.getLocationType(), LocationType.CIRCLE);
            RawLocationReference raw = locrefs.getRawLocationReferenceData();
            Assert.assertNotNull(raw);
            Assert.assertEquals(raw.getCenterPoint().getLongitudeDeg(), COORDINATE_LONGITUDE);
            Assert.assertEquals(raw.getCenterPoint().getLatitudeDeg(), COORDINATE_LATITUDE);
            Assert.assertEquals(raw.getRadius(), RADIUS_LARGE);
        } catch (Exception e) {
            Assert.fail("unexpected exception", e);
        }
    }

    @Test
    public final void testCircleExtraLarge() {
        try {
            Location loc = LocationFactory.createCircleLocation("circle",
                    COORDINATE_LONGITUDE, COORDINATE_LATITUDE, RADIUS_EXTRA_LARGE);
            OpenLREncoderParameter params = new OpenLREncoderParameter.Builder().buildParameter();
            OpenLREncoder encoder = new OpenLREncoder();
            LocationReferenceHolder locrefs = encoder.encodeLocation(params, loc);
            Assert.assertNotNull(locrefs);
            Assert.assertTrue(locrefs.isValid());
            Assert.assertEquals(locrefs.getLocationType(), LocationType.CIRCLE);
            RawLocationReference raw = locrefs.getRawLocationReferenceData();
            Assert.assertNotNull(raw);
            Assert.assertEquals(raw.getCenterPoint().getLongitudeDeg(), COORDINATE_LONGITUDE);
            Assert.assertEquals(raw.getCenterPoint().getLatitudeDeg(), COORDINATE_LATITUDE);
            Assert.assertEquals(raw.getRadius(), RADIUS_EXTRA_LARGE);
        } catch (Exception e) {
            Assert.fail("unexpected exception", e);
        }
    }


    @Test
    public final void testCircleInvalid() {
        try {
            Location loc = LocationFactory.createCircleLocation("circle",
                    COORDINATE_LONGITUDE, COORDINATE_LATITUDE, RADIUS_INVALID);
            OpenLREncoderParameter params = new OpenLREncoderParameter.Builder().buildParameter();
            OpenLREncoder encoder = new OpenLREncoder();
            LocationReferenceHolder locrefs = encoder.encodeLocation(params, loc);
            Assert.assertNotNull(locrefs);
            Assert.assertFalse(locrefs.isValid());
            Assert.assertEquals(locrefs.getLocationType(), LocationType.CIRCLE);
            Assert.assertEquals(locrefs.getReturnCode(), EncoderReturnCode.INVALID_RADIUS);
        } catch (Exception e) {
            Assert.fail("unexpected exception", e);
        }
    }

    @Test
    public final void testCircleInvalid2() {
        try {
            Location loc = LocationFactory.createCircleLocation("circle",
                    COORDINATE_LONGITUDE, COORDINATE_LATITUDE, RADIUS_INVALID2);
            OpenLREncoderParameter params = new OpenLREncoderParameter.Builder().buildParameter();
            OpenLREncoder encoder = new OpenLREncoder();
            LocationReferenceHolder locrefs = encoder.encodeLocation(params, loc);
            Assert.assertNotNull(locrefs);
            Assert.assertFalse(locrefs.isValid());
            Assert.assertEquals(locrefs.getLocationType(), LocationType.CIRCLE);
            Assert.assertEquals(locrefs.getReturnCode(), EncoderReturnCode.INVALID_RADIUS);
        } catch (Exception e) {
            Assert.fail("unexpected exception", e);
        }
    }


}
