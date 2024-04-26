package encryptedtransmission;
//
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.SealedObject;
//
import anonymoussecureassociation.AnonymousSecureAssociation;
//
public final class SocketServer {
  
  static final String CLASS_NAME = SocketServer.class.getName();
  static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

  public static void main (final String args[]) {

    final int portNumber = 4000;    
    try (final ServerSocket servSocket = new ServerSocket(portNumber)) {
      
      System.out.println("Esperando conexión por puerto " + portNumber);
      
      try (final Socket socket = servSocket.accept();
           final ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
           final ObjectInputStream  is = new ObjectInputStream(socket.getInputStream())) {
        
        // Instanciación del generador de acuerdo.
        final AnonymousSecureAssociation secureAssociation =
              new AnonymousSecureAssociation(is, os);
        // Aceptación (en primer lugar, se recibe la clave pública del otro agente).
        secureAssociation.accept();
        // Acuerdo generado; se extraen los cifradores.
        final Cipher encrypter = secureAssociation.getEncrypter();
        final Cipher decrypter = secureAssociation.getDecrypter();
        
        // Se recibe mensaje encriptado; se desencripta.
        final SealedObject sealedString = (SealedObject) is.readObject();
        final String mensaje = (String) sealedString.getObject(decrypter);
        System.out.println("Mensaje de cliente: " + mensaje);
        
        // Se encripta respuesta; se envía.
        final String respuesta = "Adios";
        final SealedObject _sealedString = new SealedObject(respuesta, encrypter);
        os.writeObject(_sealedString);
        os.flush();
        
      } catch (final GeneralSecurityException ex) {
        LOGGER.log(Level.SEVERE, "", ex);
      } catch (final ClassNotFoundException ex) {
        LOGGER.log(Level.SEVERE, "", ex);
      } 
      
    } catch (final IOException ex) {
      System.err.println("No se ha podido arrancar el servicio");
      LOGGER.log(Level.WARNING, "problema al abrir socket de servicio");
      LOGGER.log(Level.WARNING, "", ex);
    }
    
  }

}