package openlr.encoder.routesearch;

import openlr.encoder.OpenLREncoderProcessingException;
import openlr.map.Line;
import openlr.map.teststubs.OpenLRMapDatabaseAdaptor;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class SecondShortestRouteCheckerTest {
    private OpenLRMapDatabaseAdaptor map;

    @BeforeTest
    public void loadMapStub() {
        InputStream mapFile = OpenLRMapDatabaseAdaptor.class.getClassLoader().getResourceAsStream("teststubs/TestMapStub.xml");
        this.map = OpenLRMapDatabaseAdaptor.from(mapFile);
    }

    @Test
    public void verifyExistenceOfSecondValidShortestRoute() throws OpenLREncoderProcessingException {
        List<Line> location = Arrays.asList(map.getLine(1L), map.getLine(2L), map.getLine(5L), map.getLine(6L));
        SecondShortestRouteChecker checker = SecondShortestRouteChecker.on(location, .20);
        assertFalse(checker.hasValidDeviationBefore(0));
        assertTrue(checker.hasValidDeviationBefore(1));
    }
}
