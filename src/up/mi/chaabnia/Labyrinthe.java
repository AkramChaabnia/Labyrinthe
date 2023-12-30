package up.mi.chaabnia;

import java.util.*;

public class Labyrinthe {
    static final char MUR = '#';
    private static final char SORTIE = 'S';
    private static final char PRISONNIER = 'D';
    static final char FEU = 'F';

    private int n;
    private int m;
    private char[][] labyrinthe;

    public Labyrinthe(int n, int m, char[][] labyrinthe) {
        this.n = n;
        this.m = m;
        this.labyrinthe = labyrinthe;
    }

    public List<List<String>> resoudre() {
        List<List<String>> solutions = new ArrayList<>();

        // Construction du graphe d'états
        Map<String, Etat> etats = new HashMap<>();
        Etat initial = new Etat(0, 0, 0, null);
        etats.put(initial.toString(), initial);

        Etat sortie = null; // Ajout d'une référence à l'état de sortie

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                char c = labyrinthe[i][j];
                if (c == PRISONNIER) {
                    initial = new Etat(i, j, 0, null);
                } else if (c == SORTIE) {
                    sortie = new Etat(i, j, 0, null);
                    etats.put(sortie.toString(), sortie);
                }
            }
        }

        Heuristique heuristique1 = new HeuristiqueDistance(sortie);
        Heuristique heuristique2 = new HeuristiqueFeu(labyrinthe, sortie);

        AStar astar1 = new AStar(etats, heuristique1, heuristique2, labyrinthe, sortie);
        System.out.println("Solving with heuristic 1..."); // Log before solving with heuristic 1
        Etat solution_etat1 = astar1.resoudre(true);
        List<String> solution1 = buildSolution(solution_etat1);
        System.out.println("Solution with heuristic 1: " + solution1); // Log solution with heuristic 1

        AStar astar2 = new AStar(etats, heuristique1, heuristique2, labyrinthe, sortie);
        System.out.println("Solving with heuristic 2..."); // Log before solving with heuristic 2
        Etat solution_etat2 = astar2.resoudre(false);
        List<String> solution2 = buildSolution(solution_etat2);
        System.out.println("Solution with heuristic 2: " + solution2); // Log solution with heuristic 2

        solutions.add(solution1);
        solutions.add(solution2);

        return solutions;
    }

    private List<String> buildSolution(Etat solution_etat) {
        List<String> solution = new ArrayList<>();
        // Récupération de la solution
        if (solution_etat == null) {
            return solution;
        }

        solution.add(String.format("(%d,%d,%d)", solution_etat.x, solution_etat.y, solution_etat.temps));
        while (solution_etat.pere != null) {
            solution.add(
                    String.format("(%d,%d,%d)", solution_etat.pere.x, solution_etat.pere.y, solution_etat.pere.temps));
            solution_etat = solution_etat.pere;
        }

        Collections.reverse(solution);

        return solution;
    }

    public void burn_around(int x, int y, int fireSpeed) {
        final int UP = -1;
        final int DOWN = 1;
        final int LEFT = -1;
        final int RIGHT = 1;

        // Propagation du feu vers le haut
        for (int i = x - 1; i >= 0 && i >= x - fireSpeed; i--) {
            if (labyrinthe[i][y] != MUR) {
                labyrinthe[i][y] = FEU;
            } else {
                break;
            }
        }

        // Propagation du feu vers le bas
        for (int i = x + 1; i < n && i <= x + fireSpeed; i++) {
            if (labyrinthe[i][y] != MUR) {
                labyrinthe[i][y] = FEU;
            } else {
                break;
            }
        }

        // Propagation du feu vers la gauche
        for (int j = y - 1; j >= 0 && j >= y - fireSpeed; j--) {
            if (labyrinthe[x][j] != MUR) {
                labyrinthe[x][j] = FEU;
            } else {
                break;
            }
        }

        // Propagation du feu vers la droite
        for (int j = y + 1; j < m && j <= y + fireSpeed; j++) {
            if (labyrinthe[x][j] != MUR) {
                labyrinthe[x][j] = FEU;
            } else {
                break;
            }
        }
    }

    public void propagerFeu() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (labyrinthe[i][j] == FEU) {
                    propagerFeuDepuisPosition(i, j);
                }
            }
        }
    }

    private void propagerFeuDepuisPosition(int x, int y) {
        final int fireSpeed = 1;

        propagerFeuDansDirection(x, y, Direction.HAUT, fireSpeed);
        propagerFeuDansDirection(x, y, Direction.BAS, fireSpeed);
        propagerFeuDansDirection(x, y, Direction.GAUCHE, fireSpeed);
        propagerFeuDansDirection(x, y, Direction.DROITE, fireSpeed);
    }

    private void propagerFeuDansDirection(int x, int y, Direction direction, int fireSpeed) {
        for (int i = 1; i <= fireSpeed; i++) {
            int newX = x + i * direction.dx;
            int newY = y + i * direction.dy;

            if (newX >= 0 && newX < n && newY >= 0 && newY < m && labyrinthe[newX][newY] != MUR) {
                labyrinthe[newX][newY] = FEU;
            } else {
                break;
            }
        }
    }
}
