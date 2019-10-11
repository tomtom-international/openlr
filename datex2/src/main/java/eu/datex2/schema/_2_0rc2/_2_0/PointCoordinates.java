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
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for PointCoordinates complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="PointCoordinates">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="latitude" type="{http://datex2.eu/schema/2_0RC2/2_0}Float"/>
 *         &lt;element name="longitude" type="{http://datex2.eu/schema/2_0RC2/2_0}Float"/>
 *         &lt;element name="pointCoordinatesExtension" type="{http://datex2.eu/schema/2_0RC2/2_0}_ExtensionType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PointCoordinates", propOrder = {"latitude", "longitude",
        "pointCoordinatesExtension"})
public class PointCoordinates {

    /** The latitude. */
    protected float latitude;

    /** The longitude. */
    protected float longitude;

    /** The point coordinates extension. */
    protected ExtensionType pointCoordinatesExtension;

    /**
     * Gets the value of the latitude property.
     *
     * @return the latitude
     */
    public final float getLatitude() {
        return latitude;
    }

    /**
     * Sets the value of the latitude property.
     *
     * @param value
     *            the new latitude
     */
    public final void setLatitude(final float value) {
        this.latitude = value;
    }

    /**
     * Gets the value of the longitude property.
     *
     * @return the longitude
     */
    public final float getLongitude() {
        return longitude;
    }

    /**
     * Sets the value of the longitude property.
     *
     * @param value
     *            the new longitude
     */
    public final void setLongitude(final float value) {
        this.longitude = value;
    }

    /**
     * Gets the value of the pointCoordinatesExtension property.
     *
     * @return the point coordinates extension possible object is
     *         {@link ExtensionType }
     */
    public final ExtensionType getPointCoordinatesExtension() {
        return pointCoordinatesExtension;
    }

    /**
     * Sets the value of the pointCoordinatesExtension property.
     *
     * @param value
     *            allowed object is {@link ExtensionType }
     *
     */
    public final void setPointCoordinatesExtension(final ExtensionType value) {
        this.pointCoordinatesExtension = value;
    }

    /**
     * To string.
     *
     * @param toStringBuilder
     *            the to string builder
     */
    public final void toString(final ToStringBuilder toStringBuilder) {
        float theLatitude;
        theLatitude = this.getLatitude();
        toStringBuilder.append("latitude", theLatitude);
        float theLongitude;
        theLongitude = this.getLongitude();
        toStringBuilder.append("longitude", theLongitude);
        ExtensionType thePointCoordinatesExtension;
        thePointCoordinatesExtension = this.getPointCoordinatesExtension();
        toStringBuilder.append("pointCoordinatesExtension",
                thePointCoordinatesExtension);
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
    public final void equals(final Object object,
                             final EqualsBuilder equalsBuilder) {
        if (!(object instanceof PointCoordinates)) {
            equalsBuilder.appendSuper(false);
            return;
        }
        if (this == object) {
            return;
        }
        final PointCoordinates that = ((PointCoordinates) object);
        equalsBuilder.append(this.getLatitude(), that.getLatitude());
        equalsBuilder.append(this.getLongitude(), that.getLongitude());
        equalsBuilder.append(this.getPointCoordinatesExtension(),
                that.getPointCoordinatesExtension());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object object) {
        if (!(object instanceof PointCoordinates)) {
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
        hashCodeBuilder.append(this.getLatitude());
        hashCodeBuilder.append(this.getLongitude());
        hashCodeBuilder.append(this.getPointCoordinatesExtension());
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
