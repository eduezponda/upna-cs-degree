package fileencryption;
/**
 *
 * @author MAZ
 */
import cipherconfigurator.AESCipherConfigurator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.Normalizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.DeflaterInputStream;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
//
public final class FileFilterEncrypter {

  static public void main(final String[] args) throws Exception {

    // Configurador de objetos Cipher
    final AESCipherConfigurator cipherFactory = new AESCipherConfigurator("CFB", "PKCS5Padding");

    // Ficheros con datos    
    final String path = System.getProperty("user.dir") + File.separator
                                              + "data" + File.separator;    
    final File inFile = new File(path + args[0]);
    final File outFile = new File(path + args[0] + ".enc");

    // Obtencion de datos de contraseña
    final char[] passwd = Normalizer.normalize(args[1], Normalizer.Form.NFKC).toCharArray();
//    System.out.print("Introduzca contraseña: ");    
//    final char[] passwd = System.console.password();

    final Cipher encrypter = cipherFactory.getEncrypter(AESCipherConfigurator.toBytes(passwd));

    try (final FileInputStream  fis = new FileInputStream(inFile);
         final DeflaterInputStream dis = new DeflaterInputStream(fis);
         final CipherInputStream cis = new CipherInputStream(dis, encrypter); //hemos añadido esto, hemos cambiado los read y block por bytes, y len por bytes.length
         final FileOutputStream fos = new FileOutputStream(outFile)) {
        cis.transferTo(fos);

/*      final byte[] block = new byte[73];
      for (int len = cis.read(block); len > 0; len = cis.read(block)) { //recibimos información ya encriptada. Con fis no pasaria eso
        //final byte[] bytes = encrypter.update(block, 0, len);
        fos.write(block, 0, len);        
        //System.out.println("block = " + len + " bytes = " + bytes.length);
      }
      fos.write(encrypter.doFinal());
*/
    } catch (final FileNotFoundException ex) {
      Logger.getLogger(FileFilterEncrypter.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

}