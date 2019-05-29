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
package openlr.map.utils;

import java.io.Serializable;
import java.util.Comparator;

import openlr.map.Line;

/**
 * The Class PQElem represents an entry of a priority queue. It implements the
 * {@link LineLinkedListElement} interface and holds additionally to the line
 * also data for ordering the elements in the priority queue. Each element has
 * got two numerical values for such ordering and the compare method orders the
 * elements lexicographically.
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public class PQElem implements LineLinkedListElement {
	
	/** used for hash code */
	private static final int PRIME_NUMBER = 31;


	/** {@inheritDoc} */
	@Override
	public final boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof PQElem)) {
			return false;
		}
		PQElem other = (PQElem) o;
		return this.line.getID() == other.line.getID();
	}

	/** {@inheritDoc} */
	@Override
	public final int hashCode() {
		int hash = line.hashCode();
		hash += PRIME_NUMBER * (firstVal + secondVal);
		if (parent != null) {
			hash += parent.getLine().hashCode();
		}
		return hash;
	}

	/** The line - the main element. */
	private final Line line;

	/** The parent line in path. */
	private final PQElem parent;

	/**
	 * The first numerical value - route search: route length including a
	 * heuristic value.
	 */
	private final int firstVal;

	/** The second numerical value - route search: route length. */
	private final int secondVal;

	/**
	 * Instantiates a new Priority Queue element.
	 * 
	 * @param l
	 *            the line
	 * @param f
	 *            the first numerical value
	 * @param s
	 *            the second numerical value
	 * @param p
	 *            the parent
	 */
	public PQElem(final Line l, final int f, final int s, final PQElem p) {
		if (l == null) {
			throw new NullPointerException();
		}
		line = l;
		secondVal = s;
		firstVal = f;
		parent = p;
	}

	/** {@inheritDoc} */
	public final Line getLine() {
		return line;
	}

	/** {@inheritDoc} */
	public final PQElem getPrevious() {
		return parent;
	}

	/**
	 * Gets the first value.
	 * 
	 * @return the first value
	 */
	public final int getFirstVal() {
		return firstVal;
	}

	/**
	 * Gets the second value.
	 * 
	 * @return the second value
	 */
	public final int getSecondVal() {
		return secondVal;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("line: ").append(line.getID());
		sb.append(" first: ").append(firstVal);
		sb.append(" second: ").append(secondVal);
		sb.append(" parent: ").append(parent);
		return sb.toString();
	}
	
	/**
	 * The Class PQElemComparator.
	 */
	public static class PQElemComparator implements Comparator<PQElem>, Serializable {

		/**
		 * Serial ID.
		 */
		private static final long serialVersionUID = 8164069785443434930L;

		/**
		 * {@inheritDoc}
		 */
		@Override
		public final int compare(final PQElem o1, final PQElem o2) {
			int retval = 0;
			// lexicographical order
			if (o1.firstVal < o2.firstVal) {
				retval = -1;
			} else if (o1.firstVal == o2.firstVal && o1.secondVal < o2.secondVal) {
				retval = -1;
			} else if (o1.firstVal > o2.firstVal) {
				retval = 1;
			} else if (o1.firstVal == o2.firstVal && o1.secondVal > o2.secondVal) {
				retval = 1;
			}
			return retval;
		}
		
	}
}
