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
package openlr.datex2;

import eu.datex2.schema._2_0rc2._2_0.D2LogicalModel;
import openlr.datex2.OpenLRDatex2Exception.XMLErrorType;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.OutputStream;
import java.io.Writer;

/**
 * The XMLFileWriter stores OpenLR location reference data as XML.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class XmlWriter {

    /** Used to make sure that only one jaxb operation is active at any certain time. */
    private static final Object SYNC_OBJECT = new Object();

    /** The jaxb context. */
    private final JAXBContext jaxbContext;

    /**
     * Instantiates a new XML file writer.
     *
     * @throws OpenLRDatex2Exception if instatiation failed
     */
    public XmlWriter() throws OpenLRDatex2Exception {
        try {
            jaxbContext = JAXBContext.newInstance(D2LogicalModel.class);
        } catch (JAXBException e) {
            throw new OpenLRDatex2Exception(XMLErrorType.XML_ERROR, e);
        }
    }


    /**
     * Save OpenLR location reference data as xml using the file writer.
     *
     * @param data the location reference data
     * @param fw the file writer to write to
     *
     * @throws JAXBException if a xml error occurs
     * @throws SAXException if a xml error occurs
     */
    public final void saveDatex2Location(final Datex2Location data,
                                         final Writer fw) throws JAXBException,
            SAXException {
        if (data != null && fw != null) {
            synchronized (SYNC_OBJECT) {
                Marshaller marshaller = null;
                marshaller = jaxbContext.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                        Boolean.TRUE);
                marshaller.marshal(data.getXMLData(), fw);
            }
        }
    }

    /**
     * Save OpenLR location reference data as xml using the output stream.
     *
     * @param data the location reference data
     * @param os the output stream to write to
     *
     * @throws JAXBException if a xml error occurs
     * @throws SAXException if a xml error occurs
     */
    public final void saveDatex2Location(final Datex2Location data,
                                         final OutputStream os) throws JAXBException,
            SAXException {
        if (data != null && os != null) {
            synchronized (SYNC_OBJECT) {
                Marshaller marshaller = null;
                marshaller = jaxbContext.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                        Boolean.TRUE);
                marshaller.marshal(data.getXMLData(), os);
            }
        }
    }
}
