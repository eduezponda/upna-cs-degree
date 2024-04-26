package bytecounter;
/**
 *
 * @author MAZ
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.AccessControlException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.logging.Level;
import java.util.logging.Logger;
//
public final class ByteCounter {
  
  static private final String CLASS_NAME = ByteCounter.class.getName();
  static private final Logger LOGGER = Logger.getLogger(CLASS_NAME);

  private int _count (final String filename) throws FileNotFoundException, IOException {
    
    final String path = System.getProperty("user.dir") + File.separator +
                                                "data" + File.separator;    
    
    final File file = new File(path + filename);
    try (final FileInputStream     fis = new FileInputStream(file);
         final BufferedInputStream bis = new BufferedInputStream(fis)) {
      
      int bytesCounter = 0;
      while (bis.read() != -1) {
        ++bytesCounter;
      }
      
      return bytesCounter;
      
    } catch (final FileNotFoundException ex) {
      LOGGER.log(Level.WARNING, "{0}: {1}", new Object[]{CLASS_NAME, ex.getMessage()});
      throw ex;
    } catch (final IOException ex) {
      LOGGER.log(Level.WARNING, "{0}: {1}", new Object[]{CLASS_NAME, ex.getMessage()});
      throw ex;
    }

  }
  
  public int count (final String filename) throws FileNotFoundException, IOException {
    
    try {
      
      return AccessController.doPrivileged(new PrivilegedExceptionAction<Integer>() {
        
        @Override
        public Integer run () throws FileNotFoundException, IOException {
          return _count(filename);
        }
        
      });
      
      
    } catch (final AccessControlException ex) {
      LOGGER.log(Level.WARNING, "Intento de operación no autorizada en {0}: {1}", new Object[]{CLASS_NAME, ex.getMessage()});
    } catch (final PrivilegedActionException ex) {
      if (ex.getException() instanceof FileNotFoundException) {
        throw (FileNotFoundException) ex.getException();
      } else if (ex.getException() instanceof IOException) {
        throw (FileNotFoundException) ex.getException();
      }
    }
    
    return -1;
    
  }
  
}