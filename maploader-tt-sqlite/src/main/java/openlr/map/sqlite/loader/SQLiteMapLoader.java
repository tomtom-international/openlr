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
package openlr.map.sqlite.loader;

import openlr.map.MapDatabase;
import openlr.map.loader.MapLoadParameter;
import openlr.map.loader.OpenLRMapLoader;
import openlr.map.loader.OpenLRMapLoaderException;
import openlr.map.sqlite.impl.MapDatabaseImpl;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The class SQLiteMapLoader implements an OpenLR map loader. It loads a SQLite
 * database provided by TomTom.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class SQLiteMapLoader implements OpenLRMapLoader {

    private static final Collection<MapLoadParameter> PARAMS = new ArrayList<MapLoadParameter>();

    static {
        PARAMS.add(new DBFileNameParameter());
    }

    /** The map descriptor. */
    private String mapDescriptor;

    /**
     * {@inheritDoc}
     */
    @Override
    public final MapDatabase load(final Collection<MapLoadParameter> params)
            throws OpenLRMapLoaderException {
        MapDatabase map = null;
        if (params.isEmpty()) {
            throw new OpenLRMapLoaderException("No parameter found");
        }
        String fName = extractDBFileName(params);
        try {
            map = new MapDatabaseImpl(fName);
        } catch (Exception e) {
            throw new OpenLRMapLoaderException("Cannot load SQLite database", e);
        }
        mapDescriptor = fName;
        return map;
    }

    /**
     * Extract the database file name from the parameter list.
     *
     * @param params
     *            the parameter list
     * @return the database filename
     * @throws OpenLRMapLoaderException if no map can be found
     */
    private String extractDBFileName(final Collection<MapLoadParameter> params) throws OpenLRMapLoaderException {
        String fName = null;
        for (MapLoadParameter param : params) {
            if (param.getIdentifier() == DBFileNameParameter.IDENTIFIER) {
                fName = param.getValue();
            }
        }
        if (fName == null) {
            throw new OpenLRMapLoaderException(
                    "Incorrect parameter (db file name)");
        }
        return fName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Collection<MapLoadParameter> getParameter() {
        return PARAMS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getNumberOfParams() {
        return 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getDescription() {
        return "Loader of SQLite map databases";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getName() {
        return "SQLite Map Loader (TomTom)";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getMapDescriptor() {
        return mapDescriptor;
    }

}
