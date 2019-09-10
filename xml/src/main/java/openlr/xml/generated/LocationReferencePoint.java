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
 * Copyright (C) 2009-2012 TomTom International B.V.
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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 *
 * The basis of a location reference is a sequence of location reference points
 * (LRPs). Such a LRP contains a coordinate pair, specified in WGS84 longitude
 * and latitude values and additionally several line and path attributes.
 *
 *
 * <p>
 * Java class for LocationReferencePoint complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="LocationReferencePoint">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Coordinates" type="{http://www.openlr.org/openlr}Coordinates"/>
 *         &lt;element name="LineAttributes" type="{http://www.openlr.org/openlr}LineAttributes"/>
 *         &lt;element name="PathAttributes" type="{http://www.openlr.org/openlr}PathAttributes"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LocationReferencePoint", propOrder = {

})
public class LocationReferencePoint implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -7072466829477213509L;

    /** The coordinates. */
    @XmlElement(name = "Coordinates", required = true)
    protected Coordinates coordinates;

    /** The line attributes. */
    @XmlElement(name = "LineAttributes", required = true)
    protected LineAttributes lineAttributes;

    /** The path attributes. */
    @XmlElement(name = "PathAttributes", required = true)
    protected PathAttributes pathAttributes;

    /**
     * Gets the value of the coordinates property.
     *
     * @return possible object is {@link Coordinates }
     *
     */
    public final Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Sets the value of the coordinates property.
     *
     * @param value
     *            allowed object is {@link Coordinates }
     *
     */
    public final void setCoordinates(final Coordinates value) {
        this.coordinates = value;
    }

    /**
     * Gets the value of the lineAttributes property.
     *
     * @return possible object is {@link LineAttributes }
     *
     */
    public final LineAttributes getLineAttributes() {
        return lineAttributes;
    }

    /**
     * Sets the value of the lineAttributes property.
     *
     * @param value
     *            allowed object is {@link LineAttributes }
     *
     */
    public final void setLineAttributes(final LineAttributes value) {
        this.lineAttributes = value;
    }

    /**
     * Gets the value of the pathAttributes property.
     *
     * @return possible object is {@link PathAttributes }
     *
     */
    public final PathAttributes getPathAttributes() {
        return pathAttributes;
    }

    /**
     * Sets the value of the pathAttributes property.
     *
     * @param value
     *            allowed object is {@link PathAttributes }
     *
     */
    public final void setPathAttributes(final PathAttributes value) {
        this.pathAttributes = value;
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
        if (!(o instanceof LocationReferencePoint)) {
            return false;
        }
        LocationReferencePoint other = (LocationReferencePoint) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(coordinates, other.coordinates)
                .append(lineAttributes, other.lineAttributes)
                .append(pathAttributes, other.pathAttributes);
        return builder.isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(coordinates).append(lineAttributes)
                .append(pathAttributes);
        return builder.toHashCode();
    }

}
