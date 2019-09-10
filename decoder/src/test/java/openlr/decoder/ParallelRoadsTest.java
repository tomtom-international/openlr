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
package openlr.decoder;

import openlr.LocationReferencePoint;
import openlr.LocationType;
import openlr.Offsets;
import openlr.OpenLRProcessingException;
import openlr.location.Location;
import openlr.map.FormOfWay;
import openlr.map.FunctionalRoadClass;
import openlr.map.Line;
import openlr.map.mockdb.InvalidConfigurationException;
import openlr.map.mockdb.MockedMapDatabase;
import openlr.rawLocRef.RawLineLocRef;
import openlr.rawLocRef.RawLocationReference;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.testng.Assert.*;

/**
 * This class tests cases with decoding locations around a part of the map with
 * parallel roads.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class ParallelRoadsTest {

    /**
     * The identifier of line 1 of the underlying map.
     */
    private static final long LINE_1 = 1;
    /**
     * The identifier of line 2 of the underlying map.
     */
    private static final long LINE_2 = 2;
    /**
     * The identifier of line 3 of the underlying map.
     */
    private static final long LINE_3 = 3;
    /**
     * The mocked map database. Its structure looks as follows:
     *        ____
     * N1---N2____N3---N4
     *
     * Each connection is drivable in both directions.
     */
    private final MockedMapDatabase mdb;

    /**
     * Initializes the map database.
     *
     * @throws InvalidConfigurationException
     *             If an error occurs initializing the map database from the
     *             configuration.
     */
    public ParallelRoadsTest() throws InvalidConfigurationException {
        mdb = new MockedMapDatabase("ParallelRoadsMap.xml", false);
        mockBasics(mdb.getMockery());
    }

    /**
     * Tests the encoding of a location with two parallel roads in the middle.
     *        ____
     * N1---N2____N3---N4 (Path: N1, N2, N3, N4)
     */
    @Test
    public final void testParallelRoads() {

        RawLocationReference locRef = mockLineLocation("locMiddle",
                Lrp.LRP_MIDDLE_1, Lrp.LRP_MIDDLE_2);

        runTest(locRef, LINE_1, LINE_2, LINE_3);
    }

    /**
     * Tests the encoding of a location with only two parallel roads.
     *   ____
     * N2____N3 (Path: N2, N3)
     */
    @Test
    public final void testParallelRoadsOnly() {

        RawLocationReference locRef = mockLineLocation("locOnly",
                Lrp.LRP_ONLY_1, Lrp.LRP_ONLY_2);
        runTest(locRef, LINE_2);
    }

    /**
     * Tests the encoding of a location with two parallel roads at the end.
     *        ____
     * N1---N2____N3 (Path: N1, N2, N3)
     */
    @Test
    public final void testParallelRoadsAtEnd() {
        RawLocationReference locRef = mockLineLocation("locEnd", Lrp.LRP_END_1,
                Lrp.LRP_END_2);
        runTest(locRef, LINE_1, LINE_2);
    }

    /**
     * Executes the encoding of the given location. Checks validity and number
     * of expected LRP afterwards.
     *
     * @param lrf
     *            The location reference to decode.
     * @param expectedLocationLines
     *            The IDs of the expected location lines in the expected order.
     */
    private void runTest(final RawLocationReference lrf,
                         final long... expectedLocationLines) {

        Location location = null;
        try {
            OpenLRDecoder decoder = new OpenLRDecoder();
            OpenLRDecoderParameter parameter = new OpenLRDecoderParameter.Builder().with(mdb).with(TestData.getInstance()
                    .getProperties()).buildParameter();
            location = decoder.decodeRaw(parameter, lrf);
        } catch (OpenLRProcessingException e) {
            fail("Encoding location failed with exception: " + e.getErrorCode(),
                    e);
        }
        assertTrue(location.isValid(),
                "Encoding delivered invalid location.");

        List<? extends Line> locLines = location.getLocationLines();
        assertEquals(locLines.size(), expectedLocationLines.length);

        int i = 0;
        for (Line line : locLines) {
            assertEquals(line.getID(), expectedLocationLines[i++]);
        }
    }

    /**
     * Delivers a mocked location reference describing a line location with
     * offsets > location length.
     *
     * @param id
     *            The identifier of the location reference.
     * @param lrps
     *            An array of {@link Lrp} containing the data for the location
     *            reference points of the {@link RawLocationReference}.
     * @return The location reference object.
     */
    private RawLocationReference mockLineLocation(final String id,
                                                  final Lrp... lrps) {

        final Mockery mockery = mdb.getMockery();

        final RawLocationReference mockedLocRef = new RawLineLocRef(id, mockLRPs(mockery, lrps), getMockedOffsets(new int[]{0, 0}, mockery));
        return mockedLocRef;
    }

    /**
     * Delivers the mocked offsets for the line location example.
     *
     * @param offsets
     *            The offset values to set. An array in the order of {positive
     *            offset, negative offset}
     * @return The mocked offsets for the line location example.
     */
    private Offsets getMockedOffsets(final int[] offsets, final Mockery context) {

        final Offsets mOff = context.mock(Offsets.class,
                String.valueOf(UUID.randomUUID()));

        context.checking(new Expectations() {
            {
                allowing(mOff).hasPositiveOffset();
                will(returnValue(offsets[0] > 0));
            }
        });
        context.checking(new Expectations() {
            {
                allowing(mOff).hasNegativeOffset();
                will(returnValue(offsets[1] > 0));
            }
        });
        context.checking(new Expectations() {
            {
                allowing(mOff).getPositiveOffset(with(any(Integer.class)));
                will(returnValue(offsets[0]));
            }
        });
        context.checking(new Expectations() {
            {
                allowing(mOff).getNegativeOffset(with(any(Integer.class)));
                will(returnValue(offsets[1]));
            }
        });

        return mOff;
    }

    /**
     * Mocks a list of LocationReferencePoint.
     *
     * @param mockery
     *            The mock creator.
     * @param inputLRPs
     *            The data for the LRPs.
     * @return the list of mocked LRPs.
     */
    private List<? extends LocationReferencePoint> mockLRPs(
            final Mockery mockery, final Lrp... inputLRPs) {

        List<LocationReferencePoint> result = new ArrayList<LocationReferencePoint>(
                inputLRPs.length);

        for (int i = 0; i < inputLRPs.length; i++) {

            final Lrp lrp = inputLRPs[i];

            final LocationReferencePoint mLrp = mockery.mock(
                    LocationReferencePoint.class, String.valueOf(lrp.name()));

            mockery.checking(new Expectations() {
                {
                    allowing(mLrp).getLongitudeDeg();
                    will(returnValue(lrp.longitude));
                }
            });
            mockery.checking(new Expectations() {
                {
                    allowing(mLrp).getLatitudeDeg();
                    will(returnValue(lrp.latitude));
                }
            });

            mockery.checking(new Expectations() {
                {
                    allowing(mLrp).getBearing();
                    will(returnValue(lrp.bearing));
                }
            });
            mockery.checking(new Expectations() {
                {
                    allowing(mLrp).getDistanceToNext();
                    will(returnValue(lrp.distanceToNext));
                }
            });
            final int seqNr = i;
            mockery.checking(new Expectations() {
                {
                    allowing(mLrp).getSequenceNumber();
                    will(returnValue(seqNr));
                }
            });

            final boolean isLast = i == inputLRPs.length - 1;
            mockery.checking(new Expectations() {
                {
                    allowing(mLrp).isLastLRP();
                    will(returnValue(isLast));
                }
            });

            result.add(mLrp);
        }

        return result;
    }

    /**
     * Mocks reused method call for groups of involved objects.
     * @param mockery The mocking context.
     */
    private void mockBasics(final Mockery mockery) {

        // For this test every LRP has this common parameters
        mockery.checking(new Expectations() {
            {
                allowing(any(LocationReferencePoint.class)).method("getLocationType");
                will(returnValue(LocationType.LINE_LOCATION));
            }
        });
        mockery.checking(new Expectations() {
            {
                allowing(any(LocationReferencePoint.class)).method("getFRC");
                will(returnValue(FunctionalRoadClass.FRC_5));
            }
        });
        mockery.checking(new Expectations() {
            {
                allowing(any(LocationReferencePoint.class)).method("getFOW");
                will(returnValue(FormOfWay.MULTIPLE_CARRIAGEWAY));
            }
        });
        mockery.checking(new Expectations() {
            {
                allowing(any(LocationReferencePoint.class)).method("getLfrc");
                will(returnValue(FunctionalRoadClass.FRC_5));
            }
        });

        // every raw location reference will return the empty offsets
        mockery.checking(new Expectations() {
            {
                allowing(any(RawLocationReference.class)).method("getOffsets");
                will(returnValue(getEmptyMockedOffsets(mockery)));
            }
        });

    }

    /**
     * Mocks {@link Offsets} of value 0.
     *
     * @param mockery
     *            The mocking context.
     * @return The offset objects.
     */
    private Offsets getEmptyMockedOffsets(final Mockery mockery) {

        final Offsets mOff = mockery.mock(Offsets.class,
                String.valueOf(UUID.randomUUID()));

        mockery.checking(new Expectations() {
            {
                allowing(mOff).getPositiveOffset(with(any(Integer.class)));
                will(returnValue(0));
            }
        });
        mockery.checking(new Expectations() {
            {
                allowing(mOff).getNegativeOffset(with(any(Integer.class)));
                will(returnValue(0));
            }
        });

        return mOff;
    }

    /**
     * An enumeration of constant LRPs that provide the data for the input
     * location references. Their values correspond to the result of the
     * encoder test of parallel lines.
     */
    private enum Lrp {
        /**
         * LRP 1 of the example containing parallel roads in the middle of the
         * route.
         */
        LRP_MIDDLE_1(40.75629, -73.96526, 280.20506539980875, 1800),

        /**
         * LRP 2 of the example containing parallel roads in the middle of the
         * route.
         */
        LRP_MIDDLE_2(40.75862, -73.9708, 129.5266356017972, -1),

        /**
         * LRP 1 of the example containing only parallel roads in the route.
         */
        LRP_ONLY_1(40.75702, -73.96696, 352.8046369119083, 750),

        /**
         * LRP 2 of the example containing only parallel roads in the route.
         */
        LRP_ONLY_2(40.75797, -73.96922, 92.69944942500938, -1),

        /**
         * LRP 1 of the example containing parallel roads at the end of the
         * route.
         */
        LRP_END_1(40.75629, -73.96526, 280.20506539980875, 1250),

        /**
         * LRP 2 of the example containing parallel roads at the end of the
         * route.
         */
        LRP_END_2(40.75797, -73.96922, 92.69944942500938, -1);

        /** The bearing of the line referenced by the LRP. */
        private final double bearing;

        /** The distance to the next LRP along the shortest-path. */
        private final int distanceToNext;

        /** The longitude coordinate. */
        private final double longitude;

        /** The latitude coordinate. */
        private final double latitude;

        /**
         * @param latitudeValue
         *            The latitude
         * @param longitudeValue
         *            The longitude
         * @param bearingValue
         *            TThe bearing
         * @param dnpValue
         *            The DNP
         */
        private Lrp(final double latitudeValue, final double longitudeValue,
                    final double bearingValue, final int dnpValue) {
            this.longitude = longitudeValue;
            this.latitude = latitudeValue;
            this.bearing = bearingValue;
            this.distanceToNext = dnpValue;
        }
    }
}
