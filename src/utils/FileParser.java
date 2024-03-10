package utils;
import graph.Graph;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class FileParser {
    private Graph graph;

    public FileParser(Graph graph) {
        this.graph = graph;
    }

    public void parseCoordinatesFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            
            while ((line = reader.readLine()) != null) {
            	
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String name = parts[0].trim();
                    double latitude = Double.parseDouble(parts[1].trim());
                    double longitude = Double.parseDouble(parts[2].trim());
                    graph.addNode(name, latitude, longitude);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseAdjacenciesFile(String filePath) {
    	
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\s");
                if (parts.length >= 2) {
                    String from = parts[0].trim();
                    String to = parts[1].trim();
                    double cost = 0;//Double.parseDouble(parts[2].trim());
                    graph.addEdge(from, to, cost);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
