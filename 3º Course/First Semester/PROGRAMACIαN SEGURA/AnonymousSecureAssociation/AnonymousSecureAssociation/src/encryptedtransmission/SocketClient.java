package encryptedtransmission;
//
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.SealedObject;
//
import anonymoussecureassociation.AnonymousSecureAssociation;
//
public final class SocketClient {
  
  static final String CLASS_NAME = SocketClient.class.getName();
  static final Logger LOGGER = Logger.getLogger(CLASS_NAME);  

  public static void main (final String args[]) throws IOException {
      
    final int portNumber = 4000;

    try (final Socket socket = new Socket(InetAddress.getLocalHost(), portNumber);
         final ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
         final ObjectInputStream is = new ObjectInputStream(socket.getInputStream())) {

      try {    
        
        // Instanciación del generador de acuerdo.
        final AnonymousSecureAssociation secureAssociation =
              new AnonymousSecureAssociation(is, os);
        // Iniciativa (en primer lugar, se envía la clave pública propia).
        secureAssociation.init(4096);
        // Acuerdo generado; se extraen los cifradores.
        final Cipher encrypter = secureAssociation.getEncrypter();
        final Cipher decrypter = secureAssociation.getDecrypter();
        
        // Se encripta mensaje; se envía.
        final String mensaje = "Hola";
        final SealedObject sealedString = new SealedObject(mensaje, encrypter);
        os.writeObject(sealedString);
        os.flush();
        
        // Se recibe respuesta encriptada; se desencripta.
        final SealedObject _sealedString = (SealedObject) is.readObject();
        final String respuesta = (String) _sealedString.getObject(decrypter);
        System.out.println("Respuesta de servidor: " + respuesta);
        
      }  catch (final GeneralSecurityException ex) {
        LOGGER.log(Level.SEVERE, "", ex);
      } catch (final ClassNotFoundException ex) {
        LOGGER.log(Level.SEVERE, "", ex);
      }
      
    } catch (final IOException ex) {
      System.err.println("No se ha podido establecer conexión con el servicio");
    }
    
  }

}