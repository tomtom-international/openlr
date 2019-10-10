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
package openlr.decoder;

import openlr.LocationType;
import openlr.location.InvalidLocation;
import openlr.location.data.Orientation;
import openlr.location.data.SideOfRoad;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.InvalidMapDataException;
import openlr.map.Line;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static openlr.testutils.CommonObjectTestUtils.testCompare;
import static openlr.testutils.CommonObjectTestUtils.testToString;

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
     * Tests an {@link InvalidLocation} object.
     */
    @Test
    public final void testInvalidLocation() {

        Mockery mockery = new Mockery();
        final Line mockedLine = mockery.mock(Line.class);
        mockery.checking(new Expectations() {
            {
                allowing(mockedLine).getID();
                will(returnValue(Long.MAX_VALUE));
            }
        });

        List<Line> lines = Arrays.asList(mockedLine, mockedLine);

        InvalidLocation invLoc = new InvalidLocation("id",
                DecoderReturnCode.INVALID_LOCATION_TYPE, LocationType.UNKNOWN);
        InvalidLocation invLocEqual = new InvalidLocation("id",
                DecoderReturnCode.INVALID_LOCATION_TYPE, LocationType.UNKNOWN);
        InvalidLocation invLocUnEqual;
        try {
            invLocUnEqual = new InvalidLocation("id",
                    LocationType.POI_WITH_ACCESS_POINT,
                    DecoderReturnCode.NO_CANDIDATE_LINE_FOUND, lines, null, 0, 1,
                    new GeoCoordinatesImpl(0, 1),
                    new GeoCoordinatesImpl(0, 1), SideOfRoad.BOTH,
                    Orientation.AGAINST_LINE_DIRECTION);


            testCompare(invLoc, invLocEqual, invLocUnEqual);
            testToString(invLocUnEqual);

        } catch (InvalidMapDataException e) {
            Assert.fail("unexpected exception", e);
        }
    }

    /**
     * Tests return code objects.
     */
    @Test
    public final void testReturnCode() {

        testCompare(DecoderReturnCode.NO_CANDIDATE_LINE_FOUND,
                DecoderReturnCode.NO_CANDIDATE_LINE_FOUND,
                DecoderReturnCode.INVALID_LOCATION_TYPE);
        testToString(DecoderReturnCode.INVALID_LOCATION_TYPE);
    }
}
