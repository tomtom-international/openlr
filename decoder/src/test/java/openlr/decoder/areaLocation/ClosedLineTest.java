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
package openlr.decoder.areaLocation;

import openlr.LocationReferencePoint;
import openlr.decoder.OpenLRDecoder;
import openlr.decoder.OpenLRDecoderParameter;
import openlr.decoder.TestData;
import openlr.decoder.TestMap;
import openlr.location.Location;
import openlr.map.Line;
import openlr.map.MapDatabase;
import openlr.rawLocRef.RawLocationReference;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class ClosedLineTest {

    @Test
    public final void testClosedLineDecode1() {
        try {
            MapDatabase mdb = TestMap.getTestMapDatabase();
            OpenLRDecoderParameter params = new OpenLRDecoderParameter.Builder().with(mdb).buildParameter();
            OpenLRDecoder decoder = new OpenLRDecoder();
            List<LocationReferencePoint> locRefPoints = new ArrayList<LocationReferencePoint>();
            RawLocationReference rawLocRef = TestData.getInstance().getClosedLineLocationReference1();
            Location loc = decoder.decodeRaw(params, rawLocRef);
            Assert.assertNotNull(loc);
            Assert.assertTrue(loc.isValid());
            Assert.assertEquals(loc.getID(), "closedLine1");
            Assert.assertNotNull(loc.getLocationLines());
            List<? extends Line> lines = loc.getLocationLines();
            Assert.assertFalse(lines.isEmpty());
            List<Line> expectedLines = new ArrayList<Line>();
            expectedLines.add(mdb.getLine(15280001229940L));
            expectedLines.add(mdb.getLine(15280001229939L));
            expectedLines.add(mdb.getLine(-15280001229312L));
            expectedLines.add(mdb.getLine(15280001441181L));
            expectedLines.add(mdb.getLine(15280001441182L));
            expectedLines.add(mdb.getLine(-15280001229331L));
            expectedLines.add(mdb.getLine(-15280001229330L));
            expectedLines.add(mdb.getLine(15280001229308L));
            expectedLines.add(mdb.getLine(-15280001229177L));
            expectedLines.add(mdb.getLine(-15280001229176L));
            expectedLines.add(mdb.getLine(-15280001229175L));
            Assert.assertEquals(lines.size(), expectedLines.size());
            int i = 0;
            for (Line l : lines) {
                Assert.assertEquals(l, expectedLines.get(i));
                i++;
            }
        } catch (Exception e) {
            Assert.fail("Unexpected exception", e);
        }
    }

    @Test
    public final void testClosedLineDecode2() {
        try {
            MapDatabase mdb = TestMap.getTestMapDatabase();
            OpenLRDecoderParameter params = new OpenLRDecoderParameter.Builder().with(mdb).buildParameter();
            OpenLRDecoder decoder = new OpenLRDecoder();
            List<LocationReferencePoint> locRefPoints = new ArrayList<LocationReferencePoint>();
            RawLocationReference rawLocRef = TestData.getInstance().getClosedLineLocationReference2();
            Location loc = decoder.decodeRaw(params, rawLocRef);
            Assert.assertNotNull(loc);
            Assert.assertTrue(loc.isValid());
            Assert.assertEquals(loc.getID(), "closedLine2");
            Assert.assertNotNull(loc.getLocationLines());
            List<? extends Line> lines = loc.getLocationLines();
            Assert.assertFalse(lines.isEmpty());
            List<Line> expectedLines = new ArrayList<Line>();
            expectedLines.add(mdb.getLine(15280001229306L));
            expectedLines.add(mdb.getLine(-15280001229314L));
            expectedLines.add(mdb.getLine(-15280001229303L));
            expectedLines.add(mdb.getLine(-15280001229941L));
            expectedLines.add(mdb.getLine(-15280001229942L));
            expectedLines.add(mdb.getLine(-15280001229222L));
            expectedLines.add(mdb.getLine(-15280001229300L));
            expectedLines.add(mdb.getLine(-15280001229320L));
            expectedLines.add(mdb.getLine(-15280001229321L));
            expectedLines.add(mdb.getLine(15280001229299L));
            expectedLines.add(mdb.getLine(-15280001229191L));
            expectedLines.add(mdb.getLine(15280001305196L));
            expectedLines.add(mdb.getLine(15280001305197L));
            expectedLines.add(mdb.getLine(15280001437103L));
            expectedLines.add(mdb.getLine(15280001229190L));
            expectedLines.add(mdb.getLine(15280001229189L));
            expectedLines.add(mdb.getLine(15280001229188L));
            expectedLines.add(mdb.getLine(15280001229187L));
            expectedLines.add(mdb.getLine(15280001229330L));
            expectedLines.add(mdb.getLine(15280001229331L));
            expectedLines.add(mdb.getLine(15280001441173L));
            expectedLines.add(mdb.getLine(15280001441165L));
            expectedLines.add(mdb.getLine(-15280001229220L));
            Assert.assertEquals(lines.size(), expectedLines.size());
            int i = 0;
            for (Line l : lines) {
                Assert.assertEquals(l, expectedLines.get(i));
                i++;
            }
        } catch (Exception e) {
            Assert.fail("Unexpected exception", e);
        }
    }

}
