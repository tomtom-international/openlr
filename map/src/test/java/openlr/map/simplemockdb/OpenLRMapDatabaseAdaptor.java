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

public class OpenLRMapDatabaseAdaptor implements MapDatabase {
    private Map<Long, SimpleMockedNode> nodes = new HashMap<>();
    private Map<Long, SimpleMockedLine> lines = new HashMap<>();
    private SimpleMockedMapDatabase simpleMockedMapDatabase;

    private OpenLRMapDatabaseAdaptor(SimpleMockedMapDatabase simpleMockedMapDatabase) {
        this.simpleMockedMapDatabase = simpleMockedMapDatabase;
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


    private void createNodes() {
        this.simpleMockedMapDatabase.getNode().stream().forEach(node -> {
            nodes.put(node.getId().longValue(), SimpleMockedNode.from(node));
        });
    }

    private void createLines() {
        simpleMockedMapDatabase.getLine().forEach(
                line -> {
                    Node startNode = this.nodes.get(line.getStart().longValue());
                    Node endNode = this.nodes.get(line.getEnd().longValue());
                    SimpleMockedLine simpleMockedLine = SimpleMockedLine.from(line, startNode, endNode);
                    this.lines.put(simpleMockedLine.getID(), simpleMockedLine);
                }
        );
    }

    private void connectLines() {
        this.nodes.forEach(
                (id, node) -> {
                    node.setConnections(lines.values());
                }
        );
    }

    public static OpenLRMapDatabaseAdaptor from(InputStream xmlMap) {
        SimpleMockedMapDatabase simpleMockedMapDatabase = parseXmlToSimpleMockedDatabase(xmlMap);
        OpenLRMapDatabaseAdaptor map = new OpenLRMapDatabaseAdaptor(simpleMockedMapDatabase);
        map.createNodes();
        map.createLines();
        map.connectLines();
        return map;
    }

    public boolean hasTurnRestrictions() {
        return (lines.values().stream().filter(line -> {
            return !line.getRestrictions().isEmpty();
        }).count() != 0);
    }

    public Line getLine(long id) {
        return lines.get(id);
    }

    public Node getNode(long id) {
        return nodes.get(id);
    }

    public Iterator<Node> findNodesCloseByCoordinate(double longitude, double latitude, int distance) {
        Envelope envelope = new Envelope(new Coordinate(longitude, latitude));
        envelope.expandBy(distance);
        return nodes.values().stream().filter(node -> {
            GeoCoordinates crd = node.getGeoCoordinates();
            return envelope.contains(new Coordinate(crd.getLongitudeDeg(), crd.getLatitudeDeg()));
        }).map(x -> ((Node) x)).iterator();
    }

    public Iterator<Line> findLinesCloseByCoordinate(double longitude, double latitude, int distance) {
        Envelope envelope = new Envelope(new Coordinate(longitude, latitude));
        envelope.expandBy(distance);
        return lines.values().stream().filter(line -> {
            LineString lineString = line.getLineString();
            return envelope.intersects(lineString.getEnvelopeInternal());
        }).map(x -> ((Line) x)).iterator();
    }

    public boolean hasTurnRestrictionOnPath(List<? extends Line> path) {
        boolean hasNoRestriction = true;
        for (int index = 0; index < path.size() - 1 && hasNoRestriction; ++index) {
            long current = path.get(index).getID();
            long next = path.get(index + 1).getID();
            hasNoRestriction = lines.get(current).getRestrictions().contains(next);
        }
        return !hasNoRestriction;
    }

    public Iterator<Node> getAllNodes() {
        return nodes.values().stream()
                .map(x -> ((Node) x)).iterator();
    }

    public Iterator<Line> getAllLines() {
        return lines.values().stream().map(x -> ((Line) x)).iterator();
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
