package openlr.decoder.rating.critics;

import openlr.OpenLRProcessingException;
import openlr.decoder.database.TestMapStubTest;
import openlr.decoder.properties.OpenLRDecoderProperties;
import openlr.map.Line;
import openlr.map.MapDatabase;
import openlr.map.teststubs.OpenLRMapDatabaseAdaptor;
import openlr.properties.OpenLRPropertiesReader;
import org.apache.commons.configuration.Configuration;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.InputStream;

public class BearingRatingTest {

    private BearingCritic bearingCritic;
    Line line;

    @BeforeTest
    public void initiate() throws OpenLRProcessingException {
        InputStream mapFile = OpenLRMapDatabaseAdaptor.class.getClassLoader().getResourceAsStream("teststubs/TestMapStub.xml");
        MapDatabase map = OpenLRMapDatabaseAdaptor.from(mapFile);
        Configuration decoderConfig = OpenLRPropertiesReader.loadPropertiesFromFile(new File(TestMapStubTest.class.getClassLoader().getResource("OpenLR-Decoder-Properties.xml").getFile()));
        OpenLRDecoderProperties openLRDecoderProperties = new OpenLRDecoderProperties(decoderConfig);
        bearingCritic = BearingCritic.with(openLRDecoderProperties);
        line = map.getLine(1);
    }

    @Test
    public void bestRating() {
        Assert.assertEquals(bearingCritic.rate(line, false, 119, 0), 100);
        Assert.assertEquals(bearingCritic.rate(line, true, 286, line.getLineLength()), 100);
    }

    @Test
    public void worstRating() {
        Assert.assertEquals(bearingCritic.rate(line, false, 214, 0), 0);
        Assert.assertEquals(bearingCritic.rate(line, true, 237, line.getLineLength()), 0);
    }

    @Test
    public void realWorldRating() {
        Assert.assertEquals(bearingCritic.rate(line, false, 128, 0), 80);
        Assert.assertEquals(bearingCritic.rate(line, true, 304, line.getLineLength()), 60);
    }
}
