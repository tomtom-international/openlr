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
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Java class for SituationPublication complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="SituationPublication">
 *   &lt;complexContent>
 *     &lt;extension base="{http://datex2.eu/schema/2_0RC2/2_0}PayloadPublication">
 *       &lt;sequence>
 *         &lt;element name="situation" type="{http://datex2.eu/schema/2_0RC2/2_0}Situation" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="situationPublicationExtension" type="{http://datex2.eu/schema/2_0RC2/2_0}_ExtensionType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SituationPublication", propOrder = {"situation",
        "situationPublicationExtension"})
public class SituationPublication extends PayloadPublication {

    /** The situation. */
    protected List<Situation> situation;

    /** The situation publication extension. */
    protected ExtensionType situationPublicationExtension;

    /**
     * Gets the value of the situation property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the situation property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     *
     * <pre>
     * getSituation().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the situation {@link Situation }
     */
    public final List<Situation> getSituation() {
        if (situation == null) {
            situation = new ArrayList<Situation>();
        }
        return this.situation;
    }

    /**
     * Gets the value of the situationPublicationExtension property.
     *
     * @return the situation publication extension possible object is
     *         {@link ExtensionType }
     */
    public final ExtensionType getSituationPublicationExtension() {
        return situationPublicationExtension;
    }

    /**
     * Sets the value of the situationPublicationExtension property.
     *
     * @param value
     *            allowed object is {@link ExtensionType }
     *
     */
    public final void setSituationPublicationExtension(final ExtensionType value) {
        this.situationPublicationExtension = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void toString(final ToStringBuilder toStringBuilder) {
        super.toString(toStringBuilder);
        List<Situation> theSituation;
        theSituation = this.getSituation();
        toStringBuilder.append("situation", theSituation);
        ExtensionType theSituationPublicationExtension;
        theSituationPublicationExtension = this
                .getSituationPublicationExtension();
        toStringBuilder.append("situationPublicationExtension",
                theSituationPublicationExtension);
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
        if (!(object instanceof SituationPublication)) {
            equalsBuilder.appendSuper(false);
            return;
        }
        if (this == object) {
            return;
        }
        super.equals(object, equalsBuilder);
        final SituationPublication that = ((SituationPublication) object);
        equalsBuilder.append(this.getSituation(), that.getSituation());
        equalsBuilder.append(this.getSituationPublicationExtension(),
                that.getSituationPublicationExtension());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object object) {
        if (!(object instanceof SituationPublication)) {
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
        hashCodeBuilder.append(this.getSituation());
        hashCodeBuilder.append(this.getSituationPublicationExtension());
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
