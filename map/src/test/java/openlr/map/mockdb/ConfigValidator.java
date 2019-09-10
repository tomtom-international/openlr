/**
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 2 of the License and the extra
 * conditions for OpenLR. (see openlr-license.txt)
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 * <p>
 * Copyright (C) 2009,2010 TomTom International B.V.
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
 *  Copyright (C) 2009,2010 TomTom International B.V.
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
package openlr.map.mockdb;

import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Validates a mock database configuration against the XML schema 
 * <code>openlr/map/mockdb/MockDatabase.xsd</code>.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
final class ConfigValidator {

    /**
     * The single instance of the XML validator.
     */
    private static Validator validator;

    /**
     * Disable constructor.
     */
    private ConfigValidator() {

    }

    /**
     * Validates the input stream. Initializes the validator at the first run.
     *
     * @param is
     *            The input stream to validate against the mock database schema.
     * @throws InvalidConfigurationException
     *             If an validation error occurs.
     */
    static void validate(final InputStream is)
            throws InvalidConfigurationException {

        if (validator == null) {

            SchemaFactory factory = SchemaFactory
                    .newInstance("http://www.w3.org/2001/XMLSchema");
            URL schemaLocation = MockedMapDatabase.class.getClassLoader()
                    .getResource("openlr/map/mockdb/MockDatabase.xsd");
            try {
                Schema schema = factory.newSchema(schemaLocation);
                validator = schema.newValidator();
            } catch (SAXException e) {
                throw new InitializationException("Error instantiating the XML "
                        + "schema for validation!", e);
            }
        }

        if (validator != null) {
            try {
                validator.validate(new StreamSource(is));
            } catch (SAXException e) {
                throw new InvalidConfigurationException("Validation exception: "
                        + e.getMessage(), e);
            } catch (IOException e) {
                throw new InitializationException("Error during validation.", e);
            }
        }
    }

}
