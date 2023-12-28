package up.mi.chaabnia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List; // Hagop Hannachian 22108423
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

public class Labyrinthe extends WeightedGraph {

	// TP7_8 pour avoir le chemin de deplacement dans le Labyrinthe
	private static ArrayList<Vertex> Dijkstra(Graph graph, int start, int end, int ncols, int numberV) {
		// Liste pour stocker le chemin final
		ArrayList<Vertex> path = new ArrayList<>();

		// Initialisation du temps depuis la source pour tous les sommets à l'infini,
		// sauf le point de départ
		graph.vertexList.get(start).timeFromSource = 0;

		// Ensemble des sommets à visiter
		Set<Vertex> to_visit = new HashSet<>();
		for (Vertex vertex : graph.vertexList)
			to_visit.add(vertex);

		// Boucle principale de l'algorithme
		while (!to_visit.isEmpty()) {
			// Recherche du sommet avec le temps depuis la source le plus bas
			Vertex minVertex = null;
			double minimal = Double.POSITIVE_INFINITY;

			for (Vertex vertex : to_visit) {
				if (vertex.timeFromSource <= minimal) {
					minVertex = vertex;
					minimal = vertex.timeFromSource;
				}
			}
			// Vérification si le sommet minimum est valide
			if (minVertex == null) {
				// System.out.println("Error: minVertex is null in Dijkstra.");
				return new ArrayList<>();
			}
			// Retrait du sommet minimum de l'ensemble à visiter
			to_visit.remove(minVertex);

			// Mise à jour des temps depuis la source pour les sommets adjacents
			for (Edge edge : minVertex.adjacencylist) {
				int destination = edge.destination;
				// Vérification de la validité de l'indice de destination
				if (destination < 0 || destination >= graph.vertexList.size()) {
					System.out.println("Invalid destination index in Dijkstra.");
					return new ArrayList<>();
				}

				Vertex neighbor = graph.vertexList.get(destination);

				// Ajout de la vérification de déplacement ici
				boolean peutDeplacer = peutDeplace(destination, graph.vertexList, numberV);
				if (!peutDeplacer) {
					continue; // Ignorer ce voisin s'il ne peut pas être déplacé
				}

				double newTime = minVertex.timeFromSource + edge.weight;

				// Mise à jour si un temps plus court est trouvé
				if (newTime < neighbor.timeFromSource) {
					neighbor.timeFromSource = newTime;
					neighbor.prev = minVertex;
				}
			}
		}
		// Reconstruction du chemin à partir de la fin
		Vertex current = graph.vertexList.get(end);
		while (current != null) {
			path.add(current);
			current = current.prev;
		}
		Collections.reverse(path);

		return path;
	}

