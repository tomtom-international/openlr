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

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the eu.datex2.schema._2_0rc2._2_0 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 *
 */
@XmlRegistry
public class ObjectFactory {

    /** The Constant _D2LogicalModel_QNAME. */
    private static final QName D2_LOGICAL_MODEL_QNAME = new QName("http://datex2.eu/schema/2_0RC2/2_0", "d2LogicalModel");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: eu.datex2.schema._2_0rc2._2_0
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link OpenlrLineAttributes }.
     *
     * @return the openlr line attributes
     */
    public final OpenlrLineAttributes createOpenlrLineAttributes() {
        return new OpenlrLineAttributes();
    }

    /**
     * Create an instance of {@link OpenlrPoiWithAccessPoint }.
     *
     * @return the openlr poi with access point
     */
    public final OpenlrPoiWithAccessPoint createOpenlrPoiWithAccessPoint() {
        return new OpenlrPoiWithAccessPoint();
    }

    /**
     * Create an instance of {@link Situation }.
     *
     * @return the situation
     */
    public final Situation createSituation() {
        return new Situation();
    }

    /**
     * Create an instance of {@link SituationPublication }.
     *
     * @return the situation publication
     */
    public final SituationPublication createSituationPublication() {
        return new SituationPublication();
    }

    /**
     * Create an instance of {@link OverallPeriod }.
     *
     * @return the overall period
     */
    public final OverallPeriod createOverallPeriod() {
        return new OverallPeriod();
    }

    /**
     * Create an instance of {@link OpenlrExtendedPoint }.
     *
     * @return the openlr extended point
     */
    public final OpenlrExtendedPoint createOpenlrExtendedPoint() {
        return new OpenlrExtendedPoint();
    }

    /**
     * Create an instance of {@link PointCoordinates }.
     *
     * @return the point coordinates
     */
    public final PointCoordinates createPointCoordinates() {
        return new PointCoordinates();
    }

    /**
     * Create an instance of {@link ExtensionType }.
     *
     * @return the extension type
     */
    public final ExtensionType createExtensionType() {
        return new ExtensionType();
    }

    /**
     * Create an instance of {@link Exchange }.
     *
     * @return the exchange
     */
    public final Exchange createExchange() {
        return new Exchange();
    }

    /**
     * Create an instance of {@link OpenlrLineLocationReference }.
     *
     * @return the openlr line location reference
     */
    public final OpenlrLineLocationReference createOpenlrLineLocationReference() {
        return new OpenlrLineLocationReference();
    }

    /**
     * Create an instance of {@link OpenlrGeoCoordinate }.
     *
     * @return the openlr geo coordinate
     */
    public final OpenlrGeoCoordinate createOpenlrGeoCoordinate() {
        return new OpenlrGeoCoordinate();
    }

    /**
     * Create an instance of {@link Accident }.
     *
     * @return the accident
     */
    public final Accident createAccident() {
        return new Accident();
    }

    /**
     * Create an instance of {@link OpenlrLocationReferencePoint }.
     *
     * @return the openlr location reference point
     */
    public final OpenlrLocationReferencePoint createOpenlrLocationReferencePoint() {
        return new OpenlrLocationReferencePoint();
    }

    /**
     * Create an instance of {@link OpenlrPointLocationReference }.
     *
     * @return the openlr point location reference
     */
    public final OpenlrPointLocationReference createOpenlrPointLocationReference() {
        return new OpenlrPointLocationReference();
    }

    /**
     * Create an instance of {@link D2LogicalModel }.
     *
     * @return the d2 logical model
     */
    public final D2LogicalModel createD2LogicalModel() {
        return new D2LogicalModel();
    }

    /**
     * Create an instance of {@link OpenlrPointAlongLine }.
     *
     * @return the openlr point along line
     */
    public final OpenlrPointAlongLine createOpenlrPointAlongLine() {
        return new OpenlrPointAlongLine();
    }

    /**
     * Create an instance of {@link OpenlrPathAttributes }.
     *
     * @return the openlr path attributes
     */
    public final OpenlrPathAttributes createOpenlrPathAttributes() {
        return new OpenlrPathAttributes();
    }

    /**
     * Create an instance of {@link LinearExtensionType }.
     *
     * @return the linear extension type
     */
    public final LinearExtensionType createLinearExtensionType() {
        return new LinearExtensionType();
    }

    /**
     * Create an instance of {@link OpenlrOffsets }.
     *
     * @return the openlr offsets
     */
    public final OpenlrOffsets createOpenlrOffsets() {
        return new OpenlrOffsets();
    }

    /**
     * Create an instance of {@link Point }.
     *
     * @return the point
     */
    public final Point createPoint() {
        return new Point();
    }

    /**
     * Create an instance of {@link InternationalIdentifier }.
     *
     * @return the international identifier
     */
    public final InternationalIdentifier createInternationalIdentifier() {
        return new InternationalIdentifier();
    }

    /**
     * Create an instance of {@link OpenlrLastLocationReferencePoint }.
     *
     * @return the openlr last location reference point
     */
    public final OpenlrLastLocationReferencePoint createOpenlrLastLocationReferencePoint() {
        return new OpenlrLastLocationReferencePoint();
    }

    /**
     * Create an instance of {@link OpenlrExtendedLinear }.
     *
     * @return the openlr extended linear
     */
    public final OpenlrExtendedLinear createOpenlrExtendedLinear() {
        return new OpenlrExtendedLinear();
    }

    /**
     * Create an instance of {@link Linear }.
     *
     * @return the linear
     */
    public final Linear createLinear() {
        return new Linear();
    }

    /**
     * Create an instance of {@link HeaderInformation }.
     *
     * @return the header information
     */
    public final HeaderInformation createHeaderInformation() {
        return new HeaderInformation();
    }

    /**
     * Create an instance of {@link Validity }.
     *
     * @return the validity
     */
    public final Validity createValidity() {
        return new Validity();
    }

    /**
     * Create an instance of {@link PointExtensionType }.
     *
     * @return the point extension type
     */
    public final PointExtensionType createPointExtensionType() {
        return new PointExtensionType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link D2LogicalModel }{@code >}}.
     *
     * @param value the value
     * @return the JAXB element< d2 logical model>
     */
    @XmlElementDecl(namespace = "http://datex2.eu/schema/2_0RC2/2_0", name = "d2LogicalModel")
    public final JAXBElement<D2LogicalModel> createD2LogicalModel(final D2LogicalModel value) {
        return new JAXBElement<D2LogicalModel>(D2_LOGICAL_MODEL_QNAME, D2LogicalModel.class, null, value);
    }

}
