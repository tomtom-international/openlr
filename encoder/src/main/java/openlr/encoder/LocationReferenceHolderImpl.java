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
package openlr.encoder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import openlr.LocationReference;
import openlr.LocationReferencePoint;
import openlr.LocationType;
import openlr.StatusCode;
import openlr.rawLocRef.RawLocationReference;

/**
 * The class LocationReferenceHolder contains the result of an OpenLR encoding
 * process. The caller needs to check whether the location reference is valid or
 * invalid. If it is valid then the encoding process ended successfully and the
 * location reference contains physical representations of the location
 * reference. All physical formats being created are stored within this object
 * and each format can be identified by its data identifier string.
 * 
 * If the location reference is invalid then the encoding failed, no physical
 * representation exists and the exception being thrown during encoding is
 * stored.
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public class LocationReferenceHolderImpl implements
		openlr.encoder.LocationReferenceHolder {

	/** The binary location reference(s), if encoding was successful. */
	private final Map<String, LocationReference> data = new HashMap<String, LocationReference>();

	/** The unique ID. */
	private String id;

	/** The error, if encoding failed. */
	private StatusCode returnCode;

	/** The loc type. */
	private final LocationType locType;

	/** The raw data. */
	private final RawLocationReference rawData;

	/**
	 * Instantiates a new location reference holder with ID id and the location
	 * references identified by their data identifier string. Additionally it
	 * holds the raw location reference data. This location reference is valid.
	 * 
	 * @param idString
	 *            the unique id
	 * @param rd
	 *            the rd
	 */
	public LocationReferenceHolderImpl(final String idString,
			final RawLocationReference rd) {
		id = idString;
		returnCode = null;
		locType = rd.getLocationType();
		rawData = rd;
	}

	/**
	 * Instantiates a new location reference with ID id and the error being
	 * occurred during encoding. This location reference is invalid.
	 * 
	 * @param idString
	 *            the unique id
	 * @param ex
	 *            the exception being thrown during encoding
	 * @param lt
	 *            the location type
	 */
	public LocationReferenceHolderImpl(final String idString,
			final StatusCode ex, final LocationType lt) {
		id = idString;
		returnCode = ex;
		locType = lt;
		rawData = null;
	}

	/**
	 * Gets the data format identifiers.
	 *
	 * @return the data format identifiers
	 * {@inheritDoc}
	 */
	@Override
	public final Set<String> getDataFormatIdentifiers() {
		return data.keySet();
	}

	/**
	 * Gets the number of data formats.
	 *
	 * @return the number of data formats
	 * {@inheritDoc}
	 */
	@Override
	public final int getNumberOfDataFormats() {
		return data.size();
	}

	/**
	 * Gets the number of valid lr.
	 *
	 * @return the number of valid lr
	 * {@inheritDoc}
	 */
	@Override
	public final int getNumberOfValidLR() {
		int valid = 0;
		for (LocationReference lr : data.values()) {
			if (lr.isValid()) {
				valid++;
			}
		}
		return valid;
	}

	/**
	 * Gets the iD.
	 *
	 * @return the iD
	 * {@inheritDoc}
	 */
	@Override
	public final String getID() {
		return id;
	}

	/**
	 * Checks if is valid.
	 *
	 * @return true, if is valid
	 * {@inheritDoc}
	 */
	@Override
	public final boolean isValid() {
		return (returnCode == null);
	}

	/**
	 * Gets the return code.
	 *
	 * @return the return code
	 * {@inheritDoc}
	 */
	@Override
	public final StatusCode getReturnCode() {
		return returnCode;
	}

	/**
	 * Gets the nr of lr ps.
	 *
	 * @return the nr of lr ps
	 * {@inheritDoc}
	 */
	@Override
	public final int getNrOfLRPs() {
		if (rawData == null || rawData.getLocationReferencePoints() == null) {
			return 0;
		}
		return rawData.getLocationReferencePoints().size();
	}

	/**
	 * Checks for lr ps.
	 *
	 * @return true, if successful
	 * {@inheritDoc}
	 */
	@Override
	public final boolean hasLRPs() {
		if (rawData == null || rawData.getLocationReferencePoints() == null) {
			return false;
		}
		return !rawData.getLocationReferencePoints().isEmpty();
	}

	/**
	 * Gets the lr ps.
	 *
	 * @return the LR ps
	 * {@inheritDoc}
	 */
	@Override
	public final List<LocationReferencePoint> getLRPs() {
		if (rawData == null || rawData.getLocationReferencePoints() == null) {
			return null;
		}
		return rawData.getLocationReferencePoints();
	}

	
	/**
	 * Adds the location reference.
	 *
	 * @param ident the ident
	 * @param lr the lr
	 */
	public final void addLocationReference(final String ident,
			final LocationReference lr) {
		data.put(ident, lr);
	}

	/**
	 * Gets the location reference.
	 *
	 * @param dataID the data id
	 * @return the location reference
	 * {@inheritDoc}
	 */
	@Override
	public final LocationReference getLocationReference(final String dataID) {
		return data.get(dataID);
	}

	/**
	 * Gets the location type.
	 *
	 * @return the location type
	 * {@inheritDoc}
	 */
	@Override
	public final LocationType getLocationType() {
		return locType;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 * {@inheritDoc}
	 */
	@Override
	public final String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id: ").append(id);
		sb.append(" loc type: ").append(locType);
		if (returnCode != null) {
			sb.append(" error: ").append(returnCode);
		} else {
			sb.append(" #physformats: ").append(data.size());
			Iterator<String> iter = data.keySet().iterator();
			while (iter.hasNext()) {
				sb.append(iter.next());
				if (iter.hasNext()) {
					sb.append(",");
				}
			}
			sb.append(" #rawData: ").append(rawData);
		}
		return sb.toString();
	}

	/**
	 * Gets the raw location reference data.
	 *
	 * @return the raw location reference data
	 * {@inheritDoc}
	 */
	@Override
	public final RawLocationReference getRawLocationReferenceData() {
		return rawData;
	}

}
