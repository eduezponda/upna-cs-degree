package correduría;

/**
 *
 * @author Sol Blasco Di Rosa (blasco.138141)
 */
public interface IVistaAdmision {
       String consultarNombre();
       
       String consultarApellido();
       
       Integer consultarAnnoNacimiento();
       
       Integer consultarSalarioAnual();
       
       String consultarTipoBien();
       
       Integer consultarValorBien(String tipoBien);
       
       void mostrarEnListado(String nombreS, int importe, int comision);
}
