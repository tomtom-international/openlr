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
package openlr.xml.impl;

import openlr.LocationReference;
import openlr.LocationType;
import openlr.StatusCode;
import openlr.xml.LocationReferenceXmlHelper;
import openlr.xml.OpenLRXMLConstants;
import openlr.xml.OpenLRXmlWriter;
import openlr.xml.XmlReturnCode;
import openlr.xml.generated.OpenLR;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Implementation of the {@link LocationReference} interface.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class LocationReferenceXmlImpl implements LocationReference {

    /** The id. */
    private final String id;

    /** The error. */
    private final XmlReturnCode returnCode;

    /** The loc type. */
    private final LocationType locType;

    /** The location reference data. */
    private final OpenLR data;

    /** The version. */
    private final int version;

    /**
     * Instantiates a valid location reference.
     *
     * @param i the id
     * @param d the location reference data
     * @param ver the version
     */
    public LocationReferenceXmlImpl(final String i, final OpenLR d, final int ver) {
        id = i;
        data = d;
        returnCode = null;
        locType = LocationReferenceXmlHelper.resolveLocationType(d);
        version = ver;
    }

    /**
     * Instantiates an invalid location reference.
     *
     * @param i the id
     * @param e the error, an {@link XmlReturnCode}
     * @param t the location type
     * @param ver the version
     */
    public LocationReferenceXmlImpl(final String i, final XmlReturnCode e,
                                    final LocationType t, final int ver) {
        id = i;
        data = null;
        returnCode = e;
        locType = t;
        version = ver;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public final StatusCode getReturnCode() {
        return returnCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getID() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isValid() {
        return returnCode == null && locType != LocationType.UNKNOWN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Class<?> getDataClass() {
        return OpenLR.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getDataIdentifier() {
        return OpenLRXMLConstants.IDENTIFIER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Object getLocationReferenceData() {
        if (isValid()) {
            return data;
        } else {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void toStream(final OutputStream os) throws IOException {
        OpenLRXmlWriter writer = null;
        try {
            writer = new OpenLRXmlWriter();
            writer.saveOpenLRXML(data, os, true);
        } catch (JAXBException e) {
            throw new IOException(e);
        } catch (SAXException e) {
            throw new IOException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final LocationType getLocationType() {
        return locType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id: ").append(id);
        sb.append(" type: ").append(locType);
        if (returnCode != null) {
            sb.append(" error: ").append(returnCode);
        } else {
            sb.append(" data: ").append(data);
        }
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getVersion() {
        return version;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(locType).append(returnCode).append(data).append(version);
        return builder.toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object obj) {
        if (!(obj instanceof LocationReferenceXmlImpl)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        LocationReferenceXmlImpl other = (LocationReferenceXmlImpl) obj;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(locType, other.locType).append(returnCode,
                other.returnCode).append(data, other.data).append(version,
                other.version);
        return builder.isEquals();
    }

}
