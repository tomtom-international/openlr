package org.openlr.sample.map;

import openlr.map.Line;
import openlr.map.MapDatabase;
import openlr.map.Node;
import openlr.map.utils.GeometryUtils;

import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A sample implementation of a map connector to use in conjunction with the OpenLR encoder and decoder.
 */
class SampleMapDatabase implements MapDatabase {
    /**
     * An index to lookup nodes by their identifier
     */
    private final Map<Long, Node> nodeIndex;

    /**
     * An index to lookup lines by their identifier
     */
    private final Map<Long, Line> lineIndex;

    SampleMapDatabase(Map<Long, Node> nodeIndex, Map<Long, Line> lineIndex) {
        this.nodeIndex = nodeIndex;
        this.lineIndex = lineIndex;
    }

    /**
     * Indicate if the map holds information on turn restrictions not expressed through the network topology.
     *
     * @return true if the map also provides information on turn restrictions.
     */
    @Override
    public boolean hasTurnRestrictions() {
        return false;
    }

    /**
     * Lookup a line by its identifier.
     *
     * @param id the identifier of the line.
     * @return the line with this identifier if it exists, otherwise null.
     */
    @Override
    public Line getLine(long id) {
        return lineIndex.get(id);
    }

    /**
     * Lookup a node by its identifier.
     *
     * @param id the identifier of the node.
     * @return the node with this identifier if it exists, otherwise null.
     */
    @Override
    public Node getNode(long id) {
        return nodeIndex.get(id);
    }

    /**
     * Find all nodes in the map within a distance of a certain coordinate. The coordinate and distance form a circle
     * with the coordinate as the center and the distance as radius. All nodes that lie within this circle should be returned.
     *
     * Normally the spatial index of the underlying map technology should be used to perform the search. For the
     * sample implementation here, a sequential search is performed.
     *
     * @param longitude the longitude of the coordinate.
     * @param latitude the latitude of the coordinate.
     * @param distance the distance in meters
     * @return all nodes that lie within the distance from the coordinate.
     */
    @Override
    public Iterator<Node> findNodesCloseByCoordinate(double longitude, double latitude, int distance) {
        // In the sample implementation a sequential search is performed.
        // For an efficient implementation the spatial index of the underlying map technology should be used.
        return nodeIndex.values().stream()
                .filter(node -> GeometryUtils.distance(node.getLongitudeDeg(), node.getLatitudeDeg(), longitude, latitude) <= distance)
                .iterator();
    }

    /**
     * Find all lines in the map within a distance of a certain coordinate. The coordinate and distance form a circle
     * with the coordinate as the center and the distance as radius. All lines that intersect this circle should be returned.
     *
     * Normally the spatial index of the underlying map technology should be used to perform the search. For the
     * sample implementation here, a sequential search is performed.
     *
     * @param longitude the longitude of the coordinate.
     * @param latitude the latitude of the coordinate.
     * @param distance the distance in meters
     * @return all lines that lie within the distance from the coordinate.
     */
    @Override
    public Iterator<Line> findLinesCloseByCoordinate(double longitude, double latitude, int distance) {
        // In the sample implementation a sequential search is performed.
        // For an efficient implementation the spatial index of the underlying map technology should be used.
        return lineIndex.values().stream()
                .filter(line -> line.distanceToPoint(longitude, latitude) <= distance)
                .iterator();
    }

    /**
     * Determine if any turn restrictions exist along a path that are not expressed through the network topology.
     *
     * @param lines the sequence of lines that form the path.
     * @return true if there are any turn restrictions along the path, otherwise false.
     */
    @Override
    public boolean hasTurnRestrictionOnPath(List<? extends Line> lines) {
        return false;
    }

    /**
     * Get all nodes within the map.
     *
     * @return all nodes within the map.
     */
    @Override
    public Iterator<Node> getAllNodes() {
        // This method is not explicitly required for the purposes of encoding and decoding.
        throw new UnsupportedOperationException();
    }

    /**
     * Get all lines within the map.
     *
     * @return all lines within the map.
     */
    @Override
    public Iterator<Line> getAllLines() {
        // This method is not explicitly required for the purposes of encoding and decoding.
        throw new UnsupportedOperationException();
    }

    /**
     * Get the bounding box of the map. This is the minimum rectangle that contains completely all lines of the map.
     *
     * @return the bounding box of the map.
     */
    @Override
    public Rectangle2D.Double getMapBoundingBox() {
        // This method is not explicitly required for the purposes of encoding and decoding.
        throw new UnsupportedOperationException();
    }

    /**
     * Get the number of nodes in the map.
     *
     * @return the number of nodes.
     */
    @Override
    public int getNumberOfNodes() {
        // This method is not explicitly required for the purposes of encoding and decoding.
        throw new UnsupportedOperationException();
    }

    /**
     * Get the number of lines in the map.
     *
     * @return the number of lines.
     */
    @Override
    public int getNumberOfLines() {
        // This method is not explicitly required for the purposes of encoding and decoding.
        throw new UnsupportedOperationException();
    }
}
