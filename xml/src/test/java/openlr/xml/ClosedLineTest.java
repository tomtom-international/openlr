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
package openlr.xml;

import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import openlr.LocationReference;
import openlr.LocationReferencePoint;
import openlr.LocationType;
import openlr.PhysicalFormatException;
import openlr.rawLocRef.RawClosedLineLocRef;
import openlr.rawLocRef.RawLocationReference;
import openlr.xml.generated.AreaLocationReference;
import openlr.xml.generated.LineAttributes;
import openlr.xml.generated.OpenLR;
import openlr.xml.generated.XMLLocationReference;
import openlr.xml.impl.LocationReferenceXmlImpl;

import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ClosedLineTest {
	
	@Test
	public final void testClosedLine() {
		final Mockery context = new Mockery();
		context.setImposteriser(ClassImposteriser.INSTANCE);
	
		List<LocationReferencePoint> locRefPoints = Utils.mockLrps123(
				context, new Lrp[] {Lrp.LINE_ENC_LRP1, Lrp.LINE_ENC_LRP2,
						Lrp.LINE_ENC_LRP3_CLOSEDLINE});

		RawLocationReference rawLocRef = new RawClosedLineLocRef("closedLine",
				locRefPoints);
		OpenLRXMLEncoder encoder = new OpenLRXMLEncoder();
		LocationReference lr = encoder.encodeData(rawLocRef);
		Assert.assertNotNull(lr);
		Assert.assertTrue(lr.isValid());
		Assert.assertEquals(lr.getID(), "closedLine");
		Assert.assertEquals(lr.getLocationType(), LocationType.CLOSED_LINE);
		Assert.assertNotNull(lr.getLocationReferenceData());

		checkEncodedLocation(lr, locRefPoints);
	}
	
	@Test
	public final void testClosedLineDecode() {	
		
		OpenLR closedLineLocation = Utils.readLocationFromFile(
				"ClosedLine.xml", true);
		try {
			LocationReference lr = new LocationReferenceXmlImpl("closedLine", closedLineLocation, 1);
			OpenLRXMLDecoder decoder = new OpenLRXMLDecoder();
			RawLocationReference rawLocRef = decoder.decodeData(lr);
			Assert.assertNotNull(rawLocRef);
			Assert.assertTrue(rawLocRef.isValid());
			Assert.assertEquals(rawLocRef.getLocationType(), LocationType.CLOSED_LINE);
			Assert.assertEquals(rawLocRef.getID(), "closedLine");
			
			Lrp[] expectedLrps = new Lrp[] {Lrp.LINE_ENC_LRP1, Lrp.LINE_ENC_LRP2,
					Lrp.LINE_ENC_LRP3_CLOSEDLINE };
			checkDecodedData(rawLocRef, expectedLrps);
			
		} catch (PhysicalFormatException e) {
			Assert.fail("Unexpected exception", e);
		}
	}
	
	
	public final void checkDecodedData(final RawLocationReference rawLocRef,
			final Lrp[]  expectedLrps) {
		assertSame(rawLocRef.getLocationType(), LocationType.CLOSED_LINE);
		assertTrue(rawLocRef.isValid());
		assertNull(rawLocRef.getReturnCode());

		assertNull(rawLocRef.getOrientation());
		assertNull(rawLocRef.getSideOfRoad());
		assertNull(rawLocRef.getOffsets());
		Assert.assertNotNull(rawLocRef.getLocationReferencePoints());

		Utils.checkDecodedLrps(rawLocRef, expectedLrps, false);
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
			final LocationReference encodedLoc,
			final List<LocationReferencePoint> expectedLrps) {

		assertSame(encodedLoc.getLocationType(),
				LocationType.CLOSED_LINE);
		assertTrue(encodedLoc.isValid());
		assertNull(encodedLoc.getReturnCode());

		XMLLocationReference xmLoc = ((OpenLR) encodedLoc
				.getLocationReferenceData()).getXMLLocationReference();

		assertNull(xmLoc.getLineLocationReference());
		assertNull(xmLoc.getPointLocationReference());
		AreaLocationReference aLoc = xmLoc.getAreaLocationReference();
		Assert.assertNotNull(aLoc.getClosedLineLocationReference());
		Assert.assertNull(aLoc.getGridLocationReference());
		Assert.assertNull(aLoc.getRectangleLocationReference());
		assertNull(aLoc.getPolygonLocationReference());
		assertNull(aLoc.getCircleLocationReference());
		Assert.assertNotNull(aLoc.getClosedLineLocationReference().getLocationReferencePoint());
		List<openlr.xml.generated.LocationReferencePoint> lrps = aLoc.getClosedLineLocationReference().getLocationReferencePoint();
		Assert.assertFalse(lrps.isEmpty());
		List<LocationReferencePoint> realLrps = new ArrayList<LocationReferencePoint>();
		for (int i = 0; i < expectedLrps.size() - 1; i++) {
			realLrps.add(expectedLrps.get(i));
		}
		Utils.checkLRPs(lrps, null, realLrps);		
		Assert.assertNotNull(aLoc.getClosedLineLocationReference().getLastLine());
		LineAttributes lastLine = aLoc.getClosedLineLocationReference().getLastLine();
		LocationReferencePoint lastLRP = expectedLrps.get(expectedLrps.size() - 1);
		Assert.assertEquals(lastLine.getBEAR(), Math.round(lastLRP.getBearing()));
		Assert.assertEquals(Utils.mapFRC(lastLine.getFRC()), lastLRP.getFRC());
		Assert.assertEquals(lastLine.getFOW().name(), lastLRP.getFOW().name());
	}

}
