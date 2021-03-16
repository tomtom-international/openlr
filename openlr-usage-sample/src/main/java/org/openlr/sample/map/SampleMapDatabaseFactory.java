package org.openlr.sample.map;

import com.fasterxml.jackson.databind.ObjectMapper;
import openlr.map.*;
import org.geojson.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Creates a map connector for a little map snippet loaded from a geojson feature collection. The feature collection
 * contains node features and road link features. The node features have point geometry and the road link features have line
 * string geometry. The feature properties store the properties of the nodes and lines.
 *
 * A road link can have direction both (1), in just a forward direction (2) and in just a negative direction (3) relative to the road
 * link geometry. For road links going in both directions, two lines are constructed, one going in a forward direction and one in a
 * backward direction relative to the road link.  For road links going in either just a forward or backward direction, a
 * single line is constructed.
 */
public class SampleMapDatabaseFactory {
    /**
     * Create a map connector from a geojson file.
     *
     * @param geoJsonFile the geojson file containing the map data.
     * @return a map connector for the map.
     * @throws IOException if the file cannot be read.
     * @throws InvalidMapDataException if the map data is invalid.
     */
    public MapDatabase create(File geoJsonFile) throws IOException, InvalidMapDataException {
        try (InputStream inputStream = new FileInputStream(geoJsonFile)) {
            return create(inputStream);
        }
    }

    /**
     * Create a map connector for a geojson input stream of map data.
     *
     * @param inputStream the geojson input stream.
     * @return a map connector for the map.
     * @throws IOException if the input stream cannot be read.
     * @throws InvalidMapDataException if the map data is invalid.
     */
    public MapDatabase create(InputStream inputStream) throws IOException, InvalidMapDataException {
        ObjectMapper objectMapper = new ObjectMapper();
        FeatureCollection featureCollection = objectMapper.readValue(inputStream, FeatureCollection.class);
        return create(featureCollection);
    }

    /**
     * Create a map connector for a geojson feature collection of map data.
     *
     * @param featureCollection the geojson feature collection.
     * @return a map connector for the map.
     * @throws InvalidMapDataException if the map data is invalid.
     */
    public MapDatabase create(FeatureCollection featureCollection) throws InvalidMapDataException {
        // A lookup index for nodes based on their identifier
        Map<Long, Node> nodeIndex = new HashMap<>();

        // Read in all nodes
        for (Feature feature : featureCollection) {
            // Nodes are features with point geometry
            if (feature.getGeometry() instanceof Point) {
                // Build the node from the feature

                // Read in the feature properties
                Map<String, Object> properties = feature.getProperties();

                // Read in the identifier of the node
                Long id = ((Integer) properties.get("id")).longValue();

                // Read the point geometry of the node
                Point point = (Point) feature.getGeometry();

                // Build the node coordinate
                GeoCoordinates coordinate = new GeoCoordinatesImpl(
                        point.getCoordinates().getLongitude(),
                        point.getCoordinates().getLatitude());

                // Build the node
                SampleNode sampleNode = new SampleNode(id, coordinate);

                // Insert the node into the index
                nodeIndex.put(id, sampleNode);
            }
        }

        // A lookup index for lines based on their identifier
        Map<Long, Line> lineIndex = new HashMap<>();

        // All possible functional road classes
        FunctionalRoadClass[] functionalRoadClasses = FunctionalRoadClass.values();

        // All possible forms of way
        FormOfWay[] formOfWays = FormOfWay.values();

        // Read in all road links
        for (Feature feature : featureCollection) {
            // Road links are features with a line string geometry
            if (feature.getGeometry() instanceof LineString) {
                // Build the lines from the road link

                // Read in the feature properties
                Map<String, Object> properties = feature.getProperties();

                // Read the road link identifier
                Long id = ((Integer) properties.get("id")).longValue();

                // Read the start and end node identifiers
                Long startId = ((Integer) properties.get("startId")).longValue();
                Long endId = ((Integer) properties.get("endId")).longValue();

                // Lookup the nodes in the index
                SampleNode startNode = (SampleNode) nodeIndex.get(startId);
                SampleNode endNode = (SampleNode) nodeIndex.get(endId);

                // Read the direction of the road link
                Integer direction = (Integer) properties.get("direction");

                // Read the functional road class of the road link
                Integer frc = (Integer) properties.get("frc");
                FunctionalRoadClass functionalRoadClass = functionalRoadClasses[frc];

                // Read the form of way of the road link
                Integer fow = (Integer) properties.get("fow");
                FormOfWay formOfWay = formOfWays[fow];

                // Read the length of the road link
                Integer length = (Integer) properties.get("length");

                // Read the line string geometry of the road link
                LineString lineString = (LineString) feature.getGeometry();

                // Build the shape point coordinates of the road link
                List<GeoCoordinates> coordinates = new ArrayList<>();

                for (LngLatAlt lngLatAlt : lineString.getCoordinates()) {
                    GeoCoordinates coordinate = new GeoCoordinatesImpl(lngLatAlt.getLongitude(), lngLatAlt.getLatitude());
                    coordinates.add(coordinate);
                }

                // The road link goes in both direction or in just a forward direction
                if (direction == 1 || direction == 2) {
                    // Build a line in a forward direction
                    SampleLine forwardLine = new SampleLine(
                            id, // The forward line takes the identifier of the road link
                            startNode, // The forward line starts at the start node
                            endNode, // The forward line ends at the end node
                            functionalRoadClass,
                            formOfWay,
                            length,
                            coordinates // The forward line has the same shape coordinates as the road link
                    );

                    // Insert the line in the index
                    lineIndex.put(id, forwardLine);

                    // Attach the line as an outgoing line of the start node
                    startNode.addOutgoingLine(forwardLine);

                    // Attach the line as an incoming line of the end node
                    endNode.addIncomingLine(forwardLine);
                }

                // The road link goes in both directions or in just a backward direction
                if (direction == 1 || direction == 3) {
                    // Reverse the road link line string geometry
                    List<GeoCoordinates> reversedCoordinates = new ArrayList<>(coordinates);
                    Collections.reverse(reversedCoordinates);

                    // Build a line in a backward direction
                    SampleLine backwardLine = new SampleLine(
                            -id, // The backward line takes the negation of the identifier of the road link
                            endNode, // The backward line starts at the end node
                            startNode, // The backward line ends at the start node
                            functionalRoadClass,
                            formOfWay,
                            length,
                            reversedCoordinates // The backward line has the reversed shape coordinates of the road link
                    );

                    // Insert the line in the index
                    lineIndex.put(-id, backwardLine);

                    // Attach the line as an outgoing line of the end node
                    endNode.addOutgoingLine(backwardLine);

                    // Attach the line as an incoming line of the start node
                    startNode.addIncomingLine(backwardLine);
                }
            }
        }

        // Build the map connector
        return new SampleMapDatabase(nodeIndex, lineIndex);
    }
}
