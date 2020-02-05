package openlr.decoder.backtracking;

import java.util.List;
import java.util.stream.Collectors;

public class Slice<T,L> {
    private final List<GraphNode<T,L>> graphNodeList;
    private final boolean isLast;

    private int index = -1;

    public Slice(List<GraphNode<T,L>> graphNodeList, boolean isLast) {
        this.graphNodeList = graphNodeList;
        this.isLast = isLast;
    }

    public boolean isDeadEnd() {
        return graphNodeList.stream().filter(node -> node.isDeadEnd()).count() == graphNodeList.size() && !isLast;
    }

    public final GraphNode<T,L> getNextBestNode() {
        List<GraphNode<T,L>> graphNodeListWithOutDeadEndNodes = graphNodeList.stream()
                .filter(graphNode -> !graphNode.isDeadEnd() || isLast).collect(Collectors.toList());

        if (index > graphNodeListWithOutDeadEndNodes.size() - 1) {
            index = graphNodeListWithOutDeadEndNodes.size() - 1;
        }

        while (!graphNodeListWithOutDeadEndNodes.isEmpty()) {
            ++index;
            if (index < graphNodeListWithOutDeadEndNodes.size()) {
                return graphNodeListWithOutDeadEndNodes.get(index);
            } else {
                index = -1;
            }
        }

        return null;
    }

    public List<GraphNode<T,L>> getNodes() {
        return graphNodeList;

    }

    public int getNodePosition(GraphNode<T,L> node) {
        return graphNodeList.indexOf(node);
    }
}
