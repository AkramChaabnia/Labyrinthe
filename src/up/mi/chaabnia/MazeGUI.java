package up.mi.chaabnia;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MazeGUI extends Application {
  private char[][] labyrinthe;

  public MazeGUI(char[][] labyrinthe) {
    this.labyrinthe = labyrinthe;
  }

  @Override
  public void start(Stage primaryStage) {
    GridPane gridPane = new GridPane();
    for (int i = 0; i < labyrinthe.length; i++) {
      for (int j = 0; j < labyrinthe[0].length; j++) {
        Rectangle rectangle = new Rectangle(20, 20);
        switch (labyrinthe[i][j]) {
          case '#':
            rectangle.setFill(Color.BLACK);
            break;
          case 'D':
            rectangle.setFill(Color.BLUE);
            break;
          case 'S':
            rectangle.setFill(Color.GREEN);
            break;
          case 'F':
            rectangle.setFill(Color.RED);
            break;
          default:
            rectangle.setFill(Color.WHITE);
            break;
        }
        gridPane.add(rectangle, j, i);
      }
    }

    Scene scene = new Scene(gridPane);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}