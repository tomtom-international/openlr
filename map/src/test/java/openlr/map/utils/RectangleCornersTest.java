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
package openlr.map.utils;

import openlr.map.GeoCoordinates;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.InvalidMapDataException;
import openlr.map.RectangleCorners;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class RectangleCornersTest {

    /**
     * Test rectangle corners.
     */
    @Test
    public final void testRectangleCorners01() {
        RectangleCorners rc = null;
        try {
            rc = new RectangleCorners(GeoCoordinatesImpl.newGeoCoordinatesUnchecked(1, 1),
                    GeoCoordinatesImpl.newGeoCoordinatesUnchecked(4, 4));
        } catch (InvalidMapDataException e) {
            Assert.fail("Unexpected exception", e);
        }

        GeoCoordinates ul = rc.getUpperLeft();
        GeoCoordinates lr = rc.getLowerRight();
        Assert.assertEquals(rounding(ul.getLatitudeDeg()), 4.0);
        Assert.assertEquals(rounding(ul.getLongitudeDeg()), 1.0);
        Assert.assertEquals(rounding(lr.getLatitudeDeg()), 1.0);
        Assert.assertEquals(rounding(lr.getLongitudeDeg()), 4.0);
    }

    /**
     * Test rectangle corners.
     */
    @Test
    public final void testRectangleCorners02() {
        RectangleCorners rc = null;
        try {
            rc = new RectangleCorners(
                    new GeoCoordinatesImpl(1, 1), new GeoCoordinatesImpl(4, 4));
        } catch (InvalidMapDataException e) {
            Assert.fail("Unexpected exception", e);
        }
        List<? extends GeoCoordinates> corners = rc.getCornerPoints();
        Assert.assertEquals(4, corners.size());

        Assert.assertEquals(rounding(corners.get(0).getLatitudeDeg()), 1.0);
        Assert.assertEquals(rounding(corners.get(0).getLongitudeDeg()), 1.0);

        Assert.assertEquals(rounding(corners.get(1).getLatitudeDeg()), 1.0);
        Assert.assertEquals(rounding(corners.get(1).getLongitudeDeg()), 4.0);

        Assert.assertEquals(rounding(corners.get(2).getLatitudeDeg()), 4.0);
        Assert.assertEquals(rounding(corners.get(2).getLongitudeDeg()), 4.0);

        Assert.assertEquals(rounding(corners.get(3).getLatitudeDeg()), 4.0);
        Assert.assertEquals(rounding(corners.get(3).getLongitudeDeg()), 1.0);
    }

    /**
     * Rounding.
     *
     * @param val
     *            the val
     * @return the double
     */
    private double rounding(final double val) {
        double temp = val * 100000;
        temp = Math.round(temp);
        return temp / 100000;
    }

}
