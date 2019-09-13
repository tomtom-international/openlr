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
package openlr.binary.bitstream;

import openlr.binary.bitstream.impl.ByteArrayBitstreamOutput;
import openlr.binary.bitstream.impl.FileBitstreamOutput;
import openlr.binary.data.*;
import openlr.map.FormOfWay;
import openlr.map.FunctionalRoadClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * The Class BitstreamOutputTest.
 */
public class BitstreamOutputTest {

    /** The negative offset of test example 01. */
    private static final int NEG_OFFSET_01 = 4;
    /** The positive offset of test example 01. */
    private static final int POS_OFFSET_01 = 26;
    /** Attribute value "binary version 2" */
    private static final int VERSION_2 = 2;
    /** A reference to the valid output data of test example 01. */
    private byte[] validOutputData01;

    /**
     * Test bitstream01.
     */
    @Test
    public final void testBitstream01() {
        File dataFile01 = new File("src/test/resources/test01.stream");
        if (!dataFile01.exists()) {
            fail("resource is missing");
        }
        byte[] data = new byte[(int) dataFile01.length()];
        try {
            FileInputStream fis = new FileInputStream(dataFile01);
            fis.read(data);
        } catch (FileNotFoundException fnfe) {
            fail("missing resource", fnfe);
        } catch (IOException e) {
            fail("reading resource failed", e);
        }

        ByteArrayBitstreamOutput bo01;

        try {
            bo01 = new ByteArrayBitstreamOutput();

            fillStreamWithExample01Data(bo01);

            validOutputData01 = bo01.getData();
        } catch (BitstreamException be) {
            fail("bistream failure", be);
        }

        compareByteArrays(data, validOutputData01);
    }

    /**
     * Tests {@link FileBitstreamOutput} by comparing its output with the output
     * of the {@link BitstreamOutput} test in {@link #testBitstream01()}.
     */
    @Test(dependsOnMethods = {"testBitstream01"})
    public final void testFileBitstream01() {

        File file = null;
        FileBitstreamOutput fo = null;
        try {
            file = File.createTempFile("testFileBitstream", ".tmp");
            file.deleteOnExit();
            fo = new FileBitstreamOutput(file.getAbsolutePath());
        } catch (Exception e) {
            fail("Unexpected exception", e);
        }
        try {

            fillStreamWithExample01Data(fo);

            fo.flushAndClose();

        } catch (BitstreamException be) {
            fail("bistream failure", be);
        }

        byte[] data = new byte[(int) file.length()];
        try {
            FileInputStream fis = new FileInputStream(file);
            fis.read(data);
        } catch (FileNotFoundException fnfe) {
            fail("missing resource", fnfe);
        } catch (IOException e) {
            fail("reading resource failed", e);
        }

        compareByteArrays(data, validOutputData01);
    }

    /**
     * Fills the given {@link BitstreamOutput} with the data of line location
     * example 01.
     * @param out The output stream
     * @throws BitstreamException If an error occurs filling the stream
     */
    private void fillStreamWithExample01Data(final BitstreamOutput out)
            throws BitstreamException {

        Header h = new Header(0, 1, 0, VERSION_2);
        h.put(out);

        Attr1 lrp0attr1 = new Attr1(Lrp.FIRST_01.frc.getID(),
                Lrp.FIRST_01.fow.getID(), 0);
        Attr2 lrp0attr2 = new Attr2(Lrp.FIRST_01.lfrcnp.getID(),
                Lrp.FIRST_01.bearing);
        Attr3 lrp0attr3 = new Attr3(Lrp.FIRST_01.distanceToNext);

        FirstLRP lrp0 = new FirstLRP(Lrp.FIRST_01.longitude, Lrp.FIRST_01.latitude,
                lrp0attr1, lrp0attr2, lrp0attr3);
        lrp0.put(out);

        Attr1 lrp1attr1 = new Attr1(Lrp.INTERM_01.frc.getID(),
                Lrp.INTERM_01.fow.getID(), 0);
        Attr2 lrp1attr2 = new Attr2(Lrp.INTERM_01.lfrcnp.getID(),
                Lrp.INTERM_01.bearing);
        Attr3 lrp1attr3 = new Attr3(Lrp.INTERM_01.distanceToNext);
        IntermediateLRP lrp1 = new IntermediateLRP(Lrp.INTERM_01.longitude,
                Lrp.INTERM_01.latitude, lrp1attr1, lrp1attr2, lrp1attr3);
        lrp1.put(out);

        Attr1 lrp2attr1 = new Attr1(Lrp.LAST_01.frc.getID(),
                Lrp.LAST_01.fow.getID(), 0);
        // offsets present- flags = 1
        Attr4 lrp2attr4 = new Attr4(1, 1, Lrp.LAST_01.bearing);
        LastLRP lrp2 = new LastLRP(Lrp.LAST_01.longitude, Lrp.LAST_01.latitude,
                lrp2attr1, lrp2attr4);
        lrp2.put(out);

        Offset poff = new Offset(POS_OFFSET_01);
        poff.put(out);

        Offset noff = new Offset(NEG_OFFSET_01);
        noff.put(out);
    }

