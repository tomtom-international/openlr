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
 * <p>Java class for AccidentTypeEnum.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AccidentTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="accident"/>
 *     &lt;enumeration value="accidentInvolvingBicycles"/>
 *     &lt;enumeration value="accidentInvolvingBuses"/>
 *     &lt;enumeration value="accidentInvolvingHazardousMaterials"/>
 *     &lt;enumeration value="accidentInvolvingHeavyLorries"/>
 *     &lt;enumeration value="accidentInvolvingMassTransitVehicle"/>
 *     &lt;enumeration value="accidentInvolvingMopeds"/>
 *     &lt;enumeration value="accidentInvolvingMotorcycles"/>
 *     &lt;enumeration value="accidentInvolvingRadioactiveMaterial"/>
 *     &lt;enumeration value="accidentInvolvingTrain"/>
 *     &lt;enumeration value="chemicalSpillageAccident"/>
 *     &lt;enumeration value="collision"/>
 *     &lt;enumeration value="collisionWithAnimal"/>
 *     &lt;enumeration value="collisionWithObstruction"/>
 *     &lt;enumeration value="collisionWithPerson"/>
 *     &lt;enumeration value="earlierAccident"/>
 *     &lt;enumeration value="fuelSpillageAccident"/>
 *     &lt;enumeration value="headOnCollision"/>
 *     &lt;enumeration value="headOnOrSideCollision"/>
 *     &lt;enumeration value="jackknifedArticulatedLorry"/>
 *     &lt;enumeration value="jackknifedCaravan"/>
 *     &lt;enumeration value="jackknifedTrailer"/>
 *     &lt;enumeration value="multipleVehicleCollision"/>
 *     &lt;enumeration value="multivehicleAccident"/>
 *     &lt;enumeration value="oilSpillageAccident"/>
 *     &lt;enumeration value="overturnedHeavyLorry"/>
 *     &lt;enumeration value="overturnedTrailer"/>
 *     &lt;enumeration value="overturnedVehicle"/>
 *     &lt;enumeration value="rearCollision"/>
 *     &lt;enumeration value="secondaryAccident"/>
 *     &lt;enumeration value="seriousAccident"/>
 *     &lt;enumeration value="sideCollision"/>
 *     &lt;enumeration value="vehicleOffRoad"/>
 *     &lt;enumeration value="vehicleSpunAround"/>
 *     &lt;enumeration value="other"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 *
 */
@XmlType(name = "AccidentTypeEnum")
@XmlEnum
public enum AccidentTypeEnum {

    /** The ACCIDENT. */
    @XmlEnumValue("accident")
    ACCIDENT("accident"),

    /** The ACCIDEN t_ involvin g_ bicycles. */
    @XmlEnumValue("accidentInvolvingBicycles")
    ACCIDENT_INVOLVING_BICYCLES("accidentInvolvingBicycles"),

    /** The ACCIDEN t_ involvin g_ buses. */
    @XmlEnumValue("accidentInvolvingBuses")
    ACCIDENT_INVOLVING_BUSES("accidentInvolvingBuses"),

    /** The ACCIDEN t_ involvin g_ hazardou s_ materials. */
    @XmlEnumValue("accidentInvolvingHazardousMaterials")
    ACCIDENT_INVOLVING_HAZARDOUS_MATERIALS("accidentInvolvingHazardousMaterials"),

    /** The ACCIDEN t_ involvin g_ heav y_ lorries. */
    @XmlEnumValue("accidentInvolvingHeavyLorries")
    ACCIDENT_INVOLVING_HEAVY_LORRIES("accidentInvolvingHeavyLorries"),

    /** The ACCIDEN t_ involvin g_ mas s_ transi t_ vehicle. */
    @XmlEnumValue("accidentInvolvingMassTransitVehicle")
    ACCIDENT_INVOLVING_MASS_TRANSIT_VEHICLE("accidentInvolvingMassTransitVehicle"),

    /** The ACCIDEN t_ involvin g_ mopeds. */
    @XmlEnumValue("accidentInvolvingMopeds")
    ACCIDENT_INVOLVING_MOPEDS("accidentInvolvingMopeds"),

    /** The ACCIDEN t_ involvin g_ motorcycles. */
    @XmlEnumValue("accidentInvolvingMotorcycles")
    ACCIDENT_INVOLVING_MOTORCYCLES("accidentInvolvingMotorcycles"),

    /** The ACCIDEN t_ involvin g_ radioactiv e_ material. */
    @XmlEnumValue("accidentInvolvingRadioactiveMaterial")
    ACCIDENT_INVOLVING_RADIOACTIVE_MATERIAL("accidentInvolvingRadioactiveMaterial"),

    /** The ACCIDEN t_ involvin g_ train. */
    @XmlEnumValue("accidentInvolvingTrain")
    ACCIDENT_INVOLVING_TRAIN("accidentInvolvingTrain"),

