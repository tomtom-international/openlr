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

import java.util.List;

/**
 * The Class RawBinaryLocationReference.
 */
public class RawBinaryData {

	/** The header. */
	private Header header;
	
	/** The first lrp. */
	private FirstLRP firstLRP;
	
	/** The pos offset. */
	private Offset posOffset;
	
	/** The neg offset. */
	private Offset negOffset;
	
	/** The last lrp. */
	private LastLRP lastLRP;
	
	/** The last intermediate lrp. */
	private LastClosedLineLRP lastClosedLineLRP;
	
	/** The intermediates. */
	private List<IntermediateLRP> intermediates;

	/** The abs coord. */
	private AbsoluteCoordinates absCoord;
	
	/** The rel coord. */
	private RelativeCoordinates relCoord;
	
	/** The abs coord. */
	private AbsoluteCoordinates absCenter;
	
	/** The abs coord. */
	private AbstractCoordinate absCoordUR;
	
	/** The abs coord. */
	private AbsoluteCoordinates absCoordLL;
	
	/**
	 * Sets the header.
	 *
	 * @param h the new header
	 */
	public final void setHeader(final Header h) {
		header = h;
	}
	
	/**
	 * Gets the binary header.
	 *
	 * @return the binary header
	 */
	public final Header getBinaryHeader() {
		return header;
	}
	
	/**
	 * Sets the binary first lrp.
	 *
	 * @param f the new binary first lrp
	 */
	public final void setBinaryFirstLRP(final FirstLRP f) {
		firstLRP = f;
	}
	
	/**
	 * Gets the binary first lrp.
	 *
	 * @return the binary first lrp
	 */
	public final FirstLRP getBinaryFirstLRP() {
		return firstLRP;
	}


	/**
	 * Gets the binary pos offset.
	 *
	 * @return the binary pos offset
	 */
	public final Offset getBinaryPosOffset() {
		return posOffset;
	}


	/**
	 * Sets the binary pos offset.
	 *
	 * @param pOffset the new binary pos offset
	 */
	public final void setBinaryPosOffset(final Offset pOffset) {
		posOffset = pOffset;
	}


	/**
	 * Gets the binary neg offset.
	 *
	 * @return the binary neg offset
	 */
	public final Offset getBinaryNegOffset() {
		return negOffset;
	}


	/**
	 * Sets the binary neg offset.
	 *
	 * @param nOffset the new binary neg offset
	 */
	public final void setBinaryNegOffset(final Offset nOffset) {
		negOffset = nOffset;
	}


	/**
	 * Gets the binary last lrp.
	 *
	 * @return the binary last lrp
	 */
	public final LastLRP getBinaryLastLRP() {
		return lastLRP;
	}


	/**
	 * Sets the binary last lrp.
	 *
	 * @param lLRP the new binary last lrp
	 */
	public final void setBinaryLastLRP(final LastLRP lLRP) {
		lastLRP = lLRP;
	}


	/**
	 * Gets the binary last intermediate lrp.
	 *
	 * @return the binary last intermediate lrp
	 */
	public final LastClosedLineLRP getBinaryLastClosedLineLRP() {
		return lastClosedLineLRP;
	}


	/**
	 * Sets the binary last closed line lrp.
	 *
	 * @param lClosedLineLRP the new binary last intermediate lrp
	 */
	public final void setBinaryLastClosedLineLRP(final LastClosedLineLRP lClosedLineLRP) {
		lastClosedLineLRP = lClosedLineLRP;
	}


	/**
	 * Gets the binary absolute coordinates.
	 *
	 * @return the binary absolute coordinates
	 */
	public final AbsoluteCoordinates getBinaryAbsoluteCoordinates() {
		return absCoord;
	}
	
	/**
	 * Sets the binary absolute coordinates.
	 *
	 * @param acoord the new binary absolute coordinates
	 */
	public final void setBinaryAbsoluteCoordinates(final AbsoluteCoordinates acoord) {
		absCoord = acoord;
	}
	
	/**
	 * Gets the binary relative coordinates.
	 *
	 * @return the binary relative coordinates
	 */
	public final RelativeCoordinates getBinaryRelativeCoordinates() {
		return relCoord;
	}
	
	/**
	 * Sets the binary relative coordinates.
	 *
	 * @param rcoord the new binary relative coordinates
	 */
	public final void setBinaryRelativeCoordinates(final RelativeCoordinates rcoord) {
		relCoord = rcoord;
	}
	
	/**
	 * Gets the binary absolute coordinates center.
	 *
	 * @return the binary absolute coordinates center
	 */
	public final  AbsoluteCoordinates getBinaryAbsoluteCoordinatesCenter() {
		return absCenter;
	}
	
	/**
	 * Sets the binary absolute coordinates center.
	 *
	 * @param acenter the new binary absolute coordinates center
	 */
	public final  void setBinaryAbsoluteCoordinatesCenter(final AbsoluteCoordinates acenter) {
		absCenter = acenter;
	}
	
	/**
	 * Gets the binary absolute coordinates.
	 *
	 * @return the binary absolute coordinates
	 */
	public final AbsoluteCoordinates getBinaryAbsoluteCoordinatesLowerLeft() {
		return absCoordLL;
	}
	
	/**
	 * Sets the binary absolute coordinates.
	 *
	 * @param acoord the new binary absolute coordinates
	 */
	public final void setBinaryAbsoluteCoordinatesLL(final AbsoluteCoordinates acoord) {
		absCoordLL = acoord;
	}
	
	/**
	 * Gets the binary absolute coordinates.
	 *
	 * @return the binary absolute coordinates
	 */
	public final AbstractCoordinate getBinaryAbsoluteCoordinatesUpperRight() {
		return absCoordUR;
	}
	
	/**
	 * Sets the binary absolute coordinates.
	 *
	 * @param acoord the new binary absolute coordinates
	 */
	public final void setBinaryAbsoluteCoordinatesUR(final AbstractCoordinate acoord) {
		absCoordUR = acoord;
	}
	
	/**
	 * Gets the binary intermediates.
	 *
	 * @return the binary intermediates
	 */
	public final List<IntermediateLRP> getBinaryIntermediates() {
		return intermediates;
	}

	/**
	 * Sets the binary intermediates.
	 *
	 * @param interm the new binary intermediates
	 */
	public final void setBinaryIntermediates(final List<IntermediateLRP> interm) {
		this.intermediates = interm;
	}
	
	

}
