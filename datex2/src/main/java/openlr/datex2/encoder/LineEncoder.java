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
package openlr.datex2.encoder;

import java.util.List;

import openlr.LocationReference;
import openlr.LocationReferencePoint;
import openlr.LocationType;
import openlr.Offsets;
import openlr.datex2.Datex2ReturnCode;
import openlr.datex2.impl.LocationReferenceImpl;
import openlr.rawLocRef.RawLocationReference;
import eu.datex2.schema._2_0rc2._2_0.OpenlrLastLocationReferencePoint;
import eu.datex2.schema._2_0rc2._2_0.OpenlrLineLocationReference;
import eu.datex2.schema._2_0rc2._2_0.OpenlrLocationReferencePoint;
import eu.datex2.schema._2_0rc2._2_0.OpenlrOffsets;

/**
 * The encoder for the line location type.
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public class LineEncoder extends AbstractEncoder {
	
	/**
	 * {@inheritDoc}
	 */
	public final LocationReference encodeData(final RawLocationReference rawLocRef, final int version) {
		List<? extends LocationReferencePoint> locRef = rawLocRef.getLocationReferencePoints();
		Offsets od = rawLocRef.getOffsets();
		if (locRef == null || od == null) {
			return new LocationReferenceImpl(rawLocRef.getID(), Datex2ReturnCode.DATA_ERROR, LocationType.LINE_LOCATION, version);
		}
		OpenlrLineLocationReference xmlLoc = OBJECT_FACTORY.createOpenlrLineLocationReference();
		OpenlrOffsets o = createOffsets(od, true);
		xmlLoc.setOpenlrOffsets(o);
		List<OpenlrLocationReferencePoint> lrps = createLRPs(locRef);
		xmlLoc.getOpenlrLocationReferencePoint().addAll(lrps);
		OpenlrLastLocationReferencePoint llrp = createLastLRP(locRef);
		xmlLoc.setOpenlrLastLocationReferencePoint(llrp);
		return new LocationReferenceImpl(rawLocRef.getID(), xmlLoc, version);
	}

}
