package filepermissions;
/**
 *
 * @author MAZ
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;
//
public final class ReadFile {
  
  static private final String CLASS_NAME = ReadFile.class.getName();  

  /**
   * @param args the command line arguments
   */
  public static void main (final String[] args) {
      
    if (args.length != 2) {
      System.out.println("Uso: " + CLASS_NAME + " <filename: string> <word: string>");
      return;
    }
    
    final String path = System.getProperty("user.dir") + File.separator +
                                                "data" + File.separator;
    final String fileName = path + args[0];
    final File file = new File(fileName);
    
    final String word = args[1].toLowerCase();
    
    try (final Scanner scanner = new Scanner(file)) {
      
      final Pattern pattern = Pattern.compile("[- \t\n\",;:.!?]+");
      scanner.useDelimiter(pattern);
      
      int wordCounter = 0;
      while (scanner.hasNext()) {
        final String token = scanner.next().toLowerCase();
        if (token.equals(word))
          ++wordCounter;
      }
      
      System.out.println("Word " + word + ": " + wordCounter + " repetitions");
      
    } catch (final FileNotFoundException ex) {
      System.err.println("Fichero no encontrado");
    }
    
  }
  
}