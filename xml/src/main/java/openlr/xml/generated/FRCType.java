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

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * <p>Java class for FRC_Type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="FRC_Type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="FRC0"/>
 *     &lt;enumeration value="FRC1"/>
 *     &lt;enumeration value="FRC2"/>
 *     &lt;enumeration value="FRC3"/>
 *     &lt;enumeration value="FRC4"/>
 *     &lt;enumeration value="FRC5"/>
 *     &lt;enumeration value="FRC6"/>
 *     &lt;enumeration value="FRC7"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 *
 */
@XmlType(name = "FRC_Type")
@XmlEnum
public enum FRCType implements Serializable {

    /** The FR c_0. */
    @XmlEnumValue("FRC0")
    FRC_0("FRC0"),

    /** The FR c_1. */
    @XmlEnumValue("FRC1")
    FRC_1("FRC1"),

    /** The FR c_2. */
    @XmlEnumValue("FRC2")
    FRC_2("FRC2"),

    /** The FR c_3. */
    @XmlEnumValue("FRC3")
    FRC_3("FRC3"),

    /** The FR c_4. */
    @XmlEnumValue("FRC4")
    FRC_4("FRC4"),

    /** The FR c_5. */
    @XmlEnumValue("FRC5")
    FRC_5("FRC5"),

    /** The FR c_6. */
    @XmlEnumValue("FRC6")
    FRC_6("FRC6"),

    /** The FR c_7. */
    @XmlEnumValue("FRC7")
    FRC_7("FRC7");

    /** The value. */
    private final String value;

    /**
     * Instantiates a new fRC type.
     *
     * @param v the v
     */
    FRCType(final String v) {
        value = v;
    }

    /**
     * From value.
     *
     * @param v the v
     * @return the fRC type
     */
    public static final FRCType fromValue(final String v) {
        for (FRCType c : FRCType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

    /**
     * Value.
     *
     * @return the string
     */
    public final String value() {
        return value;
    }

}
