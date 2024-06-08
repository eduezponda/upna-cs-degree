package filepermissions;
/**
 *
 * @author MAZ
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;
//
public final class WriteFile {
  
  static private final String CLASS_NAME = WriteFile.class.getName();  
  
  /**
   * @param args the command line arguments
   */
  public static void main (final String[] args) {
    
    if (args.length != 1) {
      System.out.println("Uso: " + CLASS_NAME + " <filename: string>");
      return;
    }      
    
    final String path = System.getProperty("user.dir") + File.separator +
                                                "data" + File.separator;
    final String inputFileName = path + args[0];            
    final File inputFile = new File(inputFileName);
    
    final String outputFileName = path + "upper" + args[0];
    final File outputFile = new File(outputFileName);
    
    try (final Scanner scanner = new Scanner(inputFile);
         final FileOutputStream fos = new FileOutputStream(outputFile)) {
     
      final Pattern pattern = Pattern.compile("[- \t\n\",;:.!?]+");
      scanner.useDelimiter(pattern);
      
      while (scanner.hasNext()) {
        final String token = scanner.next().toUpperCase() + " ";
        fos.write(token.getBytes());
      }
      
    } catch (final FileNotFoundException ex) {
      System.err.println("Fichero no encontrado");
    } catch (final IOException ex) {
      System.err.println("Error de I/O");
    }
    
  }  
  
}