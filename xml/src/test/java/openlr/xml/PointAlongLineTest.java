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

import openlr.*;
import openlr.location.data.Orientation;
import openlr.location.data.SideOfRoad;
import openlr.rawLocRef.RawLocationReference;
import openlr.rawLocRef.RawPointAlongLocRef;
import openlr.xml.OpenLRXMLException.XMLErrorType;
import openlr.xml.decoder.PointAlongDecoder;
import openlr.xml.generated.OpenLR;
import openlr.xml.generated.PointAlongLine;
import openlr.xml.generated.PointLocationReference;
import openlr.xml.generated.XMLLocationReference;
import openlr.xml.impl.LocationReferenceXmlImpl;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;

/**
 * The Class OpenLRBinaryEncoderTest.
 */
public class PointAlongLineTest {

    /** The Constant BINARY_ENCODER. */
    private static final OpenLRXMLEncoder XML_ENCODER = new OpenLRXMLEncoder();

    /** The used XML encoder object. */
    private static final OpenLRXMLDecoder XML_DECODER = new OpenLRXMLDecoder();

    /**
     * The positive offset value of the mocked offset for the white paper
     * encoder example.
     */
    private static final int POS_OFFSET_WP_EXAMPLE = 28;
    /** The LRP data used for the white paper example. */
    private static final Lrp[] LRP_WP_EXAMPLE = new Lrp[]{Lrp.PL_ENC_LRP1, Lrp.PL_ENC_LRP2};
    /**
     * The LRPs of the white paper encoding example.
     */
    private List<LocationReferencePoint> lrpsWpEaxmple;
    /** The offsets of the white paper encoding example. */
    private Offsets offsetsWpExample;
    /** A reference to the valid encoded location of the white paper example. */
    private LocationReference encodedWhitePaperLocation;

    /**
     * Setup.
     */
    @BeforeTest
    public final void setup() {
        Mockery context = new Mockery();
        offsetsWpExample = context.mock(Offsets.class, "offsetsWpExample");

        context.checking(new Expectations() {
            {
                allowing(offsetsWpExample).hasNegativeOffset();
                will(returnValue(false));
            }

            {
                allowing(offsetsWpExample).hasPositiveOffset();
                will(returnValue(true));
            }

            {
                allowing(offsetsWpExample).getPositiveOffset(with(any(int.class)));
                will(returnValue(POS_OFFSET_WP_EXAMPLE));
            }

            {
                allowing(offsetsWpExample).getNegativeOffset(with(any(int.class)));
                will(returnValue(0));
            }
        });

        lrpsWpEaxmple = mockLrps12(context);
    }

    /**
     * Mocks LRPs 1 and 2 that are used in tests of this class.
     *
     * @param context
     *            The mocking context.
     * @return A list of both LRPs ordered in sequence 1, 2.
     */
    private List<LocationReferencePoint> mockLrps12(final Mockery context) {

        final List<LocationReferencePoint> lrps = new ArrayList<LocationReferencePoint>(2);

        for (final Lrp lrp : LRP_WP_EXAMPLE) {

            final LocationReferencePoint mockedLrp = context.mock(
                    LocationReferencePoint.class, lrp.name());

            context.checking(new Expectations() {
                {
                    allowing(mockedLrp).getLongitudeDeg();
                    will(returnValue(lrp.getLongitude()));
                }

                {
                    allowing(mockedLrp).getLatitudeDeg();
                    will(returnValue(lrp.getLatitude()));
                }

                {
                    allowing(mockedLrp).getBearing();
                    will(returnValue(lrp.getBearing()));
                }

                {
                    allowing(mockedLrp).getDistanceToNext();
                    will(returnValue(lrp.getDistanceToNext()));
                }

                {
                    allowing(mockedLrp).getFOW();
                    will(returnValue(lrp.getFow()));
                }

                {
                    allowing(mockedLrp).getFRC();
                    will(returnValue(lrp.getFrc()));
                }

                {
                    allowing(mockedLrp).getLfrc();
                    will(returnValue(lrp.getLfrcnp()));
                }

                {
                    allowing(mockedLrp).isLastLRP();
                    if (lrp == Lrp.LINE_ENC_LRP2) {
                        will(returnValue(true));
                    } else {
                        will(returnValue(false));
                    }
                }
            });

            lrps.add(mockedLrp);
        }

        return lrps;
    }


    /**
     * Tests encoding of the white paper example.
     */
    @Test
    public final void testWhitePaperExampleEncoding() {

        RawLocationReference rawLocRef = new RawPointAlongLocRef("",
                lrpsWpEaxmple.get(0), lrpsWpEaxmple.get(1), offsetsWpExample, SideOfRoad.LEFT,
                Orientation.NO_ORIENTATION_OR_UNKNOWN);
        try {
            encodedWhitePaperLocation = XML_ENCODER.encodeData(rawLocRef);
            checkWhitePaperEncodedLocation(encodedWhitePaperLocation);

        } catch (Exception e) {
            fail("Unexpected exception!", e);
        }

        if (!XML_ENCODER.getDataFormatIdentifier().equals(
                encodedWhitePaperLocation.getDataIdentifier())) {
            fail("Invalid data identifier");
        }

        assertEquals(encodedWhitePaperLocation.getDataClass(), XML_ENCODER
                .getDataClass(), "invalid data class");

        if (encodedWhitePaperLocation.getLocationReferenceData() == null) {
            fail("loc ref data is null but valid");
        }
    }

