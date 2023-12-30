package up.mi.chaabnia;

import Labyrinthe.*;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LabyrintheTest {

    @Test
    void testHeuristiqueDistance() {
        Etat sortie = new Etat(5, 5, 0, null);
        Heuristique HeuristiqueDistance = new HeuristiqueDistance(sortie);
        Etat etat = new Etat(3, 3, 0, null);

        int valeurHeuristique = HeuristiqueDistance.evaluer(etat);
        assertEquals(4, valeurHeuristique, "La valeur de l'heuristique de base est incorrecte");
    }

    @Test
    void testHeuristiqueFeu() {
        char[][] labyrinthe = {
                { Labyrinthe.MUR, Labyrinthe.MUR, Labyrinthe.MUR },
                { Labyrinthe.MUR, Labyrinthe.FEU, Labyrinthe.MUR },
                { Labyrinthe.MUR, Labyrinthe.MUR, Labyrinthe.MUR }
        };

        Etat sortie = new Etat(2, 2, 0, null);
        Heuristique HeuristiqueFeu = new HeuristiqueFeu(Arrays.asList(labyrinthe), sortie);
        Etat etat = new Etat(0, 0, 0, null);

        int valeurHeuristique = HeuristiqueFeu.evaluer(etat);
        assertEquals(5, valeurHeuristique, "La valeur de l'heuristique avec feu est incorrecte");
    }

    @Test
    void testAStar() {
        // Mettez en place vos données de test pour A*
        char[][] labyrinthe = {
                { Labyrinthe.MUR, Labyrinthe.MUR, Labyrinthe.MUR },
                { Labyrinthe.MUR, Labyrinthe.SORTIE, Labyrinthe.MUR },
                { Labyrinthe.MUR, Labyrinthe.PRISONNIER, Labyrinthe.MUR }
        };

        Map<String, Etat> etats = new HashMap<>();
        Etat initial = new Etat(0, 0, 0, null);
        etats.put(initial.toString(), initial);

        Etat sortie = null;

        for (int i = 0; i < labyrinthe.length; i++) {
            for (int j = 0; j < labyrinthe[0].length; j++) {
                char c = labyrinthe[i][j];
                if (c == Labyrinthe.SORTIE) {
                    sortie = new Etat(i, j, 0, null);
                    etats.put(sortie.toString(), sortie);
                }
            }
        }

        Heuristique heuristique1 = new HeuristiqueDistance(sortie);
        Heuristique heuristique2 = new HeuristiqueFeu(Arrays.asList(labyrinthe), sortie);

        AStar astar = new AStar(etats, heuristique1, heuristique2, labyrinthe, initial);
        Etat solution_etat = astar.resoudre(true);

        // Ajoutez des assertions appropriées
        assertNotNull(solution_etat, "Aucune solution trouvée avec la première heuristique");
        // Ajoutez d'autres assertions pour vérifier la validité de la solution
        // ...
    }

    @Test
    void testLabyrinthe() {
        char[][] labyrinthe = {
                { Labyrinthe.MUR, Labyrinthe.MUR, Labyrinthe.MUR },
                { Labyrinthe.MUR, Labyrinthe.SORTIE, Labyrinthe.MUR },
                { Labyrinthe.MUR, Labyrinthe.PRISONNIER, Labyrinthe.MUR }
        };

        Labyrinthe lab = new Labyrinthe(3, 3, labyrinthe);
        List<List<String>> solutions = lab.resoudre();

        // Assurez-vous d'ajuster les assertions en fonction de votre implémentation
        assertFalse(solutions.isEmpty(), "Aucune solution trouvée");
        assertEquals(2, solutions.size(), "Le nombre de solutions est incorrect");

        // Ajoutez d'autres assertions pour vérifier que les solutions sont correctes
        // ...

        // Testez également d'autres aspects de votre classe Labyrinthe
        // ...
    }
}
