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
package openlr.decoder.worker;

import openlr.LocationReferencePoint;
import openlr.OpenLRProcessingException;
import openlr.decoder.OpenLRDecoder;
import openlr.decoder.worker.LineDecoder;
import openlr.decoder.properties.OpenLRDecoderProperties;
import openlr.properties.OpenLRPropertiesReader;
import openlr.decoder.data.CandidateNodesResultSet;
import openlr.decoder.data.CandidateLinesResultSet;
import openlr.decoder.OpenLRDecoderParameter;
import openlr.decoder.TestLocationReferencePointImpl;
import openlr.location.Location;
import openlr.map.FormOfWay;
import openlr.map.FunctionalRoadClass;
import openlr.map.Line;
import openlr.map.mockdb.InvalidConfigurationException;
import openlr.map.mockdb.MockedMapDatabase;
import openlr.rawLocRef.RawLineLocRef;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * Tests the decoding and specially the offset calculation of
 * {@link LineDecoder}.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class LineDecoderTest {

    /**
     * An offsets object defining offsets of zero
     */
    private static final RelativeOffsetsImpl OFFSETS_ZERO = new RelativeOffsetsImpl(
            0, 0);
    /**
     * A large positive offset (90%)
     */
    private static final RelativeOffsetsImpl LARGE_POS_OFFSET = new RelativeOffsetsImpl(
            90, 5);
    /**
     * The expected negative offset after decoding the original location with
     * offsets
     */
    private static final int EXPECTED_NEG_OFFSET_LOC_WITH_OFFSETS = 157;
    /**
     * The expected negative offset after decoding the long location with
     * offsets
     */
    private static final int EXPECTED_NEG_OFFSET_LONG_LOC_WITH_OFFSETS = 592;

    /**
     * The expected negative offset after decoding the long location with
     * offsets
     */
    private static final int EXPECTED_NEG_OFFSET_LONG_LOC_WITHOUT_OFFSETS = 380;
    /**
     * The expected positive offset after decoding the original location with
     * offsets
     */
    private static final int EXPECTED_POS_OFFSET_LOC_WITH_OFFSETS = 3322;
    /**
     * The expected positive offset after decoding the long location with
     * offsets
     */
    private static final int EXPECTED_POS_OFFSET_LONG_LOC_WITH_OFFSETS = 3397;
    /**
     * The expected negative offset after decoding the original location without
     * offsets
     */
    private static final int EXPECTED_NEG_OFFSET_LOC_WITHOUT_OFFSETS = 95;
    /**
     * The expected positive offset after decoding the original location without
     * offsets
     */
    private static final int EXPECTED_POS_OFFSET_LOC_WITHOUT_OFFSETS = 3291;
    /**
     * The relative offset of the location including offsets
     */
    private static final RelativeOffsetsImpl RELATIVE_OFFSETS = new RelativeOffsetsImpl(
            10, 20);
    /**
     * The last LRP of the location
     */
    private static final LocationReferencePoint LRP_END = new TestLocationReferencePointImpl(
            13.543290, 52.615133, 0, FunctionalRoadClass.FRC_0,
            FormOfWay.MOTORWAY, FunctionalRoadClass.FRC_0, 275.6, true, 1);

    /**
     * The first LRP of the location
     */
    private static final LocationReferencePoint LRP_START_LONG = new TestLocationReferencePointImpl(
            13.538750, 52.615403, 2750, FunctionalRoadClass.FRC_0,
            FormOfWay.MOTORWAY, FunctionalRoadClass.FRC_0, 95.6, false, 0);
    /**
     * The first LRP of the LONG location, but with an altered bearing that produces a bearing rating of 66, 
     * which is just acceptable given a Bear_Rating.MinScore = 65 in DecoderpropertiesBearingMinScore.xml
     */
    private static final LocationReferencePoint LRP_START_LONG_BAD_BEARING_ACCEPTABLE = new TestLocationReferencePointImpl(
            13.538750, 52.615403, 2750, FunctionalRoadClass.FRC_0,
            FormOfWay.MOTORWAY, FunctionalRoadClass.FRC_0, 81.0, false, 0);
    /**
     * The first LRP of the LONG location, but with a bearing that produces a bearing rating less than the 
     * Bear_Rating.MinScore = 65 in DecoderpropertiesBearingMinScore.xml and therefore finds no candidates.
     */
    private static final LocationReferencePoint LRP_START_LONG_BAD_BEARING_REJECT = new TestLocationReferencePointImpl(
            13.538750, 52.615403, 2750, FunctionalRoadClass.FRC_0,
            FormOfWay.MOTORWAY, FunctionalRoadClass.FRC_0, 75.0, false, 0);
    /**
     * The last LRP of the location
     */
    private static final LocationReferencePoint LRP_END_LONG = new TestLocationReferencePointImpl(
            13.555723, 52.614372, 0, FunctionalRoadClass.FRC_0,
            FormOfWay.MOTORWAY, FunctionalRoadClass.FRC_0, 276, true, 1);
    /**
     * The first LRP of the location
     */
    private static final LocationReferencePoint LRP_START = new TestLocationReferencePointImpl(
            13.538750, 52.615403, 322, FunctionalRoadClass.FRC_0,
            FormOfWay.MOTORWAY, FunctionalRoadClass.FRC_0, 95.6, false, 0);

    /**
     * The first LRP of the location
     */
    private static final LocationReferencePoint LRP_START_MULTI = new TestLocationReferencePointImpl(
            13.538750, 52.615403, 650, FunctionalRoadClass.FRC_0,
            FormOfWay.MOTORWAY, FunctionalRoadClass.FRC_0, 95.6, false, 0);
    /**
     * The first LRP of the location
     */
    private static final LocationReferencePoint LRP_MIDDLE_MULTI = new TestLocationReferencePointImpl(
            13.547893, 52.614945, 550, FunctionalRoadClass.FRC_0,
            FormOfWay.MOTORWAY, FunctionalRoadClass.FRC_0, 88, false, 1);
    /**
     * The last LRP of the location
     */
    private static final LocationReferencePoint LRP_END_MULTI = new TestLocationReferencePointImpl(
            13.555723, 52.614372, 0, FunctionalRoadClass.FRC_0,
            FormOfWay.MOTORWAY, FunctionalRoadClass.FRC_0, 276, true, 2);

    /**
     * The expected detected line on the decoder side
     */
    private static final long[] EXPECTED_LINES_SMALL_LOCATION = new long[]{1};
    /**
     * The expected lines after decoding the long location.
     */
    private static final long[] EXPECTED_LINES_LONG_LOCATION = new long[]{1,
            2, 3};

    /**
     * The expected lines after decoding the long location.
     */
    private static final long[] EXPECTED_LINES_LONG_LOCATION_LARGE_OFFSETS = new long[]{
            2, 3};
    /**
     * The used decoder reference
     */
    private static final OpenLRDecoder DECODER = new OpenLRDecoder();
    /**
     *
     * The mocked map database.
     */
    private final MockedMapDatabase mdb;
    /**
     * The decoder parameters
     */
    private final OpenLRDecoderParameter params;

    /**
     * Initializes the map database.
     *
     * @throws InvalidConfigurationException
     *             If an error occurs initializing the map database from the
     *             configuration.
     */
    public LineDecoderTest() throws InvalidConfigurationException {
        mdb = new MockedMapDatabase("LineDecoderMap.xml", false);
        params = new OpenLRDecoderParameter.Builder().with(mdb)
                .buildParameter();
    }

    /**
     * Tests a small location that fits entirely in one single line on the
     * DECODER side
     */
    @Test
    public final void testSmallLocationOnSingleLine() {

        List<LocationReferencePoint> lrpList = Arrays
                .asList(LRP_START, LRP_END);

        RawLineLocRef locRef = new RawLineLocRef("shortLocRef", lrpList,
                OFFSETS_ZERO);

        runDecoding(locRef, EXPECTED_LINES_SMALL_LOCATION,
                EXPECTED_POS_OFFSET_LOC_WITHOUT_OFFSETS,
                EXPECTED_NEG_OFFSET_LOC_WITHOUT_OFFSETS);
    }

    /**
     * Tests a small location containing offsets that fits entirely in one
     * single line on the DECODER side
     */
    @Test
    public final void testSmallLocationWithOffsetsOnSingleLine() {

        List<LocationReferencePoint> lrpList = Arrays
                .asList(LRP_START, LRP_END);

        RawLineLocRef locRef = new RawLineLocRef("shortLocRef", lrpList,
                RELATIVE_OFFSETS);

        runDecoding(locRef, EXPECTED_LINES_SMALL_LOCATION,
                EXPECTED_POS_OFFSET_LOC_WITH_OFFSETS,
                EXPECTED_NEG_OFFSET_LOC_WITH_OFFSETS);
    }

    /**
     * Tests a long location without offsets that goes over multiple lines
     */
    @Test
    public final void testLongLocationWithoutOffsetsOverMultipleLine() {

        List<LocationReferencePoint> lrpList = Arrays.asList(LRP_START_LONG,
                LRP_END_LONG);

        RawLineLocRef locRef = new RawLineLocRef("longLocRef", lrpList,
                OFFSETS_ZERO);

        runDecoding(locRef, EXPECTED_LINES_LONG_LOCATION,
                EXPECTED_POS_OFFSET_LOC_WITHOUT_OFFSETS,
                EXPECTED_NEG_OFFSET_LONG_LOC_WITHOUT_OFFSETS);
    }

    /**
     * Tests a long location wit offsets that goes over multiple lines
     */
    @Test
    public final void testLongLocationWithOffsetsOverMultipleLines() {

        List<LocationReferencePoint> lrpList = Arrays.asList(LRP_START_LONG,
                LRP_END_LONG);

        RawLineLocRef locRef = new RawLineLocRef("longLocRef", lrpList,
                RELATIVE_OFFSETS);

        runDecoding(locRef, EXPECTED_LINES_LONG_LOCATION,
                EXPECTED_POS_OFFSET_LONG_LOC_WITH_OFFSETS,
                EXPECTED_NEG_OFFSET_LONG_LOC_WITH_OFFSETS);
    }

    /**
     * Tests a long location wit offsets that goes over multiple lines
     */
    @Test
    public final void testLongLocationWithLargeOffsetsOverMultipleLines() {

        List<LocationReferencePoint> lrpList = Arrays.asList(LRP_START_LONG,
                LRP_END_LONG);

        RawLineLocRef locRef = new RawLineLocRef("longLocRef", lrpList,
                LARGE_POS_OFFSET);

        runDecoding(locRef, new long[]{3}, 374, 433);
    }


    /**
     * Tests that candidates are discarded from consideration if the bearing score is
     * less than or equal to the <Bearing_Rating>.<MinScore> attribute.
     */
    @Test
    public final void testFindCandidates() {

        List<LocationReferencePoint> lrpListAcceptable = Arrays.asList(LRP_START_LONG_BAD_BEARING_ACCEPTABLE,
                LRP_END_LONG);

        List<LocationReferencePoint> lrpListReject = Arrays.asList(LRP_START_LONG_BAD_BEARING_REJECT,
                LRP_END_LONG);

        RawLineLocRef locRefAcceptableFirstBearing = new RawLineLocRef("longLocRefAcceptableFirstBearing", lrpListAcceptable,
                LARGE_POS_OFFSET);

        RawLineLocRef locRefRejectedFirstBearing = new RawLineLocRef("longLocRefRejectedFirstBearing", lrpListReject,
                LARGE_POS_OFFSET);

        LineDecoder lineDecoder = new LineDecoder();
        OpenLRDecoderProperties properties = getPropertiesWithBearingMinScore();
        
        try {
                CandidateLinesResultSet candidateLines;
                CandidateNodesResultSet candidateNodes;
                candidateNodes = lineDecoder.findCandidateNodes(properties, locRefAcceptableFirstBearing, mdb);
                candidateLines = lineDecoder.findCandidateLines(properties, locRefAcceptableFirstBearing, candidateNodes, mdb);
                assertEquals(candidateLines.getNumberOfCandidateLines(LRP_START_LONG_BAD_BEARING_ACCEPTABLE), 1);
                assertEquals(candidateLines.getNumberOfCandidateLines(LRP_END_LONG), 1);
                candidateNodes = lineDecoder.findCandidateNodes(properties, locRefRejectedFirstBearing, mdb);
                candidateLines = lineDecoder.findCandidateLines(properties, locRefRejectedFirstBearing, candidateNodes, mdb);
                assertEquals(candidateLines.getNumberOfCandidateLines(LRP_START_LONG_BAD_BEARING_REJECT), 0);
        } catch (OpenLRProcessingException e) {
            fail("failure during candidate search", e);
        }

    }

    /**
     * Tests a long location with large offsets and multiple LRPs that goes over
     * multiple lines. The pruning should remove the first line in this case.
     */
    @Test
    public final void testLongLocationMultipleLRPsWithLargeOffsets() {

        List<LocationReferencePoint> lrpList = Arrays.asList(LRP_START_MULTI,
                LRP_MIDDLE_MULTI, LRP_END_MULTI);

        RawLineLocRef locRef = new RawLineLocRef("multiLocRef", lrpList,
                LARGE_POS_OFFSET);

        runDecoding(locRef, EXPECTED_LINES_LONG_LOCATION_LARGE_OFFSETS, 68, 407);
    }

    /**
     * Tests a long location with offsets and multiple LRPs that goes over
     * multiple lines
     */
    @Test
    public final void testLongLocationMultipleLRPsWithOffsets() {

        List<LocationReferencePoint> lrpList = Arrays.asList(LRP_START_MULTI,
                LRP_MIDDLE_MULTI, LRP_END_MULTI);

        RawLineLocRef locRef = new RawLineLocRef("multiLocRef", lrpList,
                RELATIVE_OFFSETS);

        runDecoding(locRef, EXPECTED_LINES_LONG_LOCATION, 3343, 487);
    }

    /**
     * Runs the decoder with the given location reference and checks the
     * provided parameters of the result
     *
     * @param locRef
     *            The location reference
     * @param expectedLines
     *            The expected location lines
     * @param expectedPosOffset
     *            The expected positive offset
     * @param expectedNegOffset
     *            The expected negative offset
     */
    private void runDecoding(final RawLineLocRef locRef,
                             final long[] expectedLines, final int expectedPosOffset,
                             final int expectedNegOffset) {

        try {
            Location loc = DECODER.decodeRaw(params, locRef);

            List<? extends Line> lines = loc.getLocationLines();
            Line current;
            for (int i = 0; i < lines.size(); i++) {
                current = lines.get(i);
                assertEquals(current.getID(), expectedLines[i],
                        "Unexpected line in decoded path!");
            }

            Assert.assertEquals(loc.getPositiveOffset(), expectedPosOffset);
            Assert.assertEquals(loc.getNegativeOffset(), expectedNegOffset);

        } catch (OpenLRProcessingException e) {
            Assert.fail("Unexpected exception", e);
        }
    }

    /**
     * Instantiates the properties.
     *
     * @return the properties
     */
    private OpenLRDecoderProperties getPropertiesWithBearingMinScore() {
        OpenLRDecoderProperties local_properties = null;

        File validProperties = new File(
                "src/test/resources/DecoderPropertiesBearingMinScore.xml");
                // "src/test/resources/OpenLR-Decoder-Properties.xml");
        try {
            local_properties = new OpenLRDecoderProperties(OpenLRPropertiesReader
                    .loadPropertiesFromStream(new FileInputStream(validProperties), true));
        } catch (OpenLRProcessingException e) {
            fail("failed to load valid properties file", e);
        } catch (FileNotFoundException e) {
            fail("cannot find properties file", e);
        }

        return local_properties;

    }

}
