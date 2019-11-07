package openlr.encoder.postprocessor;

import openlr.OpenLRProcessingException;
import openlr.PhysicalEncoder;
import openlr.encoder.data.LocRefPoint;
import openlr.encoder.database.TestMapStubTest;
import openlr.encoder.properties.OpenLREncoderProperties;
import openlr.map.teststubs.OpenLRMapDatabaseAdaptor;
import openlr.properties.OpenLRPropertiesReader;
import org.apache.commons.configuration.Configuration;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class AlternatePathLrpProcessorTest {
    private OpenLRMapDatabaseAdaptor map;

    @BeforeTest
    public void loadMapStub(){
        InputStream mapFile = OpenLRMapDatabaseAdaptor.class.getClassLoader().getResourceAsStream("teststubs/TestMapStub.xml");
        this.map = OpenLRMapDatabaseAdaptor.from(mapFile);
    }

    @Test
    public void verifyAlternatePathHandlerProcess() throws OpenLRProcessingException
    {
        Configuration encoderConfig = OpenLRPropertiesReader.loadPropertiesFromFile(new File(TestMapStubTest.class.getClassLoader().getResource("OpenLR-Encoder-Properties.xml").getFile()));
        encoderConfig.setProperty("AlternatePathRelativeLowerThreshold",20);
        OpenLREncoderProperties properties = new OpenLREncoderProperties(encoderConfig, new ArrayList< PhysicalEncoder >());
        AlternatePathLrpProcessor alternatePathLrpHandler = AlternatePathLrpProcessor.with(properties);
        LocRefPoint firstPoint = new LocRefPoint(Arrays.asList(map.getLine(1L),map.getLine(2L),map.getLine(5L)),properties);
        LocRefPoint lastPoint = new LocRefPoint(map.getLine(6L),properties);
        List<LocRefPoint> lrps = Arrays.asList(firstPoint,lastPoint);
        assertEquals(lrps.size(),2);
        lrps = alternatePathLrpHandler.process(lrps);
        assertEquals(lrps.size(),3);
    }
}
