package distributed.service;
/**
 *
 * @author MAZ
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
//
final class SortingTask implements Runnable {
  
  static private final String CLASS_NAME = SortingTask.class.getName();
  static private final Logger LOGGER = Logger.getLogger(CLASS_NAME);    
  
  private final Socket socket;
  
  SortingTask (final Socket socket) {
    this.socket = socket;
  }

  @Override
  public void run () {
    
    try (final DataInputStream  is = new DataInputStream(socket.getInputStream());
         final DataOutputStream os = new DataOutputStream(socket.getOutputStream())) {
      
      // Se lee la longitud de la colección de datos a recibir.
      final int n = is.readInt();
      // Se crea un array con capacidad para almacenar los datos enviados por el cliente.
      final float[] data = new float[n];
      for (int j = 0; j < n; ++j) {
        data[j] = is.readFloat();
      }
      // Se ordena la colección de datos recibida.
      Arrays.sort(data);
      // Se devuelve la colección ordenada.
      for (int j = 0; j < n; ++j) {
        os.writeFloat(data[j]);
      }      
    
    } catch (final IOException ex) {
      LOGGER.log(Level.WARNING, "Problema de I/O", ex);
    }
    
  }
  
}
