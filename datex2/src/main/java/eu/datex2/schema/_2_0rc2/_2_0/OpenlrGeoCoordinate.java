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

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * <p>Java class for OpenlrGeoCoordinate complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="OpenlrGeoCoordinate">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="openlrCoordinate" type="{http://datex2.eu/schema/2_0RC2/2_0}PointCoordinates"/>
 *         &lt;element name="openlrGeoCoordinateExtension" type="{http://datex2.eu/schema/2_0RC2/2_0}_ExtensionType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OpenlrGeoCoordinate", propOrder = {
        "openlrCoordinate",
        "openlrGeoCoordinateExtension"
})
public class OpenlrGeoCoordinate {

    /** The openlr coordinate. */
    @XmlElement(required = true)
    protected PointCoordinates openlrCoordinate;

    /** The openlr geo coordinate extension. */
    protected ExtensionType openlrGeoCoordinateExtension;

    /**
     * Gets the value of the openlrCoordinate property.
     *
     * @return the openlr coordinate
     * possible object is
     * {@link PointCoordinates }
     */
    public final PointCoordinates getOpenlrCoordinate() {
        return openlrCoordinate;
    }

    /**
     * Sets the value of the openlrCoordinate property.
     *
     * @param value
     *     allowed object is
     *     {@link PointCoordinates }
     *
     */
    public final void setOpenlrCoordinate(final PointCoordinates value) {
        this.openlrCoordinate = value;
    }

    /**
     * Gets the value of the openlrGeoCoordinateExtension property.
     *
     * @return the openlr geo coordinate extension
     * possible object is
     * {@link ExtensionType }
     */
    public final ExtensionType getOpenlrGeoCoordinateExtension() {
        return openlrGeoCoordinateExtension;
    }

    /**
     * Sets the value of the openlrGeoCoordinateExtension property.
     *
     * @param value
     *     allowed object is
     *     {@link ExtensionType }
     *
     */
    public final void setOpenlrGeoCoordinateExtension(final ExtensionType value) {
        this.openlrGeoCoordinateExtension = value;
    }

    /**
     * To string.
     *
     * @param toStringBuilder the to string builder
     */
    public final void toString(final ToStringBuilder toStringBuilder) {
        PointCoordinates theOpenlrCoordinate;
        theOpenlrCoordinate = this.getOpenlrCoordinate();
        toStringBuilder.append("openlrCoordinate", theOpenlrCoordinate);
        ExtensionType theOpenlrGeoCoordinateExtension;
        theOpenlrGeoCoordinateExtension = this.getOpenlrGeoCoordinateExtension();
        toStringBuilder.append("openlrGeoCoordinateExtension", theOpenlrGeoCoordinateExtension);
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
        if (!(object instanceof OpenlrGeoCoordinate)) {
            equalsBuilder.appendSuper(false);
            return;
        }
        if (this == object) {
            return;
        }
        final OpenlrGeoCoordinate that = ((OpenlrGeoCoordinate) object);
        equalsBuilder.append(this.getOpenlrCoordinate(), that.getOpenlrCoordinate());
        equalsBuilder.append(this.getOpenlrGeoCoordinateExtension(), that.getOpenlrGeoCoordinateExtension());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object object) {
        if (!(object instanceof OpenlrGeoCoordinate)) {
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
        hashCodeBuilder.append(this.getOpenlrCoordinate());
        hashCodeBuilder.append(this.getOpenlrGeoCoordinateExtension());
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
