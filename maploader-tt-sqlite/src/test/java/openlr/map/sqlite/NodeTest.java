/**
 * Licensed to the TomTom International B.V. under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  TomTom International B.V.
 * licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * <p>
 * Copyright (C) 2009-2019 TomTom International B.V.
 * <p>
 * TomTom (Legal Department)
 * Email: legal@tomtom.com
 * <p>
 * TomTom (Technical contact)
 * Email: openlr@tomtom.com
 * <p>
 * Address: TomTom International B.V., Oosterdoksstraat 114, 1011DK Amsterdam,
 * the Netherlands
 */
/**
 *  Copyright (C) 2009-2019 TomTom International B.V.
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

import openlr.map.Line;
import openlr.map.MapDatabase;
import openlr.map.Node;
import openlr.map.sqlite.impl.MapDatabaseImpl;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.testng.Assert.*;

/**
 * The Class NodeTest.
 */
public class NodeTest {

    /**
     * The path to the map database file.
     */
    private static final String PATH_TO_MAP = "src/test/resources/test-db.sqlite";

    /** The mdb. */
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

    /**
     * Test node fail.
     */
    @Test
    public final void testNodeFail() {
        assertNull(mdb.getNode(-1));
    }

    /**
     * Test node success01.
     */
    @Test
    public final void testNodeSuccess01() {
        Node n = mdb.getNode(15280200254041l);
        assertNotNull(n);
        assertEquals(n.getID(), 15280200254041l);
        assertEquals(n.getLatitudeDeg(), 52.1069797);
        assertEquals(n.getLongitudeDeg(), 5.1016273);
        List<Line> incLines = convertToList(n.getIncomingLines());
        List<Line> outLines = convertToList(n.getOutgoingLines());
        assertEquals(incLines.size(), 2);
        assertEquals(outLines.size(), 3);
        assertTrue(incLines.contains(mdb.getLine(15280001229176l)));
        assertTrue(incLines.contains(mdb.getLine(-15280001229177l)));
        assertTrue(outLines.contains(mdb.getLine(15280001229177l)));
        assertTrue(outLines.contains(mdb.getLine(-15280001229176l)));
        assertTrue(outLines.contains(mdb.getLine(15280001229334l)));
        assertEquals(convertToList(n.getConnectedLines()).size(), 5);
        assertEquals(n.getNumberConnectedLines(), 5);
    }

    /**
     * Test node success02.
     */
    @Test
    public final void testNodeSuccess02() {
        Node n = mdb.getNode(15280200237435l);
        assertNotNull(n);
        assertEquals(n.getID(), 15280200237435l);
        assertEquals(n.getLatitudeDeg(), 52.1062796);
        assertEquals(n.getLongitudeDeg(), 5.1002275);
        List<Line> incLines = convertToList(n.getIncomingLines());
        List<Line> outLines = convertToList(n.getOutgoingLines());
        assertEquals(incLines.size(), 2);
        assertEquals(outLines.size(), 1);
        assertTrue(incLines.contains(mdb.getLine(15280001229338l)));
        assertTrue(incLines.contains(mdb.getLine(-15280001229337l)));
        assertTrue(outLines.contains(mdb.getLine(-15280001229332l)));
        assertEquals(convertToList(n.getConnectedLines()).size(), 3);
        assertEquals(n.getNumberConnectedLines(), 3);
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

}
