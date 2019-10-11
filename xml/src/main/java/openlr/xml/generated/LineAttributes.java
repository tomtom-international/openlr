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
 * Line attributes describe features of a line in a network. The attributes the
 * functional road class, form of way and the bearing of a line.
 *
 *
 * <p>
 * Java class for LineAttributes complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="LineAttributes">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="FRC" type="{http://www.openlr.org/openlr}FRC_Type"/>
 *         &lt;element name="FOW" type="{http://www.openlr.org/openlr}FOW_Type"/>
 *         &lt;element name="BEAR" type="{http://www.openlr.org/openlr}Bearing_Type"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LineAttributes", propOrder = {

})
public class LineAttributes implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -7515546890363357656L;

    /** The frc. */
    @XmlElement(name = "FRC", required = true)
    protected FRCType frc;

    /** The fow. */
    @XmlElement(name = "FOW", required = true)
    protected FOWType fow;

    /** The bear. */
    @XmlElement(name = "BEAR")
    protected int bear;

    /**
     * Gets the value of the frc property.
     *
     * @return possible object is {@link FRCType }
     *
     */
    public final FRCType getFRC() {
        return frc;
    }

    /**
     * Sets the value of the frc property.
     *
     * @param value
     *            allowed object is {@link FRCType }
     *
     */
    public final void setFRC(final FRCType value) {
        this.frc = value;
    }

    /**
     * Gets the value of the fow property.
     *
     * @return possible object is {@link FOWType }
     *
     */
    public final FOWType getFOW() {
        return fow;
    }

    /**
     * Sets the value of the fow property.
     *
     * @param value
     *            allowed object is {@link FOWType }
     *
     */
    public final void setFOW(final FOWType value) {
        this.fow = value;
    }

    /**
     * Gets the value of the bear property.
     *
     * @return the bEAR
     */
    public final int getBEAR() {
        return bear;
    }

    /**
     * Sets the value of the bear property.
     *
     * @param value the new bEAR
     */
    public final void setBEAR(final int value) {
        this.bear = value;
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
        if (!(o instanceof LineAttributes)) {
            return false;
        }
        LineAttributes other = (LineAttributes) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(frc, other.frc).append(fow, other.fow)
                .append(bear, other.bear);
        return builder.isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(frc).append(fow).append(bear);
        return builder.toHashCode();
    }

}
