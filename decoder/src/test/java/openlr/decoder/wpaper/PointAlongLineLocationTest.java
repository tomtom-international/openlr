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
package openlr.decoder.wpaper;

import openlr.LocationType;
import openlr.decoder.TestData;
import openlr.decoder.location.DecodedPointAlongLocation;
import openlr.location.Location;
import openlr.map.GeoCoordinates;
import openlr.map.Line;
import openlr.rawLocRef.RawLocationReference;

import java.util.List;

import static org.testng.Assert.*;


/**
 * Tests the encoding examples from the OpenLr White paper.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class PointAlongLineLocationTest extends AbstractDecoderTest {

    /**
     * An array of expected offset values in the order
     * {positive offset, negative offset}
     */
    static final int[] EXPECTED_OFFSETS = {28, -1};
    /**
     * The ID of the line expected to be the result of the decoding of the point
     * along line example. This refers to line 6 of the mocked database
     * configured in "DefaultMapDatabase.xml".
     */
    static final long EXPECTED_LINE = 6;
    /**
     * The processed input location reference;
     */
    private RawLocationReference inputLocationReference;

    /**
     * Checks the decoded location by testing if the location contains all the
     * expected lines.
     *
     * @param loc
     *            The decoded location.
     */
    @Override
    final void checkLocation(final Location loc) {
        assertEquals(loc.getLocationType(), LocationType.POINT_ALONG_LINE);

        DecodedPointAlongLocation palLoc = (DecodedPointAlongLocation) loc;

        GeoCoordinates point = decodedLoc.getAccessPoint();

        assertEquals(point.getLongitudeDeg(), TestData.PAL_POINT.x);
        assertEquals(point.getLatitudeDeg(), TestData.PAL_POINT.y);

        assertEquals(palLoc.getPoiLine().getID(), EXPECTED_LINE);

        assertNull(palLoc.getPointLocation());
        assertSame(palLoc.getOrientation(), inputLocationReference
                .getOrientation());
        assertSame(palLoc.getSideOfRoad(), inputLocationReference
                .getSideOfRoad());

        assertNotNull(palLoc.toString());

        List<? extends Line> lines = loc.getLocationLines();
        assertNull(lines);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    final int[] getExpectedOffsets() {
        return EXPECTED_OFFSETS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    final RawLocationReference getInputLocation() {
        if (inputLocationReference == null) {
            inputLocationReference =
                    td.getWhitepaperPointAlongLineLocationReference();
        }
        return inputLocationReference;
    }
}
