package openlr.decoder.backtracking;

import java.util.ArrayList;
import java.util.List;

public class GraphNode<T,L> {

    private final T nodeElement;
    private List<GraphEdge<T,L>> outgoingEdge = new ArrayList<>();
    private List<GraphEdge<T,L>> incomingEdge = new ArrayList<>();

    public T getNodeElement() {
        return nodeElement;
    }

    public List<GraphEdge<T,L>> getOutgoingEdge() {
        return outgoingEdge;
    }

    public List<GraphEdge<T,L>> getIncomingEdge() {
        return incomingEdge;
    }

    public GraphNode(T nodeElement) {
        this.nodeElement = nodeElement;
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

    public void addOutgoingEdge(GraphEdge<T,L> graphEdge) {
        outgoingEdge.add(graphEdge);
    }

    public void addIncomingEdge(GraphEdge<T,L> graphEdge) {
        incomingEdge.add(graphEdge);
    }
}
