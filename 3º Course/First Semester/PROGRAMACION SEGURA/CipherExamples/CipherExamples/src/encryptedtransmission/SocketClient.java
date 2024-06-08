package encryptedtransmission;

import cipherconfigurator.AESCipherConfigurator;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SealedObject;

public final class SocketClient {

  public static void main (String args[]) throws IOException {

    try {
      
      // Configurador de objetos Cipher
      final AESCipherConfigurator cipherFactory = new AESCipherConfigurator("CFB", "PKCS5Padding");
      
      // Obtencion de datos de contraseña
      final char[] passwd = args[0].toCharArray();
      // Preparacion del encriptador
      final Cipher encrypter = cipherFactory.getEncrypter(AESCipherConfigurator.toBytes(passwd));
      // Preparacion del desencriptador
      final Cipher decrypter = cipherFactory.getDecrypter(AESCipherConfigurator.toBytes(passwd));
      
      final int portNumber = 4000;
      
      try (final Socket socket = new Socket(InetAddress.getLocalHost(), portNumber);
           final ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
           final ObjectInputStream is = new ObjectInputStream(socket.getInputStream()) ) {
        
        final String mensaje = "Hola";
        //os.writeObject(new SealedObject(mensaje, encrypter));
        os.writeUTF(mensaje);
        os.flush();
        //encrypter.doFinal();
        
        //final String respuesta = is.readUTF();
        //decrypter.doFinal();
        final SealedObject _respuesta = (SealedObject) is.readObject();
        final String respuesta = (String) _respuesta.getObject(decrypter);
        System.out.println("Respuesta de servidor: " + respuesta);
        
      } catch (final IllegalBlockSizeException ex) {
        Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
      } catch (final BadPaddingException ex) {
        Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
      } catch (ClassNotFoundException ex) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    } catch (GeneralSecurityException ex) {
          Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
      }
    
  }

}