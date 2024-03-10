package algorithms;

import graph.Graph;
import utils.FileParser;
import graph.Edge;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class BFS {
	
    Graph graph;
    Queue<String> queue = new LinkedList<>();
    Set<String> visited = new HashSet<>();
    Map<String, String> pathMap = new HashMap<>();
    String startNode;
    String goalNode;
    String goalPath = "";
    
    public BFS(Graph graph, String startNode, String goalNode) {
    	this.graph = graph;
        this.startNode = startNode;
        this.goalNode = goalNode;
    }

    public void search() {
        queue.add(startNode);
        visited.add(startNode);
        long startTime = System.currentTimeMillis();
        long timeOut = 10000;
       
        while (!queue.isEmpty()) {
        	
        	if(System.currentTimeMillis() - startTime > timeOut) {
        		System.out.println("Search timed out.");
        		return;
        	}
        	
            String currentNode = queue.poll();
            
            if (currentNode.equals(goalNode)) {
            	System.out.printf("Goal node found in %d ms\n", System.currentTimeMillis() - startTime);
                goalPath(currentNode);
                return;
            }

            List<Edge> edges = graph.getEdges(currentNode);
            
            for (Edge edge : edges) {
                String newEdge = edge.getTo().getName();
                if (!visited.contains(newEdge)) {
                    queue.add(newEdge);
                    visited.add(newEdge);
                    pathMap.put(newEdge, currentNode); // Track the parent of the new node
                }
            }
        }

        System.out.println("Goal node not found.");
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
        
        System.out.println(String.format("BFS total distance to goal from %s to %s: %.2f miles" , this.startNode, this.goalNode, pathCost * 0.621371));
        System.out.println("Path to goal: " + goalPath);
    }

}

