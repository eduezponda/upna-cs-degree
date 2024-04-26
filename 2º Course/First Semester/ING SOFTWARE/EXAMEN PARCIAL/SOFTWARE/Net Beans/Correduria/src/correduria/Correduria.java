package correduria;

public class Correduria{
    public static void main(String[] args) {
        PresentadorAdmision presentador = new PresentadorAdmision(new VistaConsola());
        presentador.admitirCliente();
        presentador.admitirBien();
        presentador.imprimirMejorOferta(presentador.calcularOferta());
    }
}