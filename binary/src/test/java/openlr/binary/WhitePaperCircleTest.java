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
import openlr.binary.encoder.CircleEncoder;
import openlr.binary.impl.LocationReferenceBinaryImpl;
import openlr.map.GeoCoordinates;
import openlr.rawLocRef.RawCircleLocRef;
import openlr.rawLocRef.RawLocationReference;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Tests the circle location example of the White Paper
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class WhitePaperCircleTest {

    private static final long RADIUS = 170;
    private static final byte[] LOCATION_REFERENCE = new BitFieldBuilder()
            .addByte("00000011").addByte("00000100").addByte("01011011")
            .addByte("01100011").addByte("00100011").addByte("01000110")
            .addByte("10111011").addByte("10101010")
            .toByteArray();
    /**
     * Valid delta when comparing coordinate values. Compare at deca-micro
     * accuracy.
     */
    private static final double VALID_COORDINATE_DELTA = 0.000005;
    private static int VERSION = OpenLRBinaryConstants.BINARY_VERSION_3;
    private GeoCoordinates centerPointEncoder = Utils.geoCoordinateUnchecked(6.12699, 49.60728);
    private GeoCoordinates centerPointDecoder = Utils.geoCoordinateUnchecked(6.12699, 49.60727);

    @Test
    public void testEncoding() {
        RawCircleLocRef locRef = new RawCircleLocRef("id", centerPointEncoder,
                RADIUS);
        CircleEncoder encoder = new CircleEncoder();

        LocationReference binLocRef = encoder.encodeData(locRef, VERSION);
        ByteArray data = (ByteArray) binLocRef.getLocationReferenceData();

        Utils.checkBinData(data, LOCATION_REFERENCE, VERSION);
    }

    @Test
    public void testDecoding() throws PhysicalFormatException {
        LocationReferenceBinaryImpl locRef = new LocationReferenceBinaryImpl(
                "id", new ByteArray(LOCATION_REFERENCE));
        OpenLRBinaryDecoder decoder = new OpenLRBinaryDecoder();

        RawLocationReference binLocRef = decoder.decodeData(locRef);

        Assert.assertTrue(binLocRef.isValid());
        Assert.assertEquals(binLocRef.getLocationType(), LocationType.CIRCLE);
        Assert.assertEquals(binLocRef.getCenterPoint().getLatitudeDeg(),
                centerPointDecoder.getLatitudeDeg(), VALID_COORDINATE_DELTA);
        Assert.assertEquals(binLocRef.getCenterPoint().getLongitudeDeg(),
                centerPointDecoder.getLongitudeDeg(), VALID_COORDINATE_DELTA);
        Assert.assertEquals(binLocRef.getRadius(), RADIUS);

    }
}
