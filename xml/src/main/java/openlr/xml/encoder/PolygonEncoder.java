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

import java.util.List;

import openlr.LocationReference;
import openlr.LocationType;
import openlr.map.GeoCoordinates;
import openlr.rawLocRef.RawLocationReference;
import openlr.xml.XmlReturnCode;
import openlr.xml.generated.AreaLocationReference;
import openlr.xml.generated.Coordinates;
import openlr.xml.generated.OpenLR;
import openlr.xml.generated.PolygonLocationReference;
import openlr.xml.generated.PolygonLocationReference.PolygonCorners;
import openlr.xml.generated.XMLLocationReference;
import openlr.xml.impl.LocationReferenceXmlImpl;

/**
 * The encoder for the polygon area location type.
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author DLR e.V. (RE, LTouk)
 */
public class PolygonEncoder extends AbstractEncoder {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final LocationReference encodeData(
			final RawLocationReference rawLocRef, final int version) {
		List<? extends GeoCoordinates> cornerPoints = rawLocRef
				.getCornerPoints();

		if (cornerPoints == null) {
			return new LocationReferenceXmlImpl(rawLocRef.getID(),
					XmlReturnCode.INVALID_DATA, LocationType.POLYGON, version);
		}

		OpenLR xmlData = OBJECT_FACTORY.createOpenLR();
		String id = "";
		if (rawLocRef.hasID()) {
			id = rawLocRef.getID();
		}
		xmlData.setLocationID(id);
		XMLLocationReference xmlLoc = createXMLPolygonLocRef(cornerPoints);
		xmlData.setXMLLocationReference(xmlLoc);
		LocationReference locRefData = new LocationReferenceXmlImpl(id,
				xmlData, version);
		return locRefData;
	}

	/**
	 * Creates the xml location reference.
	 * 
	 * @param cornerPoints
	 *            the list of corner points
	 * 
	 * @return the XML location reference
	 */
	private XMLLocationReference createXMLPolygonLocRef(
			final List<? extends GeoCoordinates> cornerPoints) {
		XMLLocationReference xmlLoc = OBJECT_FACTORY
				.createXMLLocationReference();
		PolygonLocationReference polygonLocRef = createPolygonLocRef(cornerPoints);

		AreaLocationReference areaLoc = OBJECT_FACTORY
				.createAreaLocationReference();
		areaLoc.setPolygonLocationReference(polygonLocRef);
		xmlLoc.setAreaLocationReference(areaLoc);
		return xmlLoc;
	}

	/**
	 * Creates the polygon location reference.
	 * 
	 * @param cornerPoints
	 *            the list of corner points
	 * 
	 * @return the polygon location reference
	 */
	private PolygonLocationReference createPolygonLocRef(
			final List<? extends GeoCoordinates> cornerPoints) {
		PolygonLocationReference polygonLocRef = OBJECT_FACTORY
				.createPolygonLocationReference();		
		PolygonCorners pCorners = OBJECT_FACTORY.createPolygonLocationReferencePolygonCorners();
		polygonLocRef.setPolygonCorners(pCorners);
		List<Coordinates> corners = pCorners.getCoordinates();
		corners.clear();
		for (GeoCoordinates gc : cornerPoints) {
			corners.add(createCoordinates(gc));
		}
		return polygonLocRef;
	}

}
