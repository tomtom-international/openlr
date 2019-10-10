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
import openlr.location.GeoCoordLocation;
import openlr.location.Location;
import openlr.map.GeoCoordinates;
import openlr.map.InvalidMapDataException;
import openlr.rawLocRef.RawLocationReference;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Tests the decoding examples from the OpenLr White paper.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class GeoCoordinateLocationTest extends AbstractDecoderTest {

    /**
     * The processed input location reference;
     */
    private RawLocationReference inputLocationReference;


    /**
     * Test the coordinates of the encoded location against the input
     * coordinates.
     */
    @Test(dependsOnMethods = {"testWhitepaperLocation"})
    final void testAccessPoint() {
        try {
            GeoCoordinates inputCoord = getInputLocation().getGeoCoordinates();

            GeoCoordinates coords = decodedLoc.getPointLocation();

            assertNotNull(coords, "No GEO coordinate point in decoding result.");

            String message = "Encoded GEO-coordinate doesn't match the input data.";
            assertEquals(coords.getLatitudeDeg(), inputCoord.getLatitudeDeg(),
                    message);
            assertEquals(coords.getLongitudeDeg(), inputCoord.getLongitudeDeg(),
                    message);
        } catch (Exception e) {
            Assert.fail("Unepected exception", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    final void checkLocation(final Location loc) {

        assertEquals(loc.getLocationType(), LocationType.GEO_COORDINATES);

        GeoCoordLocation geoLoc = (GeoCoordLocation) loc;
        assertNull(geoLoc.getAccessPoint());
        assertNull(geoLoc.getPoiLine());
        assertNull(geoLoc.getOrientation());
        assertNull(geoLoc.getSideOfRoad());
        assertNull(geoLoc.getLocationLines());

        GeoCoordinates coordDec = geoLoc.getPointLocation();
        GeoCoordinates coordInput = geoLoc.getPointLocation();
        assertEquals(coordDec.getLatitudeDeg(), coordInput.getLatitudeDeg());
        assertEquals(coordDec.getLongitudeDeg(), coordInput.getLongitudeDeg());

        assertNotNull(geoLoc.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    final int[] getExpectedOffsets() {
        return new int[]{-1, -1};
    }

    /**
     * {@inheritDoc}
     * @throws InvalidMapDataException
     */
    @Override
    final RawLocationReference getInputLocation() throws InvalidMapDataException {
        if (inputLocationReference == null) {
            inputLocationReference =
                    td.getWhitepaperGeoCoordinateLocation();
        }
        return inputLocationReference;
    }


}
