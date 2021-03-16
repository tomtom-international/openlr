package org.openlr.sample;

import openlr.*;
import openlr.binary.ByteArray;
import openlr.binary.OpenLRBinaryDecoder;
import openlr.binary.impl.LocationReferenceBinaryImpl;
import openlr.decoder.OpenLRDecoderParameter;
import openlr.location.LineLocation;
import openlr.location.Location;
import openlr.location.PointAlongLocation;
import openlr.map.InvalidMapDataException;
import openlr.map.Line;
import openlr.map.MapDatabase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openlr.sample.map.SampleMapDatabaseFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * Some examples showing how to use the OpenLR reference implementation to decode OpenLR location references to locations.
 */
public class DecodeTest {
    /**
     * The OpenLR decoder.
     */
    private OpenLRDecoder decoder;

    /**
     * The connector to the map.
     */
    private MapDatabase mapDatabase;

    /**
     * Configuration options to pass to the decoder.
     */
    private OpenLRDecoderParameter decoderParameter;

    /**
     * Build the decoder and the map connector needed for decoding.
     *
     * @throws IOException if the geojson stream of map data cannot be read.
     * @throws InvalidMapDataException if the map data is invalid.
     */
    @Before
    public void setUp() throws IOException, InvalidMapDataException {
        // Construct the OpenLR decoder
        decoder = new openlr.decoder.OpenLRDecoder();

        // Create an input stream to read in the geojson map data
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("geojson-map.json");

        // Build the map connector from the geojson map data
        SampleMapDatabaseFactory sampleMapDatabaseFactory = new SampleMapDatabaseFactory();
        mapDatabase = sampleMapDatabaseFactory.create(inputStream);

        // Create the OpenLR binary format decoder and add to the list of format decoders
        PhysicalDecoder binaryDecoder = new OpenLRBinaryDecoder();
        List<PhysicalDecoder> physicalDecoders = Arrays.asList(binaryDecoder);

        // Build the decoder configuration
        decoderParameter = new OpenLRDecoderParameter.Builder()
                .with(mapDatabase)
                .with(physicalDecoders)
                .buildParameter();
    }

    /**
     * Decode an OpenLR line location reference to a line location. A line location is a path in the road network.
     *
     * @throws PhysicalFormatException if the location reference format cannot be read.
     * @throws OpenLRProcessingException if the location reference cannot be matched to the map.
     */
    @Test
    public void decodeLineLocationReference() throws PhysicalFormatException, OpenLRProcessingException {
        // Build the location reference from the base-64 string
        ByteArray byteArray = new ByteArray("CwmShiVYczPJBgCs/y0zAQ==");
        LocationReference locationReference = new LocationReferenceBinaryImpl("locationreference", byteArray);

        // Decode the location reference onto the map
        Location location = decoder.decode(decoderParameter, locationReference);

        // Ensure that the decoding process was successful
        Assert.assertTrue(location.isValid());

        // Ensure that the location found is a line location
        Assert.assertTrue(location instanceof LineLocation);

        // Cast to a line location
        LineLocation lineLocation = (LineLocation) location;

        // Get the sequence of lines that form the path
        List<Line> locationLines = lineLocation.getLocationLines();

        // Ensure that the expected number of lines was found
        Assert.assertEquals(3, locationLines.size());

        // Get the lines
        Line line1 = locationLines.get(0);
        Line line2 = locationLines.get(1);
        Line line3 = locationLines.get(2);

        // Ensure that expected lines were found
        Assert.assertEquals(8717174L, line1.getID());
        Assert.assertEquals(8717175L, line2.getID());
        Assert.assertEquals(109783L, line3.getID());

        // Ensure that there are no offsets and the path spans the lines completely
        Assert.assertEquals(0, lineLocation.getPositiveOffset());
        Assert.assertEquals(0, lineLocation.getNegativeOffset());
    }

    /**
     * Decode an OpenLR line location reference to a line location. A line location is a path in the road network.
     * In this example the location reference contains offsets meaning that the path does not start and end
     * at nodes in the road network.
     *
     * @throws PhysicalFormatException if the location reference format cannot be read.
     * @throws OpenLRProcessingException if the location reference cannot be matched to the map.
     */
    @Test
    public void decodeLineLocationReferenceWithOffsets() throws PhysicalFormatException, OpenLRProcessingException {
        // Build the location reference from the base-64 string
        ByteArray byteArray = new ByteArray("CwmTaSVYpTPZCP4a/5UjYQUH");
        LocationReference locationReference = new LocationReferenceBinaryImpl("locationreference", byteArray);

        // Decode the location reference onto the map
        Location location = decoder.decode(decoderParameter, locationReference);

        // Ensure that the decoding process was successful
        Assert.assertTrue(location.isValid());

        // Ensure that the location found is a line location
        Assert.assertTrue(location instanceof LineLocation);

        // Cast to a line location
        LineLocation lineLocation = (LineLocation) location;

        // Get the sequence of lines that form the path
        List<Line> locationLines = lineLocation.getLocationLines();

        // Ensure that the expected number of lines was found
        Assert.assertEquals(4, locationLines.size());

        // Get the lines
        Line line1 = locationLines.get(0);
        Line line2 = locationLines.get(1);
        Line line3 = locationLines.get(2);
        Line line4 = locationLines.get(3);

        // Ensure that expected lines were found
        Assert.assertEquals(1653344L, line1.getID());
        Assert.assertEquals(4997411L, line2.getID());
        Assert.assertEquals(5359424L, line3.getID());
        Assert.assertEquals(5359425L, line4.getID());

        // Ensure that there are no offsets and the path spans the lines completely
        Assert.assertEquals(11, lineLocation.getPositiveOffset());
        Assert.assertEquals(14, lineLocation.getNegativeOffset());
    }

    /**
     * Decode an OpenLR point along line location reference to a line location.
     *
     * @throws PhysicalFormatException if the location reference format cannot be read.
     * @throws OpenLRProcessingException if the location reference cannot be matched to the map.
     */
    @Test
    public void decodePointAlongLineLocationReference() throws PhysicalFormatException, OpenLRProcessingException {
        // Build the location reference from the base-64 string
        ByteArray byteArray = new ByteArray("KwmTQyVYUDPRA/+y/2czQTk=");
        LocationReference locationReference = new LocationReferenceBinaryImpl("locationreference", byteArray);

        // Decode the location reference onto the map
        Location location = decoder.decode(decoderParameter, locationReference);

        // Ensure that the decoding process was successful
        Assert.assertTrue(location.isValid());

        // Ensure that the location found is a point along line location
        Assert.assertTrue(location instanceof PointAlongLocation);

        // Cast to a point along line location
        PointAlongLocation pointAlongLocation = (PointAlongLocation) location;

        // Get the line on which the point lies
        Line line = pointAlongLocation.getPoiLine();

        // Ensure the point lies on the correct line
        Assert.assertEquals(109782L, line.getID());

        // Ensure that the distance along the line is correct
        Assert.assertEquals(40, pointAlongLocation.getPositiveOffset());
    }
}
