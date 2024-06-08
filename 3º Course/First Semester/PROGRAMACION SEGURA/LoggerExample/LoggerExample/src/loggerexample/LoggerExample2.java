package loggerexample;
/**
 *
 * @author MAZ
 */
import java.util.logging.Level;
import java.util.logging.Logger;

public final class LoggerExample2 {
  
  // Nombre de la clase
  static private final String CLASS_NAME = LoggerExample2.class.getName();
  // Registro de log de la aplicación
  static private final Logger LOGGER = Logger.getLogger(CLASS_NAME);

  /**
   * @param args the command line arguments
   */
  public static void main (final String[] args) {
    
    System.out.println(CLASS_NAME + ": " + LOGGER.getName());
    for (int i = 0; i < 10; i++) {
      // logging messages
      LOGGER.log(Level.INFO, "Msg{0}", i);
    }
    
    final A a = new A();
    final B b = new B();
    
    a.run();
    b.run();

  }
  
}