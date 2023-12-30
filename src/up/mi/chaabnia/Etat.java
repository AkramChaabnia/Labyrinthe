package up.mi.chaabnia;

public class Etat {
    int x;
    int y;
    int temps;
    Direction direction;
    Etat pere;
    int cout;

    public Etat(int x, int y, int temps, Direction direction) {
        this.x = x;
        this.y = y;
        this.temps = temps;
        this.direction = direction;
        this.pere = null;
        this.cout = 0;
    }

    @Override
    public String toString() {
        return String.format("(%d,%d,%d)", x, y, temps);
    }
}
