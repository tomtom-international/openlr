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
package openlr.datex2.encoder;

import openlr.LocationReference;
import openlr.datex2.impl.LocationReferenceImpl;
import openlr.map.GeoCoordinates;
import openlr.rawLocRef.RawLocationReference;
import eu.datex2.schema._2_0rc2._2_0.OpenlrGeoCoordinate;
import eu.datex2.schema._2_0rc2._2_0.OpenlrPointLocationReference;
import eu.datex2.schema._2_0rc2._2_0.PointCoordinates;

/**
 * The encoder for the geo coordinate location type.
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public class GeoCoordEncoder extends AbstractEncoder {	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final LocationReference encodeData(final RawLocationReference rawLocRef,
			final int version) {
		GeoCoordinates gCoord = rawLocRef.getGeoCoordinates();
		OpenlrPointLocationReference xmlData = OBJECT_FACTORY.createOpenlrPointLocationReference();
		OpenlrGeoCoordinate xmlLoc = OBJECT_FACTORY.createOpenlrGeoCoordinate();
		PointCoordinates coord = OBJECT_FACTORY.createPointCoordinates();
		coord.setLongitude((float) gCoord.getLongitudeDeg());
		coord.setLatitude((float) gCoord.getLatitudeDeg());
		xmlLoc.setOpenlrCoordinate(coord);
		xmlData.setOpenlrGeoCoordinate(xmlLoc);
		return new LocationReferenceImpl(rawLocRef.getID(), xmlData, version);
	}

}
