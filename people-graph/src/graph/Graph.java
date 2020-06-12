package graph;

import java.util.*;

/**
 * Created by Chirag Patel
 * User: chiragpatel
 * Date: 5/17/15
 * Time: 7:30 AM
 */
public abstract class Graph<T extends Node> {
    private final Set<T> nodes;

    public Graph(Set<T> nodes) {
        this.nodes = new HashSet<>(nodes);
    }

    public void traverseGraph(NodeVisitor visitor) {
        Stack<Node> vistited = new Stack<>();
        for (Node node : nodes) {
            traversGraphDepthFirst(node, vistited, visitor);
        }
    }
    private void traversGraphDepthFirst(Node node, Stack<Node> visited, NodeVisitor visitor) {
        if(visitor.done()) {
            return;
        }
        visitor.visit(node);
        if(visited.contains(node)){
            return;
        }
        visited.push(node);
        Set<Node> children = node.children();
        for (Node child : children) {
                traversGraphDepthFirst(child, visited, visitor);
        }
        visited.pop();

    }

    public int connected(Node start, Node end) {
        Stack<Node> currentPath = new Stack<>();
        ShortestPathLengthVisitor shortestPathLengthVisitor = new ShortestPathLengthVisitor(currentPath, end);
        traversGraphDepthFirst(start, currentPath, shortestPathLengthVisitor);
        return shortestPathLengthVisitor.getMinPathLength();
    }

    public Map<Node, List<Node>> findConnections(Node node, int depth) {
        Stack<Node> currentPath = new Stack<>();
        ConnectionsFinder connectionsFinder = new ConnectionsFinder(currentPath, depth);
        traversGraphDepthFirst(node, currentPath, connectionsFinder);
        return connectionsFinder.getConnectionsMap();
    }

    public boolean hasCycle() {
        Stack<Node> visited = new Stack<>();
        CycleDetector detector = new CycleDetector(visited);
        for (Node node : nodes) {
            traversGraphDepthFirst(node, visited, detector);
        }
        return detector.hasCycle;
    }

    public int nodeCount() {
       NodeCountingVisitor visitor = new NodeCountingVisitor();
       traverseGraph(visitor);
       return visitor.count;
    }

    public int getMinimumNodeConnections() {
        MinConnectionsVisitor visitor = new MinConnectionsVisitor();
        traverseGraph(visitor);
        return visitor.getMinimumNodeConnections();
    }

    private class MinConnectionsVisitor implements NodeVisitor {
        private int minNodeConnections = -1;
        @Override
        public void visit(Node node) {
            if(minNodeConnections == -1) {
                minNodeConnections = node.children().size();
            } else if(node.children().size() < minNodeConnections) {
                minNodeConnections = node.children().size();
            }
        }
        public int getMinimumNodeConnections() {
            return minNodeConnections;
        }

        public boolean done() {
            return false;
        }
    }


    private class CycleDetector implements NodeVisitor {
        private boolean hasCycle;
        private final Stack<Node> visited;

        private CycleDetector(Stack<Node> visited) {
            this.visited = visited;
        }

        @Override
        public void visit(Node node) {
            Set<Node> children = node.children();
            for (Node child : children) {
                if(visited.contains(child)) {
                    hasCycle = true;
                }
            }
        }
        @Override
        public boolean done() {
            return hasCycle;
        }

    }

    private class NodeCountingVisitor implements NodeVisitor {
        private int count;

        @Override
        public void visit(Node node) {
            count++;
        }
        @Override
        public boolean done() {
            return false;
        }
    }

    private class ShortestPathLengthVisitor implements NodeVisitor {
        private int minPathLength = -1;
        private Node searchNode;
        private Stack<Node> currentPath;
        private ShortestPathLengthVisitor(Stack<Node> currentPath, Node searchNode) {
            this.searchNode = searchNode;
            this.currentPath = currentPath;
        }
        @Override
        public void visit(Node node) {
            if(node.equals(searchNode)) {
                if(minPathLength == -1) {
                    minPathLength = currentPath.size();
                } else if(currentPath.size() < minPathLength) {
                    minPathLength = currentPath.size();
                }
            }
        }

        @Override
        public boolean done() {
            return false;
        }

        private int getMinPathLength() {
            return minPathLength;
        }
    }

    private class ConnectionsFinder implements NodeVisitor {
        private Map<Node, List<Node>> connectionsMap = new HashMap<>();
        private Stack<Node> currentPath;
        private int depth;

        private ConnectionsFinder(Stack<Node> currentPath, int depth) {
            this.currentPath = currentPath;
            this.depth = depth;
        }
        @Override
        public void visit(Node node) {
            for(int i = 1 ;i <= depth; i++) {
                if(currentPath.size() - i >= 0) {
                    Node key = currentPath.get(currentPath.size() - i);
                    List<Node> connections = connectionsMap.get(key);
                    if (connections == null) {
                        connections = new ArrayList<>();
                        connectionsMap.put(key, connections);
                    }
                    if (!connections.contains(node)) {
                        connections.add(node);
                    }
                }
            }
        }

        @Override
        public boolean done() {
            return false;
        }

        private Map<Node, List<Node>> getConnectionsMap() {
            return connectionsMap;
        }
    }

}
