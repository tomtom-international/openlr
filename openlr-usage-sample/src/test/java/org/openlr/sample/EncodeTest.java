package org.openlr.sample;

import openlr.LocationReference;
import openlr.OpenLREncoder;
import openlr.OpenLRProcessingException;
import openlr.PhysicalEncoder;
import openlr.binary.ByteArray;
import openlr.binary.OpenLRBinaryEncoder;
import openlr.encoder.LocationReferenceHolder;
import openlr.encoder.OpenLREncoderParameter;
import openlr.location.Location;
import openlr.location.LocationFactory;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Some examples showing how to use the OpenLR reference implementation to encode locations to OpenLR location references.
 */
public class EncodeTest {
    /**
     * The OpenLR encoder.
     */
    private OpenLREncoder encoder;

    /**
     * The connector to the map.
     */
    private MapDatabase mapDatabase;

    /**
     * Configuration options to pass to the OpenLR encoder.
     */
    private OpenLREncoderParameter encoderParameter;

    /**
     * Build the encoder and the map connector needed for encoding.
     *
     * @throws IOException if the geojson stream of map data cannot be read.
     * @throws InvalidMapDataException if the map data is invalid.
     */
    @Before
    public void setUp() throws IOException, InvalidMapDataException {
        // Construct the OpenLR encoder
        encoder = new openlr.encoder.OpenLREncoder();

        // Create an input stream to read in the geojson map data
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("geojson-map.json");

        // Build the map connector from the geojson map data
        SampleMapDatabaseFactory sampleMapDatabaseFactory = new SampleMapDatabaseFactory();
        mapDatabase = sampleMapDatabaseFactory.create(inputStream);

        // Create the OpenLR binary format encoder and add to the list of format encoders
        PhysicalEncoder binaryEncoder = new OpenLRBinaryEncoder();
        List<PhysicalEncoder> physicalEncoders = Arrays.asList(binaryEncoder);

        // Build the encoder configuration
        encoderParameter = new OpenLREncoderParameter.Builder()
                .with(mapDatabase)
                .with(physicalEncoders)
                .buildParameter();
    }

    /**
     * An example showing the encoding to OpenLR of a line location. A line location is a path in the road network.
     *
     * @throws OpenLRProcessingException if the line location cannot be encoded to an OpenLR location reference.
     */
    @Test
    public void encodeLineLocation() throws OpenLRProcessingException {
        // Lookup the sequence of lines that form the path in the map connector
        List<Line> lines = Stream.of(8717174L, 8717175L, 109783L)
                .map(mapDatabase::getLine)
                .collect(Collectors.toList());

        // Build the line location
        Location location = LocationFactory.createLineLocation("location", lines);

        // Encode the line location to an OpenLR location reference
        LocationReferenceHolder locationReferenceHolder = encoder.encodeLocation(encoderParameter, location);

        // Ensure that the encoding succeeded and a location reference was produced
        Assert.assertTrue(locationReferenceHolder.isValid());

        // Extract the location reference in the OpenLR binary format
        LocationReference binaryLocationReference = locationReferenceHolder.getLocationReference("binary");
        ByteArray locationReferenceData = (ByteArray) binaryLocationReference.getLocationReferenceData();
        String base64String = locationReferenceData.getBase64Data();

        // Ensure that the location reference is as expected
        Assert.assertEquals("CwmShiVYczPJBgCs/y0zAQ==", base64String);
    }

    /**
     * An example showing the encoding to OpenLR of a line location that does not begin or end at a node.
     * The position along the first line where the line location begins is expressed via the start offset.
     * The position back along the last line where the line location ends is expressed via the negative offset.
     *
     * @throws OpenLRProcessingException if the line location cannot be encoded to an OpenLR location reference.
     */
    @Test
    public void encodeLineLocationWithOffsets() throws OpenLRProcessingException {
        // Lookup the sequence of lines that form the path in the map connector
        List<Line> lines = Stream.of(1653344L, 4997411L, 5359424L, 5359425L)
                .map(mapDatabase::getLine)
                .collect(Collectors.toList());

        // The offset in meters from the start of the first line to where the line location begins
        int positiveOffset = 11;

        // The offset in meters back along the last line where the line location ends
        int negativeOffset = 14;

        // Create the line location
        Location location = LocationFactory.createLineLocationWithOffsets("location", lines, positiveOffset, negativeOffset);

        // Encode the line location to an OpenLR location reference
        LocationReferenceHolder locationReferenceHolder = encoder.encodeLocation(encoderParameter, location);

        // Ensure that the encoding succeeded and a location reference was produced
        Assert.assertTrue(locationReferenceHolder.isValid());

        // Extract the location reference in the OpenLR binary format
        LocationReference binaryLocationReference = locationReferenceHolder.getLocationReference("binary");
        ByteArray locationReferenceData = (ByteArray) binaryLocationReference.getLocationReferenceData();
        String base64String = locationReferenceData.getBase64Data();

        // Ensure that the location reference is as expected
        Assert.assertEquals("CwmTaSVYpTPZCP4a/5UjYQUH", base64String);
    }

    /**
     * An example showing the encoding to OpenLR of a point along a line in the road network.
     *
     * @throws InvalidMapDataException if the point along line location cannot be constructed
     * @throws OpenLRProcessingException if the point along line cannot be encoded to an OpenLR location reference.
     */
    @Test
    public void encodePointAlongLine() throws InvalidMapDataException, OpenLRProcessingException {
        // Lookup the line in the map connector
        Line line = mapDatabase.getLine(109782L);

        // The offset in meters along the line where the point is found
        int positiveOffset = 40;

        // Create the point along line location
        Location location = LocationFactory.createPointAlongLineLocation("location", line, positiveOffset);

        // Encode the point along line location to an OpenLR location reference
        LocationReferenceHolder locationReferenceHolder = encoder.encodeLocation(encoderParameter, location);

        // Ensure that the encoding succeeded and a location reference was produced
        Assert.assertTrue(locationReferenceHolder.isValid());

        // Extract the location reference in the OpenLR binary format
        LocationReference binaryLocationReference = locationReferenceHolder.getLocationReference("binary");
        ByteArray locationReferenceData = (ByteArray) binaryLocationReference.getLocationReferenceData();
        String base64String = locationReferenceData.getBase64Data();

        // Ensure that the location reference is as expected
        Assert.assertEquals("KwmTQyVYUDPRA/+y/2czQTk=", base64String);
    }
}
