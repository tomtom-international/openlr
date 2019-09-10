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
package eu.datex2.schema._2_0rc2._2_0;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for HeaderInformation complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="HeaderInformation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="areaOfInterest" type="{http://datex2.eu/schema/2_0RC2/2_0}AreaOfInterestEnum" minOccurs="0"/>
 *         &lt;element name="confidentiality" type="{http://datex2.eu/schema/2_0RC2/2_0}ConfidentialityValueEnum"/>
 *         &lt;element name="informationStatus" type="{http://datex2.eu/schema/2_0RC2/2_0}InformationStatusEnum"/>
 *         &lt;element name="urgency" type="{http://datex2.eu/schema/2_0RC2/2_0}UrgencyEnum" minOccurs="0"/>
 *         &lt;element name="headerInformationExtension" type="{http://datex2.eu/schema/2_0RC2/2_0}_ExtensionType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HeaderInformation", propOrder = {
        "areaOfInterest",
        "confidentiality",
        "informationStatus",
        "urgency",
        "headerInformationExtension"
})
public class HeaderInformation {

    /** The area of interest. */
    protected AreaOfInterestEnum areaOfInterest;

    /** The confidentiality. */
    @XmlElement(required = true)
    protected ConfidentialityValueEnum confidentiality;

    /** The information status. */
    @XmlElement(required = true)
    protected InformationStatusEnum informationStatus;

    /** The urgency. */
    protected UrgencyEnum urgency;

    /** The header information extension. */
    protected ExtensionType headerInformationExtension;

    /**
     * Gets the value of the areaOfInterest property.
     *
     * @return the area of interest
     * possible object is
     * {@link AreaOfInterestEnum }
     */
    public final AreaOfInterestEnum getAreaOfInterest() {
        return areaOfInterest;
    }

    /**
     * Sets the value of the areaOfInterest property.
     *
     * @param value
     *     allowed object is
     *     {@link AreaOfInterestEnum }
     *
     */
    public final void setAreaOfInterest(final AreaOfInterestEnum value) {
        this.areaOfInterest = value;
    }

    /**
     * Gets the value of the confidentiality property.
     *
     * @return the confidentiality
     * possible object is
     * {@link ConfidentialityValueEnum }
     */
    public final ConfidentialityValueEnum getConfidentiality() {
        return confidentiality;
    }

    /**
     * Sets the value of the confidentiality property.
     *
     * @param value
     *     allowed object is
     *     {@link ConfidentialityValueEnum }
     *
     */
    public final void setConfidentiality(final ConfidentialityValueEnum value) {
        this.confidentiality = value;
    }

    /**
     * Gets the value of the informationStatus property.
     *
     * @return the information status
     * possible object is
     * {@link InformationStatusEnum }
     */
    public final InformationStatusEnum getInformationStatus() {
        return informationStatus;
    }

    /**
     * Sets the value of the informationStatus property.
     *
     * @param value
     *     allowed object is
     *     {@link InformationStatusEnum }
     *
     */
    public final void setInformationStatus(final InformationStatusEnum value) {
        this.informationStatus = value;
    }

    /**
     * Gets the value of the urgency property.
     *
     * @return the urgency
     * possible object is
     * {@link UrgencyEnum }
     */
    public final UrgencyEnum getUrgency() {
        return urgency;
    }

    /**
     * Sets the value of the urgency property.
     *
     * @param value
     *     allowed object is
     *     {@link UrgencyEnum }
     *
     */
    public final void setUrgency(final UrgencyEnum value) {
        this.urgency = value;
    }

    /**
     * Gets the value of the headerInformationExtension property.
     *
     * @return the header information extension
     * possible object is
     * {@link ExtensionType }
     */
    public final ExtensionType getHeaderInformationExtension() {
        return headerInformationExtension;
    }

    /**
     * Sets the value of the headerInformationExtension property.
     *
     * @param value
     *     allowed object is
     *     {@link ExtensionType }
     *
     */
    public final void setHeaderInformationExtension(final ExtensionType value) {
        this.headerInformationExtension = value;
    }

    /**
     * To string.
     *
     * @param toStringBuilder the to string builder
     */
    public final void toString(final ToStringBuilder toStringBuilder) {
        AreaOfInterestEnum theAreaOfInterest;
        theAreaOfInterest = this.getAreaOfInterest();
        toStringBuilder.append("areaOfInterest", theAreaOfInterest);
        ConfidentialityValueEnum theConfidentiality;
        theConfidentiality = this.getConfidentiality();
        toStringBuilder.append("confidentiality", theConfidentiality);
        InformationStatusEnum theInformationStatus;
        theInformationStatus = this.getInformationStatus();
        toStringBuilder.append("informationStatus", theInformationStatus);
        UrgencyEnum theUrgency;
        theUrgency = this.getUrgency();
        toStringBuilder.append("urgency", theUrgency);
        ExtensionType theHeaderInformationExtension;
        theHeaderInformationExtension = this.getHeaderInformationExtension();
        toStringBuilder.append("headerInformationExtension", theHeaderInformationExtension);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        final ToStringBuilder toStringBuilder = new ToStringBuilder(this);
        toString(toStringBuilder);
        return toStringBuilder.toString();
    }

    /**
     * Equals.
     *
     * @param object the object
     * @param equalsBuilder the equals builder
     */
    public final void equals(final Object object, final EqualsBuilder equalsBuilder) {
        if (!(object instanceof HeaderInformation)) {
            equalsBuilder.appendSuper(false);
            return;
        }
        if (this == object) {
            return;
        }
        final HeaderInformation that = ((HeaderInformation) object);
        equalsBuilder.append(this.getAreaOfInterest(), that.getAreaOfInterest());
        equalsBuilder.append(this.getConfidentiality(), that.getConfidentiality());
        equalsBuilder.append(this.getInformationStatus(), that.getInformationStatus());
        equalsBuilder.append(this.getUrgency(), that.getUrgency());
        equalsBuilder.append(this.getHeaderInformationExtension(), that.getHeaderInformationExtension());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object object) {
        if (!(object instanceof HeaderInformation)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final EqualsBuilder equalsBuilder = new EqualsBuilder();
        equals(object, equalsBuilder);
        return equalsBuilder.isEquals();
    }

    /**
     * Hash code.
     *
     * @param hashCodeBuilder the hash code builder
     */
    public final void hashCode(final HashCodeBuilder hashCodeBuilder) {
        hashCodeBuilder.append(this.getAreaOfInterest());
        hashCodeBuilder.append(this.getConfidentiality());
        hashCodeBuilder.append(this.getInformationStatus());
        hashCodeBuilder.append(this.getUrgency());
        hashCodeBuilder.append(this.getHeaderInformationExtension());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        final HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
        hashCode(hashCodeBuilder);
        return hashCodeBuilder.toHashCode();
    }

}
