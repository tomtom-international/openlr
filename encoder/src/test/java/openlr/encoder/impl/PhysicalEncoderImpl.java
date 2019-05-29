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
package openlr.encoder.impl;

import openlr.LocationReference;
import openlr.PhysicalEncoder;
import openlr.rawLocRef.RawLocationReference;

/**
 * A dummy physical encoder used for testing.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public class PhysicalEncoderImpl implements PhysicalEncoder {

	/**
	 * A reference to a mocked location reference that will be returned by this
	 * dummy physical encoder implementation.
	 */
	private static LocationReference locToReturn;

	/** The data identifier of this encoder implementation. */
	public static final String DATA_IDENTIFIER = "DummyEncoded";

	@Override
	public final LocationReference encodeData(
			final RawLocationReference rawLocRef) {
		return locToReturn;
	}

	@Override
	public final LocationReference encodeData(
			final RawLocationReference rawLocRef, final int version) {
		return locToReturn;
	}

	@Override
	public final Class<?> getDataClass() {
		return Object.class;
	}

	@Override
	public final String getDataFormatIdentifier() {
		return DATA_IDENTIFIER;
	}

	@Override
	public final int[] getSupportedVersions() {
		return new int[] {1};
	}

	/**
	 * Possibility to set the {@link LocationReference} that will be delivered
	 * by this dummy physical encoder implementation.
	 * 
	 * @param locRef
	 *            The location reference to return.
	 */
	public static void setLocToReturn(final LocationReference locRef) {
		locToReturn = locRef;
	}
}
