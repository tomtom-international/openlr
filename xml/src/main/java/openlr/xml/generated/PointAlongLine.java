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
 * Point along line is a point location which is defined by a line and an offset
 * value. The line will be referenced by two location reference points and the
 * concrete position on that line is referenced using the positive offset.
 * Additionally information about the side of the road where the point is
 * located and the orientation with respect to the direction of the line can be
 * added.
 *
 *
 * <p>
 * Java class for PointAlongLine complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="PointAlongLine">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="LocationReferencePoint" type="{http://www.openlr.org/openlr}LocationReferencePoint"/>
 *         &lt;element name="LastLocationReferencePoint" type="{http://www.openlr.org/openlr}LastLocationReferencePoint"/>
 *         &lt;element name="Offsets" type="{http://www.openlr.org/openlr}Offsets" minOccurs="0"/>
 *         &lt;element name="SideOfRoad" type="{http://www.openlr.org/openlr}SideOfRoad_Type" minOccurs="0"/>
 *         &lt;element name="Orientation" type="{http://www.openlr.org/openlr}Orientation_Type" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PointAlongLine", propOrder = {

})
public class PointAlongLine implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5118791026454552836L;

    /** The location reference point. */
    @XmlElement(name = "LocationReferencePoint", required = true)
    protected LocationReferencePoint locationReferencePoint;

    /** The last location reference point. */
    @XmlElement(name = "LastLocationReferencePoint", required = true)
    protected LastLocationReferencePoint lastLocationReferencePoint;

    /** The offsets. */
    @XmlElement(name = "Offsets")
    protected Offsets offsets;

    /** The side of road. */
    @XmlElement(name = "SideOfRoad", defaultValue = "ON_ROAD_OR_UNKNOWN")
    protected SideOfRoadType sideOfRoad;

    /** The orientation. */
    @XmlElement(name = "Orientation", defaultValue = "NO_ORIENTATION_OR_UNKNOWN")
    protected OrientationType orientation;

    /**
     * Gets the value of the locationReferencePoint property.
     *
     * @return possible object is {@link LocationReferencePoint }
     *
     */
    public final LocationReferencePoint getLocationReferencePoint() {
        return locationReferencePoint;
    }

    /**
     * Sets the value of the locationReferencePoint property.
     *
     * @param value
     *            allowed object is {@link LocationReferencePoint }
     *
     */
    public final void setLocationReferencePoint(final LocationReferencePoint value) {
        this.locationReferencePoint = value;
    }

    /**
     * Gets the value of the lastLocationReferencePoint property.
     *
     * @return possible object is {@link LastLocationReferencePoint }
     *
     */
    public final LastLocationReferencePoint getLastLocationReferencePoint() {
        return lastLocationReferencePoint;
    }

    /**
     * Sets the value of the lastLocationReferencePoint property.
     *
     * @param value
     *            allowed object is {@link LastLocationReferencePoint }
     *
     */
    public final void setLastLocationReferencePoint(final LastLocationReferencePoint value) {
        this.lastLocationReferencePoint = value;
    }

    /**
     * Gets the value of the offsets property.
     *
     * @return possible object is {@link Offsets }
     *
     */
    public final Offsets getOffsets() {
        return offsets;
    }

    /**
     * Sets the value of the offsets property.
     *
     * @param value
     *            allowed object is {@link Offsets }
     *
     */
    public final void setOffsets(final Offsets value) {
        this.offsets = value;
    }

    /**
     * Gets the value of the sideOfRoad property.
     *
     * @return possible object is {@link SideOfRoadType }
     *
     */
    public final SideOfRoadType getSideOfRoad() {
        return sideOfRoad;
    }

    /**
     * Sets the value of the sideOfRoad property.
     *
     * @param value
     *            allowed object is {@link SideOfRoadType }
     *
     */
    public final void setSideOfRoad(final SideOfRoadType value) {
        this.sideOfRoad = value;
    }

    /**
     * Gets the value of the orientation property.
     *
     * @return possible object is {@link OrientationType }
     *
     */
    public final OrientationType getOrientation() {
        return orientation;
    }

    /**
     * Sets the value of the orientation property.
     *
     * @param value
     *            allowed object is {@link OrientationType }
     *
     */
    public final void setOrientation(final OrientationType value) {
        this.orientation = value;
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
        if (!(o instanceof PointAlongLine)) {
            return false;
        }
        PointAlongLine other = (PointAlongLine) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(locationReferencePoint, other.locationReferencePoint)
                .append(lastLocationReferencePoint,
                        other.lastLocationReferencePoint)
                .append(offsets, other.offsets)
                .append(sideOfRoad, other.sideOfRoad)
                .append(orientation, other.orientation);
        return builder.isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(locationReferencePoint)
                .append(lastLocationReferencePoint).append(offsets)
                .append(sideOfRoad).append(orientation);
        return builder.toHashCode();
    }
}
