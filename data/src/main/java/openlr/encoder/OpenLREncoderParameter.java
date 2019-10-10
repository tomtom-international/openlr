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
package openlr.encoder;

import openlr.PhysicalEncoder;
import openlr.map.MapDatabase;
import org.apache.commons.configuration.Configuration;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public final class OpenLREncoderParameter {

    /** The map. */
    private final MapDatabase map;

    /** The config. */
    private final Configuration config;

    /** The lrdb. */
    private final LRDatabase lrdb;

    /** The phys encoder. */
    private final List<PhysicalEncoder> physEncoder;


    /**
     * Instantiates a new open lr encoder parameter.
     *
     * @param b the builder
     */
    private OpenLREncoderParameter(final Builder b) {
        map = b.map;
        config = b.config;
        lrdb = b.lrdb;
        physEncoder = b.physEncoder;
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
     * Gets the lR database.
     *
     * @return the lR database
     */
    public LRDatabase getLRDatabase() {
        return lrdb;
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
     * Checks for lr database.
     *
     * @return true, if successful
     */
    public boolean hasLRDatabase() {
        return lrdb != null;
    }

    /**
     * Checks for physical encoder.
     *
     * @return true, if successful
     */
    public boolean hasPhysicalEncoder() {
        return !physEncoder.isEmpty();
    }

    /**
     * Gets the physical encoders.
     *
     * @return the physical encoders
     */
    public List<PhysicalEncoder> getPhysicalEncoders() {
        return physEncoder;
    }

    /**
     * The Class Builder.
     */
    public static final class Builder {

        /** The map. */
        private MapDatabase map;

        /** The config. */
        private Configuration config;

        /** The lrdb. */
        private LRDatabase lrdb;

        /** The phys encoder. */
        private List<PhysicalEncoder> physEncoder = Collections.emptyList();


        /**
         * With map database.
         *
         * @param mdb the mdb
         * @return the builder
         */
        public Builder with(final MapDatabase mdb) {
            map = mdb;
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
         * With location reference database.
         *
         * @param l the l
         * @return the builder
         */
        public Builder with(final LRDatabase l) {
            lrdb = l;
            return this;
        }

        /**
         * With physical encoders.
         *
         * @param pe the pe
         * @return the builder
         */
        public Builder with(final List<PhysicalEncoder> pe) {
            physEncoder = Collections.unmodifiableList(pe);
            return this;
        }

        /**
         * Builds the parameter.
         *
         * @return the open lr encoder parameter
         */
        public OpenLREncoderParameter buildParameter() {
            return new OpenLREncoderParameter(this);
        }

    }

}
