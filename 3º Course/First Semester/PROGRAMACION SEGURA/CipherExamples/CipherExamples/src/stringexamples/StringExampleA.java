package stringexamples;
/**
 *
 * @author MAZ
 */
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.text.Normalizer;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
//
public final class StringExampleA {

  private static String toHex (final byte[] bytes) {
    final BigInteger x = new BigInteger(+1, bytes);
    return String.format("%0" + (bytes.length << 1) + "x", x);
  }

  public static void main (final String[] args) throws GeneralSecurityException {
       
    // Mensaje a encriptar
    final String message = Normalizer.normalize(args[0], Normalizer.Form.NFKC);
    System.out.println("Mensaje en claro:   " + message);
    System.out.println("Octetos:            " + toHex(message.getBytes(Charset.forName("UTF-8"))));
    System.out.println(message.getBytes(Charset.forName("UTF-8")).length);
    
    try {
      
      // Se instancia un generador random para el vector de inicialización.
      final SecureRandom rg = SecureRandom.getInstance("SHA1PRNG");      

      // Se instancia un cifrador
      final Cipher cipher = Cipher.getInstance("AES/CFB/PKCS5Padding");
      
      // Se instancia e inicializa un generador de claves secretas.
      final KeyGenerator kg = KeyGenerator.getInstance("AES");
      kg.init(128);
      
      // Se genera una clave para el algoritmo AES.
      final SecretKey key = kg.generateKey();
      
      // Se genera el vector de inicialización.
      final byte[] ivRawData = new byte[16];
      rg.nextBytes(ivRawData);
      final AlgorithmParameterSpec iv = new IvParameterSpec(ivRawData);
      
      // Se inicializa el cifrador en modo encriptado.
      cipher.init(Cipher.ENCRYPT_MODE, key, iv);
      
      // Encriptado del mensaje
      final byte[] encryptedData = cipher.doFinal(message.getBytes(Charset.defaultCharset()));
      System.out.println("Mensaje cifrado:    " + new String(encryptedData, Charset.forName("UTF-8")));
      System.out.println("Octetos:            " + toHex(encryptedData));
      System.out.println();      
      
      // Se cambia a modo desencriptado (manteniendo clave y vector de inicialización).
      cipher.init(Cipher.DECRYPT_MODE, key, iv);      
      
      // Desencriptado del mensaje
      final byte[] decryptedData = cipher.doFinal(encryptedData);
      System.out.println("Mensaje descifrado: " + new String(decryptedData, Charset.forName("UTF-8")));
      System.out.println("Octetos:            " + toHex(decryptedData));      
    
    } catch (final GeneralSecurityException ex) {
      System.out.println(ex.getMessage());
      System.err.println("Revisa parámetros empleado para configurar cifrador o generador random");
    }

  }

}