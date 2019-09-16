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
package openlr.datex2;

import openlr.LocationReference;
import openlr.PhysicalFormatException;
import openlr.map.GeoCoordinates;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.InvalidMapDataException;
import openlr.rawLocRef.RawGeoCoordLocRef;
import openlr.rawLocRef.RawLocationReference;
import org.jmock.Mockery;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.*;

import static org.testng.Assert.fail;

/**
 * The Class OpenLRBinaryEncoderTest.
 */
public class GeoCoordTest {

    /** The Constant BINARY_ENCODER. */
    private static final OpenLRDatex2Encoder XML_ENCODER = new OpenLRDatex2Encoder();
    private static final File TEST_DATA = new File(
            "src/test/resources/geocoord.txt");
    private GeoCoordinates coord;
    private RawLocationReference rawLocRef;

    /**
     * Setup.
     */
    @BeforeTest
    public final void setup() {
        Mockery context = new Mockery();
        try {
            coord = new GeoCoordinatesImpl(6.12699, 49.60728);
        } catch (InvalidMapDataException e) {
            Assert.fail("Unexpected exception", e);
        }
        rawLocRef = new RawGeoCoordLocRef("", coord);
    }

    @Test
    public final void testEncoder01() {
        LocationReference ref = null;
        try {
            ref = XML_ENCODER.encodeData(rawLocRef);
            XmlWriter writer = new XmlWriter();
            StringWriter sw = new StringWriter();
            writer.saveDatex2Location(
                    (Datex2Location) ref.getLocationReferenceData(), sw);
            BufferedReader br = new BufferedReader(new FileReader(TEST_DATA));
            String[] lines = sw.toString().split("\n");
            int nrLines = lines.length;
            String current = null;
            for (int i = 0; i < nrLines; i++) {
                current = br.readLine();
                if (current == null) {
                    fail("data not complete");
                }
                if (!current.trim().equals(lines[i].trim())) {
                    fail("data invalid");
                }
            }
            if (br.readLine() != null) {
                fail("data not complete");
            }
        } catch (OpenLRDatex2Exception e) {
            fail("", e);
        } catch (JAXBException e) {
            fail("", e);
        } catch (SAXException e) {
            fail("", e);
        } catch (PhysicalFormatException e) {
            fail("", e);
        } catch (FileNotFoundException e) {
            fail("", e);
        } catch (IOException e) {
            fail("", e);
        }
    }

}
