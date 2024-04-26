public class Ensayo extends Libro {

    public Ensayo (String n, String t, int Np, int p) {
        super(n, t, Np, p);
    }

    public Boolean esDestacado() {
        if (this.precio <= 20) {
            return (true);
            }
        else {
            return (false);
        }
    }
}
