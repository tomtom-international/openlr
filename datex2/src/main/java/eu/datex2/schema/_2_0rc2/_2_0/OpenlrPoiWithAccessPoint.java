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
 * <p>
 * Java class for OpenlrPoiWithAccessPoint complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="OpenlrPoiWithAccessPoint">
 *   &lt;complexContent>
 *     &lt;extension base="{http://datex2.eu/schema/2_0RC2/2_0}OpenlrBasePointLocation">
 *       &lt;sequence>
 *         &lt;element name="openlrCoordinate" type="{http://datex2.eu/schema/2_0RC2/2_0}PointCoordinates"/>
 *         &lt;element name="openlrPoiWithAccessPointExtension" type="{http://datex2.eu/schema/2_0RC2/2_0}_ExtensionType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OpenlrPoiWithAccessPoint", propOrder = {"openlrCoordinate",
        "openlrPoiWithAccessPointExtension"})
public class OpenlrPoiWithAccessPoint extends OpenlrBasePointLocation {

    /** The openlr coordinate. */
    @XmlElement(required = true)
    protected PointCoordinates openlrCoordinate;

    /** The openlr poi with access point extension. */
    protected ExtensionType openlrPoiWithAccessPointExtension;

    /**
     * Gets the value of the openlrCoordinate property.
     *
     * @return the openlr coordinate possible object is {@link PointCoordinates }
     */
    public final PointCoordinates getOpenlrCoordinate() {
        return openlrCoordinate;
    }

    /**
     * Sets the value of the openlrCoordinate property.
     *
     * @param value
     *            allowed object is {@link PointCoordinates }
     *
     */
    public final void setOpenlrCoordinate(final PointCoordinates value) {
        this.openlrCoordinate = value;
    }

    /**
     * Gets the value of the openlrPoiWithAccessPointExtension property.
     *
     * @return the openlr poi with access point extension possible object is
     *         {@link ExtensionType }
     */
    public final ExtensionType getOpenlrPoiWithAccessPointExtension() {
        return openlrPoiWithAccessPointExtension;
    }

    /**
     * Sets the value of the openlrPoiWithAccessPointExtension property.
     *
     * @param value
     *            allowed object is {@link ExtensionType }
     *
     */
    public final void setOpenlrPoiWithAccessPointExtension(final ExtensionType value) {
        this.openlrPoiWithAccessPointExtension = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void toString(final ToStringBuilder toStringBuilder) {
        super.toString(toStringBuilder);
        PointCoordinates theOpenlrCoordinate;
        theOpenlrCoordinate = this.getOpenlrCoordinate();
        toStringBuilder.append("openlrCoordinate", theOpenlrCoordinate);
        ExtensionType theOpenlrPoiWithAccessPointExtension;
        theOpenlrPoiWithAccessPointExtension = this
                .getOpenlrPoiWithAccessPointExtension();
        toStringBuilder.append("openlrPoiWithAccessPointExtension",
                theOpenlrPoiWithAccessPointExtension);
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
     * {@inheritDoc}
     */
    @Override
    public final void equals(final Object object, final EqualsBuilder equalsBuilder) {
        if (!(object instanceof OpenlrPoiWithAccessPoint)) {
            equalsBuilder.appendSuper(false);
            return;
        }
        if (this == object) {
            return;
        }
        super.equals(object, equalsBuilder);
        final OpenlrPoiWithAccessPoint that = ((OpenlrPoiWithAccessPoint) object);
        equalsBuilder.append(this.getOpenlrCoordinate(),
                that.getOpenlrCoordinate());
        equalsBuilder.append(this.getOpenlrPoiWithAccessPointExtension(),
                that.getOpenlrPoiWithAccessPointExtension());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object object) {
        if (!(object instanceof OpenlrPoiWithAccessPoint)) {
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
     * {@inheritDoc}
     */
    @Override
    public final void hashCode(final HashCodeBuilder hashCodeBuilder) {
        super.hashCode(hashCodeBuilder);
        hashCodeBuilder.append(this.getOpenlrCoordinate());
        hashCodeBuilder.append(this.getOpenlrPoiWithAccessPointExtension());
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
