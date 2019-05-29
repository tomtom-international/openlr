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
package openlr.binary;

import static org.testng.Assert.assertEquals;
import org.apache.commons.codec.binary.Base64;
import org.testng.annotations.Test;

/**
 * The Class ByteArrayTest.
 */
public class ByteArrayTest {
	
	/** The Constant base64normal. */
	private static final String BASE64NORMAL = "IwOd/iUESw==";
	
	/** The Constant locRef. */
	private static final byte[] LOC_REF = {0x23, 0x03, (byte) 0x9d, (byte) 0xfe, 0x25, 0x04, 0x4b};
	
	/** The Constant BASE64URL. */
	private static final String BASE64URL = "IwOd_iUESw";
	
	/**
	 * Test different base64-encodings.
	 */
	@Test
	public final void testEncoding() {
		ByteArray ba = new ByteArray(BASE64NORMAL);
		ByteArray ba2 = new ByteArray(LOC_REF);
		ByteArray ba3 = new ByteArray(BASE64URL);
		assertEquals(ba, ba2);
		assertEquals(ba, ba3);
		assertEquals(ba2, ba3);
	}

}
