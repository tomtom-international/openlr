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
package openlr.map.sqlite.loader;

import openlr.map.loader.MapLoadParameter;

/**
 * The class DBFileNameParameter implements a map loader parameter. This
 * parameter represents the path to the database file.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class DBFileNameParameter implements MapLoadParameter {

    /**
     * The Constant IDENTIFIER.
     */
    public static final int IDENTIFIER = 1;

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -7329609426045551511L;

    /**
     * The value.
     */
    private String value;

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getDescription() {
        return "The SQLite database";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getName() {
        return "Database file";
    }

    /*
     * {@inheritDoc}
     */
    @Override
    public final boolean isRequired() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ParameterType getType() {
        return ParameterType.FILE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getIdentifier() {
        return IDENTIFIER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getValue() {
        return value;
    }

    /**
     * Sets the value.
     *
     * @param s the new value
     */
    public final void setValue(final String s) {
        value = s;
    }
}
