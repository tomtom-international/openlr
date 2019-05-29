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
package openlr.map;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The Enum FormOfWay (FOW) describes certain aspects of the physical form that
 * a line in a road network takes. It is based on a number of certain physical
 * and traffic properties. Each line within a road network should have a FOW
 * value attached.
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public enum FormOfWay {

	/** The physical road type is unknown. */
	UNDEFINED(0),

	/**
	 * A Motorway is defined as a road permitted for motorized vehicles only in
	 * combination with a prescribed minimum speed. It has two or more
	 * physically separated carriageways and no single level-crossings.
	 */
	MOTORWAY(1),

	/**
	 * A multiple carriageway is defined as a road with physically separated
	 * carriageways regardless of the number of lanes. If a road is also a
	 * motorway, it should be coded as such and not as a multiple carriageway.
	 */
	MULTIPLE_CARRIAGEWAY(2),

	/**
	 * All roads without separate carriageways are considered as roads with a
	 * single carriageway.
	 */
	SINGLE_CARRIAGEWAY(3),

	/**
	 * A Roundabout is a road which forms a ring on which traffic travelling in
	 * only one direction is allowed.
	 */
	ROUNDABOUT(4),

	/**
	 * A Traffic Square is an open area (partly) enclosed by roads which is used
	 * for non-traffic purposes and which is not a Roundabout.
	 */
	TRAFFIC_SQUARE(5),

	/** A Slip Road is a road especially designed to enter or leave a line. */
	SLIPROAD(6),

	/**
	 * The physical road type is known but does not fit into one of the other
	 * categories.
	 */
	OTHER(7);

	/**
	 * The unique ID. This id corresponds to the value in the physical data
	 * format.
	 */
	private final int uid;

	/** The Constant VALUES. */
	private static final List<FormOfWay> VALUES = Collections.unmodifiableList(Arrays.asList(values()));

	/**
	 * Instantiates a new form of way.
	 * 
	 * @param id
	 *            the unique id
	 */
	FormOfWay(final int id) {
		uid = id;
	}

	/**
	 * Gets an unique ID identifying that specific FormOfWay.
	 * 
	 * @return the unique ID
	 */
	public int getID() {
		return uid;
	}
	
	/**
	 * Gets the FOW values.
	 * 
	 * @return the fow values
	 */
	public static List<FormOfWay> getFOWs() {
		return VALUES;
	}

}
