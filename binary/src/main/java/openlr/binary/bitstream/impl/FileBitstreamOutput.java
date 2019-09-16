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
package openlr.binary.bitstream.impl;

import openlr.binary.bitstream.BitstreamException;

import java.io.*;

/**
 * A FileBitstreamOutput provides the ability to store bitstream content in a
 * file. Binary data can be put into a bitstream using the
 * {@link openlr.binary.bitstream.BitstreamOutput} interface and the data put
 * into the stream can be stored permanently in a file.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class FileBitstreamOutput extends BitstreamOutputImpl {

    /** The file output stream where all the data goes to. */
    private final OutputStream fos;

    /**
     * This method constructs a new FileBitstreamOutput in order to store binary
     * data being put into a stream permanently stored in a file.
     *
     * @param filename
     *            File name to store the binary data in the stream
     *
     * @throws FileNotFoundException
     *             if the file is not found or created
     * @throws BitstreamException
     *             if a bitstream handling error occurred
     */
    public FileBitstreamOutput(final String filename)
            throws BitstreamException, FileNotFoundException {
        this(filename, DEFAULT_BUFFER_LENGTH);
    }

    /**
     * This method constructs a new FileBitstreamOutput in order to store binary
     * data being put into a stream permanently stored in a file. The initial
     * size of the stream buffer is set to buffer_length
     *
     * @param filename
     *            File name to store the binary data in the stream
     * @param bufferLength
     *            initial size of the buffer
     *
     * @throws FileNotFoundException
     *             if the file is not found or created
     * @throws BitstreamException
     *             if a bitstream handling error occurred
     */
    public FileBitstreamOutput(final String filename, final int bufferLength)
            throws BitstreamException, FileNotFoundException {
        super(bufferLength);
        fos = new BufferedOutputStream(new FileOutputStream(filename));
    }

    /**
     * Flush all bits in the stream and close the stream. This last action
     * flushes all bits not being stored in the file yet and closes the stream.
     * No further action is allowed.
     *
     * @throws BitstreamException
     *             if a bitstream handling error occurred
     */
    public final void flushAndClose() throws BitstreamException {
        try {
            flushbits(fos);
            fos.close();
        } catch (IOException e) {
            throw new BitstreamException(
                    BitstreamException.BitstreamErrorType.SYSTEMIOFAILED, e);
        }
    }
}
