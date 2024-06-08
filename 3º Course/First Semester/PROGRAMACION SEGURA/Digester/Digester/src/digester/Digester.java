package digester;
/**
 *
 * @author MAZ
 */
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
//
public final class Digester {
  
  static private final String CLASS_NAME = Digester.class.getName();
  static private final Logger LOGGER = Logger.getLogger(CLASS_NAME);

  private final MessageDigest md;

  public Digester (final String algorithm) throws NoSuchAlgorithmException {
    try {
      this.md = MessageDigest.getInstance(algorithm);
    } catch (final NoSuchAlgorithmException ex) {
      throw new NoSuchAlgorithmException(algorithm);
    }
  }

  public String getAlgorithm () {
    return md.getAlgorithm();
  }

  public byte[] digest (final byte[] bytes) {
    
      try {
          return this.digest(new ByteArrayInputStream(bytes));
      } catch (final IOException ex) {
          //esto no tendria que pasar
          return new byte[0];
      }
    
  }

  public byte[] digest (final InputStream is) throws IOException {
    try (final BufferedInputStream bis = new BufferedInputStream(is)){
        final DigestInputStream dis = new DigestInputStream(bis, md);
        final byte[] buffer = new byte[1024];
        while (dis.available() > 0){
            final int len = dis.read(buffer); //sería necesario el len si quieres procesar la información
           // md.update(buffer,0,len); ( se descomentaria si quieres dejar de usar dis. En ese caso pon bis en todos los dis (len y available)
        }
         return md.digest();
    }
  }
}