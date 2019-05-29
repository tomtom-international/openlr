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
package openlr.decoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import openlr.PhysicalDecoder;
import openlr.map.MapDatabase;

import org.apache.commons.configuration.Configuration;

/** 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public final class OpenLRDecoderParameter {

	/** The map. */
	private final MapDatabase map;

	/** The config. */
	private final Configuration config;

	/** The ldb. */
	private final LocationDatabase ldb;
	
	/** The phys decoder. */
	private final List<PhysicalDecoder> physDecoder;

	/**
	 * Instantiates a new open lr decoder parameter.
	 *
	 * @param b the b
	 */
	private OpenLRDecoderParameter(final Builder b) {
		map = b.map;
		config = b.config;
		ldb = b.ldb;
		physDecoder = b.physDecoder;
	}

	/**
	 * Gets the map datase.
	 *
	 * @return the map datase
	 */
	public MapDatabase getMapDatase() {
		return map;
	}

	/**
	 * Gets the configuration.
	 *
	 * @return the configuration
	 */
	public Configuration getConfiguration() {
		return config;
	}

	/**
	 * Gets the location database.
	 *
	 * @return the location database
	 */
	public LocationDatabase getLocationDatabase() {
		return ldb;
	}

	/**
	 * Checks for map database.
	 *
	 * @return true, if successful
	 */
	public boolean hasMapDatabase() {
		return map != null;
	}

	/**
	 * Checks for configuration.
	 *
	 * @return true, if successful
	 */
	public boolean hasConfiguration() {
		return config != null;
	}

	/**
	 * Checks for location database.
	 *
	 * @return true, if successful
	 */
	public boolean hasLocationDatabase() {
		return ldb != null;
	}
	
	/**
	 * Checks for physical decoder.
	 *
	 * @return true, if successful
	 */
	public boolean hasPhysicalDecoder() {
		return !physDecoder.isEmpty();
	}
	
	/**
	 * Gets the physical decoders.
	 *
	 * @return the physical decoders
	 */
	public List<PhysicalDecoder> getPhysicalDecoders() {
		return physDecoder;
	}
	
	/**
	 * The Class Builder.
	 */
	public static final class Builder {
		/** The map. */
		private MapDatabase map;

		/** The config. */
		private Configuration config;

		/** The ldb. */
		private LocationDatabase ldb;
		
		/** The phys decoder. */
		private List<PhysicalDecoder> physDecoder = new ArrayList<PhysicalDecoder>();
		
		/**
		 * With map database.
		 *
		 * @param m the m
		 * @return the builder
		 */
		public Builder with(final MapDatabase m) {
			map = m;
			return this;
		}
		
		/**
		 * With configuration.
		 *
		 * @param c the c
		 * @return the builder
		 */
		public Builder with(final Configuration c) {
			config = c;
			return this;
		}
		
		/**
		 * With location database.
		 *
		 * @param l the l
		 * @return the builder
		 */
		public Builder with(final LocationDatabase l) {
			ldb = l;
			return this;
		}
		
		/**
		 * With physical decoders.
		 *
		 * @param pd the pd
		 * @return the builder
		 */
		public Builder with(final List<PhysicalDecoder> pd) {
			physDecoder = Collections.unmodifiableList(pd);
			return this;
		}
		
		/**
		 * Builds the parameter.
		 *
		 * @return the open lr decoder parameter
		 */
		public OpenLRDecoderParameter buildParameter() {
			return new OpenLRDecoderParameter(this);
		}
	}

}
