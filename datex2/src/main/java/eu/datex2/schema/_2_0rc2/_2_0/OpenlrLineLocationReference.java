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
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Java class for OpenlrLineLocationReference complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="OpenlrLineLocationReference">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="openlrLocationReferencePoint" type="{http://datex2.eu/schema/2_0RC2/2_0}OpenlrLocationReferencePoint" maxOccurs="unbounded"/>
 *         &lt;element name="openlrLastLocationReferencePoint" type="{http://datex2.eu/schema/2_0RC2/2_0}OpenlrLastLocationReferencePoint"/>
 *         &lt;element name="openlrOffsets" type="{http://datex2.eu/schema/2_0RC2/2_0}OpenlrOffsets" minOccurs="0"/>
 *         &lt;element name="openlrLineLocationReferenceExtension" type="{http://datex2.eu/schema/2_0RC2/2_0}_ExtensionType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OpenlrLineLocationReference", propOrder = {
        "openlrLocationReferencePoint",
        "openlrLastLocationReferencePoint",
        "openlrOffsets",
        "openlrLineLocationReferenceExtension"
})
public class OpenlrLineLocationReference {

    /** The openlr location reference point. */
    @XmlElement(required = true)
    protected List<OpenlrLocationReferencePoint> openlrLocationReferencePoint;

    /** The openlr last location reference point. */
    @XmlElement(required = true)
    protected OpenlrLastLocationReferencePoint openlrLastLocationReferencePoint;

    /** The openlr offsets. */
    protected OpenlrOffsets openlrOffsets;

    /** The openlr line location reference extension. */
    protected ExtensionType openlrLineLocationReferenceExtension;

    /**
     * Gets the value of the openlrLocationReferencePoint property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the openlrLocationReferencePoint property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     * getOpenlrLocationReferencePoint().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the openlr location reference point
     * {@link OpenlrLocationReferencePoint }
     */
    public final List<OpenlrLocationReferencePoint> getOpenlrLocationReferencePoint() {
        if (openlrLocationReferencePoint == null) {
            openlrLocationReferencePoint = new ArrayList<OpenlrLocationReferencePoint>();
        }
        return this.openlrLocationReferencePoint;
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
     * Gets the value of the openlrOffsets property.
     *
     * @return the openlr offsets
     * possible object is
     * {@link OpenlrOffsets }
     */
    public final OpenlrOffsets getOpenlrOffsets() {
        return openlrOffsets;
    }

    /**
     * Sets the value of the openlrOffsets property.
     *
     * @param value
     *     allowed object is
     *     {@link OpenlrOffsets }
     *
     */
    public final void setOpenlrOffsets(final OpenlrOffsets value) {
        this.openlrOffsets = value;
    }

    /**
     * Gets the value of the openlrLineLocationReferenceExtension property.
     *
     * @return the openlr line location reference extension
     * possible object is
     * {@link ExtensionType }
     */
    public final ExtensionType getOpenlrLineLocationReferenceExtension() {
        return openlrLineLocationReferenceExtension;
    }

    /**
     * Sets the value of the openlrLineLocationReferenceExtension property.
     *
     * @param value
     *     allowed object is
     *     {@link ExtensionType }
     *
     */
    public final void setOpenlrLineLocationReferenceExtension(final ExtensionType value) {
        this.openlrLineLocationReferenceExtension = value;
    }

    /**
     * To string.
     *
     * @param toStringBuilder the to string builder
     */
    public final void toString(final ToStringBuilder toStringBuilder) {
        List<OpenlrLocationReferencePoint> theOpenlrLocationReferencePoint;
        theOpenlrLocationReferencePoint = this.getOpenlrLocationReferencePoint();
        toStringBuilder.append("openlrLocationReferencePoint", theOpenlrLocationReferencePoint);
        OpenlrLastLocationReferencePoint theOpenlrLastLocationReferencePoint;
        theOpenlrLastLocationReferencePoint = this.getOpenlrLastLocationReferencePoint();
        toStringBuilder.append("openlrLastLocationReferencePoint", theOpenlrLastLocationReferencePoint);
        OpenlrOffsets theOpenlrOffsets;
        theOpenlrOffsets = this.getOpenlrOffsets();
        toStringBuilder.append("openlrOffsets", theOpenlrOffsets);
        ExtensionType theOpenlrLineLocationReferenceExtension;
        theOpenlrLineLocationReferenceExtension = this.getOpenlrLineLocationReferenceExtension();
        toStringBuilder.append("openlrLineLocationReferenceExtension", theOpenlrLineLocationReferenceExtension);
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
        if (!(object instanceof OpenlrLineLocationReference)) {
            equalsBuilder.appendSuper(false);
            return;
        }
        if (this == object) {
            return;
        }
        final OpenlrLineLocationReference that = ((OpenlrLineLocationReference) object);
        equalsBuilder.append(this.getOpenlrLocationReferencePoint(), that.getOpenlrLocationReferencePoint());
        equalsBuilder.append(this.getOpenlrLastLocationReferencePoint(), that.getOpenlrLastLocationReferencePoint());
        equalsBuilder.append(this.getOpenlrOffsets(), that.getOpenlrOffsets());
        equalsBuilder.append(this.getOpenlrLineLocationReferenceExtension(), that.getOpenlrLineLocationReferenceExtension());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object object) {
        if (!(object instanceof OpenlrLineLocationReference)) {
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
        hashCodeBuilder.append(this.getOpenlrLocationReferencePoint());
        hashCodeBuilder.append(this.getOpenlrLastLocationReferencePoint());
        hashCodeBuilder.append(this.getOpenlrOffsets());
        hashCodeBuilder.append(this.getOpenlrLineLocationReferenceExtension());
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
