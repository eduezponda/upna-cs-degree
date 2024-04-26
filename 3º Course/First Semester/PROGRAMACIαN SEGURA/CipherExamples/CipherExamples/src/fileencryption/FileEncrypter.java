package fileencryption;
/**
 *
 * @author MAZ
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
//
import cipherconfigurator.AESCipherConfigurator;
import java.text.Normalizer;
//
public final class FileEncrypter {
    
  static public void main (final String[] args) throws GeneralSecurityException {
    
    // Configurador de objetos Cipher
    final AESCipherConfigurator cipherFactory = new AESCipherConfigurator("CFB", "PKCS5Padding");    
    
    // Ficheros con datos    
    final String path = System.getProperty("user.dir") + File.separator
                                              + "data" + File.separator;    
    final File inFile  = new File(path + args[0]);
    final File outFile = new File(path + args[0] + ".enc");   
    
    // Obtención de datos de contraseña
    //final char[] passwd = Normalizer.normalize(args[1], Normalizer.Form.NFKC).toCharArray();
//    System.out.print("Introduzca contraseña: ");    
    final char[] passwd = System.console().readPassword("Introduzca contraseña: ");

    final Cipher encrypter = cipherFactory.getEncrypter(AESCipherConfigurator.toBytes(passwd));
      
    try (final FileInputStream  fis = new FileInputStream(inFile);
         final FileOutputStream fos = new FileOutputStream(outFile)) {

      final byte[] block = new byte[73];
      for (int len = fis.read(block); len > 0; len = fis.read(block)) {
        final byte[] bytes = encrypter.update(block, 0, len);
        fos.write(bytes, 0, bytes.length);        
        System.out.println("block = " + len + " bytes = " + bytes.length);
      }
      fos.write(encrypter.doFinal());
        
    } catch (final IOException ex) {
      Logger.getLogger(FileEncrypter.class.getName()).log(Level.SEVERE, null, ex);
    }
    
  }
    
}