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

  public void writeSolutionToFile(char[][] labyrinthe, List<List<String>> solutions, String filePath)
      throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
      writer.write("Maze:\n");
      for (int i = 0; i < labyrinthe.length; i++) {
        for (int j = 0; j < labyrinthe[0].length; j++) {
          switch (labyrinthe[i][j]) {
            case '#':
              writer.write('█'); // wall
              break;
            case 'D':
              writer.write('S'); // start point
              break;
            case 'S':
              writer.write('E'); // exit point
              break;
            case 'F':
              writer.write('F'); // fire cell
              break;
            default:
              writer.write(' '); // empty cell
              break;
          }
        }
        writer.write("\n");
      }

      if (solutions.isEmpty()) {
        writer.write("Aucune solution trouvée.");
      } else {
        writer.write("Solution trouvée :\n");

        // Create a copy of the maze
        char[][] mazeCopy = new char[labyrinthe.length][labyrinthe[0].length];
        for (int i = 0; i < labyrinthe.length; i++) {
          mazeCopy[i] = labyrinthe[i].clone();
        }

        // Mark the path in the copy of the maze
        for (List<String> solution : solutions) {
          for (String state : solution) {
            String[] parts = state.replaceAll("[()]", "").split(",");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            mazeCopy[x][y] = '*'; // path
          }

          // Write the maze with the path to the file
          writer.write("Maze with path:\n");
          for (int i = 0; i < mazeCopy.length; i++) {
            for (int j = 0; j < mazeCopy[0].length; j++) {
              writer.write(mazeCopy[i][j]);
            }
            writer.write("\n");
          }
        }
      }
    }
  }
}