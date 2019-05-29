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
package openlr.encoder.wpaper;

import static openlr.map.FormOfWay.MULTIPLE_CARRIAGEWAY;
import static openlr.map.FormOfWay.SINGLE_CARRIAGEWAY;
import static openlr.map.FunctionalRoadClass.FRC_2;
import static openlr.map.FunctionalRoadClass.FRC_3;
import static openlr.map.FunctionalRoadClass.FRC_5;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.util.List;

import openlr.LocationReferencePoint;
import openlr.LocationType;
import openlr.Offsets;
import openlr.encoder.LocationReferenceHolder;
import openlr.encoder.OpenLREncoder;
import openlr.encoder.OpenLREncoderParameter;
import openlr.encoder.TestData;
import openlr.location.Location;
import openlr.map.FormOfWay;
import openlr.map.FunctionalRoadClass;
import openlr.map.InvalidMapDataException;
import openlr.rawLocRef.RawLocationReference;

import org.testng.Reporter;
import org.testng.annotations.Test;

/**
 * Tests the encoding examples from the OpenLr White paper.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public abstract class AbstractEncoderTest {

	/**
	 * Some LRP data used to verify result of encoding tests.
	 */
	enum Lrps {

		/**
		 * The LRP 1.
		 */
		LRP1(FRC_3, MULTIPLE_CARRIAGEWAY, 49.60851, 6.12683, 135, 561, FRC_3,
				false),

		/**
		 * The LRP 2.
		 */
		LRP2(FRC_3, SINGLE_CARRIAGEWAY, 49.60398, 6.12838, 227, 274, FRC_5,
				false),

		/**
		 * The LRP 3.
		 */
		LRP3(FRC_5, SINGLE_CARRIAGEWAY, 49.60305, 6.12817, 290, 0, null, true),

		/**
		 * The LRP 4.
		 */
		LRP4(FRC_2, SINGLE_CARRIAGEWAY, 49.60597, 6.12829, 202, 92, FRC_2,
				false),

		/**
		 * The LRP 5.
		 */
		LRP5(FRC_2, SINGLE_CARRIAGEWAY, 49.60521, 6.12779, 42, 0, null, true),

		LRP_GEOCOORD(null, null, 49.60728, 6.12699, 0, 0, null, false);

		/** The bearing of the line referenced by the LRP. */
		private final double bearing;

		/** The distance to the next LRP along the shortest-path. */
		private final int distanceToNext;

		/** The functional road class of the line referenced by the LRP. */
		private final FunctionalRoadClass frc;

		/** The form of way of the line referenced by the LRP. */
		private final FormOfWay fow;

		/** The lowest functional road class to the next LRP. */
		private final FunctionalRoadClass lfrcnp;

		/** The longitude coordinate. */
		private final double longitude;

		/** The latitude coordinate. */
		private final double latitude;

		/** Determines if this LRP is the last in the sequence. */
		private final boolean isLast;

		/**
		 * @param frcValue
		 *            The FRC
		 * @param fowValue
		 *            The FOW
		 * @param longitudeValue
		 *            The longitude
		 * @param latitudeValue
		 *            The latitude
		 * @param bearingValue
		 *            TThe bearing
		 * @param dnpValue
		 *            The DNP
		 * @param lfrcnpValue
		 *            The lowest FRC to the next point.
		 * @param isLastValue
		 *            Determines if this LRP is the last in the sequence.
		 */
		private Lrps(final FunctionalRoadClass frcValue,
				final FormOfWay fowValue, final double latitudeValue,
				final double longitudeValue, final double bearingValue,
				final int dnpValue, final FunctionalRoadClass lfrcnpValue,
				final boolean isLastValue) {
			this.longitude = longitudeValue;
			this.latitude = latitudeValue;
			this.frc = frcValue;
			this.fow = fowValue;
			this.bearing = bearingValue;
			this.lfrcnp = lfrcnpValue;
			this.distanceToNext = dnpValue;
			this.isLast = isLastValue;
		}

	}

	/**
	 * An utility class holding prepared/mocked test data.
	 */
	protected static TestData td = TestData.getInstance();

	/**
	 * A reference to the encoded location.
	 */
	protected LocationReferenceHolder locRef;

	/**
	 * Tests the encoding of the example from the White paper
	 */
	@Test
	public final void testWhitepaperLocation() {

		locRef = encodeLocation();

		Reporter.log("Testing location \"" + locRef.getID() + "\", class "
				+ getClass().getSimpleName());

		checkPoints(locRef, getExpectedLrps());

		checkOffsets(locRef, getExpectedOffsets());
	}

	/**
	 * Delivers the input location for the encoder.
	 * 
	 * @return The input location.
	 * @throws InvalidMapDataException 
	 */
	abstract Location getInputLocation() throws InvalidMapDataException;

	/**
	 * Delivers the LRP expected after encoding in the correct sequence.
	 * 
	 * @return The LRP expected after encoding.
	 */
	abstract Lrps[] getExpectedLrps();

	/**
	 * Delivers the offsets expected after encoding in the following sequence:
	 * {positive offset, negative offset}
	 * 
	 * @return The offsets expected after encoding.
	 */
	abstract int[] getExpectedOffsets();

	/**
	 * Encodes the location of the example and checks it for validity. Fails the
	 * test if if couldn't be encoded or isn't valid.
	 * 
	 * @return The valid location reference.
	 */
	private LocationReferenceHolder encodeLocation() {
		LocationReferenceHolder locationRef = null;
		try {
		Location inputLoc = getInputLocation();
			OpenLREncoder encoder = new OpenLREncoder();
			OpenLREncoderParameter params = new OpenLREncoderParameter.Builder().with(
					td.getMapDatabase()).with(td.getConfiguration()).buildParameter();
			// this requires that an encoder implementation is in the classpath!
			locationRef = encoder.encodeLocation(params, inputLoc);

			checkLocation(locationRef, inputLoc);
		} catch (Exception e) {
			fail("Encoding location failed with exception",
					e);
		}
		return locationRef;
	}

	/**
	 * Performs some general checks on the encoding result.
	 * 
	 * @param encodedLocation
	 *            The result of the encoding process.
	 * @param inputLocation
	 *            The (mocked) input location.
	 */
	private void checkLocation(final LocationReferenceHolder encodedLocation,
			final Location inputLocation) {

		assertTrue(encodedLocation.isValid(),
				"Encoding delivered invalid location.");
		assertNull(encodedLocation.getReturnCode());
		assertEquals(encodedLocation.getID(), inputLocation.getID());
		assertSame(encodedLocation.getLocationType(),
				inputLocation.getLocationType());

		checkRawLocationReference(
				encodedLocation.getRawLocationReferenceData(), inputLocation);

		assertNotNull(encodedLocation.toString());
	}

	/**
	 * Performs some general checks on the raw location reference.
	 * 
	 * @param rawData
	 *            The raw location reference data.
	 * @param inputLocation
	 *            The input location.
	 */
	private void checkRawLocationReference(final RawLocationReference rawData,
			final Location inputLocation) {

		if (rawData.hasID()) {
			assertNotNull(rawData.getID());
		} else {
			assertNull(rawData.getID());
		}
		assertSame(rawData.getOrientation(), inputLocation.getOrientation());
		assertSame(rawData.getSideOfRoad(), inputLocation.getSideOfRoad());
		assertTrue(rawData.isValid());
		assertNull(rawData.getReturnCode());

		assertNotNull(rawData.hashCode());
		assertTrue(rawData.equals(rawData));
		assertFalse(rawData.equals(null));
	}

	/**
	 * Tests the offsets for the expected values.
	 * 
	 * @param locationRef
	 *            The encoded location reference.
	 * @param expectedOffsets
	 *            An array of expected offset values in the order {positive
	 *            offset, negative offset}.
	 */
	private void checkOffsets(final LocationReferenceHolder locationRef,
			final int[] expectedOffsets) {

		Offsets offsets = locationRef.getRawLocationReferenceData()
				.getOffsets();

		if (expectedOffsets != null) {
			assertTrue(offsets.hasPositiveOffset() == expectedOffsets[0] > 0);
			assertTrue(offsets.hasNegativeOffset() == expectedOffsets[1] > 0);
			assertEquals(offsets.getPositiveOffset(0), expectedOffsets[0]);
			assertEquals(offsets.getNegativeOffset(0), expectedOffsets[1]);
		} else {
			// a location with no offsets expected
			assertNull(offsets, "Unexpected offsets in the encoder result.");
		}
	}

	/**
	 * Tests if there are all of the expected LRP delivered by the encoding with
	 * the correct values.
	 * 
	 * @param locationRef
	 *            The encoded location reference.
	 * @param expectedLrps
	 *            The expected LRPs.
	 */
	private void checkPoints(final LocationReferenceHolder locationRef,
			final Lrps[] expectedLrps) {

		List<? extends LocationReferencePoint> lrps = locationRef.getLRPs();

		if (expectedLrps != null) {
			assertEquals(locationRef.getNrOfLRPs(), expectedLrps.length,
					"Unexpected number of LRP calculated for encoder location!");

			// check LRP data
			for (int i = 0; i < lrps.size(); i++) {
				LocationReferencePoint lrp = lrps.get(i);

				assertEquals(lrp.getLatitudeDeg(), expectedLrps[i].latitude);
				assertEquals(lrp.getLongitudeDeg(), expectedLrps[i].longitude);
				// round the bearing to integers on both sides to be not that
				// niggling with decimal places
				assertEquals(Math.round(lrp.getBearing()),
						Math.round(expectedLrps[i].bearing));
				assertEquals(lrp.getDistanceToNext(),
						expectedLrps[i].distanceToNext);
				if (locationRef.getLocationType() != LocationType.GEO_COORDINATES) {
					assertEquals(lrp.getLfrc(), expectedLrps[i].lfrcnp);
					assertEquals(lrp.getFRC(), expectedLrps[i].frc);
					assertEquals(lrp.getFOW(), expectedLrps[i].fow);
				}
				assertEquals(lrp.isLastLRP(), expectedLrps[i].isLast);
			}

		} else {
			// a location with no LRPs expected
			assertFalse(locationRef.hasLRPs());
			assertNull(lrps, "Unexpected LRSs in the encoder result.");
		}
	}
}
