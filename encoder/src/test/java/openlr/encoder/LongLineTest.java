package openlr.encoder;

import openlr.LocationReferencePoint;
import openlr.OpenLRProcessingException;
import openlr.location.Location;
import openlr.location.LocationFactory;
import openlr.map.InvalidMapDataException;
import openlr.map.Line;
import openlr.map.mockdb.InvalidConfigurationException;
import openlr.map.mockdb.MockedMapDatabase;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.fail;

/**
 * The Class LongLineAndExtensionTest.
 */
public class LongLineTest {

    /**
     * The Constant POSITIVE_OFFSET.
     */
    private static final int POSITIVE_OFFSET = 3200;

    /**
     * Test long line and an extension of the path. The extension does not contain a long line.
     * It should not result in an OpenLRProcessingException!
     *
     * @throws InvalidMapDataException   the invalid map data exception
     * @throws OpenLRProcessingException the open lr processing exception
     */
    @Test
    public final void testLongLineAndExtension() throws InvalidMapDataException, OpenLRProcessingException {
        MockedMapDatabase mdb = null;
        try {
            mdb = new MockedMapDatabase("ExtraLongLineMapAndExtension.xml", false);
        } catch (InvalidConfigurationException e) {
            fail("Unexpected exception!", e);
        }
        Location location = LocationFactory.createPointAlongLineLocation("", mdb.getLine(1), POSITIVE_OFFSET);
        OpenLREncoder encoder = new OpenLREncoder();
        OpenLREncoderParameter parameter = new OpenLREncoderParameter.Builder().with(mdb).buildParameter();
        LocationReferenceHolder locRefHolder = encoder.encodeLocation(parameter, location);
        Assert.assertTrue(locRefHolder.isValid());
        Assert.assertEquals(locRefHolder.getNrOfLRPs(), 2);
    }

    /**
     * Test: long line followed by normal line. This test covers the encoding of
     * a very long line (> 15km) followed by a normal line (< 15km).
     *
     * @throws InvalidConfigurationException the invalid configuration exception
     * @throws OpenLRProcessingException     the open lr processing exception
     */
    @Test
    public final void testLongLineNormalLine()
            throws InvalidConfigurationException, OpenLRProcessingException {
        MockedMapDatabase mdb = new MockedMapDatabase("ExtraLongLineMap2.xml",
                false);
        List<Line> locLines = new ArrayList<Line>();
        locLines.add(mdb.getLine(1));
        locLines.add(mdb.getLine(3));
        Location location = LocationFactory.createLineLocation("", locLines);
        OpenLREncoder encoder = new OpenLREncoder();
        OpenLREncoderParameter parameter = new OpenLREncoderParameter.Builder()
                .with(mdb).buildParameter();
        LocationReferenceHolder locRefHolder = encoder.encodeLocation(
                parameter, location);
        Assert.assertTrue(locRefHolder.isValid());
        Assert.assertEquals(locRefHolder.getNrOfLRPs(), 4);
        List<LocationReferencePoint> lrps = locRefHolder.getLRPs();
        Assert.assertEquals(lrps.get(0).getDistanceToNext(), 15000);
        Assert.assertEquals(lrps.get(1).getDistanceToNext(), 3000);
        Assert.assertEquals(lrps.get(2).getDistanceToNext(), 10000);
        Assert.assertTrue(lrps.get(3).isLastLRP());
    }

    /**
     * Test: long line followed by long line. This test covers the encoding of
     * two very long line (both > 15km).
     *
     * @throws InvalidConfigurationException the invalid configuration exception
     * @throws OpenLRProcessingException     the openlr processing exception
     */
    @Test
    public final void testLongLineLongLine()
            throws InvalidConfigurationException, OpenLRProcessingException {
        MockedMapDatabase mdb = new MockedMapDatabase("ExtraLongLineMap2.xml",
                false);
        List<Line> locLines = new ArrayList<Line>();
        locLines.add(mdb.getLine(1));
        locLines.add(mdb.getLine(2));
        Location location = LocationFactory.createLineLocation("", locLines);
        OpenLREncoder encoder = new OpenLREncoder();
        OpenLREncoderParameter parameter = new OpenLREncoderParameter.Builder()
                .with(mdb).buildParameter();
        LocationReferenceHolder locRefHolder = encoder.encodeLocation(
                parameter, location);
        Assert.assertTrue(locRefHolder.isValid());
        Assert.assertEquals(locRefHolder.getNrOfLRPs(), 5);
        List<LocationReferencePoint> lrps = locRefHolder.getLRPs();
        Assert.assertEquals(lrps.get(0).getDistanceToNext(), 15000);
        Assert.assertEquals(lrps.get(1).getDistanceToNext(), 3000);
        Assert.assertEquals(lrps.get(2).getDistanceToNext(), 15000);
        Assert.assertEquals(lrps.get(3).getDistanceToNext(), 2000);
        Assert.assertTrue(lrps.get(4).isLastLRP());
    }


}
