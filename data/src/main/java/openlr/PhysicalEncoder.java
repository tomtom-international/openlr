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

import openlr.rawLocRef.RawLocationReference;

/**
 * The interface PhysicalEncoder needs to be implemented by all OpenLR physical
 * format encoders. These encoders translate the internal data structure of a
 * location reference into a defined physical format. Each encoder is identified
 * by a data identifier string. Examples for different physical formats might
 * be "xml" or "binary".
 *
 * Physical encoders may support several versions of their format identified
 * by a unique number.
 *
 * Encoder packages implementing this interface also need to register the
 * implementation as a service. The service loader mechanism from Java is used by
 * the OpenLR encoder to collect all implementation of this PhysicalEncoder
 * interface. All implementations will be called during the encoding process.
 *
 * In order to register a service one needs to add a file "openlr.PhysicalEncoder"
 * to the "META-INF/services" directory of the java package. This file must contain
 * the fully qualified path to the implementation class,
 * e.g. "openlr.xml.OpenLRXMLEncoder".
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public interface PhysicalEncoder {

    /**
     * Encode data using the latest version.
     *
     * @param rawLocRef the raw loc ref
     * @return the location reference
     */
    LocationReference encodeData(RawLocationReference rawLocRef);

    /**
     * Encode data using a specific version.
     *
     * @param rawLocRef the raw loc ref
     * @param version the version
     * @return the location reference
     */
    LocationReference encodeData(RawLocationReference rawLocRef, int version);

    /**
     * Gets the supported versions. A version is defined by a unique number.
     *
     * @return the supported versions
     */
    int[] getSupportedVersions();

    /**
     * Gets the data identifier.
     *
     * @return the data identifier
     */
    String getDataFormatIdentifier();

    /**
     * Gets the class of the location reference data.
     *
     * @return the data class
     */
    Class<?> getDataClass();
}
