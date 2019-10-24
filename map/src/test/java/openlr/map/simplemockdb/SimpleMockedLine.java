package openlr.map.simplemockdb;

import com.vividsolutions.jts.geom.*;
import openlr.map.Line;
import openlr.map.Node;
import openlr.map.GeoCoordinates;
import openlr.map.FormOfWay;
import openlr.map.FunctionalRoadClass;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.InvalidMapDataException;
import org.geotools.geometry.jts.JTS;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeocentricCRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

import java.awt.geom.Point2D;
import java.util.*;
import java.util.stream.Collectors;


public class SimpleMockedLine implements Line {

    private openlr.map.simplemockdb.schema.Line xmlLine;
    private static GeometryFactory factory = JTSFactoryFinder.getGeometryFactory();
    private static MathTransform wgs84ToCartesian;
    private static MathTransform cartesianToWgs843d;

    static {
        try {
            wgs84ToCartesian = CRS.findMathTransform(DefaultGeographicCRS.WGS84, DefaultGeocentricCRS.CARTESIAN);
            cartesianToWgs843d = CRS.findMathTransform(DefaultGeocentricCRS.CARTESIAN, DefaultGeographicCRS.WGS84_3D);
        } catch (FactoryException e) {
            throw new SimpleMockedException(e.getMessage());
        }
    }

    private Node startNode;
    private Node endNode;
    private List<LineSegment> lineSegments;

    public final LineString getLineString() {
        Coordinate[] crds = new Coordinate[getShapeCoordinates().size()];
        crds = (Coordinate[]) getShapeCoordinates().stream().map(shapePoint -> {
            return toCartesian(shapePoint.getLongitudeDeg(), shapePoint.getLatitudeDeg());
        }).collect(Collectors.toList()).toArray(crds);
        return factory.createLineString(crds);
    }


    public static Coordinate toCartesian(double lon, double lat) {
        final Coordinate to = new Coordinate();
        try {
            JTS.transform(new Coordinate(lon, lat, 0), to, wgs84ToCartesian);
            return to;
        } catch (TransformException e) {
            throw new SimpleMockedException(e.getMessage());

        }
    }


    public static GeoCoordinates ToWGS84(Coordinate crd) {
        try {
            final Coordinate to = new Coordinate();
            JTS.transform(crd, to, cartesianToWgs843d);
            return new GeoCoordinatesImpl(to.x, to.y);
        } catch (TransformException e) {
            throw new SimpleMockedException(e.getMessage());

        } catch (InvalidMapDataException e) {
            throw new SimpleMockedException(e.getMessage());

        }
    }

    public void generateShape() {
        int numberOfShapePoints = xmlLine.getIntermediatePoint().size() + 2;
        Coordinate[] shapePoints = new Coordinate[numberOfShapePoints];
        shapePoints[0] = toCartesian(this.startNode.getLongitudeDeg(), this.startNode.getLatitudeDeg());
        for (int index = 0; index < xmlLine.getIntermediatePoint().size(); ++index) {
            openlr.map.simplemockdb.schema.Line.IntermediatePoint shapePoint = xmlLine.getIntermediatePoint().get(index);
            shapePoints[index + 1] = toCartesian(shapePoint.getLongitude(), shapePoint.getLatitude());
        }
        shapePoints[numberOfShapePoints - 1] = toCartesian(this.endNode.getLongitudeDeg(), this.endNode.getLatitudeDeg());
        for (int index = 1; index < shapePoints.length; ++index) {
            Coordinate end = shapePoints[index];
            Coordinate start = shapePoints[index - 1];
            LineSegment segment = new LineSegment(start, end);
            lineSegments.add(segment);
        }

    }


    public static SimpleMockedLine from(openlr.map.simplemockdb.schema.Line xmlLine, Node startNode, Node endNode) {
        SimpleMockedLine simpleMockedLine = new SimpleMockedLine(xmlLine, startNode, endNode);
        simpleMockedLine.generateShape();
        return simpleMockedLine;
    }


    private SimpleMockedLine(openlr.map.simplemockdb.schema.Line xmlLine, Node startNode, Node endNode) {
        this.xmlLine = xmlLine;
        this.startNode = startNode;
        this.endNode = endNode;
        this.lineSegments = new ArrayList<>();
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

    public GeoCoordinates getGeoCoordinateAlongLine(int distance) throws SimpleMockedException {

        int lengthCovered = 0;
        for (LineSegment segment : lineSegments) {
            if (lengthCovered <= distance && lengthCovered + segment.getLength() >= distance) {
                double offset = distance - lengthCovered;
                Coordinate point = segment.pointAlong(segment.getLength() / offset);
                point = segment.project(point);
                double z = segment.p0.z + 1 * (segment.p1.z - segment.p0.z);
                point.setOrdinate(2, z);
                return ToWGS84(point);
            }
            lengthCovered += segment.getLength();
        }
        throw new SimpleMockedException("length is greater than the line length");
    }

    public int getLineLength() {
        return (int) this.lineSegments.stream().mapToDouble(segment -> segment.getLength()).sum();
    }

    public long getID() {
        return xmlLine.getId().longValue();
    }

    public Iterator<Line> getPrevLines() {
        return this.startNode.getIncomingLines();
    }

    /**
     * @deprecated use {@link #getShapeCoordinates()}
     */
    @Deprecated
    public java.awt.geom.Path2D.Double getShape() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
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
        return this.xmlLine.hashCode();
    }

    public int distanceToPoint(double var1, double var3) {
        Geometry geometry = factory.createPoint(toCartesian(var1, var3));
        return (int) Math.round(getLineString().distance(geometry));
    }

    public int measureAlongLine(double lon, double lat) {
        Coordinate crd = toCartesian(lon, lat);
        int segmentLength = 0;
        for (LineSegment segment : lineSegments) {
            if (segment.distance(crd) == 0) {
                Coordinate start = segment.p0;
                return (int) (segmentLength + (new LineSegment(start, crd)).getLength());
            }
            segmentLength += segment.getLength();
        }
        throw new SimpleMockedException("point is not on the line");
    }

    public List<GeoCoordinates> getShapeCoordinates() {
        List<GeoCoordinates> shape = new ArrayList<>();
        for (int index = 0; index < lineSegments.size(); ++index) {
            shape.add(ToWGS84(lineSegments.get(index).p0));

            if (index == lineSegments.size() - 1) {
                shape.add(ToWGS84(lineSegments.get(index).p1));
            }
        }
        return shape;
    }

    public Map<Locale, List<String>> getNames() {
        return new TreeMap<>();
    }
}
