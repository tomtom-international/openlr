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
package openlr.binary;

import openlr.LocationReference;
import openlr.LocationType;
import openlr.PhysicalFormatException;
import openlr.binary.encoder.PolygonEncoder;
import openlr.binary.impl.LocationReferenceBinaryImpl;
import openlr.map.GeoCoordinates;
import openlr.rawLocRef.RawLocationReference;
import openlr.rawLocRef.RawPolygonLocRef;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Tests the polygon location example of the white paper
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class WhitePaperPolygonTest {

    private static final List<? extends GeoCoordinates> COORDINATES = Arrays
            .asList(new GeoCoordinates[]{
                    Utils.geoCoordinateUnchecked(6.12549, 49.60576),
                    Utils.geoCoordinateUnchecked(6.12903, 49.60591),
                    Utils.geoCoordinateUnchecked(6.12739, 49.60834),
                    Utils.geoCoordinateUnchecked(6.12657, 49.6087),
                    Utils.geoCoordinateUnchecked(6.12492, 49.60795)});

    private static final List<? extends GeoCoordinates> COORDINATES_DECODED = Arrays
            .asList(new GeoCoordinates[]{
                    Utils.geoCoordinateUnchecked(6.12549, 49.60577),
                    Utils.geoCoordinateUnchecked(6.12903, 49.60592),
                    Utils.geoCoordinateUnchecked(6.12739, 49.60835),
                    Utils.geoCoordinateUnchecked(6.12658, 49.60871),
                    Utils.geoCoordinateUnchecked(6.12493, 49.60796)});
    /**
     * Valid delta when comparing coordinate values. Compare at deca-micro
     * accuracy.
     */
    private static final double VALID_COORDINATE_DELTA = 0.000005;
    private static final byte[] LOCATION_REFERENCE_DATA = new BitFieldBuilder()
            .addByte("00010011").addByte("00000100").addByte("01011011")
            .addByte("00011101").addByte("00100011").addByte("01000110")
            .addByte("01110101").addByte("00000001").addByte("01100010")
            .addByte("00000000").addByte("00001111").addByte("11111111")
            .addByte("01011100").addByte("00000000").addByte("11110011")
            .addByte("11111111").addByte("10101110").addByte("00000000")
            .addByte("00100100").addByte("11111111").addByte("01011011")
            .addByte("11111111").addByte("10110101").toByteArray();
    private static int VERSION = OpenLRBinaryConstants.BINARY_VERSION_3;

    @Test
    public void testEncoding() {
        RawLocationReference rawLocRef = new RawPolygonLocRef("pentagon",
                COORDINATES);
        PolygonEncoder encoder = new PolygonEncoder();

        LocationReference binLocRef = encoder.encodeData(rawLocRef, VERSION);

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
        Assert.assertEquals(binLocRef.getLocationType(), LocationType.POLYGON);
        List<? extends GeoCoordinates> points = binLocRef.getCornerPoints();

        for (int i = 0; i < points.size(); i++) {
            GeoCoordinates actual = points.get(i);
            GeoCoordinates expected = COORDINATES_DECODED.get(i);
            Assert.assertEquals(actual.getLatitudeDeg(),
                    expected.getLatitudeDeg(), VALID_COORDINATE_DELTA);
            Assert.assertEquals(actual.getLongitudeDeg(),
                    expected.getLongitudeDeg(), VALID_COORDINATE_DELTA);

        }
    }
}
