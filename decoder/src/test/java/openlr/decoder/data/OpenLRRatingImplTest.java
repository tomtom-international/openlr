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
 */
package openlr.decoder.data;

import openlr.LocationReferencePoint;
import openlr.OpenLRProcessingException;
import openlr.decoder.TestData;
import openlr.decoder.properties.OpenLRDecoderProperties;
import openlr.decoder.rating.OpenLRRatingImpl;
import openlr.map.FormOfWay;
import openlr.map.FunctionalRoadClass;
import openlr.map.Line;
import openlr.map.MapDatabase;
import openlr.properties.OpenLRPropertiesReader;
import org.apache.commons.configuration.BaseConfiguration;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * The Class TestTTLRRating.
 */
public class OpenLRRatingImplTest {


    /** The value used for parameter projectionAlongLine of the test on line 26 */
    public static final int PROJECTION_LINE_26 = 22;

    /** The bearing value of the test LRP #1 */
    private static final double BEARING_LRP_1 = 162.9;
    /** The bearing value of the test LRP #1 */
    private static final double BEARING_LRP_2 = 256.67;

    /** The expected result of rating test #1. */
    private static final int EXPECTED_RESULT_RATING_1 = 916;
    /** The expected result of rating test #2. */
    private static final int EXPECTED_RESULT_RATING_2 = 494;
    /** The expected result of rating test #3. */
    private static final int EXPECTED_RESULT_RATING_3 = 755;

    /** The non-junction node factor to apply for tests */
    private static final float NON_JUNCTION_NODE_FACTOR = 0.8f;

    /** The expected result of rating test with junction node with no non-junction node factor. */
    private static final int EXPECTED_RESULT_RATING_JUNCTION_NODE_NO_NON_JUNCTION_NODE_FACTOR = 1002;
    /** The expected result of rating test with junction node with a non-junction node factor. */
    private static final int EXPECTED_RESULT_RATING_JUNCTION_NODE_NON_JUNCTION_NODE_FACTOR = 1002;
    /** The expected result of rating test with non-junction node with no non-junction node factor. */
    private static final int EXPECTED_RESULT_RATING_NON_JUNCTION_NODE_NO_NON_JUNCTION_NODE_FACTOR = 852;
    /** The expected result of rating test with non-junction node with a non-junction node factor. */
    private static final int EXPECTED_RESULT_RATING_NON_JUNCTION_NODE_NON_JUNCTION_NODE_FACTOR = 798;

    /** The distance value of rating test #1. */
    private static final int DISTANCE_RATING_1 = 14;
    /** The distance value of rating test #2. */
    private static final int DISTANCE_RATING_2 = 3;


    /** The ID of line 4 of the test map. */
    private static final int TEST_LINE_4 = 4;
    /** The ID of line 10 of the test map. */
    private static final int TEST_LINE_10 = 10;
    /** The ID of line 26 of the test map. */
    private static final int TEST_LINE_26 = 26;

    /** The rating function being used. */
    private static final OpenLRRatingImpl RATING_FUNCTION = new OpenLRRatingImpl();

    /** A reference to the test OpenLR properties. */
    private OpenLRDecoderProperties properties;

    /** The mocked {@link LocRefPoint} #1. */
    private LocationReferencePoint point1;
    /** The mocked {@link LocRefPoint} #2. */
    private LocationReferencePoint point2;
    /** The line object 4 of the test map. */
    private Line line4;
    /** The line object 10 of the test map. */
    private Line line10;
    /** The line object 26 of the test map. */
    private Line line26;

    /**
     * Performs initial setup of the tests.
     */
    @BeforeTest
    public final void setUp() {
        TestData td = TestData.getInstance();
        MapDatabase mdb = td.getMapDatabase();

        line4 = mdb.getLine(TEST_LINE_4);
        line10 = mdb.getLine(TEST_LINE_10);
        line26 = mdb.getLine(TEST_LINE_26);

        Mockery mockery = new Mockery();

        point1 = mockery.mock(
                LocationReferencePoint.class, "lrp1");

        mockery.checking(new Expectations() {
            {
                allowing(point1).getFOW();
                will(returnValue(FormOfWay.MOTORWAY));
            }
        });
        mockery.checking(new Expectations() {
            {
                allowing(point1).getFRC();
                will(returnValue(FunctionalRoadClass.FRC_1));
            }
        });
        mockery.checking(new Expectations() {
            {
                allowing(point1).getBearing();
                will(returnValue(BEARING_LRP_1));
            }
        });
        mockery.checking(new Expectations() {
            {
                allowing(point1).isLastLRP();
                will(returnValue(false));
            }
        });

        point2 = mockery.mock(
                LocationReferencePoint.class, "lrp2");

        mockery.checking(new Expectations() {
            {
                allowing(point2).getFOW();
                will(returnValue(FormOfWay.MOTORWAY));
            }
        });
        mockery.checking(new Expectations() {
            {
                allowing(point2).getFRC();
                will(returnValue(FunctionalRoadClass.FRC_7));
            }
        });
        mockery.checking(new Expectations() {
            {
                allowing(point2).getBearing();
                will(returnValue(BEARING_LRP_2));
            }
        });
        mockery.checking(new Expectations() {
            {
                allowing(point2).isLastLRP();
                will(returnValue(true));
            }
        });
    }

