/**
 * Licensed to the TomTom International B.V. under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  TomTom International B.V.
 * licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * <p>
 * Copyright (C) 2009-2019 TomTom International B.V.
 * <p>
 * TomTom (Legal Department)
 * Email: legal@tomtom.com
 * <p>
 * TomTom (Technical contact)
 * Email: openlr@tomtom.com
 * <p>
 * Address: TomTom International B.V., Oosterdoksstraat 114, 1011DK Amsterdam,
 * the Netherlands
 * <p>
 * Copyright (C) 2009-2019 TomTom International B.V.
 * <p>
 * TomTom (Legal Department)
 * Email: legal@tomtom.com
 * <p>
 * TomTom (Technical contact)
 * Email: openlr@tomtom.com
 * <p>
 * Address: TomTom International B.V., Oosterdoksstraat 114, 1011DK Amsterdam,
 * the Netherlands
 * <p>
 * Copyright (C) 2009-2019 TomTom International B.V.
 * <p>
 * TomTom (Legal Department)
 * Email: legal@tomtom.com
 * <p>
 * TomTom (Technical contact)
 * Email: openlr@tomtom.com
 * <p>
 * Address: TomTom International B.V., Oosterdoksstraat 114, 1011DK Amsterdam,
 * the Netherlands
 * <p>
 * Copyright (C) 2009-2019 TomTom International B.V.
 * <p>
 * TomTom (Legal Department)
 * Email: legal@tomtom.com
 * <p>
 * TomTom (Technical contact)
 * Email: openlr@tomtom.com
 * <p>
 * Address: TomTom International B.V., Oosterdoksstraat 114, 1011DK Amsterdam,
 * the Netherlands
 */
/**
 *  Copyright (C) 2009-2019 TomTom International B.V.
 *
 *   TomTom (Legal Department)
 *   Email: legal@tomtom.com
 *
 *   TomTom (Technical contact)
 *   Email: openlr@tomtom.com
 *
 *   Address: TomTom International B.V., Oosterdoksstraat 114, 1011DK Amsterdam,
 *   the Netherlands
 */
package openlr.map.utils;

import openlr.map.GeoCoordinates;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.InvalidMapDataException;
import openlr.map.Line;

import java.awt.geom.Line2D;

