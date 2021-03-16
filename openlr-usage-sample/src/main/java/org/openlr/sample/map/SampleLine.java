package org.openlr.sample.map;

import openlr.map.*;
import openlr.map.utils.GeometryUtils;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.*;

/**
 * A sample implementation of a map line.
 */
class SampleLine implements Line {
    /**
     * The identifier of the line.
     */
    private final long id;

    /**
     * The node in the map where the line starts.
     */
    private final Node startNode;

    /**
     * The node in the map where the line ends.
     */
    private final Node endNode;

    /**
     * The functional road class of the line.
     */
    private final FunctionalRoadClass functionalRoadClass;

    /**
     * The form of way of the line.
     */
    private final FormOfWay formOfWay;

    /**
     * The length of the line in meters.
     */
    private final int length;

    /**
     * A sequence of shape points describing the geometry of the line.
     */
    private final List<GeoCoordinates> coordinates;

    SampleLine(long id, Node startNode, Node endNode, FunctionalRoadClass functionalRoadClass, FormOfWay formOfWay, int length, List<GeoCoordinates> coordinates) {
        this.id = id;
        this.startNode = startNode;
        this.endNode = endNode;
        this.functionalRoadClass = functionalRoadClass;
        this.formOfWay = formOfWay;
        this.length = length;
        this.coordinates = coordinates;
    }

    /**
     * Get the identifier of the line.
     *
     * @return the identifier of the line.
     */
    @Override
    public long getID() {
        return id;
    }

    /**
     * Get the start node of the line.
     *
     * @return the start node of the line.
     */
    @Override
    public Node getStartNode() {
        return startNode;
    }

    /**
     * Get the end node of the line.
     *
     * @return the end node of the line.
     */
    @Override
    public Node getEndNode() {
        return endNode;
    }

    /**
     * Get the functional road class of the line.
     *
     * @return the functional road class of the line.
     */
    @Override
    public FunctionalRoadClass getFRC() {
        return functionalRoadClass;
    }

    /**
     * Get the form of way of the line.
     *
     * @return the form of way of the line.
     */
    @Override
    public FormOfWay getFOW() {
        return formOfWay;
    }

    /**
     * Get the point along the line a certain distance in meters along the line from start.
     *
     * @param distance distance in meters along the line from the start.
     * @return the point on the line at this distance.
     */
    @Override
    public Point2D.Double getPointAlongLine(int distance) {
        GeoCoordinates coordinate = getGeoCoordinateAlongLine(distance);
        return new Point2D.Double(coordinate.getLongitudeDeg(), coordinate.getLatitudeDeg());
    }

    /**
     * Get the coordinate along the line a certain distance in meters along the line from start.
     *
     * @param distance distance in meters along the line from the start.
     * @return the coordinate on the line at this distance.
     */
    @Override
    public GeoCoordinates getGeoCoordinateAlongLine(int distance) {
        // Constrain the coordinate to lie on the line
        if (distance < 0) {
            return startNode.getGeoCoordinates();
        }
        else if (distance >= getLineLength()) {
            return endNode.getGeoCoordinates();
        }

        // A pointer to the previous coordinate
        GeoCoordinates previous = null;

        // The remaining distance left to walk
        double remaining = distance;

        // Iterate the coordinates
        for (GeoCoordinates coordinate : coordinates) {
            if (previous != null) {
                // Get the length of the current segment of the line
                double segmentLength = GeometryUtils.distance(previous, coordinate);

                // Check if distance has been reached
                if (remaining > segmentLength) {
                    // Reduce the distance left to walk
                    remaining -= segmentLength;
                }
                else {
                    // Distance has been exceeded along the current line segment
                    // Get the fraction along the curent segment at which the distance
                    double fraction = remaining / segmentLength;

                    // Get the coordinate at this fraction along the current segment
                    return getCoordinateAtFraction(previous, coordinate, fraction);
                }
            }

            // Update pointer to previous coordinate
            previous = coordinate;
        }

        // Reached the end of the line without exceeding the distance.
        // This could only happen if there are inconsistencies between the line geometry and length.
        throw new IllegalStateException();
    }

    /**
     * Get the line length in meters.
     *
     * @return the line length in meters.
     */
    @Override
    public int getLineLength() {
        return length;
    }

    /**
     * Get all predecessor lines to the line in the map.
     *
     * @return all predecessor lines.
     */
    @Override
    public Iterator<Line> getPrevLines() {
        return startNode.getIncomingLines();
    }

