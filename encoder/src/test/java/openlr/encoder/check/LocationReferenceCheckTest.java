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
 * Copyright (C) 2009,2010 TomTom International B.V.
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
package openlr.encoder.check;

import openlr.OpenLRProcessingException;
import openlr.encoder.TestData;
import openlr.encoder.data.ExpansionData;
import openlr.encoder.data.ExpansionHelper;
import openlr.encoder.data.LocRefData;
import openlr.encoder.data.LocRefPoint;
import openlr.encoder.locRefAdjust.LocationReferenceAdjust;
import openlr.encoder.locRefAdjust.worker.BasicLrpBasedLocRefAdjust;
import openlr.location.Location;
import openlr.location.LocationFactory;
import openlr.map.Line;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * Tests class {@link LocationReferenceAdjust}.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class LocationReferenceCheckTest {

    /** The test data. */
    private static final TestData TD = TestData.getInstance();


    /**
     * The lines used for LRP 1 in the expansion test (to be found in map
     * configuration DefaultMapDatabase.xml).
     */
    private static final List<Line> LINES_LRP1 = Arrays.asList(
            TD.getMapDatabase().getLine(4), TD.getMapDatabase().getLine(6),
            TD.getMapDatabase().getLine(10), TD.getMapDatabase().getLine(14),
            TD.getMapDatabase().getLine(16), TD.getMapDatabase().getLine(18));

    /**
     * The line used for the last LRP in the expansion test of the map
     * configuration DefaultMapDatabase.xml.
     */
    private static final Line LINE_LAST_LRP = TD.getMapDatabase().getLine(19);

    /**
     * The negative offset.
     */
    private static final int NEGATIVE_OFFSET = 50;

    /**
     * The positive offset.
     */
    private static final int POSITIVE_OFFSET = 200;


    /**
     * Tests the expansion of a location at start and end.
     */
    @Test
    public final void testExpansion() {

        Location loc2 = LocationFactory.createLineLocationWithOffsets("2",
                LINES_LRP1, POSITIVE_OFFSET, NEGATIVE_OFFSET);

        ExpansionData expLoc = null;
        LocRefData lrd = new LocRefData(loc2);
        try {
            expLoc = ExpansionHelper.createExpandedLocation(TD
                    .getProperties(), TD.getMapDatabase(), lrd);
        } catch (OpenLRProcessingException e) {
            fail("failed expanding location", e);
        }

        LocRefPoint lrp1 = null;
        LocRefPoint lastLrp = null;
        lrd.setExpansion(expLoc);
        try {
            lrp1 = new LocRefPoint(ExpansionHelper.getExpandedLocation(lrd), TD
                    .getProperties());
            lastLrp = new LocRefPoint(LINE_LAST_LRP, TD.getProperties());
        } catch (OpenLRProcessingException e) {
            fail("", e);
        }

        LocationReferenceAdjust lrc = new BasicLrpBasedLocRefAdjust();
        //List<LocRefPoint> newLRPs = null;
        lrd.setLocRefPoints(Arrays.asList(lrp1, lastLrp));
        try {
            lrc.adjustLocationReference(TD.getProperties(),
                    lrd);
        } catch (OpenLRProcessingException e) {
            fail("checking location reference failed", e);
        }

        assertEquals(lrd.getLocRefPoints().size(), 2);
    }

}
