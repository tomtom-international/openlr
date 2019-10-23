package openlr.map;

import generated.SimpleMockedMapDatabase;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

public class XmlToSimpleMockDatabaseTest {
    @Test

    public void testXmlToObject() throws JAXBException, FileNotFoundException {

        SimpleMockedMapDatabase smd;

        URL testDb = XmlToSimpleMockDatabaseTest.class.getClassLoader().getResource("openlr/map/mockdb/SampleSimpleMockDatabase.xml");

        File file = new File(testDb.getFile());

        JAXBContext jaxbContext = JAXBContext.newInstance(SimpleMockedMapDatabase.class);

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        smd = (SimpleMockedMapDatabase) unmarshaller.unmarshal(file);

        assertEquals(smd.getLine().size(), 2);
        assertEquals(smd.getNode().size(), 3);
        assertEquals(smd.getNode().stream().filter(node -> node.getId().longValue() == 1).count(), 1);
        assertEquals(smd.getLine().stream().filter(line -> line.getId().longValue() == 1).count(), 1);
        assertEquals(smd.getLine().stream().filter(line -> line.getId().longValue() == 1).findFirst().get().getShapePoint().size(), 1);
    }

}
