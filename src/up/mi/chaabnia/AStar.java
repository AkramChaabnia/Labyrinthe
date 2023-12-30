package up.mi.chaabnia;

import java.util.Map;
import java.util.PriorityQueue;

public class AStar {

    private Map<String, Etat> etats;
    private Heuristique heuristique1; // Pour la première heuristique
    private Heuristique heuristique2; // Pour la deuxième heuristique
    private char[][] labyrinthe;
    private Etat sortie;

    public AStar(Map<String, Etat> etats, Heuristique heuristique1, Heuristique heuristique2, char[][] labyrinthe,
            Etat sortie) {
        this.etats = etats;
        this.heuristique1 = heuristique1;
        this.heuristique2 = heuristique2;
        this.labyrinthe = labyrinthe;
        this.sortie = sortie;
    }

    public Etat resoudre(boolean utiliserHeuristique1) {
        PriorityQueue<Etat> file = new PriorityQueue<>((etat1, etat2) -> Integer.compare(etat1.cout, etat2.cout));
        file.add(etats.get(sortie.toString())); // Add the exit state to the queue

        while (!file.isEmpty()) {
            Etat etat = file.poll();

            System.out.println("Current state: " + etat); // Log current state

            if (etat.x == sortie.x && etat.y == sortie.y) {
                System.out.println("Exit found: " + etat); // Log when exit is found
                return etat;
            }

            for (Direction direction : Direction.values()) {
                int x = etat.x + direction.dx;
                int y = etat.y + direction.dy;

                int n = labyrinthe.length;
                int m = labyrinthe[0].length;

                if (x >= 0 && x < n && y >= 0 && y < m && labyrinthe[x][y] != Labyrinthe.MUR) {
                    Etat nouvel_etat = new Etat(x, y, etat.temps + 1, direction);
                    nouvel_etat.direction = direction; // Ajout de la direction
                    nouvel_etat.pere = etat;

                    if (utiliserHeuristique1) {
                        nouvel_etat.cout = etat.cout + heuristique1.evaluer(nouvel_etat);
                    } else {
                        nouvel_etat.cout = etat.cout + heuristique2.evaluer(nouvel_etat);
                    }

                    etats.put(nouvel_etat.toString(), nouvel_etat);
                    file.add(nouvel_etat);

                    System.out.println("Adding new state to queue: " + nouvel_etat); // Log when a new state is added to
                                                                                     // the queue
                }
            }
        }

        System.out.println("No solution found"); // Log when no solution is found
        return null;
    }
}
