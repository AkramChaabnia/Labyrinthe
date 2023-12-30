package up.mi.chaabnia;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FileManager fileManager = new FileManager();
        char[][] labyrinthe;
        int n, m;

        System.out.println("Do you want to enter the maze manually or read from a file? (1 - Manual, 2 - File)");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        try {
            if (choice == 1) {
                System.out.println("Enter the number of rows of the maze: ");
                n = scanner.nextInt();
                System.out.println("Enter the number of columns of the maze: ");
                m = scanner.nextInt();
                scanner.nextLine(); // consume newline

                labyrinthe = new char[n][m];
                System.out.println("Enter the maze (use '#' for walls, 'D' for the prisoner, 'S' for the exit): ");
                for (int i = 0; i < n; i++) {
                    String row = scanner.nextLine();
                    labyrinthe[i] = row.toCharArray();
                }
            } else {
                System.out.println("Enter the path to the maze file: ");
                String filePath = scanner.nextLine();
                labyrinthe = fileManager.readMazeFromFile(filePath);
                n = labyrinthe.length;
                m = labyrinthe[0].length;
            }

            Labyrinthe lab = new Labyrinthe(n, m, labyrinthe);
            List<List<String>> solutions = lab.resoudre();

            System.out.println(
                    "Which solution do you want to see? (1 - HeuristiqueDistance, 2 - HeuristiqueFeu, 3 - Both)");
            int solutionChoice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (solutionChoice == 1) {
                System.out.println("Solution with heuristic 1 (HeuristiqueDistance): " + solutions.get(0));
            } else if (solutionChoice == 2) {
                System.out.println("Solution with heuristic 2 (HeuristiqueFeu): " + solutions.get(1));
            } else {
                System.out.println("Solution with heuristic 1 (HeuristiqueDistance): " + solutions.get(0));
                System.out.println("Solution with heuristic 2 (HeuristiqueFeu): " + solutions.get(1));
            }

            System.out.println("Do you want to write the solution to an output file? (1 - Yes, 2 - No)");
            int outputChoice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (outputChoice == 1) {
                System.out.println("Enter the path to the output file: ");
                String outputPath = scanner.nextLine();
                fileManager.writeSolutionToFile(labyrinthe, solutions, outputPath);
            } else {
                System.out.println("Solution: " + solutions);
            }

            if (solutions.isEmpty()) {
                System.out.println("Aucune solution trouvée.");
            } else {
                System.out.println("Solution trouvée :");
                for (List<String> etape : solutions) {
                    System.out.println(etape);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
    /*
     * private static void runTests() {
     * // Exécutez vos tests ici
     * org.junit.runner.JUnitCore.main("Labyrinthe.LabyrintheTest");
     * }
     */
}
