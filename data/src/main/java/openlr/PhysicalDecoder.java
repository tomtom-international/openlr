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
 * The interface PhysicalDecoder needs to be implemented by all OpenLR physical
 * format decoders. These decoders translate a defined physical format for OpenLR
 * location references into an internal representation (raw format). Each decoder 
 * is identified by a data identifier string. Examples for different physical 
 * formats might be "xml" or "binary".
 *
 * Decoder packages implementing this interface also need to register the 
 * implementation as a service. The service loader mechanism from Java is used by
 * the OpenLR decoder to collect all implementation of this PhysicalDecoder
 * interface. The decoder will select the required physical decoder to read the
 * physical data.
 *
 * In order to register a service one needs to add a file "openlr.PhysicalDecoder"
 * to the "META-INF/services" directory of the java package. This file must contain
 * the fully qualified path to the implementation class, 
 * e.g. "openlr.xml.OpenLRXMLDecoder".
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public interface PhysicalDecoder {

    /**
     * Gets the class of the location reference data.
     *
     * @return the data class
     */
    Class<?> getDataClass();

    /**
     * Decodes the data (physical format) and generates an internal representation
     * of the location reference as a list of location reference points and offset
     * information.
     *
     * @param data the (physical) data
     * @return the raw location reference
     * @throws PhysicalFormatException the physical format exception
     */
    RawLocationReference decodeData(LocationReference data) throws PhysicalFormatException;

    /**
     * Gets the data identifier.
     *
     * @return the data identifier
     */
    String getDataFormatIdentifier();

}

