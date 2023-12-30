package up.mi.chaabnia;

public class HeuristiqueFeu implements Heuristique {

    private char[][] labyrinthe;
    private Etat sortie;
    private static final char FEU = 'F';

    public HeuristiqueFeu(char[][] labyrinthe, Etat sortie) {
        this.labyrinthe = labyrinthe;
        this.sortie = sortie;
    }

    @Override
    public int evaluer(Etat etat) {
        int distanceEuclidienne = (int) Math
                .sqrt((etat.x - sortie.x) * (etat.x - sortie.x) + (etat.y - sortie.y) * (etat.y - sortie.y));
        int distanceFeu = 0;

        int n = labyrinthe.length;
        int m = labyrinthe[0].length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (labyrinthe[i][j] == FEU) {
                    int distanceTemporaire = Math.abs(i - etat.x) + Math.abs(j - etat.y);
                    if (distanceTemporaire < distanceFeu || distanceFeu == 0) {
                        distanceFeu = distanceTemporaire;
                    }
                }
            }
        }

        // Log the distances
        System.out.println("Evaluating state: " + etat);
        System.out.println("Euclidean distance to exit: " + distanceEuclidienne);
        System.out.println("Fire distance: " + distanceFeu);

        return distanceEuclidienne + distanceFeu;
    }
}