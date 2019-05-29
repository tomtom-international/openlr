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

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import openlr.binary.bitstream.BitstreamException;

/**
 * A FileBitstreamInput provides the ability to read bitstream content from a
 * file. Binary data can be read from a bitstream using the
 * {@link openlr.binary.bitstream.BitstreamInput} interface.
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public class FileBitstreamInput extends BitstreamInputImpl {

	/**
	 * This method constructs a new bitstream reading data from the file
	 * filename.
	 * 
	 * @param filename
	 *            file to open
	 * 
	 * @throws FileNotFoundException
	 *             if the file was not found
	 * @throws BitstreamException
	 *             if a bitstream handling error occurred
	 */
	public FileBitstreamInput(final String filename) throws BitstreamException,
			FileNotFoundException {
		this(filename, DEFAULT_BUFFER_LENGTH);
	}

	/**
	 * This method constructs a new bitstream reading data from the file
	 * filename and an initial internal buffer size of buffer_length.
	 * 
	 * @param filename
	 *            file to open
	 * @param bufferLength
	 *            the initial buffer size
	 * 
	 * @throws FileNotFoundException
	 *             if the file was not found
	 * @throws BitstreamException
	 *             if a bitstream handling error occurred
	 */
	public FileBitstreamInput(final String filename, final int bufferLength)
			throws FileNotFoundException, BitstreamException {
		super(new BufferedInputStream(new FileInputStream(filename)), bufferLength);
	}
}
