package encryptedtransmission;

import cipherconfigurator.AESCipherConfigurator;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
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

public class SocketServer {

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
      final ServerSocket servSocket = new ServerSocket(portNumber);
      
      System.out.println("Esperando conexion por puerto " + portNumber);
      
      try (final Socket socket = servSocket.accept();
           final ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
           final ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream())) {
        
        //final String mensaje = is.readUTF();
        final SealedObject _mensaje = (SealedObject) is.readObject(); //objeto sellado
        final String mensaje = (String) _mensaje.getObject(decrypter);
        //decrypter.doFinal();
        System.out.println("Mensaje de cliente: " + mensaje);
        
        final String respuesta = "Adios";
        os.writeObject(new SealedObject(respuesta, encrypter)); 
        //os.writeUTF(respuesta);
        //encrypter.doFinal();
        
      } catch (final IllegalBlockSizeException ex) {
        Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
      } catch (final BadPaddingException ex) {
        Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
      } catch (ClassNotFoundException ex) {
            Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    } catch (GeneralSecurityException ex) {
          Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
      }
    
  }

}