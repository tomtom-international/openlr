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

/**
 * <p>
 * Java class for OpenlrPointLocationReference complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="OpenlrPointLocationReference">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="openlrGeoCoordinate" type="{http://datex2.eu/schema/2_0RC2/2_0}OpenlrGeoCoordinate" minOccurs="0"/>
 *         &lt;element name="openlrPoiWithAccessPoint" type="{http://datex2.eu/schema/2_0RC2/2_0}OpenlrPoiWithAccessPoint" minOccurs="0"/>
 *         &lt;element name="openlrPointAlongLine" type="{http://datex2.eu/schema/2_0RC2/2_0}OpenlrPointAlongLine" minOccurs="0"/>
 *         &lt;element name="openlrPointLocationReferenceExtension" type="{http://datex2.eu/schema/2_0RC2/2_0}_ExtensionType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OpenlrPointLocationReference", propOrder = {
        "openlrGeoCoordinate", "openlrPoiWithAccessPoint",
        "openlrPointAlongLine", "openlrPointLocationReferenceExtension"})
public class OpenlrPointLocationReference {

    /** The openlr geo coordinate. */
    protected OpenlrGeoCoordinate openlrGeoCoordinate;

    /** The openlr poi with access point. */
    protected OpenlrPoiWithAccessPoint openlrPoiWithAccessPoint;

    /** The openlr point along line. */
    protected OpenlrPointAlongLine openlrPointAlongLine;

    /** The openlr point location reference extension. */
    protected ExtensionType openlrPointLocationReferenceExtension;

    /**
     * Gets the value of the openlrGeoCoordinate property.
     *
     * @return the openlr geo coordinate possible object is
     *         {@link OpenlrGeoCoordinate }
     */
    public final OpenlrGeoCoordinate getOpenlrGeoCoordinate() {
        return openlrGeoCoordinate;
    }

    /**
     * Sets the value of the openlrGeoCoordinate property.
     *
     * @param value
     *            allowed object is {@link OpenlrGeoCoordinate }
     *
     */
    public final void setOpenlrGeoCoordinate(final OpenlrGeoCoordinate value) {
        this.openlrGeoCoordinate = value;
    }

    /**
     * Gets the value of the openlrPoiWithAccessPoint property.
     *
     * @return the openlr poi with access point possible object is
     *         {@link OpenlrPoiWithAccessPoint }
     */
    public final OpenlrPoiWithAccessPoint getOpenlrPoiWithAccessPoint() {
        return openlrPoiWithAccessPoint;
    }

    /**
     * Sets the value of the openlrPoiWithAccessPoint property.
     *
     * @param value
     *            allowed object is {@link OpenlrPoiWithAccessPoint }
     *
     */
    public final void setOpenlrPoiWithAccessPoint(final OpenlrPoiWithAccessPoint value) {
        this.openlrPoiWithAccessPoint = value;
    }

    /**
     * Gets the value of the openlrPointAlongLine property.
     *
     * @return the openlr point along line possible object is
     *         {@link OpenlrPointAlongLine }
     */
    public final OpenlrPointAlongLine getOpenlrPointAlongLine() {
        return openlrPointAlongLine;
    }

    /**
     * Sets the value of the openlrPointAlongLine property.
     *
     * @param value
     *            allowed object is {@link OpenlrPointAlongLine }
     *
     */
    public final void setOpenlrPointAlongLine(final OpenlrPointAlongLine value) {
        this.openlrPointAlongLine = value;
    }

    /**
     * Gets the value of the openlrPointLocationReferenceExtension property.
     *
     * @return the openlr point location reference extension possible object is
     *         {@link ExtensionType }
     */
    public final ExtensionType getOpenlrPointLocationReferenceExtension() {
        return openlrPointLocationReferenceExtension;
    }

    /**
     * Sets the value of the openlrPointLocationReferenceExtension property.
     *
     * @param value
     *            allowed object is {@link ExtensionType }
     *
     */
    public final void setOpenlrPointLocationReferenceExtension(final ExtensionType value) {
        this.openlrPointLocationReferenceExtension = value;
    }

    /**
     * To string.
     *
     * @param toStringBuilder
     *            the to string builder
     */
    public final void toString(final ToStringBuilder toStringBuilder) {
        OpenlrGeoCoordinate theOpenlrGeoCoordinate;
        theOpenlrGeoCoordinate = this.getOpenlrGeoCoordinate();
        toStringBuilder.append("openlrGeoCoordinate",
                theOpenlrGeoCoordinate);
        OpenlrPoiWithAccessPoint theOpenlrPoiWithAccessPoint;
        theOpenlrPoiWithAccessPoint = this.getOpenlrPoiWithAccessPoint();
        toStringBuilder.append("openlrPoiWithAccessPoint",
                theOpenlrPoiWithAccessPoint);
        OpenlrPointAlongLine theOpenlrPointAlongLine;
        theOpenlrPointAlongLine = this.getOpenlrPointAlongLine();
        toStringBuilder.append("openlrPointAlongLine",
                theOpenlrPointAlongLine);
        ExtensionType theOpenlrPointLocationReferenceExtension;
        theOpenlrPointLocationReferenceExtension = this
                .getOpenlrPointLocationReferenceExtension();
        toStringBuilder.append("openlrPointLocationReferenceExtension",
                theOpenlrPointLocationReferenceExtension);
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
        if (!(object instanceof OpenlrPointLocationReference)) {
            equalsBuilder.appendSuper(false);
            return;
        }
        if (this == object) {
            return;
        }
        final OpenlrPointLocationReference that = ((OpenlrPointLocationReference) object);
        equalsBuilder.append(this.getOpenlrGeoCoordinate(),
                that.getOpenlrGeoCoordinate());
        equalsBuilder.append(this.getOpenlrPoiWithAccessPoint(),
                that.getOpenlrPoiWithAccessPoint());
        equalsBuilder.append(this.getOpenlrPointAlongLine(),
                that.getOpenlrPointAlongLine());
        equalsBuilder.append(this.getOpenlrPointLocationReferenceExtension(),
                that.getOpenlrPointLocationReferenceExtension());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object object) {
        if (!(object instanceof OpenlrPointLocationReference)) {
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
        hashCodeBuilder.append(this.getOpenlrGeoCoordinate());
        hashCodeBuilder.append(this.getOpenlrPoiWithAccessPoint());
        hashCodeBuilder.append(this.getOpenlrPointAlongLine());
        hashCodeBuilder.append(this.getOpenlrPointLocationReferenceExtension());
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
