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
 * Copyright (C) 2009-2012 TomTom International B.V.
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
package openlr;

import openlr.decoder.OpenLRDecoderParameter;
import openlr.location.Location;
import openlr.rawLocRef.RawLocationReference;

import java.util.List;

/**
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 *
 */
public interface OpenLRDecoder {

    /**
     * Decode.
     *
     * @param parameter the parameter
     * @param data the data
     * @return the location
     * @throws OpenLRProcessingException the open lr processing exception
     */
    Location decode(final OpenLRDecoderParameter parameter, final LocationReference data) throws OpenLRProcessingException;

    /**
     * Decode.
     *
     * @param parameter the parameter
     * @param data the data
     * @return the list
     * @throws OpenLRProcessingException the open lr processing exception
     */
    List<Location> decode(final OpenLRDecoderParameter parameter, final List<LocationReference> data)
            throws OpenLRProcessingException;

    /**
     * Decode.
     *
     * @param parameter the parameter
     * @param rawData the raw data
     * @return the location
     * @throws OpenLRProcessingException the open lr processing exception
     */
    Location decodeRaw(final OpenLRDecoderParameter parameter, final RawLocationReference rawData) throws OpenLRProcessingException;

    /**
     * Decode.
     *
     * @param parameter the parameter
     * @param rawData the raw data
     * @return the list
     * @throws OpenLRProcessingException the open lr processing exception
     */
    List<Location> decodeRaw(final OpenLRDecoderParameter parameter, final List<RawLocationReference> rawData)
            throws OpenLRProcessingException;

    /**
     * Gets the major version number.
     *
     * @return the major version
     */
    String getMajorVersion();

    /**
     * Gets the minor version number.
     *
     * @return the minor version
     */
    String getMinorVersion();

    /**
     * Gets the patch version number.
     *
     * @return the patch version
     */
    String getPatchVersion();

    /**
     * Gets the version information as a string.
     *
     * @return the version
     */
    String getVersion();

}
