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
package openlr.datex2;

import eu.datex2.schema._2_0rc2._2_0.*;
import openlr.datex2.OpenLRDatex2Exception.XMLErrorType;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

/**
 * The XMLFileReader reads OpenLR location reference data from a XML file.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class XmlReader {

    /** The Constant OPENLR_OBJECT_FACTORY. */
    private static final Class<?> OBJECT_FACTORY = eu.datex2.schema._2_0rc2._2_0.ObjectFactory.class;

    /**
     * Used to make sure that only one jaxb operation is active at any certain
     * time.
     */
    private static final Object SYNC_OBJECT = new Object();

    /** The JAXB context. */
    private final JAXBContext jc;

    /**
     * Instantiates a new XML file reader.
     *
     * @throws OpenLRDatex2Exception
     *             if instantiation failed
     */
    public XmlReader() throws OpenLRDatex2Exception {
        try {
            jc = JAXBContext.newInstance(OBJECT_FACTORY);
        } catch (JAXBException e) {
            throw new OpenLRDatex2Exception(XMLErrorType.XML_ERROR, e);
        }
    }

    /**
     * Read OpenLR location reference data from a xml file.
     *
     * @param file
     *            the input file to read from
     * @return the OpenLR location reference
     * @throws JAXBException
     *             if a xml error occurs
     * @throws SAXException
     *             if a xml error occurs
     * @throws FileNotFoundException
     *             if file not found
     * @throws OpenLRDatex2Exception
     *             if no valid location reference can be found
     */
    public final Datex2Location readDatex2Location(final File file)
            throws JAXBException, SAXException, FileNotFoundException,
            OpenLRDatex2Exception {
        Datex2Location result = null;
        if (file != null) {
            synchronized (SYNC_OBJECT) {
                Unmarshaller unmarshaller = jc.createUnmarshaller();
                result = new Datex2Location(unmarshaller
                        .unmarshal(new StreamSource(new BufferedInputStream(
                                new FileInputStream(file)))));
            }
        }
        return result;
    }

    /**
     * Read OpenLR location reference data as xml using an input stream.
     *
     * @param is
     *            the input stream to read from
     *
     * @return the OpenLR location reference
     *
     * @throws JAXBException
     *             if a xml error occurs
     * @throws SAXException
     *             if a xml error occurs
     * @throws OpenLRDatex2Exception
     *             if no valid location reference can be found
     */
    public final Datex2Location readDatex2Location(final InputStream is)
            throws JAXBException, SAXException, OpenLRDatex2Exception {
        Datex2Location result = null;
        if (is != null) {
            synchronized (SYNC_OBJECT) {
                Unmarshaller unmarshaller = jc.createUnmarshaller();
                Object o = unmarshaller.unmarshal(new StreamSource(
                        new BufferedInputStream(is)));
                Object data = ((JAXBElement<?>) o).getValue();
                if (data instanceof OpenlrLineLocationReference
                        || data instanceof OpenlrPointLocationReference) {
                    result = new Datex2Location(data);
                } else if (data instanceof D2LogicalModel) {
                    D2LogicalModel model = (D2LogicalModel) data;
                    if (model.getPayloadPublication() == null
                            || !(model.getPayloadPublication() instanceof SituationPublication)) {
                        throw new OpenLRDatex2Exception(
                                XMLErrorType.DATA_ERROR);
                    }
                    SituationPublication payload = (SituationPublication) model
                            .getPayloadPublication();
                    if (payload.getSituation().isEmpty()) {
                        throw new OpenLRDatex2Exception(
                                XMLErrorType.DATA_ERROR);
                    }
                    Situation s = payload.getSituation().get(0);
                    if (s == null || s.getSituationRecord().isEmpty()) {
                        throw new OpenLRDatex2Exception(
                                XMLErrorType.DATA_ERROR);
                    }
                    SituationRecord record = s.getSituationRecord().get(0);
                    if (record == null || !(record instanceof Accident)) {
                        throw new OpenLRDatex2Exception(
                                XMLErrorType.DATA_ERROR);
                    }
                    Accident a = (Accident) record;
                    GroupOfLocations locations = a.getGroupOfLocations();
                    if (locations instanceof Linear) {
                        Linear linLoc = (Linear) locations;
                        if (linLoc.getLinearExtension() == null
                                || linLoc.getLinearExtension()
                                .getOpenlrExtendedLinear() == null) {
                            throw new OpenLRDatex2Exception(
                                    XMLErrorType.DATA_ERROR);
                        }
                        OpenlrExtendedLinear linear = linLoc
                                .getLinearExtension().getOpenlrExtendedLinear();
                        if (linear.getOpenlrLineLocationReference() == null) {
                            throw new OpenLRDatex2Exception(
                                    XMLErrorType.DATA_ERROR);
                        }
                        return new Datex2Location(linear
                                .getOpenlrLineLocationReference());

                    } else if (locations instanceof Point) {
                        Point pointLoc = (Point) locations;
                        if (pointLoc.getPointExtension() == null
                                || pointLoc.getPointExtension().getOpenlrExtendedPoint() == null) {
                            throw new OpenLRDatex2Exception(
                                    XMLErrorType.DATA_ERROR);
                        }
                        OpenlrExtendedPoint point = pointLoc.getPointExtension().getOpenlrExtendedPoint();
                        if (point.getOpenlrPointLocationReference() == null) {
                            throw new OpenLRDatex2Exception(
                                    XMLErrorType.DATA_ERROR);
                        }
                        return new Datex2Location(point
                                .getOpenlrPointLocationReference());
                    } else {
                        throw new OpenLRDatex2Exception(
                                XMLErrorType.DATA_ERROR);
                    }
                } else {
                    throw new OpenLRDatex2Exception(
                            XMLErrorType.DATA_ERROR);
                }

            }
        }
        return result;
    }

}
