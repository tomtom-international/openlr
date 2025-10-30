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
 * <p>Java class for OpenlrSideOfRoadEnum.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="OpenlrSideOfRoadEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="onRoadOrUnknown"/>
 *     &lt;enumeration value="right"/>
 *     &lt;enumeration value="left"/>
 *     &lt;enumeration value="both"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 *
 */
@XmlType(name = "OpenlrSideOfRoadEnum")
@XmlEnum
public enum OpenlrSideOfRoadEnum {

    /** The O n_ roa d_ o r_ unknown. */
    @XmlEnumValue("onRoadOrUnknown")
    ON_ROAD_OR_UNKNOWN("onRoadOrUnknown"),

    /** The RIGHT. */
    @XmlEnumValue("right")
    RIGHT("right"),

    /** The LEFT. */
    @XmlEnumValue("left")
    LEFT("left"),

    /** The BOTH. */
    @XmlEnumValue("both")
    BOTH("both");

    /** The value. */
    private final String value;

    /**
     * Instantiates a new openlr side of road enum.
     *
     * @param v the v
     */
    OpenlrSideOfRoadEnum(final String v) {
        value = v;
    }

    /**
     * From value.
     *
     * @param v the v
     * @return the openlr side of road enum
     */
    public static final OpenlrSideOfRoadEnum fromValue(final String v) {
        for (OpenlrSideOfRoadEnum c : OpenlrSideOfRoadEnum.values()) {
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
