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
package openlr.encoder;

import openlr.LocationReferencePoint;
import openlr.OpenLRProcessingException;
import openlr.encoder.data.LocRefPoint;
import openlr.encoder.data.OffsetData;
import openlr.encoder.properties.OpenLREncoderProperties;
import openlr.location.data.Orientation;
import openlr.location.data.SideOfRoad;
import openlr.map.*;
import openlr.map.utils.GeometryUtils;
import openlr.rawLocRef.RawLineLocRef;
import openlr.rawLocRef.RawPoiAccessLocRef;
import openlr.rawLocRef.RawPointAlongLocRef;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static openlr.testutils.CommonObjectTestUtils.testCompare;
import static openlr.testutils.CommonObjectTestUtils.testToString;
import static org.testng.Assert.fail;

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
     * An instance of {@link OffsetData}.
     */
    private static final OffsetData OFFSETS_IMPL = new OffsetData(2, 1);
    /**
     * An instance of {@link OffsetData} that should be equal to
     * {@link #OFFSETS_IMPL}.
     */
    private static final OffsetData OFFSETS_IMPL_EQUAL = new OffsetData(2, 1);
    /**
     * An instance of {@link LocRefPoint}.
     */
    private final LocRefPoint lrp;
    /**
     * A {@link LocRefPoint} instance that should not be equal to {@link #lrp}.
     */
    private final LocRefPoint lrpUnequal;

    /**
     * Performs some setup tasks.
     *
     * @throws OpenLRProcessingException
     *             If an error occurs creating the {@link LocRefPoint}s.
     */
    public EqualsHashCodeToStringTest() throws OpenLRProcessingException {

        OpenLREncoderProperties config = TestData.getInstance().getProperties();
        final Line mockedLine = mockLine(1);

        List<Line> linesA = Arrays.asList(mockedLine, mockedLine);
        List<Line> linesB = Arrays.asList(mockedLine, mockedLine, mockedLine);

        try {
            lrp = new LocRefPoint(linesA, config);

            lrpUnequal = new LocRefPoint(linesB, config);
        } catch (OpenLRProcessingException e) {
            fail("Unexpected exception", e);
            throw e;
        }

    }

    /**
     * Mocks a line object.
     *
     * @param id
     *            The desired line ID.
     * @return The mocked line.
     */
    private Line mockLine(final long id) {

        Mockery mockery = new Mockery();
        long nodeId = id << 2;
        final Node mockedNode = mockery.mock(Node.class, "node" + nodeId);
        mockery.checking(new Expectations() {
            {
                allowing(mockedNode).getID();
                will(returnValue(Long.MAX_VALUE));
            }
        });
        mockery.checking(new Expectations() {
            {
                allowing(mockedNode).getLongitudeDeg();
                will(returnValue(Double.MAX_VALUE));
            }
        });
        mockery.checking(new Expectations() {
            {
                allowing(mockedNode).getLatitudeDeg();
                will(returnValue(Double.MIN_VALUE));
            }
        });
        mockery.checking(new Expectations() {
            {
                allowing(mockedNode).getGeoCoordinates();
                will(returnValue(GeoCoordinatesImpl.newGeoCoordinatesUnchecked(179, 89)));
            }
        });
        final Line mockedLine = mockery.mock(Line.class, "line" + id);
        mockery.checking(new Expectations() {
            {
                allowing(mockedLine).getID();
                will(returnValue(id));
            }
        });
        mockery.checking(new Expectations() {
            {
                allowing(mockedLine).getStartNode();
                will(returnValue(mockedNode));
            }
        });
        mockery.checking(new Expectations() {
            {
                allowing(mockedLine).getEndNode();
                will(returnValue(mockedNode));
            }
        });
        mockery.checking(new Expectations() {
            {
                allowing(mockedLine).getLineLength();
                will(returnValue(2));
            }
        });
        return mockedLine;
    }

    /**
     * Tests the raw location reference classes.
     */
    @Test
    public final void testRawLocationReferenceObjects() {

        List<LocationReferencePoint> lrps = new ArrayList<LocationReferencePoint>(
                2);
        lrps.add(lrp);
        lrps.add(lrpUnequal);

        RawLineLocRef lineLoc = new RawLineLocRef("id", lrps, OFFSETS_IMPL);
        RawLineLocRef lineLocEqual = new RawLineLocRef("id",
                Collections.unmodifiableList(lrps), OFFSETS_IMPL_EQUAL);
        RawLineLocRef lineLocUnequal = new RawLineLocRef("id", lrps,
                new OffsetData(2, 0));
        testCompare(lineLoc, lineLocEqual, lineLocUnequal);

        try {
            GeoCoordinates geoCoordinate = new GeoCoordinatesImpl(
                    GeometryUtils.MAX_LON, GeometryUtils.MIN_LAT);
            GeoCoordinates geoCoordinateEqual;

            geoCoordinateEqual = new GeoCoordinatesImpl(GeometryUtils.MAX_LON,
                    GeometryUtils.MIN_LAT);

            RawPoiAccessLocRef poiLoc = new RawPoiAccessLocRef("id", lrp,
                    lrpUnequal, OFFSETS_IMPL, geoCoordinate, SideOfRoad.BOTH,
                    Orientation.AGAINST_LINE_DIRECTION);
            RawPoiAccessLocRef poiLocEqual = new RawPoiAccessLocRef("id", lrp,
                    lrpUnequal, OFFSETS_IMPL_EQUAL, geoCoordinateEqual,
                    SideOfRoad.BOTH, Orientation.AGAINST_LINE_DIRECTION);
            RawPoiAccessLocRef poiLocUnEqual = new RawPoiAccessLocRef("id",
                    lrp, lrpUnequal, OFFSETS_IMPL, geoCoordinate,
                    SideOfRoad.LEFT, Orientation.AGAINST_LINE_DIRECTION);
            testCompare(poiLoc, poiLocEqual, poiLocUnEqual);
            testToString(poiLoc);

        } catch (InvalidMapDataException e) {
            fail("Unexpected exception!", e);
        }

        RawPointAlongLocRef palLoc = new RawPointAlongLocRef("id", lrp,
                lrpUnequal, OFFSETS_IMPL, SideOfRoad.LEFT, Orientation.BOTH);
        RawPointAlongLocRef palLocEqual = new RawPointAlongLocRef("id", lrp,
                lrpUnequal, OFFSETS_IMPL_EQUAL, SideOfRoad.LEFT,
                Orientation.BOTH);
        RawPointAlongLocRef palLocUnequal = new RawPointAlongLocRef("id", lrp,
                lrpUnequal, OFFSETS_IMPL_EQUAL, SideOfRoad.RIGHT,
                Orientation.BOTH);
        testCompare(palLoc, palLocEqual, palLocUnequal);
        testToString(palLoc);
    }
}
