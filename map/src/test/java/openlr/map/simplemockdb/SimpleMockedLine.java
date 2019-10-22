package openlr.map.simplemockdb;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import openlr.map.Line;
import openlr.map.Node;
import openlr.map.GeoCoordinates;
import openlr.map.FormOfWay;
import openlr.map.FunctionalRoadClass;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.InvalidMapDataException;


import java.awt.geom.Point2D;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;


public class SimpleMockedLine implements Line {

    private generated.Line xmlLine;
    private static GeometryFactory factory = new GeometryFactory();
    private Node startNode;
    private Node endNode;

    public LineString getLineString() {
        return lineString;
    }

    private LineString lineString;

    public SimpleMockedLine(generated.Line xmlLine, Node startNode, Node endNode) {
        this.xmlLine = xmlLine;
        this.startNode = startNode;
        this.endNode = endNode;
        List<Coordinate> shapePoints = new ArrayList<>();
        shapePoints.add(new Coordinate(this.startNode.getLongitudeDeg(), this.startNode.getLatitudeDeg()));
        shapePoints.add(new Coordinate(this.endNode.getLongitudeDeg(), this.endNode.getLatitudeDeg()));
        xmlLine.getShapePoint().forEach(point -> {
            shapePoints.add(new Coordinate(point.getLongitude(), point.getLatitude()));
        });
        this.lineString = factory.createLineString((Coordinate[]) shapePoints.toArray());
    }

    public Node getStartNode() {
        return this.startNode;
    }

    public List<Long> getRestrictions() {
        return xmlLine.getRestrictions().stream().map(line -> line.longValue()).collect(Collectors.toList());
    }

    public Node getEndNode() {
        return this.endNode;
    }

    public FormOfWay getFOW() {

        return FormOfWay.getFOWs().get(xmlLine.getFow());
    }

    public FunctionalRoadClass getFRC() {
        return FunctionalRoadClass.getFRCs().get(xmlLine.getFrc());
    }

    /**
     * @deprecated
     */
    @Deprecated
    public Point2D.Double getPointAlongLine(int var1) {
        return null;
    }

    public GeoCoordinates getGeoCoordinateAlongLine(int var1) throws SimpleMockedException {
        try {
            Geometry gm = this.lineString.buffer(var1);
            return new GeoCoordinatesImpl(gm.getCoordinate().x, gm.getCoordinate().y);
        } catch (InvalidMapDataException e) {
            throw new SimpleMockedException(e.getMessage());
        }
    }

    public int getLineLength() {
        return (int) this.lineString.getLength();
    }

    public long getID() {
        return xmlLine.getId().longValue();
    }

    public Iterator<Line> getPrevLines() {
        return this.startNode.getIncomingLines();
    }

    /**
     * @deprecated
     */
    @Deprecated
    public java.awt.geom.Path2D.Double getShape() {
        return null;
    }

    public Iterator<Line> getNextLines() {
        return this.endNode.getOutgoingLines();
    }

    public boolean equals(Object var1) {
        if (var1 instanceof SimpleMockedLine) {
            return this.getID() == ((SimpleMockedLine) var1).getID();
        }
        return false;
    }

    public int hashCode() {
        return this.hashCode();
    }

    public int distanceToPoint(double var1, double var3) {
        Geometry geometry = factory.createPoint(new Coordinate(var1, var3));
        return (int) Math.round(lineString.distance(geometry));
    }

    public int measureAlongLine(double var1, double var3) {
        Geometry geometry = factory.createPoint(new Coordinate(var1, var3));
        return (int) Math.round(lineString.distance(geometry));
    }

    public List<GeoCoordinates> getShapeCoordinates() {
        List<GeoCoordinates> crds = new ArrayList<>();
        crds.add(startNode.getGeoCoordinates());
        crds.add(endNode.getGeoCoordinates());
        return crds;
    }

    public Map<Locale, List<String>> getNames() {
        return null;
    }
}
