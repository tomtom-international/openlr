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
package openlr.xml;

import openlr.LocationReference;
import openlr.LocationReferencePoint;
import openlr.LocationType;
import openlr.Offsets;
import openlr.rawLocRef.RawLineLocRef;
import openlr.rawLocRef.RawLocationReference;
import openlr.xml.generated.LineLocationReference;
import openlr.xml.generated.OpenLR;
import openlr.xml.generated.XMLLocationReference;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

/**
 * The Class OpenLRBinaryEncoderTest.
 */
public class OpenLRXMLEncoderTest {

    /** The positive offset of the line location from the white paper example. */
    private static final int POS_OFFSET_WP_LINE_EXAMPLE = 150;

    /**
     * The encoder object used for the tests.
     */
    private static final OpenLRXMLEncoder XML_ENCODER = new OpenLRXMLEncoder();

    /** The pointsWPLineExample. */
    private List<LocationReferencePoint> pointsWPLineExample;

    /** A mocked offset object representing unset offsets. */
    private Offsets nullOffsets;

    /** The offsets of the white paper line location example. */
    private Offsets offsetsWPLine;

    /** The location reference of the White Paper (v. 1.3) example. */
    private RawLocationReference lineLocWhitePaperExample;

    /** A valid encoded location. */
    private LocationReference validEncodeLocation;

    /**
     * Setup.
     */
    @BeforeTest
    public final void setup() {
        pointsWPLineExample = new ArrayList<LocationReferencePoint>();
        Mockery context = new Mockery();

        mockOffsets(context);
        Lrp[] lrpsToMock = new Lrp[]{Lrp.LINE_ENC_LRP1, Lrp.LINE_ENC_LRP2,
                Lrp.LINE_ENC_LRP3};
        pointsWPLineExample = Utils.mockLrps123(context, lrpsToMock);

        lineLocWhitePaperExample = new RawLineLocRef("locOff",
                pointsWPLineExample, offsetsWPLine);
    }

    /**
     * Mocks some offsets used in the tests.
     *
     * @param context
     *            The mocking object.
     */
    private void mockOffsets(final Mockery context) {

        nullOffsets = context.mock(Offsets.class, "nullOffsets");
        offsetsWPLine = context.mock(Offsets.class, "offsetsWPLine");
        context.checking(new Expectations() {
            {
                allowing(nullOffsets).hasNegativeOffset();
                will(returnValue(false));
            }

            {
                allowing(nullOffsets).hasPositiveOffset();
                will(returnValue(false));
            }

            {
                allowing(nullOffsets).getPositiveOffset(0);
                will(returnValue(0));
            }

            {
                allowing(nullOffsets).getNegativeOffset(0);
                will(returnValue(0));
            }
        });
        context.checking(new Expectations() {
            {
                allowing(offsetsWPLine).hasNegativeOffset();
                will(returnValue(false));
            }

            {
                allowing(offsetsWPLine).hasPositiveOffset();
                will(returnValue(true));
            }

            {
                allowing(offsetsWPLine).getPositiveOffset(
                        with(any(Integer.class)));
                will(returnValue(POS_OFFSET_WP_LINE_EXAMPLE));
            }

            {
                allowing(offsetsWPLine).getNegativeOffset(
                        with(any(Integer.class)));
                will(returnValue(0));
            }
        });
    }

    /**
     * Tests the encoding of the line location as described in the OpenLR white
     * paper (v. 1.3).
     */
    @Test
    public final void testWhitePaperLineLocation() {
        LocationReference ref = XML_ENCODER
                .encodeData(lineLocWhitePaperExample);

        XMLLocationReference xmlLoc = ((OpenLR) ref.getLocationReferenceData())
                .getXMLLocationReference();

        LineLocationReference lineLoc = xmlLoc.getLineLocationReference();

        Utils.checkLRPs(lineLoc.getLocationReferencePoint(),
                lineLoc.getLastLocationReferencePoint(), pointsWPLineExample);

        Utils.checkOffsets(lineLoc.getOffsets(), offsetsWPLine);

        validEncodeLocation = ref;
    }

    /**
     * Tests the case of missing offsets in the input location.
     */
    @Test
    public final void testMissingOffsets() {
        RawLineLocRef rawLoc = new RawLineLocRef("1",
                new ArrayList<LocationReferencePoint>(), null);
        LocationReference locRef = XML_ENCODER.encodeData(rawLoc);
        assertFalse(locRef.isValid());

    }

