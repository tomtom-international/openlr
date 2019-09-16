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
package openlr.encoder;

import openlr.LocationReferencePoint;
import openlr.LocationType;
import openlr.OpenLRProcessingException;
import openlr.location.Location;
import openlr.location.LocationFactory;
import openlr.map.GeoCoordinates;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.InvalidMapDataException;
import openlr.map.Line;
import openlr.map.mockdb.InvalidConfigurationException;
import openlr.map.mockdb.MockedMapDatabase;
import openlr.rawLocRef.RawLocationReference;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class LongPointAlongTest {

    /**
     * Test long line and offset poi.
     */
    @Test(groups = {"broken"})
    public final void testLongLineAndOffsetPOI() {
        try {
            MockedMapDatabase mdb = new MockedMapDatabase("ExtraLongLineMap.xml", false);
            Location loc = LocationFactory.createPointAlongLineLocation(
                    "longLine", mdb.getLine(26), 16000);

            LocationReferenceHolder locationRef = null;
            try {
                OpenLREncoder encoder = new OpenLREncoder();
                OpenLREncoderParameter params = new OpenLREncoderParameter.Builder().with(mdb).buildParameter();
                locationRef = encoder.encodeLocation(params, loc);
            } catch (OpenLRProcessingException e) {
                fail("Encoding location failed with exception: "
                        + e.getErrorCode(), e);
            }
            assertTrue(locationRef.isValid(),
                    "Encoding delivered invalid location.");
            assertEquals(locationRef.getLocationType(),
                    LocationType.POINT_ALONG_LINE);
            assertEquals(locationRef.getNrOfLRPs(), 2);
            Assert.assertNotNull(locationRef.getRawLocationReferenceData());
            RawLocationReference rawLocRef = locationRef.getRawLocationReferenceData();

            assertEquals(rawLocRef.getOffsets().getPositiveOffset(15000), 1000);
            List<? extends LocationReferencePoint> points = rawLocRef.getLocationReferencePoints();
            Assert.assertNotNull(points);
            assertEquals(points.size(), 2);
            LocationReferencePoint lrp1 = points.get(0);
            assertEquals(lrp1.getLongitudeDeg(), 8.87777);
            assertEquals(lrp1.getLatitudeDeg(), 50.93595);

            LocationReferencePoint lrp2 = points.get(1);
            assertEquals(lrp2.getLongitudeDeg(), 9.09091);
            assertEquals(lrp2.getLatitudeDeg(), 50.93419);
        } catch (InvalidConfigurationException e) {
            fail("Unexpected exception!", e);
        } catch (InvalidMapDataException e) {
            fail("Unexpected exception!", e);
        }
    }

    /**
     * Tests a case such a long line that forces the creation of two (!) additional intermediates on
     * the line between the two LRPs at start and end.
     */
    @Test
    public final void testVeryLongLineZeroOffset()
            throws InvalidMapDataException, InvalidConfigurationException,
            OpenLRProcessingException {

        MockedMapDatabase mdb = new MockedMapDatabase("ExtraLongLineMap.xml", false);
        Line line26 = mdb.getLine(26);
        Location loc = LocationFactory.createPointAlongLineLocation(
                "longLine", line26, 0);

        OpenLREncoder encoder = new OpenLREncoder();
        OpenLREncoderParameter params = new OpenLREncoderParameter.Builder()
                .with(mdb).buildParameter();
        LocationReferenceHolder locationRef = encoder.encodeLocation(params,
                loc);

        // expected is the first intermediate on the line after 15.000 m
        GeoCoordinates expectedLRP2 = GeoCoordinatesImpl.newGeoCoordinatesUnchecked(8.87777, 50.93595);
        verifyEncodedLocation(locationRef, line26.getStartNode()
                .getGeoCoordinates(), expectedLRP2, 0);
    }


    /**
     * Test long line and offset line.
     */
    @Test
    public final void testLongLineAndOffsetLine() {
        try {
            MockedMapDatabase mdb = new MockedMapDatabase("ExtraLongLineMap.xml", false);
            List<Line> lines = new ArrayList<Line>();
            lines.add(mdb.getLine(26));
            Location loc = LocationFactory.createLineLocationWithOffsets(
                    "longLine", lines, 16000, 15500);

            LocationReferenceHolder locationRef = null;
            try {
                OpenLREncoder encoder = new OpenLREncoder();
                OpenLREncoderParameter params = new OpenLREncoderParameter.Builder().with(mdb).buildParameter();
                locationRef = encoder.encodeLocation(params, loc);
            } catch (OpenLRProcessingException e) {
                fail("Encoding location failed with exception: "
                        + e.getErrorCode(), e);
            }
            assertTrue(locationRef.isValid(),
                    "Encoding delivered invalid location.");
            assertEquals(locationRef.getLocationType(),
                    LocationType.LINE_LOCATION);
            assertEquals(locationRef.getNrOfLRPs(), 2);
            Assert.assertNotNull(locationRef.getRawLocationReferenceData());
            RawLocationReference rawLocRef = locationRef.getRawLocationReferenceData();

            assertEquals(rawLocRef.getOffsets().getPositiveOffset(15000), 1000);
            assertEquals(rawLocRef.getOffsets().getNegativeOffset(15000), 10500);
            List<? extends LocationReferencePoint> points = rawLocRef.getLocationReferencePoints();
            Assert.assertNotNull(points);
            LocationReferencePoint lrp1 = points.get(0);
            assertEquals(lrp1.getLongitudeDeg(), 8.87777);
            assertEquals(lrp1.getLatitudeDeg(), 50.93595);

            LocationReferencePoint lrp2 = points.get(1);
            assertEquals(lrp2.getLongitudeDeg(), 9.09091);
            assertEquals(lrp2.getLatitudeDeg(), 50.93419);

        } catch (InvalidConfigurationException e) {
            fail("Unexpected exception!", e);
        }
    }

    /**
     * Tests a case where a point along line location with offset zero (!) is expanded at the start.
     * The potential problem is that because of offset zero the max length check for the 15 km doesn't
     * take the case into account that it is exactly reached while expanding (currentLegth == 15.000).
     * In this case the 15000 km are reached immediately with the first expansion line.
     *
     * Map like this (Node: "Nx", POI line: "==", offset: 0)
     * [N2]-------15000m----------[N3]==200m==[N4]--------14800-----[N5]
     * Should result in:
     * [N2]-------15000m--------[LRP1]==200m==[N4]------14800-----[LRP2]
     */
    @Test
    public final void testPointAlongExpansionBackwardsImmediatelyMaxDist() throws InvalidMapDataException, InvalidConfigurationException, OpenLRProcessingException {

        MockedMapDatabase mdb = new MockedMapDatabase(
                "LongPathExtensionBackwards.xml", true);
        Location loc = LocationFactory.createPointAlongLineLocation(
                "pleaseExtendMe", mdb.getLine(3), 0);


        OpenLREncoder encoder = new OpenLREncoder();
        OpenLREncoderParameter params = new OpenLREncoderParameter.Builder().with(mdb).buildParameter();
        LocationReferenceHolder locationRef = encoder.encodeLocation(params, loc);

        verifyEncodedLocation(locationRef, mdb.getNode(3)
                .getGeoCoordinates(), mdb.getNode(5).getGeoCoordinates(), 0);

    }

    /**
     * Tests a case where a point along line location with offset zero (!) is expanded at the start. Internally the expansion is later removed again by our mechanism that reduces point location references to two LRPS.
     * The potential problem is that because of offset zero the max length check for the 15 km doesn't
     * take the case into account that it is exactly reached while expanding (currentLegth == 15.000).
     * Map like this (Node: "Nx", POI line: "==", offset 0)
     * [N3]--200m--[N4]-------14800m--------[N5]==200m==[N6]
     * Should result in:
     * [N3]--200m--[N4]-------14800m------[LRP1]==200m==[LRP2]
     */
    @Test
    public final void testPointAlongExpansionBackwards()
            throws InvalidMapDataException, InvalidConfigurationException,
            OpenLRProcessingException {

        MockedMapDatabase mdb = new MockedMapDatabase(
                "LongPathExtensionBackwards.xml", true);
        Location loc = LocationFactory.createPointAlongLineLocation(
                "pleaseExtendMe", mdb.getLine(5), 0);

        OpenLREncoder encoder = new OpenLREncoder();
        OpenLREncoderParameter params = new OpenLREncoderParameter.Builder()
                .with(mdb).buildParameter();
        LocationReferenceHolder locationRef = encoder.encodeLocation(params,
                loc);

        verifyEncodedLocation(locationRef, mdb.getNode(5)
                .getGeoCoordinates(), mdb.getNode(6).getGeoCoordinates(), 0);
    }

    /**
     * Test similar to {@link #testPointAlongExpansionBackwards()} but with offset.
     * Map like this (Node: "Nx", POI line: "==", pos. offset: "x")
     * [N4]-------14800m--------[N5]==200m==x=[N6]
     * Should result in:
     * [LRP1]-------14800m--------[N5]==200m==x=[LRP2]
     */
    @Test
    public final void testPointAlongExpansionBackwardsWithOffset()
            throws InvalidMapDataException, InvalidConfigurationException,
            OpenLRProcessingException {

        MockedMapDatabase mdb = new MockedMapDatabase(
                "LongPathExtensionBackwards.xml", true);

        // offset close to the end of the POI line
        int poffOnLine5 = 199;
        Location loc = LocationFactory.createPointAlongLineLocation(
                "pleaseExtendMe", mdb.getLine(5), poffOnLine5);

        OpenLREncoder encoder = new OpenLREncoder();
        OpenLREncoderParameter params = new OpenLREncoderParameter.Builder()
                .with(mdb).buildParameter();
        LocationReferenceHolder locationRef = encoder.encodeLocation(params,
                loc);

        verifyEncodedLocation(locationRef, mdb.getNode(4)
                .getGeoCoordinates(), mdb.getNode(6).getGeoCoordinates(), mdb
                .getLine(4).getLineLength() + poffOnLine5);
    }

    /**
     * Performs the verification of the encoded location.
     * @param locationRef The location reference data
     * @param expectedLrp1 The expected location of LRP1
     * @param expectedLrp2 The expected location of LRP 2
     * @param expectedOffset The expected offset
     */
    private void verifyEncodedLocation(final LocationReferenceHolder locationRef,
                                       final GeoCoordinates expectedLrp1, final GeoCoordinates expectedLrp2,
                                       final int expectedOffset) {

        assertTrue(locationRef.isValid(),
                "Encoding delivered invalid location.");
        assertEquals(locationRef.getLocationType(),
                LocationType.POINT_ALONG_LINE);
        assertEquals(locationRef.getNrOfLRPs(), 2);
        Assert.assertNotNull(locationRef.getRawLocationReferenceData());
        RawLocationReference rawLocRef = locationRef
                .getRawLocationReferenceData();

        List<? extends LocationReferencePoint> points = rawLocRef
                .getLocationReferencePoints();

        assertEquals(points.size(), 2);

        LocationReferencePoint lrp1 = points.get(0);
        assertEquals(lrp1.getLongitudeDeg(), expectedLrp1.getLongitudeDeg());
        assertEquals(lrp1.getLatitudeDeg(), expectedLrp1.getLatitudeDeg());

        LocationReferencePoint lrp2 = points.get(1);
        assertEquals(lrp2.getLongitudeDeg(), expectedLrp2.getLongitudeDeg());
        assertEquals(lrp2.getLatitudeDeg(), expectedLrp2.getLatitudeDeg());

        assertEquals(
                rawLocRef.getOffsets().getPositiveOffset(
                        lrp1.getDistanceToNext()), expectedOffset);
    }

}
