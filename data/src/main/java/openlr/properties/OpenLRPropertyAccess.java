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

import openlr.OpenLRProcessingException;
import openlr.properties.OpenLRPropertyException.PropertyError;
import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * The Class OpenLRPropertiesAccess offers the access to the properties being
 * loaded from the OpenLR properties file and encapsulated in the
 * {@link Configuration} object.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public final class OpenLRPropertyAccess {

    /** logging */
    private static final Logger LOG = Logger
            .getLogger(OpenLRPropertyAccess.class);

    /**
     * Utility class shall not be instantiated.
     */
    private OpenLRPropertyAccess() {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the integer property value.
     *
     * @param properties
     *            the properties
     * @param property
     *            the property
     * @return the integer property int value
     * @throws OpenLRPropertyException
     *             the open lr property exception
     */
    public static int getIntegerPropertyValue(final Configuration properties,
                                              final OpenLRProperty property) throws OpenLRPropertyException {
        if (property.getPropertyType() != PropertyType.INTEGER) {
            String error = property.getKey() + " is not an integer";
            LOG.error(error);
            throw new OpenLRPropertyException(
                    PropertyError.INVALID_PROPERTY_TYPE, error);
        }
        if (properties == null) {
            return (Integer) property.getDefault();
        }
        return properties.getInt(property.getKey(),
                (Integer) property.getDefault());
    }

    /**
     * Gets the float property value.
     *
     * @param properties
     *            the properties
     * @param property
     *            the property
     * @return the float property value
     * @throws OpenLRPropertyException
     *             the open lr property exception
     */
    public static float getFloatPropertyValue(final Configuration properties,
                                              final OpenLRProperty property) throws OpenLRPropertyException {
        if (property.getPropertyType() != PropertyType.FLOAT) {
            String error = property.getKey() + " is not a float";
            LOG.error(error);
            throw new OpenLRPropertyException(
                    PropertyError.INVALID_PROPERTY_TYPE, error);
        }
        if (properties == null) {
            return (Float) property.getDefault();
        }
        return properties.getFloat(property.getKey(),
                (Float) property.getDefault());
    }

    /**
     * Gets the string property value.
     *
     * @param properties
     *            the properties
     * @param property
     *            the property
     * @return the integer property int value
     * @throws OpenLRPropertyException
     *             the open lr property exception
     */
    public static String getStringPropertyValue(final Configuration properties,
                                                final OpenLRProperty property) throws OpenLRPropertyException {
        if (property.getPropertyType() != PropertyType.STRING) {
            String error = property.getKey() + " is not an integer";
            LOG.error(error);
            throw new OpenLRPropertyException(
                    PropertyError.INVALID_PROPERTY_TYPE, error);
        }
        if (properties == null) {
            return (String) property.getDefault();
        }
        return properties.getString(property.getKey(),
                (String) property.getDefault());
    }

    /**
     * Gets the boolean property value.
     *
     * @param properties
     *            the properties
     * @param property
     *            the property
     * @return the boolean property value
     * @throws OpenLRProcessingException
     *             the open lr processing exception
     */
    public static boolean getBooleanPropertyValue(
            final Configuration properties, final OpenLRProperty property)
            throws OpenLRProcessingException {
        if (property.getPropertyType() != PropertyType.BOOLEAN) {
            String error = property.getKey() + " is not a boolean";
            LOG.error(error);
            throw new OpenLRPropertyException(
                    PropertyError.INVALID_PROPERTY_TYPE, error);
        }
        if (properties == null) {
            return (Boolean) property.getDefault();
        }
        return properties.getBoolean(property.getKey(),
                (Boolean) property.getDefault());
    }

    /**
     * Gets the integer property value.
     *
     * @param properties
     *            the properties
     * @param property
     *            the property
     * @param id
     *            the id
     * @return the boolean property value
     * @throws OpenLRProcessingException
     *             the open lr processing exception
     */
    public static int getIntegerPropertyValueById(
            final Configuration properties, final OpenLRProperty property,
            final String id) throws OpenLRProcessingException {
        if (property.getPropertyType() != PropertyType.INTEGER_BY_ID) {
            String error = property.getKey() + " is not an integer by id";
            LOG.error(error);
            throw new OpenLRPropertyException(
                    PropertyError.INVALID_PROPERTY_TYPE, error);
        }
        if (properties == null) {
            return (Integer) property.getDefault();
        }
        return properties.getInt(property.getKey() + "." + id,
                (Integer) property.getDefault());
    }

    /**
     * Gets the integer property value.
     *
     * @param properties
     *            the properties
     * @param property
     *            the property
     * @param id
     *            the id
     * @return the boolean property value
     * @throws OpenLRProcessingException
     *             the open lr processing exception
     */
    @SuppressWarnings("unchecked")
    public static int getIntegerPropertyValueFromMap(
            final Configuration properties, final OpenLRProperty property,
            final String id) throws OpenLRProcessingException {
        if (property.getPropertyType() != PropertyType.INTEGER_BY_MAP) {
            String error = property.getKey() + " is not an integer from map";
            LOG.error(error);
            throw new OpenLRPropertyException(
                    PropertyError.INVALID_PROPERTY_TYPE, error);
        }
        Map<String, Integer> data = (Map<String, Integer>) property
                .getDefault();
        Integer defaultValue = data.get(id);
        if (properties == null) {
            if (defaultValue == null) {
                String error = property.getKey() + "[" + id
                        + "] has no default value";
                LOG.error(error);
                throw new OpenLRPropertyException(
                        PropertyError.INVALID_PROPERTY_TYPE, error);
            }
            return data.get(id);
        }
        if (!properties.containsKey(property.getKey() + "." + id)
                && defaultValue == null) {
            String error = property.getKey() + "[" + id + "] has no value";
            LOG.error(error);
            throw new OpenLRPropertyException(
                    PropertyError.INVALID_PROPERTY_TYPE, error);
        }
        return properties.getInt(property.getKey() + "." + id, defaultValue);
    }
}
