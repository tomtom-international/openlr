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

import openlr.binary.bitstream.BitstreamException;
import openlr.binary.bitstream.BitstreamInput;
import openlr.binary.bitstream.BitstreamOutput;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.log4j.Logger;

/**
 * The Class Attr3 holds the data for the third attribute defined in the physical data format of OpenLR.
 * <p>
 * This attribute is used for the DNP (distance to next point) information. See OpenLR white paper for
 * additional information.
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public class Attr3 extends OpenLRBinaryInformation {
    
    /** logger */
    private static final Logger LOG = Logger.getLogger(Attr3.class);
    
    /** number of bits used for dnp */
    private static final int DNP_BITS = 8;
	
    /** The distance to next point information. */
    private final int dnp;


    /**
     * Instantiates a new attr3.
     *
     * @param dnpValue the dnp value
     */
    public Attr3(final int dnpValue) {
    	dnp = dnpValue;
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
    public Attr3(final BitstreamInput ibs) throws BitstreamException {
        dnp = ibs.getBits(DNP_BITS);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Attribute 3 filled with data: dnp - " + dnp);
        }
    }


    /** {@inheritDoc} */
    public final void put(final BitstreamOutput obs) throws BitstreamException {
        obs.putBits(dnp, DNP_BITS);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Attribte 3 data written: dnp - " + dnp);
        }
    }

	/**
	 * Gets the distance to next point information.
	 * 
	 * @return the dnp
	 */
	public final int getDnp() {
		return dnp;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("dnp: ").append(dnp);
		return sb.toString();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		builder.append(dnp);
		return builder.toHashCode();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean equals(final Object obj) {
		if (!(obj instanceof Attr3)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Attr3 other = (Attr3) obj;
		EqualsBuilder builder = new EqualsBuilder();
		builder.append(dnp, other.dnp);
		return builder.isEquals();
	}

}
