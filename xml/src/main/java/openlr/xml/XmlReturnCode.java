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
package openlr.xml;

import openlr.StatusCode;

/**
 * The return codes.
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public enum XmlReturnCode implements StatusCode {

		/** The number of LRP is invalid. */
		INVALID_NUMBER_OF_LRP,
 
		
		/** The last LRP is missing. */
		NO_LAST_LRP_FOUND,
		
		
		/** The INVALI d_ version. */
		INVALID_VERSION, 
		
		/** The UNKNOW n_ locatio n_ type. */
		UNKNOWN_LOCATION_TYPE,
		
		/** The circle data is missing or invalid. */
		MISSING_CIRCLE_DATA,
		
		/** The INVALI d_ data. */
		INVALID_DATA, 
		
		/** The MISSIN g_ gri d_ data. */
		MISSING_GRID_DATA, 
		
		/** The MISSIN g_ polygo n_ data. */
		MISSING_POLYGON_DATA , 
		
		/** The MISSIN g_ rectangl e_ data. */
		MISSING_RECTANGLE_DATA;
		

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int getID() {
			return ordinal();
		}

}
