public class Libreria {
    private Libro[] estante;

    public Libreria (Libro[] libreria) {
        this.estante = libreria;
    }

    public void destacado() {
        for (int i = 0; i < 5; i++) {
            if (estante[i].esDestacado()) {
                System.out.print(estante[i].obtenerTitulo() + " - ");
            }
        }
        System.out.println("");
    }

}
