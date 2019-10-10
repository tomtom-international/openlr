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
import java.math.BigInteger;

/**
 * <p>
 * Java class for OpenlrPathAttributes complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="OpenlrPathAttributes">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="openlrLowestFRCToNextLRPoint" type="{http://datex2.eu/schema/2_0RC2/2_0}OpenlrFunctionalRoadClassEnum"/>
 *         &lt;element name="openlrDistanceToNextLRPoint" type="{http://datex2.eu/schema/2_0RC2/2_0}NonNegativeInteger"/>
 *         &lt;element name="openlrPathAttributesExtension" type="{http://datex2.eu/schema/2_0RC2/2_0}_ExtensionType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OpenlrPathAttributes", propOrder = {
        "openlrLowestFRCToNextLRPoint", "openlrDistanceToNextLRPoint",
        "openlrPathAttributesExtension"})
public class OpenlrPathAttributes {

    /** The openlr lowest frc to next lr point. */
    @XmlElement(required = true)
    protected OpenlrFunctionalRoadClassEnum openlrLowestFRCToNextLRPoint;

    /** The openlr distance to next lr point. */
    @XmlElement(required = true)
    protected BigInteger openlrDistanceToNextLRPoint;

    /** The openlr path attributes extension. */
    protected ExtensionType openlrPathAttributesExtension;

    /**
     * Gets the value of the openlrLowestFRCToNextLRPoint property.
     *
     * @return the openlr lowest frc to next lr point possible object is
     *         {@link OpenlrFunctionalRoadClassEnum }
     */
    public final OpenlrFunctionalRoadClassEnum getOpenlrLowestFRCToNextLRPoint() {
        return openlrLowestFRCToNextLRPoint;
    }

    /**
     * Sets the value of the openlrLowestFRCToNextLRPoint property.
     *
     * @param value
     *            allowed object is {@link OpenlrFunctionalRoadClassEnum }
     *
     */
    public final void setOpenlrLowestFRCToNextLRPoint(
            final OpenlrFunctionalRoadClassEnum value) {
        this.openlrLowestFRCToNextLRPoint = value;
    }

    /**
     * Gets the value of the openlrDistanceToNextLRPoint property.
     *
     * @return the openlr distance to next lr point possible object is
     *         {@link BigInteger }
     */
    public final BigInteger getOpenlrDistanceToNextLRPoint() {
        return openlrDistanceToNextLRPoint;
    }

    /**
     * Sets the value of the openlrDistanceToNextLRPoint property.
     *
     * @param value
     *            allowed object is {@link BigInteger }
     *
     */
    public final void setOpenlrDistanceToNextLRPoint(final BigInteger value) {
        this.openlrDistanceToNextLRPoint = value;
    }

    /**
     * Gets the value of the openlrPathAttributesExtension property.
     *
     * @return the openlr path attributes extension possible object is
     *         {@link ExtensionType }
     */
    public final ExtensionType getOpenlrPathAttributesExtension() {
        return openlrPathAttributesExtension;
    }

    /**
     * Sets the value of the openlrPathAttributesExtension property.
     *
     * @param value
     *            allowed object is {@link ExtensionType }
     *
     */
    public final void setOpenlrPathAttributesExtension(final ExtensionType value) {
        this.openlrPathAttributesExtension = value;
    }

    /**
     * To string.
     *
     * @param toStringBuilder
     *            the to string builder
     */
    public final void toString(final ToStringBuilder toStringBuilder) {
        OpenlrFunctionalRoadClassEnum theOpenlrLowestFRCToNextLRPoint;
        theOpenlrLowestFRCToNextLRPoint = this
                .getOpenlrLowestFRCToNextLRPoint();
        toStringBuilder.append("openlrLowestFRCToNextLRPoint",
                theOpenlrLowestFRCToNextLRPoint);
        BigInteger theOpenlrDistanceToNextLRPoint;
        theOpenlrDistanceToNextLRPoint = this
                .getOpenlrDistanceToNextLRPoint();
        toStringBuilder.append("openlrDistanceToNextLRPoint",
                theOpenlrDistanceToNextLRPoint);
        ExtensionType theOpenlrPathAttributesExtension;
        theOpenlrPathAttributesExtension = this
                .getOpenlrPathAttributesExtension();
        toStringBuilder.append("openlrPathAttributesExtension",
                theOpenlrPathAttributesExtension);
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
     * @param object
     *            the object
     * @param equalsBuilder
     *            the equals builder
     */
    public final void equals(final Object object, final EqualsBuilder equalsBuilder) {
        if (!(object instanceof OpenlrPathAttributes)) {
            equalsBuilder.appendSuper(false);
            return;
        }
        if (this == object) {
            return;
        }
        final OpenlrPathAttributes that = ((OpenlrPathAttributes) object);
        equalsBuilder.append(this.getOpenlrLowestFRCToNextLRPoint(),
                that.getOpenlrLowestFRCToNextLRPoint());
        equalsBuilder.append(this.getOpenlrDistanceToNextLRPoint(),
                that.getOpenlrDistanceToNextLRPoint());
        equalsBuilder.append(this.getOpenlrPathAttributesExtension(),
                that.getOpenlrPathAttributesExtension());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object object) {
        if (!(object instanceof OpenlrPathAttributes)) {
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
     * @param hashCodeBuilder
     *            the hash code builder
     */
    public final void hashCode(final HashCodeBuilder hashCodeBuilder) {
        hashCodeBuilder.append(this.getOpenlrLowestFRCToNextLRPoint());
        hashCodeBuilder.append(this.getOpenlrDistanceToNextLRPoint());
        hashCodeBuilder.append(this.getOpenlrPathAttributesExtension());
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
