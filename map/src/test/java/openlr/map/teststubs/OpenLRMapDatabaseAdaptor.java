package openlr.map.teststubs;

import openlr.map.MapDatabase;
import openlr.map.Line;
import openlr.map.Node;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.FormOfWay;
import openlr.map.FunctionalRoadClass;
import openlr.map.GeoCoordinates;
import openlr.map.InvalidMapDataException;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.LineString;

import openlr.map.teststubs.schema.TestMapStub;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.awt.geom.Rectangle2D;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class OpenLRMapDatabaseAdaptor implements MapDatabase {
    private List<NodeStub> nodes;
    private List<LineStub> lines;

    private OpenLRMapDatabaseAdaptor(List<NodeStub> nodes, List<LineStub> lines) {
        this.nodes = nodes;
        this.lines = lines;
    }

    private static TestMapStub parseXmlToTestMapStub(InputStream xmlMap) throws MapStubException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(TestMapStub.class);

            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            return (TestMapStub) unmarshaller.unmarshal(xmlMap);
        } catch (JAXBException e) {
            throw new MapStubException(e.getMessage());
        }
    }


    private static List<NodeStub> createNodes(TestMapStub testMapStub) throws MapStubException {
        return testMapStub.getNode().stream().map(node -> {
                    try {
                        return NodeStub.from(node.getId().longValue(), new GeoCoordinatesImpl(node.getLongitude(), node.getLatitude()),node.hashCode());
                    } catch (InvalidMapDataException e) {
                        throw new MapStubException(e.getMessage());
                    }
                }
        ).collect(Collectors.toList());
    }

    private static List<LineStub> createLines(TestMapStub testMapStub, List<NodeStub> nodes) {
        return testMapStub.getLine().stream().map(
                line -> {
                    Node startNode = nodes.stream().filter(node -> node.getID() == line.getStart().longValue()).findFirst().orElseThrow(() -> new MapStubException("Start Node " + line.getStart().longValue() +
                            " of line " + line.getId().longValue() + " does not exist"));
                    Node endNode = nodes.stream().filter(node -> node.getID() == line.getEnd().longValue()).findFirst().orElseThrow(() -> new MapStubException("End Node " + line.getEnd().longValue() +
                            " of line " + line.getId().longValue() + " does not exist"));
                    long id = line.getId().longValue();
                    List<Long> restrictions = line.getRestrictions().stream().map(restrictionTo -> restrictionTo.longValue()).collect(Collectors.toList());
                    FunctionalRoadClass frc = FunctionalRoadClass.getFRCs().get(line.getFrc());
                    FormOfWay fow = FormOfWay.getFOWs().get(line.getFow());
                    int hashCode = line.hashCode();
                    List<Coordinate> intermediatePoints = line.getIntermediatePoint().stream().map(point -> LineStub.toCartesian(point.getLongitude(), point.getLatitude())).collect(Collectors.toList());
                    return LineStub.from(id, restrictions, frc, fow, hashCode, startNode, endNode, intermediatePoints);
                }
        ).collect(Collectors.toList());
    }

    private static List<NodeStub> connectNodes(List<NodeStub> nodes, List<LineStub> lines) {
        nodes.stream().forEach(
                node -> node.setConnections(lines)
        );
        return nodes;
    }

    public static OpenLRMapDatabaseAdaptor from(InputStream xmlMap) {
        TestMapStub testMapStub = parseXmlToTestMapStub(xmlMap);
        List<NodeStub> nodes = createNodes(testMapStub);
        List<LineStub> lines = createLines(testMapStub, nodes);
        List<NodeStub> connectedNodes = connectNodes(nodes, lines);
        return new OpenLRMapDatabaseAdaptor(connectedNodes, lines);
    }

    public boolean hasTurnRestrictions() {
        return (lines.stream().filter(line -> {
            return !line.getRestrictions().isEmpty();
        }).count() != 0);
    }

    public Line getLine(long id) {
        return lines.stream().filter(line -> line.getID() == id).findFirst().orElseThrow(() -> new MapStubException("Line " + id + " does not exist"));
    }

    public Node getNode(long id) {
        return nodes.stream().filter(node -> node.getID() == id).findFirst().orElseThrow(() -> new MapStubException("Node " + id + "does not exist"));
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
            LineStub line = lines.stream().filter(jaxLine -> jaxLine.getID() == current).findFirst().orElseThrow(() -> new MapStubException("Line " + current + " does not exist"));
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
