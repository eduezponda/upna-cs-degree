public class GuiaTecnica extends Libro {

    public GuiaTecnica (String n, String t, int Np, int p) {
        super(n, t, Np, p);
    }

    public Boolean esDestacado() {
        if (this.Npaginas > 200) {
            return (true);
        }
        else {
            return (false);
        }
    }
}
