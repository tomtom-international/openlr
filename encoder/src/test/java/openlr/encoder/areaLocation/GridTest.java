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
package openlr.encoder.areaLocation;

import openlr.LocationType;
import openlr.encoder.EncoderReturnCode;
import openlr.encoder.LocationReferenceHolder;
import openlr.encoder.OpenLREncoder;
import openlr.encoder.OpenLREncoderParameter;
import openlr.location.Location;
import openlr.location.LocationFactory;
import openlr.map.GeoCoordinates;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.InvalidMapDataException;
import openlr.map.utils.GeometryUtils;
import openlr.rawLocRef.RawLocationReference;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GridTest {

    private static final GeoCoordinates LL_SMALL;
    private static final GeoCoordinates UR_SMALL;

    private static final GeoCoordinates LL_LARGE;
    private static final GeoCoordinates UR_LARGE;

    private static final int NR_COLUMNS_1 = 4;

    private static final int NR_ROWS_1 = 5;

    private static final int NR_COLUMNS_2 = 10;

    private static final int NR_ROWS_2 = 26;

    private static final int INVALID_COLUMNS = 65000;

    private static final int INVALID_ROWS = 165000;

    private static final GeoCoordinates SCALED_UR;

    static {
        GeoCoordinates lls = null;
        GeoCoordinates urs = null;
        GeoCoordinates lll = null;
        GeoCoordinates url = null;
        GeoCoordinates scaled = null;
        try {
            lls = new GeoCoordinatesImpl(5.09030, 52.08591);
            urs = new GeoCoordinatesImpl(5.12141, 52.10437);
            lll = new GeoCoordinatesImpl(4.95941, 52.01232);
            url = new GeoCoordinatesImpl(5.61497, 52.29168);
            scaled = new GeoCoordinatesImpl(5.09808, 52.08960);
        } catch (InvalidMapDataException e) {
            Assert.fail("Unexpected exception", e);
        }
        LL_SMALL = lls;
        UR_SMALL = urs;
        LL_LARGE = lll;
        UR_LARGE = url;
        SCALED_UR = scaled;
    }

    /**
     * Test rectangle1 encode.
     */
    @Test
    public final void testGrid1Encode() {
        try {
            Location loc = LocationFactory.createGridLocationFromBasisCell(
                    "grid", LL_SMALL.getLongitudeDeg(),
                    LL_SMALL.getLatitudeDeg(), UR_SMALL.getLongitudeDeg(),
                    UR_SMALL.getLatitudeDeg(), NR_COLUMNS_1, NR_ROWS_1);

            OpenLREncoder encoder = new OpenLREncoder();
            OpenLREncoderParameter parameter = new OpenLREncoderParameter.Builder().buildParameter();
            LocationReferenceHolder locrefs = encoder.encodeLocation(parameter,
                    loc);
            Assert.assertNotNull(locrefs);
            Assert.assertTrue(locrefs.isValid());
            Assert.assertEquals(locrefs.getID(), "grid");
            Assert.assertEquals(locrefs.getLocationType(), LocationType.GRID);
            RawLocationReference raw = locrefs.getRawLocationReferenceData();
            Assert.assertNotNull(raw);
            Assert.assertEquals(raw.getLowerLeftPoint(), LL_SMALL);
            Assert.assertEquals(raw.getUpperRightPoint(), UR_SMALL);
            Assert.assertEquals(raw.getNumberOfColumns(), NR_COLUMNS_1);
            Assert.assertEquals(raw.getNumberOfRows(), NR_ROWS_1);
        } catch (Exception e) {
            Assert.fail("Unexpected exception", e);
        }
    }

    /**
     * Test rectangle1 encode.
     */
    @Test
    public final void testGrid2Encode() {
        try {
            Location loc = LocationFactory.createGridLocationFromBasisCell(
                    "grid", LL_SMALL.getLongitudeDeg(),
                    LL_SMALL.getLatitudeDeg(), UR_SMALL.getLongitudeDeg(),
                    UR_SMALL.getLatitudeDeg(), NR_COLUMNS_2, NR_ROWS_2);

            OpenLREncoder encoder = new OpenLREncoder();
            OpenLREncoderParameter parameter = new OpenLREncoderParameter.Builder().buildParameter();
            LocationReferenceHolder locrefs = encoder.encodeLocation(parameter,
                    loc);
            Assert.assertNotNull(locrefs);
            Assert.assertTrue(locrefs.isValid());
            Assert.assertEquals(locrefs.getID(), "grid");
            Assert.assertEquals(locrefs.getLocationType(), LocationType.GRID);
            RawLocationReference raw = locrefs.getRawLocationReferenceData();
            Assert.assertNotNull(raw);
            Assert.assertEquals(raw.getLowerLeftPoint(), LL_SMALL);
            Assert.assertEquals(raw.getUpperRightPoint(), UR_SMALL);
            Assert.assertEquals(raw.getNumberOfColumns(), NR_COLUMNS_2);
            Assert.assertEquals(raw.getNumberOfRows(), NR_ROWS_2);
        } catch (Exception e) {
            Assert.fail("Unexpected exception", e);
        }
    }

    /**
     * Test rectangle1 encode.
     */
    @Test
    public final void testGridInvalidColumnsEncode() {
        try {
            Location loc = LocationFactory.createGridLocationFromBasisCell(
                    "grid", LL_SMALL.getLongitudeDeg(),
                    LL_SMALL.getLatitudeDeg(), UR_SMALL.getLongitudeDeg(),
                    UR_SMALL.getLatitudeDeg(), INVALID_COLUMNS, NR_ROWS_2);

            OpenLREncoder encoder = new OpenLREncoder();
            OpenLREncoderParameter parameter = new OpenLREncoderParameter.Builder().buildParameter();
            LocationReferenceHolder locrefs = encoder.encodeLocation(parameter,
                    loc);
            Assert.assertNotNull(locrefs);
            Assert.assertFalse(locrefs.isValid());
            Assert.assertEquals(locrefs.getID(), "grid");
            Assert.assertEquals(locrefs.getLocationType(), LocationType.GRID);
            RawLocationReference raw = locrefs.getRawLocationReferenceData();
            Assert.assertNull(raw);
            Assert.assertEquals(locrefs.getReturnCode(),
                    EncoderReturnCode.INVALID_NUMBER_OF_COLUMNS);
        } catch (Exception e) {
            Assert.fail("Unexpected exception", e);
        }
    }

    /**
     * Test rectangle1 encode.
     */
    @Test
    public final void testGridInvalidRowsEncode() {
        try {
            Location loc = LocationFactory.createGridLocationFromBasisCell(
                    "grid", LL_SMALL.getLongitudeDeg(),
                    LL_SMALL.getLatitudeDeg(), UR_SMALL.getLongitudeDeg(),
                    UR_SMALL.getLatitudeDeg(), NR_COLUMNS_2, INVALID_ROWS);

            OpenLREncoder encoder = new OpenLREncoder();
            OpenLREncoderParameter parameter = new OpenLREncoderParameter.Builder().buildParameter();
            LocationReferenceHolder locrefs = encoder.encodeLocation(parameter,
                    loc);
            Assert.assertNotNull(locrefs);
            Assert.assertFalse(locrefs.isValid());
            Assert.assertEquals(locrefs.getID(), "grid");
            Assert.assertEquals(locrefs.getLocationType(), LocationType.GRID);
            RawLocationReference raw = locrefs.getRawLocationReferenceData();
            Assert.assertNull(raw);
            Assert.assertEquals(locrefs.getReturnCode(),
                    EncoderReturnCode.INVALID_NUMBER_OF_ROWS);
        } catch (Exception e) {
            Assert.fail("Unexpected exception", e);
        }
    }

    /**
     * Test rectangle2 encode.
     */
    @Test
    public final void testGrid3Encode() {
        try {
            Location loc = LocationFactory.createGridLocationFromBasisCell(
                    "grid", LL_LARGE.getLongitudeDeg(),
                    LL_LARGE.getLatitudeDeg(), UR_LARGE.getLongitudeDeg(),
                    UR_LARGE.getLatitudeDeg(), NR_COLUMNS_1, NR_ROWS_1);

            OpenLREncoder encoder = new OpenLREncoder();
            OpenLREncoderParameter parameter = new OpenLREncoderParameter.Builder().buildParameter();
            LocationReferenceHolder locrefs = encoder.encodeLocation(parameter,
                    loc);
            Assert.assertNotNull(locrefs);
            Assert.assertTrue(locrefs.isValid());
            Assert.assertEquals(locrefs.getID(), "grid");
            Assert.assertEquals(locrefs.getLocationType(), LocationType.GRID);
            RawLocationReference raw = locrefs.getRawLocationReferenceData();
            Assert.assertNotNull(raw);
            Assert.assertEquals(raw.getLowerLeftPoint(), LL_LARGE);
            Assert.assertEquals(raw.getUpperRightPoint(), UR_LARGE);
            Assert.assertEquals(raw.getNumberOfColumns(), NR_COLUMNS_1);
            Assert.assertEquals(raw.getNumberOfRows(), NR_ROWS_1);
        } catch (Exception e) {
            Assert.fail("Unexpected exception", e);
        }
    }

    @Test
    public final void testEncodeGridFromArea() {
        try {
            Location loc = LocationFactory.createGridLocationFromGridArea(
                    "gridArea", LL_SMALL.getLongitudeDeg(),
                    LL_SMALL.getLatitudeDeg(), UR_SMALL.getLongitudeDeg(),
                    UR_SMALL.getLatitudeDeg(), NR_COLUMNS_1, NR_ROWS_1);

            OpenLREncoder encoder = new OpenLREncoder();
            OpenLREncoderParameter parameter = new OpenLREncoderParameter.Builder().buildParameter();
            LocationReferenceHolder locrefs = encoder.encodeLocation(parameter,
                    loc);
            Assert.assertNotNull(locrefs);
            Assert.assertTrue(locrefs.isValid());
            Assert.assertEquals(locrefs.getID(), "gridArea");
            Assert.assertEquals(locrefs.getLocationType(), LocationType.GRID);
            RawLocationReference raw = locrefs.getRawLocationReferenceData();
            Assert.assertNotNull(raw);
            Assert.assertEquals(raw.getLowerLeftPoint(), LL_SMALL);
            Assert.assertEquals(GeometryUtils.round(raw.getUpperRightPoint()
                    .getLongitudeDeg()), SCALED_UR.getLongitudeDeg());
            Assert.assertEquals(GeometryUtils.round(raw.getUpperRightPoint()
                    .getLatitudeDeg()), SCALED_UR.getLatitudeDeg());
            Assert.assertEquals(raw.getNumberOfColumns(), NR_COLUMNS_1);
            Assert.assertEquals(raw.getNumberOfRows(), NR_ROWS_1);
        } catch (Exception e) {
            Assert.fail("Unexpected exception", e);
        }
    }

}
