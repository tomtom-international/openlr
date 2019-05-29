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
package openlr.decoder.rating;

import openlr.LocationReferencePoint;
import openlr.OpenLRProcessingException;
import openlr.decoder.properties.OpenLRDecoderProperties;
import openlr.map.Line;

/**
 * The Interface OpenLRRating defines methods for calculating a rating value of
 * a line in a road network. The rating value shall indicate how good the
 * attributes of the line match to the attributes of a location reference point.
 * The higher the rating value the better the line matches the specified
 * attributes.
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public interface OpenLRRating {

	/**
	 * The Enum RatingCategory defines 4 different categories in which the
	 * compared attributes might fall into. The ranking of the categories from
	 * best to worst is: EXCELLENT -> GOOD -> AVERAGE -> POOR.
	 */
	public enum RatingCategory {

		/**
		 * indicates an excellent match of the attributes of the line with the
		 * attributes specified in the LRP
		 */
		EXCELLENT("Excellent"),

		/**
		 * indicates a good match of the attributes of the line with the
		 * attributes specified in the LRP
		 */
		GOOD("Good"),

		/**
		 * indicates an average match of the attributes of the line with the
		 * attributes specified in the LRP
		 */
		AVERAGE("Average"),

		/**
		 * indicates a poor match of the attributes of the line with the
		 * attributes specified in the LRP
		 */
		POOR("Poor");
		
		/** The identifier. */
		private final String identifier;
		
		/**
		 * Instantiates a new rating category.
		 *
		 * @param ident the ident
		 */
		RatingCategory(final String ident) {
			identifier = ident;
		}
		
		/**
		 * Gets the identifier.
		 *
		 * @return the identifier
		 */
		public final String getIdentifier() {
			return identifier;
		}
	}

	/**
	 * Calculates a rating value for line which needs to match the attributes of
	 * the location reference point. The rating function is parameterized by the
	 * OpenLR properties. The rating could also be influenced by the distance
	 * between the LRP position and the corresponding node of the line.
	 *
	 * @param properties the OpenLR properties
	 * @param distance the distance between the LRP coordinate and the node or
	 * projection point
	 * @param p the location reference point
	 * @param line the line which needs to be rated
	 * @param projectionAlongLine the projection along line
	 * @return the rating value for the line line
	 * @throws OpenLRProcessingException the open lr processing exception
	 */
	int getRating(OpenLRDecoderProperties properties, int distance,
			LocationReferencePoint p, Line line, int projectionAlongLine)
			throws OpenLRProcessingException;
}