    /** The CHEMICA l_ spillag e_ accident. */
    @XmlEnumValue("chemicalSpillageAccident")
    CHEMICAL_SPILLAGE_ACCIDENT("chemicalSpillageAccident"),

    /** The COLLISION. */
    @XmlEnumValue("collision")
    COLLISION("collision"),

    /** The COLLISIO n_ wit h_ animal. */
    @XmlEnumValue("collisionWithAnimal")
    COLLISION_WITH_ANIMAL("collisionWithAnimal"),

    /** The COLLISIO n_ wit h_ obstruction. */
    @XmlEnumValue("collisionWithObstruction")
    COLLISION_WITH_OBSTRUCTION("collisionWithObstruction"),

    /** The COLLISIO n_ wit h_ person. */
    @XmlEnumValue("collisionWithPerson")
    COLLISION_WITH_PERSON("collisionWithPerson"),

    /** The EARLIE r_ accident. */
    @XmlEnumValue("earlierAccident")
    EARLIER_ACCIDENT("earlierAccident"),

    /** The FUE l_ spillag e_ accident. */
    @XmlEnumValue("fuelSpillageAccident")
    FUEL_SPILLAGE_ACCIDENT("fuelSpillageAccident"),

    /** The HEA d_ o n_ collision. */
    @XmlEnumValue("headOnCollision")
    HEAD_ON_COLLISION("headOnCollision"),

    /** The HEA d_ o n_ o r_ sid e_ collision. */
    @XmlEnumValue("headOnOrSideCollision")
    HEAD_ON_OR_SIDE_COLLISION("headOnOrSideCollision"),

    /** The JACKKNIFE d_ articulate d_ lorry. */
    @XmlEnumValue("jackknifedArticulatedLorry")
    JACKKNIFED_ARTICULATED_LORRY("jackknifedArticulatedLorry"),

    /** The JACKKNIFE d_ caravan. */
    @XmlEnumValue("jackknifedCaravan")
    JACKKNIFED_CARAVAN("jackknifedCaravan"),

    /** The JACKKNIFE d_ trailer. */
    @XmlEnumValue("jackknifedTrailer")
    JACKKNIFED_TRAILER("jackknifedTrailer"),

    /** The MULTIPL e_ vehicl e_ collision. */
    @XmlEnumValue("multipleVehicleCollision")
    MULTIPLE_VEHICLE_COLLISION("multipleVehicleCollision"),

    /** The MULTIVEHICL e_ accident. */
    @XmlEnumValue("multivehicleAccident")
    MULTIVEHICLE_ACCIDENT("multivehicleAccident"),

    /** The OI l_ spillag e_ accident. */
    @XmlEnumValue("oilSpillageAccident")
    OIL_SPILLAGE_ACCIDENT("oilSpillageAccident"),

    /** The OVERTURNE d_ heav y_ lorry. */
    @XmlEnumValue("overturnedHeavyLorry")
    OVERTURNED_HEAVY_LORRY("overturnedHeavyLorry"),

    /** The OVERTURNE d_ trailer. */
    @XmlEnumValue("overturnedTrailer")
    OVERTURNED_TRAILER("overturnedTrailer"),

    /** The OVERTURNE d_ vehicle. */
    @XmlEnumValue("overturnedVehicle")
    OVERTURNED_VEHICLE("overturnedVehicle"),

    /** The REA r_ collision. */
    @XmlEnumValue("rearCollision")
    REAR_COLLISION("rearCollision"),

    /** The SECONDAR y_ accident. */
    @XmlEnumValue("secondaryAccident")
    SECONDARY_ACCIDENT("secondaryAccident"),

    /** The SERIOU s_ accident. */
    @XmlEnumValue("seriousAccident")
    SERIOUS_ACCIDENT("seriousAccident"),

    /** The SID e_ collision. */
    @XmlEnumValue("sideCollision")
    SIDE_COLLISION("sideCollision"),

    /** The VEHICL e_ of f_ road. */
    @XmlEnumValue("vehicleOffRoad")
    VEHICLE_OFF_ROAD("vehicleOffRoad"),

    /** The VEHICL e_ spu n_ around. */
    @XmlEnumValue("vehicleSpunAround")
    VEHICLE_SPUN_AROUND("vehicleSpunAround"),

    /** The OTHER. */
    @XmlEnumValue("other")
    OTHER("other");

    /** The value. */
    private final String value;

    /**
     * Instantiates a new accident type enum.
     *
     * @param v the v
     */
    AccidentTypeEnum(final String v) {
        value = v;
    }

    /**
     * From value.
     *
     * @param v the v
     * @return the accident type enum
     */
    public static final AccidentTypeEnum fromValue(final String v) {
        for (AccidentTypeEnum c : AccidentTypeEnum.values()) {
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
