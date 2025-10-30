/**
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 2 of the License and the extra
 * conditions for OpenLR. (see openlr-license.txt)
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
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
package eu.datex2.schema._2_0rc2._2_0;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * <p>Java class for OpenlrFunctionalRoadClassEnum.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="OpenlrFunctionalRoadClassEnum">
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
@XmlType(name = "OpenlrFunctionalRoadClassEnum")
@XmlEnum
public enum OpenlrFunctionalRoadClassEnum {

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
     * Instantiates a new openlr functional road class enum.
     *
     * @param v the v
     */
    OpenlrFunctionalRoadClassEnum(final String v) {
        value = v;
    }

    /**
     * From value.
     *
     * @param v the v
     * @return the openlr functional road class enum
     */
    public static final OpenlrFunctionalRoadClassEnum fromValue(final String v) {
        for (OpenlrFunctionalRoadClassEnum c : OpenlrFunctionalRoadClassEnum.values()) {
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
