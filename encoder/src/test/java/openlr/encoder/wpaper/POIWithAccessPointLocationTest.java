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
package openlr.encoder.wpaper;

import openlr.location.Location;
import openlr.map.GeoCoordinates;
import openlr.map.InvalidMapDataException;
import org.testng.annotations.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Tests the encoding examples from the OpenLr White paper.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class POIWithAccessPointLocationTest extends AbstractEncoderTest {

    /** Delivers the positive offset expected after encoding.*/
    private static final int EXPECTED_POSITIVE_OFFSET = 28;

    /** Holds a reference to the input location. */
    private Location inputLocation;

    /**
     * {@inheritDoc}
     * @throws InvalidMapDataException
     */
    @Override
    final Location getInputLocation() throws InvalidMapDataException {
        if (inputLocation == null) {
            inputLocation = td.getWhitepaperPOIWithAccessPointLocation();
        }
        return inputLocation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    final Lrps[] getExpectedLrps() {
        return new Lrps[]{Lrps.LRP4, Lrps.LRP5};
    }

    /**
     * {@inheritDoc}
     */
    @Override
    final int[] getExpectedOffsets() {
        return new int[]{EXPECTED_POSITIVE_OFFSET, -1};
    }

    /**
     * Test the coordinates of the encoded location against the input
     * coordinates.
     */
    @Test(dependsOnMethods = {"testWhitepaperLocation"})
    final void testAccessPoint() {
        GeoCoordinates coords = locRef.getRawLocationReferenceData()
                .getGeoCoordinates();
        GeoCoordinates inputPoi = inputLocation.getPointLocation();

        String message = "Encoded POI doesn't match the input data.";
        assertEquals(message, coords.getLatitudeDeg(), inputPoi
                .getLatitudeDeg());
        assertEquals(message, coords.getLongitudeDeg(), inputPoi
                .getLongitudeDeg());
    }
}
