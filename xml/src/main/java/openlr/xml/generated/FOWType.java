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

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for FOW_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="FOW_Type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="UNDEFINED"/>
 *     &lt;enumeration value="MOTORWAY"/>
 *     &lt;enumeration value="MULTIPLE_CARRIAGEWAY"/>
 *     &lt;enumeration value="SINGLE_CARRIAGEWAY"/>
 *     &lt;enumeration value="ROUNDABOUT"/>
 *     &lt;enumeration value="TRAFFICSQUARE"/>
 *     &lt;enumeration value="SLIPROAD"/>
 *     &lt;enumeration value="OTHER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "FOW_Type")
@XmlEnum
public enum FOWType implements Serializable {

    /** The UNDEFINED. */
    UNDEFINED,
    
    /** The MOTORWAY. */
    MOTORWAY,
    
    /** The MULTIPL e_ carriageway. */
    MULTIPLE_CARRIAGEWAY,
    
    /** The SINGL e_ carriageway. */
    SINGLE_CARRIAGEWAY,
    
    /** The ROUNDABOUT. */
    ROUNDABOUT,
    
    /** The TRAFFICSQUARE. */
    TRAFFICSQUARE,
    
    /** The SLIPROAD. */
    SLIPROAD,
    
    /** The OTHER. */
    OTHER;

    /**
     * Value.
     *
     * @return the string
     */
    public final String value() {
        return name();
    }

    /**
     * From value.
     *
     * @param v the v
     * @return the fOW type
     */
    public static final FOWType fromValue(final String v) {
        return valueOf(v);
    }

}
