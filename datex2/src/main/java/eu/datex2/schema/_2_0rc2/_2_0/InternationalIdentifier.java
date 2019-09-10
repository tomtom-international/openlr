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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for InternationalIdentifier complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="InternationalIdentifier">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="country" type="{http://datex2.eu/schema/2_0RC2/2_0}CountryEnum"/>
 *         &lt;element name="nationalIdentifier" type="{http://datex2.eu/schema/2_0RC2/2_0}String"/>
 *         &lt;element name="internationalIdentifierExtension" type="{http://datex2.eu/schema/2_0RC2/2_0}_ExtensionType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InternationalIdentifier", propOrder = {"country",
        "nationalIdentifier", "internationalIdentifierExtension"})
public class InternationalIdentifier {

    /** The country. */
    @XmlElement(required = true)
    protected CountryEnum country;

    /** The national identifier. */
    @XmlElement(required = true)
    protected String nationalIdentifier;

    /** The international identifier extension. */
    protected ExtensionType internationalIdentifierExtension;

    /**
     * Gets the value of the country property.
     *
     * @return possible object is {@link CountryEnum }
     *
     */
    public final CountryEnum getCountry() {
        return country;
    }

    /**
     * Sets the value of the country property.
     *
     * @param value
     *            allowed object is {@link CountryEnum }
     *
     */
    public final void setCountry(final CountryEnum value) {
        this.country = value;
    }

    /**
     * Gets the value of the nationalIdentifier property.
     *
     * @return possible object is {@link String }
     *
     */
    public final String getNationalIdentifier() {
        return nationalIdentifier;
    }

    /**
     * Sets the value of the nationalIdentifier property.
     *
     * @param value
     *            allowed object is {@link String }
     *
     */
    public final void setNationalIdentifier(final String value) {
        this.nationalIdentifier = value;
    }

    /**
     * Gets the value of the internationalIdentifierExtension property.
     *
     * @return possible object is {@link ExtensionType }
     *
     */
    public final ExtensionType getInternationalIdentifierExtension() {
        return internationalIdentifierExtension;
    }

    /**
     * Sets the value of the internationalIdentifierExtension property.
     *
     * @param value
     *            allowed object is {@link ExtensionType }
     *
     */
    public final void setInternationalIdentifierExtension(final ExtensionType value) {
        this.internationalIdentifierExtension = value;
    }

    /**
     * To string.
     *
     * @param toStringBuilder the to string builder
     */
    public final void toString(final ToStringBuilder toStringBuilder) {
        CountryEnum theCountry;
        theCountry = this.getCountry();
        toStringBuilder.append("country", theCountry);
        String theNationalIdentifier;
        theNationalIdentifier = this.getNationalIdentifier();
        toStringBuilder.append("nationalIdentifier", theNationalIdentifier);
        ExtensionType theInternationalIdentifierExtension;
        theInternationalIdentifierExtension = this
                .getInternationalIdentifierExtension();
        toStringBuilder.append("internationalIdentifierExtension",
                theInternationalIdentifierExtension);
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
        if (!(object instanceof InternationalIdentifier)) {
            equalsBuilder.appendSuper(false);
            return;
        }
        if (this == object) {
            return;
        }
        final InternationalIdentifier that = ((InternationalIdentifier) object);
        equalsBuilder.append(this.getCountry(), that.getCountry());
        equalsBuilder.append(this.getNationalIdentifier(),
                that.getNationalIdentifier());
        equalsBuilder.append(this.getInternationalIdentifierExtension(),
                that.getInternationalIdentifierExtension());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object object) {
        if (!(object instanceof InternationalIdentifier)) {
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
        hashCodeBuilder.append(this.getCountry());
        hashCodeBuilder.append(this.getNationalIdentifier());
        hashCodeBuilder.append(this.getInternationalIdentifierExtension());
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
