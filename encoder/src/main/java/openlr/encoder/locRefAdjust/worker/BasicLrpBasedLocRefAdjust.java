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
package openlr.encoder.locRefAdjust.worker;

import openlr.OpenLRProcessingException;
import openlr.encoder.OpenLREncoderProcessingException;
import openlr.encoder.OpenLREncoderProcessingException.EncoderProcessingError;
import openlr.encoder.data.LocRefData;
import openlr.encoder.locRefAdjust.LocationReferenceAdjust;
import openlr.encoder.properties.OpenLREncoderProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Performs adjustments relevant for fall LRP based location references.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class BasicLrpBasedLocRefAdjust extends LocationReferenceAdjust {

    /** The Constant LOG. */
    private static final Logger LOG = LogManager.getLogger(BasicLrpBasedLocRefAdjust.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public final void adjustLocationReference(final OpenLREncoderProperties properties,
                                              final LocRefData locRefData) throws OpenLRProcessingException {

        if (!checkMaxDistances(properties, locRefData)) {
            throw new OpenLREncoderProcessingException(
                    EncoderProcessingError.MAX_LRP_DISTANCE_EXCEEDED);
        }

        /* check if the location reference path covers the location completely */
        /* last check of the data!! */
        if (!checkLRPCoverage(locRefData)) {
            throw new OpenLREncoderProcessingException(
                    EncoderProcessingError.LOCATION_REFERENCE_DOES_NOT_COVER_LOCATION);
        }

        locRefData.setLocRefPoints(checkAndAdjustOffsets(
                locRefData, properties));

        // check if all LRP are on valid nodes
        if (LOG.isDebugEnabled()) {
            checkNodeValidity(locRefData.getLocRefPoints());
        }

        additionalAdjustments(properties, locRefData);
    }

    /**
     * Hook to be implemented by subclasses. This method allows to perform
     * additional adjustments after
     * {@link #adjustLocationReference(OpenLREncoderProperties, LocRefData)}.
     *
     * @param properties
     *            the OpenLR encoder properties
     * @param locRefData
     *            the location reference to process
     * @throws OpenLRProcessingException
     *             thrown in case of an error during adjustment or in case of
     *             invalid input location reference
     */
    protected void additionalAdjustments(
            final OpenLREncoderProperties properties,
            final LocRefData locRefData) throws OpenLRProcessingException {
    }

}