    /**
     * Get all successor lines to the line in the map.
     *
     * @return all successor lines.
     */
    @Override
    public Iterator<Line> getNextLines() {
        return endNode.getOutgoingLines();
    }

    /**
     * Get the smallest distance in meters from a coordinate to the line.
     *
     * @param longitude the longitude of the coordinate.
     * @param latitude the latitude of the coordinate
     * @return the smallest distance in meters from the coordinate to the line.
     */
    @Override
    public int distanceToPoint(double longitude, double latitude) {
        // Project the coordinate onto the line
        ProjectionResult projectionResult = project(longitude, latitude);

        // The projection distance
        return projectionResult.getDistance();
    }

    /**
     * Get the distance in meters along a line to the point on the line that is closest to a given coordinate.
     *
     * @param longitude the longitude of the coordinate.
     * @param latitude the latitude of the coordinate.
     * @return the distance along the line to the point on the line that is closest to the coordinate.
     */
    @Override
    public int measureAlongLine(double longitude, double latitude) {
        // Project the coordinate onto the line
        ProjectionResult projectionResult = project(longitude, latitude);

        // An iterator of the shape coordinates of the line
        Iterator<GeoCoordinates> coordinatesIterator = coordinates.iterator();

        // A pointer to the previous coordinate
        GeoCoordinates previous = null;

        // The current segment index
        int counter = 1;

        // The index of the segment where the projection point was found
        int index = projectionResult.getIndex();

        // The distance walked so far
        int currentLength = 0;

        // Iterate the shape coordinates
        while (coordinatesIterator.hasNext() && counter <= index) {
            // The current coordinate
            GeoCoordinates current = coordinatesIterator.next();

            if (previous != null) {
                // Check if we have yet to reach the segment with the projection point
                if (counter < index) {
                    // Not yet reached the segment with the projection point
                    // Add the length of the current segment
                    int segmentLength = (int) GeometryUtils.distance(previous, current);
                    currentLength += segmentLength;
                }
                else if (counter == index) {
                    // Segment with the projection point reached
                    int segmentLength = (int) GeometryUtils.distance(previous, current);

                    // Add the distance along the segment where the projection point is found
                    currentLength += projectionResult.getFraction() * segmentLength;
                }
            }

            // Update previous coordinate pointer
            previous = current;

            // Increase the current segment index
            counter++;
        }

        // Return the distance to the projection point
        return currentLength;
    }

    /**
     * Get the shape of the line.
     *
     * @return the shape of the line.
     */
    @Override
    public Path2D.Double getShape() {
        // The path describing the shape
        Path2D.Double path = new Path2D.Double();

        // Flag to indicate whether at the start of the path
        boolean first = true;

        // Iterate all coordinates of the line
        for (GeoCoordinates coordinate : coordinates) {
            // Check whether at the start
            if (first) {
                // Move to the start point
                path.moveTo(coordinate.getLongitudeDeg(), coordinate.getLatitudeDeg());
                // Unset flag
                first = false;
            } else {
                // Not at the start
                // Add a line for the current segment
                path.lineTo(coordinate.getLongitudeDeg(), coordinate.getLatitudeDeg());
            }
        }

        // Return the path describing the shape of the line
        return path;
    }

    /**
     * The sequence of coordinates describing the line shape.
     *
     * @return the coordinates of the line shape.
     */
    @Override
    public List<GeoCoordinates> getShapeCoordinates() {
        return coordinates;
    }

    /**
     * The names of the line in different languages.
     *
     * @return the names of the line.
     */
    @Override
    public Map<Locale, List<String>> getNames() {
        // This is not explicitly needed for OpenLR encoding or decoding
        throw new UnsupportedOperationException();
    }

    /**
     * Get the coordinate at a fraction between two coordinates.
     *
     * @param from the origin coordinate.
     * @param to the destination coordinate.
     * @param fraction the fraction of the distance between the origin and destination of the result coordinate.
     * @return the result coordinate.
     */
    private GeoCoordinates getCoordinateAtFraction(GeoCoordinates from, GeoCoordinates to, double fraction) {
        // Bound the coordinate to somewhere between the origin and destination
        if (fraction <= 0.0) {
            return from;
        }
        else if (fraction >= 1.0) {
            return to;
        }

        // Compute the longitude of the coordinate at this fraction
        double longitude = from.getLongitudeDeg() + ((to.getLongitudeDeg() - from.getLongitudeDeg()) * fraction);

        // Compute the latitude of the coordinate at this fraction
        double latitude = from.getLatitudeDeg() + ((to.getLatitudeDeg() - from.getLatitudeDeg()) * fraction);

        // Return the coordinate at this fraction
        return GeoCoordinatesImpl.newGeoCoordinatesUnchecked(longitude, latitude);
    }

