package cli;
import java.util.List;
import cli.CLI;
import graph.Graph;
import graph.Node;
import graph.Edge;
import utils.FileParser;
import algorithms.DFS;
import algorithms.IDDFS;
import algorithms.AStarSearch;
import algorithms.BFS;
import algorithms.BestFirstSearch;
import java.util.Scanner;
public class CLI {
	
	public CLI() {
		start();
	}
	
	public void displayMenu() {
		
		System.out.println("Enter the integer for search of choice.");
		System.out.println("a) Depth First Search");
		System.out.println("b) Bredth First Search");
		System.out.println("c) ID-Depth First Search");
		System.out.println("d) Best First Search");
		System.out.println("e) A* Search");
		System.out.println("f) Run all the searches");
		System.out.println("Enter any other key to quit.");
		
	}
	
	public void search(char choice, Graph graph, String start, String goal) {
		
		switch (choice) {
		case 'a':
			DFS dfs = new DFS(graph, start, goal);
			dfs.search();
			break;
		case 'b':
			BFS bfs = new BFS(graph, start, goal);
			bfs.search();
			break;
		case 'c':
			IDDFS iddfs = new IDDFS(graph, start, goal);
			iddfs.search();
			break;
		case 'd':
			BestFirstSearch best = new BestFirstSearch(graph, start, goal);
			best.search();
			break;
		case 'e': 
			AStarSearch astar = new AStarSearch(graph, start, goal);
			astar.search();
		case 'f': 
			runAll(graph, start, goal);
			break;
		}
		
	}
	
	public void runAll(Graph graph, String start, String goal) {
    	DFS dfs = new DFS(graph, start, goal);
    	dfs.search();
    	System.out.println();
    	BFS bfs = new BFS(graph, start, goal);
    	bfs.search();
    	System.out.println();
    	IDDFS iddfs = new IDDFS(graph, start, goal);
    	iddfs.search();
    	System.out.println();
    	BestFirstSearch bestFirst = new BestFirstSearch(graph,start, goal);
    	bestFirst.search();
    	System.out.println();
    	AStarSearch astar = new AStarSearch(graph,start, goal);
    	astar.search();
	}
	
    public void start() {
    	
    	Scanner sysIn = new Scanner(System.in);
    	Graph graph = new Graph();
    	FileParser parser = new FileParser(graph);
    	parser.parseCoordinatesFile("coordinates.csv");
    	parser.parseAdjacenciesFile("Adjacencies.txt");
    	String start = "", goal = "", choice = "";
    	////////////////////////////////////////////////////////////////////////////////////
    	/*
    	start = "Salina";
    	goal = "Wichita";
    	
    	BestFirstSearch bestFirst = new BestFirstSearch(graph,start, goal);
    	bestFirst.search();
    	System.out.println();
    	AStarSearch astar = new AStarSearch(graph,start, goal);
    	astar.search();
    	System.out.println();
    	IDDFS iddfs = new IDDFS(graph, start, goal);
    	iddfs.search();
    	System.out.println();
    	BFS bfs = new BFS(graph, start, goal);
    	bfs.search();
    	System.out.println();
    	DFS dfs = new DFS(graph, start, goal);
    	dfs.search();
    	*/
    	///////////////////////////////////////////////////////////////////////////////////
    	//System.out.println("\n\n\n\n\nWelcome to the Graph Search CLI!");
    	do {
			do {
				start = "";
	    		System.out.println("Enter start city.");
	    		start = sysIn.nextLine();
	    		
				if (graph.getNode(start) == null) {
					System.out.printf("%s is not a valid start city. Pick a different start city.\n", start);
					start = "";
				} 
				
			} while (start.equals(""));
			
			do {
				goal = "";
	    		System.out.println("Enter goal city.");
	    		goal = sysIn.nextLine();
	    		
				if (graph.getNode(goal) == null) {
					System.out.printf("%s is not a valid goal city. Pick a different goal city.\n", goal);
					goal= "";
				} 
				
			} while (goal.equals(""));

			displayMenu();
			choice = sysIn.nextLine().toLowerCase();
			
			if(!(choice.equals("f") || equals("a") || choice.equals("b") || choice.equals("c") || choice.equals("d") || choice.equals("e"))) {
			    break;
			}

			search(choice.charAt(0), graph, start, goal);
			
		} while (true);
		System.out.println("thankyou");
		sysIn.close();
    }
}
