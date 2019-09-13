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
package openlr.datex2;

import openlr.LocationReference;
import openlr.LocationReferencePoint;
import openlr.Offsets;
import openlr.PhysicalFormatException;
import openlr.location.data.Orientation;
import openlr.location.data.SideOfRoad;
import openlr.map.*;
import openlr.rawLocRef.RawLocationReference;
import openlr.rawLocRef.RawPoiAccessLocRef;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.*;

import static org.testng.Assert.fail;

/**
 * The Class OpenLRBinaryEncoderTest.
 */
public class PoiWithAccessTest {

    /** The Constant BINARY_ENCODER. */
    private static final OpenLRDatex2Encoder BINARY_ENCODER = new OpenLRDatex2Encoder();

    private static final File TEST_DATA = new File("src/test/resources/poiaccess.txt");


    private LocationReferencePoint lrp1;

    private LocationReferencePoint lrp2;

    private GeoCoordinates coord;

    /** The off1. */
    private Offsets off1;

    private SideOfRoad sor = SideOfRoad.LEFT;

    private Orientation o = Orientation.NO_ORIENTATION_OR_UNKNOWN;

    private RawLocationReference rawLocRef;


    /**
     * Setup.
     */
    @BeforeTest
    public final void setup() {
        Mockery context = new Mockery();
        off1 = context.mock(Offsets.class, "off1");
        context.checking(new Expectations() {
            {
                allowing(off1).hasNegativeOffset();
                will(returnValue(false));
            }

            {
                allowing(off1).hasPositiveOffset();
                will(returnValue(true));
            }

            {
                allowing(off1).getPositiveOffset(with(any(int.class)));
                will(returnValue(28));
            }

            {
                allowing(off1).getNegativeOffset(with(any(int.class)));
                will(returnValue(0));
            }
        });
        try {
            coord = new GeoCoordinatesImpl(6.12699, 49.60728);
        } catch (InvalidMapDataException e) {
            Assert.fail("Unexpected exception", e);
        }

        lrp1 = context.mock(
                LocationReferencePoint.class, "lrp1");
        lrp2 = context.mock(
                LocationReferencePoint.class, "lrp2");
        context.checking(new Expectations() {
            {
                allowing(lrp1).getLongitudeDeg();
                will(returnValue(6.12829));
            }

            {
                allowing(lrp1).getLatitudeDeg();
                will(returnValue(49.60597));
            }

            {
                allowing(lrp1).getBearing();
                will(returnValue(202.0));
            }

            {
                allowing(lrp1).getDistanceToNext();
                will(returnValue(92));
            }

            {
                allowing(lrp1).getFOW();
                will(returnValue(FormOfWay.MULTIPLE_CARRIAGEWAY));
            }

            {
                allowing(lrp1).getFRC();
                will(returnValue(FunctionalRoadClass.FRC_2));
            }

            {
                allowing(lrp1).getLfrc();
                will(returnValue(FunctionalRoadClass.FRC_2));
            }

            {
                allowing(lrp1).isLastLRP();
                will(returnValue(false));
            }
        });
        context.checking(new Expectations() {
            {
                allowing(lrp2).getLongitudeDeg();
                will(returnValue(6.12779));
            }

            {
                allowing(lrp2).getLatitudeDeg();
                will(returnValue(49.60521));
            }

            {
                allowing(lrp2).getBearing();
                will(returnValue(42.0));
            }

            {
                allowing(lrp2).getDistanceToNext();
                will(returnValue(0));
            }

            {
                allowing(lrp2).getFOW();
                will(returnValue(FormOfWay.MULTIPLE_CARRIAGEWAY));
            }

            {
                allowing(lrp2).getFRC();
                will(returnValue(FunctionalRoadClass.FRC_2));
            }

            {
                allowing(lrp2).getLfrc();
                will(returnValue(FunctionalRoadClass.FRC_2));
            }

            {
                allowing(lrp2).isLastLRP();
                will(returnValue(true));
            }
        });

        rawLocRef = new RawPoiAccessLocRef("", lrp1, lrp2, off1, coord, SideOfRoad.LEFT, Orientation.NO_ORIENTATION_OR_UNKNOWN);
    }

    @Test
    public final void testBinaryEncoder01() {
        LocationReference ref = null;
        try {
            ref = BINARY_ENCODER.encodeData(rawLocRef);
            XmlWriter writer = new XmlWriter();
            StringWriter sw = new StringWriter();
            writer.saveDatex2Location((Datex2Location) ref.getLocationReferenceData(), sw);
            BufferedReader br = new BufferedReader(new FileReader(TEST_DATA));
            String[] lines = sw.toString().split("\n");
            int nrLines = lines.length;
            String current = null;
            for (int i = 0; i < nrLines; i++) {
                current = br.readLine();
                if (current == null) {
                    fail("data not complete");
                }
                if (!current.trim().equals(lines[i].trim())) {
                    fail("data invalid");
                }
            }
            if (br.readLine() != null) {
                fail("data not complete");
            }
        } catch (OpenLRDatex2Exception e) {
            fail("", e);
        } catch (JAXBException e) {
            fail("", e);
        } catch (SAXException e) {
            fail("", e);
        } catch (PhysicalFormatException e) {
            fail("", e);
        } catch (FileNotFoundException e) {
            fail("", e);
        } catch (IOException e) {
            fail("", e);
        }
    }

}
