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

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.math.BigInteger;

/**
 *
 * 				Path attributes describes
 * 				features of a path. The LFRCNP attribute
 * 				is the lowest functional
 * 				road class value which appears in the path.
 * 				The highest FRC value is
 * 				FRC0 and the lowest possible FRC value
 * 				is FRC7.				
 *
 *
 * <p>Java class for PathAttributes complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="PathAttributes">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="LFRCNP" type="{http://www.openlr.org/openlr}FRC_Type"/>
 *         &lt;element name="DNP" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PathAttributes", propOrder = {

})
public class PathAttributes implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2582496850522536496L;

    /** The lfrcnp. */
    @XmlElement(name = "LFRCNP", required = true)
    protected FRCType lfrcnp;

    /** The dnp. */
    @XmlElement(name = "DNP", required = true)
    protected BigInteger dnp;

    /**
     * Gets the value of the lfrcnp property.
     *
     * @return the lFRCNP
     * possible object is
     * {@link FRCType }
     */
    public final FRCType getLFRCNP() {
        return lfrcnp;
    }

    /**
     * Sets the value of the lfrcnp property.
     *
     * @param value
     *     allowed object is
     *     {@link FRCType }
     *
     */
    public final void setLFRCNP(final FRCType value) {
        this.lfrcnp = value;
    }

    /**
     * Gets the value of the dnp property.
     *
     * @return the dNP
     * possible object is
     * {@link BigInteger }
     */
    public final BigInteger getDNP() {
        return dnp;
    }

    /**
     * Sets the value of the dnp property.
     *
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *
     */
    public final void setDNP(final BigInteger value) {
        this.dnp = value;
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
        if (!(o instanceof PathAttributes)) {
            return false;
        }
        PathAttributes other = (PathAttributes) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(lfrcnp, other.lfrcnp).append(dnp, other.dnp);
        return builder.isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(lfrcnp).append(dnp);
        return builder.toHashCode();
    }

}
