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
package openlr.xml;

import openlr.LocationReference;
import openlr.LocationType;
import openlr.PhysicalFormatException;
import openlr.map.GeoCoordinates;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.InvalidMapDataException;
import openlr.rawLocRef.RawLocationReference;
import openlr.rawLocRef.RawRectangleLocRef;
import openlr.xml.generated.AreaLocationReference;
import openlr.xml.generated.Coordinates;
import openlr.xml.generated.OpenLR;
import openlr.xml.generated.XMLLocationReference;
import openlr.xml.impl.LocationReferenceXmlImpl;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class RectangleTest {

    /**
     * Test rectangle1 encode.
     */
    @Test
    public final void testRectangle1Encode() {
        try {
            GeoCoordinates ll = new GeoCoordinatesImpl(5.09030, 52.08591);
            GeoCoordinates ur = new GeoCoordinatesImpl(5.12141, 52.10437);

            RawLocationReference rawLocRef = new RawRectangleLocRef(
                    "rectangle", ll, ur);
            OpenLRXMLEncoder encoder = new OpenLRXMLEncoder();
            LocationReference lr = encoder.encodeData(rawLocRef);
            Assert.assertTrue(lr.isValid());
            Assert.assertNotNull(lr.getLocationReferenceData());
            Assert.assertEquals(lr.getLocationType(), LocationType.RECTANGLE);
            checkEncodedLocation(lr, ll, ur);
        } catch (InvalidMapDataException e) {
            Assert.fail("Unexpected exception", e);
        }
    }

    /**
     * Test rectangle2 encode.
     */
    @Test
    public final void testRectangle2Encode() {
        try {
            GeoCoordinates ll = new GeoCoordinatesImpl(4.95941, 52.01232);
            GeoCoordinates ur = new GeoCoordinatesImpl(5.61497, 52.29168);
            RawLocationReference rawLocRef = new RawRectangleLocRef(
                    "rectangle", ll, ur);
            OpenLRXMLEncoder encoder = new OpenLRXMLEncoder();
            LocationReference lr = encoder.encodeData(rawLocRef);
            Assert.assertTrue(lr.isValid());
            Assert.assertNotNull(lr.getLocationReferenceData());
            Assert.assertEquals(lr.getLocationType(), LocationType.RECTANGLE);

            checkEncodedLocation(lr, ll, ur);
        } catch (InvalidMapDataException e) {
            Assert.fail("Unexpected exception", e);
        }
    }

    /**
     * Test rectangle1 decode.
     */
    @Test
    public final void testRectangle1Decode() {

        OpenLR rectangleLocation = Utils.readLocationFromFile("Rectangle1.xml",
                true);

        try {
            LocationReference lr = new LocationReferenceXmlImpl("rectangle",
                    rectangleLocation, 1);
            OpenLRXMLDecoder decoder = new OpenLRXMLDecoder();
            RawLocationReference rawLocRef = decoder.decodeData(lr);
            GeoCoordinates ll = new GeoCoordinatesImpl(5.09030, 52.08591);
            GeoCoordinates ur = new GeoCoordinatesImpl(5.12141, 52.10437);
            checkDecodedData(rawLocRef, ll, ur);
        } catch (PhysicalFormatException e) {
            Assert.fail("Unexpected exception", e);
        } catch (InvalidMapDataException e) {
            Assert.fail("Unexpected exception", e);
        }
    }

    /**
     * Test rectangle2 decode.
     */
    @Test
    public final void testRectangle2Decode() {
        OpenLR rectangleLocation = Utils.readLocationFromFile("Rectangle2.xml",
                true);
        try {
            LocationReference lr = new LocationReferenceXmlImpl("rectangle", rectangleLocation, 1
            );
            OpenLRXMLDecoder decoder = new OpenLRXMLDecoder();
            RawLocationReference rawLocRef = decoder.decodeData(lr);
            GeoCoordinates ll = new GeoCoordinatesImpl(4.95941, 52.01232);
            GeoCoordinates ur = new GeoCoordinatesImpl(5.61497, 52.29168);
            checkDecodedData(rawLocRef, ll, ur);
        } catch (PhysicalFormatException e) {
            Assert.fail("Unexpected exception", e);
        } catch (InvalidMapDataException e) {
            Assert.fail("Unexpected exception", e);
        }
    }

    public final void checkDecodedData(final RawLocationReference rawLocRef,
                                       final GeoCoordinates expectedLL, final GeoCoordinates expectedUR) {
        assertSame(rawLocRef.getLocationType(), LocationType.RECTANGLE);
        assertTrue(rawLocRef.isValid());
        assertNull(rawLocRef.getReturnCode());

        assertNull(rawLocRef.getOrientation());
        assertNull(rawLocRef.getSideOfRoad());
        assertNull(rawLocRef.getOffsets());
        assertNull(rawLocRef.getLocationReferencePoints());

        GeoCoordinates ll = rawLocRef.getLowerLeftPoint();
        GeoCoordinates ur = rawLocRef.getUpperRightPoint();

        assertEquals(ll.getLongitudeDeg(), expectedLL.getLongitudeDeg());
        assertEquals(ll.getLatitudeDeg(), expectedLL.getLatitudeDeg());
        assertEquals(ur.getLongitudeDeg(), expectedUR.getLongitudeDeg());
        assertEquals(ur.getLatitudeDeg(), expectedUR.getLatitudeDeg());

    }

    /**
     * Checks the given encode geo coordinates against the expected geo
     * coordinates.
     *
     * @param encodedRectangleLoc
     *            The encoded geo coordinate location.
     * @param expectedLL
     *            the expected ll
     * @param expectedUR
     *            the expected ur
     */
    private void checkEncodedLocation(
            final LocationReference encodedRectangleLoc,
            final GeoCoordinates expectedLL, final GeoCoordinates expectedUR) {

        assertSame(encodedRectangleLoc.getLocationType(),
                LocationType.RECTANGLE);
        assertTrue(encodedRectangleLoc.isValid());
        assertNull(encodedRectangleLoc.getReturnCode());

        XMLLocationReference xmLoc = ((OpenLR) encodedRectangleLoc
                .getLocationReferenceData()).getXMLLocationReference();

        assertNull(xmLoc.getLineLocationReference());
        assertNull(xmLoc.getPointLocationReference());
        AreaLocationReference aLoc = xmLoc.getAreaLocationReference();
        assertNull(aLoc.getClosedLineLocationReference());
        assertNull(aLoc.getGridLocationReference());
        Assert.assertNotNull(aLoc.getRectangleLocationReference());
        assertNull(aLoc.getPolygonLocationReference());
        assertNull(aLoc.getCircleLocationReference());
        Assert.assertNotNull(aLoc.getRectangleLocationReference()
                .getRectangle());
        Coordinates ll = aLoc.getRectangleLocationReference().getRectangle()
                .getLowerLeft();
        Coordinates ur = aLoc.getRectangleLocationReference().getRectangle()
                .getUpperRight();

        assertEquals(ll.getLatitude(), expectedLL.getLatitudeDeg());
        assertEquals(ll.getLongitude(), expectedLL.getLongitudeDeg());

        assertEquals(ur.getLatitude(), expectedUR.getLatitudeDeg());
        assertEquals(ur.getLongitude(), expectedUR.getLongitudeDeg());
    }

}
