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
package openlr.xml.encoder;

import openlr.LocationReference;
import openlr.map.GeoCoordinates;
import openlr.rawLocRef.RawLocationReference;
import openlr.xml.generated.Coordinates;
import openlr.xml.generated.GeoCoordinate;
import openlr.xml.generated.OpenLR;
import openlr.xml.generated.PointLocationReference;
import openlr.xml.generated.XMLLocationReference;
import openlr.xml.impl.LocationReferenceXmlImpl;

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
	 * Creates the xml geo coord loc ref.
	 * 
	 * @param gCoord the g coord
	 * 
	 * @return the xML location reference
	 */
	private XMLLocationReference createXMLGeoCoordLocRef(final GeoCoordinates gCoord) {
		XMLLocationReference xmlLoc = OBJECT_FACTORY.createXMLLocationReference();
		PointLocationReference pointLoc = OBJECT_FACTORY.createPointLocationReference();
		GeoCoordinate geoCoord = OBJECT_FACTORY.createGeoCoordinate();
		Coordinates coord = OBJECT_FACTORY.createCoordinates();
		coord.setLongitude(gCoord.getLongitudeDeg());
		coord.setLatitude(gCoord.getLatitudeDeg());
		geoCoord.setCoordinates(coord);
		pointLoc.setGeoCoordinate(geoCoord);
		xmlLoc.setPointLocationReference(pointLoc);
		return xmlLoc;
	}
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final LocationReference encodeData(final RawLocationReference rawLocRef,
			final int version) {
		GeoCoordinates coord = rawLocRef.getGeoCoordinates();
		OpenLR xmlData = OBJECT_FACTORY.createOpenLR();
		String id = "";
		if (rawLocRef.hasID()) {
			id = rawLocRef.getID();
		}
		xmlData.setLocationID(id);
		XMLLocationReference xmlLoc = createXMLGeoCoordLocRef(coord);
		xmlData.setXMLLocationReference(xmlLoc);
		LocationReference locRefData = new LocationReferenceXmlImpl(id, xmlData, version);
		return locRefData;
	}
}

