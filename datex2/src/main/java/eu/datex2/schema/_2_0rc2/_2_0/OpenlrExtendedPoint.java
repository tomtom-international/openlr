/**
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License and the extra
 *  conditions for OpenLR. (see openlr-license.txt)
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>Java class for OpenlrExtendedPoint complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OpenlrExtendedPoint">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="openlrPointLocationReference" type="{http://datex2.eu/schema/2_0RC2/2_0}OpenlrPointLocationReference"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OpenlrExtendedPoint", propOrder = {
    "openlrPointLocationReference"
})
public class OpenlrExtendedPoint {

    /** The openlr point location reference. */
    @XmlElement(required = true)
    protected OpenlrPointLocationReference openlrPointLocationReference;

    /**
     * Gets the value of the openlrPointLocationReference property.
     *
     * @return the openlr point location reference
     * possible object is
     * {@link OpenlrPointLocationReference }
     */
    public final OpenlrPointLocationReference getOpenlrPointLocationReference() {
        return openlrPointLocationReference;
    }

    /**
     * Sets the value of the openlrPointLocationReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link OpenlrPointLocationReference }
     *     
     */
    public final void setOpenlrPointLocationReference(final OpenlrPointLocationReference value) {
        this.openlrPointLocationReference = value;
    }

    /**
     * To string.
     *
     * @param toStringBuilder the to string builder
     */
    public final void toString(final ToStringBuilder toStringBuilder) {
        OpenlrPointLocationReference theOpenlrPointLocationReference;
        theOpenlrPointLocationReference = this.getOpenlrPointLocationReference();
        toStringBuilder.append("openlrPointLocationReference", theOpenlrPointLocationReference);
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
        if (!(object instanceof OpenlrExtendedPoint)) {
            equalsBuilder.appendSuper(false);
            return;
        }
        if (this == object) {
            return;
        }
        final OpenlrExtendedPoint that = ((OpenlrExtendedPoint) object);
        equalsBuilder.append(this.getOpenlrPointLocationReference(), that.getOpenlrPointLocationReference());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object object) {
        if (!(object instanceof OpenlrExtendedPoint)) {
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
        hashCodeBuilder.append(this.getOpenlrPointLocationReference());
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
