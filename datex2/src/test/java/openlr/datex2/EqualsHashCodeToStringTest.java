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
package openlr.datex2;

import static openlr.testutils.CommonObjectTestUtils.testCompare;
import static openlr.testutils.CommonObjectTestUtils.testToString;
import static org.testng.Assert.fail;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import openlr.LocationReferencePoint;
import openlr.datex2.impl.LocationReferenceImpl;
import openlr.datex2.impl.LocationReferencePointImpl;
import openlr.datex2.impl.OffsetsImpl;
import openlr.location.data.Orientation;
import openlr.location.data.SideOfRoad;
import openlr.map.FormOfWay;
import openlr.map.FunctionalRoadClass;
import openlr.map.GeoCoordinates;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.utils.GeometryUtils;
import openlr.rawLocRef.RawInvalidLocRef;
import openlr.rawLocRef.RawLineLocRef;
import openlr.rawLocRef.RawPoiAccessLocRef;
import openlr.rawLocRef.RawPointAlongLocRef;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * This class performs tests of the toString, hashCode and equals methods of
 * several classes.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public class EqualsHashCodeToStringTest {

	/**
	 * The name of a location reference file containing a geo coordinate
	 * location.
	 */
	private static final String GEOLOCATION_XML = "geolocation.xml";

	/**
	 * An instance of {@link LocationReferencePointBinaryImpl}.
	 */
	private static final LocationReferencePointImpl LRP_IMPL = new LocationReferencePointImpl(
			1, FunctionalRoadClass.FRC_2, FormOfWay.MOTORWAY, Double.MAX_VALUE,
			Double.MIN_VALUE, Double.MIN_NORMAL, 0, FunctionalRoadClass.FRC_4,
			false);

	/**
	 * A {@link LocationReferencePointBinaryImpl} instance that should not be
	 * equal to {@link #LRP_IMPL}.
	 */
	private static final LocationReferencePointImpl LRP_IMPL_UNEQUAL = new LocationReferencePointImpl(
			1, FunctionalRoadClass.FRC_1, FormOfWay.MOTORWAY, Double.MAX_VALUE,
			Double.MIN_VALUE, Double.MIN_NORMAL, 2, FunctionalRoadClass.FRC_4,
			false);

	/**
	 * An instance of {@link GeoCoordinates}.
	 */
	private final GeoCoordinates geoCoordinate;

	/**
	 * An instance of {@link GeoCoordinates} that should be equal to
	 * {@link #geoCoordinate}.
	 */
	private final GeoCoordinates geoCoordinateEqual;

	/**
	 * An instance of {@link GeoCoordinates} that should not be equal to
	 * {@link #geoCoordinate}.
	 */
	private final GeoCoordinates geoCoordinateUnEqual;

	/**
	 * An instance of {@link OffsetsImpl}.
	 */
	private static final OffsetsImpl OFFSETS_IMPL = new OffsetsImpl(2, 1);

	/**
	 * An instance of {@link OffsetsImpl} that should be equal to
	 * {@link #OFFSETS_IMPL}.
	 */
	private static final OffsetsImpl OFFSETS_IMPL_EQUAL = new OffsetsImpl(2, 1);

	/**
	 * An instance of {@link OffsetsImpl} that should not be equal to
	 * {@link #OFFSETS_IMPL}.
	 */
	private static final OffsetsImpl OFFSETS_IMPL_UNEQUAL = new OffsetsImpl(2,
			2);

	private final Datex2Location geoLoc;
	private final Datex2Location lineLoc;

	public EqualsHashCodeToStringTest() throws Exception {

		XmlReader reader;
		try {

			geoCoordinate = new GeoCoordinatesImpl(GeometryUtils.MAX_LON, 
					GeometryUtils.MIN_LAT);
			geoCoordinateEqual = new GeoCoordinatesImpl(GeometryUtils.MAX_LON,
					GeometryUtils.MIN_LAT);
			geoCoordinateUnEqual = new GeoCoordinatesImpl(GeometryUtils.MAX_LON, 0);

			reader = new XmlReader();

			geoLoc = reader
					.readDatex2Location(getResourceAsStream(GEOLOCATION_XML));
			lineLoc = reader
					.readDatex2Location(getResourceAsStream("pointalonglocation.xml"));
		} catch (Exception e) {
			fail("Unexpected exception!", e);
			throw e;
		}
	}

	/**
	 * Tests the raw location reference classes.
	 */
	@Test
	public final void testGeoLocation() {

		try {

			Datex2Location d2Equal = new XmlReader()
					.readDatex2Location(getResourceAsStream(GEOLOCATION_XML));

			testCompare(geoLoc, d2Equal, lineLoc);

		} catch (Exception e) {
			fail("Unexpected exception!", e);
		}
	}

	/**
	 * Tests the raw location reference classes.
	 */
	@Test
	public final void testRawLocationReferenceObjects() {

		List<LocationReferencePoint> lrps = new ArrayList<LocationReferencePoint>(
				2);
		lrps.add(LRP_IMPL);
		lrps.add(LRP_IMPL_UNEQUAL);

		RawLineLocRef lineLoca = new RawLineLocRef("id", lrps, OFFSETS_IMPL);
		RawLineLocRef lineLocEqual = new RawLineLocRef("id",
				Collections.unmodifiableList(lrps), OFFSETS_IMPL_EQUAL);
		RawLineLocRef lineLocUnequal = new RawLineLocRef("od", lrps,
				OFFSETS_IMPL_UNEQUAL);
		testCompare(lineLoca, lineLocEqual, lineLocUnequal);
		testToString(lineLoca);

		RawPoiAccessLocRef poiLoc = new RawPoiAccessLocRef("id", LRP_IMPL,
				LRP_IMPL_UNEQUAL, OFFSETS_IMPL, geoCoordinate, SideOfRoad.BOTH,
				Orientation.AGAINST_LINE_DIRECTION);
		RawPoiAccessLocRef poiLocEqual = new RawPoiAccessLocRef("id", LRP_IMPL,
				LRP_IMPL_UNEQUAL, OFFSETS_IMPL_EQUAL, geoCoordinateEqual,
				SideOfRoad.BOTH, Orientation.AGAINST_LINE_DIRECTION);
		RawPoiAccessLocRef poiLocUnEqual = new RawPoiAccessLocRef("id",
				LRP_IMPL, LRP_IMPL_UNEQUAL, OFFSETS_IMPL_EQUAL,
				geoCoordinateEqual, SideOfRoad.LEFT,
				Orientation.AGAINST_LINE_DIRECTION);
		testCompare(poiLoc, poiLocEqual, poiLocUnEqual);
		testToString(poiLoc);

		RawPointAlongLocRef palLoc = new RawPointAlongLocRef("id", LRP_IMPL,
				LRP_IMPL_UNEQUAL, OFFSETS_IMPL, SideOfRoad.LEFT,
				Orientation.BOTH);
		RawPointAlongLocRef palLocEqual = new RawPointAlongLocRef("id",
				LRP_IMPL, LRP_IMPL_UNEQUAL, OFFSETS_IMPL_EQUAL,
				SideOfRoad.LEFT, Orientation.BOTH);
		RawPointAlongLocRef palLocUnequal = new RawPointAlongLocRef("id",
				LRP_IMPL, LRP_IMPL_UNEQUAL, OFFSETS_IMPL, SideOfRoad.RIGHT,
				Orientation.BOTH);
		testCompare(palLoc, palLocEqual, palLocUnequal);
		testToString(palLoc);

		RawInvalidLocRef invLoc = new RawInvalidLocRef("id",
				Datex2ReturnCode.UNKNOWN_LOCATION_TYPE);
		RawInvalidLocRef invLocEqual = new RawInvalidLocRef("id",
				Datex2ReturnCode.UNKNOWN_LOCATION_TYPE);
		RawInvalidLocRef invLocUnEqual = new RawInvalidLocRef("id",
				Datex2ReturnCode.INVALID_NUMBER_OF_LRP);
		testCompare(invLoc, invLocEqual, invLocUnEqual);
		testToString(invLoc);
	}

	@Test
	public void testImplementationClasses() {
		try {
			testCompare(new LocationReferenceImpl("id1", geoLoc,
					 2),
					new LocationReferenceImpl("id1", geoLoc,
							 2),
					new LocationReferenceImpl("id", lineLoc,
							 2));
		} catch (Exception e) {
			Assert.fail("Unexpected exception", e);
		}

		LocationReferencePointImpl lrpEqual = new LocationReferencePointImpl(1,
				FunctionalRoadClass.FRC_2, FormOfWay.MOTORWAY,
				Double.MAX_VALUE, Double.MIN_VALUE, Double.MIN_NORMAL, 0,
				FunctionalRoadClass.FRC_4, false);
		testCompare(LRP_IMPL, lrpEqual, LRP_IMPL_UNEQUAL);

		testCompare(OFFSETS_IMPL, OFFSETS_IMPL_EQUAL, OFFSETS_IMPL_UNEQUAL);
	}

	/**
	 * Delivers an input stream containing the data of the given resources
	 * expected in the class path.
	 * 
	 * @param name
	 *            The name of the resource.
	 * @return The input stream of data or null if the resource was not found.
	 */
	private InputStream getResourceAsStream(final String name) {
		return this.getClass().getClassLoader().getResourceAsStream(name);
	}
}
