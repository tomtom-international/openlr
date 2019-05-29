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
package openlr.encoder.areaLocation;

import java.util.ArrayList;
import java.util.List;

import openlr.LocationReferencePoint;
import openlr.LocationType;
import openlr.encoder.LocationReferenceHolder;
import openlr.encoder.OpenLREncoder;
import openlr.encoder.OpenLREncoderParameter;
import openlr.encoder.TestData;
import openlr.location.Location;
import openlr.location.LocationFactory;
import openlr.map.FormOfWay;
import openlr.map.FunctionalRoadClass;
import openlr.map.Line;
import openlr.map.MapDatabase;
import openlr.rawLocRef.RawLocationReference;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ClosedLineTest {

	/**
	 * Test closed line encoding with2 lrp.
	 */
	@Test
	public final void testClosedLineEncodingWith2LRP() {
		TestData td = TestData.getInstance();
		MapDatabase mdb = td.getMapDatabase();
		List<Line> lines = new ArrayList<Line>();
		lines.add(mdb.getLine(7));
		lines.add(mdb.getLine(11));
		lines.add(mdb.getLine(22));
		lines.add(mdb.getLine(24));
		try {
			Location loc = LocationFactory.createClosedLineLocation(
					"closedLine", lines);

			OpenLREncoderParameter params = new OpenLREncoderParameter.Builder().with(mdb).buildParameter();
			OpenLREncoder encoder = new OpenLREncoder();
			LocationReferenceHolder locrefs = encoder.encodeLocation(params,
					loc);
			Assert.assertNotNull(locrefs);
			Assert.assertTrue(locrefs.isValid());
			Assert.assertEquals(locrefs.getID(), "closedLine");
			Assert.assertEquals(locrefs.getLocationType(),
					LocationType.CLOSED_LINE);
			RawLocationReference raw = locrefs.getRawLocationReferenceData();
			Assert.assertNotNull(raw);
			Assert.assertNotNull(raw.getLocationReferencePoints());
			List<? extends LocationReferencePoint> rawLocRefs = raw.getLocationReferencePoints();
			Assert.assertEquals(rawLocRefs.size(), 2);
			LocationReferencePoint startLRP = rawLocRefs.get(0);
			LocationReferencePoint endLRP = rawLocRefs.get(1);
			Assert.assertEquals(Math.round(startLRP.getBearing()), 134);
			Assert.assertEquals(startLRP.getDistanceToNext(), 482);
			Assert.assertEquals(startLRP.getLatitudeDeg(), 49.60597);
			Assert.assertEquals(startLRP.getLongitudeDeg(), 6.12829);
			Assert.assertEquals(startLRP.getSequenceNumber(), 1);
			Assert.assertFalse(startLRP.isLastLRP());
			Assert.assertEquals(startLRP.getFOW(), FormOfWay.MULTIPLE_CARRIAGEWAY);
			Assert.assertEquals(startLRP.getFRC(), FunctionalRoadClass.FRC_2);
			Assert.assertEquals(startLRP.getLfrc(), FunctionalRoadClass.FRC_3);
			
			Assert.assertEquals(Math.round(endLRP.getBearing()), 239);
			Assert.assertEquals(endLRP.getDistanceToNext(), 0);
			Assert.assertEquals(endLRP.getLatitudeDeg(), 49.60597);
			Assert.assertEquals(endLRP.getLongitudeDeg(), 6.12829);
			Assert.assertEquals(endLRP.getSequenceNumber(), 2);
			Assert.assertTrue(endLRP.isLastLRP());
			Assert.assertEquals(endLRP.getFOW(), FormOfWay.SINGLE_CARRIAGEWAY);
			Assert.assertEquals(endLRP.getFRC(), FunctionalRoadClass.FRC_2);
			Assert.assertNull(endLRP.getLfrc());
			
		} catch (Exception e) {
			Assert.fail("unexpected exception", e);
		}
	}
	
	/**
	 * Test closed line encoding with3 lrp.
	 */
	@Test
	public final void testClosedLineEncodingWith3LRP() {
		TestData td = TestData.getInstance();
		MapDatabase mdb = td.getMapDatabase();
		List<Line> lines = new ArrayList<Line>();
		lines.add(mdb.getLine(7));
		lines.add(mdb.getLine(11));
		lines.add(mdb.getLine(14));
		lines.add(mdb.getLine(16));
		lines.add(mdb.getLine(25));
		lines.add(mdb.getLine(26));
		lines.add(mdb.getLine(27));
		lines.add(mdb.getLine(24));
		try {
			Location loc = LocationFactory.createClosedLineLocation(
					"closedLine", lines);

			OpenLREncoderParameter params = new OpenLREncoderParameter.Builder().with(mdb).buildParameter();
			OpenLREncoder encoder = new OpenLREncoder();
			LocationReferenceHolder locrefs = encoder.encodeLocation(params,
					loc);
			Assert.assertNotNull(locrefs);
			Assert.assertTrue(locrefs.isValid());
			Assert.assertEquals(locrefs.getID(), "closedLine");
			Assert.assertEquals(locrefs.getLocationType(),
					LocationType.CLOSED_LINE);
			RawLocationReference raw = locrefs.getRawLocationReferenceData();
			Assert.assertNotNull(raw);
			Assert.assertNotNull(raw.getLocationReferencePoints());
			List<? extends LocationReferencePoint> rawLocRefs = raw.getLocationReferencePoints();
			Assert.assertEquals(rawLocRefs.size(), 3);
			LocationReferencePoint startLRP = rawLocRefs.get(0);
			LocationReferencePoint intermediate = rawLocRefs.get(1);
			LocationReferencePoint endLRP = rawLocRefs.get(2);
			Assert.assertEquals(Math.round(startLRP.getBearing()), 134);
			Assert.assertEquals(startLRP.getDistanceToNext(), 246);
			Assert.assertEquals(startLRP.getLatitudeDeg(), 49.60597);
			Assert.assertEquals(startLRP.getLongitudeDeg(), 6.12829);
			Assert.assertEquals(startLRP.getSequenceNumber(), 1);
			Assert.assertFalse(startLRP.isLastLRP());
			Assert.assertEquals(startLRP.getFOW(), FormOfWay.MULTIPLE_CARRIAGEWAY);
			Assert.assertEquals(startLRP.getFRC(), FunctionalRoadClass.FRC_2);
			Assert.assertEquals(startLRP.getLfrc(), FunctionalRoadClass.FRC_3);

			Assert.assertEquals(Math.round(intermediate.getBearing()), 227);
			Assert.assertEquals(intermediate.getDistanceToNext(), 475);
			Assert.assertEquals(intermediate.getLatitudeDeg(), 49.60398);
			Assert.assertEquals(intermediate.getLongitudeDeg(), 6.12838);
			Assert.assertEquals(intermediate.getSequenceNumber(), 2);
			Assert.assertFalse(intermediate.isLastLRP());
			Assert.assertEquals(intermediate.getFOW(), FormOfWay.SINGLE_CARRIAGEWAY);
			Assert.assertEquals(intermediate.getFRC(), FunctionalRoadClass.FRC_3);
			Assert.assertEquals(intermediate.getLfrc(), FunctionalRoadClass.FRC_7);
			
			Assert.assertEquals(Math.round(endLRP.getBearing()), 239);
			Assert.assertEquals(endLRP.getDistanceToNext(), 0);
			Assert.assertEquals(endLRP.getLatitudeDeg(), 49.60597);
			Assert.assertEquals(endLRP.getLongitudeDeg(), 6.12829);
			Assert.assertEquals(endLRP.getSequenceNumber(), 3);
			Assert.assertTrue(endLRP.isLastLRP());
			Assert.assertEquals(endLRP.getFOW(), FormOfWay.SINGLE_CARRIAGEWAY);
			Assert.assertEquals(endLRP.getFRC(), FunctionalRoadClass.FRC_2);
			Assert.assertNull(endLRP.getLfrc());
			
		} catch (Exception e) {
			Assert.fail("unexpected exception", e);
		}
	}

}
