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

            System.out.println("Enter the path to the output file: ");
            String outputPath = scanner.nextLine();
            fileManager.writeSolutionToFile(solutions, outputPath);

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
    } /*
       * private static void runTests() {
       * // Exécutez vos tests ici
       * org.junit.runner.JUnitCore.main("Labyrinthe.LabyrintheTest");
       * }
       */
}
