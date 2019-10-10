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

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Java class for SituationRecord complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="SituationRecord">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="situationRecordCreationTime" type="{http://datex2.eu/schema/2_0RC2/2_0}DateTime"/>
 *         &lt;element name="situationRecordVersionTime" type="{http://datex2.eu/schema/2_0RC2/2_0}DateTime"/>
 *         &lt;element name="probabilityOfOccurrence" type="{http://datex2.eu/schema/2_0RC2/2_0}ProbabilityOfOccurrenceEnum"/>
 *         &lt;element name="validity" type="{http://datex2.eu/schema/2_0RC2/2_0}Validity"/>
 *         &lt;element name="groupOfLocations" type="{http://datex2.eu/schema/2_0RC2/2_0}GroupOfLocations"/>
 *         &lt;element name="situationRecordExtension" type="{http://datex2.eu/schema/2_0RC2/2_0}_ExtensionType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="version" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SituationRecord", propOrder = {"situationRecordCreationTime",
        "situationRecordVersionTime", "probabilityOfOccurrence", "validity",
        "groupOfLocations", "situationRecordExtension"})
@XmlSeeAlso({TrafficElement.class})
public abstract class SituationRecord {

    /** The situation record creation time. */
    @XmlElement(required = true)
    protected XMLGregorianCalendar situationRecordCreationTime;

    /** The situation record version time. */
    @XmlElement(required = true)
    protected XMLGregorianCalendar situationRecordVersionTime;

    /** The probability of occurrence. */
    @XmlElement(required = true)
    protected ProbabilityOfOccurrenceEnum probabilityOfOccurrence;

    /** The validity. */
    @XmlElement(required = true)
    protected Validity validity;

    /** The group of locations. */
    @XmlElement(required = true)
    protected GroupOfLocations groupOfLocations;

    /** The situation record extension. */
    protected ExtensionType situationRecordExtension;

    /** The id. */
    @XmlAttribute(name = "id", required = true)
    protected String id;

    /** The version. */
    @XmlAttribute(name = "version", required = true)
    protected String version;

    /**
     * Gets the value of the situationRecordCreationTime property.
     *
     * @return possible object is {@link XMLGregorianCalendar }
     *
     */
    public final XMLGregorianCalendar getSituationRecordCreationTime() {
        return situationRecordCreationTime;
    }

    /**
     * Sets the value of the situationRecordCreationTime property.
     *
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     *
     */
    public final void setSituationRecordCreationTime(final XMLGregorianCalendar value) {
        this.situationRecordCreationTime = value;
    }

    /**
     * Gets the value of the situationRecordVersionTime property.
     *
     * @return possible object is {@link XMLGregorianCalendar }
     *
     */
    public final XMLGregorianCalendar getSituationRecordVersionTime() {
        return situationRecordVersionTime;
    }

    /**
     * Sets the value of the situationRecordVersionTime property.
     *
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     *
     */
    public final void setSituationRecordVersionTime(final XMLGregorianCalendar value) {
        this.situationRecordVersionTime = value;
    }

    /**
     * Gets the value of the probabilityOfOccurrence property.
     *
     * @return possible object is {@link ProbabilityOfOccurrenceEnum }
     *
     */
    public final ProbabilityOfOccurrenceEnum getProbabilityOfOccurrence() {
        return probabilityOfOccurrence;
    }

    /**
     * Sets the value of the probabilityOfOccurrence property.
     *
     * @param value
     *            allowed object is {@link ProbabilityOfOccurrenceEnum }
     *
     */
    public final void setProbabilityOfOccurrence(final ProbabilityOfOccurrenceEnum value) {
        this.probabilityOfOccurrence = value;
    }

    /**
     * Gets the value of the validity property.
     *
     * @return possible object is {@link Validity }
     *
     */
    public final Validity getValidity() {
        return validity;
    }

    /**
     * Sets the value of the validity property.
     *
     * @param value
     *            allowed object is {@link Validity }
     *
     */
    public final void setValidity(final Validity value) {
        this.validity = value;
    }

    /**
     * Gets the value of the groupOfLocations property.
     *
     * @return possible object is {@link GroupOfLocations }
     *
     */
    public final GroupOfLocations getGroupOfLocations() {
        return groupOfLocations;
    }

    /**
     * Sets the value of the groupOfLocations property.
     *
     * @param value
     *            allowed object is {@link GroupOfLocations }
     *
     */
    public final void setGroupOfLocations(final GroupOfLocations value) {
        this.groupOfLocations = value;
    }

