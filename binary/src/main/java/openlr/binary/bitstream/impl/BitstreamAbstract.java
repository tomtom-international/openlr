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
package openlr.binary.bitstream.impl;

import java.util.Arrays;

/**
 * The Class BitstreamAbstract is the parent class for all input/output
 * bitstream classes and provides common bit masks and an internal data buffer.
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 * 
 */
public abstract class BitstreamAbstract {

	/** factor to shift between bit and byte */
	static final int BIT_BYTE_SHIFT = 3;

	/** number of bits in a byte */
	static final int BYTE_SIZE = 8;

	/** position of the highest bit in a byte (count starts at 0) */
	static final int HIGHEST_BIT = BYTE_SIZE - 1;

	/**
	 * Bit mask to mask the lowest bits of a byte
	 */
	static final int[] BITMASK = {0x00000000, 0x00000001, 0x00000003,
			0x00000007, 0x0000000f, 0x0000001f, 0x0000003f, 0x0000007f,
			0x000000ff, 0x000001ff, 0x000003ff, 0x000007ff, 0x00000fff,
			0x00001fff, 0x00003fff, 0x00007fff, 0x0000ffff, 0x0001ffff,
			0x0003ffff, 0x0007ffff, 0x000fffff, 0x001fffff, 0x003fffff,
			0x007fffff, 0x00ffffff, 0x01ffffff, 0x03ffffff, 0x07ffffff,
			0x0fffffff, 0x1fffffff, 0x3fffffff, 0x7fffffff, 0xffffffff };

	/**
	 * Complementary bit mask for negative integer values
	 */
	static final int[] COMPLEMENT_MASK = {0xffffffff, 0xfffffffe, 0xfffffffc,
			0xfffffff8, 0xfffffff0, 0xffffffe0, 0xffffffc0, 0xffffff80,
			0xffffff00, 0xfffffe00, 0xfffffc00, 0xfffff800, 0xfffff000,
			0xffffe000, 0xffffc000, 0xffff8000, 0xffff0000, 0xfffe0000,
			0xfffc0000, 0xfff80000, 0xfff00000, 0xffe00000, 0xffc00000,
			0xff800000, 0xff000000, 0xfe000000, 0xfc000000, 0xf8000000,
			0xf0000000, 0xe0000000, 0xc0000000, 0x80000000, 0x00000000 };

	/**
	 * Bit mask to mask one single bit of a byte (needed for the detection of
	 * negative numbers)
	 */
	static final int[] SIGNED_MASK = {0x00000000, 0x00000001, 0x00000002,
			0x00000004, 0x00000008, 0x00000010, 0x00000020, 0x00000040,
			0x00000080, 0x00000100, 0x00000200, 0x00000400, 0x00000800,
			0x00001000, 0x00002000, 0x00004000, 0x00008000, 0x00010000,
			0x00020000, 0x00040000, 0x00080000, 0x00100000, 0x00200000,
			0x00400000, 0x00800000, 0x01000000, 0x02000000, 0x04000000,
			0x08000000, 0x10000000, 0x20000000, 0x40000000, 0x80000000 };

	/** the default buffer size */
	protected static final int DEFAULT_BUFFER_LENGTH = 1024;

	/** maximum number of bits which can be read/put at a time */
	protected static final int MAX_BIT_SIZE = 32;

	/** The internal data buffer */
	protected byte[] buffer;

	/** The buffer size in bytes */
	protected int totalBufferLengthBytes = DEFAULT_BUFFER_LENGTH;

	/** the current bit position in the internal data buffer */
	protected int currentBit;

	/**
	 * Expand buffer the size of the internal data buffer. The new size is
	 * doubled.
	 */
	protected final void expandBuffer() {
		expandBuffer(2 * totalBufferLengthBytes);
	}

	/**
	 * Expand buffer the size of the internal data buffer to size new_length. If
	 * new_length is smaller than the current size, then nothing will be done.
	 * 
	 * @param newLength
	 *            the new buffer size
	 */
	protected final void expandBuffer(final int newLength) {
		if (newLength > totalBufferLengthBytes) {
			totalBufferLengthBytes = newLength;
			buffer = Arrays.copyOf(buffer, totalBufferLengthBytes);
		}
	}

	/**
	 * Creates the internal data buffer of size length.
	 * 
	 * @param length
	 *            the initial size of the internal data buffer
	 */
	protected final void createBuffer(final int length) {
		totalBufferLengthBytes = length;
		buffer = new byte[length];

	}

}
