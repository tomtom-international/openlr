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
package openlr.map.sqlite;

import openlr.map.*;
import openlr.map.sqlite.impl.MapDatabaseImpl;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.testng.Assert.*;

public class LineTest {

    /**
     * The path to the map database file.
     */
    private static final String PATH_TO_MAP = "src/test/resources/test-db.sqlite";

    /**
     * The mdb.
     */
    private MapDatabase mdb;

    /**
     * Inits the.
     */
    @BeforeTest
    public final void init() {
        try {
            mdb = new MapDatabaseImpl(PATH_TO_MAP);
        } catch (Exception e) {
            fail("Unexpected exception", e);
        }
    }

    @Test
    public final void testLineFail() {
        assertNull(mdb.getLine(0));
    }

    @Test
    public final void testLine01() {
        Line line = mdb.getLine(15280001530576L);
        assertEquals(line.getID(), 15280001530576L);
        assertEquals(line.getLineLength(), 77);
        assertEquals(line.getEndNode().getID(), 15280200246956L);
        assertEquals(line.getStartNode().getID(), 15280200250694L);
        assertEquals(line.getFOW(), FormOfWay.SINGLE_CARRIAGEWAY);
        assertEquals(line.getFRC(), FunctionalRoadClass.FRC_3);
        assertEquals(line.getNames().toString(), "{=[Ondiep-Zuidzijde]}");
        assertEquals(line.distanceToPoint(5.12345, 51.3241), 86696);

        GeoCoordinates expectedPointAlong = GeoCoordinatesImpl
                .newGeoCoordinatesUnchecked(5.0990069711481665,
                        52.103574875955076);
        assertEquals(line.getGeoCoordinateAlongLine(20), expectedPointAlong);
        GeoCoordinates pointAlongLine = line.getGeoCoordinateAlongLine(20);
        assertEquals(pointAlongLine.getLongitudeDeg(), expectedPointAlong.getLongitudeDeg());
        assertEquals(pointAlongLine.getLatitudeDeg(), expectedPointAlong.getLatitudeDeg());
        assertEquals(
                line.measureAlongLine(5.099006952227727, 52.103574858528034),
                20);
        List<Line> next = convertToList(line.getNextLines());
        assertTrue(next.contains(mdb.getLine(-15280001530576L)));
        assertTrue(next.contains(mdb.getLine(15280001530577L)));
        assertTrue(next.contains(mdb.getLine(15280001834811L)));
        assertEquals(next.size(), 3);
        List<Line> prev = convertToList(line.getPrevLines());
        assertTrue(prev.contains(mdb.getLine(15280001229310L)));
        assertTrue(prev.contains(mdb.getLine(-15280001530576L)));
        assertTrue(prev.contains(mdb.getLine(-15280002185158L)));
        assertEquals(prev.size(), 3);

        double[] coordsExpected = new double[] {5.0991692, 52.1037243,
                5.0985446, 52.103149};
        assertTrue(checkShapeCoordinates(line.getShapeCoordinates(),
                coordsExpected));
    }

    @Test
    public final void testLine02() {
        Line line = mdb.getLine(15280049412529L);
        assertEquals(line.getID(), 15280049412529L);
        assertEquals(line.getLineLength(), 39);
        assertEquals(line.getEndNode().getID(), 15280200248968L);
        assertEquals(line.getStartNode().getID(), 15280200254946L);
        assertEquals(line.getFOW(), FormOfWay.SINGLE_CARRIAGEWAY);
        assertEquals(line.getFRC(), FunctionalRoadClass.FRC_4);
        assertEquals(line.getNames().toString(), "{=[Van der Marckstraat]}");
        assertEquals(line.distanceToPoint(5.12345, 51.3241), 87664);
		
        GeoCoordinates expectedPointAlong = GeoCoordinatesImpl
                .newGeoCoordinatesUnchecked(5.097857006797935, 52.111972794658);
        assertEquals(line.getGeoCoordinateAlongLine(20), expectedPointAlong);
        GeoCoordinates pointAlongLine = line.getGeoCoordinateAlongLine(20);
        assertEquals(pointAlongLine.getLongitudeDeg(), expectedPointAlong.getLongitudeDeg());
        assertEquals(pointAlongLine.getLatitudeDeg(), expectedPointAlong.getLatitudeDeg());

        assertEquals(line.measureAlongLine(5.097856393202065, 52.111972405342),
                19);
        List<Line> next = convertToList(line.getNextLines());
        assertTrue(next.contains(mdb.getLine(-15280049412529L)));
        assertTrue(next.contains(mdb.getLine(15280049412530L)));
        assertEquals(next.size(), 2);
        List<Line> prev = convertToList(line.getPrevLines());
        assertTrue(prev.contains(mdb.getLine(-15280049412529L)));
        assertTrue(prev.contains(mdb.getLine(15280049301152L)));
        assertTrue(prev.contains(mdb.getLine(-15280001836912L)));
        assertTrue(prev.contains(mdb.getLine(15280001836911L)));
        assertEquals(prev.size(), 4);

        double[] coordsExpected = new double[] {5.0976537,
                52.1118438, 5.0980597, 52.1121014};
        assertTrue(checkShapeCoordinates(line.getShapeCoordinates(),
                coordsExpected));
    }

