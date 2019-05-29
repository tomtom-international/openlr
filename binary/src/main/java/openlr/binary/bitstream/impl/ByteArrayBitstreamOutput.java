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

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import openlr.binary.bitstream.BitstreamException;

/**
 * A ByteArrayBitstreamOutput provides the ability to get bitstream content as a
 * byte array. Binary data can be put into a bitstream using the
 * {@link openlr.binary.bitstream.BitstreamOutput} interface and the data put
 * into the stream can be accessed as a byte array.
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public class ByteArrayBitstreamOutput extends BitstreamOutputImpl {

	/** The byte array output stream where all the data goes to. */
	private final ByteArrayOutputStream byteArrayOutputStream;

	/**
	 * This method constructs a new ByteArrayBitstreamOutput in order to get
	 * binary data as a byte array.
	 * 
	 * @throws BitstreamException
	 *             if a bitstream handling error occurred
	 */
	public ByteArrayBitstreamOutput() throws BitstreamException {
		this(DEFAULT_BUFFER_LENGTH);
	}

	/**
	 * This method constructs a new ByteArrayBitstreamOutput in order to get
	 * binary data as a byte array. It uses an initial size of buffer_length for
	 * the internal data buffer.
	 * 
	 * @param bufferLength
	 *            the initial size of the internal buffer
	 * 
	 * @throws BitstreamException
	 *             if a bitstream handling error occurred
	 */
	public ByteArrayBitstreamOutput(final int bufferLength)
			throws BitstreamException {
		super(bufferLength);
		byteArrayOutputStream = new ByteArrayOutputStream();
	}

	/**
	 * Returns the data being put into the bitstream as a byte array. This
	 * method will close the internal stream so that no further action is
	 * allowed after that call.
	 * 
	 * @return the binary data as a byte array
	 * 
	 * @throws BitstreamException
	 *             if a bitstream handling error occurred
	 */
	public final byte[] getData() throws BitstreamException {
		flushAndClose();
		return byteArrayOutputStream.toByteArray();
	}

	/**
	 * Flushes the internal data buffer into the byte array stream and closes
	 * the stream. No further action on this stream is allowed.
	 * 
	 * @throws BitstreamException
	 *             if a bitstream handling error occurred
	 */
	private void flushAndClose() throws BitstreamException {
		try {
			flushbits(byteArrayOutputStream);
			byteArrayOutputStream.close();
		} catch (IOException e) {
			throw new BitstreamException(
					BitstreamException.BitstreamErrorType.SYSTEMIOFAILED, e);
		}
	}
}
