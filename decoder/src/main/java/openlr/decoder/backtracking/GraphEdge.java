package openlr.decoder.backtracking;

import java.util.List;

public class GraphEdge<T> {

    private EntityColor edgeColor;
    private List<T> value;
    private GraphNode<T> sourceNode;
    private GraphNode<T> destinationNode;
    private int sourceSliceIndex;
    private int destinationSliceIndex;

    public int getSourceSliceIndex() {
        return sourceSliceIndex;
    }

    public int getDestinationSliceIndex() {
        return destinationSliceIndex;
    }

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

    public GraphEdge(GraphNode sourceNode, GraphNode destinationNode, int sourceSliceIndex, int destinationSliceIndex) {
        this.sourceNode = sourceNode;
        this.destinationNode = destinationNode;
        this.sourceSliceIndex = sourceSliceIndex;
        this.destinationSliceIndex = destinationSliceIndex;
        this.edgeColor = EntityColor.WHITE;
    }
}
