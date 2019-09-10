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
 * Copyright (C) 2009,2010 TomTom International B.V.
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
package openlr.map.mockdb;

import openlr.map.GeoCoordinatesImpl;
import openlr.map.Line;
import openlr.map.MapDatabase;
import openlr.map.Node;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.SubnodeConfiguration;
import org.jmock.Expectations;
import org.jmock.Mockery;

import java.util.*;

/**
 * Mocks method calls defined in a mock database configuration.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
class CallsMockery {

    /**
     * The list of map method mockers.
     */
    private final Mocker[] methodMockers = new Mocker[]{
            new PointAlongLineMocker(), new MeasureAlongLineMocker(),
            new DistanceToPointMocker(), new NodesByCoordinateMocker(),
            new LinesByCoordinateMocker()};
    /** The mocked map database object. */
    private MapDatabase mdb;
    /** A map of all the mocked nodes. */
    private HashMap<Long, Object> mockedNodes;
    /**  A map of all the mocked lines.*/
    private HashMap<Long, Object> mockedLines;
    /** The processed configuration. */
    private HierarchicalConfiguration config;

    /**
     * Creates a new instance.
     *
     * @param context
     *            The mocking object.
     * @param configuration
     *            The configuration object.
     * @param mapDB
     *            The mocked map database object.
     * @param linesMap
     *            A map of all the mocked lines.
     * @param nodesMap
     *            A map of all the mocked nodes.
     * @throws InvalidConfigurationException
     *             If an error occurred processing the configuration.
     */
    public final void mockMethods(final Mockery context,
                                  final HierarchicalConfiguration configuration,
                                  final MapDatabase mapDB, final HashMap<Long, Object> linesMap,
                                  final HashMap<Long, Object> nodesMap)
            throws InvalidConfigurationException {

        this.config = configuration;
        this.mdb = mapDB;
        this.mockedLines = linesMap;
        this.mockedNodes = nodesMap;

        mockMethodCalls(context);
    }

    /**
     * Checks for know (yet implemented) method call configurations for
     * {@link Line} objects and delegates the mocking tasks to the corresponding
     * {@link Mocker}s.
     *
     * @param context
     *            The mocking object.
     * @throws InvalidConfigurationException
     *             If an error occurred processing the configuration.
     */
    private void mockMethodCalls(final Mockery context)
            throws InvalidConfigurationException {

        for (Mocker mocker : methodMockers) {
            // convention a method configuration must fit path "Calls.<MethodName>Calls.<MethodName>:
            String propertyPrefix = "Calls." + mocker.getMethodName() + "Calls";

            Collection<?> col = getAsCollectionProperty(propertyPrefix + "."
                    + mocker.getIdentifier());

            if (col != null) {
                int size = col.size();
                for (int i = 0; i < size; i++) {
                    mocker.mock(context, config.configurationAt(propertyPrefix
                            + "." + mocker.getMethodName() + "(" + i + ")"));
                }
            }
        }
    }

    /**
     * Helper method that delivers a {@link Collection} object containing the
     * values of configuration for the specified property if there is at least
     * one value found. This simplifies handling of properties that can occur a
     * single time or multiple times. If the value wasn't found at all
     * <code>null</code> is returned.
     *
     * @param property
     *            The property key.
     * @return A collection of the values or <code>null</code>.
     */
    private Collection<?> getAsCollectionProperty(final String property) {
        Object prop = config.getProperty(property);
        Collection<?> propColl;
        if (prop != null) {
            if (prop instanceof Collection<?>) {
                propColl = ((Collection<?>) prop);
            } else {
                propColl = Arrays.asList(prop);
            }
        } else {
            propColl = null;
        }

        return propColl;
    }

    /**
     * The interface of a mocker worker.
     */
    private interface Mocker {

        /**
         * Mocks the specific method this mocker instance is implemented for
         * using the data of the <code>methodConfigurationBody</code>.
         *
         * @param context
         *            The mocking context.
         * @param methodConfigurationBody
         *            The sub-node configuration of this method mock
         *            configuration (the content below configuration node
         *            "Calls.&lt;MockersDesignatedMethodName>Calls.&lt;MockersDesignatedMethodName>".
         * @throws InvalidConfigurationException
         *             If an error occurred processing the configuration.
         */
        void mock(Mockery context,
                  SubnodeConfiguration methodConfigurationBody)
                throws InvalidConfigurationException;

        /**
         * @return The name of the method this mocked is implemented for as it
         * occurs in the configuration, e.g. "GetPointAlongLine".
         */
        String getMethodName();

        /**
         * Delivers the configuration sub-path to a mandatory property inside this
         * type of method configuration. This enables the possibility to check if
         * this kind of method is configured.
         * Such a key has to be a mandatory node. The delivered property path
         * has to start with the root element of the mocked method node and
         * right at the leaf of this configuration tree element (i.e. right
         * before the value of this property), e.g. "DistanceToPoint.Input.Longitude".
         *
         * @return The configuration sub-path to a mandatory property inside this
         * type of method configuration.
         */
        String getIdentifier();
    }

    /**
     * A {@link Line} method mocker processing configurations of method
     * {@link Line#getPointAlongLine}.
     */
    private class PointAlongLineMocker implements Mocker {

        /** {@inheritDoc} */
        public String getMethodName() {
            return "GetPointAlongLine";
        }

        /** {@inheritDoc} */
        public String getIdentifier() {
            return "GetPointAlongLine.Input";
        }

        /** {@inheritDoc} */
        public void mock(final Mockery context, final SubnodeConfiguration snc)
                throws InvalidConfigurationException {

            final int inputVal = snc.getInt("Input");
            final double outputLong = snc.getDouble("Output.Longitude");
            final double outputLat = snc.getDouble("Output.Latitude");
            final Line relatedLine = (Line) mockedLines.get(snc.getLong("[@line]"));

            if (relatedLine != null) {
                context.checking(new Expectations() {
                    {
                        allowing(relatedLine).getGeoCoordinateAlongLine(
                                with(equal(inputVal)));
                        will(returnValue(GeoCoordinatesImpl.newGeoCoordinatesUnchecked(outputLong,
                                outputLat)));
                    }
                });
            } else {
                throw new InvalidConfigurationException(
                        "Line " + snc.getLong("[@line]") + " is configured to "
                                + "be mocked for " + getMethodName() + " but no line "
                                + "with this ID could be found among the configured lines.");
            }
        }
    }

    /**
     * A {@link Line} method mocker processing configurations of method
     * {@link Line#measureAlongLine}.
     */
    private class MeasureAlongLineMocker implements Mocker {

        /** {@inheritDoc} */
        public String getMethodName() {
            return "MeasureAlongLine";
        }

        /** {@inheritDoc} */
        public String getIdentifier() {
            return "MeasureAlongLine.Output";
        }

        /** {@inheritDoc} */
        public void mock(final Mockery context, final SubnodeConfiguration snc) throws InvalidConfigurationException {

            final int outputVal = snc.getInt("Output");
            final double inputLong = snc.getDouble("Input.Longitude");
            final double inputLat = snc.getDouble("Input.Latitude");
            final Line relatedLine = (Line) mockedLines.get(snc.getLong("[@line]"));

            if (relatedLine != null) {
                context.checking(new Expectations() {
                    {
                        allowing(relatedLine).measureAlongLine(inputLong,
                                inputLat);
                        will(returnValue(outputVal));
                    }
                });
            } else {
                throw new InvalidConfigurationException(
                        "Line " + snc.getLong("[@line]") + " is configured to "
                                + "be mocked for " + getMethodName() + " but no line "
                                + "with this ID could be found among the configured lines.");
            }
        }
    }

    /**
     * A {@link Line} method mocker processing configurations of method
     * {@link Line#distanceToPoint}.
     */
    private class DistanceToPointMocker implements Mocker {

        /** {@inheritDoc} */
        public String getMethodName() {
            return "DistanceToPoint";
        }

        /** {@inheritDoc} */
        public String getIdentifier() {
            return "DistanceToPoint.Output";
        }

        /** {@inheritDoc} */
        public void mock(final Mockery context, final SubnodeConfiguration snc) throws InvalidConfigurationException {

            final int outputVal = snc.getInt("Output");
            final double inputLong = snc.getDouble("Input.Longitude");
            final double inputLat = snc.getDouble("Input.Latitude");
            final Line relatedLine = (Line) mockedLines.get(snc.getLong("[@line]"));

            if (relatedLine != null) {
                context.checking(new Expectations() {
                    {
                        allowing(relatedLine).distanceToPoint(inputLong,
                                inputLat);
                        will(returnValue(outputVal));
                    }
                });
            } else {
                throw new InvalidConfigurationException(
                        "Line " + snc.getLong("[@line]") + " is configured to "
                                + "be mocked for " + getMethodName() + " but no line "
                                + "with this ID could be found among the configured lines.");
            }
        }
    }

    /**
     * A {@link MapDatabase} method mocker processing configurations of method
     * {@link MapDatabase#findNodesCloseByCoordinate}.
     */
    private class NodesByCoordinateMocker implements Mocker {

        /** {@inheritDoc} */
        public String getMethodName() {
            return "FindNodesCloseByCoordinate";
        }

        /** {@inheritDoc} */
        public String getIdentifier() {
            return "FindNodesCloseByCoordinate.Input.Longitude";
        }

        /** {@inheritDoc} */
        public void mock(final Mockery context, final SubnodeConfiguration snc)
                throws InvalidConfigurationException {

            final double inputLong = snc.getDouble("Input.Longitude");
            final double inputLat = snc.getDouble("Input.Latitude");
            final int inputDistance = snc.getInt("Input.Distance");

            final Collection<Node> result;
            Object prop = snc.getProperty("Output.Node");
            Collection<?> propColl;
            if (prop == null) {
                propColl = Collections.EMPTY_LIST;
                result = Collections.emptyList();
            } else if (prop instanceof Collection<?>) {
                propColl = ((Collection<?>) prop);
                result = new ArrayList<Node>(propColl.size());
            } else {
                result = new ArrayList<Node>(1);
                propColl = Arrays.asList(prop);
            }

            Node node;
            for (Object nodeId : propColl) {
                node = (Node) mockedNodes.get(Long.valueOf((String) nodeId));
                if (node != null) {
                    result.add(node);
                } else {
                    throw new InvalidConfigurationException("Node \"" + nodeId
                            + "\" referenced in definition of method "
                            + "FindNodesCloseByCoordinate was not "
                            + "found in the prior setup list of nodes.");
                }
            }

            context.checking(new Expectations() {
                {
                    allowing(mdb).findNodesCloseByCoordinate(inputLong,
                            inputLat, inputDistance);
                    will(returnIterator(result));
                }
            });
        }
    }

    /**
     * A {@link MapDatabase} method mocker processing configurations of method
     * {@link MapDatabase#findLinesCloseByCoordinate}.
     */
    private class LinesByCoordinateMocker implements Mocker {

        /** {@inheritDoc} */
        public String getMethodName() {
            return "FindLinesCloseByCoordinate";
        }

        /** {@inheritDoc} */
        public String getIdentifier() {
            return "FindLinesCloseByCoordinate.Input.Longitude";
        }

        /** {@inheritDoc} */
        public void mock(final Mockery context, final SubnodeConfiguration snc) throws InvalidConfigurationException {

            final double inputLong = snc.getDouble("Input.Longitude");
            final double inputLat = snc.getDouble("Input.Latitude");
            final int inputDistance = snc.getInt("Input.Distance");

            final Collection<Line> result;
            Object prop = snc.getProperty("Output.Line");
            Collection<?> propColl;
            if (prop == null) {
                propColl = Collections.EMPTY_LIST;
                result = Collections.emptyList();
            } else if (prop instanceof Collection<?>) {
                propColl = ((Collection<?>) prop);
                result = new ArrayList<Line>(propColl.size());
            } else {
                result = new ArrayList<Line>(1);
                propColl = Arrays.asList(prop);
            }

            Line line;
            for (Object lineID : propColl) {
                line = (Line) mockedLines.get(Long.valueOf((String) lineID));
                if (line != null) {
                    result.add(line);
                } else {
                    throw new InvalidConfigurationException("Line \"" + lineID
                            + "\" referenced in definition of method "
                            + "FindLinesCloseByCoordinate was not "
                            + "found in the prior setup list of lines.");
                }
            }

            context.checking(new Expectations() {
                {
                    allowing(mdb).findLinesCloseByCoordinate(inputLong,
                            inputLat, inputDistance);
                    will(returnIterator(result));
                }
            });
        }
    }
}
