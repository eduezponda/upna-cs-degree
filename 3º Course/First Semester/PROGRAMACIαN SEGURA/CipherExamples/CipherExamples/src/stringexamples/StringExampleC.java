package stringexamples;
/**
 *
 * @author MAZ
 */
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.text.Normalizer;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
//
public final class StringExampleC {

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
    
    // Modo de operación con bloques de cifrado
    final String operationMode = args[1];    
    // Método de relleno
    final String paddingScheme = args[2];    
    
    // Array de octetos con contraseña
    final byte[] password = Normalizer.normalize(args[3], Normalizer.Form.NFKC).getBytes();

    try {
      
      // Objeto para generar huella digital
      final MessageDigest md = MessageDigest.getInstance("SHA3-256");
      // Huella digital de 32 octetos de la constraseña
      final byte[] bytes = md.digest(password);

      final SecretKeySpec key = new SecretKeySpec(bytes, 0, 16, "AES");

      // Instanciacion de un cifrador
      final String cipherTransform = "AES/" + operationMode + "/" + paddingScheme;
      final Cipher cipher = Cipher.getInstance(cipherTransform);
      
      // Configuración para encriptado
      if (operationMode.compareTo("ECB") != 0) {
        final IvParameterSpec iv = new IvParameterSpec(bytes, 16, 16);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
      } else {
        cipher.init(Cipher.ENCRYPT_MODE, key);
      }
      
      // Encriptado
      final byte[] encryptedData = cipher.doFinal(message.getBytes(Charset.defaultCharset()));
      System.out.println("Mensaje cifrado:    " + new String(encryptedData, Charset.forName("UTF-8")));
      System.out.println("Octetos:            " + toHex(encryptedData));
      System.out.println();

      // Configuración para desencriptado
      if (operationMode.compareTo("ECB") != 0) {
        final IvParameterSpec iv = new IvParameterSpec(bytes, 16, 16);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
      } else {
        cipher.init(Cipher.DECRYPT_MODE, key);
      }

      // Desencriptado
      final byte[] decryptedData = cipher.doFinal(encryptedData);
      System.out.println("Mensaje descifrado: " + new String(decryptedData, Charset.forName("UTF-8")));
      System.out.println("Octetos:            " + toHex(decryptedData));
      
      // Los datos sensibles se eliminan de memoria RAM
      Arrays.fill(bytes, (byte) 0);
      Arrays.fill(password, (byte) 0);   
    
    } catch (final GeneralSecurityException ex) {
      System.out.println(ex.getMessage());
      System.err.println("Revisa parámetros empleado para configurar cifrador o resumen digital");
    }

  }

}