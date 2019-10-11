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
import openlr.xml.generated.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The BinaryLocRefHandler handles binary data included in the XML format.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public final class BinaryLocRefHandler {

    /**
     * Instantiates a new binary loc ref handler.
     */
    private BinaryLocRefHandler() {
        throw new UnsupportedOperationException();
    }

    /**
     * Creates OpenLR XML data format which includes binary data.
     *
     * @param data
     *            the binary data
     * @return the openlr xml
     */
    public static OpenLR createXmlWithBinaryData(
            final List<BinaryLocationReferenceData> data) {
        ObjectFactory of = new ObjectFactory();
        OpenLR xmlData = of.createOpenLR();
        BinaryLocationReferences bins = of.createBinaryLocationReferences();
        xmlData.setBinaryLocationReferences(bins);
        for (BinaryLocationReferenceData b : data) {
            BinaryLocationReference binRef = of.createBinaryLocationReference();
            binRef.setId(b.getId());
            binRef.setVersion(Integer.toString(b.getVersion()));
            binRef.setValue(b.getData());
            bins.getBinaryLocationReference().add(binRef);
        }
        return xmlData;
    }

    /**
     * Resolve binary data from XML.
     *
     * @param xmlData
     *            the xml data
     * @return list of binary location references
     * @throws OpenLRXMLException
     *             if extracting fails
     */
    public static List<BinaryLocationReferenceData> resolveBinaryDatafromXML(
            final OpenLR xmlData) throws OpenLRXMLException {
        BinaryLocationReferences binaries = xmlData
                .getBinaryLocationReferences();
        if (binaries == null) {
            throw new OpenLRXMLException(XMLErrorType.DATA_ERROR,
                    "no binary location reference");
        }
        List<BinaryLocationReference> binariesData = binaries
                .getBinaryLocationReference();
        if (binariesData == null || binariesData.isEmpty()) {
            throw new OpenLRXMLException(XMLErrorType.DATA_ERROR,
                    "no valid binary location reference");
        }
        List<BinaryLocationReferenceData> bins = new ArrayList<BinaryLocationReferenceData>();
        for (BinaryLocationReference b : binariesData) {
            int version = -1;
            try {
                version = Integer.parseInt(b.getVersion());
                if (b.getValue().length > 0) {
                    bins.add(new BinaryLocationReferenceData(b.getId(),
                            version, b.getValue()));
                } else {
                    throw new OpenLRXMLException(
                            XMLErrorType.DATA_ERROR,
                            "Binary location reference without value: "
                                    + b.getId());
                }
            } catch (NumberFormatException nfe) {
                throw new OpenLRXMLException(XMLErrorType.INVALID_VERSION,
                        "Invalid version in OpenLR location reference: " + b.getVersion());
            }
        }
        return bins;
    }

    /**
     * Tests whether the XML data contains (at least one) binary location
     * reference. <b>This test does not interpret the value of the location
     * reference!</a> It does not check if the location reference is valid or
     * even if its value is set (empty string)! This method will always return
     * <code>true</code> if an location reference object exists in the given
     * OpenLR instance.
     *
     * @param xmlData
     *            the xml data
     * @return true, if successful
     */
    public static boolean containsBinaryLocationReference(final OpenLR xmlData) {
        if (xmlData.getBinaryLocationReferences() != null) {
            List<BinaryLocationReference> binaryLocationReference = xmlData
                    .getBinaryLocationReferences().getBinaryLocationReference();
            if (binaryLocationReference != null
                    && !binaryLocationReference.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Tests whether the XML data contains a xml location reference.
     *
     * @param xmlData
     *            the xml data
     * @return true, if successful
     */
    public static boolean containsXmlLocationReference(final OpenLR xmlData) {
        XMLLocationReference xmlLocationReference = xmlData
                .getXMLLocationReference();
        if (xmlLocationReference != null
                && (xmlLocationReference.getLineLocationReference() != null || xmlLocationReference
                .getPointLocationReference() != null)) {
            return true;
        }
        return false;
    }

    /**
     * The BinaryLocationReferenceData holds the information for binary data
     * directly included in the XML format.
     *
     */
    public static class BinaryLocationReferenceData {

        /** The id. */
        private final String id;

        /** The version. */
        private final int version;

        /** The data. */
        private final byte[] data;

        /**
         * Instantiates a new binary location reference data.
         *
         * @param i
         *            the id
         * @param v
         *            the version
         * @param d
         *            the data
         */
        public BinaryLocationReferenceData(final String i, final int v,
                                           final byte[] d) {
            id = i;
            version = v;
            data = d.clone();
        }

        /**
         * Gets the id.
         *
         * @return the id
         */
        public final String getId() {
            return id;
        }

        /**
         * Gets the version.
         *
         * @return the version
         */
        public final int getVersion() {
            return version;
        }

        /**
         * Gets the data.
         *
         * @return the data
         */
        public final byte[] getData() {
            return data.clone();
        }

    }

}

