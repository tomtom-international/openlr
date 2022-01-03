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
package openlr.map.sqlite.impl;

import openlr.map.*;
import openlr.map.sqlite.helpers.SpatialUtils;
import openlr.map.utils.GeometryUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.geom.Rectangle2D;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import static openlr.map.sqlite.impl.Configuration.TURN_RESTRICTIONS_SUPPORTED;

/**
 * Thread safe implementation of the OpenLR {@link openlr.map.MapDatabase}
 * interface for use with a TomTom digital map in SQLite format.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public final class MapDatabaseImpl implements openlr.map.MapDatabase {

    /**
     * The Constant KILO_BYTE.
     */
    private static final int KILO_BYTE = 1024;

    /**
     * The Constant STREAM_READ_BUFFER_SIZE.
     */
    private static final int STREAM_READ_BUFFER_SIZE = 1024;

    /**
     * The default logger for this class.
     */
    private static final Logger LOG = LogManager.getLogger(MapDatabaseImpl.class);

    /**
     * Map containing cached instances of class {@link openlr.map.Line}.
     */
    private final Map<Long, Line> cachedLines = SpatialUtils.createLRUCache();

    /**
     * Map containing cached instances of class {@link openlr.map.Node}.
     */
    private final Map<Long, Node> cachedNodes = SpatialUtils.createLRUCache();

    /**
     * Number of cache hits.
     */
    private final AtomicLong cacheHits = new AtomicLong(0);

    /**
     * Number of cache misses.
     */
    private final AtomicLong cacheMisses = new AtomicLong(0);

    /**
     * The name of this instance. This field should be populated on first access
     * to the getter method.
     */
    private String name;

    /**
     * The connection.
     */
    private final DBConnection connection;

    /**
     * Creates an instance of this {@link openlr.map.MapDatabase} implementation
     * which uses the given SQLite database.
     *
     * @param db a SQLite database which holds the network.
     */
    public MapDatabaseImpl(final String db) throws Exception {
        /* Validate arguments. */
        if (db == null) {
            throw new IllegalArgumentException("Database file not specified.");
        }
        final File dbFile = new File(db);
        if (!dbFile.exists()) {
            throw new IllegalArgumentException("Database file does not exist.");
        } else if (!dbFile.canRead()) {
            throw new IllegalArgumentException("Database is not readable.");
        } else if (dbFile.isDirectory()) {
            throw new IllegalArgumentException("Database file is a directory.");
        }

        /* Open database connection. */
        try {
            connection = new DBConnection(db);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Unable to load database driver.",
                    e);
        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Unable to open database connection.", e);
        }
    }

    /**
     * Creates a new instance of map database from the given input stream.
     * Assumes the input stream to provide the content of a valid SQLite
     * database according to the OpenLR SQLite schema. The given stream is
     * closed after successful processing. The application will create a
     * temporary file in the systems default temporary file directory holding
     * the database content.
     *
     * @param databaseStream The input stream providing the map database content, must not
     *                       be <code>null</code>
     * @throws IOException  If an error occurs when reading the stream or writing
     *                      internal content to the system temporary file directory
     * @throws SQLException If opening the database connection fails
     */
    public MapDatabaseImpl(final InputStream databaseStream)
            throws Exception {
        this(databaseStream, File.createTempFile("openlrMap", ".sqlite"));
    }

    /**
     * Creates a new instance of map database from the given input stream.
     * Assumes the input stream to provide the content of a valid SQLite
     * database according to the OpenLR SQLite schema. The given stream is
     * closed after successful processing. The application will create a
     * temporary file in in the specified target holding the database content.
     *
     * @param databaseStream The input stream providing the map database content, must not
     *                       be <code>null</code>
     * @param tempDataTarget A file object defining the path to a file system location the
     *                       application is allowed to write to. If
     *                       <code>tempDataTarget</code> points to a directory the
     *                       application will create its temporary file in that directory.
     *                       If <code>tempDataTarget</code> is a concrete file it will use
     *                       exactly this file. The temporary file will be deleted on
     *                       system exit.
     * @throws IOException  If an error occurs when reading the stream or writing
     *                      internal content to the specified temporary target file
     * @throws SQLException If opening the database connection fails
     */
    public MapDatabaseImpl(final InputStream databaseStream,
                           final File tempDataTarget) throws Exception {
        if (databaseStream == null) {
            throw new IllegalArgumentException(
                    "Database stream must not be null.");
        }
        if (tempDataTarget == null) {
            throw new IllegalArgumentException(
                    "Temporary data target must not be null.");
        }

        final File dbTempFile;
        if (tempDataTarget.isDirectory()) {
            dbTempFile = File.createTempFile("openlrMap", ".sqlite",
                    tempDataTarget);
        } else {
            dbTempFile = tempDataTarget;
        }
        dbTempFile.deleteOnExit();
        BufferedOutputStream out = new BufferedOutputStream(
                new FileOutputStream(dbTempFile));
        BufferedInputStream in = new BufferedInputStream(databaseStream);
        byte[] buffer = new byte[STREAM_READ_BUFFER_SIZE];
        try {
            int bytesRead = 0;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        } finally {
            out.close();
            in.close();
        }

        try {
            connection = new DBConnection(dbTempFile.getAbsolutePath());
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Unable to load database driver.",
                    e);
        }
    }

    /**
     * Stores a {@link openlr.map.Line} object in the cache, removes the first
     * object from cache if the size is the defined maximum size.
     *
     * @param line the {@link openlr.map.Line} to store in the cache.
     */
    private void cacheLine(final Line line) {
        if (line == null) {
            throw new IllegalArgumentException("Unable to cache null object.");
        }
        cachedLines.put(line.getID(), line);
    }

    /**
     * Stores a {@link openlr.map.Node} object in the cache, removes the first
     * object from cache if the size is the defined maximum size.
     *
     * @param node the {@link openlr.map.Node} to store in the cache.
     */
    private void cacheNode(final Node node) {
        if (node == null) {
            throw new IllegalArgumentException("Unable to cache null object.");
        }
        cachedNodes.put(node.getID(), node);
    }

    /**
     * Closes a {@link java.sql.ResultSet} and a
     * {@link java.sql.PreparedStatement} quietly.
     *
     * @param rs an object of type {@link java.sql.ResultSet}.
     */
    private void closeQuietly(final ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                LOG.error(e);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<Line> findLinesCloseByCoordinate(
            final double longitude, final double latitude, final int distance) {
        if (!SpatialUtils.isCoordinateValid(longitude, latitude)) {
            throw new IllegalArgumentException("Coordinate is out of bounds.");
        } else if (distance < 0) {
            throw new IllegalArgumentException("Distance value out of bounds.");
        }
        final Set<Line> linesCloseBy = new HashSet<Line>();
        ResultSet rs = null;
        try {
            final RectangleCorners bbox = SpatialUtils.calcBoundingBox(
                    longitude, latitude, 2 * distance);
            connection.getPsLinesCloseBy().setDouble(Configuration.BBOX_MINX,
                    bbox.getLowerLeft().getLongitudeDeg());
            connection.getPsLinesCloseBy().setDouble(Configuration.BBOX_MINY,
                    bbox.getLowerLeft().getLatitudeDeg());
            connection.getPsLinesCloseBy().setDouble(Configuration.BBOX_MAXX,
                    bbox.getUpperRight().getLongitudeDeg());
            connection.getPsLinesCloseBy().setDouble(Configuration.BBOX_MAXY,
                    bbox.getUpperRight().getLatitudeDeg());
            rs = connection.getPsLinesCloseBy().executeQuery();
            while (rs.next()) {
                final long id = rs.getLong("Id");
                final Line line = getLine(id);
                if (line.distanceToPoint(longitude, latitude) <= distance) {
                    linesCloseBy.add(line);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        } finally {
            closeQuietly(rs);
        }
        return linesCloseBy.iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<Node> findNodesCloseByCoordinate(
            final double longitude, final double latitude, final int distance) {
        if (!SpatialUtils.isCoordinateValid(longitude, latitude)) {
            throw new IllegalArgumentException("Coordinate is out of bounds.");
        } else if (distance < 0) {
            throw new IllegalArgumentException("Distance value out of bounds.");
        }

        final Set<Node> nodesCloseBy = new HashSet<Node>();
        ResultSet rs = null;

        try {
            final RectangleCorners bbox = SpatialUtils.calcBoundingBox(
                    longitude, latitude, distance);
            connection.getPsNodesCloseBy().setDouble(Configuration.BBOX_MINX,
                    bbox.getLowerLeft().getLongitudeDeg());
            connection.getPsNodesCloseBy().setDouble(Configuration.BBOX_MINY,
                    bbox.getLowerLeft().getLatitudeDeg());
            connection.getPsNodesCloseBy().setDouble(Configuration.BBOX_MAXX,
                    bbox.getUpperRight().getLongitudeDeg());
            connection.getPsNodesCloseBy().setDouble(Configuration.BBOX_MAXY,
                    bbox.getUpperRight().getLatitudeDeg());
            rs = connection.getPsNodesCloseBy().executeQuery();
            while (rs.next()) {
                final long id = rs.getLong("Id");
                Node node = getNode(id);
                if (GeometryUtils.distance(longitude, latitude,
                        node.getLongitudeDeg(), node.getLatitudeDeg()) <= distance) {
                    nodesCloseBy.add(node);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        } finally {
            closeQuietly(rs);
        }
        return nodesCloseBy.iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Line getLine(final long id) {
        return getLine(id, true);
    }

    /**
     * Gets the line.
     *
     * @param id       the id
     * @param useCache the use cache
     * @return the line
     */
    private Line getLine(final long id, final boolean useCache) {
        Line line = null;
        if (useCache) {
            line = cachedLines.get(id);
            if (line == null) {
                cacheMisses.incrementAndGet();
                line = readLine(id);
                if (line != null) {
                    cacheLine(line);
                }
            } else {
                cacheHits.incrementAndGet();
            }
        } else {
            line = readLine(id);
        }
        return line;
    }

    /**
     * Read line.
     *
     * @param id the id
     * @return the line
     */
    private Line readLine(final long id) {
        Line line = null;
        ResultSet rs = null;
        try {
            connection.getPsGetLine().setLong(1, id);
            rs = connection.getPsGetLine().executeQuery();
            if (rs.next()) {
                line = createLine(id, rs);
            }
        } catch (Exception e) {
            LOG.error(e);
        } finally {
            closeQuietly(rs);
        }
        return line;
    }

    /**
     * Creates the line.
     *
     * @param rs the rs
     * @return the line
     * @throws SQLException the sQL exception
     */
    private Line createLine(final ResultSet rs) throws SQLException {
        long id = rs.getLong(1);
        return createLine(id, rs);
    }

    /**
     * Creates the line.
     *
     * @param id the id
     * @param rs the rs
     * @return the line
     * @throws SQLException the sQL exception
     */
    private Line createLine(final long id, final ResultSet rs)
            throws SQLException {
        Line line;
        long startNodeId = rs.getLong("Start_Node_Id");
        long endNodeId = rs.getLong("End_Node_Id");
        FormOfWay formOfWay = FormOfWay.values()[rs.getInt("FOW")];
        FunctionalRoadClass functionalRoadClass = FunctionalRoadClass.values()[rs
                .getInt("FRC")];
        int length = rs.getInt("Length_Meters");
        List<GeoCoordinates> shape = SpatialUtils.shapeFromWKB(rs
                .getBytes("Geom"));

        final String displayName = rs.getString("Display_Name");
        final String displayNameLangCode = rs
                .getString("Display_Name_LangCode");
        Map<Locale, List<String>> names = new HashMap<Locale, List<String>>();
        if (displayName != null && displayNameLangCode != null) {
            names.put(new Locale(displayNameLangCode),
                    Arrays.asList(displayName));
        }
        names = Collections.unmodifiableMap(names);
        line = new LineImpl(this, id, startNodeId, endNodeId, formOfWay,
                functionalRoadClass, Collections.unmodifiableList(shape),
                length, names);
        return line;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Node getNode(final long id) {
        return getNode(id, true);

    }

    /**
     * Gets the node.
     *
     * @param id       the id
     * @param useCache the use cache
     * @return the node
     */
    private Node getNode(final long id, final boolean useCache) {
        Node node = null;
        if (useCache) {
            node = cachedNodes.get(id);
            if (node == null) {
                cacheMisses.incrementAndGet();
                node = createNode(id);
                if (node != null) {
                    cacheNode(node);
                }
            } else {
                cacheHits.incrementAndGet();
            }
        } else {
            node = createNode(id);
        }
        return node;
    }

    /**
     * Creates the node.
     *
     * @param id the id
     * @return the node
     */
    private Node createNode(final long id) {
        Node node = null;
        ResultSet rs = null;
        try {
            connection.getPsGetNode().setLong(1, id);
            rs = connection.getPsGetNode().executeQuery();
            node = createNode(rs, id);
        } catch (SQLException e) {
            LOG.error(e);
            node = null;
        } finally {
            closeQuietly(rs);
        }
        return node;
    }

    /**
     * Creates the node.
     *
     * @param rsNode the rs node
     * @return the node
     * @throws SQLException the sQL exception
     */
    private Node createNode(final ResultSet rsNode) throws SQLException {
        long id = rsNode.getLong(1);
        return createNode(rsNode, id);
    }

    /**
     * Creates the node.
     *
     * @param rsNode the rs node
     * @param id     the id
     * @return the node
     * @throws SQLException the sQL exception
     */
    private Node createNode(final ResultSet rsNode, final long id)
            throws SQLException {
        double longitude = rsNode.getDouble("Longitude");
        double latitude = rsNode.getDouble("Latitude");
        connection.getPsIncoming().setLong(1, id);
        ResultSet rs = connection.getPsIncoming().executeQuery();
        Set<Long> incoming = new HashSet<Long>();
        while (rs.next()) {
            final long lineId = rs.getLong("Id");
            incoming.add(lineId);
        }
        rs.close();

        connection.getPsOutgoing().setLong(1, id);
        rs = connection.getPsOutgoing().executeQuery();
        Set<Long> outgoing = new HashSet<Long>();
        while (rs.next()) {
            final long lineId = rs.getLong("Id");
            outgoing.add(lineId);
        }
        return new NodeImpl(this, id, longitude, latitude, incoming, outgoing);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasTurnRestrictionOnPath(final List<? extends Line> path) {
        return TURN_RESTRICTIONS_SUPPORTED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasTurnRestrictions() {
        return TURN_RESTRICTIONS_SUPPORTED;
    }

    /**
     * Outputs statistics on the caches to the log.
     */
    public void printCacheStats() {
        LOG.info(String
                .format("Memory usage: %d kb", (Runtime.getRuntime()
                        .totalMemory() - Runtime.getRuntime().freeMemory())
                        / KILO_BYTE));
        LOG.info(String.format("Cache entries: %d", cachedLines.size()
                + cachedNodes.size()));
        LOG.info(String.format("Cache hits: %d", cacheHits.get()));
        LOG.info(String.format("Cache misses: %d", cacheMisses.get()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        if (name == null) {
            ResultSet rs = null;
            try {
                rs = connection.getPsMetaData().executeQuery();
                if (rs.next()) {
                    String mapName = rs.getString("Map_Name");
                    String mapRelease = rs.getString("Map_Release");
                    String mapCopyrightOwner = rs
                            .getString("Map_Copyright_Owner");
                    name = String.format("%s %s (C) %s", mapName, mapRelease,
                            mapCopyrightOwner);
                }
            } catch (SQLException e) {
                LOG.error(e);
                name = "(unknown)";
            } finally {
                closeQuietly(rs);
            }
        }
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<Line> getAllLines() {
        final Set<Line> lines = new HashSet<Line>();
        ResultSet rs = null;
        Statement ps = null;
        try {
            ps = connection.createStatement();
        } catch (SQLException e1) {
            LOG.error(e1);
        }
        try {
            rs = ps.executeQuery("Select * from Line");
            while (rs.next()) {
                lines.add(createLine(rs));
            }
            ps.close();
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            closeQuietly(rs);
        }
        return lines.iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<Node> getAllNodes() {
        final Set<Node> nodes = new HashSet<Node>();
        ResultSet rs = null;
        Statement ps = null;
        try {
            ps = connection.createStatement();
            rs = ps.executeQuery("Select * from Node");
            while (rs.next()) {
                nodes.add(createNode(rs));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            closeQuietly(rs);
        }
        return nodes.iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Rectangle2D.Double getMapBoundingBox() {
        ResultSet rs = null;
        final Rectangle2D.Double rect = new Rectangle2D.Double();
        try {
            rs = connection.getPsMetaData().executeQuery();
            while (rs.next()) {
                final double minLon = rs.getDouble("Min_Longitude");
                final double minLat = rs.getDouble("Min_Latitude");
                final double maxLon = rs.getDouble("Max_Longitude");
                final double maxLat = rs.getDouble("Max_Latitude");
                rect.setRect(minLon, minLat, maxLon - minLon, maxLat - minLat);
            }
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            closeQuietly(rs);
        }
        return rect;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfLines() {
        int nrLines = 0;
        ResultSet rs = null;
        try {
            rs = connection.getPsLineCount().executeQuery();
            if (rs.next()) {
                nrLines = rs.getInt(1);
            } else {
                LOG.warn("Cannot resolve number of lines.");
            }
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            closeQuietly(rs);
        }
        return nrLines;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfNodes() {
        int nrNodes = 0;
        ResultSet rs = null;
        try {

            rs = connection.getPsNodeCount().executeQuery();
            if (rs.next()) {
                nrNodes = rs.getInt(1);
            } else {
                LOG.warn("Cannot resolve number of nodes.");
            }
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            closeQuietly(rs);
        }
        return nrNodes;
    }
}
