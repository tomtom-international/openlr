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
package openlr.decoder.impl;

import openlr.LocationReference;
import openlr.PhysicalDecoder;
import openlr.PhysicalFormatException;
import openlr.rawLocRef.RawLocationReference;

/**
 * A special {@link PhysicalDecoder} that delivers a mocked 
 * {@link RawLocationReference} preset via
 *  {@link #setRawLocToReturn(RawLocationReference)}. 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class DummyPhysicalDecoderImpl implements PhysicalDecoder {

    /** The data identifier of this decoder implementation. */
    public static final String DATA_IDENTIFIER = "DummyDecoded";
    /**
     * A reference to a mocked location reference that will be returned by
     * this dummy physical decoder implementation.
     */
    private static RawLocationReference rawLocToReturn;

    /**
     * Possibility to set the {@link RawLocationReference} that will be
     * delivered by the dummy physical decoder implementation
     * {@link openlr.decoder.impl.DummyPhysicalDecoderImpl}.
     *
     * @param rawLoc
     *            The location reference to return.
     */
    public static void setRawLocToReturn(final RawLocationReference rawLoc) {
        rawLocToReturn = rawLoc;
    }

    /**
     * @return class {@link Object}
     */
    @Override
    public final Class<?> getDataClass() {
        return Object.class;
    }

    /**
     * @return {@link #DATA_IDENTIFIER}
     */
    @Override
    public final String getDataFormatIdentifier() {
        return DATA_IDENTIFIER;
    }

    /**
     * Returns a fix preset mocked location reference set by.
     *
     * @param data is ignored!
     * @return The location reference set via {@link #setRawLocToReturn}
     * @throws PhysicalFormatException the physical format exception
     * {@link #setRawLocToReturn}.
     */
    @Override
    public final RawLocationReference decodeData(final LocationReference data)
            throws PhysicalFormatException {
        return rawLocToReturn;
    }
}
