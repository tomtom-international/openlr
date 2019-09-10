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
 * Copyright (C) 2009-2012 TomTom International B.V.
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
 *  Copyright (C) 2009-2012 TomTom International B.V.
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
package openlr.location;

import openlr.location.data.Orientation;
import openlr.location.data.SideOfRoad;
import openlr.map.GeoCoordinates;
import openlr.map.InvalidMapDataException;
import openlr.map.Line;
import openlr.map.utils.GeometryUtils;

import java.util.List;

/**
 * A factory for creating Location objects.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public final class LocationFactory {

    /**
     * Instantiates a new location factory.
     */
    private LocationFactory() {
        throw new UnsupportedOperationException();
    }

    /**
     * Instantiates a new location with a unique key and the location as a list
     * of lines including offset information used to find the precise location
     * on the location path.
     *
     * @param idString
     *            the unique ID
     * @param loc
     *            the location as a list of lines
     * @param pOff
     *            the distance between the start of the location and the start
     *            of the precise location
     * @param nOff
     *            the distance between the end of the location and the end of
     *            the precise location
     * @return the location
     */
    public static Location createLineLocationWithOffsets(final String idString,
                                                         final List<? extends Line> loc, final int pOff, final int nOff) {
        return new LineLocation(idString, loc, pOff, nOff);
    }

    /**
     * Instantiates a new location with a unique key and the location as a list
     * of lines. The positive and negative offset are treated as 0.
     *
     * @param idString
     *            the unique ID
     * @param loc
     *            the location as a list of lines
     * @return the location
     */
    public static Location createLineLocation(final String idString,
                                              final List<? extends Line> loc) {
        return new LineLocation(idString, loc, 0, 0);
    }

    // *************** GEO COORIDNATE ***********************.

    /**
     * Instantiates a new geo coordinate location.
     *
     * @param idString
     *            the id string
     * @param lonDeg
     *            the longitude
     * @param latDeg
     *            the latitude
     * @return the location
     * @throws InvalidMapDataException
     *             the invalid map data exception
     */
    public static Location createGeoCoordinateLocation(final String idString,
                                                       final double lonDeg, final double latDeg)
            throws InvalidMapDataException {
        return new GeoCoordLocation(idString, lonDeg, latDeg);
    }

    // ***************** POINT ALONG LINE ************************.

    /**
     * Instantiates a new point along line location.
     *
     * @param idString
     *            the id string
     * @param l
     *            the line
     * @param poff
     *            the positive offset
     * @param s
     *            the side of road
     * @param o
     *            the oroentation
     * @return the location
     * @throws InvalidMapDataException
     *             the invalid map data exception
     */
    public static Location createPointAlongLineLocationWithSideAndOrientation(
            final String idString, final Line l, final int poff,
            final SideOfRoad s, final Orientation o)
            throws InvalidMapDataException {
        return new PointAlongLocation(idString, l, poff, s, o);
    }

    /**
     * Instantiates a new point along line location.
     *
     * @param idString
     *            the id string
     * @param l
     *            the line
     * @param poff
     *            the positive offset
     * @param s
     *            the side of road
     * @return the location
     * @throws InvalidMapDataException
     *             the invalid map data exception
     */
    public static Location createPointAlongLineLocationWithSide(
            final String idString, final Line l, final int poff,
            final SideOfRoad s) throws InvalidMapDataException {
        return new PointAlongLocation(idString, l, poff, s,
                Orientation.getDefault());
    }

    /**
     * Instantiates a new point along line location.
     *
     * @param idString
     *            the id string
     * @param l
     *            the lien
     * @param poff
     *            the positive offset
     * @param o
     *            the orientation
     * @return the location
     * @throws InvalidMapDataException
     *             the invalid map data exception
     */
    public static Location createPointAlongLineLocationWithOrientation(
            final String idString, final Line l, final int poff,
            final Orientation o) throws InvalidMapDataException {
        return new PointAlongLocation(idString, l, poff,
                SideOfRoad.getDefault(), o);
    }

    /**
     * Instantiates a new point along line location.
     *
     * @param idString
     *            the id string
     * @param l
     *            the line
     * @param poff
     *            the positive offset
     * @return the location
     * @throws InvalidMapDataException
     *             the invalid map data exception
     */
    public static Location createPointAlongLineLocation(final String idString,
                                                        final Line l, final int poff) throws InvalidMapDataException {
        return new PointAlongLocation(idString, l, poff,
                SideOfRoad.getDefault(), Orientation.getDefault());
    }

    // ********************* NETWORK NODE ****************************.

    /**
     * Instantiates a new point along line location at a network node.
     *
     * @param idString
     *            the id string
     * @param l
     *            the line
     * @param s
     *            the side of road
     * @return the location
     * @throws InvalidMapDataException
     *             the invalid map data exception
     */
    public static Location createNodeLocationWithSide(final String idString,
                                                      final Line l, final SideOfRoad s) throws InvalidMapDataException {
        return new PointAlongLocation(idString, l, 0, s,
                Orientation.getDefault());
    }

    /**
     * Instantiates a new point along line location at a network node.
     *
     * @param idString
     *            the id string
     * @param l
     *            the line
     * @param o
     *            the orientation
     * @return the location
     * @throws InvalidMapDataException
     *             the invalid map data exception
     */
    public static Location createNodeLocationWithOrientation(
            final String idString, final Line l, final Orientation o)
            throws InvalidMapDataException {
        return new PointAlongLocation(idString, l, 0, SideOfRoad.getDefault(),
                o);
    }

    /**
     * Instantiates a new point along line location at a network node.
     *
     * @param idString
     *            the id string
     * @param l
     *            the line
     * @param s
     *            the side of road
     * @param o
     *            the orientation
     * @return the location
     * @throws InvalidMapDataException
     *             the invalid map data exception
     */
    public static Location createNodeLocationWithSideAndOrientation(
            final String idString, final Line l, final SideOfRoad s,
            final Orientation o) throws InvalidMapDataException {
        return new PointAlongLocation(idString, l, 0, s, o);
    }

    /**
     * Instantiates a new point along line location at a network node.
     *
     * @param idString
     *            the id string
     * @param l
     *            the line
     * @return the location
     * @throws InvalidMapDataException
     *             the invalid map data exception
     */
    public static Location createNodeLocation(final String idString,
                                              final Line l) throws InvalidMapDataException {
        return new PointAlongLocation(idString, l, 0);
    }

    // * **************** POI WITH ACCESS ************************

    /**
     * Instantiates a new poi with access point location.
     *
     * @param idString
     *            the id string
     * @param l
     *            the line
     * @param poff
     *            the positive offset
     * @param lonDeg
     *            the longitude
     * @param latDeg
     *            the latitude
     * @param s
     *            the side of road
     * @param o
     *            the orientation
     * @return the location
     * @throws InvalidMapDataException
     *             the invalid map data exception
     */
    public static Location createPoiAccessLocationWithSideAndOrientation(
            final String idString, final Line l, final int poff,
            final double lonDeg, final double latDeg, final SideOfRoad s,
            final Orientation o) throws InvalidMapDataException {
        return new PoiAccessLocation(idString, l, poff, lonDeg, latDeg, s, o);
    }

    /**
     * Instantiates a new poi with access point location.
     *
     * @param idString
     *            the id string
     * @param l
     *            the line
     * @param poff
     *            the positive offset
     * @param lonDeg
     *            the longitude
     * @param latDeg
     *            the latitude
     * @param s
     *            the side of road
     * @return the location
     * @throws InvalidMapDataException
     *             the invalid map data exception
     */
    public static Location createPoiAccessLocationWithSide(
            final String idString, final Line l, final int poff,
            final double lonDeg, final double latDeg, final SideOfRoad s)
            throws InvalidMapDataException {
        return new PoiAccessLocation(idString, l, poff, lonDeg, latDeg, s,
                Orientation.getDefault());
    }

    /**
     * Instantiates a new poi with access point location.
     *
     * @param idString
     *            the id string
     * @param l
     *            the line
     * @param poff
     *            the positive offset
     * @param lonDeg
     *            the longitude
     * @param latDeg
     *            the latitude
     * @param o
     *            the orientation
     * @return the location
     * @throws InvalidMapDataException
     *             the invalid map data exception
     */
    public static Location createPoiAccessLocationWithOrientation(
            final String idString, final Line l, final int poff,
            final double lonDeg, final double latDeg, final Orientation o)
            throws InvalidMapDataException {
        return new PoiAccessLocation(idString, l, poff, lonDeg, latDeg,
                SideOfRoad.getDefault(), o);
    }

    /**
     * Instantiates a new poi with access point location.
     *
     * @param idString
     *            the id string
     * @param l
     *            the line
     * @param poff
     *            the positive offset
     * @param lonDeg
     *            the longitude
     * @param latDeg
     *            the latitude
     * @return the location
     * @throws InvalidMapDataException
     *             the invalid map data exception
     */
    public static Location createPoiAccessLocation(final String idString,
                                                   final Line l, final int poff, final double lonDeg,
                                                   final double latDeg) throws InvalidMapDataException {
        return new PoiAccessLocation(idString, l, poff, lonDeg, latDeg,
                SideOfRoad.getDefault(), Orientation.getDefault());
    }

    // **************** POI WITH ACCESS AT NODE ***********************/

    /**
     * Instantiates a new poi with access point location at a network node.
     *
     * @param idString
     *            the id string
     * @param l
     *            the line
     * @param lonDeg
     *            the longitude
     * @param latDeg
     *            the latitude
     * @param s
     *            the side of road
     * @param o
     *            the orientation
     * @return the location
     * @throws InvalidMapDataException
     *             the invalid map data exception
     */
    public static Location createPoiAccessAtNodeLocationWithSideAndOrientation(
            final String idString, final Line l, final double lonDeg,
            final double latDeg, final SideOfRoad s, final Orientation o)
            throws InvalidMapDataException {
        return new PoiAccessLocation(idString, l, lonDeg, latDeg, s, o);
    }

    /**
     * Instantiates a new poi with access point location at a network node.
     *
     * @param idString
     *            the id string
     * @param l
     *            the line
     * @param lonDeg
     *            the longitude
     * @param latDeg
     *            the latitude
     * @return the location
     * @throws InvalidMapDataException
     *             the invalid map data exception
     */
    public static Location createPoiAccessAtNodeLocation(final String idString,
                                                         final Line l, final double lonDeg, final double latDeg)
            throws InvalidMapDataException {
        return new PoiAccessLocation(idString, l, 0, lonDeg, latDeg,
                SideOfRoad.getDefault(), Orientation.getDefault());
    }

    /**
     * Instantiates a new poi with access point location at a network node.
     *
     * @param idString
     *            the id string
     * @param l
     *            the line
     * @param lonDeg
     *            the longitude
     * @param latDeg
     *            the latitude
     * @param s
     *            the side of road
     * @return the location
     * @throws InvalidMapDataException
     *             the invalid map data exception
     */
    public static Location createPoiAccessAtNodeLocationWithSide(
            final String idString, final Line l, final double lonDeg,
            final double latDeg, final SideOfRoad s)
            throws InvalidMapDataException {
        return new PoiAccessLocation(idString, l, 0, lonDeg, latDeg, s,
                Orientation.getDefault());
    }

    /**
     * Instantiates a new poi with access point location at a network node.
     *
     * @param idString
     *            the id string
     * @param l
     *            the line
     * @param lonDeg
     *            the longitude
     * @param latDeg
     *            the latitude
     * @param o
     *            the orientation
     * @return the location
     * @throws InvalidMapDataException
     *             the invalid map data exception
     */
    public static Location createPoiAccessAtNodeLocationWithOrientation(
            final String idString, final Line l, final double lonDeg,
            final double latDeg, final Orientation o)
            throws InvalidMapDataException {
        return new PoiAccessLocation(idString, l, 0, lonDeg, latDeg,
                SideOfRoad.getDefault(), o);
    }

    // **************** DLR e.V. (RE): CIRCLE LOCATION ***********************/

    /**
     * Instantiates a new circle location.
     *
     * @param idString
     *            the id string
     * @param lonDeg
     *            the longitude
     * @param latDeg
     *            the latitude
     * @param radius
     *            the radius in meters
     * @return the location
     * @throws InvalidMapDataException
     *             the invalid map data exception
     */
    public static Location createCircleLocation(final String idString,
                                                final double lonDeg, final double latDeg, final long radius)
            throws InvalidMapDataException {
        return new CircleLocation(idString, lonDeg, latDeg, radius);
    }

    // **************** DLR e.V. (RE): RECTANGLE LOCATION *********************/

    /**
     * Instantiates a new rectangle location.
     *
     * @param idString
     *            the id string
     * @param lowerLeftLonDeg
     *            the lower left lon deg
     * @param lowerLeftLatDeg
     *            the lower left lat deg
     * @param upperRightLonDeg
     *            the upper right lon deg
     * @param upperRightLatDeg
     *            the upper right lat deg
     * @return the location
     * @throws InvalidMapDataException
     *             the invalid map data exception
     */
    public static Location createRectangleLocation(final String idString,
                                                   final double lowerLeftLonDeg, final double lowerLeftLatDeg,
                                                   final double upperRightLonDeg, final double upperRightLatDeg)
            throws InvalidMapDataException {
        return new RectangleLocation(idString, lowerLeftLonDeg,
                lowerLeftLatDeg, upperRightLonDeg, upperRightLatDeg);
    }

    /**
     * Creates a new Location object.
     *
     * @param string
     *            the string
     * @param rectangleLL
     *            the rectangle ll
     * @param rectangleUR
     *            the rectangle ur
     * @return the location
     * @throws InvalidMapDataException
     *             the invalid map data exception
     */
    public static Location createRectangleLocation(final String string,
                                                   final GeoCoordinates rectangleLL, final GeoCoordinates rectangleUR)
            throws InvalidMapDataException {
        return createRectangleLocation(string, rectangleLL.getLongitudeDeg(),
                rectangleLL.getLatitudeDeg(), rectangleUR.getLongitudeDeg(),
                rectangleUR.getLatitudeDeg());
    }

    // **************** DLR e.V. (RE): GRID LOCATION *********************/

    /**
     * Instantiates a new grid location.
     *
     * @param idString
     *            the id string
     * @param lowerLeftLonDeg
     *            the leftmost longitude
     * @param lowerLeftLatDeg
     *            the leftmost latitude
     * @param upperRightLonDeg
     *            the rightmost longitude
     * @param upperRightLatDeg
     *            the rightmost latitude
     * @param ncols
     *            the number of columns
     * @param nrows
     *            the number of rows
     * @return the location
     * @throws InvalidMapDataException
     *             the invalid map data exception
     */
    public static Location createGridLocationFromBasisCell(
            final String idString, final double lowerLeftLonDeg,
            final double lowerLeftLatDeg, final double upperRightLonDeg,
            final double upperRightLatDeg, final int ncols, final int nrows)
            throws InvalidMapDataException {
        return new GridLocation(idString, lowerLeftLonDeg, lowerLeftLatDeg,
                upperRightLonDeg, upperRightLatDeg, ncols, nrows);
    }

    /**
     * Creates a new Location object.
     *
     * @param idString
     *            the id string
     * @param lowerLeft
     *            the lower left
     * @param upperRight
     *            the upper right
     * @param ncols
     *            the ncols
     * @param nrows
     *            the nrows
     * @return the abstract location
     * @throws InvalidMapDataException
     *             the invalid map data exception
     */
    public static Location createGridLocationFromBasisCell(
            final String idString, final GeoCoordinates lowerLeft,
            final GeoCoordinates upperRight, final int ncols, final int nrows)
            throws InvalidMapDataException {
        return createGridLocationFromBasisCell(idString,
                lowerLeft.getLongitudeDeg(), lowerLeft.getLatitudeDeg(),
                upperRight.getLongitudeDeg(), upperRight.getLatitudeDeg(),
                ncols, nrows);
    }

    /**
     * Instantiates a new grid location. In this version, the encoding type is
     * passed via a flag: if the parameter <code>encodesLowerLeftCell</code> is
     * <code>true</code>, the encoder will see this, thus knowing, that the
     * basic building block of a grid, i.e. a single cell, is to be encoded and
     * therefore will not first split up the given rectangle into
     * <code>ncols</code> columns and <code>nrows</code> rows before
     * transmission. If, however, the flag is <code>false</code>, the encoder
     * will interpret the given rectangle as the boundary of the grid area
     * location, and thus, before transmission to the receiver side, extract a
     * lower left cell (by splitting the rectangle into <code>ncols</code>
     * columns and <code>nrows</code> rows). Upon receipt, the decoder also sees
     * this flag and will interpret the rectangle information accordingly.
     *
     * @param idString
     *            the id string
     * @param lowerLeftLonDeg
     *            the lower left lon deg
     * @param lowerLeftLatDeg
     *            the lower left lat deg
     * @param upperRightLonDeg
     *            the upper right lon deg
     * @param upperRightLatDeg
     *            the upper right lat deg
     * @param ncols
     *            the number of columns
     * @param nrows
     *            the number of rows
     * @return the location
     * @throws InvalidMapDataException
     *             the invalid map data exception
     */
    public static Location createGridLocationFromGridArea(
            final String idString, final double lowerLeftLonDeg,
            final double lowerLeftLatDeg, final double upperRightLonDeg,
            final double upperRightLatDeg, final int ncols, final int nrows)
            throws InvalidMapDataException {
        GeoCoordinates basisUpperRight = GeometryUtils.scaleUpperRightCoordinate(
                lowerLeftLonDeg, lowerLeftLatDeg,
                upperRightLonDeg, upperRightLatDeg,
                1. / ncols, 1. / nrows);
        return new GridLocation(idString, lowerLeftLonDeg, lowerLeftLatDeg,
                basisUpperRight.getLongitudeDeg(), basisUpperRight.getLatitudeDeg(), ncols, nrows);
    }

    /**
     * Creates a new Location object.
     *
     * @param string
     *            the string
     * @param lowerLeft
     *            the lower left
     * @param upperRight
     *            the upper right
     * @param columns
     *            the columns
     * @param rows
     *            the rows
     * @return the location
     * @throws InvalidMapDataException
     *             the invalid map data exception
     */
    public static Location createGridLocationFromGridArea(final String string,
                                                          final GeoCoordinates lowerLeft, final GeoCoordinates upperRight,
                                                          final int columns, final int rows) throws InvalidMapDataException {
        GeoCoordinates basisUpperRight = GeometryUtils.scaleUpperRightCoordinate(
                lowerLeft.getLongitudeDeg(), lowerLeft.getLatitudeDeg(),
                upperRight.getLongitudeDeg(), upperRight.getLatitudeDeg(),
                1. / columns, 1. / rows);
        return createGridLocationFromBasisCell(string,
                lowerLeft.getLongitudeDeg(), lowerLeft.getLatitudeDeg(),
                basisUpperRight.getLongitudeDeg(), basisUpperRight.getLatitudeDeg(),
                columns, rows);
    }

    // **************** DLR e.V. (RE): POLYGON LOCATION *********************/

    /**
     * Instantiates a new polygon location.
     *
     * @param idString
     *            the id string
     * @param cornerPoints
     *            the list of corner points
     * @return the location
     */
    public static Location createPolygonLocation(final String idString,
                                                 final List<? extends GeoCoordinates> cornerPoints) {
        return new PolygonLocation(idString, cornerPoints);
    }

    // ************** DLR e.V. (RE): CLOSED LINE LOCATION ********************/

    /**
     * Instantiates a new location with a unique key and the location as a list
     * of lines.
     *
     * @param idString
     *            the unique ID
     * @param loc
     *            the location as a list of lines
     * @return the location
     */
    public static Location createClosedLineLocation(final String idString,
                                                    final List<? extends Line> loc) {
        return new ClosedLineLocation(idString, loc);
    }

}
