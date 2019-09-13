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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * A LineLocationReference is defined by an ordered sequence of location
 * reference points and a terminating last location reference point.
 *
 *
 * <p>
 * Java class for ClosedLineLocationReference complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="ClosedLineLocationReference">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LocationReferencePoint" type="{http://www.openlr.org/openlr}LocationReferencePoint" maxOccurs="unbounded"/>
 *         &lt;element name="LastLine" type="{http://www.openlr.org/openlr}LineAttributes"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClosedLineLocationReference", propOrder = {
        "locationReferencePoint", "lastLine"})
public class ClosedLineLocationReference implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6104110941286423226L;

    /** The location reference point. */
    @XmlElement(name = "LocationReferencePoint", required = true)
    protected List<LocationReferencePoint> locationReferencePoint;

    /** The last line. */
    @XmlElement(name = "LastLine", required = true)
    protected LineAttributes lastLine;

    /**
     * Gets the value of the locationReferencePoint property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the locationReferencePoint property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     *
     * <pre>
     * getLocationReferencePoint().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the location reference point
     * {@link LocationReferencePoint }
     */
    public final List<LocationReferencePoint> getLocationReferencePoint() {
        if (locationReferencePoint == null) {
            locationReferencePoint = new ArrayList<LocationReferencePoint>();
        }
        return this.locationReferencePoint;
    }

    /**
     * Gets the value of the lastLine property.
     *
     * @return possible object is {@link LineAttributes }
     *
     */
    public final LineAttributes getLastLine() {
        return lastLine;
    }

    /**
     * Sets the value of the lastLine property.
     *
     * @param value
     *            allowed object is {@link LineAttributes }
     *
     */
    public final void setLastLine(final LineAttributes value) {
        this.lastLine = value;
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
        if (!(o instanceof ClosedLineLocationReference)) {
            return false;
        }
        ClosedLineLocationReference other = (ClosedLineLocationReference) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(locationReferencePoint, other.locationReferencePoint)
                .append(lastLine, other.lastLine);
        return builder.isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(locationReferencePoint).append(lastLine);
        return builder.toHashCode();
    }

}
