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
package openlr.binary.decoder;

import java.util.ArrayList;
import java.util.List;

import openlr.binary.BinaryReturnCode;
import openlr.binary.OpenLRBinaryConstants;
import openlr.binary.OpenLRBinaryException;
import openlr.binary.OpenLRBinaryException.PhysicalFormatError;
import openlr.binary.bitstream.impl.ByteArrayBitstreamInput;
import openlr.binary.data.AbsoluteCoordinates;
import openlr.binary.data.RawBinaryData;
import openlr.binary.data.RelativeCoordinates;
import openlr.map.GeoCoordinates;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.InvalidMapDataException;
import openlr.rawLocRef.RawLocationReference;
import openlr.rawLocRef.RawPolygonLocRef;

/**
 * The decoder for the polygon location type.
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
	public final RawLocationReference decodeData(final String id,
			final ByteArrayBitstreamInput ibs, final int totalBytes,
			final int version, final RawBinaryData binData)
			throws OpenLRBinaryException {

		if (version < OpenLRBinaryConstants.BINARY_VERSION_3) {
			throw new OpenLRBinaryException(PhysicalFormatError.INVALID_VERSION);
		}
		int remainingBytes = totalBytes
				- (OpenLRBinaryConstants.HEADER_SIZE + OpenLRBinaryConstants.ABS_COORD_SIZE);
		if (remainingBytes % OpenLRBinaryConstants.RELATIVE_COORD_SIZE != 0) {
			throw new OpenLRBinaryException(
					PhysicalFormatError.INVALID_BINARY_DATA);
		}
		int numRemainingCorners = remainingBytes
				/ OpenLRBinaryConstants.RELATIVE_COORD_SIZE;
		if (numRemainingCorners < 2) {
			throw new OpenLRBinaryException(
					PhysicalFormatError.INVALID_BINARY_DATA);
		}
		List<GeoCoordinates> cornersCoords = new ArrayList<GeoCoordinates>();
		try {
			AbsoluteCoordinates firstCornerAbsCoord = new AbsoluteCoordinates(
					ibs);
			GeoCoordinates firstCornerCoord = new GeoCoordinatesImpl(
					calculate32BitRepresentation(firstCornerAbsCoord.getLon()),
					calculate32BitRepresentation(firstCornerAbsCoord.getLat()));

			cornersCoords.add(firstCornerCoord);
			AbsoluteCoordinates prevCornerAbsCoord = firstCornerAbsCoord;

			int counter = numRemainingCorners;
			while (counter > 0) {
				counter--;
				RelativeCoordinates remainingCoord = new RelativeCoordinates(
						ibs);
				double lon = calculate32BitRepresentation(prevCornerAbsCoord
						.getLon())
						+ (remainingCoord.getLon() / OpenLRBinaryConstants.DECA_MICRO_DEG_FACTOR);
				double lat = calculate32BitRepresentation(prevCornerAbsCoord
						.getLat())
						+ (remainingCoord.getLat() / OpenLRBinaryConstants.DECA_MICRO_DEG_FACTOR);

				GeoCoordinates cornerCoord = new GeoCoordinatesImpl(lon, lat);
				cornersCoords.add(cornerCoord);
				prevCornerAbsCoord = new AbsoluteCoordinates(
						get24BitRepresentation(lon),
						get24BitRepresentation(lat));
			}
		} catch (InvalidMapDataException e) {
			throw new OpenLRBinaryException(
					BinaryReturnCode.INVALID_BINARY_DATA, e);
		}
		RawPolygonLocRef rawLocRef = new RawPolygonLocRef(id, cornersCoords);
		return rawLocRef;
	}

}
