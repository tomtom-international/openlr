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
package openlr.decoder;

import openlr.LocationReferencePoint;
import openlr.Offsets;
import openlr.OpenLRProcessingException;
import openlr.location.data.Orientation;
import openlr.location.data.SideOfRoad;
import openlr.map.*;
import openlr.map.mockdb.InvalidConfigurationException;
import openlr.map.mockdb.MockedMapDatabase;
import openlr.properties.OpenLRPropertiesReader;
import openlr.rawLocRef.*;
import org.apache.commons.configuration.Configuration;
import org.jmock.Expectations;
import org.jmock.Mockery;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

import static org.testng.Assert.fail;

/**
 * An utility class holding prepared/mocked test data.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public final class TestData {

    /**
     * The default map configuration.
     *
     * @deprecated This configuration represents the ported configuration from
     *             the Java code to the XML file after introduction of the
     *             configurable database mocking. For future tests please create
     *             {@link MockedMapDatabase} instances with smaller map
     *             configurations.
     */
    @Deprecated
    public static final String DEFAULT_MAP_DB_CONFIG = "DefaultMapDatabase.xml";
    /** The point on the line of the white paper example for point along line. */
    public static final Point2D.Double PAL_POINT = new Point2D.Double(6.12821,
            49.60572);
    /** The access point of the white paper example for POI with access point. */
    public static final Point2D.Double PWA_ACCESS_POINT = PAL_POINT;
    /** The decoder properties file. */
    static final File DECODER_PROPERTIES = new File(
            "src/test/resources/OpenLR-Decoder-Properties.xml");
    /**
     * An array of expected offset values in the order {positive offset,
     * negative offset}
     */
    static final int[] OFFSETS_POINT_ALONG_LINE = {28, 0};
    /**
     * The LPSs that are input of the decoding of the line location example in
     * the correct sequence.
     */
    private static final InputLrp[] INPUT_LRPS_LINE_LOCATION = {InputLrp.LRP1,
            InputLrp.LRP2, InputLrp.LRP3};
    /**
     * The POI in the point with access point example, x = longitude, y =
     * latitude.
     */
    private static final Point2D.Double POINT_WITH_ACCESS_POINT_POI = new Point2D.Double(
            6.12699, 49.60728);
    /**
     * The GEO-coordinate in the GEO-coordinate example, x = longitude, y =
     * latitude.
     */
    private static final Point2D.Double GEO_COORDINATE = new Point2D.Double(
            6.12699, 49.60728);
    /**
     * The LPSs that are input of the decoding of the point along line location
     * example in the correct sequence.
     */
    private static final InputLrp[] INPUT_LRPS_POINT_ALONG_LINE = {
            InputLrp.LRP4, InputLrp.LRP5};
    /**
     * The LPSs that are input of the decoding of the line location example in
     * the correct sequence.
     */
    private static final InputLrp[] INPUT_LRPS_NOT_CONNECTED_PWA = {
            InputLrp.LRP1, InputLrp.LRP3};
    /** The input LRPs of a point with access point test. */
    private static final InputLrp[] INPUT_LRPS_PWA = {InputLrp.LRP4,
            InputLrp.LRP5};
    /** The Constant EXP_START_IDS. */
    private static final long[] EXP_START_IDS = {2};
    /** The Constant notConnectedIDs. */
    private static final long[] NOTCONNECTEDIDS = {4, 6, 10, 16, 18};
    /** The Constant invalidFRCIDs. */
    private static final long[] INVALIDFRCIDS = {9};
    /** The Constant NEW_NEGOFF. */
    private static final int NEW_NEGOFF = 0;
    /** The Constant NEW_POSOFF. */
    private static final int NEW_POSOFF = 150;
    /** The offset that exceed the location length. */
    private static final int[] EXCEEDING_OFFSETS_LINE_LOC = new int[]{1000,
            500};
    /** The Constant ROUTELENGTH1. */
    private static final int ROUTELENGTH1 = 411;
    /** The Constant ROUTELENGTH2. */
    private static final int ROUTELENGTH2 = 274;
    /** The Constant INTERMEDIATEPOS. */
    private static final int INTERMEDIATEPOS = 3;
    /**
     * An array of expected offset values in the order {positive offset,
     * negative offset}
     */
    private static final int[] OFFSETS_LINE_LOCATION = {150, 0};
    /** The Constant NODEDSEG1. */
    private static final long NODEDSEG1 = 4;
    /** The Constant NODEDSEG2. */
    private static final long NODEDSEG2 = 3;
    /** The Constant INTERMEDIATE. */
    private static final long INTERMEDIATE = 14;

    // /** The valid location. */
    // private ArrayList<Line> validLocation = new ArrayList<Line>();
    /** The unique instance of this class. */
    private static TestData instance = new TestData();
    /**
     * The default mocked map database.
     *
     * @deprecated This global instance of map database shouldn't be used any
     *             more. Our new approach is to generate smaller instances of
     *             mocked map databases via configuration for each single test.
     * @see MockedMapDatabase
     */
    @Deprecated
    private MapDatabase defaultMockedMapDB;
    /**
     * The mocking context.
     */
    private Mockery context = new Mockery();
    /** The valid location expand end. */
    private ArrayList<Line> validLocationExpandStart = new ArrayList<Line>();
    /** The not connected location. */
    private ArrayList<Line> notConnectedLocation = new ArrayList<Line>();
    /** The invalid frc location. */
    private ArrayList<Line> invalidFRCLocation = new ArrayList<Line>();
    /** The valid node. */
    private Node validNode = null;

    /** The invalid node2. */
    private Node invalidNode2 = null;

    /** The invalid node4. */
    private Node invalidNode4 = null;

    /** The intermediate dseg. */
    private Line intermediateDseg = null;

    /** The properties for the decoder. */
    private Configuration properties = null;
    /** Stores repeatedly requested mocked location reference objects. */
    private Map<String, RawLocationReference> mocks = new HashMap<String, RawLocationReference>();

    /**
     * Instantiates a new test data.
     */
    private TestData() {
        initData();
    }

    /**
     * @return The unique instance of this class.
     */
    public static TestData getInstance() {
        return instance;
    }

    /**
     * Inits the data.
     */
    private void initData() {

        defaultMockedMapDB = getMapDatabase();

        try {
            properties = OpenLRPropertiesReader.loadPropertiesFromStream(
                    new FileInputStream(DECODER_PROPERTIES), true);
        } catch (OpenLRProcessingException e) {
            fail("Reading encoder properties failed.");
        } catch (FileNotFoundException e) {
            fail("cannot find properties file", e);
        }
        generateLocations();
        generateNodes();
    }

    /**
     * Generate locations.
     */
    private void generateLocations() {
        for (int i = 0; i < EXP_START_IDS.length; i++) {
            validLocationExpandStart.add(defaultMockedMapDB
                    .getLine(EXP_START_IDS[i]));
        }
        for (int i = 0; i < NOTCONNECTEDIDS.length; i++) {
            notConnectedLocation.add(defaultMockedMapDB
                    .getLine(NOTCONNECTEDIDS[i]));
        }
        for (int i = 0; i < INVALIDFRCIDS.length; i++) {
            invalidFRCLocation
                    .add(defaultMockedMapDB.getLine(INVALIDFRCIDS[i]));
        }
        intermediateDseg = defaultMockedMapDB.getLine(INTERMEDIATE);
    }

    /**
     * Generate nodes.
     */
    private void generateNodes() {
        validNode = defaultMockedMapDB.getLine(NODEDSEG1).getEndNode();
        invalidNode2 = defaultMockedMapDB.getLine(NODEDSEG1).getStartNode();
        invalidNode4 = defaultMockedMapDB.getLine(NODEDSEG2).getEndNode();
    }

    /**
     * Gets the nr expanded end.
     *
     * @return the nr expanded end
     */
    public int getNrExpandedEnd() {
        return validLocationExpandStart.size();
    }

    /**
     * Gets the default mocked map database.
     *
     * @return the map database
     * @deprecated This global instance of map database shouldn't be used any
     *             more. Our new approach is to generate smaller instances of
     *             mocked map databases via configuration for each single test.
     *             For future tests please create {@link MockedMapDatabase}
     *             instances with smaller map configurations.
     * @see MockedMapDatabase
     */
    @Deprecated
    public MapDatabase getMapDatabase() {

        if (defaultMockedMapDB == null) {
            try {
                defaultMockedMapDB = new MockedMapDatabase(
                        DEFAULT_MAP_DB_CONFIG, false);
            } catch (InvalidConfigurationException e) {
                throw new RuntimeException(
                        "Unexpected exception during mock build-up from config "
                                + "file " + DEFAULT_MAP_DB_CONFIG, e);
            }
        }
        return defaultMockedMapDB;
    }

    /**
     * Gets the tuning.
     *
     * @return the tuning
     */
    public Configuration getProperties() {
        return properties;
    }

    /**
     * Gets the valid node.
     *
     * @return the valid node
     */
    public Node getValidNode() {
        return validNode;
    }

    /**
     * Gets the invalid node2.
     *
     * @return the invalid node2
     */
    public Node getInvalidNode2() {
        return invalidNode2;
    }

    /**
     * Gets the invalid node4.
     *
     * @return the invalid node4
     */
    public Node getInvalidNode4() {
        return invalidNode4;
    }

    /**
     * Gets the intermediate dseg.
     *
     * @return the intermediate dseg
     */
    public Line getIntermediate() {
        return intermediateDseg;
    }

    /**
     * Gets the new neg off.
     *
     * @return the new neg off
     */
    public int getNewNegOff() {
        return NEW_NEGOFF;
    }

    /**
     * Gets the new pos off.
     *
     * @return the new pos off
     */
    public int getNewPosOff() {
        return NEW_POSOFF;
    }

    /**
     * Gets the rout length1.
     *
     * @return the rout length1
     */
    public int getRoutLength1() {
        return ROUTELENGTH1;
    }

    /**
     * Gets the rout length2.
     *
     * @return the rout length2
     */
    public int getRoutLength2() {
        return ROUTELENGTH2;
    }

    /**
     * Gets the intermediate pos.
     *
     * @return the intermediate pos
     */
    public int getIntermediatePos() {
        return INTERMEDIATEPOS;
    }

    /**
     * Delivers a raw location reference describing the line location decoder
     * example from the White paper.
     *
     * @return The line location reference.
     */
    public RawLocationReference getWhitepaperLineLocationReference() {

        final String key = "WPLine";
        RawLocationReference result = mocks.get(key);

        if (result == null) {
            final RawLocationReference mockedLocRef = new RawLineLocRef(key,
                    getLRPs(INPUT_LRPS_LINE_LOCATION),
                    getNewMockedOffsets(OFFSETS_LINE_LOCATION));
            mocks.put(key, mockedLocRef);
            result = mockedLocRef;
        }

        return result;
    }

    public RawLocationReference getClosedLineLocationReference1() {

        final String key = "closedLine1";
        RawLocationReference result = mocks.get(key);

        if (result == null) {
            final RawLocationReference mockedLocRef = new RawClosedLineLocRef(
                    key, getLRPs(new InputLrp[]{InputLrp.LRP_CLOSEDLINE_1,
                    InputLrp.LRP_CLOSEDLINE_2}));
            mocks.put(key, mockedLocRef);
            result = mockedLocRef;
        }
        return result;
    }

    public RawLocationReference getClosedLineLocationReference2() {

        final String key = "closedLine2";
        RawLocationReference result = mocks.get(key);

        if (result == null) {
            final RawLocationReference mockedLocRef = new RawClosedLineLocRef(
                    key, getLRPs(new InputLrp[]{InputLrp.LRP_CLOSEDLINE_3,
                    InputLrp.LRP_CLOSEDLINE_4,
                    InputLrp.LRP_CLOSEDLINE_5,
                    InputLrp.LRP_CLOSEDLINE_6,
                    InputLrp.LRP_CLOSEDLINE_7,
                    InputLrp.LRP_CLOSEDLINE_8,
                    InputLrp.LRP_CLOSEDLINE_9,
                    InputLrp.LRP_CLOSEDLINE_10,
                    InputLrp.LRP_CLOSEDLINE_11}));
            mocks.put(key, mockedLocRef);
            result = mockedLocRef;
        }
        return result;
    }

    /**
     * Delivers a mocked location reference describing a line location with
     * offsets > location length.
     *
     * @return The location reference object.
     */
    public RawLocationReference getLineLocationExceedingOffsets() {

        final String key = "lineExceedOffsets";
        RawLocationReference result = mocks.get(key);

        if (result == null) {
            final RawLocationReference mockedLocRef = new RawLineLocRef(key,
                    getLRPs(INPUT_LRPS_LINE_LOCATION),
                    getNewMockedOffsets(EXCEEDING_OFFSETS_LINE_LOC));
            mocks.put(key, mockedLocRef);
            result = mockedLocRef;
        }

        return result;
    }

    /**
     * Delivers a raw location reference describing the point-along-line-example
     * for the decoder from the White paper.
     *
     * @return The point along line location reference.
     */
    public RawLocationReference getWhitepaperPointAlongLineLocationReference() {

        final String key = "WPPal";
        RawLocationReference result = mocks.get(key);

        if (result == null) {

            List<? extends LocationReferencePoint> lrps = getLRPs(INPUT_LRPS_POINT_ALONG_LINE);
            RawLocationReference mockedLocRef = new RawPointAlongLocRef(key,
                    lrps.get(0), lrps.get(1),
                    getNewMockedOffsets(OFFSETS_POINT_ALONG_LINE),
                    SideOfRoad.LEFT, Orientation.NO_ORIENTATION_OR_UNKNOWN);
            mocks.put(key, mockedLocRef);
            result = mockedLocRef;
        }

        return result;
    }

    /**
     * Delivers a raw location reference describing the point with access point
     * example for the decoder from the White paper.
     *
     * @return The point with access point location reference.
     * @throws InvalidMapDataException
     */
    public RawLocationReference getWhitepaperPOIWithAccessPointLocation()
            throws InvalidMapDataException {

        final String key = "WPPoi";
        RawLocationReference result = mocks.get(key);

        if (result == null) {
            List<? extends LocationReferencePoint> lrps = getLRPs(INPUT_LRPS_POINT_ALONG_LINE);
            RawLocationReference mockedLocRef = new RawPoiAccessLocRef(key,
                    lrps.get(0), lrps.get(1),
                    getNewMockedOffsets(OFFSETS_POINT_ALONG_LINE),
                    getMockedGeoCoordsForPointWithAccessPoint("POI_WP"),
                    SideOfRoad.LEFT, Orientation.NO_ORIENTATION_OR_UNKNOWN);
            mocks.put(key, mockedLocRef);
            result = mockedLocRef;
        }

        return result;
    }

    /**
     * Delivers a raw location reference describing the GEO-coordinate example
     * for the decoder from the White paper.
     *
     * @return The GEO-coordinate location reference.
     * @throws InvalidMapDataException
     */
    public RawLocationReference getWhitepaperGeoCoordinateLocation()
            throws InvalidMapDataException {

        final String key = "WPGeo";
        RawLocationReference result = mocks.get(key);

        if (result == null) {
            final RawLocationReference mockedLocRef = new RawGeoCoordLocRef(
                    key, getMockedGeoCoordsForGeoCoordinateExample());
            mocks.put(key, mockedLocRef);
            result = mockedLocRef;
        }

        return result;
    }

    /**
     * Delivers an mocked example of a POI with access point location with not
     * properly connected lines.
     *
     * @return The mocked location reference.
     */
    public RawLocationReference getNotConnectedPWA() {

        final String key = "notConnectedPAL";
        RawLocationReference result = mocks.get(key);

        if (result == null) {
            List<? extends LocationReferencePoint> lrps = getLRPs(INPUT_LRPS_NOT_CONNECTED_PWA);
            final RawLocationReference mockedLocRef = new RawPointAlongLocRef(
                    key, lrps.get(0), lrps.get(1),
                    getNewMockedOffsets(new int[]{0, 0}), SideOfRoad.LEFT,
                    Orientation.NO_ORIENTATION_OR_UNKNOWN);
            mocks.put(key, mockedLocRef);
            result = mockedLocRef;
        }

        return result;
    }

    /**
     * Delivers an mocked example of a POI with access point location with not
     * properly connected lines.
     *
     * @return The mocked location reference.
     * @throws InvalidMapDataException
     */
    public RawLocationReference getPWANoOffsets()
            throws InvalidMapDataException {

        final String key = "PWANoOff";
        RawLocationReference result = mocks.get(key);

        if (result == null) {
            List<? extends LocationReferencePoint> lrps = getLRPs(INPUT_LRPS_PWA);
            RawLocationReference mockedLocRef = new RawPoiAccessLocRef(key,
                    lrps.get(0), lrps.get(1), getNewMockedOffsets(new int[]{0,
                    0}),
                    getMockedGeoCoordsForPointWithAccessPoint("POI_NO_OFF"),
                    SideOfRoad.LEFT, Orientation.NO_ORIENTATION_OR_UNKNOWN);
            mocks.put(key, mockedLocRef);
            result = mockedLocRef;
        }

        return result;
    }

    /**
     * Delivers the mocked {@link GeoCoordinates} for the point with access
     * point location example.
     *
     * @param mockName
     *            The name the mocked object shall be registered with.
     *
     * @return The mocked {@link GeoCoordinates}.
     * @throws InvalidMapDataException
     */
    private GeoCoordinates getMockedGeoCoordsForPointWithAccessPoint(
            final String mockName) throws InvalidMapDataException {

        final GeoCoordinates mockedCoords = new GeoCoordinatesImpl(
                POINT_WITH_ACCESS_POINT_POI.x, POINT_WITH_ACCESS_POINT_POI.y);
        return mockedCoords;
    }

    /**
     * Delivers the mocked {@link GeoCoordinates} for the GEO-coordinate
     * location example.
     *
     * @return The mocked {@link GeoCoordinates}.
     * @throws InvalidMapDataException
     */
    private GeoCoordinates getMockedGeoCoordsForGeoCoordinateExample()
            throws InvalidMapDataException {

        final GeoCoordinates mockedCoords = new GeoCoordinatesImpl(
                GEO_COORDINATE.x, GEO_COORDINATE.y);
        return mockedCoords;
    }

    /**
     * Delivers mocked location reference points that are expected to be
     * calculated by the decoder in the expected sequence.
     *
     * @param inputLRPs
     *            The data used to mock the LRPs.
     * @return an array of LRPs
     */
    private List<? extends LocationReferencePoint> getLRPs(
            final InputLrp[] inputLRPs) {

        List<LocationReferencePoint> result = new ArrayList<LocationReferencePoint>(
                inputLRPs.length);

        for (int i = 0; i < inputLRPs.length; i++) {

            final InputLrp lrp = inputLRPs[i];

            final LocationReferencePoint mLrp = context.mock(
                    LocationReferencePoint.class,
                    String.valueOf(UUID.randomUUID()));
            final int seqNr = i + 1;
            context.checking(new Expectations() {
                {
                    allowing(mLrp).getSequenceNumber();
                    will(returnValue(seqNr));
                }
            });

            context.checking(new Expectations() {
                {
                    allowing(mLrp).getLongitudeDeg();
                    will(returnValue(lrp.longitude));
                }
            });
            context.checking(new Expectations() {
                {
                    allowing(mLrp).getLatitudeDeg();
                    will(returnValue(lrp.latitude));
                }
            });
            context.checking(new Expectations() {
                {
                    allowing(mLrp).getFRC();
                    will(returnValue(lrp.frc));
                }
            });
            context.checking(new Expectations() {
                {
                    allowing(mLrp).getFOW();
                    will(returnValue(lrp.fow));
                }
            });
            context.checking(new Expectations() {
                {
                    allowing(mLrp).getBearing();
                    will(returnValue(lrp.bearing));
                }
            });
            context.checking(new Expectations() {
                {
                    allowing(mLrp).getDistanceToNext();
                    will(returnValue(lrp.distanceToNext));
                }
            });
            context.checking(new Expectations() {
                {
                    allowing(mLrp).getLfrc();
                    will(returnValue(lrp.lfrcnp));
                }
            });

            final boolean isLast = i == inputLRPs.length - 1;
            context.checking(new Expectations() {
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
     * Delivers the mocked offsets for the line location example.
     *
     * @param offsets
     *            The offset values to set. An array in the order of {positive
     *            offset, negative offset}
     * @return The mocked offsets for the line location example.
     */
    private Offsets getNewMockedOffsets(final int[] offsets) {

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
     * An enumeration of constant LRPs that are expected to be the result of
     * encoding the example location.
     */
    enum InputLrp {

        /**
         * The input LRP 1.
         */
        LRP1(FunctionalRoadClass.FRC_3, FormOfWay.MULTIPLE_CARRIAGEWAY,
                49.60851, 6.12683, 135, 561, FunctionalRoadClass.FRC_3),

        /**
         * The input LRP 2.
         */
        LRP2(FunctionalRoadClass.FRC_3, FormOfWay.SINGLE_CARRIAGEWAY, 49.60398,
                6.12838, 227, 274, FunctionalRoadClass.FRC_5),

        /**
         * The input LRP 3 used as a the end LRP of a location (DNP = 0)
         */
        LRP3(FunctionalRoadClass.FRC_5, FormOfWay.SINGLE_CARRIAGEWAY, 49.60305,
                6.12817, 290, 0, null),

        /**
         * The input LRP 4.
         */
        LRP4(FunctionalRoadClass.FRC_2, FormOfWay.SINGLE_CARRIAGEWAY, 49.6,
                6.12829, 202, 92, FunctionalRoadClass.FRC_2),

        /**
         * The input LRP 5 used as a the end LRP of a location (DNP = 0)
         */
        LRP5(FunctionalRoadClass.FRC_2, FormOfWay.SINGLE_CARRIAGEWAY, 49.60521,
                6.12779, 42, 0, FunctionalRoadClass.FRC_2),

        LRP_CLOSEDLINE_1(FunctionalRoadClass.FRC_4,
                FormOfWay.SINGLE_CARRIAGEWAY, 52.1075119, 5.1003693, 220, 733,
                FunctionalRoadClass.FRC_4),

        LRP_CLOSEDLINE_2(FunctionalRoadClass.FRC_2,
                FormOfWay.SINGLE_CARRIAGEWAY, 52.1075119, 5.1003693, 130, 0,
                null),

        LRP_CLOSEDLINE_3(FunctionalRoadClass.FRC_4, FormOfWay.SINGLE_CARRIAGEWAY,
                52.1039393, 5.1002196, 35, 133, FunctionalRoadClass.FRC_4),
        LRP_CLOSEDLINE_4(FunctionalRoadClass.FRC_4, FormOfWay.SINGLE_CARRIAGEWAY,
                52.1049203, 5.1013458, 125, 186, FunctionalRoadClass.FRC_4),
        LRP_CLOSEDLINE_5(FunctionalRoadClass.FRC_4, FormOfWay.SINGLE_CARRIAGEWAY,
                52.1036694, 5.1008357, 125, 26, FunctionalRoadClass.FRC_4),
        LRP_CLOSEDLINE_6(FunctionalRoadClass.FRC_4, FormOfWay.SINGLE_CARRIAGEWAY,
                52.1035308, 5.1011523, 126, 23, FunctionalRoadClass.FRC_4),
        LRP_CLOSEDLINE_7(FunctionalRoadClass.FRC_4, FormOfWay.SINGLE_CARRIAGEWAY,
                52.1034103, 5.1014272, 126, 52, FunctionalRoadClass.FRC_4),
        LRP_CLOSEDLINE_8(FunctionalRoadClass.FRC_4, FormOfWay.SINGLE_CARRIAGEWAY,
                52.1031407, 5.1020562, 35, 134, FunctionalRoadClass.FRC_4),
        LRP_CLOSEDLINE_9(FunctionalRoadClass.FRC_4, FormOfWay.SINGLE_CARRIAGEWAY,
                52.1041319, 5.1031815, 35, 438, FunctionalRoadClass.FRC_4),
        LRP_CLOSEDLINE_10(FunctionalRoadClass.FRC_4, FormOfWay.SINGLE_CARRIAGEWAY,
                52.1063535, 5.1017868, 215, 337, FunctionalRoadClass.FRC_4),
        LRP_CLOSEDLINE_11(FunctionalRoadClass.FRC_4, FormOfWay.SINGLE_CARRIAGEWAY,
                52.1039393, 5.1002196, 305, 0, null);

        /** The bearing of the line referenced by the LRP. */
        private final double bearing;

        /** The distance to the next LRP along the shortest-path. */
        private final int distanceToNext;

        /** The functional road class of the line referenced by the LRP. */
        private final FunctionalRoadClass frc;

        /** The form of way of the line referenced by the LRP. */
        private final FormOfWay fow;

        /** The lowest functional road class to the next LRP. */
        private final FunctionalRoadClass lfrcnp;

        /** The longitude coordinate. */
        private final double longitude;

        /** The latitude coordinate. */
        private final double latitude;

        /**
         * @param frcValue
         *            The FRC
         * @param fowValue
         *            The FOW
         * @param longitudeValue
         *            The longitude
         * @param latitudeValue
         *            The latitude
         * @param bearingValue
         *            TThe bearing
         * @param dnpValue
         *            The DNP
         * @param lfrcnpValue
         *            The lowest FRC to the next point.
         */
        private InputLrp(final FunctionalRoadClass frcValue,
                         final FormOfWay fowValue, final double latitudeValue,
                         final double longitudeValue, final double bearingValue,
                         final int dnpValue, final FunctionalRoadClass lfrcnpValue) {
            this.longitude = longitudeValue;
            this.latitude = latitudeValue;
            this.frc = frcValue;
            this.fow = fowValue;
            this.bearing = bearingValue;
            this.lfrcnp = lfrcnpValue;
            this.distanceToNext = dnpValue;
        }

        /**
         * @return The longitude value of this LRP.
         */
        public double getLongitude() {
            return longitude;
        }

        /**
         * @return The latitude value of this LRP.
         */
        public double getLatitude() {
            return latitude;
        }

    }
}
