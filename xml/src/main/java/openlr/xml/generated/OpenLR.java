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

package openlr.xml.generated;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 * <p>
 * Java class for anonymous complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LocationID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="XMLLocationReference" type="{http://www.openlr.org/openlr}XMLLocationReference" minOccurs="0"/>
 *         &lt;element name="BinaryLocationReferences" type="{http://www.openlr.org/openlr}BinaryLocationReferences" minOccurs="0"/>
 *         &lt;element name="AdditionalInformation" type="{http://www.openlr.org/openlr}AdditionalInformation" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"locationID", "xmlLocationReference",
        "binaryLocationReferences", "additionalInformation"})
@XmlRootElement(name = "OpenLR")
public class OpenLR implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -7446140059081071104L;

    /** The location id. */
    @XmlElement(name = "LocationID", required = true)
    protected String locationID;

    /** The xml location reference. */
    @XmlElement(name = "XMLLocationReference")
    protected XMLLocationReference xmlLocationReference;

    /** The binary location references. */
    @XmlElement(name = "BinaryLocationReferences")
    protected BinaryLocationReferences binaryLocationReferences;

    /** The additional information. */
    @XmlElement(name = "AdditionalInformation")
    protected AdditionalInformation additionalInformation;

    /**
     * Gets the value of the locationID property.
     *
     * @return possible object is {@link String }
     *
     */
    public final String getLocationID() {
        return locationID;
    }

    /**
     * Sets the value of the locationID property.
     *
     * @param value
     *            allowed object is {@link String }
     *
     */
    public final void setLocationID(final String value) {
        this.locationID = value;
    }

    /**
     * Gets the value of the xmlLocationReference property.
     *
     * @return possible object is {@link XMLLocationReference }
     *
     */
    public final XMLLocationReference getXMLLocationReference() {
        return xmlLocationReference;
    }

    /**
     * Sets the value of the xmlLocationReference property.
     *
     * @param value
     *            allowed object is {@link XMLLocationReference }
     *
     */
    public final void setXMLLocationReference(final XMLLocationReference value) {
        this.xmlLocationReference = value;
    }

    /**
     * Gets the value of the binaryLocationReferences property.
     *
     * @return possible object is {@link BinaryLocationReferences }
     *
     */
    public final BinaryLocationReferences getBinaryLocationReferences() {
        return binaryLocationReferences;
    }

    /**
     * Sets the value of the binaryLocationReferences property.
     *
     * @param value
     *            allowed object is {@link BinaryLocationReferences }
     *
     */
    public final void setBinaryLocationReferences(final BinaryLocationReferences value) {
        this.binaryLocationReferences = value;
    }

    /**
     * Gets the value of the additionalInformation property.
     *
     * @return possible object is {@link AdditionalInformation }
     *
     */
    public final AdditionalInformation getAdditionalInformation() {
        return additionalInformation;
    }

    /**
     * Sets the value of the additionalInformation property.
     *
     * @param value
     *            allowed object is {@link AdditionalInformation }
     *
     */
    public final void setAdditionalInformation(final AdditionalInformation value) {
        this.additionalInformation = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof OpenLR)) {
            return false;
        }
        OpenLR other = (OpenLR) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(locationID, other.locationID)
                .append(xmlLocationReference, other.xmlLocationReference)
                .append(binaryLocationReferences,
                        other.binaryLocationReferences)
                .append(additionalInformation, other.additionalInformation);
        return builder.isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(locationID).append(xmlLocationReference)
                .append(binaryLocationReferences).append(additionalInformation);
        return builder.toHashCode();
    }
}
