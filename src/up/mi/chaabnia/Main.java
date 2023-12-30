package up.mi.chaabnia;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // runTests();
        Scanner scanner = new Scanner(System.in);

        // Lire les dimensions du labyrinthe
        System.out.println("Entrez le nombre de lignes du labyrinthe : ");
        int n = scanner.nextInt();

        System.out.println("Entrez le nombre de colonnes du labyrinthe : ");
        int m = scanner.nextInt();

        // Lire le labyrinthe
        System.out.println(
                "Entrez le labyrinthe (utilisez '#' pour les murs, 'D' pour le prisonnier, 'S' pour la sortie) : ");
        char[][] labyrinthe = new char[n][m];

        for (int i = 0; i < n; i++) {
            String row = scanner.nextLine();

            // Vérifier que la longueur de la ligne correspond au nombre de colonnes
            if (row.length() != m) {
                System.out.println(
                        "La longueur de la ligne ne correspond pas au nombre de colonnes. Veuillez réessayer.");
                i--; // Répéter la saisie de cette ligne
                continue;
            }

            // Convertir la ligne en un tableau de caractères
            labyrinthe[i] = row.toCharArray();
        }

        // Créer une instance de la classe Labyrinthe
        Labyrinthe lab = new Labyrinthe(n, m, labyrinthe);

        // Résoudre le labyrinthe
        List<List<String>> solutions = lab.resoudre();

        // Afficher la solution
        if (solutions.isEmpty()) {
            System.out.println("Aucune solution trouvée.");
        } else {
            System.out.println("Solution trouvée :");
            for (List<String> etape : solutions) {
                System.out.println(etape);
            }
        }
    }
    /*
     * private static void runTests() {
     * // Exécutez vos tests ici
     * org.junit.runner.JUnitCore.main("Labyrinthe.LabyrintheTest");
     * }
     */
}
