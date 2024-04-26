package regexinjection;
/**
 *
 * @author MAZ
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//
final class UnsanitizedInput {

  // Ejemplo tomado de
  // https://wiki.sei.cmu.edu/confluence/display/java/IDS08-J.+Sanitize+untrusted+data+included+in+a+regular+expression RISK ASSESMENT (coste, riesgo en caso de fallo...)

  static public void main (final String[] args) {

    // Ubicación/identificación del fichero de datos.
    final String path = System.getProperty("user.dir") + File.separator
                                              + "data" + File.separator;
    final String filename = "log.data";
    final File file = new File(path + filename);

    // Se introduce la cadena de caracteres a buscar.
    String search;
    try (final Scanner scanner = new Scanner(System.in)) {
      System.out.print("Introduzca string a buscar: ");
      search = scanner.next();
    }

    // Se busca el string en las entradas del fichero marcadas como públicas.
    //
    // Patrón de búsqueda:
    final String regex = "(.*? +public\\[\\d+\\] +.*" + search + ".*)";
    // +: espacio en blanco; public: palabra public
    // meter en el gpt la explicación
    final Pattern searchPattern = Pattern.compile(regex);
    //
    // Búsqueda
    try (final FileInputStream fis = new FileInputStream(file);
         final FileChannel channel = fis.getChannel()) {

      // Get the file size and map it into memory
      final long size = channel.size();
      final MappedByteBuffer mappedBuffer =
              channel.map(FileChannel.MapMode.READ_ONLY, 0, size);
      final Charset charset = Charset.forName("UTF-8");
      final CharsetDecoder decoder = charset.newDecoder();
      // Read file into char buffer
      final CharBuffer log = decoder.decode(mappedBuffer);
      final Matcher logMatcher = searchPattern.matcher(log);
      while (logMatcher.find()) {
        final String match = logMatcher.group();
        if (!match.isEmpty()) {
          System.out.println(match);
        }
      }

    } catch (final FileNotFoundException ex) {
      System.out.println("Fichero no encontrado");
    } catch (final IOException ex) {
      System.err.println("thrown exception: " + ex.toString());
      final Throwable[] suppressed = ex.getSuppressed();
      for (int i = 0; i < suppressed.length; ++i) {
        System.err.println("suppressed exception: "
                + suppressed[i].toString());
      }
    }

  }

}
