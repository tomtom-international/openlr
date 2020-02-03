package openlr.decoder.backtracking;

import openlr.map.Line;

import java.util.ArrayList;
import java.util.List;

public class GraphNode<T> {
    public List<GraphEdge<T>> getOutgoingEdge() {
        return outgoingEdge;
    }

    public List<GraphEdge<T>> getIncomingEdge() {
        return incomingEdge;
    }

    private List<GraphEdge<T>> outgoingEdge = new ArrayList<>();
    private List<GraphEdge<T>> incomingEdge = new ArrayList<>();
    private final T element;

    public GraphNode(T element) {
        this.element = element;
    }

    public boolean isDeadEnd() {
        return outgoingEdge.stream().filter(edge -> edge.getEdgeColor().equals(EntityColor.RED)).count() == outgoingEdge.size();
    }

    public boolean hasUnmarkedEdges() {
        return outgoingEdge.stream().filter(edge -> edge.getEdgeColor().equals(EntityColor.WHITE)).count() != 0;
    }

    public boolean isUnreachable() {
        return incomingEdge.stream().filter(edge -> edge.getEdgeColor().equals(EntityColor.RED)).count() == incomingEdge.size();
    }

    public void addOutgoingEdge(GraphEdge<T> graphEdge) {
        outgoingEdge.add(graphEdge);
    }

    public void addIncomingEdge(GraphEdge<T> graphEdge) {
        incomingEdge.add(graphEdge);
    }
}