    /**
     * Rating test #1.
     */
    @Test
    public final void testRating1() throws OpenLRProcessingException {
        int rating = RATING_FUNCTION.getRating(getProperties(),
                DISTANCE_RATING_1, point1, line10, 0);
        assertEquals(EXPECTED_RESULT_RATING_1, rating);
    }

    /**
     * Rating test #2.
     */
    @Test
    public final void testRating2() throws OpenLRProcessingException {
        int projectionAlongLine = point2.isLastLRP() ? line10.getLineLength() : 0;
        int rating = RATING_FUNCTION.getRating(getProperties(),
                DISTANCE_RATING_2, point2, line10, projectionAlongLine);
        assertEquals(EXPECTED_RESULT_RATING_2, rating);
    }

    /**
     * Rating test #3.
     */
    @Test
    public final void testRating3() throws OpenLRProcessingException {
        int rating = RATING_FUNCTION.getRating(getProperties(),
                DISTANCE_RATING_2, point2, line26, PROJECTION_LINE_26);
        assertEquals(EXPECTED_RESULT_RATING_3, rating);
    }

    /**
     * Rating test of junction node when applying no non-junction node factor
     */
    @Test
    public final void testRatingJunctionNodeNoNonJunctionNodeFactor() throws OpenLRProcessingException {
        BaseConfiguration configuration = new BaseConfiguration();
        OpenLRDecoderProperties properties = new OpenLRDecoderProperties(configuration);

        int rating = RATING_FUNCTION.getRating(properties,
                DISTANCE_RATING_1, point1, line10, 0);
        assertEquals(rating, EXPECTED_RESULT_RATING_JUNCTION_NODE_NO_NON_JUNCTION_NODE_FACTOR);
    }

    /**
     * Rating test of junction node when applying a non-junction node factor
     */
    @Test
    public final void testRatingJunctionNodeNonJunctionNodeFactor() throws OpenLRProcessingException {
        BaseConfiguration configuration = new BaseConfiguration();
        configuration.setProperty("NonJunctionNodeFactor", NON_JUNCTION_NODE_FACTOR);
        OpenLRDecoderProperties properties = new OpenLRDecoderProperties(configuration);

        int rating = RATING_FUNCTION.getRating(properties,
                DISTANCE_RATING_1, point1, line10, 0);
        assertEquals(rating, EXPECTED_RESULT_RATING_JUNCTION_NODE_NON_JUNCTION_NODE_FACTOR);
    }

    /**
     * Rating test of non-junction node when applying no non-junction node factor
     */
    @Test
    public final void testRatingNonJunctionNodeNoNonJunctionNodeFactor() throws OpenLRProcessingException {
        BaseConfiguration configuration = new BaseConfiguration();
        OpenLRDecoderProperties properties = new OpenLRDecoderProperties(configuration);

        int rating = RATING_FUNCTION.getRating(properties,
                DISTANCE_RATING_1, point1, line4, 0);
        assertEquals(rating, EXPECTED_RESULT_RATING_NON_JUNCTION_NODE_NO_NON_JUNCTION_NODE_FACTOR);
    }

    /**
     * Rating test of non-junction node when applying a non-junction node factor
     */
    @Test
    public final void testRatingNonJunctionNodeNonJunctionNodeFactor() throws OpenLRProcessingException {
        BaseConfiguration configuration = new BaseConfiguration();
        configuration.setProperty("NonJunctionNodeFactor", NON_JUNCTION_NODE_FACTOR);
        OpenLRDecoderProperties properties = new OpenLRDecoderProperties(configuration);

        int rating = RATING_FUNCTION.getRating(properties,
                DISTANCE_RATING_1, point1, line4, 0);
        assertEquals(rating, EXPECTED_RESULT_RATING_NON_JUNCTION_NODE_NON_JUNCTION_NODE_FACTOR);
    }

    /**
     * Instantiates the properties.
     *
     * @return the properties
     */
    private OpenLRDecoderProperties getProperties() {

        if (properties == null) {
            File validProperties = new File(
                    "src/test/resources/OpenLR-Decoder-Properties.xml");
            try {
                properties = new OpenLRDecoderProperties(OpenLRPropertiesReader
                        .loadPropertiesFromStream(new FileInputStream(validProperties), true));
            } catch (OpenLRProcessingException e) {
                fail("failed to load valid properties file", e);
            } catch (FileNotFoundException e) {
                fail("cannot find properties file", e);
            }
        }
        return properties;
    }

}
