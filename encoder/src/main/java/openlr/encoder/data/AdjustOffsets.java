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
package openlr.encoder.data;

import openlr.encoder.OpenLREncoderProcessingException;
import openlr.encoder.OpenLREncoderProcessingException.EncoderProcessingError;
import openlr.encoder.properties.OpenLREncoderProperties;
import openlr.location.Location;
import openlr.location.LocationFactory;
import openlr.map.Line;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * This class checks and adjusts the offset values.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public final class AdjustOffsets {

    /** Logging. */
    private static final Logger LOG = Logger.getLogger(AdjustOffsets.class);

    /**
     * Helper class cannot be instantiated.
     */
    private AdjustOffsets() {
        throw new UnsupportedOperationException();
    }


    /**
     * Adjust offsets.
     *
     * @param location the location
     * @param properties the properties
     * @return the location
     * @throws OpenLREncoderProcessingException the open lr encoder runtime exception
     */
    public static Location adjustOffsets(final Location location,
                                         final OpenLREncoderProperties properties) throws OpenLREncoderProcessingException {
        int posOff = location.getPositiveOffset();
        int negOff = location.getNegativeOffset();
        List<? extends Line> lines = new ArrayList<Line>(location.getLocationLines());
        int startLength = lines.get(0).getLineLength();
        int endLength = lines.get(lines.size() - 1).getLineLength();
        int remainingPOff = posOff;
        if (posOff > startLength) {
            LOG
                    .warn("positive offset exceeds length of first line, location will be trimmed");
            while (!lines.isEmpty() && lines.get(0).getLineLength() < remainingPOff) {
                int currLength = lines.get(0).getLineLength();
                remainingPOff -= currLength;
                lines.remove(0);
            }
            if (lines.isEmpty()) {
                throw new OpenLREncoderProcessingException(EncoderProcessingError.OFFSET_TRIMMING_FAILED);
            }
        }
        int remainingNOff = negOff;
        if (negOff > endLength) {
            LOG
                    .warn("negative offset exceeds length of last line, location will be trimmed");

            while (!lines.isEmpty() && lines.get(lines.size() - 1).getLineLength() < remainingNOff) {
                int currLength = lines.get(lines.size() - 1).getLineLength();
                remainingNOff -= currLength;
                lines.remove(lines.size() - 1);
            }
            if (lines.isEmpty()) {
                throw new OpenLREncoderProcessingException(EncoderProcessingError.OFFSET_TRIMMING_FAILED);
            }
        }
        return LocationFactory.createLineLocationWithOffsets(location.getID(), lines, remainingPOff, remainingNOff);
    }

}
