/**
 * Licensed to the TomTom International B.V. under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  TomTom International B.V.
 * licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
/**
 *  Copyright (C) 2009-2012 TomTom International B.V.
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

package openlr.xml.generated;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 
 * The BinaryLocationReference is the Base64-encoded binary representation of
 * the location reference. Each binary location reference needs to have an
 * identifier and a version. The version is defined by the version of the binary
 * physical format.
 * 
 * 
 * <p>
 * Java class for BinaryLocationReference complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="BinaryLocationReference">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>base64Binary">
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="version" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BinaryLocationReference", propOrder = {"value" })
public class BinaryLocationReference implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9110887830779301413L;

	/** The value. */
	@XmlValue
	protected byte[] value;
	
	/** The id. */
	@XmlAttribute(required = true)
	protected String id;
	
	/** The version. */
	@XmlAttribute(required = true)
	protected String version;

	/**
	 * Gets the value of the value property.
	 * 
	 * @return possible object is byte[]
	 */
	public final byte[] getValue() {
		return value;
	}

	/**
	 * Sets the value of the value property.
	 * 
	 * @param val
	 *            allowed object is byte[]
	 */
	public final void setValue(final byte[] val) {
		this.value = ((byte[]) val);
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
	 * @param val
	 *            allowed object is {@link String }
	 * 
	 */
	public final void setId(final String val) {
		this.id = val;
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
	 * @param val
	 *            allowed object is {@link String }
	 * 
	 */
	public final void setVersion(final String val) {
		this.version = val;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean equals(final Object o) {
		if (o == null) {
			return false;
		}
		if (this == o) {
			return true;
		}
		if (!(o instanceof BinaryLocationReference)) {
			return false;
		}
		BinaryLocationReference other = (BinaryLocationReference) o;
		EqualsBuilder builder = new EqualsBuilder();
		builder.append(value, other.value).append(id, other.id)
				.append(version, other.version);
		return builder.isEquals();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		builder.append(value).append(id).append(version);
		return builder.toHashCode();
	}

}
