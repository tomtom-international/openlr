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

/**
 *
 * An AreaLocationReference represents a set of area location like circle
 * polygon rectangle and grid location.
 *
 *
 * <p>
 * Java class for AreaLocationReference complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="AreaLocationReference">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="CircleLocationReference" type="{http://www.openlr.org/openlr}CircleLocationReference"/>
 *         &lt;element name="PolygonLocationReference" type="{http://www.openlr.org/openlr}PolygonLocationReference"/>
 *         &lt;element name="RectangleLocationReference" type="{http://www.openlr.org/openlr}RectangleLocationReference"/>
 *         &lt;element name="GridLocationReference" type="{http://www.openlr.org/openlr}GridLocationReference"/>
 *         &lt;element name="ClosedLineLocationReference" type="{http://www.openlr.org/openlr}ClosedLineLocationReference"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AreaLocationReference", propOrder = {
        "circleLocationReference", "polygonLocationReference",
        "rectangleLocationReference", "gridLocationReference",
        "closedLineLocationReference"})
public class AreaLocationReference implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 121159413081750671L;

    /** The circle location reference. */
    @XmlElement(name = "CircleLocationReference")
    protected CircleLocationReference circleLocationReference;

    /** The polygon location reference. */
    @XmlElement(name = "PolygonLocationReference")
    protected PolygonLocationReference polygonLocationReference;

    /** The rectangle location reference. */
    @XmlElement(name = "RectangleLocationReference")
    protected RectangleLocationReference rectangleLocationReference;

    /** The grid location reference. */
    @XmlElement(name = "GridLocationReference")
    protected GridLocationReference gridLocationReference;

    /** The closed line location reference. */
    @XmlElement(name = "ClosedLineLocationReference")
    protected ClosedLineLocationReference closedLineLocationReference;

    /**
     * Gets the value of the circleLocationReference property.
     *
     * @return possible object is {@link CircleLocationReference }
     *
     */
    public final CircleLocationReference getCircleLocationReference() {
        return circleLocationReference;
    }

    /**
     * Sets the value of the circleLocationReference property.
     *
     * @param value
     *            allowed object is {@link CircleLocationReference }
     *
     */
    public final void setCircleLocationReference(final CircleLocationReference value) {
        this.circleLocationReference = value;
    }

    /**
     * Gets the value of the polygonLocationReference property.
     *
     * @return possible object is {@link PolygonLocationReference }
     *
     */
    public final PolygonLocationReference getPolygonLocationReference() {
        return polygonLocationReference;
    }

    /**
     * Sets the value of the polygonLocationReference property.
     *
     * @param value
     *            allowed object is {@link PolygonLocationReference }
     *
     */
    public final void setPolygonLocationReference(final PolygonLocationReference value) {
        this.polygonLocationReference = value;
    }

    /**
     * Gets the value of the rectangleLocationReference property.
     *
     * @return possible object is {@link RectangleLocationReference }
     *
     */
    public final RectangleLocationReference getRectangleLocationReference() {
        return rectangleLocationReference;
    }

    /**
     * Sets the value of the rectangleLocationReference property.
     *
     * @param value
     *            allowed object is {@link RectangleLocationReference }
     *
     */
    public final void setRectangleLocationReference(final RectangleLocationReference value) {
        this.rectangleLocationReference = value;
    }

    /**
     * Gets the value of the gridLocationReference property.
     *
     * @return possible object is {@link GridLocationReference }
     *
     */
    public final GridLocationReference getGridLocationReference() {
        return gridLocationReference;
    }

    /**
     * Sets the value of the gridLocationReference property.
     *
     * @param value
     *            allowed object is {@link GridLocationReference }
     *
     */
    public final void setGridLocationReference(final GridLocationReference value) {
        this.gridLocationReference = value;
    }

    /**
     * Gets the value of the closedLineLocationReference property.
     *
     * @return possible object is {@link ClosedLineLocationReference }
     *
     */
    public final ClosedLineLocationReference getClosedLineLocationReference() {
        return closedLineLocationReference;
    }

    /**
     * Sets the value of the closedLineLocationReference property.
     *
     * @param value
     *            allowed object is {@link ClosedLineLocationReference }
     *
     */
    public final void setClosedLineLocationReference(final ClosedLineLocationReference value) {
        this.closedLineLocationReference = value;
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
        if (!(o instanceof AreaLocationReference)) {
            return false;
        }
        AreaLocationReference other = (AreaLocationReference) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(circleLocationReference, other.circleLocationReference)
                .append(polygonLocationReference, other.polygonLocationReference)
                .append(rectangleLocationReference, other.rectangleLocationReference)
                .append(gridLocationReference, other.gridLocationReference)
                .append(closedLineLocationReference, other.closedLineLocationReference);
        return builder.isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(circleLocationReference)
                .append(polygonLocationReference)
                .append(rectangleLocationReference)
                .append(gridLocationReference)
                .append(closedLineLocationReference);
        return builder.toHashCode();
    }

}