    /**
     * Gets the value of the situationRecordExtension property.
     *
     * @return possible object is {@link ExtensionType }
     *
     */
    public final ExtensionType getSituationRecordExtension() {
        return situationRecordExtension;
    }

    /**
     * Sets the value of the situationRecordExtension property.
     *
     * @param value
     *            allowed object is {@link ExtensionType }
     *
     */
    public final void setSituationRecordExtension(final ExtensionType value) {
        this.situationRecordExtension = value;
    }

    /**
     * Gets the value of the id property.
     *
     * @return possible object is {@link String }
     *
     */
    public final String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     * @param value
     *            allowed object is {@link String }
     *
     */
    public final void setId(final String value) {
        this.id = value;
    }

    /**
     * Gets the value of the version property.
     *
     * @return possible object is {@link String }
     *
     */
    public final String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     *
     * @param value
     *            allowed object is {@link String }
     *
     */
    public final void setVersion(final String value) {
        this.version = value;
    }

    /**
     * To string.
     *
     * @param toStringBuilder
     *            the to string builder
     */
    public void toString(final ToStringBuilder toStringBuilder) {
        XMLGregorianCalendar theSituationRecordCreationTime;
        theSituationRecordCreationTime = this
                .getSituationRecordCreationTime();
        toStringBuilder.append("situationRecordCreationTime",
                theSituationRecordCreationTime);
        XMLGregorianCalendar theSituationRecordVersionTime;
        theSituationRecordVersionTime = this
                .getSituationRecordVersionTime();
        toStringBuilder.append("situationRecordVersionTime",
                theSituationRecordVersionTime);
        ProbabilityOfOccurrenceEnum theProbabilityOfOccurrence;
        theProbabilityOfOccurrence = this.getProbabilityOfOccurrence();
        toStringBuilder.append("probabilityOfOccurrence",
                theProbabilityOfOccurrence);
        Validity theValidity;
        theValidity = this.getValidity();
        toStringBuilder.append("validity", theValidity);
        GroupOfLocations theGroupOfLocations;
        theGroupOfLocations = this.getGroupOfLocations();
        toStringBuilder.append("groupOfLocations", theGroupOfLocations);
        ExtensionType theSituationRecordExtension;
        theSituationRecordExtension = this.getSituationRecordExtension();
        toStringBuilder.append("situationRecordExtension",
                theSituationRecordExtension);
        String theId;
        theId = this.getId();
        toStringBuilder.append("id", theId);
        String theVersion;
        theVersion = this.getVersion();
        toStringBuilder.append("version", theVersion);
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
     * @param object
     *            the object
     * @param equalsBuilder
     *            the equals builder
     */
    public void equals(final Object object, final EqualsBuilder equalsBuilder) {
        if (!(object instanceof SituationRecord)) {
            equalsBuilder.appendSuper(false);
            return;
        }
        if (this == object) {
            return;
        }
        final SituationRecord that = ((SituationRecord) object);
        equalsBuilder.append(this.getSituationRecordCreationTime(),
                that.getSituationRecordCreationTime());
        equalsBuilder.append(this.getSituationRecordVersionTime(),
                that.getSituationRecordVersionTime());
        equalsBuilder.append(this.getProbabilityOfOccurrence(),
                that.getProbabilityOfOccurrence());
        equalsBuilder.append(this.getValidity(), that.getValidity());
        equalsBuilder.append(this.getGroupOfLocations(),
                that.getGroupOfLocations());
        equalsBuilder.append(this.getSituationRecordExtension(),
                that.getSituationRecordExtension());
        equalsBuilder.append(this.getId(), that.getId());
        equalsBuilder.append(this.getVersion(), that.getVersion());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object object) {
        if (!(object instanceof SituationRecord)) {
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
    public void hashCode(final HashCodeBuilder hashCodeBuilder) {
        hashCodeBuilder.append(this.getSituationRecordCreationTime());
        hashCodeBuilder.append(this.getSituationRecordVersionTime());
        hashCodeBuilder.append(this.getProbabilityOfOccurrence());
        hashCodeBuilder.append(this.getValidity());
        hashCodeBuilder.append(this.getGroupOfLocations());
        hashCodeBuilder.append(this.getSituationRecordExtension());
        hashCodeBuilder.append(this.getId());
        hashCodeBuilder.append(this.getVersion());
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
