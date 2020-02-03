package openlr.decoder.backtracking;

import openlr.map.Line;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;

public class CreateGraph {

    private GraphNode graphNode1;
    private GraphNode graphNode2;
    private GraphNode graphNode3;
    private GraphNode graphNode4;
    private GraphNode graphNode5;
    private GraphNode graphNode6;
    private GraphNode graphNode7;
    private GraphNode graphNode8;
    private BackTrackingGraph backTrackingGraph;

    @BeforeMethod
    public void setUp() {


        Line line1 =  mock(Line.class);
        Line line2 = mock(Line.class);
        Line line3 =  mock(Line.class);
        Line line4 = mock(Line.class);
        Line line5 =  mock(Line.class);
        Line line6 = mock(Line.class);
        Line line7 =  mock(Line.class);
        Line line8 = mock(Line.class);

        graphNode1 = new GraphNode(line1);
        graphNode2 = new GraphNode(line2);
        graphNode3 = new GraphNode(line3);
        graphNode4 = new GraphNode(line4);
        graphNode5 = new GraphNode(line5);
        graphNode6 = new GraphNode(line6);
        graphNode7 = new GraphNode(line7);
        graphNode8 = new GraphNode(line8);


        List<GraphNode> graphNodeList1 = Arrays.asList(graphNode1,graphNode2);
        Slice slice1 = new Slice(graphNodeList1,false);
        List<GraphNode> graphNodeList2 = Arrays.asList(graphNode3,graphNode4);
        Slice slice2 = new Slice(graphNodeList2,false);
        List<GraphNode> graphNodeList3 = Arrays.asList(graphNode5,graphNode6);
        Slice slice3 = new Slice(graphNodeList3,false);
        List<GraphNode> graphNodeList4 = Arrays.asList(graphNode7,graphNode8);
        Slice slice4 = new Slice(graphNodeList4, true);

        List<Slice> slices = Arrays.asList(slice1,slice2,slice3,slice4);

        backTrackingGraph = new BackTrackingGraph(slices);
        backTrackingGraph.generateEdges();
    }

    @Test
    public void createSlices() {
        Line line1 =  mock(Line.class);
        Line line2 = mock(Line.class);
        GraphNode graphNode1 = new GraphNode(line1);
        GraphNode graphNode2 = new GraphNode(line2);
        List<GraphNode> graphNodeList = Arrays.asList(graphNode1,graphNode2);
        Slice slice1 = new Slice(graphNodeList,true);

        GraphNode graphNode = slice1.getNextBestNode();
        Assert.assertEquals(graphNode1,graphNode);

        graphNode = slice1.getNextBestNode();
        Assert.assertEquals(graphNode2,graphNode);
    }

    private void verifyStartAndEndNodes(GraphEdge edge, GraphNode startNode, GraphNode endNode){
        Assert.assertEquals(startNode, edge.getSourceNode());
        Assert.assertEquals(endNode, edge.getDestinationNode());
    }


    @Test
    public void testCreateGraph() {

        Assert.assertFalse(backTrackingGraph.isComplete());

        GraphEdge edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode1,graphNode3);
        edge.setEdgeColor(EntityColor.GREEN);

        Assert.assertFalse(backTrackingGraph.isComplete());

        edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode3,graphNode5);
        edge.setEdgeColor(EntityColor.GREEN);

        Assert.assertFalse(backTrackingGraph.isComplete());

        edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode5,graphNode7);
        edge.setEdgeColor(EntityColor.GREEN);

        Assert.assertTrue(backTrackingGraph.isComplete());
    }


    @Test
    public void testCreateGraphWithRedEdgeBetweenFirstSliceAndSecondSlice() {

        Assert.assertFalse(backTrackingGraph.isComplete());

        GraphEdge edge = backTrackingGraph.getNextBestEdge();
        edge.setEdgeColor(EntityColor.RED);

        Assert.assertFalse(backTrackingGraph.isComplete());

        edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode1,graphNode4);
        edge.setEdgeColor(EntityColor.GREEN);

        Assert.assertFalse(backTrackingGraph.isComplete());

        edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode4,graphNode5);
        edge.setEdgeColor(EntityColor.GREEN);

        Assert.assertFalse(backTrackingGraph.isComplete());

        edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode5,graphNode7);
        edge.setEdgeColor(EntityColor.GREEN);

        Assert.assertTrue(backTrackingGraph.isComplete());
    }

    @Test
    public void testCreateGraphWithRedEdgeIncomingRedEdgeToLastSlice() {

        Assert.assertFalse(backTrackingGraph.isComplete());

        GraphEdge edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode1,graphNode3);
        edge.setEdgeColor(EntityColor.GREEN);

        Assert.assertFalse(backTrackingGraph.isComplete());

        edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode3,graphNode5);
        edge.setEdgeColor(EntityColor.GREEN);

        Assert.assertFalse(backTrackingGraph.isComplete());

        edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode5,graphNode7);
        edge.setEdgeColor(EntityColor.RED);

        Assert.assertFalse(backTrackingGraph.isComplete());

        edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode5,graphNode8);
        edge.setEdgeColor(EntityColor.GREEN);

        Assert.assertTrue(backTrackingGraph.isComplete());

    }


    @Test
    public void testCreateGraphWithRedEdgeBetweenMiddleSlice() {

        Assert.assertFalse(backTrackingGraph.isComplete());

        GraphEdge edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode1,graphNode3);
        edge.setEdgeColor(EntityColor.GREEN);

        Assert.assertFalse(backTrackingGraph.isComplete());

        edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode3,graphNode5);
        edge.setEdgeColor(EntityColor.RED);

        Assert.assertFalse(backTrackingGraph.isComplete());

        edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode3,graphNode6);
        edge.setEdgeColor(EntityColor.GREEN);

        Assert.assertFalse(backTrackingGraph.isComplete());

        edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode6,graphNode7);
        edge.setEdgeColor(EntityColor.GREEN);

        Assert.assertTrue(backTrackingGraph.isComplete());

    }

    @Test
    public void testBacktrackingFromTheLastSlice() {

        GraphEdge edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode1,graphNode3);
        edge.setEdgeColor(EntityColor.GREEN);

        edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode3,graphNode5);
        edge.setEdgeColor(EntityColor.GREEN);

        edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode5,graphNode7);
        edge.setEdgeColor(EntityColor.RED);

        edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode5,graphNode8);
        edge.setEdgeColor(EntityColor.RED);

        edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode6,graphNode7);
        edge.setEdgeColor(EntityColor.RED);

        edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode6,graphNode8);
        edge.setEdgeColor(EntityColor.GREEN);

        edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode3,graphNode6);
        edge.setEdgeColor(EntityColor.GREEN);

        edge = backTrackingGraph.getNextBestEdge();
        Assert.assertNull(edge);

        Assert.assertTrue(backTrackingGraph.isComplete());
    }

    @Test
    public void testGraphUnconnectedInTheMiddle() {

        GraphEdge edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode1,graphNode3);
        edge.setEdgeColor(EntityColor.GREEN);

        edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode3,graphNode5);
        edge.setEdgeColor(EntityColor.RED);

        edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode3,graphNode6);
        edge.setEdgeColor(EntityColor.RED);

        edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode4,graphNode5);
        edge.setEdgeColor(EntityColor.RED);

        edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode4,graphNode6);
        edge.setEdgeColor(EntityColor.RED);

        edge = backTrackingGraph.getNextBestEdge();

        Assert.assertNull(edge);

        Assert.assertFalse(backTrackingGraph.isComplete());

    }


    @Test
    public void testGraphWhichUnconnectedInTheFirstTwoSlices() {

        GraphEdge edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode1,graphNode3);
        edge.setEdgeColor(EntityColor.RED);

        edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode1,graphNode4);
        edge.setEdgeColor(EntityColor.RED);

        edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode2,graphNode3);
        edge.setEdgeColor(EntityColor.RED);

        edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode2,graphNode4);
        edge.setEdgeColor(EntityColor.RED);

        edge = backTrackingGraph.getNextBestEdge();

        Assert.assertNull(edge);

        Assert.assertFalse(backTrackingGraph.isComplete());

    }


    @Test
    public void testGraphWhichUnconnectedInTheLastTwoSlices() {

        GraphEdge edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode1,graphNode3);
        edge.setEdgeColor(EntityColor.GREEN);

        edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode3,graphNode5);
        edge.setEdgeColor(EntityColor.GREEN);

        edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode5,graphNode7);
        edge.setEdgeColor(EntityColor.RED);

        edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode5,graphNode8);
        edge.setEdgeColor(EntityColor.RED);

        edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode6,graphNode7);
        edge.setEdgeColor(EntityColor.RED);

        edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode6,graphNode8);
        edge.setEdgeColor(EntityColor.RED);

        edge = backTrackingGraph.getNextBestEdge();

        Assert.assertNull(edge);

        Assert.assertFalse(backTrackingGraph.isComplete());
    }


    @Test
    public void testBacktrackingInTheMiddleSlice() {

        GraphEdge edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode1,graphNode3);
        edge.setEdgeColor(EntityColor.GREEN);

        edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode3,graphNode5);
        edge.setEdgeColor(EntityColor.RED);

        edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode3,graphNode6);
        edge.setEdgeColor(EntityColor.RED);

        edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode4,graphNode5);
        edge.setEdgeColor(EntityColor.GREEN);

        edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode1,graphNode4);
        edge.setEdgeColor(EntityColor.RED);

        edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode2,graphNode4);
        edge.setEdgeColor(EntityColor.GREEN);

        edge = backTrackingGraph.getNextBestEdge();
        verifyStartAndEndNodes(edge,graphNode5,graphNode7);
        edge.setEdgeColor(EntityColor.GREEN);

        edge = backTrackingGraph.getNextBestEdge();

        Assert.assertNull(edge);

        Assert.assertFalse(backTrackingGraph.isComplete());
    }
}
