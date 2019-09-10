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
package openlr.datex2.impl;

import eu.datex2.schema._2_0rc2._2_0.OpenlrLineLocationReference;
import eu.datex2.schema._2_0rc2._2_0.OpenlrPointLocationReference;
import openlr.LocationReference;
import openlr.LocationType;
import openlr.StatusCode;
import openlr.datex2.Datex2Location;
import openlr.datex2.OpenLRDatex2Constants;
import openlr.datex2.OpenLRDatex2Exception;
import openlr.datex2.XmlWriter;
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
public class LocationReferenceImpl implements LocationReference {

    /** The id. */
    private final String id;

    /** The error. */
    private final StatusCode returnCode;

    /** The data. */
    private final Datex2Location data;

    /** The location type. */
    private final LocationType locType;

    /** The version. */
    private final int version;

    /**
     * Instantiates a valid point location reference.
     *
     * @param i
     *            the id
     * @param d
     *            the point location reference data
     * @param ver
     *            the version
     */
    public LocationReferenceImpl(final String i,
                                 final OpenlrPointLocationReference d,
                                 final int ver) {
        this(i, new Datex2Location(d), ver);
    }

    /**
     * Instantiates a valid line location reference.
     *
     * @param i
     *            the id
     * @param d
     *            the line location reference data
     * @param ver
     *            the version
     */
    public LocationReferenceImpl(final String i,
                                 final OpenlrLineLocationReference d, final int ver) {
        this(i, new Datex2Location(d), ver);
    }

    /**
     * Instantiates a valid line location reference.
     *
     * @param i
     *            the id
     * @param d
     *            the line location reference data
     * @param ver
     *            the version
     */
    public LocationReferenceImpl(final String i, final Datex2Location d,
                                 final int ver) {
        id = i;
        data = d;
        returnCode = null;
        version = ver;
        locType = d.getLocationType();
    }

    /**
     * Instantiates an invalid location reference.
     *
     * @param i
     *            the id
     * @param e
     *            the error
     * @param t
     *            the location type
     * @param ver
     *            the version
     */
    public LocationReferenceImpl(final String i, final StatusCode e,
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
        return Datex2Location.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getDataIdentifier() {
        return OpenLRDatex2Constants.IDENTIFIER;
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
        XmlWriter writer = null;
        try {
            writer = new XmlWriter();
            writer.saveDatex2Location(data, os);
        } catch (JAXBException e) {
            throw new IOException(e);
        } catch (SAXException e) {
            throw new IOException(e);
        } catch (OpenLRDatex2Exception e) {
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
        if (!(obj instanceof LocationReferenceImpl)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        LocationReferenceImpl other = (LocationReferenceImpl) obj;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(locType, other.locType)
                .append(returnCode, other.returnCode).append(data, other.data)
                .append(version, other.version);
        return builder.isEquals();
    }
}
