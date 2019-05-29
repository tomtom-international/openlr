/**
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License and the extra
 *  conditions for OpenLR. (see openlr-license.txt)
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

/**
 *  Copyright (C) 2009,2010 TomTom International B.V.
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
package openlr.decoder;

import openlr.LocationReferencePoint;
import openlr.map.FormOfWay;
import openlr.map.FunctionalRoadClass;

/**
 * This is an implementation of {@link LocationReferencePoint} that is used in
 * test here.
 * 
 * @author berndtax
 */
public class TestLocationReferencePointImpl implements LocationReferencePoint {

    final double lon;
    final double lat;
    final int dnp, seqNumber;
    final FunctionalRoadClass frc;
    final FormOfWay fow;
    final FunctionalRoadClass lfrcnp;
    final double bearing;
    private boolean isLast;

    /**
     * 
     * @param longitude The longitude
     * @param latitude The latitude
     * @param dnp The DNP
     * @param frc The FRC
     * @param fow The FOw
     * @param lfrcnp The LFRCNP
     * @param bearing The bearing
     * @param isLast Whether this is the last LRP in the location 
     * @param sequenceNumber The LRP sequence number in the location
     */
    public TestLocationReferencePointImpl(final double longitude, double latitude, int dnp,
            FunctionalRoadClass frc, FormOfWay fow, FunctionalRoadClass lfrcnp,
            double bearing, boolean isLast, int sequenceNumber) {

        this.lon = longitude;
        this.lat = latitude;
        this.dnp = dnp;
        this.seqNumber = sequenceNumber;
        this.frc = frc;
        this.fow = fow;
        this.lfrcnp = lfrcnp;
        this.bearing = bearing;
        this.isLast = isLast;
    }

    @Override
    public final double getBearing() {
        return bearing;
    }

    @Override
    public final double getLongitudeDeg() {

        return lon;
    }

    @Override
    public final double getLatitudeDeg() {

        return lat;
    }

    @Override
    public final int getDistanceToNext() {

        return dnp;
    }

    @Override
    public final FunctionalRoadClass getLfrc() {

        return lfrcnp;
    }

    @Override
    public final FormOfWay getFOW() {

        return fow;
    }

    @Override
    public final FunctionalRoadClass getFRC() {

        return frc;
    }

    @Override
    public final boolean isLastLRP() {

        return isLast;
    }

    @Override
    public final int getSequenceNumber() {
        return seqNumber;
    }

}
