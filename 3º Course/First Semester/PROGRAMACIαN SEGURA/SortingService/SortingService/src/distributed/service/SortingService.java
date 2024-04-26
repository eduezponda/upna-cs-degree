package distributed.service;
/**
 *
 * @author MAZ
 */
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
//
final class SortingService implements Runnable {
  
  static private final int PORT = 7777; 

  static private final String CLASS_NAME = SortingService.class.getName();
  static private final Logger LOGGER = Logger.getLogger(CLASS_NAME);
  
  private volatile boolean isShutdown;
    
  @Override
  public void run () {
                
    LOGGER.log(Level.INFO, "arrancando servicio (hebra {0})", Thread.currentThread().getId());

    // Arranque de bucle de escucha.
    try (final ServerSocket serverSocket = new ServerSocket(PORT)) {
      
      LOGGER.log(Level.INFO, "servicio en escucha");
      
      isShutdown = false;
      int j = 0;
      do {

        try {
          
          // Se bloquea hasta que llegue una petición.
          final Socket socket = serverSocket.accept();

          LOGGER.log(Level.INFO, "petición entrante {0} desde {1}", new Object[]{++j, socket.getInetAddress()});

          final SortingTask sorter = new SortingTask(socket);
          final Thread serviceThread = new Thread(sorter);
          serviceThread.start();
        
        } catch (final IOException ex) {
          System.out.println();
        }
        
      } while (!isShutdown);

      LOGGER.log(Level.INFO, "escucha detenida");

    } catch (final IOException ex) {
      LOGGER.log(Level.WARNING, "problema al abrir el socket de servicio", ex.getMessage());
    }
    
    LOGGER.log(Level.INFO, "servicio finalizado (hebra {0})", Thread.currentThread().getId());

  }
  
  void stop () {
    this.isShutdown = true;
  }

}
