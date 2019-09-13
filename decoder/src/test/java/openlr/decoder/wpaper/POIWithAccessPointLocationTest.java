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
package openlr.decoder.wpaper;

import openlr.LocationType;
import openlr.decoder.TestData;
import openlr.decoder.location.DecodedPoiAccessLocation;
import openlr.location.Location;
import openlr.map.GeoCoordinates;
import openlr.map.InvalidMapDataException;
import openlr.map.Line;
import openlr.rawLocRef.RawLocationReference;
import org.testng.Assert;

import java.util.List;

import static openlr.decoder.wpaper.PointAlongLineLocationTest.EXPECTED_LINE;
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
public class POIWithAccessPointLocationTest extends AbstractDecoderTest {

    /**
     * The processed input location reference;
     */
    private RawLocationReference inputLocationReference;

    /**
     * {@inheritDoc}
     */
    @Override
    final void checkLocation(final Location loc) {
        try {
            assertEquals(loc.getLocationType(),
                    LocationType.POI_WITH_ACCESS_POINT);

            DecodedPoiAccessLocation poiLoc = (DecodedPoiAccessLocation) loc;

            GeoCoordinates access = decodedLoc.getAccessPoint();

            assertEquals(access.getLongitudeDeg(), TestData.PAL_POINT.x);
            assertEquals(access.getLatitudeDeg(), TestData.PAL_POINT.y);

            GeoCoordinates poi = decodedLoc.getPointLocation();
            GeoCoordinates inputPoi = getInputLocation().getGeoCoordinates();

            assertEquals(poi.getLatitudeDeg(), inputPoi.getLatitudeDeg());
            assertEquals(poi.getLongitudeDeg(), inputPoi.getLongitudeDeg());

            assertEquals(access.getLongitudeDeg(), TestData.PAL_POINT.x);
            assertEquals(access.getLatitudeDeg(), TestData.PAL_POINT.y);

            assertEquals(poiLoc.getPoiLine().getID(), EXPECTED_LINE);

            assertSame(poiLoc.getOrientation(),
                    inputLocationReference.getOrientation());
            assertSame(poiLoc.getSideOfRoad(),
                    inputLocationReference.getSideOfRoad());

            assertNotNull(poiLoc.toString());

            List<? extends Line> lines = loc.getLocationLines();
            assertNull(lines);
        } catch (InvalidMapDataException e) {
            Assert.fail("Unexpected exception", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    final int[] getExpectedOffsets() {
        // we use the same data
        return PointAlongLineLocationTest.EXPECTED_OFFSETS;
    }

    /**
     * {@inheritDoc}
     *
     * @throws InvalidMapDataException
     */
    @Override
    final RawLocationReference getInputLocation()
            throws InvalidMapDataException {
        if (inputLocationReference == null) {
            inputLocationReference = td
                    .getWhitepaperPOIWithAccessPointLocation();
        }
        return inputLocationReference;
    }

}
