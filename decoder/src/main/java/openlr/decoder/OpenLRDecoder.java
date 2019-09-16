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
package openlr.decoder;

import openlr.*;
import openlr.decoder.OpenLRDecoderProcessingException.DecoderProcessingError;
import openlr.decoder.properties.OpenLRDecoderProperties;
import openlr.decoder.worker.*;
import openlr.location.InvalidLocation;
import openlr.location.Location;
import openlr.rawLocRef.RawLocationReference;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * The class OpenLRDecoder provides methods to decode a single OpenLR location
 * reference or a list of OpenLR location references. The decoder needs to get a
 * map database as input and is configurable by OpenLR properties. It starts
 * parsing the location reference data using a corresponding physical decoder
 * and tries to match the parsed information onto the provided map. If
 * successful the location will be returned, if not an error will be reported.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public final class OpenLRDecoder implements openlr.OpenLRDecoder {

    /** logger */
    private static final Logger LOG = Logger.getLogger(OpenLRDecoder.class);

    /** the version of the decoder */
    private final Version VERSION = VersionHelper.getVersion("decoder");

    /**
     * Resolve physical decoder.
     *
     * @return the list
     */
    public static List<PhysicalDecoder> resolvePhysicalDecoder() {
        ServiceLoader<PhysicalDecoder> decoderServices = ServiceLoader
                .load(PhysicalDecoder.class);
        List<PhysicalDecoder> decoders = new ArrayList<PhysicalDecoder>();
        for (PhysicalDecoder pd : decoderServices) {
            decoders.add(pd);
        }
        return decoders;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Location decode(final OpenLRDecoderParameter parameter,
                           final LocationReference locRef) throws OpenLRProcessingException {
        OpenLRDecoderProperties properties = new OpenLRDecoderProperties(parameter.getConfiguration());
        return decode(parameter, properties, locRef);
    }

    /**
     * Decode.
     *
     * @param parameter the parameter
     * @param properties the properties
     * @param locRef the loc ref
     * @return the location
     * @throws OpenLRProcessingException the open lr processing exception
     */
    private Location decode(final OpenLRDecoderParameter parameter, final OpenLRDecoderProperties properties,
                            final LocationReference locRef) throws OpenLRProcessingException {
        List<PhysicalDecoder> physDecoder = parameter.getPhysicalDecoders();
        if (physDecoder.isEmpty()) {
            physDecoder = resolvePhysicalDecoder();
        }
        if (physDecoder.isEmpty()) {
            throw new OpenLRDecoderProcessingException(
                    DecoderProcessingError.NO_PHYSICAL_DECODER_DETECTED,
                    "No physical decoder found!");
        }
        return decodeRaw(parameter, properties, resolveRawFormat(locRef, physDecoder));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Location> decode(final OpenLRDecoderParameter parameter,
                                 final List<LocationReference> locRefs)
            throws OpenLRProcessingException {
        List<Location> locations = new ArrayList<Location>();
        OpenLRDecoderProperties properties = new OpenLRDecoderProperties(parameter.getConfiguration());
        for (LocationReference locRef : locRefs) {
            locations.add(decode(parameter, properties, locRef));
        }
        return locations;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Location> decodeRaw(final OpenLRDecoderParameter parameter,
                                    final List<RawLocationReference> locRefs)
            throws OpenLRProcessingException {
        List<Location> locations = new ArrayList<Location>();
        OpenLRDecoderProperties properties = new OpenLRDecoderProperties(parameter.getConfiguration());
        for (RawLocationReference locRef : locRefs) {
            locations.add(decodeRaw(parameter, properties, locRef));
        }
        return locations;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Location decodeRaw(final OpenLRDecoderParameter parameter,
                              final RawLocationReference rawLocRef) throws OpenLRProcessingException {
        OpenLRDecoderProperties properties = new OpenLRDecoderProperties(parameter.getConfiguration());
        return decodeRaw(parameter, properties, rawLocRef);
    }

    /**
     * Decode raw.
     *
     * @param parameter the parameter
     * @param properties the properties
     * @param rawLocRef the raw loc ref
     * @return the location
     * @throws OpenLRProcessingException the open lr processing exception
     */
    private Location decodeRaw(final OpenLRDecoderParameter parameter, final OpenLRDecoderProperties properties,
                               final RawLocationReference rawLocRef) throws OpenLRProcessingException {
        if (rawLocRef == null) {
            throw new OpenLRDecoderProcessingException(
                    DecoderProcessingError.INVALID_LOC_REF_DATA, "No data found!");
        }
        if (!rawLocRef.isValid()) {
            return new InvalidLocation(rawLocRef.getID(),
                    DecoderReturnCode.INVALID_LOCATION_REFERENCE_DATA,
                    rawLocRef.getLocationType());
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("decode " + rawLocRef.getID());
            if (!parameter.hasConfiguration()) {
                LOG.debug("No configuration available, use default values instead");
            }
        }

        int compTime4Cache = properties.getCompTime4Cache();
        long startTime = 0;
        long endTime = 0;

        Location cached = null;
        if (parameter.hasLocationDatabase()) {
            LocationDatabase locDB = parameter.getLocationDatabase();
            cached = locDB.getResult(rawLocRef);
            if (LOG.isDebugEnabled() && cached != null) {
                LOG.debug("decoded location found in database");
            }
        }
        Location decoded;
        if (cached != null) {
            decoded = cached;
        } else {
            AbstractDecoder worker;
            switch (rawLocRef.getLocationType()) {
                case GEO_COORDINATES:
                    worker = new GeoCoordDecoder();
                    break;
                case LINE_LOCATION:
                    worker = new LineDecoder();
                    break;
                case POI_WITH_ACCESS_POINT:
                    worker = new PoiAccessDecoder();
                    break;
                case POINT_ALONG_LINE:
                    worker = new PointAlongDecoder();
                    break;
                /** New cases addes by DLR e.V. (RE) */
                case CIRCLE:
                    worker = new CircleDecoder();
                    break;
                case RECTANGLE:
                    worker = new RectangleDecoder();
                    break;
                case GRID:
                    worker = new GridDecoder();
                    break;
                case POLYGON:
                    worker = new PolygonDecoder();
                    break;
                case CLOSED_LINE:
                    worker = new ClosedLineDecoder();
                    break;
                case UNKNOWN:
                default:
                    return new InvalidLocation(rawLocRef.getID(),
                            DecoderReturnCode.INVALID_LOCATION_TYPE,
                            LocationType.UNKNOWN);
            }

            // measure the decoding time if a threshold for caching has been set
            if (parameter.hasLocationDatabase() && compTime4Cache > 0) {
                startTime = System.currentTimeMillis();
            }
            decoded = worker.doDecoding(properties, parameter.getMapDatase(), rawLocRef);
            if (parameter.hasLocationDatabase() && compTime4Cache > 0) {
                endTime = System.currentTimeMillis();
            }

            // store in DB if a database is available and no time threshold is
            // set or
            // the decoding time exceeds this threshold!!
            if (parameter.hasLocationDatabase()
                    && (compTime4Cache <= 0 || (endTime - startTime > compTime4Cache))) {
                parameter.getLocationDatabase().storeResult(rawLocRef, decoded);
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("decoding finished");
            }
        }
        return decoded;
    }

    /**
     * Resolves the corresponding decoder to a location reference and decodes
     * the data. The method investigates all physical decoder being found on the
     * class path and tries to match the data identifiers to detect the required
     * physical decoder.
     *
     * If no corresponding physical decoder can be found or the physical
     * decoding process failed then an exception will be thrown.
     *
     * @param locRef the bin
     * @param decoders the decoders
     * @return the raw format of a location reference
     * @throws OpenLRProcessingException the open lr processing exception
     */
    private RawLocationReference resolveRawFormat(
            final LocationReference locRef, final List<PhysicalDecoder> decoders)
            throws OpenLRProcessingException {
        if (locRef == null) {
            throw new OpenLRDecoderProcessingException(
                    DecoderProcessingError.INVALID_LOC_REF_DATA);
        }
        RawLocationReference rawLocRef = null;
        for (PhysicalDecoder p : decoders) {
            if (p.getDataFormatIdentifier().equals(locRef.getDataIdentifier())) {
                try {
                    rawLocRef = p.decodeData(locRef);
                } catch (PhysicalFormatException e) {
                    throw new OpenLRDecoderProcessingException(
                            DecoderProcessingError.INVALID_LOC_REF_DATA, e);
                }
            }
        }
        return rawLocRef;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMajorVersion() {
        return VERSION.getMajorVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMinorVersion() {
        return VERSION.getMinorVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPatchVersion() {
        return VERSION.getPatchVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getVersion() {
        return VERSION.toString();
    }
}
