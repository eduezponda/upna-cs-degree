package correduría;

/**
 *
 * @author Sol Blasco Di Rosa (blasco.138141)
 */
public interface Aseguradora {
    Integer calcularImporte(Cliente cliente, Bien bien);
    
    Integer calcularComision(Cliente cliente, Bien bien);
    
}
