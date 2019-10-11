/**
 * Licensed to the TomTom International B.V. under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  TomTom International B.V.
 * licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * <p>
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
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License and the extra
 *  conditions for OpenLR. (see openlr-license.txt)
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
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
package openlr.binary;

import openlr.LocationReference;
import openlr.LocationReferencePoint;
import openlr.LocationType;
import openlr.PhysicalFormatException;
import openlr.binary.impl.LocationReferenceBinaryImpl;
import openlr.rawLocRef.RawClosedLineLocRef;
import openlr.rawLocRef.RawLocationReference;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class ClosedLineTest {

    /** The Constant EXPECTED_TRIANGLE_RESULT. */
    private static final byte[] EXPECTED_CLOSEDLINE_RESULT = {0x5b, 0x04, 0x5b,
            0x5b, 0x23, 0x46, -0x0b, 0x1a, 0x6c, 0x09, 0x00, -0x65, -0x02, 0x3b,
            0x1b, -0x4c, 0x04, 0x2b, 0x19};

    @Test
    public final void testClosedLine() {
        final Mockery context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);

        final List<LocationReferencePoint> locRefPoints = Utils.mockLrps123(
                context, new Lrp[]{Lrp.LINE_ENC_LRP1, Lrp.LINE_ENC_LRP2,
                        Lrp.LINE_ENC_LRP3_CLOSEDLINE});

        RawLocationReference rawLocRef = new RawClosedLineLocRef("closedLine",
                locRefPoints);
        OpenLRBinaryEncoder encoder = new OpenLRBinaryEncoder();
        LocationReference lr = encoder.encodeData(rawLocRef);
        Assert.assertNotNull(lr);
        Assert.assertTrue(lr.isValid());
        Assert.assertEquals(lr.getID(), "closedLine");
        Assert.assertEquals(lr.getLocationType(), LocationType.CLOSED_LINE);
        Assert.assertNotNull(lr.getLocationReferenceData());
        Utils.checkBinData((ByteArray) lr.getLocationReferenceData(),
                EXPECTED_CLOSEDLINE_RESULT, 3);
    }

    @Test
    public final void testClosedLineDecode() {
        try {
            LocationReference lr = new LocationReferenceBinaryImpl("closedLine", new ByteArray(EXPECTED_CLOSEDLINE_RESULT));
            OpenLRBinaryDecoder decoder = new OpenLRBinaryDecoder();
            RawLocationReference rawLocRef = decoder.decodeData(lr);
            Assert.assertNotNull(rawLocRef);
            Assert.assertTrue(rawLocRef.isValid());
            Assert.assertEquals(rawLocRef.getLocationType(), LocationType.CLOSED_LINE);
            Assert.assertEquals(rawLocRef.getID(), "closedLine");

            Lrp[] expectedLrps = new Lrp[]{Lrp.LINE_DEC_LRP1, Lrp.LINE_DEC_LRP2_WP,
                    Lrp.LINE_DEC_LRP3_CLOSEDLINE};
            Utils.checkDecodedLrps(rawLocRef, expectedLrps);

        } catch (PhysicalFormatException e) {
            Assert.fail("Unexpected exception", e);
        }
    }

}
