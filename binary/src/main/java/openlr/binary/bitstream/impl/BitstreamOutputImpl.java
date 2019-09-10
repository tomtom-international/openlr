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
import openlr.binary.bitstream.BitstreamOutput;

import java.io.IOException;
import java.io.OutputStream;

/**
 * The Class BitstreamOutputImpl implements the interface
 * {@link BitstreamOutput}. This class allows to put binary data (bits) into an
 * internal data buffer and also to stream the data into an output stream. The
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
public class BitstreamOutputImpl extends BitstreamAbstract implements
        BitstreamOutput {

    /**
     * Instantiates a new bitstream output with an initial buffer length of
     * buffer_length.
     *
     * @param bufferLength
     *            the initial length of the internal buffer
     */
    BitstreamOutputImpl(final int bufferLength) {
        createBuffer(bufferLength);
        currentBit = 0;
    }

    /** {@inheritDoc} */
    public final int putBits(final int value, final int nrBitsToPut)
            throws BitstreamException {
        // sanity check
        if (nrBitsToPut == 0) {
            return value;
        }
        if (nrBitsToPut > MAX_BIT_SIZE || nrBitsToPut < 1) {
            throw new BitstreamException(
                    BitstreamException.BitstreamErrorType.INVALIDBITSIZE);
        }

        // make sure we have enough room
        if ((currentBit + nrBitsToPut) > (totalBufferLengthBytes << BIT_BYTE_SHIFT)) {
            expandBuffer();
        }

        int endByteIndex = (currentBit + nrBitsToPut - 1) >>> BIT_BYTE_SHIFT; // End
        // byte
        // position
        int beginByteIndex = currentBit >>> BIT_BYTE_SHIFT; // Current byte
        // position
        int freeBitsFirstByte = BYTE_SIZE - (currentBit % BYTE_SIZE); // Room in
        // the
        // first
        // byte
        // of
        // the
        // buffer
        int remainingValue = value;

        if (freeBitsFirstByte >= nrBitsToPut) { // value fits into the first
            // byte
            // reset free bits (remove old data)
            buffer[beginByteIndex] &= COMPLEMENT_MASK[freeBitsFirstByte];
            // insert the value into the free bits
            buffer[beginByteIndex] |= BITMASK[freeBitsFirstByte]
                    & (remainingValue << freeBitsFirstByte - nrBitsToPut);
        } else {
            // value does not fit into the first byte !!
            int nrBitsToPutLastByte = (nrBitsToPut - freeBitsFirstByte)
                    % BYTE_SIZE; // Number of bits to put in
            // the last byte
            if (nrBitsToPutLastByte > 0) {
                // there will be a rest to put into an additional byte (complete
                // buffer will not be aligned)
                buffer[endByteIndex] = 0; // clear byte
                buffer[endByteIndex] |= (remainingValue << BYTE_SIZE
                        - nrBitsToPutLastByte)
                        & BITMASK[BYTE_SIZE]; // Put the bits in the head of
                // byte
                remainingValue >>= nrBitsToPutLastByte; // prune remaining data
                endByteIndex--;
            }
            for (; endByteIndex > beginByteIndex; endByteIndex--) {
                // now put the full bytes into the buffer
                buffer[endByteIndex] = 0; // clear byte
                buffer[endByteIndex] |= remainingValue & BITMASK[BYTE_SIZE]; // Put
                // next
                // byte
                remainingValue >>>= BYTE_SIZE; // Shift data
            }
            // endByteIndex is now equal to beginByteIndex
            // fill up the first byte with the remaining data
            buffer[endByteIndex] &= COMPLEMENT_MASK[freeBitsFirstByte];
            buffer[endByteIndex] |= remainingValue & BITMASK[freeBitsFirstByte];
        }
        // adjust internal pointer
        currentBit += nrBitsToPut;
        return value;
    }

    /**
     * Flushes the internal buffer and puts the data into the output stream. The
     * internal data buffer is reset.
     *
     * @param os
     *            the output stream where all the data goes to
     *
     * @throws BitstreamException
     *             if an bitstream handling error occurred
     */
    final void flushbits(final OutputStream os) throws BitstreamException {
        if (os != null) {
            // if no data to flush then return
            if (currentBit == 0) {
                return;
            }
            try {
                // calculate number of bytes
                int numberOfBytes = currentBit >>> BIT_BYTE_SHIFT; // Size of
                // data in
                // the
                // buffer
                // (in
                // bytes)
                if ((currentBit & HIGHEST_BIT) != 0) { // check if there are
                    // remaining bits in an
                    // additional byte
                    numberOfBytes++; // if yes, also flush this byte
                }
                os.write(buffer, 0, numberOfBytes); // flush now
            } catch (IOException e) {
                throw new BitstreamException(
                        BitstreamException.BitstreamErrorType.SYSTEMIOFAILED, e);
            }
            // reset internal buffer
            buffer[0] = 0;
            currentBit = 0;
        }
    }
}