    /**
     * Checks the given encode geo coordinates against the expected geo
     * coordinates.
     *
     * @param encodePointAlongLoc
     *            The encoded geo coordinate location.
     */
    private void checkWhitePaperEncodedLocation(final LocationReference encodePointAlongLoc) {

        assertSame(encodePointAlongLoc.getLocationType(), LocationType.POINT_ALONG_LINE);
        assertTrue(encodePointAlongLoc.isValid());
        assertNull(encodePointAlongLoc.getReturnCode());

        XMLLocationReference xmLoc = ((OpenLR) encodePointAlongLoc
                .getLocationReferenceData()).getXMLLocationReference();

        assertNull(xmLoc.getLineLocationReference());

        PointLocationReference pLoc = xmLoc.getPointLocationReference();
        assertNull(pLoc.getGeoCoordinate());
        assertNull(pLoc.getPoiWithAccessPoint());

        PointAlongLine pal = pLoc.getPointAlongLine();

        Utils.checkLRPs(Arrays.asList(pal.getLocationReferencePoint()), pal
                .getLastLocationReferencePoint(), lrpsWpEaxmple);

        Utils.checkOffsets(pal.getOffsets(), offsetsWpExample);

        assertEquals(pal.getOrientation().name(),
                Orientation.NO_ORIENTATION_OR_UNKNOWN.name());
        assertEquals(pal.getSideOfRoad().name(), SideOfRoad.LEFT.name());
    }

    /**
     * Tests the binary encoding with an invalid version specified.
     */
    @Test
    public final void testWrongVersion() {

        RawLocationReference rawLocRef = new RawPointAlongLocRef("",
                lrpsWpEaxmple.get(0), lrpsWpEaxmple.get(1), offsetsWpExample, SideOfRoad.RIGHT,
                Orientation.BOTH);

        LocationReference result = XML_ENCODER.encodeData(rawLocRef,
                Integer.MAX_VALUE);
        assertFalse(result.isValid());

        assertSame(result.getReturnCode(), XmlReturnCode.INVALID_VERSION);
    }

    /**
     * Tests a writing and re-reading of a XML location reference.
     */
    @Test(dependsOnMethods = {"testWhitePaperExampleEncoding"})
    public final void testWriter() {

        OpenLR writtenData = (OpenLR) encodedWhitePaperLocation
                .getLocationReferenceData();
        OpenLR readData = null;

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            OpenLRXmlWriter writer = new OpenLRXmlWriter();
            writer.saveOpenLRXML(writtenData, out, true);

            ByteArrayInputStream in = new ByteArrayInputStream(out
                    .toByteArray());

            OpenLRXmlReader reader = new OpenLRXmlReader();

            readData = reader.readOpenLRXML(in, true);

        } catch (Exception e) {
            fail("Unexpected exception!", e);
        }

        assertEquals(readData, writtenData, "Writing and re-reading of the "
                + "location delivered unequal results!");
    }

    /**
     * Tests decoding of the white paper example.
     */
    @Test
    public final void testWhitePaperExampleDecoding() {

        OpenLR wpPointAlongLocation = Utils
                .readLocationFromFile("whitePaperPointAlongLocation.xml", true);

        try {
            LocationReference lr = new LocationReferenceXmlImpl("", wpPointAlongLocation, 1);
            RawLocationReference rawLocRef = XML_DECODER
                    .decodeData(lr);

            assertSame(rawLocRef.getLocationType(),
                    LocationType.POINT_ALONG_LINE);
            assertTrue(rawLocRef.isValid());
            assertNull(rawLocRef.getReturnCode());

            Utils.checkDecodedLrps(rawLocRef, LRP_WP_EXAMPLE, true);
            checkOffsets(rawLocRef.getOffsets(), POS_OFFSET_WP_EXAMPLE, null);

            assertNull(rawLocRef.getGeoCoordinates());
            assertEquals(rawLocRef.getOrientation(),
                    Orientation.NO_ORIENTATION_OR_UNKNOWN);
            assertEquals(rawLocRef.getSideOfRoad(), SideOfRoad.LEFT);

        } catch (PhysicalFormatException e) {
            fail("Unexpected exception!", e);
        }
    }


    /**
     * Checks the given offsets against the specified values. .
     *
     * @param offset
     *            The offsets object to check.
     * @param expectedPosOffset
     *            The expected positive offset or <code>null</code> if none is
     *            expected.
     * @param expectedNegativeOffset
     *            The expected negative offset or <code>null</code> if none is
     *            expected.
     */
    private void checkOffsets(final Offsets offset,
                              final Integer expectedPosOffset,
                              final Integer expectedNegativeOffset) {

        assertTrue(offset.hasPositiveOffset() == (expectedPosOffset != null));
        assertTrue(offset.hasNegativeOffset() == (expectedNegativeOffset != null));
        if (expectedPosOffset != null) {
            assertEquals(offset.getPositiveOffset(0), expectedPosOffset
                    .intValue());
        }
        if (expectedNegativeOffset != null) {
            assertEquals(offset.getNegativeOffset(0), expectedNegativeOffset
                    .intValue());
        }
    }

    /**
     * Tests the case of providing an invalid location class to the
     * {@link PointAlongDecoder}.
     */
    @Test
    public final void testWrongDataClassDecoding() {

        OpenLR wpPaperLineLocation = Utils.readLocationFromFile(
                "whitePaperLineLocation.xml", true);

        try {
            new PointAlongDecoder().decodeData("", wpPaperLineLocation);
            fail("Exception expected!");

        } catch (PhysicalFormatException e) {
            assertSame(e.getErrorCode(), XMLErrorType.DATA_ERROR);
        }
    }
}
