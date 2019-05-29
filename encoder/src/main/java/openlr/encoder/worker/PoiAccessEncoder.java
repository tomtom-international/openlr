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
package openlr.encoder.worker;

import java.util.List;

import openlr.OpenLRProcessingException;
import openlr.encoder.OpenLREncoderProcessingException;
import openlr.encoder.OpenLREncoderProcessingException.EncoderProcessingError;
import openlr.encoder.data.ExpansionHelper;
import openlr.encoder.data.LocRefData;
import openlr.encoder.data.LocRefPoint;
import openlr.encoder.locRefAdjust.LocationReferenceAdjust;
import openlr.encoder.locRefAdjust.worker.LrpBasedPointLocRefAdjust;
import openlr.encoder.locationCheck.CheckResult;
import openlr.encoder.locationCheck.LocationCheck;
import openlr.encoder.locationCheck.worker.PoiAccessLocationCheck;
import openlr.encoder.properties.OpenLREncoderProperties;
import openlr.location.Location;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.InvalidMapDataException;
import openlr.map.MapDatabase;
import openlr.rawLocRef.RawInvalidLocRef;
import openlr.rawLocRef.RawLocationReference;
import openlr.rawLocRef.RawPoiAccessLocRef;

/**
 * The poi with access point encoder.
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public class PoiAccessEncoder extends AbstractEncoder {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final RawLocationReference doEncoding(final Location location,
			final OpenLREncoderProperties prop, final MapDatabase mdb)
			throws OpenLRProcessingException {

		// check if the location is valid and expand
		LocationCheck locCheck = new PoiAccessLocationCheck();
		CheckResult retCode = locCheck.check(prop, mdb, location);
		if (!retCode.checkPassed()) {
			RawLocationReference invalid = new RawInvalidLocRef(
					location.getID(), retCode.getError(),
					location.getLocationType());
			return invalid;
		}

		retCode = locCheck.checkOffsets(prop, location);
		if (!retCode.checkPassed()) {
			RawLocationReference invalid = new RawInvalidLocRef(
					location.getID(), retCode.getError(),
					location.getLocationType());
			return invalid;
		}
		LocRefData lrd = new LocRefData(location);
		lrd.setExpansion(ExpansionHelper.createExpandedLocation(prop,
				mdb, lrd));

		// initialize location reference data array and start encoding
		lrd.setLocRefPoints(generateLocRef(lrd, prop));

		// check if the location reference meets all restrictions and adjust
		// length values
		// if necessary
		LocationReferenceAdjust locRefAdjust = new LrpBasedPointLocRefAdjust();
		locRefAdjust.adjustLocationReference(prop, lrd);

		RawLocationReference rawLocRef = null;
		List<LocRefPoint> lrps = lrd.getLocRefPoints();
		try {
			rawLocRef = new RawPoiAccessLocRef(location.getID(),
					lrps.get(0), lrps.get(1), lrd.getOffsets(),
					new GeoCoordinatesImpl(location.getPointLocation()
							.getLongitudeDeg(), location.getPointLocation()
							.getLatitudeDeg()), location.getSideOfRoad(),
					location.getOrientation());
		} catch (InvalidMapDataException e) {
			throw new OpenLREncoderProcessingException(
					EncoderProcessingError.INVALID_MAP_DATA, e);
		}

		return rawLocRef;
	}

}
