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

import openlr.decoder.rating.OpenLRRating.RatingCategory;
import openlr.map.FormOfWay;

/**
 * The Class FormOfWayRatingTable.
 */
public class FormOfWayRatingTable {
	
	/**
	 * Fixed rating table for FOW. First index is used for the form of way of
	 * the LRP, second index is used for the form of way of the line.
	 */
	private final RatingCategory[][] fowRatingTable;
	
	
	/**
	 * Instantiates a new form of way rating table.
	 */
	public FormOfWayRatingTable() {
		int size = FormOfWay.getFOWs().size();
		fowRatingTable = new RatingCategory[size][size];
		
		fowRatingTable[FormOfWay.UNDEFINED.ordinal()][FormOfWay.UNDEFINED.ordinal()] = RatingCategory.AVERAGE;
		fowRatingTable[FormOfWay.UNDEFINED.ordinal()][FormOfWay.MOTORWAY.ordinal()] = RatingCategory.AVERAGE;
		fowRatingTable[FormOfWay.UNDEFINED.ordinal()][FormOfWay.MULTIPLE_CARRIAGEWAY.ordinal()] = RatingCategory.AVERAGE;
		fowRatingTable[FormOfWay.UNDEFINED.ordinal()][FormOfWay.SINGLE_CARRIAGEWAY.ordinal()] = RatingCategory.AVERAGE;
		fowRatingTable[FormOfWay.UNDEFINED.ordinal()][FormOfWay.ROUNDABOUT.ordinal()] = RatingCategory.AVERAGE;
		fowRatingTable[FormOfWay.UNDEFINED.ordinal()][FormOfWay.TRAFFIC_SQUARE.ordinal()] = RatingCategory.AVERAGE;
		fowRatingTable[FormOfWay.UNDEFINED.ordinal()][FormOfWay.SLIPROAD.ordinal()] = RatingCategory.AVERAGE;
		fowRatingTable[FormOfWay.UNDEFINED.ordinal()][FormOfWay.OTHER.ordinal()] = RatingCategory.AVERAGE;
		
		fowRatingTable[FormOfWay.MOTORWAY.ordinal()][FormOfWay.UNDEFINED.ordinal()] = RatingCategory.AVERAGE;
		fowRatingTable[FormOfWay.MOTORWAY.ordinal()][FormOfWay.MOTORWAY.ordinal()] = RatingCategory.EXCELLENT;
		fowRatingTable[FormOfWay.MOTORWAY.ordinal()][FormOfWay.MULTIPLE_CARRIAGEWAY.ordinal()] = RatingCategory.GOOD;
		fowRatingTable[FormOfWay.MOTORWAY.ordinal()][FormOfWay.SINGLE_CARRIAGEWAY.ordinal()] = RatingCategory.POOR;
		fowRatingTable[FormOfWay.MOTORWAY.ordinal()][FormOfWay.ROUNDABOUT.ordinal()] = RatingCategory.POOR;
		fowRatingTable[FormOfWay.MOTORWAY.ordinal()][FormOfWay.TRAFFIC_SQUARE.ordinal()] = RatingCategory.POOR;
		fowRatingTable[FormOfWay.MOTORWAY.ordinal()][FormOfWay.SLIPROAD.ordinal()] = RatingCategory.POOR;
		fowRatingTable[FormOfWay.MOTORWAY.ordinal()][FormOfWay.OTHER.ordinal()] = RatingCategory.POOR;
		
		fowRatingTable[FormOfWay.MULTIPLE_CARRIAGEWAY.ordinal()][FormOfWay.UNDEFINED.ordinal()] = RatingCategory.AVERAGE;
		fowRatingTable[FormOfWay.MULTIPLE_CARRIAGEWAY.ordinal()][FormOfWay.MOTORWAY.ordinal()] = RatingCategory.GOOD;
		fowRatingTable[FormOfWay.MULTIPLE_CARRIAGEWAY.ordinal()][FormOfWay.MULTIPLE_CARRIAGEWAY.ordinal()] = RatingCategory.EXCELLENT;
		fowRatingTable[FormOfWay.MULTIPLE_CARRIAGEWAY.ordinal()][FormOfWay.SINGLE_CARRIAGEWAY.ordinal()] = RatingCategory.GOOD;
		fowRatingTable[FormOfWay.MULTIPLE_CARRIAGEWAY.ordinal()][FormOfWay.ROUNDABOUT.ordinal()] = RatingCategory.AVERAGE;
		fowRatingTable[FormOfWay.MULTIPLE_CARRIAGEWAY.ordinal()][FormOfWay.TRAFFIC_SQUARE.ordinal()] = RatingCategory.POOR;
		fowRatingTable[FormOfWay.MULTIPLE_CARRIAGEWAY.ordinal()][FormOfWay.SLIPROAD.ordinal()] = RatingCategory.POOR;
		fowRatingTable[FormOfWay.MULTIPLE_CARRIAGEWAY.ordinal()][FormOfWay.OTHER.ordinal()] = RatingCategory.POOR;
		
		fowRatingTable[FormOfWay.SINGLE_CARRIAGEWAY.ordinal()][FormOfWay.UNDEFINED.ordinal()] = RatingCategory.AVERAGE;
		fowRatingTable[FormOfWay.SINGLE_CARRIAGEWAY.ordinal()][FormOfWay.MOTORWAY.ordinal()] = RatingCategory.POOR;
		fowRatingTable[FormOfWay.SINGLE_CARRIAGEWAY.ordinal()][FormOfWay.MULTIPLE_CARRIAGEWAY.ordinal()] = RatingCategory.GOOD;
		fowRatingTable[FormOfWay.SINGLE_CARRIAGEWAY.ordinal()][FormOfWay.SINGLE_CARRIAGEWAY.ordinal()] = RatingCategory.EXCELLENT;
		fowRatingTable[FormOfWay.SINGLE_CARRIAGEWAY.ordinal()][FormOfWay.ROUNDABOUT.ordinal()] = RatingCategory.AVERAGE;
		fowRatingTable[FormOfWay.SINGLE_CARRIAGEWAY.ordinal()][FormOfWay.TRAFFIC_SQUARE.ordinal()] = RatingCategory.AVERAGE;
		fowRatingTable[FormOfWay.SINGLE_CARRIAGEWAY.ordinal()][FormOfWay.SLIPROAD.ordinal()] = RatingCategory.POOR;
		fowRatingTable[FormOfWay.SINGLE_CARRIAGEWAY.ordinal()][FormOfWay.OTHER.ordinal()] = RatingCategory.POOR;
		
		fowRatingTable[FormOfWay.ROUNDABOUT.ordinal()][FormOfWay.UNDEFINED.ordinal()] = RatingCategory.AVERAGE;
		fowRatingTable[FormOfWay.ROUNDABOUT.ordinal()][FormOfWay.MOTORWAY.ordinal()] = RatingCategory.POOR;
		fowRatingTable[FormOfWay.ROUNDABOUT.ordinal()][FormOfWay.MULTIPLE_CARRIAGEWAY.ordinal()] = RatingCategory.AVERAGE;
		fowRatingTable[FormOfWay.ROUNDABOUT.ordinal()][FormOfWay.SINGLE_CARRIAGEWAY.ordinal()] = RatingCategory.AVERAGE;
		fowRatingTable[FormOfWay.ROUNDABOUT.ordinal()][FormOfWay.ROUNDABOUT.ordinal()] = RatingCategory.EXCELLENT;
		fowRatingTable[FormOfWay.ROUNDABOUT.ordinal()][FormOfWay.TRAFFIC_SQUARE.ordinal()] = RatingCategory.AVERAGE;
		fowRatingTable[FormOfWay.ROUNDABOUT.ordinal()][FormOfWay.SLIPROAD.ordinal()] = RatingCategory.POOR;
		fowRatingTable[FormOfWay.ROUNDABOUT.ordinal()][FormOfWay.OTHER.ordinal()] = RatingCategory.POOR;
		
		fowRatingTable[FormOfWay.TRAFFIC_SQUARE.ordinal()][FormOfWay.UNDEFINED.ordinal()] = RatingCategory.AVERAGE;
		fowRatingTable[FormOfWay.TRAFFIC_SQUARE.ordinal()][FormOfWay.MOTORWAY.ordinal()] = RatingCategory.POOR;
		fowRatingTable[FormOfWay.TRAFFIC_SQUARE.ordinal()][FormOfWay.MULTIPLE_CARRIAGEWAY.ordinal()] = RatingCategory.POOR;
		fowRatingTable[FormOfWay.TRAFFIC_SQUARE.ordinal()][FormOfWay.SINGLE_CARRIAGEWAY.ordinal()] = RatingCategory.AVERAGE;
		fowRatingTable[FormOfWay.TRAFFIC_SQUARE.ordinal()][FormOfWay.ROUNDABOUT.ordinal()] = RatingCategory.AVERAGE;
		fowRatingTable[FormOfWay.TRAFFIC_SQUARE.ordinal()][FormOfWay.TRAFFIC_SQUARE.ordinal()] = RatingCategory.EXCELLENT;
		fowRatingTable[FormOfWay.TRAFFIC_SQUARE.ordinal()][FormOfWay.SLIPROAD.ordinal()] = RatingCategory.POOR;
		fowRatingTable[FormOfWay.TRAFFIC_SQUARE.ordinal()][FormOfWay.OTHER.ordinal()] = RatingCategory.POOR;
		
		fowRatingTable[FormOfWay.SLIPROAD.ordinal()][FormOfWay.UNDEFINED.ordinal()] = RatingCategory.AVERAGE;
		fowRatingTable[FormOfWay.SLIPROAD.ordinal()][FormOfWay.MOTORWAY.ordinal()] = RatingCategory.POOR;
		fowRatingTable[FormOfWay.SLIPROAD.ordinal()][FormOfWay.MULTIPLE_CARRIAGEWAY.ordinal()] = RatingCategory.POOR;
		fowRatingTable[FormOfWay.SLIPROAD.ordinal()][FormOfWay.SINGLE_CARRIAGEWAY.ordinal()] = RatingCategory.POOR;
		fowRatingTable[FormOfWay.SLIPROAD.ordinal()][FormOfWay.ROUNDABOUT.ordinal()] = RatingCategory.POOR;
		fowRatingTable[FormOfWay.SLIPROAD.ordinal()][FormOfWay.TRAFFIC_SQUARE.ordinal()] = RatingCategory.POOR;
		fowRatingTable[FormOfWay.SLIPROAD.ordinal()][FormOfWay.SLIPROAD.ordinal()] = RatingCategory.EXCELLENT;
		fowRatingTable[FormOfWay.SLIPROAD.ordinal()][FormOfWay.OTHER.ordinal()] = RatingCategory.POOR;
		
		fowRatingTable[FormOfWay.OTHER.ordinal()][FormOfWay.UNDEFINED.ordinal()] = RatingCategory.AVERAGE;
		fowRatingTable[FormOfWay.OTHER.ordinal()][FormOfWay.MOTORWAY.ordinal()] = RatingCategory.POOR;
		fowRatingTable[FormOfWay.OTHER.ordinal()][FormOfWay.MULTIPLE_CARRIAGEWAY.ordinal()] = RatingCategory.POOR;
		fowRatingTable[FormOfWay.OTHER.ordinal()][FormOfWay.SINGLE_CARRIAGEWAY.ordinal()] = RatingCategory.POOR;
		fowRatingTable[FormOfWay.OTHER.ordinal()][FormOfWay.ROUNDABOUT.ordinal()] = RatingCategory.POOR;
		fowRatingTable[FormOfWay.OTHER.ordinal()][FormOfWay.TRAFFIC_SQUARE.ordinal()] = RatingCategory.POOR;
		fowRatingTable[FormOfWay.OTHER.ordinal()][FormOfWay.SLIPROAD.ordinal()] = RatingCategory.POOR;
		fowRatingTable[FormOfWay.OTHER.ordinal()][FormOfWay.OTHER.ordinal()] = RatingCategory.EXCELLENT;
	}
	
	/**
	 * Gets the rating for two form of way values.
	 *
	 * @param lrp the FOW of the lrp
	 * @param line the FOR of the line
	 * @return the rating
	 */
	public final RatingCategory getRating(final FormOfWay lrp, final FormOfWay line) {
		return fowRatingTable[lrp.ordinal()][line.ordinal()];
	}

}
