/**
 * Licensed to the TomTom International B.V. under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  TomTom International B.V.
 * licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
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
package openlr.binary.bitstream;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;

import openlr.binary.bitstream.impl.FileBitstreamInput;
import openlr.binary.data.FirstLRP;
import openlr.binary.data.Header;
import openlr.binary.data.IntermediateLRP;
import openlr.binary.data.LastLRP;
import openlr.binary.data.Offset;

import org.testng.annotations.Test;

/**
 * The Class BitstreamInputTest.
 */
public class BitstreamInputTest {
	
	/**
	 * Test bitstream01.
	 */
	@Test
	public final void testBitstream01() {
		//TODO make path to test files independent
		File dataFile01 = new File("src/test/resources/test01.stream");
		if (!dataFile01.exists()) {
			fail("resource is missing");
		}
		BitstreamInput fbi01;
		try {
			fbi01 = new FileBitstreamInput(dataFile01
					.getAbsolutePath());
			
			Header h = new Header(fbi01);
			assertEquals(h.getVer(), 2);
			assertEquals(h.getArf(), 0);
			assertEquals(h.getAf(), 1);
			
			FirstLRP lrp0 = new FirstLRP(fbi01);
			assertEquals(lrp0.getLat(), 2482113);
			assertEquals(lrp0.getLon(), 324745);
			assertNotNull(lrp0.getAttrib1());
			assertNotNull(lrp0.getAttrib2());
			assertNotNull(lrp0.getAttrib3());
			assertEquals(lrp0.getAttrib1().getFow(), 3);
			assertEquals(lrp0.getAttrib1().getFrc(), 4);
			assertEquals(lrp0.getAttrib2().getBear(), 8);
			assertEquals(lrp0.getAttrib2().getLfrcnp(), 4);
			assertEquals(lrp0.getAttrib3().getDnp(), 80);
			
			IntermediateLRP lrp1 = new IntermediateLRP(fbi01);
			assertEquals(lrp1.getLat(), 1424);
			assertEquals(lrp1.getLon(), 6206);
			assertNotNull(lrp1.getAttrib1());
			assertNotNull(lrp1.getAttrib2());
			assertNotNull(lrp1.getAttrib3());
			assertEquals(lrp1.getAttrib1().getFow(), 3);
			assertEquals(lrp1.getAttrib1().getFrc(), 4);
			assertEquals(lrp1.getAttrib2().getBear(), 2);
			assertEquals(lrp1.getAttrib2().getLfrcnp(), 4);
			assertEquals(lrp1.getAttrib3().getDnp(), 47);
			
			LastLRP lrp2 = new LastLRP(fbi01);
			assertEquals(lrp2.getLat(), 1196);
			assertEquals(lrp2.getLon(), -2353);
			assertNotNull(lrp2.getAttrib1());
			assertNotNull(lrp2.getAttrib4());
			assertEquals(lrp2.getAttrib1().getFow(), 3);
			assertEquals(lrp2.getAttrib1().getFrc(), 4);
			assertEquals(lrp2.getAttrib4().getBear(), 8);
			assertEquals(lrp2.getAttrib4().getNOffsetf(), 1);
			assertEquals(lrp2.getAttrib4().getPOffsetf(), 1);
			
			Offset poff = new Offset(fbi01);
			assertEquals(poff.getOffset(), 26);
			
			Offset noff = new Offset(fbi01);
			assertEquals(noff.getOffset(), 4);		
		} catch (FileNotFoundException fnfe) {
			fail("file not found exception", fnfe);
		} catch (BitstreamException be) {
			fail("bistream failure", be);
		}		
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
		BitstreamInput fbi02;
		
		try {
			fbi02 = new FileBitstreamInput(dataFile02
					.getAbsolutePath());
			
			Header h = new Header(fbi02);
			assertEquals(h.getVer(), 2);
			assertEquals(h.getArf(), 0);
			assertEquals(h.getAf(), 1);
			
			FirstLRP lrp0 = new FirstLRP(fbi02);
			assertEquals(lrp0.getLat(), 2434700);
			assertEquals(lrp0.getLon(), 293583);
			assertNotNull(lrp0.getAttrib1());
			assertNotNull(lrp0.getAttrib2());
			assertNotNull(lrp0.getAttrib3());
			assertEquals(lrp0.getAttrib1().getFow(), 6);
			assertEquals(lrp0.getAttrib1().getFrc(), 4);
			assertEquals(lrp0.getAttrib2().getBear(), 8);
			assertEquals(lrp0.getAttrib2().getLfrcnp(), 4);
			assertEquals(lrp0.getAttrib3().getDnp(), 47);
			
			LastLRP lrp2 = new LastLRP(fbi02);
			assertEquals(lrp2.getLat(), 849);
			assertEquals(lrp2.getLon(), -692);
			assertNotNull(lrp2.getAttrib1());
			assertNotNull(lrp2.getAttrib4());
			assertEquals(lrp2.getAttrib1().getFow(), 3);
			assertEquals(lrp2.getAttrib1().getFrc(), 4);
			assertEquals(lrp2.getAttrib4().getBear(), 15);
			assertEquals(lrp2.getAttrib4().getNOffsetf(), 0);
			assertEquals(lrp2.getAttrib4().getPOffsetf(), 0);
			
		} catch (FileNotFoundException fnfe) {
			fail("file not found exception", fnfe);
		} catch (BitstreamException be) {
			fail("bistream failure", be);
		}
	}

}
