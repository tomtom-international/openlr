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

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import openlr.LocationReference;
import openlr.OpenLRProcessingException;
import openlr.PhysicalEncoder;
import openlr.Version;
import openlr.VersionHelper;
import openlr.encoder.OpenLREncoderProcessingException.EncoderProcessingError;
import openlr.encoder.properties.OpenLREncoderProperties;
import openlr.encoder.worker.AbstractEncoder;
import openlr.encoder.worker.CircleEncoder;
import openlr.encoder.worker.ClosedLineEncoder;
import openlr.encoder.worker.GeoCoordEncoder;
import openlr.encoder.worker.GridEncoder;
import openlr.encoder.worker.LineEncoder;
import openlr.encoder.worker.PoiAccessEncoder;
import openlr.encoder.worker.PointAlongEncoder;
import openlr.encoder.worker.PolygonEncoder;
import openlr.encoder.worker.RectangleEncoder;
import openlr.location.Location;
import openlr.rawLocRef.RawLocationReference;

import org.apache.log4j.Logger;

/**
 * The main class of the OpenLR encoder. <br>
 * <br>
 * The OpenLR encoder generates a map-independent location reference for a
 * location. This location reference can be used for decoding and finding back
 * the location on a (possibly different) map. The location reference(s) can be
 * returned in different physical formats by implementing the PhysicalEncoder
 * interface from the OpenLR data package and providing this implementations as
 * a service. If no physical encoder can be found the process will stop
 * immediately. <br>
 * The OpenLR system is completely documented in the OpenLR white paper.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public final class OpenLREncoder implements openlr.OpenLREncoder {

	/** Logger. */
	private static final Logger LOG = Logger.getLogger(OpenLREncoder.class);

	/** the version of the encoder */
	private final Version VERSION = VersionHelper.getVersion("encoder");

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<LocationReferenceHolder> encodeLocations(
			final OpenLREncoderParameter parameter, final List<Location> loc)
			throws OpenLRProcessingException {
		if (loc == null || loc.isEmpty()) {
			LOG.error("No location provided!");
			throw new OpenLREncoderProcessingException(
					EncoderProcessingError.INVALID_PARAMETER);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("OpenLR encoding of " + loc.size() + " locations started");
		}
		List<LocationReferenceHolder> llr = new ArrayList<LocationReferenceHolder>();
		OpenLREncoderProperties properties = new OpenLREncoderProperties(parameter.getConfiguration(), parameter.getPhysicalEncoders());
		// encode every single location
		for (Location l : loc) {
			LocationReferenceHolder lr = encodeLocation(parameter, properties, l);

			llr.add(lr);
		}
		return llr;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LocationReferenceHolder encodeLocation(
			final OpenLREncoderParameter parameter, final Location loc)
			throws OpenLRProcessingException {
		return encodeLocation(parameter,
				new OpenLREncoderProperties(parameter.getConfiguration(), parameter.getPhysicalEncoders()), loc);
	}

	/**
	 * Encode location.
	 * 
	 * @param parameter
	 *            the parameter
	 * @param properties
	 *            the properties
	 * @param loc
	 *            the loc
	 * @return the location reference holder
	 * @throws OpenLRProcessingException
	 *             the open lr processing exception
	 */
	private LocationReferenceHolder encodeLocation(
			final OpenLREncoderParameter parameter,
			final OpenLREncoderProperties properties, final Location loc)
			throws OpenLRProcessingException {
		if (loc == null) {
			LOG.error("No location provided!");
			throw new OpenLREncoderProcessingException(
					EncoderProcessingError.INVALID_PARAMETER);
		}

		int compTime4Cache = properties.getCompTime4Cache();
		long startTime = 0;
		long endTime = 0;

		// check if the location is already encoded and stored in the database
		if (parameter.hasLRDatabase()) {
			LRDatabase lrdb = parameter.getLRDatabase();
			LocationReferenceHolder lrh = lrdb.getResult(loc);
			if (LOG.isDebugEnabled() && lrh != null) {
				LOG.debug("Location found in the database cache");
			}
			if (lrh != null) {
				return lrh;
			}
		}

		// look for physical encoders in class path
		List<PhysicalEncoder> physEncoders = parameter.getPhysicalEncoders();
		if (physEncoders.isEmpty()) {
			// if no physical encoders are set from outside, try to load them
			// from classpath
			physEncoders = getPhysicalEncoderServices();
		}
		if (physEncoders.isEmpty()) {
			throw new OpenLREncoderProcessingException(
					EncoderProcessingError.NO_PHYSICAL_ENCODER_FOUND);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("OpenLR encoder called for ID: " + loc.getID());
			if (!parameter.hasConfiguration()) {
				LOG.debug("No configuration available, use default values instead");
			}
		}

		if (parameter.hasMapDatabase()
				&& properties.isCheckTurnRestrictions()
				&& !parameter.getMapDatase().hasTurnRestrictions()) {
			// no check possible because there are no restrictions loaded!!
			LOG.warn("Turn restrictions should be checked but there are no turn restrictions loaded!");
		}

		// encoding process
		AbstractEncoder worker = null;
		switch (loc.getLocationType()) {
		case GEO_COORDINATES:
			worker = new GeoCoordEncoder();
			break;
		case LINE_LOCATION:
			worker = new LineEncoder();
			break;
		case POI_WITH_ACCESS_POINT:
			worker = new PoiAccessEncoder();
			break;
		case POINT_ALONG_LINE:
			worker = new PointAlongEncoder();
			break;
		/** additional cases added by DLR e.V. (RE) */
		case CIRCLE:
			worker = new CircleEncoder();
			break;
		case RECTANGLE:
			worker = new RectangleEncoder();
			break;
		case GRID:
			worker = new GridEncoder();
			break;
		case POLYGON:
			worker = new PolygonEncoder();
			break;
		case CLOSED_LINE:
			worker = new ClosedLineEncoder();
			break;
		case UNKNOWN:
		default:
			return new LocationReferenceHolderImpl(loc.getID(),
					EncoderReturnCode.INVALID_LOCATION_TYPE,
					loc.getLocationType());
		}
		// measure the encoding time if a threshold for caching has been set
		if (parameter.hasLRDatabase() && compTime4Cache > 0) {
			startTime = System.currentTimeMillis();
		}
		RawLocationReference rawLocRef = worker.doEncoding(loc, properties, parameter.getMapDatase());

		LocationReferenceHolderImpl lrHolder = null;
		if (!rawLocRef.isValid()) {
			lrHolder = new LocationReferenceHolderImpl(rawLocRef.getID(),
					rawLocRef.getReturnCode(), rawLocRef.getLocationType());
		} else {
			lrHolder = new LocationReferenceHolderImpl(loc.getID(), rawLocRef);
			for (PhysicalEncoder pEnc : physEncoders) {
				int version = properties.getPhysicalFormatVersion(pEnc.getDataFormatIdentifier());
				LocationReference locRef = null;
				if (version == -1) {
					locRef = pEnc.encodeData(rawLocRef);
				} else {
					locRef = pEnc.encodeData(rawLocRef, version);
				}
				lrHolder.addLocationReference(pEnc.getDataFormatIdentifier(),
						locRef);
			}
		}
		if (parameter.hasLRDatabase() && compTime4Cache > 0) {
			endTime = System.currentTimeMillis();
		}

		if (parameter.hasLRDatabase()
				&& (compTime4Cache <= 0 || (endTime - startTime > compTime4Cache))) {
			LRDatabase lrdb = parameter.getLRDatabase();
			lrdb.storeResult(loc, lrHolder);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("encoding finished (valid=" + lrHolder.isValid() + ")");
		}
		return lrHolder;
	}

	/**
	 * Gets the physical encoder services found in the class path. If no
	 * physical encoders are found the returned list is empty.
	 * 
	 * @return the physical encoders
	 */
	public static List<PhysicalEncoder> getPhysicalEncoderServices() {
		ServiceLoader<PhysicalEncoder> encoderServices = ServiceLoader
				.load(PhysicalEncoder.class);
		List<PhysicalEncoder> encoders = new ArrayList<PhysicalEncoder>();
		for (PhysicalEncoder p : encoderServices) {
			encoders.add(p);
		}
		return encoders;
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
