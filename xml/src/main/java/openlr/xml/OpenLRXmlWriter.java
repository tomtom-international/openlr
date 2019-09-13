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
package openlr.xml;

import openlr.xml.generated.OpenLR;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

/**
 * The OpenLRXmlWriter stores OpenLR location reference data as XML.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class OpenLRXmlWriter {

    /** The Constant LOG. */
    private static final Logger LOG = Logger.getLogger(OpenLRXmlWriter.class);

    /** The Constant XML_SCHEMA_OPENLR. */
    private static final String XML_SCHEMA_OPENLR = "openlr.xsd";

    /** The Constant OPENLR_XSD. */
    private static final URL OPENLR_XSD = OpenLRXmlWriter.class.getClassLoader().getResource(XML_SCHEMA_OPENLR);

    /** Used to make sure that only one jaxb operation is active at any certain time. */
    private static final Object SYNC_OBJECT = new Object();

    /** The jaxb context. */
    private final JAXBContext jaxbContext;

    /** The schema. */
    private final Schema schema;

    /**
     * Instantiates a new XML file writer.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public OpenLRXmlWriter() throws IOException {
        if (OPENLR_XSD == null) {
            throw new IOException("schema file not found");
        }
        try {
            jaxbContext = JAXBContext.newInstance(OpenLR.class);
        } catch (JAXBException e) {
            throw new IOException(e);
        }
        SchemaFactory sf = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            schema = sf.newSchema(OPENLR_XSD);
        } catch (SAXException e) {
            throw new IOException(e);
        }
    }


    /**
     * Save OpenLR location reference data as xml using the file writer. The
     * xml data will be validated if validate is true.
     *
     * @param data the location reference data
     * @param fw the file writer to write to
     * @param validate validate option
     *
     * @throws JAXBException if a xml error occurs
     * @throws SAXException if a xml error occurs
     */
    public final void saveOpenLRXML(final OpenLR data,
                                    final FileWriter fw, final boolean validate) throws JAXBException,
            SAXException {
        if (data != null && fw != null) {
            synchronized (SYNC_OBJECT) {
                Marshaller marshaller = jaxbContext.createMarshaller();
                if (validate) {
                    marshaller.setSchema(schema);
                }
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                        Boolean.TRUE);
                marshaller.marshal(data, fw);
            }
        } else {
            LOG.error("cannot save data as data or stream is null");
        }
    }

    /**
     * Save OpenLR location reference data as xml using the output stream. The
     * xml data will be validated if validate is true.
     *
     * @param data the location reference data
     * @param os the output stream to write to
     * @param validate validate option
     *
     * @throws JAXBException if a xml error occurs
     * @throws SAXException if a xml error occurs
     */
    public final void saveOpenLRXML(final OpenLR data,
                                    final OutputStream os, final boolean validate) throws JAXBException,
            SAXException {
        if (data != null && os != null) {
            synchronized (SYNC_OBJECT) {
                Marshaller marshaller = jaxbContext.createMarshaller();
                if (validate) {
                    marshaller.setSchema(schema);
                }
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                        Boolean.TRUE);
                marshaller.marshal(data, os);
            }
        }
    }

}
