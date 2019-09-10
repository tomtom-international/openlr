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
package openlr.map.loader;

/**
 * The class OpenLRMapLoaderException will be thrown if the map loader cannot
 * load the map. 
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class OpenLRMapLoaderException extends Exception {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 7185284579422479439L;

    /**
     * Instantiates a new openlr data loader exception due to a previously thrown
     * exception.
     *
     * @param t the previously thrown exception
     */
    public OpenLRMapLoaderException(final Throwable t) {
        super(t);
    }

    /**
     * Instantiates a new openlr data loader exception with a message.
     *
     * @param s the message
     */
    public OpenLRMapLoaderException(final String s) {
        super(s);
    }

    /**
     * Instantiates a new openlr data loader exception.
     */
    public OpenLRMapLoaderException() {
        super();
    }

    /**
     * Instantiates a new openlr data loader exception due to a previously thrown
     * exception and with a message.
     *
     * @param s the message
     * @param t the previously thrown exception
     */
    public OpenLRMapLoaderException(final String s, final Throwable t) {
        super(s, t);
    }

}
