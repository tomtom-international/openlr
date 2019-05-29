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
package openlr.decoder.data;

import java.io.Serializable;
import java.util.Comparator;


/**
 * The Class CandidatePair represents a pair of candidates lines in combination with a score
 * value indicating the relevance of this pair. Each LRP might have resolved several candidate
 * lines and for each consecutive LRP pair combinations of candidate lines might be ordered in
 * order to determine the most promising pair to start the route calculation in between.
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public class CandidateLinePair {

	/** The start index. */
	private int startIndex;

	/** The destination index. */
	private int destIndex;

	/** The score. */
	private int score;

	/**
	 * Instantiates a new candidate pair. The candidate lines are referenced by their index in the
	 * candidate lines ranking.
	 * 
	 * @param i1 the index of the start line
	 *            
	 * @param i2 the index of the destination line
	 * @param s the score for this pair of candidate lines
	 */
	public CandidateLinePair(final int i1, final int i2, final int s) {
		startIndex = i1;
		destIndex = i2;
		score = s;
	}
	
	/**
	 * Gets the start index.
	 * 
	 * @return the start index
	 */
	public final int getStartIndex() {
		return startIndex;
	}
	
	/**
	 * Gets the destination index.
	 * 
	 * @return the destination index
	 */
	public final int getDestIndex() {
		return destIndex;
	}
	
	/**
	 * Gets the score.
	 * 
	 * @return the score
	 */
	public final int getScore() {
		return score;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("startIdx: ").append(startIndex);
		sb.append(" destIdx: ").append(destIndex);
		sb.append(" score: ").append(score);
		return sb.toString();
	}
	
	/**
	 * The Class CandidatePairComparator provides a comparator for candidate pairs. The comparator
	 * sorts according to better (greater) score values.
	 */
	public static class CandidateLinePairComparator implements Comparator<CandidateLinePair>, Serializable {

		/**
		 * Serialization ID
		 */
		private static final long serialVersionUID = -5038985615293780820L;

		/** {@inheritDoc} */
		@Override
		public final int compare(final CandidateLinePair o1, final CandidateLinePair o2) {
			if (o1.score < o2.score) {
				return 1;
			} else if (o1.score > o2.score) {
				return -1;
			}
			return 0;
		}		
	}
}
