package up.mi.chaabnia;

import java.util.*;

public class WeightedGraph {
    static class Vertex implements Comparable<Vertex> {
        double indivTime;
        double timeFromSource;
        double heuristic;
        Vertex prev;
        LinkedList<Edge> adjacencyList;
        int num;
        int firePosition;

        @Override
        public int compareTo(Vertex other) {
            return Integer.compare(this.num, other.num);
        }

        public Vertex(int num) {
            this.indivTime = Double.POSITIVE_INFINITY;
            this.timeFromSource = Double.POSITIVE_INFINITY;
            this.heuristic = -1;
            this.prev = null;
            this.adjacencyList = new LinkedList<>();
            this.num = num;
            this.firePosition = -1;
        }

        public void addNeighbor(Edge e) {
            this.adjacencyList.addFirst(e);
        }
    }

    static class Edge {
        Vertex source;
        Vertex destination;
        double weight;

        public Edge(Vertex destination, double weight) {
            this.destination = destination;
            this.weight = weight;
        }

        public Edge(Vertex source, Vertex destination, double weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    public static void main(String[] args) {
        int vertices = 6;
        List<Vertex> vertexList = new ArrayList<>();

        for (int i = 0; i < vertices; i++) {
            vertexList.add(new Vertex(i));
        }

        WeightedGraph weightedGraph = new WeightedGraph();
        for (int i = 0; i < vertices; i++) {
            weightedGraph.addVertex(vertexList.get(i), 10); // Adjust the parameters as needed
        }

        weightedGraph.addEdge(vertexList.get(0), vertexList.get(1), 4);
        weightedGraph.addEdge(vertexList.get(0), vertexList.get(2), 3);
        weightedGraph.addEdge(vertexList.get(1), vertexList.get(3), 2);
        weightedGraph.addEdge(vertexList.get(1), vertexList.get(2), 5);
        weightedGraph.addEdge(vertexList.get(2), vertexList.get(3), 7);
        weightedGraph.addEdge(vertexList.get(3), vertexList.get(4), 2);
        weightedGraph.addEdge(vertexList.get(4), vertexList.get(0), 4);
        weightedGraph.addEdge(vertexList.get(4), vertexList.get(1), 4);
        weightedGraph.addEdge(vertexList.get(4), vertexList.get(5), 6);

        // Rest of your code...
    }

    public void addVertex(Vertex vertex, double indivTime) {
        vertex.indivTime = indivTime;
    }

    public void addEdge(Vertex source, Vertex destination, double weight) {
        Edge edge = new Edge(source, destination, weight);
        source.addNeighbor(edge);
    }
}
