/**
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License and the extra
 *  conditions for OpenLR. (see openlr-license.txt)
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
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
 * <p>Java class for OpenlrFormOfWayEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="OpenlrFormOfWayEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="undefined"/>
 *     &lt;enumeration value="motorway"/>
 *     &lt;enumeration value="multipleCarriageway"/>
 *     &lt;enumeration value="singleCarriageway"/>
 *     &lt;enumeration value="roundabout"/>
 *     &lt;enumeration value="slipRoad"/>
 *     &lt;enumeration value="trafficSquare"/>
 *     &lt;enumeration value="other"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "OpenlrFormOfWayEnum")
@XmlEnum
public enum OpenlrFormOfWayEnum {

    /** The UNDEFINED. */
    @XmlEnumValue("undefined")
    UNDEFINED("undefined"),
    
    /** The MOTORWAY. */
    @XmlEnumValue("motorway")
    MOTORWAY("motorway"),
    
    /** The MULTIPL e_ carriageway. */
    @XmlEnumValue("multipleCarriageway")
    MULTIPLE_CARRIAGEWAY("multipleCarriageway"),
    
    /** The SINGL e_ carriageway. */
    @XmlEnumValue("singleCarriageway")
    SINGLE_CARRIAGEWAY("singleCarriageway"),
    
    /** The ROUNDABOUT. */
    @XmlEnumValue("roundabout")
    ROUNDABOUT("roundabout"),
    
    /** The SLI p_ road. */
    @XmlEnumValue("slipRoad")
    SLIP_ROAD("slipRoad"),
    
    /** The TRAFFI c_ square. */
    @XmlEnumValue("trafficSquare")
    TRAFFIC_SQUARE("trafficSquare"),
    
    /** The OTHER. */
    @XmlEnumValue("other")
    OTHER("other");
    
    /** The value. */
    private final String value;

    /**
     * Instantiates a new openlr form of way enum.
     *
     * @param v the v
     */
    OpenlrFormOfWayEnum(final String v) {
        value = v;
    }

    /**
     * Value.
     *
     * @return the string
     */
    public final String value() {
        return value;
    }

    /**
     * From value.
     *
     * @param v the v
     * @return the openlr form of way enum
     */
    public static final OpenlrFormOfWayEnum fromValue(final String v) {
        for (OpenlrFormOfWayEnum c : OpenlrFormOfWayEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
