public class Libreria {
    private Libro[] estante = new Libro[5];

    public Libreria (Libro l0, Libro l1, Libro l2, Libro l3, Libro l4) {
        estante[0] = l0;
        estante[1] = l1;
        estante[2] = l2;
        estante[3] = l3;
        estante[4] = l4;
    }
	
    public void destacado() {
    
    	int i;
        for (i = 0; i < 5; i = i + 1) {
            if (estante[i].esDestacado()) {
                System.out.print(estante[i].obtenerTitulo() + " - ");
            }
        }
        System.out.println("");
    }

}
