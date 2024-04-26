package highscores;
/**
 *
 * @author MAZ
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.text.Normalizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
//
public final class EncryptPassword {
    private byte[] params;
    private byte[] encryptedData;
    private String key;
    private AESCipherConfigurator cipherConfigurator;
    private Cipher encrypter;

  public void doBytesEncryptedData (){
    
    // Mensaje a encriptar
    //final String password = "trustnoone";
    String password = System.getProperty("SECRET");
    final String message = Normalizer.normalize(password, Normalizer.Form.NFKC);
    /*System.out.println("Mensaje en claro:   " + message);
    System.out.println("Octetos:            " + toHex(message.getBytes(Charset.forName("UTF-8"))));
    System.out.println();  */
    
    try {
      // Configurador de objetos Cipher
      cipherConfigurator = new AESCipherConfigurator("CFB", "PKCS5Padding");
      // String con contraseña 
      key = inicialiseKey();
      key = Normalizer.normalize(key, Normalizer.Form.NFKC);
      // Instanciacion de un encriptador
      encrypter = cipherConfigurator.getEncrypter(key.toCharArray(), 10000);
      // Representación estándar de parámetros de configuración del cifrador.
      params = encrypter.getParameters().getEncoded();
      // Encriptado
      encryptedData = encrypter.doFinal(message.getBytes(Charset.defaultCharset()));
      /*System.out.println("Mensaje cifrado:    " + new String(encryptedData, Charset.forName("UTF-8")));
      System.out.println("Octetos:            " + toHex(encryptedData));
      System.out.println();*/

    } catch (final GeneralSecurityException ex) {
      System.out.println(ex.getMessage());
      System.err.println("Revisa parámetros del cifrador");

    }   catch (IOException ex) {
            Logger.getLogger(EncryptPassword.class.getName()).log(Level.SEVERE, null, ex);
    }

  }
  public byte[] getParams(){
      return params;
  }
  public byte[] getEncryptedData(){
      return encryptedData;
  }
  public String getKey(){
      return key;
  }
  public AESCipherConfigurator getCipher(){
      return cipherConfigurator;
  }
  public String inicialiseKey() throws IOException{
    final String targetDirectory = System.getProperty("user.dir") + File.separator
                                              + "data";
    final String filePath = targetDirectory + File.separator + "key.txt";
    File keyFile = new File(filePath);
    String canonicalDestinationPath = keyFile.getCanonicalPath();

    if (!canonicalDestinationPath.startsWith(targetDirectory)) {
        throw new IOException("Entry is outside of the target directory");
    } else if (!keyFile.exists()) {
        throw new IOException("File does not exists in the target directory");
    }

    
    try (BufferedReader br = new BufferedReader(new FileReader(keyFile))) {
        return br.readLine();
    } catch (FileNotFoundException ex) {
        Logger.getLogger(EncryptPassword.class.getName()).log(Level.SEVERE, null, ex);
        return null;
    } catch (IOException ex) {
        Logger.getLogger(EncryptPassword.class.getName()).log(Level.SEVERE, null, ex);
        return null;
    }
    
  }
  
}