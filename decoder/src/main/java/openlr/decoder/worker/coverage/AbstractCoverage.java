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
package openlr.decoder.worker.coverage;

import openlr.decoder.OpenLRDecoderProcessingException;
import openlr.decoder.OpenLRDecoderProcessingException.DecoderProcessingError;
import openlr.decoder.location.AffectedLinesImpl;
import openlr.location.data.AffectedLines;
import openlr.map.GeoCoordinates;
import openlr.map.InvalidMapDataException;
import openlr.map.Line;
import openlr.map.MapDatabase;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author DLR e.V. (RE)
 */
public abstract class AbstractCoverage {

    /** the Constant MINIMUM_ADDITIONAL_DISTANCE. */
    public static final int MINIMUM_ADDITIONAL_DISTANCE = 100;

    /** The Constant COORD_TO_INT_FACTOR. */
    public static final int COORD_TO_INT_FACTOR = 100000;

    /** the Constant PATH_ITERATOR_COORDINATE_SIZE. */
    public static final int PATH_ITERATOR_COORDINATE_SIZE = 6;

    /**
     * Find all lines covered (i.e., completely covered or partially covered,
     * i.e. intersected) by an area location. For the returned iterator to be
     * different from null, the location has to be an area location that is a
     * circle, a rectangle or grid or a polygon location.
     *
     * @param mdb
     *            the mdb
     * @return the iterator<? extends line>
     * @throws OpenLRDecoderProcessingException
     *             the open lr decoder processing exception
     */
    abstract Iterator<? extends Line> determineCoveredLines(
            final MapDatabase mdb) throws OpenLRDecoderProcessingException;

    /**
     * Checks if is contained in.
     *
     * @param longitude
     *            the longitude
     * @param latitude
     *            the latitude
     * @return true, if is contained in
     * @throws InvalidMapDataException
     *             the invalid map data exception
     */
    abstract boolean isContainedIn(final double longitude, final double latitude)
            throws InvalidMapDataException;

    /**
     * Intersects boundary.
     *
     * @param gcStart the gc start
     * @param gcEnd the gc end
     * @return true, if successful
     * @throws InvalidMapDataException the invalid map data exception
     */
    abstract boolean intersectsBoundary(final GeoCoordinates gcStart, final GeoCoordinates gcEnd) throws InvalidMapDataException;

    /**
     * Gets the affected lines.
     *
     * @param mdb
     *            the mdb
     * @return the affected lines
     * @throws OpenLRDecoderProcessingException
     *             the open lr decoder processing exception
     */
    public final AffectedLines getAffectedLines(final MapDatabase mdb)
            throws OpenLRDecoderProcessingException {
        if (mdb == null) {
            return AffectedLinesImpl.EMPTY;
        }
        return makeCoveredAndIntersectedLinesList(determineCoveredLines(mdb));
    }

    /**
     * Create a polygon from the list of corner points.
     *
     * @param corners
     *            the corners
     * @return the polygon
     */
    final Polygon createPolygon(final List<? extends GeoCoordinates> corners) {
        int[] xpoints = new int[corners.size()];
        int[] ypoints = new int[corners.size()];
        int i = 0;
        for (GeoCoordinates geoCoord : corners) {
            double xrep = geoCoord.getLongitudeDeg() * COORD_TO_INT_FACTOR;
            int intxrep = (int) xrep;
            xpoints[i] = intxrep;
            double yrep = geoCoord.getLatitudeDeg() * COORD_TO_INT_FACTOR;
            int intyrep = (int) yrep;
            ypoints[i++] = intyrep;
        }
        return new Polygon(xpoints, ypoints, corners.size());
    }

    /**
     * Generate and return a pair of two lists: the list of lines covered by the
     * area location given by the raw location reference and the list of lines
     * intersected by the boundary of the aforementioned area location.
     *
     * @param coveredLinesIter
     *            the covered lines iter
     * @return a pair containing the list of covered lines and the list of
     *         intersected lines
     * @throws OpenLRDecoderProcessingException
     *             the open lr decoder runtime exception
     */
    public final AffectedLines makeCoveredAndIntersectedLinesList(
            final Iterator<? extends Line> coveredLinesIter)
            throws OpenLRDecoderProcessingException {
        List<Line> coveredLines = new ArrayList<Line>();
        List<Line> intersectedLines = new ArrayList<Line>();
        try {
            while (coveredLinesIter.hasNext()) {
                Line line = coveredLinesIter.next();
                List<GeoCoordinates> shape = line.getShapeCoordinates();
                if (shape != null) {

                    Iterator<GeoCoordinates> path = shape.iterator();
                    boolean atLeastOneWasInside = false;
                    boolean atLeastOneWasOutside = false;
                    boolean boundaryIntersection = false;
                    GeoCoordinates previous = null;

                    int nrShapePoints = 0;
                    while (path.hasNext()) {

                        GeoCoordinates current = path.next();

                        nrShapePoints++;

                        if (previous != null) {
                            boundaryIntersection = intersectsBoundary(previous,
                                    current);
                        }

                        if (isContainedIn(current.getLongitudeDeg(),
                                current.getLatitudeDeg())) {
                            if (atLeastOneWasOutside) {
                                intersectedLines.add(line);
                                break;
                            } else {
                                atLeastOneWasInside = true;
                            }
                        } else {
                            if (atLeastOneWasInside || boundaryIntersection) {
                                intersectedLines.add(line);
                                break;
                            } else {
                                atLeastOneWasOutside = true;
                            }
                        }
                        // }
                        if (!path.hasNext() && !atLeastOneWasOutside) {
                            if (nrShapePoints > 2 || checkMidPoint(line)) {
                                coveredLines.add(line);
                            } else {
                                intersectedLines.add(line);
                            }
                        }

                        previous = current;
                    }
                } else if ((isContainedIn(
                        line.getStartNode().getLongitudeDeg(), line
                                .getStartNode().getLatitudeDeg()) && !isContainedIn(
                        line.getEndNode().getLongitudeDeg(), line.getEndNode()
                                .getLatitudeDeg()))
                        || (!isContainedIn(line.getStartNode()
                        .getLongitudeDeg(), line.getStartNode()
                        .getLatitudeDeg()) && isContainedIn(line
                        .getEndNode().getLongitudeDeg(), line
                        .getEndNode().getLatitudeDeg()))) {
                    intersectedLines.add(line);
                }
            }
        } catch (InvalidMapDataException e) {
            throw new OpenLRDecoderProcessingException(
                    DecoderProcessingError.INVALID_MAP_DATA, e);
        }
        return new AffectedLinesImpl(coveredLines, intersectedLines);
    }

    /**
     * Check mid point.
     *
     * @param l the l
     * @return true, if successful
     * @throws InvalidMapDataException the invalid map data exception
     */
    private boolean checkMidPoint(final Line l) throws InvalidMapDataException {
        int dist = l.getLineLength() / 2;
        GeoCoordinates p = l.getGeoCoordinateAlongLine(dist);
        return isContainedIn(p.getLongitudeDeg(), p.getLatitudeDeg());
    }

}
