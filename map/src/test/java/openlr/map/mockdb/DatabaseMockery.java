/**
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 2 of the License and the extra
 * conditions for OpenLR. (see openlr-license.txt)
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
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
package openlr.map.mockdb;

import openlr.map.*;
import org.apache.commons.configuration.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jmock.Expectations;
import org.jmock.Mockery;

import java.io.InputStream;
import java.util.*;

/**
 * Initializes a mocked map database using the provided XML configuration.
 * The mocked lines and nodes are available via methods
 * {@link #getNode} and {@link #getLine}.
 *
 * If further mocking of these objects is wanted or necessary the mocking 
 * context object is available via {@link #getMockery()}.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
class DatabaseMockery {

    /** logger. */
    private static final Logger LOG = LogManager.getLogger(MockedMapDatabase.class);
    /** The mocking context. */
    private final Mockery context;
    /**
     * The encapsulated mocked map database.
     */
    private final MapDatabase mdb;
    /**
     * A map of all the mocked nodes.
     */
    private HashMap<Long, Object> mockedNodes;
    /**
     * A map of all the mocked lines.
     */
    private HashMap<Long, Object> mockedLines;


    /**
     * Creates e new instance.
     *
     * @param mockery
     *            The mockery instance to use for mocking objects.
     */
    DatabaseMockery(final Mockery mockery) {
        context = mockery;
        mdb = context.mock(MapDatabase.class);
    }


    /**
     * Creates a mocked map database from the given configuration. If this
     * method returns successfully the mocked lines and nodes are available via
     * methods {@link MapDatabase#getNode} and {@link MapDatabase#getLine} of
     * the returned {@link MapDatabase} object.
     *
     * @param configXml
     *            The configuration file containing the static map data.
     * @param resourceName
     *            The name of the configuration file, used for error logging.
     * @return The successfully setup mocked {@link MapDatabase}.
     * @throws InvalidConfigurationException
     *             In case of an error reading the configuration files.
     */
    MapDatabase mockMapDatabase(final InputStream configXml,
                                final String resourceName)
            throws InvalidConfigurationException {

        XMLConfiguration config = new XMLConfiguration();
        config.setDelimiterParsingDisabled(true);
        try {
            config.load(configXml);
        } catch (ConfigurationException e) {
            throw new InvalidConfigurationException(
                    "Error loading the configuration from \"" + resourceName
                            + "\"", e);
        }
        return buildUpMockedMapDatabase(config);
    }

    /**
     * Creates the mocked map database object and all desired mocked lines and
     * nodes objects.
     *
     * @param config
     *            The configuration object.
     * @return The successfully mocked map database.
     * @throws InvalidConfigurationException If an error occurred processing the
     * configuration.
     */
    private MapDatabase buildUpMockedMapDatabase(
            final HierarchicalConfiguration config)
            throws InvalidConfigurationException {

        HashMap<Long, Object> nodesData = new HashMap<Long, Object>();

        HashMap<Long, Object> linesMap = initLines(config, nodesData);

        mockedNodes = initNodes(config, nodesData);

        mockConnnectedEdges(linesMap);
        // only now the map of line elements is finished!
        mockedLines = linesMap;

        mockDbIteratorMethods(mockedLines.values(), mockedNodes.values());

        mockMethods(config);

        return mdb;
    }

    /**
     * Initializes the map of mocked nodes.
     *
     * @param config
     *            The configuration object.
     * @param nodesData
     *            The map of pre-configured nodes data.
     * @return A map of mocked nodes.
     */
    private HashMap<Long, Object> initNodes(final AbstractConfiguration config,
                                            final HashMap<Long, Object> nodesData) {

        HashMap<Long, Object> result;
        Object nodes = config.getProperty("Nodes.Node[@id]");


        if (nodes instanceof Collection<?>) {
            int size = ((Collection<?>) nodes).size();
            result = new HashMap<Long, Object>(size);

            for (int i = 0; i < size; i++) {

                final long nodeID = config.getLong("Nodes.Node(" + i + ")[@id]");

                NodesData nData = (NodesData) nodesData.get(nodeID);

                if (nData != null) {

                    final double lon = config.getDouble("Nodes.Node(" + i
                            + ").Longitude");
                    final double lat = config.getDouble("Nodes.Node(" + i
                            + ").Latitude");

                    final Node mockedNode = nData.getMockedNode();

                    final Collection<Line> outgoingLines = nData.getOutgoingLines();
                    final Collection<Line> incomingLines = nData.getIncomingLines();

                    // handle loop lines (duplicates in outgoing and incoming)
                    final HashMap<Long, Object> allLines = new HashMap<Long, Object>(
                            outgoingLines.size() + incomingLines.size());
                    for (Line line : incomingLines) {
                        allLines.put(line.getID(), line);
                    }
                    for (Line line : outgoingLines) {
                        allLines.put(line.getID(), line);
                    }

                    context.checking(new Expectations() {
                        {
                            allowing(mockedNode).getLatitudeDeg();
                            will(returnValue(lat));
                        }
                    });

                    context.checking(new Expectations() {
                        {
                            allowing(mockedNode).getLongitudeDeg();
                            will(returnValue(lon));
                        }
                    });

                    context.checking(new Expectations() {
                        {
                            allowing(mockedNode).getGeoCoordinates();
                            will(returnValue(GeoCoordinatesImpl.newGeoCoordinatesUnchecked(lon, lat)));
                        }
                    });

                    context.checking(new Expectations() {
                        {
                            allowing(mockedNode).getNumberConnectedLines();
                            will(returnValue(allLines.size()));
                        }
                    });

                    context.checking(new Expectations() {
                        {
                            allowing(mockedNode).getConnectedLines();
                            will(returnIterator(allLines.values()));
                        }
                    });

                    context.checking(new Expectations() {
                        {
                            allowing(mockedNode).getOutgoingLines();
                            will(returnIterator(outgoingLines));
                        }
                    });
                    context.checking(new Expectations() {
                        {
                            allowing(mockedNode).getIncomingLines();
                            will(returnIterator(incomingLines));
                        }
                    });

                    // popularize the node in the mocked map
                    context.checking(new Expectations() {
                        {
                            allowing(mdb).getNode(with(equal(nodeID)));
                            will(returnValue(mockedNode));
                        }
                    });

                    result.put(nodeID, mockedNode);

                } else {
                    LOG.warn("Node with ID " + nodeID
                            + " is not connected to any line!");
                }
            }

        } else {
            throw new IllegalArgumentException(
                    "There should be at least two nodes in a map configuration!");
        }

        if (result.size() < nodesData.size()) {
            logMissingNodesConfiguration(result.keySet(), nodesData.keySet(),
                    "Not all nodes of the line definitions are parametrized!");
        }

        return result;
    }

    /**
     * Mocks all the lines defined in the configuration and delivers a map of
     * all these line objects assigned to their IDs.
     *
     * @param config
     *            The configuration object.
     * @param nodesData
     *            A map ready to receive {@link NodesData} objects that are
     *            created during the line initialization. These objects contain
     *            all the nodes information that already originates during the
     *            line initialization including the prepared mocked {@link Node}
     *            objects.
     * @return The map containing all the mocked line objects.
     */
    private HashMap<Long, Object> initLines(final HierarchicalConfiguration config,
                                            final HashMap<Long, Object> nodesData) {

        HashMap<Long, Object> linesMap;
        Object lines = config.getProperty("Lines.Line[@id]");

        if (lines instanceof Collection<?>) {
            int size = ((Collection<?>) lines).size();
            linesMap = new HashMap<Long, Object>(size);

            Line current;
            for (int i = 0; i < size; i++) {
                current = initSingleLine(config.configurationAt("Lines.Line(" + i + ")"), nodesData);
                linesMap.put(current.getID(), current);
            }

        } else {
            linesMap = new HashMap<Long, Object>(1);
            Line result = initSingleLine(config.configurationAt("Lines.Line"), nodesData);
            linesMap.put(result.getID(), result);
        }

        return linesMap;
    }

    /**
     * Initializes a single line from the given line configuration. A mocked
     * line object is created. It is guaranteed that this object will deliver
     * have an available method {@link Line#getID()}. All the other basic data
     * methods are mocked on demand depending on the available data in the
     * configuration.
     *
     * @param config
     *            The sub configuration for a single line.
     * @param nodesData
     *            A map ready to receive {@link NodesData} objects that are
     *            created during the line initialization. This map will get a
     *            new entry for each node that is defined as start or end node
     *            in this line configuration. If this entry was already present
     *            it will be updated with an incoming line if it is configured
     *            as end node of this line or with an outgoing line if it is
     *            configured as start node.
     * @return The mocked line object.
     */
    private Line initSingleLine(final SubnodeConfiguration config,
                                final HashMap<Long, Object> nodesData) {

        final long lineID = config.getLong("[@id]");

        final Line mockedLine = context.mock(Line.class, "line" + String.valueOf(lineID));

        context.checking(new Expectations() {
            {
                allowing(mockedLine).getID();
                will(returnValue(lineID));
            }
        });

        if (config.containsKey("Length")) {
            final int length = config.getInt("Length");
            context.checking(new Expectations() {
                {
                    allowing(mockedLine).getLineLength();
                    will(returnValue(length));
                }
            });
        }

        if (config.containsKey("FOW")) {
            final FormOfWay fow = mapFOW(config.getString("FOW"));
            context.checking(new Expectations() {
                {
                    allowing(mockedLine).getFOW();
                    will(returnValue(fow));
                }
            });
        }

        if (config.containsKey("FRC")) {
            final FunctionalRoadClass frc = mapFRC(config.getString("FRC"));
            context.checking(new Expectations() {
                {
                    allowing(mockedLine).getFRC();
                    will(returnValue(frc));
                }
            });
        }

        if (config.containsKey("StartNode")) {
            final NodesData startNodeData = getOrAddNodesData(config
                    .getLong("StartNode"), nodesData);
            startNodeData.addOutgoingLine(mockedLine);
            context.checking(new Expectations() {
                {
                    allowing(mockedLine).getStartNode();
                    will(returnValue(startNodeData.getMockedNode()));
                }
            });
        }

        if (config.containsKey("EndNode")) {
            final NodesData endNodeData = getOrAddNodesData(config
                    .getLong("EndNode"), nodesData);
            endNodeData.addIncomingLine(mockedLine);
            context.checking(new Expectations() {
                {
                    allowing(mockedLine).getEndNode();
                    will(returnValue(endNodeData.getMockedNode()));
                }
            });
        }

        // popularize the line in the mocked map
        context.checking(new Expectations() {
            {
                allowing(mdb).getLine(with(equal(lineID)));
                will(returnValue(mockedLine));
            }
        });

        return mockedLine;
    }

    /**
     * Delivers the {@link NodesData} object assigned to the specified ID in the
     * given <code>nodesData</code> map or creates a new entry in it and
     * delivers this one.
     *
     * @param id
     *            The desired ID of the nodes data object
     * @param nodesData
     *            The map of already registered {@link NodesData} objects.
     * @return The retrieved or created {@link NodesData} object.
     */
    private NodesData getOrAddNodesData(final long id,
                                        final HashMap<Long, Object> nodesData) {

        NodesData data = (NodesData) nodesData.get(id);

        if (data == null) {
            final Node mockedNode = context.mock(Node.class, "node"
                    + String.valueOf(id));

            context.checking(new Expectations() {
                {
                    allowing(mockedNode).getID();
                    will(returnValue(id));
                }
            });

            data = new NodesData(mockedNode);
            nodesData.put(id, data);
        }

        return data;
    }


    /**
     * Mocks the methods of the mocked line objects that deliver the incoming
     * and outgoing lines. This method expects that the lines are already setup
     * this way that the mocked start and end node are delivered by the
     * corresponding methods and that these objects deliver the final results
     * of {@link Node#getIncomingLines()} and {@link Node#getOutgoingLines()}!
     *
     * @param linesMap The map of mocked line objects.
     */
    private void mockConnnectedEdges(final HashMap<Long, Object> linesMap) {

        for (final Object linesObj : linesMap.values()) {

            final Line line = (Line) linesObj;

            final Node startNode = line.getStartNode();
            final Node endNode = line.getEndNode();

            // We have to build up a new collection using the iterator to enable
            // that the mocked method later will return a new iterator instance
            // on every call
            final Collection<Line> tempCollIn = new ArrayList<Line>();
            for (Iterator<? extends Line> iter = startNode.getIncomingLines(); iter
                    .hasNext(); ) {
                tempCollIn.add(iter.next());
            }
            final Collection<Line> tempCollOut = new ArrayList<Line>();
            for (Iterator<? extends Line> iter = endNode.getOutgoingLines(); iter
                    .hasNext(); ) {
                tempCollOut.add(iter.next());
            }

            context.checking(new Expectations() {
                {
                    allowing(line).getPrevLines();
                    will(returnIterator(tempCollIn));
                }
            });

            context.checking(new Expectations() {
                {
                    allowing(line).getNextLines();
                    will(returnIterator(tempCollOut));
                }
            });

            context.checking(new Expectations() {
                {
                    allowing(line).getGeoCoordinateAlongLine(0);
                    will(returnValue(line.getStartNode().getGeoCoordinates()));
                }
            });

            context.checking(new Expectations() {
                {
                    allowing(line).getGeoCoordinateAlongLine(line.getLineLength());
                    will(returnValue(line.getEndNode().getGeoCoordinates()));
                }
            });



        }
    }


    /**
     * Maps form of way type from XML to OpenLR.
     *
     * @param fow
     *            the XML value for FOW
     *
     * @return the form of way
     */
    private FormOfWay mapFOW(final String fow) {
        FormOfWay result = null;
        for (FormOfWay fowType : FormOfWay.values()) {


            if (fowType.name().equals(fow)) {
                result = fowType;
                break;
            }
        }

        return result;
    }

    /**
     * Maps functional road class from XML to OpenLR.
     *
     * @param frc
     *            the xml functional road class
     * @return the functional road class
     */
    private FunctionalRoadClass mapFRC(final String frc) {
        FunctionalRoadClass result = null;

        for (FunctionalRoadClass frcType : FunctionalRoadClass.values()) {


            if (frcType.name().equals(frc)) {
                result = frcType;
                break;
            }
        }

        return result;
    }


    /**
     * Logs information about the error that not all expected IDs were found.
     *
     * @param actual
     *            The set of found IDs.
     * @param expected
     *            The set of expected IDs.
     * @param info
     *            A general information printed before the output of the
     *            mismatching IDs.
     */
    private void logMissingNodesConfiguration(final Set<Long> actual,
                                              final Set<Long> expected, final String info) {

        LOG.error(info);
        StringBuilder missing = new StringBuilder();
        for (Long nodeId : expected) {
            if (!actual.contains(nodeId)) {
                missing.append(nodeId).append(" ");
            }
        }
        LOG.error("Configurations for the following IDs are missing: "
                + missing);
    }

    /**
     * Mocks the map database objects methods that deliver iterators over the
     * network elements: {@link MapDatabase#getAllLines()} and
     * {@link MapDatabase#getAllNodes()}.
     *
     * @param mockedLines
     *          The mocked line objects.
     * @param mockedNodes
     */
    private void mockDbIteratorMethods(final Collection<Object> mockedLines,
                                       final Collection<Object> mockedNodes) {

        context.checking(new Expectations() {
            {
                allowing(mdb).getAllLines();
                will(returnIterator(mockedLines));
            }
        });
        context.checking(new Expectations() {
            {
                allowing(mdb).getAllNodes();
                will(returnIterator(mockedNodes));
            }
        });
    }

    /**
     * Mocks special methods on the mocked {@link MapDatabase}, {@link Line} or
     * {@link Node} objects if defined in the configuration. This method depends
     * on fully initialized node and lines objects!
     *
     * @param config
     *            The configuration object.
     * @throws InvalidConfigurationException
     *             If an error occurred processing the configuration.
     */
    private void mockMethods(final HierarchicalConfiguration config)
            throws InvalidConfigurationException {

        new CallsMockery().mockMethods(context, config, mdb, mockedLines,
                mockedNodes);
    }

    /**
     * An internal data class for collection data about nodes that is collected
     * during initialization of other elements than the nodes configurations
     * itself.
     */
    private static final class NodesData {

        /** The mocked node object. */
        private final Node node;

        /** The list of incoming lines of the node. */
        private Collection<Line> incomingLines = new ArrayList<Line>();

        /** The list of outgoing lines of the node. */
        private Collection<Line> outgoingLines = new ArrayList<Line>();

        /**
         * Creates a new instance.
         *
         * @param mockedNodeObject
         *            The mocked node object.
         */
        private NodesData(final Node mockedNodeObject) {
            this.node = mockedNodeObject;
        }

        /**
         * Adds the given line to the list of incoming lines of this node.
         * @param line The line to add.
         */
        private void addIncomingLine(final Line line) {
            this.incomingLines.add(line);
        }

        /**
         * Adds the given line to the list of outgoing lines of this node.
         * @param line The line to add.
         */
        private void addOutgoingLine(final Line line) {
            this.outgoingLines.add(line);
        }

        /**
         * @return A collection of outgoing lines of this node. If there are no
         * outgoing lines an empty collection is returned.
         */
        private Collection<Line> getOutgoingLines() {
            return Collections.unmodifiableCollection(outgoingLines);
        }

        /**
         * @return A collection of incoming lines of this node. If there are no
         * outgoing lines an empty collection is returned.
         */
        private Collection<Line> getIncomingLines() {
            return Collections.unmodifiableCollection(incomingLines);
        }

        /**
         * @return The mocked {@link Node} object assigned to this node data.
         */
        private Node getMockedNode() {
            return node;
        }
    }
}
