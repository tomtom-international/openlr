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
 * A XMLLocationReference describes an OpenLR location reference. It is
 * configured to support line and point as well as area location.
 *
 *
 * <p>
 * Java class for XMLLocationReference complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="XMLLocationReference">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="LineLocationReference" type="{http://www.openlr.org/openlr}LineLocationReference"/>
 *         &lt;element name="PointLocationReference" type="{http://www.openlr.org/openlr}PointLocationReference"/>
 *         &lt;element name="AreaLocationReference" type="{http://www.openlr.org/openlr}AreaLocationReference"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XMLLocationReference", propOrder = {"lineLocationReference",
        "pointLocationReference", "areaLocationReference"})
public class XMLLocationReference implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3189580851243495356L;

    /** The line location reference. */
    @XmlElement(name = "LineLocationReference")
    protected LineLocationReference lineLocationReference;

    /** The point location reference. */
    @XmlElement(name = "PointLocationReference")
    protected PointLocationReference pointLocationReference;

    /** The area location reference. */
    @XmlElement(name = "AreaLocationReference")
    protected AreaLocationReference areaLocationReference;

    /**
     * Gets the value of the lineLocationReference property.
     *
     * @return possible object is {@link LineLocationReference }
     *
     */
    public final LineLocationReference getLineLocationReference() {
        return lineLocationReference;
    }

    /**
     * Sets the value of the lineLocationReference property.
     *
     * @param value
     *            allowed object is {@link LineLocationReference }
     *
     */
    public final void setLineLocationReference(final LineLocationReference value) {
        this.lineLocationReference = value;
    }

    /**
     * Gets the value of the pointLocationReference property.
     *
     * @return possible object is {@link PointLocationReference }
     *
     */
    public final PointLocationReference getPointLocationReference() {
        return pointLocationReference;
    }

    /**
     * Sets the value of the pointLocationReference property.
     *
     * @param value
     *            allowed object is {@link PointLocationReference }
     *
     */
    public final void setPointLocationReference(final PointLocationReference value) {
        this.pointLocationReference = value;
    }

    /**
     * Gets the value of the areaLocationReference property.
     *
     * @return possible object is {@link AreaLocationReference }
     *
     */
    public final AreaLocationReference getAreaLocationReference() {
        return areaLocationReference;
    }

    /**
     * Sets the value of the areaLocationReference property.
     *
     * @param value
     *            allowed object is {@link AreaLocationReference }
     *
     */
    public final void setAreaLocationReference(final AreaLocationReference value) {
        this.areaLocationReference = value;
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
        if (!(o instanceof XMLLocationReference)) {
            return false;
        }
        XMLLocationReference other = (XMLLocationReference) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(lineLocationReference, other.lineLocationReference)
                .append(pointLocationReference, other.pointLocationReference)
                .append(areaLocationReference, other.areaLocationReference);
        return builder.isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(lineLocationReference).append(pointLocationReference)
                .append(areaLocationReference);
        return builder.toHashCode();
    }

}
