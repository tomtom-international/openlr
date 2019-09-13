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

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


// TODO: Auto-generated Javadoc

/**
 * <p>Java class for Situation complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="Situation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="headerInformation" type="{http://datex2.eu/schema/2_0RC2/2_0}HeaderInformation"/>
 *         &lt;element name="situationRecord" type="{http://datex2.eu/schema/2_0RC2/2_0}SituationRecord" maxOccurs="unbounded"/>
 *         &lt;element name="situationExtension" type="{http://datex2.eu/schema/2_0RC2/2_0}_ExtensionType" minOccurs="0"/>
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
@XmlType(name = "Situation", propOrder = {
        "headerInformation",
        "situationRecord",
        "situationExtension"
})
public class Situation {

    /** The header information. */
    @XmlElement(required = true)
    protected HeaderInformation headerInformation;

    /** The situation record. */
    @XmlElement(required = true)
    protected List<SituationRecord> situationRecord;

    /** The situation extension. */
    protected ExtensionType situationExtension;

    /** The id. */
    @XmlAttribute(name = "id", required = true)
    protected String id;

    /** The version. */
    @XmlAttribute(name = "version", required = true)
    protected String version;

    /**
     * Gets the value of the headerInformation property.
     *
     * @return the header information
     * possible object is
     * {@link HeaderInformation }
     */
    public HeaderInformation getHeaderInformation() {
        return headerInformation;
    }

    /**
     * Sets the value of the headerInformation property.
     *
     * @param value
     *     allowed object is
     *     {@link HeaderInformation }
     *
     */
    public void setHeaderInformation(HeaderInformation value) {
        this.headerInformation = value;
    }

    /**
     * Gets the value of the situationRecord property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the situationRecord property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     * getSituationRecord().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the situation record
     * {@link SituationRecord }
     */
    public List<SituationRecord> getSituationRecord() {
        if (situationRecord == null) {
            situationRecord = new ArrayList<SituationRecord>();
        }
        return this.situationRecord;
    }

    /**
     * Gets the value of the situationExtension property.
     *
     * @return the situation extension
     * possible object is
     * {@link ExtensionType }
     */
    public ExtensionType getSituationExtension() {
        return situationExtension;
    }

    /**
     * Sets the value of the situationExtension property.
     *
     * @param value
     *     allowed object is
     *     {@link ExtensionType }
     *
     */
    public void setSituationExtension(ExtensionType value) {
        this.situationExtension = value;
    }

    /**
     * Gets the value of the id property.
     *
     * @return the id
     * possible object is
     * {@link String }
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the version property.
     *
     * @return the version
     * possible object is
     * {@link String }
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * To string.
     *
     * @param toStringBuilder the to string builder
     */
    public void toString(ToStringBuilder toStringBuilder) {
        {
            HeaderInformation theHeaderInformation;
            theHeaderInformation = this.getHeaderInformation();
            toStringBuilder.append("headerInformation", theHeaderInformation);
        }
        {
            List<SituationRecord> theSituationRecord;
            theSituationRecord = this.getSituationRecord();
            toStringBuilder.append("situationRecord", theSituationRecord);
        }
        {
            ExtensionType theSituationExtension;
            theSituationExtension = this.getSituationExtension();
            toStringBuilder.append("situationExtension", theSituationExtension);
        }
        {
            String theId;
            theId = this.getId();
            toStringBuilder.append("id", theId);
        }
        {
            String theVersion;
            theVersion = this.getVersion();
            toStringBuilder.append("version", theVersion);
        }
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
    public void equals(Object object, EqualsBuilder equalsBuilder) {
        if (!(object instanceof Situation)) {
            equalsBuilder.appendSuper(false);
            return;
        }
        if (this == object) {
            return;
        }
        final Situation that = ((Situation) object);
        equalsBuilder.append(this.getHeaderInformation(), that.getHeaderInformation());
        equalsBuilder.append(this.getSituationRecord(), that.getSituationRecord());
        equalsBuilder.append(this.getSituationExtension(), that.getSituationExtension());
        equalsBuilder.append(this.getId(), that.getId());
        equalsBuilder.append(this.getVersion(), that.getVersion());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Situation)) {
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
    public void hashCode(HashCodeBuilder hashCodeBuilder) {
        hashCodeBuilder.append(this.getHeaderInformation());
        hashCodeBuilder.append(this.getSituationRecord());
        hashCodeBuilder.append(this.getSituationExtension());
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
