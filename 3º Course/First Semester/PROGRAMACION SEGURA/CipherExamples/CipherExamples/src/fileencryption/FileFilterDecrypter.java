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
import java.util.zip.InflaterOutputStream;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
//
public final class FileFilterDecrypter {

  static public void main (final String[] args) throws Exception {

    // Configurador de objetos Cipher
    final AESCipherConfigurator cipherFactory = new AESCipherConfigurator("CFB", "PKCS5Padding");

    // Ficheros con datos
    final String path = System.getProperty("user.dir") + File.separator
                                              + "data" + File.separator;    
    final File inFile = new File(path + args[0]);
    final File outFile = new File(path + args[0] + ".dec");

    // Obtencion de datos de contraseña
    final char[] passwd = Normalizer.normalize(args[1], Normalizer.Form.NFKC).toCharArray();
//    System.out.print("Introduzca contraseña: ");    
//    final char[] passwd = System.console.password();

    final Cipher decrypter = cipherFactory.getDecrypter(AESCipherConfigurator.toBytes(passwd));

    try (final FileInputStream  fis = new FileInputStream(inFile);
         final FileOutputStream fos = new FileOutputStream(outFile);
         final InflaterOutputStream ios = new InflaterOutputStream(fos);
         final CipherOutputStream cos = new CipherOutputStream(ios, decrypter)) { //añadimos el cipheroutputstream y cambiamos el write. Cambiamos ios por fos
         
        fis.transferTo(cos);
/*      final byte[] block = new byte[67];
      for (int len = fis.read(block); len > 0; len = fis.read(block)) {
        //final byte[] bytes = decrypter.update(block, 0, len);
        cos.write(block, 0, len);
        //System.out.println("block = " + len + " bytes = " + bytes.length);
      }
      fos.write(decrypter.doFinal());
 */     
    } catch (final FileNotFoundException ex) {
      Logger.getLogger(FileFilterDecrypter.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

}