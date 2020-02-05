package openlr.decoder.backtracking;

import java.util.List;

public class GraphEdge<T,L> {

    private EntityColor edgeColor;
    private List<L> value;
    private GraphNode<T,L> sourceNode;
    private GraphNode<T,L> destinationNode;
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

    public List<L> getValue() {
        return value;
    }

    public void setValue(List<L> value) {
        this.value = value;
    }

    public GraphNode<T,L> getSourceNode() {
        return sourceNode;
    }

    public GraphNode<T,L> getDestinationNode() {
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
