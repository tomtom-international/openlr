package openlr.decoder.backtracking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BackTrackingGraph<T,L> {
    private final List<Slice<T,L>> slices;
    private final Map<String, GraphEdge> edges;
    private int currentSourceSliceIndex = -1;
    private GraphEdge<T,L> graphEdge = null;
    private GraphEdge<T,L> lastSuccessfulEdge = null;

    public BackTrackingGraph(List<Slice<T,L>> slices) {
        this.slices = slices;
        this.edges = new HashMap<>();
    }

    /**
     * if the user has marked last edge returned by the graph as red;
     * then the next edge should be an unmarked edge with the same start node
     * But If all the edges starts from this start node is already marked
     * then the next edge can start from any node in the slice
     *
     * @param sourceSlice Slice from which next edge will start if all edges from the start node of previous edge is already marked
     * @return index of the node from which next edge starts
     */
    private int findNextNodeIndexIfCurrentIsRed(Slice sourceSlice) {
        if (graphEdge.getSourceNode().isDeadEnd() || !graphEdge.getSourceNode().hasUnmarkedEdges()) {
            return sourceSlice.getNodePosition(sourceSlice.getNextBestNode());
        } else {
            return sourceSlice.getNodePosition(graphEdge.getSourceNode());
        }
    }

    /**
     * Index of the start node of next best edge from the slice
     *
     * @param sourceSlice
     * @return index of the node from which next edge starts
     */
    private int getNextSourceNodeIndex(Slice sourceSlice) {
        if (graphEdge == null) {
            GraphNode sourceNode = sourceSlice.getNextBestNode();
            return sourceSlice.getNodePosition(sourceNode);
        } else if (graphEdge.getEdgeColor().equals(EntityColor.RED)) {
            return findNextNodeIndexIfCurrentIsRed(sourceSlice);
        } else {
            return sourceSlice.getNodePosition(graphEdge.getDestinationNode());
        }

    }

    /**
     * Backtracking is required if the green edges in the previous slices are not connected directly or indirectly to the given edge
     *
     * @param currentSliceIndex index of the slice from which the edge starts
     * @param graphEdge         edge of which the predecessors need to verified
     * @return
     */
    public boolean isBacktrackingRequired(int currentSliceIndex, GraphEdge<T,L> graphEdge) {
        int sliceIndex = currentSliceIndex;
        for (GraphEdge<T,L> edge = graphEdge; edge != null; --sliceIndex) {
            edge = edge.getSourceNode().getIncomingEdge()
                    .stream()
                    .filter(incomingEdge -> incomingEdge.getEdgeColor().equals(EntityColor.GREEN)).findFirst().orElse(null);
        }
        return (sliceIndex != -1);

    }

    /**
     * Backtracking is required if the green edges in the previous slices are not connected directly or indirectly to the last best edge
     */
    private boolean isBacktrackingRequired() {
        return isBacktrackingRequired(currentSourceSliceIndex, graphEdge);
    }


    /**
     * Forwardtracking is required if there are green successors to the current edge
     */
    private boolean isForwardTrackingRequired() {
        return (graphEdge.getDestinationNode().getOutgoingEdge()
                .stream()
                .filter(outgoingEdge -> outgoingEdge.getEdgeColor().equals(EntityColor.GREEN)).count() != 0);
    }


    /**
     * if no backtracking or forwardtracking is preformed, then next best edge is is estimated based on start or end node of the last best edge
     */
    private GraphEdge getNextEdgeFromNormalFlow() {
        if (currentSourceSliceIndex < slices.size() - 1) {
            int sourceSliceIndex = currentSourceSliceIndex;
            int destinationSliceIndex = currentSourceSliceIndex + 1;
            Slice sourceSlice = slices.get(sourceSliceIndex);
            Slice destinationSlice = slices.get(destinationSliceIndex);

            GraphNode destinationNode = destinationSlice.getNextBestNode();

            int sourceNodeIndex = getNextSourceNodeIndex(sourceSlice);
            int destinationNodeIndex = destinationSlice.getNodePosition(destinationNode);

            String edgeid = getEdgeId(sourceSliceIndex, sourceNodeIndex, destinationSliceIndex, destinationNodeIndex);
            graphEdge = edges.get(edgeid);
            return graphEdge;
        }
        return null;
    }

    /**
     * Backtrack the graph to identify where the connection is broken and return an unmarked edge with the end node same as the
     * start node of the last tracked edge. if all edges are already marked then it will try to find an edge from the source slice.
     */
    private GraphEdge getEdgeByBackwardTracking() {

        GraphEdge<T,L> edge = graphEdge;
        GraphEdge<T,L> parent = null;
        do {
            parent = edge.getSourceNode().getIncomingEdge()
                    .stream()
                    .filter(incomingEdge -> incomingEdge.getEdgeColor().equals(EntityColor.GREEN)).findFirst().orElse(null);
            if (parent != null) {
                edge = parent;
            }
            --currentSourceSliceIndex;
        } while (parent != null);

        if (edge.getSourceNode().getIncomingEdge()
                .stream().count() == 0) {
            return null;
        }

        edge = edge.getSourceNode().getIncomingEdge()
                .stream()
                .filter(incomingEdge -> !incomingEdge.getEdgeColor().equals(EntityColor.RED)).findFirst().orElse(null);


        if (edge == null) {
            return getNextEdgeFromNormalFlow();
        }

        return edge;
    }


    /**
     * if there are any green successor to the last edge then it will try to find the last green edge in the successor links
     * then return an unmarked edge starting from the end node of the final green edge, if there are no such unmarked edges then it will try
     * to find the best candidate from slice which contains the start node of the last edge in the chain
     */
    private GraphEdge<T,L> getEdgeByForwardTracking() {
        GraphEdge<T,L> edge = graphEdge;
        GraphEdge<T,L> successor = null;
        do {
            successor = (edge.getDestinationNode().getOutgoingEdge()
                    .stream()
                    .filter(outgoingEdge -> outgoingEdge.getEdgeColor().equals(EntityColor.GREEN)).findFirst().orElse(null));
            if (successor != null) {
                edge = successor;
                lastSuccessfulEdge = edge;
            }
            ++currentSourceSliceIndex;
        } while (successor != null);


        if (edge.getDestinationNode().getOutgoingEdge()
                .stream().count() == 0) {
            return null;
        }

        edge = edge.getDestinationNode().getOutgoingEdge()
                .stream()
                .filter(outgoingEdge -> !outgoingEdge.getEdgeColor().equals(EntityColor.RED)).findFirst().orElse(null);

        if (edge == null) {
            return getNextEdgeFromNormalFlow();
        }

        return edge;
    }

    /**
     * retrieve the next best edge based on the users opinion of the last best edge
     */
    private GraphEdge<T,L> edgeFormGraph() {
        if (graphEdge == null) {
            ++currentSourceSliceIndex;
            return getNextEdgeFromNormalFlow();
        } else if (graphEdge.getEdgeColor().equals(EntityColor.GREEN) && isBacktrackingRequired()) {
            return getEdgeByBackwardTracking();
        } else if (graphEdge.getEdgeColor().equals(EntityColor.GREEN) && isForwardTrackingRequired()) {
            return getEdgeByForwardTracking();
        } else if (graphEdge.getEdgeColor().equals(EntityColor.GREEN)) {
            ++currentSourceSliceIndex;
            return getNextEdgeFromNormalFlow();
        } else {
            return getNextEdgeFromNormalFlow();
        }
    }


    /**
     * Verifies there is a successful connection of edges between the source and last slice along the intermediate slice.
     */
    public boolean isComplete() {
        return (currentSourceSliceIndex == slices.size() - 1)
                && lastSuccessfulEdge != null
                && lastSuccessfulEdge.getEdgeColor().equals(EntityColor.GREEN)
                && !isBacktrackingRequired(currentSourceSliceIndex - 1, lastSuccessfulEdge);
    }


    /**
     * returns the next best edge in the graph, and the edge if such an edge actually exist it can be of any color
     */
    public GraphEdge getNextBestEdge() {
        graphEdge = edgeFormGraph();
        if (graphEdge != null) {
            lastSuccessfulEdge = graphEdge;
        }
        return graphEdge;
    }

    /**
     * Generate edges between all the nodes of the consecutive slices
     */
    public void generateEdges() {
        for (int index = 0; index < this.slices.size() - 1; ++index) {
            Slice sourceSlice = this.slices.get(index);
            Slice destinationSlice = this.slices.get(index + 1);
            generateEdges(sourceSlice, index, destinationSlice, index + 1);
        }
    }

    private static String getEdgeId(int sourceSliceIndex, int sourceNodeIndex, int destinationSliceIndex, int destinationNodeIndex) {
        return sourceSliceIndex + ":" + sourceNodeIndex + ":" + destinationSliceIndex + ":" + destinationNodeIndex;
    }

    /**
     * Get the successors of the last Successful edge
     */
    public List<List<L>> traceBack() {
        GraphEdge<T,L> edge = lastSuccessfulEdge;
        List<List<L>> traceSummary = new ArrayList<>();

        while (edge != null && edge.getEdgeColor().equals(EntityColor.GREEN)) {
            traceSummary.add(edge.getValue());
            edge = edge.getSourceNode().getIncomingEdge()
                    .stream()
                    .filter(incomingEdge -> incomingEdge.getEdgeColor().equals(EntityColor.GREEN)).findFirst().orElse(null);
        }
        return traceSummary;
    }


    /**
     * generate edges between the given slices
     */
    private void generateEdges(Slice sourceSlice, int sourceSliceIndex, Slice destinationSlice, int destinationSliceIndex) {

        List<GraphNode> sourceSliceNodes = sourceSlice.getNodes();
        List<GraphNode> destinationSliceNodes = destinationSlice.getNodes();
        for (int sourceNodeIndex = 0; sourceNodeIndex < sourceSliceNodes.size(); ++sourceNodeIndex) {
            for (int destinationNodeIndex = 0; destinationNodeIndex < destinationSliceNodes.size(); ++destinationNodeIndex) {
                String edgeId = getEdgeId(sourceSliceIndex, sourceNodeIndex, destinationSliceIndex, destinationNodeIndex);
                GraphNode sourceNode = sourceSliceNodes.get(sourceNodeIndex);
                GraphNode destinationNode = destinationSliceNodes.get(destinationNodeIndex);
                GraphEdge graphEdge = new GraphEdge(sourceNode, destinationNode, sourceSliceIndex, destinationSliceIndex);
                sourceNode.addOutgoingEdge(graphEdge);
                destinationNode.addIncomingEdge(graphEdge);
                this.edges.put(edgeId, graphEdge);
            }
        }
    }

}
