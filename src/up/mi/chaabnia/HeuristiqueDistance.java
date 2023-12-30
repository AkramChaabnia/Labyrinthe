package up.mi.chaabnia;

public class HeuristiqueDistance implements Heuristique {

    private Etat sortie;

    public HeuristiqueDistance(Etat sortie) {
        this.sortie = sortie;
    }

    @Override
    public int evaluer(Etat etat) {
        return Math.abs(etat.x - sortie.x) + Math.abs(etat.y - sortie.y);
    }
}
