package openlr.map.teststubs;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineSegment;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
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


public class LineStub implements Line {

    private static GeometryFactory factory = JTSFactoryFinder.getGeometryFactory();
    private static MathTransform wgs84ToCartesian;
    private static MathTransform cartesianToWgs843d;

    static {
        try {
            wgs84ToCartesian = CRS.findMathTransform(DefaultGeographicCRS.WGS84, DefaultGeocentricCRS.CARTESIAN);
            cartesianToWgs843d = CRS.findMathTransform(DefaultGeocentricCRS.CARTESIAN, DefaultGeographicCRS.WGS84_3D);
        } catch (FactoryException e) {
            throw new MapStubException(e.getMessage());
        }
    }

    private Node startNode;
    private Node endNode;
    private FunctionalRoadClass frc;
    private FormOfWay fow;
    private long id;
    private int hashCode;
    private List<Long> restrictions;
    private List<LineSegment> lineSegments;

    public final LineString getLineString() {
        Coordinate[] crds = getShapeCoordinates().stream()
                .map(shapePoint -> toCartesian(shapePoint.getLongitudeDeg(), shapePoint.getLatitudeDeg()))
                .collect(Collectors.toList())
                .toArray(new Coordinate[getShapeCoordinates().size()]);
        return factory.createLineString(crds);
    }


    public static Coordinate toCartesian(double lon, double lat) {
        try {
            return JTS.transform(new Coordinate(lon, lat, 0), null, wgs84ToCartesian);
        } catch (TransformException e) {
            throw new MapStubException(e.getMessage());

        }
    }


    public static GeoCoordinates toWGS84(Coordinate crd) {
        try {
            Coordinate wgs84crd = JTS.transform(crd, null, cartesianToWgs843d);
            return new GeoCoordinatesImpl(wgs84crd.x, wgs84crd.y);
        } catch (TransformException e) {
            throw new MapStubException(e.getMessage());

        } catch (InvalidMapDataException e) {
            throw new MapStubException(e.getMessage());

        }
    }

    private static List<LineSegment> generateShape(List<Coordinate> intermediatePoints, Node startNode, Node endNode) {
        List<LineSegment> lineSegments = new ArrayList<>();
        int numberOfShapePoints = intermediatePoints.size() + 2;
        Coordinate[] shapePoints = new Coordinate[numberOfShapePoints];
        shapePoints[0] = toCartesian(startNode.getLongitudeDeg(), startNode.getLatitudeDeg());
        for (int index = 0; index < intermediatePoints.size(); ++index) {
            shapePoints[index + 1] = intermediatePoints.get(index);
        }
        shapePoints[numberOfShapePoints - 1] = toCartesian(endNode.getLongitudeDeg(), endNode.getLatitudeDeg());
        for (int index = 1; index < shapePoints.length; ++index) {
            Coordinate end = shapePoints[index];
            Coordinate start = shapePoints[index - 1];
            LineSegment segment = new LineSegment(start, end);
            lineSegments.add(segment);
        }
        return lineSegments;
    }


    public static LineStub from(long id, List<Long> restrictions, FunctionalRoadClass frc, FormOfWay fow, int hashcode, Node startNode, Node endNode, List<Coordinate> intermediatePoints) {
        List<LineSegment> lineSegments = generateShape(intermediatePoints, startNode, endNode);
        LineStub simpleMockedLine = new LineStub(id, restrictions, frc, fow, hashcode, startNode, endNode, lineSegments);
        return simpleMockedLine;
    }


    private LineStub(long id, List<Long> restrictions, FunctionalRoadClass frc, FormOfWay fow, int hashcode, Node startNode, Node endNode, List<LineSegment> segments) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.lineSegments = segments;
        this.id = id;
        this.restrictions = restrictions;
        this.frc = frc;
        this.fow = fow;
        this.hashCode = hashcode;
    }

    public Node getStartNode() {
        return this.startNode;
    }

    public List<Long> getRestrictions() {
        return this.restrictions;
    }

    public Node getEndNode() {
        return this.endNode;
    }

    public FormOfWay getFOW() {

        return fow;
    }

    public FunctionalRoadClass getFRC() {
        return frc;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public Point2D.Double getPointAlongLine(int var1) {
        return null;
    }

    public GeoCoordinates getGeoCoordinateAlongLine(int distance) throws MapStubException {

        int lengthCovered = 0;
        for (LineSegment segment : lineSegments) {
            if (lengthCovered <= distance && lengthCovered + segment.getLength() >= distance) {
                double offset = distance - lengthCovered;
                Coordinate point = segment.pointAlong(segment.getLength() / offset);
                point = segment.project(point);
                double z = segment.p0.z + 1 * (segment.p1.z - segment.p0.z);
                point.setOrdinate(2, z);
                return toWGS84(point);
            }
            lengthCovered += segment.getLength();
        }
        throw new MapStubException("length is greater than the line length");
    }

    public int getLineLength() {
        return (int) this.lineSegments.stream().mapToDouble(segment -> segment.getLength()).sum();
    }

    public long getID() {
        return this.id;
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

    @Override
    public boolean equals(Object other) {
        if (other instanceof LineStub) {
            return this.getID() == ((LineStub) other).getID();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }

    public int distanceToPoint(double lon, double lat) {
        Geometry geometry = factory.createPoint(toCartesian(lon, lat));
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
        throw new MapStubException("point is not on the line");
    }

    public List<GeoCoordinates> getShapeCoordinates() {
        List<GeoCoordinates> shape = new ArrayList<>();
        for (int index = 0; index < lineSegments.size(); ++index) {
            shape.add(toWGS84(lineSegments.get(index).p0));

            if (index == lineSegments.size() - 1) {
                shape.add(toWGS84(lineSegments.get(index).p1));
            }
        }
        return shape;
    }

    public Map<Locale, List<String>> getNames() {
        return new TreeMap<>();
    }
}
