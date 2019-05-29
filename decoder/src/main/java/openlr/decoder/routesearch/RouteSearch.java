/**
 * Licensed to the TomTom International B.V. under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  TomTom International B.V.
 * licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
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
package openlr.decoder.routesearch;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import openlr.map.Line;
import openlr.map.Node;
import openlr.map.utils.GeometryUtils;
import openlr.map.utils.PQElem;
import openlr.map.utils.PathUtils;

import org.apache.log4j.Logger;

/**
 * The class RouteSearch provides a method to calculate the shortest path
 * between start and destination line. The calculation stops if
 * <ul>
 * <li>a shortest-path is found
 * <li>the whole network is investigated without having found a proper route
 * <li>the maximum distance of a route has been exceeded
 * </ul>
 * The route search will be limited to lines having a functional road class
 * which is more important than the frc value indicated by lowest_frc. The
 * isLast property needs to be set for the route calculation to the last LRP
 * because in this case the implemented A* algorithm needs to use a different
 * target for its heuristic values. The heuristic being used in this
 * implementation is the airline distance between two nodes.
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public class RouteSearch {

	/** logger */
	private static final Logger LOG = Logger.getLogger(RouteSearch.class);

	/**
	 * The route search will stop in one of these status.
	 */
	public enum RouteSearchResult {
		/** route is found. */
		ROUTE_FOUND,

		/** no route found. */
		NO_ROUTE_FOUND,

		/** construction of the route failed. */
		ROUTE_CONSTRUCTION_FAILED,

		/** nothing is calculated yet. */
		NOT_CALCULATED
	}

	/** The result status. */
	private RouteSearchResult state = RouteSearchResult.NOT_CALCULATED;

	/** The calculated route. */
	private List<Line> theRoute = null;

	/** The length of the calculated route. */
	private int routeLength = -1;

	/**
	 * Calculates the shortest path between start and destination line. The
	 * calculation stops if
	 * <ul>
	 * <li>a shortest-path is found
	 * <li>the whole network is investigated without having found a proper route
	 * <li>the maximum distance of a route has been exceeded
	 * </ul>
	 * The route search will be limited to lines having a functional road class
	 * which is more important than the frc value indicated by lowest_frc. The
	 * isLast property needs to be set for the route calculation to the last LRP
	 * because in this case the implemented A* algorithm needs to use a
	 * different target for its heuristic values. The heuristic being used in
	 * this implementation is the airline distance between two nodes.
	 * 
	 * @param startline
	 *            the start of the route calculation
	 * @param destline
	 *            the destination of the route calculation
	 * @param maxDistance
	 *            the max_distance for a shortest-path
	 * @param lowestFRC
	 *            the lowest functional road class for lines being investigated
	 *            during the search
	 * @param isLast
	 *            indicator if this is the last route search
	 * 
	 * @return the status of the route search after stopping the search
	 */
	public final RouteSearchResult calculateRoute(final Line startline,
			final Line destline, final int maxDistance, final int lowestFRC,
			final boolean isLast) {

		if (startline == null || destline == null) {
			state = RouteSearchResult.ROUTE_CONSTRUCTION_FAILED;
			return state;
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("route calculation between start (" + startline.getID()
					+ ") and destination (" + destline.getID() + ")");
		}
		// reset global data structures (data needs to be stored in the class)
		theRoute = null;
		routeLength = -1;
		state = RouteSearchResult.NOT_CALCULATED;
		// setup local data structures
		RouteSearchData data = new RouteSearchData();

		// for the use of the heuristic
		Node e = null;
		if (isLast) {
			e = destline.getEndNode();
		} else {
			e = destline.getStartNode();
		}
		double destX = e.getLongitudeDeg();
		double destY = e.getLatitudeDeg();

		// initialize the open list with the start element
		int startLength = startline.getLineLength();
		PQElem startElem = new PQElem(startline, 0/*
												 * HACK: we do not need the
												 * correct value, this is faster
												 * now
												 */, startLength, null);
		data.addToOpen(startElem);

		// main loop
		while (!data.isOpenEmpty()) {
			// stop if route is found or if no further line is
			// available

			// get the top element of the open list
			PQElem actualElement = data.pollElement();

			// check if we have found the destination and if so then construct
			// the route and stop calculation
			if (isLast && actualElement.getLine().getID() == destline.getID()) {
				setRouteFound(actualElement);
				break;
			} else if (!isLast && actualElement.getLine().getEndNode().equals(e)) {
				Iterator<? extends Line> iter = actualElement.getLine()
						.getNextLines();
				boolean destFound = false;
				while (iter.hasNext()) {
					if (iter.next().getID() == destline.getID()) {
						destFound = true;
						break;
					}
				}
				if (destFound) {
					setRouteFound(actualElement);
					break;
				}
			} else {
				// iterate over the successors, because we have not reached the
				// end
				extractNextLines(maxDistance, lowestFRC, data,
						destX, destY, actualElement);
			}
		}
		if (state == RouteSearchResult.NOT_CALCULATED) {
			// uuh, route calculation stopped but there was no route
			state = RouteSearchResult.NO_ROUTE_FOUND;
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("route search finished with status: " + state.name());
		}
		return state;
	}

	/**
	 * Extract the successor lines and add the lines to the open list if a
	 * shorter distance to that line is found. This method takes the maximum
	 * distance and lowest function road class criteria into account.
	 *
	 * @param maxDistance maximum distance allowed for the shortest path
	 * @param lowestFRC lowest functional road class being used in this search
	 * @param data the data
	 * @param destX the x coordinate for the calculation of the heuristic value
	 * @param destY the y coordinate for the calculation of the heuristic value
	 * @param actualElement the current element
	 */
	private void extractNextLines(final int maxDistance, final int lowestFRC,
			final RouteSearchData data,
			final double destX, final double destY, final PQElem actualElement) {
		Iterator<? extends Line> iterNext = actualElement.getLine()
				.getNextLines();
		// check all successors
		while (iterNext.hasNext()) {
			Line succ = iterNext.next();
			// check lowest functional road class property
			if (succ.getFRC().getID() > lowestFRC) {
                if (LOG.isTraceEnabled()) {
                    LOG.trace("Stop route searching a path because of too high FRC "
                            + succ.getFRC() + " at line " + succ.getID());
                }
				continue;
			}

			// calculate heuristic value, length, weighted length
			Node succEnd = succ.getEndNode();

			int succLength = succ.getLineLength();
			int newDist = actualElement.getSecondVal() + succLength;
			if (newDist > maxDistance) {
                if (LOG.isTraceEnabled()) {
                    LOG.trace("Stop route searching a path because of reaching max search distance with "
                            + newDist + " meters at line " + succ.getID());
                }
				continue;
			}
			int heurist = (int) Math.round(GeometryUtils.distance(destX, destY,
					succEnd.getLongitudeDeg(), succEnd.getLatitudeDeg()));
			int newHeurVal = newDist + heurist;
			// check if we already have a value for this line
			if (data.hasLengthValue(succ)) {
				// check if we can improve the value
				if (newDist < data.getLengthValue(succ)) {
					// improving the value means deleting the existing
					// entry and creating a new one with the better
					// values
					PQElem newElem = new PQElem(succ, newHeurVal, newDist,
							actualElement);
					data.updateInOpen(newElem);
				}
			} else {
				// no value stored yet, so create a new one
				PQElem newElem = new PQElem(succ, newHeurVal, newDist,
						actualElement);
				data.addToOpen(newElem);
			}
		}
	}

	/**
	 * The route calculation found a route and the route will be constructed
	 * explicitly and the length value will be set properly. The route will be
	 * constructed starting with its end line and looking for its parent
	 * (predecessor) in the path until the method reaches the start of the
	 * route.
	 * 
	 * @param actualElement
	 *            the last element of the calculated route
	 */
	private void setRouteFound(final PQElem actualElement) {
		// route is found
		theRoute = PathUtils.constructPath(actualElement);
		if (theRoute == null) {
			// strange error
			LOG.error("route construction failed");
			state = RouteSearchResult.ROUTE_CONSTRUCTION_FAILED;
		} else {
			// route found and constructed, set the length values
			state = RouteSearchResult.ROUTE_FOUND;
			routeLength = actualElement.getSecondVal();
		}
	}

	/**
	 * Gets the calculated route if the route calculation was started and a
	 * route was found, otherwise the list is empty.
	 * 
	 * @return the calculated route or an empty list if no route calculated
	 */
	public final List<Line> getCalculatedRoute() {
		if (state == RouteSearchResult.ROUTE_FOUND) {
			return theRoute;
		}
		return Collections.emptyList();
	}

	/**
	 * Gets the length of the calculated route.
	 * 
	 * @return the route length
	 */
	public final int getRouteLength() {
		return routeLength;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("route search state: ").append(state);
		if (state == RouteSearchResult.ROUTE_FOUND) {
			sb.append(" route length: ").append(routeLength).append("m ");
			sb.append("[");
			for (int i = 0; i < theRoute.size(); i++) {
				Line l = theRoute.get(i);
				sb.append(l.getID());
				if (i == theRoute.size() - 1) {
					sb.append("]");
				} else {
					sb.append(", ");
				}
			}
		}
		return sb.toString();
	}

}
