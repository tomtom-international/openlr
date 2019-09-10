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

import openlr.LocationReferencePoint;
import openlr.Offsets;
import openlr.map.FunctionalRoadClass;
import openlr.rawLocRef.RawLocationReference;
import openlr.xml.generated.*;
import org.jmock.Expectations;
import org.jmock.Mockery;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Provides some utility methods for tests of this OpenLR package.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public final class Utils {

    /**
     * Disabled constructor.
     */
    private Utils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Maps a OpenLR-XML {@link FRCType} to an OpenLR
     * {@link FunctionalRoadClass}.
     *
     * @param frc
     *            the OpenLR-XML FRC identifier
     * @return The corresponding {@link FunctionalRoadClass}
     */
    static FunctionalRoadClass mapFRC(final FRCType frc) {
        FunctionalRoadClass f = null;
        switch (frc) {
            case FRC_0:
                f = FunctionalRoadClass.FRC_0;
                break;
            case FRC_1:
                f = FunctionalRoadClass.FRC_1;
                break;
            case FRC_2:
                f = FunctionalRoadClass.FRC_2;
                break;
            case FRC_3:
                f = FunctionalRoadClass.FRC_3;
                break;
            case FRC_4:
                f = FunctionalRoadClass.FRC_4;
                break;
            case FRC_5:
                f = FunctionalRoadClass.FRC_5;
                break;
            case FRC_6:
                f = FunctionalRoadClass.FRC_6;
                break;
            case FRC_7:
                f = FunctionalRoadClass.FRC_7;
                break;
            default:
                f = null;
        }
        return f;
    }

    /**
     * Checks the given location reference points from an encoding process
     * against the given expected data.
     *
     * @param lrps
     *            The LRPs to check.
     * @param lastLrp
     *            The last LRP of the checked location.
     * @param expectedLrps
     *            The expected LRPs.
     */
    static void checkLRPs(
            final List<openlr.xml.generated.LocationReferencePoint> lrps,
            final LastLocationReferencePoint lastLrp,
            final List<LocationReferencePoint> expectedLrps) {
        if (lastLrp != null) {
            assertEquals(lrps.size() + 1, expectedLrps.size());
        } else {
            assertEquals(lrps.size(), expectedLrps.size());
        }

        int i = 0;
        for (openlr.xml.generated.LocationReferencePoint lrp : lrps) {

            LocationReferencePoint expected = expectedLrps.get(i);
            String message = " when checking lrp " + i;

            Coordinates coords = lrp.getCoordinates();

            assertEquals(coords.getLongitude(), expected.getLongitudeDeg(),
                    message);
            assertEquals(coords.getLatitude(), expected.getLatitudeDeg(),
                    message);

            LineAttributes lineAttr = lrp.getLineAttributes();

            assertEquals(lineAttr.getBEAR(), Math.round(expected.getBearing()),
                    message);

            assertEquals(lineAttr.getFOW().value(), expected.getFOW().name(),
                    message);
            assertEquals(Utils.mapFRC(lineAttr.getFRC()), expected.getFRC(),
                    message);

            PathAttributes pathAttr = lrp.getPathAttributes();
            assertEquals(pathAttr.getDNP().intValue(),
                    expected.getDistanceToNext(), message);
            assertEquals(Utils.mapFRC(pathAttr.getLFRCNP()),
                    expected.getLfrc(), message);

            i++;
        }

        if (lastLrp != null) {
            LocationReferencePoint expected = expectedLrps.get(i++);

            Coordinates coords = lastLrp.getCoordinates();

            assertEquals(coords.getLongitude(), expected.getLongitudeDeg());
            assertEquals(coords.getLatitude(), expected.getLatitudeDeg());

            LineAttributes lineAttr = lastLrp.getLineAttributes();

            assertEquals(lineAttr.getBEAR(), Math.round(expected.getBearing()));
            assertEquals(lineAttr.getFOW().value(), expected.getFOW().name());
            assertEquals(Utils.mapFRC(lineAttr.getFRC()), expected.getFRC());
        }
    }

    /**
     * Checks the given XML offsets against the expected.
     *
     * @param actual
     *            The offsets to check.
     * @param expected
     *            The expected offsets.
     */
    static void checkOffsets(final openlr.xml.generated.Offsets actual,
                             final Offsets expected) {

        if (expected.hasPositiveOffset()) {
            assertEquals(actual.getPosOff().intValue(),
                    expected.getPositiveOffset(0));
        } else {
            assertEquals(actual.getPosOff().intValue(), 0);
        }

        if (expected.hasNegativeOffset()) {
            assertEquals(actual.getNegOff().intValue(),
                    expected.getNegativeOffset(0));
        } else {
            assertEquals(actual.getNegOff().intValue(), 0);
        }
    }

    /**
     * Reads a location from the file specified by the given file name.
     *
     * @param fileName
     *            The file name, searched in the class path.
     * @param validate
     *            Flag indication if this read location reference XML shall be
     *            validated.
     * @return The build up {@link OpenLR} object.
     */
    public static OpenLR readLocationFromFile(final String fileName,
                                              final boolean validate) {

        OpenLR result = null;
        try {
            OpenLRXmlReader reader = new OpenLRXmlReader();
            InputStream stream = Utils.class.getClassLoader()
                    .getResourceAsStream(fileName);

            result = reader.readOpenLRXML(stream, validate);

        } catch (Exception e) {
            fail("Unexpected exception!", e);
        }
        return result;
    }

    /**
     * Checks the LRPs of the decoded location against the expected values.
     *
     * @param locRef
     *            The decoded location.
     * @param expectedLrps
     *            The expected LRP data.
     */
    static void checkDecodedLrps(final RawLocationReference locRef,
                                 final Lrp[] expectedLrps, final boolean hasLastIndicator) {

        List<? extends LocationReferencePoint> lrps = locRef
                .getLocationReferencePoints();

        assertSame(lrps.size(), expectedLrps.length);

        LocationReferencePoint lrp;

        for (int i = 0; i < expectedLrps.length; i++) {

            lrp = lrps.get(i);
            String message = " when checking lrp " + i;
            if (hasLastIndicator) {
                assertTrue(lrp.isLastLRP() == (i == expectedLrps.length - 1),
                        message);
            }

            assertEquals(lrp.getLongitudeDeg(), expectedLrps[i].getLongitude(),
                    message);
            assertEquals(lrp.getLatitudeDeg(), expectedLrps[i].getLatitude(),
                    message);
            assertEquals(lrp.getFOW(), expectedLrps[i].getFow(), message);
            assertEquals(lrp.getDistanceToNext(),
                    expectedLrps[i].getDistanceToNext(), message);
            assertEquals(lrp.getFRC(), expectedLrps[i].getFrc(), message);
            assertEquals(lrp.getLfrc(), expectedLrps[i].getLfrcnp(), message);
            assertEquals(lrp.getBearing(), expectedLrps[i].getBearing(),
                    message);
        }
    }

    /**
     * Mocks LINE_ENC_LRP1, LINE_ENC_LRP2 and LINE_ENC_LRP3 which are commonly
     * used in some tests.
     *
     * @param context
     *            The mocking context.
     * @return A list of the three mocked objects in sequence 1, 2, 3
     */
    public static List<LocationReferencePoint> mockLrps123(
            final Mockery context, final Lrp[] lrpsToMock) {

        final List<LocationReferencePoint> points123 = new ArrayList<LocationReferencePoint>(
                lrpsToMock.length);

        for (int i = 0; i < lrpsToMock.length; i++) {
            final Lrp lrp = lrpsToMock[i];

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
            });
            if (i == lrpsToMock.length - 1) {
                context.checking(new Expectations() {
                    {
                        allowing(mockedLrp).isLastLRP();
                        will(returnValue(true));
                    }
                });
            } else {
                context.checking(new Expectations() {
                    {
                        allowing(mockedLrp).isLastLRP();
                        will(returnValue(false));
                    }
                });
            }
            points123.add(mockedLrp);
        }

        return points123;
    }
}
