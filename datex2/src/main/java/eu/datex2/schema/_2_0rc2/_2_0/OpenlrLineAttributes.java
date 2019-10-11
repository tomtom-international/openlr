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
package eu.datex2.schema._2_0rc2._2_0;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for OpenlrLineAttributes complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="OpenlrLineAttributes">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="openlrFunctionalRoadClass" type="{http://datex2.eu/schema/2_0RC2/2_0}OpenlrFunctionalRoadClassEnum"/>
 *         &lt;element name="openlrFormOfWay" type="{http://datex2.eu/schema/2_0RC2/2_0}OpenlrFormOfWayEnum"/>
 *         &lt;element name="openlrBearing" type="{http://datex2.eu/schema/2_0RC2/2_0}AngleInDegreesRestrictedRange"/>
 *         &lt;element name="openlrLineAttributesExtension" type="{http://datex2.eu/schema/2_0RC2/2_0}_ExtensionType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OpenlrLineAttributes", propOrder = {
        "openlrFunctionalRoadClass",
        "openlrFormOfWay",
        "openlrBearing",
        "openlrLineAttributesExtension"
})
public class OpenlrLineAttributes {

    /** The openlr functional road class. */
    @XmlElement(required = true)
    protected OpenlrFunctionalRoadClassEnum openlrFunctionalRoadClass;

    /** The openlr form of way. */
    @XmlElement(required = true)
    protected OpenlrFormOfWayEnum openlrFormOfWay;

    /** The openlr bearing. */
    protected int openlrBearing;

    /** The openlr line attributes extension. */
    protected ExtensionType openlrLineAttributesExtension;

    /**
     * Gets the value of the openlrFunctionalRoadClass property.
     *
     * @return the openlr functional road class
     * possible object is
     * {@link OpenlrFunctionalRoadClassEnum }
     */
    public final OpenlrFunctionalRoadClassEnum getOpenlrFunctionalRoadClass() {
        return openlrFunctionalRoadClass;
    }

    /**
     * Sets the value of the openlrFunctionalRoadClass property.
     *
     * @param value
     *     allowed object is
     *     {@link OpenlrFunctionalRoadClassEnum }
     *
     */
    public final void setOpenlrFunctionalRoadClass(final OpenlrFunctionalRoadClassEnum value) {
        this.openlrFunctionalRoadClass = value;
    }

    /**
     * Gets the value of the openlrFormOfWay property.
     *
     * @return the openlr form of way
     * possible object is
     * {@link OpenlrFormOfWayEnum }
     */
    public final OpenlrFormOfWayEnum getOpenlrFormOfWay() {
        return openlrFormOfWay;
    }

    /**
     * Sets the value of the openlrFormOfWay property.
     *
     * @param value
     *     allowed object is
     *     {@link OpenlrFormOfWayEnum }
     *
     */
    public final void setOpenlrFormOfWay(final OpenlrFormOfWayEnum value) {
        this.openlrFormOfWay = value;
    }

    /**
     * Gets the value of the openlrBearing property.
     *
     * @return the openlr bearing
     */
    public final int getOpenlrBearing() {
        return openlrBearing;
    }

    /**
     * Sets the value of the openlrBearing property.
     *
     * @param value the new openlr bearing
     */
    public final void setOpenlrBearing(final int value) {
        this.openlrBearing = value;
    }

    /**
     * Gets the value of the openlrLineAttributesExtension property.
     *
     * @return the openlr line attributes extension
     * possible object is
     * {@link ExtensionType }
     */
    public final ExtensionType getOpenlrLineAttributesExtension() {
        return openlrLineAttributesExtension;
    }

    /**
     * Sets the value of the openlrLineAttributesExtension property.
     *
     * @param value
     *     allowed object is
     *     {@link ExtensionType }
     *
     */
    public final void setOpenlrLineAttributesExtension(final ExtensionType value) {
        this.openlrLineAttributesExtension = value;
    }

    /**
     * To string.
     *
     * @param toStringBuilder the to string builder
     */
    public final void toString(final ToStringBuilder toStringBuilder) {
        OpenlrFunctionalRoadClassEnum theOpenlrFunctionalRoadClass;
        theOpenlrFunctionalRoadClass = this.getOpenlrFunctionalRoadClass();
        toStringBuilder.append("openlrFunctionalRoadClass", theOpenlrFunctionalRoadClass);
        OpenlrFormOfWayEnum theOpenlrFormOfWay;
        theOpenlrFormOfWay = this.getOpenlrFormOfWay();
        toStringBuilder.append("openlrFormOfWay", theOpenlrFormOfWay);
        int theOpenlrBearing;
        theOpenlrBearing = this.getOpenlrBearing();
        toStringBuilder.append("openlrBearing", theOpenlrBearing);
        ExtensionType theOpenlrLineAttributesExtension;
        theOpenlrLineAttributesExtension = this.getOpenlrLineAttributesExtension();
        toStringBuilder.append("openlrLineAttributesExtension", theOpenlrLineAttributesExtension);
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
        if (!(object instanceof OpenlrLineAttributes)) {
            equalsBuilder.appendSuper(false);
            return;
        }
        if (this == object) {
            return;
        }
        final OpenlrLineAttributes that = ((OpenlrLineAttributes) object);
        equalsBuilder.append(this.getOpenlrFunctionalRoadClass(), that.getOpenlrFunctionalRoadClass());
        equalsBuilder.append(this.getOpenlrFormOfWay(), that.getOpenlrFormOfWay());
        equalsBuilder.append(this.getOpenlrBearing(), that.getOpenlrBearing());
        equalsBuilder.append(this.getOpenlrLineAttributesExtension(), that.getOpenlrLineAttributesExtension());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object object) {
        if (!(object instanceof OpenlrLineAttributes)) {
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
        hashCodeBuilder.append(this.getOpenlrFunctionalRoadClass());
        hashCodeBuilder.append(this.getOpenlrFormOfWay());
        hashCodeBuilder.append(this.getOpenlrBearing());
        hashCodeBuilder.append(this.getOpenlrLineAttributesExtension());
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
