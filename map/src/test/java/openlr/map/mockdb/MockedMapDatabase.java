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

import openlr.map.Line;
import openlr.map.MapDatabase;
import openlr.map.Node;
import org.jmock.Mockery;

import java.awt.geom.Rectangle2D.Double;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

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
public class MockedMapDatabase implements MapDatabase {

    /** The mocking context. */
    private final Mockery context = new Mockery();

    /**
     * The encapsulated mocked map database.
     */
    private final MapDatabase mdb;


    /**
     * Creates a new mocked map database by processing the provided
     * configuration without validating the XML structure.
     *
     * @param configurationFile
     *            The configuration file. It is first searched in the class
     *            path. If this fails it is tried to evaluate it as file path.
     * @throws InvalidConfigurationException
     *             In case of an error processing the configuration file.
     */
    public MockedMapDatabase(final String configurationFile)
            throws InvalidConfigurationException {
        this(configurationFile, false);
    }

    /**
     * Creates a new mocked map database by processing the provided
     * configuration.
     *
     * @param configurationFile
     *            The configuration file. It is first searched in the class
     *            path. If this fails it is tried to evaluate it as file path.
     * @param validate
     *            Specifies whether a validation of the configuration file shall
     *            be done. This is helpful during development of the test
     *            configuration but should be disabled for enhancing performance
     *            once the test is successfully running.
     * @throws InvalidConfigurationException
     *             In case of an error processing the configuration file.
     */
    public MockedMapDatabase(final String configurationFile,
                             final boolean validate) throws InvalidConfigurationException {

        if (validate) {
            ConfigValidator.validate(resolveConfiguration(configurationFile));
        }

        mdb = new DatabaseMockery(context).mockMapDatabase(
                resolveConfiguration(configurationFile), configurationFile);
    }


    /**
     * @return The mocking context that created the instances of mocked objects
     * of this class. This exact instance of {@link Mockery} has to be used if
     * further mocking on these objects if this is desired.
     */
    public final Mockery getMockery() {
        return context;
    }

    /**
     * Returns an input stream of the configuration resource file. The provided
     * file is first searched in the class path. If this fails it is tried to
     * evaluate it as file path.
     * @param configFile The name of the configuration file.
     * @return The configuration as input stream.
     * @throws InvalidConfigurationException
     *             If an error occurs reading the configuration or if it was not
     *             found.
     */
    private InputStream resolveConfiguration(final String configFile)
            throws InvalidConfigurationException {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(
                configFile);

        if (stream == null) {
            File file = new File(configFile);
            if (file.exists()) {
                try {
                    stream = new FileInputStream(file);
                } catch (FileNotFoundException e) {
                    throw new InvalidConfigurationException(
                            "Error reading the " + "configuration resource \""
                                    + configFile + "\"", e);
                }
            } else {
                throw new InvalidConfigurationException("Could not find the "
                        + "configuration resource \"" + configFile + "\"");
            }
        }
        return stream;
    }


    /*
     * All the delegated methods to the encapsulated mocked map database:
     */

    @Override
    public final Iterator<Line> findLinesCloseByCoordinate(
            final double longitude, final double latitude, final int distance) {
        return mdb.findLinesCloseByCoordinate(longitude, latitude, distance);
    }

    @Override
    public final Iterator<Node> findNodesCloseByCoordinate(
            final double longitude, final double latitude, final int distance) {
        return mdb.findNodesCloseByCoordinate(longitude, latitude, distance);
    }

    @Override
    public final Iterator<Line> getAllLines() {
        return mdb.getAllLines();
    }

    @Override
    public final Iterator<Node> getAllNodes() {
        return mdb.getAllNodes();
    }

    @Override
    public final Line getLine(final long id) {
        return mdb.getLine(id);
    }

    @Override
    public final Double getMapBoundingBox() {
        return mdb.getMapBoundingBox();
    }

    @Override
    public final Node getNode(final long id) {
        return mdb.getNode(id);
    }

    @Override
    public final int getNumberOfLines() {
        return mdb.getNumberOfLines();
    }

    @Override
    public final int getNumberOfNodes() {
        return mdb.getNumberOfNodes();
    }

    @Override
    public final boolean hasTurnRestrictionOnPath(
            final List<? extends Line> path) {
        return mdb.hasTurnRestrictionOnPath(path);
    }

    @Override
    public final boolean hasTurnRestrictions() {
        return mdb.hasTurnRestrictions();
    }
}
