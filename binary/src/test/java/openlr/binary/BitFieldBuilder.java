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
package openlr.binary;

import java.util.ArrayList;
import java.util.List;

/**
 * This class enables to create by byte array from binary strings in a readable
 * way. The byte string is interpreted as eight pure unsigned bits.
 * <p>
 * The usage is the following:
 * <p>
 *
 * <pre>
 * byte[] bytes = new BitFieldBuilder().addByte(&quot;01011011&quot;).addByte(&quot;00000100&quot;)
 *         .addByte(&quot;01011011&quot;).addByte(&quot;11110100&quot;).addByte(&quot;00001000&quot;)
 *         .addByte(&quot;00010011&quot;).addByte(&quot;00010101&quot;).toByteArray();
 * </pre>
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
final class BitFieldBuilder {

    /**
     * The number of required bits for a valid input string
     */
    private static final int REQUIRED_BIT_COUNT = 8;
    /**
     * The underlying byte array
     */
    private final List<Byte> bytes = new ArrayList<Byte>();

    /**
     * Adds eight bits to the bit field
     *
     * @param eightBitsString
     *            A binary string that defines eight unsigned bits.
     * @return This {@link BitFieldBuilder} instance to enable to call further
     *         methods on it.
     */
    public BitFieldBuilder addByte(final String eightBitsString) {
        if (eightBitsString.length() != REQUIRED_BIT_COUNT) {
            throw new IllegalArgumentException("Byte string is not of length "
                    + REQUIRED_BIT_COUNT + ": " + eightBitsString);
        }

        bytes.add(Byte.valueOf((byte) Integer.parseInt(eightBitsString, 2)));
        return this;
    }

    /**
     * Delivers the byte array that represents the binary data added up to this
     * point.
     *
     * @return The byte array that represents the current data.
     */
    public byte[] toByteArray() {

        byte[] result = new byte[bytes.size()];
        int i = 0;
        for (Byte b : bytes) {
            result[i++] = b.byteValue();
        }

        return result;
    }
}