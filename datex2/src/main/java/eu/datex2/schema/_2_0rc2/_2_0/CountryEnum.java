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
 * <p>Java class for CountryEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CountryEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="at"/>
 *     &lt;enumeration value="be"/>
 *     &lt;enumeration value="bg"/>
 *     &lt;enumeration value="ch"/>
 *     &lt;enumeration value="cs"/>
 *     &lt;enumeration value="cy"/>
 *     &lt;enumeration value="cz"/>
 *     &lt;enumeration value="de"/>
 *     &lt;enumeration value="dk"/>
 *     &lt;enumeration value="ee"/>
 *     &lt;enumeration value="es"/>
 *     &lt;enumeration value="fi"/>
 *     &lt;enumeration value="fo"/>
 *     &lt;enumeration value="fr"/>
 *     &lt;enumeration value="gb"/>
 *     &lt;enumeration value="gg"/>
 *     &lt;enumeration value="gi"/>
 *     &lt;enumeration value="gr"/>
 *     &lt;enumeration value="hr"/>
 *     &lt;enumeration value="hu"/>
 *     &lt;enumeration value="ie"/>
 *     &lt;enumeration value="im"/>
 *     &lt;enumeration value="is"/>
 *     &lt;enumeration value="it"/>
 *     &lt;enumeration value="je"/>
 *     &lt;enumeration value="li"/>
 *     &lt;enumeration value="lt"/>
 *     &lt;enumeration value="lu"/>
 *     &lt;enumeration value="lv"/>
 *     &lt;enumeration value="ma"/>
 *     &lt;enumeration value="mc"/>
 *     &lt;enumeration value="mk"/>
 *     &lt;enumeration value="mt"/>
 *     &lt;enumeration value="nl"/>
 *     &lt;enumeration value="no"/>
 *     &lt;enumeration value="pl"/>
 *     &lt;enumeration value="pt"/>
 *     &lt;enumeration value="ro"/>
 *     &lt;enumeration value="se"/>
 *     &lt;enumeration value="si"/>
 *     &lt;enumeration value="sk"/>
 *     &lt;enumeration value="sm"/>
 *     &lt;enumeration value="tr"/>
 *     &lt;enumeration value="va"/>
 *     &lt;enumeration value="other"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CountryEnum")
@XmlEnum
public enum CountryEnum {

    /** The AT. */
    @XmlEnumValue("at")
    AT("at"),
    
    /** The BE. */
    @XmlEnumValue("be")
    BE("be"),
    
    /** The BG. */
    @XmlEnumValue("bg")
    BG("bg"),
    
    /** The CH. */
    @XmlEnumValue("ch")
    CH("ch"),
    
    /** The CS. */
    @XmlEnumValue("cs")
    CS("cs"),
    
    /** The CY. */
    @XmlEnumValue("cy")
    CY("cy"),
    
    /** The CZ. */
    @XmlEnumValue("cz")
    CZ("cz"),
    
    /** The DE. */
    @XmlEnumValue("de")
    DE("de"),
    
    /** The DK. */
    @XmlEnumValue("dk")
    DK("dk"),
    
    /** The EE. */
    @XmlEnumValue("ee")
    EE("ee"),
    
    /** The ES. */
    @XmlEnumValue("es")
    ES("es"),
    
    /** The FI. */
    @XmlEnumValue("fi")
    FI("fi"),
    
    /** The FO. */
    @XmlEnumValue("fo")
    FO("fo"),
    
    /** The FR. */
    @XmlEnumValue("fr")
    FR("fr"),
    
    /** The GB. */
    @XmlEnumValue("gb")
    GB("gb"),
    
    /** The GG. */
    @XmlEnumValue("gg")
    GG("gg"),
    
    /** The GI. */
    @XmlEnumValue("gi")
    GI("gi"),
    
    /** The GR. */
    @XmlEnumValue("gr")
    GR("gr"),
    
    /** The HR. */
    @XmlEnumValue("hr")
    HR("hr"),
    
    /** The HU. */
    @XmlEnumValue("hu")
    HU("hu"),
    
    /** The IE. */
    @XmlEnumValue("ie")
    IE("ie"),
    
    /** The IM. */
    @XmlEnumValue("im")
    IM("im"),
    
    /** The IS. */
    @XmlEnumValue("is")
    IS("is"),
    
    /** The IT. */
    @XmlEnumValue("it")
    IT("it"),
    
    /** The JE. */
    @XmlEnumValue("je")
    JE("je"),
    
    /** The LI. */
    @XmlEnumValue("li")
    LI("li"),
    
    /** The LT. */
    @XmlEnumValue("lt")
    LT("lt"),
    
    /** The LU. */
    @XmlEnumValue("lu")
    LU("lu"),
    
    /** The LV. */
    @XmlEnumValue("lv")
    LV("lv"),
    
    /** The MA. */
    @XmlEnumValue("ma")
    MA("ma"),
    
    /** The MC. */
    @XmlEnumValue("mc")
    MC("mc"),
    
    /** The MK. */
    @XmlEnumValue("mk")
    MK("mk"),
    
    /** The MT. */
    @XmlEnumValue("mt")
    MT("mt"),
    
    /** The NL. */
    @XmlEnumValue("nl")
    NL("nl"),
    
    /** The NO. */
    @XmlEnumValue("no")
    NO("no"),
    
    /** The PL. */
    @XmlEnumValue("pl")
    PL("pl"),
    
    /** The PT. */
    @XmlEnumValue("pt")
    PT("pt"),
    
    /** The RO. */
    @XmlEnumValue("ro")
    RO("ro"),
    
    /** The SE. */
    @XmlEnumValue("se")
    SE("se"),
    
    /** The SI. */
    @XmlEnumValue("si")
    SI("si"),
    
    /** The SK. */
    @XmlEnumValue("sk")
    SK("sk"),
    
    /** The SM. */
    @XmlEnumValue("sm")
    SM("sm"),
    
    /** The TR. */
    @XmlEnumValue("tr")
    TR("tr"),
    
    /** The VA. */
    @XmlEnumValue("va")
    VA("va"),
    
    /** The OTHER. */
    @XmlEnumValue("other")
    OTHER("other");
    
    /** The value. */
    private final String value;

    /**
     * Instantiates a new country enum.
     *
     * @param v the v
     */
    CountryEnum(final String v) {
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
     * @return the country enum
     */
    public static final CountryEnum fromValue(final String v) {
        for (CountryEnum c : CountryEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
