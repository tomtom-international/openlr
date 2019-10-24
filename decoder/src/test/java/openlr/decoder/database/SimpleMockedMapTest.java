package openlr.decoder.database;

import openlr.OpenLRProcessingException;
import openlr.PhysicalFormatException;
import openlr.binary.ByteArray;
import openlr.binary.OpenLRBinaryDecoder;
import openlr.binary.impl.LocationReferenceBinaryImpl;
import openlr.decoder.OpenLRDecoder;
import openlr.decoder.OpenLRDecoderParameter;
import openlr.location.Location;
import openlr.map.simplemockdb.OpenLRMapDatabaseAdaptor;
import openlr.properties.OpenLRPropertiesReader;
import openlr.rawLocRef.RawLocationReference;
import org.apache.commons.configuration.Configuration;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.Test;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleMockedMapTest {

    @Test
    void decoding() throws OpenLRProcessingException, PhysicalFormatException {
        String openlr = "CwmQ9SVWJS2qBAD9/14tCQ==";
        OpenLRBinaryDecoder binaryDecoder = new OpenLRBinaryDecoder();
        ByteArray byteArray = new ByteArray(Base64.getDecoder().decode(openlr));
        LocationReferenceBinaryImpl locationReferenceBinary = new LocationReferenceBinaryImpl("ProtoTypeTesting", byteArray);
        RawLocationReference rawLocationReference = binaryDecoder.decodeData(locationReferenceBinary);

        InputStream mapFile = OpenLRMapDatabaseAdaptor.class.getClassLoader().getResourceAsStream("simplemockedmaps/SimpleMockedTestMap.xml");
        OpenLRMapDatabaseAdaptor map = OpenLRMapDatabaseAdaptor.from(mapFile);
        Configuration decoderConfig = OpenLRPropertiesReader.loadPropertiesFromFile(new File(SimpleMockedMapTest.class.getClassLoader().getResource("OpenLR-Decoder-Properties.xml").getFile()));
        OpenLRDecoderParameter params = new OpenLRDecoderParameter.Builder().with(map).with(decoderConfig).buildParameter();
        OpenLRDecoder decoder = new openlr.decoder.OpenLRDecoder();
        Location location = decoder.decodeRaw(params, rawLocationReference);
        assertNotNull(location);
        assertEquals(location.getLocationLines().size(), 4);
        List<Long> expectedLines = new ArrayList<Long>();
        expectedLines.add(new Long(1));
        expectedLines.add(new Long(2));
        expectedLines.add(new Long(5));
        expectedLines.add(new Long(6));
        assertEquals(location.getLocationLines().stream().map(line -> {
            return line.getID();
        }).collect(Collectors.toList()), expectedLines);
    }
}