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
package openlr.decoder.worker;

import java.util.List;

import openlr.OpenLRProcessingException;
import openlr.decoder.location.AffectedLinesImpl;
import openlr.decoder.location.DecodedPolygonLocation;
import openlr.decoder.properties.OpenLRDecoderProperties;
import openlr.decoder.worker.coverage.PolygonCoverage;
import openlr.location.Location;
import openlr.location.data.AffectedLines;
import openlr.map.GeoCoordinates;
import openlr.map.MapDatabase;
import openlr.rawLocRef.RawLocationReference;

/**
 * The polygon decoder.
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author DLR e.V. (RE)
 */
public class PolygonDecoder extends AbstractDecoder {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Location doDecoding(final OpenLRDecoderProperties prop, final MapDatabase mdb,
			final RawLocationReference rawLocRef) throws OpenLRProcessingException {

		List<? extends GeoCoordinates> cornerPoints = rawLocRef.getCornerPoints();
		AffectedLines result;
		if (prop.isCalcAffectedLines()) {
			PolygonCoverage coverage = new PolygonCoverage(cornerPoints);
			result = coverage.getAffectedLines(mdb);
		} else {
			result = AffectedLinesImpl.EMPTY;
		}
		Location decoded = new DecodedPolygonLocation(rawLocRef.getID(), result, cornerPoints);
		// return the decoded location
		return decoded;
	}
}
