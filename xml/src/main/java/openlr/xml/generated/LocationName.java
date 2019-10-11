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

/**
 *
 * The LocationName provides information in order to generate a proper
 * human-readable name. It may contain different building blocks or a compressed
 * name. The following building blocks may be provided: fromArea = area name
 * where the location starts, toArea = area name where the location ends,
 * roadName = list of road names along the location, start = concrete start
 * point of the location, end = concrete end point of the location.
 *
 *
 * <p>
 * Java class for LocationName complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="LocationName">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Roadname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FromArea" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToArea" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Start" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="End" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LocationName", propOrder = {

})
public class LocationName implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1485528341459090119L;

    /** The name. */
    @XmlElement(name = "Name")
    protected String name;

    /** The roadname. */
    @XmlElement(name = "Roadname")
    protected String roadname;

    /** The from area. */
    @XmlElement(name = "FromArea")
    protected String fromArea;

    /** The to area. */
    @XmlElement(name = "ToArea")
    protected String toArea;

    /** The start. */
    @XmlElement(name = "Start")
    protected String start;

    /** The end. */
    @XmlElement(name = "End")
    protected String end;

    /**
     * Gets the value of the name property.
     *
     * @return possible object is {@link String }
     *
     */
    public final String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value
     *            allowed object is {@link String }
     *
     */
    public final void setName(final String value) {
        this.name = value;
    }

    /**
     * Gets the value of the roadname property.
     *
     * @return possible object is {@link String }
     *
     */
    public final String getRoadname() {
        return roadname;
    }

    /**
     * Sets the value of the roadname property.
     *
     * @param value
     *            allowed object is {@link String }
     *
     */
    public final void setRoadname(final String value) {
        this.roadname = value;
    }

    /**
     * Gets the value of the fromArea property.
     *
     * @return possible object is {@link String }
     *
     */
    public final String getFromArea() {
        return fromArea;
    }

    /**
     * Sets the value of the fromArea property.
     *
     * @param value
     *            allowed object is {@link String }
     *
     */
    public final void setFromArea(final String value) {
        this.fromArea = value;
    }

    /**
     * Gets the value of the toArea property.
     *
     * @return possible object is {@link String }
     *
     */
    public final String getToArea() {
        return toArea;
    }

    /**
     * Sets the value of the toArea property.
     *
     * @param value
     *            allowed object is {@link String }
     *
     */
    public final void setToArea(final String value) {
        this.toArea = value;
    }

    /**
     * Gets the value of the start property.
     *
     * @return possible object is {@link String }
     *
     */
    public final String getStart() {
        return start;
    }

    /**
     * Sets the value of the start property.
     *
     * @param value
     *            allowed object is {@link String }
     *
     */
    public final void setStart(final String value) {
        this.start = value;
    }

    /**
     * Gets the value of the end property.
     *
     * @return possible object is {@link String }
     *
     */
    public final String getEnd() {
        return end;
    }

    /**
     * Sets the value of the end property.
     *
     * @param value
     *            allowed object is {@link String }
     *
     */
    public final void setEnd(final String value) {
        this.end = value;
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
        if (!(o instanceof LocationName)) {
            return false;
        }
        LocationName other = (LocationName) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(name, other.name).append(roadname, other.roadname)
                .append(fromArea, other.fromArea).append(toArea, other.toArea)
                .append(start, other.start).append(end, other.end);
        return builder.isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(name).append(roadname).append(fromArea).append(toArea)
                .append(start).append(end);
        return builder.toHashCode();
    }

}
