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
package openlr.map.loader;

import openlr.map.MapDatabase;

import java.util.Collection;

/**
 * The interface OpenLRMapLoader defines methods which needs to be implemented
 * in order to provide a new map to be shown in an application. The map loader
 * implementation is aware of the underlying layout of the map data and makes
 * this data available through this map loader interface and the OpenLR map interface.
 *
 * An implementation of the map loader interface needs to be registered as a service
 * so that an application is able to use it during runtime. The implementation needs
 * to be in the class path and will be found at runtime automatically. All required
 * classes must be packaged into a jar-file including a file named
 *
 * META-INF/services/openlr.map.loader.OpenLRMapLoader
 *
 * The content of this text file is the fully qualified name of the map loader
 * interface implementation. 
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public interface OpenLRMapLoader {


    /**
     * Load the map database and return it using parameters.
     *
     * @param params the parameters
     *
     * @return the map database
     *
     * @throws OpenLRMapLoaderException if loading the map failed
     */
    MapDatabase load(Collection<MapLoadParameter> params) throws OpenLRMapLoaderException;

    /**
     * Gets the parameter (required and optional) used by this map loader .
     *
     * @return the parameter
     */
    Collection<MapLoadParameter> getParameter();

    /**
     * Gets the number of parameter (required and optional) used by this map loader.
     *
     * @return the number of parameter
     */
    int getNumberOfParams();

    /**
     * Gets the description of the map loader.
     *
     * @return the description
     */
    String getDescription();

    /**
     * Gets the name of the map loader. The name should be unique and identify this
     * map loader.
     *
     * @return the name of the map loader
     */
    String getName();

    /**
     * Gets the map descriptor. This string describes the map being loaded by the map loader. It should
     * include the location of the map (e.g. in the file system) and the parameters used for loading the
     * map data. If no map has been loaded the string should be null.
     *
     * @return the map descriptor
     */
    String getMapDescriptor();

}
