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
package openlr.properties;

import openlr.properties.OpenLRPropertyException.PropertyError;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.FileConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;

/**
 * The Class OpenLRPropertiesReader provides a reader for the OpenLR properties
 * file. The properties file should be proper XML according to the OpenLR
 * properties schema definition.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public final class OpenLRPropertiesReader {

    /** logger. */
    private static final Logger LOG = LoggerFactory.getLogger(OpenLRPropertiesReader.class);

    /**
     * Utility class shall not be instantiated.
     */
    private OpenLRPropertiesReader() {
        throw new UnsupportedOperationException();
    }

    /**
     * Load the OpenLR properties file and validate the content according to the
     * OpenLR properties schema definition. This method is able to process
     * properties in format of XML or Java properties files. If the file suffix
     * is not ".properties" XML is assumed to be the content format, which is
     * the internally standard.
     *
     * @param file
     *            the file
     * @return the OpenLR properties
     * @throws OpenLRPropertyException
     *             the open lr property exception
     */
    public static FileConfiguration loadPropertiesFromFile(final File file)
            throws OpenLRPropertyException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("load OpenLR properties file");
        }
        if (file == null) {
            LOG.error("Cannot find the OpenLR properties file.");
            throw new OpenLRPropertyException(
                    PropertyError.PROPERTIES_FILE_NOT_FOUND);
        }
        FileConfiguration conf;
        try {
            if (file.getName().endsWith(".properties")) {
                conf = new PropertiesConfiguration(file);
            } else {
                conf = new XMLConfiguration(file);
            }
        } catch (ConfigurationException e) {
            throw new OpenLRPropertyException(PropertyError.MISSING_PROPERTY, e);
        }
        return conf;
    }

    /**
     * Load properties from stream.
     *
     * @param iStream
     *            the i stream
     * @param isXML
     *            the is xml
     * @return the configuration
     * @throws OpenLRPropertyException
     *             the open lr property exception
     */
    public static FileConfiguration loadPropertiesFromStream(
            final InputStream iStream, final boolean isXML)
            throws OpenLRPropertyException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("load OpenLR properties file");
        }
        if (iStream == null) {
            LOG.error("Cannot find the OpenLR properties file.");
            throw new OpenLRPropertyException(
                    PropertyError.PROPERTIES_FILE_NOT_FOUND);
        }
        try {
            FileConfiguration conf;
            if (isXML) {
                conf = new XMLConfiguration();
            } else {
                conf = new PropertiesConfiguration();
            }
            conf.load(iStream);
            return conf;
        } catch (ConfigurationException e) {
            throw new OpenLRPropertyException(PropertyError.MISSING_PROPERTY, e);
        }
    }
}
