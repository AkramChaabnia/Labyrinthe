package up.mi.chaabnia;

import java.io.*;
import java.util.*;

public class FileManager {
  public char[][] readMazeFromFile(String filePath) throws IOException {
    List<String> lines = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        lines.add(line);
      }
    }

    int n = lines.size();
    int m = lines.get(0).length();
    char[][] labyrinthe = new char[n][m];

    for (int i = 0; i < n; i++) {
      labyrinthe[i] = lines.get(i).toCharArray();
    }

    // Log the maze
    System.out.println("Maze read from file:");
    for (char[] row : labyrinthe) {
      System.out.println(Arrays.toString(row));
    }

    return labyrinthe;
  }

  public void writeSolutionToFile(List<List<String>> solutions, String filePath) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
      if (solutions.isEmpty()) {
        writer.write("Aucune solution trouvée.");
      } else {
        writer.write("Solution trouvée :\n");
        writer.write("Solution with heuristic 1 (HeuristiqueDistance): " + solutions.get(0) + "\n");
        writer.write("Solution with heuristic 2 (HeuristiqueFeu): " + solutions.get(1) + "\n");
      }
    }
  }
}