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
 * Copyright (C) 2009,2010 TomTom International B.V.
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
 *  Copyright (C) 2009,2010 TomTom International B.V.
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
 * <p>Java class for ConfidentialityValueEnum.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ConfidentialityValueEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="internalUse"/>
 *     &lt;enumeration value="noRestriction"/>
 *     &lt;enumeration value="restrictedToAuthorities"/>
 *     &lt;enumeration value="restrictedToAuthoritiesAndTrafficOperators"/>
 *     &lt;enumeration value="restrictedToAuthoritiesTrafficOperatorsAndPublishers"/>
 *     &lt;enumeration value="restrictedToAuthoritiesTrafficOperatorsAndVms"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 *
 */
@XmlType(name = "ConfidentialityValueEnum")
@XmlEnum
public enum ConfidentialityValueEnum {

    /** The INTERNA l_ use. */
    @XmlEnumValue("internalUse")
    INTERNAL_USE("internalUse"),

    /** The N o_ restriction. */
    @XmlEnumValue("noRestriction")
    NO_RESTRICTION("noRestriction"),

    /** The RESTRICTE d_ t o_ authorities. */
    @XmlEnumValue("restrictedToAuthorities")
    RESTRICTED_TO_AUTHORITIES("restrictedToAuthorities"),

    /** The RESTRICTE d_ t o_ authoritie s_ an d_ traffi c_ operators. */
    @XmlEnumValue("restrictedToAuthoritiesAndTrafficOperators")
    RESTRICTED_TO_AUTHORITIES_AND_TRAFFIC_OPERATORS("restrictedToAuthoritiesAndTrafficOperators"),

    /** The RESTRICTE d_ t o_ authoritie s_ traffi c_ operator s_ an d_ publishers. */
    @XmlEnumValue("restrictedToAuthoritiesTrafficOperatorsAndPublishers")
    RESTRICTED_TO_AUTHORITIES_TRAFFIC_OPERATORS_AND_PUBLISHERS("restrictedToAuthoritiesTrafficOperatorsAndPublishers"),

    /** The RESTRICTE d_ t o_ authoritie s_ traffi c_ operator s_ an d_ vms. */
    @XmlEnumValue("restrictedToAuthoritiesTrafficOperatorsAndVms")
    RESTRICTED_TO_AUTHORITIES_TRAFFIC_OPERATORS_AND_VMS("restrictedToAuthoritiesTrafficOperatorsAndVms");

    /** The value. */
    private final String value;

    /**
     * Instantiates a new confidentiality value enum.
     *
     * @param v the v
     */
    ConfidentialityValueEnum(final String v) {
        value = v;
    }

    /**
     * From value.
     *
     * @param v the v
     * @return the confidentiality value enum
     */
    public static final ConfidentialityValueEnum fromValue(final String v) {
        for (ConfidentialityValueEnum c : ConfidentialityValueEnum.values()) {
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
