package loggerexample;
/**
 *
 * @author MAZ
 * https://www.journaldev.com/977/logger-in-java-logging-example
 */
import java.util.logging.Level;
import java.util.logging.Logger;

public final class LoggerExample1 {
  
  // Registro de log de la aplicación
  static private final Logger LOGGER = Logger.getLogger(LoggerExample1.class.getName());

  /**
   * @param args the command line arguments
   */
  public static void main (final String[] args) {
    
    for (int i = 0; i < 10; ++i) {
      // Niveles de gravedad en registro.
      // https://docs.oracle.com/en/java/javase/11/docs/api/java.logging/java/util/logging/Level.html
      // Registros de logging
      switch (i % 5) {
        case 0:
          LOGGER.log(Level.INFO, "Msg{0}", i);
          break;
        case 1:
          LOGGER.log(Level.WARNING, "Msg{0}", i);
          break;
        case 2:
          LOGGER.log(Level.CONFIG, "Msg{0}", i);
          break;          
        default:
          LOGGER.log(Level.SEVERE, "Msg{0}", i);
          break;
      }
    }

  }
  
}