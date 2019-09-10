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
import javax.xml.bind.annotation.XmlType;
import java.math.BigInteger;

/**
 * <p>Java class for OpenlrOffsets complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="OpenlrOffsets">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="openlrPositiveOffset" type="{http://datex2.eu/schema/2_0RC2/2_0}MetresAsNonNegativeInteger" minOccurs="0"/>
 *         &lt;element name="openlrNegativeOffset" type="{http://datex2.eu/schema/2_0RC2/2_0}MetresAsNonNegativeInteger" minOccurs="0"/>
 *         &lt;element name="openlrOffsetsExtension" type="{http://datex2.eu/schema/2_0RC2/2_0}_ExtensionType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OpenlrOffsets", propOrder = {
        "openlrPositiveOffset",
        "openlrNegativeOffset",
        "openlrOffsetsExtension"
})
public class OpenlrOffsets {

    /** The openlr positive offset. */
    protected BigInteger openlrPositiveOffset;

    /** The openlr negative offset. */
    protected BigInteger openlrNegativeOffset;

    /** The openlr offsets extension. */
    protected ExtensionType openlrOffsetsExtension;

    /**
     * Gets the value of the openlrPositiveOffset property.
     *
     * @return the openlr positive offset
     * possible object is
     * {@link BigInteger }
     */
    public final BigInteger getOpenlrPositiveOffset() {
        return openlrPositiveOffset;
    }

    /**
     * Sets the value of the openlrPositiveOffset property.
     *
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *
     */
    public final void setOpenlrPositiveOffset(final BigInteger value) {
        this.openlrPositiveOffset = value;
    }

    /**
     * Gets the value of the openlrNegativeOffset property.
     *
     * @return the openlr negative offset
     * possible object is
     * {@link BigInteger }
     */
    public final BigInteger getOpenlrNegativeOffset() {
        return openlrNegativeOffset;
    }

    /**
     * Sets the value of the openlrNegativeOffset property.
     *
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *
     */
    public final void setOpenlrNegativeOffset(final BigInteger value) {
        this.openlrNegativeOffset = value;
    }

    /**
     * Gets the value of the openlrOffsetsExtension property.
     *
     * @return the openlr offsets extension
     * possible object is
     * {@link ExtensionType }
     */
    public final ExtensionType getOpenlrOffsetsExtension() {
        return openlrOffsetsExtension;
    }

    /**
     * Sets the value of the openlrOffsetsExtension property.
     *
     * @param value
     *     allowed object is
     *     {@link ExtensionType }
     *
     */
    public final void setOpenlrOffsetsExtension(final ExtensionType value) {
        this.openlrOffsetsExtension = value;
    }

    /**
     * To string.
     *
     * @param toStringBuilder the to string builder
     */
    public final void toString(final ToStringBuilder toStringBuilder) {
        BigInteger theOpenlrPositiveOffset;
        theOpenlrPositiveOffset = this.getOpenlrPositiveOffset();
        toStringBuilder.append("openlrPositiveOffset", theOpenlrPositiveOffset);
        BigInteger theOpenlrNegativeOffset;
        theOpenlrNegativeOffset = this.getOpenlrNegativeOffset();
        toStringBuilder.append("openlrNegativeOffset", theOpenlrNegativeOffset);
        ExtensionType theOpenlrOffsetsExtension;
        theOpenlrOffsetsExtension = this.getOpenlrOffsetsExtension();
        toStringBuilder.append("openlrOffsetsExtension", theOpenlrOffsetsExtension);
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
        if (!(object instanceof OpenlrOffsets)) {
            equalsBuilder.appendSuper(false);
            return;
        }
        if (this == object) {
            return;
        }
        final OpenlrOffsets that = ((OpenlrOffsets) object);
        equalsBuilder.append(this.getOpenlrPositiveOffset(), that.getOpenlrPositiveOffset());
        equalsBuilder.append(this.getOpenlrNegativeOffset(), that.getOpenlrNegativeOffset());
        equalsBuilder.append(this.getOpenlrOffsetsExtension(), that.getOpenlrOffsetsExtension());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object object) {
        if (!(object instanceof OpenlrOffsets)) {
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
        hashCodeBuilder.append(this.getOpenlrPositiveOffset());
        hashCodeBuilder.append(this.getOpenlrNegativeOffset());
        hashCodeBuilder.append(this.getOpenlrOffsetsExtension());
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
