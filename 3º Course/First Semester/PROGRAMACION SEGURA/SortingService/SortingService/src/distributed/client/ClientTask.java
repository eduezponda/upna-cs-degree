package distributed.client;
/**
 *
 * @author MAZ
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
//
class ClientTask implements Runnable {
  
  static private final String CLASS_NAME = ClientTask.class.getName();
  static private final Logger LOGGER = Logger.getLogger(CLASS_NAME);  
  
  final String HOST_ADDRESS = "127.0.0.1";  
  static private final int SERVICE_PORT = 7777;
  
  private final float[] data;
  
  ClientTask (final float[] data) {
    this.data = data;
  }

  @Override
  public void run () {
    
    final InetAddress  host;
    try {
      host = InetAddress.getByName(HOST_ADDRESS);
    } catch (final UnknownHostException ex) {
      LOGGER.log(Level.WARNING, "incapaz de resolver dirección " + HOST_ADDRESS, ex.getMessage());
      return;
    }
    
    try (final Socket socket = new Socket(host, SERVICE_PORT)) {
      
      try (final DataOutputStream os = new DataOutputStream(socket.getOutputStream());
           final DataInputStream  is = new DataInputStream(socket.getInputStream())) {
        
        // Se informa de la longitud del array de valores de tipo float.
        final int n = data.length;
        os.writeInt(n);
        // Se envía la colección de valores
        for (int j = 0; j < n; ++j) {
          os.writeFloat(data[j]);
        }
        // Se recibe la colección ordenada
        for (int j = 0; j < n; ++j) {
          data[j] = is.readFloat();
        }        
        
      }
      
    } catch (final IOException ex) {
      LOGGER.log(Level.WARNING, "problema al establecer conexión con servicio", ex.getMessage());
    }
    
  }
  
}