	// TP7_8 pour avoir le chemin de deplacement dans le Labyrinthe
	private static ArrayList<Vertex> AStar(Graph graph, int start, int end, int ncols, int numberV) {
		graph.vertexList.get(start).timeFromSource = 0;

		// Liste pour stocker le chemin final
		ArrayList<Vertex> path = new ArrayList<Vertex>();
		// Initialisation du temps depuis la source pour tous les sommets à l'infini,
		// sauf le point de départ
		graph.vertexList.get(start).timeFromSource = 0;

		// Ensemble des sommets à visiter
		HashSet<Integer> to_visit = new HashSet<Integer>();
		for (Vertex vertex : graph.vertexList)
			to_visit.add(vertex.num);

		// Calcul des heuristiques pour chaque vertex
		// Elle prend en compte les coordonnées (i % ncols, i / ncols) du vertex actuel
		// et les coordonnées (end % ncols, end / ncols) du point de destination
		int i = 0;
		for (Vertex vertex : graph.vertexList) {
			vertex.heuristic = distance(i % ncols, i / ncols, end % ncols, end / ncols);
			i++;
		}
		// Boucle principale de l'algorithme A*
		while (to_visit.contains(end)) {
			// Recherche du vertex avec la plus petite valeur de timeFromSource + heuristic
			int min_v = 0;
			double timeFromSourceHeuristicMinimale = Double.POSITIVE_INFINITY; // JavaDoc
			for (Integer vertexNum : to_visit) {
				if ((graph.vertexList.get(vertexNum).timeFromSource
						+ graph.vertexList.get(vertexNum).heuristic) <= timeFromSourceHeuristicMinimale) {
					min_v = vertexNum;
					timeFromSourceHeuristicMinimale = graph.vertexList.get(vertexNum).timeFromSource
							+ graph.vertexList.get(vertexNum).heuristic;
				}
			}
			// Ajout du vertex actuel au chemin
			path.add(graph.vertexList.get(min_v));
			to_visit.remove(min_v);

			// Mise à jour des valeurs de timeFromSource pour les vertices adjacents
			for (i = 0; i < graph.vertexList.get(min_v).adjacencylist.size(); i++) {
				if (to_visit.contains(graph.vertexList.get(min_v).adjacencylist.get(i).destination)) {
					int to_try = graph.vertexList.get(min_v).adjacencylist.get(i).destination;
					boolean peutDeplacer = peutDeplace(to_try, graph.vertexList, numberV);

					if (peutDeplacer) { // Prisonnier deplace
						if (((graph.vertexList.get(min_v).timeFromSource
								+ graph.vertexList.get(min_v).adjacencylist.get(i).weight) < (graph.vertexList
										.get(to_try).timeFromSource))) {
							graph.vertexList.get(to_try).timeFromSource = (graph.vertexList.get(min_v).timeFromSource
									+ graph.vertexList.get(min_v).adjacencylist.get(i).weight);
							graph.vertexList.get(to_try).prev = graph.vertexList.get(min_v);
						}
					}
				}
			}
		}
		return path;
	}

	// Vérifie si le prisonnier peut se déplacer vers le vertex spécifié
	public static boolean peutDeplace(int vertex_num, ArrayList<Vertex> vertexList, int numberV) {
		// Vérifie si le numéro de vertex est valide
		if (vertex_num < numberV) {
			// Récupère le vertex actuel à partir de la liste des vertices
			Vertex currentVertex = vertexList.get(vertex_num);

			// Vérifie si l'étiquette du vertex est un point vide ou un point de départ
			return currentVertex.etiquette == '.' || currentVertex.etiquette == 'S';
		} else {
			// Le numéro de vertex est invalide, le prisonnier ne peut pas se déplacer
			return false;
		}
	}

	static ArrayList<Character> DirectionMouvementPourChaqueTour(Graph graph, int start, int end, int ncols, int numberV,
			char res) {
		ArrayList<Vertex> chemin = null;
		if (res == 'A') {
			// Calcul du chemin optimal en utilisant l'algorithme A*
			chemin = AStar(graph, start, end, ncols, numberV);
		}
		if (res == 'D') {
			// Calcul du chemin optimal en utilisant l'algorithme Dijkstra
			chemin = Dijkstra(graph, start, end, ncols, numberV);
		}
		// Liste pour stocker les directions de mouvement
		ArrayList<Character> directions = new ArrayList<Character>();
		// System.out.println("dir size: " + directions.size());
		// System.out.println("chemin size: " + chemin.size());

		// Parcours du chemin pour déterminer les directions de mouvement entre chaque
		// paire de vertices
		for (int i = 0; i < (chemin.size() - 1); i++) {
			// Calcul du mouvement entre deux vertices adjacents
			int mouvement = chemin.get(i + 1).num - chemin.get(i).num;

			// Assignation de la direction correspondante en fonction du mouvement
			if (mouvement == 1)
				directions.add('R'); // à droite
			else if (mouvement == -1)
				directions.add('L'); // à gauche
			else if (mouvement == ncols)
				directions.add('B'); // au-dessus
			else if (mouvement == (-1 * ncols))
				directions.add('T'); // en dessous
			else
				return directions; // Arrêt si le mouvement n'est pas reconnu
		}
		// System.out.println("dir size: " + directions.size());
		return directions;
	}

