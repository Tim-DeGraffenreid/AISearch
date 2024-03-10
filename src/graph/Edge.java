package graph;

public class Edge {
    private Node from;
    private Node to;
    private double cost;

    public Edge(Node from, Node to, double cost) {
        this.from = from;
        this.to = to;
        this.cost = cost;
    }

    // Getters
    public Node getFrom() { return from; }
    public Node getTo() { return to; }
    public double getCost() { return cost; }

    // Setters
    public void setFrom(Node from) { this.from = from; }
    public void setTo(Node to) { this.to = to; }
    public void setCost(double cost) { this.cost = cost; }
}
