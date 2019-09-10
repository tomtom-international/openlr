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

import javax.xml.bind.annotation.*;

/**
 * <p>Java class for D2LogicalModel complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="D2LogicalModel">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="exchange" type="{http://datex2.eu/schema/2_0RC2/2_0}Exchange"/>
 *         &lt;element name="payloadPublication" type="{http://datex2.eu/schema/2_0RC2/2_0}PayloadPublication" minOccurs="0"/>
 *         &lt;element name="d2LogicalModelExtension" type="{http://datex2.eu/schema/2_0RC2/2_0}_ExtensionType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="modelBaseVersion" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" fixed="2.0RC2" />
 *       &lt;attribute name="extensionName" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" default="OpenLR DATEX II extension" />
 *       &lt;attribute name="extensionVersion" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" default="1.1" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "D2LogicalModel", propOrder = {
        "exchange",
        "payloadPublication",
        "d2LogicalModelExtension"
})
public class D2LogicalModel {

    /** The exchange. */
    @XmlElement(required = true)
    protected Exchange exchange;

    /** The payload publication. */
    protected PayloadPublication payloadPublication;

    /** The d2 logical model extension. */
    protected ExtensionType d2LogicalModelExtension;

    /** The model base version. */
    @XmlAttribute(name = "modelBaseVersion", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String modelBaseVersion;

    /** The extension name. */
    @XmlAttribute(name = "extensionName")
    @XmlSchemaType(name = "anySimpleType")
    protected String extensionName;

    /** The extension version. */
    @XmlAttribute(name = "extensionVersion")
    @XmlSchemaType(name = "anySimpleType")
    protected String extensionVersion;

    /**
     * Gets the value of the exchange property.
     *
     * @return the exchange
     * possible object is
     * {@link Exchange }
     */
    public final Exchange getExchange() {
        return exchange;
    }

    /**
     * Sets the value of the exchange property.
     *
     * @param value
     *     allowed object is
     *     {@link Exchange }
     *
     */
    public final void setExchange(final Exchange value) {
        this.exchange = value;
    }

    /**
     * Gets the value of the payloadPublication property.
     *
     * @return the payload publication
     * possible object is
     * {@link PayloadPublication }
     */
    public final PayloadPublication getPayloadPublication() {
        return payloadPublication;
    }

    /**
     * Sets the value of the payloadPublication property.
     *
     * @param value
     *     allowed object is
     *     {@link PayloadPublication }
     *
     */
    public final void setPayloadPublication(final PayloadPublication value) {
        this.payloadPublication = value;
    }

    /**
     * Gets the value of the d2LogicalModelExtension property.
     *
     * @return the d2 logical model extension
     * possible object is
     * {@link ExtensionType }
     */
    public final ExtensionType getD2LogicalModelExtension() {
        return d2LogicalModelExtension;
    }

    /**
     * Sets the value of the d2LogicalModelExtension property.
     *
     * @param value
     *     allowed object is
     *     {@link ExtensionType }
     *
     */
    public final void setD2LogicalModelExtension(final ExtensionType value) {
        this.d2LogicalModelExtension = value;
    }

    /**
     * Gets the value of the modelBaseVersion property.
     *
     * @return the model base version
     * possible object is
     * {@link String }
     */
    public final String getModelBaseVersion() {
        if (modelBaseVersion == null) {
            return "2.0RC2";
        } else {
            return modelBaseVersion;
        }
    }

    /**
     * Sets the value of the modelBaseVersion property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public final void setModelBaseVersion(final String value) {
        this.modelBaseVersion = value;
    }

    /**
     * Gets the value of the extensionName property.
     *
     * @return the extension name
     * possible object is
     * {@link String }
     */
    public final String getExtensionName() {
        if (extensionName == null) {
            return "OpenLR DATEX II extension";
        } else {
            return extensionName;
        }
    }

    /**
     * Sets the value of the extensionName property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public final void setExtensionName(final String value) {
        this.extensionName = value;
    }

    /**
     * Gets the value of the extensionVersion property.
     *
     * @return the extension version
     * possible object is
     * {@link String }
     */
    public final String getExtensionVersion() {
        if (extensionVersion == null) {
            return "1.1";
        } else {
            return extensionVersion;
        }
    }

    /**
     * Sets the value of the extensionVersion property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public final void setExtensionVersion(final String value) {
        this.extensionVersion = value;
    }

    /**
     * To string.
     *
     * @param toStringBuilder the to string builder
     */
    public final void toString(final ToStringBuilder toStringBuilder) {
        Exchange theExchange;
        theExchange = this.getExchange();
        toStringBuilder.append("exchange", theExchange);
        PayloadPublication thePayloadPublication;
        thePayloadPublication = this.getPayloadPublication();
        toStringBuilder.append("payloadPublication", thePayloadPublication);
        ExtensionType theD2LogicalModelExtension;
        theD2LogicalModelExtension = this.getD2LogicalModelExtension();
        toStringBuilder.append("d2LogicalModelExtension", theD2LogicalModelExtension);
        String theModelBaseVersion;
        theModelBaseVersion = this.getModelBaseVersion();
        toStringBuilder.append("modelBaseVersion", theModelBaseVersion);
        String theExtensionName;
        theExtensionName = this.getExtensionName();
        toStringBuilder.append("extensionName", theExtensionName);
        String theExtensionVersion;
        theExtensionVersion = this.getExtensionVersion();
        toStringBuilder.append("extensionVersion", theExtensionVersion);
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
        if (!(object instanceof D2LogicalModel)) {
            equalsBuilder.appendSuper(false);
            return;
        }
        if (this == object) {
            return;
        }
        final D2LogicalModel that = ((D2LogicalModel) object);
        equalsBuilder.append(this.getExchange(), that.getExchange());
        equalsBuilder.append(this.getPayloadPublication(), that.getPayloadPublication());
        equalsBuilder.append(this.getD2LogicalModelExtension(), that.getD2LogicalModelExtension());
        equalsBuilder.append(this.getModelBaseVersion(), that.getModelBaseVersion());
        equalsBuilder.append(this.getExtensionName(), that.getExtensionName());
        equalsBuilder.append(this.getExtensionVersion(), that.getExtensionVersion());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object object) {
        if (!(object instanceof D2LogicalModel)) {
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
        hashCodeBuilder.append(this.getExchange());
        hashCodeBuilder.append(this.getPayloadPublication());
        hashCodeBuilder.append(this.getD2LogicalModelExtension());
        hashCodeBuilder.append(this.getModelBaseVersion());
        hashCodeBuilder.append(this.getExtensionName());
        hashCodeBuilder.append(this.getExtensionVersion());
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