	// Pour algorithme AStart
	static double distance(int xa, int ya, int xb, int yb) {
		double resultX = Math.pow((xb - xa), 2);
		double resultY = Math.pow((yb - ya), 2);
		return (Math.sqrt(resultX + resultY));
	}

	// Gère la propagation du feu autour d'une position donnée dans le labyrinthe
	// Si le prisonnier se déplace vers le feu dans un tour, la case est marquée
	// comme inaccessible ('A')
	static boolean propagationFeuAutour(int vertexNum, ArrayList<Vertex> vertexList, int numLines, int numCols) {
		// Vérification et mise à jour de la case à gauche du prisonnier
		if (vertexNum % numCols != 0 && vertexList.get(vertexNum - 1).etiquette != 'F') {
			vertexList.get(vertexNum - 1).etiquette = 'A'; // On interdit que le feu d'etre dans meme case
			if (vertexList.get(vertexNum - 1).etiquette == 'S')
				return true;
		}
		// Vérification et mise à jour de la case à droite du prisonnier
		if ((vertexNum + 1) % numCols != 0 && vertexList.get(vertexNum + 1).etiquette != 'F') {
			vertexList.get(vertexNum + 1).etiquette = 'A';
			if (vertexList.get(vertexNum + 1).etiquette == 'S')
				return true;
		}
		// Vérification et mise à jour de la case au-dessus du prisonnier
		if (vertexNum >= numCols && vertexList.get(vertexNum - numCols).etiquette != 'F') {
			vertexList.get(vertexNum - numCols).etiquette = 'A';
			if (vertexList.get(vertexNum - numCols).etiquette == 'S')
				return true;
		}
		// Vérification et mise à jour de la case en dessous du prisonnier
		if (vertexNum < (numLines - 1) * numCols && vertexList.get(vertexNum + numCols).etiquette != 'F') {
			vertexList.get(vertexNum + numCols).etiquette = 'A';
			if (vertexList.get(vertexNum + numCols).etiquette == 'S')
				return true;
		}
		// Aucun impact sur le prisonnier dans ce tour
		return false;
	}

	/*
	 * static void afficherLabyrinthe(ArrayList<Vertex> vertexList, int numCols, int
	 * numVertices) {
	 * for (int i = 0; i < numVertices; i++) {
	 * System.out.print(vertexList.get(i).etiquette + " ");
	 * if ((i + 1) % numCols == 0) System.out.println();
	 * }
	 * System.out.println();
	 * }
	 */
	static boolean deplacerPrisonnier(char directionMouvementPossiblePourCeTour, ArrayList<Vertex> vertexList,
			int nlignes, int ncols, int end) {
		int debut = 0;

		// Recherche de la position actuelle du prisonnier
		for (int i = 0; i < vertexList.size(); i++) {
			if (vertexList.get(i).etiquette == 'D')
				debut = i;
		}

		// Vérification de la victoire
		boolean victoire = gagnerDeplacement(debut, vertexList, nlignes, ncols);

		// Vérification de la victoire
		if (victoire)
			return true;
		else {
			vertexList.get(debut).etiquette = 'L';// Le debut déjà passé

			// Déplacement du prisonnier selon la direction
			int newDebut = debut; // Initialiser la nouvelle position à la position actuelle

			if (directionMouvementPossiblePourCeTour == 'B' && (debut + ncols < vertexList.size())) {
				newDebut = debut + ncols; // Le prisonnier se déplace vers le bas
			} else if (directionMouvementPossiblePourCeTour == 'T' && (debut - ncols >= 0)) {
				newDebut = debut - ncols; // Le prisonnier se déplace vers le haut
			} else if (directionMouvementPossiblePourCeTour == 'L' && (debut % ncols != 0)) {
				newDebut = debut - 1; // Le prisonnier se déplace vers la gauche
			} else if (directionMouvementPossiblePourCeTour == 'R' && ((debut + 1) % ncols != 0)) {
				newDebut = debut + 1; // Le prisonnier se déplace vers la droite
			}

			// Vérification si le mouvement est possible
			if (peutDeplace(newDebut, vertexList, nlignes * ncols)) {
				vertexList.get(newDebut).etiquette = 'D'; // Le prisonnier se déplace vers la nouvelle position
				return false; // Déplacement réussi
			} else {
				return false; // Mouvement invalide
			}
		}
	}

