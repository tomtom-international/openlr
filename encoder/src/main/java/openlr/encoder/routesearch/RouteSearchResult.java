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
package openlr.encoder.routesearch;

import java.util.ArrayList;
import java.util.List;

import openlr.map.Line;
import openlr.map.utils.PathUtils;

/**
 * The Class RouteData encapsulates the results of a route search. It stores the
 * result type, the route being found and also information about new OpenLR
 * intermediate location reference points being detected during route search.
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public class RouteSearchResult {

	/**
	 * The Enum RouteSearchResult defines the possible return codes for a OpenLR
	 * route search.
	 */
	public enum RouteSearchReturnCode {

		/** route is found. */
		ROUTE_FOUND,

		/** intermediate is found. */
		INTERMEDIATE_FOUND,

		/** no route found. */
		NO_ROUTE_FOUND,

		/** nothing is calculated yet. */
		NOT_CALCULATED
	}

	/** The route being calculated. */
	private final List<Line> theRoute = new ArrayList<Line>();

	/** The intermediate (if an intermediate is found). */
	private final Line theIntermediate;
	
	/** The intermediate pos. */
	private final int intermediatePos;

	/** The result code. */
	private final RouteSearchReturnCode result;

	/** The second intermediate (if applicable). */
	private final Line secondIntermediate;
	
	/**
	 * Instantiates a new route search result.
	 *
	 * @param code the code
	 */
	RouteSearchResult(final RouteSearchReturnCode code) {
		theIntermediate = null;
		intermediatePos = -1;
		result = code;
		secondIntermediate = null;
	}

	/**
	 * Instantiates a new route search result.
	 *
	 * @param code the code
	 * @param route the route
	 * @param line the line
	 * @param pos the pos
	 */
	RouteSearchResult(final RouteSearchReturnCode code,
			final List<Line> route, final Line line, final int pos) {
		result = code;
		intermediatePos = pos;
		theIntermediate = line;
		theRoute.addAll(route);
		secondIntermediate = null;
	}

	/**
	 * Instantiates a new route search result.
	 *
	 * @param code the code
	 * @param route the route
	 */
	public RouteSearchResult(final RouteSearchReturnCode code, final List<Line> route) {
		result = code;
		intermediatePos = -1;
		theIntermediate = null;
		theRoute.addAll(route);
		secondIntermediate = null;
	}

	/**
	 * Instantiates a new route search result.
	 *
	 * @param code the code
	 * @param route the route
	 * @param line the line
	 * @param pos the pos
	 * @param second the second
	 */
	public RouteSearchResult(final RouteSearchReturnCode code,
			final List<Line> route, final Line line, final int pos, final Line second) {
		result = code;
		intermediatePos = pos;
		theIntermediate = line;
		theRoute.addAll(route);
		secondIntermediate = second;
	}

	/**
	 * Gets the the calculated route (if routing finished successfully).
	 * 
	 * @return the calculated route
	 */
	public final List<Line> getRoute() {
		return theRoute;
	}

	/**
	 * Gets the length of the calculated route.
	 * 
	 * @return the route length or -1 if no route was calculated
	 */
	public final int getRouteLength() {
		return PathUtils.getLength(theRoute);
	}

	/**
	 * Gets the intermediate being detected during route search. The method
	 * returns null if no intermediate was detected
	 * 
	 * @return the intermediate
	 */
	public final Line getIntermediate() {
		return theIntermediate;
	}
	
	/**
	 * Gets the intermediate pos.
	 * 
	 * @return the intermediate pos
	 */
	public final int getIntermediatePos() {
		return intermediatePos;
	}

	/**
	 * Gets the route search result code.
	 * 
	 * @return the route search result code
	 */
	public final RouteSearchReturnCode getResult() {
		return result;
	}
	
	/**
	 * Checks for the existence of a second intermediate.
	 * 
	 * @return true, if a second intermediate exists
	 */
	public final boolean hasSecondIntermediate() {
		return secondIntermediate != null;
	}

	/**
	 * Gets the second intermediate if it exists, otherwise null.
	 * 
	 * @return the second intermediate if it exists, otherwise null
	 */
	public final Line getSecondIntermediate() {
		return secondIntermediate;
	}

}
