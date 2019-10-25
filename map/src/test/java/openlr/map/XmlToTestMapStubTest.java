package openlr.map;

import openlr.map.teststubs.OpenLRMapDatabaseAdaptor;
import openlr.map.utils.GeometryUtils;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import  openlr.map.teststubs.schema.TestMapStub;

public class XmlToTestMapStubTest {
    @Test
    public void testXmlToObject() throws JAXBException {

        TestMapStub smd;

        URL testDb = XmlToTestMapStubTest.class.getClassLoader().getResource("openlr/map/stub/SampleTestMapStub.xml");

        File file = new File(testDb.getFile());

        JAXBContext jaxbContext = JAXBContext.newInstance(TestMapStub.class);

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        smd = (TestMapStub) unmarshaller.unmarshal(file);

        assertEquals(smd.getLine().size(), 2);
        assertEquals(smd.getNode().size(), 3);
        assertEquals(smd.getNode().stream().filter(node -> node.getId().longValue() == 1).count(), 1);
        assertEquals(smd.getLine().stream().filter(line -> line.getId().longValue() == 1).count(), 1);
        assertEquals(smd.getLine().stream().filter(line -> line.getId().longValue() == 1).findFirst().get().getIntermediatePoint().size(), 1);
    }

    @Test
    public  void  testXmlToSimpleMockedDatabaseAdaptor() throws InvalidMapDataException{
        InputStream mapFile = OpenLRMapDatabaseAdaptor.class.getClassLoader().getResourceAsStream("teststubs/TestMapStub.xml");
        OpenLRMapDatabaseAdaptor map = OpenLRMapDatabaseAdaptor.from(mapFile);
        Line testLine = map.getLine(1);
        GeoCoordinates pointOnLine = new GeoCoordinatesImpl(13.45300,52.50431);
        int length = testLine.measureAlongLine(pointOnLine.getLongitudeDeg(),pointOnLine.getLatitudeDeg());
        GeoCoordinates geoCoordinates = testLine.getGeoCoordinateAlongLine(length);
        assertTrue(GeometryUtils.distance(pointOnLine,geoCoordinates) < 2);
    }


}
