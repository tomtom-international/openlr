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

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 *
 * AdditionalInformation can be added if necessary. This includes the bounding
 * box covering the location and information on the map used for encoding. The
 * AdditionalData section also provides the freedom to add any other information
 * needed.
 *
 *
 * <p>
 * Java class for AdditionalInformation complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="AdditionalInformation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="BoundingBox" type="{http://www.openlr.org/openlr}Rectangle" minOccurs="0"/>
 *         &lt;element name="MapDatabase" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LocationName" type="{http://www.openlr.org/openlr}LocationName" minOccurs="0"/>
 *         &lt;element name="AdditionalData" type="{http://www.openlr.org/openlr}AdditionalData" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AdditionalInformation", propOrder = {

})
public class AdditionalInformation implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1623974273151582143L;

    /** The bounding box. */
    @XmlElement(name = "BoundingBox")
    protected Rectangle boundingBox;

    /** The map database. */
    @XmlElement(name = "MapDatabase")
    protected String mapDatabase;

    /** The location name. */
    @XmlElement(name = "LocationName")
    protected LocationName locationName;

    /** The additional data. */
    @XmlElement(name = "AdditionalData")
    protected AdditionalData additionalData;

    /**
     * Gets the value of the boundingBox property.
     *
     * @return possible object is {@link Rectangle }
     *
     */
    public final Rectangle getBoundingBox() {
        return boundingBox;
    }

    /**
     * Sets the value of the boundingBox property.
     *
     * @param value
     *            allowed object is {@link Rectangle }
     *
     */
    public final void setBoundingBox(final Rectangle value) {
        this.boundingBox = value;
    }

    /**
     * Gets the value of the mapDatabase property.
     *
     * @return possible object is {@link String }
     *
     */
    public final String getMapDatabase() {
        return mapDatabase;
    }

    /**
     * Sets the value of the mapDatabase property.
     *
     * @param value
     *            allowed object is {@link String }
     *
     */
    public final void setMapDatabase(final String value) {
        this.mapDatabase = value;
    }

    /**
     * Gets the value of the locationName property.
     *
     * @return possible object is {@link LocationName }
     *
     */
    public final LocationName getLocationName() {
        return locationName;
    }

    /**
     * Sets the value of the locationName property.
     *
     * @param value
     *            allowed object is {@link LocationName }
     *
     */
    public final void setLocationName(final LocationName value) {
        this.locationName = value;
    }

    /**
     * Gets the value of the additionalData property.
     *
     * @return possible object is {@link AdditionalData }
     *
     */
    public final AdditionalData getAdditionalData() {
        return additionalData;
    }

    /**
     * Sets the value of the additionalData property.
     *
     * @param value
     *            allowed object is {@link AdditionalData }
     *
     */
    public final void setAdditionalData(final AdditionalData value) {
        this.additionalData = value;
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
        if (!(o instanceof AdditionalInformation)) {
            return false;
        }
        AdditionalInformation other = (AdditionalInformation) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(boundingBox, other.boundingBox)
                .append(mapDatabase, other.mapDatabase)
                .append(locationName, other.locationName)
                .append(additionalData, other.additionalData);
        return builder.isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(boundingBox).append(mapDatabase).append(locationName)
                .append(additionalData);
        return builder.toHashCode();
    }

}
