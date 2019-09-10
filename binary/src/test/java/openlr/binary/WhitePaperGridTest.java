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
package openlr.binary;

import openlr.LocationReference;
import openlr.LocationType;
import openlr.PhysicalFormatException;
import openlr.binary.encoder.GridEncoder;
import openlr.binary.impl.LocationReferenceBinaryImpl;
import openlr.map.GeoCoordinates;
import openlr.rawLocRef.RawGridLocRef;
import openlr.rawLocRef.RawLocationReference;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test the rectangle location example of the white paper
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class WhitePaperGridTest {

    private static final GeoCoordinates WP_LOWER_LEFT = Utils
            .geoCoordinateUnchecked(6.12555, 49.60586);

    private static final GeoCoordinates WP_UPPER_RIGHT = Utils
            .geoCoordinateUnchecked(6.126291, 49.606170);

    private static final int NR_COLUMNS = 5;

    private static final int NR_ROWS = 3;
    /**
     * Valid delta when comparing coordinate values. Compare at deca-micro
     * accuracy.
     */
    private static final double VALID_COORDINATE_DELTA = 0.000005;
    private static final byte[] LOCATION_REFERENCE_DATA = new BitFieldBuilder()
            .addByte("01000011").addByte("00000100").addByte("01011011")
            .addByte("00100000").addByte("00100011").addByte("01000110")
            .addByte("01111001").addByte("00000000").addByte("01001010")
            .addByte("00000000").addByte("00011111").addByte("00000000")
            .addByte("00000101").addByte("00000000").addByte("00000011")
            .toByteArray();
    private static int VERSION = OpenLRBinaryConstants.BINARY_VERSION_3;

    @Test
    public void testEncoding() {
        RawLocationReference locRef = new RawGridLocRef("grid", WP_LOWER_LEFT,
                WP_UPPER_RIGHT, NR_COLUMNS, NR_ROWS);
        GridEncoder encoder = new GridEncoder();

        LocationReference binLocRef = encoder.encodeData(locRef, VERSION);

        ByteArray data = (ByteArray) binLocRef.getLocationReferenceData();
        Utils.checkBinData(data, LOCATION_REFERENCE_DATA, VERSION);

        Assert.assertNotNull(binLocRef.toString());
        Assert.assertNotNull(binLocRef.hashCode());
        Assert.assertTrue(binLocRef.equals(binLocRef));

    }

    @Test
    public void testDecoding() throws PhysicalFormatException {
        LocationReferenceBinaryImpl binLocRef = new LocationReferenceBinaryImpl(
                "id", new ByteArray(LOCATION_REFERENCE_DATA));
        OpenLRBinaryDecoder decoder = new OpenLRBinaryDecoder();

        RawLocationReference locRef = decoder.decodeData(binLocRef);

        Assert.assertTrue(locRef.isValid());
        Assert.assertEquals(locRef.getLocationType(), LocationType.GRID);
        GeoCoordinates lowerLeft = locRef.getLowerLeftPoint();
        GeoCoordinates upperRight = locRef.getUpperRightPoint();

        Assert.assertEquals(lowerLeft.getLatitudeDeg(),
                WP_LOWER_LEFT.getLatitudeDeg(), VALID_COORDINATE_DELTA);
        Assert.assertEquals(lowerLeft.getLongitudeDeg(),
                WP_LOWER_LEFT.getLongitudeDeg(), VALID_COORDINATE_DELTA);

        Assert.assertEquals(upperRight.getLatitudeDeg(),
                WP_UPPER_RIGHT.getLatitudeDeg(), VALID_COORDINATE_DELTA);
        Assert.assertEquals(upperRight.getLongitudeDeg(),
                WP_UPPER_RIGHT.getLongitudeDeg(), VALID_COORDINATE_DELTA);

        Assert.assertEquals(locRef.getNumberOfColumns(), NR_COLUMNS);
        Assert.assertEquals(locRef.getNumberOfRows(), NR_ROWS);

    }
}
