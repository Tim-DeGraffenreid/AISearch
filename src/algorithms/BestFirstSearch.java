package algorithms;

import graph.Graph;
import graph.Node;
import graph.Edge;
import utils.FileParser;
import java.util.*;

public class BestFirstSearch {
	private Graph graph;
	private String startNode;
	private String goalNode;
	private String goalPath = "";
	private List<Node> open; // Replaces frontier with an open list
	private Set<Node> closed; // Closed list
	private Map<String, String> cameFrom;
	private Map<String, Double> costSoFar; // Track the cost of the path for each node
	private Map<String, Node> allNodes;

	public BestFirstSearch(Graph graph, String startNode, String goalNode) {
		this.graph = graph;
		this.startNode = startNode;
		this.goalNode = goalNode;
		this.cameFrom = new HashMap<>();
		this.costSoFar = new HashMap<>();
		this.open = new ArrayList<>();
		this.closed = new HashSet<>();
		this.allNodes = graph.getNodesMap();
	}

    public void search() {
        Node start = allNodes.get(startNode);
        open.add(start);
        cameFrom.put(startNode, null);

        long startTime = System.currentTimeMillis();
        long timeOut = 10000;

        while (!open.isEmpty()) {

            if (System.currentTimeMillis() - startTime > timeOut) {
                System.out.println("Search timed out.");
                return;
            }

            open.sort(Comparator.comparingDouble(node -> heuristic(node, allNodes.get(goalNode))));
            Node current = open.remove(0);

            if (current.getName().equals(goalNode)) {
                System.out.printf("Goal node found in %d ms\n", System.currentTimeMillis() - startTime);
                reconstructPath(current.getName());
                return;
            }

            for (Edge edge : graph.getEdges(current.getName())) {
                Node child = edge.getTo();
                
                if (!closed.contains(child) && !open.contains(child)) {
                    open.add(child);
                    cameFrom.put(child.getName(), current.getName());
                }
            }

            closed.add(current);
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
			double distanceKm = 0.0; // Distance from parent to child in kilometers

			if (parent != null) {
				Node parentNode = allNodes.get(parent);
				Edge edge = findEdgeBetweenNodes(parentNode, node);
				if (edge != null) {
					distanceKm = edge.getCost(); // Assuming edge cost is in kilometers
					totalDistance += distanceKm;
				}
				formattedPath.addFirst(String.format("%s (%.2f miles)", node.getName(), distanceKm * 0.621371)); // Convert
																													// km
																													// to
																													// miles
			} else {
				formattedPath.addFirst(String.format("%s (0.0 miles)", node.getName())); // Starting node
			}

			child = parent;
		}

		System.out.println(String.format("Best First Search total distance to goal from %s to %s: %.2f miles",
				this.startNode, this.goalNode, totalDistance * 0.621371));
		System.out.println("Path to goal: " + String.join(" -> ", formattedPath));

	}

	private Edge findEdgeBetweenNodes(Node fromNode, Node toNode) {
		List<Edge> edges = graph.getEdges(fromNode.getName());
		for (Edge edge : edges) {
			if (edge.getTo().equals(toNode)) {
				return edge;
			}
		}
		return null; // Return null if no direct edge is found
	}

	public static double heuristic(Node a, Node b) {
		// Haversine formula for spherical Earth approximation
		double dLat = Math.toRadians(b.getLatitude() - a.getLatitude());
		double dLon = Math.toRadians(b.getLongitude() - a.getLongitude());
		double lat1 = Math.toRadians(a.getLatitude());
		double lat2 = Math.toRadians(b.getLatitude());

		double r = 6371; // Earth radius in kilometers
		double a_hav = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(lat1) * Math.cos(lat2) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
		double c = 2 * Math.atan2(Math.sqrt(a_hav), Math.sqrt(1 - a_hav));
		return r * c;
	}
}