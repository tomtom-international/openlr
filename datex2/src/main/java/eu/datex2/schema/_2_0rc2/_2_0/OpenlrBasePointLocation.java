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
import java.math.BigInteger;

/**
 * <p>Java class for OpenlrBasePointLocation complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="OpenlrBasePointLocation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="openlrSideOfRoad" type="{http://datex2.eu/schema/2_0RC2/2_0}OpenlrSideOfRoadEnum"/>
 *         &lt;element name="openlrOrientation" type="{http://datex2.eu/schema/2_0RC2/2_0}OpenlrOrientationEnum"/>
 *         &lt;element name="openlrPositiveOffset" type="{http://datex2.eu/schema/2_0RC2/2_0}MetresAsNonNegativeInteger" minOccurs="0"/>
 *         &lt;element name="openlrLocationReferencePoint" type="{http://datex2.eu/schema/2_0RC2/2_0}OpenlrLocationReferencePoint"/>
 *         &lt;element name="openlrLastLocationReferencePoint" type="{http://datex2.eu/schema/2_0RC2/2_0}OpenlrLastLocationReferencePoint"/>
 *         &lt;element name="openlrBasePointLocationExtension" type="{http://datex2.eu/schema/2_0RC2/2_0}_ExtensionType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OpenlrBasePointLocation", propOrder = {
        "openlrSideOfRoad",
        "openlrOrientation",
        "openlrPositiveOffset",
        "openlrLocationReferencePoint",
        "openlrLastLocationReferencePoint",
        "openlrBasePointLocationExtension"
})
@XmlSeeAlso({
        OpenlrPoiWithAccessPoint.class,
        OpenlrPointAlongLine.class
})
public abstract class OpenlrBasePointLocation {

    /** The openlr side of road. */
    @XmlElement(required = true)
    protected OpenlrSideOfRoadEnum openlrSideOfRoad;

    /** The openlr orientation. */
    @XmlElement(required = true)
    protected OpenlrOrientationEnum openlrOrientation;

    /** The openlr positive offset. */
    protected BigInteger openlrPositiveOffset;

    /** The openlr location reference point. */
    @XmlElement(required = true)
    protected OpenlrLocationReferencePoint openlrLocationReferencePoint;

    /** The openlr last location reference point. */
    @XmlElement(required = true)
    protected OpenlrLastLocationReferencePoint openlrLastLocationReferencePoint;

    /** The openlr base point location extension. */
    protected ExtensionType openlrBasePointLocationExtension;

    /**
     * Gets the value of the openlrSideOfRoad property.
     *
     * @return the openlr side of road
     * possible object is
     * {@link OpenlrSideOfRoadEnum }
     */
    public final OpenlrSideOfRoadEnum getOpenlrSideOfRoad() {
        return openlrSideOfRoad;
    }

    /**
     * Sets the value of the openlrSideOfRoad property.
     *
     * @param value
     *     allowed object is
     *     {@link OpenlrSideOfRoadEnum }
     *
     */
    public final void setOpenlrSideOfRoad(final OpenlrSideOfRoadEnum value) {
        this.openlrSideOfRoad = value;
    }

    /**
     * Gets the value of the openlrOrientation property.
     *
     * @return the openlr orientation
     * possible object is
     * {@link OpenlrOrientationEnum }
     */
    public final OpenlrOrientationEnum getOpenlrOrientation() {
        return openlrOrientation;
    }

    /**
     * Sets the value of the openlrOrientation property.
     *
     * @param value
     *     allowed object is
     *     {@link OpenlrOrientationEnum }
     *
     */
    public final void setOpenlrOrientation(final OpenlrOrientationEnum value) {
        this.openlrOrientation = value;
    }

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
     * Gets the value of the openlrLocationReferencePoint property.
     *
     * @return the openlr location reference point
     * possible object is
     * {@link OpenlrLocationReferencePoint }
     */
    public final OpenlrLocationReferencePoint getOpenlrLocationReferencePoint() {
        return openlrLocationReferencePoint;
    }

    /**
     * Sets the value of the openlrLocationReferencePoint property.
     *
     * @param value
     *     allowed object is
     *     {@link OpenlrLocationReferencePoint }
     *
     */
    public final void setOpenlrLocationReferencePoint(final OpenlrLocationReferencePoint value) {
        this.openlrLocationReferencePoint = value;
    }

    /**
     * Gets the value of the openlrLastLocationReferencePoint property.
     *
     * @return the openlr last location reference point
     * possible object is
     * {@link OpenlrLastLocationReferencePoint }
     */
    public final OpenlrLastLocationReferencePoint getOpenlrLastLocationReferencePoint() {
        return openlrLastLocationReferencePoint;
    }

    /**
     * Sets the value of the openlrLastLocationReferencePoint property.
     *
     * @param value
     *     allowed object is
     *     {@link OpenlrLastLocationReferencePoint }
     *
     */
    public final void setOpenlrLastLocationReferencePoint(final OpenlrLastLocationReferencePoint value) {
        this.openlrLastLocationReferencePoint = value;
    }

    /**
     * Gets the value of the openlrBasePointLocationExtension property.
     *
     * @return the openlr base point location extension
     * possible object is
     * {@link ExtensionType }
     */
    public final ExtensionType getOpenlrBasePointLocationExtension() {
        return openlrBasePointLocationExtension;
    }

    /**
     * Sets the value of the openlrBasePointLocationExtension property.
     *
     * @param value
     *     allowed object is
     *     {@link ExtensionType }
     *
     */
    public final void setOpenlrBasePointLocationExtension(final ExtensionType value) {
        this.openlrBasePointLocationExtension = value;
    }

    /**
     * To string.
     *
     * @param toStringBuilder the to string builder
     */
    public void toString(final ToStringBuilder toStringBuilder) {
        OpenlrSideOfRoadEnum theOpenlrSideOfRoad;
        theOpenlrSideOfRoad = this.getOpenlrSideOfRoad();
        toStringBuilder.append("openlrSideOfRoad", theOpenlrSideOfRoad);
        OpenlrOrientationEnum theOpenlrOrientation;
        theOpenlrOrientation = this.getOpenlrOrientation();
        toStringBuilder.append("openlrOrientation", theOpenlrOrientation);
        BigInteger theOpenlrPositiveOffset;
        theOpenlrPositiveOffset = this.getOpenlrPositiveOffset();
        toStringBuilder.append("openlrPositiveOffset", theOpenlrPositiveOffset);
        OpenlrLocationReferencePoint theOpenlrLocationReferencePoint;
        theOpenlrLocationReferencePoint = this.getOpenlrLocationReferencePoint();
        toStringBuilder.append("openlrLocationReferencePoint", theOpenlrLocationReferencePoint);
        OpenlrLastLocationReferencePoint theOpenlrLastLocationReferencePoint;
        theOpenlrLastLocationReferencePoint = this.getOpenlrLastLocationReferencePoint();
        toStringBuilder.append("openlrLastLocationReferencePoint", theOpenlrLastLocationReferencePoint);
        ExtensionType theOpenlrBasePointLocationExtension;
        theOpenlrBasePointLocationExtension = this.getOpenlrBasePointLocationExtension();
        toStringBuilder.append("openlrBasePointLocationExtension", theOpenlrBasePointLocationExtension);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
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
    public void equals(final Object object, final EqualsBuilder equalsBuilder) {
        if (!(object instanceof OpenlrBasePointLocation)) {
            equalsBuilder.appendSuper(false);
            return;
        }
        if (this == object) {
            return;
        }
        final OpenlrBasePointLocation that = ((OpenlrBasePointLocation) object);
        equalsBuilder.append(this.getOpenlrSideOfRoad(), that.getOpenlrSideOfRoad());
        equalsBuilder.append(this.getOpenlrOrientation(), that.getOpenlrOrientation());
        equalsBuilder.append(this.getOpenlrPositiveOffset(), that.getOpenlrPositiveOffset());
        equalsBuilder.append(this.getOpenlrLocationReferencePoint(), that.getOpenlrLocationReferencePoint());
        equalsBuilder.append(this.getOpenlrLastLocationReferencePoint(), that.getOpenlrLastLocationReferencePoint());
        equalsBuilder.append(this.getOpenlrBasePointLocationExtension(), that.getOpenlrBasePointLocationExtension());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object object) {
        if (!(object instanceof OpenlrBasePointLocation)) {
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
    public void hashCode(final HashCodeBuilder hashCodeBuilder) {
        hashCodeBuilder.append(this.getOpenlrSideOfRoad());
        hashCodeBuilder.append(this.getOpenlrOrientation());
        hashCodeBuilder.append(this.getOpenlrPositiveOffset());
        hashCodeBuilder.append(this.getOpenlrLocationReferencePoint());
        hashCodeBuilder.append(this.getOpenlrLastLocationReferencePoint());
        hashCodeBuilder.append(this.getOpenlrBasePointLocationExtension());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
        hashCode(hashCodeBuilder);
        return hashCodeBuilder.toHashCode();
    }

}