    /**
     * Test bitstream02.
     */
    @Test
    public final void testBitstream02() {
        File dataFile02 = new File("src/test/resources/test02.stream");
        if (!dataFile02.exists()) {
            fail("resource is missing");
        }
        byte[] data = new byte[(int) dataFile02.length()];
        try {
            FileInputStream fis = new FileInputStream(dataFile02);
            fis.read(data);
        } catch (FileNotFoundException fnfe) {
            fail("missing resource", fnfe);
        } catch (IOException e) {
            fail("reading resource failed", e);
        }

        ByteArrayBitstreamOutput bo02;
        byte[] outputData = new byte[0];
        try {
            bo02 = new ByteArrayBitstreamOutput();

            Header h = new Header(0, 1, 0, VERSION_2);
            h.put(bo02);

            Attr1 lrp0attr1 = new Attr1(Lrp.FIRST_02.frc.getID(),
                    Lrp.FIRST_02.fow.getID(), 0);
            Attr2 lrp0attr2 = new Attr2(Lrp.FIRST_02.lfrcnp.getID(),
                    Lrp.FIRST_02.bearing);
            Attr3 lrp0attr3 = new Attr3(Lrp.FIRST_02.distanceToNext);
            FirstLRP lrp0 = new FirstLRP(Lrp.FIRST_02.longitude,
                    Lrp.FIRST_02.latitude, lrp0attr1, lrp0attr2, lrp0attr3);
            lrp0.put(bo02);

            Attr1 lrp1attr1 = new Attr1(Lrp.LAST_02.frc.getID(),
                    Lrp.LAST_02.fow.getID(), 0);
            // no offsets- flags = 0
            Attr4 lrp1attr4 = new Attr4(0, 0, Lrp.LAST_02.bearing);
            LastLRP lrp1 = new LastLRP(Lrp.LAST_02.longitude,
                    Lrp.LAST_02.latitude, lrp1attr1, lrp1attr4);
            lrp1.put(bo02);

            outputData = bo02.getData();
        } catch (BitstreamException be) {
            fail("bistream failure", be);
        }
        assertEquals(data.length, outputData.length);
        for (int i = 0; i < data.length; i++) {
            assertEquals(data[i], outputData[i]);
        }
    }

    /**
     * Compares two byte arrays.
     * @param a array a
     * @param b array b
     */
    private void compareByteArrays(final byte[] a, final byte[] b) {
        assertEquals(a.length, b.length);
        for (int i = 0; i < a.length; i++) {
            assertEquals(a[i], b[i]);
        }
    }


    /**
     * Provides data for the tests of this class.
     */
    private enum Lrp {

        /** LRP #1 used in test example 01. */
        FIRST_01(FunctionalRoadClass.FRC_4, FormOfWay.SINGLE_CARRIAGEWAY,
                2482113, 324745, 8, 80, FunctionalRoadClass.FRC_4),

        /** LRP #2 used in test example 01. */
        INTERM_01(FunctionalRoadClass.FRC_4, FormOfWay.SINGLE_CARRIAGEWAY,
                1424, 6206, 2, 47, FunctionalRoadClass.FRC_4),
        /** LRP #2 used in test example 01. */
        LAST_01(FunctionalRoadClass.FRC_4, FormOfWay.SINGLE_CARRIAGEWAY, 1196,
                -2353, 8, 0, null),

        /** LRP #1 used in test example 02. */
        FIRST_02(FunctionalRoadClass.FRC_4, FormOfWay.SLIPROAD, 2434700,
                293583, 8, 47, FunctionalRoadClass.FRC_4),
        /** LRP #2 used in test example 02. */
        LAST_02(FunctionalRoadClass.FRC_4, FormOfWay.SINGLE_CARRIAGEWAY, 849,
                -692, 15, 0, null);

        /** The bearing of the line referenced by the LRP. */
        private final int bearing;

        /** The distance to the next LRP along the shortest-path. */
        private final int distanceToNext;

        /** The functional road class of the line referenced by the LRP. */
        private final FunctionalRoadClass frc;

        /** The form of way of the line referenced by the LRP. */
        private final FormOfWay fow;

        /** The lowest functional road class to the next LRP. */
        private final FunctionalRoadClass lfrcnp;

        /** The longitude coordinate. */
        private final int longitude;

        /** The latitude coordinate. */
        private final int latitude;

        /**
         * @param frcValue
         *            The FRC
         * @param fowValue
         *            The FOW
         * @param longitudeValue
         *            The longitude
         * @param latitudeValue
         *            The latitude
         * @param bearingValue
         *            TThe bearing
         * @param dnpValue
         *            The DNP
         * @param lfrcnpValue
         *            The lowest FRC to the next point.
         */
        private Lrp(final FunctionalRoadClass frcValue,
                    final FormOfWay fowValue, final int latitudeValue,
                    final int longitudeValue, final int bearingValue,
                    final int dnpValue, final FunctionalRoadClass lfrcnpValue) {
            this.longitude = longitudeValue;
            this.latitude = latitudeValue;
            this.frc = frcValue;
            this.fow = fowValue;
            this.bearing = bearingValue;
            this.lfrcnp = lfrcnpValue;
            this.distanceToNext = dnpValue;
        }
    }

}
