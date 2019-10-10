/**
 * Licensed to the TomTom International B.V. under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  TomTom International B.V.
 * licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * <p>
 * Copyright (C) 2009-2019 TomTom International B.V.
 * <p>
 * TomTom (Legal Department)
 * Email: legal@tomtom.com
 * <p>
 * TomTom (Technical contact)
 * Email: openlr@tomtom.com
 * <p>
 * Address: TomTom International B.V., Oosterdoksstraat 114, 1011DK Amsterdam,
 * the Netherlands
 * <p>
 * Copyright (C) 2009-2019 TomTom International B.V.
 * <p>
 * TomTom (Legal Department)
 * Email: legal@tomtom.com
 * <p>
 * TomTom (Technical contact)
 * Email: openlr@tomtom.com
 * <p>
 * Address: TomTom International B.V., Oosterdoksstraat 114, 1011DK Amsterdam,
 * the Netherlands
 */
/**
 *  Copyright (C) 2009-2019 TomTom International B.V.
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
package openlr;

import java.io.IOException;
import java.io.OutputStream;

/**
 * The interface LocationReference defines an OpenLR location reference. A location
 * reference describes in a map-agnostic way a location in a digital map. The
 * reference can be used to exchange the information about a location between
 * several maps. These maps might differ to some extent.
 *
 * This interface holds an identification of the location/location reference, the
 * reference itself as an abstract field and if no location reference could be 
 * generated the object holds the exception. As there might be several formats
 * for location references this object identifies itself with a data identifier
 * and data class information.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public interface LocationReference {

    /**
     * Gets the location type.
     *
     * @return the location type
     */
    LocationType getLocationType();


    /**
     * Gets the location reference data. As there might be several formats
     * for location references the return value is an object. The class of this
     * object can be identified using the {@link #getDataClass()} method.
     *
     * @return the location reference data
     */
    Object getLocationReferenceData();


    /**
     * Gets the unique location/location reference ID.
     *
     * @return the unique ID
     */
    String getID();

    /**
     * Checks if the location reference is valid. If this location reference was
     * encoded successfully and it contains a location reference object, then
     * the location reference is valid. It is invalid if an error occurred
     * during encoding and the exception stored within the location reference
     * indicates what went wrong.
     *
     * @return true, if the location reference is valid, otherwise false
     */
    boolean isValid();

    /**
     * Gets the return code (only valid after an encoding process)
     *
     * @return the return code
     */
    StatusCode getReturnCode();

    /**
     * Gets the class of the location reference.
     *
     * @return the class of the location reference
     */

    Class<?> getDataClass();

    /**
     * Gets the unique data identifier. This identifier specifies the location
     * reference format being used. The value "xml" might indicate that the
     * location reference data represents a XML document and the value "binary"
     * might indicate that the location reference is stored in a binary array.
     *
     * @return the data identifier
     */
    String getDataIdentifier();

    /**
     * Writes the location reference data to the provided stream.
     *
     * @param os the output stream to write
     * @throws IOException if storing the data failed
     * @throws OpenLRProcessingException the open lr processing exception
     */
    void toStream(OutputStream os) throws IOException, OpenLRProcessingException;

    /**
     * Gets the version.
     *
     * @return the version
     */
    int getVersion();

}
