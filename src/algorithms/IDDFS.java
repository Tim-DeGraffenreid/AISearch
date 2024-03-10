package algorithms;

import graph.Graph;
import graph.Edge;
import utils.FileParser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class IDDFS {

    Graph graph;
    String startNode;
    String goalNode;
    String goalPath = "";
    Map<String, String> pathMap = new HashMap<>();

    public IDDFS(Graph graph, String startNode, String goalNode) {
    	this.graph = graph;
        this.startNode = startNode;
        this.goalNode = goalNode;
    }

    public void search() {
        long startTime = System.currentTimeMillis();
        long timeOut = 10000; 
        
        for (int depth = 0; ; depth++) {
        	
        	if(System.currentTimeMillis() - startTime > timeOut) {
        		System.out.println("Search timed out.");
        		return;
        	}
        	
            Set<String> visited = new HashSet<>();
            boolean found = depthLimitedSearch(startNode, goalNode, depth, visited);
            if (found) {
                System.out.println("Goal node found: " + goalNode + " at depth: " + depth + " in " + (System.currentTimeMillis() - startTime) + " ms");
                goalPath(goalNode);
                return;
            }
        }
    }

    private boolean depthLimitedSearch(String currentNode, String goalNode, int limit, Set<String> visited) {
        if (currentNode.equals(goalNode)) {
            return true;
        }
        if (limit <= 0) {
            return false;
        }
        visited.add(currentNode);

        List<Edge> edges = graph.getEdges(currentNode);
        for (Edge edge : edges) {
            String newEdge = edge.getTo().getName();
            if (!visited.contains(newEdge)) {
                pathMap.put(newEdge, currentNode); // Track the parent of the new node
                boolean found = depthLimitedSearch(newEdge, goalNode, limit - 1, visited);
                if (found) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private void goalPath(String goalNode) {
        Stack<String> path = new Stack<>();
        String current = goalNode;
        String goalPath = "";
        double pathCost = 0.0;
        
        while (current != null) {
        	String from = current;
            
            current = pathMap.get(current);
            if(current != null) {
            	double cost = graph.getEdgeCost(from, current);
            	pathCost += cost;
            	path.push(String.format("%s (%.2f miles)", from, cost * 0.621371));
            	
            }else {
            	path.push(from + " (0.0 miles)");
            }
            
        }
        
        while (!path.isEmpty()) {
           goalPath += path.pop() + (path.size() > 0 ? " -> " : "");
        }
        
        this.goalPath = goalPath;
        
        System.out.println(String.format("ID-DFS total distance to goal from %s to %s: %.2f miles" , this.startNode, this.goalNode, pathCost * 0.621371));
        System.out.println("Path to goal: " + goalPath);
    }

}
