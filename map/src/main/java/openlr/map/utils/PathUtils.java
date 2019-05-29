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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

import openlr.map.Line;

/**
 * The Class RouteUtils.
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public final class PathUtils {

	/**
	 * Utility class shall not be instantiated.
	 */
	private PathUtils() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Gets the length of the subpath of path between start and end. The start
	 * and end line needs to be part of the route and the start line needs to be
	 * in the list prior to the end line. The returned length value includes the
	 * lengths of the start and end lines.
	 * 
	 * @param path
	 *            the complete path
	 * @param start
	 *            start of the subpath
	 * @param end
	 *            end of the subpath
	 * 
	 * @return the length of the subpath including start and end line, if no
	 *         subpath exists the method returns -1, if the path is empty the method returns 0
	 */
	public static int getLength(final List<Line> path, final Line start,
			final Line end) {
		if (path == null || path.isEmpty()) {
			return 0;
		}
		int posStart = path.indexOf(start);
		int posEnd = path.indexOf(end);
		if (posStart < 0 || posEnd < 0 || posEnd < posStart) {
			return -1;
		}
		int length = 0;
		int i = posStart;
		while (i <= posEnd) {
			length += path.get(i).getLineLength();
			i++;
		}
		return length;
	}

	/**
	 * Gets the total length of the path [in meter, rounded].
	 * 
	 * @param path
	 *            the path
	 * 
	 * @return the total length or 0 if route is empty or null.
	 * 
	 */
	public static int getLength(final List<Line> path) {
		if (path == null || path.isEmpty()) {
			return 0;
		}
		return getLength(path, path.get(0), path.get(path.size() - 1));
	}

	/**
	 * Checks if the path is connected properly. For each line the subsequent
	 * line in the list also needs to be a direct successor of the line in the
	 * network.
	 * 
	 * @param path
	 *            the path to be checked
	 * 
	 * @return true, if the path is connected, otherwise false
	 */
	public static boolean checkPathConnection(final List<Line> path) {
		if (path == null) {
			return true;
		}
		// / check whether the subsequent line is in the list of successors
		int size = path.size();
		for (int i = 0; i < size - 1; ++i) {
			Line currentline = path.get(i);
			Line successorline = path.get(i + 1);
			Iterator<? extends Line> iter = currentline.getNextLines();
			boolean found = false;
			while (iter.hasNext()) { // iterate over all successor
				// test if it is the right one
				if (successorline.getID() == iter.next().getID()) {
					found = true;
					break;
				}
			}
			if (!found) {
				return false; // no valid successor found!
			}
		}
		return true;
	}

	/**
	 * Constructs a path from information in the LineLinkedListElement. These
	 * elements store a pointer to its predecessor in a path and starting from
	 * the end of that path the method follows these pointers and stores the
	 * lines of the path until the path ends (indicated by an empty predecessor
	 * pointer). The returned path is directed from start to end. The path to be
	 * constructed shall not have any loops and if a loop is detected the method
	 * will return null.
	 * 
	 * @param dest
	 *            the end of the path
	 * 
	 * @return the constructed path from start to dest or null if a loop is
	 *         detected
	 */
	public static List<Line> constructPath(final LineLinkedListElement dest) {
		ArrayList<Line> path = new ArrayList<Line>();
		if (dest != null) {
			path.add(dest.getLine());
			LineLinkedListElement akt = dest;
			while (akt.getPrevious() != null) {
				akt = akt.getPrevious();
				if (path.contains(akt.getLine())) {
					return null;
				}
				path.add(0, akt.getLine());
			}
		}
		return path;
	}

	/**
	 * Returns the first {@link LineLinkedListElement} from the queue pq which
	 * contains the line l.
	 * 
	 * @param pq
	 *            a queue containing LineLinkedListElements
	 * @param line
	 *            the line
	 * 
	 * @return the LineLinkedListElement from pq containing l or null if it
	 *         could not be found
	 */
	public static LineLinkedListElement findElementInQueue(
			final Queue<? extends LineLinkedListElement> pq, final Line line) {
		if (pq == null || line == null) {
			throw new IllegalArgumentException();
		}
		Iterator<? extends LineLinkedListElement> iter = pq.iterator();
		long id = line.getID();
		while (iter.hasNext()) {
			LineLinkedListElement next = iter.next();
			if (next.getLine().getID() == id) {
				return next;
			}
		}
		return null;
	}

	/**
	 * Compares the path of the location and the path indicated by the
	 * {@link LineLinkedListElement} el and returns the first line element which
	 * is part of both paths, starting from the end of the linked list path. If
	 * no such line exists the method returns null.
	 * 
	 * @param location
	 *            the location path
	 * @param el
	 *            LineLinkedListElement defining a second path
	 * 
	 * @return the first line which is part of both paths, starting from the end
	 *         of the paths
	 */
	public static Line findCommonLineInPaths(final List<? extends Line> location,
			final LineLinkedListElement el) {
		Line line = null;
		LineLinkedListElement current = el;
		if (location != null && current != null && current.getLine() != null) {
			if (location.contains(current.getLine())) {
				line = current.getLine();
			} else {
				while (current.getPrevious() != null) {
					current = current.getPrevious();
					if (location.contains(current.getLine())) {
						line = current.getLine();
						break;
					}
				}
			}
		}
		return line;
	}

}
