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
 * The bounding box describes a rectangle which covers the location completely.
 * The rectangle is defined by the coordinates of the lower left and upper right
 * corners.
 *
 *
 * <p>
 * Java class for Rectangle complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="Rectangle">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="LowerLeft" type="{http://www.openlr.org/openlr}Coordinates"/>
 *         &lt;element name="UpperRight" type="{http://www.openlr.org/openlr}Coordinates"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Rectangle", propOrder = {

})
public class Rectangle implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 911845144369114378L;

    /** The lower left. */
    @XmlElement(name = "LowerLeft", required = true)
    protected Coordinates lowerLeft;

    /** The upper right. */
    @XmlElement(name = "UpperRight", required = true)
    protected Coordinates upperRight;

    /**
     * Gets the value of the lowerLeft property.
     *
     * @return possible object is {@link Coordinates }
     *
     */
    public final Coordinates getLowerLeft() {
        return lowerLeft;
    }

    /**
     * Sets the value of the lowerLeft property.
     *
     * @param value
     *            allowed object is {@link Coordinates }
     *
     */
    public final void setLowerLeft(final Coordinates value) {
        this.lowerLeft = value;
    }

    /**
     * Gets the value of the upperRight property.
     *
     * @return possible object is {@link Coordinates }
     *
     */
    public final Coordinates getUpperRight() {
        return upperRight;
    }

    /**
     * Sets the value of the upperRight property.
     *
     * @param value
     *            allowed object is {@link Coordinates }
     *
     */
    public final void setUpperRight(final Coordinates value) {
        this.upperRight = value;
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
        if (!(o instanceof Rectangle)) {
            return false;
        }
        Rectangle other = (Rectangle) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(lowerLeft, other.lowerLeft).append(upperRight,
                other.upperRight);
        return builder.isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(lowerLeft).append(upperRight);
        return builder.toHashCode();
    }

}
