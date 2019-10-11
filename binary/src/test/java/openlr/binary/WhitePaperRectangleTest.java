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
package openlr.binary;

import openlr.LocationReference;
import openlr.LocationType;
import openlr.PhysicalFormatException;
import openlr.binary.encoder.RectangleEncoder;
import openlr.binary.impl.LocationReferenceBinaryImpl;
import openlr.map.GeoCoordinates;
import openlr.rawLocRef.RawLocationReference;
import openlr.rawLocRef.RawRectangleLocRef;
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
public class WhitePaperRectangleTest {
    private static final GeoCoordinates WP_LOWER_LEFT = Utils.geoCoordinateUnchecked(6.12555, 49.60586);

    private static final GeoCoordinates WP_UPPER_RIGHT = Utils.geoCoordinateUnchecked(6.12875, 49.60711);
    /**
     * Valid delta when comparing coordinate values. Compare at deca-micro
     * accuracy.
     */
    private static final double VALID_COORDINATE_DELTA = 0.000005;
    private static final byte[] LOCATION_REFERENCE_DATA = new BitFieldBuilder()
            .addByte("01000011").addByte("00000100").addByte("01011011")
            .addByte("00100000").addByte("00100011").addByte("01000110")
            .addByte("01111001").addByte("00000001").addByte("01000000")
            .addByte("00000000").addByte("01111101").toByteArray();
    private static int VERSION = OpenLRBinaryConstants.BINARY_VERSION_3;

    /**
     * Calculates the 32 bit double value representation of a coordinate out of
     * a 24 bit integer value representation.
     *
     * @param val
     *            the 24 bit integer value
     *
     * @return the 32 bit double value representation
     */
    private static final double calculate32BitRepresentation(final int val) {
        int sgn = (int) Math.signum(val);
        double retVal = (val - (sgn * OpenLRBinaryConstants.ROUND_FACTOR))
                * OpenLRBinaryConstants.BIT24FACTOR_REVERSED;
        return retVal;
    }

    @Test
    public void testEncoding() {
        RawRectangleLocRef locRef = new RawRectangleLocRef("rect", WP_LOWER_LEFT,
                WP_UPPER_RIGHT);
        RectangleEncoder encoder = new RectangleEncoder();

        LocationReference binLocRef = encoder.encodeData(locRef, VERSION);

        ByteArray data = (ByteArray) binLocRef.getLocationReferenceData();

        Utils.checkBinData(data, LOCATION_REFERENCE_DATA, VERSION);
    }

    @Test
    public void testDecoding() throws PhysicalFormatException {
        LocationReferenceBinaryImpl locRef = new LocationReferenceBinaryImpl(
                "id", new ByteArray(LOCATION_REFERENCE_DATA));
        OpenLRBinaryDecoder decoder = new OpenLRBinaryDecoder();

        RawLocationReference binLocRef = decoder.decodeData(locRef);

        Assert.assertTrue(binLocRef.isValid());
        Assert.assertEquals(binLocRef.getLocationType(), LocationType.RECTANGLE);
        GeoCoordinates lowerLeft = binLocRef.getLowerLeftPoint();
        GeoCoordinates upperRight = binLocRef.getUpperRightPoint();

        int latitude = get24BitRepresentation(WP_UPPER_RIGHT.getLatitudeDeg());
        System.out.println(Integer.toBinaryString(latitude));

        Assert.assertEquals(lowerLeft.getLatitudeDeg(),
                WP_LOWER_LEFT.getLatitudeDeg(), VALID_COORDINATE_DELTA);
        Assert.assertEquals(lowerLeft.getLongitudeDeg(),
                WP_LOWER_LEFT.getLongitudeDeg(), VALID_COORDINATE_DELTA);

        Assert.assertEquals(upperRight.getLatitudeDeg(),
                WP_UPPER_RIGHT.getLatitudeDeg(), VALID_COORDINATE_DELTA);
        Assert.assertEquals(upperRight.getLongitudeDeg(),
                WP_UPPER_RIGHT.getLongitudeDeg(), VALID_COORDINATE_DELTA);


    }

    /**
     * Calculates the 24 bit representation of a coordinate value.
     *
     * @param val
     *            the coordinate value
     *
     * @return the 24 bit representation of the coordinate value
     */
    private int get24BitRepresentation(final double val) {
        int sgn = (int) Math.signum(val);
        int retVal = Math
                .round((float) ((sgn * OpenLRBinaryConstants.ROUND_FACTOR) + (val * OpenLRBinaryConstants.BIT24FACTOR)));
        return retVal;
    }


}
