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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import openlr.LocationReference;
import openlr.LocationReferencePoint;
import openlr.Offsets;
import openlr.datex2.OpenLRDatex2Constants;
import openlr.datex2.impl.LocationReferenceImpl;
import openlr.location.data.Orientation;
import openlr.location.data.SideOfRoad;
import openlr.map.GeoCoordinates;
import openlr.rawLocRef.RawLocationReference;
import eu.datex2.schema._2_0rc2._2_0.OpenlrLastLocationReferencePoint;
import eu.datex2.schema._2_0rc2._2_0.OpenlrLocationReferencePoint;
import eu.datex2.schema._2_0rc2._2_0.OpenlrPoiWithAccessPoint;
import eu.datex2.schema._2_0rc2._2_0.OpenlrPointLocationReference;
import eu.datex2.schema._2_0rc2._2_0.PointCoordinates;

/**
 * The encoder for the poi with access point location type.
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public class PoiAccessEncoder extends AbstractEncoder {	

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public final LocationReference encodeData(final RawLocationReference rawLocRef, final int version) {
		LocationReferencePoint startLRP = rawLocRef.getLocationReferencePoints().get(0);
		LocationReferencePoint endLRP = rawLocRef.getLocationReferencePoints().get(1);
		Offsets od = rawLocRef.getOffsets();
		GeoCoordinates gCoord = rawLocRef.getGeoCoordinates();
		SideOfRoad s = rawLocRef.getSideOfRoad();
		Orientation o = rawLocRef.getOrientation();
		
		OpenlrPointLocationReference xmlData = OBJECT_FACTORY.createOpenlrPointLocationReference();
		OpenlrPoiWithAccessPoint xmlLoc = OBJECT_FACTORY.createOpenlrPoiWithAccessPoint();
		OpenlrLocationReferencePoint lrp1 = createLRP(startLRP);
		List<LocationReferencePoint> lrps = new ArrayList<LocationReferencePoint>();
		lrps.add(startLRP);
		lrps.add(endLRP);
		OpenlrLastLocationReferencePoint lrp2 = createLastLRP(lrps);
		BigInteger dist = OpenLRDatex2Constants.NO_OFFSET;
		if (od.hasPositiveOffset()) {
			dist = BigInteger.valueOf(od.getPositiveOffset(0));
		} 
		PointCoordinates coord = OBJECT_FACTORY.createPointCoordinates();
		coord.setLongitude((float) gCoord.getLongitudeDeg());
		coord.setLatitude((float) gCoord.getLatitudeDeg());
		xmlLoc.setOpenlrCoordinate(coord);
		xmlLoc.setOpenlrLocationReferencePoint(lrp1);
		xmlLoc.setOpenlrLastLocationReferencePoint(lrp2);
		xmlLoc.setOpenlrPositiveOffset(dist);
		xmlLoc.setOpenlrSideOfRoad(resolveSideOfRoad(s));
		xmlLoc.setOpenlrOrientation(resolveOrientation(o));
		xmlData.setOpenlrPoiWithAccessPoint(xmlLoc);
		LocationReference locRefData = new LocationReferenceImpl(rawLocRef.getID(), xmlData, version);
		return locRefData;
	}
}
