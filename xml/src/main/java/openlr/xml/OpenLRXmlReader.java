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
package openlr.xml;

import openlr.xml.OpenLRXMLException.XMLErrorType;
import openlr.xml.generated.ObjectFactory;
import openlr.xml.generated.OpenLR;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;
import java.net.URL;

/**
 * The OpenLRXmlReader reads OpenLR location reference data from XML.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class OpenLRXmlReader {

    /** The Constant XML_SCHEMA_OPENLR. */
    private static final String XML_SCHEMA_OPENLR = "openlr.xsd";

    /** The Constant TTLR_XSD points to the XML schema. */
    private static final URL OPENLR_XSD = OpenLRXmlReader.class.getClassLoader().getResource(XML_SCHEMA_OPENLR);

    /** The Constant OPENLR_OBJECT_FACTORY. */
    private static final Class<?> OPENLR_OBJECT_FACTORY = ObjectFactory.class;

    /**
     * Used to make sure that only one jaxb operation is active at any certain
     * time.
     */
    private static final Object SYNC_OBJECT = new Object();

    /** The JAXB context. */
    private final JAXBContext jc;

    /** The schema. */
    private final Schema schema;

    /**
     * Instantiates a new XML file reader.
     *
     * @throws OpenLRXMLException if instantiation failed
     */
    public OpenLRXmlReader() throws OpenLRXMLException {
        if (OPENLR_XSD == null) {
            throw new OpenLRXMLException(XMLErrorType.XSD_ERROR, "schema file not found");
        }
        try {
            jc = JAXBContext.newInstance(OPENLR_OBJECT_FACTORY);
        } catch (JAXBException e) {
            throw new OpenLRXMLException(XMLErrorType.XML_ERROR, e);
        }
        SchemaFactory sf = SchemaFactory
                .newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            schema = sf.newSchema(OPENLR_XSD);
        } catch (SAXException e) {
            throw new OpenLRXMLException(XMLErrorType.XSD_ERROR, e);
        }
    }

    /**
     * Read OpenLR location reference data from a xml file. The
     * xml data will be validated if validate is true.
     *
     * @param file  the input file to read from
     * @param validate validate option
     *
     * @return the OpenLR location reference
     *
     * @throws JAXBException if a xml error occurs
     * @throws SAXException if a xml error occurs
     * @throws IOException if an i/o exception occurs
     */
    public final OpenLR readOpenLRXML(final File file, final boolean validate)
            throws JAXBException, SAXException, IOException {
        OpenLR result = null;
        if (file != null) {
            synchronized (SYNC_OBJECT) {
                Unmarshaller unmarshaller = jc.createUnmarshaller();
                if (validate) {
                    unmarshaller.setSchema(schema);
                }
                InputStream is = new BufferedInputStream(new FileInputStream(file));
                result = unmarshaller.unmarshal(new StreamSource(is),
                        OpenLR.class).getValue();
                is.close();
            }
        }
        return result;
    }

    /**
     * Read OpenLR location reference data as xml using an input stream. The
     * xml data will be validated if validate is true.
     *
     * @param is  the input stream to read from
     * @param validate validate option
     *
     * @return the OpenLR location reference
     *
     * @throws JAXBException if a xml error occurs
     * @throws SAXException if a xml error occurs
     */
    public final OpenLR readOpenLRXML(final InputStream is, final boolean validate)
            throws JAXBException, SAXException {
        OpenLR result = null;
        if (is != null) {
            synchronized (SYNC_OBJECT) {
                Unmarshaller unmarshaller = jc.createUnmarshaller();
                if (validate) {
                    unmarshaller.setSchema(schema);
                }
                result = unmarshaller.unmarshal(new StreamSource(new BufferedInputStream(is)),
                        OpenLR.class).getValue();
            }
        }
        return result;
    }

}
