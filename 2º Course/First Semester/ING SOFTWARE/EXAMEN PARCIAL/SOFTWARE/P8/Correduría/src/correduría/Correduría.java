package correduría;

/**
 *
 * @author Sol Blasco Di Rosa (blasco.138141)
 */
public class Correduría {

    public static void main(String[] args) {
        PresentadorAdmision presentador = new PresentadorAdmision(new VistaConsola());
        
        Cliente cliente = presentador.registrarCliente();
        
        Bien bien = presentador.registrarBien();
        
        presentador.ofrecerOfertaMásVentajosa(cliente, bien);
        
    }
    
}
