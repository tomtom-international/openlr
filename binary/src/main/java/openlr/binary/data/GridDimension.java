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
 *  Copyright (C) 2009-2012 TomTom International B.V.
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
package openlr.binary.data;

import openlr.binary.OpenLRBinaryConstants;
import openlr.binary.OpenLRBinaryException;
import openlr.binary.OpenLRBinaryException.PhysicalFormatError;
import openlr.binary.bitstream.BitstreamException;
import openlr.binary.bitstream.BitstreamInput;
import openlr.binary.bitstream.BitstreamOutput;

import org.apache.log4j.Logger;

/**
 * See OpenLR white paper for additional information.
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author DLR e.V. (RE)
 */
public class GridDimension extends OpenLRBinaryInformation {

	/** logger */
	private static final Logger LOG = Logger.getLogger(GridDimension.class);

	/** number of bits used for grid dimension */
	private static final int DIMENSION_BITS = OpenLRBinaryConstants.DIMENSION_SIZE * 8;

	/** The grid dimension according to OpenLR white paper */
	private short dimension;

	/**
	 * Fills the internal values with the values received from the input stream.
	 *
	 * @param d the d
	 * @throws OpenLRBinaryException the open lr binary exception
	 */
	public GridDimension(final int d) throws OpenLRBinaryException {
		if (d < 0 || d > Short.MAX_VALUE) {
			throw new OpenLRBinaryException(
					PhysicalFormatError.INVALID_BINARY_DATA);
		}
		// d represents dimension in meters
		dimension = (short) d;
		if (LOG.isDebugEnabled()) {
			LOG.debug("Dimension filled with data: dimension - " + dimension);
		}
	}

	/**
	 * Fills the internal values with the values received from the input stream.
	 * 
	 * @param ibs
	 *            the input stream to read from
	 * 
	 * @throws BitstreamException
	 *             if a bitstream handling error occurred
	 */
	public GridDimension(final BitstreamInput ibs) throws BitstreamException {
		dimension = (short) ibs.getSignedBits(DIMENSION_BITS);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void put(final BitstreamOutput rbs) throws BitstreamException {
		rbs.putBits(dimension, DIMENSION_BITS);
		if (LOG.isDebugEnabled()) {
			LOG.debug("Dimension data written: dimension - " + dimension);
		}
	}

	/**
	 * Gets the dimension information.
	 * 
	 * @return the dimension
	 */
	public final short getDimension() {
		return dimension;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("dimension: ").append(dimension);
		return sb.toString();
	}
}