	// Vérifie si le prisonnier a atteint la sortie du labyrinthe
	static boolean gagnerDeplacement(int debut, ArrayList<Vertex> listeDeSommets, int nlignes, int ncols) {
		// Vérifie si le prisonnier peut se déplacer vers la case à gauche et si cette
		// case contient la sortie
		boolean left = listeDeSommets.get(debut).j != 0 && (listeDeSommets.get(debut - 1).etiquette == 'S');
		// Vérifie si le prisonnier peut se déplacer vers la case à droite et si cette
		// case contient la sortie
		boolean right = listeDeSommets.get(debut).j != (ncols - 1) && (listeDeSommets.get(debut + 1).etiquette == 'S');
		// Vérifie si le prisonnier peut se déplacer vers la case en en haut et si cette
		// case contient la sortie
		boolean top = listeDeSommets.get(debut).i != 0 && listeDeSommets.get(debut - ncols).etiquette == 'S';
		// Vérifie si le prisonnier peut se déplacer vers la case en bas et si cette
		// case contient la sortie
		boolean bottom = listeDeSommets.get(debut).i != (nlignes - 1) && listeDeSommets.get(debut + ncols).etiquette == 'S';
		// System.out.println("gagner OU: " + (top || left || right || bottom));
		// System.out.println("gagner ET: " + (top && left && right && bottom));

		// Le prisonnier gagne s'il peut sortir par le haut, la gauche, la droite ou le
		// bas
		return top || left || right || bottom;
	}

	// Exécution de l'instance du labyrinthe
	static char run_instance(Graph graph, int start, int end, int nlignes, int ncols, char res) {
		int turn = 0;

		// Calcul des directions de mouvement pour chaque tour
		ArrayList<Character> directions = DirectionMouvementPourChaqueTour(graph, start, end, ncols, nlignes * ncols, res);
		// System.out.println("dir size: " + directions.size());
		while (turn < directions.size()) {
			// System.out.println("Dans le boucle while run_instance");

			// Mise à jour des états du feu sur le graphe
			for (int i = 0; i < graph.vertexList.size(); i++) {
				if (graph.vertexList.get(i).etiquette == 'A') // Deplacement Feu
					graph.vertexList.get(i).etiquette = 'F'; // Feu
			}

			// Propagation du feu et vérification de la fin de partie
			for (int i = 0; i < graph.vertexList.size(); i++) {
				if (graph.vertexList.get(i).etiquette == 'F') {
					if (propagationFeuAutour(i, graph.vertexList, nlignes, ncols))
						return 'N'; // Perdu
				}
			}

			// Déplacement du prisonnier
			if (deplacerPrisonnier(directions.get(turn), graph.vertexList, nlignes, ncols, end))
				return 'Y'; // Gagner

			turn++;
		}

		return 'N'; // Perdu
	}

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		List<String> resDijkstra = new ArrayList<String>();
		List<String> resAStar = new ArrayList<String>();
		int instances;

		System.out.println("Entrez le numéro de l'instance du labyrinthe : ");
		try {
			instances = Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e) {
			instances = -1;
		}

		char[][] labyrinth = null;
		int n = 0, m = 0;

