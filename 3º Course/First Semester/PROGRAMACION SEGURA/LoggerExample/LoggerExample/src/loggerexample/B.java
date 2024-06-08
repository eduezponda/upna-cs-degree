package loggerexample;
/**
 *
 * @author MAZ
 */
import java.util.logging.Level;
import java.util.logging.Logger;

final class B {
  
  // Nombre de la clase
  static private final String CLASS_NAME = B.class.getName();
  // Registro de log de la aplicación
  static private final Logger LOGGER = Logger.getLogger(CLASS_NAME);
  
  void run () {
    System.out.println(CLASS_NAME + ": " + LOGGER.getName());
    for (int i = 0; i < 10; ++i) {
      // logging messages
      if ((i % 2) == 0)
        LOGGER.log(Level.SEVERE, "Msg{0}", i);
      else
        LOGGER.log(Level.INFO, "Msg{0}", i);
    }    
  }

}