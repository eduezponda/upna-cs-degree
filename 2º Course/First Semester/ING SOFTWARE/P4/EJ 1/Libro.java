public class Libro implements Destacable {
    protected String nombre;
    protected String tipo;
    protected int Npaginas;
    protected int precio;

    public Libro (String n, String t, int Np, int p) {
        nombre = n;
        tipo = t;
        Npaginas = Np;
        precio = p;
    }

    public String obtenerTitulo() {
        return (nombre);
    }

    public Boolean esDestacado() {
        return false;
    }
}