    /**
     * Tests encoding of a valid line location without offsets.
     */
    @Test
    public final void testWithoutOffsets() {
        LocationReference ref = null;
        RawLocationReference rawLocRef = new RawLineLocRef("no-offset",
                pointsWPLineExample, nullOffsets);

        ref = XML_ENCODER.encodeData(rawLocRef);
        assertTrue(ref.isValid());
        assertSame(ref.getLocationType(), LocationType.LINE_LOCATION);

        if (!XML_ENCODER.getDataFormatIdentifier().equals(
                ref.getDataIdentifier())) {
            fail("Invalid data identifier");
        }
        if (!checkDataClass(ref)) {
            fail("invalid data class");
        }
        if (ref.getLocationReferenceData() == null) {
            fail("loc ref data is null but valid");
        }

        XMLLocationReference xmlLocRef = ((OpenLR) ref
                .getLocationReferenceData()).getXMLLocationReference();

        LineLocationReference lineLoc = xmlLocRef.getLineLocationReference();

        Utils.checkLRPs(lineLoc.getLocationReferencePoint(),
                lineLoc.getLastLocationReferencePoint(), pointsWPLineExample);
        Utils.checkOffsets(lineLoc.getOffsets(), nullOffsets);
    }

    /**
     * Check data class.
     *
     * @param ref
     *            the ref
     *
     * @return true, if successful
     */
    private boolean checkDataClass(final LocationReference ref) {
        return (ref.getDataClass() == XML_ENCODER.getDataClass());
    }

    /**
     * Test encoding with all supported versions.
     */
    @Test
    public final void testVersions() {
        LocationReference ref;

        int[] versions = XML_ENCODER.getSupportedVersions();
        for (int version : versions) {
            ref = XML_ENCODER.encodeData(lineLocWhitePaperExample, version);
            assertTrue(ref.isValid(), "Invalid location after encoding with"
                    + " xml version " + version);
            assertSame(ref.getVersion(), version);

        }

        // test unknown version
        ref = XML_ENCODER.encodeData(lineLocWhitePaperExample,
                Integer.MAX_VALUE);
        assertSame(ref.getReturnCode(), XmlReturnCode.INVALID_VERSION);

    }

    /**
     * Tests writing an encoded location reference to a stream and checks the
     * result by re-creating the same location from the output.
     */
    @Test(dependsOnMethods = {"testWhitePaperLineLocation"})
    public final void testToStream() {

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        try {
            validEncodeLocation.toStream(os);

            ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());

            OpenLRXmlReader reader = new OpenLRXmlReader();

            OpenLR readData = reader.readOpenLRXML(is, true);

            assertEquals(readData,
                    validEncodeLocation.getLocationReferenceData(),
                    "Writing and re-reading of the "
                            + "location delivered unequal results!");
        } catch (Exception e) {
            fail("Unexpected exception!", e);
        }
    }

    /**
     * Tests a writing and re-reading of a XML location reference.
     */
    @Test(dependsOnMethods = {"testWhitePaperLineLocation"})
    public final void testStreamWriter() {

        OpenLR writtenData = (OpenLR) validEncodeLocation
                .getLocationReferenceData();
        OpenLR readData = null;

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            OpenLRXmlWriter writer = new OpenLRXmlWriter();
            writer.saveOpenLRXML(writtenData, out, true);

            ByteArrayInputStream in = new ByteArrayInputStream(
                    out.toByteArray());

            OpenLRXmlReader reader = new OpenLRXmlReader();

            readData = reader.readOpenLRXML(in, true);

        } catch (Exception e) {
            fail("Unexpected exception!", e);
        }
        assertEquals(readData.toString(), writtenData.toString(),
                "Writing and re-reading of the "
                        + "location delivered unequal results!");
    }


    /**
     * Tests a writing and re-reading of a XML location reference.
     */
    @Test(dependsOnMethods = {"testWhitePaperLineLocation"})
    public final void testFileWriter() {

        OpenLR writtenData = (OpenLR) validEncodeLocation
                .getLocationReferenceData();
        OpenLR readData = null;

        try {

            File file = File.createTempFile("testlocation", ".xml");
            file.deleteOnExit();
            FileWriter out = new FileWriter(file);

            OpenLRXmlWriter writer = new OpenLRXmlWriter();
            writer.saveOpenLRXML(writtenData, out, true);

            out.close();

            OpenLRXmlReader reader = new OpenLRXmlReader();

            readData = reader.readOpenLRXML(file, false);

        } catch (Exception e) {
            fail("Unexpected exception!", e);
        }

        assertEquals(readData, writtenData, "Writing and re-reading of the "
                + "location delivered unequal results!");
    }

}
