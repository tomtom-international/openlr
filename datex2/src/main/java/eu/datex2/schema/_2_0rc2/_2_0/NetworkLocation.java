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
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;

/**
 * <p>Java class for NetworkLocation complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="NetworkLocation">
 *   &lt;complexContent>
 *     &lt;extension base="{http://datex2.eu/schema/2_0RC2/2_0}Location">
 *       &lt;sequence>
 *         &lt;element name="networkLocationExtension" type="{http://datex2.eu/schema/2_0RC2/2_0}_ExtensionType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NetworkLocation", propOrder = {
        "networkLocationExtension"
})
@XmlSeeAlso({
        Linear.class,
        Point.class
})
public abstract class NetworkLocation
        extends Location {

    /** The network location extension. */
    protected ExtensionType networkLocationExtension;

    /**
     * Gets the value of the networkLocationExtension property.
     *
     * @return the network location extension
     * possible object is
     * {@link ExtensionType }
     */
    public final ExtensionType getNetworkLocationExtension() {
        return networkLocationExtension;
    }

    /**
     * Sets the value of the networkLocationExtension property.
     *
     * @param value
     *     allowed object is
     *     {@link ExtensionType }
     *
     */
    public final void setNetworkLocationExtension(final ExtensionType value) {
        this.networkLocationExtension = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void toString(final ToStringBuilder toStringBuilder) {
        super.toString(toStringBuilder);
        ExtensionType theNetworkLocationExtension;
        theNetworkLocationExtension = this.getNetworkLocationExtension();
        toStringBuilder.append("networkLocationExtension", theNetworkLocationExtension);
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
     * {@inheritDoc}
     */
    @Override
    public void equals(final Object object, final EqualsBuilder equalsBuilder) {
        if (!(object instanceof NetworkLocation)) {
            equalsBuilder.appendSuper(false);
            return;
        }
        if (this == object) {
            return;
        }
        super.equals(object, equalsBuilder);
        final NetworkLocation that = ((NetworkLocation) object);
        equalsBuilder.append(this.getNetworkLocationExtension(), that.getNetworkLocationExtension());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object object) {
        if (!(object instanceof NetworkLocation)) {
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
    public void hashCode(final HashCodeBuilder hashCodeBuilder) {
        super.hashCode(hashCodeBuilder);
        hashCodeBuilder.append(this.getNetworkLocationExtension());
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
