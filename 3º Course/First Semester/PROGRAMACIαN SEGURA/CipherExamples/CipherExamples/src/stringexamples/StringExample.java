package stringexamples;
/**
 *
 * @author MAZ
 */
import cipherconfigurator.AESCipherConfigurator;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.text.Normalizer;
import javax.crypto.Cipher;
//
public final class StringExample {

  private static String toHex (final byte[] bytes) {
    final BigInteger x = new BigInteger(+1, bytes);
    return String.format("%0" + (bytes.length << 1) + "x", x);
  }

  public static void main (final String[] args) throws GeneralSecurityException, IOException {
    
    // Mensaje a encriptar
    final String message = Normalizer.normalize(args[0], Normalizer.Form.NFKC);
    System.out.println("Mensaje en claro:   " + message);
    System.out.println("Octetos:            " + toHex(message.getBytes(Charset.forName("UTF-8"))));
    System.out.println();
    
    // String con contraseña
    final String password = Normalizer.normalize(args[1], Normalizer.Form.NFKC);   
    
    try {

      // Configurador de objetos Cipher
      final AESCipherConfigurator cipherConfigurator = new AESCipherConfigurator("CFB", "PKCS5Padding");

      // Instanciacion de un encriptador
      final Cipher encrypter = cipherConfigurator.getEncrypter(password.toCharArray(), 10000);
      // Representación estándar de parámetros de configuración del cifrador.
      final byte[] params = encrypter.getParameters().getEncoded();

      // Encriptado
      final byte[] encryptedData = encrypter.doFinal(message.getBytes(Charset.defaultCharset()));
      System.out.println("Mensaje cifrado:    " + new String(encryptedData, Charset.forName("UTF-8")));
      System.out.println("Octetos:            " + toHex(encryptedData));
      System.out.println();

      // Instanciacion de un desencriptador a partir de la misma clave
      // y los mismos parámetros.
      final Cipher decrypter =
              cipherConfigurator.getDecrypter(password.toCharArray(), params);

      // Desencriptado
      final byte[] decryptedData = decrypter.doFinal(encryptedData);
      System.out.println("Mensaje descifrado: " + new String(decryptedData, Charset.forName("UTF-8")));
      System.out.println("Octetos:            " + toHex(decryptedData));
    
    } catch (final GeneralSecurityException ex) {
      System.out.println(ex.getMessage());
      System.err.println("Revisa parámetros del cifrador");
    }

  }

}