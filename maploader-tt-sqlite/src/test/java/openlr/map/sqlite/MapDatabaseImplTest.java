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

import openlr.map.Line;
import openlr.map.MapDatabase;
import openlr.map.Node;
import openlr.map.loader.MapLoadParameter;
import openlr.map.loader.OpenLRMapLoader;
import openlr.map.loader.OpenLRMapLoaderException;
import openlr.map.sqlite.impl.MapDatabaseImpl;
import openlr.map.sqlite.loader.DBFileNameParameter;
import openlr.map.sqlite.loader.SQLiteMapLoader;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Tests instantiations of the map database
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class MapDatabaseImplTest {

    /**
     * The path to the map database file
     */
    private static final String PATH_TO_MAP = "src/test/resources/test-db.sqlite";

    /**
     * The expected number of lines in the map database after loading the
     * database of file {@link #PATH_TO_MAP}
     */
    private static final int EXPECTED_NUMBER_OF_LINES = 756;

    /**
     * The expected number of nodes in the map database after loading the
     * database of file {@link #PATH_TO_MAP}
     */
    private static final int EXPECTED_NUMBER_OF_NODES = 374;

    /**
     * Instantiates a map database via direct constructor call
     */
    @Test
    public final void testMapLoad() {

        try {
            MapDatabase map = new MapDatabaseImpl(PATH_TO_MAP);
            checkMapValid(map);
        } catch (Exception e) {
            Assert.fail("Unexpected exception", e);
        }
    }

    /**
     * Instantiates a map database via direct constructor call providing an
     * input stream
     */
    @Test
    public final void testMapLoadViaStream() {

        FileInputStream in;
        try {
            in = new FileInputStream(new File(PATH_TO_MAP));

            MapDatabase map = new MapDatabaseImpl(in);

            checkMapValid(map);

        } catch (IOException e) {
            Assert.fail("Unexpected exception", e);
        } catch (SQLException e) {
            Assert.fail("Unexpected exception", e);
        }
    }

    /**
     * Instantiates a map database via direct constructor call providing an
     * input stream and a target file
     */
    @Test
    public final void testMapLoadViaStreamToFile() {

        FileInputStream in;
        File targetFile = new File("target/tempSqlite.sqlite");
        try {
            in = new FileInputStream(new File(PATH_TO_MAP));

            MapDatabase map = new MapDatabaseImpl(in, targetFile);

            checkMapValid(map);

            Assert.assertTrue(targetFile.exists());

        } catch (IOException e) {
            Assert.fail("Unexpected exception", e);
        } catch (SQLException e) {
            Assert.fail("Unexpected exception", e);
        }
    }

    /**
     * Instantiates a map database via direct constructor call providing an
     * input stream and a target directory
     */
    @Test
    public final void testMapLoadViaStreamToDirectory() {
        FileInputStream in;
        File targetDirectory = new File("target/testTempDir");
        targetDirectory.mkdirs();
        try {
            in = new FileInputStream(new File(PATH_TO_MAP));
            MapDatabase map = new MapDatabaseImpl(in, targetDirectory);
            checkMapValid(map);
            targetDirectory.delete();

        } catch (IOException e) {
            Assert.fail("Unexpected exception", e);
        } catch (SQLException e) {
            Assert.fail("Unexpected exception", e);
        }
    }

    /**
     * Instantiates a map database via map loader
     */
    @Test
    public final void testMapLoadViaLoader() {

        MapLoadParameter param = new DBFileNameParameter();
        param.setValue(PATH_TO_MAP);
        OpenLRMapLoader loader = new SQLiteMapLoader();
        try {

            MapDatabase map = loader.load(Arrays.asList(param));

            checkMapValid(map);

        } catch (OpenLRMapLoaderException e) {
            Assert.fail("Unexpected exception during map load", e);
        }
    }

    /**
     * Verifies if the given map is valid.
     *
     * @param map The map to check
     */
    private void checkMapValid(final MapDatabase map) {
        Assert.assertEquals(map.getNumberOfLines(), EXPECTED_NUMBER_OF_LINES);
        Assert.assertEquals(map.getNumberOfNodes(), EXPECTED_NUMBER_OF_NODES);
    }

    @Test
    public final void testAllLinesAndNodes() {
        MapDatabase map = new MapDatabaseImpl(PATH_TO_MAP);
        Assert.assertEquals(convertToList(map.getAllLines()).size(),
                EXPECTED_NUMBER_OF_LINES);
        Assert.assertEquals(convertToList(map.getAllNodes()).size(),
                EXPECTED_NUMBER_OF_NODES);
    }

    @Test
    public final void testAllBBox() {
        MapDatabase map = new MapDatabaseImpl(PATH_TO_MAP);
        Rectangle2D.Double bbox = map.getMapBoundingBox();
        Assert.assertEquals(bbox.x, 5.0953228);
        Assert.assertEquals(bbox.y, 52.0995754);
        Assert.assertEquals(bbox.height, 0.013891100000002155);
        Assert.assertEquals(bbox.width, 0.014085099999999962);
    }

    @Test
    public final void testClosestLines() {
        MapDatabase map = new MapDatabaseImpl(PATH_TO_MAP);
        List<Line> lines = convertToList(map.findLinesCloseByCoordinate(
                5.09500, 52.10000, 200));
        Assert.assertTrue(lines.contains(map.getLine(15280001229410L)));
        Assert.assertTrue(lines.contains(map.getLine(-15280001229409L)));
        Assert.assertTrue(lines.contains(map.getLine(15280001229408L)));
        Assert.assertTrue(lines.contains(map.getLine(-15280001442912L)));
        Assert.assertTrue(lines.contains(map.getLine(-15280001229410L)));
        Assert.assertTrue(lines.contains(map.getLine(15280001229409L)));
        Assert.assertTrue(lines.contains(map.getLine(-15280001229408L)));
        Assert.assertTrue(lines.contains(map.getLine(-15280001442910L)));
        Assert.assertTrue(lines.contains(map.getLine(15280001442914L)));
        Assert.assertEquals(lines.size(), 9);
    }

    @Test
    public final void testClosestNodes() {
        MapDatabase map = new MapDatabaseImpl(PATH_TO_MAP);
        List<Node> nodes = convertToList(map.findNodesCloseByCoordinate(
                5.09500, 52.10000, 200));
        Assert.assertTrue(nodes.contains(map.getNode(15280200242589L)));
        Assert.assertTrue(nodes.contains(map.getNode(15280200240880L)));
        Assert.assertTrue(nodes.contains(map.getNode(15280200255259L)));
        Assert.assertEquals(nodes.size(), 3);
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