    @Test
    public final void testLine03() {
        Line line = mdb.getLine(15280001229443L);
        assertEquals(line.getID(), 15280001229443L);
        assertEquals(line.getLineLength(), 110);
        assertEquals(line.getEndNode().getID(), 15280200256355L);
        assertEquals(line.getStartNode().getID(), 15280200241429L);
        assertEquals(line.getFOW(), FormOfWay.SINGLE_CARRIAGEWAY);
        assertEquals(line.getFRC(), FunctionalRoadClass.FRC_4);
        assertEquals(line.getNames().toString(), "{=[Esdoornstraat]}");
        assertEquals(line.distanceToPoint(5.12345, 51.3241), 86409);
		
        
        GeoCoordinates expectedPointAlong = GeoCoordinatesImpl
                .newGeoCoordinatesUnchecked(5.1010161156332305, 52.10103891977444);
        assertEquals(line.getGeoCoordinateAlongLine(20), expectedPointAlong);
        GeoCoordinates pointAlongLine = line.getGeoCoordinateAlongLine(20);
        assertEquals(pointAlongLine.getLongitudeDeg(), expectedPointAlong.getLongitudeDeg());
        assertEquals(pointAlongLine.getLatitudeDeg(), expectedPointAlong.getLatitudeDeg());

        assertEquals(
                line.measureAlongLine(5.101014346091693, 52.10103982005639), 19);
        List<Line> next = convertToList(line.getNextLines());
        assertTrue(next.contains(mdb.getLine(15280001442938L)));
        assertTrue(next.contains(mdb.getLine(15280001229444L)));
        assertTrue(next.contains(mdb.getLine(15280001229441L)));
        assertEquals(next.size(), 3);
        List<Line> prev = convertToList(line.getPrevLines());
        assertTrue(prev.contains(mdb.getLine(-15280001442879L)));
        assertTrue(prev.contains(mdb.getLine(15280001229438L)));
        assertEquals(prev.size(), 2);

        double[] coordsExpected = new double[] { 5.1007911,
                52.1011534, 5.1010706, 52.1010112, 5.1012007, 52.1009611,
                5.1017395, 52.1007831, 5.1019401, 52.1007102, 5.1020738,
                52.1006554, 5.1021449, 52.1006148};
        assertTrue(checkShapeCoordinates(line.getShapeCoordinates(),
                coordsExpected));
    }

    /**
     * Convert to list.
     *
     * @param <T>  the generic type
     * @param iter the iter
     * @return the list
     */
    private <T> List<T> convertToList(Iterator<? extends T> iter) {
        List<T> list = new ArrayList<T>();
        while (iter.hasNext()) {
            list.add(iter.next());
		}
		return list;
	}

	
    private boolean checkShapeCoordinates(List<GeoCoordinates> path,
                                          double... coords) {
        if (coords.length < 2) {
            return false;
        }

        int coordsCounter = 0;
        for (GeoCoordinates current : path) {

            GeoCoordinates expected = GeoCoordinatesImpl
                    .newGeoCoordinatesUnchecked(coords[coordsCounter++],
                            coords[coordsCounter++]);

            if (!current.equals(expected)) {
                return false;
            }

        }
        return true;
    }
}
