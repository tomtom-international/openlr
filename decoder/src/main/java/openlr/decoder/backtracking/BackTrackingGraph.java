package openlr.decoder.backtracking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BackTrackingGraph<T> {
    private final List<Slice<T>> slices;
    private final Map<String, GraphEdge> edges;
    private int currentSourceSliceIndex = -1;
    private GraphEdge<T> graphEdge = null;
    private GraphEdge<T> lastSuccessfulEdge = null;

    public BackTrackingGraph(List<Slice<T>> slices) {
        this.slices = slices;
        this.edges = new HashMap<>();
    }

    private int findNextNodeIndexIfCurrentIsRed(Slice sourceSlice) {
        if (graphEdge.getSourceNode().isDeadEnd() || !graphEdge.getSourceNode().hasUnmarkedEdges()) {
            return sourceSlice.getNodePosition(sourceSlice.getNextBestNode());
        } else {
            return sourceSlice.getNodePosition(graphEdge.getSourceNode());
        }
    }

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

    public boolean isBacktrackingRequired(int currentSliceIndex, GraphEdge<T> graphEdge) {
        int sliceIndex = currentSliceIndex;
        for (GraphEdge<T> edge = graphEdge; edge != null; --sliceIndex) {
            edge = edge.getSourceNode().getIncomingEdge()
                    .stream()
                    .filter(incomingEdge -> incomingEdge.getEdgeColor().equals(EntityColor.GREEN)).findFirst().orElse(null);
        }
        return (sliceIndex != -1);

    }


    public boolean isBacktrackingRequired() {
        return isBacktrackingRequired(currentSourceSliceIndex, graphEdge);
    }


    public boolean isForwardingRequired() {
        return (graphEdge.getDestinationNode().getOutgoingEdge()
                .stream()
                .filter(outgoingEdge -> outgoingEdge.getEdgeColor().equals(EntityColor.GREEN)).count() != 0);
    }


    public GraphEdge getNextEdgeFromNormalFlow() {
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

    private GraphEdge getEdgeByBackwardTracking() {

        GraphEdge<T> edge = graphEdge;
        GraphEdge<T> parent = null;
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


    private GraphEdge<T> getEdgeByForwardTracking() {
        GraphEdge<T> edge = graphEdge;
        GraphEdge<T> successor = null;
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


    private GraphEdge<T> edgeFormGraph() {
        if (graphEdge == null) {
            ++currentSourceSliceIndex;
            return getNextEdgeFromNormalFlow();
        } else if (graphEdge.getEdgeColor().equals(EntityColor.GREEN) && isBacktrackingRequired()) {
            return getEdgeByBackwardTracking();
        } else if (graphEdge.getEdgeColor().equals(EntityColor.GREEN) && isForwardingRequired()) {
            return getEdgeByForwardTracking();
        } else if (graphEdge.getEdgeColor().equals(EntityColor.GREEN)) {
            ++currentSourceSliceIndex;
            return getNextEdgeFromNormalFlow();
        } else {
            return getNextEdgeFromNormalFlow();
        }
    }

    public boolean isComplete() {
        return (currentSourceSliceIndex == slices.size() - 1)
                && lastSuccessfulEdge != null
                && lastSuccessfulEdge.getEdgeColor().equals(EntityColor.GREEN)
                && !isBacktrackingRequired(currentSourceSliceIndex -1, lastSuccessfulEdge);
    }


    public GraphEdge getNextBestEdge() {
        graphEdge = edgeFormGraph();
        if (graphEdge != null) {
            lastSuccessfulEdge = graphEdge;
        }
        return graphEdge;
    }

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

    public List<List<T>> traceBack() {
        GraphEdge<T> edge = lastSuccessfulEdge;
        List<List<T>> traceSummary = new ArrayList<>();

        while (edge != null && edge.getEdgeColor().equals(EntityColor.GREEN)) {
            traceSummary.add(edge.getValue());
            edge = edge.getSourceNode().getIncomingEdge()
                    .stream()
                    .filter(incomingEdge -> incomingEdge.getEdgeColor().equals(EntityColor.GREEN)).findFirst().orElse(null);
        }
        return traceSummary;
    }


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
