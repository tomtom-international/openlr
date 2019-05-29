/**
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License and the extra
 *  conditions for OpenLR. (see openlr-license.txt)
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import openlr.LocationReference;
import openlr.LocationReferencePoint;
import openlr.LocationType;
import openlr.PhysicalFormatException;
import openlr.binary.encoder.ClosedLineEncoder;
import openlr.binary.impl.LocationReferenceBinaryImpl;
import openlr.binary.impl.LocationReferencePointBinaryImpl;
import openlr.map.FormOfWay;
import openlr.map.FunctionalRoadClass;
import openlr.map.utils.GeometryUtils;
import openlr.rawLocRef.RawClosedLineLocRef;
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
public class WhitePaperClosedLineTest {

    private static int VERSION = OpenLRBinaryConstants.BINARY_VERSION_3;

    private static final LocationReferencePointBinaryImpl LRP1 = new LocationReferencePointBinaryImpl(
            1, FunctionalRoadClass.FRC_2, FormOfWay.MULTIPLE_CARRIAGEWAY,
            6.12829, 49.60597, 134, 246, FunctionalRoadClass.FRC_3, false);

    private static final LocationReferencePointBinaryImpl LRP2 = new LocationReferencePointBinaryImpl(
            2, FunctionalRoadClass.FRC_3, FormOfWay.SINGLE_CARRIAGEWAY,
            6.12838, 49.60398, 227, 475, FunctionalRoadClass.FRC_7, false);

    private static final LocationReferencePointBinaryImpl LRP3 = new LocationReferencePointBinaryImpl(
            3, FunctionalRoadClass.FRC_2, FormOfWay.SINGLE_CARRIAGEWAY,
            6.12829, 49.60597, 239, 0, null, true);
    
    private static final LocationReferencePointBinaryImpl LRP1_DEC = new LocationReferencePointBinaryImpl(
            1, FunctionalRoadClass.FRC_2, FormOfWay.MULTIPLE_CARRIAGEWAY,
            6.1283, 49.60596, 129.375, 264, FunctionalRoadClass.FRC_3, false);
    
    private static final LocationReferencePointBinaryImpl LRP2_DEC = new LocationReferencePointBinaryImpl(
            2, FunctionalRoadClass.FRC_3, FormOfWay.SINGLE_CARRIAGEWAY,
            6.12839, 49.60397, 230.625, 498, FunctionalRoadClass.FRC_7, false);
    
    private static final LocationReferencePointBinaryImpl LRP3_DEC = new LocationReferencePointBinaryImpl(
            3, FunctionalRoadClass.FRC_2, FormOfWay.SINGLE_CARRIAGEWAY,
            6.1283, 49.60596, 241.875, 0, null, true);

    private static final byte[] LOCATION_REFERENCE_DATA = new BitFieldBuilder()
            .addByte("01011011").addByte("00000100").addByte("01011011")
            .addByte("10100000").addByte("00100011").addByte("01000110")
            .addByte("01111110").addByte("00010010").addByte("01101011")
            .addByte("00000100").addByte("00000000").addByte("00001001")
            .addByte("11111111").addByte("00111001").addByte("00011011")
            .addByte("11110100").addByte("00001000").addByte("00010011")
            .addByte("00010101").toByteArray();

    @Test
    public void testEncoding() {

        List<LocationReferencePoint> lrpList = new ArrayList<LocationReferencePoint>();
        lrpList.add(LRP1);
        lrpList.add(LRP2);
        lrpList.add(LRP3);

        RawLocationReference locRef = new RawClosedLineLocRef("closedLine",
                lrpList);
        ClosedLineEncoder encoder = new ClosedLineEncoder();

        LocationReference binLocRef = encoder.encodeData(locRef, VERSION);
    
        ByteArray data = (ByteArray) binLocRef.getLocationReferenceData();

        Utils.checkBinData(data, LOCATION_REFERENCE_DATA, VERSION);
    }

    @Test
    public void testDecoding() throws PhysicalFormatException {
        LocationReferenceBinaryImpl binLocRef = new LocationReferenceBinaryImpl(
                "id", new ByteArray(LOCATION_REFERENCE_DATA));
        OpenLRBinaryDecoder decoder = new OpenLRBinaryDecoder();

        RawLocationReference locRef = decoder.decodeData(binLocRef);

        Assert.assertTrue(locRef.isValid());
        Assert.assertEquals(locRef.getLocationType(), LocationType.CLOSED_LINE);
        checkDecodedLrps(locRef,
                new LocationReferencePoint[] {LRP1_DEC, LRP2_DEC, LRP3_DEC});
        Assert.assertNull(locRef.getOffsets());

    }

    /**
     * Checks the decoded LRPs against the expected values.
     * 
     * @param locRef
     *            The decoded location.
     * @param expectedLrps
     *            The list of expected LRPs
     */
    private void checkDecodedLrps(final RawLocationReference locRef,
            final LocationReferencePoint[] expectedLrps) {

        List<? extends LocationReferencePoint> lrps = locRef
                .getLocationReferencePoints();

        assertSame(lrps.size(), expectedLrps.length);

        LocationReferencePoint lrp;

        for (int i = 0; i < expectedLrps.length; i++) {

            lrp = lrps.get(i);
            double logRounded = GeometryUtils.round(lrps.get(i)
                    .getLongitudeDeg());
            double latRounded = GeometryUtils.round(lrps.get(i)
                    .getLatitudeDeg());

            assertEquals(logRounded, expectedLrps[i].getLongitudeDeg());
            assertEquals(latRounded, expectedLrps[i].getLatitudeDeg());
            assertEquals(lrp.getFOW(), expectedLrps[i].getFOW());
            assertEquals(lrp.getDistanceToNext(),
                    expectedLrps[i].getDistanceToNext());
            assertEquals(lrp.getFRC(), expectedLrps[i].getFRC());
            assertEquals(lrp.getLfrc(), expectedLrps[i].getLfrc());
            assertEquals(lrp.getBearing(), expectedLrps[i].getBearing());
        }
    }
}
