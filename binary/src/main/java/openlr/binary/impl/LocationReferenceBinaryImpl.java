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
package openlr.binary.impl;

import java.io.IOException;
import java.io.OutputStream;

import openlr.LocationReference;
import openlr.LocationType;
import openlr.PhysicalFormatException;
import openlr.binary.BinaryReturnCode;
import openlr.binary.ByteArray;
import openlr.binary.OpenLRBinaryConstants;
import openlr.binary.OpenLRBinaryDecoder;
import openlr.binary.OpenLRBinaryException;
import openlr.binary.OpenLRBinaryException.PhysicalFormatError;
import openlr.binary.bitstream.BitstreamException;
import openlr.binary.bitstream.impl.ByteArrayBitstreamInput;
import openlr.binary.data.Header;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Implementation of the interface {@link LocationReference} for the binary
 * format.
 * 
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public class LocationReferenceBinaryImpl implements LocationReference {

	/** The unique id. */
	private final String id;

	/** The loc type. */
	private final LocationType locType;

	/** The error. */
	private final BinaryReturnCode returnCode;

	/** The binary location reference data. */
	private final ByteArray data;

	/** The version. */
	private final int version;

	/** The Constant VERSION_MASK. */
	private static final byte VERSION_MASK = 7;

	/**
	 * Instantiates a valid location reference.
	 *
	 * @param i the id
	 * @param d the binary location reference data
	 * @throws PhysicalFormatException the physical format exception
	 */
	public LocationReferenceBinaryImpl(final String i, final ByteArray d)
			throws PhysicalFormatException {
		id = i;
		data = d;
		returnCode = null;
		locType = resolveLocationType(d.getData());
		int ver = resolveVersion(d.getData());
		if (!checkVersion(ver)) {
			throw new OpenLRBinaryException(PhysicalFormatError.INVALID_VERSION); 
		}
		version = ver;
	}
	
	/**
	 * Check version.
	 *
	 * @param ver the ver
	 * @return true, if successful
	 */
	private boolean checkVersion(final int ver) {
		for (int v : OpenLRBinaryDecoder.getVersions()) {
			if (ver == v) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Resolve version.
	 *
	 * @param d the d
	 * @return the int
	 * @throws PhysicalFormatException the physical format exception
	 */
	private int resolveVersion(final byte[] d)
			throws PhysicalFormatException {
		if (d == null || d.length == 0) {
			throw new OpenLRBinaryException(
					PhysicalFormatError.INVALID_BINARY_DATA);
		}
		return d[0] & VERSION_MASK;
	}

	/**
	 * Instantiates an invalid location reference due to an error.
	 * 
	 * @param i
	 *            the id
	 * @param e
	 *            the error
	 * @param t
	 *            the location type
	 * @param ver
	 *            the ver
	 */
	public LocationReferenceBinaryImpl(final String i,
			final BinaryReturnCode e, final LocationType t, final int ver) {
		id = i;
		data = null;
		returnCode = e;
		locType = t;
		version = ver;
	}

	/**
	 * Resolves the location type.
	 *
	 * @param binData the binary data
	 * @return the location type
	 * @throws PhysicalFormatException the physical format exception
	 */
	public static final LocationType resolveLocationType(final byte[] binData)
			throws PhysicalFormatException {
		LocationType lt = null;
		int totalBytes = binData.length;
		Header header = null;
		ByteArrayBitstreamInput ibs = new ByteArrayBitstreamInput(binData, totalBytes);
		try {
			header = new Header(ibs);
		} catch (BitstreamException e) {
			throw new OpenLRBinaryException(
					PhysicalFormatError.INVALID_BINARY_DATA, e);
		} finally {
			ibs.close();
		}

		boolean hasAttributes = header.getAf() == OpenLRBinaryConstants.HAS_ATTRIBUTES;
		boolean isPointLocation = header.getPf() == OpenLRBinaryConstants.IS_POINT;
		//DLR e.V. (RE):
		int areaLocationCode = header.getArf();
		boolean isAreaLocation = ((areaLocationCode == 0 && !isPointLocation && !hasAttributes) || areaLocationCode > 0);
		
		if (!isPointLocation && !isAreaLocation && hasAttributes) {
			lt = LocationType.LINE_LOCATION;
		} else if (isPointLocation && !isAreaLocation) {
			if (!hasAttributes) {
				if (totalBytes == OpenLRBinaryConstants.GEOCOORD_SIZE) {
					lt = LocationType.GEO_COORDINATES;
				} else {
					throw new OpenLRBinaryException(
							PhysicalFormatError.INVALID_BINARY_DATA,
							"byte size does not match geo coordinate location");
				}
			} else {
				if (totalBytes == OpenLRBinaryConstants.POINT_ALONG_LINE_SIZE
						|| totalBytes == OpenLRBinaryConstants.POINT_ALONG_LINE_SIZE
								+ OpenLRBinaryConstants.POINT_OFFSET_SIZE) {
					lt = LocationType.POINT_ALONG_LINE;
				} else if (totalBytes == OpenLRBinaryConstants.POINT_WITH_ACCESS_SIZE
						|| totalBytes == OpenLRBinaryConstants.POINT_WITH_ACCESS_SIZE
								+ OpenLRBinaryConstants.POINT_OFFSET_SIZE) {
					lt = LocationType.POI_WITH_ACCESS_POINT;
				} else {
					throw new OpenLRBinaryException(
							PhysicalFormatError.INVALID_BINARY_DATA,
							"byte size does not match point location");
				}
			}
		} else if (isAreaLocation && !isPointLocation && hasAttributes) {
			// Added by DLR e.V. (RE)
			// CLOSED_LINE
			if (totalBytes >= OpenLRBinaryConstants.MIN_BYTES_CLOSED_LINE_LOCATION) {
				lt = LocationType.CLOSED_LINE;
			} else {
				throw new OpenLRBinaryException(
						PhysicalFormatError.INVALID_BINARY_DATA,
						"byte size does not match point location");
			}
		} else { //DLR e.V. (RE) changed old code to match new binary format
			switch(areaLocationCode) {
			case OpenLRBinaryConstants.AREA_CODE_CIRCLE: 
				lt = LocationType.CIRCLE;
				break;
			case OpenLRBinaryConstants.AREA_CODE_RECTANGLE:
			/* includes case OpenLRBinaryConstants.AREA_CODE_GRID */
				if (totalBytes == OpenLRBinaryConstants.RECTANGLE_SIZE
						|| totalBytes == OpenLRBinaryConstants.LARGE_RECTANGLE_SIZE) {
					lt = LocationType.RECTANGLE;
				} else if (totalBytes == OpenLRBinaryConstants.GRID_SIZE
						|| totalBytes == OpenLRBinaryConstants.LARGE_GRID_SIZE) {
					lt = LocationType.GRID;
				} else {
					throw new OpenLRBinaryException(
							PhysicalFormatError.INVALID_BINARY_DATA,
							"byte size does not match point location");
				}
				break;
			case OpenLRBinaryConstants.AREA_CODE_POLYGON:
				if (!hasAttributes
						&& totalBytes >= OpenLRBinaryConstants.MIN_BYTES_POLYGON) {
					lt = LocationType.POLYGON;
				} else {
					throw new OpenLRBinaryException(
							PhysicalFormatError.INVALID_BINARY_DATA,
							"byte size does not match point location");
				}
				break;
			default:
				throw new OpenLRBinaryException(
						PhysicalFormatError.INVALID_BINARY_DATA,
				"byte size does not match point location");
			}
		}
		return lt;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final BinaryReturnCode getReturnCode() {
		return returnCode;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String getID() {
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean isValid() {
		return returnCode == null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Class<?> getDataClass() {
		return ByteArray.class;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String getDataIdentifier() {
		return OpenLRBinaryConstants.IDENTIFIER;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Object getLocationReferenceData() {
		if (isValid()) {
			return data;
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void toStream(final OutputStream os) throws IOException {
		if (isValid()) {
			for (byte b : data.getData()) {
				os.write(b);
			}
			os.flush();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final LocationType getLocationType() {
		return locType;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id: ").append(id);
		sb.append(" type: ").append(locType);
		if (returnCode != null) {
			sb.append(" error: ").append(returnCode);
		} else {
			sb.append(" data: ").append(data);
		}
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int getVersion() {
		return version;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		builder.append(locType).append(returnCode).append(data).append(version);
		return builder.toHashCode();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean equals(final Object obj) {
		if (!(obj instanceof LocationReferenceBinaryImpl)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		LocationReferenceBinaryImpl other = (LocationReferenceBinaryImpl) obj;
		EqualsBuilder builder = new EqualsBuilder();
		builder.append(locType, other.locType).append(returnCode,
				other.returnCode).append(data, other.data).append(version,
				other.version);
		return builder.isEquals();
	}

}
