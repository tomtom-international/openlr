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
package openlr.decoder.worker;

import openlr.Offsets;

/**
 * This is an {@link Offsets} implementation that delivers calculates offset
 * values depending on the given line length (similar to the binary offset
 * implementation)
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public class RelativeOffsetsImpl implements Offsets {

    /**
     * The percentage values to calculate offsets
     */
    private final float posOff, negOff;

    /** The Constant PERCENTAGE. */
    private static final int PERCENTAGE = 100;

    /**
     * 
     * @param relativePositiveOffset
     *            The percentage of the positive offset
     * @param relativeNegativeOffset
     *            The percentage of the negatve offset
     */
    public RelativeOffsetsImpl(final int relativePositiveOffset,
            final int relativeNegativeOffset) {
        posOff = relativePositiveOffset;
        negOff = relativeNegativeOffset;
    }

    @Override
    public final int getPositiveOffset(final int length) {
        if (hasPositiveOffset()) {

            return Math.round(posOff * length / PERCENTAGE);
        }
        return 0;
    }

    @Override
    public final int getNegativeOffset(final int length) {
        if (hasNegativeOffset()) {

            return Math.round(negOff * length / PERCENTAGE);
        }
        return 0;
    }

    @Override
    public final boolean hasPositiveOffset() {
        return posOff > 0;
    }

    @Override
    public final boolean hasNegativeOffset() {
        return negOff > 0;
    }

}
