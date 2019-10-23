package openlr.encoder.database;

import com.vividsolutions.jts.geom.Coordinate;
import openlr.OpenLREncoder;
import openlr.OpenLRProcessingException;
import openlr.encoder.LocationReferenceHolder;
import openlr.encoder.OpenLREncoderParameter;
import openlr.encoder.worker.LineEncoder;
import openlr.location.Location;
import openlr.location.LocationFactory;
import openlr.map.GeoCoordinates;
import openlr.map.Line;
import openlr.map.XmlToSimpleMockDatabaseTest;
import openlr.map.simplemockdb.SimpleMockedDatabaseAdaptor;
import openlr.map.simplemockdb.SimpleMockedLine;
import openlr.properties.OpenLRPropertiesReader;
import openlr.rawLocRef.RawLocationReference;
import org.apache.commons.configuration.Configuration;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SimpleMockedMapTest {
    @Test
    void encoding() throws OpenLRProcessingException {
        InputStream mapFile = SimpleMockedDatabaseAdaptor.class.getClassLoader().getResourceAsStream("simplemockedmaps/SimpleMockedTestMap.xml");
        SimpleMockedDatabaseAdaptor map = SimpleMockedDatabaseAdaptor.from(mapFile);
        Configuration encoderConfig = OpenLRPropertiesReader.loadPropertiesFromFile(new File(SimpleMockedMapTest.class.getClassLoader().getResource("OpenLR-Encoder-Properties.xml").getFile()));
        OpenLREncoderParameter params = new OpenLREncoderParameter.Builder().with(map).with(encoderConfig).buildParameter();
        List<Line> lines = new ArrayList<>();
        lines.add(map.getLine(1));
        lines.add(map.getLine(2));
        lines.add(map.getLine(5));
        lines.add(map.getLine(6));
        Location loc1 = LocationFactory.createLineLocation("ProtoTypeTesting", lines);
        OpenLREncoder encoder = new openlr.encoder.OpenLREncoder();
        LocationReferenceHolder locationReferenceHolder = encoder.encodeLocation(params, loc1);
        assertEquals(locationReferenceHolder.getLRPs().size(),2);
        assertEquals(locationReferenceHolder.getLRPs().get(0).getDistanceToNext(),286);
        assertEquals(locationReferenceHolder.getLRPs().get(0).getLongitudeDeg(),13.45252);
        assertEquals(locationReferenceHolder.getLRPs().get(0).getLatitudeDeg(),52.50444);
        assertEquals(locationReferenceHolder.getLRPs().get(1).getLongitudeDeg(),13.45505);
        assertEquals(locationReferenceHolder.getLRPs().get(1).getLatitudeDeg(),52.502817);
    }
}
