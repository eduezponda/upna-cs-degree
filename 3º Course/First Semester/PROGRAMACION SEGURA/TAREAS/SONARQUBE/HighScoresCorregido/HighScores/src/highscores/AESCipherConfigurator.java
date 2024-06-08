package highscores;
/**
 *
 * @author MAZ
 */
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
//
public final class AESCipherConfigurator {
  
  static private final Logger LOGGER = Logger.getLogger(AESCipherConfigurator.class.getName());  

  static private final String BLOCK_CIPHER = "AES";
  static private final String PBE_BLOCK_CIPHER = "PBEWithHmacSHA256AndAES_128";  
  static private final int KEY_LENGTH  = 16; 
  static private final int SALT_LENGTH = 256;
  
  private final String operationMode;
  private final String cipherTransform;
  private final String pbeCipherTransform;
  private final MessageDigest md;
  private final SecureRandom rg;
  
  static public byte[] toBytes (final char[] chars) {
    final CharBuffer charBuffer = CharBuffer.wrap(chars);
    final ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);
    final byte[] bytes = Arrays.copyOfRange(byteBuffer.array(),
            byteBuffer.position(), byteBuffer.limit());
    return bytes;
  }  

  public AESCipherConfigurator (final String mode,
                                final String paddingScheme) throws GeneralSecurityException {

    try {
      this.operationMode = mode;
      this.cipherTransform = BLOCK_CIPHER + "/" + operationMode + "/" + paddingScheme;
      this.pbeCipherTransform = PBE_BLOCK_CIPHER + "/CBC/PKCS5Padding";
      this.md = MessageDigest.getInstance("SHA3-256");
      this.rg = new SecureRandom();
    } catch (final NoSuchAlgorithmException ex) {
      LOGGER.logp(Level.INFO, AESCipherConfigurator.class.getName(), "constructor", ex.getMessage());
      throw new GeneralSecurityException();
    }
    
  }
  
  public AESCipherConfigurator () throws GeneralSecurityException {
    this("CFB", "NoPadding");
  }  

  public Cipher getEncrypter (final char[] passwd,
                              final int iterations) throws GeneralSecurityException {
    if (iterations > 0)
      return getCipher(Cipher.ENCRYPT_MODE, passwd, iterations);
    else
      throw new IllegalArgumentException("Illegal number of iterations");
  }
  
  public Cipher getEncrypter (final char[] passwd,
                              final byte[] encodedParams) throws GeneralSecurityException, IOException {
    return getCipher(Cipher.ENCRYPT_MODE, passwd, encodedParams);
  }

  public Cipher getDecrypter (final char[] passwd,
                              final byte[] encodedParams) throws GeneralSecurityException, IOException {
    return getCipher(Cipher.DECRYPT_MODE, passwd, encodedParams);
  }  
  
  public Cipher getEncrypter (final byte[] bytes) throws GeneralSecurityException {
    return getCipher(Cipher.ENCRYPT_MODE, bytes);
  }

  public Cipher getDecrypter (final byte[] bytes) throws GeneralSecurityException {
    return getCipher(Cipher.DECRYPT_MODE, bytes);
  }

  private Cipher getCipher (final int mode,
                            final byte[] rawMaterial) throws GeneralSecurityException {
    
    try {

      // Generación del resumen digital del material en bruto recibido
      final byte[] bytes = md.digest(rawMaterial);

      // Preparación de clave simétrica
      final SecretKeySpec key = new SecretKeySpec(bytes, 0, KEY_LENGTH, BLOCK_CIPHER);

      // Instaciaciçon y configuración del cifrador
      final Cipher cipher = Cipher.getInstance(cipherTransform);
      if (operationMode.compareTo("ECB") != 0) {
        final IvParameterSpec iv = new IvParameterSpec(bytes, KEY_LENGTH, KEY_LENGTH);
        cipher.init(mode, key, iv);
      } else {
        cipher.init(mode, key);
      }

      // Borrado de datos sensibles
      Arrays.fill(rawMaterial, (byte) 0);
      Arrays.fill(bytes, (byte) 0);

      return cipher;

    } catch (final NoSuchAlgorithmException |
                   NoSuchPaddingException |
                   InvalidAlgorithmParameterException ex) {
      LOGGER.log(Level.INFO, "", ex.getCause());
      throw new GeneralSecurityException();
    }
    
  }
  
  private Cipher getCipher (final int mode,
                            final char[] password,
                            final int iterations) throws GeneralSecurityException {
    
    try {
      
      // Generación de clave secreta a partir de contraseña
      final PBEKeySpec keySpec = new PBEKeySpec(password);
      final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(PBE_BLOCK_CIPHER);
      final SecretKey key = keyFactory.generateSecret(keySpec);
      keySpec.clearPassword(); // Se borra el duplicado interno del material de la contraseña
      
      // Borrado de soporte de contraseña
      Arrays.fill(password, (char) 0);      

      // Generación del vector de inicialización
      final byte[] ivRawData = new byte[KEY_LENGTH];
      rg.nextBytes(ivRawData);
      final IvParameterSpec iv = new IvParameterSpec(ivRawData);
      
      // Parámetros de configuración del cifrador PBE
      final byte[] salt = new byte[SALT_LENGTH];
      rg.nextBytes(salt);
      final PBEParameterSpec params = new PBEParameterSpec(salt, iterations, iv);

      // Instanciación del cifrador PBE
      final Cipher cipher = Cipher.getInstance(pbeCipherTransform);
      
      // Configuración del cifrador PBE
      cipher.init(mode, key, params);

      return cipher;

    } catch (final NoSuchAlgorithmException |
                   NoSuchPaddingException |
                   InvalidAlgorithmParameterException ex) {
      LOGGER.log(Level.INFO, "", ex.getCause());
      throw new GeneralSecurityException();
    }
    
  }

  private Cipher getCipher (final int mode,
                            final char[] password,
                            final byte[] encodedParams) throws GeneralSecurityException, IOException {
    
    try {
      
      // Reconstrucción de parámetros del cifrador PBE
      final AlgorithmParameters algParams = AlgorithmParameters.getInstance(PBE_BLOCK_CIPHER);
      algParams.init(encodedParams);

      // Generación de clave secreta a partir de contraseña
      final PBEKeySpec keySpec = new PBEKeySpec(password);
      final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(PBE_BLOCK_CIPHER);
      final SecretKey key = keyFactory.generateSecret(keySpec);
      keySpec.clearPassword(); // Se borra el duplicado interno de la contraseña
      
      // Borrado de datos sensibles
      Arrays.fill(password, (char) 0);      
      
      // Instanciación del cifrador PBE
      final Cipher cipher = Cipher.getInstance(pbeCipherTransform);
      
      // Configuración del cifrador PBE
      cipher.init(mode, key, algParams);

      return cipher;

    } catch (final NoSuchAlgorithmException |
                   NoSuchPaddingException |
                   InvalidAlgorithmParameterException ex) {
      LOGGER.log(Level.SEVERE, "", ex.getCause());
      throw new GeneralSecurityException();
    } catch (final IOException ex) {
      LOGGER.log(Level.SEVERE, null, ex);
      throw new IOException();
    }
    
  }  

}