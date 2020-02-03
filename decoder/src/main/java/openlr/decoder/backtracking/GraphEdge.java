package openlr.decoder.backtracking;

import java.util.List;

public class GraphEdge<T> {

    private EntityColor edgeColor;
    private List<T> value;
    private GraphNode<T> sourceNode;
    private GraphNode<T> destinationNode;

    public EntityColor getEdgeColor() {
        return edgeColor;
    }

    public void setEdgeColor(EntityColor edgeColor) {
        this.edgeColor = edgeColor;
    }

    public List<T> getValue() {
        return value;
    }

    public void setValue(List<T> value) {
        this.value = value;
    }

    public GraphNode<T> getSourceNode() {
        return sourceNode;
    }

    public GraphNode<T> getDestinationNode() {
        return destinationNode;
    }

    public GraphEdge(GraphNode sourceNode, GraphNode destinationNode) {
        this.sourceNode = sourceNode;
        this.destinationNode = destinationNode;
        this.edgeColor = EntityColor.WHITE;
    }
}
