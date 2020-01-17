package openlr.encoder.postprocessor;

import openlr.OpenLRProcessingException;
import openlr.encoder.database.TestMapStubTest;
import openlr.encoder.properties.OpenLREncoderProperties;
import openlr.map.InvalidMapDataException;
import openlr.map.Line;
import openlr.map.teststubs.OpenLRMapDatabaseAdaptor;
import openlr.properties.OpenLRPropertiesReader;
import org.apache.commons.configuration.Configuration;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OfframpProcessorTest {
    private OpenLRMapDatabaseAdaptor map;
    private OfframpProcessor offrampProcessor;

    @BeforeTest
    public void loadMapStub() throws OpenLRProcessingException {
        InputStream mapFile = OpenLRMapDatabaseAdaptor.class.getClassLoader().getResourceAsStream("teststubs/OfframpTest.xml");
        this.map = OpenLRMapDatabaseAdaptor.from(mapFile);
        Configuration encoderConfig = OpenLRPropertiesReader.loadPropertiesFromFile(new File(TestMapStubTest.class.getClassLoader().getResource("OpenLR-Encoder-Properties.xml").getFile()));
        OpenLREncoderProperties properties = new OpenLREncoderProperties(encoderConfig, new ArrayList<>());
        this.offrampProcessor = OfframpProcessor.with(properties);
    }


    @Test
    public void locationAlongMotorWay() throws OpenLRProcessingException, InvalidMapDataException {
        List<Line> route = Arrays.asList(map.getLine(1L), map.getLine(2L), map.getLine(6L));
        List<Integer> corePointPositions = offrampProcessor.determineNewIntermediatePoints(route);
        Assert.assertEquals(corePointPositions.size(), 1);
        Assert.assertEquals(corePointPositions.get(0).intValue(),2);
    }

    @Test
    public void locationAlongOfframp() throws OpenLRProcessingException, InvalidMapDataException {
        List<Line> route = Arrays.asList(map.getLine(3L), map.getLine(4L), map.getLine(5L),map.getLine(6L));
        List<Integer> corePointPositions = offrampProcessor.determineNewIntermediatePoints(route);
        Assert.assertEquals(corePointPositions.size(), 1);
        Assert.assertEquals(corePointPositions.get(0).intValue(),2);

    }
}
