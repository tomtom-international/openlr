/**
 * Licensed to the TomTom International B.V. under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  TomTom International B.V.
 * licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 
 * 				It may exist several
 * 				different formats for binary location references
 * 				and all formats or a
 * 				subset can be included.
 * 			
 * 
 * <p>Java class for BinaryLocationReferences complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BinaryLocationReferences">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BinaryLocationReference" type="{http://www.openlr.org/openlr}BinaryLocationReference" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BinaryLocationReferences", propOrder = {
    "binaryLocationReference"
})
public class BinaryLocationReferences implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7451060043544850156L;
	/** The binary location reference. */
    @XmlElement(name = "BinaryLocationReference", required = true)
    protected List<BinaryLocationReference> binaryLocationReference;

    /**
     * Gets the value of the binaryLocationReference property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the binaryLocationReference property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     * getBinaryLocationReference().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     *
     * @return the binary location reference
     * {@link BinaryLocationReference }
     */
    public final List<BinaryLocationReference> getBinaryLocationReference() {
        if (binaryLocationReference == null) {
            binaryLocationReference = new ArrayList<BinaryLocationReference>();
        }
        return this.binaryLocationReference;
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
		if (!(o instanceof BinaryLocationReferences)) {
			return false;
		}
		BinaryLocationReferences other = (BinaryLocationReferences) o;
    	EqualsBuilder builder  = new EqualsBuilder();
    	builder.append(binaryLocationReference, other.binaryLocationReference);
    	return builder.isEquals();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
    	HashCodeBuilder builder = new HashCodeBuilder();
    	builder.append(binaryLocationReference);
    	return builder.toHashCode();    	
    }

}
