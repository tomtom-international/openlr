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
package openlr.xml.decoder;

import openlr.LocationType;
import openlr.PhysicalFormatException;
import openlr.rawLocRef.RawCircleLocRef;
import openlr.rawLocRef.RawInvalidLocRef;
import openlr.rawLocRef.RawLocationReference;
import openlr.xml.OpenLRXMLException;
import openlr.xml.OpenLRXMLException.XMLErrorType;
import openlr.xml.XmlReturnCode;
import openlr.xml.generated.CircleLocationReference;
import openlr.xml.generated.GeoCoordinate;

/**
 * The decoder for the circle area location type.
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author DLR e.V. (LTouk)
 */
public class CircleDecoder extends AbstractDecoder {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final RawLocationReference decodeData(final String id,
			final Object data) throws PhysicalFormatException {
		if (!(data instanceof CircleLocationReference)) {
			throw new OpenLRXMLException(XMLErrorType.DATA_ERROR,
					"incorrect data class");
		}
		CircleLocationReference circleLoc = (CircleLocationReference) data;
		long radius = circleLoc.getRadius().longValue();
		GeoCoordinate centerPoint = circleLoc.getGeoCoordinate();
		if (centerPoint == null || radius < 0) {
			return new RawInvalidLocRef(id, XmlReturnCode.MISSING_CIRCLE_DATA,
					LocationType.CIRCLE);
		}
		RawLocationReference rawLocRef = new RawCircleLocRef(id,
				createGeoCoord(centerPoint.getCoordinates()), radius);

		return rawLocRef;
	}

}
