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
import java.math.BigInteger;

/**
 *
 * Offsets are used to shorten the location reference path at its start and end.
 * The new positions along the location reference path indicate the real start
 * and end of the location. The positive offset is the difference of the start
 * point of the location reference and the start point of the desired location
 * along the location reference path. The negative offset is the difference of
 * the end point of the desired location and the end point of the location
 * reference along the location reference path. Both values are measured in
 * meter.
 *
 *
 * <p>
 * Java class for Offsets complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="Offsets">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="PosOff" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="NegOff" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Offsets", propOrder = {

})
public class Offsets implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2956265370995194148L;

    /** The pos off. */
    @XmlElement(name = "PosOff")
    protected BigInteger posOff;

    /** The neg off. */
    @XmlElement(name = "NegOff")
    protected BigInteger negOff;

    /**
     * Gets the value of the posOff property.
     *
     * @return possible object is {@link BigInteger }
     *
     */
    public final BigInteger getPosOff() {
        return posOff;
    }

    /**
     * Sets the value of the posOff property.
     *
     * @param value
     *            allowed object is {@link BigInteger }
     *
     */
    public final void setPosOff(final BigInteger value) {
        this.posOff = value;
    }

    /**
     * Gets the value of the negOff property.
     *
     * @return possible object is {@link BigInteger }
     *
     */
    public final BigInteger getNegOff() {
        return negOff;
    }

    /**
     * Sets the value of the negOff property.
     *
     * @param value
     *            allowed object is {@link BigInteger }
     *
     */
    public final void setNegOff(final BigInteger value) {
        this.negOff = value;
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
        if (!(o instanceof Offsets)) {
            return false;
        }
        Offsets other = (Offsets) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(posOff, other.posOff).append(negOff, other.negOff);
        return builder.isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(posOff).append(negOff);
        return builder.toHashCode();
    }

}
