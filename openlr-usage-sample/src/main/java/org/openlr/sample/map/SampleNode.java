package org.openlr.sample.map;

import openlr.map.GeoCoordinates;
import openlr.map.Line;
import openlr.map.Node;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * A sample implementation of a map node
 */
class SampleNode implements Node {
    /**
     * The identifier of the node.
     */
    private final long id;

    /**
     * The coordinate of the node.
     */
    private final GeoCoordinates coordinate;

    /**
     * The lines that lead out from the node.
     */
    private final List<Line> outgoingLines;

    /**
     * The lines that lead into the node.
     */
    private final List<Line> incomingLines;

    SampleNode(long id, GeoCoordinates coordinate, List<Line> outgoingLines, List<Line> incomingLines) {
        this.id = id;
        this.coordinate = coordinate;
        this.outgoingLines = outgoingLines;
        this.incomingLines = incomingLines;
    }

    public SampleNode(long id, GeoCoordinates coordinate) {
        this(id, coordinate, new ArrayList<>(), new ArrayList<>());
    }

    /**
     * Attach an outgoing line to the node. This method is only used during the initial construction of the node.
     *
     * @param line the line to attach.
     */
    protected void addOutgoingLine(Line line) {
        outgoingLines.add(line);
    }

    /**
     * Attach an incoming line to the node. This method is only used during the initial construction of the node.
     *
     * @param line the line to attach.
     */
    protected void addIncomingLine(Line line) {
        incomingLines.add(line);
    }

    /**
     * Get the identifier of the node.
     *
     * @return the identifier of the node.
     */
    @Override
    public long getID() {
        return id;
    }

    /**
     * The longitude at which the node is located.
     *
     * @return the longitude portion of the node coordinate.
     */
    @Override
    public double getLongitudeDeg() {
        return coordinate.getLongitudeDeg();
    }

    /**
     * The latitude at which the node is located.
     *
     * @return the latitude portion of the node coordinate.
     */
    @Override
    public double getLatitudeDeg() {
        return coordinate.getLatitudeDeg();
    }

    /**
     * The coordinate at which the node is located.
     *
     * @return the coordinate of the node.
     */
    @Override
    public GeoCoordinates getGeoCoordinates() {
        return coordinate;
    }

    /**
     * Get all lines, both outgoing and incoming, that are connected to the node.
     *
     * @return all lines connected to the node.
     */
    @Override
    public Iterator<Line> getConnectedLines() {
        return Stream.concat(
                outgoingLines.stream(),
                incomingLines.stream()
        ).iterator();
    }

    /**
     * Get the total number of lines connected to the node.
     *
     * @return the total number of lines connected to the node.
     */
    @Override
    public int getNumberConnectedLines() {
        return outgoingLines.size() + incomingLines.size();
    }

    /**
     * Get all lines that lead out from the node.
     *
     * @return the lines that lead out from the node.
     */
    @Override
    public Iterator<Line> getOutgoingLines() {
        return outgoingLines.iterator();
    }

    /**
     * Get all lines that lead into the node.
     *
     * @return the lines that lead into the node.
     */
    @Override
    public Iterator<Line> getIncomingLines() {
        return incomingLines.iterator();
    }
}