    /**
     * Project a coordinate onto the line.
     *
     * @param longitude the longitude of the coordinate to project.
     * @param latitude the latitude of the coordinate to project.
     * @return a projection result representing the position of the point on the line that is nearest to the coordinate.
     */
    private ProjectionResult project(double longitude, double latitude) {
        // The coordinate to project
        GeoCoordinates coordinate = GeoCoordinatesImpl.newGeoCoordinatesUnchecked(longitude, latitude);

        // A pointer to the previous coordinate
        GeoCoordinates previous = null;

        // The current segment index
        int counter = 1;

        // The index of the segment where the nearest point is found
        int minIndex = -1;

        // The fraction along the segment where the nearest point is found
        double minProjectionFraction = 0.0;

        // The minimum distance
        int minDistance = Integer.MAX_VALUE;

        // Iterate all coordinates of the line
        for (GeoCoordinates current : coordinates) {
            if (previous != null) {
                // Project the coordinate onto the current segment
                double projectionFraction = projectionFraction(previous, current, coordinate);

                // Get the coordinate on the line segment that is nearest
                GeoCoordinates coordinateAtFraction = getCoordinateAtFraction(previous, current, projectionFraction);

                // Calculate the distance
                int distance = (int) GeometryUtils.distance(coordinate, coordinateAtFraction);

                // Check if the distance is smaller than the minimum distance seen so far
                if (distance < minDistance) {
                    // Distance is smaller
                    // Update the pointers
                    minIndex = counter;
                    minProjectionFraction = projectionFraction;
                    minDistance = distance;
                }
            }

            // Update pointer to previous coordinate
            previous = current;

            // Increment current segment index
            counter++;
        }

        // Build the projection result
        return new ProjectionResult(minIndex, minProjectionFraction, minDistance);
    }


    /**
     * Project a coordinate onto a line segment and return the fraction along the line segment where the
     * projection point is found.
     *
     * @param from the start of the segment.
     * @param to the end of the segment
     * @param coordinate the coordinate to project
     * @return
     */
    private double projectionFraction(GeoCoordinates from, GeoCoordinates to, GeoCoordinates coordinate) {
        // The difference between the longitudes
        double dx = to.getLongitudeDeg() - from.getLongitudeDeg();

        // The difference between the latitudes
        double dy = to.getLatitudeDeg() - from.getLatitudeDeg();

        // The square of the length
        double len = (dx * dx) + (dy * dy);

        // The fraction
        double fraction = (((coordinate.getLongitudeDeg() - from.getLongitudeDeg()) * dx) + ((coordinate.getLatitudeDeg() - from.getLatitudeDeg()) * dy)) / len;

        // Constrain to lie along the segment
        // Snap to the start or the end of the segment if necessary
        if (fraction < 0.0) {
            fraction = 0.0;
        }
        else if (fraction > 1.0) {
            fraction = 1.0;
        }

        // Return the fraction along the segment
        return fraction;
    }


    /**
     * A utility data object to represent the result of projecting a coordinate onto the line
     */
    private static class ProjectionResult {
        /**
         * The index of the segment on the line where the projection point was found.
         */
        private final int index;

        /**
         * The fraction along the segment where the projection point was found.
         */
        private final double fraction;

        /**
         * The distance of the point that was projected to the projection point on the line.
         */
        private final int distance;

        public ProjectionResult(int index, double fraction, int distance) {
            this.index = index;
            this.fraction = fraction;
            this.distance = distance;
        }

        /**
         * Get the index of the segment on the line.
         *
         * @return the index of the segment on the line.
         */
        public int getIndex() {
            return index;
        }

        /**
         * Get the fraction along the segment.
         *
         * @return the fraction along the segment.
         */
        public double getFraction() {
            return fraction;
        }

        /**
         * Get the distance from the point that was projected to the line.
         *
         * @return the distance from the point that was projected to the line.
         */
        public int getDistance() {
            return distance;
        }
    }
}
