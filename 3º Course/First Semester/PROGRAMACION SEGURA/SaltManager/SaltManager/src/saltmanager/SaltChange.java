package saltmanager;
/**
 *
 * @author MAZ
 */
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
//
public final class SaltChange {
  
  static private final String CLASS_NAME = SaltChange.class.getName();  
  static private final Logger LOGGER = Logger.getLogger(CLASS_NAME);

  private boolean _change () {
    
    final String path = System.getProperty("user.dir") + File.separator
                                              + "data" + File.separator;
    final String filename = "salt.data";
    final File file = new File(path + filename);

    try (final DataOutputStream os =
            new DataOutputStream(new FileOutputStream(file))) {

      final SecureRandom rg = new SecureRandom();

      final byte[] salt = new byte[64];
      rg.nextBytes(salt);
      final String saltB64 = Base64.getEncoder().encodeToString(salt);
      os.writeUTF(saltB64);
      return true;

    } catch (final FileNotFoundException ex) {
      LOGGER.log(Level.WARNING, "fichero {0} no encontrado", filename);
    } catch (final IOException ex) {
      LOGGER.log(Level.WARNING, "problema de escritura en fichero {0}", filename);
    }
    
    return false;    
        
  }
  public boolean change () {
      return AccessController.doPrivileged(new PrivilegedAction<Boolean>(){
          @Override
          public Boolean run (){
              return _change();
          }
      });
  }
  
}