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
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Java class for OverallPeriod complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="OverallPeriod">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="overallStartTime" type="{http://datex2.eu/schema/2_0RC2/2_0}DateTime"/>
 *         &lt;element name="overallPeriodExtension" type="{http://datex2.eu/schema/2_0RC2/2_0}_ExtensionType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OverallPeriod", propOrder = {"overallStartTime",
        "overallPeriodExtension"})
public class OverallPeriod {

    /** The overall start time. */
    @XmlElement(required = true)
    protected XMLGregorianCalendar overallStartTime;

    /** The overall period extension. */
    protected ExtensionType overallPeriodExtension;

    /**
     * Gets the value of the overallStartTime property.
     *
     * @return the overall start time possible object is
     *         {@link XMLGregorianCalendar }
     */
    public final XMLGregorianCalendar getOverallStartTime() {
        return overallStartTime;
    }

    /**
     * Sets the value of the overallStartTime property.
     *
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     *
     */
    public final void setOverallStartTime(final XMLGregorianCalendar value) {
        this.overallStartTime = value;
    }

    /**
     * Gets the value of the overallPeriodExtension property.
     *
     * @return the overall period extension possible object is
     *         {@link ExtensionType }
     */
    public final ExtensionType getOverallPeriodExtension() {
        return overallPeriodExtension;
    }

    /**
     * Sets the value of the overallPeriodExtension property.
     *
     * @param value
     *            allowed object is {@link ExtensionType }
     *
     */
    public final void setOverallPeriodExtension(final ExtensionType value) {
        this.overallPeriodExtension = value;
    }

    /**
     * To string.
     *
     * @param toStringBuilder
     *            the to string builder
     */
    public final void toString(final ToStringBuilder toStringBuilder) {
        XMLGregorianCalendar theOverallStartTime;
        theOverallStartTime = this.getOverallStartTime();
        toStringBuilder.append("overallStartTime", theOverallStartTime);
        ExtensionType theOverallPeriodExtension;
        theOverallPeriodExtension = this.getOverallPeriodExtension();
        toStringBuilder.append("overallPeriodExtension",
                theOverallPeriodExtension);
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
        if (!(object instanceof OverallPeriod)) {
            equalsBuilder.appendSuper(false);
            return;
        }
        if (this == object) {
            return;
        }
        final OverallPeriod that = ((OverallPeriod) object);
        equalsBuilder.append(this.getOverallStartTime(),
                that.getOverallStartTime());
        equalsBuilder.append(this.getOverallPeriodExtension(),
                that.getOverallPeriodExtension());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object object) {
        if (!(object instanceof OverallPeriod)) {
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
        hashCodeBuilder.append(this.getOverallStartTime());
        hashCodeBuilder.append(this.getOverallPeriodExtension());
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
