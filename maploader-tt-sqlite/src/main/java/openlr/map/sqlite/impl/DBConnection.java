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

import org.apache.log4j.Logger;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteJDBCLoader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The Class DBConnection.
 */
public class DBConnection {

    /**
     * The default logger for this class.
     */
    private static final Logger LOG = Logger.getLogger(DBConnection.class);

    /**
     * Database connection.
     */
    private final Connection connection;

    /**
     * The ps get line.
     */
    private final PreparedStatement psGetLine;

    /**
     * The ps nodes close by.
     */
    private final PreparedStatement psNodesCloseBy;

    /**
     * The ps lines close by.
     */
    private final PreparedStatement psLinesCloseBy;

    /**
     * The ps get node.
     */
    private final PreparedStatement psGetNode;

    /**
     * The ps meta data.
     */
    private final PreparedStatement psMetaData;

    /**
     * The ps line count.
     */
    private final PreparedStatement psLineCount;

    /**
     * The ps node count.
     */
    private final PreparedStatement psNodeCount;

    /**
     * The ps incoming.
     */
    private final PreparedStatement psIncoming;

    /**
     * The ps outgoing.
     */
    private final PreparedStatement psOutgoing;


    /**
     * Instantiates a new dB connection.
     *
     * @param db the db
     * @throws ClassNotFoundException the class not found exception
     * @throws SQLException           the sQL exception
     */
    public DBConnection(final String db) throws Exception {
        connection = getDatabaseConnection(db);
        try {
            psGetLine = connection
                    .prepareStatement(Configuration.SQL_SELECT_LINE);
            psNodesCloseBy = connection
                    .prepareStatement(Configuration.SQL_FIND_CLOSE_BY_NODE);
            psLinesCloseBy = connection
                    .prepareStatement(Configuration.SQL_FIND_CLOSE_BY_LINE);
            psGetNode = connection
                    .prepareStatement(Configuration.SQL_SELECT_NODE);
            psMetaData = connection
                    .prepareStatement(Configuration.SQL_METADATA);
            psLineCount = connection
                    .prepareStatement(Configuration.SQL_LINE_COUNT);
            psNodeCount = connection
                    .prepareStatement(Configuration.SQL_NODE_COUNT);
            psIncoming = connection
                    .prepareStatement(Configuration.SQL_LINE_INCOMING);
            psOutgoing = connection
                    .prepareStatement(Configuration.SQL_LINE_OUTGOING);
        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Unable to prepare SQL statements.", e);
        }
    }


    /**
     * Gets the ps get line.
     *
     * @return the ps get line
     */
    public final PreparedStatement getPsGetLine() {
        return psGetLine;
    }


    /**
     * Gets the ps nodes close by.
     *
     * @return the ps nodes close by
     */
    public final PreparedStatement getPsNodesCloseBy() {
        return psNodesCloseBy;
    }


    /**
     * Gets the ps lines close by.
     *
     * @return the ps lines close by
     */
    public final PreparedStatement getPsLinesCloseBy() {
        return psLinesCloseBy;
    }


    /**
     * Gets the ps get node.
     *
     * @return the ps get node
     */
    public final PreparedStatement getPsGetNode() {
        return psGetNode;
    }


    /**
     * Gets the ps meta data.
     *
     * @return the ps meta data
     */
    public final PreparedStatement getPsMetaData() {
        return psMetaData;
    }


    /**
     * Gets the ps line count.
     *
     * @return the ps line count
     */
    public final PreparedStatement getPsLineCount() {
        return psLineCount;
    }


    /**
     * Gets the ps node count.
     *
     * @return the ps node count
     */
    public final PreparedStatement getPsNodeCount() {
        return psNodeCount;
    }


    /**
     * Gets the ps incoming.
     *
     * @return the ps incoming
     */
    public final PreparedStatement getPsIncoming() {
        return psIncoming;
    }


    /**
     * Gets the ps outgoing.
     *
     * @return the ps outgoing
     */
    public final PreparedStatement getPsOutgoing() {
        return psOutgoing;
    }

    /**
     * Gets the connection to a given SQLite database.
     *
     * @param db path to the SQLite database.
     * @return a connection to the specified SQLite database.
     * @throws ClassNotFoundException if the driver was not found in classpath.
     * @throws SQLException           if opening the database connection fails.
     */
    private Connection getDatabaseConnection(final String db)
            throws Exception {
        assert db != null;
        if (LOG.isDebugEnabled()) {
            String mode = "pure-java";
            if (SQLiteJDBCLoader.isNativeMode()) {
                mode = "native";
            }
            LOG.debug(String
                    .format("SQLite driver is running in %s mode", mode));
        }
        SQLiteConfig config = new SQLiteConfig();
        config.setReadOnly(true);
        final String url = String.format("jdbc:sqlite:%s", db);

        return config.createConnection(url);
    }

    /**
     * Creates the statement.
     *
     * @return the statement
     * @throws SQLException the sQL exception
     */
    public final Statement createStatement() throws SQLException {
        return connection.createStatement();
    }

}
