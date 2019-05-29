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
package openlr.location.data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The enumeration SideOfRoad defines on which side of the road a point location
 * can be. The direction of the road is identified by the order of the LRPs and
 * the direction goes from first LRP to second LRP. 
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public enum SideOfRoad {
	
	/** directly on the road or unknown */
	ON_ROAD_OR_UNKNOWN,
	
	/** on the right side */
	RIGHT,
	
	/** on the left side */
	LEFT,
	
	/** on both sides */
	BOTH;
	
	/** The Constant VALUES. */
	private static final List<SideOfRoad> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	
	/**
	 * Gets the side of road values.
	 * 
	 * @return the side of road values
	 */
	public static List<SideOfRoad> getSideOfRoadValues() {
		return VALUES;
	}
	
	/**
	 * Gets the default.
	 *
	 * @return the default
	 */
	public static SideOfRoad getDefault() {
		return ON_ROAD_OR_UNKNOWN;
	}
}
