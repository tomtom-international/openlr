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

import java.math.BigInteger;

import openlr.LocationReference;
import openlr.LocationType;
import openlr.map.GeoCoordinates;
import openlr.rawLocRef.RawLocationReference;
import openlr.xml.XmlReturnCode;
import openlr.xml.generated.AreaLocationReference;
import openlr.xml.generated.CircleLocationReference;
import openlr.xml.generated.GeoCoordinate;
import openlr.xml.generated.OpenLR;
import openlr.xml.generated.XMLLocationReference;
import openlr.xml.impl.LocationReferenceXmlImpl;

/**
 * The encoder for the circle area location type.
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author DLR e.V. (LTouk)
 */
public class CircleEncoder extends AbstractEncoder {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final LocationReference encodeData(
			final RawLocationReference rawLocRef, final int version) {
		GeoCoordinates centerPoint = rawLocRef.getCenterPoint();
		long radius = rawLocRef.getRadius();

		if (centerPoint == null || radius < 0) {
			return new LocationReferenceXmlImpl(rawLocRef.getID(),
					XmlReturnCode.INVALID_DATA, LocationType.CIRCLE, version);
		}

		OpenLR xmlData = OBJECT_FACTORY.createOpenLR();
		String id = "";
		if (rawLocRef.hasID()) {
			id = rawLocRef.getID();
		}
		xmlData.setLocationID(id);
		XMLLocationReference xmlLoc = createXMLCircleLocRef(centerPoint, radius);
		xmlData.setXMLLocationReference(xmlLoc);
		LocationReference locRefData = new LocationReferenceXmlImpl(id,
				xmlData, version);
		return locRefData;
	}

	/**
	 * Creates the xml location reference.
	 * 
	 * @param centerPoint
	 *            the center point of circle location reference
	 * @param radius
	 *            the radius of circle location reference
	 * 
	 * @return the XML location reference
	 */
	private XMLLocationReference createXMLCircleLocRef(
			final GeoCoordinates centerPoint, final long radius) {
		XMLLocationReference xmlLoc = OBJECT_FACTORY
				.createXMLLocationReference();
		CircleLocationReference circleLocRef = createCircleLocRef(centerPoint,
				radius);

		AreaLocationReference areaLoc = OBJECT_FACTORY
				.createAreaLocationReference();
		areaLoc.setCircleLocationReference(circleLocRef);
		xmlLoc.setAreaLocationReference(areaLoc);
		return xmlLoc;
	}

	/**
	 * Creates the line location reference.
	 * 
	 * @param centerPoint
	 *            the center point of circle location reference
	 * @param radius
	 *            the radius of circle location reference
	 * 
	 * @return the circle location reference
	 */
	private CircleLocationReference createCircleLocRef(
			final GeoCoordinates centerPoint, final long radius) {
		CircleLocationReference circleLocRef = OBJECT_FACTORY
				.createCircleLocationReference();
		circleLocRef.setRadius(BigInteger.valueOf(radius));
		GeoCoordinate gc = OBJECT_FACTORY.createGeoCoordinate();
		gc.setCoordinates(createCoordinates(centerPoint));
		circleLocRef.setGeoCoordinate(gc);

		return circleLocRef;
	}

}
