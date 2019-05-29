/**
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License and the extra
 *  conditions for OpenLR. (see openlr-license.txt)
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * Java class for PayloadPublication complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="PayloadPublication">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="publicationTime" type="{http://datex2.eu/schema/2_0RC2/2_0}DateTime"/>
 *         &lt;element name="publicationCreator" type="{http://datex2.eu/schema/2_0RC2/2_0}InternationalIdentifier"/>
 *         &lt;element name="payloadPublicationExtension" type="{http://datex2.eu/schema/2_0RC2/2_0}_ExtensionType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="lang" use="required" type="{http://datex2.eu/schema/2_0RC2/2_0}Language" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PayloadPublication", propOrder = {"publicationTime",
		"publicationCreator", "payloadPublicationExtension" })
@XmlSeeAlso({SituationPublication.class })
public abstract class PayloadPublication {

	/** The publication time. */
	@XmlElement(required = true)
	protected XMLGregorianCalendar publicationTime;

	/** The publication creator. */
	@XmlElement(required = true)
	protected InternationalIdentifier publicationCreator;

	/** The payload publication extension. */
	protected ExtensionType payloadPublicationExtension;

	/** The lang. */
	@XmlAttribute(name = "lang", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String lang;

	/**
	 * Gets the value of the publicationTime property.
	 * 
	 * @return the publication time possible object is
	 *         {@link XMLGregorianCalendar }
	 */
	public final XMLGregorianCalendar getPublicationTime() {
		return publicationTime;
	}

	/**
	 * Sets the value of the publicationTime property.
	 * 
	 * @param value
	 *            allowed object is {@link XMLGregorianCalendar }
	 * 
	 */
	public final void setPublicationTime(final XMLGregorianCalendar value) {
		this.publicationTime = value;
	}

	/**
	 * Gets the value of the publicationCreator property.
	 * 
	 * @return the publication creator possible object is
	 *         {@link InternationalIdentifier }
	 */
	public final InternationalIdentifier getPublicationCreator() {
		return publicationCreator;
	}

	/**
	 * Sets the value of the publicationCreator property.
	 * 
	 * @param value
	 *            allowed object is {@link InternationalIdentifier }
	 * 
	 */
	public final void setPublicationCreator(final InternationalIdentifier value) {
		this.publicationCreator = value;
	}

	/**
	 * Gets the value of the payloadPublicationExtension property.
	 * 
	 * @return the payload publication extension possible object is
	 *         {@link ExtensionType }
	 */
	public final ExtensionType getPayloadPublicationExtension() {
		return payloadPublicationExtension;
	}

	/**
	 * Sets the value of the payloadPublicationExtension property.
	 * 
	 * @param value
	 *            allowed object is {@link ExtensionType }
	 * 
	 */
	public final void setPayloadPublicationExtension(final ExtensionType value) {
		this.payloadPublicationExtension = value;
	}

	/**
	 * Gets the value of the lang property.
	 * 
	 * @return the lang possible object is {@link String }
	 */
	public final String getLang() {
		return lang;
	}

	/**
	 * Sets the value of the lang property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public final void setLang(final String value) {
		this.lang = value;
	}

	/**
	 * To string.
	 * 
	 * @param toStringBuilder
	 *            the to string builder
	 */
	public void toString(final ToStringBuilder toStringBuilder) {
		XMLGregorianCalendar thePublicationTime;
		thePublicationTime = this.getPublicationTime();
		toStringBuilder.append("publicationTime", thePublicationTime);
		InternationalIdentifier thePublicationCreator;
		thePublicationCreator = this.getPublicationCreator();
		toStringBuilder.append("publicationCreator", thePublicationCreator);
		ExtensionType thePayloadPublicationExtension;
		thePayloadPublicationExtension = this.getPayloadPublicationExtension();
		toStringBuilder.append("payloadPublicationExtension",
				thePayloadPublicationExtension);
		String theLang;
		theLang = this.getLang();
		toStringBuilder.append("lang", theLang);
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
		if (!(object instanceof PayloadPublication)) {
			equalsBuilder.appendSuper(false);
			return;
		}
		if (this == object) {
			return;
		}
		final PayloadPublication that = ((PayloadPublication) object);
		equalsBuilder.append(this.getPublicationTime(),
				that.getPublicationTime());
		equalsBuilder.append(this.getPublicationCreator(),
				that.getPublicationCreator());
		equalsBuilder.append(this.getPayloadPublicationExtension(),
				that.getPayloadPublicationExtension());
		equalsBuilder.append(this.getLang(), that.getLang());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof PayloadPublication)) {
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
		hashCodeBuilder.append(this.getPublicationTime());
		hashCodeBuilder.append(this.getPublicationCreator());
		hashCodeBuilder.append(this.getPayloadPublicationExtension());
		hashCodeBuilder.append(this.getLang());
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
