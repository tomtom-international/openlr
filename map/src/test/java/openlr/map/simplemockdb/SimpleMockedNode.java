package openlr.map.simplemockdb;

import openlr.map.Node;
import openlr.map.Line;
import openlr.map.GeoCoordinates;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.InvalidMapDataException;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.HashSet;


public class SimpleMockedNode implements Node {
    private  openlr.map.simplemockdb.schema.Node xmlNode;
    private List<Line> outgoingLines;
    private List<Line> incomingLines;
    private List<Line> connectedLines;
    private GeoCoordinates coordinate;

    private SimpleMockedNode(final  openlr.map.simplemockdb.schema.Node xmlNode) {
        try {
            this.xmlNode = xmlNode;
            this.outgoingLines = new ArrayList<>();
            this.incomingLines = new ArrayList<>();
            this.connectedLines = new ArrayList<>();
            this.coordinate = new GeoCoordinatesImpl(xmlNode.getLongitude(), xmlNode.getLatitude());
        } catch (InvalidMapDataException e) {
            throw new SimpleMockedException(e.getMessage());
        }
    }

    public static SimpleMockedNode from(final  openlr.map.simplemockdb.schema.Node xmlNode) {
        return new SimpleMockedNode(xmlNode);
    }

    public double getLatitudeDeg() {
        return this.coordinate.getLatitudeDeg();
    }

    public double getLongitudeDeg() {
        return this.coordinate.getLongitudeDeg();
    }

    public GeoCoordinates getGeoCoordinates() {
        return coordinate;
    }

    public void setConnections(final Collection<SimpleMockedLine> lines) {
        for (Line line : lines) {
            if (line.getStartNode().getID() == xmlNode.getId().longValue()) {
                outgoingLines.add(line);
            }

            if (line.getEndNode().getID() == xmlNode.getId().longValue()) {
                incomingLines.add(line);
            }
        }
        Set<Line> set = new HashSet<Line>();
        set.addAll(outgoingLines);
        set.addAll(incomingLines);
        connectedLines.addAll(set);
    }

    public Iterator<Line> getConnectedLines() {
        return connectedLines.iterator();
    }

    public int getNumberConnectedLines() {
        return connectedLines.size();
    }

    public Iterator<Line> getOutgoingLines() {
        return outgoingLines.iterator();
    }

    public Iterator<Line> getIncomingLines() {
        return incomingLines.iterator();
    }

    public boolean equals(final Object var1) {
        if (var1 instanceof SimpleMockedNode) {
            return this.getID() == ((SimpleMockedNode) var1).getID();
        }
        return false;
    }

    public int hashCode() {
        return this.hashCode();
    }

    public long getID() {
        return xmlNode.getId().longValue();
    }
}
