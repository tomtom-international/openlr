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
 * Copyright (C) 2009,2010 TomTom International B.V.
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

import openlr.binary.bitstream.BitstreamException;
import openlr.binary.bitstream.BitstreamException.BitstreamErrorType;
import openlr.binary.bitstream.BitstreamInput;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * The Class BitstreamInputImpl implements the interface {@link BitstreamInput}.
 * This class allows to get binary data (bits) from an internal data buffer. The
 * data alignment used in this class is Big Endian so that the most significant
 * bit comes first in the data buffer.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class BitstreamInputImpl extends BitstreamAbstract implements
        BitstreamInput {

    /** The input stream to read data from. */
    private final InputStream in; // Input file handle

    /** The number of bytes being used in the buffer. */
    private int bufferFilledBytes;

    /**
     * Instantiates a new input bitstream which reads data from inputStream and
     * initializes the internal data buffer with a size of buffer_length.
     *
     * @param inputStream
     *            the input stream to read data from
     * @param bufferLength
     *            the initial size of the internal buffer
     *
     * @throws BitstreamException
     *             if a bitstream handling error occurred
     */
    BitstreamInputImpl(final InputStream inputStream, final int bufferLength)
            throws BitstreamException {
        createBuffer(bufferLength);
        in = new BufferedInputStream(inputStream);
        currentBit = 0;
        bufferFilledBytes = 0;
        fillBufferFromStream(); // read in data into internal buffer
    }

    /**
     * Returns the next n bits from the internal buffer as an unsigned value. If
     * the end of the internal data buffer is reached the method tries to read
     * more data from the input stream.
     *
     * @param n
     *            the number of bits to read and return
     * @return the integer value of the n bits
     * @exception BitstreamException
     *                if bitstream handling error occurred
     */
    private int getNextBits(final int n) throws BitstreamException {
        if (n == 0) {
            // nothing to do
            return 0;
        }
        if (n > MAX_BIT_SIZE || n < 1) {
            // can read only a maximum of MAX_SIZE_OF_BITS bits at a time
            throw new BitstreamException(
                    BitstreamException.BitstreamErrorType.INVALIDBITSIZE);
        }
        if (currentBit + n > bufferFilledBytes << BIT_BYTE_SHIFT) {
            // forward check if we reach the end of the buffer
            fillBufferFromStream();
        }
        if ((totalBufferLengthBytes << BIT_BYTE_SHIFT) - currentBit < n) {
            // check if there is enough data in the buffer (after reading from
            // stream)
            throw new BitstreamException(
                    BitstreamException.BitstreamErrorType.NOTENOUGHDATA);
        }

        int returnValue = 0; // Output value
        int currentByteIndex = currentBit >>> BIT_BYTE_SHIFT; // Current byte
        // position
        int endByteIndex = (currentBit + n - 1) >>> BIT_BYTE_SHIFT; // End byte
        // position
        int room = BYTE_SIZE - (currentBit % BYTE_SIZE); // unread bits in the
        // first byte

        if (room >= n) {
            // the requested value is completely in the first byte
            // so read the data
            returnValue = (buffer[currentByteIndex] >> room - n) & BITMASK[n];

        } else {
            // Leftover bits in the last byte
            int leftover = (currentBit + n) % BYTE_SIZE;
            // read the first bits
            returnValue |= buffer[currentByteIndex] & BITMASK[room]; // Fill out
            // first
            // byte

            // now iterate byte-wise
            // stop before last byte
            for (currentByteIndex++; currentByteIndex < endByteIndex; currentByteIndex++) {
                // shift return value
                returnValue <<= BYTE_SIZE;
                // and put the bits read instead (full byte)
                returnValue |= buffer[currentByteIndex] & BITMASK[BYTE_SIZE];
            }
            // now deal with the last part
            if (leftover > 0) {
                returnValue <<= leftover; // Make room for remaining bits
                // read the last bits
                returnValue |= (buffer[currentByteIndex] >> (BYTE_SIZE - leftover))
                        & BITMASK[leftover];
            } else {
                // last byte will be read completely
                returnValue <<= BYTE_SIZE; // Shift and put
                returnValue |= buffer[currentByteIndex] & BITMASK[BYTE_SIZE];
            }
        }
        return returnValue;
    }

    /**
     * Returns the next n bits from the internal buffer as a signed value. If
     * the end of the internal data buffer is reached the method tries to read
     * more data from the input stream. The maximum number of bits which can be
     * read at a time is 32.
     *
     * @param n
     *            the number of bits to read and return
     * @return the integer value of the n bits
     * @exception BitstreamException
     *                if bitstream handling error occurred
     */
    private int getNextSignedBits(final int n) throws BitstreamException {
        // get the (unsigned) bits
        int x = getNextBits(n);
        // check if the number read is negative?
        if (n > 1 && ((SIGNED_MASK[n] & x) != 0)) {
            // number is negative so transform into an integer including the
            // sign
            return x | COMPLEMENT_MASK[n];
        } else {
            // number is positive, no transformation needed
            return x;
        }
    }

    /** {@inheritDoc} */
    public final int getBits(final int n) throws BitstreamException {
        // get the bits
        int x = getNextBits(n);
        // adjust the bit pointers
        currentBit += n;
        return x;
    }

    /** {@inheritDoc} */
    public final int getSignedBits(final int n) throws BitstreamException {
        // get the (signed) bits
        int x = getNextSignedBits(n);
        // adjust the bit pointers
        currentBit += n;
        return x;
    }

    /**
     * Fills out the internal data buffer by reading more data from the input
     * stream.
     *
     * @throws BitstreamException
     *             if a bitstream handling error occurred
     */
    private void fillBufferFromStream() throws BitstreamException {
        int currentByteIndex = currentBit >>> BIT_BYTE_SHIFT; // Current byte
        // offset
        int remainingBytes = bufferFilledBytes - currentByteIndex; // Remaining
        // bytes

        // Copy remaining data (not read yet) into the head of the buffer
        // and overwrite already read data
        System.arraycopy(buffer, currentByteIndex, buffer, 0, remainingBytes);

        int maxSizeInBuffer = totalBufferLengthBytes - currentByteIndex;
        int bytesreadFromStream = 0;
        try {
            // Now we have a room for currentByteIndex bytes from offset
            // remainingBytes in the buffer
            // we shifted the not read data to the beginning and the already
            // data size is now free
            bytesreadFromStream = in.read(buffer, remainingBytes,
                    maxSizeInBuffer);
        } catch (IOException e) {
            throw new BitstreamException(
                    BitstreamException.BitstreamErrorType.SYSTEMIOFAILED, e);
        }
        if (bytesreadFromStream == -1) {
            // no more data available but application should read more, that is
            // an error
            throw new BitstreamException(BitstreamException.BitstreamErrorType.ENDOFDATA);
        }
        // adjust buffer size being used
        // the total buffer size might be larger but this should only happen
        // the last reading data from stream!
        bufferFilledBytes = remainingBytes + bytesreadFromStream;
        // set bit pointer according to the part already being processed
        currentBit &= HIGHEST_BIT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void close() throws BitstreamException {
        try {
            in.close();
        } catch (IOException e) {
            throw new BitstreamException(BitstreamErrorType.SYSTEMIOFAILED, e);
        }
    }
}
