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
package openlr.datex2;

import openlr.LocationReference;
import openlr.LocationReferencePoint;
import openlr.Offsets;
import openlr.PhysicalFormatException;
import openlr.map.FormOfWay;
import openlr.map.FunctionalRoadClass;
import openlr.rawLocRef.RawLineLocRef;
import openlr.rawLocRef.RawLocationReference;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.fail;

/**
 * The Class OpenLRBinaryEncoderTest.
 */
public class OpenLRDatex2EncoderTest {


    private static final OpenLRDatex2Encoder XML_ENCODER = new OpenLRDatex2Encoder();

    private static final File TEST_DATA = new File("src/test/resources/line.txt");


    /** The points. */
    private List<LocationReferencePoint> points;

    /** The off1. */
    private Offsets off1;

    /** The off2. */
    private Offsets off2;

    /**
     * Setup.
     */
    @BeforeTest
    public final void setup() {
        points = new ArrayList<LocationReferencePoint>();
        Mockery context = new Mockery();
        off1 = context.mock(Offsets.class, "off1");
        off2 = context.mock(Offsets.class, "off2");
        context.checking(new Expectations() {
            {
                allowing(off1).hasNegativeOffset();
                will(returnValue(false));
            }

            {
                allowing(off1).hasPositiveOffset();
                will(returnValue(false));
            }

            {
                allowing(off1).getPositiveOffset(0);
                will(returnValue(0));
            }

            {
                allowing(off1).getNegativeOffset(0);
                will(returnValue(0));
            }
        });
        context.checking(new Expectations() {
            {
                allowing(off2).hasNegativeOffset();
                will(returnValue(false));
            }

            {
                allowing(off2).hasPositiveOffset();
                will(returnValue(true));
            }

            {
                allowing(off2).getPositiveOffset(0);
                will(returnValue(200));
            }

            {
                allowing(off2).getNegativeOffset(0);
                will(returnValue(0));
            }
        });

        final LocationReferencePoint lrp1 = context.mock(
                LocationReferencePoint.class, "lrp1");
        final LocationReferencePoint lrp2 = context.mock(
                LocationReferencePoint.class, "lrp2");
        final LocationReferencePoint lrp3 = context.mock(
                LocationReferencePoint.class, "lrp3");
        context.checking(new Expectations() {
            {
                allowing(lrp1).getLongitudeDeg();
                will(returnValue(6.12683));
            }

            {
                allowing(lrp1).getLatitudeDeg();
                will(returnValue(49.60851));
            }

            {
                allowing(lrp1).getBearing();
                will(returnValue(135.0));
            }

            {
                allowing(lrp1).getDistanceToNext();
                will(returnValue(561));
            }

            {
                allowing(lrp1).getFOW();
                will(returnValue(FormOfWay.MULTIPLE_CARRIAGEWAY));
            }

            {
                allowing(lrp1).getFRC();
                will(returnValue(FunctionalRoadClass.FRC_3));
            }

            {
                allowing(lrp1).getLfrc();
                will(returnValue(FunctionalRoadClass.FRC_3));
            }

            {
                allowing(lrp1).isLastLRP();
                will(returnValue(false));
            }
        });
        context.checking(new Expectations() {
            {
                allowing(lrp2).getLongitudeDeg();
                will(returnValue(6.12838));
            }

            {
                allowing(lrp2).getLatitudeDeg();
                will(returnValue(49.60398));
            }

            {
                allowing(lrp2).getBearing();
                will(returnValue(227.0));
            }

            {
                allowing(lrp2).getDistanceToNext();
                will(returnValue(274));
            }

            {
                allowing(lrp2).getFOW();
                will(returnValue(FormOfWay.SINGLE_CARRIAGEWAY));
            }

            {
                allowing(lrp2).getFRC();
                will(returnValue(FunctionalRoadClass.FRC_3));
            }

            {
                allowing(lrp2).getLfrc();
                will(returnValue(FunctionalRoadClass.FRC_5));
            }

            {
                allowing(lrp2).isLastLRP();
                will(returnValue(false));
            }
        });
        context.checking(new Expectations() {
            {
                allowing(lrp3).getLongitudeDeg();
                will(returnValue(6.12817));
            }

            {
                allowing(lrp3).getLatitudeDeg();
                will(returnValue(49.60305));
            }

            {
                allowing(lrp3).getBearing();
                will(returnValue(290.0));
            }

            {
                allowing(lrp3).getFOW();
                will(returnValue(FormOfWay.SINGLE_CARRIAGEWAY));
            }

            {
                allowing(lrp3).getFRC();
                will(returnValue(FunctionalRoadClass.FRC_5));
            }

            {
                allowing(lrp3).isLastLRP();
                will(returnValue(true));
            }
        });
        points = new ArrayList<LocationReferencePoint>();
        points.add(lrp1);
        points.add(lrp2);
        points.add(lrp3);
    }

/*
	@Test
	public final void testBinaryEncoder01() {		
		try {
			LocationReference ref = XML_ENCODER.encodeData("", null, null);
			fail("exception expected");
		} catch (OpenLRException e) {
			
		}
	}
	*/


	/*
	@Test
	public final void testBinaryEncoder02() {
		try {
			LocationReference ref = XML_ENCODER.encodeData("1", new ArrayList<LocationReferencePoint>(),
					null);
			fail("exception expected");
		} catch (OpenLRException e) {
			
		}
	}	
	*/

	/*
	@Test
	public final void testBinaryEncoder03() {
		try {
			LocationReference ref = XML_ENCODER.encodeData("1", points, null);
			fail("exception expected");
		} catch (OpenLRException e) {
		}
	}
	*/

	/*
	@Test
	public final void testBinaryEncoder04() {
		LocationReference ref = null;
		try {
			ref = XML_ENCODER.encodeData("no-offset", points, off1);
		} catch (OpenLRException e) {
			fail("cannot encode data");
		}
		if (!"xml".equals(ref.getDataIdentifier())) {
			fail("Invalid data identifier");
		}
		if (!checkDataClass(ref)) {
			fail("invalid data class");
		}	
		assertTrue(checkData((OpenLR)ref.getLocationReferenceData(), "no-offset"), "wrong data");
		assertTrue(checkOffsetData((OpenLR)ref.getLocationReferenceData(), false), "wrong offset data");
	}
	*/


    @Test
    public final void testBinaryEncoder05() {
        LocationReference ref = null;
        RawLocationReference rawLocRef = new RawLineLocRef("", points, off2);
        try {
            ref = XML_ENCODER.encodeData(rawLocRef, 11);
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
        } catch (PhysicalFormatException e) {
            fail("cannot encode data", e);
        } catch (JAXBException e) {
            e.printStackTrace();
            fail("cannot encode data");
        } catch (SAXException e) {
            e.printStackTrace();
            fail("cannot encode data");
        } catch (FileNotFoundException e) {
            fail("", e);
        } catch (IOException e) {
            fail("", e);
        }
        if (!"datex2".equals(ref.getDataIdentifier())) {
            fail("Invalid data identifier");
        }

    }


}

