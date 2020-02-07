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
        List<GraphNode<T, L>> graphNodeListWithOutDeadEndNodes = graphNodeList.stream().filter((graphNode) -> {
            return !graphNode.isDeadEnd() || isLast;
        }).filter((graphNode) -> {
            return !graphNode.isUnreachable() || graphNode.getIncomingEdge().size() == 0;
        }).collect(Collectors.toList());
        if (graphNodeListWithOutDeadEndNodes.isEmpty()) {
            return null;
        } else {
            if (index >= graphNodeList.size() - 1) {
                index = 0;
            } else {
                ++index;
            }

            GraphNode nextNode;
            for (nextNode = graphNodeList.get(index); !graphNodeListWithOutDeadEndNodes.contains(nextNode); nextNode = graphNodeList.get(index)) {
                ++index;
                if (index > graphNodeList.size() - 1) {
                    index = 0;
                }
            }
            return nextNode;
        }
    }

    public List<GraphNode<T,L>> getNodes() {
        return graphNodeList;

    }

    public int getNodePosition(GraphNode<T,L> node) {
        return graphNodeList.indexOf(node);
    }
}
