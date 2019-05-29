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
package openlr.decoder.data;

import java.util.Collections;
import java.util.List;

import openlr.LocationReferencePoint;
import openlr.StatusCode;
import openlr.decoder.DecoderReturnCode;
import openlr.decoder.OpenLRDecoderProcessingException;
import openlr.decoder.OpenLRDecoderProcessingException.DecoderProcessingError;
import openlr.map.Line;
import cern.colt.map.OpenIntObjectHashMap;

/**
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public class ResolvedRoutes {
	
	/**
	 * The Enum Status.
	 */
	public enum Status {
		
		/** The AL l_ route s_ resolved. */
		ALL_ROUTES_RESOLVED,
		
		/** The PROCESSING. */
		PROCESSING,
		
		/** The ERROR. */
		ERROR;
	}
	
	/** The path calculated for this LRP. */
	private final OpenIntObjectHashMap calculatedShortestPaths = new OpenIntObjectHashMap();
	
	/** The status. */
	private Status statusCode = Status.PROCESSING;
	
	/** The return code. */
	private DecoderReturnCode returnCode;
	
	/**
	 * Put route.
	 *
	 * @param lrp the lrp
	 * @param route the route
	 * @param start the start
	 * @param end the end
	 */
	public final void putRoute(final LocationReferencePoint lrp, final List<Line> route, final CandidateLine start, final CandidateLine end) {
		RouteWithCandidateLines rwl = new RouteWithCandidateLines(route, start, end);
		calculatedShortestPaths.put(lrp.getSequenceNumber(), rwl);
	}
	
	/**
	 * All routes resolved.
	 *
	 * @return true, if successful
	 */
	public final boolean allRoutesResolved() {
		return statusCode == Status.ALL_ROUTES_RESOLVED;
	}
	
	/**
	 * Sets the return code.
	 *
	 * @param retCode the new return code
	 */
	public final void setError(final DecoderReturnCode retCode) {
		returnCode = retCode;
		statusCode = Status.ERROR;
	}
	
	/**
	 * Sets the return code.
	 *
	 */
	public final void setAllResolved() {
		statusCode = Status.ALL_ROUTES_RESOLVED;
	}
	
	/**
	 * Checks for error code.
	 *
	 * @return true, if successful
	 */
	public final boolean hasErrorCode() {
		return (statusCode == Status.ERROR);
	}
	
	/**
	 * Gets the error code.
	 *
	 * @return the error code
	 */
	public final Status getReturnCode() {
		return statusCode;
	}
	
	/**
	 * Gets the route.
	 *
	 * @param lrp the lrp
	 * @return the route
	 */
	@SuppressWarnings("unchecked")
	public final List<Line> getRoute(final LocationReferencePoint lrp) {
		RouteWithCandidateLines rwl = (RouteWithCandidateLines) calculatedShortestPaths.get(lrp.getSequenceNumber());
		if (rwl == null) {
			return Collections.EMPTY_LIST;
		}
		return rwl.getRoute();
	}
	
	/**
	 * Gets the candidate start.
	 *
	 * @param lrp the lrp
	 * @return the candidate start
	 * @throws OpenLRDecoderProcessingException the open lr decoder processing exception
	 */
	public final CandidateLine getCandidateStart(final LocationReferencePoint lrp) throws OpenLRDecoderProcessingException {
		RouteWithCandidateLines rwl = (RouteWithCandidateLines) calculatedShortestPaths.get(lrp.getSequenceNumber());
		if (rwl == null) {
			throw new OpenLRDecoderProcessingException(DecoderProcessingError.ROUTE_RESOLVE_ERROR);
		}
		return rwl.getStartCandidate();
	}
	
	/**
	 * Gets the candidate end.
	 *
	 * @param lrp the lrp
	 * @return the candidate end
	 * @throws OpenLRDecoderProcessingException the open lr decoder processing exception
	 */
	public final CandidateLine getCandidateEnd(final LocationReferencePoint lrp) throws OpenLRDecoderProcessingException {
		RouteWithCandidateLines rwl = (RouteWithCandidateLines) calculatedShortestPaths.get(lrp.getSequenceNumber());
		if (rwl == null) {
			throw new OpenLRDecoderProcessingException(DecoderProcessingError.ROUTE_RESOLVE_ERROR);
		}
		return rwl.getEndCandidate();
	}
	
	/**
	 * The Class RouteWithCandidateLines.
	 */
	private static final class RouteWithCandidateLines {
		
		/** The route. */
		private final List<Line> route;
		
		/** The start. */
		private CandidateLine start;
		
		/** The end. */
		private CandidateLine end;
		
		/**
		 * Instantiates a new route with candidate lines.
		 *
		 * @param r the r
		 * @param s the s
		 * @param e the e
		 */
		public RouteWithCandidateLines(final List<Line> r, final CandidateLine s, final CandidateLine e) {
			route = r;
			start = s;
			end = e;			
		}
		
		/**
		 * Gets the start candidate.
		 *
		 * @return the start candidate
		 */
		CandidateLine getStartCandidate() {
			return start;
		}
		
		/**
		 * Gets the end candidate.
		 *
		 * @return the end candidate
		 */
		CandidateLine getEndCandidate() {
			return end;
		}
		
		/**
		 * Gets the route.
		 *
		 * @return the route
		 */
		List<Line> getRoute() {
			return route;
		}
	}

	/**
	 * Gets the error code.
	 *
	 * @return the error code
	 */
	public final StatusCode getErrorCode() {
		return returnCode;
	}

}
