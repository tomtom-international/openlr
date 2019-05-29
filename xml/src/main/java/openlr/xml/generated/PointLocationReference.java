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
 * A PointLocationReference represents a point of interest. The point may be
 * bound to the underlying network.
 * 
 * 
 * <p>
 * Java class for PointLocationReference complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="PointLocationReference">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="GeoCoordinate" type="{http://www.openlr.org/openlr}GeoCoordinate"/>
 *         &lt;element name="PointAlongLine" type="{http://www.openlr.org/openlr}PointAlongLine"/>
 *         &lt;element name="PoiWithAccessPoint" type="{http://www.openlr.org/openlr}PoiWithAccessPoint"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PointLocationReference", propOrder = {"geoCoordinate",
		"pointAlongLine", "poiWithAccessPoint" })
public class PointLocationReference implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -288277342095263803L;

	/** The geo coordinate. */
	@XmlElement(name = "GeoCoordinate")
	protected GeoCoordinate geoCoordinate;
	
	/** The point along line. */
	@XmlElement(name = "PointAlongLine")
	protected PointAlongLine pointAlongLine;
	
	/** The poi with access point. */
	@XmlElement(name = "PoiWithAccessPoint")
	protected PoiWithAccessPoint poiWithAccessPoint;

	/**
	 * Gets the value of the geoCoordinate property.
	 * 
	 * @return possible object is {@link GeoCoordinate }
	 * 
	 */
	public final GeoCoordinate getGeoCoordinate() {
		return geoCoordinate;
	}

	/**
	 * Sets the value of the geoCoordinate property.
	 * 
	 * @param value
	 *            allowed object is {@link GeoCoordinate }
	 * 
	 */
	public final void setGeoCoordinate(final GeoCoordinate value) {
		this.geoCoordinate = value;
	}

	/**
	 * Gets the value of the pointAlongLine property.
	 * 
	 * @return possible object is {@link PointAlongLine }
	 * 
	 */
	public final PointAlongLine getPointAlongLine() {
		return pointAlongLine;
	}

	/**
	 * Sets the value of the pointAlongLine property.
	 * 
	 * @param value
	 *            allowed object is {@link PointAlongLine }
	 * 
	 */
	public final void setPointAlongLine(final PointAlongLine value) {
		this.pointAlongLine = value;
	}

	/**
	 * Gets the value of the poiWithAccessPoint property.
	 * 
	 * @return possible object is {@link PoiWithAccessPoint }
	 * 
	 */
	public final PoiWithAccessPoint getPoiWithAccessPoint() {
		return poiWithAccessPoint;
	}

	/**
	 * Sets the value of the poiWithAccessPoint property.
	 * 
	 * @param value
	 *            allowed object is {@link PoiWithAccessPoint }
	 * 
	 */
	public final void setPoiWithAccessPoint(final PoiWithAccessPoint value) {
		this.poiWithAccessPoint = value;
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
		if (!(o instanceof PointLocationReference)) {
			return false;
		}
		PointLocationReference other = (PointLocationReference) o;
		EqualsBuilder builder = new EqualsBuilder();
		builder.append(geoCoordinate, other.geoCoordinate)
				.append(pointAlongLine, other.pointAlongLine)
				.append(poiWithAccessPoint, other.poiWithAccessPoint);
		return builder.isEquals();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		builder.append(geoCoordinate).append(pointAlongLine)
				.append(poiWithAccessPoint);
		return builder.toHashCode();
	}
}
