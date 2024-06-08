package app;
/**
 *
 * @author MAZ
 */
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import objetos.ComplexCompany;
//
public final class Client {
  
  /**
   * @param args the command line arguments
   */ 
  public static void main (final String[] args) {

    final int port = 1777;
    final String hostAddress = "127.0.0.1"; 
    final InetAddress host;
    try {
      host = InetAddress.getByName(hostAddress);
    } catch (final UnknownHostException ex) {
      System.err.println("Unable to resolve host address " + hostAddress);
      return;
    }
    
    // Connection establishment    
    try (final Socket socket = new Socket(host, port)) {
      
      //System.out.println(socket.getPort());
      
      // Prepare send and receive streams      
      try(final ObjectInputStream  is = new ObjectInputStream(socket.getInputStream());
          final ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream())) {

        // Testing the socket        
        os.writeObject(ComplexCompany.getInstance());
        try {

          String str;
          while ((str = (String) is.readObject()) != null) {
            System.out.println("Plain client: " + str);
            os.writeObject("Bye");
            if (str.equals("Bye"))
              break;
          }

        } catch (final ClassNotFoundException ex) {
          System.err.println("class not found");
        }
      
      } catch (final IOException ex) {
        System.err.println("data transmission problem");
      } 
      
    } catch (final IOException ex) {
      System.err.println("error creating socket");
    }

  }

}