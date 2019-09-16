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
 * <p>Java class for AreaOfInterestEnum.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AreaOfInterestEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="continentWide"/>
 *     &lt;enumeration value="national"/>
 *     &lt;enumeration value="neighbouringCountries"/>
 *     &lt;enumeration value="notSpecified"/>
 *     &lt;enumeration value="regional"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 *
 */
@XmlType(name = "AreaOfInterestEnum")
@XmlEnum
public enum AreaOfInterestEnum {

    /** The CONTINEN t_ wide. */
    @XmlEnumValue("continentWide")
    CONTINENT_WIDE("continentWide"),

    /** The NATIONAL. */
    @XmlEnumValue("national")
    NATIONAL("national"),

    /** The NEIGHBOURIN g_ countries. */
    @XmlEnumValue("neighbouringCountries")
    NEIGHBOURING_COUNTRIES("neighbouringCountries"),

    /** The NO t_ specified. */
    @XmlEnumValue("notSpecified")
    NOT_SPECIFIED("notSpecified"),

    /** The REGIONAL. */
    @XmlEnumValue("regional")
    REGIONAL("regional");

    /** The value. */
    private final String value;

    /**
     * Instantiates a new area of interest enum.
     *
     * @param v the v
     */
    AreaOfInterestEnum(final String v) {
        value = v;
    }

    /**
     * From value.
     *
     * @param v the v
     * @return the area of interest enum
     */
    public static final AreaOfInterestEnum fromValue(final String v) {
        for (AreaOfInterestEnum c : AreaOfInterestEnum.values()) {
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
