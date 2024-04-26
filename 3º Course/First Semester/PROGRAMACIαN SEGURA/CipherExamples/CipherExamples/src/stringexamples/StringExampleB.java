package stringexamples;
/**
 *
 * @author MAZ
 */
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.text.Normalizer;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
//
public final class StringExampleB {

  private static String toHex (final byte[] bytes) {
    final BigInteger x = new BigInteger(+1, bytes);
    return String.format("%0" + (bytes.length << 1) + "x", x);
  }
  
  public static void main (final String[] args) throws GeneralSecurityException {
       
    // Mensaje a encriptar
    final String message = Normalizer.normalize(args[0], Normalizer.Form.NFKC);
    System.out.println("Mensaje en claro:   " + message);
    System.out.println("Octetos:            " + toHex(message.getBytes(Charset.forName("UTF-8"))));
    System.out.println();
    
    // Array de caracteres con contraseña
    final char[] password = Normalizer.normalize(args[1], Normalizer.Form.NFKC).toCharArray();
    
    // Número de iteraciones para preparar cifrador
    final int itn = Integer.parseInt(args[2]);
    
    try {
      
      // Se instancia un generador random.
      final SecureRandom rg = new SecureRandom(); //SecureRandom.getInstance("NativePRNGBlocking");      
      
      // Se instancia un cifrador basado en contraseña.
      final Cipher cipher = Cipher.getInstance("PBEWithHmacSHA256AndAES_128/CBC/PKCS5Padding");

      //
      // Generación de clave a partir de contraseña
      //
       // La pizca de sal debe tener una longitud mayor o igual que la clave (128 bits).
      final byte[] salt = new byte[37];
      rg.nextBytes(salt);
      final PBEKeySpec keySpec = new PBEKeySpec(password, salt, itn);
      final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithHmacSHA256AndAES_128");
      final SecretKey key = keyFactory.generateSecret(keySpec);

      // Generación de parámetros del cifrador (vector de inicialización)
      final byte[] ivRawData = new byte[16]; // El vector de inicialización ha de tener la misma longitud que la clave.
      rg.nextBytes(ivRawData);
      // Se instancia un objeto con los parámetros de configuración del cifrador.
      // Ese objeto incluye la pizca de sal, el número de iteraciones y el vector
      // de inicialización.
      final PBEParameterSpec params = new PBEParameterSpec(salt, itn, new IvParameterSpec(ivRawData));

      // Se inicializa el cifrador en modo encriptado 
      cipher.init(Cipher.ENCRYPT_MODE, key, params);
      
      // Encriptado del mensaje
      final byte[] encryptedData = cipher.doFinal(message.getBytes(Charset.defaultCharset()));
      System.out.println("Mensaje cifrado:    " + new String(encryptedData, Charset.forName("UTF-8")));
      System.out.println("Octetos:            " + toHex(encryptedData));
      System.out.println();      
      
      // Se cambia a modo desencriptado (manteniendo clave y parámetros)
      cipher.init(Cipher.DECRYPT_MODE, key, params);      
      
      // Desencriptado del mensaje
      final byte[] decryptedData = cipher.doFinal(encryptedData);
      System.out.println("Mensaje descifrado: " + new String(decryptedData, Charset.forName("UTF-8")));
      System.out.println("Octetos:            " + toHex(decryptedData));      
    
    } catch (final GeneralSecurityException ex) {
      System.out.println(ex.getMessage());
      System.err.println("Revisa parámetros del cifrador");
    }

  }

}