/**
 * The GeometryUtils offer methods for the calculation of distances of points
 * and bearings of lines.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public final class GeometryUtils {

    /** The Constant MAX_LAT. */
    public static final float MAX_LAT = 90;

    /** The Constant MIN_LAT. */
    public static final float MIN_LAT = -90;

    /** The Constant MAX_LON. */
    public static final float MAX_LON = 180;

    /** The Constant MIN_LON. */
    public static final float MIN_LON = -180;

    /** The Constant ZERO_CIRCLE. */
    public static final double ZERO_CIRCLE = 0;

    /** The Constant HALF_CIRCLE. */
    public static final double HALF_CIRCLE = 180;

    /** The Constant QUARTER_CIRCLE. */
    public static final double QUARTER_CIRCLE = 90;

    /** The Constant FULL_CIRCLE. */
    public static final double FULL_CIRCLE = 360;

    /** The Constant QUARTER_CIRCLE. */
    public static final double THREE_QUARTER_CIRCLE = 270;
    /** degree in a full circle */
    public static final int FULL_CIRCLE_DEGREE = 360;
    /** The default precision for rounding coordinate values. */
    private static final int DEFAULT_PRECISION = 5;
    /** Integer of value 10. */
    private static final int TEN = 10;
    /** The Constant THREE. */
    private static final int THREE = 3;
    /** The Constant METER_PER_KILOMETER. */
    private static final float METER_PER_KILOMETER = 1000.0f;
    /** The Constant divisionsPerDegree. */
    private static final int DIVISIONS_PER_DEGREE = 100000; // 1000 * 100;
    /** = DIVISIONS_PER_DEGREE / DIVISIONS_PER_RADIAN */
    private static final double RAD_FACTOR = 0.017453292519943294;
    /** The equatorial radius in meters */
    private static final double EQUATORIAL_RADIUS = 6378137; // meter
    /** The Constant INVERSE_FLATTENING. */
    private static final double INVERSE_FLATTENING = 298.257223563;
    /** The Constant OBLATENESS. */
    private static final double OBLATENESS = 1. / INVERSE_FLATTENING;

    private static final BearingPointCalculator bearingPointCalculator = new BearingPointCalculator();

    /**
     * Utility class shall not be instantiated.
     */
    private GeometryUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * @param value
     *            (degrees)
     * @return the radians
     */
    private static double toRadians(final double value) {
        return value * RAD_FACTOR;
    }

    /**
     * Returns the distance in meters between two points specified in degrees
     * longitude (x) and latitude (y). <br>
     * The distance calculation works with the WGS84-orthodrome and thus
     * accounts for the oblateness of the earth, resulting in increased
     * preciseness.
     *
     *
     * @param coord1
     *            the GeoCoordinate of the first point
     * @param coord2
     *            the GeoCoordinate of the second point
     *
     * @return the distance between the two points in meter
     *
     */
    public static double distance(final GeoCoordinates coord1,
                                  final GeoCoordinates coord2) {
        return distance(coord1.getLongitudeDeg(), coord1.getLatitudeDeg(),
                coord2.getLongitudeDeg(), coord2.getLatitudeDeg());
    }

    /**
     * Returns the distance in meters between two points specified in degrees
     * longitude (x) and latitude (y). <br>
     * The distance calculation works with the WGS84-orthodrome and thus
     * accounts for the oblateness of the earth, resulting in increased
     * preciseness.
     *
     *
     * @param longitude1
     *            the longitude of the first point
     * @param latitude1
     *            the latitude of the first point
     * @param longitude2
     *            the longitude of the second point
     * @param latitude2
     *            the latitude of the second point
     *
     * @return the distance between the two points in meter
     *
     */
    public static double distance(final double longitude1,
                                  final double latitude1, final double longitude2,
                                  final double latitude2) {
        if (latitude2 == latitude1 && longitude2 == longitude1) {
            return 0.0;
        }
        double f = toRadians((latitude2 + latitude1) / 2.0);
        double g = toRadians((latitude2 - latitude1) / 2.0);
        double l = toRadians((longitude2 - longitude1) / 2.0);

        double sinF = Math.sin(f);
        double sinG = Math.sin(g);
        double cosF = Math.cos(f);
        double cosG = Math.cos(g);
        double sinl = Math.sin(l);
        double cosl = Math.cos(l);
        double s = sinG * sinG * cosl * cosl + cosF * cosF * sinl * sinl;
        if (s == 0) {
            return 0.0;
        }
        double c = cosG * cosG * cosl * cosl + sinF * sinF * sinl * sinl;
        if (c == 0) {
            return 0.0;
        }
        double w = Math.atan(Math.sqrt(s / c));
        if (w == 0) {
            return 0.0;
        }
        double d = 2 * w * EQUATORIAL_RADIUS;
        double r = Math.sqrt(s * c) / w;
        double h1 = (THREE * r - 1.0) / (2 * c);
        double h2 = (THREE * r + 1.0) / (2 * s);

        return d
                * (1 + OBLATENESS * h1 * sinF * sinF * cosG * cosG - OBLATENESS
                * h2 * cosF * cosF * sinG * sinG);
    }

    /**
     * Transforms a degree value into a deca-micro degree value.
     *
     * @param val
     *            the degree value
     *
     * @return the deca-micro degree value
     */
    private static double transformDecaMicroDeg(final double val) {
        return val * DIVISIONS_PER_DEGREE;
    }

    /**
     * Return the bearing (degrees clockwise from North) of p2 from p1 (the
     * direction that must be traveled from p1 in order to arrive at p2).
     *
     * @param coord1
     *            the GeoCoordinates of the first point
     * @param coord2
     *            the GeoCoordinates of the second point
     *
     * @return the bearing
     */
    public static double bearing(final GeoCoordinates coord1,
                                 final GeoCoordinates coord2) {
        return bearing(coord1.getLongitudeDeg(), coord1.getLatitudeDeg(),
                coord2.getLongitudeDeg(), coord2.getLatitudeDeg());
    }

    /**
     * Return the bearing (degrees clockwise from North) of p2 from p1 (the
     * direction that must be traveled from p1 in order to arrive at p2).
     *
     * @param p1Longitude
     *            the longitude of the first point
     * @param p1Latitude
     *            the latitude of the first point
     * @param p2Longitude
     *            the longitude of the second point
     * @param p2Latitude
     *            the latitude of the second point
     *
     * @return the bearing
     */
    public static double bearing(final double p1Longitude,
                                 final double p1Latitude, final double p2Longitude,
                                 final double p2Latitude) {
        double deltaX = (transformDecaMicroDeg(p2Longitude - p1Longitude))
                * hMult(p2Latitude);
        double deltaY = transformDecaMicroDeg(p2Latitude - p1Latitude);
        double angle = Math.toDegrees(Math.atan2(deltaX, deltaY));
        if (angle < 0) {
            angle += FULL_CIRCLE_DEGREE;
        }
        return angle;
    }

    /**
     * The cosine of y (latitude value), treating y as deca-micro degrees.
     *
     * @param y
     *            the latitude value
     *
     * @return the cosine
     */
    private static double hMult(final double y) {
        return Math.cos(y * RAD_FACTOR);
    }

    /**
     * Calculates and returns the line bearing. The bearing will be calculated
     * in degrees clockwise from North. The {@link BearingDirection} indicates
     * the direction for the measurement. <br>
     * The projectionAlongLine will be used if the given value is greater than 0
     * and refers to a position along the line being projectionAlongLine meters
     * away from the start of the line. In such a case the first point for the
     * measurement is equal to the position defined by projectionAlongLine. <br>
     * The second point will be pointDistance meters away from the first point.
     * If dir equals IN_DIRECTION the second point will be determined in the
     * direction of the line (from start to end) otherwise if dir equals
     * AGAINST_DIRECTION the second point will be determined against the
     * direction of the line. <br>
     * If projectionAlongLine equals 0 then the first point will be determined
     * as the start node of the line if dir is IN_DIRECTION and if dir is
     * AGAINST_DIRECTION the end node of the line will be used. <br>
     * If the remaining line is shorter than pointDistance the next node of the
     * line will be used. <br>
     * The bearing is the angle between the North and the line being defined by
     * the first and second point.
     *
     * @param line
     *            the line
     * @param dir
     *            the direction of the line being used
     * @param pointDistance
     *            the point distance of the second point
     * @param projectionAlongLine
     *            the projection along line in meter
     *
     * @return the bearing value for the line and -1.0 if the line is null
     */
    public static double calculateLineBearing(final Line line,
                                              final BearingDirection dir, final int pointDistance,
                                              final int projectionAlongLine) {
        if (line == null || projectionAlongLine < 0 || projectionAlongLine > line.getLineLength()) {
            return -1.0;
        }

        GeoCoordinates p1 = line.getGeoCoordinateAlongLine(projectionAlongLine);
        GeoCoordinates p2;
        if (dir == BearingDirection.IN_DIRECTION) {
            p2 = bearingPointCalculator.calculateBearingDestinationInDirection(line, pointDistance, projectionAlongLine);
        } else {
            p2 = bearingPointCalculator.calculateBearingDestinationAgainstDirection(line, pointDistance, projectionAlongLine);

        }

        return bearing(p1, p2);
    }

    /**
     * Check coordinate bounds.
     *
     * @param lon
     *            the lon
     * @param lat
     *            the lat
     * @return true, if coordinate is valid
     */
    public static boolean checkCoordinateBounds(final double lon,
                                                final double lat) {
        return (lon >= MIN_LON && lon <= MAX_LON && lat >= MIN_LAT && lat <= MAX_LAT);
    }

    /**
     * Intersects two straights given by rsp. start points and initial bearings.
     * The geodesic coordinates are interpreted as being sufficiently close to a
     * equidistant cartesic coordinate system.
     *
     * @param coord1
     *            the GeoCoordinates of the first straight's start point
     * @param bear1
     *            the bearing angle of the first straight
     * @param coord2
     *            the GeoCoordinates of the second straight's start point
     * @param bear2
     *            the bearing angle of the second straight
     * @return the intersection point as a pair of lon/lat (Doubles)
     * @throws InvalidMapDataException
     *             the invalid map data exception
     */
    public static GeoCoordinates intersectStraights(
            final GeoCoordinates coord1, final double bear1,
            final GeoCoordinates coord2, final double bear2)
            throws InvalidMapDataException {
        return intersectStraights(coord1.getLongitudeDeg(),
                coord1.getLatitudeDeg(), bear1, coord2.getLongitudeDeg(),
                coord2.getLatitudeDeg(), bear2);
    }

    /**
     * Intersects two straights given by rsp. start points and initial bearings.
     * The geodesic coordinates are interpreted as being sufficiently close to a
     * equidistant cartesic coordinate system.
     *
     * @param longitude1
     *            the longitude of the first straight's start point
     * @param latitude1
     *            the latitude of the first straight's start point
     * @param bear1
     *            the bearing angle of the first straight
     * @param longitude2
     *            the longitude of the second straight's start point
     * @param latitude2
     *            the latitude of the second straigth's start point
     * @param bear2
     *            the bearing angle of the second straight
     * @return the intersection point as a pair of lon/lat (Doubles)
     * @throws InvalidMapDataException
     *             the invalid map data exception
     */
    public static GeoCoordinates intersectStraights(final double longitude1,
                                                    final double latitude1, final double bear1,
                                                    final double longitude2, final double latitude2, final double bear2)
            throws InvalidMapDataException {
        double m1 = Math.tan(toRadians(QUARTER_CIRCLE - bear1));
        double m2 = Math.tan(toRadians(QUARTER_CIRCLE - bear2));
        double x;
        double y;
        if (bear1 == 0.0) {
            if (bear2 == 0.0) {
                return null;
            }
            x = longitude1;
            y = +m2 * (x - longitude2) + latitude2;
            if (Double.isInfinite(x) || Double.isInfinite(y)) {
                return null;
            }
            return new GeoCoordinatesImpl(x, y);
        }
        if (bear2 == 0.0) {
            if (bear1 == 0.0) {
                return null;
            }
            x = longitude2;
            y = +m1 * (x - longitude1) + latitude1;
            if (Double.isInfinite(x) || Double.isInfinite(y)) {
                return null;
            }
            return new GeoCoordinatesImpl(x, y);
        }
        if (Double.isNaN(m1) || Double.isInfinite(m1) || Double.isNaN(m2)
                || Double.isInfinite(m2)) {
            return null;
        }
        if (m1 == m2) {
            return null;
        }
        x = (m1 * longitude1 - m2 * longitude2 + latitude2 - latitude1)
                / (m1 - m2);
        y = latitude1 + m1 * (x - longitude1);
        if (Double.isInfinite(x) || Double.isInfinite(y)) {
            return null;
        }
        return new GeoCoordinatesImpl(x, y);
    }

    /**
     * Line intersection.
     *
     * @param gc1Start
     *            the gc1 start
     * @param gc1End
     *            the gc1 end
     * @param gc2Start
     *            the gc2 start
     * @param gc2End
     *            the gc2 end
     * @return true, if successful
     */
    public static boolean lineIntersection(final GeoCoordinates gc1Start,
                                           final GeoCoordinates gc1End, final GeoCoordinates gc2Start,
                                           final GeoCoordinates gc2End) {
        Line2D.Double line1 = new Line2D.Double(gc1Start.getLongitudeDeg(),
                gc1Start.getLatitudeDeg(), gc1End.getLongitudeDeg(),
                gc1End.getLatitudeDeg());
        Line2D.Double line2 = new Line2D.Double(gc2Start.getLongitudeDeg(),
                gc2Start.getLatitudeDeg(), gc2End.getLongitudeDeg(),
                gc2End.getLatitudeDeg());
        return line1.intersectsLine(line2);
    }

    /**
     * Calculates a point on the straight line defined by point a and b. The
     * parameter <code>offset</code> defines the position of the desired point
     * on that line relative to point a. If it is above 0 it will deliver a
     * point in direction to point 2 or above. The offset can even be negative-
     * it then defines a point in the opposite direction.
     *
     * @param coord1
     *            the GeoCoordinates of the straight's start point
     * @param coord2
     *            the GeoCoordinates of the straight's end point
     * @param offset
     *            The offset on the line from a to b measured from a. Can be in
     *            negative or positive direction.
     * @return the calculated point on the line.
     * @throws InvalidMapDataException
     *             If the calculation results in an invalid geo coordinate
     *             according to WGS84.
     */
    public static GeoCoordinates pointAlongLine(final GeoCoordinates coord1,
                                                final GeoCoordinates coord2, final double offset)
            throws InvalidMapDataException {
        return pointAlongLine(coord1.getLongitudeDeg(),
                coord1.getLatitudeDeg(), coord2.getLongitudeDeg(),
                coord2.getLatitudeDeg(), offset);
    }

    /**
     * Calculates a point on the straight line defined by point a and b. The
     * parameter <code>offset</code> defines the position of the desired point
     * on that line relative to point a. If it is above 0 it will deliver a
     * point in direction to point 2 or above. The offset can even be negative-
     * it then defines a point in the opposite direction.
     *
     * @param longitudeA
     *            the longitude of the straight's start point
     * @param latitudeA
     *            the latitude of the straight's start point
     * @param longitudeB
     *            the longitude of the straight's end point
     * @param latitudeB
     *            the latitude of the straight's end point
     * @param offset
     *            The offset on the line from a to b measured from a. Can be in
     *            negative or positive direction.
     * @return the calculated point on the line.
     * @throws InvalidMapDataException
     *             If the calculation results in an invalid geo coordinate
     *             according to WGS84.
     */
    public static GeoCoordinates pointAlongLine(final double longitudeA,
                                                final double latitudeA, final double longitudeB,
                                                final double latitudeB, final double offset)
            throws InvalidMapDataException {

        double deltaX = Math.abs(longitudeB - longitudeA);
        double deltaY = latitudeB - latitudeA;
        if ((longitudeA > longitudeB)) {
            deltaX = -deltaX;
        }
        if (latitudeA < latitudeB) {
            if (deltaY < 0) {
                deltaY = -deltaY;
            }
        } else {
            if (deltaY > 0) {
                deltaY = -deltaY;
            }
        }
        deltaX *= offset;
        deltaY *= offset;
        double lon = longitudeA + deltaX;
        double lat = latitudeA + deltaY;
        return new GeoCoordinatesImpl(lon, lat);
    }

    /**
     * Scale the upperright point of a rectangle given by lowerleft and
     * upperright wrt to given factors in x- and y-direction.
     *
     * @param lowerLeft
     *            the lower left GeoCoordinates
     * @param upperRight
     *            the upper right GeoCoordinates
     * @param xfactor
     *            the scaling factor in x-direction
     * @param yfactor
     *            the scaling factor in y-direction
     * @return the pair (lon,lat) of the new rightmost
     * @throws InvalidMapDataException
     *             the invalid map data exception
     */
    public static GeoCoordinates scaleUpperRightCoordinate(
            final GeoCoordinates lowerLeft, final GeoCoordinates upperRight,
            final double xfactor, final double yfactor)
            throws InvalidMapDataException {
        return scaleUpperRightCoordinate(lowerLeft.getLongitudeDeg(),
                lowerLeft.getLatitudeDeg(), upperRight.getLongitudeDeg(),
                upperRight.getLatitudeDeg(), xfactor, yfactor);
    }

    /**
     * Scale the upperright point of a rectangle given by lowerleft and
     * upperright wrt to given factors in x- and y-direction.
     *
     * @param lowerLeftLon
     *            the longitude of the leftmost
     * @param lowerLeftLat
     *            the latitude of the leftmost
     * @param upperRightLon
     *            the longitude of the rightmost
     * @param upperRightLat
     *            the latitude of the rightmost
     * @param xfactor
     *            the scaling factor in x-direction
     * @param yfactor
     *            the scaling factor in y-direction
     * @return the pair (lon,lat) of the new rightmost
     * @throws InvalidMapDataException
     *             the invalid map data exception
     */
    public static GeoCoordinates scaleUpperRightCoordinate(
            final double lowerLeftLon, final double lowerLeftLat,
            final double upperRightLon, final double upperRightLat,
            final double xfactor, final double yfactor)
            throws InvalidMapDataException {
        assert (xfactor > 0. && yfactor > 0.);
        GeoCoordinates newBottomRight = pointAlongLine(lowerLeftLon,
                lowerLeftLat, upperRightLon, lowerLeftLat, xfactor);
        GeoCoordinates newTopLeft = pointAlongLine(lowerLeftLon, lowerLeftLat,
                lowerLeftLon, upperRightLat, yfactor);
        return new GeoCoordinatesImpl(newBottomRight.getLongitudeDeg(),
                newTopLeft.getLatitudeDeg());
    }

    /**
     * Round.
     *
     * @param val
     *            the val
     * @return the double
     */
    public static double round(final double val) {
        return round(val, DEFAULT_PRECISION);
    }

    /**
     * Rounds a coordinate value (longitude or latitude) to a specific
     * precision.
     *
     * @param val
     *            The coordinate value.
     * @param decimalPlaces
     *            The number of decimal place to round to.
     * @return The rounded value.
     */
    public static double round(final double val, final int decimalPlaces) {
        return Math.round(val * Math.pow(TEN, decimalPlaces))
                / Math.pow(TEN, decimalPlaces);
    }

    /**
     * Determine coordinate in distance.
     *
     * @param coord
     *            the GeoCoordinates the lon
     * @param angle
     *            the angle
     * @param distanceKm
     *            the distance in kilometers
     * @return the geo coordinates
     * @throws InvalidMapDataException
     *             the invalid map data exception
     */
    public static GeoCoordinates determineCoordinateInDistance(
            final GeoCoordinates coord, final int angle, final double distanceKm)
            throws InvalidMapDataException {
        return determineCoordinateInDistance(coord.getLongitudeDeg(),
                coord.getLatitudeDeg(), angle, distanceKm);
    }

    /**
     * Determine coordinate in distance.
     *
     * @param lon
     *            the lon
     * @param lat
     *            the lat
     * @param angle
     *            the angle
     * @param distanceKm
     *            the distance in kilometers
     * @return the geo coordinates
     * @throws InvalidMapDataException
     *             the invalid map data exception
     */
    public static GeoCoordinates determineCoordinateInDistance(
            final double lon, final double lat, final int angle,
            final double distanceKm) throws InvalidMapDataException {
        double lat1 = lat * Math.PI / HALF_CIRCLE;
        double az12 = angle * Math.PI / HALF_CIRCLE;
        double sinu1 = Math.sin(lat1);
        double cosu1 = Math.cos(lat1);
        double az12cos = Math.cos(az12);
        double az12sin = Math.sin(az12);
        double sina = cosu1 * az12sin;
        double ss = Math.sin(distanceKm
                / (EQUATORIAL_RADIUS / METER_PER_KILOMETER));
        double cs = Math.cos(distanceKm
                / (EQUATORIAL_RADIUS / METER_PER_KILOMETER));
        double g = sinu1 * ss - cosu1 * cs * az12cos;
        double lat2 = Math.atan(((sinu1 * cs + cosu1 * ss * az12cos) / (Math
                .sqrt(sina * sina + g * g)))) * HALF_CIRCLE / Math.PI;
        double lon2 = lon
                + Math.atan(ss * az12sin / (cosu1 * cs - sinu1 * ss * az12cos))
                * HALF_CIRCLE / Math.PI + (2 * FULL_CIRCLE_DEGREE);
        while (lat2 > QUARTER_CIRCLE) {
            lat2 = lat2 - FULL_CIRCLE_DEGREE;
        }
        while (lon2 > HALF_CIRCLE) {
            lon2 = lon2 - FULL_CIRCLE_DEGREE;
        }
        return new GeoCoordinatesImpl(lon2, lat2);
    }

    /**
     * Degree of interval between two angles
     * @param firstBearing angle of line to the north
     * @param secondBearing angle of line to the north
     * @return Absolute rounded interval value
     */
    public static double bearingDifference(double firstBearing, double secondBearing) {
        double diff = Math.round(Math.abs(firstBearing - secondBearing));
        if (diff > HALF_CIRCLE) {
            diff = FULL_CIRCLE - diff;
        }
        return diff;
    }


    /**
     * The Enum BearingDirection defines the direction off a line being used for
     * the bearing calculation. The bearing value can be measured in direction
     * (starting at the start node) or against the direction (starting at the
     * end node).
     */
    public enum BearingDirection {
        /** in direction */
        IN_DIRECTION,
        /** against direction */
        AGAINST_DIRECTION
    }

}
