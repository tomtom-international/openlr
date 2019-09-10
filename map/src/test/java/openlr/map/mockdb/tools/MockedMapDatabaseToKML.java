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
package openlr.map.mockdb.tools;

import de.micromata.opengis.kml.v_2_2_0.*;
import openlr.map.Line;
import openlr.map.MapDatabase;
import openlr.map.Node;
import openlr.map.mockdb.InvalidConfigurationException;
import openlr.map.mockdb.MockedMapDatabase;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A tool that creates KML from a mock database configuration.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class MockedMapDatabaseToKML {

    /**
     * The width of the drawn lines between nodes.
     */
    private static final double LINE_WIDTH = 3.0d;

    /**
     * Initializes a mocked map database from the file specified by the first
     * argument and creates KML written to a file specified by argument two.
     *
     * @param args
     *            The program arguments
     */
    public static void main(final String[] args) {

        if ((args.length > 0) && args[0].equals("-?")) {
            usage();
            System.exit(0);
        }
        if (args.length < 2) {
            System.out.println("Invalid number of arguments!");
            usage();
            System.exit(-1);
        }

        File inputFile = new File(args[0]);
        File outputFile = new File(args[1]);

        MapDatabase mdb = null;
        try {
            System.out.println("Generating mocked map database from "
                    + inputFile.getAbsolutePath() + " ...");
            mdb = new MockedMapDatabase(inputFile.getAbsolutePath());
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        try {
            System.out.println("Generating KML to "
                    + outputFile.getAbsolutePath() + " ...");
            new MockedMapDatabaseToKML().generateKml(mdb, inputFile.getName(),
                    new FileWriter(outputFile));
        } catch (IOException e) {
            System.out.println("Error initializing output file " + outputFile);
            e.printStackTrace();
        }

        System.out.println("Success!");

    }

    /**
     * Dumps out the usage help to system out.
     */
    private static void usage() {
        System.out.println("Usage:");
        System.out
                .println("openlr.map.mockdb.tools.MockedMapDatabaseToKML <mockDbFile> <outputKmlFile>");
        System.out.println();
        System.out
                .println("mockDbFile:\tThe path to the mock database configuration file.");
        System.out
                .println("outputKmlFile:\tThe path to the file to be created for the result.");
        System.out.println();
        System.out.println("Example:");
        System.out
                .println("openlr.map.mockdb.tools.MockedMapDatabaseToKML DefaultMapDatabase.xml result.kml");
        System.out.println();
        System.out
                .println("(openlr.map.mockdb.tools.MockedMapDatabaseToKML -? prints this help)");
    }

    /**
     * Creates a KML file displaying the given {@link MapDatabase}.
     *
     * @param mdb
     *            The map database.
     * @param name
     *            The name of the entire place for KML.
     * @param outWriter
     *            The output writer.
     */
    public final void generateKml(final MapDatabase mdb, final String name,
                                  final Writer outWriter) {
        Kml kml = generateKML(mdb, name);
        kml.marshal(outWriter);
    }

    /**
     * Generates KML for drawing the lines and nodes.
     *
     * @param mdb
     *            The map database.
     * @param name
     *            The content name.
     * @return The generated KML.
     */
    private Kml generateKML(final MapDatabase mdb, final String name) {

        Kml kml = KmlFactory.createKml();

        Document document = kml.createAndSetDocument().withName(name)
                .withOpen(true);

        drawLines(mdb, document);

        drawNodes(mdb, document);

        return kml;
    }

    /**
     * Generates KML for all lines of the database.
     *
     * @param mdb
     *            The map database.
     * @param document
     *            The KML {@link Document}.
     */
    private void drawLines(final MapDatabase mdb, final Document document) {

        Iterator<? extends Line> lineIter = mdb.getAllLines();
        Line line;

        final Style style = document.createAndAddStyle().withId("lineStyle");
        style.createAndSetLineStyle().withColor("7ff90000").withWidth(LINE_WIDTH);

        while (lineIter.hasNext()) {
            line = lineIter.next();
            Node start = line.getStartNode();
            Node end = line.getEndNode();

            Placemark placemark = new Placemark();
            document.getFeature().add(placemark);
            placemark.setName("Line " + line.getID());
            placemark.setDescription("From node " + start.getID() + " to "
                    + end.getID());
            placemark.setStyleUrl("#lineStyle");

            LineString linestring = new LineString();
            placemark.setGeometry(linestring);
            linestring.setExtrude(false);
            linestring.setTessellate(true);
            List<Coordinate> coords = new ArrayList<Coordinate>();
            linestring.setCoordinates(coords);

            coords.add(new Coordinate(start.getLongitudeDeg(), start
                    .getLatitudeDeg()));
            coords.add(new Coordinate(end.getLongitudeDeg(), end
                    .getLatitudeDeg()));
        }
    }

    /**
     * Generates KML for all nodes of the database.
     *
     * @param mdb
     *            The map database.
     * @param document
     *            The KML {@link Document}.
     */
    private void drawNodes(final MapDatabase mdb, final Document document) {
        Iterator<? extends Node> nodeIter = mdb.getAllNodes();
        Node node;
        while (nodeIter.hasNext()) {
            node = nodeIter.next();
            document.addToFeature(createPlacemarkFromNode(node));
        }
    }

    /**
     * Creates a {@link Placemark} object from a {@link Node}.
     *
     * @param node
     *            The node.
     * @return The placemark
     */
    private Placemark createPlacemarkFromNode(final Node node) {
        Placemark placemark = new Placemark().withName(String.valueOf(node
                .getID()));
        placemark.createAndSetPoint().addToCoordinates(
                node.getLongitudeDeg() + "," + node.getLatitudeDeg());

        StringBuilder sb = new StringBuilder();
        sb.append("Lon: " + node.getLongitudeDeg() + ", Lat: "
                + node.getLatitudeDeg() + "<br><br>");

        sb.append("<u>Incoming lines from:</u><br>");
        Iterator<? extends Line> inLines = node.getIncomingLines();
        Line line;
        while (inLines.hasNext()) {
            line = inLines.next();
            sb.append("Node " + line.getStartNode().getID())
                    .append(" (line " + line.getID() + ")").append("<br>");
        }

        sb.append("<u>Outgoing lines to:</u><br>");
        Iterator<? extends Line> outLines = node.getOutgoingLines();
        while (outLines.hasNext()) {
            line = outLines.next();
            sb.append("Node " + line.getEndNode().getID())
                    .append(" (line " + line.getID() + ")").append("<br>");
        }

        placemark.setDescription(sb.toString());
        return placemark;
    }
}
