package openlr.decoder.worker;

import openlr.LocationReferencePoint;
import openlr.Offsets;
import openlr.OpenLRProcessingException;
import openlr.decoder.OpenLRDecoder;
import openlr.decoder.OpenLRDecoderParameter;
import openlr.decoder.TestLocationReferencePointImpl;
import openlr.location.Location;
import openlr.location.data.Orientation;
import openlr.location.data.SideOfRoad;
import openlr.map.FormOfWay;
import openlr.map.FunctionalRoadClass;
import openlr.map.GeoCoordinates;
import openlr.map.MapDatabase;
import openlr.map.mockdb.InvalidConfigurationException;
import openlr.map.mockdb.MockedMapDatabase;
import openlr.rawLocRef.RawPointAlongLocRef;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * The Class PointAlongDecoderTest.
 */
public class PointAlongDecoderTest {

    /**
     * The Constant LRP_START.
     */
    private static final LocationReferencePoint LRP_START = new TestLocationReferencePointImpl(
            10.887121, 51.663269, 5000, FunctionalRoadClass.FRC_0,
            FormOfWay.MOTORWAY, FunctionalRoadClass.FRC_0, 90.0, false, 0);

    /**
     * The Constant LRP_END.
     */
    private static final LocationReferencePoint LRP_END = new TestLocationReferencePointImpl(
            10.958748, 51.663104, 0, FunctionalRoadClass.FRC_0,
            FormOfWay.MOTORWAY, FunctionalRoadClass.FRC_0, 270.0, true, 1);
    ;

    /**
     * The Constant OFFSETS.
     */
    private static final Offsets OFFSETS = new RelativeOffsetsImpl(20, 0); //20%

    /**
     * The Constant DECODER.
     */
    private static final OpenLRDecoder DECODER = new OpenLRDecoder();

    /**
     * The mdb.
     */
    private final MapDatabase mdb;

    /**
     * The params.
     */
    private final OpenLRDecoderParameter params;

    /**
     * Instantiates a new point along decoder test.
     *
     * @throws InvalidConfigurationException the invalid configuration exception
     */
    public PointAlongDecoderTest() throws InvalidConfigurationException {
        mdb = new MockedMapDatabase("PointAlongLineDecoderMap.xml", false);
        params = new OpenLRDecoderParameter.Builder().with(mdb)
                .buildParameter();
    }

    /**
     * Test point along line with lr pon line.
     */
    @Test
    public final void testPointAlongLineWithLRPonLine() {

        RawPointAlongLocRef locRef = new RawPointAlongLocRef("", LRP_START,
                LRP_END, OFFSETS, SideOfRoad.ON_ROAD_OR_UNKNOWN,
                Orientation.NO_ORIENTATION_OR_UNKNOWN);

        try {
            Location loc = DECODER.decodeRaw(params, locRef);
            Assert.assertTrue(loc.isValid());
            GeoCoordinates coord = loc.getAccessPoint();
            Assert.assertEquals(coord.getLongitudeDeg(), 10.0);
            Assert.assertEquals(coord.getLatitudeDeg(), 51.0);

        } catch (OpenLRProcessingException e) {
            Assert.fail("Unexpected exception", e);
        }
    }

}
