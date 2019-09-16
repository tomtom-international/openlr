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

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for OpenlrOrientationEnum.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * <p>
 *
 * <pre>
 * &lt;simpleType name="OpenlrOrientationEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="noOrientationOrUnknown"/>
 *     &lt;enumeration value="withLineDirection"/>
 *     &lt;enumeration value="againstLineDirection"/>
 *     &lt;enumeration value="both"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 *
 */
@XmlType(name = "OpenlrOrientationEnum")
@XmlEnum
public enum OpenlrOrientationEnum {

    /** The N o_ orientatio n_ o r_ unknown. */
    @XmlEnumValue("noOrientationOrUnknown")
    NO_ORIENTATION_OR_UNKNOWN("noOrientationOrUnknown"),

    /** The WIT h_ lin e_ direction. */
    @XmlEnumValue("withLineDirection")
    WITH_LINE_DIRECTION("withLineDirection"),

    /** The AGAINS t_ lin e_ direction. */
    @XmlEnumValue("againstLineDirection")
    AGAINST_LINE_DIRECTION("againstLineDirection"),

    /** The BOTH. */
    @XmlEnumValue("both")
    BOTH("both");

    /** The value. */
    private final String value;

    /**
     * Instantiates a new openlr orientation enum.
     *
     * @param v
     *            the v
     */
    OpenlrOrientationEnum(final String v) {
        value = v;
    }

    /**
     * From value.
     *
     * @param v
     *            the v
     * @return the openlr orientation enum
     */
    public static final OpenlrOrientationEnum fromValue(final String v) {
        for (OpenlrOrientationEnum c : OpenlrOrientationEnum.values()) {
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
