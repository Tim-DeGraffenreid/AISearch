package main;
import java.util.List;
import cli.CLI;
import graph.Graph;
import graph.Node;
import graph.Edge;
import utils.FileParser;
import algorithms.DFS;

public class Main {

	public static void main(String[] args) {
		/*
        Graph graph = new Graph();
        FileParser parser = new FileParser(graph);

        // Update the file paths according to your local setup
        String coordinatesFilePath = "coordinates.csv";
        String adjacenciesFilePath = "Adjacencies.txt";

        parser.parseCoordinatesFile(coordinatesFilePath);
        parser.parseAdjacenciesFile(adjacenciesFilePath);
        
        for (Node node : graph.getNodes()) {
            System.out.println("Node: " + node.getName() + " (" + node.getLatitude() + ", " + node.getLongitude() + ")");
            List<Edge> edges = graph.getEdges(node.getName());
            for (Edge edge : edges) {
                System.out.println("  Edge to: " + edge.getTo().getName() + " with cost: " + edge.getCost());
            }
        }
        
        for (Node node : graph.getNodes()) {
            System.out.println("Adjacencies for Node: " + node.getName());

            // Retrieve and loop through all edges (adjacencies) for the current node
            List<Edge> adjacencies = graph.getEdges(node.getName());
            for (Edge edge : adjacencies) {
                System.out.println(" - to: " + edge.getTo().getName() + ", cost: " + edge.getCost());
            }
        }*/
        
		CLI cli = new CLI();

	}

}
