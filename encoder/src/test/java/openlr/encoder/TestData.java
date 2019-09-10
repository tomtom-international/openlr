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
package openlr.encoder;

import openlr.OpenLRProcessingException;
import openlr.PhysicalEncoder;
import openlr.encoder.data.ExpansionData;
import openlr.encoder.data.ExpansionHelper;
import openlr.encoder.data.LocRefData;
import openlr.encoder.properties.OpenLREncoderProperties;
import openlr.location.LineLocation;
import openlr.location.Location;
import openlr.location.LocationFactory;
import openlr.location.PoiAccessLocation;
import openlr.location.data.SideOfRoad;
import openlr.map.InvalidMapDataException;
import openlr.map.Line;
import openlr.map.MapDatabase;
import openlr.map.Node;
import openlr.map.mockdb.InvalidConfigurationException;
import openlr.map.mockdb.MockedMapDatabase;
import openlr.properties.OpenLRPropertiesReader;
import org.apache.commons.configuration.Configuration;

import java.awt.geom.Point2D;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import static org.testng.Assert.fail;

/**
 * Delivers mocked test data.
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
     * <b> This configuration represents the ported configuration from
     * the Java code to the XML file after introduction of the configurable
     * database mocking. For future tests please create
     * {@link MockedMapDatabase} instances with smaller map configurations. </b>
     */
    public static final String DEFAULT_MAP_DB_CONFIG = "DefaultMapDatabase.xml";
    /** The positive offset for the PAL example. */
    static final int POINT_ALONG_LINE_POS_OFFSET = 28;
    /** The encoder properties file. */
    static final String ENCODER_PROPERTIES = "/OpenLR-Encoder-Properties.xml";
    /** The id of the lines for the valid location (to be found in map
     * configuration DefaultMapDatabase.xml). */
    private static final long[] VALIDIDS = {4, 6, 10, 14, 16, 18, 19};
    /** The ID of the relevant line for the PAL example. */
    private static final long POINT_ALONG_LINE_LINE = 6;
    /** The POI in the point with access point example, x = longitude, y = latitude. */
    private static final Point2D.Double POI_WITH_ACCESS_POINT_POI =
            new Point2D.Double(6.12699, 49.60728);
    /** The GEO-coordinate of the White Paper example, x = longitude, y = latitude. */
    private static final Point2D.Double GEO_COORDINATE =
            new Point2D.Double(6.12699, 49.60728);
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
    /**
     * The negative offset used for an example of a location that can be reduced
     * because of exceeding offsets.
     */
    private static final int NEG_OFFSET_REDUCIBLE = 150;
    /**
     * The positive offset used for an example of a location that can be reduced
     * because of exceeding offsets.
     */
    private static final int POS_OFFSET_REDUCIBLE = 200;
    /** The Constant ROUTELENGTH1. */
    private static final int ROUTELENGTH1 = 411;
    /** The Constant ROUTELENGTH2. */
    private static final int ROUTELENGTH2 = 274;
    /** The Constant INTERMEDIATEPOS. */
    private static final int INTERMEDIATEPOS = 3;
    /** The Constant NODEDSEG1. */
    private static final long NODEDSEG1 = 4;
    /** The Constant NODEDSEG2. */
    private static final long NODEDSEG2 = 3;
    /** The Constant INTERMEDIATE. */
    private static final long INTERMEDIATE = 14;
    /**
     * The lines used to create the reducible line location (to be found in map
     * configuration DefaultMapDatabase.xml).
     */
    private static final long[] LINES_REDUCIBLE_OFFSET = new long[]{4, 6, 10,
            14, 16, 18};
    /** The unique instance of this class. */
    private static TestData instance = new TestData();
    /**
     * The default mocked map database.
     * <b> Note! This global instance of map database shouldn't be used any
     * more. Our new approach is to generate smaller instances of mocked map
     * databases via configuration for each single test. <b/>
     * @see MockedMapDatabase
     */
    private MapDatabase defaultMockedMapDB;
    /** The valid location. */
    private ArrayList<Line> validLocation = new ArrayList<Line>();
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
    /** The valid lr. */
    private LocationReferenceHolderImpl validLR = null;
    /** The invalid lr. */
    private LocationReferenceHolderImpl invalidLR = null;
    /** The intermediate dseg. */
    private Line intermediateDseg = null;
    /**
     * The encoder properties.
     */
    private Configuration config;

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

        InputStream propFile = TestData.class.getResourceAsStream(
                ENCODER_PROPERTIES);

        if (propFile == null) {
            fail("missing properties file");
        }
        defaultMockedMapDB = getMapDatabase();
        try {
            config = OpenLRPropertiesReader.loadPropertiesFromStream(propFile, true);
        } catch (OpenLRProcessingException e) {
            fail("Reading encoder properties failed.");
        }
        generateLocations();
        generateNodes();
    }

    /**
     * Generate locations.
     */
    private void generateLocations() {
        for (int i = 0; i < VALIDIDS.length; i++) {
            validLocation.add(defaultMockedMapDB.getLine(VALIDIDS[i]));
        }

        for (int i = 0; i < EXP_START_IDS.length; i++) {
            validLocationExpandStart.add(defaultMockedMapDB.getLine(EXP_START_IDS[i]));
        }
        for (int i = 0; i < NOTCONNECTEDIDS.length; i++) {
            notConnectedLocation.add(defaultMockedMapDB.getLine(NOTCONNECTEDIDS[i]));
        }
        for (int i = 0; i < INVALIDFRCIDS.length; i++) {
            invalidFRCLocation.add(defaultMockedMapDB.getLine(INVALIDFRCIDS[i]));
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
     * Gets the default mocked map database. <br>
     * <b>Note! This global instance of map database shouldn't be used any
     * more. Our new approach is to generate smaller instances of mocked map
     * databases via configuration for each single test.
     * For future tests please create {@link MockedMapDatabase} instances with
     * smaller map configurations.</b>
     *
     * @return the map database

     * @see MockedMapDatabase
     */
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
     * Delivers the line location example for the encoder from the OpenLR
     * White Paper (v 1.3)
     *
     * @return the line location
     */
    public Location getWhitepaperLineLocation() {
        return LocationFactory.createLineLocation("wpLine", validLocation);
    }

    /**
     * Delivers the point along line location example for the encoder from the
     * OpenLR White Paper (v 1.3)
     *
     * @return the PAL location
     * @throws InvalidMapDataException
     */
    public Location getWhitepaperPointAlongLineLocation() throws InvalidMapDataException {

        return LocationFactory.createPointAlongLineLocationWithSide("wpPAL",
                defaultMockedMapDB.getLine(POINT_ALONG_LINE_LINE),
                POINT_ALONG_LINE_POS_OFFSET, SideOfRoad.LEFT);
    }

    /**
     * Delivers the geo coordinate example for the encoder from the OpenLR White
     * Paper (v 1.3)
     *
     * @return the geo-coordinate location
     * @throws InvalidMapDataException
     */
    public Location getWhitepaperGeoCoordinateLocation() throws InvalidMapDataException {
        return LocationFactory.createGeoCoordinateLocation("wpGeo",
                GEO_COORDINATE.x, GEO_COORDINATE.y);
    }

    /**
     * Delivers the POI with access point location example for the encoder
     * from the OpenLR White Paper (v 1.3)
     *
     * @return the PAL location
     * @throws InvalidMapDataException
     */
    public Location getWhitepaperPOIWithAccessPointLocation() throws InvalidMapDataException {
        return LocationFactory.createPoiAccessLocationWithSide("wpPWA", defaultMockedMapDB
                        .getLine(POINT_ALONG_LINE_LINE), POINT_ALONG_LINE_POS_OFFSET,
                POI_WITH_ACCESS_POINT_POI.x, POI_WITH_ACCESS_POINT_POI.y,
                SideOfRoad.LEFT);
    }

    /**
     * @return A mocked POI with access point location reference wit no offset.
     * @throws InvalidMapDataException
     */
    public PoiAccessLocation getPOIWithAccessNoOffset() throws InvalidMapDataException {
        return (PoiAccessLocation) LocationFactory
                .createPoiAccessAtNodeLocationWithSide("wpPWANoOff", defaultMockedMapDB
                                .getLine(POINT_ALONG_LINE_LINE),
                        POI_WITH_ACCESS_POINT_POI.x,
                        POI_WITH_ACCESS_POINT_POI.y, SideOfRoad.LEFT);
    }

    /**
     * Gets the not connected location.
     *
     * @return the not connected location
     */
    public Location getNotConnectedLocation() {
        return LocationFactory.createLineLocation("notConnected",
                notConnectedLocation);
    }


    /**
     * Gets the empty location.
     *
     * @return the empty location
     */
    public Location getEmptyLocation() {
        return LocationFactory.createLineLocation("empty", new ArrayList<Line>());
    }

    /**
     * @return A line location with positive and negative offset set that should
     * be reduced during the encoding because the offsets exceed the first and
     * last line.
     */
    public LineLocation getLineLocationReducibleOffsets() {


        ArrayList<Line> lines = new ArrayList<Line>(LINES_REDUCIBLE_OFFSET.length);
        for (long id : LINES_REDUCIBLE_OFFSET) {
            lines.add(defaultMockedMapDB.getLine(id));
        }

        return (LineLocation) LocationFactory.createLineLocationWithOffsets(
                "lineLoc2Off", lines, POS_OFFSET_REDUCIBLE, NEG_OFFSET_REDUCIBLE);
    }

    /**
     * Gets the tuning.
     *
     * @return the tuning
     * @throws OpenLRProcessingException
     */
    public OpenLREncoderProperties getProperties() throws OpenLRProcessingException {
        return new OpenLREncoderProperties(config, Collections.<PhysicalEncoder>emptyList());
    }

    /**
     * Gets the tuning.
     *
     * @return the tuning
     */
    public Configuration getConfiguration() {
        return config;
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
     * Gets the valid lr.
     *
     * @return the valid lr
     */
    public LocationReferenceHolderImpl getValidLR() {
        return validLR;
    }

    /**
     * Gets the invalid lr.
     *
     * @return the invalid lr
     */
    public LocationReferenceHolderImpl getInvalidLR() {
        return invalidLR;
    }

    /**
     * Gets the route.
     *
     * @return the route
     */
    public Location getRoute() {
        return LocationFactory.createLineLocation("route", validLocation);
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
     * Gets the lR location.
     *
     * @return the lR location
     */
    public ExpansionData getLRLocation() {
        ExpansionData loc = null;
        LocRefData lrd = new LocRefData(getRoute());
        try {
            loc = ExpansionHelper.createExpandedLocation(getProperties(), defaultMockedMapDB, lrd);
        } catch (OpenLRProcessingException e) {
            fail("cannot create expanded location", e);
        }
        return loc;
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

}
