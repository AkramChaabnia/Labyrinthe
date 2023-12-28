package up.mi.chaabnia;


import java.util.LinkedList;
import java.util.ArrayList;

public class WeightedGraph {


    
    // Classe interne pour représenter une arête pondérée
    static class Edge {
        int source; // Sommet source
        int destination; // Sommet destination
        double weight; // Poids de l'arête

        // Constructeur de l'arête
        public Edge(int source, int destination, double weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    // Classe interne pour représenter un sommet du graphe pondéré
    static class Vertex {
        double indivTime; // Temps individuel du sommet
        double timeFromSource; // Temps depuis la source jusqu'à ce sommet
        double heuristic; // Heuristique du sommet
        Vertex prev; // Sommet précédent dans le chemin optimal
        LinkedList<Edge> adjacencylist; // Liste des arêtes adjacentes au sommet
        int num; // Numéro du sommet
        char etiquette; // Étiquette du sommet
        int i; // Coordonnée i du sommet
        int j; // Coordonnée j du sommet

        // Constructeur du sommet
        public Vertex(int num, char etiquette, int i, int j) {
            this.indivTime = Double.POSITIVE_INFINITY;
            this.timeFromSource = Double.POSITIVE_INFINITY;
            this.heuristic = -1;
            this.prev = null;
            this.adjacencylist = new LinkedList<Edge>();
            this.num = num;
            this.etiquette = etiquette;
            this.i = i;
            this.j = j;
        }

        // Méthode pour ajouter un voisin (arête) au sommet
        public void addNeighbor(Edge e) {
            this.adjacencylist.addFirst(e);
        }
    }

    // Classe principale représentant le graphe pondéré
    static class Graph {

        ArrayList<Vertex> vertexList; // Liste des sommets du graphe
        int num_v = 0; // Nombre de sommets dans le graphe

        // Constructeur du graphe
        Graph() {
            vertexList = new ArrayList<Vertex>();
        }

        // Méthode pour ajouter un sommet au graphe
        public void addVertex(char etiquette, double indivTime, int i, int j) {
            Vertex v = new Vertex(num_v, etiquette, i, j);
            v.indivTime = indivTime;
            vertexList.add(v);
            num_v = num_v + 1;
        }

        // Méthode pour ajouter une arête pondérée entre deux sommets
        public void addEdge(int source, int destination, double weight) {
            Edge edge = new Edge(source, destination, weight);
            vertexList.get(source).addNeighbor(edge);
        }

    }
}
