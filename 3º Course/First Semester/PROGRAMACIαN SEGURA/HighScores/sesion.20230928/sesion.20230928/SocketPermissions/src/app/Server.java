package app;
/**
 *
 * @author MAZ
 */
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
//
import objetos.ComplexCompany;
//
public final class Server {

  /**
   * @param args the command line arguments
   */ 
  public static void main (final String[] args) {

    // Connection establishment    
    final int port = 1777;
    
    try (final ServerSocket serverSocket = new ServerSocket(port)) {
      
      System.out.println("Waiting for a connection on port " + port);

      try (final Socket fromClientSocket = serverSocket.accept()) {
        
        //System.out.println(fromClientSocket.getPort());
        
        // Prepare send and receive streams           
        try (final ObjectOutputStream os = new ObjectOutputStream(fromClientSocket.getOutputStream());
             final ObjectInputStream  is = new ObjectInputStream(fromClientSocket.getInputStream())) {

          try {

            ComplexCompany comp;
            while ((comp = (ComplexCompany) is.readObject()) != null) {

              comp.printCompanyObject();
              os.writeObject("Bye");
              break;

            }

          } catch (final ClassNotFoundException ex) {
            System.err.println("class not found");
          }

        } catch (final IOException ex) {  
          System.err.println("data transmission problem");
        }
    
      } catch (final IOException ex) {  
        System.err.println("error creating socket to client");
      }
      
    } catch (final IOException ex) {  
      System.err.println("error creating server socket");  
    }
    
  }

}