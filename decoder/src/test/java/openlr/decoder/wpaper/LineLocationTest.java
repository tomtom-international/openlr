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
import openlr.decoder.location.DecodedLineLocation;
import openlr.location.Location;
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
public class LineLocationTest extends AbstractDecoderTest {

    /**
     * The IDs of the lines expected to be the result of the decoding of the
     * line location example. In this case the specified IDs refer to
     * configuration "DefaultMapDatabase.xml".
     */
    private static final long[] EXPECTED_LINES = new long[]{4, 6, 10, 14, 16, 18, 19};

    /**
     * Checks the decoded location by testing if the location contains all the
     * expected lines.
     *
     * @param loc
     *            The decoded location.
     */
    @Override
    final void checkLocation(final Location loc) {
        assertEquals(loc.getLocationType(), LocationType.LINE_LOCATION);

        DecodedLineLocation lineLoc = (DecodedLineLocation) loc;

        assertNull(lineLoc.getAccessPoint());
        assertNull(lineLoc.getPointLocation());
        assertNull(lineLoc.getPoiLine());
        assertNull(lineLoc.getOrientation());
        assertNull(lineLoc.getSideOfRoad());

        assertNotNull(lineLoc.toString());

        List<? extends Line> lines = loc.getLocationLines();
        Line current;
        for (int i = 0; i < lines.size(); i++) {
            current = lines.get(i);
            assertEquals(current.getID(), EXPECTED_LINES[i],
                    "Unexpected line in decoded path!");
        }
    }

    @Override
    final int[] getExpectedOffsets() {
        return new int[]{0, 0};
    }

    @Override
    final RawLocationReference getInputLocation() {
        return td.getWhitepaperLineLocationReference();
    }
}
