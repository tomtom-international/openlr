/**
 * Licensed to the TomTom International B.V. under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  TomTom International B.V.
 * licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
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

package openlr.xml.generated;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.math.BigInteger;

/**
 *
 * A CircleLocationReference represents a circle area location. The circle area
 * may be bound to the underlying network.
 *
 *
 * <p>
 * Java class for CircleLocationReference complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="CircleLocationReference">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="GeoCoordinate" type="{http://www.openlr.org/openlr}GeoCoordinate"/>
 *         &lt;element name="Radius" type="{http://www.openlr.org/openlr}Radius_Type"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CircleLocationReference", propOrder = {

})
public class CircleLocationReference implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4649481193760029373L;

    /** The geo coordinate. */
    @XmlElement(name = "GeoCoordinate", required = true)
    protected GeoCoordinate geoCoordinate;

    /** The radius. */
    @XmlElement(name = "Radius", required = true)
    protected BigInteger radius;

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
     * Gets the value of the radius property.
     *
     * @return possible object is {@link BigInteger }
     *
     */
    public final BigInteger getRadius() {
        return radius;
    }

    /**
     * Sets the value of the radius property.
     *
     * @param value
     *            allowed object is {@link BigInteger }
     *
     */
    public final void setRadius(final BigInteger value) {
        this.radius = value;
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
        if (!(o instanceof CircleLocationReference)) {
            return false;
        }
        CircleLocationReference other = (CircleLocationReference) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(geoCoordinate, other.geoCoordinate).append(radius,
                other.radius);
        return builder.isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(geoCoordinate).append(radius);
        return builder.toHashCode();
    }

}
