package saltmanager;
/**
 *
 * @author MAZ
 */
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
//
public final class SaltRecovery {
  
  static private final String CLASS_NAME = SaltRecovery.class.getName();  
  static private final Logger LOGGER = Logger.getLogger(CLASS_NAME);

  private byte[] _recover () {
    
    final String path = System.getProperty("user.dir") + File.separator
                                              + "data" + File.separator;
    final String filename = "salt.data";
    final File file = new File(path + filename);

    try (final DataInputStream is =
            new DataInputStream(new FileInputStream(file))) {
 
      final String saltB64 = is.readUTF();
      return Base64.getDecoder().decode(saltB64);

    } catch (final FileNotFoundException ex) {
      LOGGER.log(Level.WARNING, "fichero no encontrado");
    } catch (final IOException ex) {
      LOGGER.log(Level.WARNING, "problema de lectura");
    }
    
    return new byte[0];
    
  }
  public byte[] recover(){
      return AccessController.doPrivileged(new PrivilegedAction<byte[]>(){
          @Override
          public byte[] run(){
              return _recover(); 
          }
      });
  }
 
}