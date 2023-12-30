<p align="center">
  <a href="" rel="noopener">
    <img src="" alt="Project logo">
  </a>
</p>
<h3 align="center">Maze Solver</h3>

<div align="center">

[![Status](https://img.shields.io/badge/status-active-success.svg)]()
[![GitHub Issues](https://img.shields.io/github/issues/AkramChaabnia/MazeSolver.svg)](https://github.com/AkramChaabnia/MazeSolver/issues)
[![GitHub Pull Requests](https://img.shields.io/github/issues-pr/AkramChaabnia/MazeSolver.svg)](https://github.com/AkramChaabnia/MazeSolver/pulls)
[![University](https://img.shields.io/badge/University-Paris%20Cité%20Université-%23A6192E)](https://u-paris.fr)

</div>

---

<p align="center"> Maze Solver is a Java project that uses the A* algorithm to find a path from a start point to an exit point in a 2D maze while avoiding fire cells. It was developed as part of the advanced algorithms module at Université de Paris Cité.
  <br> 
</p>

## 📝 Table of Contents

- [About](#about)
- [Code Structure](#code-structure)
- [Maze File Format](#maze-file-format)
- [Heuristic Comparison](#heuristic-comparison)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Built Using](#built-using)
- [Authors](#authors)
- [Acknowledgments](#acknowledgments)

## 🧐 About <a name = "about"></a>

The goal of this project is to implement the A\* algorithm for finding a path in a 2D maze. The project includes a maze structure, methods for running the algorithm and displaying the maze, and different mazes encoded in text files.

## 🚀 Code Structure <a name = "code_structure"></a>

The project consists of the following files:

- `Labyrinthe.java`: Represents the 2D maze. It includes methods for reading the maze from a file and displaying the maze.
- `Etat.java`: Represents a state in the maze, including the position and the time step. It includes methods for comparing states and calculating the Manhattan distance between two states.
- `Heuristique.java`: An interface for heuristic functions. It includes a method for evaluating a state.
- `HeuristiqueDistance.java`: A heuristic function that uses the Euclidean distance to the exit. It implements the `evaluer` method from the `Heuristique` interface.
- `HeuristiqueFeu.java`: A heuristic function that takes into account the distance to the nearest fire cell. It implements the `evaluer` method from the `Heuristique` interface.
- `AStar.java`: Implements the A\* algorithm for finding a path in the maze. It includes methods for running the algorithm and building the path.
- `FileManager.java`: Includes methods for reading the maze from a file and writing the solution to a file.
- `Main.java`: The main class that runs the program. It includes a user interface for entering the maze and choosing the heuristic.
- `maze.txt`: A text file that encodes the maze. The maze is represented by different symbols for the walls, the start point, the exit point, the fire cells, and the free cells.
- `result.txt`: A text file that contains the solution. The solution is represented by the initial state of the maze and the maze with the path marked by asterisks (`*`), if a solution is found.

## 📄 Maze File Format <a name="map_format"></a>

The maze is encoded in a text file with the following symbols:

- `.`: Free cell
- `#`: Wall
- `F`: Fire cell
- `D`: Start point
- `S`: Exit point

The start point is the initial position of the prisoner when the fire was lit. In addition, the following constraints are imposed in the model:

- The fire spreads in the four cardinal directions (N, S, E, W) at a speed of one cell per minute.
- The prisoner can also move in these four directions at the same speed.
- Neither the fire nor the prisoner can cross a wall.
- If the prisoner and the fire occupy the same position at any time, the prisoner dies instantly.

You can define your own mazes by creating a text file that follows this format.

## 📊 Heuristic Comparison <a name="heuristic_comparison"></a>

The project includes two heuristic functions: `HeuristiqueDistance` and `HeuristiqueFeu`.

- `HeuristiqueDistance` uses the Euclidean distance to the exit. It guides the A\* algorithm to prioritize states that are closer to the exit, but it does not take into account the fire cells.
- `HeuristiqueFeu` takes into account the distance to the nearest fire cell in addition to the Euclidean distance to the exit. It guides the A\* algorithm to avoid the fire cells while also prioritizing states that are closer to the exit.

The choice of heuristic can greatly impact the performance of the A\* algorithm and the path found. `HeuristiqueDistance` can find shorter paths, but it might lead the prisoner into fire. `HeuristiqueFeu` can help the prisoner avoid fire, but it might result in longer paths.

## 🛠️ Prerequisites <a name = "prerequisites"></a>

To run the project, ensure the following are installed:

- [Java](https://www.java.com/en/download/): Download and install Java on your machine.
- [JavaFX](https://openjfx.io/): Install JavaFX and configure it in your IDE if you're using one.

## 🏁 Getting Started <a name = "getting_started"></a>

To compile and execute the project:

1. Clone the repository or download the project files.

### Using an IDE (Eclipse, VSCode, etc.)

2. Set up the JavaFX environment in your IDE.
3. Run the `Main.java` file.

### Using Command Line

2. Compile the Java code using the command: `javac --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls Main.java`.
3. Run the compiled code using the command: `java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls Main`.

## 🎈 Usage <a name="usage"></a>

To use the project, you can follow these steps:

- Run the `Main` class. You will be prompted to enter the maze manually or from a file.
- If you choose to enter the maze from a file, specify the name of the maze file. The file should follow the format described in the maze file format.
- Choose the heuristic to use: `HeuristiqueDistance` or `HeuristiqueFeu`. You can also choose to display the results of both heuristics.
- The program will run the A\* algorithm and display the maze and the path found. The performance metrics of the algorithm will also be printed.
- You can choose to save the result in an output text file. The file will contain the initial state of the maze and the maze with the path marked by asterisks (`*`), if a solution is found.
- You can run the program again with a different maze file or heuristic to compare the results.

## ⛏️ Built Using <a name = "built_using"></a>

- [Java](https://www.java.com/) - Programming Language
- [JavaFX](https://openjfx.io/) - GUI Library

## ✍️ Authors <a name = "authors"></a>

- [@AkramChaabnia](https://github.com/AkramChaabnia)

## 🎉 Acknowledgements <a name = "acknowledgement"></a>

- This project was inspired by the advanced algorithms module at Université de Paris Cité (IF05X040 Algorithmique avancée).
- The code skeleton and the map format were provided by the module instructor, <u><b>Professor Nicolas Loménie</b></u>.

## 📞 Contact <a name = "contact"></a>

If you have any questions, suggestions, or just want to connect, here's how you can reach me:

- Email: akram.chaabnia25@gmail.com
- LinkedIn: [Akram CHAABNIA](https://www.linkedin.com/in/akram-chaabnia-43b7941b0/)
- GitHub: [@AkramChaabnia](https://github.com/AkramChaabnia)

Please feel free to contact me!
