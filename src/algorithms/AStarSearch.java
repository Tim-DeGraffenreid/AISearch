package algorithms;

import graph.Graph;
import graph.Node;
import graph.Edge;
import utils.FileParser;
import java.util.*;

public class AStarSearch {
    private Graph graph;
    private String startNode;
    private String goalNode;
    private List<Node> open; 
    private Set<Node> closed; 
    private Map<String, Double> costSoFar; 
    private Map<String, String> cameFrom;
    private Map<String, Node> allNodes;

    public AStarSearch(Graph graph, String startNode, String goalNode) {
        this.graph = graph;
        this.startNode = startNode;
        this.goalNode = goalNode;
        this.open = new ArrayList<>();
        this.closed = new HashSet<>();
        this.costSoFar = new HashMap<>();
        this.cameFrom = new HashMap<>();
        
        this.allNodes = graph.getNodesMap();
        Node start = allNodes.get(startNode);
        costSoFar.put(startNode, 0.0); 
        open.add(start);
    }

    public void search() {
        long startTime = System.currentTimeMillis();
        long timeOut = 10000; 

        while (!open.isEmpty()) {
            if (System.currentTimeMillis() - startTime > timeOut) {
                System.out.println("Search timed out.");
                return;
            }

            Node current = open.remove(0);

            if (current.getName().equals(goalNode)) {
                System.out.printf("Goal node found in %d ms\n", System.currentTimeMillis() - startTime );
                reconstructPath(current.getName());
                return;
            }

            closed.add(current);
            for (Edge edge : graph.getEdges(current.getName())) {
                Node successor = edge.getTo();
                
                double successorCurrentCost = costSoFar.get(current.getName()) + edge.getCost();
                //System.out.printf("From %s to %s is %.2f for a total of %.2f, then to goal %.2f\n", current.getName(), successor.getName(), edge.getCost(), successorCurrentCost, heuristic(successor, goal));
                if (!costSoFar.containsKey(successor.getName()) || successorCurrentCost < costSoFar.get(successor.getName())) {
                    costSoFar.put(successor.getName(), successorCurrentCost);
                    cameFrom.put(successor.getName(), current.getName());
                    if (!open.contains(successor) && !closed.contains(successor)) {
                        open.add(successor);
                    } else if (closed.contains(successor)) {
                        closed.remove(successor);
                        open.add(successor);
                    }
                }
            }
            // Re-sort OPEN list by f(node) = g(node) + h(node) after updates
            open.sort(Comparator.comparingDouble(node -> costSoFar.get(node.getName()) + heuristic(node, allNodes.get(goalNode))));
        }

        System.out.println("Goal node not found.");
    }

    private void reconstructPath(String current) {
        LinkedList<String> formattedPath = new LinkedList<>();
        double totalDistance = 0.0; // Total distance in kilometers
        String child = current;

        while (child != null) {
            Node node = allNodes.get(child);
            String parent = cameFrom.get(child);
            double distanceKm = 0.0; 
            
            if (parent != null) {
                Node parentNode = allNodes.get(parent);
                Edge edge = findEdgeBetweenNodes(parentNode, node);
                if (edge != null) {
                    distanceKm = edge.getCost(); 
                    totalDistance += distanceKm;
                }
                formattedPath.addFirst(String.format("%s (%.2f miles)", node.getName(), distanceKm * 0.621371)); 
            } else {
                formattedPath.addFirst(String.format("%s (0.0 miles)", node.getName())); 
            }

            child = parent;
        }
        System.out.println(String.format("A* Search total distance to goal from %s to %s: %.2f miles" , this.startNode, this.goalNode, totalDistance * 0.621371));
        System.out.println("Path to goal: " + String.join(" -> ", formattedPath));
        
    }
    
    private Edge findEdgeBetweenNodes(Node fromNode, Node toNode) {
        List<Edge> edges = graph.getEdges(fromNode.getName());
        for (Edge edge : edges) {
            if (edge.getTo().equals(toNode)) {
                return edge;
            }
        }
        return null; 
    }

    private double heuristic(Node a, Node b) {
        
        double dLat = Math.toRadians(b.getLatitude() - a.getLatitude());
        double dLon = Math.toRadians(b.getLongitude() - a.getLongitude());
        double lat1 = Math.toRadians(a.getLatitude());
        double lat2 = Math.toRadians(b.getLatitude());
        double r = 6371; // Earth radius in kilometers
        double a_hav = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(lat1) * Math.cos(lat2) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a_hav), Math.sqrt(1 - a_hav));
        return r * c;
    }
}
