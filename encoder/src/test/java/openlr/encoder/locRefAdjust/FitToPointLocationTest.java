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
package openlr.encoder.locRefAdjust;

import openlr.LocationType;
import openlr.OpenLRProcessingException;
import openlr.PhysicalEncoder;
import openlr.encoder.TestData;
import openlr.encoder.data.ExpansionData;
import openlr.encoder.data.LocRefData;
import openlr.encoder.data.LocRefPoint;
import openlr.encoder.locRefAdjust.worker.LrpBasedPointLocRefAdjust;
import openlr.encoder.properties.OpenLREncoderProperties;
import openlr.location.Location;
import openlr.map.GeoCoordinates;
import openlr.map.GeoCoordinatesImpl;
import openlr.map.Line;
import openlr.map.Node;
import openlr.properties.OpenLRPropertiesReader;
import org.apache.commons.configuration.Configuration;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * Tests the code that shrinkes location references of point locations to two LRPs if they have more than two.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class FitToPointLocationTest {

    private static final double DOUBLE_DELTA_ALLOWED = 0.0001;
    /**
     * Our simple map, a path providing three connected lines
     */
    final Line line1, line2, line3;
    private final GeoCoordinates MIDDLE_OF_LINE2 = GeoCoordinatesImpl.newGeoCoordinatesUnchecked(1, 2.5);
    private final GeoCoordinates MIDDLE_OF_LINE3 = GeoCoordinatesImpl.newGeoCoordinatesUnchecked(1, 1.5);
    private final Mockery mockery = new Mockery();
    private final OpenLREncoderProperties encProperties;
    private final LrpBasedPointLocRefAdjust locRefAdjust = new LrpBasedPointLocRefAdjust();

    public FitToPointLocationTest() throws OpenLRProcessingException {
        InputStream propFile = TestData.class
                .getResourceAsStream("/OpenLR-Encoder-Properties.xml");

        if (propFile == null) {
            fail("missing properties file");
        }
        Configuration config = null;
        try {
            config = OpenLRPropertiesReader.loadPropertiesFromStream(propFile,
                    true);
        } catch (OpenLRProcessingException e) {
            fail("Reading encoder properties failed.");
        }

        encProperties = new OpenLREncoderProperties(config,
                Collections.<PhysicalEncoder>emptyList());

        Line[] lines = mockMap();
        line1 = lines[0];
        line2 = lines[1];
        line3 = lines[2];
    }


    /**
     * Checks reduction of a locref with 4 points and the POI line (======) in the middle.
     * 1------2======3-------4
     * Should result in:
     * 2======3
     */
    @Test
    public void testFitForLrpAtNodeInTheMiddle() throws OpenLRProcessingException {

        final Location loc = mockNewLocation(0);

        // the POI line is the one in the middle, line 2
        mockery.checking(new Expectations() {
            {
                allowing(loc).getPoiLine();
                will(returnValue(line2));
            }
        });

        LocRefData locRefData = new LocRefData(loc);
        LocRefPoint point1 = new LocRefPoint(asList(line1), encProperties);
        LocRefPoint point2 = new LocRefPoint(asList(line2), encProperties);
        LocRefPoint point3 = new LocRefPoint(asList(line3), encProperties);
        // this is the expansion defined by the second LRP
        locRefData.setExpansion(new ExpansionData(asList(line1), asList(line3)));
        LocRefPoint last = new LocRefPoint(line3, encProperties);
        point1.setNextLRP(point2);
        point2.setNextLRP(point3);
        point3.setNextLRP(last);

        locRefData.setLocRefPoints(asList(point1, point2, point3, last));

        locRefAdjust.adjustLocationReference(encProperties, locRefData);

        checkShrinkedLocationReference(locRefData, point2, point3);
    }


    /**
     * Checks reduction of a locref with 4 points and the POI line in the middle.
     * 1======2--------3
     * Should result in:
     * 1======2
     */
    @Test
    public void testFitForLrpEndOfFirstLine() throws OpenLRProcessingException {

        final Location loc = mockNewLocation(0);

        // the POI line is the last one
        mockery.checking(new Expectations() {
            {
                allowing(loc).getPoiLine();
                will(returnValue(line1));
            }
        });


        LocRefData locRefData = new LocRefData(loc);
        LocRefPoint point1 = new LocRefPoint(asList(line1), encProperties);
        LocRefPoint point2 = new LocRefPoint(asList(line2), encProperties);
        // this is the expansion defined by the second LRP
        locRefData.setExpansion(new ExpansionData(Collections.<Line>emptyList(), asList(line2)));

        LocRefPoint last = new LocRefPoint(line2, encProperties);
        point1.setNextLRP(point2);
        point2.setNextLRP(last);

        locRefData.setLocRefPoints(asList(point1, point2, last));

        locRefAdjust.adjustLocationReference(encProperties, locRefData);

        checkShrinkedLocationReference(locRefData, point1, point2);
    }

    /**
     * Checks reduction of a locref with 4 points and the POI line in the middle.
     * 2----------3======4
     * Should result in:
     * 3======4
     */
    @Test
    public void testFitForLrpEndOfLastLine() throws OpenLRProcessingException {

        final Location loc = mockNewLocation(0);

        // the POI line is the last one
        mockery.checking(new Expectations() {
            {
                allowing(loc).getPoiLine();
                will(returnValue(line3));
            }
        });


        LocRefData locRefData = new LocRefData(loc);
        LocRefPoint point1 = new LocRefPoint(asList(line2), encProperties);
        // this is the expansion defined by the first LRP
        locRefData.setExpansion(new ExpansionData(asList(line2), Collections.<Line>emptyList()));

        LocRefPoint point2 = new LocRefPoint(asList(line3), encProperties);
        LocRefPoint last = new LocRefPoint(line3, encProperties);
        point1.setNextLRP(point2);
        point2.setNextLRP(last);
        locRefData.setLocRefPoints(asList(point1, point2, last));

        locRefAdjust.adjustLocationReference(encProperties, locRefData);

        checkShrinkedLocationReference(locRefData, point2, last);
    }

    /**
     * Tests a case where an intermediate LRP on a line is involved.
     * POI line is the last line of the two involved
     * 1---(IM)---3======4
     * Should result in:
     * 3======4
     */
    @Test
    public void testFitForIntermediateLrpOnLine() throws OpenLRProcessingException {

        final Location loc = mockNewLocation(0);

        // the POI line is the last one
        mockery.checking(new Expectations() {
            {
                allowing(loc).getPoiLine();
                will(returnValue(line3));
            }
        });


        LocRefData locRefData = new LocRefData(loc);
        LocRefPoint point1 = new LocRefPoint(asList(line2), encProperties);
        // this is the expansion defined by the first LRP
        locRefData.setExpansion(new ExpansionData(asList(line2), Collections.<Line>emptyList()));

        // this is special, it's an LRP in the middle of the line
        LocRefPoint point2 = new LocRefPoint(line2,
                MIDDLE_OF_LINE2.getLongitudeDeg(),
                MIDDLE_OF_LINE2.getLatitudeDeg(), encProperties, false);
        LocRefPoint point3 = new LocRefPoint(asList(line3), encProperties);
        LocRefPoint last = new LocRefPoint(line3, encProperties);
        point1.setNextLRP(point2);
        point2.setNextLRP(point3);
        point3.setNextLRP(last);

        locRefData.setLocRefPoints(asList(point1, point2, point3, last));

        locRefAdjust.adjustLocationReference(encProperties, locRefData);

        checkShrinkedLocationReference(locRefData, point3, last);
    }

    /**
     * Tests a case where the last is on the line instead of at a node.
     * POI line is the last line of the two involved, LRP 2 and 3 are on a node.
     * 2---------3======(IM)=====4
     * Should result in:
     * 3======(IM)
     */
    @Test
    public final void testFitForLastLrpOnLine() throws OpenLRProcessingException {

        final Location loc = mockNewLocation(0);

        // the POI line is the last one
        mockery.checking(new Expectations() {
            {
                allowing(loc).getPoiLine();
                will(returnValue(line3));
            }
        });


        LocRefData locRefData = new LocRefData(loc);
        LocRefPoint point1 = new LocRefPoint(asList(line2), encProperties);
        // this is the expansion defined by the first LRP
        locRefData.setExpansion(new ExpansionData(asList(line2), Collections.<Line>emptyList()));

        LocRefPoint point2 = new LocRefPoint(asList(line3), encProperties);
        // this is special, it's an LRP in the middle of the line
        LocRefPoint point3 = new LocRefPoint(line3,
                MIDDLE_OF_LINE3.getLongitudeDeg(),
                MIDDLE_OF_LINE3.getLatitudeDeg(), encProperties, false);
        LocRefPoint last = new LocRefPoint(line3, encProperties);
        point1.setNextLRP(point2);
        point2.setNextLRP(point3);
        point3.setNextLRP(last);

        locRefData.setLocRefPoints(asList(point1, point2, point3, last));

        locRefAdjust.adjustLocationReference(encProperties, locRefData);

        checkShrinkedLocationReference(locRefData, point2, point3);
    }

    /**
     * Checks if the given loc ref data meets the expected shrinked Location
     * Reference.
     *
     * @param locRefData
     *            The loc ref data to check
     * @param expectedLrp1
     *            The expected LRP to be LRP #1 in the list
     * @param expectedLrp2
     *            The expected LRP to be LRP #2 in the list
     */
    private void checkShrinkedLocationReference(final LocRefData locRefData,
                                                final LocRefPoint expectedLrp1, final LocRefPoint expectedLrp2) {

        // should be shrinked to two LRPs
        List<LocRefPoint> locRefPoints = locRefData.getLocRefPoints();
        assertEquals(locRefPoints.size(), 2);
        // ... and without expansion
        ExpansionData expansionData = locRefData.getExpansionData();
        assertEquals(expansionData.getExpansionStart().size(), 0);
        assertEquals(expansionData.getExpansionEnd().size(), 0);

        LocRefPoint newFirstLrp = locRefPoints.get(0);
        assertEquals(newFirstLrp.getLongitudeDeg(), expectedLrp1.getLongitudeDeg(), DOUBLE_DELTA_ALLOWED);
        assertEquals(newFirstLrp.getLatitudeDeg(), expectedLrp1.getLatitudeDeg(), DOUBLE_DELTA_ALLOWED);
        LocRefPoint newLastLrp = locRefPoints.get(1);
        assertEquals(newLastLrp.getLongitudeDeg(), expectedLrp2.getLongitudeDeg(), DOUBLE_DELTA_ALLOWED);
        assertEquals(newLastLrp.getLatitudeDeg(), expectedLrp2.getLatitudeDeg(), DOUBLE_DELTA_ALLOWED);
    }


    private final Location mockNewLocation(final int posOffset) {

        final Location loc = mockery.mock(Location.class, "uniquename" + System.nanoTime());
        mockery.checking(new Expectations() {
            {
                allowing(loc).getLocationType();
                will(returnValue(LocationType.POINT_ALONG_LINE));
            }
        });
        mockery.checking(new Expectations() {
            {
                allowing(loc).getPositiveOffset();
                will(returnValue(posOffset));
            }
        });
        mockery.checking(new Expectations() {
            {
                allowing(loc).getNegativeOffset();
                will(returnValue(0));
            }
        });
        return loc;
    }


    private final Line mockLine(final Node startNode, final Node endNode,
                                final int length, final long id) {

        final Line line = mockery.mock(Line.class, startNode + "-" + endNode);
        mockery.checking(new Expectations() {
            {
                allowing(line).getID();
                will(returnValue(id));
            }
        });
        mockery.checking(new Expectations() {
            {
                allowing(line).getStartNode();
                will(returnValue(startNode));
            }
        });
        mockery.checking(new Expectations() {
            {
                allowing(line).getEndNode();
                will(returnValue(endNode));
            }
        });
        mockery.checking(new Expectations() {
            {
                allowing(line).getLineLength();
                will(returnValue(length));
            }
        });

        mockery.checking(new Expectations() {
            {
                allowing(line).getGeoCoordinateAlongLine(0);
                will(returnValue(line.getStartNode().getGeoCoordinates()));
            }
        });

        mockery.checking(new Expectations() {
            {
                allowing(line).getGeoCoordinateAlongLine(line.getLineLength());
                will(returnValue(line.getEndNode().getGeoCoordinates()));
            }
        });


        return line;
    }

    private final Node mockNode(final String mockID, final long id, final double lon, final double lat) {
        final Node node = mockery.mock(Node.class, mockID);
        mockery.checking(new Expectations() {
            {
                allowing(node).getID();
                will(returnValue(id));
            }
        });
        mockery.checking(new Expectations() {
            {
                allowing(node).getGeoCoordinates();
                will(returnValue(GeoCoordinatesImpl.newGeoCoordinatesUnchecked(
                        lon, lat)));
            }
        });
        mockery.checking(new Expectations() {
            {
                allowing(node).getLongitudeDeg();
                will(returnValue(lon));
            }
        });
        mockery.checking(new Expectations() {
            {
                allowing(node).getLatitudeDeg();
                will(returnValue(lat));
            }
        });
        return node;
    }

    /**
     * [N1]---L1----[N2]----L2----[N3]----L3---[N4]
     */
    private final Line[] mockMap() {
        final Node node1 = mockNode("1", 1, 1, 1);
        final Node node2 = mockNode("2", 2, 1, 2);
        final Node node3 = mockNode("3", 3, 1, 3);
        final Node node4 = mockNode("4", 4, 1, 4);

        final Line line1 = mockLine(node1, node2, 200, 1);
        final Line line2 = mockLine(node2, node3, 200, 2);
        final Line line3 = mockLine(node3, node4, 200, 3);

        mockConnectivity(node1, asList(line1));
        mockConnectivity(node2, asList(line1, line2));
        mockConnectivity(node3, asList(line2, line3));
        mockConnectivity(node4, asList(line3));

        mockery.checking(new Expectations() {
            {
                allowing(line1).getGeoCoordinateAlongLine(20);
                will(returnValue(line1.getStartNode().getGeoCoordinates())); // simplified
            }
        });

        mockery.checking(new Expectations() {
            {
                allowing(line1).getGeoCoordinateAlongLine(180);
                will(returnValue(line1.getEndNode().getGeoCoordinates())); // simplified
            }
        });
        mockery.checking(new Expectations() {
            {
                allowing(line2).getGeoCoordinateAlongLine(20);
                will(returnValue(line2.getStartNode().getGeoCoordinates())); // simplified
            }
        });
        mockery.checking(new Expectations() {
            {
                allowing(line3).getGeoCoordinateAlongLine(20);
                will(returnValue(line3.getStartNode().getGeoCoordinates())); // simplified
            }
        });
        mockery.checking(new Expectations() {
            {
                allowing(line3).getGeoCoordinateAlongLine(180);
                will(returnValue(line3.getEndNode().getGeoCoordinates())); // simplified
            }
        });
        mockery.checking(new Expectations() {
            {
                allowing(line2).getGeoCoordinateAlongLine(180);
                will(returnValue(line2.getEndNode().getGeoCoordinates())); // simplified
            }
        });

        mockery.checking(new Expectations() {
            {
                allowing(line2).measureAlongLine(MIDDLE_OF_LINE2.getLongitudeDeg(), MIDDLE_OF_LINE2.getLatitudeDeg());
                will(returnValue(line2.getLineLength() / 2)); // simplified
            }
        });

        mockery.checking(new Expectations() {
            {
                allowing(line2).getGeoCoordinateAlongLine(100);
                will(returnValue(MIDDLE_OF_LINE3));
            }
        });

        mockery.checking(new Expectations() {
            {
                allowing(line2).getGeoCoordinateAlongLine(120);
                will(returnValue(MIDDLE_OF_LINE3)); // simplified
            }
        });

        mockery.checking(new Expectations() {
            {
                allowing(line3).measureAlongLine(MIDDLE_OF_LINE3.getLongitudeDeg(), MIDDLE_OF_LINE3.getLatitudeDeg());
                will(returnValue(line3.getLineLength() / 2)); // simplified
            }
        });
        mockery.checking(new Expectations() {
            {
                allowing(line3).getGeoCoordinateAlongLine(80);
                will(returnValue(MIDDLE_OF_LINE3));  // simplified
            }
        });
        mockery.checking(new Expectations() {
            {
                allowing(line3).getGeoCoordinateAlongLine(100);
                will(returnValue(MIDDLE_OF_LINE3));
            }
        });
        mockery.checking(new Expectations() {
            {
                allowing(line3).getGeoCoordinateAlongLine(120);
                will(returnValue(MIDDLE_OF_LINE3));  // simplified
            }
        });

        return new Line[]{line1, line2, line3};
    }

    private void mockConnectivity(final Node mockedNode, final Collection<Line> connectedLines) {
        mockery.checking(new Expectations() {
            {
                allowing(mockedNode).getNumberConnectedLines();
                will(returnValue(connectedLines.size()));
            }
        });
        mockery.checking(new Expectations() {
            {
                allowing(mockedNode).getConnectedLines();
                will(returnIterator(connectedLines));
            }
        });
    }

//
//    /**
//     * A sub class of {@link LocationReferenceAdjust}. It is our goal to test some protected methods in the parent class.
//     */
//    private static final class MyLocRefAdjust extends LocationReferenceAdjust {
//
//        @Override
//        public void adjustLocationReference(final OpenLREncoderProperties properties,
//                final LocRefData locRefData) throws OpenLRProcessingException {
//
//            fit4PointLocation(locRefData, properties);
//        }
//
//    }

}
