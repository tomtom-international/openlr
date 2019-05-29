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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 
 * A GridLocationReference represents a rectangular area location with a grid of
 * 
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;code xmlns:openlr="http://www.openlr.org/openlr" xmlns:xs="http://www.w3.org/2001/XMLSchema"&gt;ncols&lt;/code&gt;
 * </pre>
 * 
 * columns and
 * 
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;code xmlns:openlr="http://www.openlr.org/openlr" xmlns:xs="http://www.w3.org/2001/XMLSchema"&gt;nrows&lt;/code&gt;
 * </pre>
 * 
 * rows.
 * 
 * 
 * <p>
 * Java class for GridLocationReference complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="GridLocationReference">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Rectangle" type="{http://www.openlr.org/openlr}Rectangle"/>
 *         &lt;element name="NumColumns" type="{http://www.openlr.org/openlr}Dimension_Type"/>
 *         &lt;element name="NumRows" type="{http://www.openlr.org/openlr}Dimension_Type"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GridLocationReference", propOrder = {

})
public class GridLocationReference implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2117940842434087473L;

	/** The rectangle. */
	@XmlElement(name = "Rectangle", required = true)
	protected Rectangle rectangle;
	
	/** The num columns. */
	@XmlElement(name = "NumColumns")
	protected short numColumns;
	
	/** The num rows. */
	@XmlElement(name = "NumRows")
	protected short numRows;

	/**
	 * Gets the value of the rectangle property.
	 * 
	 * @return possible object is {@link Rectangle }
	 * 
	 */
	public final Rectangle getRectangle() {
		return rectangle;
	}

	/**
	 * Sets the value of the rectangle property.
	 * 
	 * @param value
	 *            allowed object is {@link Rectangle }
	 * 
	 */
	public final void setRectangle(final Rectangle value) {
		this.rectangle = value;
	}

	/**
	 * Gets the value of the numColumns property.
	 *
	 * @return the num columns
	 */
	public final short getNumColumns() {
		return numColumns;
	}

	/**
	 * Sets the value of the numColumns property.
	 *
	 * @param value the new num columns
	 */
	public final void setNumColumns(final short value) {
		this.numColumns = value;
	}

	/**
	 * Gets the value of the numRows property.
	 *
	 * @return the num rows
	 */
	public final short getNumRows() {
		return numRows;
	}

	/**
	 * Sets the value of the numRows property.
	 *
	 * @param value the new num rows
	 */
	public final void setNumRows(final short value) {
		this.numRows = value;
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
		if (!(o instanceof GridLocationReference)) {
			return false;
		}
		GridLocationReference other = (GridLocationReference) o;
		EqualsBuilder builder = new EqualsBuilder();
		builder.append(rectangle, other.rectangle)
				.append(numColumns, other.numColumns)
				.append(numRows, other.numRows);
		return builder.isEquals();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		builder.append(rectangle).append(numColumns).append(numRows);
		return builder.toHashCode();
	}

}
