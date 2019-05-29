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

import java.util.List;
import java.util.Set;

import openlr.LocationReference;
import openlr.LocationReferencePoint;
import openlr.LocationType;
import openlr.StatusCode;
import openlr.rawLocRef.RawLocationReference;

/**
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public interface LocationReferenceHolder {
	
	/**
	 * Gets the data format identifiers.
	 * 
	 * @return the data format identifiers
	 */
	Set<String> getDataFormatIdentifiers();
	
	/**
	 * Gets the number of data formats.
	 * 
	 * @return the number of data formats
	 */
	int getNumberOfDataFormats();
	
	/**
	 * Gets the number of valid physical location references.
	 * 
	 * @return the number of valid physical location references
	 */
	int getNumberOfValidLR();

	/**
	 * Gets the unique ID.
	 * 
	 * @return the unique ID
	 */
	String getID();

	/**
	 * Checks if the location reference is valid. If this location reference was
	 * encoded successfully and it contains at least one  location reference, then
	 * the location reference is valid. It is invalid if an error occurred
	 * during encoding and the exception stored within the location reference
	 * indicates what went wrong.
	 * 
	 * @return true, if the location reference is valid, otherwise false
	 */
	boolean isValid();

	/**
	 * Gets the error being thrown during encoding. If the location reference is
	 * valid then the method will return null.
	 * 
	 * @return the error if encoding failed, otherwise null
	 */
	StatusCode getReturnCode();

	/**
	 * Gets the number of sub routes of this location reference.
	 * 
	 * @return the number of sub routes
	 */
	int getNrOfLRPs();

	/**
	 * Checks for a sub route list.
	 * 
	 * @return true, if a sub route list is available
	 */
	boolean hasLRPs();

	/**
	 * Gets the list of sub routes. The concatenation of these sub routes covers
	 * the location.
	 * 
	 * @return the sub route list
	 */
	List<LocationReferencePoint> getLRPs();
	
	/**
	 * Gets the location reference with the data identifier dataID. If the data
	 * identifier does not exist the method will return null.
	 * 
	 * @param dataID
	 *            the data identifier
	 * 
	 * @return the location reference
	 */
	LocationReference getLocationReference(final String dataID);
	
	/**
	 * Gets the location type.
	 * 
	 * @return the location type
	 */
	LocationType getLocationType();	
	
	
	/**
	 * Gets the raw location reference data.
	 *
	 * @return the raw location reference data
	 */
	RawLocationReference getRawLocationReferenceData();

}
