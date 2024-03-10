 package graph;
import java.util.*;

public class Graph {
    private Map<String, Node> nodes = new HashMap<>();
    private Map<Node, List<Edge>> adjList = new HashMap<>();

    public Node addNode(String name, double lat, double lon) {
        Node node = new Node(name, lat, lon);
        nodes.put(name, node);
        adjList.put(node, new ArrayList<>());
        return node;
    }

    public void addEdge(String from, String to, double cost) {
        Node fromNode = nodes.get(from);
        Node toNode = nodes.get(to);
        
        
        double heuresticCost = heuristic(fromNode, toNode);
        //System.out.println(from + " to " + to + " cost " + heuresticCost);
        // Check if the edge already exists
        boolean edgeExists = adjList.get(fromNode).stream().anyMatch(e -> e.getTo().equals(toNode));
        if (!edgeExists) {
            Edge edge = new Edge(fromNode, toNode, heuresticCost);
            adjList.get(fromNode).add(edge);
        }

        // Check if the reverse edge already exists
        boolean reverseEdgeExists = adjList.get(toNode).stream().anyMatch(e -> e.getTo().equals(fromNode));
        if (!reverseEdgeExists) {
            Edge reverseEdge = new Edge(toNode, fromNode, heuresticCost);
            adjList.get(toNode).add(reverseEdge);
        }
    }
    
    private double heuristic(Node a, Node b) {
        // Simplified for example; replace with actual heuristic function
        double dLat = Math.toRadians(b.getLatitude() - a.getLatitude());
        double dLon = Math.toRadians(b.getLongitude() - a.getLongitude());
        double lat1 = Math.toRadians(a.getLatitude());
        double lat2 = Math.toRadians(b.getLatitude());
        double r = 6371; // Earth radius in kilometers
        double a_hav = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(lat1) * Math.cos(lat2) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a_hav), Math.sqrt(1 - a_hav));
        return r * c;
    }

    public List<Edge> getEdges(String nodeName) {
        return adjList.get(nodes.get(nodeName));
    }
    
    public double getEdgeCost(String from, String to) {
        Node fromNode = nodes.get(from);
        if (fromNode == null) {
            throw new IllegalArgumentException("Node '" + from + "' not found in graph.");
        }
        
        List<Edge> edges = adjList.get(fromNode);
        if (edges != null) {
            for (Edge edge : edges) {
                if (edge.getTo().getName().equals(to)) {
                    return edge.getCost();
                }
            }
        }
        throw new IllegalArgumentException("Edge from '" + from + "' to '" + to + "' not found in graph.");
    }
    

    public Node getNode(String name) {
        return nodes.get(name);
    }

    public Collection<Node> getNodes() {
        return nodes.values();
    }
    
    public Map<String, Node> getNodesMap() {
        return new HashMap<>(this.nodes);
    }

}