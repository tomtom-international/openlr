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
 * <p>Java class for Orientation_Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Orientation_Type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NO_ORIENTATION_OR_UNKNOWN"/>
 *     &lt;enumeration value="WITH_LINE_DIRECTION"/>
 *     &lt;enumeration value="AGAINST_LINE_DIRECTION"/>
 *     &lt;enumeration value="BOTH"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Orientation_Type")
@XmlEnum
public enum OrientationType implements Serializable {

    /** The N o_ orientatio n_ o r_ unknown. */
    NO_ORIENTATION_OR_UNKNOWN,
    
    /** The WIT h_ lin e_ direction. */
    WITH_LINE_DIRECTION,
    
    /** The AGAINS t_ lin e_ direction. */
    AGAINST_LINE_DIRECTION,
    
    /** The BOTH. */
    BOTH;

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
     * @return the orientation type
     */
    public static final OrientationType fromValue(final String v) {
        return valueOf(v);
    }

}
