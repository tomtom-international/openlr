package openlr.map.simplemockdb;

import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.LineString;
import openlr.map.simplemockdb.schema.SimpleMockedMapDatabase;
import openlr.map.GeoCoordinates;
import openlr.map.Line;
import openlr.map.MapDatabase;
import openlr.map.Node;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.awt.geom.Rectangle2D;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class OpenLRMapDatabaseAdaptor implements MapDatabase {
    private List<SimpleMockedNode> nodes;
    private List<SimpleMockedLine> lines;

    private OpenLRMapDatabaseAdaptor(List<SimpleMockedNode> nodes, List<SimpleMockedLine> lines) {
        this.nodes = nodes;
        this.lines = lines;
    }

    private static SimpleMockedMapDatabase parseXmlToSimpleMockedDatabase(InputStream xmlMap) throws SimpleMockedException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(SimpleMockedMapDatabase.class);

            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            return (SimpleMockedMapDatabase) unmarshaller.unmarshal(xmlMap);
        } catch (JAXBException e) {
            throw new SimpleMockedException(e.getMessage());
        }
    }


    private static List<SimpleMockedNode> createNodes(SimpleMockedMapDatabase simpleMockedMapDatabase) {
        return simpleMockedMapDatabase.getNode().stream().map(node -> SimpleMockedNode.from(node)).collect(Collectors.toList());
    }

    private static List<SimpleMockedLine> createLines(SimpleMockedMapDatabase simpleMockedMapDatabase, List<SimpleMockedNode> nodes) {
        return simpleMockedMapDatabase.getLine().stream().map(
                line -> {
                    Node startNode = nodes.stream().filter(node -> node.getID() == line.getStart().longValue()).findFirst().orElseThrow(() -> new SimpleMockedException("Start Node " + line.getStart().longValue() +
                            " of line " + line.getId().longValue() + " does not exist"));
                    Node endNode = nodes.stream().filter(node -> node.getID() == line.getEnd().longValue()).findFirst().orElseThrow(() -> new SimpleMockedException("End Node " + line.getEnd().longValue() +
                            " of line " + line.getId().longValue() + " does not exist"));
                    return SimpleMockedLine.from(line, startNode, endNode);
                }
        ).collect(Collectors.toList());
    }

    private static void connectLines(List<SimpleMockedNode> nodes, List<SimpleMockedLine> lines) {
        nodes.stream().forEach(
                node -> node.setConnections(lines)
        );
    }

    public static OpenLRMapDatabaseAdaptor from(InputStream xmlMap) {
        SimpleMockedMapDatabase simpleMockedMapDatabase = parseXmlToSimpleMockedDatabase(xmlMap);
        List<SimpleMockedNode> nodes = createNodes(simpleMockedMapDatabase);
        List<SimpleMockedLine> lines = createLines(simpleMockedMapDatabase, nodes);
        connectLines(nodes, lines);
        return new OpenLRMapDatabaseAdaptor(nodes, lines);
    }

    public boolean hasTurnRestrictions() {
        return (lines.stream().filter(line -> {
            return !line.getRestrictions().isEmpty();
        }).count() != 0);
    }

    public Line getLine(long id) {
        return lines.stream().filter(line -> line.getID() == id).findFirst().orElseThrow(() -> new SimpleMockedException("Line " + id + " does not exist"));
    }

    public Node getNode(long id) {
        return nodes.stream().filter(node -> node.getID() == id).findFirst().orElseThrow(() -> new SimpleMockedException("Node " + id + "does not exist"));
    }

    public Iterator<Node> findNodesCloseByCoordinate(double longitude, double latitude, int distance) {
        Envelope envelope = new Envelope(new Coordinate(longitude, latitude));
        envelope.expandBy(distance);
        return nodes.stream().filter(node -> {
            GeoCoordinates crd = node.getGeoCoordinates();
            return envelope.contains(new Coordinate(crd.getLongitudeDeg(), crd.getLatitudeDeg()));
        }).map(x -> ((Node) x)).iterator();
    }

    public Iterator<Line> findLinesCloseByCoordinate(double longitude, double latitude, int distance) {
        Envelope envelope = new Envelope(new Coordinate(longitude, latitude));
        envelope.expandBy(distance);
        return lines.stream().filter(line -> {
            LineString lineString = line.getLineString();
            return envelope.intersects(lineString.getEnvelopeInternal());
        }).map(x -> ((Line) x)).iterator();
    }

    public boolean hasTurnRestrictionOnPath(List<? extends Line> path) {
        boolean hasNoRestriction = true;
        for (int index = 0; index < path.size() - 1 && hasNoRestriction; ++index) {
            long current = path.get(index).getID();
            long next = path.get(index + 1).getID();
            SimpleMockedLine line = lines.stream().filter(jaxLine -> jaxLine.getID() == current).findFirst().orElseThrow(() -> new SimpleMockedException("Line " + current + " does not exist"));
            hasNoRestriction = line.getRestrictions().contains(next);
        }
        return !hasNoRestriction;
    }

    public Iterator<Node> getAllNodes() {
        return nodes.stream()
                .map(x -> ((Node) x)).iterator();
    }

    public Iterator<Line> getAllLines() {
        return lines.stream().map(x -> ((Line) x)).iterator();
    }

    public Rectangle2D.Double getMapBoundingBox() {
        return null;
    }

    public int getNumberOfNodes() {
        return nodes.size();
    }

    public int getNumberOfLines() {
        return lines.size();
    }
}
