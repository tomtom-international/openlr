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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * A PolygonLocationReference represents a polygonal area location
 *
 *
 * <p>
 * Java class for PolygonLocationReference complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="PolygonLocationReference">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PolygonCorners">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Coordinates" type="{http://www.openlr.org/openlr}Coordinates" maxOccurs="unbounded" minOccurs="3"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PolygonLocationReference", propOrder = {"polygonCorners"})
public class PolygonLocationReference implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1491139106623173359L;
    /** The polygon corners. */
    @XmlElement(name = "PolygonCorners", required = true)
    protected PolygonLocationReference.PolygonCorners polygonCorners;

    /**
     * Gets the value of the polygonCorners property.
     *
     * @return possible object is
     *         {@link PolygonLocationReference.PolygonCorners }
     *
     */
    public final PolygonLocationReference.PolygonCorners getPolygonCorners() {
        return polygonCorners;
    }

    /**
     * Sets the value of the polygonCorners property.
     *
     * @param value
     *            allowed object is
     *            {@link PolygonLocationReference.PolygonCorners }
     *
     */
    public final void setPolygonCorners(final PolygonLocationReference.PolygonCorners value) {
        this.polygonCorners = value;
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
        if (!(o instanceof PolygonLocationReference)) {
            return false;
        }
        PolygonLocationReference other = (PolygonLocationReference) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(polygonCorners, other.polygonCorners);
        return builder.isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(polygonCorners);
        return builder.toHashCode();
    }

    /**
     * <p>
     * Java class for anonymous complex type.
     *
     * <p>
     * The following schema fragment specifies the expected content contained
     * within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Coordinates" type="{http://www.openlr.org/openlr}Coordinates" maxOccurs="unbounded" minOccurs="3"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"coordinates"})
    public static class PolygonCorners {

        /** The coordinates. */
        @XmlElement(name = "Coordinates", required = true)
        protected List<Coordinates> coordinates;

        /**
         * Gets the value of the coordinates property.
         *
         * <p>
         * This accessor method returns a reference to the live list, not a
         * snapshot. Therefore any modification you make to the returned list
         * will be present inside the JAXB object. This is why there is not a
         * <CODE>set</CODE> method for the coordinates property.
         *
         * <p>
         * For example, to add a new item, do as follows:
         *
         * <pre>
         * getCoordinates().add(newItem);
         * </pre>
         *
         *
         * <p>
         * Objects of the following type(s) are allowed in the list
         *
         * @return the coordinates
         * {@link Coordinates }
         */
        public final List<Coordinates> getCoordinates() {
            if (coordinates == null) {
                coordinates = new ArrayList<Coordinates>();
            }
            return this.coordinates;
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
            if (!(o instanceof PolygonCorners)) {
                return false;
            }
            PolygonCorners other = (PolygonCorners) o;
            EqualsBuilder builder = new EqualsBuilder();
            builder.append(coordinates, other.coordinates);
            return builder.isEquals();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public final int hashCode() {
            HashCodeBuilder builder = new HashCodeBuilder();
            builder.append(coordinates);
            return builder.toHashCode();
        }

    }

}
