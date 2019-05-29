/**
 * Licensed to the TomTom International B.V. under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  TomTom International B.V.
 * licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
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
package openlr.binary;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import openlr.LocationReferencePoint;
import openlr.Offsets;
import openlr.map.GeoCoordinates;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.InvalidMapDataException;
import openlr.map.utils.GeometryUtils;
import openlr.rawLocRef.RawLocationReference;

import org.jmock.Expectations;
import org.jmock.Mockery;

/**
 * Provides utility function common for all tests of this package.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
final class Utils {
	
	/**
	 * Private constructor- utility class should not be instanciable.
	 */
	private Utils() {
	}

	/**
	 * Verifies the given {@link ByteArray} against the <code>expected</code>
	 * data.
	 * 
	 * @param actual
	 *            The data to check.
	 * @param expected
	 *            The expected bytes.
	 * @param version
	 *            The binary version of the data, optional- if not provided no
	 *            version info is printed.
	 */
	public static void checkBinData(final ByteArray actual,
			final byte[] expected, final Integer version) {

		String versionTerm;
		if (version != null) {
			versionTerm = " of version " + version;
		} else {
			versionTerm = "";
		}

		assertEquals(actual.size(), expected.length, "Encoded data"
				+ versionTerm + " doesn't fit the expected length.");
		for (int i = 0; i < actual.size(); i++) {
			assertEquals(actual.getData()[i], expected[i],
					"Unexpected value in encoded data" + versionTerm
							+ " for byte of index " + i);
		}
	}

	/**
	 * Checks the LRP of the decoding result of the White Paper example.
	 * 
	 * @param lrps
	 *            The list of {@link LocationReferencePoint}s to check.
	 * @param expectedLrps
	 *            The expected LRP data.
	 */
	public static void checkLrps(
			final List<? extends LocationReferencePoint> lrps,
			final Lrp... expectedLrps) {

		LocationReferencePoint lrp;
		for (int i = 0; i < expectedLrps.length; i++) {

			lrp = lrps.get(i);
			double logRounded = GeometryUtils.round(lrps.get(i).getLongitudeDeg());

			double latRounded = GeometryUtils.round(lrps.get(i).getLatitudeDeg());

			assertEquals(logRounded, expectedLrps[i].getLongitude());
			assertEquals(latRounded, expectedLrps[i].getLatitude());
			assertEquals(lrp.getFOW(), expectedLrps[i].getFow());
			assertEquals(lrp.getDistanceToNext(),
					expectedLrps[i].getDistanceToNext());
			assertEquals(lrp.getFRC(), expectedLrps[i].getFrc());
			assertEquals(lrp.getLfrc(), expectedLrps[i].getLfrcnp());
			assertEquals(lrp.getBearing(), expectedLrps[i].getBearing());
		}
		
		assertTrue(lrps.get(lrps.size() - 1).isLastLRP());
	}
	
	
	/**
	 * Mocks LINE_ENC_LRP1, LINE_ENC_LRP2 and LINE_ENC_LRP3 which are commonly
	 * used in some tests.
	 * 
	 * @param context
	 *            The mocking context.
	 * @return A list of the three mocked objects in sequence 1, 2, 3
	 */
	public static List<LocationReferencePoint> mockLrps123(final Mockery context, final Lrp[] lrpsToMock) {

		final List<LocationReferencePoint> points123 = new ArrayList<LocationReferencePoint>(
				lrpsToMock.length);

		for (final Lrp lrp : lrpsToMock) {

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
					if (lrp == Lrp.LINE_ENC_LRP3) {
						will(returnValue(true));
					} else {
						will(returnValue(false));
					}
				}
			});

			points123.add(mockedLrp);
		}

		return points123;
	}

	/**
	 * Mocks an {@link Offsets} object with the given data.
	 * 
	 * @param context
	 *            The mocking object.
	 * @param positive
	 *            The positive offset value, "0" is interpreted as "no offset"
	 * @param negative
	 *            The negative offset value, "0" is interpreted as "no offset"
	 * @return The mocked offset.
	 */
	public static Offsets mockOffsets(final Mockery context, final int positive,
			final int negative) {

		final Offsets mockedOffsets = context.mock(Offsets.class, positive
				+ "-" + negative);

		context.checking(new Expectations() {
			{
				allowing(mockedOffsets).hasNegativeOffset();
				will(returnValue(negative != 0));
			}
			{
				allowing(mockedOffsets).hasPositiveOffset();
				will(returnValue(positive != 0));
			}
			{
				allowing(mockedOffsets).getPositiveOffset(with(any(int.class)));
				will(returnValue(positive));
			}

			{
				allowing(mockedOffsets).getNegativeOffset(with(any(int.class)));
				will(returnValue(negative));
			}
		});

		return mockedOffsets;
	}
	
	/**
	 * Checks the decoded LRPs against the expected values.
	 * 
	 * @param locRef
	 *            The decoded location.
	 * @param expectedLrps The list of expected LRPs
	 */
	public static void checkDecodedLrps(final RawLocationReference locRef, final Lrp[] expectedLrps) {

		List<? extends LocationReferencePoint> lrps = locRef
				.getLocationReferencePoints();

		assertSame(lrps.size(), expectedLrps.length);

		LocationReferencePoint lrp;

		for (int i = 0; i < expectedLrps.length; i++) {

			lrp = lrps.get(i);
			double logRounded = GeometryUtils.round(lrps.get(i).getLongitudeDeg());
			double latRounded = GeometryUtils.round(lrps.get(i).getLatitudeDeg());

			assertEquals(logRounded, expectedLrps[i].getLongitude());
			assertEquals(latRounded, expectedLrps[i].getLatitude());
			assertEquals(lrp.getFOW(), expectedLrps[i].getFow());
			assertEquals(lrp.getDistanceToNext(),
					expectedLrps[i].getDistanceToNext());
			assertEquals(lrp.getFRC(), expectedLrps[i].getFrc());
			assertEquals(lrp.getLfrc(), expectedLrps[i].getLfrcnp());
			assertEquals(lrp.getBearing(), expectedLrps[i].getBearing());
		}
	}

    /**
     * Instantiates a geo coordinate and wraps the checked
     * {@link InvalidMapDataException} into an unchecked
     * {@link IllegalArgumentException} if it occues
     * 
     * @param longitude
     *            The longitude
     * @param latitude
     *            The latitude
     * @return The geo coordinate instance
     */
    public static GeoCoordinates geoCoordinateUnchecked(final double longitude,
            final double latitude) {
        try {
            return new GeoCoordinatesImpl(longitude, latitude);
        } catch (InvalidMapDataException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