		// Traitement des instances de labyrinthe
		for (int i = 0; i < instances; i++) {
			try {
				System.out.println(
						"Entrez les dimensions du labyrinthe (nombre de lignes et de colonnes) de l'instance " + (i + 1) + " :");
				// Lecture des dimensions du labyrinthe
				String inputLine = sc.nextLine();
				StringTokenizer tk = new StringTokenizer(inputLine);

				if (tk.countTokens() >= 2) {
					n = Integer.parseInt(tk.nextToken());
					m = Integer.parseInt(tk.nextToken());
				} else {
					System.err.println("Entrée de dimensions invalide. (longeur labyrinthe > 0 ou longeur labyrinthe < 1000 )");
					n = -1; // Réinitialiser à une valeur non positive pour répéter la boucle
					continue;
				}
			} catch (NumberFormatException e) {
				System.err.println("Entrée de dimensions invalide. (longeur labyrinthe > 0 ou longeur labyrinthe < 1000 )");
				n = -1; // Réinitialiser à une valeur non positive pour répéter la boucle
			}

			labyrinth = new char[n][m];

			// Lecture du labyrinthe
			for (int j = 0; j < n; j++) {
				System.out.println("Entrez la ligne n°" + (j + 1) + " :");
				String entree = sc.nextLine();

				for (int k = 0; k < m; k++) {
					labyrinth[j][k] = entree.charAt(k);
				}

				// Création du graphe représentant le labyrinthe
				Graph graphAstar = new Graph();
				Graph graphDijkstra = new Graph();
				int startV = 0, endV = 0;

				for (int v = 0; v < n; v++) {
					for (int k = 0; k < m; k++) {
						graphAstar.addVertex(labyrinth[v][k], 1, v, k);
						graphDijkstra.addVertex(labyrinth[v][k], 1, v, k);
						if (labyrinth[v][k] == 'D')
							startV = v * m + k;
						if (labyrinth[v][k] == 'S')
							endV = v * m + k;
					}
				}

				// Ajout des arêtes dans le graphe
				for (int ligne = 0; ligne < n; ligne++) {
					for (int col = 0; col < m; col++) {
						int debut = ligne * m + col;
						int destination;
						double weight;
						// Parcours des voisins directs et diagonaux
						for (int w = -1; w <= 1; w++) {
							for (int k = -1; k <= 1; k++) {
								if ((w != 0) || (k != 0)) { // Exclut le sommet lui-même
									// Vérification des limites de la grille
									if ((ligne + w) >= 0 && (ligne + w) < n && (col + k) >= 0 && (col + k) < m) {
										destination = (ligne + w) * m + col + k;
										weight = 1;
										// Ajout de l'arête si elle est horizontale ou verticale
										if (Math.abs(debut - destination) == 1 || Math.abs(debut - destination) == m)
											graphDijkstra.addEdge(debut, destination, weight);
										graphAstar.addEdge(debut, destination, weight);
									}
								}
							}
						}
					}
				}

				// Exécution de l'instance avec Dijkstra et ajout du résultat à la liste
				long startTimeDijkstra = System.currentTimeMillis();
				char Dijkstra = run_instance(graphDijkstra, startV, endV, n, m, 'D');
				long endTimeDijkstra = System.currentTimeMillis();

				resDijkstra.add(Dijkstra + " " + (endTimeDijkstra - startTimeDijkstra) + " milliseconds");

				// Exécution de l'instance avec A* et ajout du résultat à la liste
				long startTimeAStar = System.currentTimeMillis();
				char Astar = run_instance(graphAstar, startV, endV, n, m, 'A');
				long endTimeAStar = System.currentTimeMillis();

				resAStar.add(Astar + " " + (endTimeAStar - startTimeAStar) + " milliseconds");
			}

			// Affichage des résultats pour Dijkstra
			System.out.println("\nResults for Dijkstra:");
			for (String c : resDijkstra)
				System.out.println(c);

			// Affichage des résultats pour A*
			System.out.println("\nResults for A*:");
			for (String c : resAStar)
				System.out.println(c);

			sc.close();
		}
	}
}
